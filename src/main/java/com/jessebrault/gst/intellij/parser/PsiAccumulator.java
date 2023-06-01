package com.jessebrault.gst.intellij.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.jessebrault.gst.ast.TreeNodeType;
import com.jessebrault.gst.parser.ParserAccumulator;
import com.jessebrault.gst.tokenizer.TokenType;
import com.jessebrault.gst.util.Diagnostic;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

final class PsiAccumulator implements ParserAccumulator {

    private static IElementType mapTreeNodeType(TreeNodeType treeNodeType) {
        return switch (treeNodeType) {
            case G_STRING -> GstElements.GST_FILE;
            case DOLLAR_REFERENCE -> GstElements.DOLLAR_REFERENCE;
            case BLOCK_SCRIPTLET -> GstElements.BLOCK_SCRIPTLET;
            case EXPRESSION_SCRIPTLET -> GstElements.EXPRESSION_SCRIPTLET;
            case IMPORT_BLOCK -> GstElements.IMPORT_BLOCK;
            case DOLLAR_SCRIPTLET -> GstElements.DOLLAR_SCRIPTLET;
        };
    }

    private static String combineDiagnostics(Collection<Diagnostic> diagnostics) {
        final var b = new StringBuilder();
        final var iter = diagnostics.iterator();
        while (iter.hasNext()) {
            b.append(iter.next().getMessage());
            if (iter.hasNext()) {
                b.append(" ");
            }
        }
        return b.toString();
    }

    private final PsiBuilder builder;
    private final Deque<PsiBuilder.Marker> markers = new ArrayDeque<>();
    private final Deque<TreeNodeType> treeNodeTypes = new ArrayDeque<>();

    public PsiAccumulator(PsiBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void start(TreeNodeType type) {
        this.markers.push(this.builder.mark());
        this.treeNodeTypes.push(type);
    }

    @Override
    public void leaf(TokenType type, int start, int end, Collection<Diagnostic> diagnostics) {
        diagnostics.forEach(diagnostic -> this.builder.error(diagnostic.getMessage()));
    }

    @Override
    public void done(Collection<Diagnostic> diagnostics) {
        final var marker = this.markers.pop();
        final var treeNodeType = this.treeNodeTypes.pop();
        if (diagnostics.isEmpty()) {
            marker.done(mapTreeNodeType(treeNodeType));
        } else {
            marker.error(combineDiagnostics(diagnostics));
        }
    }

    @Override
    public void leaf(TokenType type, int start, int end) {}

    @Override
    public void done() {
        final var marker = this.markers.pop();
        final var treeNodeType = this.treeNodeTypes.pop();
        marker.done(mapTreeNodeType(treeNodeType));
    }

}