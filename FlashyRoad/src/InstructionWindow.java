import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class InstructionWindow extends JFrame implements ActionListener{
	
	//Variable for JFrame size.
			private final int HEIGHT = 700;
			private final int WIDTH = 1150;
			private JButton back;
			private JPanel buttonPanel;
			
			InstructionWindow(boolean pause) {

				//Set the title.
				setTitle("How to Play");

				//Set the size of the JFrame.
				setSize(WIDTH, HEIGHT);

				//Set window to screen center.
				setLocationRelativeTo(null);

				//set resize.
				setResizable(false);

				
				
				buttonPanel = new JPanel();
				buttonPanel.setBounds(80, 550, 100, 60);
				buttonPanel.setBackground(new Color(244,229,193));
				
				//Add panel to frame.
				getContentPane().add(buttonPanel);
				getContentPane().add(new InstructionContent());

//				// Create button component, set image, remove borders.
				back = new JButton(new ImageIcon(getClass().getResource("Flash/backbtn.png")));
				back.setBorder(BorderFactory.createEmptyBorder());
				back.addActionListener(this);
				buttonPanel.add(back);
				
//				// Display start and control button
				back.setBounds(50, 50, 50, 50);

				//Display the window.
				setVisible(true);
				back.setVisible(true);
			}
			
				public void actionPerformed(ActionEvent e) {
					// Start a new game
					if (e.getSource() == back) {
						// Get this instruction panel and destroy it.
						JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(back);
						frame.dispose();
					}
			}

			
			public static void main(String[] args) {
				
				new InstructionWindow(true);
			}
			
		}

class InstructionContent extends JPanel {
	
	private Sprite bg = new Sprite("Flash/Instructions/instructionbg.png");
	private Sprite arrow = new Sprite("Flash/Instructions/arrow.png");
	private Sprite WASD = new Sprite("Flash/Instructions/WASD.png");
	private Sprite shift = new Sprite("Flash/Instructions/shift.png");
	private Sprite clickstart = new Sprite("Flash/Instructions/clickstart.png");
	private Sprite lives = new Sprite("Flash/Instructions/3lives.png");
	private Sprite car = new Sprite("Flash/Instructions/IBC-L.png");
	private Sprite lorry = new Sprite("Flash/Instructions/IBL-L.png");
	private Sprite raft = new Sprite("Flash/Instructions/Iraft.png");
	private Sprite tree = new Sprite("Flash/Instructions/ITree.png");
	private Sprite water = new Sprite("Flash/Instructions/IWater.png");
	private Sprite coin = new Sprite("Flash/Instructions/Icoin.png");
	private Sprite potion = new Sprite("Flash/Instructions/Ipotion.png");

	
	public void paintComponent(Graphics g) {
				
		// Erases the previous screen.
				super.paintComponent(g);
				//setbackground
				bg.setXLoc(0);
				bg.setYLoc(0);
				bg.paint(g, this);
				
				//arrow keys img
				arrow.setXLoc(370);
				arrow.setYLoc(350);
				arrow.paint(g, this);
				
				//WASD keys img
				WASD.setXLoc(300);
				WASD.setYLoc(310);
				WASD.paint(g, this);
				
				//shift keys img
				shift.setXLoc(400);
				shift.setYLoc(420);
				shift.paint(g, this);
				
				//clickstart img
				clickstart.setXLoc(300);
				clickstart.setYLoc(455);
				clickstart.paint(g, this);
				
				//3lives img
				lives.setXLoc(950);
				lives.setYLoc(315);
				lives.paint(g, this);
				
				//car img
				car.setXLoc(930);
				car.setYLoc(430);
				car.paint(g, this);
				
				//lorry img
				lorry.setXLoc(975);
				lorry.setYLoc(433);
				lorry.paint(g, this);
				
				//raft img
				raft.setXLoc(930);
				raft.setYLoc(520);
				raft.paint(g, this);
				
				//tree img
				tree.setXLoc(950);
				tree.setYLoc(480);
				tree.paint(g, this);
				
				//water img
				water.setXLoc(895);
				water.setYLoc(460);
				water.paint(g, this);
				
				//coin img
				coin.setXLoc(980);
				coin.setYLoc(550);
				coin.paint(g, this);
				
				//potion img
				potion.setXLoc(1025);
				potion.setYLoc(590);
				potion.paint(g, this);
				
				// Set the font size and color.
				Font currentFont = new Font("SansSerif", Font.BOLD, 10);
				Font newFont = currentFont.deriveFont(currentFont.getSize() * 3f);
				g.setFont(newFont);
				g.setColor(Color.black);
				
				g.drawString("Game Controls", 165, 300);
				g.drawString("Rules", 780, 300);
				g.drawString("Objectives", 475, 70);
				
				Font cF = new Font("Candara",Font.BOLD, 22);
				Font nF = cF.deriveFont(cF.getSize() * 1f);
				g.setFont(nF);
				g.setColor(Color.red);
				
				//Game Control
				//g.drawString("Game Controls:", 100, 450);
				g.drawString("- WASD:  Move player 1",65, 360);
				g.drawString("- Arrow Keys:  Move player 2",65, 400);
				g.drawString("- Shift:  Pause / Resume the game.", 65, 440);
				g.drawString("- Click Start:  Start game", 65, 480);
				//g.drawString("- Ctrl: Activates 3 seconds of invincibility once ",65,500);
				//g.drawString("per game.", 65 ,520);


				//Rules
				g.drawString("- Each player will be given 3 lives",630,340);
				g.drawString("- Invincibility: Allow player to pass through ", 630 ,370);
				g.drawString("any objects for 3 seconds", 640 ,395);
				g.drawString("- Both scores and lives will be deducted when:",630,420);
				g.drawString("o Player hits cars and lorries", 660,450);
				g.drawString("o Player falls in the river", 660,480);
				g.drawString("- Trees block players from moving",630,510);
				g.drawString("- Rafts: Help to cross river safely",630,540);
				g.drawString("- Coins: Will help to gain more points", 630 ,570);
				g.drawString("- Potions: Will help to gain more lives but will not ",630 ,590);
				g.drawString("increase if player still has 3 lives", 725 ,610);
				g.drawString("- Player lose when they run out of lives", 630 ,630);
				
				Font CF = new Font("Consolas", Font.BOLD, 10);
				Font NF = CF.deriveFont(cF.getSize() * 1f);
				g.setFont(NF);
				g.setColor(Color.blue);

				//Objectives
				g.drawString("Player who survive the longest by crossing through the map will win the game."
						+ " Players ", 70 ,120);
				g.drawString("must gauge and estimate the correct timing to pass the obstacles without dying.", 70,150);
				g.drawString("Good luck in surviving and have fun!", 70,180);
			
	}
	public static void main(String[] args) {
		
					
				}
	}
