package hr.knjiznica.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.time.temporal.ChronoUnit;

import hr.knjiznica.baza.podataka.BazaPodataka;
import hr.knjiznica.entiteti.IzdanaKnjiga;
import hr.knjiznica.entiteti.Knjiga;
import hr.knjiznica.entiteti.Knjiznicar;
import hr.knjiznica.entiteti.Korisnik;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class VratiKnjiguProzorController {

	@FXML
	TextField idKnjigeTextField;
	@FXML
	Label knjiznicarLabel;
	@FXML
	Label datumLabel;
	@FXML
	Label naslovLabel;
	@FXML
	Label autorLabel;
	@FXML
	Label kategorijaLabel;
	@FXML
	Label godinaIzdanjaLabel;
	@FXML
	Label izdanjeLabel;
	@FXML
	Label izdavacLabel;
	@FXML
	Label korisnikLabel;
	@FXML
	Label zakasninaLabel;

	Korisnik korisnik;
	Knjiga knjiga;
	Knjiznicar knjiznicar;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyy.");
	LocalDate datumPovratka = LocalDate.now();
	List<IzdanaKnjiga> listaIzdanihKnjiga;
	Integer ID;

	@FXML
	public void initialize() {
		idKnjigeTextField.requestFocus();
		idKnjigeTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {

					pretraziTransakcije();

				}
			}
		});
	}

	public void pretraziTransakcije() {
		for(IzdanaKnjiga izdanaKnjiga : listaIzdanihKnjiga) {
			if(izdanaKnjiga.getKnjiga().getBarcodeId().toString().equals(idKnjigeTextField.getText()) 
					&& izdanaKnjiga.getDatumPovratka().equals("-")) {
				 knjiga = new Knjiga(izdanaKnjiga.getKnjiga().getBarcodeId(), izdanaKnjiga.getKnjiga().getNaslov(), 
						 izdanaKnjiga.getKnjiga().getAutor(), izdanaKnjiga.getKnjiga().getKategorija(), izdanaKnjiga.getKnjiga().getIzdanje(), 
						 izdanaKnjiga.getKnjiga().getIzdavac(), izdanaKnjiga.getKnjiga().getGodinaIzdanja(), izdanaKnjiga.getKnjiga().getKolicina());
				 knjiga.setID(izdanaKnjiga.getKnjiga().getID());
				 knjiznicarLabel.setText(izdanaKnjiga.getKnjiznicar().getIme() + izdanaKnjiga.getKnjiznicar().getPrezime());
				 datumLabel.setText(izdanaKnjiga.getDatumPosudbe());
				 naslovLabel.setText(izdanaKnjiga.getKnjiga().getNaslov());;
				 autorLabel.setText(izdanaKnjiga.getKnjiga().getAutor());
				 kategorijaLabel.setText(izdanaKnjiga.getKnjiga().getKategorija());
				 godinaIzdanjaLabel.setText(izdanaKnjiga.getKnjiga().getGodinaIzdanja().toString());
				 izdanjeLabel.setText(izdanaKnjiga.getKnjiga().getIzdanje().toString());
				 izdavacLabel.setText(izdanaKnjiga.getKnjiga().getIzdavac());
				 ID = izdanaKnjiga.getID();
				 LocalDate datumPosudbe = LocalDate.parse(datumLabel.getText(),dtf);
				 datumPosudbe.format(dtf);
				 Long daysElapsed = ChronoUnit.DAYS.between(datumPosudbe,datumPovratka);
				 System.out.println(datumPosudbe.toString());
				 System.out.println(datumPovratka.toString());
				 System.out.println(daysElapsed);
				 Double zakasnina = 0.0;
				 if(daysElapsed>30) {
					 zakasnina = (daysElapsed-7)*0.5;
					 zakasninaLabel.setText(zakasnina.toString() + "kn");
					 Alert alert = new Alert(AlertType.WARNING);
					 alert.setTitle(null);
					 alert.setHeaderText("Knjiga je vraćena sa zakašnjenjem!");
					 alert.setContentText("Iznos zakasnine: " + zakasninaLabel.getText());
					 Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					 stage.setAlwaysOnTop(true);
					 alert.showAndWait();
				 }
				 else {
					 zakasninaLabel.setText(zakasnina.toString() + "kn");
				 }
				 

			}
		}
	}
	
	public void vratiKnjigu() throws SQLException, IOException {
		IzdanaKnjiga izdanaKnjiga = new IzdanaKnjiga(korisnik,knjiga,datumLabel.getText(),datumPovratka.format(dtf).toString(),knjiznicar);
		izdanaKnjiga.setID(ID);
		BazaPodataka.updateTransakcija(izdanaKnjiga);
		knjiga.setKolicina(knjiga.getKolicina()+1);
		BazaPodataka.updateKnjiga(knjiga);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText("Transakcija uspješno izvršena");
		alert.showAndWait();
		
		naslovLabel.setText("");
		autorLabel.setText("");
		kategorijaLabel.setText("");
		godinaIzdanjaLabel.setText("");
		izdanjeLabel.setText("");
		izdavacLabel.setText("");
		datumLabel.setText("");
		knjiznicarLabel.setText("");
		zakasninaLabel.setText("");
		idKnjigeTextField.clear();
		idKnjigeTextField.requestFocus();
	}

}
