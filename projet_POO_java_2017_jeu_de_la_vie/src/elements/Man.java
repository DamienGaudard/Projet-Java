package elements;

public class Man extends Human{
	
	public Man(int posX, int posY) {
		super(posX, posY);
	}

	private static final String img_path = "man.png";
	private static Sprite man_sprite = null;
	
	public Sprite get_sprite_info(){
		
		if(Man.man_sprite == null)
			Man.man_sprite = new Sprite(Man.img_path);
		
		return Man.man_sprite;
		
	}

	public int get_width() {
		return 32;
	}

	public int get_height() {
		return 48;
	}

}
