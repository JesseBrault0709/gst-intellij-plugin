package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class EGstGroovyPsiFile extends GstGroovyPsiFile {

    public EGstGroovyPsiFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider);
    }

    @Override
    public String toString() {
        return "eGstGroovyPsiFile";
    }

}
