package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorTlakaZraka;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.Zupanija;

public class Glavna {

	public static final int BROJ_POSTAJA = 3;
	public static final int BROJ_SENZORA = 3;

	public static void main(String[] args) {

		List<MjernaPostaja> listaMjernihPostaja = new ArrayList<MjernaPostaja>();
		Integer visinaRadioSonazneMjernePostaje = 0;
		Scanner unos = new Scanner(System.in);
		// unos.hasNextLine();
		while (true) {
			int i = 0;

			System.out.println("Unesite podatke za " + (i + 1) + ". mjernu postaju");

			// Unos podataka korisnickih podataka
			String nazivMjernePostaje = unosNazivaMjernePostaje(unos, i);
			if (nazivMjernePostaje.equals(""))
				break;

			if (i == 2) {
				visinaRadioSonazneMjernePostaje = unosVisineRadioSonazneMjernePostaje(unos);
			}

			String nazivMjesta = unosNazivaMjesta(unos);

			String nazivZupanije = unosNazivaZupanije(unos);

			String nazivDrzave = unosNazivaDrzave(unos);

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

			if (i < 2 || i > 2) {
				listaMjernihPostaja
						.add(new MjernaPostaja(nazivMjernePostaje, novoMjesto, novaGeografskaTocka, senzori));
			} else {
				listaMjernihPostaja.add(new RadioSondaznaMjernaPostaja(nazivMjernePostaje, novoMjesto,
						novaGeografskaTocka, senzori, visinaRadioSonazneMjernePostaje));
			}
			i++;

		}

		// Sortiranje mjernih postaja
		Comparator<MjernaPostaja> c = (p, o) -> p.getNaziv().compareTo(o.getNaziv());
		c = c.thenComparing((p, o) -> p.getMjesto().getNaziv().compareTo(o.getMjesto().getNaziv()));
		listaMjernihPostaja.sort(c);

		// Pretvorba u polje
		MjernaPostaja[] novaMjernaPostaja = (MjernaPostaja[]) listaMjernihPostaja
				.toArray(new MjernaPostaja[listaMjernihPostaja.size()]);

		// ispis podataka o mjernoim postajama
		ispisPodataka(novaMjernaPostaja, visinaRadioSonazneMjernePostaje);
	}

	// metode za unos podataka
	private static Integer unosVisineRadioSonazneMjernePostaje(Scanner unos) {
		System.out.println("Unesite visinu radio sondažne mjerne postaje:");
		Integer visina = unos.nextInt();
		unos.nextLine();
		return visina;
	}

	private static BigDecimal unosTlakaZraka(Scanner unos) {
		System.out.println("Unesite vrijednost barometra:");
		BigDecimal tlak = unos.nextBigDecimal();
		unos.nextLine();
		return tlak;
	}

	private static String unosOpisaTlaka(Scanner unos) {
		System.out.println("Unesite opis tlaka zraka (visoki, niski, normalan):");
		String opis = unos.nextLine();
		return opis;
	}

	private static BigDecimal unosVlageZraka(Scanner unos) {
		System.out.println("Unesite vrijednost senzora vlage:");
		BigDecimal vlaga = unos.nextBigDecimal();
		unos.nextLine();
		return vlaga;
	}

	private static BigDecimal unosVrijednostiTermometra(Scanner unos) {
		System.out.println("Unesite vrijednost senzora za temperaturu:");
		BigDecimal temperatura = unos.nextBigDecimal();
		unos.nextLine();
		return temperatura;
	}

	private static String unosKomponente(Scanner unos) {
		System.out.println("Unesite elektonicku komponentu za senzor temperature");
		String elKomponenta = unos.nextLine();
		return elKomponenta;
	}

	private static BigDecimal unosKoordinateY(Scanner unos) {
		System.out.println("Unesite y koordinatu");
		BigDecimal yKoordinata = unos.nextBigDecimal();
		unos.nextLine();
		return yKoordinata;
	}

	private static BigDecimal unosKoordinateX(Scanner unos) {
		System.out.println("Unesite X koordinatu");
		BigDecimal xKoordinata = unos.nextBigDecimal();
		unos.nextLine();
		return xKoordinata;
	}

	private static BigDecimal unosPovrsine(Scanner unos) {
		System.out.println("Unesite povrsinu drzave");
		BigDecimal povrsinaDrzave = unos.nextBigDecimal();
		unos.nextLine();
		return povrsinaDrzave;
	}

	private static String unosNazivaDrzave(Scanner unos) {
		System.out.println("Unesite naziv drzave");
		String nazivDrzave = unos.nextLine();
		return nazivDrzave;
	}

	private static String unosNazivaZupanije(Scanner unos) {
		System.out.println("Unesite naziv zupanije");
		String nazivZupanije = unos.nextLine();
		return nazivZupanije;
	}

	private static String unosNazivaMjesta(Scanner unos) {
		System.out.println("Unesite naziv mjesta");
		String nazivMjesta = unos.nextLine();
		return nazivMjesta;
	}

	private static String unosNazivaMjernePostaje(Scanner unos, int i) {
		if (i < 2) {
			System.out.println("Unesite naziv mjerne postaje");
			String nazivMjernePostaje = unos.nextLine();
			return nazivMjernePostaje;
		} else {
			System.out.println("Unesite naziv radio sonazne mjerne postaje");
			String nazivRadioSonazneMjernePostaje = unos.nextLine();
			return nazivRadioSonazneMjernePostaje;
		}
	}

	// --------------------Metoda za ispis podataka o
	// mjernimpostajama-----------------------

	private static void ispisPodataka(MjernaPostaja[] novaMjernaPostaja, Integer visinaRadioSonazneMjernePostaje) {
		for (int j = 0; j < novaMjernaPostaja.length; j++) {

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
