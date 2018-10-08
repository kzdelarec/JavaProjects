package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.util.Scanner;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.Zupanija;

public class Glavna {

	public static final int BROJ_POSTAJA = 3;

	public static void main(String[] args) {

		MjernaPostaja[] novaMjernaPostaja = new MjernaPostaja[BROJ_POSTAJA];
		for (int i = 0; i < BROJ_POSTAJA; i++) {

			Scanner unos = new Scanner(System.in);

			System.out.println("Unesite podatke za " + (i + 1) + ". mjernu postaju");

			String nazivMjernePostaje = unosNazivaMjernePostaje(unos);

			String nazivMjesta = unosNazivaMjesta(unos);

			String nazivZupanije = unosNazivaZupanije(unos);

			String nazivDrzave = unosNazivaDrzave(unos);

			BigDecimal povrsinaDrzave = unosPovrsine(unos);

			BigDecimal xKoordinata = unosKoordinateX(unos);

			BigDecimal yKoordinata = unosKoordinateY(unos);

			GeografskaTocka novaGeografskaTocka = new GeografskaTocka(xKoordinata, yKoordinata);
			Drzava novaDrzava = new Drzava(nazivDrzave, povrsinaDrzave);
			Zupanija novaZupanija = new Zupanija(nazivZupanije, novaDrzava);
			Mjesto novoMjesto = new Mjesto(nazivMjesta, novaZupanija);

			novaMjernaPostaja[i] = new MjernaPostaja(nazivMjernePostaje, novoMjesto, novaGeografskaTocka);

		}
		for (int j = 0; j < BROJ_POSTAJA; j++) {
			System.out.println("\n\n--------------------\nNaziv mjerne postaje: " + novaMjernaPostaja[j].getNaziv()
					+ "\nPostaja se nalazi u mjestu " + novaMjernaPostaja[j].getMjesto().getNaziv() + ", zupaniji: "
					+ novaMjernaPostaja[j].getMjesto().getZupanija().getNaziv() + ", drzavi "
					+ novaMjernaPostaja[j].getMjesto().getZupanija().getDrzava().getNaziv()
					+ "\nTocne koordinate postaje su: X:" + novaMjernaPostaja[j].getGeografskaTocka().getX() + " Y:"
					+ novaMjernaPostaja[j].getGeografskaTocka().getY());

		}
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

	private static String unosNazivaMjernePostaje(Scanner unos) {
		System.out.println("Unesite naziv mjerne postaje");
		String nazivMjernePostaje = unos.nextLine();
		return nazivMjernePostaje;
	}

}
