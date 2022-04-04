package game;

public class Cell {

	public static final int BLANK = 0;
	public static final int HIT = 1;
	public static final int MISS = 2;
	public static final int RADAR = 3;

	private int state;
	private Ship ship;

	public int shot() {
		if (hasShip()) {
			state = HIT;
			ship.hit();
		} else
			state = MISS;

		return state;
	}

	public void putRadar() {
		state = RADAR;
	}

	public boolean hasShip() {
		return ship != null;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public int getState() {
		return state;
	}

	public Ship getShip() {
		return ship;
	}

}
