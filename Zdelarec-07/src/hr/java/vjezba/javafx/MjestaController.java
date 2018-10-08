package hr.java.vjezba.javafx;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.Zupanija;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * Hendla prozor za prikaz mjesta.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class MjestaController {

	private List<Mjesto> listaMjesta;
	private List<Drzava> listaDrzava;
	private List<Zupanija> listaZupanija;
	private List<Senzor> listaSenzora;
	private List<MjernaPostaja> listaPostaja;
	@FXML
	private TextField mjestaFilterTextField;
	@FXML
	private TableView<Mjesto> mjestaTableView;
	@FXML
	private TableColumn<Mjesto, String> nazivColumn;
	@FXML
	private TableColumn<Mjesto, String> tipColumn;
	@FXML
	private TableColumn<Mjesto, String> zupanijaColumn;
	@FXML
	private Label pogrska;

	/**
	 * Inicijalizira scenu.
	 */

	@FXML
	public void initialize() {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Mjesto, String>("naziv"));
		tipColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Mjesto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Mjesto, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getVrstaMjest().toString());
					}
				});
		zupanijaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Mjesto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Mjesto, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getZupanija().getNaziv());
					}
				});

		setCellValueFromTable();
		setListaDrzava(Main.dohvatiDrzave());
		setListaZupanija(Main.dohvatiZupanije());
		listaMjesta = Main.dohvatiMjesta();
		setListaSenzora(Main.dohvatiSenzore());
		setListaPostaja(Main.dohvatiPostaje());
	}

	/**
	 * Prikazuje podatke o mjestima.
	 */
	public void prikaziMjesta() {
		List<Mjesto> filtriranaMjesta = new ArrayList<Mjesto>();
		if (mjestaFilterTextField.getText().isEmpty() == false) {
			filtriranaMjesta = listaMjesta.stream()
					.filter(m -> m.getVrstaMjest().name().toLowerCase().contains(mjestaFilterTextField.getText().toLowerCase()))
					.collect(Collectors.toList());
		} else {
			filtriranaMjesta = listaMjesta;
		}
		if (filtriranaMjesta.isEmpty()) {
			mjestaFilterTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ; -fx-border-radius: 2px;");
			pogrska.setText("Ne postoji u listi!");
			pogrska.setStyle(
					"-fx-text-fill: red; -fx-background-color:rgba(180, 180, 180,0.3);-fx-border-radius: 5px;");
		} else {
			mjestaFilterTextField.setStyle(null);
			pogrska.setText("");
		}

		ObservableList<Mjesto> listaMjesta = FXCollections.observableArrayList(filtriranaMjesta);
		mjestaTableView.setItems(listaMjesta);
	}

	private void setCellValueFromTable() {
		
		
		mjestaTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(mjestaTableView.getItems().get(mjestaTableView.getSelectionModel().getSelectedIndex())
						.getNaziv().toString());
				alert.setHeaderText("Info");
				alert.setContentText("Naziv:" + mjestaTableView.getItems()
						.get(mjestaTableView.getSelectionModel().getSelectedIndex()).getNaziv().toString() + 
						"\nVrsta mjesta:" + mjestaTableView.getItems()
						.get(mjestaTableView.getSelectionModel().getSelectedIndex()).getVrstaMjest().toString());
				alert.showAndWait();
				
			}
		});
		
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
