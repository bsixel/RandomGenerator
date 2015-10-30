package application;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UI {
	
	public static final String TITLE = "Pseudo Random String Generator";
	
	public Label title = new Label(TITLE);
	
	public Label elementsToGen = new Label("Number of strings to generate: ");
	public TextField elements = new TextField();
	public HBox elementsBox = new HBox(10);
	
	public Label maxLengthToGen = new Label("Max length of each string: ");
	public TextField maxLength = new TextField();
	public Label varyLabel = new Label("Vary string lengths? ");
	public CheckBox varyLength = new CheckBox();
	public HBox lengthBox = new HBox(10);
	
	
	public Label askAllowedChars = new Label("Allowed characters: ");
	public CheckBox allowNumbers = new CheckBox();
	public CheckBox allowUCLetters = new CheckBox();
	public CheckBox allowLCLetters = new CheckBox();
	public CheckBox allowStartZero = new CheckBox();
	public CheckBox allowDuplicates = new CheckBox();
	public HBox charsBox = new HBox(10);
	HBox subCharsBox = new HBox(10);
	
	public Label prefixLabel = new Label("Prefix for the entire list: ");
	public TextField listPrefix = new TextField();
	public Label prefixElLabel = new Label("Prefix for each element: ");
	public TextField elementPrefix = new TextField();
	public HBox prefixBox = new HBox(10);
	
	public Label suffixLabel = new Label("Suffix for the entire list: ");
	public TextField listSuffix= new TextField();
	public Label suffixElLabel = new Label("Suffix for each element: ");
	public TextField elementSuffix = new TextField();
	public HBox suffixBox = new HBox(10);
	
	public Label oneLineLabel = new Label("Put all entries on a single line? ");
	public CheckBox oneLine = new CheckBox();
	public HBox oneLineBox = new HBox();
	
	public Label saveInfo = new Label("You will be prompted upon hitting 'Start' to choose a save destination.");
	public Button startButton = new Button("Start");
	
	public VBox root = new VBox(10);
	public Scene scene = new Scene(root, 950, 400);
	
	public UI() {
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		initElements();
		initLength();
		initChars();
		initPrefix();
		initSuffix();
		initOneLine();
		this.root.setPadding(new Insets(10));
		this.root.getChildren().addAll(this.title, this.elementsBox, this.lengthBox, this.charsBox, this.subCharsBox, this.prefixBox, this.suffixBox, this.oneLineBox, this.saveInfo, this.startButton);
		this.startButton.setOnAction(e -> {
			File file = RandomGenerator.startFileSaver("Select a location to save the file.");
			Thread thread = new Thread(() -> RandomGenerator.generateStrings(file, Double.parseDouble(this.elements.getText()), Integer.parseInt(this.maxLength.getText()), this.varyLength.isSelected(), this.allowNumbers.isSelected(), this.allowUCLetters.isSelected(), this.allowLCLetters.isSelected(), this.allowStartZero.isSelected(), this.allowDuplicates.isSelected(), this.listPrefix.getText(), this.elementPrefix.getText(), this.listSuffix.getText(), this.elementSuffix.getText(), this.oneLine.isSelected()));
			thread.setDaemon(true);
			thread.start();
			RandomGenerator.startInfoDlg("Do not interrupt!", String.format("You have started the writing process; %nPlease do not close the window until the 'completed' dialog appears."));
		});
	}
	
	public void initElements() {
		this.elementsBox.getChildren().addAll(this.elementsToGen, this.elements);
		this.elements.setPromptText("Enter a number of strings to generate...");
	}
	
	public void initLength() {
		this.lengthBox.getChildren().addAll(this.maxLengthToGen, this.maxLength, this.varyLabel, this.varyLength);
		this.maxLength.setPromptText("Enter a max length...");
		this.varyLength.setText("Vary the length of the strings?");
	}
	
	public void initChars() {
		subCharsBox.getChildren().addAll(new Label("Special cases: "), this.allowStartZero, this.allowDuplicates);
		this.charsBox.getChildren().addAll(this.askAllowedChars, this.allowNumbers, this.allowUCLetters, this.allowLCLetters);
		this.allowNumbers.setText("Allow numbers in the generated strings?");
		this.allowUCLetters.setText("Allow upper case letters in the generated strings?");
		this.allowLCLetters.setText("Allow lower case letters in the generated strings?");
		this.allowStartZero.setText("Allow a string to start with a zero?");
		this.allowDuplicates.setText("Allow duplicate entries?");
		
		this.allowNumbers.setTooltip(new Tooltip("Allow numbers in the generated strings?"));
		this.allowUCLetters.setTooltip(new Tooltip("Allow upper case letters in the generated strings?"));
		this.allowLCLetters.setTooltip(new Tooltip("Allow lower case letters in the generated strings?"));
		this.allowStartZero.setTooltip(new Tooltip("Allow a string to start with a zero?"));
		this.allowUCLetters.setTooltip(new Tooltip("Allow duplicate entries?"));
	}
	
	public void initPrefix() {
		this.prefixBox.getChildren().addAll(this.prefixLabel, this.listPrefix, this.prefixElLabel, this.elementPrefix);
		this.listPrefix.setPromptText("List prefix...");
		this.elementPrefix.setPromptText("String prefix...");
	}
	
	public void initSuffix() {
		this.suffixBox.getChildren().addAll(this.suffixLabel, this.listSuffix, this.suffixElLabel, this.elementSuffix);
		this.listSuffix.setPromptText("List suffix...");
		this.elementSuffix.setPromptText("String suffix...");
	}
	
	public void initOneLine() {
		this.oneLineBox.getChildren().addAll(this.oneLineLabel, this.oneLine);
		this.oneLine.setText("Put the entire list on one line?");
	}
	
}