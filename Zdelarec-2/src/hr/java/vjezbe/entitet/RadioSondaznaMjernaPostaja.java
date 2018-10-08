package hr.java.vjezbe.entitet;

public class RadioSondaznaMjernaPostaja extends MjernaPostaja implements RadioSondaza {

	int visina;
	

	public RadioSondaznaMjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka,
			Senzor[] sviSenzori, int visina) {
		super(naziv, mjesto, geografskaTocka, sviSenzori);
		this.visina = visina;
	}

	@Override
	public void podesiVisinuPostaje(int visinaPostaje) {
		visina = visinaPostaje;

	}

	@Override
	public int dohvatiVisinuPostaje() {
		return visina;

	}
	

}
