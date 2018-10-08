package hr.java.vjezbe.entitet;

public class Mjesto {
	
	private String naziv;
	private Zupanija zupanija;
	
	public Mjesto(String naziv, Zupanija zupanija) {
		this.naziv = naziv;
		this.zupanija = zupanija;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Zupanija getZupanija() {
		return zupanija;
	}

	public void setZupanija(Zupanija zupanija) {
		this.zupanija = zupanija;
	}
	

}
