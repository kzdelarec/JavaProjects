package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja entitet mjest koji je definiran nazivom i zupanjom.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class Mjesto extends BazniEntitet{
	
	private Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String naziv;
	private Zupanija zupanija;
	private VrstaMjesta vrstaMjest;
	private List<MjernaPostaja> popisMjernihPostaja;
	
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
		popisMjernihPostaja = new ArrayList<>();
	}

	public Mjesto(String naziv, Zupanija zupanija, Integer id) {
		super(id);
		this.naziv = naziv;
		this.zupanija = zupanija;
		popisMjernihPostaja = new ArrayList<>();
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

	/**
	 * Dohvaca podatak o vrsti mjesta.
	 * 
	 * @return vraca podatak o vrsti mjesta
	 */
	public VrstaMjesta getVrstaMjest() {
		return vrstaMjest;
	}

	/**
	 * Postavlja podatak o vrsti mjesta.
	 * 
	 * @param vrstaMjest enumeracija vrste mjesta
	 */
	public void setVrstaMjest(VrstaMjesta vrstaMjest) {
		this.vrstaMjest = vrstaMjest;
	}

	/**
	 * Dohvaca popis u kojemu se nalaze objekti mjernih postaja koji se nalaze u tom mjestu.
	 * 
	 * @return vraca popis mjernih postaja koji se nalaze u tom mjestu
	 */
	public List<MjernaPostaja> getPopisMjernihPostaja() {
		return popisMjernihPostaja;
	}

	/**
	 * Posavlja listu mjernih postaja u nekom mjestu.
	 * 
	 * @param popisMjernihPostaja mjerne postaje u nekom mjestu
	 */
	public void setPopisMjernihPostaja(List<MjernaPostaja> popisMjernihPostaja) {
		this.popisMjernihPostaja = popisMjernihPostaja;
	}
	
	public List<MjernaPostaja> getMjesta() {
		return popisMjernihPostaja;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return getNaziv();
	}

}
