package ver_2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class sudoku_GUI implements ActionListener {

	private static JMenuBar menu;
	static Frame game;
	static JButton start;
	static JButton undo;
	static JButton redo;
	private static JButton submit;
	private static JButton showhint;
	static JButton soundcontrol;
	private static JFileChooser fc;
	private static Monitor boardmonitor = new Monitor();

	// Method to createMenuBar
	private JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu file;
		JMenu about;
		String[] filemenuname = { "New Random Game", "New Specific Game",
				"Save", "Load", "Exit" };

		menuBar = new JMenuBar();
		// File Menu
		file = new JMenu("File");
		file.getAccessibleContext().setAccessibleDescription("File Menu");
		file.setMnemonic(KeyEvent.VK_F);
		for (String s : filemenuname) {
			JMenuItem m = new JMenuItem(s);
			m.addActionListener(this);
			file.add(m);
			if (s == "Load") {
				file.addSeparator();
			}
		}
		menuBar.add(file);
		// About Menu
		about = new JMenu("About");
		about.getAccessibleContext().setAccessibleDescription("About Menu");
		about.setMnemonic(KeyEvent.VK_A);
		JMenuItem help = new JMenuItem("Help");
		help.addActionListener(this);
		about.add(help);
		JMenuItem aboutitem = new JMenuItem("About SUDOKU");
		aboutitem.addActionListener(this);
		about.add(aboutitem);
		menuBar.add(about);
		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) (e.getSource());
		if (source.getText() == "New Random Game") {
			sudoku_MusicPlayer.playmusic("start.wav");
			int difficulty = sudoku_Logic.getRandomnum()+1;
			sudoku_Logic.newgame();
			sudoku_Logic.getcell(game.answer);
			sudoku_Logic.removenum(difficulty);
			sudoku_Logic.getcell(game.puzzle);
			/*
			 * System.out.println(Arrays.equals(game.puzzle, game.answer)); for
			 * (int i = 0; i<9 ; i++){ System.out.println(); for (int n = 0;
			 * n<9; n++){ System.out.print(game.answer[i][n]); }
			 * System.out.println(); }
			 */
			resetbuttons();
			game.gamestarted = true;
			game.repaint();
		} else if (source.getText() == "New Specific Game") {
			int difficulty = getInt("Please indicate the difficulty.");
			sudoku_MusicPlayer.playmusic("start.wav");
			sudoku_Logic.newgame();
			sudoku_Logic.getcell(game.answer);
			sudoku_Logic.removenum(difficulty);
			sudoku_Logic.getcell(game.puzzle);
			resetbuttons();
			game.gamestarted = true;
			game.repaint();
		} else if (source.getText() == "Save") {
			if (game.gamestarted == true) {
				savefile();
				resetbuttons();
				start.setEnabled(false);
				game.repaint();
			} else {
				display("Please start game first!", "WARNING");
				game.repaint();
			}
		} else if (source.getText() == "Load") {
			if (game.gamestarted == true){
				display("Please end game first!", "WARNING");
				game.repaint();
			}
			else{
				loadfile();
				undo.setEnabled(true);
				redo.setEnabled(true);
				submit.setEnabled(true);
				showhint.setEnabled(true);
				start.setEnabled(false);
				soundcontrol.setEnabled(true);
				game.repaint();
			}
		} else if (source.getText() == "Help") {
			display(
					"Try show hint, and visit http://en.wikipedia.org/wiki/Sudoku" +
					"\r\n" +
					"You can only save the game when the game is started." +
					"\r\n" +
					"You can only load a game when no game is started." +
					"\r\n" +
					"Random Game doesn't allow you to choose difficutly whereas Specific Game can." +
					"\r\n",
					"HELP");
		} else if (source.getText() == "About SUDOKU") {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String about = String
					.format(
							"Developer: wgx731 \nDevelop Date: 2010-12-29 \nToday Date :%s \nVersion: 1.0 \n Wish you have fun here!",
							sdf.format(cal.getTime()));
			display(about, "ABOUT");
		} else {
			System.exit(0);
		}
	}

	public static void resetbuttons() {
		game.filledup.clear();
		game.redo.clear();
		undo.setEnabled(false);
		redo.setEnabled(false);
		submit.setEnabled(false);
		showhint.setEnabled(false);
		soundcontrol.setEnabled(false);
		start.setEnabled(true);
		game.removeMouseListener(boardmonitor);
	}

	private static void savefile() {
		if (fc == null) {
			fc = new JFileChooser();
			// Add a custom file filter and disable the default
			// (Accept All) file filter.
			String[] ext = { "xml" };
			fc.addChoosableFileFilter(new sudokuFileFilter(ext, ".xml"));
			fc.setAcceptAllFileFilterUsed(false);
		}
		int returnVal = fc.showSaveDialog(game);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			sudoku_gameWriter.writeXML(file.getAbsolutePath(), game);
		}
	}

	private static void loadfile() {
		if (fc == null) {
			fc = new JFileChooser();
			// Add a custom file filter and disable the default
			// (Accept All) file filter.
			String[] ext = { "xml" };
			fc.addChoosableFileFilter(new sudokuFileFilter(ext, ".xml"));
			fc.setAcceptAllFileFilterUsed(false);
		}
		int returnVal = fc.showDialog(game, "Load");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			sudoku_gameReader.Readxml(file, game);
		}
	}

	static Integer getInt(String prompt) {
		Integer value = null;
		boolean valid = false;
		while (!valid) {
			try {
				String input = JOptionPane.showInputDialog(game, prompt,
						"Input", JOptionPane.QUESTION_MESSAGE);
				if (input != null) {
					value = Integer.parseInt(input);
				} else {
					value = -1;
				}
				if (value > 0 && value < 10 || value == -1) {
					valid = true;
					if (value == -1) {
						value = 0;
					}
				} else {
					JOptionPane.showMessageDialog(game, "Only 1-9", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(game, "Please enter an integer.",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return value;
	}

	static void display(String msg, String title) {
		if (title == "ABOUT") {
			JOptionPane.showMessageDialog(game, msg, title,
					JOptionPane.INFORMATION_MESSAGE);
		} else if (title == "HELP") {
			JOptionPane.showMessageDialog(game, msg, title,
					JOptionPane.QUESTION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(game, msg, title,
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		game = new Frame("A SUDOKU GAME");
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setLayout(new BorderLayout());
		sudoku_GUI sukodu = new sudoku_GUI();
		// Create/set menu bar and panel.
		menu = sukodu.createMenuBar();
		menu.setOpaque(true);
		game.setJMenuBar(menu);
		// Add game buttons
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		start = new JButton("Start solving");
		undo = new JButton("Undo");
		showhint = new JButton("Show hint");
		redo = new JButton("Redo");
		submit = new JButton("Show solution");
		soundcontrol = new JButton("Play");
		start.setEnabled(false);
		undo.setEnabled(false);
		redo.setEnabled(false);
		submit.setEnabled(false);
		showhint.setEnabled(false);
		soundcontrol.setEnabled(false);
		buttons.add(start);
		buttons.add(submit);
		buttons.add(undo);
		buttons.add(redo);
		buttons.add(showhint);
		buttons.add(soundcontrol);
		buttons.setBorder(new TitledBorder("Game Control"));
		buttons.setOpaque(true);
		game.add(buttons, BorderLayout.PAGE_END);
		// Display the window.
		game.setSize(700, 600);
		game.setResizable(false);
		game.setVisible(true);
	}

	private static void AddAction() {

		// start button
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				boardmonitor = new Monitor();
				game.addMouseListener(boardmonitor);
				sudoku_GUI.display("Click on the empty cell to solve puzzle!",
						"Instruction");
				start.setEnabled(false);
				redo.setEnabled(true);
				undo.setEnabled(true);
				submit.setEnabled(true);
				showhint.setEnabled(true);
				soundcontrol.setEnabled(true);
			}
		});

		// submit button
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < 9; i++) {
					for (int n = 0; n < 9; n++) {
						game.puzzle[i][n] = game.answer[i][n];
					}
				}
				resetbuttons();
				start.setEnabled(false);
				game.repaint();
				javax.swing.SwingUtilities.invokeLater(
						new Runnable(){
							public void run() {
								if (!start.isEnabled()){
									game.gamestarted = false;
								}
							}
						}
				);
			}
		});

		// showhint button
		showhint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (showhint.getText() == "Show hint") {
					game.showhint = true;
					showhint.setText("Hide hint");
				} else {
					game.showhint = false;
					showhint.setText("Show hint");
				}
			}

		});

		// undo Button
		undo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (game.filledup.isEmpty()) {
					sudoku_GUI.display("Sorry, no step to undo!", "Error");
				} else {
					Point p = game.filledup.pop();
					int value = game.puzzle[p.x][p.y];
					game.redo.push(new Redo(p, value));
					game.puzzle[p.x][p.y] = 0;
					game.repaint();
				}
			}

		});

		// redo Button
		redo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (game.redo.isEmpty()) {
					sudoku_GUI.display("Sorry, no step to redo!", "Error");
				} else {

					Redo r = game.redo.pop();
					Point p = r.getP();
					int value = r.getValue();
					game.filledup.push(p);
					game.puzzle[p.x][p.y] = value;
					game.repaint();
				}
			}
		});
		
		//soundcontrol Button
		 soundcontrol.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (soundcontrol.getText() == "Play"){
						sudoku_MusicPlayer.loadsound("bg.wav");
						sudoku_MusicPlayer.play();
						soundcontrol.setText("Stop");
					}
					else{
						sudoku_MusicPlayer.stop();
						soundcontrol.setText("Play");
					}
				}
			});
	}

	public static void main(String args[]) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
				AddAction();
			}
		});
	}
}