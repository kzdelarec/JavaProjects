package hr.java.vjezbe.glavna;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;

public class Glavna {

	// inicijalizacija statičkih varijabli

	public static final Integer BROJ_PROFESORA = 2;
	public static final Integer BROJ_PREDMETA = 3;
	public static final Integer BROJ_ISPITA = 1;

	public static void main(String[] args) {

		// inicijalizacija polja u koje se spremaju objekti
		Profesor[] poljeProfesora = new Profesor[BROJ_PROFESORA];
		Predmet[] poljePredmeta = new Predmet[BROJ_PREDMETA];
		Ispit[] poljeIspita = new Ispit[BROJ_ISPITA];

		// inicijalizacija skenera
		Scanner unos = new Scanner(System.in);

		// unos profesora
		unosProfesora(poljeProfesora, unos);

		// unos predmeta
		unosPredmeta(poljeProfesora, poljePredmeta, unos);
		
		// unos ispita
		unosIspita(poljePredmeta, poljeIspita, unos);
		
		// ispis ispita na kojima je ostvarena ocjena 5
		ispisPetica(poljeIspita);
		
	}

	public static void ispisPetica(Ispit[] poljeIspita) {
		for(int i = 0; i<poljeIspita.length;i++) {
			if(poljeIspita[i].getOcjena().equals(5)) {
				System.out.println("\nStudent " + poljeIspita[i].getStudent().getIme() + " " + poljeIspita[i].getStudent().getPrezime()
						+ " je ostvario ocjenu 'izvrstan' na predmetu '" + poljeIspita[i].getPredmet().getNaziv() + "'");
			}
		}
	}

	public static void unosIspita(Predmet[] poljePredmeta, Ispit[] poljeIspita, Scanner unos) {
		for (int i = 0; i < BROJ_ISPITA; i++) {

			// lokalne varijable za kreiranje objekta
			Predmet predmet;
			Student student;
			Integer ocjena;
			LocalDateTime datumIVrijeme;
			Integer odabir;

			System.out.println("Unesite " + (i + 1) + ". ispitni rok:");

			System.out.println("Odaberite predmet:");
			// ispis predmeta za odabir
			for (int j = 0; j < poljePredmeta.length; j++) {
				System.out.println(j + 1 + ". " + poljePredmeta[j].getNaziv());
			}
			// odabir predmeta
			System.out.println("Odabir >> ");
			odabir = unos.nextInt();
			unos.nextLine(); // cisti buffer, bez toga kod preskace djelove za input
			predmet = poljePredmeta[odabir - 1];
			
			System.out.println("Odaberite studenta:");
			// ispis studenata za odabir
			for (int j = 0; j < predmet.getStudenti().length; j++) {
				System.out.println(j + 1 + ". " + predmet.getStudenti()[j].getIme() + " " + predmet.getStudenti()[j].getPrezime());
			}
			// odabir predmeta
			System.out.println("Odabir >> ");
			odabir = unos.nextInt();
			unos.nextLine(); // cisti buffer, bez toga kod preskace djelove za input
			student = predmet.getStudenti()[odabir-1];
			
			System.out.println("Unesite ocjenu na ispitu (1-5): ");
			ocjena = unos.nextInt();
			unos.nextLine(); // cisti buffer, bez toga kod preskace djelove za input
			
			System.out.println("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy. HH:mm)");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
			datumIVrijeme = LocalDateTime.parse(unos.nextLine(),formatter);

			
			//kreiranje objekta i pohrana u polje
			Ispit ispit = new Ispit(predmet, student, ocjena, datumIVrijeme);
			poljeIspita[i]=ispit;
		}
	}

