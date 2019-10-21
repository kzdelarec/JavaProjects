package hr.java.vjezbe.entitet;

import java.time.LocalDate;

public class Prodaja {

	private Artikl artikl;
	private Korisnik korisnik;
	private LocalDate datumObjave;
	
	public Prodaja(Artikl artikl, Korisnik korisnik, LocalDate datumObjave) {
		super();
		this.artikl = artikl;
		this.korisnik = korisnik;
		this.datumObjave = datumObjave;
	}

	public Artikl getArtikl() {
		return artikl;
	}

	public void setArtikl(Artikl artikl) {
		this.artikl = artikl;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public LocalDate getDatumObjave() {
		return datumObjave;
	}

	public void setDatumObjave(LocalDate datumObjave) {
		this.datumObjave = datumObjave;
	}
	
}
