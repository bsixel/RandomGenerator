package application;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RandomGenerator {

	private static final String[] nums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	private static final String[] lcLetters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	private static final String[] ucLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	
	private static final String[] numsLC = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	private static final String[] numsUC = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	private static final String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	private static final String[] all = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	public static void generateStrings(File file, double toGenerate, int length, boolean varyLengths,
			boolean allowNums, boolean allowUCLetters, boolean allowLCLetters, boolean allowStartZero, boolean allowDuplicates,
			String listPrefix, String entryPrefix, String listSuffix, String entrySuffix, boolean oneLine) {
		
		try {
			ArrayList<String> prev = new ArrayList<String>();
			String[] allowedList = getCharList(allowNums, allowUCLetters, allowLCLetters);
			file.createNewFile();
			FileWriter writer = new FileWriter(file, true);
			PrintWriter printer = new PrintWriter(writer);

			if (oneLine) {
				printer.format(listPrefix);
			} else {
				if (!listPrefix.equals("") && !listPrefix.equals(null)) {
					printer.format("%1$s%n", listPrefix);
				} else {
					printer.format("%1$s%n", listPrefix);
				}
			}
			
			for (double n = 0; n < toGenerate; n ++) {
				
				String str = genString(length, varyLengths, allowStartZero, allowedList);
				if (!allowDuplicates) {
					while (prev.contains(str)) {
						str = genString(length, varyLengths, allowStartZero, allowedList);
					}
				}
				if (oneLine) {
					printer.format("%1$s%2$s$3$s", entryPrefix, str, entrySuffix);
				} else {
					printer.format("%1$s%2$s$3$s%n", entryPrefix, str, entrySuffix);
				}
				
			}
			
			if (oneLine) {
				printer.format(listSuffix);
			} else {
				if (!listPrefix.equals("") && !listPrefix.equals(null)) {
					printer.format("%1$s", listPrefix);
				}
			}

			writer.close();
			printer.close();
		} catch (Exception e) {
			startErrorDlg("Error building random file!", "Unable to complete random file.");
		}
		
	}
	
	private static String genString(int length, boolean varyLength, boolean allowStartZero, String[] allowedList) throws InstantiationException, IllegalAccessException {
		String str = "";
		if (varyLength) {
			length = Random.class.newInstance().nextInt(length + 1);
		}
		
		if (!allowStartZero) {
			String s = "0";
			while (s.equals("0")) {
				s = randomChar(allowedList);
			}
			str += s;
		} else {
			str += randomChar(allowedList);
		}
		
		for (int n = 1; n < length; n ++) {
			str += randomChar(allowedList);
		}
		
		return str;
	}
	
	private static String[] getCharList(boolean allowNums, boolean allowUCLetters, boolean allowLCLetters) {
		String[] toReturn = null;
		
		if (allowNums && allowUCLetters && allowLCLetters) {
			toReturn = all;
		} else if (allowNums && !allowUCLetters && !allowLCLetters) {
			toReturn = nums;
		} else if (allowNums && allowUCLetters && !allowLCLetters) {
			toReturn = numsUC;
		} else if (allowNums && !allowUCLetters && allowLCLetters) {
			toReturn = numsLC;
		} else if (!allowNums && allowUCLetters && !allowLCLetters) {
			toReturn = ucLetters;
		} else if (!allowNums && !allowUCLetters && allowLCLetters) {
			toReturn = lcLetters;
		} else if (!allowNums && allowUCLetters && allowLCLetters) {
			toReturn = letters;
		}
		
		return toReturn;
	}
	
	private static String randomChar(String[] chars) {
		Random rand = new Random();
		return chars[rand.nextInt(chars.length)];
	}

	public static File startFileOpener(String title) {

		Stage popup = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		return fileChooser.showOpenDialog(popup);

	}

	public static void startErrorDlg(String title, String info) {

		Stage popup = new Stage(StageStyle.UTILITY);
		VBox layout = new VBox(5);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout, popup.getWidth(), popup.getHeight());
		popup.setScene(scene);
		scene.setFill(Color.SILVER);
		Button exitButton = new Button("Dismiss");
		popup.addEventHandler(KeyEvent.KEY_PRESSED, key -> {
			if (key.getCode() == KeyCode.ENTER || key.getCode() == KeyCode.ESCAPE) {
				popup.close();
			}
		});
		exitButton.setOnAction(e -> popup.close());
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setResizable(false);
		popup.setTitle(title);
		popup.setWidth(250);
		popup.setHeight(100);
		Label infoLabel = new Label();
		infoLabel.setText(info);
		layout.getChildren().addAll(exitButton, infoLabel);
		popup.setAlwaysOnTop(true);
		popup.showAndWait();

	}

}