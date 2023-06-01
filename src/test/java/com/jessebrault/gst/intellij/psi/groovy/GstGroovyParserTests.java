package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.testFramework.ParsingTestCase;
import com.jessebrault.gst.intellij.parser.GstParserDefinition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class GstGroovyParserTests extends ParsingTestCase {

    public GstGroovyParserTests() {
        super("", "gst", new GstParserDefinition());
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/gstParser";
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }

    @Test
    public void simple() {
        this.doTest(true);
    }

    @Test
    public void dollarReference() {
        this.doTest(true);
    }

    @Test
    public void dollarReferenceFollowedByTextDot() {
        this.doTest(true);
    }

    @Test
    public void importBlock() {
        this.doTest(true);
    }

}
