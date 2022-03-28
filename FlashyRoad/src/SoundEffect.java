import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
// Class to run the sound.
public class SoundEffect {
			//stores the sound
			private static Clip clip;
			
			// get the file name
			public static void setFile(String soundFileName) {
				//retrieve the sound
				try {
			        clip = AudioSystem.getClip();
			        URL url = Game.class.getResource(soundFileName);
			        AudioInputStream inputStream = AudioSystem.getAudioInputStream(url);
			        clip.open(inputStream);
			    } catch (Exception e) {
			    	//throwback error message
			        System.err.println(e.getMessage());
			    }
			}
			/**
			 * methods to call the sound and stop the sound
			 */
			public static void play() {
				clip.start();
			}
			public static void loop() {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			public static void loop(int sec) {
				clip.loop(sec);
			}
			public static void stop() {
				clip.stop();
			}
		}


