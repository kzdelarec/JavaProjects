package hr.knjiznica.entiteti;

public class IzdanaKnjiga {

	String datumPosudbe;
	String datumPovratka;
	Knjiznicar knjiznicar;
	Korisnik korisnik;
	Knjiga knjiga;
	Integer ID;
	
	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public IzdanaKnjiga(Korisnik korisnik, Knjiga knjiga, String datumPosudbe, String datumPovratka, Knjiznicar knjiznicar) {
		this.datumPosudbe = datumPosudbe;
		this.datumPovratka = datumPovratka;
		this.knjiznicar = knjiznicar;
		this.korisnik = korisnik;
		this.knjiga = knjiga;
	}

	public String getDatumPosudbe() {
		return datumPosudbe;
	}

	public void setDatumPosudbe(String datumPosudbe) {
		this.datumPosudbe = datumPosudbe;
	}

	public String getDatumPovratka() {
		return datumPovratka;
	}

	public void setDatumPovratka(String datumPovratka) {
		this.datumPovratka = datumPovratka;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public Knjiga getKnjiga() {
		return knjiga;
	}

	public void setKnjiga(Knjiga knjiga) {
		this.knjiga = knjiga;
	}

	public Knjiznicar getKnjiznicar() {
		return knjiznicar;
	}

	public void setKnjiznicar(Knjiznicar knjiznicar) {
		this.knjiznicar = knjiznicar;
	}

}
