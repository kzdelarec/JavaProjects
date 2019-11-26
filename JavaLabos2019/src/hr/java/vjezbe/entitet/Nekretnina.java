package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import hr.java.vjezbe.iznimke.CijenaJePreniskaException;

/**
 * Sucelje nekretnina
 * 
 * @author Patricija Kuže
 *
 */
public interface Nekretnina {
	
	/**
	 * Racuna porez na unesenu cijenu
	 * @param cijenaNekretnine
	 * @return porez
	 */
	default BigDecimal izracunajPorez(BigDecimal cijenaNekretnine){
		
		if(cijenaNekretnine.doubleValue() < 10000d) {
			throw new CijenaJePreniskaException("Cijena ne smije biti manja od 10000.");
		} else {
			
			BigDecimal koefPoreza = new BigDecimal(0.03);
			BigDecimal porez = cijenaNekretnine.multiply(koefPoreza);
			
			return porez;
		}
		
	}
}
