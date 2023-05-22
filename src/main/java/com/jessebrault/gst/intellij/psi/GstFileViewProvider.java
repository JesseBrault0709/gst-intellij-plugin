package com.jessebrault.gst.intellij.psi;

import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.jessebrault.gst.intellij.GstLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.GroovyLanguage;

import java.util.Set;

public final class GstFileViewProvider extends MultiplePsiFilesPerDocumentFileViewProvider {

    public GstFileViewProvider(@NotNull PsiManager manager, @NotNull VirtualFile virtualFile, boolean eventSystemEnabled) {
        super(manager, virtualFile, eventSystemEnabled);
    }

    @Override
    public @NotNull Language getBaseLanguage() {
        return GstLanguage.INSTANCE;
    }

    @Override
    protected @NotNull MultiplePsiFilesPerDocumentFileViewProvider cloneInner(@NotNull VirtualFile fileCopy) {
        return new GstFileViewProvider(this.getManager(), fileCopy, this.isEventSystemEnabled());
    }

    @Override
    public @NotNull Set<Language> getLanguages() {
        return Set.of(GstLanguage.INSTANCE, GroovyLanguage.INSTANCE);
    }

    @Override
    protected @Nullable PsiFile createFile(@NotNull Language lang) {
        if (lang.equals(GstLanguage.INSTANCE)) {
            return LanguageParserDefinitions.INSTANCE
                    .forLanguage(GstLanguage.INSTANCE)
                    .createFile(this);
        } else if (lang.equals(GroovyLanguage.INSTANCE)) {
            // TODO
            return null;
        } else {
            return null;
        }
    }

}
