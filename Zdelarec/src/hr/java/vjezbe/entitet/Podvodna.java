package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public interface Podvodna {
	void podesiX(BigDecimal x);

	void podesiY(BigDecimal y);

	BigDecimal dohvatiX();

	BigDecimal dohvatiY();

	BigDecimal najmanjiX = new BigDecimal(-180);
	BigDecimal najveciX = new BigDecimal(180);
	BigDecimal najmanjiY = new BigDecimal(-90);
	BigDecimal najveciY = new BigDecimal(90);

	private void provjeriManjiX(BigDecimal x) {
		if (x.compareTo(najmanjiX) < 0)
			x = najmanjiX;
	}

	private void provjeriVeciX(BigDecimal x) {
		if (x.compareTo(najveciX) > 0)
			x = najveciX;
	}

	private void provjeriManjiY(BigDecimal y) {
		if (y.compareTo(najmanjiY) < 0)
			y = najmanjiY;
	}

	private void provjeriVeciY(BigDecimal y) {
		if (y.compareTo(najveciY) > 0)
			y = najveciY;
	}

	default BigDecimal povecajX(BigDecimal x) {
		BigDecimal povecanje = new BigDecimal(1);
		x.add(povecanje);
		provjeriManjiX(x);
		provjeriVeciX(x);
		return x;
	}

	default BigDecimal povecajY(BigDecimal y) {
		BigDecimal povecanje = new BigDecimal(1);
		y.add(povecanje);
		provjeriManjiY(y);
		provjeriVeciY(y);
		return y;
	}

	default BigDecimal smanjiX(BigDecimal x) {
		BigDecimal smanjivanje = new BigDecimal(-1);
		x.add(smanjivanje);
		provjeriManjiX(x);
		provjeriVeciX(x);
		return x;
	}

	default BigDecimal smanjiY(BigDecimal y) {
		BigDecimal smanjivanje = new BigDecimal(-1);
		y.add(smanjivanje);
		provjeriManjiY(y);
		provjeriVeciY(y);
		return y;
	}

}
