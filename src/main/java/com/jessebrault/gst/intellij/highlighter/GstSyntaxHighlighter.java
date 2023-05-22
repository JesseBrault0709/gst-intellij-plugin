package com.jessebrault.gst.intellij.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.XmlHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.jessebrault.gst.intellij.lexer.GstLexer;
import com.jessebrault.gst.intellij.lexer.GstTokenType;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public final class GstSyntaxHighlighter extends SyntaxHighlighterBase {

    public static final TextAttributesKey TEXT = createTextAttributesKey(
            "text", HighlighterColors.TEXT
    );
    public static final TextAttributesKey BLOCK_SCRIPTLET_OPEN = createTextAttributesKey(
            "blockScriptletOpen", XmlHighlighterColors.XML_TAG
    );
    public static final TextAttributesKey EXPRESSION_SCRIPTLET_OPEN = createTextAttributesKey(
            "expressionScriptletOpen", XmlHighlighterColors.XML_TAG
    );
    public static final TextAttributesKey SCRIPTLET_CLOSE = createTextAttributesKey(
            "scriptletClose", XmlHighlighterColors.XML_TAG
    );

    private static final TextAttributesKey[] textKeys = new TextAttributesKey[] {
            TEXT
    };
    private static final TextAttributesKey[] blockScriptletOpenKeys = new TextAttributesKey[] {
            BLOCK_SCRIPTLET_OPEN
    };
    private static final TextAttributesKey[] expressionScriptletOpenKeys = new TextAttributesKey[] {
            EXPRESSION_SCRIPTLET_OPEN
    };
    private static final TextAttributesKey[] scriptletCloseKeys = new TextAttributesKey[] {
            SCRIPTLET_CLOSE
    };

    @Override
    public @NotNull Lexer getHighlightingLexer() {
        return new GstLexer();
    }

    @Override
    public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(GstTokenType.TEXT)) {
            return textKeys;
        } else if (tokenType.equals(GstTokenType.BLOCK_SCRIPTLET_OPEN)) {
            return blockScriptletOpenKeys;
        } else if (tokenType.equals(GstTokenType.EXPRESSION_SCRIPTLET_OPEN)) {
            return expressionScriptletOpenKeys;
        } else if (tokenType.equals(GstTokenType.SCRIPTLET_CLOSE)) {
            return scriptletCloseKeys;
        } else {
            return new TextAttributesKey[0];
        }
    }

}
