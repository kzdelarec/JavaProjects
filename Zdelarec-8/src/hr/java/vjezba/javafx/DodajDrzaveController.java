package hr.java.vjezba.javafx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
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

public class DodajDrzaveController {
	
	@FXML
	private TextField nazivTextField;
	@FXML
	private TextField povrsinaTextField;
	@FXML
	private Button spremiButton;
	
	private List<Drzava> sveDrzave;
	private List<VrstaMjesta> vrsteMjesta;
	private ObservableList<Drzava> listaDrzava;
	static ObservableList<Mjesto> observableListaMjesta;
	
	
	@FXML
	public void initialize() {
		
		sveDrzave=Main.dohvatiDrzave();
		
	}
	public void spremi(){
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
		if(greska==false) {
			String naziv = nazivTextField.getText();
			BigDecimal povrsina = new BigDecimal(povrsinaTextField.getText());
			int noviId = getZadnjiId() +1;
			Drzava drzava = new Drzava(naziv, povrsina,noviId);
			File mjestoFile = new File("dat/drzave.txt"); {
				try(FileWriter writer = new FileWriter(mjestoFile,true);){
					writer.write(noviId + "\n");
					writer.write(naziv + "\n");
					writer.write(povrsina + "\n");
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Uspješno spremanje mjesta!");
					alert2.setHeaderText("Uspješno spremanje mjesta!");
					alert2.setContentText("Uneseni podaci za mjesto su uspješnospremljeni.");
					Stage stage = (Stage) spremiButton.getScene().getWindow();
					stage.close();
					DrzaveController.dodajNovuDrzavu(drzava);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getZadnjiId() {
		return sveDrzave.size();
	}
	

}
