package hr.java.stranicenje.javafx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PocetniEkranController {
	
	@FXML
	Button fifo;
	@FXML
	Button rfu;
	@FXML
	Button rlu;
	
	public void prikaziEkranZaFifo() {
		try {
			BorderPane fifo = FXMLLoader.load(Main.class.getResource("Fifo.fxml"));
			Scene scene = new Scene(fifo, 600, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			//stage.getIcons().add(new Image("file:sun.png"));
			stage.setTitle("Stranicenje - FIFO algoritam");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void prikaziEkranZaLfu() {
		try {
			BorderPane fifo = FXMLLoader.load(Main.class.getResource("Lfu.fxml"));
			Scene scene = new Scene(fifo, 600, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			//stage.getIcons().add(new Image("file:sun.png"));
			stage.setTitle("Stranicenje - LFU algoritam");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaLru() {
		try {
			BorderPane fifo = FXMLLoader.load(Main.class.getResource("Lru.fxml"));
			Scene scene = new Scene(fifo, 600, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			//stage.getIcons().add(new Image("file:sun.png"));
			stage.setTitle("Stranicenje - LRU algoritam");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
