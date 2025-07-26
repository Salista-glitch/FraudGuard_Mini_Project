package csc3a.Verification;

import csc3a.DataStructures.HashedMap;
import csc3a.DataStructures.Map;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.Word;
import net.sourceforge.tess4j.ITessAPI.TessPageIteratorLevel;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;

/**
 * OCRFeatureExtractor class uses Tess4J (Tesseract Java wrapper) to extract full lines of text and their coordinates from an image.
 */
public class OCRFeatureExtractor {

    private ITesseract tesseract;

    /**
     * Constructor that sets up Tesseract with the given tessdata path 
     * @param tessdataPath Absolute path to the 'tessdata' directory
     */
    public OCRFeatureExtractor(String tessdataPath) {
        tesseract = new Tesseract();
        tesseract.setDatapath(tessdataPath);
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(3); 
    }

    /**
     * Extracts text lines and their top-left (x, y) positions from the provided image file.
     *
     * @param imageFile The image file to analyze
     * @return A map of extracted text to its (x, y) position
     * @throws TesseractException if OCR fails
     * @throws IOException if file loading fails
     */
    public Map<String, int[]> extractTextPositions(File imageFile) throws TesseractException, IOException {
        Map<String, int[]> positions = new HashedMap<>();
        BufferedImage image = ImageIO.read(imageFile);

     // Extract text lines with their bounding boxes
        List<Word> lines = tesseract.getWords(image, TessPageIteratorLevel.RIL_TEXTLINE);
        for (Word line : lines) {
            String text = line.getText().trim();
            Rectangle box = line.getBoundingBox();

            if (!text.isEmpty()) {
                positions.put(text, new int[]{box.x, box.y});
            }
        }

        return positions;
    }
}
