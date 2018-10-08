package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.Provjera;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.RadioSondaznaMjernaPostaja;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorTlakaZraka;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;
import hr.java.vjezbe.sortiranje.ZupanijaSorter;

/**
 * Predstavlja glavnu klasu iz koje se izvodi program.
 * 
 * @author Kristijan Zdelarec
 *
 */

public class Glavna {

	public static final int BROJ_POSTAJA = 3;
	public static final int BROJ_SENZORA = 3;
	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	/**
	 * Pokrece izvodenje programa.
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		List<MjernaPostaja> novaMjernaPostaja = new ArrayList<>();
		List<Zupanija> popisZupanija = new ArrayList<>();
		List<Drzava> popisDrzava = new ArrayList<>();
		List<String> popisStreaminga = new ArrayList<>();
		List<String> popisPinga = new ArrayList<>();
		List<String> popisOstalog = new ArrayList<>();
		Map<Mjesto, List<String>> mapaSenzoraMjesta = new HashMap<>();
		Map<String, List<String>> mapaSenzoraPoNacinuRada = new HashMap<>();
		Integer visinaRadioSonazneMjernePostaje = 0;
		for (int i = 0; i < BROJ_POSTAJA; i++) {

			Scanner unos = new Scanner(System.in);

			System.out.println("Unesite podatke za " + (i + 1) + ". mjernu postaju");

			// Unos podataka korisnickih podataka
			String nazivMjernePostaje = unosNazivaMjernePostaje(unos, i);

			if (i == 2) {
				visinaRadioSonazneMjernePostaje = unosVisineRadioSonazneMjernePostaje(unos);
			}

			Drzava novaDrzava = dohvatiDrzavu(unos, novaMjernaPostaja);
			popisDrzava.add(novaDrzava);

			Zupanija novaZupanija = dohvatiZupaniju(unos, novaMjernaPostaja, novaDrzava);

			if (!(popisZupanija.contains(novaZupanija))) {
				popisZupanija.add(novaZupanija);
			}

			Mjesto novoMjesto = dohvatiMjesto(unos, novaMjernaPostaja, novaZupanija);

			VrstaMjesta vrstaMjesta = unosVrsteMjesta(unos);

			BigDecimal xKoordinata = unosKoordinateX(unos);

			BigDecimal yKoordinata = unosKoordinateY(unos);

			String elektricnaKomponenta = unosKomponente(unos);

			BigDecimal temperaturaZraka = unosVrijednostiTermometra(unos);

			RadSenzora senzorTemp = unosRadaSenzora(unos);

			BigDecimal vlagaZraka = unosVlageZraka(unos);

			RadSenzora senzorVlage = unosRadaSenzora(unos);

			String opisTlaka = unosOpisaTlaka(unos);

			BigDecimal tlakZraka = unosTlakaZraka(unos);

			RadSenzora senzorTlaka = unosRadaSenzora(unos);

			// postavljanje vrijednosti objekata klase Senzor
			List<String> popisSenzora = new ArrayList<>();
			SenzorTemperature noviSenzorTemperature = new SenzorTemperature("°C", (byte) 2, elektricnaKomponenta);
			noviSenzorTemperature.setVrijednost(temperaturaZraka);
			noviSenzorTemperature.setRadSenzora(senzorTemp);
			popisSenzora.add("Senzor topline");
			if(noviSenzorTemperature.getRadSenzora().name().equals("STREAMING")) {
				popisStreaminga.add("Senzor vjetra");
			}
			if(noviSenzorTemperature.getRadSenzora().name().equals("PING")) {
				popisPinga.add("Senzor vjetra");
			}
			if(noviSenzorTemperature.getRadSenzora().name().equals("OSTALO")) {
				popisOstalog.add("Senzor vjetra");
			}
			SenzorVlage noviSenzorVlage = new SenzorVlage("%", (byte) 2);
			noviSenzorVlage.setVrijednost(vlagaZraka);
			noviSenzorVlage.setRadSenzora(senzorVlage);
			popisSenzora.add("Senzor vlage");
			if(noviSenzorVlage.getRadSenzora().name().equals("STREAMING")) {
				popisStreaminga.add("Senzor vlage");
			}
			if(noviSenzorVlage.getRadSenzora().name().equals("PING")) {
				popisPinga.add("Senzor vlage");
			}
			if(noviSenzorVlage.getRadSenzora().name().equals("OSTALO")) {
				popisOstalog.add("Senzor vlage");
			}
			SenzorTlakaZraka noviSenzorTlaka = new SenzorTlakaZraka("hPa", (byte) 1000, opisTlaka);
			noviSenzorTlaka.setVrijednost(tlakZraka);
			noviSenzorTlaka.setRadSenzora(senzorTlaka);
			popisSenzora.add("Senzor tlaka zraka");
			if(noviSenzorTlaka.getRadSenzora().name().equals("STREAMING")) {
				popisStreaminga.add("Senzor tlaka zraka");
			}
			if(noviSenzorTlaka.getRadSenzora().name().equals("PING")) {
				popisPinga.add("Senzor tlaka zraka");
			}
			if(noviSenzorTlaka.getRadSenzora().name().equals("OSTALO")) {
				popisOstalog.add("Senzor tlaka zraka");
			}
			mapaSenzoraPoNacinuRada.put("STREAMING", popisStreaminga);
			mapaSenzoraPoNacinuRada.put("PING", popisPinga);
			mapaSenzoraPoNacinuRada.put("OSTALO", popisOstalog);
			// Dodavanje senzora u polje
			List<Senzor> listaSenzora = new ArrayList<>();
			listaSenzora.add(noviSenzorTemperature);
			listaSenzora.add(noviSenzorVlage);
			listaSenzora.add(noviSenzorTlaka);

			// Kreiranje objekata ostalih klasa

			mapaSenzoraMjesta.put(novoMjesto, popisSenzora);

			GeografskaTocka novaGeografskaTocka = new GeografskaTocka(xKoordinata, yKoordinata);

			novoMjesto.setVrstaMjest(vrstaMjesta);

			if (i < 2) {
				novaMjernaPostaja.add(dohvatiPostaju(novaMjernaPostaja, novoMjesto, novaGeografskaTocka, listaSenzora,
						nazivMjernePostaje));
			} else {
				novaMjernaPostaja.add(new RadioSondaznaMjernaPostaja(nazivMjernePostaje, novoMjesto,
						novaGeografskaTocka, listaSenzora, visinaRadioSonazneMjernePostaje));
			}

		}

		// ispis podataka o mjernoim postajama
		ispisPodataka(novaMjernaPostaja, visinaRadioSonazneMjernePostaje, mapaSenzoraMjesta, popisDrzava,mapaSenzoraPoNacinuRada);

		// ucitavanje temperature svake sekunde
		kontinuiranoUcitavanjeSenzoraTemperature(novaMjernaPostaja);

	}

	/**
	 * Stvara objekt mjerna postaja i provjerava je li isti vec unesen.
	 * 
	 * @param novaMjernaPostaja lista mjernih postaja
	 * @param mjesto objekt klase Mjesto
	 * @param geo objekt klase GeografskaTocka
	 * @param listaSenzora lista senzora mjerne postaje
	 * @param naziv podatak o nazivu mjerne postaje
	 * @return objekt klase MjernaPostaja
	 */
	private static MjernaPostaja dohvatiPostaju(List<MjernaPostaja> novaMjernaPostaja, Mjesto mjesto,
			GeografskaTocka geo, List<Senzor> listaSenzora, String naziv) {

		Optional<MjernaPostaja> postaja = novaMjernaPostaja.stream().filter(p -> p.getNaziv().equals(naziv))
				.findFirst();
		if (postaja.isPresent())
			return postaja.get().getPostaje();
		MjernaPostaja mjernaPostaja = new MjernaPostaja(naziv, mjesto, geo, listaSenzora);
		mjesto.getPopisMjernihPostaja().add(mjernaPostaja);
		return mjernaPostaja;
	}

