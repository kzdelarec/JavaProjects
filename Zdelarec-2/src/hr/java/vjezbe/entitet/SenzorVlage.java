package hr.java.vjezbe.entitet;

public class SenzorVlage extends Senzor {

	public SenzorVlage(String mjernaJedinica, byte preciznost) {
		super(mjernaJedinica, preciznost);

	}

	@Override
	String dohvatiVrijednost() {

		return "Vrijednost senzora: "+getVrijednost() +" "+getMjernaJedinica() + " vlage zraka";
	}

}
