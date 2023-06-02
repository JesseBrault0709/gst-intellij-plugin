package com.jessebrault.gst.intellij.lexer.groovy;

import com.intellij.lexer.Lexer;
import com.intellij.testFramework.LexerTestCase;
import com.jessebrault.gst.intellij.lexer.groovy.GstGroovyLexer;
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

    @Test
    public void empty() {
        this.doTest("");
    }

    @Test
    public void dollarReference() {
        this.doTest("$test");
    }

    @Test
    public void complexDollarReference() {
        this.doTest("$a.b.c");
    }

    @Test
    public void dollarReferenceFollowedByTextDot() {
        this.doTest("$a.b.c.");
    }

}
