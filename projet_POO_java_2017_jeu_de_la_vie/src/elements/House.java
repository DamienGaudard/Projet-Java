package elements;

import java.awt.Rectangle;

public class House extends Non_human{
	
	public House(int posX, int posY) {
		super(posX, posY);
	}

	private static final String img_path = "house_path";
	private static Sprite house_sprite = null;

	public Sprite get_sprite_info(){
		
		if(House.house_sprite == null)
			House.house_sprite = new Sprite(House.img_path);
		
		return House.house_sprite;
		
	}
	
	public Rectangle selection_sprite() {
		return new Rectangle(0,0,32,32);
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

	public void clock_tic() {
		
	}

	public Request reply() {
		return null;
	}

	public boolean isFunctional() {
		return false;
	}

	public boolean isDying() {
		return false;
	}

}
