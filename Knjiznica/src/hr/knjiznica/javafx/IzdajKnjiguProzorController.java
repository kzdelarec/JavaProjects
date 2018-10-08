package hr.knjiznica.javafx;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import hr.knjiznica.baza.podataka.BazaPodataka;
import hr.knjiznica.entiteti.IzdanaKnjiga;
import hr.knjiznica.entiteti.Knjiga;
import hr.knjiznica.entiteti.Knjiznicar;
import hr.knjiznica.entiteti.Korisnik;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class IzdajKnjiguProzorController {

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

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyy.");
	
	Korisnik korisnik;
	Knjiznicar knjiznicar;
	Knjiga odabranaKnjiga;

	@FXML
	public void initialize() {
		idKnjigeTextField.requestFocus();
		idKnjigeTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					try {
						pretrazi();
					} catch (SQLException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	public void pretrazi() throws SQLException, IOException {
		List <Knjiga> listaKnjiga = new ArrayList<>();
		listaKnjiga = BazaPodataka.dohvatiKnjige();
		for(Knjiga knjiga : listaKnjiga) {
			if(knjiga.getBarcodeId().equals(Long.parseLong(idKnjigeTextField.getText()))) {
				naslovLabel.setText(knjiga.getNaslov());
				autorLabel.setText(knjiga.getAutor());
				kategorijaLabel.setText(knjiga.getKategorija());
				godinaIzdanjaLabel.setText(knjiga.getGodinaIzdanja().toString());
				izdanjeLabel.setText(knjiga.getIzdanje().toString());
				izdavacLabel.setText(knjiga.getIzdavac());
				odabranaKnjiga = knjiga;
			}
		}
	}
	
	public void izdajKnjigu() throws FileNotFoundException, SQLException, IOException {
		if(odabranaKnjiga.getKolicina()>0) {
			IzdanaKnjiga izdanaKnjiga = new IzdanaKnjiga(korisnik,odabranaKnjiga,datumLabel.getText(),"-",knjiznicar);
			BazaPodataka.spremiTransakciju(izdanaKnjiga);
			odabranaKnjiga.setKolicina(odabranaKnjiga.getKolicina()-1);
			BazaPodataka.updateKnjiga(odabranaKnjiga);
			
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
			idKnjigeTextField.clear();
			idKnjigeTextField.requestFocus();
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.setAlwaysOnTop(true);
			alert.setTitle("Upozorenje");
			alert.setHeaderText("Knjiga nije raspoloživa");
			alert.setContentText("Broj kopija odabrane knjige je 0. Ukoliko smatrate da je došlo do greške, kontaktirajte administratora.");
			alert.showAndWait();
			idKnjigeTextField.clear();
			idKnjigeTextField.requestFocus();
		}
	}

}
