package com.jessebrault.gst.intellij.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.jessebrault.gst.intellij.EGstLanguage;

public final class EGstElements {

    public static final IFileElementType E_GST_FILE = new IFileElementType("eGstFile", EGstLanguage.INSTANCE);
    public static final IElementType DOLLAR_REFERENCE = new IElementType("dollarReference", EGstLanguage.INSTANCE);
    public static final IElementType BLOCK_SCRIPTLET = new IElementType("blockScriptlet", EGstLanguage.INSTANCE);
    public static final IElementType EXPRESSION_SCRIPTLET = new IElementType("expressionScriptlet", EGstLanguage.INSTANCE);
    public static final IElementType IMPORT_BLOCK = new IElementType("importBlock", EGstLanguage.INSTANCE);
    public static final IElementType DOLLAR_SCRIPTLET = new IElementType("dollarScriptlet", EGstLanguage.INSTANCE);

}
