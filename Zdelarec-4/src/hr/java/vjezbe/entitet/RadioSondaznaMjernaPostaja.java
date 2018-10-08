package hr.java.vjezbe.entitet;

import java.util.List;

/**
 * Predstavlja entitet radiosondazne mjerne postaje koja nasljeduje klasu MjernaPostaja i implementira sucenje RadioSondaza.
 * 
 * @author Kristijan Zdelarec
 * @see hr.java.vjezbe.entitet.MjernaPostaja
 * @see hr.java.vjezbe.entitet.RadioSondaza
 */
public class RadioSondaznaMjernaPostaja extends MjernaPostaja implements RadioSondaza {

	int visina;
	

	/**
	 * Iniijalizira podatae o nazivu, mjestu, geografsoj tocki, senzorima i visini. 
	 * 
	 * @param naziv podatak o nazivu radiosondazne mjerne postaje
	 * @param mjesto podatak o mjestu u kojem se nalazi radiosondazna mjerna postaja
	 * @param geografskaTocka podatak o koordinatama radiosondazne mjerne postaje
	 * @param sviSenzori polje senzora koje sadrzi mjerna postaja
	 * @param visina podatak o visini radiosondazne mjerne postaje
	 */
	public RadioSondaznaMjernaPostaja(String naziv, Mjesto mjesto, GeografskaTocka geografskaTocka,
			List<Senzor> sviSenzori, int visina) {
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
