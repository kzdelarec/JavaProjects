package hr.java.vjezba.javafx;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Hendla prozor za prikaz pocetnog ekrana.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class PocetniEkranController {
	static ScheduledExecutorService scheduler;
	@FXML
	public Label zadnjiSenzorLabel;
	@FXML
	public void initialize() {
		
		Thread nit = new Thread(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						String zadnji = null;
						try {
							Senzor zadnjiSenzor = dohvatiZadnji().get(0);
							zadnji = zadnjiSenzor.getVrijednost() + zadnjiSenzor.getMjernaJedinica() + " - " + zadnjiSenzor.getStatus();
							zadnjiSenzorLabel.setText(zadnji);
							System.out.println(zadnji);
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
				});
			}});
		 scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(nit, 0, 1, TimeUnit.SECONDS);
		
	}
	
	public List<SenzorTemperature> dohvatiZadnji() throws FileNotFoundException, SQLException, IOException{
		List<SenzorTemperature> listTemp = new ArrayList<>();
		Connection veza = BazaPodataka.spajanjeNaBazuPodataka();
		Statement statementSenzorTopline = veza.createStatement();
		ResultSet resultSetSenzorTemp = statementSenzorTopline.executeQuery("SELECT * FROM POSTAJE.SENZOR_TEMP ORDER BY ID DESC LIMIT 1");
		while (resultSetSenzorTemp.next()) {
			Integer tempId = resultSetSenzorTemp.getInt("ID");
			String komponenta = resultSetSenzorTemp.getString("KOMPONENTA");
			BigDecimal vrijednost = new BigDecimal(resultSetSenzorTemp.getString("VRIJEDNOST"));
			Boolean isAktivanTemp = resultSetSenzorTemp.getBoolean("AKTIVAN");
			SenzorTemperature senzorTemp = new SenzorTemperature("°C", (byte) 2, komponenta);
			if(isAktivanTemp) {
				senzorTemp.setIsAktivan(true);
				senzorTemp.setStatus("Aktivan");
			}
			if(!isAktivanTemp) {
				senzorTemp.setIsAktivan(false);
				senzorTemp.setStatus("Neaktivan");
			}
			senzorTemp.setId(tempId);
			for (RadSenzora rad : RadSenzora.values()) {
				if (resultSetSenzorTemp.getString("RAD_SENZORA").equals(rad.name())) {
					senzorTemp.setRadSenzora(rad);
				}
			}
			senzorTemp.setVrijednost(vrijednost);
			listTemp.add(senzorTemp);
		}
		BazaPodataka.zatvaranjeVezeNaBazuPodataka(veza);
		return listTemp;
	}
	
	public void prikaziEkranZaNoveSenzore() {
		try {
			BorderPane novoMjestoPane = FXMLLoader.load(Main.class.getResource("DodajSenzore.fxml"));
			Scene scene = new Scene(novoMjestoPane, 600, 500);
			scene.getStylesheets().add(getClass().getResource("application6.css").toExternalForm());
			Stage stage = new Stage();
			stage.getIcons().add(new Image("file:sun.png"));
			stage.setTitle("Meteo Base - Novi senzori");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void prikaziEkranZaNovuPostaju() {
		try {
			BorderPane novoMjestoPane = FXMLLoader.load(Main.class.getResource("DodajMjernuPostaju.fxml"));
			Scene scene = new Scene(novoMjestoPane, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application5.css").toExternalForm());
			Stage stage = new Stage();
			stage.getIcons().add(new Image("file:sun.png"));
			stage.setTitle("Meteo Base - Nove mjerne postaje");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void prikaziEkranZaNovuDrzavu() {
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
	
	public void prikaziEkranZaNovuZupaniju() {
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
	
	public void prikaziEkranZaNovoMjesto() {
		try {
			BorderPane novoMjestoPane = FXMLLoader.load(Main.class.getResource("DodajMjesto.fxml"));
			Scene scene = new Scene(novoMjestoPane, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
			Stage stage = new Stage();
			stage.getIcons().add(new Image("file:sun.png"));
			stage.setTitle("Meteo Base - Nova mjesta");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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
