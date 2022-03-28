
//Header Files
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

//Main Game Logic File
public class Game extends JPanel implements ActionListener, KeyListener {
	private boolean newGame = false;
	private boolean instructions = false;
	private boolean lb = false;

	/*
	 * Declaring new game variables
	 */

	// Call on the MapGenerator class to get random terrains
	private MapGenerator mapGen = new MapGenerator();
	// Holds Number of strips on screen.
	private int numOfStrips = 13;
	// 2D array for holding rows and columns of the map.
	private Sprite[][] mapArray = new Sprite[13][12];
	// Stores the special terrains of the map
	private ArrayList<Integer> special = new ArrayList<>();
	// Holds number of special images in special strip.
	private int land = 1, water = 0;
	private JButton start, control, sound, leaderboard;
	private JLabel logo;
	private int soundCount = 1;

	// Array that holds the vehicles. | Vehicles
	private ArrayList<Sprite> object1 = new ArrayList<>();
	// Array that holds the other obstacles. | Potions
	private ArrayList<Sprite> object2 = new ArrayList<>();

	// Inherit obstacles class attributes
	private DeadlyItems deadlyItems = new DeadlyItems();
	private FriendlyItem friendlyItem = new FriendlyItem();

	// Create players and add image file names for each direction
	private Character playerone = new Character("Player 1", "Flash/Characters/RF-B.png", "Flash/Characters/RF-F.png",
			"Flash/Characters/RF-L.png", "Flash/Characters/RF-R.png", "Flash/Characters/RF-B.png",
			"Flash/Characters/RF-B.png");
	private Character playertwo = new Character("Player 2", "Flash/Characters/BF-B.png", "Flash/Characters/BF-F.png",
			"Flash/Characters/BF-L.png", "Flash/Characters/BF-R.png", "Flash/Characters/BF-B.png",
			"Flash/Characters/BF-B.png");

	// Create in-game timer.
	private Timer timerLoop;

	/**
	 * Default constructor.
	 */
	Game(boolean pause) {

		// Set layout to absolute for buttons.
		setLayout(null);
		// Generating player Icon at starting point of the game.
		playerone.setImage(playerone.getCharacter_img_up());
		playertwo.setImage(playertwo.getCharacter_img_up());

		// Create button component, set image, remove borders.
		start = new JButton(new ImageIcon(getClass().getResource("Flash/newstart.png")));
		start.setBorder(BorderFactory.createEmptyBorder());
		control = new JButton(new ImageIcon(getClass().getResource("Flash/howtoplay.png")));
		control.setBorder(BorderFactory.createEmptyBorder());
		sound = new JButton(new ImageIcon(getClass().getResource("Flash/hearsound.png")));
		sound.setBorder(BorderFactory.createEmptyBorder());
		leaderboard = new JButton(new ImageIcon(getClass().getResource("Flash/leaderboard.png")));
		leaderboard.setBorder(BorderFactory.createEmptyBorder());
		logo = new JLabel(new ImageIcon(getClass().getResource("Flash/flashylogo.png")));

		// Trigger at the start of the game when there isnt any sound

		sound.setIcon(new ImageIcon(getClass().getResource("Flash/hearsound.png")));

		//Listener for button clicking
		start.addActionListener(this);
		control.addActionListener(this);
		sound.addActionListener(this);
		leaderboard.addActionListener(this);

		//Append the buttons onto the frame
		add(logo);
		add(start);
		add(control);
		add(sound);
		add(leaderboard);

		// Display start and control button
		logo.setBounds(200, 120, 800, 200);
		start.setBounds(445, 350, 300, 100);
		control.setBounds(475, 500, 240, 50);
		sound.setBounds(100, 500, 40, 40);
		sound.setFocusPainted(false);
		leaderboard.setBounds(475, 600, 240, 50);

		// Create key listeners for each player
		addKeyListener(this);
		// Center the JPanel
		setFocusable(true);
		// Make the movement smooth.
		setDoubleBuffered(true);
		// Create the game timer and start it.
		timerLoop = new Timer(18, this);
		// Method to set the sprite locations.
		setInitialLocations();

		/// Pauses the game on first run.
		if (!pause) {
			start.setVisible(false);
			control.setVisible(false);
			sound.setVisible(false);
			logo.setVisible(false);
			leaderboard.setVisible(false);
			timerLoop.start();
		}

	}

