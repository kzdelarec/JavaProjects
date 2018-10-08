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
public class MjernePostaje<T extends MjernaPostaja> {
	
	private List<T> listaMjernihPostaja = new ArrayList<>();

	/**
	 * Stvara objekt klase MjernePostaje.
	 * 
	 */
	public MjernePostaje() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Dohvaca listu u kojoj su spremjljeni objekti mjernih postaja.
	 * 
	 * @return vraca listu objekata mjerne postaje
	 */
	public List<T> getListaMjernihPostaja() {
		return listaMjernihPostaja;
	}

	/**
	 * Postavlja vrijednosti liste mjernih postaja.
	 * 
	 * @param listaMjernihPostaja lista svih mjernih postaja
	 */
	public void setListaMjernihPostaja(List<T> listaMjernihPostaja) {
		this.listaMjernihPostaja = listaMjernihPostaja;
	}
	
	/**
	 * Dohvaca objekt iz liste mjernih postaja koji ima trazeni index.
	 * 
	 * @param i index objekta kojeg trazimo
	 * @return vraca objekt pod trazenim indexom
	 */
	public MjernaPostaja get(Integer i) {
		return listaMjernihPostaja.get(i);
	}
	
	/**
	 * Sortira objekte mjernih postaja u listi po njihovom nazivu.
	 * 
	 * @return vraca sortiranu listu mjernih postaja
	 */
	public List<T> getSorted(){
		listaMjernihPostaja.sort((p1,p2)->p1.getNaziv().compareTo(p2.getNaziv()));
		return listaMjernihPostaja;
	}

	/**
	 * Dodaje objekte mjernih postaja u listu mjernih postaja.
	 * 
	 * @param postaja objekt kojeg dodajemo u listu
	 */
	public void add(T postaja) {
		listaMjernihPostaja.add(postaja);
		
	}

}
