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

public class DodajZupanijeController implements Runnable {
	
	@FXML
	public TextField nazivTextField;
	@FXML
	public ComboBox<Drzava> drzavaCombobox;
	@FXML
	public Button spremiButton;
	
	private List<Zupanija> sveZupanije;
	public ObservableList<Drzava> listaDrzava;
	static ObservableList<Mjesto> observableListaMjesta;
	public Integer Id;
	public Boolean promjena = false;
	
	
	@FXML
	public void initialize() throws SQLException, IOException {
		sveZupanije=BazaPodataka.dohvatiZupanije();
		observableListaMjesta = FXCollections.observableArrayList(BazaPodataka.dohvatiMjesto());
		listaDrzava = FXCollections.observableArrayList(BazaPodataka.dohvatiDrzave());
		drzavaCombobox.getItems().addAll(listaDrzava);
		drzavaCombobox.itemsProperty().toString();
		
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
		if(greska==false && promjena==false) {
			String naziv = nazivTextField.getText();
			Drzava drzava = drzavaCombobox.getValue();
			int noviId = getZadnjiId() +1;
			Zupanija zupanija = new Zupanija(naziv, drzava,noviId);
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			BazaPodataka.spremiZupaniju(zupanija);
			stage.close();
			ZupanijaController.dodajNovuZupaniju(zupanija);
			
		}
		if(greska==false && promjena ==true) {
			String naziv = nazivTextField.getText();
			Drzava drzava = drzavaCombobox.getValue();
			Zupanija zupanija = new Zupanija(naziv, drzava);
			zupanija.setId(Id);
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			BazaPodataka.updateZupanija(zupanija);
			stage.close();
			ZupanijaController.dodajNovuZupaniju(zupanija);
			promjena=false;
			
		}
	}
	
	public int getZadnjiId() {
		return sveZupanije.size();
	}

	@Override
	public void run() {
		try {
			initialize();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
