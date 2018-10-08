package hr.java.vjezba.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.MjernaPostaja;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Hendla prozor za prikaz mjernih postaja.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class PostajeController {

	private static List<MjernaPostaja> listaPostaja;
	static ObservableList<MjernaPostaja> obsListaPostja;
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
	 * @throws IOException 
	 * @throws SQLException 
	 */
	@FXML
	public void initialize() throws SQLException, IOException {

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
		BazaPodataka.dohvatiSenzore();
		listaPostaja = BazaPodataka.dohvatiPostaje();
		prikaziPostaje();
		promjeniVrijednost();
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
		obsListaPostja = FXCollections.observableArrayList(filtriranePostaje);
		postajaTableView.setItems(obsListaPostja);
	}

	public static void dodajNovePostaje(MjernaPostaja mjernaPostaja) {
		obsListaPostja.add(mjernaPostaja);
		listaPostaja.add(mjernaPostaja);
		
	}
	
	private void promjeniVrijednost() {
		postajaTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				 MjernaPostaja odabranaPostaja = postajaTableView.getSelectionModel().getSelectedItem();
				 obsListaPostja.remove(odabranaPostaja);
				 FXMLLoader loader = new FXMLLoader();
				 
				 loader.setLocation(getClass().getResource("DodajMjernuPostaju.fxml"));
				 try {
					loader.load();
					DodajMjernuPostajuController ctl = (DodajMjernuPostajuController) loader.getController();
					ctl.Id = odabranaPostaja.getId();
					ctl.Id1 = odabranaPostaja.getSviSenzori().get(0).getId();
					ctl.Id2 = odabranaPostaja.getSviSenzori().get(1).getId();
					ctl.Id3 = odabranaPostaja.getSviSenzori().get(2).getId();
					ctl.idMjesta = odabranaPostaja.getMjesto().getId();
					ctl.promjena = true;
					ctl.nazivTextField.setText(odabranaPostaja.getNaziv());
					ctl.mjestoCombobox.getSelectionModel().select(ctl.observableListaMjesta.stream().filter(drz->drz.getId() == odabranaPostaja.getMjesto().getId()).findFirst().get());
					ctl.koordinataXtextField.setText(odabranaPostaja.getGeografskaTocka().getX().toString());
					ctl.koordinataYtextField.setText(odabranaPostaja.getGeografskaTocka().getY().toString());
					ctl.senzoriTempCombobox.getSelectionModel().select(odabranaPostaja.getSviSenzori().get(0).getId());
					ctl.senzoriTlakaCombobox.getSelectionModel().select(odabranaPostaja.getSviSenzori().get(1).getId());
					ctl.senzoriVlageCombobox.getSelectionModel().select(odabranaPostaja.getSviSenzori().get(2).getId());
					BorderPane root = (BorderPane)loader.getRoot();
					Scene scene = new Scene(root, 600, 400);
					scene.getStylesheets().add(getClass().getResource("application5.css").toExternalForm());
					Stage stage = new Stage();
					stage.getIcons().add(new Image("file:sun.png"));
					stage.setTitle("Meteo Base - Promjena mjerne postaje");
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