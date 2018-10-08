package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Predstavlja entitet geografske tocke koja je definirana x i y koordinatom.
 * 
 * @author Kristijan Zdelarec
 *
 */
public class GeografskaTocka extends BazniEntitet {

	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	private BigDecimal x;
	private BigDecimal y;

	/**
	 * Inicijalizira podatak o x i y koordinati.
	 * 
	 * @param x podatak o x koordinati
	 * @param y podatak o y koordinati
	 */
	public GeografskaTocka(BigDecimal x, BigDecimal y) {
		this.x = x;
		this.y = y;
	}
	public GeografskaTocka(BigDecimal x, BigDecimal y, Integer id) {
		super(id);
		this.x = x;
		this.y = y;
	}

	/**
	 * Vraca x koordinatu entiteta geografska tocka.
	 * 
	 * @return vraca podatak o x koordinati geografske tocke
	 */
	public BigDecimal getX() {
		return x;
	}

	/**
	 * Postavlja vrijednost x koordinate.
	 * 
	 * @param x podatak o x koordinati
	 */
	public void setX(BigDecimal x) {
		this.x = x;
	}

	/**
	 * Vraca y koordinatu entiteta geografska tocka.
	 * 
	 * @return vraca podatak o x koordinati geografske tocke
	 */
	public BigDecimal getY() {
		return y;
	}

	/**
	 * Postavlja vrijednost y koordinate.
	 * 
	 * @param y podatak o x koordinati
	 */
	public void setY(BigDecimal y) {
		this.y = y;
	}

}
