package hr.java.vjezba.javafx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.Zupanija;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class SenzoriController {

	/**
	 * Hendla prozor za prikaz senzora.
	 * 
	 * @author Kristijan Zdelarec
	 *
	 */
	private List<Mjesto> listaMjesta;
	private List<Drzava> listaDrzava;
	private List<Zupanija> listaZupanija;
	private List<Senzor> listaSenzora;
	private List<MjernaPostaja> listaPostaja;
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
	 */
	@FXML
	public void initialize() {
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

		listaDrzava = Main.dohvatiDrzave();
		listaZupanija = Main.dohvatiZupanije();
		listaMjesta = Main.dohvatiMjesta();
		listaSenzora = Main.dohvatiSenzore();
		listaPostaja = Main.dohvatiPostaje();
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
		ObservableList<Senzor> listaSenzora = FXCollections.observableArrayList(filtriraniSenzori);
		senzoriTableView.setItems(listaSenzora);
	}

}