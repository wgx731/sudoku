package ver_2;

import sun.audio.*;
import java.io.*;

public class sudoku_MusicPlayer {
	
	
	private static AudioStream sound;
	
	static void loadsound(String Filename){
		InputStream in;
		try {
			in = new FileInputStream(Filename);
			sound = new AudioStream(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void play(){
		AudioPlayer.player.start(sound);
	}
	
	static void stop(){
		AudioPlayer.player.stop(sound);
		sound = null;
	}
	static void playmusic(String Filename) {
		InputStream in;
		AudioStream as = null;
		try {
			in = new FileInputStream(Filename);
			as = new AudioStream(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		AudioPlayer.player.start(as);
	}
}
