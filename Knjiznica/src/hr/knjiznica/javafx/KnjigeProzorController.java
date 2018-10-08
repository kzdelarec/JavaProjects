package hr.knjiznica.javafx;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.knjiznica.baza.podataka.BazaPodataka;
import hr.knjiznica.entiteti.Knjiga;
import hr.knjiznica.enumeracije.KategorijaKnjigaEnum;
import hr.knjiznica.enumeracije.KategorijaZaPretrazivanjeKnjigaEnum;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

public class KnjigeProzorController {
	
	@FXML
	TextField idTextField; //barkod knjige
	@FXML
	TextField naslovTextField;
	@FXML
	TextField autorTextField;
	@FXML
	ComboBox<KategorijaKnjigaEnum> kategorijeComboBox;
	@FXML
	TextField godinaIzdanjaTextField;
	@FXML
	TextField izdanjeTextField;
	@FXML
	TextField izdavacTextField;
	@FXML
	TextField kolicinaTextField;
	@FXML
	Label idKnjige; //id knjige u bazi
	@FXML
	ComboBox<KategorijaZaPretrazivanjeKnjigaEnum> kategorijePretrazivanjaComboBox;
	@FXML
	TextField kljucnaRijecTextField;
	@FXML
	TableView<Knjiga> sveKnjigeTableView;
	@FXML
	TableColumn<Knjiga, Long> idColumn;
	@FXML
	TableColumn<Knjiga, String> naslovColumn;
	@FXML
	TableColumn<Knjiga, String> autorColumn;
	@FXML
	TableColumn<Knjiga, String> kategorijaColumn;
	@FXML
	TableColumn<Knjiga, Integer> godinaIzdanjaColumn;
	@FXML
	TableColumn<Knjiga, Integer> izdanjeColumn;
	@FXML
	TableColumn<Knjiga, String> izdavacColumn;
	@FXML
	TableColumn<Knjiga, Integer> kolicinaColumn;
	
	List<Knjiga> knjige = new ArrayList<>();
	
