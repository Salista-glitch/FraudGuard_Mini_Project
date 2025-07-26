package csc3a.ImageProcessing;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileChooser {
	/**
     * @param parentFrame The frame we are coming from
     * @return the File we have chosen
     */
	public static File chooseFile(JFrame parentFrame) {
		//The directory we must choose from
		JFileChooser fileChooser = new JFileChooser("data/ID documents");
		
		//Allow file selection
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		//Show open dialog and get the user's action
		int option = fileChooser.showOpenDialog(parentFrame);
		
		// If the user clicked "Open" or "Select"
        if (option == JFileChooser.APPROVE_OPTION) {
            // Return the selected file as a File object
            return fileChooser.getSelectedFile();
        } else {
            // User canceled or closed the dialog, return null
            return null;
        }
	}
}