package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import model.Model;
import view.View;

public class Controller {

	Model model;
	View view;
	int counter = 0;
	
	public Controller(){
		model = new Model();
		BufferedImage img = model.getImage();
		view = new View(this, img);
		view.update(model.getImage(),model.getXCenter(), model.getYCenter(), 1.0/model.getMagnfication());
	}
	

	public void handleMousePress(MouseEvent arg0) {
		
		// TODO Auto-generated method stub
		System.out.println("MouseClicked Called");
		model.calculateNewOrigin(arg0.getX(), arg0.getY());
		System.out.println("calculateNewOrigin done");
		model.updateMagnification();
		System.out.println("updateMag done");
		model.reRender();
		System.out.println("reRender done");
		view.update(model.getImage(),model.getXCenter(), model.getYCenter(), model.getMagnfication());
		counter++;
		System.out.println("Zoom: " + counter);
	}

	public void handleKeyPress(KeyEvent k) {
		
		if(k.getKeyChar() == 'h')
		view.displayHelp();
		model.processKeyInput(k.getKeyChar());
		model.reRender();
		view.update(model.getImage(), model.getXCenter(), model.getYCenter(), model.getMagnfication());
		// TODO Auto-generated method stub
		
	}

	
	public static void main (String [] arg){
		Controller c = new Controller();
	}
}