	@FXML
	public void initialize() throws SQLException, IOException {
		knjige = BazaPodataka.dohvatiKnjige();
		
		kategorijePretrazivanjaComboBox.getItems().addAll(KategorijaZaPretrazivanjeKnjigaEnum.values());
    	kategorijePretrazivanjaComboBox.getSelectionModel().select(0);
    	kategorijeComboBox.getItems().addAll(KategorijaKnjigaEnum.values());
    	
    	idColumn.setCellValueFactory(new PropertyValueFactory<Knjiga, Long>("barcodeId"));
		naslovColumn.setCellValueFactory(new PropertyValueFactory<Knjiga, String>("naslov"));
		autorColumn.setCellValueFactory(new PropertyValueFactory<Knjiga, String>("autor"));
		kategorijaColumn.setCellValueFactory(new PropertyValueFactory<Knjiga, String>("kategorija"));
		godinaIzdanjaColumn.setCellValueFactory(new PropertyValueFactory<Knjiga, Integer>("godinaIzdanja"));
		izdanjeColumn.setCellValueFactory(new PropertyValueFactory<Knjiga, Integer>("izdanje"));
		izdavacColumn.setCellValueFactory(new PropertyValueFactory<Knjiga, String>("izdavac"));
		kolicinaColumn.setCellValueFactory(new PropertyValueFactory<Knjiga, Integer>("kolicina"));
		
		pretraziKnjige();
		promjeniVrijednost();
		
		kljucnaRijecTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					pretraziKnjige();
				}
			}
		});
		
		kljucnaRijecTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				pretraziKnjige();
			}
		});
		
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	        	kljucnaRijecTextField.requestFocus();
	        }
	    });
		
	}
	
	public void pretraziKnjige() {
		List<Knjiga> filtriraneKnjige = new ArrayList<>();
		if (kljucnaRijecTextField.getText().isEmpty() == false) {
			if (kategorijePretrazivanjaComboBox.getValue().toString().equals("Barkod")) {
				filtriraneKnjige = knjige.stream()
						.filter(m -> m.getBarcodeId().toString().toLowerCase().contains(kljucnaRijecTextField.getText().toLowerCase()))
						.collect(Collectors.toList());
				if(filtriraneKnjige.size() == 1) {
					Knjiga tempKnjiga = filtriraneKnjige.get(0);
					idTextField.setText(tempKnjiga.getBarcodeId().toString());
					naslovTextField.setText(tempKnjiga.getNaslov());
					autorTextField.setText(tempKnjiga.getAutor());
					for(KategorijaKnjigaEnum enumKnjige : KategorijaKnjigaEnum.values()) {
						if(tempKnjiga.getKategorija().equals(enumKnjige.toString())) {
							
							kategorijeComboBox.getSelectionModel().select(enumKnjige);
						}
					}
					godinaIzdanjaTextField.setText(tempKnjiga.getGodinaIzdanja().toString());
					izdanjeTextField.setText(tempKnjiga.getIzdanje().toString());
					izdavacTextField.setText(tempKnjiga.getIzdavac());
					kolicinaTextField.setText(tempKnjiga.getKolicina().toString());
					idKnjige.setText(tempKnjiga.getID().toString());
				}
			}
			if (kategorijePretrazivanjaComboBox.getValue().toString().equals("Naslov")) {
				filtriraneKnjige = knjige.stream()
						.filter(m -> m.getNaslov().toString().toLowerCase().contains(kljucnaRijecTextField.getText().toLowerCase()))
						.collect(Collectors.toList());
			}
			if (kategorijePretrazivanjaComboBox.getValue().toString().equals("Autor")) {
				filtriraneKnjige = knjige.stream()
						.filter(m -> m.getAutor().toString().toLowerCase().contains(kljucnaRijecTextField.getText().toLowerCase()))
						.collect(Collectors.toList());
			}
			if (kategorijePretrazivanjaComboBox.getValue().toString().equals("Godina izdanja")) {
				filtriraneKnjige = knjige.stream()
						.filter(m -> m.getGodinaIzdanja().toString().toLowerCase().contains(kljucnaRijecTextField.getText().toLowerCase()))
						.collect(Collectors.toList());
			}
			if (kategorijePretrazivanjaComboBox.getValue().toString().equals("Izdavač")) {
				filtriraneKnjige = knjige.stream()
						.filter(m -> m.getIzdavac().toString().toLowerCase().contains(kljucnaRijecTextField.getText().toLowerCase()))
						.collect(Collectors.toList());
			}
			if (kategorijePretrazivanjaComboBox.getValue().toString().equals("Kategorija")) {
				filtriraneKnjige = knjige.stream()
						.filter(m -> m.getKategorija().toString().toLowerCase().contains(kljucnaRijecTextField.getText().toLowerCase()))
						.collect(Collectors.toList());
			}
		} else {
			filtriraneKnjige = knjige;
		}
		GlavniProzorController.obsListaKnjiga = FXCollections.observableArrayList(filtriraneKnjige);
		sveKnjigeTableView.setItems(GlavniProzorController.obsListaKnjiga);
		//kljucnaRijecTextField.clear();
	}
	
	public void promjeniVrijednost() {
		sveKnjigeTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Knjiga tempKnjiga = sveKnjigeTableView.getSelectionModel().getSelectedItem();
				idTextField.setText(tempKnjiga.getBarcodeId().toString());
				naslovTextField.setText(tempKnjiga.getNaslov());
				autorTextField.setText(tempKnjiga.getAutor());
				for(KategorijaKnjigaEnum enumKnjige : KategorijaKnjigaEnum.values()) {
					if(tempKnjiga.getKategorija().equals(enumKnjige.toString())) {
						
						kategorijeComboBox.getSelectionModel().select(enumKnjige);
					}
				}
				godinaIzdanjaTextField.setText(tempKnjiga.getGodinaIzdanja().toString());
				izdanjeTextField.setText(tempKnjiga.getIzdanje().toString());
				izdavacTextField.setText(tempKnjiga.getIzdavac());
				kolicinaTextField.setText(tempKnjiga.getKolicina().toString());
				idKnjige.setText(tempKnjiga.getID().toString());
			}
		});
	}
	
	public void spremiPromjene() throws FileNotFoundException, SQLException, IOException {
		Long barcodeId = Long.parseLong(idTextField.getText());
		String naslov = naslovTextField.getText();
		String autor = autorTextField.getText();
		String kategorija  = kategorijeComboBox.getSelectionModel().getSelectedItem().name();
		Integer godinaIzdanja = Integer.parseInt(godinaIzdanjaTextField.getText());
		Integer izdanje = Integer.parseInt(izdanjeTextField.getText());
		String izdavac = izdavacTextField.getText();
		Integer kolicina = Integer.parseInt(kolicinaTextField.getText());
		Knjiga updateknjiga = new Knjiga(barcodeId, naslov, autor, kategorija, izdanje, izdavac, godinaIzdanja, kolicina);
		updateknjiga.setID(Integer.parseInt(idKnjige.getText()));
		BazaPodataka.updateKnjiga(updateknjiga);
		knjige = BazaPodataka.dohvatiKnjige();
		GlavniProzorController.obsListaKnjiga.clear();
		GlavniProzorController.obsListaKnjiga = FXCollections.observableArrayList(knjige);
		sveKnjigeTableView.setItems(GlavniProzorController.obsListaKnjiga);
		sveKnjigeTableView.refresh();
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText("Podaci uspješno spremljeni");

		alert.showAndWait();
		
		idKnjige.setText("");
		idTextField.clear();
		naslovTextField.clear();
		autorTextField.clear();
		godinaIzdanjaTextField.clear();
		izdanjeTextField.clear();
		izdavacTextField.setText("");
		kolicinaTextField.clear();
		kategorijeComboBox.getSelectionModel().select(null);
		kljucnaRijecTextField.requestFocus();
		
	}
	
}
