package ht.java.pictionary.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class TeamSettingsWindowController {


    @FXML
    ColorPicker teamOneColor;
    @FXML
    ColorPicker teamTwoColor;
    @FXML
    TextField teamOneName;
    @FXML
    TextField teamTwoName;

    public void initialize(){

    }

    public void save(){
        Stage stage = (Stage) teamOneColor.getScene().getWindow();
        stage.getOnCloseRequest()
                .handle(
                        new WindowEvent(
                                stage,
                                WindowEvent.WINDOW_CLOSE_REQUEST
                        )
                );
        stage.close();
    }
}
