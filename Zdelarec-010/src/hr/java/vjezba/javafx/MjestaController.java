package hr.java.vjezba.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.VrstaMjesta;
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
 * Hendla prozor za prikaz mjesta.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class MjestaController {

	
	private static List<Mjesto> listaMjesta;
	static ObservableList<Mjesto> observableListaMjesta;
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
	 * @throws IOException 
	 * @throws SQLException 
	 */
	@FXML
	public void initialize() throws SQLException, IOException {
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

		listaMjesta = BazaPodataka.dohvatiMjesto();
		prikaziMjesta();
		promjeniVrijednost();
	}

	/**
	 * Prikazuje podatke o mjestima.
	 */
	public void prikaziMjesta() {
		List<Mjesto> filtriranaMjesta = new ArrayList<Mjesto>();
		if (mjestaFilterTextField.getText().isEmpty() == false) {
			filtriranaMjesta = listaMjesta.stream().filter(m -> m.getNaziv().toLowerCase()
					.contains(mjestaFilterTextField.getText().toLowerCase()))
					.collect(Collectors.toList());
		} else {
			filtriranaMjesta = listaMjesta;
		}
		if(filtriranaMjesta.isEmpty()) {
			mjestaFilterTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ; -fx-border-radius: 2px;");
			pogrska.setText("Ne postoji u listi!");
			pogrska.setStyle( "-fx-text-fill: red; -fx-background-color:rgba(180, 180, 180,0.3);-fx-border-radius: 5px;");
		}
		else {
			mjestaFilterTextField.setStyle(null);
			pogrska.setText("");
		}
		

		observableListaMjesta = FXCollections.observableArrayList(filtriranaMjesta);
		mjestaTableView.setItems(observableListaMjesta);
	}

	public static void dodajNovoMjesto(Mjesto mjesto) {
		observableListaMjesta.add(mjesto);
		listaMjesta.add(mjesto);
	}
	
	private void promjeniVrijednost() {
		mjestaTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				 Mjesto odabranoMjesto = mjestaTableView.getSelectionModel().getSelectedItem();
				 observableListaMjesta.remove(odabranoMjesto);
				 VrstaMjesta vrsta = null;
					for (VrstaMjesta mj : VrstaMjesta.values()) {
						if (odabranoMjesto.getVrstaMjest().equals(mj)) {
							vrsta = mj;
						}
					}
				 
				 FXMLLoader loader = new FXMLLoader();
				 loader.setLocation(getClass().getResource("DodajMjesto.fxml"));
					try {
						loader.load();
						DodajMjestacontroller ctl = (DodajMjestacontroller) loader.getController();
						ctl.Id = odabranoMjesto.getId();
						ctl.nazivTextField.setText(odabranoMjesto.getNaziv());
						ctl.zupanijaCombobox.getSelectionModel()
						.select(ctl.listaZupanija.stream().filter(drz->drz.getId() == odabranoMjesto.getZupanija().getId()).findFirst().get());
						ctl.vrstaCombobox.getSelectionModel().select(vrsta);
						ctl.spremiButton.setText("Spremi");
						ctl.promjena=true;
						BorderPane root = (BorderPane) loader.getRoot();
						Scene scene = new Scene(root, 600, 400);
						scene.getStylesheets().add(getClass().getResource("application4.css").toExternalForm());
						Stage stage = new Stage();
						stage.getIcons().add(new Image("file:sun.png"));
						stage.setTitle("Meteo Base - Promjena mjesta");
						stage.setScene(scene);
						stage.show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
					
		});
	}
}
