import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class ScoreBoardWindow extends JFrame implements ActionListener{
	
			//Variable for JFrame size.
			private final int HEIGHT = 700;
			private final int WIDTH = 1100;
			private JButton back;
			private JPanel buttonPanel;			
			
			ScoreBoardWindow(boolean pause) {

				//Set the title.
				setTitle("Leaderboard");

				//Set the size of the JFrame.
				setSize(WIDTH, HEIGHT);

				//Set window to screen center.
				setLocationRelativeTo(null);
				
				try {
					Score.sortScore();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//set resize.
				setResizable(false);
				
				buttonPanel = new JPanel();	
				buttonPanel.setBounds(500, 500, 100, 100);
				buttonPanel.setBackground(Color.black);
			
				
				getContentPane().add(buttonPanel);
				getContentPane().add(new ScoreBoardContent());
//				// Create button component, set image, remove borders.
				back = new JButton(new ImageIcon(getClass().getResource("Flash/backbtn.png")));
				back.setBorder(BorderFactory.createEmptyBorder());
				back.addActionListener(this);
				buttonPanel.add(back);
				
				// Display start and control button
				back.setBounds(100, 100, 100, 100);

				//Display the window.
				setVisible(true);
				back.setVisible(true);
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
					// Start a new game
					if (e.getSource() == back) {
						// Get this leaderboard panel and destroy it.
						JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(back);
						frame.dispose();
						// Create new main menu JFrame.
//						new Flashyroad(true);
						
					}
			}
			public static void main(String[] args) throws Exception {
				new ScoreBoardWindow(true);
			}
			
		}

class ScoreBoardContent extends JPanel {
	/**
	 * 
	 */
	private final JLabel char1 = new JLabel(new ImageIcon(getClass().getResource("Flash/Characters/RF-L.png")));
	private final JLabel char2 = new JLabel(new ImageIcon(getClass().getResource("Flash/Characters/BF-R.png")));

	public ArrayList<String> HighScores;

	
	public void paintComponent(Graphics g) {
				// Erases the previous screen.
				super.paintComponent(g);
				//setbackground
				setBackground(Color.black);
				HighScores = Score.readScore();
				
				add(char1);
				add(char2);
				
				char1.setBounds(300, 40, 104, 104);
				char2.setBounds(670, 40, 104, 104);
				
				// Set the font size and color.
				Font currentFont = new Font("SansSerif", Font.BOLD, 10);
				Font newFont = currentFont.deriveFont(currentFont.getSize() * 3f);
				g.setFont(newFont);
				g.setColor(Color.white);
				
				g.drawString("#", 120, 200);
				g.drawString("Player Name", 300, 200);
				g.drawString("Score", 650, 200);
				int x = 10;
				for(int counter = 0; counter < HighScores.size(); counter++) {
					g.drawString(String.valueOf(counter+1), 120, 250+x);
					g.drawString(HighScores.get(counter).split(":")[0], 300, 250+x);
					g.drawString(HighScores.get(counter).split(":")[1], 650, 250+x);

					x +=30;
				}
				g.setFont(currentFont.deriveFont(currentFont.getSize() * 4f));
				g.drawString("Leaderboard", 415, 110);
				
				Font cF = new Font("Candara",Font.BOLD, 22);
				Font nF = cF.deriveFont(cF.getSize() * 1f);
				g.setFont(nF);
				g.setColor(Color.red);

				
				Font CF = new Font("Consolas", Font.BOLD, 10);
				Font NF = CF.deriveFont(cF.getSize() * 1f);
				g.setFont(NF);
				g.setColor(Color.blue);

	}
}