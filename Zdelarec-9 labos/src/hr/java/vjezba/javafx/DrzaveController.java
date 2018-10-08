package hr.java.vjezba.javafx;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Drzava;
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
 * Hendla prozor za prikaz drzava.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class DrzaveController {


	private static List<Drzava> listaDrzava;
	static ObservableList<Drzava> obsListaDrzava;
	static String nazivPromjena;
	static String povrsinaPromjena;
	static Boolean promjena=false;
	static Integer drzavaId;

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
	 * @throws IOException 
	 * @throws SQLException 
	 */
	@FXML
	
	public void initialize() throws SQLException, IOException {
		nazivColumn.setCellValueFactory(new PropertyValueFactory<Drzava, String>("naziv"));
		povrsinaColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Drzava, BigDecimal>, ObservableValue<BigDecimal>>() {
					@Override
					public ObservableValue<BigDecimal> call(CellDataFeatures<Drzava, BigDecimal> param) {
						return new ReadOnlyObjectWrapper<BigDecimal>(param.getValue().getPovrsina());
					}
				});


			listaDrzava = BazaPodataka.dohvatiDrzave();
		prikaziDrzave();
		promjeniVrijednost();
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
		obsListaDrzava= FXCollections.observableArrayList(filtriraneDrzave);
		drzaveTableView.setItems(obsListaDrzava);
	}

	public static void dodajNovuDrzavu(Drzava drzava) {
		obsListaDrzava.add(drzava);
		listaDrzava.add(drzava);
		
	}
	
	private void promjeniVrijednost() {
		drzaveTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				 Drzava odabranaDrzava = drzaveTableView.getSelectionModel().getSelectedItem();
				 nazivPromjena=odabranaDrzava.getNaziv();
				 povrsinaPromjena=odabranaDrzava.getPovrsina().toString();
				 drzavaId=odabranaDrzava.getId();
				 promjena=true;
				 obsListaDrzava.remove(odabranaDrzava);
				 try {
						BorderPane novoMjestoPane = FXMLLoader.load(Main.class.getResource("DodajDrzavu.fxml"));
						Scene scene = new Scene(novoMjestoPane, 600, 400);
						scene.getStylesheets().add(getClass().getResource("application4.css").toExternalForm());
						Stage stage = new Stage();
						stage.getIcons().add(new Image("file:sun.png"));
						stage.setTitle("Meteo Base - Nove države");
						stage.setScene(scene);
						stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		});
	}
	
}