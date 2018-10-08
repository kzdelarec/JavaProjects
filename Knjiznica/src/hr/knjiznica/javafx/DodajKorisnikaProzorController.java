package hr.knjiznica.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import hr.knjiznica.baza.podataka.BazaPodataka;
import hr.knjiznica.entiteti.Korisnik;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DodajKorisnikaProzorController {
	
	@FXML
	TextField idKorisnikaTextField;
	@FXML
	TextField imeTextField;
	@FXML
	TextField prezimeTextField;
	@FXML
	TextField oibTextField;
	@FXML
	TextField mailTextField;
	@FXML
	TextField mobitelTextField;
	@FXML
	Label datumUclanjenjaLabel;
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	LocalDate datumUclanjenja = LocalDate.now();
	
	Boolean isNovi = true;
	
	@FXML
	public void initialize() {
		datumUclanjenjaLabel.setText(datumUclanjenja.format(dtf).toString());
	}
	
	public void dodajKorisnika() throws SQLException, IOException {
		Long idKorisnika = Long.parseLong(idKorisnikaTextField.getText());
		String ime = imeTextField.getText();
		String prezime = prezimeTextField.getText();
		Long oib = Long.parseLong(oibTextField.getText());
		String mail = mailTextField.getText();
		Integer mob = Integer.parseInt(mobitelTextField.getText());
		
		Korisnik noviKorisnik = new Korisnik(ime, prezime, mail, mob, oib, datumUclanjenja, idKorisnika);
		
		List<Korisnik> listaKorisnika = BazaPodataka.dohvatiKorisnike();
		
		for(Korisnik tempKorisnik : listaKorisnika ) {
			if(noviKorisnik.getOib().equals(tempKorisnik.getOib())) {
				isNovi=false;
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Upozorenje");
				alert.setHeaderText("Korisnik je već registriran");
				alert.setContentText(tempKorisnik.getIme() + " " + tempKorisnik.getPrezime() 
				+ " ->Datum učlanjenja: " + tempKorisnik.getDatumUclanjenja().format(dtf).toString());
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.setAlwaysOnTop(true);
				alert.showAndWait();
			}
		}
		
		if(isNovi) {
			BazaPodataka.spremiKorisnika(noviKorisnik);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(null);
			alert.setHeaderText(null);
			alert.setContentText("Korisnik uspješno registriran");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.setAlwaysOnTop(true);
			alert.showAndWait();
		}
		
		idKorisnikaTextField.clear();
		imeTextField.clear();
		prezimeTextField.clear();
		oibTextField.clear();
		mobitelTextField.clear();
		mailTextField.clear();
		datumUclanjenjaLabel.setText("");
	}
	
}
