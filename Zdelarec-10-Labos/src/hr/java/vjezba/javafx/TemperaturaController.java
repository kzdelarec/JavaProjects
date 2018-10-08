package hr.java.vjezba.javafx;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.regex.Pattern;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TemperaturaController {
	public Integer Id;
	@FXML
	public TextField komponentaTextField;
	@FXML
	public TextField vrijednostTempTextField;
	@FXML
	public ComboBox<RadSenzora> radTempCombobox;
	
	@FXML
	public Button spremiButton;
	@FXML
	public CheckBox aktivanTemp;

	@FXML
	public void initialize() throws SQLException, IOException {

		radTempCombobox.getItems().addAll(RadSenzora.values());

	}

	public void spremi() throws SQLException, IOException {
		boolean greska = false;
		komponentaTextField.setStyle(null);
		vrijednostTempTextField.setStyle(null);
		radTempCombobox.setStyle(null);
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Pogreška pri spremanju");

		if ((!Pattern.matches("[0-9-.]+", vrijednostTempTextField.getText()))
				&& (!vrijednostTempTextField.getText().isEmpty())) {
			alert.setContentText(
					alert.getContentText() + "Podaci za vrijednosti senzora moraju biti brojevnog tipa!\n");
			greska = true;
		}

		if (vrijednostTempTextField.getText().isEmpty() || radTempCombobox.getSelectionModel().isEmpty()) {
			alert.setContentText(alert.getContentText() + "Popunite sva polja kako biste spremili podatke!\n");
			greska = true;
		}
		if (komponentaTextField.getText().isEmpty()) {
			komponentaTextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}

		if (vrijednostTempTextField.getText().isEmpty()) {
			vrijednostTempTextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}

		if (radTempCombobox.getSelectionModel().isEmpty()) {
			radTempCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}


		if (greska == true) {
			alert.showAndWait();
		}
		if (greska == false) {
			// ------------temperatura----------------
			String komponenta = komponentaTextField.getText();
			BigDecimal vrijednostTemp = new BigDecimal(vrijednostTempTextField.getText());
			RadSenzora radTemp = radTempCombobox.getValue();
			// int noviIdTemp = getZadnjiIdTemp() + 1;
			SenzorTemperature temperatura = new SenzorTemperature("°C", (byte) 2, komponenta);
			temperatura.setId(Id);
			if (aktivanTemp.isSelected()) {
				temperatura.setIsAktivan(true);
				temperatura.setStatus("Aktivan");
			}
			if (!aktivanTemp.isSelected()) {
				temperatura.setIsAktivan(false);
				temperatura.setStatus("Neaktivan");
			}
			temperatura.setVrijednost(vrijednostTemp);
			temperatura.setRadSenzora(radTemp);
			BazaPodataka.updateTemp(temperatura);

			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			SenzoriController.spremiSenzor((Senzor)temperatura);
		}

	}
}
