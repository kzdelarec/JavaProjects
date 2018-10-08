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

/**
 * Hendla prozor za prikaz zupanija.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class ZupanijaController {

	private List<Mjesto> listaMjesta;
	private List<Drzava> listaDrzava;
	private static List<Zupanija> listaZupanija;
	private List<Senzor> listaSenzora;
	private List<MjernaPostaja> listaPostaja;
	static ObservableList<Zupanija> obsListaZupanija;
	@FXML
	private TextField zupanijeFilterTextField;
	@FXML
	private TableView<Zupanija> zupanijeTableView;
	@FXML
	private TableColumn<Zupanija, String> nazivColumn;
	@FXML
	private TableColumn<Zupanija, String> drzavaColumn;
	@FXML
	private Label pogrska;

	/**
	 * Inicijalizira scenu.
	 */
	@FXML
	public void initialize() {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Zupanija, String>("naziv"));
		drzavaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Zupanija, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Zupanija, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getDrzava().getNaziv().toString());
					}
				});
		
		zupanijeFilterTextField.focusedProperty().addListener((obs, oldValue, newValue)->{
			if(!newValue) {
				if(!"".equals(zupanijeFilterTextField.getText())) {
					prikaziZupanija();
				}
				else {
					prikaziZupanija();
				}
			}
		});

		listaDrzava = Main.dohvatiDrzave();
		listaZupanija = Main.dohvatiZupanije();
		listaMjesta = Main.dohvatiMjesta();
		listaSenzora = Main.dohvatiSenzore();
		listaPostaja = Main.dohvatiPostaje();
		prikaziZupanija();
	}

	/**
	 * Prikazuje podatke o zupanijama.
	 */
	public void prikaziZupanija() {
		List<Zupanija> filtriraneZupanije = new ArrayList<>();
		if (zupanijeFilterTextField.getText().isEmpty() == false) {
			filtriraneZupanije = listaZupanija.stream().filter(m -> m.getNaziv().toLowerCase().contains(zupanijeFilterTextField.getText().toLowerCase()))
					.collect(Collectors.toList());
		} else {
			filtriraneZupanije = listaZupanija;
		}
		
		if(filtriraneZupanije.isEmpty()) {
			zupanijeFilterTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ; -fx-border-radius: 2px;");
			pogrska.setText("Ne postoji u listi!");
			pogrska.setStyle( "-fx-text-fill: red; -fx-background-color:rgba(180, 180, 180,0.3);-fx-border-radius: 5px;");
		}
		else {
			zupanijeFilterTextField.setStyle(null);
			pogrska.setText("");
		}
		obsListaZupanija = FXCollections.observableArrayList(filtriraneZupanije);
		zupanijeTableView.setItems(obsListaZupanija);
	}

	public static void dodajNovuZupaniju(Zupanija zupanija) {
		obsListaZupanija.add(zupanija);
		
	}
	
	
	
	
}
