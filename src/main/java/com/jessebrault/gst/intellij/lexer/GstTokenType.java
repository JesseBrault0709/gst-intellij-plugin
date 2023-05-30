package com.jessebrault.gst.intellij.lexer;

import com.intellij.psi.tree.IElementType;
import com.jessebrault.gst.intellij.GstLanguage;

public final class GstTokenType extends IElementType {

    public static final GstTokenType TEXT = new GstTokenType("text");
    public static final GstTokenType DOLLAR_REFERENCE_DOLLAR = new GstTokenType("dollarReferenceDollar");
    public static final GstTokenType DOLLAR_REFERENCE_BODY = new GstTokenType("dollarReferenceBody");
    public static final GstTokenType BLOCK_SCRIPTLET_OPEN = new GstTokenType("blockScriptletOpen");
    public static final GstTokenType EXPRESSION_SCRIPTLET_OPEN = new GstTokenType("expressionScriptletOpen");
    public static final GstTokenType SCRIPTLET_BODY = new GstTokenType("scriptletBody");
    public static final GstTokenType SCRIPTLET_CLOSE = new GstTokenType("scriptletClose");
    public static final GstTokenType DOLLAR_SCRIPTLET_OPEN = new GstTokenType("dollarScriptletOpen");
    public static final GstTokenType DOLLAR_SCRIPTLET_BODY = new GstTokenType("dollarScriptletBody");
    public static final GstTokenType DOLLAR_SCRIPTLET_CLOSE = new GstTokenType("dollarScriptletClose");

    public GstTokenType(String debugName) {
        super(debugName, GstLanguage.INSTANCE);
    }

}
