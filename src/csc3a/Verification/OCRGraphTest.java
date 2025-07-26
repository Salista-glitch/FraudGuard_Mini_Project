package csc3a.Verification;
import csc3a.GraphClasses.*;
import csc3a.Algorithims.*;
import csc3a.Verification.DocumentFeedbackValidator;
import csc3a.Verification.GraphBuilder;
import csc3a.Verification.OCRFeatureExtractor;
import csc3a.DataStructures.HashedMap;
import csc3a.DataStructures.Map;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * OCRGraphTest: Tests the full pipeline of extracting text from two images,
 * building graphs from text positions, and applying document validation.
 */
public class OCRGraphTest {

    // Patterns for ID and Date of Birth
    private static final Pattern ID_PATTERN = Pattern.compile("\\b\\d{13}\\b");
    private static final Pattern DOB_PATTERN = Pattern.compile("\\b\\d{2}\\s+(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)\\s+\\d{4}\\b", Pattern.CASE_INSENSITIVE);

    /*public static void main(String[] args) {
        try {
            // Set tessdata path
            String tessdataPath = Paths.get("Tess4J-3.4.8-src", "Tess4J").toAbsolutePath().toString();
            OCRFeatureExtractor extractor = new OCRFeatureExtractor(tessdataPath);

            // --- Test 1: Details.png ---
            System.out.println("\n===== Running Test on Details.png =====");
            String result1 = getOCRTestResults(extractor, "Details.png");
            System.out.println(result1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    
    public static String getOCRTestResults(OCRFeatureExtractor extractor, String imageName) {
        StringBuilder result = new StringBuilder();

        try {
            File imageFile = new File(imageName);
            Map<String, int[]> wordPositions = extractor.extractTextPositions(imageFile);
            Map<Vertex<String, Double>, int[]> vertexPositions = new HashedMap<>();
            Graph<String, Double> graph = GraphBuilder.buildOCRGraph(wordPositions, vertexPositions);

            result.append("\n-- Extracted Identity Number and Date of Birth from ").append(imageName).append(" --\n");
            boolean found = false;
            for (String word : wordPositions.keySet()) {
                Matcher idMatcher = ID_PATTERN.matcher(word);
                Matcher dobMatcher = DOB_PATTERN.matcher(word);

                if (idMatcher.find()) {
                    int[] pos = wordPositions.get(word);
                    result.append(String.format("ID Number: '%s' %n", word, pos[0], pos[1]));
                    found = true;
                } else if (dobMatcher.find()) {
                    int[] pos = wordPositions.get(word);
                    result.append(String.format("Date of Birth: '%s' %n", word, pos[0], pos[1]));
                    found = true;
                }
            }
            if (!found) {
                result.append("No valid ID or DOB found in this image.\n");
            }

            result.append("\n--- Textual Structure Feedback ---\n");
            List<String> feedback = DocumentFeedbackValidator.validate(wordPositions);
            for (String line : feedback) {
                result.append("- ").append(line).append("\n");
            }

        } catch (Exception e) {
            result.append("Failed processing image: ").append(imageName).append("\n");
            result.append(e.toString()).append("\n");
        }

        return result.toString();
    }

    
}
