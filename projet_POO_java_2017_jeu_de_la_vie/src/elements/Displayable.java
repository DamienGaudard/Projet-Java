package elements;

import java.awt.Image;
import java.awt.Rectangle;

public abstract class Displayable{ // affichable

	

	// en pixels utilisés pour l'affichage
	protected int posX;
	protected int posY;
	
	// les positions sont ici en cases
	public Displayable(int posX, int posY){
		this.posX = posX * 32;
		this.posY = posY * 32;
		
	}
	
	public abstract Sprite get_sprite_info();
	
	public Image get_img(){
		
		return get_sprite_info().get_texture();
		
	}
	
	/* *
	 * function : selection_sprite
	 * 
	 * @return la section de l'image charg�e dans le sprite � afficher 
	 * */
	public abstract Rectangle selection_sprite();
	
	public int getPosX(){
		return this.posX;
	}
	
	public int getPosY(){
		return this.posY;
	}
	
	// dimensions du personnage sur la grille
	public abstract int get_width();
	public abstract int get_height();
	
	// decaler les sprites pour qu'ils aient les pieds sur la bonne case
	public abstract int dWidth();
	public abstract int dHeight();
	
}
