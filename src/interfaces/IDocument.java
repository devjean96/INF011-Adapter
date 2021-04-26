package interfaces;

import javax.swing.JFrame;

public interface IDocument {
	
	public void open(String filename);
	public JFrame getEditor() throws Exception;

}
