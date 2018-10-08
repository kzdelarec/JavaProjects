package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public abstract class Senzor {

	private String mjernaJedinica;
	private byte preciznost;
	private BigDecimal vrijednost;

	protected Senzor(String mjernaJedinica, byte preciznost) {
		super();
		this.mjernaJedinica = mjernaJedinica;
		this.preciznost = preciznost;
	}
	
	abstract String dohvatiVrijednost();

	public String getMjernaJedinica() {
		return mjernaJedinica;
	}

	public void setMjernaJedinica(String mjernaJedinica) {
		this.mjernaJedinica = mjernaJedinica;
	}

	public byte getPreciznost() {
		return preciznost;
	}

	public void setPreciznost(byte preciznost) {
		this.preciznost = preciznost;
	}

	public BigDecimal getVrijednost() {
		return vrijednost;
	}

	public void setVrijednost(BigDecimal vrijednost) {
		this.vrijednost = vrijednost;
	}

}
