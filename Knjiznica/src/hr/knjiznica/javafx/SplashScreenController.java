package hr.knjiznica.javafx;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SplashScreenController {
	
	@FXML
	ImageView splashScreen;
	@FXML
	BorderPane pane;
	
	@FXML
	public void initialize() throws InterruptedException {
		splashScreen.setImage(new Image ("JLibrarySplashScreen.jpg"));
		makniSe();
	}
	
	public void makniSe() throws InterruptedException {
		
		
		Thread logInThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						Stage stage = new Stage();
						Pane root = new Pane();
						try {
							root = (Pane)FXMLLoader.load(getClass().getResource("LogInProzor.fxml"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Scene scene = new Scene(root,330,150);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						stage.setScene(scene);
						stage.getIcons().add(new Image("ikona.png"));
						stage.setTitle("JLibrary Management System");
						stage.show();
						Stage zatvori = (Stage) pane.getScene().getWindow();
						zatvori.close();
						
					}
				});
				
			}
		});
		logInThread.start();
		
	}
}
