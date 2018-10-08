package hr.java.vjezbe.entitet;

public class SenzorTlakaZraka extends Senzor {
	String opis;

	public SenzorTlakaZraka(String mjernaJedinica, byte preciznost, String opis) {
		super(mjernaJedinica, preciznost);
		this.opis = opis;

	}

	@Override
	String dohvatiVrijednost() {

		return "Velicina: " + opis + ", vrijednost: " + getVrijednost() + " " + getMjernaJedinica();
	}

}
