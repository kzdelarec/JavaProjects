package hr.java.vjezba.javafx;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.regex.Pattern;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.OpisTlaka;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTlakaZraka;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TlakController {
	public Integer Id;

	// --------senzor tlaka
	@FXML
	public TextField vrijednostTlakTextField;
	@FXML
	public ComboBox<RadSenzora> radTlakCombobox;
	@FXML
	public ComboBox<OpisTlaka> opisTlakCombobox;
	@FXML
	public CheckBox aktivanTlak;
	@FXML
	public Button spremiButton;
	
	@FXML
	public void initialize() throws SQLException, IOException {

		opisTlakCombobox.getItems().addAll(OpisTlaka.values());
		radTlakCombobox.getItems().addAll(RadSenzora.values());
		

	}

	public void spremi() throws SQLException, IOException {
		boolean greska = false;
		opisTlakCombobox.setStyle(null);
		vrijednostTlakTextField.setStyle(null);
		radTlakCombobox.setStyle(null);
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Pogreška pri spremanju");

		if ((!Pattern.matches("[0-9-.]+", vrijednostTlakTextField.getText()))
				&& (!vrijednostTlakTextField.getText().isEmpty())) {
			alert.setContentText(
					alert.getContentText() + "Podaci za vrijednosti senzora moraju biti brojevnog tipa!\n");
			greska = true;
		}

		if (vrijednostTlakTextField.getText().isEmpty() || radTlakCombobox.getSelectionModel().isEmpty()) {
			alert.setContentText(alert.getContentText() + "Popunite sva polja kako biste spremili podatke!\n");
			greska = true;
		}
		if (opisTlakCombobox.getSelectionModel().isEmpty()) {
			opisTlakCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}

		if (vrijednostTlakTextField.getText().isEmpty()) {
			vrijednostTlakTextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}

		if (radTlakCombobox.getSelectionModel().isEmpty()) {
			radTlakCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}


		if (greska == true) {
			alert.showAndWait();
		}
		if (greska == false) {
			// ------------temperatura----------------
			BigDecimal vrijednostTlak = new BigDecimal(vrijednostTlakTextField.getText());
			RadSenzora radTlak = radTlakCombobox.getValue();
			OpisTlaka opis = opisTlakCombobox.getValue();
			String opisTlaka = opis.name();
			//int noviIdTlak = getZadnjiIdTlak() + 1;
			SenzorTlakaZraka tlak = new SenzorTlakaZraka("hPa", (byte) 2, opisTlaka);
			tlak.setId(Id);
			if(aktivanTlak.isSelected()) {
				tlak.setIsAktivan(true);
				tlak.setStatus("Aktivan");
			}
			if(!aktivanTlak.isSelected()) {
				tlak.setIsAktivan(false);
				tlak.setStatus("Neaktivan");
			}
			tlak.setVrijednost(vrijednostTlak);
			tlak.setRadSenzora(radTlak);
			BazaPodataka.updateTlak(tlak);
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			SenzoriController.spremiSenzor((Senzor)tlak);
		}

	}
}
