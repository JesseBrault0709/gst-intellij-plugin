package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import com.jessebrault.gst.intellij.lexer.GstLexer;
import com.jessebrault.gst.intellij.lexer.GstTokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.lang.lexer.GroovyLexer;

public class GstGroovyLexer extends LexerBase {

    private final Lexer gstLexer = new GstLexer();
    private final Lexer groovyLexer = new GroovyLexer();

    private CharSequence buffer;
    private Lexer currentLexer;

    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
        if (this.currentLexer == null) {
            this.currentLexer = this.gstLexer;
        }
        this.buffer = buffer;
        this.currentLexer.start(buffer, startOffset, endOffset, initialState);
    }

    @Override
    public int getState() {
        return this.currentLexer.getState();
    }

    @Override
    public @Nullable IElementType getTokenType() {
        final var currentTokenType = this.currentLexer.getTokenType();
        if (currentTokenType != null && currentTokenType.equals(GstTokenType.SCRIPTLET_BODY)) {
            this.groovyLexer.start(
                    this.buffer,
                    this.gstLexer.getTokenStart(),
                    this.gstLexer.getTokenEnd()
            );
            this.currentLexer = this.groovyLexer;
        } else if (currentTokenType == null) {
            this.currentLexer = this.gstLexer;
            this.currentLexer.advance();
        }
        return this.currentLexer.getTokenType();
    }

    @Override
    public int getTokenStart() {
        return this.currentLexer.getTokenStart();
    }

    @Override
    public int getTokenEnd() {
        return this.currentLexer.getTokenEnd();
    }

    @Override
    public void advance() {
        this.currentLexer.advance();
    }

    @Override
    public @NotNull CharSequence getBufferSequence() {
        return this.currentLexer.getBufferSequence();
    }

    @Override
    public int getBufferEnd() {
        return this.currentLexer.getBufferEnd();
    }

}
