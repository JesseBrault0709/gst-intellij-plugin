package com.jessebrault.gst.intellij.lexer;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import com.jessebrault.gst.tokenizer.FsmBasedTokenizer;
import com.jessebrault.gst.tokenizer.Tokenizer;
import com.jessebrault.gst.tokenizer.TokenizerState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GstLexer extends LexerBase {

    private final Tokenizer tokenizer = new FsmBasedTokenizer();

    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
        this.tokenizer.start(
                buffer,
                startOffset,
                endOffset,
                TokenizerState.values()[initialState]
        );
    }

    @Override
    public int getState() {
        return this.tokenizer.getCurrentTokenState().ordinal();
    }

    @Override
    public @Nullable IElementType getTokenType() {
        final var token = this.tokenizer.getCurrentToken();
        if (token == null) {
            return null;
        } else {
            return switch (token.getType()) {
                case TEXT -> GstTokenType.TEXT;
                case DOLLAR_REFERENCE_DOLLAR -> GstTokenType.DOLLAR_REFERENCE_DOLLAR;
                case DOLLAR_REFERENCE_BODY -> GstTokenType.DOLLAR_REFERENCE_BODY;
                case BLOCK_SCRIPTLET_OPEN -> GstTokenType.BLOCK_SCRIPTLET_OPEN;
                case EXPRESSION_SCRIPTLET_OPEN -> GstTokenType.EXPRESSION_SCRIPTLET_OPEN;
                case SCRIPTLET_BODY -> GstTokenType.SCRIPTLET_BODY;
                case SCRIPTLET_CLOSE -> GstTokenType.SCRIPTLET_CLOSE;
                case IMPORT_BLOCK_OPEN -> GstTokenType.IMPORT_BLOCK_OPEN;
                case IMPORT_BLOCK_BODY -> GstTokenType.IMPORT_BLOCK_BODY;
                case IMPORT_BLOCK_CLOSE -> GstTokenType.IMPORT_BLOCK_CLOSE;
                case DOLLAR_SCRIPTLET_OPEN -> GstTokenType.DOLLAR_SCRIPTLET_OPEN;
                case DOLLAR_SCRIPTLET_BODY -> GstTokenType.DOLLAR_SCRIPTLET_BODY;
                case DOLLAR_SCRIPTLET_CLOSE -> GstTokenType.DOLLAR_SCRIPTLET_CLOSE;
            };
        }
    }

    @Override
    public int getTokenStart() {
        //noinspection DataFlowIssue
        return this.tokenizer.getCurrentToken().getStartIndex();
    }

    @Override
    public int getTokenEnd() {
        //noinspection DataFlowIssue
        return this.tokenizer.getCurrentToken().getEndIndex();
    }

    @Override
    public void advance() {
        this.tokenizer.advance();
    }

    @Override
    public @NotNull CharSequence getBufferSequence() {
        return this.tokenizer.getCurrentInput();
    }

    @Override
    public int getBufferEnd() {
        return this.tokenizer.getCurrentEndIndex();
    }

}
