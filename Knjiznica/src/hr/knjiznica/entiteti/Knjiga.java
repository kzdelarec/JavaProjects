package hr.knjiznica.entiteti;

public class Knjiga {
	
	private String naslov;
	private String autor;
	private String kategorija;
	private Integer izdanje;
	private String izdavac;
	private Integer godinaIzdanja;
	private Integer ID;
	private Long barcodeId;
	private Integer kolicina;
	
	
	
	public Knjiga(Long barcodeId, String naslov, String autor, String kategorija, Integer izdanje, String izdavac,
			Integer godinaIzdanja, Integer kolicina) {
		super();
		this.naslov = naslov;
		this.autor = autor;
		this.kategorija = kategorija;
		this.izdanje = izdanje;
		this.izdavac = izdavac;
		this.godinaIzdanja = godinaIzdanja;
		this.kolicina = kolicina;
		this.barcodeId = barcodeId;
	}
	public String getNaslov() {
		return naslov;
	}
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getKategorija() {
		return kategorija;
	}
	public void setKategorija(String kategorija) {
		this.kategorija = kategorija;
	}
	public Integer getIzdanje() {
		return izdanje;
	}
	public void setIzdanje(Integer izdanje) {
		this.izdanje = izdanje;
	}
	public String getIzdavac() {
		return izdavac;
	}
	public void setIzdavac(String izdavac) {
		this.izdavac = izdavac;
	}
	public Integer getGodinaIzdanja() {
		return godinaIzdanja;
	}
	public void setGodinaIzdanja(Integer godinaIzdanja) {
		this.godinaIzdanja = godinaIzdanja;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Integer getKolicina() {
		return kolicina;
	}
	public void setKolicina(Integer kolicina) {
		this.kolicina = kolicina;
	}
	public Long getBarcodeId() {
		return barcodeId;
	}
	public void setBarcodeId(Long barcodeId) {
		this.barcodeId = barcodeId;
	}

}
