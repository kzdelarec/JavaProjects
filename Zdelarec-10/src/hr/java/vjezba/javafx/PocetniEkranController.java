package hr.java.vjezba.javafx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
