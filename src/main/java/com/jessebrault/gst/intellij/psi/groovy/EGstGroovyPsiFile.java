package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiClass;
import com.intellij.psi.util.PsiUtil;
import com.intellij.util.IncorrectOperationException;
import com.jessebrault.gst.intellij.psi.EGstPsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.imports.GrImportStatement;

import java.util.Arrays;

public class EGstGroovyPsiFile extends GstGroovyPsiFile {

    public EGstGroovyPsiFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider);
    }

    @Override
    public GrImportStatement[] getImportStatements() {
        final var egstPsiFile = (EGstPsiFile) Arrays.stream(this.getPsiRoots())
                .filter(psiFile -> psiFile instanceof EGstPsiFile)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("EGstGroovyPsiFile is not alongside an EGstPsiFile."));
        return super.getImportStatements();
    }

    @Override
    public @Nullable GrImportStatement addImportForClass(@NotNull PsiClass aClass) throws IncorrectOperationException {
        return super.addImportForClass(aClass);
    }

    @Override
    public void removeImport(@NotNull GrImportStatement importStatement) throws IncorrectOperationException {
        super.removeImport(importStatement);
    }

    @Override
    public @NotNull GrImportStatement addImport(@NotNull GrImportStatement statement) throws IncorrectOperationException {
        return super.addImport(statement);
    }

    @Override
    public String toString() {
        return "eGstGroovyPsiFile";
    }

}
