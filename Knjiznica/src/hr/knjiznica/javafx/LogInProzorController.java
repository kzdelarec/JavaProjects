package hr.knjiznica.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import hr.knjiznica.baza.podataka.BazaPodataka;
import hr.knjiznica.entiteti.Knjiznicar;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LogInProzorController {
	@FXML
	Button logInButton;
	@FXML
	TextField korisnickoImeTextField;
	@FXML
	PasswordField lozinkaPasswordField;
	@FXML
	Label pogreskaLabel;
	
	
	List <Knjiznicar> listaKnjiznicara = new ArrayList<>();
	Boolean nePostoji = true;

	@FXML
	public void initialize() throws SQLException, IOException, ParseException {
		listaKnjiznicara=BazaPodataka.dohvatiKnjiznicare();
		pogreskaLabel.setText("");
		lozinkaPasswordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					prijaviSe();
				}
			}
		});
		
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	        	korisnickoImeTextField.requestFocus();
	        }
	    });

	}

	public void prijaviSe() {
		for(Knjiznicar knjiznicar : listaKnjiznicara) {
			if (korisnickoImeTextField.getText().equals(knjiznicar.getKorisnickoIme()) 
					&& lozinkaPasswordField.getText().equals(knjiznicar.getLozinka()) 
					&& knjiznicar.getIsAktivan() == true) {
				nePostoji = false;
				Stage zatvori = (Stage) logInButton.getScene().getWindow();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("GlavniProzor.fxml"));
				try {
					loader.load();
					GlavniProzorController ctl = (GlavniProzorController) loader.getController();
					ctl.trenutniKnjiznicar = new Knjiznicar(knjiznicar.getIme(), knjiznicar.getPrezime(), knjiznicar.getMailAdresa(), 
							knjiznicar.getBrojMobitela(), knjiznicar.getKorisnickoIme(), knjiznicar.getLozinka(), knjiznicar.getIsAdmin(), knjiznicar.getIsAktivan());
					ctl.trenutniKnjiznicar.setID(knjiznicar.getID());
					ctl.knjiznicarLabel.setText(ctl.trenutniKnjiznicar.getIme() + " " + ctl.trenutniKnjiznicar.getPrezime());
					ctl.sviKnjiznicari = listaKnjiznicara;
					ctl.pozadina.setImage(new Image("userPozadina.jpg"));
					if(knjiznicar.getIsAdmin()) {
						ctl.knjiznicarIkona.setImage(new Image ("adminIkona.png"));
					} else {
						ctl.knjiznicarIkona.setImage(new Image ("knjiznicarIkona.png"));
						ctl.meniVbox.getChildren().remove(ctl.logButton);
					}
					GlavniProzorController.obsListaKnjiznicara =FXCollections.observableArrayList(listaKnjiznicara);
					Pane root = (Pane) loader.getRoot();
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					Stage stage = new Stage();
					stage.setMaximized(true);
					stage.initStyle(StageStyle.UNDECORATED);
					stage.setScene(scene);
					stage.show();
					zatvori.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		if(nePostoji) {
			pogreskaLabel.setText("Pogrešno korisničko ime ili lozinka");
			pogreskaLabel.setStyle("-fx-text-fill: red; -fx-font-size: 10;");
			korisnickoImeTextField.clear();
			korisnickoImeTextField.requestFocus();
			lozinkaPasswordField.clear();
		}
	}

	public void ponisti() {
		korisnickoImeTextField.clear();
		korisnickoImeTextField.requestFocus();
		lozinkaPasswordField.clear();
	}

}
