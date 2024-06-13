package game.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	public static final int MENU = 0;
	public static final int PLAYING = 1;

	public static final int ENEMY_DEATH = 0;
	public static final int SIREN = 1;
	public static final int FIREBALL = 2;

    private static String[] songNames = { "menu", "playing" };
	private Clip[] songs;
	private int currentSongId;

	private static String[] effectNames = { "enemy_death", "siren", "fireball" };
	
    private float volume = 0.8f;

	public AudioPlayer() {
		loadSongs();
	}

	private void loadSongs() {
		songs = new Clip[songNames.length];
		for (int i = 0; i < songs.length; i++)
			songs[i] = getClip(songNames[i]);
	}

	private Clip getClip(String name) {
		AudioInputStream audio;

		try {
			audio = AudioSystem.getAudioInputStream(new File("audios/" + name + ".wav"));
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

			e.printStackTrace();
		}

		return null;
	}
	
	public void playSong(int song) {
		stopSong();

		currentSongId = song;
		updateVolume(songs[currentSongId]);
		songs[currentSongId].setMicrosecondPosition(0);
		songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stopSong() {
		if (songs[currentSongId].isActive())
			songs[currentSongId].stop();
	}

	public void playEffect(int effect) {
		try {
            Clip clip = getClip(effectNames[effect]);
            if (clip != null) {
            	updateVolume(clip);

                clip.start();
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	private void updateVolume(Clip c) {
		FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);
	}

}
