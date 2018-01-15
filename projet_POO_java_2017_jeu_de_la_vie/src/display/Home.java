package display;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JPanel;

import diplayable.Displayable;
import elements.Ground;
import entity.House;
import entity.Man;
import entity.Woman;

public class Home extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JButton play;
	private JButton help;
	private JButton quit;
	private int choice = 1000;
	
	//decoration
	private Man ma;
	private Woman wo;
	private Ground gr;
	private House ho;
	
	//images des boutons
	private static Image button_img;
	private static Image play_img;
	private static Image help_img;
	private static Image quit_img;
	
	
	public Home(){
		
		this.setBackground(Color.cyan);
		
		try {
			button_img = ImageIO.read(new File("wooden_plank.png"));
			play_img = ImageIO.read(new File("play_text.png"));
			help_img = ImageIO.read(new File("help_text.png"));
			quit_img = ImageIO.read(new File("quit_text.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		play = new JButton("Play") {
			
			private static final long serialVersionUID = 0L;
			
			public void paintComponent(Graphics g) {
				g.drawImage(button_img,0,0, this.getWidth(), this.getHeight(),this);
				g.drawImage(play_img,0,0, this.getWidth(), this.getHeight(),this);
			}
			
		};
		
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		
		help = new JButton("Help"){
			
			private static final long serialVersionUID = 0L;
			
			public void paintComponent(Graphics g) {
				g.drawImage(button_img,0,0, this.getWidth(), this.getHeight(),this);
				g.drawImage(help_img,0,0, this.getWidth(), this.getHeight(),this);
			}
			
		};
		
		help.setOpaque(false);
		help.setContentAreaFilled(false);
		help.setBorderPainted(false);
		
		quit = new JButton("Quit"){
			
			private static final long serialVersionUID = 0L;
			
			public void paintComponent(Graphics g) {
				g.drawImage(button_img,0,0, this.getWidth(), this.getHeight(),this);
				g.drawImage(quit_img,0,0, this.getWidth(), this.getHeight(),this);
			}
			
		};
		
		quit.setOpaque(false);
		quit.setContentAreaFilled(false);
		quit.setBorderPainted(false);
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(gbl);
		
		gbc.ipady = 100;
		gbc.ipadx = 300;
		gbc.insets = new Insets(20,20,20,20); // padding

		gbc.gridy = 0;
		add(play,gbc);

		gbc.gridy = 1;
		add(help,gbc);
		
		gbc.gridy = 2;
		add(quit,gbc);
		
		ActionHome go = new ActionHome();
		play.addActionListener(go);
		
		ActionHome h = new ActionHome();
		help.addActionListener(h);
		
		ActionHome leave = new ActionHome();
		quit.addActionListener(leave);
		
		ma = new Man(0,0,"Osef");
		wo = new Woman(0,0,"Osef");
		gr = new Ground(0,0);
		ho = new House(0,0);
		
	}
	
	public int getChoice(){
		return choice;
	}
	
	public void setChoice(int a){
		this.choice = a;
	}
	
	private class ActionHome implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() == play){
				setChoice(0);
			}else if(e.getSource() == help){
				setChoice(1);
			}else if(e.getSource() == quit){
				setChoice(2);
			}
			
		}
	}	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		for(int i = 0; i < this.getWidth()/32/2 + 1; i ++) {
			for(int j = 0; j < this.getHeight()/32/2 + 1; j++) {
				if(Math.cos(i*j + i*i + j*j - i - j) > 0.5)
					gr.setEtat(1);
				else
					gr.setEtat(0);
				this.drawImageAt(gr, i, j, g);
			}
		}
		
		this.drawImageAt(ho, -1, 1, g);
		this.drawImageAt(ho, 7, 7, g);
		this.drawImageAt(ho, 9, -2, g);
		
		this.drawImageAt(wo, 1, 6, g);
		this.drawImageAt(ma, 2, 10, g);
		this.drawImageAt(wo, 10, 6, g);
		this.drawImageAt(ma, 6, 2, g);
		
		this.drawImageAt(ho, 20, 13, g);
		this.drawImageAt(ho, 22, 4, g);
		
		this.drawImageAt(ma, 15, 14, g);
		this.drawImageAt(wo, 23, 11, g);
		this.drawImageAt(ma, 19, 8, g);
		
		this.drawImageAt(wo, 7, 15, g);
		this.drawImageAt(ma, 28, 14, g);
		this.drawImageAt(wo, 22, 2, g);
		
		Graphics2D g2d = (Graphics2D)g; 
		
		// effet
		g2d.setPaint(new GradientPaint(0, 0, new Color(155,55,0,160), this.getWidth(), this.getHeight(), new Color(155,0,89,160)));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
	}
	
	private void drawImageAt(Displayable d, int x, int y, Graphics g) {
		
		int beginX = x*32*2 + d.dWidth()*2;
		int beginY = y*32*2 + d.dHeight()*2;
		int endX = beginX + d.get_width()*2;
		int endY = beginY + d.get_height()*2;
		
		Rectangle r = d.selection_sprite();
		
		int selBeginX = r.x;
		int setBeginY = r.y;
		int selEndX = r.x + r.width;
		int selEndY = r.y + r.height;
		
		g.drawImage(d.get_img(), beginX, beginY, endX, endY, selBeginX, setBeginY, selEndX, selEndY, this);
		
	}
	
}
