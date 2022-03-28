import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


import javax.imageio.ImageIO;

public class DeadlyItems extends Items implements hitSounds, generateObstacles {
	// Create random generator.
	private Random rand = new Random();

	/**
	 * Method that creates and resets cars on the road strip. Returns Sprite Object
	 * (Car)
	 */
	public Sprite setObstacles(int stripYLoc) {

		// Makes sprite.
		Sprite vehicle = new Sprite();

		// Scrolls sprite.
		vehicle.setYDir(2);

		// Set sprite to strip location.
		vehicle.setYLoc(stripYLoc);

		if (rand.nextInt(2) == 1) {
			// Right to left.
			vehicle.setXLoc(1200);
			vehicle.setXDir(-(rand.nextInt(10) + 10));
			vehicle.setImage(randomObstacles("left"));

		} else {
			// Left to right.
			vehicle.setXLoc(-200);
			vehicle.setXDir((rand.nextInt(10) + 10));
			vehicle.setImage(randomObstacles("right"));
		}

		return vehicle;
	}

	/**
	 * Method to return random car color. Returns Sprite Object (Car)
	 */
	public String randomObstacles(String dir) {
		// Vehicle color variables.
		int vehicleType = rand.nextInt(4);
		String vehicleImage = "";

		if (dir.equals("left")) {

			switch (vehicleType) {
			case 0:
				vehicleImage = "Flash/Obstacles/BC-L.png";
				break;
			case 1:
				vehicleImage = "Flash/Obstacles/RC-L.png";
				break;
			case 2:
				vehicleImage = "Flash/Obstacles/BL-L.png";
				break;
			case 3:
				vehicleImage = "Flash/Obstacles/GL-L.png";
				break;
			}
		}

		if (dir.equals("right")) {

			switch (vehicleType) {
			case 0:
				vehicleImage = "Flash/Obstacles/BC-R.png";
				break;
			case 1:
				vehicleImage = "Flash/Obstacles/RC-R.png";
				break;
			case 2:
				vehicleImage = "Flash/Obstacles/BL-R.png";
				break;
			case 3:
				vehicleImage = "Flash/Obstacles/GL-R.png";
				break;
			}
		}
		ObjImage(vehicleImage);
		return vehicleImage;
	}

	/**
	 * Method that: Moves Vehicles. Removes object1 passing Y bounds. Checks for car
	 * collisions. Note: foreach not working correctly.
	 */
	public int manageObject(ArrayList<Sprite> object1, Character playerOne, Character playerTwo) {

		// Cycles through object sprites.
		for (int i = 0; i < object1.size(); i++) {

			Sprite obj1 = object1.get(i);

			// Removes object1 passing Y bounds.
			if (obj1.getYLoc() > 800) {
				object1.remove(i);
			} else {

				// Moves oject1 sprites.
				obj1.move();

				// Reset object1 passing X bounds.
				if (obj1.getXLoc() < -(rand.nextInt(1200) + 400)) {
					// Right to left.
					obj1.setXDir(-(rand.nextInt(4) + 2));
					obj1.setXLoc(1200);
					obj1.setImage(randomObstacles("left"));
				} else if (obj1.getXLoc() > (rand.nextInt(700) + 1100)) {
					// Left to right.
					obj1.setXDir((rand.nextInt(4) + 2));
					obj1.setXLoc(-200);
					obj1.setImage(randomObstacles("right"));
				}
			}
			// Checks for Vehicle & Player collisions.
			int a = playerVehicleCollision(obj1, playerOne);
			int b = playerVehicleCollision(obj1, playerTwo);
			if (a == 1) {
				return 1;
			}
			if (b == 1) {
				return 1;
			}
		}
		return 0;
	}

	public int playerVehicleCollision(Sprite obj1, Character player) {

		if (player.collision(obj1) && !player.getInvulnerable()) {
			hitSound(0);
			hitSound(2);
			player.collideMovement(2);
			player.minusHp();
			if (player.getHp() != 0) {
				if (player.getName() == "Player 1") {
					player.setXLoc(298);
				} else {
					player.setXLoc(698);
				}
				player.setYLoc((int) (player.getYLoc() / 100) + 298);
				player.setInvulnerable();
			} else {
				return 1;
			}
		}
		return 0;
	}

	// Play the hit sounds when collision occurs
	public void hitSound(int FXmusic) {
		//easier access to the different FX sounds.
		switch (FXmusic) {
		case 0:
			SoundEffect.setFile("Flash/Sounds/no.wav");
			SoundEffect.play();
			break;
		case 1:
			SoundEffect.setFile("Flash/Sounds/mygod.wav");
			SoundEffect.play();
			break;
		case 2:
			SoundEffect.setFile("Flash/Sounds/carcrash.wav");
			SoundEffect.play();
			break;
		default:
			SoundEffect.setFile("Flash/Sounds/scream.wav");
			SoundEffect.play();
			break;
		}

	}
	// Print message when collision against item.
	public void hitMessage(String text) {
		System.out.println(text);
	}

	// Check image height doesn't exceed 100
	public void ObjImage(String randomValue) {
		
		// Check for full path
				try {
					String newPath = new File(randomValue).getCanonicalPath().toString();
					String replaceS2 = newPath.substring(0, newPath.length() - randomValue.length());
					// Add the missing folder
					String finalString = replaceS2 + "src\\" + randomValue;
					
					//Get height of string
					File myObj = new File(finalString);
					BufferedImage bimg;
					try {
						bimg = ImageIO.read(myObj);
						int width = bimg.getWidth();
						int height = bimg.getHeight();
						System.out.println("Height of deadly items:" + String.valueOf(height));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("Height of deadly items: exceed 100");
						e.printStackTrace();
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		



	}
	

}
