package ht.java.pictionary.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewWordWindowController {

    @FXML
    TextField newWordTextField;
    @FXML
    TextField newWordTranslationTextField;
    @FXML
    ComboBox<difficultyEnum> difficultyComboBox;

    public void initialize(){
        difficultyComboBox.getItems().addAll(difficultyEnum.values());
        difficultyComboBox.getSelectionModel().select(0);
    }

    public void addNewWord() throws SQLException {
        Connection veza = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/JDBC-PICTIONARY", "admin", "admin");
        try {
            PreparedStatement insertWord = veza
                    .prepareStatement("INSERT INTO PICTIONARY.WORDS(WORD_ATOM, TRANSLATION, DIFFICULTY)"
                            + " VALUES (?,?,?);");
            insertWord.setString(1, newWordTextField.getText());
            insertWord.setString(2, newWordTranslationTextField.getText());
            insertWord.setString(3, difficultyComboBox.getSelectionModel().getSelectedItem().toString());
            insertWord.executeUpdate();
            veza.commit();
            veza.setAutoCommit(true);
        } catch (Throwable ex) {
            veza.rollback();
            throw ex;
        }
        newWordTextField.clear();
        newWordTextField.requestFocus();
        newWordTranslationTextField.clear();
        difficultyComboBox.getSelectionModel().select(0);

        veza.close();
        Stage stage = (Stage) newWordTextField.getScene().getWindow();
        stage.close();
    }
}
