package com.jessebrault.gst.intellij.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static com.jessebrault.gst.intellij.lexer.GstTokenType.*;

public class GstParser implements PsiParser {

    private PsiBuilder builder;

    @Override
    public @NotNull ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        this.builder = builder;
        final var rootMarker = builder.mark();
        while (builder.getTokenType() != null) {
            this.textDollarReferenceOrScriptlet();
        }
        rootMarker.done(root);
        return builder.getTreeBuilt();
    }

    private static boolean testTokenType(IElementType actual, IElementType... allPossible) {
        if (actual != null) {
            for (final var possible : allPossible) {
                if (actual == possible) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String getError(IElementType actual, IElementType... expected) {
        final var b = new StringBuilder("Unexpected token ")
                .append(actual);
        if (expected.length == 0) {
            b.append(".");
            return b.toString();
        } else {
            b.append("; expected ");
            final var iterator = Arrays.asList(expected).iterator();
            while (iterator.hasNext()) {
                final var individualExpected = iterator.next();
                b.append(individualExpected);
                if (iterator.hasNext()) {
                    b.append(", ");
                }
            }
            b.append(".");
            return b.toString();
        }
    }

    private void textDollarReferenceOrScriptlet() {
        final var actual = this.builder.getTokenType();
        if (testTokenType(actual, TEXT, DOLLAR_REFERENCE)) {
            this.builder.advanceLexer();
        } else if (testTokenType(actual, BLOCK_SCRIPTLET_OPEN)) {
            this.blockScriptlet();
        } else if (testTokenType(actual, EXPRESSION_SCRIPTLET_OPEN)) {
            this.expressionScriptlet();
        } else if (actual != null) {
            this.builder.error(getError(actual, TEXT, DOLLAR_REFERENCE, BLOCK_SCRIPTLET_OPEN, EXPRESSION_SCRIPTLET_OPEN));
            this.builder.advanceLexer();
            this.textDollarReferenceOrScriptlet();
        }
    }

    private void blockScriptlet() {
        final var t0 = this.builder.getTokenType();
        if (testTokenType(t0, BLOCK_SCRIPTLET_OPEN)) {
            this.blockAndExpressionScriptletBodyAndClose();
        } else {
            this.builder.error(getError(t0, BLOCK_SCRIPTLET_OPEN));
        }
    }

    private void expressionScriptlet() {
        final var t0 = this.builder.getTokenType();
        if (testTokenType(t0, EXPRESSION_SCRIPTLET_OPEN)) {
            this.blockAndExpressionScriptletBodyAndClose();
        } else {
            this.builder.error(getError(t0, BLOCK_SCRIPTLET_OPEN));
        }
    }

    private void blockAndExpressionScriptletBodyAndClose() {
        final var scriptletMarker = this.builder.mark();
        this.builder.advanceLexer();
        final var actualBody = this.builder.getTokenType();
        if (testTokenType(actualBody, SCRIPTLET_BODY)) {
            this.builder.advanceLexer();
            final var actualClose = this.builder.getTokenType();
            if (testTokenType(actualClose, SCRIPTLET_CLOSE)) {
                this.builder.advanceLexer();
                scriptletMarker.done(GstElements.BLOCK_SCRIPTLET);
            } else {
                scriptletMarker.error(getError(actualClose, SCRIPTLET_CLOSE));
            }
        } else {
            scriptletMarker.error(getError(actualBody, SCRIPTLET_BODY));
        }
    }

}
