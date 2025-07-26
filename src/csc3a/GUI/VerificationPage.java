package csc3a.GUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

import org.opencv.core.Point;

import csc3a.Algorithims.*;
import csc3a.GraphClasses.Graph;
import csc3a.Visualisation.GraphVisualizer;
import csc3a.ImageProcessing.*;
import csc3a.Verification.*;

public class VerificationPage extends JPanel {
	BufferedImage img;
	File Template = new File("data/Template.png");	//path to find the template image
	
	public VerificationPage(JFrame prevframe, JFrame currentFrame) {
		//add padding
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setLayout(new BorderLayout());
	    
		//Open the file chooser
		File selectedImage = FileChooser.chooseFile(prevframe);
		
		//Display error message if the user did not select any file
		if(selectedImage == null) {
			JOptionPane.showMessageDialog(null,"No file selected!", "Warning",JOptionPane.WARNING_MESSAGE);
		}
		//create a panel for the verification page
		JPanel vPage = new JPanel(new GridLayout(1,3, 20, 20));
		
		///////////////////////////////////////Right Panel//////////////////////////////////////////////
		
		JPanel idPanel = ImageUtils.createRoundedPanel();
		idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.Y_AXIS));
		idPanel.add(Box.createVerticalStrut(60));
		
		JLabel idHeading = createLabel("ID Analysis of: " + selectedImage.getName(), false, 33, new Color(54, 69, 79));
		idHeading.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//add heading to panel
		idPanel.add(idHeading);
		idPanel.add(Box.createVerticalStrut(10));
		
		//read image from directory
		try {
			//read the image
			img = ImageIO.read(selectedImage);
			
			//convert it to grayscale
			BufferedImage grayImage = ImageSegmentation.convertToGrayscale(img);
			
			//extract visula elements from the template an dget the path
			String TemplatePath = ImageSegmentation.analyseTemplate(Template);
			
			 Map<String, String> templateFiles = Map.of(
		                "Flag", "Flag.png",
		                "Header", "Header.png",
		                "Details", "Details.png",
		                "Photo", "Photo.png",
		                "ID Icon", "ID Icon.png"
		            );

		    //Get the graph of the Template and the ID
		    Graph<String, Integer> IDGraph = GraphVisualizer.analyzeImageAndBuildGraph(selectedImage.getAbsolutePath(), templateFiles);
		    Graph<String, Integer> TemplateGraph = GraphVisualizer.analyzeImageAndBuildGraph(TemplatePath, templateFiles);
		    
		    //Visualize the graphs
		    BufferedImage IDimg = GraphVisualizer.visualizeGraph(IDGraph);
		    BufferedImage Templateimg = GraphVisualizer.visualizeGraph(TemplateGraph);

			//draw the images
			JPanel grayImagePanel = ImageUtils.createRoundedImagePanel(grayImage, 20, 20);
			JPanel TemplatePanel = ImageUtils.createRoundedImagePanel(Templateimg, 20, 20);
			JPanel IDPanel = ImageUtils.createRoundedImagePanel(IDimg, 20, 20);
			
			idPanel.add(grayImagePanel);
			idPanel.add(Box.createVerticalStrut(10));
			idPanel.setBackground(Color.WHITE);
			
			//create a button that analyses a new ID
			JButton newId = createButton("  ANALYSE A NEW ID  ");
			
			newId.addActionListener(new ActionListener() {
	      		@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
	  
	      			//create a new frame and display
	      			JFrame nextFrame = new JFrame("Verification Page");
	                VerificationPage panel = new VerificationPage(currentFrame, nextFrame);
	                nextFrame.add(panel);
	                nextFrame.setSize(1500, 700);
	                nextFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                
	                // make the current frame invisible
	      			currentFrame.setVisible(false);
	                
	                nextFrame.setVisible(true);
	                
	             
				}
	        });
			
			//add the image to the panel
			idPanel.add(newId);
			idPanel.add(Box.createVerticalStrut(50));
			idPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
			
			//create a button that goes back
			JButton back = createButton("BACK TO HOME PAGE");
			
			back.addActionListener(new ActionListener() {
	      		@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
	                
	                // make the current frame invisible
	      			currentFrame.setVisible(false);
				}
	        });
			
			//add button to the panel
			idPanel.add(back);
			idPanel.add(Box.createVerticalStrut(10));
			
			JButton close = createButton("  CLOSE APPLICATION  ");
	 		   
	 		  close.addActionListener(new ActionListener() {
		      		@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
		                
		                // exit the application
		      			System.exit(0);;
					}
		        });
	 		 
	 		 //add the button to the panel and apply some padding
	 		idPanel.add(close);
	 		idPanel.add(Box.createVerticalStrut(3));
			//idPanel.add(Box.createVerticalStrut(100));
			
			////////////////////////////////////////////Graphs/////////////////////////////////////////////////////////
			JPanel middle = new JPanel(new GridLayout(2,1, 20, 20));
			
			//Create panel for the template graph
			JPanel tPanel = ImageUtils.createRoundedPanel();
			tPanel.setLayout(new BoxLayout(tPanel, BoxLayout.Y_AXIS));
			tPanel.setBackground(Color.WHITE);
			
			//Add label to Panel
		    JLabel lbltGraph = createLabel("Template Graph", false, 29, new Color(26, 97, 97));
		    lbltGraph.setOpaque(true);
		    lbltGraph.setBackground(Color.WHITE);
		    lbltGraph.setAlignmentX(Component.CENTER_ALIGNMENT);
		    tPanel.add(lbltGraph);
		    tPanel.add(Box.createVerticalStrut(5));
		    tPanel.add(TemplatePanel);
			
			//Create panel for the ID graph
			JPanel graphPanel = ImageUtils.createRoundedPanel();
			graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
			graphPanel.setBackground(Color.WHITE);

		    //Add label to Panel
		    JLabel lblidGraph = createLabel("ID Graph", false, 29, new Color(26, 97, 97));
		    lblidGraph.setOpaque(true);
		    lblidGraph.setBackground(Color.WHITE);
		    lblidGraph.setAlignmentX(Component.CENTER_ALIGNMENT);
		    graphPanel.add(lblidGraph);
		    graphPanel.add(IDPanel);
		    
		    //add both template graph and the ID graph
		    middle.add(tPanel);
			middle.add(graphPanel);
			
		    ////////////////////////////////////Structure Verification/////////////////////////////////////////////////////////
			
		    //Create the algorithm classes 
        	Dijkstra<String, Integer> dijkstra = new Dijkstra<>();
        	KNN<String, Integer> knn = new KNN<>();
        	
        	//Create the object which will use the object to calculate the similarity score
        	GraphSimilarityAnalyzer<String, Integer> analyzer = new GraphSimilarityAnalyzer<>(dijkstra, knn);

        	//Get the score
        	double similarity = analyzer.compare(TemplateGraph, IDGraph);
        	
        	JPanel right = new JPanel(new GridLayout(2,1, 20, 20));
        	
        	JPanel SimilarityPanel = ImageUtils.createRoundedPanel();
        	SimilarityPanel.setBackground(Color.WHITE);
        	SimilarityPanel.setLayout(new BoxLayout(SimilarityPanel, BoxLayout.Y_AXIS));
        	
        	//Add label to Panel
		    JLabel lblSimScore = createLabel("Similarity Score", false, 29, new Color(26, 97, 97));
		    lblSimScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		    SimilarityPanel.add(lblSimScore);
		    SimilarityPanel.add(Box.createVerticalStrut(10));
        	
		    //Create the circular progress bar
        	CircularSimilarityProgress similarityScore = new CircularSimilarityProgress((int) Math.round(similarity * 100));
        	similarityScore.setBackground(Color.WHITE);
        	
        	//Create the status label to classify the score
        	JLabel lblStatus = classifyScore(similarity);
        	lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        	
        	SimilarityPanel.add(similarityScore);
        	SimilarityPanel.add(Box.createVerticalStrut(10));
 		    SimilarityPanel.add(lblStatus);
 		    
 		    right.add(SimilarityPanel);		
 		   
 		   ///////////////////////////////////////////Text Analysis///////////////////////////////////////////////////////
 		   
 		   //get the top coordinate point of the Text in the selected image
 		   Point[] details = TemplateMatcher.findTemplateMatch(selectedImage.getPath(), "Details.png");		   
 		   
 		   //extract the text segment from the image
 		   BufferedImage Text = grayImage.getSubimage((int) details[0].x, (int) details[0].y, 210, 315);
 		   
 		   //save image
 		   ImageIO.write(Text, "png", new File("IDText.png"));
 		   
 		   //Set Tessdata Path
 		   String tessdataPath = Paths.get("Tess4J-3.4.8-src", "Tess4J").toAbsolutePath().toString();
 		   OCRFeatureExtractor extractor = new OCRFeatureExtractor(tessdataPath);
 		   
 		   //get the analysis
 		   String result1 = OCRGraphTest.getOCRTestResults(extractor, "IDText.png");
 		   JTextPane TextAnalysis = createTextPane(result1);
 		   
 		  JPanel structurePanel = ImageUtils.createRoundedPanel();
 		  structurePanel.setBackground(Color.WHITE);
 		  structurePanel.setLayout(new BoxLayout(structurePanel, BoxLayout.Y_AXIS));
 		  
 		  JLabel lblStructure = createLabel("Structure Verification", false, 29, new Color(26, 97, 97));
 		  lblStructure.setAlignmentX(Component.CENTER_ALIGNMENT);
 		   
 		  
 		  //add to panel
 		  structurePanel.add(lblStructure);
 		  structurePanel.add(TextAnalysis);
 		  right.add(structurePanel);	
 		   
 		 
 		  //create a button that closes the application
 		/*  JButton close = createButton("  CLOSE APPLICATION  ");
 		   
 		  close.addActionListener(new ActionListener() {
	      		@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
	                
	                // exit the application
	      			System.exit(0);;
				}
	        });
 		 
 		 //add the button to the panel and apply some padding
 		 structurePanel.add(close);
 		 structurePanel.add(Box.createVerticalStrut(3));*/
 		   
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
 		//add panels into the main panel
 		vPage.add(idPanel);
		vPage.add(middle);
		vPage.add(right);
			
		add(vPage);	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param string The string of the label
	 * @param plain True if text is plain, false if otherwise
	 * @param size The size to make the text
	 * @param labelColour The color to make the label
	 * @return The label
	 */
	private JLabel createLabel(String string, boolean plain, int size, Color labelColour) {
        // Create a label with a bullet point
        JLabel Label = new JLabel(string); 
        
        //set the colour
        Label.setForeground(labelColour);
       
        //Determine the style
        if(plain) {
        	Label.setFont(new Font("Georgia", Font.PLAIN, size));
        }else Label.setFont(new Font("Roboto", Font.BOLD, size));
        
        return Label;
    }
	
	/**
	 * @param score The score which need to be classified
	 * @return Label with the classificatin
	 */
	public JLabel classifyScore(double score) {
        if (score >= 0.70) return createLabel("Status: Authentic",false, 28, Color.GREEN);
        else if (score >= 0.50) return createLabel("Status: Needs Review",false, 28, Color.ORANGE);
        else return createLabel("Status: Fraudulent",false, 28, Color.RED);
    }
	
	/**
	 * @param label The text to add inside the button
	 * @return The button with the label inside
	 */
	public JButton createButton(String label) {
		JButton button = new JButton(label);
		
		 button.setPreferredSize(new Dimension(150,30));
	     button.setBackground(new Color(54, 69, 79)); 		// Background color
	     button.setForeground(Color.WHITE); 					// Text color
	       
	     // Remove default border styling
	     button.setBorderPainted(false); 
	     button.setFocusPainted(false); 				// Remove focus highlight
	     button.setContentAreaFilled(true);
	     
	     button.setAlignmentX(CENTER_ALIGNMENT);
		return button;
	}
	
	/**
	 * @param text The text to add to the Pane
	 * @return The JText Pane with the text
	 */
	public static JTextPane createTextPane(String text) {
	    JTextPane textPane = new JTextPane();
	    textPane.setText(text);
	    textPane.setEditable(false);
	    textPane.setFocusable(false);
	    textPane.setOpaque(false);
	    textPane.setFont( new Font("Georgia", Font.PLAIN, 15) );

	    // Align text to center
	    StyledDocument doc = textPane.getStyledDocument();
	    SimpleAttributeSet center = new SimpleAttributeSet();
	    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
	    doc.setParagraphAttributes(0, doc.getLength(), center, false);

	    return textPane;
	}
}
