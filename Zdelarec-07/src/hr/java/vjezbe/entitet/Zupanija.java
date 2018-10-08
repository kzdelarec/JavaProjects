package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja entitet zupanije koji je definiran nazivom i objektom klase
 * Drzava.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class Zupanija extends BazniEntitet{

	private String naziv;
	private Drzava drzava;
	List<Mjesto> listaMjestaZupanije;
	/**
	 * Inicijalizira podatak o nazivu zupanije i o drzavi kojoj pripada.
	 * 
	 * @param naziv
	 *            podatak o nazivu zupanije
	 * @param drzava
	 *            objekt klase Drzava
	 * @see hr.java.vjezbe.entitet.Drzava
	 */
	public Zupanija(String naziv, Drzava drzava) {
		this.naziv = naziv;
		this.drzava = drzava;
		listaMjestaZupanije = new ArrayList<>();
	}
	public Zupanija(String naziv, Drzava drzava, Integer id) {
		super(id);
		this.naziv = naziv;
		this.drzava = drzava;
		listaMjestaZupanije = new ArrayList<>();
	}

	/**
	 * Vraca naziv drzave.
	 * 
	 * @return vraca podatak o nazivu drzave
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Postavlja naziv zupanije.
	 * 
	 * @param naziv
	 *            podatak o nazivu zupanije
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	/**
	 * Vraca podatak o drzavi kojoj pripada zupanija.
	 * 
	 * @return vraca podatak o drzavi
	 * @see hr.java.vjezbe.entitet.Drzava
	 */
	public Drzava getDrzava() {
		return drzava;
	}

	/**
	 * Postavlja podatke za drzavu kojoj zupanija pripada.
	 * 
	 * @param drzava
	 *            podatak o drzavi kojoj pripada zupanija.
	 * @see hr.java.vjezbe.entitet.Drzava
	 */
	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}
	
	public List<Mjesto> getPopisMjestaZupanije() {
		return listaMjestaZupanije;
	}

	public void setPopisMjestaZupanije(List<Mjesto> listaMjestaZupanije) {
		this.listaMjestaZupanije = listaMjestaZupanije;
	}

	public List<Mjesto> getMjesta() {
		return listaMjestaZupanije;
	}

}
