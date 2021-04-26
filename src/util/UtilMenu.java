package util;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.pdfbox.pdmodel.PDDocument;

import adapters.PDFDocument;
import interfaces.IDocument;

public class UtilMenu {

	private JPanel panel;
	private JFrame frame;

	public UtilMenu() {
		this.panel = new JPanel();
		this.frame = new JFrame();
	}

	public void createMenu() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel("Adapter - INF011");
		frame.setTitle("INF011");
		label.setOpaque(true);
		label.setBackground(new Color(135, 233, 126));
		label.setFont(new Font("Dialog", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel.setLayout(new BorderLayout());
		frame.getContentPane().add(panel);
		panel.add(label, BorderLayout.CENTER);

		Button button = new Button("Selecionar Arquivo");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfile = new JFileChooser();
				jfile.setFileSelectionMode(JFileChooser.FILES_ONLY);
				panel.add(jfile);
				int i = jfile.showSaveDialog(null);
				if (i != 1) {
					File arquivo = jfile.getSelectedFile();
					try {
						if(getFileExtension(arquivo.getName()).equals("pdf")) { 
							IDocument doc = new PDFDocument(new PDDocument());
							doc.open(arquivo.getAbsolutePath());
							doc.getEditor();
						} else {
							JOptionPane.showMessageDialog(null, "Arquivo sem suporte pela ferramenta");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		panel.add(button, BorderLayout.PAGE_END);
		frame.setSize(320, 200);
		frame.setLocation(1250, 400);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
	}
	
	private static String getFileExtension(String filename) {
		if (filename.contains("."))
			return filename.substring(filename.lastIndexOf(".") + 1);
		else
			return "";
	}

}
