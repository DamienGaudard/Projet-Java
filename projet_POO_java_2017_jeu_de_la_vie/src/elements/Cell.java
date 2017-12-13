package elements;

public abstract class Cell extends Displayable{

	public Cell(int posX, int posY) {
		super(posX, posY);
	} // case
	
	public abstract boolean isTraversable();

}
