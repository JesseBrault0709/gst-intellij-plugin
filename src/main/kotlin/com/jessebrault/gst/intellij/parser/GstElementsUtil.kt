@file:JvmName("GstElementsUtil")

package com.jessebrault.gst.intellij.parser

import com.intellij.psi.tree.IElementType
import com.jessebrault.gst.ast.TreeNodeType

fun mapTreeNodeTypeGst(treeNodeType: TreeNodeType): IElementType = when (treeNodeType) {
    TreeNodeType.G_STRING -> GstElements.GST_FILE
    TreeNodeType.DOLLAR_REFERENCE -> GstElements.DOLLAR_REFERENCE
    TreeNodeType.BLOCK_SCRIPTLET -> GstElements.BLOCK_SCRIPTLET
    TreeNodeType.EXPRESSION_SCRIPTLET -> GstElements.EXPRESSION_SCRIPTLET
    TreeNodeType.IMPORT_BLOCK -> GstElements.IMPORT_BLOCK
    TreeNodeType.DOLLAR_SCRIPTLET -> GstElements.DOLLAR_SCRIPTLET
}
