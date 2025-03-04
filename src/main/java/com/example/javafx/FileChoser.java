package com.example.javafx;

import javax.swing.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FileChoser {

	public static File chooseWindow() {
		return chooseWindow(false);
	}
	public static File chooseWindow(boolean selectFolder) {
		
		File selectedFile = null;

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		// Create a frame to attach the file chooser to
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setVisible(true);

		// Create a file chooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select a file");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        
		// Show the file chooser dialog
		int result = fileChooser.showOpenDialog(frame);

		// Check if the user selected a file
		if (result == JFileChooser.APPROVE_OPTION) {
			if(selectFolder) {
			    File selectedFolder = fileChooser.getSelectedFile();
			    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			    String date = LocalDate.now().format(formatter);
			    selectedFile = new File(selectedFolder, date + "_Ordizia.xml");
			}else {
				// Get the selected file
				selectedFile = fileChooser.getSelectedFile();
			}

			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		} else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("File selection cancelled.");
		}

		// Close the frame
		frame.dispose();

		return selectedFile;
	}

	public static File getFileFromRoute() {
		File fXmlFile;
		String route = "C:\\Users\\amadinabeitia\\Documents\\2024_2025_Workspaces\\XMLWorkspace\\DatuAtzipena\\2ERRONKA\\";
		String fileName = "eguraldia.xml";
		String fullRoute = route + fileName;
		fXmlFile = new File(fullRoute);
		return fXmlFile;
	}

}
