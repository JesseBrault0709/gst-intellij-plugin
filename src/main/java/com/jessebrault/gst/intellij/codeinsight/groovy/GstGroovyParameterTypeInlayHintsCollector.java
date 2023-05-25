package com.jessebrault.gst.intellij.codeinsight.groovy;

import com.intellij.codeInsight.hints.declarative.*;
import com.intellij.openapi.project.DumbService;
import com.intellij.psi.CommonClassNames;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilCore;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.GrControlFlowOwner;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.blocks.GrClosableBlock;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.params.GrParameter;
import org.jetbrains.plugins.groovy.lang.psi.dataFlow.types.TypeAugmenter;
import org.jetbrains.plugins.groovy.lang.psi.dataFlow.types.TypeInferenceHelper;
import org.jetbrains.plugins.groovy.lang.psi.impl.synthetic.ClosureSyntheticParameter;
import org.jetbrains.plugins.groovy.lang.psi.typeEnhancers.GrVariableEnhancer;

import java.util.List;

public final class GstGroovyParameterTypeInlayHintsCollector implements SharedBypassCollector {

    @Override
    public void collectFromElement(@NotNull PsiElement psiElement, @NotNull InlayTreeSink inlayTreeSink) {
        if (!DumbService.isDumb(psiElement.getProject()) && !psiElement.getProject().isDefault()) {
            PsiUtilCore.ensureValid(psiElement);
            if (psiElement instanceof GrClosableBlock grClosableBlock && grClosableBlock.getParameterList().isEmpty()) {
                final GrParameter[] allParameters = grClosableBlock.getAllParameters();
                if (allParameters.length == 1) {
                    final ClosureSyntheticParameter itParameter = (ClosureSyntheticParameter) allParameters[0];
                    if (itParameter.isStillValid()) {
                        final PsiType type = getRepresentableType(itParameter);
                        if (type != null) {
                            inlayTreeSink.addPresentation(
                                    new InlineInlayPosition(
                                            grClosableBlock.getLBrace().getTextOffset() + grClosableBlock.getTextLength(),
                                            true,
                                            0
                                    ),
                                    List.of(new InlayPayload("gst.inlay.test", new StringInlayActionPayload(" it ->"))),
                                    "test tool tip",
                                    true,
                                    presentationTreeBuilder -> Unit.INSTANCE
                            );
                        }
                    }
                }
            }
        }
    }

    private static PsiType getRepresentableType(GrParameter grParameter) {
        final var inferredType = GrVariableEnhancer.getEnhancedType(grParameter);
        if (inferredType != null) {
            return inferredType.equalsToText(CommonClassNames.JAVA_LANG_OBJECT) ? null : inferredType;
        } else {
            final var owner = PsiTreeUtil.getParentOfType(grParameter, GrControlFlowOwner.class);
            if (owner == null) {
                return null;
            } else {
                final var ownerFlow = owner.getControlFlow();
                if (TypeInferenceHelper.isSimpleEnoughForAugmenting(ownerFlow)) {
                    return TypeAugmenter.Companion.inferAugmentedType(grParameter);
                } else {
                    return null;
                }
            }
        }
    }

}
