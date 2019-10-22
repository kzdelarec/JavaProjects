package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public interface Vozilo {

	default BigDecimal izracunajKw(BigDecimal ks) {
		BigDecimal koef = new BigDecimal(0.746);
		return ks.multiply(koef);
	}
	
	public BigDecimal izracunajGrupuOsiguranja();
	
	default BigDecimal izracunajCijenuOsiguranja() {
		switch (Integer.parseInt(izracunajGrupuOsiguranja().toString())) {
		case 1:
			return new BigDecimal(100);
		case 2:
			return new BigDecimal(200);
		case 3:
			return new BigDecimal(300);
		case 4:
			return new BigDecimal(400);
		case 5:
			return new BigDecimal(500);

		default:
			return new BigDecimal(100);
		}

	}
	
}
