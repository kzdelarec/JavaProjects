package hr.java.vjezba.javafx;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.Zupanija;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * Hendla prozor za prikaz mjernih postaja.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class PostajeController {

	private List<Mjesto> listaMjesta;
	private List<Drzava> listaDrzava;
	private List<Zupanija> listaZupanija;
	private List<Senzor> listaSenzora;
	private List<MjernaPostaja> listaPostaja;
	private List<GeografskaTocka> listaTocaka;
	@FXML
	private TextField postajeFilterTextField;
	@FXML
	private TableView<MjernaPostaja> postajaTableView;
	@FXML
	private TableColumn<MjernaPostaja, String> nazivColumn;
	@FXML
	private TableColumn<MjernaPostaja, String> mjestoColumn;
	@FXML
	private TableColumn<MjernaPostaja, String> geoColumn;
	@FXML
	private Label pogrska;

	/**
	 * Inicijalizira scenu.
	 */
	@FXML
	public void initialize() {

		nazivColumn.setCellValueFactory(new PropertyValueFactory<MjernaPostaja, String>("naziv"));
		mjestoColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<MjernaPostaja, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<MjernaPostaja, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getMjesto().getNaziv().toString());
					}
				});
		geoColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<MjernaPostaja, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<MjernaPostaja, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getGeografskaTocka().getX().toString()
								+ "   " + param.getValue().getGeografskaTocka().getY().toString());
					}
				});
		listaTocaka = Main.dohvatiKoordinate();
		listaDrzava = Main.dohvatiDrzave();
		listaZupanija = Main.dohvatiZupanije();
		listaMjesta = Main.dohvatiMjesta();
		listaSenzora = Main.dohvatiSenzore();
		listaPostaja = Main.dohvatiPostaje();
	}

	/**
	 * Prikazuje podatke o mjernim postajama.
	 */
	public void prikaziPostaje() {
		List<MjernaPostaja> filtriranePostaje = new ArrayList<MjernaPostaja>();
		if (postajeFilterTextField.getText().isEmpty() == false) {
			filtriranePostaje = listaPostaja.stream()
					.filter(m -> m.getNaziv().toLowerCase().contains(postajeFilterTextField.getText().toLowerCase()))
					.collect(Collectors.toList());
		} else {
			filtriranePostaje = listaPostaja;
		}
		if(filtriranePostaje.isEmpty()) {
			postajeFilterTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ; -fx-border-radius: 2px;");
			pogrska.setText("Ne postoji u listi!");
			pogrska.setStyle( "-fx-text-fill: red; -fx-background-color:rgba(180, 180, 180,0.3);-fx-border-radius: 5px;");
		}
		else {
			postajeFilterTextField.setStyle(null);
			pogrska.setText("");
		}
		ObservableList<MjernaPostaja> listaPostja = FXCollections.observableArrayList(filtriranePostaje);
		postajaTableView.setItems(listaPostja);
	}

	public List<Drzava> getListaDrzava() {
		return listaDrzava;
	}

	public void setListaDrzava(List<Drzava> listaDrzava) {
		this.listaDrzava = listaDrzava;
	}

	public List<Zupanija> getListaZupanija() {
		return listaZupanija;
	}

	public void setListaZupanija(List<Zupanija> listaZupanija) {
		this.listaZupanija = listaZupanija;
	}

	public List<Senzor> getListaSenzora() {
		return listaSenzora;
	}

	public void setListaSenzora(List<Senzor> listaSenzora) {
		this.listaSenzora = listaSenzora;
	}

	public List<MjernaPostaja> getListaPostaja() {
		return listaPostaja;
	}

	public void setListaPostaja(List<MjernaPostaja> listaPostaja) {
		this.listaPostaja = listaPostaja;
	}

}