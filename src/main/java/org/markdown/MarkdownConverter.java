package org.markdown;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;

public class MarkdownConverter {

    private static final Logger logger = Logger.getLogger(MarkdownConverter.class.getName());

    public static void main(String[] args) {
        selectMenuOption();
    }

    public static void displayMenu() {
        String menu = "\n" +
                "-----------------------------------------------------------------------------------\n" +
                "Markdown to HTML Converter\n" +
                "-----------------------------------------------------------------------------------\n" +
                "1. Select option 1 to convert Markdown to HTML\n" +
                "2. Exit\n" +
                "-----------------------------------------------------------------------------------\n" +
                "\nSelect an option: ";

        logger.info(menu);
    }

    public static void selectMenuOption() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            displayMenu();
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    logger.info("Enter the path of the Markdown file to convert: ");
                    sc.nextLine();
                    String filePath = sc.nextLine();
                    logger.info("Converted HTML");
                    if (filePath.isEmpty() || filePath.isBlank()) {
                        filePath = "./input1.md";
                        logger.info("The specified file path is blank, using the default file : " + filePath);
                    }
                    handleFileInput(filePath);
                    break;
                case 2:
                    logger.info("Exiting the program...");
                    System.exit(0);
                    break;
                default:
                    logger.info(input + " is not a valid option. Please select an option from the above menu.\n");
                    break;
            }
        }
    }

    public static void handleFileInput(String fileName) {
        String htmlText;
        try {
            Scanner scanner = new Scanner(new File(fileName));
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            String markdownInput = stringBuilder.toString();
            htmlText = convertToHtml(markdownInput);
            logger.info(htmlText);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "ERROR: The system cannot find the specified file path: " + fileName + "\nPlease try again with the correct path",  e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "ERROR: Unexpected error occurred", e);
        }
    }

    public static String convertToHtml(String markdown) {
        markdown = convertHeadings(markdown);
        markdown = convertLinks(markdown);
        markdown = convertParagraphs(markdown);
        return markdown;
    }

    private static String convertHeadings(String markdown) {
        Pattern pattern = Pattern.compile("^(#{1,6})\\s+(.+)$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(markdown);
        StringBuilder html = new StringBuilder();
        while (matcher.find()) {
            int level = matcher.group(1).length();
            String heading = matcher.group(2);
            String replacement = "<h" + level + ">" + heading + "</h" + level + ">";
            matcher.appendReplacement(html, replacement);
        }
        matcher.appendTail(html);
        return html.toString();
    }

    private static String convertParagraphs(String markdown) {
        String[] paragraphs = markdown.split("\\n{2,}");

        StringBuilder html = new StringBuilder();
        for (String paragraph : paragraphs) {
            if (!paragraph.isEmpty()) {
                if (paragraph.matches("^(<h\\d|<a)\\s*[^>]*>.*")) {
                    html.append(paragraph).append("\n\n");
                } else {
                    html.append("<p>").append(paragraph.trim()).append("</p>\n\n");
                }
            }
        }

        return html.toString();
    }

    private static String convertLinks(String markdown) {
        return markdown.replaceAll("\\[(.*?)]\\((.*?)\\)", "<a href=\"$2\">$1</a>");
    }

}

