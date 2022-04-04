package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Cell;
import game.Game;

public class BoardPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel[][] panels;
	private JLabel[][] cells;

	public BoardPanel() {
		setPreferredSize(new Dimension(32 * Game.BOARD_SIZE, 32 * Game.BOARD_SIZE)); // Board size is 10 * 10
		setLayout(new GridLayout(Game.BOARD_SIZE, Game.BOARD_SIZE));

		// init cells
		cells = new JLabel[Game.BOARD_SIZE][Game.BOARD_SIZE];
		panels = new JPanel[Game.BOARD_SIZE][Game.BOARD_SIZE];
		for (int i = 0; i < cells.length; i++)
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = new JLabel(" ");
				panels[i][j] = new JPanel();
				
				panels[i][j].setBackground(Color.GRAY);
				panels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				panels[i][j].setPreferredSize(new Dimension(32, 32));
				panels[i][j].add(cells[i][j]);

				add(panels[i][j]); // draw them boxes
			}

	}

	public void set(Cell[][] model) {
		for (int i = 0; i < model.length; i++)
			for (int j = 0; j < model[i].length; j++) {
				int state = model[i][j].getState(); // getState sends back state of tiles

				switch (state) {
				case Cell.BLANK:
					cells[i][j].setText("");
					break;
				case Cell.HIT:
					cells[i][j].setText("H");
					break;
				case Cell.MISS:
					cells[i][j].setText("M");
					break;
				case Cell.RADAR:
					cells[i][j].setText("o");
					colorRadarCells(model, i, j);
					break;
				}
				
				//for debugging,colors the ships cells
				/*
				 * if(model[i][j].hasShip()) { int type = model[i][j].getShip().getType();
				 * 
				 * switch(type) { case Ship.AIRCRAFT_CARRIER:
				 * panels[i][j].setBackground(Color.RED); break; case Ship.BATTLESHIP:
				 * panels[i][j].setBackground(Color.BLUE); break; case Ship.SUBMARINE:
				 * panels[i][j].setBackground(Color.GREEN); break; case Ship.DESTROYER:
				 * panels[i][j].setBackground(Color.YELLOW); break; case Ship.PATROL_BOAT:
				 * panels[i][j].setBackground(Color.CYAN); break; } } else
				 * panels[i][j].setBackground(Color.GRAY);
				 */
			}
	}

	public void setClickListener(ActionListener al) {
		for (int i = 0; i < cells.length; i++)
			for (int j = 0; j < cells[i].length; j++) {
				final int row = i, col = j;
				panels[i][j].addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent e) {
					}

					@Override
					public void mousePressed(MouseEvent e) {
						al.actionPerformed(new ActionEvent(cells[row][col], e.getButton(), row + "-" + col));
					}

					@Override
					public void mouseReleased(MouseEvent e) {
					}

					@Override
					public void mouseEntered(MouseEvent e) {
					}

					@Override
					public void mouseExited(MouseEvent e) {
					}

				});
			}

	}
	
	public void clearColors() {
		for (int i = 0; i < panels.length; i++)
			for (int j = 0; j < panels[i].length; j++) 
				panels[i][j].setBackground(Color.GRAY);
	}

	private void colorRadarCells(Cell[][] model, int row, int col) {
		// start row,col & end row,col
		int sr = row - 1;
		int sc = col - 1;
		int er = row + 1;
		int ec = col + 1;

		for (int i = sr; i <= er; i++)
			for (int j = sc; j <= ec; j++)
				if (i >= 0 && i < cells.length && j >= 0 && j < cells[0].length) {
					panels[i][j].setBackground(model[i][j].hasShip() ? Color.RED : Color.YELLOW);
				}
	}

}
