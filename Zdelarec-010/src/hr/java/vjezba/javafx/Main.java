package hr.java.vjezba.javafx;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
import hr.java.vjezbe.niti.SenzoriNit;
import hr.java.vjezbe.niti.ZadnjaZupnijaNit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	public static List<String> listaStringova = new ArrayList<>();
	public static List<Drzava> listaDrzava = new ArrayList<>();
	public static List<Zupanija> listaZupanija = new ArrayList<>();
	public static List<Mjesto> listaMjesta = new ArrayList<>();
	public static Map<Integer, GeografskaTocka> koordinateIzDatoteke = new HashMap<>();
	public static List<GeografskaTocka> listakoordinata = new ArrayList<>();
	public static List<MjernaPostaja> listaPostaja = new ArrayList<>();
	public static List<Senzor> listaSenzora = new ArrayList<>();
	public static List<SenzorTemperature> listaTemp = new ArrayList<>();
	public static List<SenzorVlage> listaVlage = new ArrayList<>();
	public static List<SenzorTlakaZraka> listaTlaka = new ArrayList<>();
	public static Map<Integer, Drzava> drzaveIzDatoteke = new HashMap<>();
	public static Map<Integer, Zupanija> zupanijeIzDatoteke = new HashMap<>();
	public static Map<Integer, Mjesto> mjestaIzDatoteke = new HashMap<>();
	public static Map<Integer, MjernaPostaja> postajeIzDatoteke = new HashMap<>();
	public static Map<Integer, SenzorTemperature> mapaTemp = new HashMap<>();
	public static Map<Integer, SenzorVlage> mapaVlage = new HashMap<>();
	public static Map<Integer, SenzorTlakaZraka> mapaTlaka = new HashMap<>();
	public static final Integer BROJ_POSTAJA = 3;
	public static final String FILE_NAME = "dat/output.txt";
	private static BorderPane root;
	private Stage primaryStage;
	public ScheduledExecutorService scheduler;

	@Override
	public void start(Stage stage) {
		primaryStage = stage;
		try {
			root = (BorderPane) FXMLLoader.load(getClass().getResource("PocetniEkran.fxml"));
			stage.getIcons().add(new Image("file:sun.png"));
			stage.setTitle("Meteo Base");
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		scheduler = Executors.newScheduledThreadPool(2);
		SenzoriNit senzoriNit = new SenzoriNit();
		PocetniEkranController zadnjaZupanijaNit = new PocetniEkranController();
		//ZupanijaController zadnjaZupanijaNit = new ZupanijaController();
		//scheduler.scheduleAtFixedRate(senzoriNit, 0, 10, TimeUnit.SECONDS);
		//scheduler.scheduleAtFixedRate(zadnjaZupanijaNit, 0, 1, TimeUnit.SECONDS);
		ZupanijaController ucitajZupanije = new ZupanijaController();
		scheduler.execute(ucitajZupanije);
		DodajZupanijeController dodajZupaniju = new DodajZupanijeController();
		scheduler.execute(dodajZupaniju);
		
		
		
	}
	
	@Override
	public void stop(){
	    scheduler.shutdown();
	    PocetniEkranController.scheduler.shutdown();
	    // Save file
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
	 * 
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
				mapaTemp.put(id, new SenzorTemperature("°C", (byte) 2, komponenta));
				break;
			}
			case 3: {
				enumeracija = red;
				for (RadSenzora rad : RadSenzora.values()) {
					if (enumeracija.equals(rad.name())) {
						radSenzora = rad;
					}
				}
				mapaTemp.get(id).setRadSenzora(radSenzora);
				mapaTemp.get(id).setVrijednost(vrijednost);
				listaTemp.add(mapaTemp.get(id));

			}
				break;
			}
		}
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
				mapaVlage.put(id, new SenzorVlage("%", (byte) 2));
				mapaVlage.get(id).setVrijednost(vrijednost);
				break;
			}
			case 2: {
				enumeracija = red;
				for (RadSenzora rad : RadSenzora.values()) {
					if (enumeracija.equals(rad.name())) {
						radSenzora = rad;
					}
				}
				mapaVlage.get(id).setRadSenzora(radSenzora);
				mapaVlage.get(id).setVrijednost(vrijednost);
				listaVlage.add(mapaVlage.get(id));
			}
				break;
			}
		}

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
				mapaTlaka.put(id, new SenzorTlakaZraka("hPa", (byte) 2, opis));
				mapaTlaka.get(id).setVrijednost(vrijednost);
				break;
			}
			case 3: {
				enumeracija = red;
				for (RadSenzora rad : RadSenzora.values()) {
					if (enumeracija.equals(rad.name())) {
						radSenzora = rad;
					}
				}
				mapaTlaka.get(id).setRadSenzora(radSenzora);
				mapaTlaka.get(id).setVrijednost(vrijednost);
				listaTlaka.add(mapaTlaka.get(id));

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
		// List<MjernaPostaja> listaPostaja = new ArrayList<>();
		List<Senzor> senzoraZaPostaju = new ArrayList<>();
		try (Stream<String> stream = Files.lines(new File("dat/mjernePostaje.txt").toPath())) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Integer id = null;
		Integer idTemp = null;
		Integer idTlak = null;
		Integer idVlaga = null;
		Integer idMjesta = null;
		String naziv = null;
		int brojRedovaZaPostaju = 6;
		for (int i = 0; i < listaStringova.size(); i++) {
			String red = listaStringova.get(i);
			switch (i % brojRedovaZaPostaju) {
			case 0:
				id = Integer.parseInt(red);
				break;
			case 1: {
				naziv = red;
			}
				break;
			case 2: {
				idMjesta = Integer.parseInt(red);
			}
				break;
			case 3: {
				idTemp = Integer.parseInt(red);
				senzoraZaPostaju.add(mapaTemp.get(idTemp));
			}
			case 4: {
				idTlak = Integer.parseInt(red);
				senzoraZaPostaju.add(mapaTlaka.get(idTlak));
			}
			case 5: {
				idVlaga = Integer.parseInt(red);
				senzoraZaPostaju.add(mapaVlage.get(idVlaga));
				MjernaPostaja postaja = new MjernaPostaja(naziv, mjestaIzDatoteke.get(idMjesta),
						koordinateIzDatoteke.get(id), senzoraZaPostaju, id);
				postajeIzDatoteke.put(id, postaja);

			}
				break;

			}
		}
		List<MjernaPostaja> listaPostaja = new ArrayList<>(postajeIzDatoteke.values());
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
		int brojRedovaZaMjesto = 4;
		Integer id = null;
		Integer idZupanije = null;
		String naziv = null;
		String enumeracija = null;
		VrstaMjesta vrstaMjesta = null;
		for (int i = 0; i < listaStringova.size(); i++) {
			String red = listaStringova.get(i);
			switch (i % brojRedovaZaMjesto) {
			case 0:
				id = Integer.parseInt(red);
				break;
			case 1: {
				naziv = red;
			}
				break;
			case 3: {
				enumeracija = red;
				for (VrstaMjesta mjesta : VrstaMjesta.values()) {
					if (enumeracija.equals(mjesta.name())) {
						vrstaMjesta = mjesta;
					}
				}
				listaMjesta.get(id - 1).setVrstaMjest(vrstaMjesta);
				mjestaIzDatoteke.get(id).setVrstaMjest(vrstaMjesta);

			}
				break;

			case 2: {
				idZupanije = Integer.parseInt(red);
				mjestaIzDatoteke.put(id, new Mjesto(naziv, zupanijeIzDatoteke.get(idZupanije), id));
				listaMjesta.add(new Mjesto(naziv, listaZupanija.get(idZupanije - 1), id));

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
		Integer idDrzave = null;
		String naziv = null;
		;

		int brojRedovaZaZupaniju = 3;
		for (int i = 0; i < listaStringova.size(); i++) {
			String red = listaStringova.get(i);
			switch (i % brojRedovaZaZupaniju) {
			case 0:
				id = Integer.parseInt(red);
				break;
			case 1: {
				naziv = red;
			}
				break;
			case 2: {
				idDrzave = Integer.parseInt(red);
				listaZupanija.add(new Zupanija(naziv, listaDrzava.get(idDrzave - 1), id));
				zupanijeIzDatoteke.put(id, new Zupanija(naziv, drzaveIzDatoteke.get(idDrzave), id));
			}
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
		try (Stream<String> stream = Files.lines(new File("dat/drzav.txt").toPath())) {
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
				Drzava tmp = new Drzava(naziv, povrsina, id);
				if (!listaDrzava.contains(tmp)) {
					listaDrzava.add(tmp);
					drzaveIzDatoteke.put(id, tmp);
				}
			}
				break;
			}
		}
		return listaDrzava;
	}
}
