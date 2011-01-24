package ver_2;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Monitor extends MouseAdapter {

	private boolean withinboard(int x, int y) {
		return (x >= 0 && x <= 8) && (y >= 0 && y <= 8);
	}

	// based on the mouse position to get the board location
	@Override
	public void mouseClicked(MouseEvent e) {
		sudoku_GUI.game.redo.clear();
		int x = (int) Math.floor((e.getX() - 200) / 50.0) + 1;
		int y = (int) Math.floor((e.getY() - 130) / 50.0) + 1;
		if (withinboard(x, y)) {
			if (sudoku_GUI.game.puzzle[x][y] == 0) {
				int input = sudoku_GUI.getInt("Your answer ?");
				if (sudoku_GUI.game.showhint){
					if (sudoku_Logic.legal(x, y, input, sudoku_GUI.game.puzzle)) {
						sudoku_GUI.game.puzzle[x][y] = input;
						sudoku_GUI.game.filledup.push(new Point(x, y));
						javax.swing.SwingUtilities.invokeLater(
								new Runnable(){
									public void run() {
										if (sudoku_GUI.game.update()){
											sudoku_MusicPlayer.stop();
											sudoku_GUI.soundcontrol.setText("Play");
											sudoku_MusicPlayer.playmusic("end.wav");
											sudoku_GUI.display("Congratulations, you have solved the puzzle.", "HELP");
											sudoku_GUI.resetbuttons();
											sudoku_GUI.start.setEnabled(false);
											sudoku_GUI.game.gamestarted=false;
											sudoku_GUI.game.repaint();
										}
									}
								}
						);
					} else {
						sudoku_GUI.display("Sorry, invalid answer.", "Warning");
					}
				}
				else{
					sudoku_GUI.game.puzzle[x][y] = input;
					sudoku_GUI.game.filledup.push(new Point(x, y));
					javax.swing.SwingUtilities.invokeLater(
							new Runnable(){
								public void run() {
									if (sudoku_GUI.game.update()){
										sudoku_MusicPlayer.stop();
										sudoku_GUI.soundcontrol.setText("Play");
										sudoku_MusicPlayer.playmusic("end.wav");
										sudoku_GUI.display("Congratulations, you have solved the puzzle.", "HELP");
										sudoku_GUI.resetbuttons();
										sudoku_GUI.start.setEnabled(false);
										sudoku_GUI.game.gamestarted=false;
										sudoku_GUI.game.repaint();
									}
								}
							}
					);
				}
				sudoku_GUI.game.repaint();
			}
		}
	}
}
