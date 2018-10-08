package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.Provjera;
import hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorTlakaZraka;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.Zupanija;
import hr.java.vjezbe.iznimke.DuplaZupanijaException;

/**
 * Predstavlja glavnu klasu iz koje se izvodi program.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class Glavna {

	public static final int BROJ_POSTAJA = 3;
	public static final int BROJ_SENZORA = 3;
	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	/**
	 * Pokrece izvodenje programa.
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws DuplaZupanijaException 
	 */
	public static void main(String[] args) throws InterruptedException, DuplaZupanijaException {
		MjernaPostaja[] novaMjernaPostaja = new MjernaPostaja[BROJ_POSTAJA];
		List<String> sveZupanije = new ArrayList<>();
		List<String> sveDrzave = new ArrayList<>();
		
		Integer visinaRadioSonazneMjernePostaje = 0;
		for (int i = 0; i < BROJ_POSTAJA; i++) {

			Scanner unos = new Scanner(System.in);

			System.out.println("Unesite podatke za " + (i + 1) + ". mjernu postaju");

			// Unos podataka korisnickih podataka
			String nazivMjernePostaje = unosNazivaMjernePostaje(unos, i);

			if (i == 2) {
				visinaRadioSonazneMjernePostaje = unosVisineRadioSonazneMjernePostaje(unos);
			}

			String nazivMjesta = unosNazivaMjesta(unos);

			String nazivZupanije = unosNazivaZupanije(unos);

			String nazivDrzave = unosNazivaDrzave(unos);
			boolean nastavi=false;
			do {
				try {
					provjera(sveZupanije, sveDrzave, nazivZupanije, nazivDrzave);
					nastavi = false;
				}
				catch(DuplaZupanijaException ex){
					System.out.println("Duplo, upisite ponovno");
					logger.error("Dupli unos zupanije",ex);
					nazivDrzave=unos.nextLine();
					nastavi=true;
				}
			}while(nastavi);
			
			BigDecimal povrsinaDrzave = unosPovrsine(unos);
			

			BigDecimal xKoordinata = unosKoordinateX(unos);

			BigDecimal yKoordinata = unosKoordinateY(unos);

			String elektricnaKomponenta = unosKomponente(unos);

			BigDecimal temperaturaZraka = unosVrijednostiTermometra(unos);

			BigDecimal vlagaZraka = unosVlageZraka(unos);

			String opisTlaka = unosOpisaTlaka(unos);

			BigDecimal tlakZraka = unosTlakaZraka(unos);

			// postavljanje vrijednosti objekata klase Senzor
			SenzorTemperature noviSenzorTemperature = new SenzorTemperature("°C", (byte) 2, elektricnaKomponenta);
			noviSenzorTemperature.setVrijednost(temperaturaZraka);
			SenzorVlage noviSenzorVlage = new SenzorVlage("%", (byte) 2);
			noviSenzorVlage.setVrijednost(vlagaZraka);
			SenzorTlakaZraka noviSenzorTlaka = new SenzorTlakaZraka("hPa", (byte) 1000, opisTlaka);
			noviSenzorTlaka.setVrijednost(tlakZraka);

			// Dodavanje senzora u polje
			Senzor[] senzori = new Senzor[BROJ_SENZORA];
			senzori[0] = noviSenzorTemperature;
			senzori[1] = noviSenzorVlage;
			senzori[2] = noviSenzorTlaka;

			// Kreiranje objekata ostalih klasa
			GeografskaTocka novaGeografskaTocka = new GeografskaTocka(xKoordinata, yKoordinata);
			Drzava novaDrzava = new Drzava(nazivDrzave, povrsinaDrzave);
			Zupanija novaZupanija = new Zupanija(nazivZupanije, novaDrzava);
			Mjesto novoMjesto = new Mjesto(nazivMjesta, novaZupanija);

			if (i < 2) {
				novaMjernaPostaja[i] = new MjernaPostaja(nazivMjernePostaje, novoMjesto, novaGeografskaTocka, senzori);
			} else {
				novaMjernaPostaja[i] = new RadioSondaznaMjernaPostaja(nazivMjernePostaje, novoMjesto,
						novaGeografskaTocka, senzori, visinaRadioSonazneMjernePostaje);
			}

		}

		// ispis podataka o mjernoim postajama
		ispisPodataka(novaMjernaPostaja, visinaRadioSonazneMjernePostaje);
		// ucitavanje temperature svake sekunde
		kontinuiranoUcitavanjeSenzoraTemperature(novaMjernaPostaja);
	}

	private static boolean provjera(List<String> sveZupanije, List<String> sveDrzave, String nazivZupanije,
			String nazivDrzave) throws DuplaZupanijaException {
		boolean nastavi=false;
		if(sveDrzave.contains(nazivDrzave) && sveZupanije.contains(nazivZupanije)) {
			logger.error("Dupli unos");
			throw new DuplaZupanijaException("Dupla zupanija");
		}
		else {
			sveDrzave.add(nazivDrzave);
			sveZupanije.add(nazivZupanije);
			nastavi=false;
		}
		return nastavi;
	}

	/**
	 * Generira vrijednost senzora temperature za sve mjerne postaje u intervalima
	 * od jedne sekunde.
	 * 
	 * @param novaMjernaPostaja
	 *            mjerne postaje na kojima se izvodi mjerenje
	 * @throws InterruptedException
	 *             simulira intervale mjerenja
	 */
	private static void kontinuiranoUcitavanjeSenzoraTemperature(MjernaPostaja[] novaMjernaPostaja)
			throws InterruptedException {
		while (true) {
			for (int i = 0; i < BROJ_POSTAJA; i++) {
				Thread.sleep(10000);
				Senzor[] poljeSenzoraTemperature = novaMjernaPostaja[i].getSviSenzori();
				for (int j = 0; j < BROJ_SENZORA; j++) {
					if (poljeSenzoraTemperature[j] instanceof SenzorTemperature) {
						((SenzorTemperature) poljeSenzoraTemperature[j]).generirajVrjednost(novaMjernaPostaja, i);

					}

				}
			}

		}
	}

	/**
	 * Trazi korisnika da unese podatke za visinu radiosondazne mjerne postaje.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o visini radiosondazne mjerne postaje
	 */
	private static Integer unosVisineRadioSonazneMjernePostaje(Scanner unos) {
		System.out.println("Unesite visinu radio sondažne mjerne postaje:");
		Integer visina = Provjera.provjeraIntegera(unos);
		logger.info("Unesena je visina radiosondazne : " + visina);
		unos.nextLine();
		return visina;
	}

	/**
	 * Trazi korisnika da unese podatak za tlak zraka.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o tlaku zraka
	 */
	private static BigDecimal unosTlakaZraka(Scanner unos) {
		System.out.println("Unesite vrijednost barometra:");
		BigDecimal tlak = Provjera.provjeraBigDecimala(unos);
		logger.info("Unesena je vrijednost senzor za tlaka zraka: " + tlak);
		return tlak;
	}

	/**
	 * Trazi korisnika da unese podatak za opis tlaka zraka.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o opisu tlaka zraka
	 */
	private static String unosOpisaTlaka(Scanner unos) {
		System.out.println("Unesite opis tlaka zraka (visoki, niski, normalan):");
		String opis = unos.nextLine();
		return opis;
	}

	/**
	 * Trazi korisnika da unese podatak za vlagu zraka.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o tlaku zraka
	 */
	private static BigDecimal unosVlageZraka(Scanner unos) {
		System.out.println("Unesite vrijednost senzora vlage:");
		BigDecimal vlaga = SenzorVlage.kontrolaVlage(unos);
		logger.info("Unesena je vrijednost senzor za vlagu zraka: " + vlaga);
		unos.nextLine();
		return vlaga;
	}

	/**
	 * Trazi korisnika da unese podatak za temperaturu zraka.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o tempeaturi zraka
	 */
	private static BigDecimal unosVrijednostiTermometra(Scanner unos) {
		System.out.println("Unesite vrijednost senzora za temperaturu:");
		BigDecimal temperatura = Provjera.provjeraBigDecimala(unos);
		logger.info("Unesena je vrijednost senzor za temperaturu: " + temperatura);
		unos.nextLine();
		return temperatura;
	}

	/**
	 * Trazi korisnika da unese podatak za elektricnu komponentu senzora.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak za elektricnu komponentu senzora
	 */
	private static String unosKomponente(Scanner unos) {
		System.out.println("Unesite elektonicku komponentu za senzor temperature");
		String elKomponenta = unos.nextLine();
		return elKomponenta;
	}

	/**
	 * Trazi korisnika da unese podatak y koordinatu mjerne postaje.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o y koordinati mjerne postaje
	 */
	private static BigDecimal unosKoordinateY(Scanner unos) {
		System.out.println("Unesite y koordinatu");
		BigDecimal yKoordinata = Provjera.provjeraBigDecimala(unos);
		logger.info("Unesena je Y koordinata: " + yKoordinata);
		unos.nextLine();
		return yKoordinata;
	}

	/**
	 * Trazi korisnika da unese podatak x koordinatu mjerne postaje.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o x koordinati mjerne postaje
	 */
	private static BigDecimal unosKoordinateX(Scanner unos) {
		System.out.println("Unesite X koordinatu");
		BigDecimal xKoordinata = Provjera.provjeraBigDecimala(unos);
		logger.info("Unesena je X koordinata: " + xKoordinata);
		unos.nextLine();
		return xKoordinata;
	}

	/**
	 * Trazi korisnika da unese podatak za povrsinu drzave.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o povrsini drzave
	 */
	private static BigDecimal unosPovrsine(Scanner unos) {
		System.out.println("Unesite povrsinu drzave");
		BigDecimal povrsinaDrzave = Provjera.provjeraBigDecimala(unos);
		logger.info("Unesena je povrsina drzave: " + povrsinaDrzave);
		unos.nextLine();
		return povrsinaDrzave;
	}

	/**
	 * Trazi korisnika da unese podatak za naziv drzave.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o nazivu drzave
	 */
	private static String unosNazivaDrzave(Scanner unos) {
		System.out.println("Unesite naziv drzave");
		String nazivDrzave = unos.nextLine();
		logger.info("Unesen je naziv drzave: " + nazivDrzave);
		return nazivDrzave;
	}

	/**
	 * Trazi korisnika da unese podatak za naziv zupanije.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return podatak o nazivu zupanije
	 */
	private static String unosNazivaZupanije(Scanner unos) {
		System.out.println("Unesite naziv zupanije");
		String nazivZupanije = unos.nextLine();
		logger.info("Unesen je naziv zupanije: " + nazivZupanije);
		return nazivZupanije;
	}

	/**
	 * Trazi korisnika da unese podatak za naziv mjesta.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return podatak o nazivu zupa nije
	 */
	private static String unosNazivaMjesta(Scanner unos) {
		System.out.println("Unesite naziv mjesta");
		String nazivMjesta = unos.nextLine();
		logger.info("Unesen je naziv mjesta: " + nazivMjesta);
		return nazivMjesta;
	}

	/**
	 * Trazi korisnika da unese podatak za naziv mjesta. Ovisno o rednom broju
	 * mjerne postaje, unosi se naziv mjerne postaje ili radiosondazne mjerne
	 * postaje.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @param i
	 *            iterator koji oznacava redni broj mjerne postaje
	 * @return vraca podatak o nazivu mjerne postaje
	 */
	private static String unosNazivaMjernePostaje(Scanner unos, int i) {
		if (i < 2) {
			System.out.println("Unesite naziv mjerne postaje");
			String nazivMjernePostaje = unos.nextLine();
			logger.info("Unesen je naziv mjerne postaje: " + nazivMjernePostaje);
			return nazivMjernePostaje;
		} else {
			System.out.println("Unesite naziv radio sonazne mjerne postaje");
			String nazivRadioSonazneMjernePostaje = unos.nextLine();
			logger.info("Unesen je naziv radiosondazne mjerne postaje: " + nazivRadioSonazneMjernePostaje);
			return nazivRadioSonazneMjernePostaje;
		}
	}

	/**
	 * Ispisuje podatke o mjernim postajama i senzorima. U prvoj for petlji se
	 * ispisuju podaci o mjernim postajama. U drugoj for petlji se ispisuju podaci o
	 * senzorima. Zadnji if povecava visinu radiosondazne mjerne postaje za 1.
	 * 
	 * @param novaMjernaPostaja mjerna postaja za koju se ispisuje podatak
	 * @param visinaRadioSonazneMjernePostaje podatak o visini radiosondazne mjerne postaje
	 */
	private static void ispisPodataka(MjernaPostaja[] novaMjernaPostaja, Integer visinaRadioSonazneMjernePostaje) {
		for (int j = 0; j < BROJ_POSTAJA; j++) {

			/* Ispisuje podatke o postajama */
			System.out.println("\n\n--------------------\nNaziv mjerne postaje: " + novaMjernaPostaja[j].getNaziv());

			if (novaMjernaPostaja[j] instanceof RadioSondaznaMjernaPostaja) {
				System.out.println("Postaja je radio sondazna\nVisina radio sondazne mjerne postaje je: "
						+ ((RadioSondaznaMjernaPostaja) novaMjernaPostaja[j]).dohvatiVisinuPostaje());
			}
			System.out.println("Postaja se nalazi u mjestu " + novaMjernaPostaja[j].getMjesto().getNaziv()
					+ ", zupaniji: " + novaMjernaPostaja[j].getMjesto().getZupanija().getNaziv() + ", drzavi "
					+ novaMjernaPostaja[j].getMjesto().getZupanija().getDrzava().getNaziv()
					+ "\nTocne koordinate postaje su: X:" + novaMjernaPostaja[j].getGeografskaTocka().getX() + " Y:"
					+ novaMjernaPostaja[j].getGeografskaTocka().getY() + "\nVrijednosti Senzora postaje su:");

			/* Ispisuje podatke o senzorima */
			for (int i = 0; i < BROJ_SENZORA; i++) {
				System.out.println(novaMjernaPostaja[j].dohvatiSenzore(i));
			}

			/* Povecava visinu mjerne postaje */
			if (novaMjernaPostaja[j] instanceof RadioSondaznaMjernaPostaja) {
				((RadioSondaznaMjernaPostaja) novaMjernaPostaja[j])
						.podesiVisinuPostaje(((RadioSondaznaMjernaPostaja) novaMjernaPostaja[j]).povecajVisinu(
								((RadioSondaznaMjernaPostaja) novaMjernaPostaja[j]).dohvatiVisinuPostaje()));
				System.out.println(((RadioSondaznaMjernaPostaja) novaMjernaPostaja[j]).dohvatiVisinuPostaje());
			}
		}
	}

}
