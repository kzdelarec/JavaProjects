package hr.java.vjezbe.entitet;

import java.util.Arrays;


public class MjernaPostaja{

	private String naziv;
	private Mjesto mjesto;
	private GeografskaTocka geografskaTocka;
	private Senzor[] sviSenzori;

	public MjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka, Senzor[] sviSenzori) {
		super();
		this.naziv = naziv;
		this.mjesto = mjesto;
		this.geografskaTocka = geografskaTocka;
		this.sviSenzori = sviSenzori;
	}

	public String dohvatiSenzore(int i) {
		Arrays.sort(sviSenzori, (p1, p2) -> p1.getMjernaJedinica().compareTo(p2.getMjernaJedinica()));
		return sviSenzori[i].dohvatiVrijednost();
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Mjesto getMjesto() {
		return mjesto;
	}

	public void setMjesto(Mjesto mjesto) {
		this.mjesto = mjesto;
	}

	public GeografskaTocka getGeografskaTocka() {
		return geografskaTocka;
	}

	public void setGeografskaTocka(GeografskaTocka geografskaTocka) {
		this.geografskaTocka = geografskaTocka;
	}

	public Senzor[] getSviSenzori() {
		return sviSenzori;
	}

	public void setSviSenzori(Senzor[] sviSenzori) {
		this.sviSenzori = sviSenzori;
	}

	

}