	public static void unosPredmeta(Profesor[] poljeProfesora, Predmet[] poljePredmeta, Scanner unos) {
		for (int i = 0; i < BROJ_PREDMETA; i++) {

			// lokalne varijable za kreiranje objekta
			String sifra;
			String naziv;
			Integer brojEctsBodova;
			Profesor nositelj;
			Integer odabir;
			Integer brojStudenata;

			System.out.println("Unesite " + (i + 1) + ". predmet:");

			// kod za popunjavanje lokalnih varijabli
			System.out.println("Unesite šifru predmeta: ");
			sifra = unos.nextLine();

			System.out.println("Unesite naziv predmeta: ");
			naziv = unos.nextLine();

			System.out.println("Unesite broj ECTS bodova za predmet '" + naziv + "': ");
			brojEctsBodova = unos.nextInt();
			unos.nextLine(); // cisti buffer, bez toga kod preskace djelove za input

			System.out.println("Odaberite profesora:");

			// ispis profesora
			for (int j = 0; j < poljeProfesora.length; j++) {
				System.out.println(j + 1 + ". " + poljeProfesora[j].getIme() + " " + poljeProfesora[j].getPrezime());
			}

			// odabir profesora
			System.out.println("Odabir >> ");
			odabir = unos.nextInt();
			unos.nextLine(); // cisti buffer, bez toga kod preskace djelove za input

			nositelj = poljeProfesora[odabir - 1]; // odabir-1 zato što odabir ide od 1, a polje od 0

			// broj studenata i njihov unos
			System.out.println("Unesite broj studenata za predmet '" + naziv + "': ");
			brojStudenata = unos.nextInt();
			unos.nextLine(); // cisti buffer, bez toga kod preskace djelove za input

			Student[] studenti = new Student[brojStudenata];

			studenti = unosStudenata(unos, i, brojStudenata, studenti);

			// kreiranje objekta i pohrana u polje
			Predmet predmet = new Predmet(sifra, naziv, brojEctsBodova, nositelj, studenti);
			poljePredmeta[i] = predmet;
		}
	}

	public static Student[] unosStudenata(Scanner unos, int i, Integer brojStudenata, Student[] studenti) {
		for (int k = 0; k < brojStudenata; k++) {
			// lokalne varijable za kreiranje objekta
			String ime;
			String prezime;
			String jmbag;
			LocalDate datumRodenja;

			System.out.println("Unesite " + (i + 1) + ". studenta:");

			// kod za popunjavanje lokalnih varijabli

			System.out.println("Unesite ime studenta: ");
			ime = unos.nextLine();

			System.out.println("Unesite prezime studenta: ");
			prezime = unos.nextLine();

			System.out.println("Unesite datum rođenja studenta "+ prezime + " " + ime + " u formatu (dd.MM.yyyy.): ");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
			datumRodenja = LocalDate.parse(unos.nextLine(), formatter);

			System.out.println("Unesite JMBAG studenta " + prezime + " " + ime + ": ");
			jmbag = unos.nextLine();

			// kreiranje objekta i pohrana u polje
			Student student = new Student(ime, prezime, jmbag, datumRodenja);
			studenti[k] = student;
		}
		return studenti;
	}

	public static void unosProfesora(Profesor[] poljeProfesora, Scanner unos) {
		for (int i = 0; i < BROJ_PROFESORA; i++) {

			// lokalne varijable za kreiranje objekta
			String sifra;
			String ime;
			String prezime;
			String titula;

			System.out.println("Unesite " + (i + 1) + ". profesora:");

			// kod za popunjavanje lokalnih varijabli
			System.out.println("Unesite šifru profesora: ");
			sifra = unos.nextLine();

			System.out.println("Unesite ime profesora: ");
			ime = unos.nextLine();

			System.out.println("Unesite prezime profesora: ");
			prezime = unos.nextLine();

			System.out.println("Unesite titula profesora: ");
			titula = unos.nextLine();

			// kreiranje objekta i pohrana u polje

			Profesor profesor = new Profesor(sifra, ime, prezime, titula);
			poljeProfesora[i] = profesor;

		}
	}

}
