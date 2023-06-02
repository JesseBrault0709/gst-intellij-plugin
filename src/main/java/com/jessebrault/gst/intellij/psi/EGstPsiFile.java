package com.jessebrault.gst.intellij.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.jessebrault.gst.intellij.EGstFile;
import com.jessebrault.gst.intellij.EGstLanguage;
import org.jetbrains.annotations.NotNull;

public class EGstPsiFile extends PsiFileBase {

    public EGstPsiFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, EGstLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return EGstFile.INSTANCE;
    }

}
