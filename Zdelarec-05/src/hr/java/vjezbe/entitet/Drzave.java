package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja entitet koji je zajednicki za sve objekte i podobjekte klase MjernaPostaja.
 * 
 * @author Kristijan Zdelarec
 * 
 * @param <T> objekt ili podobjekt klase MjernaPostaja
 * 
 * @see hr.java.vjezbe.entitet.MjernaPostaja
 * @See hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja
 */
public class Drzave<T extends Drzava> {
	
	private List<T> listaDrzava = new ArrayList<>();

	/**
	 * Stvara objekt klase MjernePostaje.
	 * 
	 */
	public Drzave() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Dohvaca listu u kojoj su spremjljeni objekti mjernih postaja.
	 * 
	 * @return vraca listu objekata mjerne postaje
	 */
	public List<T> getListaDrzava() {
		return listaDrzava;
	}

	/**
	 * Postavlja vrijednosti liste mjernih postaja.
	 * 
	 * @param listaMjernihPostaja lista svih mjernih postaja
	 */
	public void setListaDrzava(List<T> listaDrzava) {
		this.listaDrzava = listaDrzava;
	}
	
	/**
	 * Dohvaca objekt iz liste mjernih postaja koji ima trazeni index.
	 * 
	 * @param i index objekta kojeg trazimo
	 * @return vraca objekt pod trazenim indexom
	 */
	public Drzava get(Integer i) {
		return listaDrzava.get(i);
	}
	
	/**
	 * Sortira objekte mjernih postaja u listi po njihovom nazivu.
	 * 
	 * @return vraca sortiranu listu mjernih postaja
	 */
	public List<T> getSorted(){
		listaDrzava.sort((p1,p2)->p1.getNaziv().compareTo(p2.getNaziv()));
		return listaDrzava;
	}

	/**
	 * Dodaje objekte mjernih postaja u listu mjernih postaja.
	 * 
	 * @param postaja objekt kojeg dodajemo u listu
	 */
	public void add(T drzava) {
		listaDrzava.add(drzava);
		
	}

}
