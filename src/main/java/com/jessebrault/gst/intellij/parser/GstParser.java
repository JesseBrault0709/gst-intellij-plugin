package com.jessebrault.gst.intellij.parser;

import com.jessebrault.gst.parser.StandardGstParser;

public final class GstParser extends AbstractGstParser {

    public GstParser() {
        super(new StandardGstParser(), GstElementsUtil::mapTreeNodeTypeGst);
    }

}
