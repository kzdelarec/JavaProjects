package hr.java.vjezba.javafx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.Mjesto;
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

public class DodajZupanijeController {
	
	@FXML
	private TextField nazivTextField;
	@FXML
	private ComboBox<Drzava> drzavaCombobox;
	@FXML
	private Button spremiButton;
	
	private List<Zupanija> sveZupanije;
	private List<VrstaMjesta> vrsteMjesta;
	private ObservableList<Drzava> listaDrzava;
	static ObservableList<Mjesto> observableListaMjesta;
	
	
	@FXML
	public void initialize() {
		sveZupanije=Main.dohvatiZupanije();
		observableListaMjesta = FXCollections.observableArrayList(Main.dohvatiMjesta());

		listaDrzava = FXCollections.observableArrayList(Main.dohvatiDrzave());
		
		drzavaCombobox.getItems().addAll(listaDrzava);
		drzavaCombobox.itemsProperty().toString();
	}
	
	public void spremi(){
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
		if(greska==false) {
			String naziv = nazivTextField.getText();
			Drzava drzava = drzavaCombobox.getValue();
			int noviId = getZadnjiId() +1;
			Zupanija zupanija = new Zupanija(naziv, drzava,noviId);
			File mjestoFile = new File("dat/zupanije.txt"); {
				try(FileWriter writer = new FileWriter(mjestoFile,true);){
					writer.write(noviId + "\n");
					writer.write(naziv + "\n");
					writer.write(drzava.getId() + "\n");
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Uspješno spremanje mjesta!");
					alert2.setHeaderText("Uspješno spremanje mjesta!");
					alert2.setContentText("Uneseni podaci za mjesto su uspješnospremljeni.");
					Stage stage = (Stage) spremiButton.getScene().getWindow();
					stage.close();
					ZupanijaController.dodajNovuZupaniju(zupanija);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getZadnjiId() {
		return sveZupanije.size();
	}

}
