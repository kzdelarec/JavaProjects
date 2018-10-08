package hr.java.vjezba.javafx;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.Mjesto;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DodajDrzaveController {
	
	@FXML
	private TextField nazivTextField;
	@FXML
	private TextField povrsinaTextField;
	@FXML
	private Button spremiButton;
	
	private List<Drzava> sveDrzave;
	static ObservableList<Mjesto> observableListaMjesta;
	
	
	@FXML
	public void initialize() throws SQLException, IOException {
		sveDrzave=BazaPodataka.dohvatiDrzave();
		
		if(DrzaveController.promjena==true) {
			nazivTextField.setText(DrzaveController.nazivPromjena);
			povrsinaTextField.setText(DrzaveController.povrsinaPromjena);
			spremiButton.setText("Spremi");
		}
		
	}
	public void spremi() throws SQLException, IOException{
		boolean greska = false;
		nazivTextField.setStyle(null);
		povrsinaTextField.setStyle(null);
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Pogreška pri spremanju");
		if (!Pattern.matches("[A-Z ]+", nazivTextField.getText().toUpperCase()) && !nazivTextField.getText().isEmpty()) {
			alert.setContentText(alert.getContentText() + "Podaci za naziv moraju biti znakovnog tipa!\n");
			greska = true;
		}
		if ((!Pattern.matches("[0-9]+", povrsinaTextField.getText())) && (!povrsinaTextField.getText().isEmpty())) {
			alert.setContentText(alert.getContentText() + "Podaci za povrsinu moraju biti brojevnog tipa!\n");
			greska = true;
		}
		
		if (nazivTextField.getText().isEmpty() || povrsinaTextField.getText().isEmpty()) {
			alert.setContentText(alert.getContentText() + "Popunite sva polja kako biste spremili podatke!\n");
			greska = true;
		}
		
		if(nazivTextField.getText().isEmpty()) {
			nazivTextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		if(povrsinaTextField.getText().isEmpty()) {
			povrsinaTextField.setStyle("-fx-background-color:rgb(255, 77, 77);");
		}
		
		if (greska == true) {
			alert.showAndWait();
		}
		if(greska==false && DrzaveController.promjena==false) {
			String naziv = nazivTextField.getText();
			BigDecimal povrsina = new BigDecimal(povrsinaTextField.getText());
			int noviId = getZadnjiId() +1;
			Drzava drzava = new Drzava(naziv, povrsina,noviId);
			BazaPodataka.spremiDrzavu(drzava);
			DrzaveController.dodajNovuDrzavu(drzava);
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			
		}
		if(greska==false && DrzaveController.promjena==true) {
			String naziv = nazivTextField.getText();
			BigDecimal povrsina = new BigDecimal(povrsinaTextField.getText());
			int noviId = getZadnjiId() +1;
			Drzava drzava = new Drzava(naziv, povrsina,noviId);
			drzava.setId(DrzaveController.drzavaId);
			BazaPodataka.updateDrzava(drzava);
			DrzaveController.dodajNovuDrzavu(drzava);
			DrzaveController.promjena=false;
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			
		}
	}
	
	public int getZadnjiId() {
		return sveDrzave.size();
	}
	

}
