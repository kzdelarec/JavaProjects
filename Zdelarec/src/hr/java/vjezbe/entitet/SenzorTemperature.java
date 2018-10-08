package hr.java.vjezbe.entitet;

public class SenzorTemperature extends Senzor {

	private String nazivKomponente;

	public SenzorTemperature(String mjernaJedinica, byte preciznost, String nazivKomponente) {
		super(mjernaJedinica, preciznost);
		this.nazivKomponente = nazivKomponente;

	}

	public String getNazivKomponente() {
		return nazivKomponente;
	}

	public void setNazivKomponente(String nazivKomponente) {
		this.nazivKomponente = nazivKomponente;
	}

	@Override
	String dohvatiVrijednost() {

		return "Komponenta: " + nazivKomponente + ", vrijednost: " + getVrijednost() + " " + getMjernaJedinica();
	}
}