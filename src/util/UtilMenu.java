package util;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
						checkPluginToFile(arquivo);
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
	
	private static <T> void checkPluginToFile(File file) throws Exception {

		String extensaoArquivo = UtilArquivo.getFileExtension(file.getName());
		String[] plugins = UtilArquivo.getAllPlugins();

		int i;
		URL[] jars = new URL[plugins.length];
		for (i = 0; i < plugins.length; i++) {
			jars[i] = (new File("./plugins/" + plugins[i])).toURL();
		}
		URLClassLoader ulc = new URLClassLoader(jars);
		boolean existePlugin = false;
		for (int j = 0; j < plugins.length; j++) {
			String factoryName = UtilArquivo.getDocumentName(plugins[j]);
     		String extensionPlugin = UtilArquivo.getPluginExtension(plugins[j]);
			
		     if (extensionPlugin.equals(extensaoArquivo)) { 
		    	 String className = factoryName.toLowerCase() + "." + factoryName; 
		    	 Class<?> metaAdapter = Class.forName(className, true, ulc);
		    	 
				 Method[] methods = metaAdapter.getMethods();
				 for (Method method : methods) {
					if (method.getName().equals("setDocument")){
						Type[] types = method.getGenericParameterTypes();
						Class<T> metaDocument = (Class<T>) types[0];
						Constructor<T> constructDocument = metaDocument.getConstructor();
						IDocument doc = (IDocument) metaAdapter.newInstance();
						method.invoke(doc, constructDocument.newInstance());
						doc.open(file.getAbsolutePath());
						doc.getEditor();
					}
				}
		    	 
				existePlugin = true; 
			  }
			 
		}
		if (!existePlugin) {
			JOptionPane.showMessageDialog(null, "Não existe plugin que suporte este arquivo");
		}
	}
	
}
