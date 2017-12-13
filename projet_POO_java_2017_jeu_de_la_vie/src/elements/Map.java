package elements;

import java.awt.Image;
import java.awt.Rectangle;

import elements.Human.Orientation;

public class Map{

	private Cell[][] map_cells;
	
	// a afficher
	private Non_human[][] map_non_human;
	private Human[][] map_human;
	
	// buffers
	private Non_human[][] map_buffer_non_human;
	private Human[][] map_buffer_human;
	
	private int width;
	private int height;
	
	public Map(int width, int height){
		
		this.width = width;
		this.height = height;
		
		this.map_cells = new Cell[width][height];
		for(int i = 0; i < width; i ++) {
			for(int j = 0; j < height; j ++)
				map_cells[i][j] = new Ground(i,j);
		}
		
		this.map_human = new Human[width][height];
		for(int i = 5; i < width-5; i ++) {
			for(int j = 5; j < height-5; j ++)
				map_human[i][j] = null;
		}
		
		this.map_non_human = new Non_human[width][height];
		for(int i = 5; i < width-5; i ++) {
			for(int j = 5; j < height-5; j ++)
				map_non_human[i][j] = new Corn(i,j);
		}
		this.map_buffer_human = new Human[width][height];
		this.map_buffer_non_human = new Non_human[width][height];
		
		map_human[10][10] = new Man(10,10);
		map_human[11][10] = new Woman(11,10);
		map_human[10][11] = new Man(10,11);
		map_human[11][11] = new Woman(11,11);
		map_human[10][12] = new Man(10,12);
		map_human[11][12] = new Woman(11,12);
		map_human[10][13] = new Man(10,13);
		map_human[11][13] = new Woman(11,13);
		map_human[10][14] = new Man(10,14);
		map_human[11][14] = new Woman(11,14);
		
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
	
	public Cell get_cell(int x, int y){
		
		if(0 <= x && x < this.width && 0 <= y && y < this.height)
			return this.map_cells[x][y];
		
		return null;
		
	}
	
	public void set_cell(int x, int y, Cell c){
		
		this.map_cells[x][y] = c;
		
	}
	
	public Image get_cell_img_at(int x, int y){
		
		if(0 <= x && x < this.width && 0 <= y && y < this.height)
			return this.map_cells[x][y].get_img();
		
		return null;
		
	}
	
	public Rectangle get_rectangle_of_cell(int x, int y) {
		return this.map_cells[x][y].selection_sprite();
	}
	
	public void next_turn() {
		
		int grid[][] =  new int[this.width][this.height];
		boolean grid_d[][] = new boolean[this.width][this.height];
		
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
			
				if(this.map_cells[i][j].isTraversable()){
					if(this.map_non_human[i][j] == null )
						grid[i][j] = 0; // de l'air
					else if(this.map_non_human[i][j] instanceof Corn)
						grid[i][j] = 1; // du blé
					else if(this.map_non_human[i][j] instanceof Weed)
						grid[i][j] = 2; // de l'herbe
					else
						grid[i][j] = 3; // une maison
				}
				else {
					grid[i][j] = -1; // ne peut pas passer à travers
				}
				
				grid_d[i][j] = false; // quelles entités se déplacent
				this.map_buffer_human[i][j] = null; // netoyer le buffer
				
			}
		}
		
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if( this.map_human[i][j] != null) {
					int x = i,y = j;
					//choisir une direction
					//this.map_human[i][j].clock_tic();
					if(this.map_human[i][j].isDying()) {
						
						this.map_human[i][j] = null; // TUER
						
					}
					else { // deplacer (ou pas)
						
						Orientation direction = this.map_human[i][j].chose_direction(grid);
						
						//verifier s'il y a une colision
						switch(direction) {
						
						case DOWN:
							y++;
							break;
						case LEFT:
							x--;
							break;
						case RIGHT:
							x++;
							break;
						case TOP:
							y--;
							break;
						default: // Comment ?
							break;
						
						}
						
						if(i != x || j != y) { // si l'entite souhaite se déplacer
							if(0<=y&&y<this.height&&0<=x&&x<this.width && this.map_buffer_human[x][y] == null) { // deplacement
								this.map_buffer_human[x][y] = this.map_human[i][j];
								grid_d[i][j] = true;
							}
							else { // colision : annuler un ou plusieurs deplacements
								Human h = this.map_human[i][j];
								Human tmp_h;
								x = i; y = j;
								boolean colision_nullified = false;
								while(!colision_nullified){ // on informe les entitées qui se suivent qu'un élément bloque
									
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
								
							} // deplacements annulés
							
						}
						
					}
				}
				
			}
		}
		
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if(this.map_human[i][j] != null) {
					if(grid_d[i][j]) {
						this.map_human[i][j].animer();
					}
				}
				
				this.map_human[i][j] = this.map_buffer_human[i][j];
				
			}
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if(this.map_human[i][j] != null) {
					this.map_human[i][j].reply();
					
				}
			}
		}
		
	}
	
}
