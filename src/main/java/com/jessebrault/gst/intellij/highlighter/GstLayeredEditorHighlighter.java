package com.jessebrault.gst.intellij.highlighter;

import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.util.LayerDescriptor;
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter;
import com.jessebrault.gst.intellij.lexer.GstTokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.highlighter.GroovySyntaxHighlighter;

/**
 * Can be extended to add additional layers to the Gst syntax; for example, HTML.
 */
public class GstLayeredEditorHighlighter extends LayeredLexerEditorHighlighter {

    public GstLayeredEditorHighlighter(@NotNull EditorColorsScheme scheme) {
        super(new GstSyntaxHighlighter(), scheme);
        final var groovyHighlighter = new GroovySyntaxHighlighter();
        final var groovyDescriptor = new LayerDescriptor(groovyHighlighter, "");
        this.registerLayer(GstTokenType.DOLLAR_REFERENCE_BODY, groovyDescriptor);
        this.registerLayer(GstTokenType.SCRIPTLET_BODY, groovyDescriptor);
    }

}
