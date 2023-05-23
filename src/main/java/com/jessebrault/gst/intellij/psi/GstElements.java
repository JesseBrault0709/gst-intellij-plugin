package com.jessebrault.gst.intellij.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.jessebrault.gst.intellij.GstLanguage;

public final class GstElements {

    public static final IFileElementType GST_FILE = new IFileElementType("gstFile", GstLanguage.INSTANCE);
    public static final IElementType BLOCK_SCRIPTLET = new IElementType("blockScriptlet", GstLanguage.INSTANCE);

}
