package com.jessebrault.gst.intellij.psi;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.PsiFileImpl;
import com.jessebrault.gst.intellij.EGstLanguage;
import com.jessebrault.gst.intellij.psi.groovy.EGstGroovyPsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.GroovyLanguage;

import java.util.Set;

public class EGstFileViewProvider extends GstFileViewProvider {

    public EGstFileViewProvider(
            @NotNull PsiManager manager,
            @NotNull VirtualFile virtualFile,
            boolean eventSystemEnabled
    ) {
        super(manager, virtualFile, eventSystemEnabled);
    }

    @Override
    public @NotNull Language getBaseLanguage() {
        return EGstLanguage.INSTANCE;
    }

    @Override
    protected @NotNull MultiplePsiFilesPerDocumentFileViewProvider cloneInner(@NotNull VirtualFile fileCopy) {
        return new EGstFileViewProvider(this.getManager(), fileCopy, false);
    }

    @Override
    public @NotNull Set<Language> getLanguages() {
        return Set.of(EGstLanguage.INSTANCE, GroovyLanguage.INSTANCE);
    }

    @Override
    protected @Nullable PsiFileImpl createPsiFileImpl(@NotNull Language target) {
        if (target.equals(EGstLanguage.INSTANCE)) {
            return new EGstPsiFile(this);
        } else if (target.equals(GroovyLanguage.INSTANCE)) {
            return new EGstGroovyPsiFile(this);
        } else {
            return null;
        }
    }

}
