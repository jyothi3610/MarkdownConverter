import org.junit.jupiter.api.Test;
import org.markdown.MarkdownConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MarkdownConverterTest {

    @Test
    public void testEmptyString() {
        String markdown = "";
        String expectedHtml = "";
        String result = MarkdownConverter.convertToHtml(markdown).trim();
        assertEquals(expectedHtml, result);
    }

    @Test
    public void testHeading() {
        String markdown = "# Heading";
        String expectedHtml = "<h1>Heading</h1>";
        String result = MarkdownConverter.convertToHtml(markdown).trim();
        assertEquals(expectedHtml, result);
    }

    @Test
    public void testParagraph() {
        String markdown = "This is a paragraph";
        String expectedHtml = "<p>This is a paragraph</p>";
        String result = MarkdownConverter.convertToHtml(markdown).trim();
        assertEquals(expectedHtml, result);
    }

    @Test
    public void testMultilineParagraph() {
        String markdown = "How are you?\n" +
                "What's going on?";
        String expectedHtml = "<p>How are you?\n" +
                "What's going on?</p>";
        String result = MarkdownConverter.convertToHtml(markdown).trim();
        assertEquals(expectedHtml, result);
    }

    @Test
    public void testAnchorLink() {
        String markdown = "[Link](https://example.com)";
        String expectedHtml = "<a href=\"https://example.com\">Link</a>";
        String result = MarkdownConverter.convertToHtml(markdown).trim();
        assertEquals(expectedHtml, result);
    }

    @Test
    public void testNestedInput() {
        String markdown = "# Heading [with a [nested link]](http://example.com)";
        String expectedHtml = "<h1>Heading <a href=\"http://example.com\">with a [nested link]</a></h1>";
        String result = MarkdownConverter.convertToHtml(markdown).trim();
        assertEquals(expectedHtml, result);
    }

    @Test
    public void testComplexInput() {
        String markdown = "This is a paragraph with [a link](https://example.com) and text. Another [link](https://example.org) in the same paragraph.";
        String expectedHtml = "<p>This is a paragraph with <a href=\"https://example.com\">a link</a> and text. Another <a href=\"https://example.org\">link</a> in the same paragraph.</p>";
        String result = MarkdownConverter.convertToHtml(markdown).trim();
        assertEquals(expectedHtml, result);
    }

    @Test
    public void testComplexNestedInput() {
        String markdown = "# Heading 1 This is a paragraph with [a link](https://example.com) and text. Another [link](https://example.org) in the same paragraph. ## Heading 2";
        String expectedHtml = "<h1>Heading 1 This is a paragraph with <a href=\"https://example.com\">a link</a> and text. Another <a href=\"https://example.org\">link</a> in the same paragraph. ## Heading 2</h1>";
        String result = MarkdownConverter.convertToHtml(markdown).trim();
        assertEquals(expectedHtml, result);
    }

    @Test
    public void testMultilineInput() {
        String markdown = "# Header one\n" +
                "\n" +
                "Hello there\n" +
                "\n" +
                "How are you?\n" +
                "What's going on?\n" +
                "\n" +
                "## Another Header\n" +
                "\n" +
                "This is a paragraph [with an inline link](http://google.com). Neat, eh?\n" +
                "\n" +
                "## This is a header [with a link](http://yahoo.com)";
        String expectedHtml = "<h1>Header one</h1>\n" +
                "\n" +
                "<p>Hello there</p>\n" +
                "\n" +
                "<p>How are you?\n" +
                "What's going on?</p>\n" +
                "\n" +
                "<h2>Another Header</h2>\n" +
                "\n" +
                "<p>This is a paragraph <a href=\"http://google.com\">with an inline link</a>. Neat, eh?</p>\n" +
                "\n" +
                "<h2>This is a header <a href=\"http://yahoo.com\">with a link</a></h2>";
        String result = MarkdownConverter.convertToHtml(markdown).trim();
        assertEquals(expectedHtml, result);
    }

    @Test
    public void testDisplayMenu() {
        assertDoesNotThrow(MarkdownConverter::displayMenu);
    }

    @Test
    public void testHandleFileInput() {
        assertDoesNotThrow(() -> MarkdownConverter.handleFileInput("./input1.md"));
    }
}
