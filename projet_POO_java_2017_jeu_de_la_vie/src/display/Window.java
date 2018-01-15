package display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Window extends JFrame{

	private Thread auto_refresh;
	private Auto_refresh_display ARD;
	private static final long serialVersionUID = 1L;
	private Layout contentPane;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu = new JMenu("Fichier");
	private JMenu menu2 = new JMenu("Aide");
	private JMenuItem menu2Item = new JMenuItem("Afficher l'aide");
	private JMenuItem menuItem = new JMenuItem("Agrandir");
	private JMenuItem menuItem2 = new JMenuItem("Quitter");
	
	public Window(int width, int height, Layout l){
		
		contentPane = l;
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setTitle("Jeu de la vie v0.0");
		menuBar.add(menu);
		menuBar.add(menu2);
		
		// fermeture du la fenetre
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0) {
				stop_auto_refresh();
				System.exit(0);
			}
		});
		
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//ne marche pas... maintenant si
				setExtendedState(Window.MAXIMIZED_BOTH);
			}
		});
		
		menuItem2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				stop_auto_refresh();
				System.exit(0);
			}
		});
		
		menu2Item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == menu2Item){
					//Afficher l'aide
				}
			}
		});
		
		menu.add(menuItem);
		menu.add(menuItem2);
		menu2.add(menu2Item);
		setJMenuBar(menuBar);
		
	}
	
	public class Zoom implements MouseWheelListener{

		public void mouseWheelMoved(MouseWheelEvent e) {
			
			int scale = contentPane.getLayout_scale();
			int new_scale = scale - e.getWheelRotation();
			if(new_scale < 8)
				new_scale = 8;
			if(new_scale > 128)
				new_scale = 128;
			contentPane.setLayout_scale(new_scale);
			
		}
		
	}
	
	public class CamMovement implements KeyListener{

		public void keyTyped(KeyEvent e) {
			
		}

		public void keyPressed(KeyEvent e) {

			switch(e.getKeyCode()) {
			
			case KeyEvent.VK_UP:
				contentPane.move_view(0, -4);
				break;
			
			case KeyEvent.VK_RIGHT:
				contentPane.move_view(4, 0);
				break;
				
			case KeyEvent.VK_DOWN:
				contentPane.move_view(0, 4);
				break;
				
			case KeyEvent.VK_LEFT:
				contentPane.move_view(-4, 0);
				break;
				
			default:
				break;
			
			}
			
		}

		public void keyReleased(KeyEvent e) {
			
		}
		
	}
	
	public class CamMovementMouse implements MouseListener, MouseMotionListener{

		Point2D origine;
		
		public void mouseClicked(MouseEvent e) {
			
		}

		public void mousePressed(MouseEvent e) {
			
			origine = new Point2D.Float(e.getX(), e.getY());
			
		}

		public void mouseReleased(MouseEvent e) {
			
			origine = null;
			
		}

		public void mouseEntered(MouseEvent e) {
			
		}

		public void mouseExited(MouseEvent e) {
			
		}

		public void mouseDragged(MouseEvent e) {
			
			Point2D nou = new Point2D.Float(e.getX(), e.getY());
			contentPane.move_view(-(int)nou.getX()*8/contentPane.getLayout_scale()
					+ (int)origine.getX()*8/contentPane.getLayout_scale() , 
					-(int)nou.getY()*8/contentPane.getLayout_scale()  
					+ (int)origine.getY()*8/contentPane.getLayout_scale() );
			origine = nou;
			
		}

		public void mouseMoved(MouseEvent e) {
			
		}
		
	}
	
	public void auto_refresh() {
		if(auto_refresh == null) {
			ARD = new Auto_refresh_display();
			auto_refresh = new Thread(ARD);
			auto_refresh.start();
		}
	}
	
	public void stop_auto_refresh() {
		if(auto_refresh != null) {
			
			try {
				ARD.stop();
				auto_refresh.join();
				ARD = null;
				auto_refresh = null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class Auto_refresh_display implements Runnable{

		private boolean state;
		
		public Auto_refresh_display() {
			this.state = true;
		}
		
		public void run() {
			
			while(state) {
				repaint();
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		public void stop() {
			this.state = false;
		}
		
	}
	
}
