package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class Automobil extends Artikl implements Vozilo{
	
	private BigDecimal snagaKs;

	public Automobil(String naslov, String opis, BigDecimal cijena, BigDecimal snagaKs) {
		super(naslov, opis, cijena);
		this.snagaKs = snagaKs;
	}

	@Override
	public String tekstOglasa() {
		return "Naslov automobila: " + getNaslov() + "\n"
				+ "Opis automobila: " + getOpis() + "\n"
				+ "Snaga automobila: " + izracunajKw(snagaKs) + "\n"
				+ "Izračun osiguranja automobila: " + izracunajCijenuOsiguranja() + "\n"
				+ "Cijena automobila: " + getCijena();
	}

	@Override
	public BigDecimal izracunajGrupuOsiguranja() {
		if (getSnagaKs().doubleValue() <= 100d) {
			return new BigDecimal(1);
		} else if (getSnagaKs().doubleValue() > 100d && getSnagaKs().doubleValue() <= 200d) {
			return new BigDecimal(2);
		} else if (getSnagaKs().doubleValue() > 200d && getSnagaKs().doubleValue() <= 300d) {
			return new BigDecimal(3);
		} else if (getSnagaKs().doubleValue() > 300d && getSnagaKs().doubleValue() <= 400d) {
			return new BigDecimal(4);
		} else {
			return new BigDecimal(5);
		}
	}

	public BigDecimal getSnagaKs() {
		return snagaKs;
	}

	public void setSnagaKs(BigDecimal snagaKs) {
		this.snagaKs = snagaKs;
	}
	
	

}
