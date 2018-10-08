package hr.java.vjezbe.entitet;

/**
 * Predstavlja entitet senzora za mjerenje tlaka zraka. Klasa nasljeduje klasu Senzor.
 * 
 * @author Kristijan Zdelarec
 * @see hr.java.vjezbe.entitet.Senzor
 */
public class SenzorTlakaZraka extends Senzor {
	String opis;

	/**
	 * Inicijalizira podatake o mjernoj jedinici, preciznosti i opisnoj vrijednosti za senzor.
	 * 
	 * @param mjernaJedinica podatak o jedinici u kojoj se mjeri vrijednost tlaka zraka
	 * @param preciznost podatak o odstupanju senzora
	 * @param opis podatak o tlaku zraka
	 */
	public SenzorTlakaZraka(String mjernaJedinica, byte preciznost, String opis) {
		super(mjernaJedinica, preciznost);
		this.opis = opis;

	}

	
	/* (non-Javadoc)
	 * @see hr.java.vjezbe.entitet.Senzor#dohvatiVrijednost()
	 */
	@Override
	String dohvatiVrijednost() {

		return "Velicina: " + opis + ", vrijednost: " + getVrijednost() + " " + getMjernaJedinica()+ ", Nacin rada: "+getRadSenzora();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return dohvatiVrijednost();
	}
}
