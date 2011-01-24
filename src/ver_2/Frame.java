package ver_2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Stack;

import javax.swing.JFrame;

public class Frame extends JFrame implements Serializable {

	private static final long serialVersionUID = 3071347790808488913L;
	boolean gamestarted;
	boolean showhint;
	private static final int COL = 9;
	private static final int ROW = 9;
	private static final int CELL = 50;
	Stack<Point> filledup = new Stack<Point>();
	Stack<Redo> redo = new Stack<Redo>();
	int[][] puzzle;
	int[][] answer;

	public Frame(String string) {
		super(string);
		this.getContentPane().setBackground(Color.WHITE);
		gamestarted = false;
		showhint = false;
		puzzle = new int[COL][ROW];
		answer = new int[COL][ROW];
	}

	public boolean update() {
		if (Arrays.deepEquals(answer, puzzle)) {
			return true;
		} else {
			return false;
		}
	}

	// Paint the board
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paint(g);
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.GRAY);
		g2d.drawRect(150, 80, 450, 450);
		for (int r = 0; r < ROW; r++) {
			for (int c = 0; c < COL; c++) {
				if (r % 3 == 0 && c % 3 == 0) {
					g2d.setStroke(new BasicStroke(3));
					g.drawRect(150 + r * CELL, 80 + c * CELL, 3 * CELL,
							3 * CELL);
				} else {
					g2d.setStroke(new BasicStroke(1));
					g2d.drawRect(150 + r * CELL, 80 + c * CELL, CELL, CELL);
				}
			}
		}
		if (gamestarted) {
			for (int r = 0; r < ROW; r++) {
				for (int c = 0; c < COL; c++) {

					if (puzzle[r][c] != 0) {
						if (filledup.contains(new Point(r, c))) {
							g2d.setFont(new Font("Courier New", Font.BOLD, 40));
							g2d.setColor(Color.RED);
							g2d.drawString(String.valueOf(puzzle[r][c]), 150
									+ r * CELL + 15, 80 + c * CELL + 35);
						} else {
							g2d.setFont(new Font("Courier New", Font.BOLD, 40));
							g2d.setColor(Color.BLUE);
							g2d.drawString(String.valueOf(puzzle[r][c]), 150
									+ r * CELL + 15, 80 + c * CELL + 35);
						}
					}
				}
			}
		}
	}
}
