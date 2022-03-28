
/**
 * Map generator is used to generate the map and assign tiles
 */
import java.util.ArrayList;
import java.util.Random;


class MapGenerator {

	/**
	 * Randomly generates a strip
	 */
	public Sprite[] getStrip() {
		//Array to hold a strip.
		Sprite[] spriteStrip = new Sprite[12];

		//Initiate random library to be used.
		Random gen = new Random();

		//Breath of the map.
		int y = spriteStrip.length;

		//Choose the terrain.
		int x = gen.nextInt(4);


		//Sets landscape.
		switch (x) {
			//Vehicle crossing.
			case 0:
				for (int i = 0; i < y; i++) {
					Sprite strip = new Sprite("Flash/StripR.png");
					spriteStrip[i] = strip;
				}
				break;

			//Pavement.
			case 1:
				for (int i = 0; i < y; i++) {
					Sprite strip = new Sprite("Flash/Sidewalk.png");
					spriteStrip[i] = strip;
				}
				break;

			//Land with trees.
			case 2:
				for (int i = 0; i < y; i++) {
					//Holds random number.
					x = gen.nextInt(3);
					spriteStrip[i] = makeSpecialStrip(i, x, "Flash/Grass.png", "Flash/Grass.png", "Flash/Tree.png");
				}
				break;

			//Water with platform.
			case 3:
				for (int i = 0; i < y; i++) {
					//Holds random number.
					x = gen.nextInt(5);
					spriteStrip[i] = makeSpecialStrip(i, x, "Flash/Water.png", "Flash/Water.png", "Flash/raft.png");
				}
		}

		return spriteStrip;
	}


	/*
	 * Class to either create a terrain or a terrain with inmoveable obstacles.
	 */
	private Sprite makeSpecialStrip(int i, int x, String background, String specialBlockOne, String specialBlockTwo) {

		Sprite oneBlock = new Sprite();

		switch (x) {
			//Add normal terrain.
			case 0:
				oneBlock.setImage(background);
				break;

			//Add normal terrain.
			case 1:
				oneBlock.setImage(background);
				break;

			//Add normal terrain.
			case 2:
				oneBlock.setImage(background);
				break;


			//Add immovable obstacles.
			case 3:
				oneBlock.setImage(specialBlockOne);
				break;

			//Add immovable obstacles.
			case 4:
				oneBlock.setImage(specialBlockTwo);
				break;
		}

		//Return filename to array of strip.
		return oneBlock;
	}

	/*
	 * CREATED BY ASIF 
	 * PURPOSE: ALL IN 1 FUNCTION TO GENERATE RESPECTIVE TILES
	 */
	public Sprite[] getTiles(int type) {

		String background = null,blockOne = null,obstacleBlock = null;
		if (type == 0)
		{
			// Base Tile: Grass w Tree
			background = "Flash/Grass.png";
			blockOne = "Flash/Grass.png";
			obstacleBlock = "Flash/Tree.png";
		}
		else if (type == 1)
		{
			// Base Tile: Grass w Shrubs
			background = "Flash/Grass.png";
			blockOne = "Flash/Grass.png";
			obstacleBlock = "Flash/Log.png";
		}
		else if (type == 2)
		{
			// Base Tile: Water w Raft
			background = "Flash/Water.png";
			blockOne = "Flash/Water.png";
			obstacleBlock = "Flash/raft.png";
		}
		else if (type == 3)
		{
			// Base Tile: Generated during initial stages of the game. 
			background = "Flash/Grass.png";
			blockOne = "Flash/Grass.png";
			obstacleBlock = "Flash/Grass.png";
		}
		else
		{
			// To collect any outliers
			background = "Flash/Grass.png";
			blockOne = "Flash/Grass.png";
			obstacleBlock = "Flash/Tree.png";
		}
		
		
		//Makes random numbers.
		Random gen = new Random();

		//Array to hold strip.
		Sprite[] spriteStrip = new Sprite[12];

		for (int i = 0; i < spriteStrip.length; i++) {
			//Holds random number.
			int x = gen.nextInt(5);
			spriteStrip[i] = makeSpecialStrip(i, x, background, blockOne, obstacleBlock);
		}
		return spriteStrip;
	}
	
