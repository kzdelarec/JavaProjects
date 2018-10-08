package hr.knjiznica.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import hr.knjiznica.baza.podataka.BazaPodataka;
import hr.knjiznica.entiteti.Knjiga;
import hr.knjiznica.entiteti.Knjiznicar;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class GlavniProzorController {
	@FXML
	Button logInButton;
	@FXML
	BorderPane appPane;
	@FXML
	Label knjiznicarLabel;
	@FXML
	WebView cam1WebView;
	@FXML
	WebView cam2WebView;
	@FXML
	WebView cam3WebView;
	@FXML
	WebView cam4WebView;
	@FXML
	VBox meniVbox;
	@FXML
	JFXHamburger hamburger;
	@FXML
	ImageView pozadina;
	@FXML
	ImageView knjiznicarIkona;
	@FXML
	Button logButton;

	Stage primaryStage = new Stage();

	public String korisnickoIme;
	public String lozinka;
	public Boolean isAdmin;
	public Boolean isAktivan;
	public String ime;
	public String prezime;
	public String mailAdresa;
	public Integer brojMobitela;
	public Integer ID;
	public Knjiznicar trenutniKnjiznicar = new Knjiznicar(ime, prezime, mailAdresa, brojMobitela, korisnickoIme,
			lozinka, isAdmin, isAktivan);

	List<Knjiznicar> sviKnjiznicari = new ArrayList<>();
	static ObservableList<Knjiznicar> obsListaKnjiznicara;
	static ObservableList<Knjiga> obsListaKnjiga;
	public Boolean isClosed = true;
	Double vBoxWidth = 0.0;
	HamburgerBackArrowBasicTransition transition;

	@FXML
	public void initialize() {

		trenutniKnjiznicar.setID(ID);
		pokreniVideoThread();
		appPane.setLeft(null);
		vBoxWidth = meniVbox.getPrefHeight();
		pokrenuMeni();

	}

	public void pokreniVideoThread() {
		Thread videoThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						cam1WebView.getEngine().load("https://www.youtube.com/embed/q-snIvsb-30?autoplay=1&mute=1");
						cam2WebView.getEngine().load("https://www.youtube.com/embed/RDao2UKpfsc?autoplay=1&mute=1");
						cam3WebView.getEngine().load("https://www.youtube.com/embed/KsFWSyTmk7c?autoplay=1&mute=1");
						cam4WebView.getEngine().load("https://www.youtube.com/embed/HTqBpsc48CE?autoplay=1&mute=1");
					}
				});

			}
		});
		// videoThread.start();
	}

	public void pokrenuMeni() {
		transition = new HamburgerBackArrowBasicTransition(hamburger);
		transition.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			transition.setRate(transition.getRate() * -1);
			transition.play();

			if (isClosed) {
				otvoriSideMeni();
			} else {
				zatvoriSideMeni();
			}
		});
	}

	public void zatvoriSideMeni() {
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, new KeyValue(meniVbox.prefWidthProperty(), vBoxWidth)),
				new KeyFrame(Duration.millis(100), new KeyValue(meniVbox.prefWidthProperty(), 0)));
		timeline.play();
		timeline.setOnFinished(event -> appPane.setLeft(null));
		isClosed = true;
	}

	public void otvoriSideMeni() {
		appPane.setLeft(meniVbox);
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(meniVbox.prefWidthProperty(), 0)),
				new KeyFrame(Duration.millis(100), new KeyValue(meniVbox.prefWidthProperty(), vBoxWidth)));
		timeline.play();
		isClosed = false;
	}

	public void odjaviSe(ActionEvent event) {
		System.out.println("Odjavio se: " + trenutniKnjiznicar.getIme() + " " + trenutniKnjiznicar.getPrezime());
		Stage stage = (Stage) appPane.getScene().getWindow();
		stage.close();
		Stage logInStage = new Stage();
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("LogInProzor.fxml"));
			Scene scene = new Scene(root, 300, 150);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			logInStage.setScene(scene);
			stage.getIcons().add(new Image("ikona.png"));
			stage.setTitle("JLibrary Management System");
			logInStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void projenaPodataka() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				zatvoriSideMeni();
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						transition.setRate(transition.getRate() * -1);
						transition.play();

					}
				});

			}
		});
		thread.start();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("PromjenaKnjiznicaraProzor.fxml"));
		try {
			loader.load();
			PromjenaKnjiznicaraProzorController ctl = (PromjenaKnjiznicaraProzorController) loader.getController();
			if (trenutniKnjiznicar.getIsAdmin() == false) {
				ctl.IDLabel.setText(trenutniKnjiznicar.getID().toString());
				ctl.imeTextField.setText(trenutniKnjiznicar.getIme());
				ctl.imeTextField.setDisable(true);
				ctl.prezimeTextField.setText(trenutniKnjiznicar.getPrezime());
				ctl.prezimeTextField.setDisable(true);
				ctl.mailTextField.setText(trenutniKnjiznicar.getMailAdresa());
				ctl.mailTextField.setDisable(true);
				ctl.mobitelextField.setText(trenutniKnjiznicar.getBrojMobitela().toString());
				ctl.mobitelextField.setDisable(true);
				ctl.korisnickoImeLabel.setText(trenutniKnjiznicar.getKorisnickoIme());
				ctl.lozinkaTextField.setText(trenutniKnjiznicar.getLozinka());
				ctl.kategorijeComboBox.setDisable(true);
				ctl.kljucnarijecTextField.setDisable(true);
				ctl.knjiznicariTableView.setDisable(true);
				ctl.adminCheckBox.setDisable(true);
			} else {
				ctl.adminLogged = true;
				ctl.korisnickoImeLabel.setText("");
				ctl.knjiznicariTableView.setItems(obsListaKnjiznicara);
			}
			BorderPane root = (BorderPane) loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setAlwaysOnTop(true);
			stage.getIcons().add(new Image("ikona.png"));
			stage.setTitle("Promjena podataka");
			stage.setScene(scene);
			stage.show();
			stage.setOnCloseRequest(event -> {
				if(ctl.promijenjenaLozinka) {
					trenutniKnjiznicar.setLozinka(ctl.novaLozinka);
				}
			});
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void izadiIzAplikacije() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(null);
		alert.setHeaderText("Jeste li sigurni da želite zatvoriti aplikaciju?");
		alert.setContentText(null);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Platform.exit();
		} else {
			// ... user chose CANCEL or closed the dialog
		}
	}

	public void otvoriKorisnike() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				zatvoriSideMeni();
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						transition.setRate(transition.getRate() * -1);
						transition.play();

					}
				});

			}
		});
		thread.start();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("KorisniciProzor.fxml"));
		try {
			loader.load();
			KorisniciProzorController ctl = (KorisniciProzorController) loader.getController();
			ctl.knjiznicar = new Knjiznicar(trenutniKnjiznicar.getIme(), trenutniKnjiznicar.getPrezime(),
					trenutniKnjiznicar.getMailAdresa(), trenutniKnjiznicar.getBrojMobitela(),
					trenutniKnjiznicar.getKorisnickoIme(), trenutniKnjiznicar.getLozinka(),
					trenutniKnjiznicar.getIsAdmin(), trenutniKnjiznicar.getIsAktivan());
			ctl.knjiznicar.setID(trenutniKnjiznicar.getID());
			Pane root = (Pane) loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setMaximized(true);
			stage.setResizable(false);
			stage.initStyle(StageStyle.UTILITY);
			stage.setAlwaysOnTop(true);
			stage.setScene(scene);
			stage.getIcons().add(new Image("ikona.png"));
			stage.setTitle("Pregled korisnika");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void otvoriKnjige() throws SQLException {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				zatvoriSideMeni();
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						transition.setRate(transition.getRate() * -1);
						transition.play();

					}
				});

			}
		});
		thread.start();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Knjige.fxml"));
		try {
			loader.load();
			KnjigeProzorController ctl = (KnjigeProzorController) loader.getController();
			obsListaKnjiga = FXCollections.observableArrayList(BazaPodataka.dohvatiKnjige());
			ctl.sveKnjigeTableView.setItems(obsListaKnjiga);
			Pane root = (Pane) loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setMaximized(true);
			stage.initStyle(StageStyle.UTILITY);
			stage.setAlwaysOnTop(true);
			stage.setScene(scene);
			stage.getIcons().add(new Image("ikona.png"));
			stage.setTitle("Pregled knjiga");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void dodajKnjige() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				zatvoriSideMeni();
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						transition.setRate(transition.getRate() * -1);
						transition.play();

					}
				});

			}
		});
		thread.start();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("DodajKnjiguProzor.fxml"));
		try {
			loader.load();
			@SuppressWarnings("unused")
			DodajKnjiguProzorController ctl = (DodajKnjiguProzorController) loader.getController();
			Pane root = (Pane) loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setAlwaysOnTop(true);
			stage.setScene(scene);
			stage.getIcons().add(new Image("ikona.png"));
			stage.setTitle("Nova knjiga");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void dodajKorisnika() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				zatvoriSideMeni();
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						transition.setRate(transition.getRate() * -1);
						transition.play();

					}
				});

			}
		});
		thread.start();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("DodajKorisnikaProzor.fxml"));
		try {
			loader.load();
			@SuppressWarnings("unused")
			DodajKorisnikaProzorController ctl = (DodajKorisnikaProzorController) loader.getController();
			Pane root = (Pane) loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setAlwaysOnTop(true);
			stage.setScene(scene);
			stage.getIcons().add(new Image("ikona.png"));
			stage.setTitle("Novi korisnik");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void dodajKnjiznicara() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				zatvoriSideMeni();
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						transition.setRate(transition.getRate() * -1);
						transition.play();

					}
				});

			}
		});
		thread.start();
		if (trenutniKnjiznicar.getIsAdmin()) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("DodajKnjiznicaraProzor.fxml"));
			try {
				loader.load();
				@SuppressWarnings("unused")
				DodajKnjiznicaraProzorController ctl = (DodajKnjiznicaraProzorController) loader.getController();
				Pane root = (Pane) loader.getRoot();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.setScene(scene);
				stage.getIcons().add(new Image("ikona.png"));
				stage.setTitle("Novi knjižničar");
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Upozorenje!");
			alert.setHeaderText("Nedopuštena radnja");
			alert.setContentText("Prijavite se kao administrator kako biste dodali novog zaposlenika");

			alert.showAndWait();
		}
	}

	public void otvoriLog() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				zatvoriSideMeni();
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						transition.setRate(transition.getRate() * -1);
						transition.play();

					}
				});

			}
		});
		thread.start();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("LogProzor.fxml"));
		try {
			loader.load();
			JFXTabPane root = (JFXTabPane) loader.getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setMaximized(true);
			stage.initStyle(StageStyle.UTILITY);
			stage.setAlwaysOnTop(true);
			stage.setScene(scene);
			stage.getIcons().add(new Image("ikona.png"));
			stage.setTitle("Pregled knjiga");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
