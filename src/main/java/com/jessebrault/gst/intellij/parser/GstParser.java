package com.jessebrault.gst.intellij.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.jessebrault.gst.parser.StandardGstParser;
import org.jetbrains.annotations.NotNull;

public final class GstParser implements PsiParser {

    @Override
    public @NotNull ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        final var tokenProvider = new PsiBuilderTokenProvider(builder);
        final var acc = new PsiAccumulator(builder);
        final var parser = new StandardGstParser();
        parser.parse(tokenProvider, acc);
        return builder.getTreeBuilt();
    }

}
