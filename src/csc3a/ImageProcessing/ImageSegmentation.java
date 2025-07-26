package csc3a.ImageProcessing;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;



public class ImageSegmentation {
	static int WIDTH = 736;
	static int HEIGHT = 478;
	
	
	/**
	 * @param image The image we need to convert to grayscale
	 * @return The grayscale image
	 */
	public static BufferedImage convertToGrayscale(BufferedImage image) {
		
		int height = image.getHeight();
		int width = image.getWidth();
		
		BufferedImage grayImage = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
		
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				int rgb = image.getRGB(x, y);	//get RGB value of the pixel
				
				//extract red, green and blue components
				int r = (rgb >> 16) & 0xff;
				int g = (rgb >> 8) & 0xff;
				int b = (rgb) & 0xff;
				
				//calculate grayscale value
				int gray = (r+g+b) / 3;
				
				//Compose new grayscale image
				int newPixel = (gray << 16) | (gray <<8) | gray;
				
				//Set pixel in the grayscale image
				grayImage.setRGB(x, y, newPixel);
			}
		}
		//Return the gray Image
		return grayImage;
	}
	
	 /**
	 * @param width Width we must resize to
	 * @param height Height we must resize to
	 * @param originalImage The image we need to resize
	 * @return The resized image
	 */
	public static BufferedImage resizeImage(int width, int height, BufferedImage originalImage) {
	        Image temp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	        
	        //Create a new buffered image with the desired dimensions
	        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
	        
	        //Draw the scaled image onto the new buffered image
	        Graphics2D Graphics2d = resized.createGraphics();
	        Graphics2d.drawImage(temp, 0, 0, null);
	        
	        //Dispose resources
	        Graphics2d.dispose();
	        
	        return resized;
	    }
	 
	 /**
	 * @param grayImage Gray Image of the ID we are wroking with
	 * @param imageFile The file of the ID we are working with
	 * @return The extracted information from the ID
	 */
	public static String extractInformation(BufferedImage grayImage, File imageFile) {
		 try {
			BufferedImage grayResized = resizeImage(WIDTH, HEIGHT, grayImage);
						
			//Get the visual elements from the image
			BufferedImage SAFlag = grayResized.getSubimage(27, 32, 130, 86);
			BufferedImage Heading = grayResized.getSubimage(174, 45, 520, 66);
			BufferedImage Details = grayResized.getSubimage(174, 128, 210, 315);
			BufferedImage Photo = grayResized.getSubimage(513, 119, 193, 231);
			BufferedImage IDicon = grayResized.getSubimage(27, 383, 76, 63);
			BufferedImage Signature = grayResized.getSubimage(546, 402, 135, 38);
 		
			//Save them in the folder
			ImageIO.write(SAFlag, "png", new File("Flag.png"));
			ImageIO.write(Details, "png", new File("Details.png"));
			ImageIO.write(Photo, "png", new File("Photo.png"));
			ImageIO.write(Heading, "png", new File("Header.png"));
			ImageIO.write(IDicon, "png", new File("ID Icon.png"));
			ImageIO.write(Signature, "png", new File("Signature.png"));
 		
			// Create Tesseract instance
			Tesseract tesseract = new Tesseract();

			// Set the path to the tessdata folder (inside your Tesseract install);
			tesseract.setDatapath("Tess4J-3.4.8-src\\Tess4J\\tessdata");
			// Optionally set the language (default is "eng")
			tesseract.setLanguage("eng");

			//String text = tesseract.doOCR(new File("Text.png").getAbsoluteFile());
			String text = tesseract.doOCR(new File("Text.png"));
			
			// Return the result
			return text;
		 }catch (IOException e) {
	            e.printStackTrace();
	        } catch (TesseractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		 
	 }
	 
	 
	 /**
	 * @param Template The template file
	 * @return The path the gray Image of the template
	 */
	public static String analyseTemplate(File Template) {
	 try {
		 	BufferedImage Temp = ImageIO.read(Template);
			BufferedImage grayResized = resizeImage(WIDTH, HEIGHT, Temp);
			grayResized = convertToGrayscale(grayResized);
			
			//Get the visual elements from the image
			BufferedImage SAFlag = grayResized.getSubimage(27, 32, 130, 86);
			BufferedImage Heading = grayResized.getSubimage(174, 45, 520, 66);
			BufferedImage Details = grayResized.getSubimage(174, 128, 210, 315);
			BufferedImage Photo = grayResized.getSubimage(513, 119, 193, 231);
			BufferedImage IDicon = grayResized.getSubimage(27, 383, 76, 63);
			BufferedImage Signature = grayResized.getSubimage(546, 402, 135, 38);
		
			//Save them in the folder
			ImageIO.write(SAFlag, "png", new File("Flag.png"));
			ImageIO.write(Details, "png", new File("Details.png"));
			ImageIO.write(Photo, "png", new File("Photo.png"));
			ImageIO.write(Heading, "png", new File("Header.png"));
			ImageIO.write(IDicon, "png", new File("ID Icon.png"));
			ImageIO.write(Signature, "png", new File("Signature.png"));
			ImageIO.write(grayResized, "png", new File("GrayTemplate.png"));
			
			return "GrayTemplate.png";
		
		 }catch (IOException e) {
	            e.printStackTrace();
	        }
	return null; 
	 }
}
