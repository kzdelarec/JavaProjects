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
	private String kratica;

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


	public List<Zupanija> getPopisZupanijaDrzave() {
		return popisZupanijaDrzave;
	}

	public void setPopisZupanijaDrzave(List<Zupanija> popisZupanijaDrzave) {
		this.popisZupanijaDrzave = popisZupanijaDrzave;
	}

	public List<Zupanija> getZupanije() {
		return popisZupanijaDrzave;
	}

	public Drzava getDrzava() {
		return this;
	}

	public String getKratica() {
		return kratica;
	}

	public void setKratica(String kratica) {
		this.kratica = kratica;
	}
	
	

	

}
