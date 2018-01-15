package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JPanel;

import diplayable.Displayable;
import elements.Ground;
import elements.Map;
import entity.Entity;
import entity.Human;
import entity.Non_human;

public class Layout extends JPanel{

	private static final long serialVersionUID = 1L;
	private Map content;
	
	private int layout_scale;
	private int view_pos_x;
	private int view_pos_y;
	
	public Layout(int width, int height, int map_width, int map_height,int layout_scale){
		
		this.content = new Map(map_width,map_height);
		this.setLayout_scale(layout_scale);
		this.setSize(width, height);
		this.view_pos_x = 75;
		this.view_pos_y = 75;
		
	}
	
	public void paintComponent(Graphics g){
		
		int begin_x = (this.view_pos_x*layout_scale/8 - this.getWidth()/2)/layout_scale - 1;
		int begin_y = (this.view_pos_y*layout_scale/8 - this.getHeight()/2)/layout_scale - 1;
		int end_x = begin_x + this.getWidth()/layout_scale + 3;
		int end_y = begin_y + this.getHeight()/layout_scale + 3;
		
		for(int i = begin_x ; i < end_x; i ++) {
			for(int j = begin_y; j < end_y; j++) {
				Ground gr = content.get_ground(i, j);
				Non_human nh = content.get_non_human(i, j);
				Human hu = content.get_human(i, j);
				
				if(gr != null)
					gr.setDisplayed(false);
				
				if(nh != null)
					nh.setDisplayed(false);
				
				if(hu != null)
					hu.setDisplayed(false);
				
			}
		}
				
		//affichage damier
		for(int i = begin_x ; i < end_x; i ++) {
			for(int j = begin_y; j < end_y; j++) {
				drawImageAt(content.get_ground(i, j),g);
			}
		}
		
		//affichage non_entites
		for(int i = begin_x ; i < end_x; i ++) {
			for(int j = begin_y; j < end_y; j++) {
				Non_human nh = content.get_non_human(i, j);
				if(nh != null) {
					if(!nh.isDisplayed()) {
						drawImageAt(nh,g);
						nh.setDisplayed(true);
					}
				}
			}
		}
		
		//affichage entites
		for(int i = begin_x ; i < end_x; i ++) {
			for(int j = begin_y; j < end_y; j++) {
				if(content.get_human(i, j) != null) {
					drawImageAt(content.get_human(i, j),g);
				}
			}
		}
		
	}
	
	/* *
	 * function : drawImage
	 * description : dessine correctement le sprite de d selon le contexte dans lequel est le layout.
	 * 
	 * @param d : l'objet affichable a afficher
	 * @param x : la position x sur la grille
	 * @param y : la position y sur la grille 
	 * @param g : dessine sur le layout 
	 * */
	public void drawImageAt(Displayable d, Graphics g) {
		
		if(d != null){		
			int left_top_corner_posX = d.getPosX()*layout_scale/32 + d.dWidth()*layout_scale/32;
			int left_top_corner_posY = d.getPosY()*layout_scale/32 + d.dHeight()*layout_scale/32;
			int scaled_width = d.get_width()*layout_scale/32;
			int scaled_height = d.get_height()*layout_scale/32;

			//d�calage� la position due la "camera"
			left_top_corner_posX += this.getWidth()/2 - this.view_pos_x*this.layout_scale/8;
			left_top_corner_posY += this.getHeight()/2 - this.view_pos_y*this.layout_scale/8;
			
			drawImage(d.get_img(), new Rectangle(left_top_corner_posX, left_top_corner_posY, scaled_width, scaled_height), d.selection_sprite(), g);
			
			if(!(d instanceof Ground)) {
				Entity e = (Entity)d;
				if(!e.isFunctional()) {
					g.setColor(Color.RED);
					
				}else {
					g.setColor(Color.green);
				}
				g.fillRect(left_top_corner_posX, left_top_corner_posY, layout_scale/4, layout_scale/4);
			}
			
		}
		
	}
	
	/* *
	 * function : drawImage
	 * description : dessine la section d'une image donnee, dans une zone donnee du Layout
	 * attention : compliquee, ne doit pas etre appelee ailleur que dans drawImageAt
	 * 
	 * @param img  : image a afficher 
	 * @param dest : zone cible de l'ecran ou sera afficher l'image
	 * @param src  : la section de l'image a afficher
	 * @param g    : dessine sur le layout 
	 * */
	public void drawImage(Image img, Rectangle dest, Rectangle src, Graphics g){
		
		g.drawImage(img, dest.x, dest.y, dest.x + dest.width, dest.y + dest.height, src.x, src.y, src.x + src.width, src.y + src.height, this);
		
	}

	public int getLayout_scale(){
		
		return layout_scale;
		
	}
	
	public void setLayout_scale(int layout_scale){
		
		this.layout_scale = layout_scale;
		
	}
	
	public void time_flies(int duration) {
		this.content.next_turn(duration);
	}
	
	public void move_view(int dx, int dy) {
		this.view_pos_x += dx;
		this.view_pos_y += dy;
	}
}
