package hr.knjiznica.entiteti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Korisnik extends Osoba{
	
	private Long oib;
	private Long idKorisnika;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyy.");
	LocalDate datumUclanjenja;

	public Korisnik(String ime, String prezime, String mailAdresa, Integer brojMobitela, Long oib, LocalDate datumUclanjenja, Long idKorisnika) {
		super(ime, prezime, mailAdresa, brojMobitela);
	
		this.datumUclanjenja = datumUclanjenja;
		this.oib = oib;
		this.idKorisnika = idKorisnika;
	}

	public Long getIdKorisnika() {
		return idKorisnika;
	}

	public void setIdKorisnika(Long idKorisnika) {
		this.idKorisnika = idKorisnika;
	}

	public Long getOib() {
		return oib;
	}

	public void setOib(Long oib) {
		this.oib = oib;
	}

	public LocalDate getDatumUclanjenja() {
		return datumUclanjenja;
	}

	public void setDatumUclanjenja(LocalDate datumUclanjenja) {
		this.datumUclanjenja = datumUclanjenja;
	}

}
