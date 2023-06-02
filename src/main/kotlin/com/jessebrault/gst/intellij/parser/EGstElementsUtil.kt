@file:JvmName("EGstElementsUtil")

package com.jessebrault.gst.intellij.parser

import com.intellij.psi.tree.IElementType
import com.jessebrault.gst.ast.TreeNodeType

fun mapTreeNodeTypeEGst(treeNodeType: TreeNodeType): IElementType = when (treeNodeType) {
    TreeNodeType.G_STRING -> EGstElements.E_GST_FILE
    TreeNodeType.DOLLAR_REFERENCE -> EGstElements.DOLLAR_REFERENCE
    TreeNodeType.BLOCK_SCRIPTLET -> EGstElements.BLOCK_SCRIPTLET
    TreeNodeType.EXPRESSION_SCRIPTLET -> EGstElements.EXPRESSION_SCRIPTLET
    TreeNodeType.IMPORT_BLOCK -> EGstElements.IMPORT_BLOCK
    TreeNodeType.DOLLAR_SCRIPTLET -> EGstElements.DOLLAR_SCRIPTLET
}