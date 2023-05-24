package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Key;
import com.intellij.psi.*;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubTree;
import com.intellij.psi.util.CachedValue;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.util.IncorrectOperationException;
import com.jessebrault.gst.intellij.parser.groovy.GstGroovyElements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.GroovyLanguage;
import org.jetbrains.plugins.groovy.lang.parser.GroovyElementTypes;
import org.jetbrains.plugins.groovy.lang.parser.GroovyStubElementTypes;
import org.jetbrains.plugins.groovy.lang.psi.GroovyFile;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrVariableDeclaration;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.typedef.GrTypeDefinition;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.typedef.members.GrMethod;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.imports.GrImportStatement;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.packaging.GrPackageDefinition;
import org.jetbrains.plugins.groovy.lang.psi.controlFlow.impl.GroovyControlFlow;
import org.jetbrains.plugins.groovy.lang.psi.impl.GroovyFileBaseImpl;
import org.jetbrains.plugins.groovy.lang.psi.impl.synthetic.GroovyScriptClass;
import org.jetbrains.plugins.groovy.lang.psi.stubs.GrPackageDefinitionStub;
import org.jetbrains.plugins.groovy.lang.resolve.imports.GroovyFileImports;
import org.jetbrains.plugins.groovy.lang.resolve.imports.GroovyImports;

import java.util.ArrayList;
import java.util.List;

public class GstGroovyPsiFile extends GroovyFileBaseImpl implements GroovyFile {

    private static final Key<CachedValue<GrVariableDeclaration[]>> scriptBodyDeclarationsKey =
            Key.create("groovy.script.declarations.body");
    private static final Key<CachedValue<GrVariableDeclaration[]>> scriptDeclarationsKey =
            Key.create("groovy.script.declarations.all");

    // TODO: do not use the impl
    private GroovyScriptClass scriptClass;

    public GstGroovyPsiFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, GroovyLanguage.INSTANCE);
        this.setContentElementType(GstGroovyElements.GST_GROOVY_FILE); // TODO: investigate what this is
    }

    @Override
    public GrImportStatement[] getImportStatements() {
        return GrImportStatement.EMPTY_ARRAY;
    }

    @Override
    public @Nullable GrPackageDefinition getPackageDefinition() {
        final StubElement<?> fileStub = this.getStub();
        if (fileStub != null) {
            for (StubElement<?> stubElement : fileStub.getChildrenStubs()) {
                if (stubElement instanceof GrPackageDefinitionStub grPackageDefinitionStub) {
                    return grPackageDefinitionStub.getPsi();
                }
            }
            return null;
        }

        final ASTNode packageDefinitionAstNode = this.calcTreeElement()
                .findChildByType(GroovyStubElementTypes.PACKAGE_DEFINITION);
        return packageDefinitionAstNode != null
                ? packageDefinitionAstNode.getPsi(GrPackageDefinition.class)
                : null;
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
    public GrVariableDeclaration @NotNull [] getScriptDeclarations(boolean topLevelOnly) {
        final StubTree tree = this.getStubTree();
        if (tree == null) {
            return CachedValuesManager.getManager(this.getProject()).getCachedValue(
                    this,
                    topLevelOnly ? scriptBodyDeclarationsKey : scriptDeclarationsKey,
                    () -> {
                        final List<GrVariableDeclaration> result = new ArrayList<>();
                        this.accept(new PsiRecursiveElementWalkingVisitor() {

                            @Override
                            public void visitElement(@NotNull PsiElement element) {
                                if (
                                        element instanceof GrVariableDeclaration grVariableDeclaration
                                                && grVariableDeclaration.getModifierList()
                                                        .getRawAnnotations().length > 0
                                ) {
                                    result.add(grVariableDeclaration);
                                } else if (
                                        !(element instanceof GrTypeDefinition
                                                || element instanceof GrMethod && topLevelOnly)
                                ) {
                                    // do not go into classes OR methods with topLevelOnly
                                    super.visitElement(element);
                                }
                            }

                        });
                        return CachedValueProvider.Result.create(
                                result.toArray(GrVariableDeclaration[]::new),
                                this
                        );
                    },
                    false
            );
        } else if (topLevelOnly) {
            final StubElement<?> root = tree.getRoot();
            return root.getChildrenByType(
                    GroovyElementTypes.VARIABLE_DECLARATION,
                    GrVariableDeclaration.EMPTY_ARRAY
            );
        } else {
            return tree.getPlainList().stream()
                    .filter(stubElement -> stubElement.getStubType() == GroovyElementTypes.VARIABLE_DECLARATION)
                    .map(stubElement -> (GrVariableDeclaration) stubElement.getPsi())
                    .toArray(GrVariableDeclaration[]::new);
        }
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
    public @Nullable PsiClass getScriptClass() {
        if (this.scriptClass == null) {
            this.scriptClass = new GroovyScriptClass(this); // TODO: do not use the impl
        }
        return this.scriptClass;
    }

    @Override
    public @NotNull GroovyFileImports getImports() {
        // TODO: do not use impl
        return GroovyImports.getFileImports(this);
    }

    @Override
    public boolean isTopControlFlowOwner() {
        // if false, the ControlFlowUtils searches higher up and there is nothing higher
        return true;
    }

    @Override
    public String toString() {
        return "gstGroovyPsiFile";
    }

}
