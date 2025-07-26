package csc3a.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CircularSimilarityProgress extends JPanel {
    private int percentage = 0;
    private int target = 5;

    /**
     * @param target The percentage where the it needs to stop
     */
    public CircularSimilarityProgress(int target) {
    	this.target = target;
        Timer timer = new Timer(50, null);
        timer.addActionListener((ActionEvent e) -> {
            if (percentage < target) {
                percentage++;
                repaint();
            } else {
                timer.stop();
            }
        });
        timer.start();
    }

    /**
     * @param value The color at which the colour must change
     * @return The colour at a specific percentage
     */
    private Color getColorForPercentage(int value) {
        // Interpolates between red -> yellow -> green
        if (value <= 50) {
            // Red to Orange
            int green = (int) (165 * (value / 50.0));
            return new Color(255, green, 0);
        } else {
            // Orange to Green
        	int red = (int) (255 * ((100 - value) / 50.0));
            int green = 165 + (int)((90.0 * (value - 50)) / 50.0); 
            return new Color(red, Math.min(green, 255), 0);
        }
    }    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = Math.min(getWidth(), getHeight()) - 40;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        // Anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background circle
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(15));
        g2.drawOval(centerX - size / 2, centerY - size / 2, size, size);

        // Progress arc
        g2.setColor(getColorForPercentage(percentage));
        g2.setStroke(new BasicStroke(15));
        g2.drawArc(centerX - size / 2, centerY - size / 2, size, size, 90, - (int) (360 * (percentage / 100.0)));

        // Percentage text
        g2.setFont(new Font("Arial", Font.BOLD, 32));
        String text = percentage + "%";
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g2.setColor(Color.BLACK);
        g2.drawString(text, centerX - textWidth / 2, centerY + textHeight / 4);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);  // Or any appropriate size
    }

}