package pdfdocument;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import app.ImagePanel;
import interfaces.IDocument;

public class PDFDocument implements IDocument{
	
	private PDDocument pddocument;
	private File file;
	
	private int pagInicio = 0;
	private int pagFim = 0;
	private JLabel label = null;
	
	public PDFDocument( ) {
		
	}
	
	public void setDocument(PDDocument pddocument) {
		this.pddocument = pddocument;
	}
	
	@Override
	public void open(String filename) {
		 this.file = new File(filename);
	}

	@Override
	public JFrame getEditor() throws Exception {
		if (file == null)
			return null;
		
		JFrame frame = new JFrame();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("INF011 - Adapter");
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setSize(900, 650);
		frame.setLocation(200, 100);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		
		try {
			pddocument = Loader.loadPDF(file);
			System.out.println(pddocument.getNumberOfPages()); 
			
			pagInicio = 0;
			pagFim = pddocument.getNumberOfPages() - 1;
			label = new JLabel();
			label.setName("labelPagina");
			label.setText("Pagina: " + (pagInicio+1) + "/" + (pagFim+1));
			PDFRenderer renderer = new PDFRenderer(pddocument);
			BufferedImage image = renderer.renderImage(pagInicio);
			
			JPanel panel = new ImagePanel(image);
			panel.setName("Panel"+pagInicio);
			frame.getContentPane().add(label,  BorderLayout.PAGE_START);
			frame.getContentPane().add(panel,  BorderLayout.CENTER);
			
			Button buttonAvancar = new Button("Avançar >");
			buttonAvancar.setFont(new Font("Dialog", Font.BOLD, 14));
			Button buttonVoltar = new Button("Voltar <");
			buttonVoltar.setFont(new Font("Dialog", Font.BOLD, 14));
			
			if (pagInicio == pagFim) {
				buttonAvancar.setEnabled(false);
			}
			buttonAvancar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pagInicio++;
					if (pagInicio == pagFim) {
						buttonAvancar.setEnabled(false);
					}
					if (pagInicio > 0) {
						buttonVoltar.setEnabled(true);
					}
					try {
						BufferedImage image2 = renderer.renderImage(pagInicio);
						Component[] components = frame.getContentPane().getComponents();
						for (Component component : components) {
							if (component.getName() != null) {
								if (component.getName().startsWith("Panel")) { 
									frame.remove(component); 
								}
							}
						
						}
						JPanel panel2 = new ImagePanel(image2);
						panel2.setName("Panel"+pagInicio);
						frame.getContentPane().add(panel2, BorderLayout.CENTER);
						label.setText("Pagina: " + (pagInicio+1) + "/" + (pagFim+1));
						SwingUtilities.updateComponentTreeUI(frame);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			buttonVoltar.setEnabled(false);
			buttonVoltar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pagInicio--;
					if (pagInicio == 0) {
						buttonVoltar.setEnabled(false);
					}
					if (pagInicio < pagFim) {
						buttonAvancar.setEnabled(true);
					}
					try {
						BufferedImage image3 = renderer.renderImage(pagInicio);
						Component[] components = frame.getContentPane().getComponents();
						for (Component component : components) {
							if (component.getName() != null) {
								if (component.getName().startsWith("Panel")) {
									frame.remove(component);
								}
							}
							
						
						}
						JPanel panel3 = new ImagePanel(image3);
						panel3.setName("Panel"+pagInicio);
						frame.getContentPane().add(panel3, BorderLayout.CENTER);
						label.setText("Pagina: " + (pagInicio+1) + "/" + (pagFim+1));
						SwingUtilities.updateComponentTreeUI(frame);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			frame.getContentPane().add(buttonAvancar, BorderLayout.EAST);
			frame.getContentPane().add(buttonVoltar, BorderLayout.WEST);
			
		}catch (Exception e) {
			e.getMessage();
		}
		
		return frame;
	}
	
}