	/**
	 * Stvara objekt mjesto i provjerava je li isti vec unesen.
	 * 
	 * @param unos podatak kojeg unosi korisnik
	 * @param novaMjernaPostaja lista mjernih postaja
	 * @param zup objekt klase Zupanija
	 * @return objekt klase Mjesto
	 */
	private static Mjesto dohvatiMjesto(Scanner unos, List<MjernaPostaja> novaMjernaPostaja, Zupanija zup) {
		System.out.println("Unesite naziv mjesta");
		String nazivMjesta = unos.nextLine();
		logger.info("Unesen je naziv mjesta: " + nazivMjesta);

		Optional<MjernaPostaja> postaja = novaMjernaPostaja.stream()
				.filter(p -> p.getMjesto().getNaziv().equals(nazivMjesta)).findFirst();
		if (postaja.isPresent())
			return postaja.get().getMjesto();
		Mjesto mjesto = new Mjesto(nazivMjesta, zup);
		zup.getMjesta().add(mjesto);
		return mjesto;
	}

	
	/**
	 * Stvara objekt zupanija i provjerava je li isti vec unesen.
	 * 
	 * @param unos podatak kojeg unosi korisnik
	 * @param novaMjernaPostaja lista mjernih postaja
	 * @param drz objekt klase Drzava
	 * @return objekt klase Zupanija
	 */
	private static Zupanija dohvatiZupaniju(Scanner unos, List<MjernaPostaja> novaMjernaPostaja, Drzava drz) {
		System.out.println("Unesite naziv županije:");
		String naziv = unos.nextLine();
		Optional<MjernaPostaja> postaja = novaMjernaPostaja.stream()
				.filter(p -> p.getMjesto().getZupanija().getNaziv().equals(naziv)).findFirst();
		if (postaja.isPresent())
			return postaja.get().getMjesto().getZupanija();
		Zupanija zup = new Zupanija(naziv, drz);
		drz.getZupanije().add(zup);
		return zup;
	}

