package hr.knjiznica.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.knjiznica.baza.podataka.BazaPodataka;
import hr.knjiznica.entiteti.Knjiznicar;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DodajKnjiznicaraProzorController {

	@FXML
	TextField imeTextField;
	@FXML
	TextField prezimeTextField;
	@FXML
	TextField mailTextField;
	@FXML
	TextField mobitelTextField;
	@FXML
	Label korisnickoImeLabel;
	@FXML
	TextField lozinkaTextField;
	@FXML
	CheckBox adminCheckBox;
	@FXML
	Button dodajKnjiznicaraButton;

	List<Knjiznicar> knjiznicari = new ArrayList<>();

	Integer brojIstoimenika = 0;

	@FXML
	public void initialize() {
		try {
			knjiznicari = BazaPodataka.dohvatiKnjiznicare();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		korisnickoImeLabel.setText("");

		imeTextField.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (!newVal) {
				if (!prezimeTextField.getText().isEmpty() && !imeTextField.getText().isEmpty()) {
					for (Knjiznicar knjiznicar : knjiznicari) {
						if (knjiznicar.getIme().equals(imeTextField.getText())
								&& knjiznicar.getPrezime().equals(prezimeTextField.getText())) {
							brojIstoimenika++;
						}
					}
					if (brojIstoimenika > 0) {
						korisnickoImeLabel
								.setText((imeTextField.getText().charAt(0) + prezimeTextField.getText()+ brojIstoimenika.toString()).toLowerCase());
						lozinkaTextField.setText(korisnickoImeLabel.getText() + "123");
					} else {
						korisnickoImeLabel
								.setText((imeTextField.getText().charAt(0) + prezimeTextField.getText()).toLowerCase());
						lozinkaTextField.setText(korisnickoImeLabel.getText() + "123");
					}
				}
			}
		});

		prezimeTextField.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (!newVal) {
				if (!prezimeTextField.getText().isEmpty() && !imeTextField.getText().isEmpty()) {
					for (Knjiznicar knjiznicar : knjiznicari) {
						if (knjiznicar.getIme().equals(imeTextField.getText())
								&& knjiznicar.getPrezime().equals(prezimeTextField.getText())) {
							brojIstoimenika++;
						}
					}
					if (brojIstoimenika > 0) {
						korisnickoImeLabel
								.setText((imeTextField.getText().charAt(0) + prezimeTextField.getText()+ brojIstoimenika.toString()).toLowerCase());
						lozinkaTextField.setText(korisnickoImeLabel.getText() + "123");
					} else {
						korisnickoImeLabel
								.setText((imeTextField.getText().charAt(0) + prezimeTextField.getText()).toLowerCase());
						lozinkaTextField.setText(korisnickoImeLabel.getText() + "123");
					}
				}
			}
		});

	}

	public void dodajKnjiznicara() throws SQLException, IOException {
		String korisnickoIme = korisnickoImeLabel.getText();
		String lozinka = lozinkaTextField.getText();
		Boolean isAdmin = false;
		if (adminCheckBox.isSelected()) {
			isAdmin = true;
		}
		String ime = imeTextField.getText();
		String prezime = prezimeTextField.getText();
		String mailAdresa = mailTextField.getText();
		Integer brojMobitela = Integer.parseInt(mobitelTextField.getText());
		Boolean isAktivan = true;
		Knjiznicar noviKnjiznicar = new Knjiznicar(ime, prezime, mailAdresa, brojMobitela, korisnickoIme, lozinka,
				isAdmin, isAktivan);
		BazaPodataka.spremiKnjiznicara(noviKnjiznicar);
		GlavniProzorController.obsListaKnjiznicara.add(noviKnjiznicar);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText("Knjižničar uspješno registriran");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);
		alert.showAndWait();

		imeTextField.clear();
		prezimeTextField.clear();
		mailTextField.clear();
		mobitelTextField.clear();
		korisnickoImeLabel.setText("");
		lozinkaTextField.clear();
		adminCheckBox.setSelected(false);

	}

}
