package com.jessebrault.gst.intellij.parser;

import com.intellij.lang.PsiBuilder;
import com.jessebrault.gst.tokenizer.TokenProvider;
import com.jessebrault.gst.tokenizer.TokenType;
import org.jetbrains.annotations.Nullable;

import static com.jessebrault.gst.intellij.lexer.GstTokenTypeUtil.mapIElementType;

final class PsiBuilderTokenProvider implements TokenProvider {

    private final PsiBuilder builder;

    public PsiBuilderTokenProvider(PsiBuilder builder) {
        this.builder = builder;
    }

    @Override
    public @Nullable TokenType getCurrentType() {
        final var currentType = this.builder.getTokenType();
        return currentType != null ? mapIElementType(currentType) : null;
    }

    @Override
    public int getCurrentStart() {
        return this.builder.getCurrentOffset();
    }

    @Override
    public int getCurrentEnd() {
        final var tokenText = this.builder.getTokenText();
        if (tokenText == null) {
            return this.builder.getCurrentOffset();
        } else {
            return this.builder.getCurrentOffset() + tokenText.length();
        }
    }

    @Override
    public void advance() {
        this.builder.advanceLexer();
    }

}
