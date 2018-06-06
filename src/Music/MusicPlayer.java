package Music;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class MusicPlayer implements Runnable{
	
	private ArrayList<AudioFile> musicFiles;
	private int currentSong;
	private boolean isRunning;
	
	public MusicPlayer(String... files) {
		musicFiles = new ArrayList<AudioFile>();
		for(String file: files) {
			musicFiles.add(new AudioFile("./resources/audio/music/" + file + ".wav"));
		}
	}
	
	@Override
	public void run() {
		isRunning = true;
		AudioFile song = musicFiles.get(currentSong);
		song.playAudio();
		while(isRunning) {
			if (!song.isPlaying()) {
				currentSong++;
				if (currentSong >= musicFiles.size()) {
					currentSong = 0;
				}
				song = musicFiles.get(currentSong);
				song.playAudio();
			}
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
