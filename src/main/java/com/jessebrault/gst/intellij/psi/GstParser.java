package com.jessebrault.gst.intellij.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.jessebrault.gst.intellij.lexer.GstTokenType;
import org.jetbrains.annotations.NotNull;

public class GstParser implements PsiParser {

    @Override
    public @NotNull ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        final var fileMarker = builder.mark();

        IElementType tokenType;
        while ((tokenType = builder.getTokenType()) != null) {
            if (tokenType.equals(GstTokenType.BLOCK_SCRIPTLET_OPEN)) {
                final var scriptletMarker = builder.mark();
                builder.advanceLexer(); // BODY
                builder.advanceLexer(); // CLOSE
                scriptletMarker.done(GstElements.BLOCK_SCRIPTLET);
                builder.advanceLexer();
            } else {
                builder.advanceLexer();
            }
        }

        fileMarker.done(GstElements.GST_FILE);

        return builder.getTreeBuilt();
    }

}
