package elements;

public class Woman extends Human{
	
	public Woman(int posX, int posY) {
		super(posX, posY);
	}

	private static final String img_path = "woman.png";
	private static Sprite woman_sprite = null;

	public Sprite get_sprite_info(){
		
		if(Woman.woman_sprite == null)
			Woman.woman_sprite = new Sprite(Woman.img_path);
		
		return Woman.woman_sprite;
		
	}

	public int get_width() {
		return 32;
	}

	public int get_height() {
		return 48;
	}
	
}
