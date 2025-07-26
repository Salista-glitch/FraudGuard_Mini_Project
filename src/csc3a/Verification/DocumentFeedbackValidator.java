package csc3a.Verification;


import java.util.*;
import java.util.regex.Pattern;

import csc3a.DataStructures.Map;
import csc3a.GraphClasses.Vertex;

public class DocumentFeedbackValidator {

    public static List<String> validate(Map<String, int[]> wordPositions) {
        List<String> feedback = new ArrayList<>();

        // --- Extract all words into list ---
        List<String> words = new ArrayList<>();
        for (String key : wordPositions.keySet()) {
            String cleaned = key.trim().replaceAll("[^A-Za-z0-9\\s]", ""); // clean punctuation
            if (!cleaned.isEmpty()) {
                words.add(cleaned.toUpperCase());
            }
        }

        // --- ID Check (must contain exactly 13 digits) ---
        boolean hasValidID = false;
        for (String word : words) {
            String digitsOnly = word.replaceAll("\\D", "");
            if (digitsOnly.length() == 13) {
                feedback.add("✔ ID number has 13 digits");
                hasValidID = true;
                break;
            }
        }
        if (!hasValidID) {
            feedback.add("✘ Invalid ID number (not 13 digits)");
        }

     // --- Date Format Check ---
        Pattern datePattern = Pattern.compile("^\\d{1,2}\\s+(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)\\s+\\d{4}$", Pattern.CASE_INSENSITIVE);
        boolean hasValidDate = false;

        // Try sliding window of 3 words
        for (int i = 0; i < words.size() - 2; i++) {
            String combined = words.get(i) + " " + words.get(i + 1) + " " + words.get(i + 2);
            if (datePattern.matcher(combined).matches()) {
                feedback.add("✔ Date format is valid");
                hasValidDate = true;
                break;
            }
        }

        //  check full lines
        if (!hasValidDate) {
            for (String word : words) {
                if (datePattern.matcher(word).matches()) {
                    feedback.add("✔ Date format is valid");
                    hasValidDate = true;
                    break;
                }
            }
        }

        if (!hasValidDate) {
            feedback.add("✘ Invalid date format (expected: 30 JAN 1990)");
        }


        // --- Text Alignment Check (Y-axis variation) ---
        List<Integer> yCoords = new ArrayList<>();
        for (int[] pos : wordPositions.values()) {
            yCoords.add(pos[1]);
        }

        Collections.sort(yCoords);
        int maxDiff = 0;
        for (int i = 1; i < yCoords.size(); i++) {
            int diff = Math.abs(yCoords.get(i) - yCoords.get(i - 1));
            if (diff > maxDiff) maxDiff = diff;
        }

        if (maxDiff > 100) {
            feedback.add("✘ Text misaligned (inconsistent Y positions)");
        } else {
            feedback.add("✔ Text alignment looks consistent");
        }

        // --- Final Summary ---
        if (hasValidID && hasValidDate && maxDiff <= 100) {
            feedback.add("✅ Structure Verified");
        } else {
            feedback.add("❌ Structural Issues Detected");
        }

        return feedback;
    }
}
