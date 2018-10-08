package hr.knjiznica.entiteti;

public class Knjiznicar extends Osoba {

	private String korisnickoIme;
	private String lozinka;
	private Boolean isAdmin;
	private Boolean isAktivan;

	public Knjiznicar(String ime, String prezime, String mailAdresa, Integer brojMobitela, String korisnickoIme,
			String lozinka, Boolean isAdmin, Boolean isAktivan) {
		
		super(ime, prezime, mailAdresa, brojMobitela);
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.isAdmin = isAdmin;
		this.isAktivan = isAktivan;
	}
	

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	public Boolean getIsAktivan() {
		return isAktivan;
	}


	public void setIsAktivan(Boolean isAktivan) {
		this.isAktivan = isAktivan;
	}
	
	

}
