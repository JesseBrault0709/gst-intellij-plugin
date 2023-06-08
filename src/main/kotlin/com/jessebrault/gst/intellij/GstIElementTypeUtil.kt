@file:JvmName("GstIElementTypeUtil")

package com.jessebrault.gst.intellij

import com.intellij.psi.tree.IElementType
import com.jessebrault.gst.intellij.lexer.GstTokenType
import com.jessebrault.gst.intellij.parser.EGstElements
import com.jessebrault.gst.intellij.parser.GstElements

private val allGstElements: Set<IElementType> = setOf(
        GstTokenType.TEXT,
        GstTokenType.DOLLAR_REFERENCE_DOLLAR,
        GstTokenType.DOLLAR_REFERENCE_BODY,
        GstTokenType.BLOCK_SCRIPTLET_OPEN,
        GstTokenType.EXPRESSION_SCRIPTLET_OPEN,
        GstTokenType.SCRIPTLET_BODY,
        GstTokenType.SCRIPTLET_CLOSE,
        GstTokenType.IMPORT_BLOCK_OPEN,
        GstTokenType.IMPORT_BLOCK_BODY,
        GstTokenType.IMPORT_BLOCK_CLOSE,
        GstTokenType.DOLLAR_SCRIPTLET_OPEN,
        GstTokenType.DOLLAR_SCRIPTLET_BODY,
        GstTokenType.DOLLAR_SCRIPTLET_CLOSE,

        GstElements.GST_FILE,
        GstElements.DOLLAR_REFERENCE,
        GstElements.BLOCK_SCRIPTLET,
        GstElements.EXPRESSION_SCRIPTLET,
        GstElements.IMPORT_BLOCK,
        GstElements.DOLLAR_SCRIPTLET,

        EGstElements.E_GST_FILE,
        EGstElements.DOLLAR_REFERENCE,
        EGstElements.BLOCK_SCRIPTLET,
        EGstElements.EXPRESSION_SCRIPTLET,
        EGstElements.IMPORT_BLOCK,
        EGstElements.DOLLAR_SCRIPTLET
)

fun isGstElement(iElementType: IElementType) = iElementType in allGstElements
