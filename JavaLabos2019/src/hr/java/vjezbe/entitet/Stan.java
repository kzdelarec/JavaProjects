package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.iznimke.CijenaJePreniskaException;

/**
 * Klasa Stan nasljeduje klasu Artikl i implementira sucelje Nekretnine
 * 
 * @author Patricija Ku�e
 *
 */
public class Stan extends Artikl implements Nekretnina{
	private static final Logger logger = LoggerFactory.getLogger(Stan.class);
	private int kvadratura;
	private Long id;

	public Stan(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje, int kvadratura) {
		super(id, naslov, opis, cijena, stanje);
		this.kvadratura = kvadratura;
		this.id = id;
	}

	public int getKvadratura() {
		return kvadratura;
	}

	public void setKvadratura(int kvadratura) {
		this.kvadratura = kvadratura;
	}
	
	
	
	/**
	 * Vraca tekst oglasa
	 */
	@Override
	public String tekstOglasa() {
		BigDecimal cijena = null;
		
		
		String oglas = "Naslov nekretnine"
				+ ": " + getNaslov() + "\n"
				+ "Opis nekretnine: " + getOpis() + "\n"
				+ "Kvadratura nekretnine: " + kvadratura + "\n"
				+ "Porez na nekretnine: ";
		
		try {
			cijena = izracunajPorez(getCijena());
			oglas += cijena;
		} catch (CijenaJePreniskaException e) {
			logger.error("Cijena mora biti veca od 10000.", e);
			oglas += "Cijena mora biti veca od 10000.";
		}
		
		oglas += "\nCijena nekretnine: " + getCijena();
		System.out.println("Stanje nekretnine: " + getStanje());
				 
		return oglas;
		
		
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}

