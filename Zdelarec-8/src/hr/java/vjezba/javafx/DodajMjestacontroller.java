package hr.java.vjezba.javafx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

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
	private TextField nazivTextField;
	@FXML
	private ComboBox<Zupanija> zupanijaCombobox;
	@FXML
	private ComboBox<VrstaMjesta> vrstaCombobox;
	@FXML
	private Button spremiButton;
	
	private List<Mjesto> svaMjesta;
	private List<VrstaMjesta> vrsteMjesta;
	private ObservableList<Zupanija> listaZupanija;
	static ObservableList<Mjesto> observableListaMjesta;
	
	
	@FXML
	public void initialize() {
		svaMjesta=Main.dohvatiMjesta();
		observableListaMjesta = FXCollections.observableArrayList(Main.dohvatiMjesta());

		listaZupanija = FXCollections.observableArrayList(Main.dohvatiZupanije());
		
		zupanijaCombobox.getItems().addAll(listaZupanija);
		zupanijaCombobox.itemsProperty().toString();
		vrstaCombobox.getItems().addAll(VrstaMjesta.values());
	}
	
	
	public void spremi(){
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
		if(greska==false) {
			String naziv = nazivTextField.getText();
			Zupanija zupanija = zupanijaCombobox.getValue();
			VrstaMjesta vrstaMjesta = vrstaCombobox.getValue();
			int noviId = getZadnjiId() +1;
			Mjesto mjesto = new Mjesto(naziv, zupanija,noviId);
			mjesto.setVrstaMjest(vrstaMjesta);
			System.out.println(mjesto.getNaziv());
			File mjestoFile = new File("dat/mjesto.txt"); {
				try(FileWriter writer = new FileWriter(mjestoFile,true);){
					writer.write(noviId + "\n");
					writer.write(naziv + "\n");
					writer.write(zupanija.getId() + "\n");
					writer.write(vrstaMjesta.toString() + "\n");
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Uspješno spremanje mjesta!");
					alert2.setHeaderText("Uspješno spremanje mjesta!");
					alert2.setContentText("Uneseni podaci za mjesto su uspješnospremljeni.");
					Stage stage = (Stage) spremiButton.getScene().getWindow();
					stage.close();
					MjestaController.dodajNovoMjesto(mjesto);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getZadnjiId() {
		return svaMjesta.size();
	}
	
}
