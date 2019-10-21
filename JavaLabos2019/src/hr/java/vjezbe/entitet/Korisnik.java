package hr.java.vjezbe.entitet;

public class Korisnik {

	private String ime;
	private String prezime;
	private String email;
	private String telefon;
	
	public Korisnik(String ime, String prezime, String email, String telefon) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.telefon = telefon;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	
}
