package hr.java.vjezba.javafx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
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

public class DodajMjernuPostajuController {

	@FXML
	private TextField nazivTextField;
	@FXML
	private ComboBox<Mjesto> mjestoCombobox;
	@FXML
	private Button spremiButton;
	@FXML
	private TextField koordinataXtextField;
	@FXML
	private TextField koordinataYtextField;
	@FXML
	private ComboBox<Senzor> senzoriTempCombobox;
	@FXML
	private ComboBox<Senzor> senzoriTlakaCombobox;
	@FXML
	private ComboBox<Senzor> senzoriVlageCombobox;

	private List<Mjesto> svaMjesta;
	private List<VrstaMjesta> vrsteMjesta;
	private ObservableList<Drzava> listaDrzava;
	static ObservableList<Mjesto> observableListaMjesta;
	private List<SenzorTemperature> listaTemp;
	private List<SenzorTlakaZraka> listaTlak;
	private List<SenzorVlage> listaVlage;

	@FXML
	public void initialize() {
		svaMjesta = Main.dohvatiMjesta();
		observableListaMjesta = FXCollections.observableArrayList(Main.dohvatiMjesta());
		listaTemp = Main.listaTemp;
		listaTlak = Main.listaTlaka;
		listaVlage = Main.listaVlage;

		mjestoCombobox.getItems().addAll(observableListaMjesta);
		mjestoCombobox.itemsProperty().toString();
		senzoriTempCombobox.getItems().addAll(listaTemp);
		senzoriTlakaCombobox.getItems().addAll(listaTlak);
		senzoriVlageCombobox.getItems().addAll(listaVlage);
	}

	public void spremi() {
		boolean greska = false;
		nazivTextField.setStyle(null);
		koordinataXtextField.setStyle(null);
		koordinataYtextField.setStyle(null);
		mjestoCombobox.setStyle(null);
		senzoriTempCombobox.setStyle(null);
		senzoriTlakaCombobox.setStyle(null);
		senzoriVlageCombobox.setStyle(null);
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Pogreška pri spremanju");
		if (!Pattern.matches("[A-Z ]+", nazivTextField.getText().toUpperCase()) && !nazivTextField.getText().isEmpty()) {
			alert.setContentText(alert.getContentText() + "Podaci za naziv moraju biti znakovnog tipa!\n");
			greska = true;
		}
		if ((!Pattern.matches("[0-9]+", koordinataXtextField.getText()) || !Pattern.matches("[0-9]+", koordinataYtextField.getText())) && (!koordinataXtextField.getText().isEmpty() && !koordinataYtextField.getText().isEmpty())) {
			alert.setContentText(alert.getContentText() + "Podaci za koordinate moraju biti brojevnog tipa!\n");
			greska = true;
		}
		if (nazivTextField.getText().isEmpty() || koordinataXtextField.getText().isEmpty()
				|| koordinataYtextField.getText().isEmpty() || mjestoCombobox.getSelectionModel().isEmpty()
				|| senzoriTempCombobox.getSelectionModel().isEmpty()
				|| senzoriTlakaCombobox.getSelectionModel().isEmpty()
				|| senzoriVlageCombobox.getSelectionModel().isEmpty()) {
			alert.setContentText(alert.getContentText() + "Popunite sva polja kako biste spremili podatke!\n");
			greska = true;
		}
		
		if(nazivTextField.getText().isEmpty()) {
			nazivTextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(koordinataXtextField.getText().isEmpty()) {
			koordinataXtextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(koordinataYtextField.getText().isEmpty()) {
			koordinataYtextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(mjestoCombobox.getSelectionModel().isEmpty()) {
			mjestoCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(senzoriTempCombobox.getSelectionModel().isEmpty()) {
			senzoriTempCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(senzoriTlakaCombobox.getSelectionModel().isEmpty()) {
			senzoriTlakaCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}

		if(senzoriVlageCombobox.getSelectionModel().isEmpty()) {
			senzoriVlageCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}

		
		if (greska == true) {
			alert.showAndWait();
		}
		if (greska == false) {
			String naziv = nazivTextField.getText();
			BigDecimal geoX = new BigDecimal(koordinataXtextField.getText());
			BigDecimal geoY = new BigDecimal(koordinataYtextField.getText());
			GeografskaTocka geoTocka = new GeografskaTocka(geoX, geoY);
			int noviGeoId = getZadnjiIdGeo() + 1;
			File geoFile = new File("dat/geo.txt");
			{
				try (FileWriter writer = new FileWriter(geoFile, true);) {
					writer.write(noviGeoId + "\n");
					writer.write(geoX + "\n");
					writer.write(geoY + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Mjesto mjesto = mjestoCombobox.getValue();
			SenzorTemperature temperatura = (SenzorTemperature) senzoriTempCombobox.getValue();
			SenzorTlakaZraka tlak = (SenzorTlakaZraka) senzoriTlakaCombobox.getValue();
			SenzorVlage vlaga = (SenzorVlage) senzoriVlageCombobox.getValue();
			List<Senzor> listaSenzora = new ArrayList<>();
			listaSenzora.add(temperatura);
			listaSenzora.add(tlak);
			listaSenzora.add(vlaga);
			int noviId = getZadnjiId() + 1;
			MjernaPostaja mjernaPostaja = new MjernaPostaja(naziv, mjesto, geoTocka, listaSenzora, noviId);
			File postajaFile = new File("dat/mjernePostaje.txt");
			{
				try (FileWriter writer = new FileWriter(postajaFile, true);) {
					writer.write(noviId + "\n");
					writer.write(naziv + "\n");
					writer.write(mjestoCombobox.getSelectionModel().getSelectedIndex()+1+"\n");
					writer.write(senzoriTempCombobox.getSelectionModel().getSelectedIndex() + 1 + "\n");
					writer.write(senzoriTlakaCombobox.getSelectionModel().getSelectedIndex() + 1 + "\n");
					writer.write(senzoriVlageCombobox.getSelectionModel().getSelectedIndex() + 1 + "\n");
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Uspješno spremanje mjesta!");
					alert2.setHeaderText("Uspješno spremanje mjesta!");
					alert2.setContentText("Uneseni podaci za mjesto su uspješnospremljeni.");
					Stage stage = (Stage) spremiButton.getScene().getWindow();
					stage.close();
					PostajeController.dodajNovePostaje(mjernaPostaja);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private int getZadnjiIdGeo() {
		return Main.koordinateIzDatoteke.values().size();
	}

	private int getZadnjiId() {
		return Main.postajeIzDatoteke.values().size();
	}

}
