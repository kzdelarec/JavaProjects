package hr.java.vjezbe.entitet;

import java.util.ArrayList;

/**
 * Predstavlja svojstva kategorija koja nastaju nasljedivanjem ove klase,a to su naziv i polje objekata artikli
 * 
 * @author Patricija Ku�e
 *
 */
public class Kategorija<T extends Artikl> {
	private String naziv;
	private ArrayList<T> artikli;
	private Long id;
	
	/**
	 * Inicijalizira podatke o nazivu i polju objekata artikli
	 * 
	 * @param naziv naziv kategorije
	 * @param class1 polje artikala
	 */
	public Kategorija(Long id, String naziv) {
		super();
		this.naziv = naziv;
		this.artikli = new ArrayList<>();
		this.id = id;
	}
	
	/**
	 * Dohvaca naziv kategorije
	 * @return naziv kategorije
	 */
	public String getNaziv() {
		return naziv;
	}
	
	/**
	 * Postavlja vrijednost naziva kategorije
	 * @param naziv postavljena vrijednost kategorije
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	/**
	 * Dohvaca vrijednost polja objekata artikli
	 * 
	 * @return vraca polje objekata
	 */
	public ArrayList<T> getArtikli() {
		return artikli;
	}
	
	/**
	 * Postavlja vrijednost artikala u polju
	 * 
	 * @param artikli uneseni podatci o svakom artiklu zasebno
	 */
	public void setArtikli(ArrayList<T> artikli) {
		this.artikli = artikli;
	}
	
	public void dodajArtikl(T objekt) {
		ArrayList<T> listaArtikala = getArtikli();
		listaArtikala.add(objekt);
	}
	
	public T dohvatiArtikle(int index) {
		return getArtikli().get(index);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
