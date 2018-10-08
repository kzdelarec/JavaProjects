package hr.knjiznica.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.knjiznica.baza.podataka.BazaPodataka;
import hr.knjiznica.entiteti.Knjiga;
import hr.knjiznica.enumeracije.KategorijaKnjigaEnum;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajKnjiguProzorController {

	@FXML
	TextField idKnjigeTextField;
	@FXML
	TextField naslovTextField;
	@FXML
	TextField autorTextField;
	@FXML
	ComboBox<KategorijaKnjigaEnum> kategorijaComboBox;
	@FXML
	TextField godinaIzdanjaTextField;
	@FXML
	TextField izdanjeTextField;
	@FXML
	TextField izdavacTextField;
	@FXML
	TextField kolicinaTextField;

	List<Knjiga> listaKnjiga = new ArrayList<>();
	Boolean isNova = true;

	@FXML
	public void initialize() throws SQLException, IOException {
		kategorijaComboBox.getItems().setAll(KategorijaKnjigaEnum.values());
		listaKnjiga = BazaPodataka.dohvatiKnjige();
		
		kolicinaTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					try {
						dodajKnjigu();
					} catch (SQLException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	public void dodajKnjigu() throws SQLException, IOException {
		
		String naslov = naslovTextField.getText();
		String autor  =autorTextField.getText();
		String kategorija = kategorijaComboBox.getSelectionModel().getSelectedItem().toString();
		Integer izdanje = Integer.parseInt(izdanjeTextField.getText());
		String izdavac = izdavacTextField.getText();
		Integer godinaIzdanja = Integer.parseInt(godinaIzdanjaTextField.getText());
		Long barcodeId = Long.parseLong(idKnjigeTextField.getText());
		Integer kolicina = Integer.parseInt(kolicinaTextField.getText());
		Knjiga novaKnjiga = new Knjiga(barcodeId, naslov, autor, kategorija, izdanje, izdavac, godinaIzdanja, kolicina);
		
		for(Knjiga tmpKnjiga : listaKnjiga) {
			if(novaKnjiga.getBarcodeId().equals(tmpKnjiga.getBarcodeId())) {
				novaKnjiga.setID(tmpKnjiga.getID());
				novaKnjiga.setKolicina(tmpKnjiga.getKolicina()+novaKnjiga.getKolicina());
				BazaPodataka.updateKnjiga(novaKnjiga);
				isNova=false;
				listaKnjiga = BazaPodataka.dohvatiKnjige();
			}
		}
		
		if(isNova) 
			BazaPodataka.SpremiKnjigu(novaKnjiga);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText("Knjiga uspješno spremljena");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);
		alert.showAndWait();
		
		idKnjigeTextField.clear();
		naslovTextField.clear();
		autorTextField.clear();
		kategorijaComboBox.valueProperty().set(null);
		godinaIzdanjaTextField.clear();
		izdanjeTextField.clear();
		izdavacTextField.clear();
		kolicinaTextField.clear();
		idKnjigeTextField.requestFocus();
	}

}
