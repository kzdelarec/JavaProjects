package hr.java.vjezba.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorTlakaZraka;
import hr.java.vjezbe.entitet.SenzorVlage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class SenzoriController {

	/**
	 * Hendla prozor za prikaz senzora.
	 * 
	 * @author Kristijan Zdelarec
	 *
	 */

	private static List<Senzor> listaSenzora;
	static ObservableList<Senzor> obsListaSenzora;
	@FXML
	private TextField senzoriFilterTextField;
	@FXML
	private TableView<Senzor> senzoriTableView;
	@FXML
	private TableColumn<Senzor, String> mjernaJedinicaColumn;
	@FXML
	private TableColumn<Senzor, String> preciznostColumn;
	@FXML
	private Label pogrska;

	/**
	 * Inicijalizira scenu.
	 * @throws IOException 
	 * @throws SQLException 
	 */
	@FXML
	public void initialize() throws SQLException, IOException {
		mjernaJedinicaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Senzor, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getMjernaJedinica());
					}
				});
		preciznostColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Senzor, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().stringPreciznost());
					}
				});

		listaSenzora = BazaPodataka.dohvatiSenzore();

		prikaziSenzore();
	}

	/**
	 * Prikazuje podatke o senzorima.
	 */
	public void prikaziSenzore() {
		List<Senzor> filtriraniSenzori = new ArrayList<>();
		if (senzoriFilterTextField.getText().isEmpty() == false) {
			filtriraniSenzori = listaSenzora.stream().filter(
					m -> m.getMjernaJedinica().toLowerCase().contains(senzoriFilterTextField.getText().toLowerCase()))
					.collect(Collectors.toList());
		} else {
			filtriraniSenzori = listaSenzora;
		}
		
		if(filtriraniSenzori.isEmpty()) {
			senzoriFilterTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ; -fx-border-radius: 2px;");
			pogrska.setText("Ne postoji u listi!");
			pogrska.setStyle( "-fx-text-fill: red; -fx-background-color:rgba(180, 180, 180,0.3);-fx-border-radius: 5px;");
		}
		else {
			senzoriFilterTextField.setStyle(null);
			pogrska.setText("");
		}
		obsListaSenzora = FXCollections.observableArrayList(filtriraniSenzori);
		senzoriTableView.setItems(obsListaSenzora);
	}

	public static void dodajNoveSenzore(SenzorTemperature temperatura, SenzorVlage vlaga, SenzorTlakaZraka tlak) {
		obsListaSenzora.add(temperatura);
		obsListaSenzora.add(vlaga);
		obsListaSenzora.add(tlak);
		Main.mapaTemp.put(Main.mapaTemp.values().size()+1, temperatura);
		Main.mapaTlaka.put(Main.mapaTlaka.values().size()+1, tlak);
		Main.mapaVlage.put(Main.mapaVlage.values().size()+1, vlaga);
		listaSenzora.add(temperatura);
		listaSenzora.add(vlaga);
		listaSenzora.add(tlak);
		
		
	}

}