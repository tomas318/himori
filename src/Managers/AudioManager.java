package Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Music.MusicPlayer;
import Music.SoundEffectPlayer;

public class AudioManager {

	private Map<String, MusicPlayer> musicMap;
	private Map<String, SoundEffectPlayer> soundEffectMap;
	private MusicPlayer currentMusicPlayer;
	private SoundEffectPlayer currentSoundEffectPlayer;

	public AudioManager() {
		musicMap = new HashMap<String, MusicPlayer>();
		soundEffectMap = new HashMap<String, SoundEffectPlayer>();
	}

	public void addMusicPlayer(MusicPlayer newMusicPlayer, String statePlayedOn) {
		musicMap.put(statePlayedOn.toUpperCase(), newMusicPlayer);
		if(currentMusicPlayer == null) {
			currentMusicPlayer = newMusicPlayer;
		}
	}

	public void addSoundEffectPlayer(SoundEffectPlayer newSoundEffectPlayer, String texturePlayedOn) {
		soundEffectMap.put(texturePlayedOn.toUpperCase(), newSoundEffectPlayer);
		if(currentSoundEffectPlayer == null) {
			currentSoundEffectPlayer = newSoundEffectPlayer;
		}
	}

	public void setMusicPlayer(String newState) {
		MusicPlayer newMusicPlayer = musicMap.get(newState.toUpperCase());
		if(newMusicPlayer == null) {
			System.err.println("MusicPlayer does not exist for " + newState);
			return;
		}
	}

	public void setSoundEffectPlayer(String newTexture) {
		SoundEffectPlayer newsoundEffectPlayer = soundEffectMap.get(newTexture.toUpperCase());
		if(newsoundEffectPlayer == null) {
			System.err.println("MusicPlayer does not exist for " + newTexture);
			return;
		}
	}

	public void tick(String currentState) {
		boolean justChanged = false;
		int whilecountlevel1 = 0;
		while (currentState.toUpperCase() == "Menu".toUpperCase()) {
			MusicPlayer menumusic = musicMap.get(currentState.toUpperCase());
			Main.Main.Handler.runTask(menumusic);
		}
		while (currentState == "Level1".toUpperCase()) {
			//if(whilecountlevel1 == 0) {
			//	justChanged = true;
			//}
			//if (justChanged) {
			//	
			//}
			MusicPlayer level1music = musicMap.get(currentState.toUpperCase());
			Main.Main.Handler.runTask(level1music);
		}
	}
}
