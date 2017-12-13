package root;

import display.Layout;
import display.Window;

public class Program_start{
	
	static Window w;
	static Layout l;

	public static void main(String[] args) throws InterruptedException{
		
		w = new Window(1600,1000);
		l = new Layout(1600,1000,50,50,32);
		w.setContentPane(l);

		Thread auto_refresh = new Thread(new Program_start().new Auto_refresh_display());
		auto_refresh.start();
		
		int i = 0;
		while(true) {
			
			l.time_flies(); //dure ~ 1 sec
			
			i = i + 1 % 10;
		}
		
	}
	
	public class Auto_refresh_display implements Runnable{

		
		
		public void run() {
			
			while(true) {
				w.repaint();
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

}