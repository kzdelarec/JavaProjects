package hr.java.vjezbe.entitet;

public class PoslovniKorisnik extends Korisnik {

	private String naziv;
	private String web;
	
	public PoslovniKorisnik(String naziv, String web, String email, String telefon) {
		super(email, telefon);
		this.naziv = naziv;
		this.web = web;
	}
	
	@Override
	public String dohvatiKontakt() {
		return "Naziv tvrtke: " + getNaziv() + ", web: " + getWeb() + ", mail: " + getEmail() + ", tel: " + getTelefon();
	}
	
	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getWeb() {
		return web;
	}

	public void setWebn(String web) {
		this.web = web;
	}


}