	/**
	 * Stvara objekt drzave i provjerava je li isti vec unesen.
	 * 
	 * @param unos podatak kojeg unosi korisnik
	 * @param novaMjernaPostaja lista mjernih postaja
	 * @return objekt klase Drzava
	 */
	private static Drzava dohvatiDrzavu(Scanner unos, List<MjernaPostaja> novaMjernaPostaja) {
		System.out.println("Unesite naziv drzave");
		String nazivDrzave = unos.nextLine();
		logger.info("Unesen je naziv drzave: " + nazivDrzave);
		System.out.println("Unesite povrsinu drzave");
		BigDecimal povrsinaDrzave = Provjera.provjeraBigDecimala(unos);
		logger.info("Unesena je povrsina drzave: " + povrsinaDrzave);
		unos.nextLine();
		System.out.println("Unesite kratic udrzave:");
		String kratica;
		do {
			kratica= unos.nextLine();
			if(kratica.length()!=2) {
				System.out.println("Kratica mora zadrzavati 2 znaka!");
			}
		}while(kratica.length()!=2);
		Drzava drzava = new Drzava(nazivDrzave, povrsinaDrzave);
		drzava.setKratica(kratica);
		return drzava;
	}

	/**
	 * Trazi unos nacina rada senzora od korisnika.
	 * 
	 * @param unos podatak koji unosi korisnik
	 * @return enumeracija koja nam govori kako radi senzor
	 */
	private static RadSenzora unosRadaSenzora(Scanner unos) {
		System.out.println("1. STREAMNG\n2. PING\nODABIR >> ");
		int odabir = 0;
		odabir = unos.nextInt();
		unos.nextLine();
		if (odabir == 1)
			return RadSenzora.STREAMING;
		if (odabir == 2)
			return RadSenzora.PING;
		else
			return RadSenzora.OSTALO;
	}

	/**
	 * Trazi unos vrste mjesta od korisnika.
	 * 
	 * @param unos podatak koji unosi korisnik
	 * @return enumeracija mjesta
	 */
	private static VrstaMjesta unosVrsteMjesta(Scanner unos) {
		System.out.println("1.SELO\n2.GRAD\nODABIR >> ");
		int odabir = 0;
		odabir = unos.nextInt();
		unos.nextLine();
		if (odabir == 1)
			return VrstaMjesta.GRAD;
		if (odabir == 2)
			return VrstaMjesta.SELO;
		else
			return VrstaMjesta.OSTALO;
	}

	/**
	 * Generira vrijednost senzora temperature za sve mjerne postaje u intervalima
	 * od jedne sekunde.
	 * 
	 * @param novaMjernaPostaja
	 *            mjerne postaje na kojima se izvodi mjerenje
	 * @throws InterruptedException
	 *             simulira intervale mjerenja
	 */
	private static void kontinuiranoUcitavanjeSenzoraTemperature(List<MjernaPostaja> novaMjernaPostaja)
			throws InterruptedException {
		while (true) {
			novaMjernaPostaja.forEach(simulacija->{
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
				List<Senzor> poljeSenzoraTemperature = new ArrayList<>(simulacija.getSviSenzori());
				
				//-----for each petlja za generiranje vrjednosti temperature
				poljeSenzoraTemperature.forEach(simulacijaSenzora->{
					if (simulacijaSenzora instanceof SenzorTemperature) {
						((SenzorTemperature) simulacijaSenzora).generirajVrjednost(simulacija.getNaziv());

					}
				}); 
			}); 

		}
	}

	/**
	 * Trazi korisnika da unese podatke za visinu radiosondazne mjerne postaje.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o visini radiosondazne mjerne postaje
	 */
	private static Integer unosVisineRadioSonazneMjernePostaje(Scanner unos) {
		System.out.println("Unesite visinu radio sondažne mjerne postaje:");
		Integer visina = Provjera.provjeraIntegera(unos);
		logger.info("Unesena je visina radiosondazne : " + visina);
		unos.nextLine();
		return visina;
	}

