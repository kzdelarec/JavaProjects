package hr.java.vjezba.javafx;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.regex.Pattern;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorVlage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class VlagaController {
	public Integer Id;

	// --------senzor tlaka
	@FXML
	public TextField vrijednostVlagaTextField;
	@FXML
	public ComboBox<RadSenzora> radVlagaCombobox;
	@FXML
	public CheckBox aktivanVlaga;
	@FXML
	public Button spremiButton;
	
	@FXML
	public void initialize() throws SQLException, IOException {

		radVlagaCombobox.getItems().addAll(RadSenzora.values());
		

	}

	public void spremi() throws SQLException, IOException {
		boolean greska = false;
		vrijednostVlagaTextField.setStyle(null);
		radVlagaCombobox.setStyle(null);
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Pogreška pri spremanju");

		if ((!Pattern.matches("[0-9-.]+", vrijednostVlagaTextField.getText()))
				&& (!vrijednostVlagaTextField.getText().isEmpty())) {
			alert.setContentText(
					alert.getContentText() + "Podaci za vrijednosti senzora moraju biti brojevnog tipa!\n");
			greska = true;
		}

		if (vrijednostVlagaTextField.getText().isEmpty() || radVlagaCombobox.getSelectionModel().isEmpty()) {
			alert.setContentText(alert.getContentText() + "Popunite sva polja kako biste spremili podatke!\n");
			greska = true;
		}

		if (vrijednostVlagaTextField.getText().isEmpty()) {
			vrijednostVlagaTextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}

		if (radVlagaCombobox.getSelectionModel().isEmpty()) {
			radVlagaCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}


		if (greska == true) {
			alert.showAndWait();
		}
		if (greska == false) {
			BigDecimal vrijednostVlaga = new BigDecimal(vrijednostVlagaTextField.getText());
			RadSenzora radVlaga = radVlagaCombobox.getValue();
			SenzorVlage vlaga = new SenzorVlage("%", (byte) 2);
			vlaga.setId(Id);
			if(aktivanVlaga.isSelected()) {
				vlaga.setIsAktivan(true);
				vlaga.setStatus("Aktivan");
			}
			if(!aktivanVlaga.isSelected()) {
				vlaga.setIsAktivan(false);
				vlaga.setStatus("Neaktivan");
			}
			vlaga.setVrijednost(vrijednostVlaga);
			vlaga.setRadSenzora(radVlaga);
			BazaPodataka.updateVlaga(vlaga);
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			SenzoriController.spremiSenzor((Senzor)vlaga);
		}

	}
}
