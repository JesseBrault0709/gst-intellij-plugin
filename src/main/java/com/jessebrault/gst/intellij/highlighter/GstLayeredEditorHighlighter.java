package com.jessebrault.gst.intellij.highlighter;

import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.util.LayerDescriptor;
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter;
import com.jessebrault.gst.intellij.lexer.GstTokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.highlighter.GroovySyntaxHighlighter;

public final class GstLayeredEditorHighlighter extends LayeredLexerEditorHighlighter {

    public GstLayeredEditorHighlighter(@NotNull EditorColorsScheme scheme) {
        super(new GstSyntaxHighlighter(), scheme);
        final var groovyHighlighter = new GroovySyntaxHighlighter();
        final var descriptor = new LayerDescriptor(groovyHighlighter, "\n"); // not sure why \n is tokenSeparator
        this.registerLayer(GstTokenType.SCRIPTLET_BODY, descriptor);
    }

}
