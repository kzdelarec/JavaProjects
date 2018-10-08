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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

/**
 * Hendla prozor za prikaz pocetnog ekrana.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class PocetniEkranController {
	
	/**
	 * Pokrece scenu za mjesta.
	 */
	@FXML
	public void prikaziEkranMjesta() {
		try {
			BorderPane zupanijePane = FXMLLoader.load(Main.class.getResource("Mjesta.fxml"));
			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Pokrece scenu za zupanije.
	 */
	@FXML
	public void prikaziEkranZupanije() {
		try {
			BorderPane zupanijePane = FXMLLoader.load(Main.class.getResource("Zupanije.fxml"));
			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Pokrece scenu za pocetni ekran.
	 */
	@FXML
	public void prikaziPocetniEkran() {
		try {
			BorderPane zupanijePane = FXMLLoader.load(Main.class.getResource("PocetniEkran.fxml"));
			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Pokrece scenu za drzave.
	 */
	@FXML
	public void prikaziEkranDrzave() {
		try {
			BorderPane zupanijePane = FXMLLoader.load(Main.class.getResource("Drzave.fxml"));
			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Pokrece scenu za postaje.
	 */
	@FXML
	public void prikaziEkranPostaje() {
		try {
			BorderPane zupanijePane = FXMLLoader.load(Main.class.getResource("Postaja.fxml"));
			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Pokrece scenu za senzore.
	 */
	@FXML
	public void prikaziEkranSenzori() {
		try {
			BorderPane zupanijePane = FXMLLoader.load(Main.class.getResource("Senzori.fxml"));
			Main.setCenterPane(zupanijePane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
