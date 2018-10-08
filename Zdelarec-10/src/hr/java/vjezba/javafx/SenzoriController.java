package hr.java.vjezba.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.OpisTlaka;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorTlakaZraka;
import hr.java.vjezbe.entitet.SenzorVlage;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
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
	private TableColumn<Senzor, String> statusColumn;
	@FXML
	private Label pogrska;

	/**
	 * Inicijalizira scenu.
	 * 
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
		statusColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Senzor, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Senzor, String> param) {
						return new ReadOnlyObjectWrapper<String>(param.getValue().getStatus());
					}
				});

		listaSenzora = BazaPodataka.dohvatiSenzore();

		prikaziSenzore();
		promjeniVrijednost();
	}

	/**
	 * Prikazuje podatke o senzorima.
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public void prikaziSenzore() throws SQLException, IOException {
		listaSenzora = BazaPodataka.dohvatiSenzore();
		List<Senzor> filtriraniSenzori = new ArrayList<>();
		if (senzoriFilterTextField.getText().isEmpty() == false) {
			filtriraniSenzori = listaSenzora.stream().filter(
					m -> m.getMjernaJedinica().toLowerCase().contains(senzoriFilterTextField.getText().toLowerCase()))
					.collect(Collectors.toList());
		} else {
			filtriraniSenzori = listaSenzora;
		}

		if (filtriraniSenzori.isEmpty()) {
			senzoriFilterTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ; -fx-border-radius: 2px;");
			pogrska.setText("Ne postoji u listi!");
			pogrska.setStyle(
					"-fx-text-fill: red; -fx-background-color:rgba(180, 180, 180,0.3);-fx-border-radius: 5px;");
		} else {
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
		Main.mapaTemp.put(Main.mapaTemp.values().size() + 1, temperatura);
		Main.mapaTlaka.put(Main.mapaTlaka.values().size() + 1, tlak);
		Main.mapaVlage.put(Main.mapaVlage.values().size() + 1, vlaga);
		listaSenzora.add(temperatura);
		listaSenzora.add(vlaga);
		listaSenzora.add(tlak);
	}

	public static void spremiSenzor(Senzor senzor) {
		obsListaSenzora.add(senzor);
	}

	private void promjeniVrijednost() {
		senzoriTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Senzor odabraniSenzor = senzoriTableView.getSelectionModel().getSelectedItem();
				obsListaSenzora.remove(odabraniSenzor);
				FXMLLoader loader = new FXMLLoader();

				// temperatura

				if (odabraniSenzor.getMjernaJedinica() == "°C") {
					SenzorTemperature temperatura = (SenzorTemperature) odabraniSenzor;
					RadSenzora rad = null;
					for (RadSenzora radS : RadSenzora.values()) {
						if (temperatura.getRadSenzora().equals(radS)) {
							rad = radS;
						}
					}
					loader.setLocation(getClass().getResource("PromjeniTemp.fxml"));
					try {
						loader.load();
						TemperaturaController ctl = (TemperaturaController) loader.getController();
						ctl.Id = temperatura.getId();
						ctl.komponentaTextField.setText(temperatura.getNazivKomponente());
						ctl.vrijednostTempTextField.setText(temperatura.getVrijednost().toString());
						ctl.radTempCombobox.getSelectionModel().select(rad);
						if (temperatura.getIsAktivan()) {
							ctl.aktivanTemp.setSelected(true);
						}
						BorderPane root = (BorderPane) loader.getRoot();
						Scene scene = new Scene(root, 600, 400);
						scene.getStylesheets().add(getClass().getResource("application6.css").toExternalForm());
						Stage stage = new Stage();
						stage.getIcons().add(new Image("file:sun.png"));
						stage.setTitle("Meteo Base - Promjena senzora temperature");
						stage.setScene(scene);
						stage.show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				// tlak

				if (odabraniSenzor.getMjernaJedinica() == "hPa") {
					SenzorTlakaZraka tlak = (SenzorTlakaZraka) odabraniSenzor;
					RadSenzora rad = null;
					for (RadSenzora radS : RadSenzora.values()) {
						if (tlak.getRadSenzora().equals(radS)) {
							rad = radS;
						}
					}
					OpisTlaka opis = null;
					for (OpisTlaka opisTlaka : OpisTlaka.values()) {
						if (opisTlaka.name().equals(tlak.getOpis())) {
							opis = opisTlaka;
						}
					}
					loader.setLocation(getClass().getResource("PromjeniTlak.fxml"));
					try {
						loader.load();
						TlakController ctl = (TlakController) loader.getController();
						ctl.Id = tlak.getId();
						ctl.vrijednostTlakTextField.setText(tlak.getVrijednost().toString());
						ctl.radTlakCombobox.getSelectionModel().select(rad);
						ctl.opisTlakCombobox.getSelectionModel().select(opis);
						if (tlak.getIsAktivan()) {
							ctl.aktivanTlak.setSelected(true);
						}
						BorderPane root = (BorderPane) loader.getRoot();
						Scene scene = new Scene(root, 600, 400);
						scene.getStylesheets().add(getClass().getResource("application6.css").toExternalForm());
						Stage stage = new Stage();
						stage.getIcons().add(new Image("file:sun.png"));
						stage.setTitle("Meteo Base - Promjena senzora tlaka zraka");
						stage.setScene(scene);
						stage.show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				// vlaga
				
				if (odabraniSenzor.getMjernaJedinica() == "%") {
					SenzorVlage vlaga = (SenzorVlage) odabraniSenzor;
					RadSenzora rad = null;
					for (RadSenzora radS : RadSenzora.values()) {
						if (vlaga.getRadSenzora().equals(radS)) {
							rad = radS;
						}
					}
					loader.setLocation(getClass().getResource("PromjeniVlagu.fxml"));
					try {
						loader.load();
						VlagaController ctl = (VlagaController) loader.getController();
						ctl.Id = vlaga.getId();
						ctl.vrijednostVlagaTextField.setText(vlaga.getVrijednost().toString());
						ctl.radVlagaCombobox.getSelectionModel().select(rad);
						if (vlaga.getIsAktivan()) {
							ctl.aktivanVlaga.setSelected(true);
						}
						BorderPane root = (BorderPane) loader.getRoot();
						Scene scene = new Scene(root, 600, 400);
						scene.getStylesheets().add(getClass().getResource("application6.css").toExternalForm());
						Stage stage = new Stage();
						stage.getIcons().add(new Image("file:sun.png"));
						stage.setTitle("Meteo Base - Promjena senzora vlage");
						stage.setScene(scene);
						stage.show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
	}

}