package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.jessebrault.gst.intellij.lexer.GstTokenType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class GstGroovyParser implements PsiParser {

    private static final Collection<IElementType> gstElements = List.of(
            GstTokenType.TEXT,
            GstTokenType.DOLLAR_REFERENCE,
            GstTokenType.BLOCK_SCRIPTLET_OPEN,
            GstTokenType.EXPRESSION_SCRIPTLET_OPEN,
            GstTokenType.SCRIPTLET_BODY,
            GstTokenType.SCRIPTLET_CLOSE,
            GstTokenType.DOLLAR_SCRIPTLET_OPEN,
            GstTokenType.DOLLAR_SCRIPTLET_BODY,
            GstTokenType.DOLLAR_SCRIPTLET_CLOSE
    );

    @Override
    public @NotNull ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        final var fileMarker = builder.mark();

        while (builder.getTokenType() != null) {
            final var actual = builder.getTokenType();
            if (gstElements.contains(actual)) {
                final var outerMarker = builder.mark();
                builder.advanceLexer();
                outerMarker.done(GstGroovyElements.GST_GROOVY_OUTER);
            }
        }
        fileMarker.done(root);
        return builder.getTreeBuilt();
    }

}
