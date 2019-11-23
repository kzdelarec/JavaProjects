package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.util.Set;

/**
 * Predstavlja zajednicka svojsta svih objekata prodaje koji nasljeduju ovu klasu
 * 
 * @author Patricija Ku�e
 *
 */
public class Prodaja {
	private Artikl artikl;
	private Korisnik korisnik;
	private LocalDate datumObjave;
	private Long id;
	
	
	/**
	 * Inicijalizira podatke o artiklu, korisniku i datumu objave
	 * 
	 * @param artikl2 polje objekata tipa Artikl
	 * @param korisnik polje objekata tipa Korisnik
	 * @param datumObjave podatak o trenutnom datumu ispisivanja oglasa
	 */
	public Prodaja(Long id, Artikl artikl, Korisnik korisnik, LocalDate datumObjave) {
		super();
		this.artikl = artikl;
		this.korisnik = korisnik;
		this.datumObjave = datumObjave;
		this.id = id;
	}
	
	/**
	 * Dohvaca vrijednost artikla
	 * @return artikl vraca vrijednost artikla
	 */
	public Artikl getArtikl() {
		return artikl;
	}
	
	

	/**
	 * Postavlja vrijednost artikla
	 * @param artikl unesena vrijednost za artikl
	 */
	public void setArtikl(Artikl artikl) {
		this.artikl = artikl;
	}
	
	/**
	 * Dohvaca podatke o korisniku
	 * @return korisnik vraca podatke o korisniku
	 */
	public Korisnik getKorisnik() {
		return korisnik;
	}
	
	/**
	 * Postavlja podatke o korisniku
	 * 
	 * @param korisnik unesene vrijednosti za tip Korisnik
	 */
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	
	/**
	 * Dohvaca trenutni datum
	 * @return datumObjave trenutni datum
	 */
	public LocalDate getDatumObjave() {
		return datumObjave;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Postavlja vrijednost trenutog lokalnog vremena
	 * @param datumObjave trenutna vrijednost datuma 
	 */
	public void setDatumObjave(LocalDate datumObjave) {
		this.datumObjave = datumObjave;
	}
	
}
