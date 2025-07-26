package csc3a.ImageProcessing;
import org.opencv.core.*;
import java.io.File;
import java.io.IOException;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class TemplateMatcher {

    static {
        // Load the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Finds the location of a template inside a full image.
     *
     * @param fullImagePath Path to the full image
     * @param templatePath  Path to the template image
     * @return A Point array: [0] = top-left, [1] = bottom-right of matched region
     * @throws IOException 
     */
    public static Point[] findTemplateMatch(String fullImagePath, String templatePath)  {   	
    	
        // Load images
        Mat fullImage = Imgcodecs.imread(fullImagePath);
        Mat template = Imgcodecs.imread(templatePath);

        if (fullImage.empty() || template.empty()) {
            throw new IllegalArgumentException("Could not load one or both images.");
        }

        // Match template
        Mat result = new Mat();
        Imgproc.matchTemplate(fullImage, template, result, Imgproc.TM_CCOEFF_NORMED);

        // Find best match
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        Point topLeft = mmr.maxLoc;
        Point bottomRight = new Point(topLeft.x + template.cols(), topLeft.y + template.rows());

        return new Point[]{topLeft, bottomRight};
    }
    
   
       

}




