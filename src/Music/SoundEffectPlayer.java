package Music;

import java.util.ArrayList;

public class SoundEffectPlayer implements Runnable{
	
	private ArrayList<AudioFile> soundEffectFiles;
	private int currentSoundEffect;
	private boolean isRunning;
	
	public SoundEffectPlayer(String... files) {
		soundEffectFiles = new ArrayList<AudioFile>();
		for (String file : files) {
			soundEffectFiles.add(new AudioFile("./resources/audio/soundeffects/" + file + ".wav"));
		}
	}
	@Override
	public void run() {
		isRunning = true;
		AudioFile soundEffect = soundEffectFiles.get(currentSoundEffect);
		soundEffect.playAudio();
		while(isRunning) {
			if (!soundEffect.isPlaying()) {
				currentSoundEffect++;
				if (currentSoundEffect >= soundEffectFiles.size()) {
					currentSoundEffect = 0;
				}
				soundEffect = soundEffectFiles.get(currentSoundEffect);
				soundEffect.playAudio();
			}
		}
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
