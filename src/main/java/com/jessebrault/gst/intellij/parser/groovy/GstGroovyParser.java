package com.jessebrault.gst.intellij.parser.groovy;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.jessebrault.gst.intellij.lexer.GstTokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.lang.parser.GroovyParser;

import java.util.Collection;
import java.util.List;

public final class GstGroovyParser implements PsiParser {

    private static final class GstAwareGroovyParser extends GroovyParser {

        private static final Collection<IElementType> gstTokens = List.of(
                GstTokenType.TEXT,
                GstTokenType.DOLLAR_REFERENCE_DOLLAR,
                GstTokenType.DOLLAR_REFERENCE_BODY,
                GstTokenType.BLOCK_SCRIPTLET_OPEN,
                GstTokenType.EXPRESSION_SCRIPTLET_OPEN,
                GstTokenType.SCRIPTLET_BODY,
                GstTokenType.SCRIPTLET_CLOSE,
                GstTokenType.DOLLAR_SCRIPTLET_OPEN,
                GstTokenType.DOLLAR_SCRIPTLET_BODY,
                GstTokenType.DOLLAR_SCRIPTLET_CLOSE
        );

        @Override
        protected boolean isExtendedSeparator(@Nullable IElementType tokenType) {
            return tokenType != null && gstTokens.contains(tokenType);
        }

    }

    @Override
    public @NotNull ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        final var groovyParser = new GstAwareGroovyParser();
        groovyParser.parse(root, builder);
        return builder.getTreeBuilt();
    }

}
