package com.jessebrault.gst.intellij;

import com.intellij.lang.Language;
import com.intellij.psi.templateLanguages.TemplateLanguage;

public final class GstLanguage extends Language implements TemplateLanguage {

    public static final GstLanguage INSTANCE = new GstLanguage();

    public GstLanguage() {
        super("Groovy String Template");
    }

}
