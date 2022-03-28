//Class for JFrame extension.
import javax.swing.*;

public class FlashyRoad extends JFrame 
{
		//Variable for JFrame size.
		private final int HEIGHT = 800;
		private final int WIDTH = 1200;

		/**
		 * Default constructor.
		 */
		FlashyRoad(boolean pause) {
			
			//Set the title.
			setTitle("Flashy Road");

			//Set the size of the JFrame.
			setSize(WIDTH, HEIGHT);

			//Set window to screen center.
			setLocationRelativeTo(null);

			//Specify the close button action.
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			//set resize.
			setResizable(false);

			//Add panel to frame.
			add(new Game(pause));

			//Display the window.
			setVisible(true);
			
			SoundEffect.setFile("Flash/Sounds/bgmusic.wav");
			SoundEffect.play();
		}

		/**
		 * To catch the sound of the windows
		 * @param pause
		 * @param soundCatch
		 */
		FlashyRoad(boolean pause, int soundCatch) {
			
			//Set the title.
			setTitle("Flashy Road");

			//Set the size of the JFrame.
			setSize(WIDTH, HEIGHT);

			//Set window to screen center.
			setLocationRelativeTo(null);

			//Specify the close button action.
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			//set resize.
			setResizable(false);

			//Add panel to frame.
			add(new Game(pause));

			//Display the window.
			setVisible(true);
			
		}
		
		/**
		 * Main constructor to start program.
		 */
		public static void main(String[] args) {
			// TODO Auto-generated method stub

			//Pause game if first run.
			final boolean pause = true;
			
			//Create window for game.
			new FlashyRoad(pause);
		}
		
}
