package Music;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioFile implements LineListener{

	private File soundFile;
	private AudioInputStream audioManager;
	private AudioFormat audioFileFormat;
	private DataLine.Info audioInfo;
	private Clip audioClip;
	private FloatControl audioControl;
	private volatile boolean isPlaying;
	
	public AudioFile(String fileName) {
		this.soundFile = new File(fileName);
		try {
			this.audioManager = AudioSystem.getAudioInputStream(soundFile);
			this.audioFileFormat = audioManager.getFormat();
			this.audioInfo = new DataLine.Info(Clip.class, audioFileFormat);
			this.audioClip = (Clip) AudioSystem.getLine(audioInfo);
			this.audioClip.addLineListener(this);
			this.audioClip.open(audioManager);
			// If you want to lower the audio of the clip by a certain float value put it in setValue.
			this.audioControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void playAudio() {
		//default decrement
		playAudio(-10);
	}
	
	public void playAudio(float audioVolumeDecrement) {
		//custom decrement
		audioControl.setValue(audioVolumeDecrement); 
		audioClip.start();
		isPlaying = true;
	}

	@Override
	public void update(LineEvent event) {
		if(event.getType() == LineEvent.Type.START) {
			isPlaying = true;
		}else if(event.getType() == LineEvent.Type.STOP) {
			audioClip.stop();
			audioClip.flush();
			audioClip.setFramePosition(0);
			isPlaying = false;
		}
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
	
}
