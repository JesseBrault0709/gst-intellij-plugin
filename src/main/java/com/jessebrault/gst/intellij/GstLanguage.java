package com.jessebrault.gst.intellij;

import com.intellij.lang.Language;

public final class GstLanguage extends Language {

    public static final GstLanguage INSTANCE = new GstLanguage();

    public GstLanguage() {
        super("Groovy String Template");
    }

}
