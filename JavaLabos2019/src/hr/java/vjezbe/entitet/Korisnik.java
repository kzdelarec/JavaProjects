package hr.java.vjezbe.entitet;

public abstract class Korisnik {

	private String email;
	private String telefon;
	
	public Korisnik(String email, String telefon) {
		super();
		this.email = email;
		this.telefon = telefon;
	}
	
	public abstract String dohvatiKontakt();
	
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