	/**
	 * Method to set the initial location of all the sprites.
	 */
	private void setInitialLocations() {

		// Initializes game with land strips.
		for (int i = 0; i < numOfStrips; i++) {
			// Creates land at the start
			Sprite[] strip = mapGen.getTiles(3);
			// Adds objects strip to map array.
			mapArray[i] = strip;
		}

		// 100 pixels between each grid x
		int x = 0;
		// 100 pixels between each grid y
		int y = 0;

		// Starting location
		for (int i = 0; i < numOfStrips; i++) {
			for (int z = 0; z < 12; z++) {
				mapArray[i][z].setXLoc(x);
				mapArray[i][z].setYLoc(y);
				x += 100;
			}
			x = 0;
			y += 100;
		}

		// Put player characters on location
		playerone.setXLoc(298);
		playerone.setYLoc(mapArray[2][2].getYLoc());
		playertwo.setXLoc(698);
		playertwo.setYLoc(mapArray[2][2].getYLoc());

		// Sets special array to first initialized land sprite array.
		// Prevents water/raft offset if it is generated right after the starting
		// location
		for (int i = 0; i < 12; i++) {
			if (mapArray[0][i].getFileName().equals("Flash/Grass.png")) {
				special.add(i);
				land++;
			}
		}
	}

	/**
	 * Reads keyboard input for moving when key is pressed down.
	 */
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		// Moves player within left and right bounds.
		case KeyEvent.VK_D:
			if (!playerone.getPress() && playerone.getXLoc() < 1000) {
				playerone.setRight(8);
				playerone.setPress();
			}
			break;
		case KeyEvent.VK_A:
			if (!playerone.getPress() && playerone.getXLoc() > 0) {
				playerone.setLeft(8);
				playerone.setPress();
			}
			break;
		case KeyEvent.VK_W:
			if (!playerone.getPress()) {
				playerone.setUp(10);
				playerone.setPress();
			}
			break;
		case KeyEvent.VK_S:
			if (!playerone.getPress()) {
				playerone.setDown(10);
				playerone.setPress();
			}
			break;
			// Allows player to pause the game
					case KeyEvent.VK_SHIFT:
						if(playerone.getHp() == 0 || playertwo.getHp() == 0) {
							System.out.print(String.valueOf(playerone.getHp()));
							newGame = true;
							newGame();
						}
						if (timerLoop.isRunning()) {
							timerLoop.stop();
							start.setVisible(true);
							control.setVisible(true);
							sound.setVisible(true);
							logo.setVisible(true);
							leaderboard.setVisible(true);
						} else {
							timerLoop.start();
							start.setVisible(false);
							control.setVisible(false);
							sound.setVisible(false);
							logo.setVisible(false);
							leaderboard.setVisible(false);
						}
						break;
		case KeyEvent.VK_ENTER:
			if (!timerLoop.isRunning()) {
				newGame = true;
				newGame();
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (!playertwo.getPress() && playertwo.getXLoc() < 1000) {
				playertwo.setRight(8);
				playertwo.setPress();
			}
			break;
		case KeyEvent.VK_LEFT:
			if (!playertwo.getPress() && playertwo.getXLoc() > 0) {
				playertwo.setLeft(8);
				playertwo.setPress();
			}
			break;
		case KeyEvent.VK_UP:
			if (!playertwo.getPress()) {
				playertwo.setUp(10);
				playertwo.setPress();
			}
			break;
		case KeyEvent.VK_DOWN:
			if (!playertwo.getPress()) {
				playertwo.setDown(10);
				playertwo.setPress();
			}
			break;
		}

	}

	/**
	 * Reads keyboard for input for stopping when key is not pressed down.
	 */
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {

		case KeyEvent.VK_D:
			playerone.setXDir(0);
			break;
		case KeyEvent.VK_A:
			playerone.setXDir(0);
			break;
		case KeyEvent.VK_W:
			playerone.setYDir(2);
			break;
		case KeyEvent.VK_S:
			playerone.setYDir(2);
			break;
		case KeyEvent.VK_RIGHT:
			playertwo.setXDir(0);
			break;
		case KeyEvent.VK_LEFT:
			playertwo.setXDir(0);
			break;
		case KeyEvent.VK_UP:
			playertwo.setYDir(2);
			break;
		case KeyEvent.VK_DOWN:
			playertwo.setYDir(2);
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Timer runs these statement on a loop.
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		// Start a new game
		if (e.getSource() == start) {
				newGame = true;
				newGame();
		}
		// display instruction in howtoplay panel.
		else if (e.getSource() == control) {
			instructions = true;
			instructionsPanel();

		}
		// Display the top few scores
		else if (e.getSource() == leaderboard) {
			lb = true;
			try {
				leaderboardPanel();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (e.getSource() == sound) {
			//check if sound is on.
			if (soundCount == 1) {
				soundCount = 0;
				sound.setIcon(new ImageIcon(getClass().getResource("Flash/mutesound.png")));
				SoundEffect.stop();
			//check if sound is off.
			} else if (soundCount == 0) {
				soundCount = 1;
				sound.setIcon(new ImageIcon(getClass().getResource("Flash/hearsound.png")));
				SoundEffect.setFile("Flash/Sounds/bgmusic.wav");
				SoundEffect.play();
				SoundEffect.loop();
			}
		}
		
		// Runs the timer.
		else {

			// Method that check player location
			playerBounds(playerone);
			playerBounds(playertwo);

			// Method to move the character one block.
			jumpPlayer(playerone, playertwo);
			jumpPlayer(playertwo, playerone);

			// Sprite method that moves the player.
			playerone.move();
			playertwo.move();

			// Method to move object1.
			int a = deadlyItems.manageObject(object1, playerone, playertwo);
			if (a == 1) {
				killMsg("obj1");
			}

			// Method to move object2.
			friendlyItem.manageObject(object2, playerone, playertwo);

			// Moves all the sprites in the sprite strips.
			for (int i = 0; i < numOfStrips; i++) {
				for (int x = 0; x < 12; x++) {
					mapArray[i][x].move();
				}
			}

			// Method that resets the strips.
			// manageStrips();
			mapGen.manageStrips(numOfStrips, mapArray, land, water, special, object1, object2, deadlyItems,
					friendlyItem);

			// Method to set the scrolling speed.
			scrollScreen();

			// Assigns farthest travel to score.
			playerone.increaseScore(playerone.getMovement());

			playertwo.increaseScore(playertwo.getMovement());

			// Redraws sprites on screen.
			repaint();

			// Stop stuttering problems fro linux
			Toolkit.getDefaultToolkit().sync();
		}
	}

	/**
	 * Method that prevent player from moving on trees, and checks for death with
	 * obstacles
	 */
	private void playerBounds(Character player) {
		if (player.getInvulnerable()) {
			player.countDownInvulnerable();
			if (player.getName() == "Player 2") {
				player.setCharacter_img_down("Flash/Invisible/IBF-F.png");
				player.setCharacter_img_up("Flash/Invisible/IBF-B.png");
				player.setCharacter_img_left("Flash/Invisible/IBF-L.png");
				player.setCharacter_img_right("Flash/Invisible/IBF-R.png");
			} else if (player.getName() == "Player 1") {
				player.setCharacter_img_down("Flash/Invisible/IRF-F.png");
				player.setCharacter_img_up("Flash/Invisible/IRF-B.png");
				player.setCharacter_img_left("Flash/Invisible/IRF-L.png");
				player.setCharacter_img_right("Flash/Invisible/IRF-R.png");
			}
			if (player.getInvulnerableTimer() == 0) {
				if (player.getName() == "Player 2") {
					player.setCharacter_img_down("Flash/Characters/BF-F.png");
					player.setCharacter_img_up("Flash/Characters/BF-B.png");
					player.setCharacter_img_left("Flash/Characters/BF-L.png");
					player.setCharacter_img_right("Flash/Characters/BF-R.png");
				} else if (player.getName() == "Player 1") {
					player.setCharacter_img_down("Flash/Characters/RF-F.png");
					player.setCharacter_img_up("Flash/Characters/RF-B.png");
					player.setCharacter_img_left("Flash/Characters/RF-L.png");
					player.setCharacter_img_right("Flash/Characters/RF-R.png");
				}
				player.unsetInvulnerable();
			}
		}
		// Collision method
		for (int i = 0; i < numOfStrips; i++) {
			for (Sprite s : mapArray[i]) {
				if (!player.getInvulnerable()) {
					// Prevents player from going through trees.
					if (s.getFileName().equals("Flash/Tree.png") || s.getFileName().equals("Flash/Tree.png")) {
						// if (collision(player, s))
						if (player.collision(s)) {
							if ((s.getYLoc() + 100) - (player.getYLoc()) < 5
									&& (s.getXLoc() + 100) - player.getXLoc() < 125
									&& (s.getXLoc() + 100) - player.getXLoc() > 20) {
								player.setUp(0);
							} else if ((player.getYLoc() + 105) > (s.getYLoc())
									&& (player.getXLoc() + 100) - s.getXLoc() < 125
									&& (player.getXLoc() + 100) - s.getXLoc() > 20) {
								player.setDown(0);
							} else if (player.getXLoc() - (s.getXLoc() + 100) > -5
									&& (s.getYLoc() + 100) - player.getYLoc() < 125
									&& (s.getYLoc() + 100) - player.getYLoc() > 20) {
								player.setLeft(0);
							} else if (s.getXLoc() - (player.getXLoc() + 100) > -5
									&& (player.getYLoc() + 100) - s.getYLoc() < 125
									&& (player.getYLoc() + 100) - s.getYLoc() > 20) {
								player.setRight(0);
							}
						}
					}

					// Ends game if user lands on water.
					if (s.getFileName().equals("Flash/Water.png")) {
						if (s.getXLoc() - player.getXLoc() > 0 && s.getXLoc() - player.getXLoc() < 10) {
							if (s.getYLoc() - player.getYLoc() > 0 && s.getYLoc() - player.getYLoc() < 10) {

								if (player.getName() == "Player 1") {
									SoundEffect.setFile("Flash/Sounds/river-4.wav");
									SoundEffect.play();
									player.collideMovement(2);
									player.minusHp();
									if (player.getHp() != 0) {
										player.setXLoc(298);
										player.setYLoc(player.getYLoc() + 202);
										player.setInvulnerable();

									} else {
										killMsg("water");
									}
								}

								if (player.getName() == "Player 2") {
									SoundEffect.setFile("Flash/Sounds/river-4.wav");
									SoundEffect.play();									
									playertwo.collideMovement(2);
									playertwo.minusHp();
									if (playertwo.getHp() != 0) {
										playertwo.setXLoc(698);
										playertwo.setYLoc(playertwo.getYLoc() + 202);
										playertwo.setInvulnerable();

									} else {
										killMsg("water");
									}
								}

							}
						}
					}
				}
			}

			// Ends game if user goes too far down.
			if (player.getYLoc() > 800) {

				if (player.getName() == "Player 1") {
					playerone.collideMovement(2);
					playerone.minusHp();
					if (playerone.getHp() != 0) {
						playerone.setXLoc(298);
						playerone.setYLoc(playerone.getYLoc() - 402);
						playerone.setInvulnerable();
					} else {
						SoundEffect.setFile("Flash/Sounds/scream.wav");
						SoundEffect.play();
						killMsg("tooFarDown");
					}
				}

				if (player.getName() == "Player 2") {
					playertwo.collideMovement(2);
					playertwo.minusHp();
					if (playertwo.getHp() != 0) {
						playertwo.setXLoc(698);
						playertwo.setYLoc(playertwo.getYLoc() - 402);
						playertwo.setInvulnerable();
					} else {
						SoundEffect.setFile("Flash/Sounds/scream.wav");
						SoundEffect.play();
						killMsg("tooFarDown");
					}
				}

			}

			// Ends game if user goes too far up kill him.
			if (player.getYLoc() < -110) {
				if (player.getName() == "Player 1") {
					playerone.setXLoc(298);
					playerone.setYLoc(500);
					playerone.setHp(0);
					if (playerone.getHp() == 0) {
						SoundEffect.setFile("Flash/Sounds/scream.wav");
						SoundEffect.play();
						killMsg("tooFarUp");
					}
				}
				if (player.getName() == "Player 2") {
					playertwo.setXLoc(698);
					playertwo.setYLoc(500);
					playertwo.setHp(0);
					if (playertwo.getHp() == 0) {
						SoundEffect.setFile("Flash/Sounds/scream.wav");
						SoundEffect.play();
						killMsg("tooFarUp");
					}
				}
			}
		}
	}

	/**
	 * Moves the character forward or backwards WITHOUT OFF-SETTING THE LOCATION DUE
	 * TO SCROLLING.
	 *
	 * Up,down,left,right :
	 */
	private void jumpPlayer(Character player, Character playertwo) {

		// if (collision(player, playertwo)) {
		if (player.collision(playertwo) || playertwo.collision(player)) {
			if ((playertwo.getYLoc() + 100) - (player.getYLoc()) < 5
					&& (playertwo.getXLoc() + 100) - player.getXLoc() < 125
					&& (playertwo.getXLoc() + 100) - player.getXLoc() > 20) {
				player.setUp(0);
			} else if ((player.getYLoc() + 105) > (playertwo.getYLoc())
					&& (player.getXLoc() + 100) - playertwo.getXLoc() < 125
					&& (player.getXLoc() + 100) - playertwo.getXLoc() > 20) {
				player.setDown(0);
			} else if (player.getXLoc() - (playertwo.getXLoc() + 100) > -5
					&& (playertwo.getYLoc() + 100) - player.getYLoc() < 125
					&& (playertwo.getYLoc() + 100) - player.getYLoc() > 20) {
				player.setLeft(0);
			} else if (playertwo.getXLoc() - (player.getXLoc() + 100) > -5
					&& (player.getYLoc() + 100) - playertwo.getYLoc() < 125
					&& (player.getYLoc() + 100) - playertwo.getYLoc() > 20) {
				player.setRight(0);
			}
		}

		// Holds the player's location.
		int location;

		// If left/right is pressed.
		if (player.getLeft() > 0 && player.getPress()) {
			player.setXDir(-12.5);
			player.setLeft(player.getLeft() - 1);
			player.setImage(player.getCharacter_img_left());
		} else if (player.getRight() > 0 && player.getPress()) {
			player.setXDir(12.5);
			player.setRight(player.getRight() - 1);
			player.setImage(player.getCharacter_img_right());
		} else if (player.getLeft() == 0 && player.getRight() == 0 && player.getUp() == 0 && player.getDown() == 0) {
			player.setXDir(0);
			player.setDepress();
		}

		// If up is pressed.
		if (player.getUp() > 0 && player.getPress()) {

			// Set player speed.
			player.setYDir(-10);
			player.move();
			player.setImage(player.getCharacter_img_up());

			// Get player Y location.
			location = player.getYLoc();

			// Sets the player's location up one strip.
			for (int i = 0; i < numOfStrips; i++) {

				Sprite x = mapArray[i][0];

				// Aligns player to strip after movement.
				if (location - x.getYLoc() > 95 && location - x.getYLoc() < 105) {

					player.setYDir(0);
					player.setUp(0);
					// press = false;
					player.setDepress();

					player.setYLoc(x.getYLoc() + 101);

					// Increases travel keeper.
					player.incrementMovement();

					i = numOfStrips;
				}
			}
		}

		// If down in pressed.
		else if (player.getDown() > 0 && player.getPress()) {

			// Set player speed.
			player.setYDir(10);
			player.move();
			player.setImage(player.getCharacter_img_down());

			// Get player location
			location = player.getYLoc();

			// Sets the players location down one strip.
			for (int i = 0; i < numOfStrips; i++) {

				Sprite x = mapArray[i][0];

				// Align player to strip after movement.
				if (location - x.getYLoc() < -95 && location - x.getYLoc() > -105) {

					player.setYDir(0);
					player.setDown(0);
					// press = false;
					player.setDepress();

					player.setYLoc(x.getYLoc() - 99);
					// Decreases travel keeper.
					if(player.getMovement() >= 1) {
						player.decrementMovement();
					}

					i = numOfStrips;
				}
			}
		}
	}

	/**
	 * Scrolls the strips and the player.
	 */
	private void scrollScreen() {

		// Cycles through strip array.
		for (int v = 0; v < numOfStrips; v++) {
			for (int x = 0; x < 12; x++) {
				mapArray[v][x].setYDir(2);
			}
		}
		// Sets scrolling if player is not moving.
		if (!playerone.getPress() || !playertwo.getPress()) {

			playerone.setYDir(2);
			playertwo.setYDir(2);

		}
	}

	/**
	 * Draws graphics onto screen.
	 */
	public void paintComponent(Graphics g) {

		// Erases the previous screen.
		super.paintComponent(g);

		// Draws strips.
		for (int i = 0; i < numOfStrips; i++) {
			for (int x = 0; x < 12; x++) {
				mapArray[i][x].paint(g, this);
			}
		}

		// Draw player sprite.
		playerone.paint(g, this);
		playertwo.paint(g, this);

		// Draw car sprites.
		for (Sprite s : object1)
			s.paint(g, this);

		// Draw train sprites.
		for (Sprite s : object2)
			s.paint(g, this);

		// Set the font size and color.
		Font currentFont = g.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 3f);
		g.setFont(newFont);
		g.setColor(Color.white);

		// Set the font size and color.
		Font cF = g.getFont();
		Font nF = cF.deriveFont(cF.getSize() * 1f);
		g.setFont(nF);
		g.setColor(Color.yellow);

		// Draws the score on the screen.
		g.drawString("Player 1: " + playerone.getScore(), 50, 150);
		// Draws the score on the screen.
		g.drawString("Player 2: " + playertwo.getScore(), 950, 150);

		// Set the font size and color.
		g.setFont(nF);
		g.setColor(Color.red);

		// Display the health and life in hearts of the players
		if (playerone.getHp() == 3) {
			g.setFont(nF);
			g.setColor(Color.red);
			g.drawString("\u2665", 50, 200);
			g.drawString("\u2665", 75, 200);
			g.drawString("\u2665", 100, 200);
		} else if (playerone.getHp() == 2) {
			g.setFont(nF);
			g.setColor(Color.red);
			g.drawString("\u2665", 50, 200);
			g.drawString("\u2665", 75, 200);
		} else if (playerone.getHp() == 1) {
			g.setFont(nF);
			g.setColor(Color.red);
			g.drawString("\u2665", 50, 200);
		} else {

		}

		if (playertwo.getHp() == 3) {
			g.setFont(nF);
			g.setColor(Color.red);
			g.drawString("\u2665", 1050, 200);
			g.drawString("\u2665", 1075, 200);
			g.drawString("\u2665", 1100, 200);
		} else if (playertwo.getHp() == 2) {
			g.setFont(nF);
			g.setColor(Color.red);
			g.drawString("\u2665", 1050, 200);
			g.drawString("\u2665", 1075, 200);
		} else if (playertwo.getHp() == 1) {
			g.setFont(nF);
			g.setColor(Color.red);
			g.drawString("\u2665", 1050, 200);
		} else {

		}

		// Set the font size and color.
		Font CF = g.getFont();
		Font NF = CF.deriveFont(CF.getSize() * 1f);
		g.setFont(NF);
		g.setColor(Color.red);
	}

	/**
	 * Method to end game. Stops loop, saves scores, displays message.
	 */
	public void killMsg(String death) {
		timerLoop.stop();
		String cause = "air";
		String winnerName, winnerName2;
		switch (death) {
		case "water":
			cause = "drown in the river!";
			break;
		case "tooFarDown":
			cause = "went out of bounds into the abyss (bottom)!";
			break;
		case "tooFarUp":
			cause = "got to excited and went out of bounds (top)!";
			break;
		case "obj1":
			cause = "got hit by a vehicle";
			break;
		}

		// Check for players' lives then check for scores
		if (playerone.getHp() == playertwo.getHp()) {
			winnerName = JOptionPane.showInputDialog(null, "Both Players Tied Enter Player 1 Name");
			winnerName2 = JOptionPane.showInputDialog(null, "Both Players Tied Enter Player 2 Name");
			if (winnerName == null) {
				winnerName = "Player1";
			}
			if (winnerName2 == null) {
				winnerName2 = "Player2";
			}
			
			if (playerone.getScore() >= playertwo.getScore()){
				Score.updateScores(winnerName, winnerName2,playerone.getScore());
			}else {
				Score.updateScores(winnerName, winnerName2,playertwo.getScore());
			}
		} else if (playerone.getHp() > playertwo.getHp()) {
			winnerName = JOptionPane.showInputDialog(null,
					"Player 1 has won\n" + "Player 2 " + cause + "\nPlease enter Player 1 Name");
			if (winnerName == null) {
				winnerName = "Player1";
			}
			Score.updateScores(winnerName, playerone.getScore());
		} else {
			winnerName = JOptionPane.showInputDialog(null,
					"Player 2 has won\n" + "Player 1 " + cause + "\nPlease enter Player 2 Name");
			if (winnerName == null) {
				winnerName = "Player2";
			}
			Score.updateScores(winnerName, playertwo.getScore());
		}

		start.setVisible(true);
		control.setVisible(true);
		sound.setVisible(true);
		logo.setVisible(true);
		leaderboard.setVisible(true);
	}

	/**
	 * Method that starts a new game.
	 */
	private void newGame() {
		if (newGame) {
			// Get this JFrame and destroy it.
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
			frame.dispose();

			// Create new main menu JFrame.
			new FlashyRoad(false, soundCount);

		}
	}

	private void instructionsPanel() {

		if (instructions) {
			// Create new main menu JFrame.
			new InstructionWindow(false);
		}
	}

	private void leaderboardPanel() throws Exception {
		if (lb) {
			// Create new main menu JFrame.
			new ScoreBoardWindow(false);
		}
	}
	
	

}

