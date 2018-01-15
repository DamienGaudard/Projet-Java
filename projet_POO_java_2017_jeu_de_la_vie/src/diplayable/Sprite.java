package diplayable;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {

	private String img_addr;
	private Image img_texture;
	private int width;
	private int height;
	
	public Sprite(String img_addr){
		
		this.img_addr = img_addr;
		this.img_texture = null;
		try {
			this.img_texture = ImageIO.read(new File(this.img_addr));
		} catch (IOException e) {
			System.out.println("l'adresse " + this.img_addr + " est invalide");
			e.printStackTrace();
		}
		
		if(this.img_texture != null){
			this.width = img_texture.getWidth(null);
			this.height = img_texture.getHeight(null);
		}
		
	}
	
	/* *
	 * @return la texture genere par le sprite
	 * */
	public Image get_texture(){
		return this.img_texture;
	}
	
	/* *
	 * @return la largeur d'origine de l'image
	 * */
	public int getwidth(){
		return this.width;
	}
	
	/* *
	 * @return la hauteur d'origine de l'image
	 * */
	public int getheight(){
		return this.height;
	}
	
}
