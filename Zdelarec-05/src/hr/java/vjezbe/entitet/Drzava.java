package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja entitet drzave koji je definiran nazivom i povrsinom.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class Drzava {

	private String naziv;
	private BigDecimal povrsina;
	private List<Zupanija> popisZupanijaDrzave;

	/**
	 * Inicijalizira podatak o nazivu drzave i njenoj povrsini.
	 * 
	 * @param naziv
	 *            podatak o nazivu drzave
	 * @param povrsina
	 *            podatak o povrsini drzave
	 */
	public Drzava(String naziv, BigDecimal povrsina) {
		this.naziv = naziv;
		this.povrsina = povrsina;
		popisZupanijaDrzave = new ArrayList<>();
	}

	/**
	 * Vraca naziv entiteta Drzava.
	 * 
	 * @return vraca podatak o nazivu drzave
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Postavlja naziv drzave.
	 * 
	 * @param naziv
	 *            podatak o nazivu drzave
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	/**
	 * Vraca povrsinu entiteta Drzava.
	 * 
	 * @return vraca podatak o povrsini drzave
	 */
	public BigDecimal getPovrsina() {
		return povrsina;
	}

	/**
	 * Postavlja vrijednost za povrsinu drzave.
	 * 
	 * @param povrsina
	 *            podatak o povrsini drzave
	 */
	public void setPovrsina(BigDecimal povrsina) {
		this.povrsina = povrsina;
	}


	/**
	 * Dohvaca popis zupanija  neke drzave.
	 * 
	 * @return vraca popis zupanije neke drzave
	 */
	public List<Zupanija> getPopisZupanijaDrzave() {
		return popisZupanijaDrzave;
	}

	/**
	 * Postavlja vrijednosti liste zupanija.
	 * 
	 * @param popisZupanijaDrzave popis zupanija u nekoj drzavi
	 */
	public void setPopisZupanijaDrzave(List<Zupanija> popisZupanijaDrzave) {
		this.popisZupanijaDrzave = popisZupanijaDrzave;
	}

	/**
	 *  Dohvaca popis zupanija  neke drzave.
	 * 
	 * @return vraca popis zupanije neke drzave
	 */
	public List<Zupanija> getZupanije() {
		return popisZupanijaDrzave;
	}

	/**
	 * Dohvaca objekt klase Drzava.
	 * 
	 * @return vraca objekt klase drzava
	 */
	public Drzava getDrzava() {
		return this;
	}
	
	public String toString() {
		return getNaziv();
	}

	

}
