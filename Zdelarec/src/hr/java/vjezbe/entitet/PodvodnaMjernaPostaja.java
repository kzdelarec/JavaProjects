package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class PodvodnaMjernaPostaja extends MjernaPostaja implements Podvodna {

	public PodvodnaMjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka, Senzor[] sviSenzori) {
		super(naziv, mjesto, geografskaTocka, sviSenzori);
		// TODO Auto-generated constructor stub
	}
	
	BigDecimal x;
	BigDecimal y;

	@Override
	public void podesiX(BigDecimal x) {
		x=getGeografskaTocka().getX();
	}

	@Override
	public void podesiY(BigDecimal y) {
		y=getGeografskaTocka().getY();
	}

	@Override
	public BigDecimal dohvatiX() {
		return x;
	}

	@Override
	public BigDecimal dohvatiY() {
		return y;
	}

}
