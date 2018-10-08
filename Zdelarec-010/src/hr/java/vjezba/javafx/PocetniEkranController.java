package hr.java.vjezba.javafx;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Zupanija;
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
	@FXML
	public Label zupanijaLabel;
	
	public String nazivZadnjezZupanije;
	static ScheduledExecutorService scheduler;

	public void initialize() throws SQLException, IOException {
		//zupanijaLabel.setText(dohvatiZupanije().get(0).getNaziv());

		Thread nit = new Thread(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						String l = null;
						try {
							l = dohvatiZupanije().get(0).getNaziv();
							zupanijaLabel.setText(l);
							System.out.println(l);
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
				});
			}});
		 scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(nit, 0, 1, TimeUnit.SECONDS);
	}
	
	private static final String DATABASE_FILE = "bazaPodataka.properties";

	private static Connection spajanjeNaBazuPodataka() throws SQLException, FileNotFoundException, IOException {
		Properties svojstva = new Properties();
		svojstva.load(new FileReader(DATABASE_FILE));
		String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
		String korisnickoIme = svojstva.getProperty("korisnickoIme");
		String lozinka = svojstva.getProperty("lozinka");
		Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);

		return veza;
	}

	private static void zatvaranjeVezeNaBazuPodataka(Connection veza) throws SQLException {
		veza.close();
	}
	//SELECT * FROM    TABLE WHERE   ID = (SELECT MAX(ID)  FROM TABLE)
	
	public  List<Zupanija> dohvatiZupanije() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementZupanije = veza.createStatement();
		ResultSet resultSetZupaije = statementZupanije.executeQuery("SELECT * FROM POSTAJE.ZUPANIJA WHERE ID = (SELECT MAX(ID) FROM POSTAJE.ZUPANIJA)");
		List<Zupanija> listaZupanija = new ArrayList<>();
		//List<Drzava> drzave = dohvatiDrzave();
		while (resultSetZupaije.next()) {
			Integer zupanijaId = resultSetZupaije.getInt("ID");
			String naziv = resultSetZupaije.getString("NAZIV");
			int id = Integer.parseInt(resultSetZupaije.getString("DRZAVA_ID"));
			Zupanija zupanija = new Zupanija(naziv, BazaPodataka.dohvatiDrzave().stream().
				    filter(drz->drz.getId()==id).
				    findFirst().get());
			zupanija.setId(zupanijaId);
			listaZupanija.add(zupanija);
			System.out.println(zupanija.getNaziv());
			nazivZadnjezZupanije = zupanija.getNaziv();
			
			
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaZupanija;
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
