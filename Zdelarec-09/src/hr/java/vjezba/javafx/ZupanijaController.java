package hr.java.vjezba.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Zupanija;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Hendla prozor za prikaz zupanija.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class ZupanijaController {


	private static List<Zupanija> listaZupanija;
	static ObservableList<Zupanija> obsListaZupanija;
	static String nazivPromjena;
	public static Integer zupanijaId;
	static Boolean promjena=false;
	public static Integer drzavaId;
	
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
	 * @throws IOException 
	 * @throws SQLException 
	 */
	@FXML
	public void initialize() throws SQLException, IOException {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Zupanija, String>("naziv"));
		drzavaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Zupanija, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Zupanija, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getDrzava().getNaziv().toString());
					}
				});

		
		listaZupanija = BazaPodataka.dohvatiZupanije();
		prikaziZupanija();
		promjeniVrijednost();
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
	
	private void promjeniVrijednost() {
		zupanijeTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				 Zupanija odabranaZupanija = zupanijeTableView.getSelectionModel().getSelectedItem();
				 nazivPromjena=odabranaZupanija.getNaziv();
				 drzavaId=odabranaZupanija.getDrzava().getId();
				 zupanijaId=odabranaZupanija.getId();
				 promjena=true;
				 obsListaZupanija.remove(odabranaZupanija);
				 try {
						BorderPane novoMjestoPane = FXMLLoader.load(Main.class.getResource("DodajZupaniju.fxml"));
						Scene scene = new Scene(novoMjestoPane, 600, 400);
						scene.getStylesheets().add(getClass().getResource("application3.css").toExternalForm());
						Stage stage = new Stage();
						stage.getIcons().add(new Image("file:sun.png"));
						stage.setTitle("Meteo Base - Nove županije");
						stage.setScene(scene);
						stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		});
	}
	
	
}
