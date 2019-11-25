package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

/**
 * Predstavlja zajednicka svojstva svih automobila koji nastanu nasljedivanjem ove klase
 * 
 * @author Patricija Ku�e
 *
 */
public class Automobil extends Artikl implements Vozilo{
	
	private static final Logger logger = LoggerFactory.getLogger(Automobil.class);
	private BigDecimal snagaKs;
	private Long id;
	/**
	 * Inicijalizira podatke o naslovu, opisu, cijeni i snagi u kilovatima automobila
	 * @param naslov
	 * @param opis
	 * @param cijena
	 * @param snagaKs
	 */
	public Automobil(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje , BigDecimal snagaKs) {
		super(id, naslov, opis, cijena, stanje);
		this.snagaKs = snagaKs;
		this.id = id;
	}

	public BigDecimal getSnagaKs() {
		return snagaKs;
	}

	public void setSnagaKs(BigDecimal snagaKs) {
		this.snagaKs = snagaKs;
	}
	
	/**
	 * Funkcija koja racuna grupu osiguranja ovisno o jacini automobila
	 * @return vraca BigDecimal grupe kojoj pripada automobil
	 * @throws NemoguceOdreditiGrupuOsiguranjaException 
	 */
	@Override
	public BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException {
		if (getSnagaKs().doubleValue() <= 100d) {
			return new BigDecimal(1);
		} else if (getSnagaKs().doubleValue() > 100d && getSnagaKs().doubleValue() <= 200d) {
			return new BigDecimal(2);
		} else if (getSnagaKs().doubleValue() > 200d && getSnagaKs().doubleValue() <= 300d) {
			return new BigDecimal(3);
		} else if (getSnagaKs().doubleValue() > 300d && getSnagaKs().doubleValue() <= 400d) {
			return new BigDecimal(4);
		} else {
			throw new NemoguceOdreditiGrupuOsiguranjaException("Previse kw, ne mogu odrediti grupu osiguranja.");
		}
	}
	
	/**
	 * Vraca tekst oglasa, tj. naslov, opis, snagu, osiguranje i cijenu unesenog automobila
	 */
	@Override
	public String tekstOglasa() {
		BigDecimal cijena = null;
		String oglas = "Naslov automobila: " + getNaslov() + "\n" 
				+ "Opis automobila: " + getOpis() + "\n" 
				+ "Snaga automobila: " + getSnagaKs() + "\n" 
				+ "Izra�un osiguranja automobila: ";
				
		try {
			cijena = izracunajCijenuOsiguranja();
			oglas += cijena;
		} catch (NemoguceOdreditiGrupuOsiguranjaException e) {
			logger.error("Previse kw, ne mogu odredit5i grupu osiguranja", e);
			oglas += "Previse kw, ne mogu odredit5i grupu osiguranja";
		}
		oglas += "\nCijena automobila: " + getCijena();
		System.out.println("Stanje automobila: " + getStanje());
		
		return oglas;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
