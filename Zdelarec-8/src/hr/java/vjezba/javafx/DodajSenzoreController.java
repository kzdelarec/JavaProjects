package hr.java.vjezba.javafx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.OpisTlaka;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorTlakaZraka;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

	private List<Mjesto> svaMjesta;
	private List<VrstaMjesta> vrsteMjesta;
	private ObservableList<Drzava> listaDrzava;
	static ObservableList<Mjesto> observableListaMjesta;
	private List<Senzor> listaTemp;
	private List<Senzor> listaVlage;
	private List<Senzor> listaTlaka;

	@FXML
	public void initialize() {
		svaMjesta = Main.dohvatiMjesta();
		observableListaMjesta = FXCollections.observableArrayList(Main.dohvatiMjesta());

		listaDrzava = FXCollections.observableArrayList(Main.dohvatiDrzave());

		radTempCombobox.getItems().addAll(RadSenzora.values());
		radTlakCombobox.getItems().addAll(RadSenzora.values());
		radVlagaCombobox.getItems().addAll(RadSenzora.values());
		opisTlakCombobox.getItems().addAll(OpisTlaka.values());

	}

	public void spremi() {
		boolean greska = false;
		komponentaTextField.setStyle(null);
		vrijednostTempTextField.setStyle(null);
		vrijednostTlakTextField.setStyle(null);
		vrijednostVlagaTextField.setStyle(null);
		opisTlakCombobox.setStyle(null);
		radTempCombobox.setStyle(null);
		radTlakCombobox.setStyle(null);
		radVlagaCombobox.setStyle(null);
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Pogreška pri spremanju");

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
		}
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
			alert.showAndWait();
		}
		if (greska == false) {
			// ------------temperatura----------------
			String komponenta = komponentaTextField.getText();
			BigDecimal vrijednostTemp = new BigDecimal(vrijednostTempTextField.getText());
			RadSenzora radTemp = radTempCombobox.getValue();
			int noviIdTemp = getZadnjiIdTemp() + 1;
			SenzorTemperature temperatura = new SenzorTemperature("°C", (byte) 2, komponenta);
			temperatura.setVrijednost(vrijednostTemp);
			temperatura.setRadSenzora(radTemp);
			File temperaturaFile = new File("dat/temperatura.txt");
			{
				try (FileWriter writer = new FileWriter(temperaturaFile, true);) {
					writer.write(noviIdTemp + "\n");
					writer.write(komponenta + "\n");
					writer.write(vrijednostTemp + "\n");
					writer.write(radTemp + "\n");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// ------------vlaga----------------
			BigDecimal vrijednostVlaga = new BigDecimal(vrijednostVlagaTextField.getText());
			RadSenzora radVlaga = radVlagaCombobox.getValue();
			int noviIdVlaga = getZadnjiIdVlaga() + 1;
			SenzorVlage vlaga = new SenzorVlage("%", (byte) 2);
			vlaga.setVrijednost(vrijednostVlaga);
			vlaga.setRadSenzora(radVlaga);
			File vlagaFile = new File("dat/vlaga.txt");
			{
				try (FileWriter writer = new FileWriter(vlagaFile, true);) {
					writer.write(noviIdVlaga + "\n");
					writer.write(vrijednostVlaga + "\n");
					writer.write(radTemp + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// ------------tlak----------------
			BigDecimal vrijednostTlak = new BigDecimal(vrijednostTlakTextField.getText());
			RadSenzora radTlak = radTlakCombobox.getValue();
			OpisTlaka opis = opisTlakCombobox.getValue();
			String opisTlaka = opis.name();
			int noviIdTlak = getZadnjiIdTlak() + 1;
			SenzorTlakaZraka tlak = new SenzorTlakaZraka("%", (byte) 2, opisTlaka);
			tlak.setVrijednost(vrijednostTlak);
			tlak.setRadSenzora(radTlak);
			File tlakFile = new File("dat/tlak.txt");
			{
				try (FileWriter writer = new FileWriter(tlakFile, true);) {
					writer.write(noviIdTlak + "\n");
					writer.write(opisTlaka + "\n");
					writer.write(vrijednostTlak + "\n");
					writer.write(radTemp + "\n");
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Uspješno spremanje mjesta!");
					alert2.setHeaderText("Uspješno spremanje mjesta!");
					alert2.setContentText("Uneseni podaci za mjesto su uspješnospremljeni.");
					Stage stage = (Stage) spremiButton.getScene().getWindow();
					stage.close();
					SenzoriController.dodajNoveSenzore(temperatura, vlaga, tlak);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private int getZadnjiIdTlak() {
		return Main.mapaTlaka.values().size();
	}

	private int getZadnjiIdVlaga() {
		return Main.mapaVlage.values().size();
	}

	public int getZadnjiIdTemp() {
		return Main.mapaTemp.values().size();
	}

}
