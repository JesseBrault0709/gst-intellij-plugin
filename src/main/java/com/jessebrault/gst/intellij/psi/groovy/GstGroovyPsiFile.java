package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.Key;
import com.intellij.psi.*;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubTree;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.util.CachedValue;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import com.jessebrault.gst.intellij.parser.groovy.GstGroovyElements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.GroovyFileType;
import org.jetbrains.plugins.groovy.GroovyLanguage;
import org.jetbrains.plugins.groovy.lang.lexer.GroovyTokenTypes;
import org.jetbrains.plugins.groovy.lang.lexer.TokenSets;
import org.jetbrains.plugins.groovy.lang.parser.GroovyElementTypes;
import org.jetbrains.plugins.groovy.lang.parser.GroovyStubElementTypes;
import org.jetbrains.plugins.groovy.lang.psi.GroovyElementVisitor;
import org.jetbrains.plugins.groovy.lang.psi.GroovyFile;
import org.jetbrains.plugins.groovy.lang.psi.GroovyPsiElement;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrStatement;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrVariable;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.GrVariableDeclaration;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.typedef.GrTypeDefinition;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.typedef.members.GrMethod;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.GrTopStatement;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.imports.GrImportStatement;
import org.jetbrains.plugins.groovy.lang.psi.api.toplevel.packaging.GrPackageDefinition;
import org.jetbrains.plugins.groovy.lang.psi.controlFlow.Instruction;
import org.jetbrains.plugins.groovy.lang.psi.controlFlow.impl.ControlFlowBuilder;
import org.jetbrains.plugins.groovy.lang.psi.controlFlow.impl.GroovyControlFlow;
import org.jetbrains.plugins.groovy.lang.psi.impl.PsiImplUtil;
import org.jetbrains.plugins.groovy.lang.psi.impl.synthetic.GroovyScriptClass;
import org.jetbrains.plugins.groovy.lang.psi.stubs.GrPackageDefinitionStub;
import org.jetbrains.plugins.groovy.lang.resolve.imports.GroovyFileImports;

import java.util.ArrayList;
import java.util.List;

public class GstGroovyPsiFile extends PsiFileBase implements GroovyFile {

    private static final Key<CachedValue<GrVariableDeclaration[]>> scriptBodyDeclarationsKey =
            Key.create("groovy.script.declarations.body");
    private static final Key<CachedValue<GrVariableDeclaration[]>> scriptDeclarationsKey =
            Key.create("groovy.script.declarations.all");

    // TODO: do not use the impl
    private GroovyScriptClass scriptClass;

    // TODO: do not use the impl
    private GroovyControlFlow controlFlow;

    public GstGroovyPsiFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, GroovyLanguage.INSTANCE);
    }

    @Override
    public @Nullable IFileElementType getFileElementType() {
        return GstGroovyElements.GST_GROOVY_FILE;
    }

    @Override
    public GrImportStatement[] getImportStatements() {
        return new GrImportStatement[0];
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
    public GrTypeDefinition @NotNull [] getTypeDefinitions() {
        final StubElement<?> stub = this.getGreenStub();
        if (stub != null) {
            return stub.getChildrenByType(TokenSets.TYPE_DEFINITIONS, GrTypeDefinition.ARRAY_FACTORY);
        } else {
            return this.calcTreeElement()
                    .getChildrenAsPsiElements(TokenSets.TYPE_DEFINITIONS, GrTypeDefinition.ARRAY_FACTORY);
        }
    }

    @Override
    public GrMethod @NotNull [] getMethods() {
        final StubElement<?> stub = this.getGreenStub();
        if (stub != null) {
            return stub.getChildrenByType(GroovyStubElementTypes.METHOD, GrMethod.ARRAY_FACTORY);
        } else {
            return this.calcTreeElement()
                    .getChildrenAsPsiElements(GroovyStubElementTypes.METHOD, GrMethod.ARRAY_FACTORY);
        }
    }

    @Override
    public GrTopStatement @NotNull [] getTopStatements() {
        return this.findChildrenByClass(GrTopStatement.class);
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
        // TODO: cache
        // TODO: look at imports.kt and GroovyImportCollector.kt
        // TODO: don't use GstGroovyFileImports
        return new GstGroovyFileImports(this);
    }

    @Override
    public PsiClass @NotNull [] getClasses() {
        final PsiClass[] typeDefinitions = this.getTypeDefinitions();
        return !this.isScript()
                ? typeDefinitions
                : ArrayUtil.append(typeDefinitions, this.getScriptClass());
    }

    @Override
    public boolean importClass(@NotNull PsiClass aClass) {
        return this.addImportForClass(aClass) != null;
    }

    @Override
    public @NotNull FileType getFileType() {
        // TODO: investigate this
        return GroovyFileType.GROOVY_FILE_TYPE;
    }

    @Override
    public Instruction[] getControlFlow() {
        if (this.controlFlow == null) {
            // TODO: do not use impl
            this.controlFlow = ControlFlowBuilder.buildControlFlow(this);
        }
        return this.controlFlow.getFlow();
    }

    @Override
    public boolean isTopControlFlowOwner() {
        return false; // TODO: why?
    }

    @Override
    public @NotNull GrStatement addStatementBefore(@NotNull GrStatement statement, @Nullable GrStatement anchor)
            throws IncorrectOperationException {
        final var result = this.addBefore(statement, anchor);
        if (anchor != null) {
            this.getNode().addLeaf(GroovyTokenTypes.mNLS, "\n", anchor.getNode());
        } else {
            this.getNode().addLeaf(GroovyTokenTypes.mNLS, "\n", result.getNode());
        }
        return (GrStatement) result;
    }

    @Override
    public void removeElements(PsiElement[] elements) throws IncorrectOperationException {
        for (final var element : elements) {
            if (element.isValid()) { // TODO: investigate a better way; read isValid() doc
                if (element.getParent() != this) {
                    throw new IncorrectOperationException("element.getParent != this");
                } else {
                    this.deleteChildRange(element, element);
                }
            }
        }
    }

    @Override
    public GrStatement @NotNull [] getStatements() {
        return this.findChildrenByClass(GrStatement.class);
    }

    @Override
    public void removeVariable(GrVariable variable) {
        PsiImplUtil.removeVariable(variable); // TODO: do not use impl
    }

    @Override
    public GrVariableDeclaration addVariableDeclarationBefore(GrVariableDeclaration declaration, GrStatement anchor)
            throws IncorrectOperationException {
        final GrStatement result = this.addStatementBefore(declaration, anchor);
        if (result instanceof GrVariableDeclaration grVariableDeclaration) {
            return grVariableDeclaration;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void accept(@NotNull GroovyElementVisitor visitor) {
        visitor.visitFile(this);
    }

    @Override
    public void acceptChildren(@NotNull GroovyElementVisitor visitor) {
        PsiElement child = this.getFirstChild();
        while (child != null) {
            if (child instanceof GroovyPsiElement groovyPsiElement) {
                groovyPsiElement.accept(visitor);
            }
            child = child.getNextSibling();
        }
    }

}