	/**
	 * Trazi korisnika da unese podatak za tlak zraka.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o tlaku zraka
	 */
	private static BigDecimal unosTlakaZraka(Scanner unos) {
		System.out.println("Unesite vrijednost barometra:");
		BigDecimal tlak = Provjera.provjeraBigDecimala(unos);
		logger.info("Unesena je vrijednost senzor za tlaka zraka: " + tlak);
		return tlak;
	}

	/**
	 * Trazi korisnika da unese podatak za opis tlaka zraka.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o opisu tlaka zraka
	 */
	private static String unosOpisaTlaka(Scanner unos) {
		System.out.println("Unesite opis tlaka zraka (visoki, niski, normalan):");
		String opis = unos.nextLine();
		return opis;
	}

	/**
	 * Trazi korisnika da unese podatak za vlagu zraka.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o tlaku zraka
	 */
	private static BigDecimal unosVlageZraka(Scanner unos) {
		System.out.println("Unesite vrijednost senzora vlage:");
		BigDecimal vlaga = Provjera.provjeraBigDecimala(unos);
		logger.info("Unesena je vrijednost senzor za vlagu zraka: " + vlaga);
		unos.nextLine();
		return vlaga;
	}

	/**
	 * Trazi korisnika da unese podatak za temperaturu zraka.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o tempeaturi zraka
	 */
	private static BigDecimal unosVrijednostiTermometra(Scanner unos) {
		System.out.println("Unesite vrijednost senzora za temperaturu:");
		BigDecimal temperatura = Provjera.provjeraBigDecimala(unos);
		logger.info("Unesena je vrijednost senzor za temperaturu: " + temperatura);
		unos.nextLine();
		return temperatura;
	}

	/**
	 * Trazi korisnika da unese podatak za elektricnu komponentu senzora.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak za elektricnu komponentu senzora
	 */
	private static String unosKomponente(Scanner unos) {
		System.out.println("Unesite elektonicku komponentu za senzor temperature");
		String elKomponenta = unos.nextLine();
		return elKomponenta;
	}

	/**
	 * Trazi korisnika da unese podatak y koordinatu mjerne postaje.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o y koordinati mjerne postaje
	 */
	private static BigDecimal unosKoordinateY(Scanner unos) {
		System.out.println("Unesite y koordinatu");
		BigDecimal yKoordinata = Provjera.provjeraBigDecimala(unos);
		logger.info("Unesena je Y koordinata: " + yKoordinata);
		unos.nextLine();
		return yKoordinata;
	}

	/**
	 * Trazi korisnika da unese podatak x koordinatu mjerne postaje.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @return vraca podatak o x koordinati mjerne postaje
	 */
	private static BigDecimal unosKoordinateX(Scanner unos) {
		System.out.println("Unesite X koordinatu");
		BigDecimal xKoordinata = Provjera.provjeraBigDecimala(unos);
		logger.info("Unesena je X koordinata: " + xKoordinata);
		unos.nextLine();
		return xKoordinata;
	}

	/**
	 * Trazi korisnika da unese podatak za naziv mjesta. Ovisno o rednom broju
	 * mjerne postaje, unosi se naziv mjerne postaje ili radiosondazne mjerne
	 * postaje.
	 * 
	 * @param unos
	 *            podatak kojeg je unio korisnik
	 * @param i
	 *            iterator koji oznacava redni broj mjerne postaje
	 * @return vraca podatak o nazivu mjerne postaje
	 */
	private static String unosNazivaMjernePostaje(Scanner unos, int i) {
		if (i < 2) {
			System.out.println("Unesite naziv mjerne postaje");
			String nazivMjernePostaje = unos.nextLine();
			logger.info("Unesen je naziv mjerne postaje: " + nazivMjernePostaje);
			return nazivMjernePostaje;
		} else {
			System.out.println("Unesite naziv radio sonazne mjerne postaje");
			String nazivRadioSonazneMjernePostaje = unos.nextLine();
			logger.info("Unesen je naziv radiosondazne mjerne postaje: " + nazivRadioSonazneMjernePostaje);
			return nazivRadioSonazneMjernePostaje;
		}
	}

