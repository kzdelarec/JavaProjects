package hr.java.vjezba.javafx;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.Mjesto;
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

public class DodajZupanijeController {
	
	@FXML
	private TextField nazivTextField;
	@FXML
	private ComboBox<Drzava> drzavaCombobox;
	@FXML
	private Button spremiButton;
	
	private List<Zupanija> sveZupanije;
	private ObservableList<Drzava> listaDrzava;
	static ObservableList<Mjesto> observableListaMjesta;
	
	
	@FXML
	public void initialize() throws SQLException, IOException {
		sveZupanije=BazaPodataka.dohvatiZupanije();
		observableListaMjesta = FXCollections.observableArrayList(BazaPodataka.dohvatiMjesto());
		listaDrzava = FXCollections.observableArrayList(BazaPodataka.dohvatiDrzave());
		drzavaCombobox.getItems().addAll(listaDrzava);
		drzavaCombobox.itemsProperty().toString();
		
		if(ZupanijaController.promjena==true) {
			nazivTextField.setText(ZupanijaController.nazivPromjena);
			drzavaCombobox.getSelectionModel()
			.select(listaDrzava.stream().filter(drz->drz.getId() == ZupanijaController.drzavaId).findFirst().get());
			spremiButton.setText("Spremi");
		}
	}
	
	public void spremi() throws SQLException, IOException{
		boolean greska = false;
		nazivTextField.setStyle(null);
		drzavaCombobox.setStyle(null);
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Pogreška pri spremanju");
		if (!Pattern.matches("[A-Z ]+", nazivTextField.getText().toUpperCase()) && !nazivTextField.getText().isEmpty()) {
			alert.setContentText(alert.getContentText() + "Podaci za naziv moraju biti znakovnog tipa!\n");
			greska = true;
		}
		
		if (nazivTextField.getText().isEmpty() 
				|| drzavaCombobox.getSelectionModel().isEmpty()) {
			alert.setContentText(alert.getContentText() + "Popunite sva polja kako biste spremili podatke!\n");
			greska = true;
		}
		
		if(nazivTextField.getText().isEmpty()) {
			nazivTextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if(drzavaCombobox.getSelectionModel().isEmpty()) {
			drzavaCombobox.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}

		if (greska == true) {
			alert.showAndWait();
		}
		if(greska==false && ZupanijaController.promjena==false) {
			String naziv = nazivTextField.getText();
			Drzava drzava = drzavaCombobox.getValue();
			int noviId = getZadnjiId() +1;
			Zupanija zupanija = new Zupanija(naziv, drzava,noviId);
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			BazaPodataka.spremiZupaniju(zupanija);
			stage.close();
			ZupanijaController.dodajNovuZupaniju(zupanija);
			
		}
		if(greska==false && ZupanijaController.promjena==true) {
			String naziv = nazivTextField.getText();
			Drzava drzava = drzavaCombobox.getValue();
			Zupanija zupanija = new Zupanija(naziv, drzava);
			zupanija.setId(ZupanijaController.zupanijaId);
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			BazaPodataka.updateZupanija(zupanija);
			stage.close();
			ZupanijaController.dodajNovuZupaniju(zupanija);
			ZupanijaController.promjena=false;
			
		}
	}
	
	public int getZadnjiId() {
		return sveZupanije.size();
	}

}
