package com.jessebrault.gst.intellij.psi;

import com.intellij.lang.ParserDefinition;
import com.intellij.testFramework.ParsingTestCase;
import com.jessebrault.gst.intellij.parser.EGstParserDefinition;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class EGstParserTests extends ParsingTestCase {

    public EGstParserTests() {
        super("", "egst", new EGstParserDefinition());
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/eGstParser";
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }

    @Test
    public void importBlock() {
        this.doTest(true);
    }

}
