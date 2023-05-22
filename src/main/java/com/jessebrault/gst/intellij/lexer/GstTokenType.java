package com.jessebrault.gst.intellij.lexer;

import com.intellij.psi.tree.IElementType;
import com.jessebrault.gst.intellij.GstLanguage;
import org.jetbrains.plugins.groovy.GroovyLanguage;

public final class GstTokenType extends IElementType {

    public static final GstTokenType TEXT = new GstTokenType("text");
    public static final IElementType DOLLAR_REFERENCE = new IElementType("GROOVY_CODE", GroovyLanguage.INSTANCE);
    public static final GstTokenType BLOCK_SCRIPTLET_OPEN = new GstTokenType("blockScriptletOpen");
    public static final GstTokenType EXPRESSION_SCRIPTLET_OPEN = new GstTokenType("expressionScriptletOpen");
    public static final IElementType SCRIPTLET_BODY = new IElementType("GROOVY_CODE", GroovyLanguage.INSTANCE);
    public static final GstTokenType SCRIPTLET_CLOSE = new GstTokenType("scriptletClose");
    public static final GstTokenType DOLLAR_SCRIPTLET_OPEN = new GstTokenType("dollarScriptletOpen");
    public static final GstTokenType DOLLAR_SCRIPTLET_BODY = new GstTokenType("dollarScriptletBody");
    public static final GstTokenType DOLLAR_SCRIPTLET_CLOSE = new GstTokenType("dollarScriptletClose");

    public GstTokenType(String debugName) {
        super(debugName, GstLanguage.INSTANCE);
    }

}
