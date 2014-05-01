package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.Controller;

public class View {
	Controller controller;
	BufferedImage image;
	JLabel l;
	ImageIcon icon;
	JFrame f;
	JLabel textArea;
	
	public void update (BufferedImage newImg, double xCenter, double yCenter, double mag){
		ImageIcon newIcon = new ImageIcon(newImg); 
		l.setIcon(newIcon);
		String tmp = "Center: " + xCenter + ", " + yCenter + "  Magnification: " + mag;
		textArea.setText(tmp);
	
		System.out.print("updating");
		
	}
public View(Controller c, BufferedImage img){
	image = img;
	controller = c;
	f = new JFrame("Fractal Explorer - Press h for help");
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//Dimension d = new Dimension(1000,800);
	//f.setPreferredSize(d);
	JLayeredPane jlp = new JLayeredPane();
	jlp.setPreferredSize(new Dimension(800,600));
	
	icon = new ImageIcon(image);
	l = new JLabel(icon);
	JPanel p = new JPanel(new BorderLayout());
	
	textArea = new JLabel();
	//Panel p2 = new JPanel();
//	textArea.setEditable(false);

	p.setOpaque(false);
    p.setSize(100, 100);
	
	l.addMouseListener(c);
	f.addKeyListener(c);
	p.add(BorderLayout.CENTER, l);
	p.add(BorderLayout.NORTH, textArea);
//	jlp.add(p2, new Integer(0), 0);
	
	//jlp.add(p, new Integer(1), 0);
	f.add(p);
	

	
	f.pack();
	f.setVisible(true);
	
}
public void displayHelp() {
	JOptionPane.showMessageDialog(f,
		    "Controls: \na: Pan left\ns: Pan down\nd: Pan right\nw: Pan up\nr: Reset fractal\no: Save image as png\nz: Return to previos zoom\nn: Randomize color cycle");
	
}	
}
