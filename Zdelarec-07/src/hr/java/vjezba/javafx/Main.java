package hr.java.vjezba.javafx;
	
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorTlakaZraka;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	public static List<String> listaStringova = new ArrayList<>();
	public static List< Drzava> listaDrzava = new ArrayList<>();
	public static List< Zupanija> listaZupanija = new ArrayList<>();
	public static List< Mjesto> listaMjesta = new ArrayList<>();
	public static Map<Integer, GeografskaTocka> koordinateIzDatoteke = new HashMap<>();
	public static List <GeografskaTocka> listakoordinata = new ArrayList<>();
	public static List< MjernaPostaja> listaPostaja = new ArrayList<>();
	public static List< Senzor> listaSenzora = new ArrayList<>();
	public static Map<Integer, List<Senzor>> senzoriIzDatoteke = new HashMap<>();
	public static List<Senzor> prvaLista = new ArrayList<>();
	public static List<Senzor> drugaLista = new ArrayList<>();
	public static List<Senzor> trecaLista = new ArrayList<>();
	public static final Integer BROJ_POSTAJA = 3;
	public static final String FILE_NAME = "dat/output.txt";
	private static BorderPane root;
	private Stage primaryStage;
	
	@Override
	public void start(Stage stage) {
		primaryStage = stage;
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("PocetniEkran.fxml"));
			stage.getIcons().add(new Image("file:sun.png"));
	        stage.setTitle("Meteo Base");
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setCenterPane(BorderPane centerPane) {
		root.setCenter(centerPane);
		}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Ucitava podatke o senzorima iz tri razlicite datoteke. Senzore stavlja u
	 * zasebne liste, ovisno o njihovom ID-ju i vrsti senzor. Na kraju se radi mapa
	 * u kojoj je kljuc ID, a vrijednost je lista senzora.
	 * @return 
	 * 
	 */
	public static List<Senzor> dohvatiSenzore() {
		
		listaSenzora.clear();
		try (Stream<String> stream = Files.lines(new File("dat/temperatura.txt").toPath())) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Integer id = null;
		String komponenta = null;
		BigDecimal vrijednost = null;
		String opis = null;
		String enumeracija = null;
		RadSenzora radSenzora = null;
		int counter1 = 0;
		int counter2 = 0;
		int counter3 = 0;

		// temperatura
		int brojRedovaZaTemp = 4;
		for (int i = 0; i < listaStringova.size(); i++) {
			String red = listaStringova.get(i);
			switch (i % brojRedovaZaTemp) {
			case 0:
				id = Integer.parseInt(red);
				break;
			case 1:
				komponenta = red;
				break;
			case 2: {
				vrijednost = new BigDecimal(red);
				listaSenzora.add(new SenzorTemperature("°C", (byte) 2, komponenta));
				if (id == 1) {
					prvaLista.add(new SenzorTemperature("°C", (byte) 2, komponenta));
					prvaLista.get(counter1).setVrijednost(vrijednost);
				}
				if (id == 2) {
					drugaLista.add(new SenzorTemperature("°C", (byte) 2, komponenta));
					drugaLista.get(counter2).setVrijednost(vrijednost);
				}
				if (id == 3) {
					trecaLista.add(new SenzorTemperature("°C", (byte) 2, komponenta));
					trecaLista.get(counter3).setVrijednost(vrijednost);
				}
				break;
			}
			case 3: {
				enumeracija = red;
				for (RadSenzora rad : RadSenzora.values()) {
					if (enumeracija.equals(rad.name())) {
						radSenzora = rad;
					}
				}
				if (id == 1) {
					prvaLista.get(counter1).setRadSenzora(radSenzora);
				}
				if (id == 2) {
					drugaLista.get(counter2).setRadSenzora(radSenzora);
				}
				if (id == 3) {
					trecaLista.get(counter3).setRadSenzora(radSenzora);
				}

			}
				break;
			}
		}
		counter1++;
		counter2++;
		counter3++;
		// System.out.println(listaStringova);

		// vlaga
		id = null;
		komponenta = null;
		vrijednost = null;
		opis = null;
		enumeracija = null;
		radSenzora = null;
		try (Stream<String> stream = Files.lines(new File("dat/vlaga.txt").toPath())) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		int brojRedovaZaVlagu = 3;
		for (int i = 0; i < listaStringova.size(); i++) {
			String red = listaStringova.get(i);
			switch (i % brojRedovaZaVlagu) {
			case 0:
				id = Integer.parseInt(red);
				break;
			case 1: {
				listaSenzora.add(new SenzorVlage("%", (byte) 2));
				vrijednost = new BigDecimal(red);
				if (id == 1) {
					prvaLista.add(new SenzorVlage("%", (byte) 2));
					prvaLista.get(counter1).setVrijednost(vrijednost);
				}
				if (id == 2) {
					drugaLista.add(new SenzorVlage("%", (byte) 2));
					drugaLista.get(counter2).setVrijednost(vrijednost);
				}
				if (id == 3) {
					trecaLista.add(new SenzorVlage("%", (byte) 2));
					trecaLista.get(counter3).setVrijednost(vrijednost);
				}
				break;
			}
			case 2: {
				enumeracija = red;
				for (RadSenzora rad : RadSenzora.values()) {
					if (enumeracija.equals(rad.name())) {
						radSenzora = rad;
					}
				}
				if (id == 1) {
					prvaLista.get(counter1).setRadSenzora(radSenzora);
				}
				if (id == 2) {
					drugaLista.get(counter2).setRadSenzora(radSenzora);
				}
				if (id == 3) {
					trecaLista.get(counter3).setRadSenzora(radSenzora);
				}

			}
				break;
			}
		}
		counter1++;
		counter2++;
		counter3++;

		// System.out.println(listaStringova);

		try (Stream<String> stream = Files.lines(new File("dat/tlak.txt").toPath())) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// tlak
		id = null;
		komponenta = null;
		vrijednost = null;
		opis = null;
		enumeracija = null;
		radSenzora = null;
		int brojRedovaZaTlak = 4;
		for (int i = 0; i < listaStringova.size(); i++) {
			String red = listaStringova.get(i);
			switch (i % brojRedovaZaTlak) {
			case 0:
				id = Integer.parseInt(red);
				break;
			case 1:
				opis = red;
				break;
			case 2: {
				vrijednost = new BigDecimal(red);
				listaSenzora.add(new SenzorTlakaZraka("hPa", (byte) 2, opis));
				if (id == 1) {
					prvaLista.add(new SenzorTlakaZraka("hPa", (byte) 2, opis));
					prvaLista.get(counter1).setVrijednost(vrijednost);
				}
				if (id == 2) {
					drugaLista.add(new SenzorTlakaZraka("hPa", (byte) 2, opis));
					drugaLista.get(counter2).setVrijednost(vrijednost);
				}
				if (id == 3) {
					trecaLista.add(new SenzorTlakaZraka("hPa", (byte) 2, opis));
					trecaLista.get(counter3).setVrijednost(vrijednost);
				}
				break;
			}
			case 3: {
				enumeracija = red;
				for (RadSenzora rad : RadSenzora.values()) {
					if (enumeracija.equals(rad.name())) {
						radSenzora = rad;
					}
				}
				if (id == 1) {
					prvaLista.get(counter1).setRadSenzora(radSenzora);
					senzoriIzDatoteke.put(id, prvaLista);
				}
				if (id == 2) {
					drugaLista.get(counter2).setRadSenzora(radSenzora);
					senzoriIzDatoteke.put(id, drugaLista);
				}
				if (id == 3) {
					trecaLista.get(counter3).setRadSenzora(radSenzora);
					senzoriIzDatoteke.put(id, trecaLista);
				}

			}
				break;
			}
		}
		return listaSenzora;
	}

	/**
	 * Ucitava podatke o postajama iz dateteke te objekte stavlja u mapu.
	 * 
	 */
	public static List<MjernaPostaja> dohvatiPostaje() {
		listaPostaja.clear();
		List<MjernaPostaja> listaPostaja = new ArrayList<>();
		try (Stream<String> stream = Files.lines(new File("dat/mjernePostaje.txt").toPath())) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Integer id = null;
		String naziv = null;
		int brojRedovaZaPostaju = 2;
		for (int i = 0; i < listaStringova.size(); i++) {
			String red = listaStringova.get(i);
			switch (i % brojRedovaZaPostaju) {
			case 0:
				id = Integer.parseInt(red);
				break;
			case 1: {
				naziv = red;
				listaPostaja.add(new MjernaPostaja(naziv, listaMjesta.get(id-1),
						koordinateIzDatoteke.get(id), senzoriIzDatoteke.get(id), id));
				break;
			}
			}
		}

		return listaPostaja;
	}

	/**
	 * Ucitava podatke o koordinatama iz dateteke te objekte stavlja u mapu.
	 */
	public static List<GeografskaTocka> dohvatiKoordinate() {
		listakoordinata.clear();
		try (Stream<String> stream = Files.lines(new File("dat/geo.txt").toPath())) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Integer id = null;
		BigDecimal xKoordinata = null;
		BigDecimal yKoordinata = null;

		int brojRedovaZaKoordinate = 3;
		for (int i = 0; i < listaStringova.size(); i++) {
			String red = listaStringova.get(i);
			switch (i % brojRedovaZaKoordinate) {
			case 0:
				id = Integer.parseInt(red);
				break;
			case 1:
				xKoordinata = new BigDecimal(red);
				break;
			case 2: {
				yKoordinata = new BigDecimal(red);
				koordinateIzDatoteke.put(id, new GeografskaTocka(xKoordinata, yKoordinata, id));
				listakoordinata.add(new GeografskaTocka(xKoordinata, yKoordinata, id));
			}
				break;
			}
		}
		return listakoordinata;
	}

	/**
	 * Ucitava podatke o mjestima iz dateteke te objekte stavlja u mapu.
	 */
	public static List<Mjesto> dohvatiMjesta() {
		listaMjesta.clear();
		try (Stream<String> stream = Files.lines(new File("dat/mjesto.txt").toPath())) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		int brojRedovaZaMjesto = 3;
		Integer id = null;
		String naziv = null;
		String enumeracija = null;
		VrstaMjesta vrstaMjesta = null;
		for (int i = 0; i < listaStringova.size(); i++) {
			String red = listaStringova.get(i);
			switch (i % brojRedovaZaMjesto) {
			case 0:
				id = Integer.parseInt(red);
				break;
			case 1:
				naziv = red;
				listaMjesta.add(new Mjesto(naziv, listaZupanija.get(id-1), id));
				break;
			case 2: {
				enumeracija = red;
				for (VrstaMjesta mjesta : VrstaMjesta.values()) {
					if (enumeracija.equals(mjesta.name())) {
						vrstaMjesta = mjesta;
					}
				}
				listaMjesta.get(id-1).setVrstaMjest(vrstaMjesta);
				

			}
				break;
			}
		}
		return listaMjesta;
	}

	/**
	 * Ucitava podatke o zupanijama iz dateteke te objekte stavlja u mapu.
	 * 
	 */
	public static List<Zupanija> dohvatiZupanije() {
		listaZupanija.clear();
		try (Stream<String> stream = Files.lines(new File("dat/zupanije.txt").toPath())) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Integer id = null;
		String naziv;

		int brojRedovaZaZupaniju = 2;
		for (int i = 0; i < listaStringova.size(); i++) {
			String red = listaStringova.get(i);
			switch (i % brojRedovaZaZupaniju) {
			case 0:
				id = Integer.parseInt(red);
				break;
			case 1:
				naziv = red;
				listaZupanija.add(new Zupanija(naziv, listaDrzava.get(id-1), id));
				break;

			}
		}
		return listaZupanija;
	}

	/**
	 * Ucitava podatke o drzavama iz dateteke te objekte stavlja u mapu.
	 * 
	 */
	public static List<Drzava> dohvatiDrzave() {
		listaDrzava.clear();
		try (Stream<String> stream = Files.lines(new File("dat/drzave.txt").toPath())) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		int brojRedovaZaDrzavu = 3;
		Integer id = null;
		String naziv = null;
		BigDecimal povrsina = null;
		for (int i = 0; i < listaStringova.size(); i++) {
			String red = listaStringova.get(i);
			switch (i % brojRedovaZaDrzavu) {
			case 0:
				id = Integer.parseInt(red);
				break;
			case 1:
				naziv = red;
				break;
			case 2: {
				povrsina = new BigDecimal(red);
				Drzava tmp =new Drzava(naziv, povrsina, id);
				if(!listaDrzava.contains(tmp)) {
					listaDrzava.add(tmp);
				}
			}
				break;
			}
		}
		return listaDrzava;
	}
}


