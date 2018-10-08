package hr.java.vjezbe.glavna;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
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
import hr.java.vjezbe.sortiranje.ZupanijaSorter;

/**
 * Pokrece program koji podatke cita iz datoteke.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class GlavnaDatoteke {

	public GlavnaDatoteke() {
		// TODO Auto-generated constructor stub
	}

	public static List<String> listaStringova = new ArrayList<>();
	public static Map<Integer, Drzava> drzaveIzDatoteke = new HashMap<>();
	public static Map<Integer, Zupanija> zupanijeIzDatoteke = new HashMap<>();
	public static Map<Integer, Mjesto> mjestaIzDatoteke = new HashMap<>();
	public static Map<Integer, GeografskaTocka> koordinateIzDatoteke = new HashMap<>();
	public static Map<Integer, MjernaPostaja> postajeIzDatoteke = new HashMap<>();
	public static Map<Integer, List<Senzor>> senzoriIzDatoteke = new HashMap<>();
	public static List<Senzor> prvaLista = new ArrayList<>();
	public static List<Senzor> drugaLista = new ArrayList<>();
	public static List<Senzor> trecaLista = new ArrayList<>();
	public static final Integer BROJ_POSTAJA = 3;
	public static final String FILE_NAME = "dat/output.txt";

	public static void main(String[] args) throws IOException {

		dohvatiDrzave();
		dohvatiZupanije();
		dohvatiMjesta();
		dohvatiKoordinate();
		dohvatiSenzore();
		dohvatiPostaje();
		ispisPodataka();
		ispisUDatoteku();

	}

	/**
	 * Ipisuje podatke u datoteku.
	 * 
	 */
	public static void ispisUDatoteku() {
		try (PrintWriter out = new PrintWriter(new FileWriter(new File(FILE_NAME)))) {
			for (int i = 1; i < BROJ_POSTAJA + 1; i++) {
				out.println("\n\n--------------------\nNaziv mjerne postaje: " + postajeIzDatoteke.get(i).getNaziv());
				out.println("Postaja se nalazi u mjestu " + postajeIzDatoteke.get(i).getMjesto().getNaziv() + "("
						+ postajeIzDatoteke.get(i).getMjesto().getVrstaMjest() + ")" + ", zupaniji: "
						+ postajeIzDatoteke.get(i).getMjesto().getZupanija().getNaziv() + ", drzavi "
						+ postajeIzDatoteke.get(i).getMjesto().getZupanija().getDrzava().getNaziv()
						+ "\nTocne koordinate postaje su: X:" + postajeIzDatoteke.get(i).getGeografskaTocka().getX()
						+ " Y:" + postajeIzDatoteke.get(i).getGeografskaTocka().getY()
						+ "\nVrijednosti Senzora postaje su:");

				out.println(postajeIzDatoteke.get(i).getSviSenzori().toString().replace("[", "").replace("]", "")
						.replace(", ", "\n") + "\n");
			}
			/*-------------Ispisuje sortirane zupanije-------*/

			out.println("\nIspis sortiranih zupanija:");
			List<Zupanija> listaZupanija = new ArrayList<>();
			List<Zupanija> lista = new ArrayList<>();
			for (int i = 1; i < BROJ_POSTAJA + 1; i++) {
				lista.add(postajeIzDatoteke.get(i).getMjesto().getZupanija());
				lista.forEach(zup -> {
					if (!(listaZupanija.contains(zup))) {
						listaZupanija.add(zup);
					}
				});
			}

			Collections.sort(listaZupanija, new ZupanijaSorter());
			listaZupanija.forEach(sortiraniIspis -> {
				out.println(sortiraniIspis.getNaziv());
			});

			/*--------Senzori po mjestima--------*/
			for (int i = 1; i < BROJ_POSTAJA + 1; i++) {
				out.println("\nU mjestu " + postajeIzDatoteke.get(i).getNaziv() + " su sljedeci senzori:");
				out.println("Senzor vlage\nSenzor tlaka\nSenzor temperature");

			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/**
	 * Ispisuje podatke o mjernim postajama, sortiranje zupanije i podatak o tome
	 * koje senzore ima neko mjesto.
	 * 
	 */
	public static void ispisPodataka() {
		for (int i = 1; i < BROJ_POSTAJA + 1; i++) {
			System.out
					.println("\n\n--------------------\nNaziv mjerne postaje: " + postajeIzDatoteke.get(i).getNaziv());
			System.out.println("Postaja se nalazi u mjestu " + postajeIzDatoteke.get(i).getMjesto().getNaziv() + "("
					+ postajeIzDatoteke.get(i).getMjesto().getVrstaMjest() + ")" + ", zupaniji: "
					+ postajeIzDatoteke.get(i).getMjesto().getZupanija().getNaziv() + ", drzavi "
					+ postajeIzDatoteke.get(i).getMjesto().getZupanija().getDrzava().getNaziv()
					+ "\nTocne koordinate postaje su: X:" + postajeIzDatoteke.get(i).getGeografskaTocka().getX() + " Y:"
					+ postajeIzDatoteke.get(i).getGeografskaTocka().getY() + "\nVrijednosti Senzora postaje su:");

			postajeIzDatoteke.get(i).dohvatiSenzore(postajeIzDatoteke.get(i).getSviSenzori());
		}
		/*-------------Ispisuje sortirane zupanije-------*/

		System.out.println("\nIspis sortiranih zupanija:");
		List<Zupanija> listaZupanija = new ArrayList<>();
		List<Zupanija> lista = new ArrayList<>();
		for (int i = 1; i < BROJ_POSTAJA + 1; i++) {
			lista.add(postajeIzDatoteke.get(i).getMjesto().getZupanija());
			lista.forEach(zup -> {
				if (!(listaZupanija.contains(zup))) {
					listaZupanija.add(zup);
				}
			});
		}

		Collections.sort(listaZupanija, new ZupanijaSorter());
		listaZupanija.forEach(sortiraniIspis -> {
			System.out.println(sortiraniIspis.getNaziv());
		});

		/*--------Senzori po mjestima--------*/
		for (int i = 1; i < BROJ_POSTAJA + 1; i++) {
			System.out.println("\nU mjestu " + postajeIzDatoteke.get(i).getNaziv() + " su sljedeci senzori:");
			System.out.println("Senzor vlage\nSenzor tlaka\nSenzor temperature");

		}
	}

	/**
	 * Ucitava podatke o senzorima iz tri razlicite datoteke. Senzore stavlja u
	 * zasebne liste, ovisno o njihovom ID-ju i vrsti senzor. Na kraju se radi mapa
	 * u kojoj je kljuc ID, a vrijednost je lista senzora.
	 * 
	 */
	public static void dohvatiSenzore() {

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

	}

	/**
	 * Ucitava podatke o postajama iz dateteke te objekte stavlja u mapu.
	 * 
	 */
	public static void dohvatiPostaje() {
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
				postajeIzDatoteke.put(id, new MjernaPostaja(naziv, mjestaIzDatoteke.get(id),
						koordinateIzDatoteke.get(id), senzoriIzDatoteke.get(id), id));
				break;
			}
			}
		}

		// listaStringova.forEach(System.out::println);
	}

	/**
	 * Ucitava podatke o koordinatama iz dateteke te objekte stavlja u mapu.
	 */
	public static void dohvatiKoordinate() {
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
			}
				break;
			}
		}
		// listaStringova.forEach(System.out::println);
	}

	/**
	 * Ucitava podatke o mjestima iz dateteke te objekte stavlja u mapu.
	 */
	public static void dohvatiMjesta() {
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
				mjestaIzDatoteke.put(id, new Mjesto(naziv, zupanijeIzDatoteke.get(id), id));
				break;
			case 2: {
				enumeracija = red;
				for (VrstaMjesta mjesta : VrstaMjesta.values()) {
					if (enumeracija.equals(mjesta.name())) {
						vrstaMjesta = mjesta;
					}
				}
				mjestaIzDatoteke.get(id).setVrstaMjest(vrstaMjesta);

			}
				break;
			}
		}
		// listaStringova.forEach(System.out::println);
	}

	/**
	 * Ucitava podatke o zupanijama iz dateteke te objekte stavlja u mapu.
	 * 
	 */
	public static void dohvatiZupanije() {
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
				zupanijeIzDatoteke.put(id, new Zupanija(naziv, drzaveIzDatoteke.get(id), id));
				break;

			}
		}
		// listaStringova.forEach(System.out::println);
	}

	/**
	 * Ucitava podatke o drzavama iz dateteke te objekte stavlja u mapu.
	 * 
	 */
	public static void dohvatiDrzave() {
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
				drzaveIzDatoteke.put(id, new Drzava(naziv, povrsina, id));
			}
				break;
			}
		}
		// listaStringova.forEach(System.out::println);
	}

}
