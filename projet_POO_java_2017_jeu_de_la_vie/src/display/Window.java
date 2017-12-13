package display;

import javax.swing.JFrame;

public class Window extends JFrame{

	private static final long serialVersionUID = 1L;

	public Window(int width, int height){
		
		this.setSize(width, height);
		this.setTitle("Jeu de la vie v0.0");
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
}
