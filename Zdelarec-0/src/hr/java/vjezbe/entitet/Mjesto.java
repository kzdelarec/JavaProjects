package hr.java.vjezbe.entitet;

/**
 * Predstavlja entitet mjest koji je definiran nazivom i zupanjom.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class Mjesto {
	
	private String naziv;
	private Zupanija zupanija;
	
	/**
	 * Inicijalizira podatak o nazivu mjesta i zupaniji kojoj pripada.
	 * 
	 * @param naziv podatak o nazivu mjesta
	 * @param zupanija objekt klase Zupanija
	 * @see hr.java.vjezbe.entitet.Zupanija
	 */
	public Mjesto(String naziv, Zupanija zupanija) {
		this.naziv = naziv;
		this.zupanija = zupanija;
	}

	/**
	 * Vraca naziv mjesta.
	 * 
	 * @return vraca podatak o nazivu mjesta
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Postavlja naziv mjesta.
	 * 
	 * @param naziv podatak o nazivu mjesta
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	/**
	 * Vraca podatak o zupaniji u kojoj se nalazi mjesto.
	 *  
	 * @return vraca podatke o zupaniji
	 * @see hr.java.vjezbe.entitet.Zupanija
	 */
	public Zupanija getZupanija() {
		return zupanija;
	}

	/**
	 * Postavlja podatke za zupaniju u kojoj se nalazi mjesto.
	 * 
	 * @param zupanija podatak o zupaniji u kojoj se nalazi mjesto
	 * @see hr.java.vjezbe.entitet.Zupanija
	 */
	public void setZupanija(Zupanija zupanija) {
		this.zupanija = zupanija;
	}
	

}
