package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Predstavlja zajednicka svojstva svih senzora koji nastanu nasljedivanjem ove klase.
 * 
 * @author Kristijan Zdelarec
 *
 */
public abstract class Senzor extends BazniEntitet{

	private String mjernaJedinica;
	private Byte preciznost;
	private BigDecimal vrijednost;
	private RadSenzora radSenzora;
	/**
	 * Inicijalizira podatke o jedinici mjerenja i preciznosti mjerenja senzora.
	 * 
	 * @param mjernaJedinica mjerna jedinica senzora
	 * @param preciznost prosjecno odstupanje senzora
	 */
	protected Senzor(String mjernaJedinica, byte preciznost) {
		this.mjernaJedinica = mjernaJedinica;
		this.preciznost = preciznost;
	}
	protected Senzor(String mjernaJedinica, byte preciznost, Integer id) {
		super(id);
		this.mjernaJedinica = mjernaJedinica;
		this.preciznost = preciznost;
	}
	
	/**
	 * Dohvaca vrijednosti senzora.
	 * 
	 * @return vraca podatke o vrijednostima senzora
	 */
	abstract String dohvatiVrijednost();

	/**
	 * Dohvaca mjernu jedinicu senzora.
	 * 
	 * @return vraca podatak o mjernoj jedinici senzora
	 */
	public String getMjernaJedinica() {
		return mjernaJedinica;
	}

	/**
	 * Postavlja podatak o mjernoj jedinici senzora.
	 * 
	 * @param mjernaJedinica podatak o mjernoj jedinici senzora
	 */
	public void setMjernaJedinica(String mjernaJedinica) {
		this.mjernaJedinica = mjernaJedinica;
	}

	/**
	 * Dohvac podatak o prosjecnom odstupanju senzora.
	 * 
	 * @return vraca podaak o prosjecnom odstupanju senzora
	 */
	public byte getPreciznost() {
		return (Byte)preciznost;
	}

	/**
	 * Postavlja vrijednos preciznosti senzora.
	 * 
	 * @param preciznost podatak o prosjecnom odstupanju senzora
	 */
	public void setPreciznost(byte preciznost) {
		this.preciznost = preciznost;
	}

	/**
	 * Dohvaca vrijednost mjerenja.
	 * 
	 * @return vraca izmjereni podatak
	 */
	public BigDecimal getVrijednost() {
		return vrijednost;
	}

	/**
	 * Postavlja vrijednost mjerenja senzora.
	 * 
	 * @param vrijednost izmjerena vrijednost senzora
	 */
	public void setVrijednost(BigDecimal vrijednost) {
		this.vrijednost = vrijednost;
	}

	/**
	 * Dohvaca podatak o nacinu rada senzora.
	 * 
	 * @return vraca podatak o vrsti mjesta
	 */
	public RadSenzora getRadSenzora() {
		return radSenzora;
	}

	/**
	 * Postavlja podatak o vrsti mjesta.
	 * 
	 * @param radSenzora podatak o nacinu rada senzora
	 */
	public void setRadSenzora(RadSenzora radSenzora) {
		this.radSenzora = radSenzora;
	}
	
	public String stringPreciznost() {
		String tmp = Byte.toString(getPreciznost());
		return tmp;
	}
	

}
