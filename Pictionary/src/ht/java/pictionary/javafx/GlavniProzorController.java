package ht.java.pictionary.javafx;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class GlavniProzorController {
	
	@FXML
	Label wordLabel;

	@FXML
	ProgressBar progressBar;
	@FXML
    Label progressLabel;

	@FXML
	Label translationLabel;
	@FXML
	Label maxLabel;
	@FXML
	VBox vBoxLeft;
	@FXML
	VBox vBoxright;
	@FXML
	Button leftSidebutton;
	@FXML
	Button rightSideButton;
	@FXML
	Button displayWordButton;
	@FXML
	Button startBlueButton;
	@FXML
	Button goodBlue;
	@FXML
	Button failBlue;
	@FXML
	Button startRedButton;
	@FXML
	Button goodRed;
	@FXML
	Button failRed;
	@FXML
	Label blueTimerLabel;
	@FXML
	Label redTimerLabel;
	@FXML
	Label bluePointsLabel;
	@FXML
	Label redPointsLabel;
    @FXML
    Pane teamOneColorPane;
    @FXML
    Pane teamTwoColorPane;
    @FXML
    Label teamOneName;
    @FXML
    Label teamTwoName;

	Integer startTime;
    Integer countdownTimer;
	Timer countdown;


	List<Integer> displayedOnes = new ArrayList<>();
	
	public void initialize(){

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GameSettingsWindow.fxml"));
        try {
            loader.load();
            GameSettingsWindowController ctl = (GameSettingsWindowController) loader.getController();
            startTime=ctl.durationComboBox.getSelectionModel().getSelectedItem().intValue();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        countdownTimer = startTime;
		vBoxright.setDisable(true);
		vBoxLeft.setDisable(true);
		leftSidebutton.setFocusTraversable(false);
		rightSideButton.setFocusTraversable(false);
	}


	public void showWord() throws SQLException {
		displayWordButton.setDisable(true);
		Connection veza = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/JDBC-PICTIONARY", "admin", "admin");
		Statement statementWord = veza.createStatement();
		ResultSet resultWords = statementWord.executeQuery("SELECT * FROM PICTIONARY.WORDS");
		List <PictionaryWord> wordList = new ArrayList<>();
		while (resultWords.next()) {
			Integer ID = resultWords.getInt("ID");
			String word = resultWords.getString("WORD_ATOM");
			String translation = resultWords.getString("TRANSLATION");
			String difficulty = resultWords.getString("DIFFICULTY");
			PictionaryWord allWords = new PictionaryWord(ID,word, translation, difficulty);
			wordList.add(allWords);
		}
		Integer m = wordList.size();
		maxLabel.setText(m.toString());
		Random rand = new Random();
		int n;
		if(displayedOnes.size()>=150){
			wordLabel.setText("No more words");
			wordLabel.setStyle("-fx-text-fill: blue;");
		}
		else{
			do {
				n = rand.nextInt(wordList.size()-1) + 1;
			} while(displayedOnes.contains(n));

			displayedOnes.add(n);
			wordLabel.setText(wordList.get(n-1).getWord());
			translationLabel.setText(wordList.get(n-1).getTranslatin());
			if(wordList.get(n-1).getDifficulty().equals("Easy")) {
				wordLabel.setStyle("-fx-text-fill: green;");
			}
			if(wordList.get(n-1).getDifficulty().equals("Medium")) {
				wordLabel.setStyle("-fx-text-fill: orange;");
			}
			if(wordList.get(n-1).getDifficulty().equals("Hard")) {
				wordLabel.setStyle("-fx-text-fill: red;");
			}
			Integer x = displayedOnes.size();
			Integer y = wordList.size();
			BigDecimal postotak = new BigDecimal(x.doubleValue()/y.doubleValue());
			progressBar.setProgress(postotak.doubleValue());
			progressLabel.setText(x.toString());
		}
		veza.close();
	}

	public void toggleMainButtons(Boolean state){
		displayWordButton.setDisable(state);
		leftSidebutton.setDisable(state);
		rightSideButton.setDisable(state);
	}

	public void nextRoundLabelPrepare(){
		wordLabel.setText("Pictionary");
		translationLabel.setText("");
	}

	public void bluePlays(){
		if(wordLabel.getText().equals("Pictionary")){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.setAlwaysOnTop(true);
			alert.setTitle(null);
			alert.setHeaderText(null);
			alert.setContentText("You can not start game without given word");

			alert.showAndWait();
		}
		else{
			vBoxLeft.setDisable(false);
			goodBlue.setDisable(true);
			failBlue.setDisable(true);
			toggleMainButtons(true);
		}

	}
	public void startBlue(){
		startBlueButton.setDisable(true);
		goodBlue.setDisable(false);
		failBlue.setDisable(false);
		failBlue.setFocusTraversable(false);
		goodBlue.setFocusTraversable(false);
		countdown = new Timer();
		countdown.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if(countdownTimer > 0)
				{
					Platform.runLater(() -> blueTimerLabel.setText(countdownTimer.toString()));
                    countdownTimer--;
				}
				else
					countdown.cancel();
			}
		}, 1000,1000);
	}
	public void blueWins(){
		countdown.cancel();
        countdownTimer = startTime;
		blueTimerLabel.setText(countdownTimer.toString());
		Integer points = Integer.parseInt(bluePointsLabel.getText());
		points++;
		bluePointsLabel.setText(points.toString());
		startBlueButton.setDisable(false);
		vBoxLeft.setDisable(true);
		toggleMainButtons(false);
		nextRoundLabelPrepare();
	}
	public void blueFails(){
		countdown.cancel();
        countdownTimer = startTime;
		blueTimerLabel.setText(countdownTimer.toString());
		startBlueButton.setDisable(false);
		vBoxLeft.setDisable(true);
		toggleMainButtons(false);
		nextRoundLabelPrepare();
	}

	public void redPlays(){
		if(wordLabel.getText().equals("Pictionary")){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.setAlwaysOnTop(true);
			alert.setTitle(null);
			alert.setHeaderText(null);
			alert.setContentText("You can not start game without given word");

			alert.showAndWait();
		}
		else{
			vBoxright.setDisable(false);
			goodRed.setDisable(true);
			failRed.setDisable(true);
			toggleMainButtons(true);
		}

	}
	public void startRed(){
		startRedButton.setDisable(true);
		goodRed.setDisable(false);
		failRed.setDisable(false);
		failRed.setFocusTraversable(false);
		goodRed.setFocusTraversable(false);
		countdown = new Timer();
		countdown.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if(countdownTimer > 0)
				{
					Platform.runLater(() -> redTimerLabel.setText(countdownTimer.toString()));
                    countdownTimer--;
				}
				else
					countdown.cancel();
			}
		}, 1000,1000);
	}
	public void redWins(){
		countdown.cancel();
        countdownTimer = startTime;
		redTimerLabel.setText(countdownTimer.toString());
		Integer points = Integer.parseInt(redPointsLabel.getText());
		points++;
		redPointsLabel.setText(points.toString());
		startRedButton.setDisable(false);
		vBoxright.setDisable(true);
		toggleMainButtons(false);
		nextRoundLabelPrepare();
	}
	public void redFails(){
		countdown.cancel();
        countdownTimer = startTime;
		redTimerLabel.setText(countdownTimer.toString());
		startRedButton.setDisable(false);
		vBoxright.setDisable(true);
		toggleMainButtons(false);
		nextRoundLabelPrepare();
	}

	public void newGame(){
		bluePointsLabel.setText("0");
		redPointsLabel.setText("0");
		displayedOnes.clear();
		maxLabel.setText("0");
		progressBar.setProgress(0);
	}
	public void newWord(){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("NewWordWindow.fxml"));
		try {
			loader.load();
			NewWordWindowController ctl = (NewWordWindowController) loader.getController();

			BorderPane root = (BorderPane) loader.getRoot();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setAlwaysOnTop(true);
			stage.initStyle(StageStyle.UTILITY);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void changeDuration(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GameSettingsWindow.fxml"));
        try {
            loader.load();
            GameSettingsWindowController ctl = (GameSettingsWindowController) loader.getController();
            switch (startTime){
                case 30:
                    ctl.durationComboBox.getSelectionModel().select(0);
                    break;
                case 45:
                    ctl.durationComboBox.getSelectionModel().select(1);
                    break;
                case 60:
                    ctl.durationComboBox.getSelectionModel().select(2);
                    break;
            }
            BorderPane root = (BorderPane) loader.getRoot();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e->{
                startTime = ctl.durationComboBox.getSelectionModel().getSelectedItem().intValue();
                countdownTimer = startTime;
                redTimerLabel.setText(startTime.toString());
                blueTimerLabel.setText(startTime.toString());
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void openTeamSettings(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TeamSettingsWindow.fxml"));
        try {
            loader.load();
            TeamSettingsWindowController ctl = (TeamSettingsWindowController) loader.getController();
            Color color1 = (Color) teamOneColorPane.getBackground().getFills().get(0).getFill();
            Color color2 = (Color) teamTwoColorPane.getBackground().getFills().get(0).getFill();
            ctl.teamOneColor.setValue(color1);
            ctl.teamTwoColor.setValue(color2);
            ctl.teamOneName.setText(teamOneName.getText());
            ctl.teamTwoName.setText(teamTwoName.getText());
            BorderPane root = (BorderPane) loader.getRoot();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e->{
                Color color3 = ctl.teamOneColor.getValue();
                teamOneColorPane.setStyle("-fx-background-color:"+color3.toString().replace("0x","")+";");
                Color color4 = ctl.teamTwoColor.getValue();
                teamTwoColorPane.setStyle("-fx-background-color:"+color4.toString().replace("0x","")+";");
                teamOneName.setText(ctl.teamOneName.getText());
                teamTwoName.setText(ctl.teamTwoName.getText());
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
