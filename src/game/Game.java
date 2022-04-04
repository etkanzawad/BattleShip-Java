package game;

import java.util.Random;

public class Game {

	public static final int BOARD_SIZE = 10;
	public static final int NUM_SHIPS = 5;
	public static final int MAX_RADARS = 4;

	private Cell[][] cells;
	private Ship[] ships;
	private int score;
	private int numRadars = 4;
	private GameObserver observer;

	public Game(GameObserver o) {
		observer = o;
		init();
	}

	public boolean isCellBlank(int row, int col) {
		return cells[row][col].getState() == Cell.BLANK;
	}

	public void shot(int row, int col) {
		if (!isCellBlank(row, col) && cells[row][col].getState() != Cell.RADAR)
			return;

		// shot the cell
		int state = cells[row][col].shot();

		// update score
		if (state == Cell.HIT)
			score++;
		else
			score--;
		if (cells[row][col].hasShip() && cells[row][col].getShip().isSunk()) {
			score += cells[row][col].getShip().getSize() * 2;
		}

		// update observer
		observer.update();

		// check if all ships sunk, then end game
		boolean allShipsSunk = true;
		for (int i = 0; i < ships.length; i++)
			if (!ships[i].isSunk()) {
				allShipsSunk = false;
				break;
			}
		if (allShipsSunk)
			observer.notifyGameEnded();
	}

	public void placeRadar(int row, int col) { // radar
		if (cells[row][col].getState() != Cell.BLANK || numRadars <= 0)
			return;

		cells[row][col].putRadar();
		numRadars--;
		observer.update();
	}

	public void restart() {
		score = 0;
		numRadars = MAX_RADARS;
		init();
	}

	private void init() {
		// cells
		cells = new Cell[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < cells.length; i++)
			for (int j = 0; j < cells[i].length; j++)
				cells[i][j] = new Cell();

		// ships
		ships = new Ship[NUM_SHIPS];
		for (int i = 0; i < NUM_SHIPS; i++)
			ships[i] = new Ship(i);

		// randomly place ships on board
		randomPlaceShips();
	}

	private void randomPlaceShips() {
		Random random = new Random();
		// place 3 ships horizontally
		for (int i = 0; i < 3; i++) {
			int col = -1;
			int row = -1;
			int size = ships[i].getSize();
			boolean noShipsBetween = true;

			// choose random valid coordinate to place ship horizontally
			do {
				col = random.nextInt(BOARD_SIZE);
				row = random.nextInt(BOARD_SIZE);
				
				noShipsBetween = true;
				for (int k = 0; k < size; k++)
					if (col + k >= BOARD_SIZE || cells[row][col + k].hasShip()) {
						noShipsBetween = false;
						break;
					}
				
			} while (cells[row][col].hasShip() || col + size >= BOARD_SIZE || !noShipsBetween);

			// place ship
			for (int j = 0; j < size; j++)
				cells[row][col + j].setShip(ships[i]);

		}

		// place 2 ships vertically
		for (int i = 3; i < NUM_SHIPS; i++) {
			int col = -1;
			int row = -1;
			int size = ships[i].getSize();
			boolean noShipsBetween = true;
			// choose random valid coordinate to place ship horizontally
			do {
				col = random.nextInt(BOARD_SIZE);
				row = random.nextInt(BOARD_SIZE);

				noShipsBetween = true;
				for (int k = 0; k < size; k++)
					if (row + k >= BOARD_SIZE || cells[row + k][col].hasShip()) {
						noShipsBetween = false;
						break;
					}

			} while (cells[row][col].hasShip() || row + size >= BOARD_SIZE || !noShipsBetween);

			// place ship
			for (int j = 0; j < size; j++)
				cells[row + j][col].setShip(ships[i]);

		}
	}

	public Cell[][] getBoard() {
		return cells;
	}

	public int getScore() {
		return score;
	}

	public int getNumRadars() {
		return numRadars;
	}
}
