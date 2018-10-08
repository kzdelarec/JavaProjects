package ht.java.pictionary.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GameSettingsWindowController {

    List <Integer> durationList = new ArrayList<>();
    @FXML
    ComboBox<Integer> durationComboBox;

    public void initialize(){
        durationList.add(30);
        durationList.add(45);
        durationList.add(60);
        durationComboBox.getItems().setAll(durationList);
        durationComboBox.getSelectionModel().select(2);
    }

    public void save(){
        Stage stage = (Stage) durationComboBox.getScene().getWindow();
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
