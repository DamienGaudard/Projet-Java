package display;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.JPanel;

import elements.Displayable;
import elements.Map;

public class Layout extends JPanel{

	private static final long serialVersionUID = 1L;
	private Map content;
	private int layout_scale;
	
	public Layout(int width, int height, int map_width, int map_height,int layout_scale){
		
		this.content = new Map(map_width,map_height);
		this.setLayout_scale(layout_scale);
		this.setSize(width, height);
		
	}
	
	public void paint(Graphics g){
		
		//affichage damier
		for(int i = 0 ; i < this.getWidth()/layout_scale + 1; i ++) {
			for(int j = 0; j < this.getHeight()/layout_scale + 1; j++) {
				drawImageAt(content.get_cell(i, j),g);
			}
		}
		
		//affichage non_entites
		for(int i = 0 ; i < this.getWidth()/layout_scale + 1; i ++) {
			for(int j = 0; j < this.getHeight()/layout_scale + 1; j++) {
				if(content.get_non_human(i, j) != null)
					drawImageAt(content.get_non_human(i, j),g);
			}
		}
		
		//affichage entites
		for(int i = 0 ; i < this.getWidth()/layout_scale + 1; i ++) {
			for(int j = 0; j < this.getHeight()/layout_scale + 1; j++) {
				if(content.get_human(i, j) != null) {
					g.drawRect(i*this.layout_scale, j*this.layout_scale, this.layout_scale, this.layout_scale);
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
			
			drawImage(d.get_img(), new Rectangle(left_top_corner_posX, left_top_corner_posY, scaled_width, scaled_height), d.selection_sprite(), g);
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
	
	public void time_flies() {
		this.content.next_turn();
	}

}
