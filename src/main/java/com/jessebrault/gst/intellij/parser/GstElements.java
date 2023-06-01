package com.jessebrault.gst.intellij.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.jessebrault.gst.intellij.GstLanguage;

public final class GstElements {

    public static final IFileElementType GST_FILE = new IFileElementType("gstFile", GstLanguage.INSTANCE);
    public static final IElementType DOLLAR_REFERENCE = new IElementType("dollarReference", GstLanguage.INSTANCE);
    public static final IElementType BLOCK_SCRIPTLET = new IElementType("blockScriptlet", GstLanguage.INSTANCE);
    public static final IElementType EXPRESSION_SCRIPTLET = new IElementType("expressionScriptlet", GstLanguage.INSTANCE);
    public static final IElementType IMPORT_BLOCK = new IElementType("importBlock", GstLanguage.INSTANCE);
    public static final IElementType DOLLAR_SCRIPTLET = new IElementType("dollarScriptlet", GstLanguage.INSTANCE);

}
