package hr.java.vjezba.javafx;

import java.io.IOException;
import java.math.BigDecimal;
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

/**
 * Hendla prozor za prikaz drzava.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class DrzaveController {

	private List<Mjesto> listaMjesta;
	private List<Drzava> listaDrzava;
	private List<Zupanija> listaZupanija;
	private List<Senzor> listaSenzora;
	private List<MjernaPostaja> listaPostaja;
	int flag=0;
	@FXML
	private TextField drzaveFilterTextField;
	@FXML
	private TableView<Drzava> drzaveTableView;
	@FXML
	private TableColumn<Drzava, String> nazivColumn;
	@FXML
	private TableColumn<Drzava, BigDecimal> povrsinaColumn;
	@FXML
	private Label pogrska;

	/**
	 * Inicijalizira scenu.
	 */
	@FXML
	
	public void initialize() {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Drzava, String>("naziv"));
		povrsinaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Drzava, BigDecimal>, ObservableValue<BigDecimal>>() {
					@Override
					public ObservableValue<BigDecimal> call(CellDataFeatures<Drzava, BigDecimal> param) {
						return new ReadOnlyObjectWrapper<BigDecimal>(param.getValue().getPovrsina());
					}
				});

		if(flag==0) {
			listaDrzava = Main.dohvatiDrzave();
			listaZupanija = Main.dohvatiZupanije();
			listaMjesta = Main.dohvatiMjesta();
			listaSenzora = Main.dohvatiSenzore();
			listaPostaja = Main.dohvatiPostaje();
			flag++;
		}
		
	}

	/**
	 * Prikazuje podatke o drzavama.
	 */
	public void prikaziDrzave() {
		List<Drzava> filtriraneDrzave = new ArrayList<>();
		if (drzaveFilterTextField.getText().isEmpty() == false) {
			filtriraneDrzave = listaDrzava.stream().filter(m -> m.getNaziv().toLowerCase().contains(drzaveFilterTextField.getText().toLowerCase()))
					.collect(Collectors.toList());
		} else {
			filtriraneDrzave = listaDrzava;
		}
		if(filtriraneDrzave.isEmpty()) {
			drzaveFilterTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ; -fx-border-radius: 2px;");
			pogrska.setText("Ne postoji u listi!");
			pogrska.setStyle( "-fx-text-fill: red; -fx-background-color:rgba(180, 180, 180,0.3);-fx-border-radius: 5px;");
		}
		else {
			drzaveFilterTextField.setStyle(null);
			pogrska.setText("");
		}
		ObservableList<Drzava> listaDrzava= FXCollections.observableArrayList(filtriraneDrzave);
		drzaveTableView.setItems(listaDrzava);
	}
	
	
	
	
}