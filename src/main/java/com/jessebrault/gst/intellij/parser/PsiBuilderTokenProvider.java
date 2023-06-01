package com.jessebrault.gst.intellij.parser;

import com.intellij.lang.PsiBuilder;
import com.jessebrault.gst.tokenizer.SimpleToken;
import com.jessebrault.gst.tokenizer.Token;
import com.jessebrault.gst.tokenizer.TokenProvider;
import org.jetbrains.annotations.Nullable;

import static com.jessebrault.gst.intellij.parser.PsiBuilderTokenProviderUtil.mapIElementType;

final class PsiBuilderTokenProvider implements TokenProvider {

    private final PsiBuilder builder;

    public PsiBuilderTokenProvider(PsiBuilder builder) {
        this.builder = builder;
    }

    @Override
    public @Nullable Token getCurrent() {
        final var type = this.builder.getTokenType();
        //noinspection DataFlowIssue
        return type != null
                ? new SimpleToken(
                        mapIElementType(type),
                        this.builder.getCurrentOffset(),
                        this.builder.getCurrentOffset() + this.builder.getTokenText().length())
                : null;
    }

    @Override
    public void advance() {
        this.builder.advanceLexer();
    }

}
