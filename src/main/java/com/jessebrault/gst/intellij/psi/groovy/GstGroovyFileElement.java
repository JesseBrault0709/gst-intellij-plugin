package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilderFactory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IFileElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.GroovyLanguage;

public class GstGroovyFileElement extends IFileElementType {

    public GstGroovyFileElement(String debugName) {
        super(debugName, GroovyLanguage.INSTANCE);
    }

    @Override
    protected ASTNode doParseContents(@NotNull ASTNode chameleon, @NotNull PsiElement psi) {
        final var lexer = new GstGroovyLexer();
        final var builder = PsiBuilderFactory.getInstance().createBuilder(psi.getProject(), lexer, chameleon);
        return new GstGroovyParser().parse(this, builder).getFirstChildNode();
    }

}
