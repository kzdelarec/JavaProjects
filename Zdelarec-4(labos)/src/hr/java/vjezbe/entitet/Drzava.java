package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Predstavlja entitet drzave koji je definiran nazivom i povrsinom.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class Drzava {

	private String naziv;
	private BigDecimal povrsina;
	private Set<Zupanija> popisZupanijaDrzave;

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
		popisZupanijaDrzave = new HashSet<>();
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


	public Set<Zupanija> getPopisZupanijaDrzave() {
		return popisZupanijaDrzave;
	}

	public void setPopisZupanijaDrzave(Set<Zupanija> popisZupanijaDrzave) {
		this.popisZupanijaDrzave = popisZupanijaDrzave;
	}

	public Set<Zupanija> getZupanije() {
		return popisZupanijaDrzave;
	}

	public Drzava getDrzava() {
		return this;
	}
	

	

}
