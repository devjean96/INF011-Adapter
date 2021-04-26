package app;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private Image img, menor;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		this.img = img;
		//System.out.println(img.getWidth(null)  + " - " + img.getHeight(null));
		boolean flag = getTamanhoImage(img.getWidth(null), img.getHeight(null));

		if (flag == false)
			menor = img;
		else
			menor = new ImageIcon(img).getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT);

		Dimension size = new Dimension(menor.getWidth(null), menor.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public boolean getTamanhoImage(int x, int y) {
		boolean flag = false;

		if (x > 800 && y > 600) {
			flag = true;
		}
		return flag;
	}

	public void paintComponent(Graphics g) {
		g.drawImage(menor, 0, 0, null);
	}
}