	/**
	 * Ispisuje podatke o mjernim postajama i senzorima. U prvoj for petlji se
	 * ispisuju podaci o mjernim postajama. U drugoj for petlji se ispisuju podaci o
	 * senzorima. Nakon ispisa senzora, iz klase Drzava dohvaca listu zupanija te ju sortira i ispisuje.
	 * Takoder kontrolira da ne ispisuje dvije iste zupanije. 
	 * Nakon ispisa zupnija ispisuje podatke o tome koja mjerna postaja ima koje senzore.
	 * 
	 * @param novaMjernaPostaja
	 *            mjerna postaja za koju se ispisuje podatak
	 * @param visinaRadioSonazneMjernePostaje
	 *            podatak o visini radiosondazne mjerne postaje
	 * @param mapaSenzoraMjesta mapa u kojoj se sa mjernim postajama pove
	 */
	private static void ispisPodataka(List<MjernaPostaja> novaMjernaPostaja, Integer visinaRadioSonazneMjernePostaje,
			Map<Mjesto, List<String>> mapaSenzoraMjesta,List<Drzava> popisDrzava, Map<String,List<String>> mapaSenzoraPoNacinuRada) {

		for (MjernaPostaja ispisPostaja : novaMjernaPostaja) {
			System.out.println("\n\n--------------------\nNaziv mjerne postaje: " + ispisPostaja.getNaziv());
			if (ispisPostaja instanceof RadioSondaznaMjernaPostaja) {
				System.out.println("Postaja je radio sondazna\nVisina radio sondazne mjerne postaje je: "
						+ ((RadioSondaznaMjernaPostaja) ispisPostaja).dohvatiVisinuPostaje());
			}
			System.out.println("Postaja se nalazi u mjestu " + ispisPostaja.getMjesto().getNaziv() + "("
					+ ispisPostaja.getMjesto().getVrstaMjest() + ")" + ", zupaniji: "
					+ ispisPostaja.getMjesto().getZupanija().getNaziv() + ", drzavi "
					+ ispisPostaja.getMjesto().getZupanija().getDrzava().getNaziv()
					+ "\nTocne koordinate postaje su: X:" + ispisPostaja.getGeografskaTocka().getX() + " Y:"
					+ ispisPostaja.getGeografskaTocka().getY() + "\nVrijednosti Senzora postaje su:");
			/* Ispisuje podatke o senzorima */

			ispisPostaja.dohvatiSenzore(ispisPostaja.getSviSenzori());
			
			
		}
		
		/*-------------Ispisuje sortirane zupanije-------*/
		List<Zupanija> listaZupanija = new ArrayList <>();
		System.out.println("\nIspis sortiranih zupanija:");
		novaMjernaPostaja.forEach(ispisZupanija->{
			List<Zupanija> lista = ispisZupanija.getMjesto().getZupanija().getDrzava().getPopisZupanijaDrzave();
			lista.forEach(zup->{
				if(!(listaZupanija.contains(zup))){
					listaZupanija.add(zup);
				}
			});
		});
		Collections.sort(listaZupanija, new ZupanijaSorter());
		listaZupanija.forEach(sortiraniIspis->{
			System.out.println(sortiraniIspis.getNaziv());
		});
		
		/*------Sortiranje drzava po kratici-----*/
		Function<Drzava, String> kratica = Drzava::getKratica;
		Comparator<Drzava> poKratici = Comparator.comparing(kratica);
		popisDrzava.sort(poKratici);
		
		/*-------Ispisuje sortirane drzave po kratici------*/
		popisDrzava.stream().forEach(drzava->{
			System.out.println(drzava.getNaziv() + " - " + drzava.getKratica());
		});
		
		
		/*------takeWhile-----*/
		System.out.println("\n");
		for(Drzava takeDrzava : popisDrzava) {
			System.out.print(takeDrzava.getNaziv() + " - ");
			Stream.of(takeDrzava.getKratica()).takeWhile(i-> i.length()<=2).forEach(System.out::println);
		}
		
		
		
		/*-------------Ispisuje mapu-------*/
		Iterator<Mjesto> iterator = mapaSenzoraMjesta.keySet().iterator();
		while (iterator.hasNext()) {

			Set<Mjesto> keys = mapaSenzoraMjesta.keySet(); // get all keys
			keys.forEach(i->{
				String key = iterator.next().toString();
				System.out.println("Mjerna postaja " + key + " ima senzore:");
				System.out.println(mapaSenzoraMjesta.get(i).toString().replace("[", "").replace("]", "").replace(", ", "\n")+ "\n");
			});

		}
		
		for(String radSenzora : mapaSenzoraPoNacinuRada.keySet()) {
			String key = radSenzora;
			String value = mapaSenzoraPoNacinuRada.get(radSenzora).toString();
			System.out.println(key+"\n" + value.replace("[", "").replace("]", "").replace(", ", "\n")+ "\n");
		}

	}

}
