package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Kategorija;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.Prodaja;

public class Glavna {

	public static void main(String[] args) {


		Scanner unos = new Scanner(System.in);
		
		Korisnik[] korisnici = unosKorisnika(unos);
		Kategorija[] kategorije = unosKategorija(unos);
		Prodaja[] prodaje = unosProdaje(unos, korisnici, kategorije);
		
		ispis(prodaje);

		unos.close();
	}

	public static void ispis(Prodaja[] prodaje) {
		System.out.println("Trenutno su oglasi na prodaju:");
		System.out.println("************************************************************");
		for (int i = 0; i < prodaje.length; i++) {
			Prodaja oglas = prodaje[i];
			Korisnik korisnik = oglas.getKorisnik();
			System.out.println("Naslov: " + oglas.getArtikl().getNaslov());
			System.out.println("opis: " + oglas.getArtikl().getOpis());
			System.out.println("Cijena: " + oglas.getArtikl().getCijena());
			System.out.println("Kontakt podaci: " + korisnik.getIme() + " " + korisnik.getPrezime() + ", mail: " + korisnik.getEmail() + ", tel: " + korisnik.getTelefon());
		}
	}

	public static Prodaja[] unosProdaje(Scanner unos, Korisnik[] korisnici, Kategorija[] kategorije) {
		System.out.println("Unesite broj artikala koji su aktivno na prodaju: ");
		int brojProdaja = unos.nextInt();
		unos.nextLine(); //clear buffer
		
		Prodaja[] prodaje = new Prodaja[brojProdaja];
		
		for (int i = 0; i < brojProdaja; i++) {
			Korisnik korisnik = odabirKorisnika(unos, korisnici);
			Kategorija kategorija = odabirKategorije(unos, kategorije);
			Artikl artikl = odabirArtikla(unos, kategorija);
			prodaje[i] = new Prodaja(artikl, korisnik, LocalDate.now());
		}
		
		return prodaje;
	}

	public static Artikl odabirArtikla(Scanner unos, Kategorija kategorija) {
		System.out.println("Odaberite artikl: ");
		Artikl [] artikli = kategorija.getArtikli();
		for (int j = 0; j < artikli.length; j++) {
			System.out.println((j+1) + "." + artikli[j].getNaslov());
		}
		
		System.out.println("Odabir >> ");
		int odabirArtikla = unos.nextInt();
		unos.nextLine(); //clear buffer
		
		Artikl artikl = artikli[odabirArtikla-1];
		return artikl;
	}

	public static Kategorija odabirKategorije(Scanner unos, Kategorija[] kategorije) {
		System.out.println("Odaberite kategoriju: ");
		for (int j = 0; j < kategorije.length; j++) {
			Kategorija kategorija = kategorije[j];
			System.out.println((j+1) + "." + kategorija.getNaziv());
		}
		
		System.out.println("Odabir >> ");
		int odabirKategorije = unos.nextInt();
		unos.nextLine(); //clear buffer
		
		Kategorija kategorija = kategorije[odabirKategorije-1];
		return kategorija;
	}

	public static Korisnik odabirKorisnika(Scanner unos, Korisnik[] korisnici) {
		System.out.println("Odaberite korisnika: ");
		for (int j = 0; j < korisnici.length; j++) {
			Korisnik korisnik = korisnici[j];
			System.out.println((j+1) + "." + korisnik.getIme() + " " + korisnik.getPrezime());
		}
		
		System.out.println("Odabir >> ");
		int odabirKorisnik = unos.nextInt();
		unos.nextLine(); //clear buffer
		
		Korisnik korisnik = korisnici[odabirKorisnik-1];
		return korisnik;
	}

	public static Kategorija[] unosKategorija(Scanner unos) {
		System.out.println("Unesite broj kategorija koji želite unijeti: ");
		int brojKategorija = unos.nextInt();
		unos.nextLine(); //clear buffer
		
		Kategorija[] kategorije = new Kategorija[brojKategorija];
		
		for (int i = 0; i < brojKategorija; i++) {
			System.out.println("Unesite naziv " + (i+1) + ". kategorije: ");
			String naziv = unos.nextLine();
			
			Artikl[] artikli = unosArtikala(unos);
			
			kategorije[i] = new Kategorija(naziv, artikli);
		}
		return kategorije;
		
	}

	public static Artikl[] unosArtikala(Scanner unos) {
		System.out.println("Unesite broj artikala koji želite unijeti za unesenu kategoriju: ");
		int brojArtiakala = unos.nextInt();
		unos.nextLine(); //clear buffer
		
		Artikl[] artikli = new Artikl[brojArtiakala];
		
		for (int j = 0; j < brojArtiakala; j++) {
			System.out.println("Unesite naslov" +  (j+1) + ". oglasa artikla: ");
			String naslov = unos.nextLine();
			
			System.out.println("Unesite opis" +  (j+1) + ". oglasa artikla: ");
			String opis = unos.nextLine();
			
			System.out.println("Unesite cijenu" +  (j+1) + ". oglasa artikla: ");
			BigDecimal cijena = unos.nextBigDecimal();
			unos.nextLine(); //clear buffer
			
			artikli[j] = new Artikl(naslov, opis, cijena);
		}
		return artikli;
	}

	public static Korisnik[] unosKorisnika(Scanner unos) {
		System.out.println("Unesite broj korisnika koji zelite unijeti: ");
		
		int brojKorisnika = unos.nextInt();
		unos.nextLine(); //clear buffer
		
		Korisnik[] korisnici = new Korisnik[brojKorisnika];
		
		for (int i = 0; i < brojKorisnika; i++) {
			System.out.println("Unesite ime " + (i+1) + ". korisnika: ");
			String ime = unos.nextLine();
			
			System.out.println("Unesite prezime " + (i+1) + ". korisnika: ");
			String prezime = unos.nextLine();
			
			System.out.println("Unesite e-mail " + (i+1) + ". korisnika: ");
			String email = unos.nextLine();
			
			System.out.println("Unesite telefon " + (i+1) + ". korisnika: ");
			String telefon = unos.nextLine();
			
			korisnici[i] = new Korisnik(ime, prezime, email, telefon);
		}
		
		return korisnici;
	}

}
