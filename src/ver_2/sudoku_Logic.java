package ver_2;

import java.awt.Point;
import java.util.*;

public class sudoku_Logic {

	private static Random rm = new Random();
	
	private static int[][] answer;
	
	
	static int getRandomnum(){
		return rm.nextInt(9);
	}
	
	private static Point getRandomPoint(){
		int r = rm.nextInt(9);
		int c = rm.nextInt(9);	
		return new Point(r,c);	
	}
	
	public static void getcell(int [][] out) {
		for (int i = 0; i<9 ; i++){
			for (int n = 0; n<9; n++){
				out[i][n] = answer[i][n];
			}
		}
	}

	public static boolean solve(int i, int j, int[][] cells) {
		if (i == 9) {
			i = 0;
			if (++j == 9)
				return true;
		}
		if (cells[i][j] != 0) // skip filled cells
			return solve(i + 1, j, cells);

		for (int val = 1; val <= 9; ++val) {
			if (legal(i, j, val, cells)) {
				cells[i][j] = val;
				if (solve(i + 1, j, cells))
					return true;
			}
		}
		return false;
	}

	private static boolean create(int i, int j, int[][] cells) {
		if (i == 9) {
			i = 0;
			if (++j == 9)
				return true;
		}
		if (cells[i][j] != 0) // skip filled cells
			return create(i + 1, j, cells);

		for (int val = 1; val <= 9; ++val) {
			if (legal(i, j, val, cells)) {
				cells[i][j] = val;
				if (create(i + 1, j, cells))
					return true;
			}
		}
		cells[i][j] = 0; // reset on backtrack
		return false;
	}
	
	public static boolean legal(int i, int j, int val, int[][] cells) {
		for (int k = 0; k < 9; ++k)
			// row
			if (val == cells[k][j])
				return false;

		for (int k = 0; k < 9; ++k)
			// col
			if (val == cells[i][k])
				return false;

		int boxRowOffset = (i / 3) * 3;
		int boxColOffset = (j / 3) * 3;
		for (int k = 0; k < 3; ++k)
			// box
			for (int m = 0; m < 3; ++m)
				if (val == cells[boxRowOffset + k][boxColOffset + m])
					return false;

		return true; // no violations, so it's legal
	}
	
	public static void newgame(){
		int randomr = getRandomnum();
		int randomc = getRandomnum();
		int num = getRandomnum()+1;
		int [][] out = new int [9][9];
		out [randomr][randomc] = num;
		create(0,0,out);
		answer = out.clone();
	}

	public static void removenum(int difficulty){
		int total;
		if (difficulty == 1){
			total = 5;
		}
		else{
			total = (difficulty-1) * 8;
		}
		for (int count = 0; count < total; count ++){
			Point p = getRandomPoint();
			if (answer[p.x][p.y]!=0){
				answer[p.x][p.y] = 0;
			}
			else{
				count --;
			}
		}
	}
	
}
