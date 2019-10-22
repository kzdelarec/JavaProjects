package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public abstract class Artikl {

	private String naslov;
	private String opis;
	private BigDecimal cijena;
	
	public Artikl(String naslov, String opis, BigDecimal cijena) {
		super();
		this.naslov = naslov;
		this.opis = opis;
		this.cijena = cijena;
	}

	public abstract String tekstOglasa();
	
	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public BigDecimal getCijena() {
		return cijena;
	}

	public void setCijena(BigDecimal cijena) {
		this.cijena = cijena;
	}
	
}


