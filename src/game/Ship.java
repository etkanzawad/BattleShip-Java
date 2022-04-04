package game;

public class Ship {
	public static final int AIRCRAFT_CARRIER = 0;
	public static final int BATTLESHIP = 1;
	public static final int SUBMARINE = 2;
	public static final int DESTROYER = 3;
	public static final int PATROL_BOAT = 4;

	
	private int type;
	private int size;
	private int numHits;
	
	public Ship(int type) {
		this.type = type;
		init();
	}
	
	public void hit() {
		numHits++;
	}
	
	public boolean isSunk() {
		return numHits >= size;
	}
	
	private void init() {
		switch(type) {
		case AIRCRAFT_CARRIER:
			size = 5;
			break;
		case BATTLESHIP:
			size = 4;
			break;
		case SUBMARINE:
			size = 3;
			break;
		case DESTROYER:
			size = 3;
			break;
		case PATROL_BOAT:
			size = 2;
			break;
		}
	}

	public int getSize() {
		return size;
	}
	
	@Override
	public String toString() {
		switch(type) {
		case AIRCRAFT_CARRIER:
			return "Aircraft carrier";
		case BATTLESHIP:
			return "Battleship";
		case SUBMARINE:
			return "Submarine";
		case DESTROYER:
			return "Destroyer";
		default:
			return "Patrol Boat";
		}
	}

	public int getType() {
		return type;
	}
}
