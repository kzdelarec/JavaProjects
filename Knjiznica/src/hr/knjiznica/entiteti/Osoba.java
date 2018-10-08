package hr.knjiznica.entiteti;

public class Osoba {
	
	private String ime;
	private String prezime;
	private String mailAdresa;
	private Integer brojMobitela;
	private Integer ID;
	
	public Osoba(String ime, String prezime, String mailAdresa, Integer brojMobitela) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.mailAdresa = mailAdresa;
		this.brojMobitela = brojMobitela;
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
	public String getMailAdresa() {
		return mailAdresa;
	}
	public void setMailAdresa(String mailAdresa) {
		this.mailAdresa = mailAdresa;
	}
	public Integer getBrojMobitela() {
		return brojMobitela;
	}
	public void setBrojMobitela(Integer brojMobitela) {
		this.brojMobitela = brojMobitela;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

}
