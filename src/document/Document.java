package document;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import interfaces.IDocument;

public class Document implements IDocument{

	@Override
	public void open(String filename) {
		System.out.println("Arquivo Aberto" + filename);
		
	}

	@Override
	public JFrame getEditor() throws Exception {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.setTitle("INF011 - Adapter");
		panel.setLayout(new BorderLayout());
		frame.getContentPane().add(panel);
		frame.setSize(320, 200);
		frame.setLocation(1250, 400);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		return frame;
	}

}
