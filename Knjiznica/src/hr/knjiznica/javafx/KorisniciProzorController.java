package hr.knjiznica.javafx;



import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.knjiznica.baza.podataka.BazaPodataka;
import hr.knjiznica.entiteti.IzdanaKnjiga;
import hr.knjiznica.entiteti.Knjiznicar;
import hr.knjiznica.entiteti.Korisnik;
import hr.knjiznica.enumeracije.KategorijaZaKorisnikeEnum;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class KorisniciProzorController {
	
	@FXML
	TextField idTextField;
	@FXML
	TextField imeTextField;
	@FXML
	TextField prezimeTextField;
	@FXML
	TextField mobitelTextField;
	@FXML
	TextField mailTextField;
	@FXML
	Label oibLabel;
	@FXML
	Label datumUclanjenjaLabel;
	@FXML
	TextField idPretrazivanjeTextField;
	@FXML
	TableView<IzdanaKnjiga> posudeneKnjigeTableView;
	@FXML
	TableColumn<IzdanaKnjiga, Long> idKnjigeColumn;
	@FXML
	TableColumn<IzdanaKnjiga, String> naslovColumn;
	@FXML
	TableColumn<IzdanaKnjiga, String> autorColumn;
	@FXML
	TableColumn<IzdanaKnjiga, String> posudenoColumn;
	@FXML
	TableColumn<IzdanaKnjiga, String> vracenoColumn;
	@FXML
	TableColumn<IzdanaKnjiga, String> knjiznicarColumn;
	@FXML
	ComboBox <KategorijaZaKorisnikeEnum> kategorijaComboBox;
	
	List<Korisnik> listaKorisnika = new ArrayList<>();
	List<IzdanaKnjiga> listaIzdanihKnjiga = new ArrayList<>();
	ObservableList<IzdanaKnjiga> obsListaTransakcija; 
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	Knjiznicar knjiznicar;
	Integer ID;
	
	@FXML
	public void initialize() throws SQLException, IOException {
		
		kategorijaComboBox.getItems().addAll(KategorijaZaKorisnikeEnum.values());
		kategorijaComboBox.getSelectionModel().select(0);
		
		idKnjigeColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<IzdanaKnjiga, Long>, ObservableValue<Long>>() {
					@Override
					public ObservableValue<Long> call(CellDataFeatures<IzdanaKnjiga, Long> param) {
						return new ReadOnlyObjectWrapper<Long>(param.getValue().getKnjiga().getBarcodeId());
					}
				});
		naslovColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<IzdanaKnjiga, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<IzdanaKnjiga, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getKnjiga().getNaslov());
					}
				});
		autorColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<IzdanaKnjiga, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<IzdanaKnjiga, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getKnjiga().getAutor());
					}
				});
		knjiznicarColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<IzdanaKnjiga, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<IzdanaKnjiga, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getKnjiznicar().getIme() + " " +
								param.getValue().getKnjiznicar().getPrezime() );
					}
				});
		posudenoColumn.setCellValueFactory(new PropertyValueFactory<IzdanaKnjiga, String>("datumPosudbe"));
		vracenoColumn.setCellValueFactory(new PropertyValueFactory<IzdanaKnjiga, String>("datumPovratka"));
		
		listaKorisnika = BazaPodataka.dohvatiKorisnike();
		Platform.runLater(new Runnable() {
	        @Override
	        public void run() {
	        	idPretrazivanjeTextField.requestFocus();
	        }
	    });
		
		idPretrazivanjeTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					try {
						pretraziKorisnike();
					} catch (SQLException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		idPretrazivanjeTextField.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				try {
					if(idPretrazivanjeTextField.getText().length()==7) {
						pretraziKorisnike();
					}
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public void izdajKnjigu() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("IzdajKnjiguProzor.fxml"));
		try {
			loader.load();
			IzdajKnjiguProzorController ctl = (IzdajKnjiguProzorController) loader.getController();
			ctl.korisnik = new Korisnik(imeTextField.getText(), prezimeTextField.getText(), mailTextField.getText(),
					Integer.parseInt(mobitelTextField.getText()),  (long) Integer.parseInt(mobitelTextField.getText()),
					LocalDate.parse(datumUclanjenjaLabel.getText(),dtf), Long.parseLong(idTextField.getText()));
			ctl.korisnikLabel.setText(ctl.korisnik.getIme() +" " + ctl.korisnik.getPrezime());
			ctl.knjiznicar = new Knjiznicar(knjiznicar.getIme(), knjiznicar.getPrezime(), knjiznicar.getMailAdresa(),
					knjiznicar.getBrojMobitela(), knjiznicar.getKorisnickoIme(), knjiznicar.getLozinka(), knjiznicar.getIsAdmin(), knjiznicar.getIsAktivan());
			ctl.knjiznicar.setID(knjiznicar.getID());
			ctl.knjiznicarLabel.setText(knjiznicar.getIme() + " " + knjiznicar.getPrezime());
			ctl.datumLabel.setText(LocalDate.now().format(dtf).toString());
			BorderPane root = (BorderPane) loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setAlwaysOnTop(true);
			stage.show();
			stage.getIcons().add(new Image("ikona.png"));
			stage.setTitle("Izdavanje knjige");
			stage.setOnCloseRequest(e->{
				try {
					listaIzdanihKnjiga = BazaPodataka.dohvtiTransakciju();
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<IzdanaKnjiga> filtriraneTransakcije = new ArrayList<>();
				if (idTextField.getText().isEmpty() == false) {
					filtriraneTransakcije = listaIzdanihKnjiga.stream()
							.filter(m -> m.getKorisnik().getIdKorisnika().toString().equals(idTextField.getText().toLowerCase()))
							.collect(Collectors.toList());
				} else {
					filtriraneTransakcije.clear();
				}
				obsListaTransakcija = FXCollections.observableArrayList(filtriraneTransakcije);
				posudeneKnjigeTableView.setItems(obsListaTransakcija);
				posudeneKnjigeTableView.refresh();
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void vratiKnjigu() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("VratiKnjiguProzor.fxml"));
		try {
			loader.load();
			VratiKnjiguProzorController ctl = (VratiKnjiguProzorController) loader.getController();
			ctl.korisnik = new Korisnik(imeTextField.getText(), prezimeTextField.getText(), mailTextField.getText(),
					Integer.parseInt(mobitelTextField.getText()),  (long) Integer.parseInt(mobitelTextField.getText()),
					LocalDate.parse(datumUclanjenjaLabel.getText(),dtf), Long.parseLong(idTextField.getText()));
			ctl.korisnikLabel.setText(ctl.korisnik.getIme() +" " + ctl.korisnik.getPrezime());
			ctl.korisnik.setID(ID);
			ctl.listaIzdanihKnjiga = new ArrayList<>(obsListaTransakcija);
			ctl.knjiznicar = new Knjiznicar(knjiznicar.getIme(), knjiznicar.getPrezime(), knjiznicar.getMailAdresa(),
					knjiznicar.getBrojMobitela(), knjiznicar.getKorisnickoIme(), knjiznicar.getLozinka(), knjiznicar.getIsAdmin(), knjiznicar.getIsAktivan());
			ctl.knjiznicar.setID(knjiznicar.getID());
			ctl.knjiznicarLabel.setText(knjiznicar.getIme() + " " + knjiznicar.getPrezime());
			BorderPane root = (BorderPane) loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setAlwaysOnTop(true);
			stage.show();
			stage.getIcons().add(new Image("ikona.png"));
			stage.setTitle("Vračanje knjige");
			stage.setOnCloseRequest(e->{
				try {
					listaIzdanihKnjiga = BazaPodataka.dohvtiTransakciju();
				} catch (SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				List<IzdanaKnjiga> filtriraneTransakcije = new ArrayList<>();
				if (idTextField.getText().isEmpty() == false) {
					filtriraneTransakcije = listaIzdanihKnjiga.stream()
							.filter(m -> m.getKorisnik().getIdKorisnika().toString().equals(idTextField.getText().toLowerCase()))
							.collect(Collectors.toList());
				} else {
					filtriraneTransakcije.clear();
				}
				obsListaTransakcija = FXCollections.observableArrayList(filtriraneTransakcije);
				posudeneKnjigeTableView.setItems(obsListaTransakcija);
				posudeneKnjigeTableView.refresh();
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void pretraziKorisnike() throws SQLException, IOException {
		idTextField.clear();
		imeTextField.clear();
		prezimeTextField.clear();
		oibLabel.setText("");
		mailTextField.clear();
		mobitelTextField.clear();
		datumUclanjenjaLabel.setText("");
		for(Korisnik tempKorisnik : listaKorisnika) {
			if(!idPretrazivanjeTextField.getText().isEmpty()) {
				if(kategorijaComboBox.getSelectionModel().getSelectedItem().name().equals("ID") && 
						tempKorisnik.getIdKorisnika().toString().equals(idPretrazivanjeTextField.getText())) {
					idTextField.setText(tempKorisnik.getIdKorisnika().toString());
					imeTextField.setText(tempKorisnik.getIme());
					prezimeTextField.setText(tempKorisnik.getPrezime());
					oibLabel.setText(tempKorisnik.getOib().toString());
					mailTextField.setText(tempKorisnik.getMailAdresa());
					mobitelTextField.setText("0" + tempKorisnik.getBrojMobitela().toString());
					datumUclanjenjaLabel.setText(tempKorisnik.getDatumUclanjenja().format(dtf));
					idPretrazivanjeTextField.clear();
					ID=tempKorisnik.getID();
				}
				
				if(kategorijaComboBox.getSelectionModel().getSelectedItem().name().equals("OIB") && 
						tempKorisnik.getOib().toString().equals(idPretrazivanjeTextField.getText())) {
					idTextField.setText(tempKorisnik.getIdKorisnika().toString());
					imeTextField.setText(tempKorisnik.getIme());
					prezimeTextField.setText(tempKorisnik.getPrezime());
					oibLabel.setText(tempKorisnik.getOib().toString());
					mailTextField.setText(tempKorisnik.getMailAdresa());
					mobitelTextField.setText("0" + tempKorisnik.getBrojMobitela().toString());
					datumUclanjenjaLabel.setText(tempKorisnik.getDatumUclanjenja().format(dtf));
					idPretrazivanjeTextField.clear();
					ID=tempKorisnik.getID();
				}
			}
			
		}
		listaIzdanihKnjiga = BazaPodataka.dohvtiTransakciju();
		List<IzdanaKnjiga> filtriraneTransakcije = new ArrayList<>();
		
		if (idTextField.getText().isEmpty() == false) {
			filtriraneTransakcije = listaIzdanihKnjiga.stream()
					.filter(m -> m.getKorisnik().getIdKorisnika().toString().equals(idTextField.getText().toLowerCase()))
					.collect(Collectors.toList());
		} else {
			filtriraneTransakcije.clear();
		}
		obsListaTransakcija = FXCollections.observableArrayList(filtriraneTransakcije);
		posudeneKnjigeTableView.setItems(obsListaTransakcija);
		
	}
	
	public void obrisiKorisnika() throws SQLException, IOException {
		Long idKorisnika = Long.parseLong(idTextField.getText());
		String ime = imeTextField.getText();
		String prezime = prezimeTextField.getText();
		Long oib = Long.parseLong(oibLabel.getText());
		String mail = mailTextField.getText();
		Integer mob = Integer.parseInt(mobitelTextField.getText());
		LocalDate datumUclanjenja = LocalDate.parse(datumUclanjenjaLabel.getText(),dtf);
		//datumUclanjenja.format(dtf);
		
		Korisnik noviKorisnik = new Korisnik(ime, prezime, mail, mob, oib, datumUclanjenja, idKorisnika);
		BazaPodataka.obrisiKorisnika(noviKorisnik);
		listaKorisnika=BazaPodataka.dohvatiKorisnike();
		
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);
		alert.setHeaderText(null);
		alert.setContentText("Korisnik uspješno obrisan");
		alert.showAndWait();
		
		posudeneKnjigeTableView.getItems().clear();
		idTextField.clear();
		imeTextField.clear();
		prezimeTextField.clear();
		oibLabel.setText("");
		mailTextField.clear();
		mobitelTextField.clear();
		datumUclanjenjaLabel.setText("");
	}
	
	public void updateKorisnika() throws SQLException, IOException {
		Long idKorisnika = Long.parseLong(idTextField.getText());
		String ime = imeTextField.getText();
		String prezime = prezimeTextField.getText();
		Long oib = Long.parseLong(oibLabel.getText());
		String mail = mailTextField.getText();
		Integer mob = Integer.parseInt(mobitelTextField.getText());
		LocalDate datumUclanjenja = LocalDate.parse(datumUclanjenjaLabel.getText(),dtf);
		//datumUclanjenja.format(dtf);
		
		Korisnik noviKorisnik = new Korisnik(ime, prezime, mail, mob, oib, datumUclanjenja, idKorisnika);
		noviKorisnik.setID(ID);
		BazaPodataka.updateKorisnika(noviKorisnik);
		listaKorisnika=BazaPodataka.dohvatiKorisnike();
		
		Alert alert = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setAlwaysOnTop(true);
		alert.setHeaderText(null);
		alert.setContentText("Podaci uspješno spremljeni");
		alert.showAndWait();
	}
	
}
