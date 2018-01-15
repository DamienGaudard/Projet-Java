package root;

import display.Home;
import display.Layout;
import display.Window;
import display.Window.CamMovementMouse;

public class Program_start{
	
	static Window w;
	static Layout l;
	static Home home;
	
	public static void main(String[] args) throws InterruptedException{

		home = new Home();
		l = new Layout(0,0,50,50,32);
		w = new Window(32*28,32*28, l);
		w.setContentPane(home);
		w.setVisible(true);
		w.revalidate();
		
		while(home.getChoice() == 1000)
			Thread.sleep(100);
		
		if(home.getChoice() == 1){
			//afficher l'aide
			
		}else if(home.getChoice() == 2){
			System.exit(0);
			
		}else if(home.getChoice() == 0){
			
			w.setSize(900,900);
			w.setContentPane(l);
			w.addMouseWheelListener(w.new Zoom());
			w.addKeyListener(w.new CamMovement());
			CamMovementMouse cmm = w.new CamMovementMouse();
			w.addMouseListener(cmm);
			w.addMouseMotionListener(cmm);
			w.setResizable(true);
			w.revalidate();
			w.requestFocus(); // pour faire fonctionner le clavier sous windows
			w.auto_refresh();
			
			while(true) {
				
				l.time_flies(500); //dure ~ 0.5sec
				
			}
			
		}
	}

}