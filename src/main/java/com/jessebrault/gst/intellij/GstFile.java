package com.jessebrault.gst.intellij;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public class GstFile extends LanguageFileType {

    public static final GstFile INSTANCE = new GstFile();

    public GstFile() {
        super(GstLanguage.INSTANCE);
    }

    @Override
    public @NonNls @NotNull String getName() {
        return "Groovy String Template";
    }

    @Override
    public @NlsContexts.Label @NotNull String getDescription() {
        return "Groovy string template";
    }

    @Override
    public @NlsSafe @NotNull String getDefaultExtension() {
        return "gst";
    }

    @Override
    public Icon getIcon() {
        return AllIcons.FileTypes.Text;
    }

}
