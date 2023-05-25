package com.jessebrault.gst.intellij.codeinsight.groovy;

import com.intellij.codeInsight.hints.declarative.InlayHintsCollector;
import com.intellij.codeInsight.hints.declarative.InlayHintsProvider;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public final class GstGroovyParameterTypeInlayHintsProvider
        implements InlayHintsProvider {
    
    @Override
    public InlayHintsCollector createCollector(@NotNull PsiFile psiFile, @NotNull Editor editor) {
        return new GstGroovyParameterTypeInlayHintsCollector();
    }

}
