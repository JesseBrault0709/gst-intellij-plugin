package com.jessebrault.gst.intellij.parser;

import com.jessebrault.gst.parser.ExtendedGstParser;

public final class EGstParser extends AbstractGstParser {

    public EGstParser() {
        super(new ExtendedGstParser(), EGstElementsUtil::mapTreeNodeTypeEGst);
    }

}
