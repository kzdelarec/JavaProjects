package hr.java.vjezbe.entitet;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NeispravnaVisinaException;


/**
 * Predstavlja entitet radiosondazne mjerne postaje koja nasljeduje klasu MjernaPostaja i implementira sucenje RadioSondaza.
 * 
 * @author Kristijan Zdelarec
 * @see hr.java.vjezbe.entitet.MjernaPostaja
 * @see hr.java.vjezbe.entitet.RadioSondaza
 */
public class RadioSondaznaMjernaPostaja extends MjernaPostaja implements RadioSondaza {
	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
	public static Integer visinaPostaje;

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
	/**
	 * Kontrolira je li unesena dobra visina postaje. Ako dode do iznimke, trazi ponovni unos.
	 * 
	 * @param unos podatak kojeg je unio korisnik
	 * @return visina radiosondazne postaje
	 */
	static public Integer kontrolaVisine(Scanner unos) {
		boolean zastavica = false;
		do {
			try {
				visinaPostaje = unos.nextInt();
				provjeraVisine(visinaPostaje);
				zastavica = false;
			} catch (NeispravnaVisinaException ex) {
				System.out.println("Unesite ispravnu visinu radiosondazne mjerne postaje");
				logger.error("Unesena je pogresna visina radiosondazne mjerne postaje("+visinaPostaje+"%)", ex);
				unos.nextLine();
				zastavica = true;
			}
			catch (InputMismatchException ex) {
				System.out.println("Unesite ispravan format");
				logger.error("Unesen je pogresan format", ex);
				unos.nextLine();
				zastavica = true;
			}
		} while (zastavica);

		return visinaPostaje;
	}
	

	/**
	 * Provjerava je li visina u zadanim granicama.
	 * 
	 * @param visinaPostaje visina radiosondazne mjerne postaje
	 * @throws NeispravnaVisinaException iznimka ukoliko visina nije unutar zadanih granica
	 */
	private static void provjeraVisine(Integer visinaPostaje) throws NeispravnaVisinaException {
		if (visinaPostaje<10) {
			throw new NeispravnaVisinaException("Visina je preniska! (" + visinaPostaje);
		}

		if (visinaPostaje > 500) {
			throw new NeispravnaVisinaException("Visina je previsoka! (" + visinaPostaje);
		}
	}

}
