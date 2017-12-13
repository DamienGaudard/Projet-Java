package elements;

import java.awt.Rectangle;

public class Ground extends Cell{

	public Ground(int posX, int posY) {
		super(posX, posY);
	}

	private static final String img_path = "ground.png";
	private static Sprite ground_sprite = null;

	public Sprite get_sprite_info(){
		
		if(Ground.ground_sprite == null)
			Ground.ground_sprite = new Sprite(Ground.img_path);
		
		return Ground.ground_sprite;
		
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

	public boolean isTraversable() {
		return true;
	}

}
