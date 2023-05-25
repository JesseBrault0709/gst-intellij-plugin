package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiType;
import com.intellij.util.IncorrectOperationException;
import com.jessebrault.gst.intellij.parser.groovy.GstGroovyElements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.imports.GrImportStatement;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.packaging.GrPackageDefinition;
import org.jetbrains.plugins.groovy.lang.psi.impl.GroovyFileImpl;

public class GstGroovyPsiFile extends GroovyFileImpl {

    public GstGroovyPsiFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider);
        this.setContentElementType(GstGroovyElements.GST_GROOVY_FILE); // TODO: investigate what this is
    }

    @Override
    public GrImportStatement[] getImportStatements() {
        return GrImportStatement.EMPTY_ARRAY;
    }

    @Override
    public @Nullable GrPackageDefinition getPackageDefinition() {
        // Nothing, because it's a script
        return null;
    }

    @Override
    public void setPackageName(String packageName) {
        // NO-OP, since it's a script
    }

    @Override
    public @Nullable GrPackageDefinition setPackage(GrPackageDefinition newPackage) {
        // NO-OP, since it's a script
        return null;
    }

    @Override
    public @Nullable PsiType getInferredScriptReturnType() {
        // TODO
        return null;
    }

    @Override
    public @NotNull String getPackageName() {
        return ""; // since it is a script
    }

    @Override
    public @Nullable GrImportStatement addImportForClass(@NotNull PsiClass aClass) throws IncorrectOperationException {
        throw new IncorrectOperationException("Cannot import into gst file.");
    }

    @Override
    public void removeImport(@NotNull GrImportStatement importStatement) throws IncorrectOperationException {
        throw new IncorrectOperationException("Cannot remove an import from a gst file.");
    }

    @Override
    public @NotNull GrImportStatement addImport(@NotNull GrImportStatement statement) throws IncorrectOperationException {
        throw new IncorrectOperationException("Cannot import into gst file.");
    }

    @Override
    public boolean isScript() {
        return true;
    }

    @Override
    public String toString() {
        return "gstGroovyPsiFile";
    }

}
