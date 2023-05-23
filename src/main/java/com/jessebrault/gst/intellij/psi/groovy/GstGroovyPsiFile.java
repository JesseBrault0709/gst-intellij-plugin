package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiClass;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.imports.GrImportStatement;
import org.jetbrains.plugins.groovy.lang.psi.impl.GroovyFileBaseImpl;
import org.jetbrains.plugins.groovy.lang.resolve.imports.GroovyFileImports;

public class GstGroovyPsiFile extends GroovyFileBaseImpl {

    public GstGroovyPsiFile(FileViewProvider provider) {
        super(GstGroovyElements.GST_GROOVY_FILE, GstGroovyElements.GST_GROOVY_FILE, provider);
    }

    @Override
    public @NotNull String getPackageName() {
        return "";
    }

    @Override
    public @Nullable GrImportStatement addImportForClass(@NotNull PsiClass aClass) throws IncorrectOperationException {
        return null;
    }

    @Override
    public @NotNull GrImportStatement addImport(@NotNull GrImportStatement statement) throws IncorrectOperationException {
        return null;
    }

    @Override
    public boolean isScript() {
        return false;
    }

    @Override
    public @Nullable PsiClass getScriptClass() {
        return null;
    }

    @Override
    public @NotNull GroovyFileImports getImports() {
        return null;
    }

    @Override
    public void setPackageName(String packageName) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

}
