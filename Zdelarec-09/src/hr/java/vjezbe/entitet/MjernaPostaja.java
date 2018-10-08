package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * Predstavlja entitet mjerne postaje. Definirana je mjestom u kojem se nalazi,
 * geografskim tockama(koordinate) i senzorima za mjerenje.
 * 
 * @author Kristijan Zdelarec
 * @see hr.java.vjezbe.entitet.Mjesto
 * @see hr.java.vjezbe.entitet.GeografskaTocka
 * @see hr.java.vjezbe.entitet.Senzor
 *
 */
public class MjernaPostaja extends BazniEntitet {

	private Integer id;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String naziv;
	private Mjesto mjesto;
	private GeografskaTocka geografskaTocka;
	private List<Senzor> sviSenzori = new ArrayList<>();

	/**
	 * Inicijalizira podatke o nazivu mjerne postaje, o mjestu u kojem se nalazi,
	 * njene koordinate i senzore za mjerenje koje sadrzi.
	 * 
	 * @param naziv
	 *            podatak o nazivu mjerne postaje
	 * @param mjesto
	 *            podatak o mjestu u kojem se nalazi mjerna postaja
	 * @param geografskaTocka
	 *            podatak o koordinatama mjerne postaje
	 * @param sviSenzori
	 *            lista senzora koje sadrzi mjerna postaja
	 */
	public MjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka, List<Senzor> sviSenzori) {
		this.naziv = naziv;
		this.mjesto = mjesto;
		this.geografskaTocka = geografskaTocka;
		this.sviSenzori = sviSenzori;
	}
	
	
	
	
	public MjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka, List<Senzor> sviSenzori, Integer id) {
		super(id);
		this.naziv = naziv;
		this.mjesto = mjesto;
		this.geografskaTocka = geografskaTocka;
		this.sviSenzori = sviSenzori;
	}

	//privremeni konstruktor
	public MjernaPostaja(Integer id, String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka) {
		this.naziv = naziv;
		this.mjesto = mjesto;
		this.geografskaTocka = geografskaTocka;
	}

	/**
	 * Dohvaca vrijednosti senzora.
	 * 
	 * @param i
	 *            iterator, predstavlja redni broj senzora iz polja
	 * @return vraca vrijednosti senzora u obliku stringa
	 */
	public void dohvatiSenzore(List<Senzor> listaSenzora) {
		Function<Senzor, String> mjernaJedinica = Senzor::getMjernaJedinica;
		Comparator<Senzor> poMjernojJedinici = Comparator.comparing(mjernaJedinica);
		listaSenzora.stream().sorted(poMjernojJedinici).forEach(System.out::println);
	}

	/**
	 * Dohvaca naziv mjerne postaje.
	 * 
	 * @return vraca podatak o nazivu mjerne postaje
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Postavlja naziv mjerne postaje.
	 * 
	 * @param naziv
	 *            podatak o nazivu mjerne postaje.
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	/**
	 * Dohvaca mjesto u kojem se nalazi mjerna postaja.
	 * 
	 * @return vraca podatak o mjestu u kojem se nalazi mjerna postaja
	 */
	public Mjesto getMjesto() {
		return mjesto;
	}

	/**
	 * Potavlja podatke o mjestu u kojem se nalazi mjerna postaja.
	 * 
	 * @param mjesto
	 *            podatak o mjestu u kojem se nalazi mjerna postaja
	 */
	public void setMjesto(Mjesto mjesto) {
		this.mjesto = mjesto;
	}

	/**
	 * Dohvaca koordinate mjerne postaje.
	 * 
	 * @return vraca koordinate mjerne postaje
	 */
	public GeografskaTocka getGeografskaTocka() {
		return geografskaTocka;
	}

	/**
	 * Postavlja koordinate mjerne postaje.
	 * 
	 * @param geografskaTocka
	 *            podatak o koordinatama mjerne postaje
	 */
	public void setGeografskaTocka(GeografskaTocka geografskaTocka) {
		this.geografskaTocka = geografskaTocka;
	}

	/**
	 * Dohvaca senzore iz liste.
	 * 
	 * @return vraca sve senzore koji se nalaze u listi
	 */
	public List<Senzor> getSviSenzori() {
		return sviSenzori;
	}

	/**
	 * Postavja senzore u listu.
	 * 
	 * @param sviSenzori svi senzori neke mjerne postaje
	 */
	public void setSviSenzori(List<Senzor> sviSenzori) {
		this.sviSenzori = sviSenzori;
	}

	/**
	 * Dohvaca objekt klase MernaPostaja.
	 * 
	 * @return vraca objekt klase MjernaPostaja
	 */
	public MjernaPostaja getPostaje() {
		return this;
	}

}
