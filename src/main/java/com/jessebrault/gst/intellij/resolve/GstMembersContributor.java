package com.jessebrault.gst.intellij.resolve;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import com.intellij.psi.ResolveState;
import com.intellij.psi.impl.light.LightVariableBuilder;
import com.intellij.psi.scope.ElementClassHint;
import com.intellij.psi.scope.PsiScopeProcessor;
import com.jessebrault.gst.intellij.psi.groovy.GstGroovyPsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.lang.psi.impl.synthetic.GroovyScriptClass;
import org.jetbrains.plugins.groovy.lang.resolve.NonCodeMembersContributor;
import org.jetbrains.plugins.groovy.lang.resolve.ResolveUtil;

public final class GstMembersContributor extends NonCodeMembersContributor {

    @Override
    public void processDynamicElements(
            @NotNull PsiType qualifierType,
            @Nullable PsiClass aClass,
            @NotNull PsiScopeProcessor processor,
            @NotNull PsiElement place,
            @NotNull ResolveState state
    ) {
        if (aClass instanceof GroovyScriptClass scriptClass
                && scriptClass.getContainingFile() instanceof GstGroovyPsiFile
                && ResolveUtil.shouldProcessProperties(processor.getHint(ElementClassHint.KEY))
        ) {
            processor.execute(new LightVariableBuilder<>("out", "java.io.Writer", aClass), state);
        }
    }

}
