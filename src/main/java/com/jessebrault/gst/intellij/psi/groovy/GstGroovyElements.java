package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.OuterLanguageElementType;
import com.jessebrault.gst.intellij.GstLanguage;

public final class GstGroovyElements {

    public static final IFileElementType GST_GROOVY_FILE = new GstGroovyFileElement("gstGroovyFile");
    public static final IElementType GST_GROOVY_OUTER = new OuterLanguageElementType("gstGroovyOuter", GstLanguage.INSTANCE);

}
