package elements;

import java.awt.Rectangle;

public class Corn extends Non_human{
	
	public Corn(int posX, int posY) {
		super(posX, posY);
	}

	private static final String img_path = "corn.png";
	private static Sprite corn_sprite = null;
	private int state = 0;

	public void clock_tic(){
		
		this.entity_age ++;
		if(this.state >= 2) this.state = 0;
		else this.state++;
	}

	public boolean isFunctional(){
		
		return this.entity_age >= 2;
		
	}

	public boolean isDying(){
		
		return this.entity_age >= 10;
		
	}

	public void send_message(Entity to){
		
	}

	public void receive_message(Entity from){
		
	}

	public Sprite get_sprite_info(){
		
		if(Corn.corn_sprite == null)
			Corn.corn_sprite = new Sprite(Corn.img_path);
		
		return Corn.corn_sprite;
		
	}

	public Rectangle selection_sprite(){ // completer
		
		return new Rectangle(0,this.state*28,28,28);
		
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
		return Request.NONE;
	}

}
