package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.testFramework.ParsingTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class GstGroovyParserTests extends ParsingTestCase {

    public GstGroovyParserTests() {
        super("", "gst");
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/gstGroovyParser";
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }

    @Test
    public void simple() {
        this.doTest(true);
    }

}
