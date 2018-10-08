package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Predstavlja entitet drzave koji je definiran nazivom i povrsinom.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class Drzava {

	private String naziv;
	private BigDecimal povrsina;

	/**
	 * Inicijalizira podatak o nazivu drzave i njenoj povrsini.
	 * 
	 * @param naziv podatak o nazivu drzave
	 * @param povrsina podatak o povrsini drzave
	 */
	public Drzava(String naziv, BigDecimal povrsina) {
		this.naziv = naziv;
		this.povrsina = povrsina;
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
	 * @param naziv podatak o nazivu drzave
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
	 * @param povrsina podatak o povrsini drzave
	 */
	public void setPovrsina(BigDecimal povrsina) {
		this.povrsina = povrsina;
	}

}
