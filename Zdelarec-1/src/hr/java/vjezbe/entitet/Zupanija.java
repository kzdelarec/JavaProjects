package hr.java.vjezbe.entitet;

public class Zupanija {

	private String naziv;
	private Drzava drzava;
	
	public Zupanija(String naziv, Drzava drzava) {
		this.naziv = naziv;
		this.drzava = drzava;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Drzava getDrzava() {
		return drzava;
	}

	public void setDrzava(Drzava drzava) {
		this.drzava = drzava;
	}
	
	
	
	
}
