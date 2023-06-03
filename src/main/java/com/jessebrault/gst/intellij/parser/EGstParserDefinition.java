package com.jessebrault.gst.intellij.parser;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.jessebrault.gst.intellij.lexer.GstLexer;
import com.jessebrault.gst.intellij.psi.EGstPsiFile;
import org.jetbrains.annotations.NotNull;

public final class EGstParserDefinition implements ParserDefinition {

    @Override
    public @NotNull Lexer createLexer(Project project) {
        return new GstLexer();
    }

    @Override
    public @NotNull PsiParser createParser(Project project) {
        return new EGstParser();
    }

    @Override
    public @NotNull IFileElementType getFileNodeType() {
        return EGstElements.E_GST_FILE;
    }

    @Override
    public @NotNull TokenSet getCommentTokens() {
        return TokenSet.EMPTY;
    }

    @Override
    public @NotNull TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @Override
    public @NotNull PsiElement createElement(ASTNode node) {
        return new ASTWrapperPsiElement(node);
    }

    @Override
    public @NotNull PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new EGstPsiFile(viewProvider);
    }

}
