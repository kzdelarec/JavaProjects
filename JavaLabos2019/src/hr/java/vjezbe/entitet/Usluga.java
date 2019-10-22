package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class Usluga extends Artikl {

	public Usluga(String naslov, String opis, BigDecimal cijena) {
		super(naslov, opis, cijena);
	}

	@Override
	public String tekstOglasa() {
		return "Naslov usluge: " + getNaslov() + "\n"
				+ "Opis usluge: " + getOpis() + "\n"
				+ "Cijena usluge: " + getCijena();
	}

}
