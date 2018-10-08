package hr.java.stranicenje.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import hr.java.stranicenje.entiteti.LfuStranica;
import hr.java.stranicenje.entiteti.LruStranica;
import hr.java.stranicenje.entiteti.Stranica;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LfuController {
	
	@FXML
	Button dodaj;
	@FXML
	TextField blok;
	@FXML
	TextField stranicaLabel;
	@FXML
	TableView<Stranica> fifoTableView;
	@FXML
	TableColumn<Stranica, Integer> korakColumn;
	@FXML
	TableColumn<Stranica, Integer> ulazColumn;
	@FXML
	TableColumn<Stranica, String> stoColumn;
	@FXML
	TableColumn<Stranica, String> izlazColumn;
	@FXML
	TableColumn<Stranica, String> memorijaColumn;
	@FXML
	Label informacijeLabel;
	
	Integer korak=1;
	Integer pogreska =0;
	Integer pogodak = 0;
	Double postotak =(double) 0;
	Boolean isPogodak = false;
	ObservableList<Stranica> obsListaStranica;
	List<Stranica> listaStranica = new ArrayList<>();
	List <Integer> blokMemorije = new ArrayList <>();
	List<LfuStranica> lfuStranice = new ArrayList<>();
	
	
	@FXML
	public void initialize() throws SQLException, IOException {

		informacijeLabel.setText("Pogodaka: " + pogodak + "\tPogresaka: " + pogreska + "\tPostotak pogreske: " + postotak + "%");
		korakColumn.setCellValueFactory(new PropertyValueFactory<Stranica, Integer>("korak"));
		ulazColumn.setCellValueFactory(new PropertyValueFactory<Stranica, Integer>("ulaz"));
		stoColumn.setCellValueFactory(new PropertyValueFactory<Stranica, String>("sto"));
		izlazColumn.setCellValueFactory(new PropertyValueFactory<Stranica, String>("izlaz"));
		memorijaColumn.setCellValueFactory(new PropertyValueFactory<Stranica, String>("memorija"));
		dodajUTablicu();
	}
	
	public void dodaj() {
		if(!blok.getText().isEmpty() && !stranicaLabel.getText().isEmpty()) {
			Integer memorija = null;
			Integer stranica = null;
			try {
				memorija = Integer.parseInt(blok.getText());
				stranica = Integer.parseInt(stranicaLabel.getText());
			} catch(Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Greska!");
				alert.setHeaderText("Pogreska prilikom izvrsavanja radnje:");
				alert.setContentText("Podaci za polja 'Velcina bloka' i 'Sranica' moraju biti cijeli brojevi");
				alert.showAndWait();
				return;
			}
			blok.setEditable(false);
			
			Stranica stranicaZaIspis = new Stranica();
			stranicaZaIspis.setKorak(korak);
			stranicaZaIspis.setUlaz(stranica);
			
			if(blokMemorije.contains(stranica)) {
				stranicaZaIspis.setSto("Pogodak");
				stranicaZaIspis.setIzlaz(" ");
				stranicaZaIspis.setMemorija(blokMemorije.toString());
				LfuStranica ponovljenaStranica = new LfuStranica();
				for(LfuStranica lfu : lfuStranice) {
					if(lfu.getVrijednost()==stranica) {
						ponovljenaStranica = lfu;
					}
				}
				lfuStranice.remove(ponovljenaStranica);
				lfuStranice.add(ponovljenaStranica);
				for(LfuStranica lfu : lfuStranice) {
					System.out.println(lfu.getVrijednost());
					
				}
				pogodak++;
			}
			
			if(!blokMemorije.contains(stranica) && lfuStranice.size()<memorija) {
				stranicaZaIspis.setSto("Pogreska");
				stranicaZaIspis.setIzlaz(" ");
				blokMemorije.add(stranica);
				
				LfuStranica lfuStranica = new LfuStranica();
				lfuStranica.setVrijednost(stranica);
				lfuStranica.setVrijeme(0);
				lfuStranice.add(lfuStranica);
				stranicaZaIspis.setMemorija(blokMemorije.toString());
				pogreska++; 
			}
			
			if(!blokMemorije.contains(stranica) && lfuStranice.size()==memorija) {
				
				stranicaZaIspis.setSto("Pogreska");
				stranicaZaIspis.setIzlaz(lfuStranice.get(0).getVrijednost().toString());
				blokMemorije.clear();
				lfuStranice.remove(0);
				for(LfuStranica lru : lfuStranice) {
					blokMemorije.add(lru.getVrijednost());	
				}
				blokMemorije.add(stranica);
				LfuStranica lfuStranica = new LfuStranica();
				lfuStranica.setVrijednost(stranica);
				lfuStranica.setVrijeme(0);
				lfuStranice.add(lfuStranica);
				stranicaZaIspis.setMemorija(blokMemorije.toString());
				pogreska++;
				
			}
			korak++;
			System.out.println("*******************");
			for(LfuStranica test : lfuStranice) {
				System.out.println(test.getVrijednost() + " - vrijeme->  " + test.getVrijeme());
			}
			postotak=(pogreska.doubleValue()/(pogodak.doubleValue()+pogreska.doubleValue()));
			informacijeLabel.setText("Pogodaka: " + pogodak + "\tPogresaka: " + pogreska + "\tPostotak pogreske: " + postotak*100 + "%");
			listaStranica.add(stranicaZaIspis);
			stranicaLabel.clear();
			stranicaLabel.requestFocus();
			obsListaStranica = FXCollections.observableArrayList(listaStranica);
			fifoTableView.setItems(obsListaStranica);
		}
		
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Upozorenje!");
			alert.setHeaderText("Pogreska prilikom izvrsavanja radnje:");
			alert.setContentText("Polja 'Velcina bloka' i 'Sranica' moraju biti popunjena!");

			alert.showAndWait();
		}
	}
	
	
	public void resetiraj() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Resetiranje postupka");
		alert.setHeaderText("Resetirnjem gubite sve dosadasnje podatke.");
		alert.setContentText("Zelite li nastaviti?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			blok.clear();
			blok.setEditable(true);
			stranicaLabel.clear();
			korak=1;
			pogreska =0;
			pogodak = 0;
			postotak =(double) 0;
			listaStranica.clear();
			blokMemorije.clear();
			lfuStranice.clear();
			informacijeLabel.setText("Pogodaka: " + pogodak + "\tPogresaka: " + pogreska + "\tPostotak pogreske: " + postotak + "%");
			fifoTableView.getItems().clear();
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
	}
	
	public void prikaziAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("O algoritmu");
		alert.setHeaderText(null);
		alert.setContentText("Najstarija stranica u memoriji je ona kuju �emo odabrati za zamjenu.\n"
				+ "Stranice se postavljaju kao vezana lista, stranica se skida sa kraja dok se nova\r\n" + 
				"stranica stavlja na po�etak.");

		alert.showAndWait();
	}
	
	public void prikaziHelp() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Korisnicka podrska");
		alert.setHeaderText("Developer: Kristijan Zdelarec");
		alert.setContentText("Email: kzdelarec@tvz.hrs");

		alert.showAndWait();
	}
	
	private void dodajUTablicu() {
		stranicaLabel.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ENTER) {
					dodaj();
				}
			}
		});
	}

}