	/**
	 * Method that correctly resets the strips.
	 */
	public void manageStrips(int numOfStrips, Sprite[][] mapArray, int land, int water, ArrayList<Integer> special,ArrayList<Sprite> object1,ArrayList<Sprite> object2,DeadlyItems deadlyItems,FriendlyItem friendlyItem) 
{
		// Blank strip test variables.
		int allWater;
		int allGrass;


		// Cycles through each strip.
		for (int v = 0; v < numOfStrips; v++) {

			// Checks if strip is out of bounds.
			if (mapArray[v][0].getYLoc() > 1200) {

				// Generates a new strip.
				mapArray[v] = getStrip();

				// Prevents an all water or grass strip.
				do {
					// Reset variables.
					allWater = 0;
					allGrass = 0;

					// Check sprites in strip.
					for (Sprite s : mapArray[v]) {
						if (s.getFileName().equals("Flash/Water.png"))
							allWater++;
						if (s.getFileName().equals("Flash/Grass.png"))
							allGrass++;
					}

					if (allWater == 12)
						mapArray[v] = getTiles(2);
					if (allGrass == 12)
						mapArray[v] = getTiles(0);

				} while (allWater == 12 || allGrass == 12);

				// If there was previously a water strip, and this strip is a water strip, match
				// this strips raft to the previous strip.
				if (water > 0) {
					if (mapArray[v][0].getFileName().equals("Flash/Water.png")
							|| mapArray[v][0].getFileName().equals("Flash/raft.png")) {

						water = 0;

						for (int i : special) {
							mapArray[v][i].setImage("Flash/raft.png");
						}
					}
				}

				// If there was previously a water strip, and this strip is a land strip, match
				// the grass to the previous strips raft.
				if (water > 0) {
					if (mapArray[v][0].getFileName().equals("Flash/Grass.png")
							|| mapArray[v][0].getFileName().equals("Flash/Log.png")
							|| mapArray[v][0].getFileName().equals("Flash/Tree.png")
							|| mapArray[v][0].getFileName().equals("Flash/Tree.png")) {

						mapArray[v] = getTiles(1);

						water = 0;

						for (int i : special) {
							mapArray[v][i].setImage("Flash/Grass.png");
						}
					}
				}

				// If there was previously a land strip, and this strip is a water strip, match
				// the raft to the grass.
				if (land > 0) {
					if (mapArray[v][0].getFileName().equals("Flash/Water.png")
							|| mapArray[v][0].getFileName().equals("Flash/raft.png")) {

						land = 0;

						int val = 0;

						while (val == 0) {

							mapArray[v] = getTiles(2);

							for (int i = 0; i < 12; i++) {
								if (mapArray[v][i].getFileName().equals("Flash/raft.png")) {
									// TODO: Remove
									for (int s : special) {
										if (i == s) {
											val++;
										}

									}
								}
							}
						}

					}
				}

				// if there is a water strip, write down the index of the raft.
				if (mapArray[v][0].getFileName().equals("Flash/Water.png")
						|| mapArray[v][0].getFileName().equals("Flash/raft.png")) {

					special.clear();

					water = 0;

					for (int i = 0; i < 12; i++) {
						if (mapArray[v][i].getFileName().equals("Flash/raft.png")) {
							special.add(i);
							water++;
						}
					}
				} else
					water = 0;

				// if there is a land strip, write down the index of the grass.
				if (mapArray[v][0].getFileName().equals("Flash/Grass.png")
						|| mapArray[v][0].getFileName().equals("Flash/Log.png")
						|| mapArray[v][0].getFileName().equals("Flash/Tree.png")
						|| mapArray[v][0].getFileName().equals("Flash/Tree.png")) {

					special.clear();

					land = 0;

					for (int i = 0; i < 12; i++) {
						if (mapArray[v][i].getFileName().equals("Flash/Grass.png")) {
							special.add(i);
							land++;
						}
					}
				}

				// Variable to reset horizontal strip location.
				int X = 0;

				// Reset the location of the strip.
				for (int i = 0; i < 12; i++) {

					mapArray[v][i].setYLoc(-99);
					mapArray[v][i].setXLoc(X);

					X += 100;
				}
				
				// Set car.
				if (mapArray[v][0].getFileName().equals("Flash/StripR.png")) {
					object1.add(deadlyItems.setObstacles(mapArray[v][0].getYLoc() + 10));
				}

				// Set train.
				if (mapArray[v][0].getFileName().equals("Flash/Sidewalk.png")) {
					object2.add(friendlyItem.setObstacles(mapArray[v][0].getYLoc() + 10));
				}
			}
		}
	}
}
