package elements;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import entity.Action;
import entity.Corn;
import entity.Entity;
import entity.House;
import entity.HouseDoor;
import entity.Human;
import entity.Man;
import entity.Non_human;
import entity.Weed;
import entity.Woman;

public class Map{

	private Ground[][] map_Grounds;
	private Rectangle2D[] crops;
	
	// a afficher
	private Non_human[][] map_non_human;
	private Human[][] map_human;
	
	// buffers
	private Non_human[][] map_buffer_non_human;
	private Human[][] map_buffer_human;
	
	private int width;
	private int height;
	
	private int nb_of_corn_available;
	private int nb_of_house_available;
	private int nb_of_herb_available;
	private int nb_of_man_available;
	private int nb_of_woman_available;
	
	private int time;
	
	public Map(int width, int height){
		
		this.width = width;
		this.height = height;
		
		this.nb_of_corn_available = 0;
		this.nb_of_house_available = 0;
		this.nb_of_herb_available = 1;
		this.nb_of_man_available = 5;
		this.nb_of_woman_available = 5;
		
		this.time = 0;
		
		this.crops = new Rectangle2D[3];
		crops[0] = new Rectangle2D.Float(5, 5, 10, 10);
		crops[1] = new Rectangle2D.Float(8, 20, 10, 10);
		crops[2] = new Rectangle2D.Float(20, 8, 10, 10);
		
		this.map_human = new Human[width][height];
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j ++)
				map_human[i][j] = null;
		}
		
		this.map_non_human = new Non_human[width][height];
		
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j ++){
				for(Rectangle2D crop:crops){
					if(crop.contains(i,j)){
						this.map_non_human[i][j] = new Corn(i,j);
						this.nb_of_corn_available ++;
					}
				}
				
			}
		}
		map_non_human[49][49] = new Weed(49,49);
		
		this.map_Grounds = new Ground[width][height];
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j ++){
				map_Grounds[i][j] = new Ground(i,j);
				if(this.map_non_human[i][j] != null && this.map_non_human[i][j].getClass().equals(Corn.class))
					map_Grounds[i][j].setEtat(1);
				else 
					map_Grounds[i][j].setEtat(0);
			}
		}
		this.map_buffer_human = new Human[width][height];
		this.map_buffer_non_human = new Non_human[width][height];
		
		map_human[10][10] = new Man(10,10,"Johnny");
		map_human[11][10] = new Woman(11,10,"Bernardette");
		map_human[10][11] = new Man(10,11,"Henry");
		map_human[11][11] = new Woman(11,11,"Christine");
		map_human[10][12] = new Man(10,12,"Gerald");
		map_human[11][12] = new Woman(11,12,"Geraldine");
		map_human[10][13] = new Man(10,13,"Guillaume");
		map_human[11][13] = new Woman(11,13,"Patricia");
		map_human[10][14] = new Man(10,14,"Pedro");
		map_human[11][14] = new Woman(11,14,"Fransoise");
		
		System.out.println(this.put_an_house(0, 0));
		System.out.println(this.put_an_house(30, 30));
		System.out.println(this.put_an_house(20, 30));
	}
	
	public Human get_human(int x,int y){
		if(0 <= x && x < this.width && 0 <= y && y < this.height)
			return this.map_human[x][y];
		return null;
	}
	
	public Non_human get_non_human(int x, int y){
		if(0 <= x && x < this.width && 0 <= y && y < this.height)
			return this.map_non_human[x][y];
		return null;
	}
	
	public Ground get_ground(int x, int y){
		
		if(0 <= x && x < this.width && 0 <= y && y < this.height)
			return this.map_Grounds[x][y];
		
		return null;
		
	}
	
	public void set_ground(int x, int y, Ground c){
		
		this.map_Grounds[x][y] = c;
		
	}
	
	// retourne si la maison a bien ete placee
	public boolean put_an_house(int x, int y){
		
		// verifier les bords
		if(x < 0 || x >= this.width || y < 0 || y >= this.height)
			return false;
		
		boolean something_found = false;
		
		// chercher si il y a deja un element
		for(int i = 0; i < 5; i ++){
			for(int j = 0; j < 5; j++){
				
				if(this.map_human[x+i][y+j] != null 
						|| this.map_non_human[x+i][y+j] != null
						|| !this.map_Grounds[x+i][y+j].isTraversable())
					something_found = true;
				
			}
		}
		
		// si rien ne bloque
		if(!something_found){
			
			House h = new House(x,y);
			
			for(int i = 0; i < 5; i ++){
				for(int j = 0; j < 5; j++){
					this.map_non_human[x+i][y+j] = h;
				}
			}
			
			this.map_non_human[x+2][y+4] = new HouseDoor(h);
			
			this.nb_of_house_available ++;
			
		}
		
		return !something_found;
	}
	
	public boolean isInBound(int x, int y) {
		return 0 <= x && x < this.width && 0 <= y && y < this.height;
	}
	
	public boolean isAvailable(int x, int y, Class<? extends Entity> type) {
		boolean result = (this.map_non_human[x][y] != null 
				&& !map_non_human[x][y].isMarked() 
				&& this.map_non_human[x][y].getClass() == type 
				&& map_non_human[x][y].isFunctional())
				||
				(this.map_human[x][y] != null 
				&& !map_human[x][y].hasPartner() 
				&& this.map_human[x][y].getClass() == type 
				&& map_human[x][y].isFunctional()
				&& !map_human[x][y].isBusy());
		
		//System.out.println(result);
		
		return result;
	}
	
	public boolean isEntity(int x, int y, Class<? extends Entity> type) {
		return (this.map_human[x][y] != null && this.map_human[x][y].getClass() == type) || 
				(this.map_non_human[x][y] != null && this.map_non_human[x][y].getClass() == type);
	}
	
	public boolean isNotSolid(int x, int y) {
		return this.map_non_human[x][y] == null || !this.map_non_human[x][y].isSolid();
	}
	
	public void next_turn(int duration) {
		
		System.out.println("tour " + time + ": " + "reserve nouriture : " + Human.getFoodAmount() );
		
		boolean grid_d[][] = new boolean[this.width][this.height];
		
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				
				grid_d[i][j] = false; // quelles entites se deplacent
				this.map_buffer_human[i][j] = null; // netoyer les buffer
				this.map_buffer_non_human[i][j] = null;
				
			}
		}
		
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				
				// les humains
				if( this.map_human[i][j] != null) {
					int x = i,y = j;
					//choisir une direction
					this.map_human[i][j].clock_tic();
					if(this.map_human[i][j].isDying()) {
						
						this.map_human[i][j].unTarget(); // decibler la ressource cible
						this.map_human[i][j] = null; // TUER l'humain
						
					}
					else { // deplacer (ou pas)
						
						if(this.map_human[i][j].hasTarget()) {
							this.map_human[i][j].checkTarget();
							this.map_human[i][j].checkPartner();
						}
						
						if(this.map_human[i][j].is_bloqued() && this.map_human[i][j].getTargetClass() != HouseDoor.class) {
							this.map_human[i][j].unTarget();
							System.err.println("Bloquage detecte");
						}
						
						// l'humain doit trouver un chemin a suivre s'il n'en a pas
						if(!this.map_human[i][j].hasTarget()){
							
							//choix d'une cible
							
							ArrayList<Coord> path = null;
							
							if(this.map_human[i][j].isFunctional())
								path= this.path_to_the_nearest_non_human(i, j, HouseDoor.class);
							
							// si on trouve un path pour une maison
							if(path != null) {
								
								// coords de la maison
								int xHouse = path.get(path.size()-1).x;
								int yHouse = path.get(path.size()-1).y;
								HouseDoor targetDoor = (HouseDoor) this.map_non_human[xHouse][yHouse];
								
								// recherche partenaire
								Class<?extends Human> oppositeSexe = null;
								if(this.map_human[i][j].getClass() == Man.class) 
									oppositeSexe = Woman.class;
								else if(this.map_human[i][j].getClass() == Woman.class)
									oppositeSexe = Man.class;
								else {
									System.err.println("Erreur l'humain n'a pas de sexe ?");
								}
								
								ArrayList<Coord> pathToTheHouse = null;
								if(oppositeSexe != null)
									pathToTheHouse = this.path_to_the_nearest_non_human(xHouse, yHouse, oppositeSexe);
								//System.out.println(pathToTheHouse);
								
								if(pathToTheHouse != null) {
									
									ArrayList<Coord> pathReversed = new ArrayList<>();
									for(Coord c:pathToTheHouse) {
										pathReversed.add(0,c);
									}
									
									int xHuman = pathReversed.get(0).x;
									int yHuman = pathReversed.get(0).y;
									
									Human partner = this.map_human[xHuman][yHuman];
									
									if(partner.getPosX()/32 != xHuman || partner.getPosY()/32 != yHuman)
										System.err.println("Erreur coord partenaire");
									
									System.out.println("Entite sexe : " + this.map_human[i][j].getClass() + " partenaire : " + partner.getClass());
										
									partner.setPartner(this.map_human[i][j]);
									this.map_human[i][j].setPartner(partner);
									partner.unTarget();
									partner.setPath(pathReversed);
									partner.setTarget(targetDoor);
									this.nb_of_house_available --;
									
								}
								else {
									path = null;
								}
							}
							
							if(path == null) {
								ArrayList<Coord> pathWeed = this.path_to_the_nearest_non_human(i, j, Weed.class);
								ArrayList<Coord> pathCorn = this.path_to_the_nearest_non_human(i, j, Corn.class);	
								
								if(pathCorn == null) {
									path = pathWeed;
								}else if (pathWeed == null) {
									path = pathCorn;
								}else if(pathCorn.size() < pathWeed.size()) {
									path = pathCorn;
								}else {
									path = pathWeed;
								}
								
							}
							
							if(path != null){
								int xTarget = path.get(path.size()-1).x;
								int yTarget = path.get(path.size()-1).y;
								Non_human target = this.map_non_human[xTarget][yTarget];
								
								Coord firstCase = path.get(0);
								if(this.map_human[i][j].getPosX()/32 != firstCase.x || this.map_human[i][j].getPosY()/32 != firstCase.y)
									System.err.println("Erreur Path : le premiere case ne correspond pas");
								
								
								this.map_human[i][j].setPath(path);
								if(target == null)
									System.err.println("erreur path : pas de cible en bout de path");
								else {
									// marquage cible
									target.mark();
								}
								this.map_human[i][j].setTarget(target);
							}
							
							//fin choix du path
							
						}
						
						 
						
					}
				}
				
			}
		}
		
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				
				// les humains
				if( this.map_human[i][j] != null) {
					int x = i,y = j;
					
					if(this.map_human[i][j].getPosX()/32 != i || this.map_human[i][j].getPosY()/32 != j)
						System.err.println("Erreur dans le posisitionnement d'un humain");
					
					Action action = this.map_human[i][j].chose_action();
					
					// en fonction de l'action
					switch(action) {
					
					case GO_DOWN:
						y++;
						break;
					case GO_LEFT:
						x--;
						break;
					case GO_RIGHT:
						x++;
						break;
					case GO_TOP:
						y--;
						break;
					case PLANT_CORN:
						if(this.map_human[i][j].corn_ready()){
							
							System.out.println("plantage de ble");
							int dx = i, dy = j;
							switch(this.map_human[i][j].get_orientation()) {
							
							case DOWN:
								dy++;
								break;
							case LEFT:
								dx--;
								break;
							case RIGHT:
								dx++;
								break;
							case TOP:
								dy--;
								break;
								
							}
							
							this.map_buffer_non_human[dx][dy] = new Corn(dx,dy);
							this.nb_of_corn_available ++;
							
						}
						break;
					case ENTER_HOUSE:
						
						HouseDoor hd = (HouseDoor)this.map_non_human[i][j-1];
						if(hd.enterHouse(this.map_human[i][j])) {
							this.map_human[i][j].setBusy(true);
							this.map_human[i][j].setTarget(null);
							this.map_human[i][j].setPath(null);
							y--;
						}
						break;
						
					default: 
						// Pas de deplacement
						break;
					
					}
					
					if(0<=y && y<this.height && 0<=x && x<this.width && this.map_buffer_human[x][y] == null) { 
						
						// deplacement
						
						if(!this.map_human[i][j].isEnteringHouse())
							this.map_buffer_human[x][y] = this.map_human[i][j];
						grid_d[i][j] = true;
						
					}
					else { 
						
						// colision : annuler un ou plusieurs deplacements
						
						Human h = this.map_human[i][j];
						Human tmp_h;
						x = i; y = j;
						boolean colision_nullified = false;
						while(!colision_nullified){ // on informe les entites qui se suivent qu'un element bloque
							
							if(this.map_buffer_human[x][y] == null){ // on a fini de rembobiner
								this.map_buffer_human[x][y] = h;
								grid_d[x][y] = false;
								colision_nullified = true;
							}
							else { // on rembobine
								tmp_h = this.map_buffer_human[x][y];
								this.map_buffer_human[x][y] = h;
								h = tmp_h;
								grid_d[x][y] = false;
								x = tmp_h.getPosX()/32;
								y = tmp_h.getPosY()/32;
							}
							
						}
						
						// deplacements annules
						
					}
					
				}
			}
		}
		
		
		// les non humains
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				
				if(this.map_non_human[i][j] != null) {
					
					this.map_non_human[i][j].clock_tic();
					if(this.map_non_human[i][j].isDying()) {
						
						Class<? extends Non_human> instance = this.map_non_human[i][j].getClass();
						
						if(instance == Corn.class)
							this.nb_of_corn_available --;
						
					}
					else {

						Class<? extends Non_human> type = this.map_non_human[i][j].getClass();
						boolean spread = this.map_non_human[i][j].spread();
						if(type == Corn.class) {
							this.map_buffer_non_human[i][j] = this.map_non_human[i][j]; // le ble ne bouge pas
						}
						else if(type == House.class ) 
							this.map_buffer_non_human[i][j] = this.map_non_human[i][j]; // les maisons non plus
						
						else if(type == HouseDoor.class) {
							this.map_buffer_non_human[i][j] = this.map_non_human[i][j];
							if(j+1 < this.height) {
								if(this.map_buffer_human[i][j+1] == null) {
									
									HouseDoor h = (HouseDoor)this.map_non_human[i][j];
									// on fait sortir un humain
									Human hu = h.exitHouse();
									
									if(hu != null) {
										
										this.map_buffer_human[i][j+1] = hu; 
										hu.exit_house();
										hu.animer(duration);
										
										if(!h.isMarked())
											this.nb_of_house_available ++;
									}
									
								}
							}
						}
						else{
							
							this.map_buffer_non_human[i][j] = this.map_non_human[i][j];
							if(spread) {
							
								if(this.isInBound(i + 1, j) && (this.map_non_human[i + 1][j] == null || this.map_non_human[i + 1][j].getClass() == Corn.class)) {
									if(this.map_buffer_non_human[i + 1][j] == null || !(this.map_buffer_non_human[i + 1][j] instanceof Weed) ) {
										this.map_buffer_non_human[i + 1][j] = new Weed(i + 1,j);
										this.nb_of_herb_available ++;
									}
									if(this.map_non_human[i + 1][j] != null)
										this.map_non_human[i + 1][j].destroy(); // detruire le ble
								}
								if(this.isInBound(i - 1, j) && (this.map_non_human[i - 1][j] == null || this.map_non_human[i - 1][j].getClass() == Corn.class)) {
									if(this.map_buffer_non_human[i - 1][j] == null || !(this.map_buffer_non_human[i - 1][j] instanceof Weed) ) {
										this.map_buffer_non_human[i - 1][j] = new Weed(i - 1,j);
										this.nb_of_herb_available ++;
									}
									if(this.map_non_human[i - 1][j] != null)
										this.map_non_human[i - 1][j].destroy(); // detruire le ble
								}
								if(this.isInBound(i, j + 1) && (this.map_non_human[i][j + 1] == null || this.map_non_human[i][j + 1].getClass() == Corn.class)) {
									if(this.map_buffer_non_human[i][j + 1] == null || !(this.map_buffer_non_human[i][j + 1] instanceof Weed) ) {
										this.map_buffer_non_human[i][j + 1] = new Weed(i,j + 1);
										this.nb_of_herb_available ++;
									}
									if(this.map_non_human[i][j + 1] != null)
										this.map_non_human[i][j + 1].destroy(); // detruire le ble
								}
								if(this.isInBound(i, j - 1) && (this.map_non_human[i][j - 1] == null || this.map_non_human[i][j - 1].getClass() == Corn.class)) {
									if(this.map_buffer_non_human[i][j - 1] == null || !(this.map_buffer_non_human[i][j - 1] instanceof Weed )) {
										this.map_buffer_non_human[i][j - 1] = new Weed(i,j - 1);
										this.nb_of_herb_available ++;
									}
									if(this.map_non_human[i][j - 1] != null)
										this.map_non_human[i][j - 1].destroy(); // detruire le ble
								}
								
							}
							
						}
							
					}
					
				}
				
			}
		}
		
		// lancer les animations
		
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if(this.map_human[i][j] != null) {
					
					// l'animation peut �tre lancee
					if(grid_d[i][j]) {
						this.map_human[i][j].animer(duration); // lancer les animations
						//this.map_human[i][j].nextStepOnPath();
						this.map_human[i][j].reset_time_bloqued();
					}
					
					else {
						this.map_human[i][j].increase_time_bloqued();
					}
					
				}
				
				// maj non humains
				this.map_non_human[i][j] = this.map_buffer_non_human[i][j];
				
			}
		}
		
		try {
			Thread.sleep(duration/2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// maj humains
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				
				this.map_human[i][j] = this.map_buffer_human[i][j];
				
			}
		}
		
		try {
			Thread.sleep(duration/2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// fin animation humains
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if(this.map_buffer_human[i][j] != null) {
					this.map_buffer_human[i][j].wait_end_of_animation(); // attendre la fin des animations
				}
			}
		}
		
		this.time ++;
		
	} // fin de next turn
	
	public class Coord{ // coordonnees d'une case
		
		private int x;
		private int y;
		public Coord(int x,int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public String toString() {
			return "(" + x + ",  " + y + ")";
		}
		
	}
	
	//contient : coordonnees d'une case et son poid, la case precedemment traversee. 
	private class Link{ 
		
		// lien vers la case precedente du chemin
		private Link previous;
		
		// coordonnées de la case
		private Coord coord;
		
		public Link(Link previous, int x, int y) {
			this.previous = previous;
			this.coord = new Coord(x,y);
		}
		
		public Link getPrevious() {
			return previous;
		}
		
		public Coord getCoord() {
			return coord;
		}
		
	}

	// methode iterative : donne le chemin a parcourir pour atteindre la plus proche entite souhaitee
	public ArrayList<Coord> path_to_the_nearest_non_human(int x,int y, Class<? extends Entity> entity_type) {
		
		if(entity_type == Corn.class && this.nb_of_corn_available == 0) {
			return null; 
		}
		
		if(entity_type == HouseDoor.class && this.nb_of_house_available == 0) {
			return null; 
		}
		
		if(entity_type == Weed.class && this.nb_of_herb_available == 0) {
			return null;
		}
		
		// creation d'un tableau de boolean pour les cases mouillees
		boolean tabWet[][] = new boolean[this.width][this.height];
		for(int i = 0 ; i< this.width; i ++) {
			for(int j = 0; j < this.height; j ++)
				tabWet[i][j] = false;
		}
		
		// Liste stoquant les possibilites pour le parcours en largeur
		ArrayList<Link> eventualities = new ArrayList<>();
		
		// mettre la case de coordonnees x,y dans la liste
		Link init = new Link(null, x, y);
		Link previous = null;
		eventualities.add(init);
		
		/*Recuperation des coordonees de la case initiale*/
		int xaux, yaux;
		Coord aux;
		
		// tant qu'on a pas trouvé l'entité souhaité
		int i = 0;
		boolean found = false;
		while(i < eventualities.size() && !found) {
			
			/*Recuperation de la prochaine case qui devient "previous" et de ses coordonees
			 *Au tour 1 c'est la case initiale qui est récupérée
			 */
			previous = eventualities.get(i);
			aux = previous.getCoord();
			xaux = aux.getX();
			yaux = aux.getY();
			
			// chercher dans les cases à coté			
			if(isInBound(xaux+1,yaux) && isEntity(xaux+1,yaux,entity_type) && isAvailable(xaux+1,yaux,entity_type)){
				found = true;
				eventualities.add(new Link(previous, xaux+1, yaux));
			}
			else if(isInBound(xaux,yaux+1) && isEntity(xaux,yaux+1,entity_type) && isAvailable(xaux,yaux+1,entity_type)){
				found = true;
				eventualities.add(new Link(previous, xaux, yaux+1));
			}
			else if(isInBound(xaux-1,yaux) && isEntity(xaux-1,yaux,entity_type) && isAvailable(xaux-1,yaux,entity_type)){
				found = true;
				eventualities.add(new Link(previous, xaux-1, yaux));
			}
			else if(isInBound(xaux,yaux-1) && isEntity(xaux,yaux-1,entity_type) && isAvailable(xaux,yaux-1,entity_type)){
				found = true;
				eventualities.add(new Link(previous, xaux, yaux-1));
			}
			else{// si on a rien trouvé: rajouter les cases adjacentes dans le tableau 
				
				//randomiser les paths
				int ra = (int) (Math.random()*8);
				int dx1, dx2, dx3, dx4;
				int dy1, dy2, dy3, dy4;
				
				dx1 = 1; dx2 = -1; dx3 = 0; dx4 = 0;
				dy1 = 0; dy2 = 0; dy3 = 1; dy4 = -1;
				
				switch(ra) {
				case 0: 
					dx1 = 1; dx2 = -1; dx3 = 0; dx4 = 0;
					dy1 = 0; dy2 = 0; dy3 = 1; dy4 = -1;
					break;
				case 1:
					dx1 = 0; dx2 = 1; dx3 = -1; dx4 = 0;
					dy1 = -1; dy2 = 0; dy3 = 0; dy4 = 1;
					break;
				case 2: 
					dx1 = 0; dx2 = 0; dx3 = 1; dx4 = -1;
					dy1 = 1; dy2 = -1; dy3 = 0; dy4 = 0;
					break;
				case 3: 
					dx1 = -1; dx2 = 0; dx3 = 0; dx4 = 1;
					dy1 = 0; dy2 = 1; dy3 = -1; dy4 = 0;
					break;
				case 4: 
					dx1 = -1; dx2 = 1; dx3 = 0; dx4 = 0;
					dy1 = 0; dy2 = 0; dy3 = -1; dy4 = 1;
					break;
				case 5: 
					dx1 = 0; dx2 = -1; dx3 = 1; dx4 = 0;
					dy1 = 1; dy2 = 0; dy3 = 0; dy4 = -1;
					break;
				case 6: 
					dx1 = 0; dx2 = 0; dx3 = -1; dx4 = 1;
					dy1 = -1; dy2 = 1; dy3 = 0; dy4 = 0;
					break;
				case 7: 
					dx1 = 1; dx2 = 0; dx3 = 0; dx4 = -1;
					dy1 = 0; dy2 = -1; dy3 = 1; dy4 = 0;
					break;
				}
				
				/*On ajoute les cases adjacentes dans la liste et on mouille les cases*/
				if(isInBound(xaux+ dx1,yaux + dy1) && !tabWet[xaux + dx1][yaux + dy1] && isNotSolid(xaux + dx1 ,yaux + dy1)) {
					eventualities.add(new Link(previous, xaux+dx1, yaux+ dy1));
					tabWet[xaux+dx1][yaux+dy1] = true;
				}
				if(isInBound(xaux+ dx2,yaux + dy2) && !tabWet[xaux + dx2][yaux + dy2] && isNotSolid(xaux + dx2 ,yaux + dy2)) {
					eventualities.add(new Link(previous, xaux+dx2, yaux+ dy2));
					tabWet[xaux+dx2][yaux+dy2] = true;
				}
				if(isInBound(xaux+ dx3,yaux + dy3) && !tabWet[xaux + dx3][yaux + dy3] && isNotSolid(xaux + dx3 ,yaux + dy3)) {
					eventualities.add(new Link(previous, xaux+dx3, yaux+ dy3));
					tabWet[xaux+dx3][yaux+dy3] = true;
				}
				if(isInBound(xaux+ dx4,yaux + dy4) && !tabWet[xaux + dx4][yaux + dy4] && isNotSolid(xaux + dx4 ,yaux + dy4)) {
					eventualities.add(new Link(previous, xaux+dx4, yaux+ dy4));
					tabWet[xaux+dx4][yaux+dy4] = true;
				}
				
			}
			
			i++;
		} 
		
		if(!found) {
			return null;
		}
		else {
		
			// on obtient la case d'arrivee et une liste a trier
			
			/*Recuperation de la case cible*/
			Link target = eventualities.get(eventualities.size()-1);
			
			// obtenir le path jusqu'a la case souhaitee
			ArrayList<Coord> path = new ArrayList<>();
			while(target != null){
				path.add(0,target.getCoord());
				target = target.getPrevious();
			}
			
			return path;
			
		}
		
	}
	
}
