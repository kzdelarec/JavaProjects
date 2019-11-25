package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
/**
 * Predstavlja klasu Usluga koja nasljeduje klasu Artikl
 * 
 * @author Patricija Ku�e
 *
 */
public class Usluga extends Artikl{

	private Long id;
	
	/**
	 * Inicijalizira podatke o naslovu, opisu i cijeni usluge
	 * 
	 * @param naslov podatak o naslovu artikla
	 * @param opis podatak o opisu artikla
	 * @param cijena podatak o cijeni artikla
	 */
	public Usluga(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje) {
		super(id, naslov, opis, cijena, stanje);
		this.id = id;
	}
	
	/**
	 * Ispisuje i vraca naslov, opis i cijenu usluge
	 */
	@Override
	public String tekstOglasa() {
		return "Naslov usluge: " + getNaslov() + "\n"
				+ "Opis usluge: " + getOpis() + "\n"
				+ "Cijena usluge: " + getCijena() + "\n"
				+ "Stanje usluge: " + getStanje();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
