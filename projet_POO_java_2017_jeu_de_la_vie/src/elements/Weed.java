package elements;

import java.awt.Rectangle;

public class Weed extends Non_human{
	
	public Weed(int posX, int posY) {
		super(posX, posY);
	}

	private static final String img_path = "weed_path";
	private static Sprite weed_sprite = null;

	public void clock_tic() {
		
		this.entity_age ++;
		
	}

	public boolean isFunctional(){
		
		return this.entity_age >= 2;
		
	}

	public boolean isDying(){
		
		return false;
		
	}
	
	public Sprite get_sprite_info(){
		
		if(Weed.weed_sprite == null)
			Weed.weed_sprite = new Sprite(Weed.img_path);
		
		return Weed.weed_sprite;
		
	}

	public Rectangle selection_sprite(){ // completer
		
		return null;
		
	}

	public int get_width() {
		return 32;
	}

	public int get_height() {
		return 32;
	}

	public int dWidth() {
		return 0;
	}

	public int dHeight() {
		return 0;
	}

	public Request reply() {

		if(this.isFunctional())
			return Request.SPREAD;
		
		return Request.NONE;			
		
	}

}
