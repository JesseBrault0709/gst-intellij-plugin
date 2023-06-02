package com.jessebrault.gst.intellij;

import com.intellij.lang.Language;
import com.intellij.psi.templateLanguages.TemplateLanguage;

public final class EGstLanguage extends Language implements TemplateLanguage {

    public static final EGstLanguage INSTANCE = new EGstLanguage();

    public EGstLanguage() {
        super("egst");
    }

}
