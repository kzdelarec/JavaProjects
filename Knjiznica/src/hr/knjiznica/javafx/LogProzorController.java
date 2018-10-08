package hr.knjiznica.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.knjiznica.baza.podataka.BazaPodataka;
import hr.knjiznica.entiteti.IzdanaKnjiga;
import hr.knjiznica.enumeracije.LogKategorijeEnum;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class LogProzorController {
	
	@FXML
	TableView <IzdanaKnjiga> transakcijeTableView;
	@FXML
	TableColumn <IzdanaKnjiga, Long> idKorisnikaColumn;
	@FXML
	TableColumn <IzdanaKnjiga, String> imeIprezimeKorisnikaColumn;
	@FXML
	TableColumn <IzdanaKnjiga, Long> idKnjigeColumn;
	@FXML
	TableColumn <IzdanaKnjiga, String> naslovKnjigeColumn;
	@FXML
	TableColumn <IzdanaKnjiga, String> autorKnjigeColumn;
	@FXML
	TableColumn <IzdanaKnjiga, String> datumPosudbeColumn;
	@FXML
	TableColumn <IzdanaKnjiga, String> datumPovratkaColumn;
	@FXML
	TableColumn <IzdanaKnjiga, String> usernameKnjiznicarColumn;
	@FXML
	TableColumn <IzdanaKnjiga, String> imeIprezimeKnjiznicaraColumn;
	@FXML
	ComboBox <LogKategorijeEnum> kategorijeComboBox;
	@FXML
	DatePicker datePicker;
	@FXML
	HBox pretragaHBox;
	@FXML
	TextField kljucnaRijecTextFied;
	@FXML
	Button pretragaButton;
	
	ObservableList<IzdanaKnjiga> obsListaTransakcija;
	
	String noviDatum;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyy.");
	
	@FXML
	public void initialize() {
		
		
		kategorijeComboBox.getItems().addAll(LogKategorijeEnum.values());
		kategorijeComboBox.getSelectionModel().select(0);
		pretragaHBox.getChildren().remove(datePicker);
		kategorijeComboBox.valueProperty().addListener(new ChangeListener<LogKategorijeEnum>() {
	        @Override public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, LogKategorijeEnum t, LogKategorijeEnum t1) {
	          if(t1.name() == "DatumPosudbe" || t1.name() == "DatumPovratka") {
	        	  pretragaHBox.getChildren().remove(pretragaButton);
	        	  if(pretragaHBox.getChildren().contains(kljucnaRijecTextFied))
	        		  pretragaHBox.getChildren().remove(kljucnaRijecTextFied);
	        	  if(!pretragaHBox.getChildren().contains(datePicker))
	        		  pretragaHBox.getChildren().add(datePicker);
	        	  pretragaHBox.getChildren().add(pretragaButton);
	          }
	          else {
	        	  pretragaHBox.getChildren().remove(pretragaButton);
	        	  if(!pretragaHBox.getChildren().contains(kljucnaRijecTextFied))
	        		  pretragaHBox.getChildren().add(kljucnaRijecTextFied);
	        	  if(pretragaHBox.getChildren().contains(datePicker))
	        		  pretragaHBox.getChildren().remove(datePicker);
	        	  pretragaHBox.getChildren().add(pretragaButton);
	          }
	        }    
	    });
		
		kljucnaRijecTextFied.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				try {
					pretrazi();
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		datePicker.getEditor().setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				try {
					noviDatum = datePicker.getEditor().getText();
					pretrazi();
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            try {
            	noviDatum = datePicker.getValue().format(dtf);
				pretrazi();
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });

		
		idKorisnikaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<IzdanaKnjiga, Long>, ObservableValue<Long>>() {
					@Override
					public ObservableValue<Long> call(CellDataFeatures<IzdanaKnjiga, Long> param) {
						return new ReadOnlyObjectWrapper<Long>(param.getValue().getKorisnik().getIdKorisnika());
					}
				});
		
		imeIprezimeKorisnikaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<IzdanaKnjiga, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<IzdanaKnjiga, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getKorisnik().getIme() + " " +
								param.getValue().getKorisnik().getPrezime() );
					}
				});
		
		usernameKnjiznicarColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<IzdanaKnjiga, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<IzdanaKnjiga, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getKnjiznicar().getKorisnickoIme());
					}
				});
		
		idKnjigeColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<IzdanaKnjiga, Long>, ObservableValue<Long>>() {
					@Override
					public ObservableValue<Long> call(CellDataFeatures<IzdanaKnjiga, Long> param) {
						return new ReadOnlyObjectWrapper<Long>(param.getValue().getKnjiga().getBarcodeId());
					}
				});
		naslovKnjigeColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<IzdanaKnjiga, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<IzdanaKnjiga, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getKnjiga().getNaslov());
					}
				});
		autorKnjigeColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<IzdanaKnjiga, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<IzdanaKnjiga, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getKnjiga().getAutor());
					}
				});
		imeIprezimeKnjiznicaraColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<IzdanaKnjiga, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<IzdanaKnjiga, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getKnjiznicar().getIme() + " " +
								param.getValue().getKnjiznicar().getPrezime() );
					}
				});
		datumPosudbeColumn.setCellValueFactory(new PropertyValueFactory<IzdanaKnjiga, String>("datumPosudbe"));
		datumPovratkaColumn.setCellValueFactory(new PropertyValueFactory<IzdanaKnjiga, String>("datumPovratka"));
		
		try {
			pretrazi();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void pretrazi() throws SQLException, IOException {
		
		List <IzdanaKnjiga> listaTransakcija = new ArrayList<>();
		listaTransakcija = BazaPodataka.dohvtiTransakciju();
		List<IzdanaKnjiga> filtriraneTranskacije = new ArrayList<>();
		if((pretragaHBox.getChildren().contains(kljucnaRijecTextFied) && !kljucnaRijecTextFied.getText().isEmpty()) 
				||(pretragaHBox.getChildren().contains(datePicker) && (datePicker.getValue() != null) || !datePicker.getEditor().getText().isEmpty())){
			if (kategorijeComboBox.getValue().toString().equals("Id korisnika")) {
				filtriraneTranskacije = listaTransakcija.stream()
						.filter(m -> m.getKorisnik().getIdKorisnika().toString().toLowerCase().contains(kljucnaRijecTextFied.getText().toLowerCase()))
						.collect(Collectors.toList());
			}
			
			if (kategorijeComboBox.getValue().toString().equals("Barkod")) {
				filtriraneTranskacije = listaTransakcija.stream()
						.filter(m -> m.getKnjiga().getBarcodeId().toString().toLowerCase().contains(kljucnaRijecTextFied.getText().toLowerCase()))
						.collect(Collectors.toList());
			}
			if (kategorijeComboBox.getValue().toString().equals("Naslov")) {
				filtriraneTranskacije = listaTransakcija.stream()
						.filter(m -> m.getKnjiga().getNaslov().toString().toLowerCase().contains(kljucnaRijecTextFied.getText().toLowerCase()))
						.collect(Collectors.toList());
			}
			if (kategorijeComboBox.getValue().toString().equals("Autor")) {
				filtriraneTranskacije = listaTransakcija.stream()
						.filter(m -> m.getKnjiga().getAutor().toString().toLowerCase().contains(kljucnaRijecTextFied.getText().toLowerCase()))
						.collect(Collectors.toList());
			}
			
			if (kategorijeComboBox.getValue().toString().equals("Datum posudbe")) {
				filtriraneTranskacije = listaTransakcija.stream()
						.filter(m -> m.getDatumPosudbe().toString().toLowerCase().contains(noviDatum.toLowerCase()))
						.collect(Collectors.toList());
			}
			if (kategorijeComboBox.getValue().toString().equals("Datum povratka")) {
				filtriraneTranskacije = listaTransakcija.stream()
						.filter(m -> m.getDatumPovratka().toString().toLowerCase().contains(noviDatum.toLowerCase()))
						.collect(Collectors.toList());
			}
			
			if (kategorijeComboBox.getValue().toString().equals("Korisnicko ime knjiznicara")) {
				filtriraneTranskacije = listaTransakcija.stream()
						.filter(m -> m.getKnjiznicar().getKorisnickoIme().toString().toLowerCase().contains(kljucnaRijecTextFied.getText().toLowerCase()))
						.collect(Collectors.toList());
			}
			
			
		}
		else {
			filtriraneTranskacije = listaTransakcija;
		}
		obsListaTransakcija = FXCollections.observableArrayList(filtriraneTranskacije);
		transakcijeTableView.setItems(obsListaTransakcija);
	}
	
}
