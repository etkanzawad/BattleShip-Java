package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.Cell;
import java.awt.event.MouseEvent;
import game.Game;
import game.GameObserver;
import game.Ship;

public class Controller implements GameObserver {

	private Game game;
	private JFrame frame;
	private BoardPanel boardPanel;
	private JLabel statusLabel, scoreLabel, radarLabel;

	public Controller() {
		game = new Game(this);  // OOP of game class

		frame = new JFrame("Battleship Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		boardPanel = new BoardPanel(); // GUI class
		statusLabel = new JLabel("Status: ");
		scoreLabel = new JLabel("Score: 0");
		radarLabel = new JLabel("Num Radar: " + Game.MAX_RADARS);

		boardPanel.setClickListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String coordinateStr = e.getActionCommand(); // AP
				int row = Integer.parseInt(coordinateStr.split("-")[0]); // multiple strings
				int col = Integer.parseInt(coordinateStr.split("-")[1]);

				if (e.getID() == MouseEvent.BUTTON1) {
						game.shot(row, col); // checks shot

						int state = game.getBoard()[row][col].getState();
						Ship ship = game.getBoard()[row][col].getShip();

						if (ship != null && ship.isSunk()) // verify ship status
							statusLabel.setText("Status: " + "You sank my : " + ship.toString());
						else if (state == Cell.HIT)
							statusLabel.setText("Status: My ship was hit!");
						else if (state == Cell.MISS)
							statusLabel.setText("Status: You missed!");
				} else if (e.getID() == MouseEvent.BUTTON3) { // MB 3 places the radar
					game.placeRadar(row, col);
				}
			}

		});

		JPanel topContainer = new JPanel(new GridLayout(1, 2));
		topContainer.add(scoreLabel);
		topContainer.add(radarLabel);

		frame.add(boardPanel, BorderLayout.CENTER);
		frame.add(topContainer, BorderLayout.NORTH);
		frame.add(statusLabel, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void update() { // Observer class
		boardPanel.set(game.getBoard());
		scoreLabel.setText("Score: " + game.getScore());
		radarLabel.setText("Num Radar: " + game.getNumRadars());
	}

	@Override
	public void notifyGameEnded() { // Only after all ships are sunk
		int option = JOptionPane.showConfirmDialog(frame,
				"Game Ended\n.Your Score: " + game.getScore() + "\nWould you like to play again");
		if (option == 0) {
			game.restart();
			boardPanel.clearColors();
			update();
		} else
			System.exit(0);
	}

	public static void main(String args[]) {
		new Controller();
	}
}
