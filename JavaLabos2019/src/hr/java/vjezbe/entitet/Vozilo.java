package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

/**
 * Pretvara konjske snage u kilovate te ovisno o snazi automobila vraca grupu osiguranja
 * 
 * @author Patricija Kuže
 *
 */
public interface Vozilo {
	default BigDecimal izracunajKw(BigDecimal kS) {
		
		BigDecimal koef = new BigDecimal(0.746);
		BigDecimal kW = kS.multiply(koef);
		
		return kW;
	}
	
	/**
	 * Predstavlja odabir grupa po switch caseovima ovisno o snazi automobila
	 * 
	 * @return vraca podatak o grupi osiguranja ovisno o tome kolika je snaga automobila unesena
	 * @throws NemoguceOdreditiGrupuOsiguranjaException 
	 */
	public BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException;
	
	/**
	 * Racuna grupu osiguranja ovisno o snagi automobila
	 * @return vraca snagu
	 * @throws NemoguceOdreditiGrupuOsiguranjaException
	 */
	default BigDecimal izracunajCijenuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException {
		/*BigDecimal izracun = switch(izracunajGrupuOsiguranja().toString()) {
		case "1" -> new BigDecimal(100);
		case "2" -> new BigDecimal(200);
		case "3" -> new BigDecimal(300);
		case "4" -> new BigDecimal(400);
		case "5" -> new BigDecimal(500);
		default -> new BigDecimal(100);
		};
		return izracun;*/
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


