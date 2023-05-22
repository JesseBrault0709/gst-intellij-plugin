package com.jessebrault.gst.intellij.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.jessebrault.gst.intellij.GstFile;
import com.jessebrault.gst.intellij.GstLanguage;
import org.jetbrains.annotations.NotNull;

public final class GstPsiFile extends PsiFileBase {

    public GstPsiFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, GstLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return GstFile.INSTANCE;
    }

}
