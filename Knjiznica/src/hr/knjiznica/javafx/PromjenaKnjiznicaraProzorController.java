package hr.knjiznica.javafx;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.knjiznica.baza.podataka.BazaPodataka;
import hr.knjiznica.entiteti.Knjiznicar;
import hr.knjiznica.enumeracije.KategirijaZaKnjiznicare;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PromjenaKnjiznicaraProzorController {

	@FXML
	TextField imeTextField;
	@FXML
	TextField prezimeTextField;
	@FXML
	TextField mailTextField;
	@FXML
	TextField mobitelextField;
	@FXML
	TextField lozinkaTextField;
	@FXML
	Label korisnickoImeLabel;
	@FXML
	Label IDLabel;
	@FXML
	CheckBox adminCheckBox;
	@FXML
	ComboBox<KategirijaZaKnjiznicare> kategorijeComboBox;
	@FXML
	TextField kljucnarijecTextField;
	@FXML
	TableView<Knjiznicar> knjiznicariTableView;
	@FXML
	TableColumn<Knjiznicar, String> imeColumn;
	@FXML
	TableColumn<Knjiznicar, String> prezimeColumn;

	List<Knjiznicar> knjiznicari = new ArrayList<>();
	Boolean adminLogged = false;
	String novaLozinka;
	Boolean promijenjenaLozinka = false;

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
		knjiznicari.remove(0);
		promjeniVrijednost();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				kljucnarijecTextField.requestFocus();
			}
		});

		imeColumn.setCellValueFactory(new PropertyValueFactory<Knjiznicar, String>("ime"));
		prezimeColumn.setCellValueFactory(new PropertyValueFactory<Knjiznicar, String>("prezime"));

		kategorijeComboBox.getItems().setAll(KategirijaZaKnjiznicare.values());
		kategorijeComboBox.getSelectionModel().select(0);

		kljucnarijecTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					pretraziKnjiznicare();
				}
			}
		});
		
		kljucnarijecTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				pretraziKnjiznicare();
				knjiznicariTableView.getSortOrder().add(prezimeColumn);
			}
		});
		pretraziKnjiznicare();
		knjiznicariTableView.getSortOrder().add(prezimeColumn);

	}

	public void pretraziKnjiznicare() {
		List<Knjiznicar> filtriraniKnjiznicari = new ArrayList<>();
		if (kljucnarijecTextField.getText().isEmpty() == false) {
			if (kategorijeComboBox.getValue().toString().equals("Ime")) {
				filtriraniKnjiznicari = knjiznicari.stream()
						.filter(m -> m.getIme().toLowerCase().contains(kljucnarijecTextField.getText().toLowerCase()))
						.collect(Collectors.toList());
			}
			if (kategorijeComboBox.getValue().toString().equals("Prezime")) {
				filtriraniKnjiznicari = knjiznicari.stream().filter(
						m -> m.getPrezime().toLowerCase().contains(kljucnarijecTextField.getText().toLowerCase()))
						.collect(Collectors.toList());
			}
		} else {
			filtriraniKnjiznicari = knjiznicari;
		}
		GlavniProzorController.obsListaKnjiznicara = FXCollections.observableArrayList(filtriraniKnjiznicari);
		knjiznicariTableView.setItems(GlavniProzorController.obsListaKnjiznicara);
	}

	public void promjeniVrijednost() {
		knjiznicariTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Knjiznicar tempKnjiznicar = knjiznicariTableView.getSelectionModel().getSelectedItem();
				IDLabel.setText(tempKnjiznicar.getID().toString());
				imeTextField.setText(tempKnjiznicar.getIme());
				prezimeTextField.setText(tempKnjiznicar.getPrezime());
				mailTextField.setText(tempKnjiznicar.getMailAdresa());
				mobitelextField.setText(tempKnjiznicar.getBrojMobitela().toString());
				lozinkaTextField.setText(tempKnjiznicar.getLozinka());
				korisnickoImeLabel.setText(tempKnjiznicar.getKorisnickoIme());
				if (tempKnjiznicar.getIsAdmin()) {
					adminCheckBox.setSelected(true);
				} else
					adminCheckBox.setSelected(false);
			}
		});
	}

	public void spremiPromjene() throws FileNotFoundException, SQLException, IOException {
		String korisnickoIme = korisnickoImeLabel.getText();
		String lozinka = lozinkaTextField.getText();
		Boolean isAdmin;
		if (adminCheckBox.isSelected()) {
			isAdmin = true;
		} else
			isAdmin = false;
		String ime = imeTextField.getText();
		String prezime = prezimeTextField.getText();
		String mailAdresa = mailTextField.getText();
		Integer brojMobitela = Integer.parseInt(mobitelextField.getText());
		Integer ID = Integer.parseInt(IDLabel.getText());
		Boolean isAktivan = true;
		Knjiznicar promjeneKnjiznicar = new Knjiznicar(ime, prezime, mailAdresa, brojMobitela, korisnickoIme, lozinka,
				isAdmin, isAktivan);
		promjeneKnjiznicar.setID(ID);
		BazaPodataka.updateKnjiznicara(promjeneKnjiznicar);
		knjiznicari = BazaPodataka.dohvatiKnjiznicare();
		knjiznicari.remove(0);
		GlavniProzorController.obsListaKnjiznicara.clear();
		GlavniProzorController.obsListaKnjiznicara = FXCollections.observableArrayList(knjiznicari);
		knjiznicariTableView.setItems(GlavniProzorController.obsListaKnjiznicara);
		knjiznicariTableView.refresh();
		knjiznicariTableView.getSortOrder().add(prezimeColumn);

		novaLozinka=lozinka;
		promijenjenaLozinka = true;
		
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText("Podaci uspješno spremljeni");

		alert.showAndWait();

		IDLabel.setText("");
		imeTextField.clear();
		prezimeTextField.clear();
		mailTextField.clear();
		mobitelextField.clear();
		lozinkaTextField.clear();
		korisnickoImeLabel.setText("");
		adminCheckBox.setSelected(false);
		kljucnarijecTextField.requestFocus();
	}

	public void obrisiKnjiznicra() throws FileNotFoundException, SQLException, IOException {
		String korisnickoIme = korisnickoImeLabel.getText();
		String lozinka = lozinkaTextField.getText();
		Boolean isAdmin;
		if (adminCheckBox.isSelected()) {
			isAdmin = true;
		} else
			isAdmin = false;
		String ime = imeTextField.getText();
		String prezime = prezimeTextField.getText();
		String mailAdresa = mailTextField.getText();
		Integer brojMobitela = Integer.parseInt(mobitelextField.getText());
		Integer ID = Integer.parseInt(IDLabel.getText());
		Boolean isAktivan = false;
		Knjiznicar promjeneKnjiznicar = new Knjiznicar(ime, prezime, mailAdresa, brojMobitela, korisnickoIme, lozinka,
				isAdmin, isAktivan);
		promjeneKnjiznicar.setID(ID);
		if (adminLogged) {
			BazaPodataka.obrisiKnjiznicara(promjeneKnjiznicar);
			knjiznicari = BazaPodataka.dohvatiKnjiznicare();
			knjiznicari.remove(0);
			GlavniProzorController.obsListaKnjiznicara.clear();
			GlavniProzorController.obsListaKnjiznicara = FXCollections.observableArrayList(knjiznicari);
			knjiznicariTableView.setItems(GlavniProzorController.obsListaKnjiznicara);
			knjiznicariTableView.refresh();
			knjiznicariTableView.getSortOrder().add(prezimeColumn);
			Alert alert = new Alert(AlertType.INFORMATION);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.setAlwaysOnTop(true);
			alert.setHeaderText(null);
			alert.setContentText("Knjižničar uspješno obrisan");

			alert.showAndWait();

			IDLabel.setText("");
			imeTextField.clear();
			prezimeTextField.clear();
			mailTextField.clear();
			mobitelextField.clear();
			lozinkaTextField.clear();
			korisnickoImeLabel.setText("");
			adminCheckBox.setSelected(false);
			kljucnarijecTextField.requestFocus();
		}

		else {
			Alert alert = new Alert(AlertType.WARNING);
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.setAlwaysOnTop(true);
			alert.setTitle("Upozorenje!");
			alert.setHeaderText("Nedopuštena radnja");
			alert.setContentText("Prijavite se kao administrator kako biste obrisali odabranog zaposlenika");

			alert.showAndWait();
		}

	}

}
