package com.jessebrault.gst.intellij;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class EGstFile extends LanguageFileType {

    public static final EGstFile INSTANCE = new EGstFile();

    public EGstFile() {
        super(EGstLanguage.INSTANCE);
    }

    @Override
    public @NonNls @NotNull String getName() {
        return "egst";
    }

    @Override
    public @NlsContexts.Label @NotNull String getDescription() {
        return "Extended Groovy string templates";
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return "egst";
    }

    @Override
    public Icon getIcon() {
        return AllIcons.FileTypes.Text;
    }

}
