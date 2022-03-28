
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class FriendlyItem extends Items implements hitSounds, generateObstacles {
	// Create random generator.
	private Random rand = new Random();

	/**
	 * Method that creates and resets persons on the track strip.
	 */
	public Sprite setObstacles(int stripYLoc) {

		// Makes sprite.
		Sprite person = new Sprite(randomObstacles());

		// Scrolls sprite.
		person.setYDir(2);

		// Set sprite to strip location.
		person.setYLoc(stripYLoc);

		if (rand.nextInt(2) == 1) {
			// Right to left.
			person.setXLoc(900);
			person.setXDir(-(rand.nextInt(10) + 30));
		} else {
			// Left to right.
			person.setXLoc(-1500);
			person.setXDir((rand.nextInt(10) + 30));
		}

		return person;

	}

	/**
	 * Method to return a random colored person.
	 */
	public String randomObstacles() {
		int personNum = rand.nextInt(20);
		String personImage = "";

		switch (personNum) {
		case 0:
			personImage = "Flash/coins.png";
			break;
		case 1:
			personImage = "Flash/potion.png";
			break;
		default:
			personImage = "Flash/coins.png";
			break;
		}
		ObjImage(personImage);
		return personImage;
	}

	/**
	 * Method that: Moves object2. Removes object2 passing Y bounds. Checks for
	 * train collisions.
	 */
	public void manageObject(ArrayList<Sprite> object2, Character playerOne, Character playerTwo) {

		// Cycles through obstacles sprites.
		for (int i = 0; i < object2.size(); i++) {

			Sprite obj2 = object2.get(i);

			// Removes object2 passing Y bounds.
			if (obj2.getYLoc() > 800) {
				object2.remove(i);
			} else {

				// Moves obj2 sprites.
				obj2.move();

				// Reset X bounds.
				// Reset object1 passing X bounds.
				if (obj2.getXLoc() < -(rand.nextInt(800) + 1700)) {
					// Right to left.
					obj2.setXDir(-(rand.nextInt(4) + 3));
					obj2.setXLoc(1200);
					obj2.setImage(randomObstacles());
				} else if (obj2.getXLoc() > rand.nextInt(1200) + 1200) {
					// Left to right.
					obj2.setXDir((rand.nextInt(4) + 3));
					obj2.setXLoc(-200);
					obj2.setImage(randomObstacles());
				}

			}
//          collision for 1st player coins and potion
			if (playerOne.collision(obj2)) {
				if (obj2.getFileName() == "Flash/coins.png") {
					hitSound(0);
					hitSound(2);
					hitMessage("You got 5 points!");
					playerOne.incrementMovement(5);
					object2.remove(i);
				} else {
					hitMessage("Player 1 drink a potion (max 3 life)!");
					hitSound(0);
					hitSound(3);
					object2.remove(i);
					if (playerOne.getHp() < 3) {
						playerOne.addHp();
					}
				}

			}
//            collision for 2nd player coins and potion
			if (playerTwo.collision(obj2)) {

				if (obj2.getFileName() == "Flash/coins.png") {
					hitSound(1);
					hitSound(2);
					hitMessage("You got 5 points!");
					playerTwo.incrementMovement(5);
					object2.remove(i);
				} else {
					hitSound(1);
					hitSound(3);
					hitMessage("Player 2 drink a potion (max 3 life)!");
					object2.remove(i);
					if (playerTwo.getHp() < 3) {
						playerTwo.addHp();
					}
				}
			}
		}
	}

	// Play the hit sounds when collision occurs
	public void hitSound(int FXmusic) {
		//easier access to the different FX sounds.
		switch (FXmusic) {
		case 0:
			SoundEffect.setFile("Flash/Sounds/yes1.wav");
			SoundEffect.play();
			break;
		case 1:
			SoundEffect.setFile("Flash/Sounds/yes2.wav");
			SoundEffect.play();
			break;
		case 2:
			SoundEffect.setFile("Flash/Sounds/coin.wav");
			SoundEffect.play();
			break;
		case 3:
			SoundEffect.setFile("Flash/Sounds/drinkpotion.wav");
			SoundEffect.play();
			break;
		default:
			SoundEffect.setFile("Flash/applause.wav");
			SoundEffect.play();
			break;
		}

	}
	// Print message when collision against item.
	public void hitMessage(String text) {
		System.out.println(text);
	}

	// Check image height doesn't exceed 100
	public void ObjImage(String value) {
		
		// Check for full path
		try {
			String newPath = new File(value).getCanonicalPath().toString();
			String replaceS2 = newPath.substring(0, newPath.length() - value.length());
			// Add the missing folder
			String finalString = replaceS2 + "src\\" + value;
			
			//Get height of string
			File myObj = new File(finalString);
			BufferedImage bimg;
			try {
				bimg = ImageIO.read(myObj);
				int width = bimg.getWidth();
				int height = bimg.getHeight();
				System.out.println("Height of friendly items:" + String.valueOf(height));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Height of  friendly items: exceed 100");
				e.printStackTrace();
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
