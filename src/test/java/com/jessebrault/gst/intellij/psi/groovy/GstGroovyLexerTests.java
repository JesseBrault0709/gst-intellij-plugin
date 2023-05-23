package com.jessebrault.gst.intellij.psi.groovy;

import com.intellij.lexer.Lexer;
import com.intellij.testFramework.LexerTestCase;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GstGroovyLexerTests extends LexerTestCase {

    @Override
    protected Lexer createLexer() {
        return new GstGroovyLexer();
    }

    @Override
    protected String getDirPath() {
        return "src/test/testData/gstGroovyLexer";
    }

    @Override
    protected @NotNull String getPathToTestDataFile(String extension) {
        return this.getDirPath() + "/" + this.getTestName(true) + this.getExpectedFileExtension();
    }

    @Test
    public void simpleBlockScriptlet() {
        this.doTest("<% greet() %>");
    }

    @Test
    public void simpleExpressionScriptlet() {
        this.doTest("<%= getGreeting() %>");
    }

}
