import java.io.IOException;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter; // Import the FileWriter class
import java.io.BufferedReader;
import java.io.BufferedWriter;

//Class for files.

/**
 * Class to save and retrieve top scores.
 */
public class Score {

	String name;
	int score;

	public Score(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public static class scoreCompare implements Comparator<Score> {
		@Override
		public int compare(Score s1, Score s2) {
			return s2.score - s1.score;
		}
	}

	/**
	 * Method to sort high score.
	 * 
	 * @throws IOException
	 */
	public static void sortScore() throws IOException {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader("record.txt"));
			ArrayList<Score> scoreRecords = new ArrayList<Score>();
			String currentLine = reader.readLine();

			while (currentLine != null) {
				String[] recordDetail = currentLine.split(":");
				String name = recordDetail[0];
				int marks = Integer.valueOf(recordDetail[1]);

				scoreRecords.add(new Score(name, marks));
				currentLine = reader.readLine();
			}
			// check where to save the scores
			Collections.sort(scoreRecords, new scoreCompare());
			writer = new BufferedWriter(new FileWriter("record.txt"));

			for (Score s : scoreRecords) {
				writer.write(s.name);

				writer.write(":" + s.score);

				writer.newLine();
			}
		} catch (IOException e) {
			// error catcher for if scores cannot be sort
			System.out.println("Sort Score Error");
			e.printStackTrace();
		} finally {
			// Closing the resources
			reader.close();
			writer.close();
		}
	}

	public static void updateScores(String p1name, String p2name, int score) {
		try {
			BufferedWriter f = null;
			f = new BufferedWriter(new FileWriter("record.txt", true));
			f.write(p1name + "&" + p2name + ":" + score);
			f.newLine();
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Update Score Error");
			e.printStackTrace();
		}
	}

	public static void updateScores(String name, int score) {
		try {
			BufferedWriter f = null;
			f = new BufferedWriter(new FileWriter("record.txt", true));
			f.write(name + ":" + score);
			f.newLine();
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Update Score Error");
			e.printStackTrace();
		}
	}

	/**
	 * Method that returns the high score.
	 * 
	 * @return
	 */
	public static ArrayList<String> readScore() {
		ArrayList<String> list = new ArrayList<String>();
		File f = new File("record.txt");
		Scanner myScanner;
		try {
			myScanner = new Scanner(f);
			for (int i = 0; i < 10; i++) {
				String data;
				try {
					data = myScanner.nextLine();
					list.add(data);
				} catch (NoSuchElementException exception) {
					list.add("None:0");
				}
			}
			myScanner.close();
			System.out.println(list);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("An error occurred while reading leaderboard records.");
			e.printStackTrace();
		}
		return list;
	}

}
