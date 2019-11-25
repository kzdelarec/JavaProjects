package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Kategorija;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;

/**
 * Predstavlja glavnu klasu iz koje se izvodi program.
 * 
 * @author Patricija Ku�e
 *
 */
public class Glavna {

	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	/**
	 * Pokrece izvodenje programa.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// unos velicine polja Korisnik
		Scanner unos = new Scanner(System.in);
		System.out.println("Unesite broj korisnika koji �elite unijeti:");
		int brojKorisnika = unosInt(unos);

		List<Korisnik> listaKorisnika = new ArrayList<>();

		// unos tipa korisnika (privatni/poslovni) te njegovi osobni podatci
		unosKorisnika(unos, brojKorisnika, listaKorisnika);

		// unos velicine polja Kategorija
		System.out.println("Unesite broj kategorija koji �elite unijeti: ");
		int brojKategorija = unosInt(unos);

		List<Kategorija<Artikl>> listaKategorija = new ArrayList<>();

		// unos naziva kategorije i unos artikla
		unosKategorije(unos, brojKategorija, listaKategorija);

		// unos velicine polja Prodaja
		System.out.println("Unesite broj artikala koji su aktivno na prodaju: ");
		int aktivniArtikli = unosInt(unos);

		List<Prodaja> prodaniArtikli = new ArrayList<>();

		// Odabir kategorije, korisnika i artikla te spremanje u polje prodani artikli
		unosProdaje(unos, brojKorisnika, listaKorisnika, brojKategorija, listaKategorija, aktivniArtikli,
				prodaniArtikli);

		/*
		 * Ispisuje podatke trazene od korisnika. Ispisuju se oglasi koji su trenutno na
		 * prodaju. For poetlja ispisuje naslov, opis i cijenu artikla te datum objave i
		 * podatke o prodavatelju
		 */
		ispis(prodaniArtikli);
	}

	/**
	 * Provjerava je li unesen cijeli broj.
	 * 
	 * @param unos
	 *            - podatak koji je unio korisnik
	 * @return vraca uneseni broj ako je ispravan
	 */
	private static int unosInt(Scanner unos) {
		int broj = 0;

		boolean nastaviPetlju = false;
		do {
			try {

				broj = unos.nextInt();
				unos.nextLine();
				nastaviPetlju = false;
			} catch (Exception ex) {
				System.out.println("Morate unijeti cijelobrojne vrijednosti.");
				logger.error("Nije cijelobrojna vrijednost", ex);
				unos.nextLine();
				nastaviPetlju = true;
			}
		} while (nastaviPetlju);
		return broj;
	}

	/**
	 * Provjerava je li unesen decimalan broj.
	 * 
	 * @param unos
	 *            - podatak koji je unio korisnik
	 * @return vraca uneseni broj ako je ispravan
	 */
	private static BigDecimal unosBigDecimal(Scanner unos) {
		BigDecimal broj = null;
		boolean nastaviPetlju = false;
		do {
			try {
				broj = unos.nextBigDecimal();
				unos.nextLine();
				nastaviPetlju = false;
			} catch (Exception ex) {
				System.out.println("Morate unijeti decimalan broj.");
				logger.error("Nije decimalna vrijednost", ex);
				unos.nextLine();
				nastaviPetlju = true;
			}
		} while (nastaviPetlju);
		return broj;
	}

	/**
	 * Ispisuje podatke trazene od korisnika. Ispisuju se oglasi koji su trenutno na
	 * prodaju. For poetlja ispisuje naslov, opis i cijenu artikla te datum objave i
	 * podatke o prodavatelju
	 * 
	 * @param prodaja[]
	 *            prodani artikli - vraca polje objekata
	 * 
	 */
	private static void ispis(List<Prodaja> prodaniArtikli) {
		System.out.println("Trenutno su oglasi na prodaju:");
		for (int i = 0; i < (prodaniArtikli.size()); i++) {
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println(prodaniArtikli.get(i).getArtikl().tekstOglasa() + "\nDatum objave: "
					+ prodaniArtikli.get(i).getDatumObjave().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "\n"
					+ prodaniArtikli.get(i).getKorisnik().dohvatiKontakt());
		}
		System.out.println("-----------------------------------------------------------------------------");
		
	}

	/**
	 * Trazi korisnika odabir korisnika, kategorije i artikla. U prvoj for petlji se
	 * ispisuju podatci za svaki aktivni artikl, a u drugoj se ispisuje svaki
	 * uneseni korisnik
	 * 
	 * @param unos
	 *            - trazeni unos od korisnika
	 * @param brojKorisnika
	 *            - koliko korisnika treba ispisati
	 * @param poljeKorisnika
	 *            - spremamo polje objekata
	 * @param brojKategorija
	 *            - podatak trazen od korisnika o tome koliko kategorija unijeti
	 * @param poljeKategorija
	 *            - polje objekata u kojem su spremljene kategorije
	 * @param aktivniArtikli
	 *            - trazilu smo od korisnika da unese koliko artikala zeli da se
	 *            ispise param prodaniArtikli - spremanje artikla, korisnika i
	 *            datuma u polje objekata
	 */
	private static void unosProdaje(Scanner unos, int brojKorisnika, List<Korisnik> listaKorisnika, int brojKategorija,
			List<Kategorija<Artikl>> listaKategorija, int aktivniArtikli, List<Prodaja> prodaniArtikli) {
		System.out.println("Odaberite korisnika: ");
		for (int k = 0; k < aktivniArtikli; k++) {
			for (int z = 0; z < brojKorisnika; z++) {
				System.out.println((z + 1) + ". " + listaKorisnika.get(z).dohvatiKontakt());
			}

			System.out.println("Odabir >>");
			int odabraniKorisnik = odabirKorisnika(unos);

			Korisnik korisnik = listaKorisnika.get(odabraniKorisnik - 1);

			System.out.println("Odaberite kategoriju: ");
			for (int z = 0; z < brojKategorija; z++) {
				System.out.println((z + 1) + ". " + listaKategorija.get(z).getNaziv());
			}

			System.out.println("Odabir >>");
			int odabranaKategorija = odaberiteKategoriju(unos);
			Kategorija<Artikl> kategorija = listaKategorija.get(odabranaKategorija - 1);

			System.out.println("Odaberite artikl: ");
			
			Function<Artikl, String> poNaslovu = Artikl::getNaslov;
			Function<Artikl, String> poOpisu = Artikl::getOpis;
			Comparator<Artikl> poNaslovuIOpisu = Comparator.comparing(poNaslovu).thenComparing(poOpisu);
			ArrayList<Artikl> artikliSorted = new ArrayList<>(listaKategorija.get(odabranaKategorija - 1).getArtikli());
			artikliSorted.stream().sorted(Comparator.comparing(Artikl::getNaslov));
			
			for (int z = 0; z < artikliSorted.size(); z++) {
				System.out.println((z + 1) + ". " + artikliSorted.get(z).getNaslov());
			}
			System.out.println("Odabir >>");
			Integer odabraniArtikl = odaberiteArtikl(unos);
			Artikl artikl = artikliSorted.get(odabraniArtikl - 1);

			//prodaniArtikli.add(new Prodaja(artikl, korisnik, LocalDate.now()));
		}
	}

	/**
	 * Trazi od korisnika unos kategorije tj. unos naziva kategorije i unos artikla
	 * u polje artikala
	 * 
	 * @param unos
	 *            - vrijednost koju je korisnik unio
	 * @param brojKategorija
	 *            - podatak trazen od korisnika o tome koliko kategorija unijeti
	 * @param poljeKategorija
	 *            - polje objekata u kojem su spremljene kategorije
	 * 
	 */
	private static void unosKategorije(Scanner unos, int brojKategorija, List<Kategorija<Artikl>> listaKategorija) {
		for (int i = 0; i < brojKategorija; i++) {

			// trazi korisnika unos naziva kategorije u posebnoj metodi
			String nazivKategorija = unosNazivaKategorije(unos, i);

			//Kategorija<Artikl> kategorija = new Kategorija<>(nazivKategorija);
			//unosArtikla(unos, kategorija);
			
			// uneseni podatci se spremaju u polje kategorija koje sadrzi nazivKategorija i
			// poljeArtikala
			//listaKategorija.add(kategorija);
		}
	}

	/**
	 * Prvo korisnik odabir tip artikla (Usluga/Automobil), a zatim se traze podatci
	 * o nazivu, opisu i cijeni trazene varijable koji se spremaju u polje objekata
	 * pod nazuvim poljeArtikala
	 * 
	 * @param unos
	 *            - trazeni unos vrijednosti od korisnika
	 */
	private static void unosArtikla(Scanner unos, Kategorija<Artikl> kategorija) {
		System.out.println("Unesite broj artikala koji �elite unijeti za unesenu kategoriju: ");
		int brojArtikala = unosInt(unos);

		for (int j = 0; j < brojArtikala; j++) {
			System.out.println("Unesite tip " + (j + 1) + ". artikla: " + "\n1. Usluga" + "\n2. Automobil" + "\n3. Stan"
					+ "\nOdabir >>");

			int tipArtikla = unosInt(unos);
			// ovaj dio koda se izvrsava ako je korisnik odabrao broj 1 - Usluga
			if (tipArtikla == 1) {
				String naslovArtikla = unosNaslovaArtikla(unos, j);
				String opisArtikla = unosOpisaArtikla(unos, j);
				BigDecimal cijenaArtikla = unosCijeneArtikla(unos, j);

				Stanje stanjeArtikla = unosStanja(unos);

				//kategorija.dodajArtikl(new Usluga(naslovArtikla, opisArtikla, cijenaArtikla, stanjeArtikla));
			}
			// izvrsava se ovaj kod ako je korisnik unio broj 2 - Automobil
			if (tipArtikla == 2) {
				System.out.println("Unesite naslov " + (j + 1) + ". oglasa automobila: ");
				String naslovAutomobila = unos.nextLine();

				System.out.println("Unesite opis " + (j + 1) + ". oglasa automobila: ");
				String opisAutomobila = unos.nextLine();

				System.out.println("Unesite snagu " + (j + 1) + ". oglasa automobila: ");
				BigDecimal snagaAutobila = unosBigDecimal(unos);

				System.out.println("Unesite cijenu " + (j + 1) + ". oglasa automobila: ");
				BigDecimal cijenaAutomobila = unosBigDecimal(unos);

				Stanje stanjeAutomobila = unosStanja(unos);

				/*kategorija.dodajArtikl(new Automobil(naslovAutomobila, opisAutomobila, snagaAutobila, stanjeAutomobila,
						cijenaAutomobila));*/
				

			}
			// Izvrsava se ovaj dio koda ako je korisnik unio broj - Stan
			if (tipArtikla == 3) {
				System.out.println("Unesite naslov " + (j + 1) + ". oglasa stana: ");
				String naslovStana = unos.nextLine();

				System.out.println("Unesite opis " + (j + 1) + ". oglasa stana: ");
				String opisStana = unos.nextLine();

				System.out.println("Unesite kvadraturu " + (j + 1) + ". oglasa stana: ");
				int kvadraturaStana = unosInt(unos);

				System.out.println("Unesite cijenu " + (j + 1) + ". oglasa stana: ");
				BigDecimal cijenaStana = unosBigDecimal(unos);

				Stanje stanjeStana = unosStanja(unos);

				//kategorija.dodajArtikl(new Stan(naslovStana, opisStana, cijenaStana, stanjeStana, kvadraturaStana));

			}

		}
	}

	private static Stanje unosStanja(Scanner scanner) {
		for (int i = 0; i < Stanje.values().length; i++) {
			System.out.println((i + 1) + ". " + Stanje.values()[i]);
		}
		Integer stanjeRedniBroj = null;
		while (true) {
			System.out.print("Odabir stanja artikla >> ");
			stanjeRedniBroj = unosInt(scanner);
			if (stanjeRedniBroj >= 1 && stanjeRedniBroj <= Stanje.values().length) {
				return Stanje.values()[stanjeRedniBroj - 1];
			} else {
				System.out.println("Neispravan unos!");
			}
		}
	}

	/**
	 * Upisuje podatke o korisniku ovisno o korisnikovom unosu. Ako je korisnik unio
	 * 1, treba upisati podatke za privatnog korisnika (ime, prezime, email,
	 * telefon), a ako je unio 2 treba upisati podatke za poslovnog korisnika
	 * (nazivTvrtke, emailTvrtke, webTvrtke i telefonTvrtke)
	 * 
	 * @param unos
	 *            - trazeni unos korisnika
	 * @param brojKorisnika
	 *            - podatak o broju korisnika cije podatke treba unijeti
	 * @param poljeKorisnika
	 *            - polje objekata u koje se spremaju uneseni podatci
	 */
	private static void unosKorisnika(Scanner unos, int brojKorisnika, List<Korisnik> listaKorisnika) {
		for (int i = 0; i < brojKorisnika; i++) {
			System.out.println(
					"Unesite tip " + (i + 1) + ". korisnika: " + "\n1. Privatni" + "\n2. Poslovni" + "\nOdabir >>");
			int tipKorisnika = unosTipaKorisnika(unos);
			if (tipKorisnika == 1) {
				String imeKorisnika = unosImeKorisnika(unos, i);
				String prezimeKorisnika = unosPrezimeKorisnika(unos, i);
				String emailKorisnika = unosEmailaKorisnika(unos, i);
				String telefonKorisnika = unosTelefonaKorsnika(unos, i);

				//listaKorisnika.add(new PrivatniKorisnik(imeKorisnika, prezimeKorisnika, emailKorisnika, telefonKorisnika));

			}
			if (tipKorisnika == 2) {
				String nazivTvrtke = unosNazivaTvrtke(unos, i);
				String emailTvrtke = unosEmailatvrtke(unos, i);
				String webTvrtke = unosWebaTvrtke(unos, i);
				String telefonTvrtke = unosTelefonaTvrtke(unos, i);

				//listaKorisnika.add(new PoslovniKorisnik(nazivTvrtke, emailTvrtke, webTvrtke, telefonTvrtke));
			}
		}
	}

	/**
	 * Unos telefona tvrtke ukoliko je odabran poslovni korisnik
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @param i
	 *            - redni broj korisnika koji trenutno unosimo
	 * @return vraca telefon tvrtke
	 */
	private static String unosTelefonaTvrtke(Scanner unos, int i) {
		System.out.println("Unesite telefon " + (i + 1) + ". tvrtke:");
		String telefonTvrtke = unos.nextLine();
		return telefonTvrtke;
	}

	/**
	 * Unos weba tvrtke ukoliko je odabran poslovni korisnik
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @param i
	 *            - redni broj korisnika koji trenutno unosimo
	 * @return vraca web tvrtke
	 */
	private static String unosWebaTvrtke(Scanner unos, int i) {
		System.out.println("Unesite web " + (i + 1) + ". tvrtke:");
		String webTvrtke = unos.nextLine();
		return webTvrtke;
	}

	/**
	 * Unos emaila tvrtke ukoliko je odabran poslovni korisnik
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @param i
	 *            - redni broj korisnika koji trenutno unosimo
	 * @return vraca email tvrtke
	 */
	private static String unosEmailatvrtke(Scanner unos, int i) {
		System.out.println("Unesite e-Mail " + (i + 1) + ". tvrtke:");
		String emailTvrtke = unos.nextLine();
		return emailTvrtke;
	}

	/**
	 * Unos naziva tvrtke ukoliko je odabran poslovni korisnik
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @param i
	 *            - redni broj korisnika koji trenutno unosimo
	 * @return vraca naziv tvrtke
	 */
	private static String unosNazivaTvrtke(Scanner unos, int i) {
		System.out.println("Unesite naziv " + (i + 1) + ". tvrtke:");
		String nazivTvrtke = unos.nextLine();
		return nazivTvrtke;
	}

	/**
	 * Odabir tipa korisnika(privatni/poslovni)
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @return int koji smo unijeli i koji predstavlja tip korisnika
	 */
	private static int unosTipaKorisnika(Scanner unos) {
		int odabirTipa = unosInt(unos);
		return odabirTipa;
	}

	/**
	 * Odabir tipa artikla(Usluga/Automobil)
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @return int koji smo unijeli i koji predstavlja tip korisnika
	 */
	private static int odaberiteArtikl(Scanner unos) {
		int odabirArtikla = unosInt(unos);
		return odabirArtikla;
	}

	/**
	 * Odabir kategorije
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @return int koji smo unijeli i koji predstavlja tip kategorije
	 */
	private static Integer odaberiteKategoriju(Scanner unos) {
		Integer odabirKategorije = unosInt(unos);
		return odabirKategorije;
	}

	/**
	 * Odabir tipa korisnika od unesenih korisnika
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @return int koji smo unijeli i koji predstavlja trazenog korisnika
	 */
	private static Integer odabirKorisnika(Scanner unos) {

		Integer odabirKorisnika = unosInt(unos);
		return odabirKorisnika;
	}

	/**
	 * Unos cijene unesenog artikla
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @param j
	 *            - cijena artikla koji je pod rednim brojem j
	 * @return vraca BigDecimal koji smo unijeli i koji predstavlja cijenu artikla
	 */
	private static BigDecimal unosCijeneArtikla(Scanner unos, int j) {
		System.out.println("Unesite cijenu " + (j + 1) + ". oglasa artikla: ");
		BigDecimal cijenaArtikla = unosBigDecimal(unos);
		return cijenaArtikla;
	}

	/**
	 * Unos opisa unesenog artikla
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @param j
	 *            - opis artikla koji je pod rednim brojem j
	 * @return string koji smo unijeli i koji predstavlja opis artikla
	 */
	private static String unosOpisaArtikla(Scanner unos, int j) {
		System.out.println("Unesite opis " + (j + 1) + ". oglasa artikla: ");
		String opisArtikla = unos.nextLine();
		return opisArtikla;
	}

	/**
	 * Unos naslova unesenog artikla
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @param j
	 *            - naslov artikla koji je pod rednim brojem j
	 * @return string koji smo unijeli i koji predstavlja naslov artikla
	 */
	private static String unosNaslovaArtikla(Scanner unos, int j) {
		System.out.println("Unesite naslov " + (j + 1) + ". oglasa usluge: ");
		String naslovArtikla = unos.nextLine();
		return naslovArtikla;
	}

	/**
	 * Unos naziva kategorije
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @param i
	 *            - redni broj unesenog artikla
	 * @return string koji smo unijeli i koji predstavlja naziv kategorije
	 */
	private static String unosNazivaKategorije(Scanner unos, Integer i) {
		System.out.println("Unesite naziv " + (i + 1) + ". kategorije: ");
		String nazivKategorije = unos.nextLine();
		return nazivKategorije;
	}

	/**
	 * Unos imena korisnika
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @param i
	 *            - ime korisnika pod rednim brojem i
	 * @return string koji smo unijeli i koji predstavlja ime korisnika
	 */
	private static String unosImeKorisnika(Scanner unos, Integer i) {
		System.out.println("Unesite ime " + (i + 1) + ". osobe: ");
		String imeKorisnika = unos.nextLine();
		return imeKorisnika;
	}

	/**
	 * Unos prezimena korisnika
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @param i
	 *            - prezime korisnika pod rednim brojem i
	 * @return string koji smo unijeli i koji predstavlja prezime korisnika
	 */
	private static String unosPrezimeKorisnika(Scanner unos, Integer i) {
		System.out.println("Unesite prezime " + (i + 1) + ". osobe: ");
		String prezimeKorisnika = unos.nextLine();
		return prezimeKorisnika;
	}

	/**
	 * Unos emaila korisnika
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @param i
	 *            - email korisnika pod rednim brojem i
	 * @return string koji smo unijeli i koji predstavlja email korisnika
	 */
	private static String unosEmailaKorisnika(Scanner unos, Integer i) {
		System.out.println("Unesite e-Mail " + (i + 1) + ". osobe: ");
		String emailKorisnika = unos.nextLine();
		return emailKorisnika;
	}

	/**
	 * Unos telefona korisnika
	 * 
	 * @param unos
	 *            - korisnikov unos
	 * @param i
	 *            - telefon korisnika pod rednim brojem i
	 * @return string koji smo unijeli i koji predstavlja telefon korisnika
	 */
	private static String unosTelefonaKorsnika(Scanner unos, Integer i) {
		System.out.println("Unesite telefon " + (i + 1) + ". osobe: ");
		String telefonKorisnika = unos.nextLine();
		return telefonKorisnika;
	}

}
