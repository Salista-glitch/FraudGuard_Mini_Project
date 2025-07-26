import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import csc3a.GUI.LandingPage;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Welcome Page");
            LandingPage panel = new LandingPage(frame);
            frame.add(panel);
            frame.setSize(1000, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            
        });
	}

}
