package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
/**
 * Predstavlja zajednicka svojsta svih artikala koja nastanu nasljedivanjem ove klase
 * 
 * @author Patricija Ku�e
 *
 */
public abstract class Artikl extends Entitet{
	private String naslov;
	private String opis;
	private BigDecimal cijena;
	private Long id;
	Stanje stanje;
	
	/**
	 * Inicijalizira naslov, opis i cijenu trazenog artikla
	 * 
	 * @param naslov vraca naslov artikla
	 * @param opis vraca opis artikla
	 * @param cijena vraca cijenu artikla
	 * @param stanje vraca stanje artikla
	 */
	public Artikl(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje) {
		super(id);
		this.naslov = naslov;
		this.opis = opis;
		this.cijena = cijena;
		this.stanje = stanje;
		this.id = id;
	}
	

	/**Poziva apstraktnu metodu tekstOglasa koji ispisuje podatke o artiklu
	 * 
	 * @return
	 */
	public abstract String tekstOglasa();
	
	/**
	 * Dohvaca naslov artikla
	 * @return naslov vraca naslov
	 */
	public String getNaslov() {
		return naslov;
	}
	
	/**
	 * Postavlja naslov artikla
	 * @param naslov vraca naslov artikla
	 */
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}
	
	/**
	 * Dohvaca opis artikla
	 * @return opis vraca opis
	 */
	public String getOpis() {
		return opis;
	}
	
	/**
	 * Postavlja opis artikla
	 * @param opis vraca opis
	 */
	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	/**
	 * Dohvaca cijenu artikla
	 * @return cijena vraca cijenu
	 */
	public BigDecimal getCijena() {
		return cijena;
	}
	
	/**
	 * Postavlja cijenu artikla 
	 * @param cijena vraca cijenu artikla
	 */
	public void setCijena(BigDecimal cijena) {
		this.cijena = cijena;
	}

	/**
	 * Dohvaca stanje artikla
	 * @return vraca stanje artikla
	 */
	public Stanje getStanje() {
		return stanje;
	}

	/**
	 * Postavlja stanje artikla
	 * @param stanje vraca stanje artikla
	 */
	public void setStanje(Stanje stanje) {
		this.stanje = stanje;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
