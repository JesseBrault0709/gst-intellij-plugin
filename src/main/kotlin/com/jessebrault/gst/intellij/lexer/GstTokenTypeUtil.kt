@file:JvmName("GstTokenTypeUtil")

package com.jessebrault.gst.intellij.lexer

import com.intellij.psi.tree.IElementType
import com.jessebrault.gst.tokenizer.TokenType
import com.jessebrault.gst.tokenizer.TokenType.*

fun mapIElementType(iElementType: IElementType): TokenType = when (iElementType) {
    GstTokenType.TEXT -> TEXT
    GstTokenType.DOLLAR_REFERENCE_DOLLAR -> DOLLAR_REFERENCE_DOLLAR
    GstTokenType.DOLLAR_REFERENCE_BODY -> DOLLAR_REFERENCE_BODY
    GstTokenType.BLOCK_SCRIPTLET_OPEN -> BLOCK_SCRIPTLET_OPEN
    GstTokenType.EXPRESSION_SCRIPTLET_OPEN -> EXPRESSION_SCRIPTLET_OPEN
    GstTokenType.SCRIPTLET_BODY -> SCRIPTLET_BODY
    GstTokenType.SCRIPTLET_CLOSE -> SCRIPTLET_CLOSE
    GstTokenType.IMPORT_BLOCK_OPEN -> IMPORT_BLOCK_OPEN
    GstTokenType.IMPORT_BLOCK_BODY -> IMPORT_BLOCK_BODY
    GstTokenType.IMPORT_BLOCK_CLOSE -> IMPORT_BLOCK_CLOSE
    GstTokenType.DOLLAR_SCRIPTLET_OPEN -> DOLLAR_SCRIPTLET_OPEN
    GstTokenType.DOLLAR_SCRIPTLET_BODY -> DOLLAR_SCRIPTLET_BODY
    GstTokenType.DOLLAR_SCRIPTLET_CLOSE -> DOLLAR_SCRIPTLET_CLOSE
    else -> throw IllegalArgumentException("Unable to map iElementToken: $iElementType")
}
