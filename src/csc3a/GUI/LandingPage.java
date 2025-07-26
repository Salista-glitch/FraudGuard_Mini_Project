package csc3a.GUI;
import javax.swing.*;
import javax.swing.border.Border;

import csc3a.GraphClasses.Graph;

import static java.awt.Image.SCALE_SMOOTH;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.*;
import javax.imageio.ImageIO;

/**
 * This is the class that will show when we run our program
 */
public class LandingPage extends JPanel {
    private Image CentreImage;


    public LandingPage(JFrame frame) {
        setLayout(new BorderLayout());
        //////////////////////////////////////Header Panel////////////////////////////////////////////
        JPanel header = new JPanel(new GridLayout(1,2));
        
        //load image icon
    	ImageIcon icon = new ImageIcon("data/Sheild.png");
    	
    	//create label with text and image
    	JLabel heading = new JLabel("FraudGuard", icon, SwingConstants.RIGHT);
    	heading.setFont(new Font("Roboto", Font.BOLD, 35));
    	heading.setForeground(new Color(26, 97, 97));
    	    	
    	//place text beside image
    	heading.setHorizontalTextPosition(SwingConstants.RIGHT);
    	heading.setHorizontalAlignment(SwingConstants.LEFT);
        
    	//add heading to the header
    	header.add(heading);
        
        //A panel with prompts the user can click on
        JPanel MorePrompts = new JPanel();
        MorePrompts.setLayout(new BoxLayout(MorePrompts, BoxLayout.X_AXIS));		//makes the element sit next to each other
        MorePrompts.setBorder(BorderFactory.createEmptyBorder(10, 300, 10, 20));	//add padding to make the components stay on the right side of panel
        
    
        //change the size of the header to make it thinner
        header.setPreferredSize(new Dimension(0,50));
  
        //add the layer to the landing page 
        add(header,BorderLayout.NORTH);
        
        ///////////////////////////// //Centre Panel//////////////////////////////////////
       
        JPanel Center = new JPanel(new GridLayout(1,2));
        loadImage("data/LandingPhoto.jpg");	//load image
        
        // Create image background
        JPanel Image2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (CentreImage != null) {
                    g.drawImage(CentreImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        
        Image2.setLayout(new BorderLayout());
        Center.add(Image2); //add image to panel
        
        // Gray panel (now white, with text + button)
        JPanel whitePanel = createColoredPanel(Color.WHITE);
        whitePanel.setLayout(new BoxLayout(whitePanel, BoxLayout.Y_AXIS));  // vertical layout

        // Create text labels
        JLabel label = createLabel("ID VERIFICATION", false , 36, new Color(54, 69, 79));
        JLabel label2 = createLabel("SYSTEM", false , 36, new Color(54, 69, 79));
        JLabel label3 = createLabel("Upload, Analyze, Verify", true , 18, Color.BLACK);
        
        //align the labels to the centre of the panel
        label.setAlignmentX(Component.CENTER_ALIGNMENT); 
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create button
        JButton button = new JButton("Start Verification");
        
        button.setPreferredSize(new Dimension(150,30));
        button.setBackground(new Color(54, 69, 79)); 		// Background color
        button.setForeground(Color.WHITE); 					// Text color
       
        
        // Remove default border styling
        button.setBorderPainted(false); 
        button.setFocusPainted(false); 				// Remove focus highlight
        button.setContentAreaFilled(true); 			// Keep background visible
        
        //button functionality
        button.addActionListener(new ActionListener() {
      		@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
      			
      			//create a new frame and display
      			JFrame nextFrame = new JFrame("Verification Page");
                VerificationPage panel = new VerificationPage(frame, nextFrame);
                nextFrame.add(panel);
                nextFrame.setSize(1500, 700);
                nextFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                nextFrame.setVisible(true);
			}
        });
        
        button.setAlignmentX(Component.CENTER_ALIGNMENT);  
       
        // Add some spacing
        whitePanel.add(Box.createVerticalStrut(70));  // top space
        
        //add labels
        whitePanel.add(label);
        whitePanel.add(label2);
        whitePanel.add(label3);
        whitePanel.add(Box.createVerticalStrut(15));  // space between label and button
        
        //add button
        whitePanel.add(button);

        // Add this to the center panel
        Center.add(whitePanel);

        add(Center,BorderLayout.CENTER);
        
        //////////////////////////////////////Footer Panel//////////////////////////////////////////////
        JPanel Footer = new JPanel(new GridLayout(1,3));
        Footer.setPreferredSize(new Dimension(0,130));
        
        //Create the feature panels
        JPanel fDetec = createFeaturePanel(
        	    "Forgery Detection",
        	    "data/fdetect2.png",
        	    "Identifies signs of tampering or alterations",
        	    "Analyzes irregularities in content & layout"
        	);
        
        JPanel similarityScore = createFeaturePanel(
        	    "Similarity Score",
        	    "data/SimDetection.png",
        	    "Checks how ID matches trusted references",
        	    "Generate score to assess ID authenticity"
        	);
        
        JPanel structureVerification = createFeaturePanel(
        	    "Structure Verification",
        	    "data/structure.png",
        	    "Evaluate layout, fields, and visual structure",
        	    "Determine if ID follows valid formatting"
        	);
          
        //add panels to the footer
        Footer.add(fDetec);
        Footer.add(similarityScore);
        Footer.add(structureVerification);
          
        add(Footer,BorderLayout.SOUTH);
        ///////////////////////////////////////////////////////////////////////////
    }
    
    /**
     * Loads an image from path
     * @param path The path to the image
     */
    private void loadImage(String path) {
    	try {
    		//read the image from the path	
    		CentreImage = ImageIO.read(new File(path)); 
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load image from: " + path);
        }
    }
    
    
    /**
     * Makes a JLabel with a backgroud colour
     * @param backgroundColor The color to make the panel
     */
    private JPanel createColoredPanel(Color backgroundColor) {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(backgroundColor);  // set the passed-in color
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    /**
     * Makes a JLabel
     * @param string The caption of the label
     * @param plain If true, make the text plain, if not, bold
     * @param size The size to make the label
     * @param labelColour color to make the label
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
     * Makes a JLabel with bullets.
     * @param string The caption of the label
     * @param size The size to make the label
     * @param labelColour The color to make the label
     */
    private JLabel createBulletedLabel(String string, int size, Color labelColour) {
        // Create a label with a bullet point
        JLabel Label = new JLabel("•" + string); 
        
        //set the font
        Label.setFont(new Font("Georgia", Font.PLAIN, size));
        
        //set the colour
        Label.setForeground(labelColour);
        Label.setAlignmentX(CENTER_ALIGNMENT);
              
        return Label;
    }
 
    /**
     * Makes a JPanel feature panel.
     * @param Title The title on the heading label
     * @param iconPath The path of where the icon can be found
     * @param f1 The first Feature
     * @param f1 The second feature
     */
    private JPanel createFeaturePanel(String Title, String iconPath, String f1, String f2) {
    	JPanel panel = new JPanel(); 						//create a new panel
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	
    	//create bulleted features
    	JLabel feature1 = createBulletedLabel(f1, 14, Color.BLACK);
    	JLabel feature2 = createBulletedLabel(f2, 14, Color.BLACK);
    	
    	//load image icon
    	ImageIcon icon = new ImageIcon(iconPath);
    	
    	//create label with text and image
    	JLabel heading = new JLabel(Title, icon, SwingConstants.CENTER);
    	heading.setFont(new Font("Roboto", Font.BOLD, 18));
    	    	
    	//place text beside image
    	heading.setHorizontalTextPosition(SwingConstants.RIGHT);
    	
    	//alaign heading accordingly
    	heading.setAlignmentY(CENTER_ALIGNMENT);
    	heading.setAlignmentX(CENTER_ALIGNMENT);
        
        // Create panel to hold feature labels
        JPanel labelPanel = new JPanel();
        labelPanel.add(feature1);
        labelPanel.add(feature2);
     
    	//add labels to panel
        panel.add(heading);
    	panel.add(labelPanel);
    	
    	//return the panel
		return panel;
    	
    }
    
    
    /**
     * Makes a JLabel clickable.
     * @param label The label to make clickable
     * @param onClick Runnable action to execute on click
     */
    public static void makeClickable(JLabel label, Runnable onClick) {
        // Change cursor on hover
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClick.run(); // Execute the action
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            	//set cursor to hand cursor when on the label
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));		
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	//set cursor to default when no longer on the label
                label.setCursor(Cursor.getDefaultCursor());			
            }
        });
}
}
