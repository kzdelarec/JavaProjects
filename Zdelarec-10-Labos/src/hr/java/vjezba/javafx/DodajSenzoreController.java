package hr.java.vjezba.javafx;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.regex.Pattern;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.OpisTlaka;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorTlakaZraka;
import hr.java.vjezbe.entitet.SenzorVlage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DodajSenzoreController {

	// --------senzor temperature
	@FXML
	private TextField komponentaTextField;
	@FXML
	private TextField vrijednostTempTextField;
	@FXML
	private ComboBox<RadSenzora> radTempCombobox;
	// --------senzor tlaka
	@FXML
	private TextField vrijednostTlakTextField;
	@FXML
	private ComboBox<RadSenzora> radTlakCombobox;
	@FXML
	private ComboBox<OpisTlaka> opisTlakCombobox;
	// --------senzor vlage
	@FXML
	private TextField vrijednostVlagaTextField;
	@FXML
	private ComboBox<RadSenzora> radVlagaCombobox;
	@FXML
	private Button spremiButton;
	@FXML
	private CheckBox aktivanTemp;
	@FXML
	private CheckBox aktivanVlaga;
	@FXML
	private CheckBox aktivanTlak;

	@FXML
	public void initialize() throws SQLException, IOException {
		
		radTempCombobox.getItems().addAll(RadSenzora.values());
		radTlakCombobox.getItems().addAll(RadSenzora.values());
		radVlagaCombobox.getItems().addAll(RadSenzora.values());
		opisTlakCombobox.getItems().addAll(OpisTlaka.values());
		
	}

	Thread thread = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				spremi();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Stage stage = (Stage) spremiButton.getScene().getWindow();
						stage.close();	
					}	
				});
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
	
	public void pokreni() {
		thread.start();
	}
	
	public void spremi() throws SQLException, IOException {
		boolean greska = false;
		komponentaTextField.setStyle(null);
		vrijednostTempTextField.setStyle(null);
		vrijednostTlakTextField.setStyle(null);
		vrijednostVlagaTextField.setStyle(null);
		opisTlakCombobox.setStyle(null);
		radTempCombobox.setStyle(null);
		radTlakCombobox.setStyle(null);
		radVlagaCombobox.setStyle(null);
		//Alert alert = new Alert(AlertType.WARNING);
		/*alert.setTitle("Pogreška pri spremanju");

		if ((!Pattern.matches("[0-9]+", vrijednostTempTextField.getText())
				|| !Pattern.matches("[0-9]+", vrijednostTlakTextField.getText())
				|| !Pattern.matches("[0-9]+", vrijednostVlagaTextField.getText()))
				&& (!vrijednostTempTextField.getText().isEmpty() 
				&& !vrijednostTlakTextField.getText().isEmpty()
				&& !vrijednostVlagaTextField.getText().isEmpty())) {
			alert.setContentText(alert.getContentText() + "Podaci za vrijednosti senzora moraju biti brojevnog tipa!\n");
			greska = true;
		}

		if (vrijednostTempTextField.getText().isEmpty() || vrijednostTlakTextField.getText().isEmpty()
				|| vrijednostVlagaTextField.getText().isEmpty() || radTempCombobox.getSelectionModel().isEmpty()
				|| radTlakCombobox.getSelectionModel().isEmpty()
				|| radVlagaCombobox.getSelectionModel().isEmpty()
				|| opisTlakCombobox.getSelectionModel().isEmpty()) {
			alert.setContentText(alert.getContentText() + "Popunite sva polja kako biste spremili podatke!\n");
			greska = true;
		}*/
		if(komponentaTextField.getText().isEmpty()) {
			komponentaTextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(vrijednostTempTextField.getText().isEmpty()) {
			vrijednostTempTextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(vrijednostTlakTextField.getText().isEmpty()) {
			vrijednostTlakTextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(vrijednostVlagaTextField.getText().isEmpty()) {
			vrijednostVlagaTextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(radTempCombobox.getSelectionModel().isEmpty()) {
			radTempCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(radTlakCombobox.getSelectionModel().isEmpty()) {
			radTlakCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(radVlagaCombobox.getSelectionModel().isEmpty()) {
			radVlagaCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}

		if(opisTlakCombobox.getSelectionModel().isEmpty()) {
			opisTlakCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}

		
		if (greska == true) {
			//alert.showAndWait();
		}
		if (greska == false) {
			// ------------temperatura----------------
			String komponenta = komponentaTextField.getText();
			BigDecimal vrijednostTemp = new BigDecimal(vrijednostTempTextField.getText());
			RadSenzora radTemp = radTempCombobox.getValue();
			//int noviIdTemp = getZadnjiIdTemp() + 1;
			SenzorTemperature temperatura = new SenzorTemperature("°C", (byte) 2, komponenta);
			if(aktivanTemp.isSelected()) {
				temperatura.setIsAktivan(true);
				temperatura.setStatus("Aktivan");
			}
			if(!aktivanTemp.isSelected()) {
				temperatura.setIsAktivan(false);
				temperatura.setStatus("Neaktivan");
			}
			temperatura.setVrijednost(vrijednostTemp);
			temperatura.setRadSenzora(radTemp);
			BazaPodataka.spremiSenzorTemp(temperatura);
			
			// ------------vlaga----------------
			BigDecimal vrijednostVlaga = new BigDecimal(vrijednostVlagaTextField.getText());
			RadSenzora radVlaga = radVlagaCombobox.getValue();
			//int noviIdVlaga = getZadnjiIdVlaga() + 1;
			SenzorVlage vlaga = new SenzorVlage("%", (byte) 2);
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
			BazaPodataka.spremiSenzorVlage(vlaga);
			
			// ------------tlak----------------
			BigDecimal vrijednostTlak = new BigDecimal(vrijednostTlakTextField.getText());
			RadSenzora radTlak = radTlakCombobox.getValue();
			OpisTlaka opis = opisTlakCombobox.getValue();
			String opisTlaka = opis.name();
			//int noviIdTlak = getZadnjiIdTlak() + 1;
			SenzorTlakaZraka tlak = new SenzorTlakaZraka("hPa", (byte) 2, opisTlaka);
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
			BazaPodataka.spremiSenzorTlaka(tlak);
			SenzoriController.dodajNoveSenzore(temperatura, vlaga, tlak);
		}
	}
	
	/*private int getZadnjiIdTlak() {
		return Main.mapaTlaka.values().size();
	}

	private int getZadnjiIdVlaga() {
		return Main.mapaVlage.values().size();
	}

	public int getZadnjiIdTemp() {
		return Main.mapaTemp.values().size();
	}*/

}
