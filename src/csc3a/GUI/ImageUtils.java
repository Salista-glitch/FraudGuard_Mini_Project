package csc3a.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class ImageUtils {
	private static int cornerRadius = 25;

    /**
     * @param image the image we need to add to the panel
     * @param arcWidth width of arc
     * @param arcHeight height of arc
     * @return The rounded panel with an Image inside
     */
    public static JPanel createRoundedImagePanel(BufferedImage image, int arcWidth, int arcHeight) {
        return new JPanel() {
        	@Override
        	public void setBackground(Color bg) {
        		// TODO Auto-generated method stub
        		super.setBackground(bg);
        	}
        	
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Shape clip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
                g2.setClip(clip); // Apply rounded clipping

                if (image != null) {
                    g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }

                g2.dispose();
            }

            @Override
            public boolean isOpaque() {
                return false; // allows background to show through rounded corners
            }
        };
    }
    
    
    /**
     * @return A rounded Panel
     */
    public static JPanel createRoundedPanel() {
        return new JPanel() {
        	
            @Override
            protected void paintComponent(Graphics g) {
                // Convert to Graphics2D for better control
                Graphics2D g2 = (Graphics2D) g.create();

                // Enable anti-aliasing for smooth edges
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Set background color and draw rounded rectangle
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

                // Dispose the graphics object
                g2.dispose();

                // Paint children/components
                super.paintComponent(g);
            }

            @Override
            public boolean isOpaque() {
                return false; // allows background to show through rounded corners
            }
        };
    }

}
