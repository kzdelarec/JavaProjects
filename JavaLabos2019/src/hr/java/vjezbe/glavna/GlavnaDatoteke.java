package hr.java.vjezbe.glavna;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Kategorija;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.utils.Utils;

public class GlavnaDatoteke {
	
	public static final String SERIALIZATION_FILE_NAME = "doc/serijalizacija.dat";

	public static void main(String[] args) {
		
		ArrayList<Korisnik> users = dohvatiKorisnike();
		ArrayList<Artikl> artikli = dohvatiArtikle();
		ArrayList<Kategorija<Artikl>> kategortije = dohvatiKategorije( artikli);
		ArrayList<Prodaja> prodaja = dohvatiProdaju(users, artikli);
		
		ispisProdaje(prodaja);
		
		try {
			serijaliziraj(prodaja);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			deserijaliziraj();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void ispisProdaje(ArrayList<Prodaja> prodaja) {
		System.out.println("Trenutno su oglasi na prodaju:");
		for (int i = 0; i < (prodaja.size()); i++) {
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println(prodaja.get(i).getArtikl().tekstOglasa() + "\nDatum objave: "
					+ prodaja.get(i).getDatumObjave().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "\n"
					+ prodaja.get(i).getKorisnik().dohvatiKontakt());
		}
		System.out.println("-----------------------------------------------------------------------------");
		
	}

	private static ArrayList<Artikl> dohvatiArtikle() {
		System.out.println("Loading articles...");
		
		ArrayList<Artikl> artikli = new ArrayList<>();
		ArrayList<String> strings = new ArrayList<>();
		
		try (Stream<String> stream = Files.lines(new File("doc/artikli").toPath())) {
			strings = (ArrayList<String>) stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		int userRowNumber = 7;
		Long id = 0l;
		Long type = 0l;
		String param1 = "";
		String param2 = "";
		BigDecimal param3 = new BigDecimal(0);
		BigDecimal param4 = new BigDecimal(0);
		int idStanja = 0;
		
		for (int i = 0; i < strings.size(); i++) {
			String row = strings.get(i);
			switch (i % userRowNumber) {
				case 0: {
					type = Long.parseLong(row);
					break;
				}
				case 1: {
					id = Long.parseLong(row);
					break;
				}
				case 2: {
					param1 = row;
					break;
				}
				case 3: {
					param2 = row;
					break;
				}
				case 4: {
					param3 = new BigDecimal(row);
					break;
				}
				case 5: {
					param4 = new BigDecimal(row);
					break;
				}
				case 6: {
					idStanja = Integer.parseInt(row);
					Stanje stanje = null;
					if(idStanja == 1) {
						stanje = Stanje.NOVO;
					} else if(idStanja == 2) {
						stanje = Stanje.IZVRSNO;
					} else if(idStanja == 3) {
						stanje = Stanje.RABLJENO;
					} else if(idStanja == 4) {
						stanje = Stanje.NEISPRAVNO;
					}
					if (type == 1) {
						artikli.add(new Usluga(id, param1, param2, param4, stanje));
					} else if (type == 2){
						artikli.add(new Automobil(id, param1, param2, param4, stanje, param3));
					} else if (type == 3){
						artikli.add(new Stan(id, param1, param2, param4, stanje, param3.intValue()));
					}
					break;
				}
			}
		}
		
		return artikli;
	}
	
	private static ArrayList<Kategorija<Artikl>> dohvatiKategorije(ArrayList<Artikl> artikli) {
		System.out.println("Loading categories...");
		
		ArrayList<Kategorija<Artikl>> kategorije = new ArrayList<>();
		ArrayList<String> strings = new ArrayList<>();
		
		try (Stream<String> stream = Files.lines(new File("doc/kategorije").toPath())) {
			strings = (ArrayList<String>) stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		int brojRedova = 3;
		Long id = 0l;
		String naziv = "";
		
		for (int i = 0; i < strings.size(); i++) {
			String row = strings.get(i);
			switch (i % brojRedova) {
				case 0: {
					id = Long.parseLong(row);
					break;
				}
				case 1: {
					naziv = row;
					break;
				}
				case 2: {
					ArrayList<Long> ids= Utils.rowToArray(row, ",");
					Kategorija<Artikl> kategorija = new Kategorija<>(id, naziv);
					
					for (Artikl artikl : artikli) {
						if (ids.contains(artikl.getId())) {
							kategorija.dodajArtikl(artikl);
						}
					}
					
					kategorije.add(kategorija);
					
					break;
				}
			}
		}
		
		return kategorije;
	}
	
	private static ArrayList<Korisnik> dohvatiKorisnike() {
		
		System.out.println("Loading users...");
		
		ArrayList<Korisnik> users = new ArrayList<>();
		ArrayList<String> strings = new ArrayList<>();
		
		try (Stream<String> stream = Files.lines(new File("doc/korisnici").toPath())) {
			strings = (ArrayList<String>) stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		int userRowNumber = 6;
		Long id = 0l;
		Long type = 0l;
		String name = "";
		String surname = "";
		String mail = "";
		String phone = "";
		
		for (int i = 0; i < strings.size(); i++) {
			String row = strings.get(i);
			switch (i % userRowNumber) {
				case 0: {
					id = Long.parseLong(row);
					break;
				}
				case 1: {
					type = Long.parseLong(row);
					break;
				}
				case 2: {
					name = row;
					break;
				}
				case 3: {
					surname = row;
					break;
				}
				case 4: {
					mail = row;
					break;
				}
				case 5: {
					phone = row;
					if (type == 1) {
						users.add(new PrivatniKorisnik(id, name, surname, mail, phone));
					} else {
						users.add(new PoslovniKorisnik(id, name, surname, mail, phone));
					}
					break;
				}
			}
		}
		
		return users;
	}
	
	private static ArrayList<Prodaja> dohvatiProdaju(ArrayList<Korisnik> users, ArrayList<Artikl> artikli) {
		System.out.println("Loading sales...");
		
		ArrayList<Prodaja> prodaja = new ArrayList<>();
		ArrayList<String> strings = new ArrayList<>();
		
		try (Stream<String> stream = Files.lines(new File("doc/prodaja").toPath())) {
			strings = (ArrayList<String>) stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		int brojRedova = 4;
		Long id = 0l;
		Long idKorisnika = 0l;
		Long idKategorije = 0l;
		Long idArtikla = 0l;
		
		for (int i = 0; i < strings.size(); i++) {
			String row = strings.get(i);
			switch (i % brojRedova) {
				case 0: {
					id = Long.parseLong(row);
					break;
				}
				case 1: {
					idKorisnika = Long.parseLong(row);
					break;
				}
				case 2: {
					idKategorije = Long.parseLong(row);
					break;
				}
				case 3: {
					idArtikla = Long.parseLong(row);
					Artikl artikl = (Artikl) Utils.getObjectById(artikli, idArtikla);
					Korisnik korisnik = (Korisnik) Utils.getObjectById(users, idKorisnika);
					prodaja.add(new Prodaja(id, artikl, korisnik, LocalDate.now()));
					
					break;
				}
			}
		}
		
		return prodaja;
	}
	
	public static void deserijaliziraj() throws IOException {
		ObjectInputStream in = null;
		try{
			in = new ObjectInputStream(new FileInputStream(SERIALIZATION_FILE_NAME));
			while (in.readObject() != null) {
				Prodaja procitanOglas= (Prodaja) in.readObject();
				System.out.println("Podaci o pročitanom objektu:");
				System.out.println("Naziv Oglasa: "+ procitanOglas.getArtikl().getNaslov());
				System.out.println("Cijena Oglasa: "+ procitanOglas.getArtikl().getCijena());
				System.out.println("");
			}
		}catch(IOException ex) {
			System.err.println(ex);
		} catch(ClassNotFoundException ex) {
		System.err.println(ex);
		} finally {
			in.close();
		}
	}

	public static void serijaliziraj(ArrayList<Prodaja> prodaje) throws IOException, FileNotFoundException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZATION_FILE_NAME));
		Stream<Prodaja> serijalizacija = prodaje.stream();
		serijalizacija.forEach(oglas->{
			try {
				out.writeObject(oglas);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		out.close();
	}

}
