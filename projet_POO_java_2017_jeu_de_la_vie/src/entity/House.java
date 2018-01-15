package entity;

import java.awt.Rectangle;

import diplayable.Sprite;

public class House extends Non_human{
	
	public House(int posX, int posY) {
		super(posX, posY);
		father = null;
		mother = null;
		child = null;
		this.full = false;
	}

	private static final String img_path = "house.png";
	private static Sprite house_sprite = null;
	
	private Man father;
	private Woman mother;
	private Human child;
	
	private boolean full;

	// affichage
	
	public Sprite get_sprite_info(){
		
		if(House.house_sprite == null)
			House.house_sprite = new Sprite(House.img_path);
		
		return House.house_sprite;
		
	}
	
	public Rectangle selection_sprite() {
		if(!full) {
			return new Rectangle(0,0,216,218);
		}
		else {
			return new Rectangle(0,219,216,218);
		}
		
	}

	public int get_width() {
		return 32*5;
	}

	public int get_height() {
		return 32*5;
	}

	public int dWidth() {
		return 0;
	}

	public int dHeight() {
		return 0;
	}

	public void clock_tic() {
		if(this.father != null && this.mother != null)
			this.full = true;
		if(father != null)
			father.checkPartner();
		if(mother != null)
			mother.checkPartner();
	}
	
	// regles
	
	public boolean spread() {
		return false;
	}
	
	public boolean isFunctional() {
		return !this.isMarked();
	}

	public boolean isDying() {
		return false;
	}
	
	public boolean enterHouse(Human enteringHuman) {
		
		if(enteringHuman.getClass() == Man.class) {
			
			if(this.father != null)
				return false;
			else {
				this.father = (Man)enteringHuman;
				return true;
			}
				
		}
		else {
			
			if(this.mother != null)
				return false;
			else {
				this.mother = (Woman)enteringHuman;
				return true;
			}
				
		}
		
	}
	
	public boolean isFull() {
		return full;
	}
	
	// a redef
	public Human exitHouse() {
		
		Human result = null;
		
		if((this.entity_age > 1 && father != null) || (father != null && !father.hasPartner())) {
			result = this.father;
			this.father.setPartner(null);
			this.father = null;
		}
		else if((this.entity_age > 5 && mother != null) || (mother != null && !mother.hasPartner())) {
			result = this.mother;
			this.mother.setPartner(null);
			this.mother.resetCount();
			this.mother = null;
		}
		else if(this.entity_age > 10 && child != null) {
			result = this.child;
			this.child = null;
			// tout le monde est parti la maison est dispo
			this.full = false;
			this.unmark();
		}
		
		return result;
		
	}
	
	public void updateHouse() {
	
		// naissance humain
		if(this.entity_age >= 5 && this.child == null) {
		
			int rand = (int) (Math.random()*2);
			
			if( rand == 0) 
				child = new Man(this.posX/32 + 2, this.posY/32 + 4,"Fils");
			else
				child = new Woman(this.posX/32 + 2, this.posY/32 + 4,"Fille");
			
		}
		
		if(this.full)
			this.entity_age ++;
		else
			this.entity_age = 0;
		
	}
	
	public void destroy() {
		
	}
	
	// pathfinding

	public boolean isSolid() {
		return true;
	}

}
