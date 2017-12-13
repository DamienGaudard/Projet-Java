package elements;

import java.awt.Rectangle;

public abstract class Human extends Entity{
	
	public enum Orientation{
		
		DOWN,
		LEFT,
		RIGHT,
		TOP
		
	}
	
	private Orientation caracter_orientation;
	private int animation_step;
	private int age_of_death;
	
	
	private Thread animation;
	
	// posX et posY Ã  donner en cases
	public Human(int posX,int posY) {
		super(posX,posY);
		int random_age = 50 + (int)(Math.random()*11); // "*11" : regarder la description de random
		this.age_of_death = random_age;
		this.caracter_orientation = Orientation.DOWN;
		this.animation_step = 0;
	}
	
	public int getPosX(){
		return this.posX;
	}
	
	public int getPosY(){
		return this.posY;
	}
	
	public void clock_tic(){ // inserer IA ici :
		
		this.entity_age ++;
		
	}
	
	public void animer() {

		animation = new Thread(new AnimationDeplacement(this.caracter_orientation));
		animation.start();
		
	}
	
	public Orientation chose_direction(int grid[][]) {
		
		this.caracter_orientation = Orientation.values()[(int) (Math.random()*4)]; // aléatoire
		return this.caracter_orientation;
	}
	
	public void reply() {
		if(animation != null)
			try {
				animation.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public boolean isFunctional(){
		
		return this.entity_age >= 5;
		
	}
	
	public boolean isDying(){
		
		return this.entity_age >= age_of_death;
		
	}

	public Orientation getCaracter_orientation() {
		return caracter_orientation;
	}
	
	public Rectangle selection_sprite(){
		
		return new Rectangle(32 * this.animation_step, 48 * this.caracter_orientation.ordinal(), 32, 48);
		
	}
	
	public int dWidth() {
		return 0;
	}

	public int dHeight() {
		return -24;
	}
	
	public class AnimationDeplacement implements Runnable{

		private Orientation direction;
		
		public AnimationDeplacement(Orientation o) {
			this.direction = o;
		}
		
		public void run() {
			
			caracter_orientation = direction;
			for(int i = 0; i < 32; i++) {
				
				try {
					Thread.sleep(1000/32);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				animation_step = i*4/32;
				switch(direction){
				
				case DOWN:
					posY ++;
					break;
				case LEFT:
					posX --;
					break;
				case RIGHT:
					posX ++;
					break;
				case TOP:
					posY --;
					break;
				
				}
				
			}
			animation_step = 0;
			
		}
		
	}

}
