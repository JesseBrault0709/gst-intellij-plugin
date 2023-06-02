package com.jessebrault.gst.intellij.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.jessebrault.gst.ast.TreeNodeType;
import com.jessebrault.gst.parser.Parser;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public final class GstParser implements PsiParser {

    private final Parser parser;
    private final Function<TreeNodeType, IElementType> treeNodeTypeMapper;

    public GstParser(Parser parser, Function<TreeNodeType, IElementType> treeNodeTypeMapper) {
        this.parser = parser;
        this.treeNodeTypeMapper = treeNodeTypeMapper;
    }

    @Override
    public @NotNull ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        final var tokenProvider = new PsiBuilderTokenProvider(builder);
        final var acc = new PsiAccumulator(builder, this.treeNodeTypeMapper);
        this.parser.parse(tokenProvider, acc);
        return builder.getTreeBuilt();
    }

}
