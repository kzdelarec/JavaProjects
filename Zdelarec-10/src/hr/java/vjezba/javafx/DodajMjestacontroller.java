package hr.java.vjezba.javafx;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajMjestacontroller {

	@FXML
	public TextField nazivTextField;
	@FXML
	public ComboBox<Zupanija> zupanijaCombobox;
	@FXML
	public ComboBox<VrstaMjesta> vrstaCombobox;
	@FXML
	public Button spremiButton;
	
	public Integer Id;
	public Boolean promjena = false;
	
	private List<Mjesto> svaMjesta;
	public ObservableList<Zupanija> listaZupanija;
	static ObservableList<Mjesto> observableListaMjesta;
	
	
	@FXML
	public void initialize() throws SQLException, IOException {
		svaMjesta=BazaPodataka.dohvatiMjesto();
		observableListaMjesta = FXCollections.observableArrayList(BazaPodataka.dohvatiMjesto());

		listaZupanija = FXCollections.observableArrayList(BazaPodataka.dohvatiZupanije());
		
		zupanijaCombobox.getItems().addAll(listaZupanija);
		zupanijaCombobox.itemsProperty().toString();
		vrstaCombobox.getItems().addAll(VrstaMjesta.values());
	}
	
	
	public void spremi() throws SQLException, IOException{
		boolean greska = false;
		nazivTextField.setStyle(null);
		zupanijaCombobox.setStyle(null);
		vrstaCombobox.setStyle(null);
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Pogreška pri spremanju");
		if (!Pattern.matches("[A-Z ]+", nazivTextField.getText().toUpperCase()) && !nazivTextField.getText().isEmpty()) {
			alert.setContentText(alert.getContentText() + "Podaci za naziv moraju biti znakovnog tipa!\n");
			greska = true;
		}
		
		if (nazivTextField.getText().isEmpty() 
				|| zupanijaCombobox.getSelectionModel().isEmpty()
				|| vrstaCombobox.getSelectionModel().isEmpty()) {
			alert.setContentText(alert.getContentText() + "Popunite sva polja kako biste spremili podatke!\n");
			greska = true;
		}
		
		if(nazivTextField.getText().isEmpty()) {
			nazivTextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(zupanijaCombobox.getSelectionModel().isEmpty()) {
			zupanijaCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(vrstaCombobox.getSelectionModel().isEmpty()) {
			vrstaCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if (greska == true) {
			alert.showAndWait();
		}
		if(greska==false && promjena == false) {
			String naziv = nazivTextField.getText();
			Zupanija zupanija = zupanijaCombobox.getValue();
			VrstaMjesta vrstaMjesta = vrstaCombobox.getValue();
			int noviId = getZadnjiId() +1;
			Mjesto mjesto = new Mjesto(naziv, zupanija,noviId);
			mjesto.setVrstaMjest(vrstaMjesta);
			System.out.println(mjesto.getNaziv());
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			BazaPodataka.spremiMjesto(mjesto);
			stage.close();
			MjestaController.dodajNovoMjesto(mjesto);	
		}
		
		if(greska==false && promjena == true) {
			String naziv = nazivTextField.getText();
			Zupanija zupanija = zupanijaCombobox.getValue();
			VrstaMjesta vrstaMjesta = vrstaCombobox.getValue();
			Mjesto mjesto = new Mjesto(naziv, zupanija);
			mjesto.setId(Id);
			mjesto.setVrstaMjest(vrstaMjesta);
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			BazaPodataka.updateMjesto(mjesto);
			stage.close();
			MjestaController.dodajNovoMjesto(mjesto);	
			promjena=false;
		}
	}
	
	public int getZadnjiId() {
		return svaMjesta.size();
	}
	
}
