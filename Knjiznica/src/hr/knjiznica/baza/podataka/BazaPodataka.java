package hr.knjiznica.baza.podataka;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import hr.knjiznica.entiteti.IzdanaKnjiga;
import hr.knjiznica.entiteti.Knjiga;
import hr.knjiznica.entiteti.Knjiznicar;
import hr.knjiznica.entiteti.Korisnik;


public class BazaPodataka {
	private static final String DATABASE_FILE = "bazaPodataka.properties";
	private static Connection spajanjeNaBazuPodataka() throws FileNotFoundException, IOException, SQLException {

		Properties svojstva = new Properties();
		svojstva.load(new FileReader(DATABASE_FILE));
		String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
		String korisnickoIme = svojstva.getProperty("korisnickoIme");
		String lozinka = svojstva.getProperty("lozinka");
		Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);
		
		return veza;
	}

	private static void zatvaranjeVezeNaBazuPodataka(Connection veza) throws SQLException {
		veza.close();
	}
	
	public static List<Knjiznicar> dohvatiKnjiznicare() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementKnjiznicar = veza.createStatement();
		ResultSet resultKnjiznicar = statementKnjiznicar.executeQuery("SELECT * FROM KNJIZNICA.KNJIZNICARI");
		List<Knjiznicar> listaKnjiznicara = new ArrayList<>();
		while (resultKnjiznicar.next()) {
			Integer knjiznicarId = resultKnjiznicar.getInt("ID");
			String ime = resultKnjiznicar.getString("IME");
			String prezime = resultKnjiznicar.getString("PREZIME");
			String mailAdresa = resultKnjiznicar.getString("MAIL");
			Integer brojMobitela = resultKnjiznicar.getInt("MOBITEL");
			String korisnickoIme = resultKnjiznicar.getString("KORISNICKO_IME");
			String lozinka = resultKnjiznicar.getString("LOZINKA");
			Integer isAdminTmp = resultKnjiznicar.getInt("ADMINISTRATOR");
			Boolean isAdmin = null;
			if(isAdminTmp == 1) {
				isAdmin=true;
			}
			if(isAdminTmp == 0) {
				isAdmin = false;
			}
			Integer isAktivanTmp = resultKnjiznicar.getInt("AKTIVAN");
			Boolean isAktivan = null;
			if(isAktivanTmp == 1) {
				isAktivan=true;
				Knjiznicar knjiznicar = new Knjiznicar(ime,prezime,mailAdresa,brojMobitela,korisnickoIme,lozinka,isAdmin,isAktivan);
				knjiznicar.setID(knjiznicarId);
				listaKnjiznicara.add(knjiznicar);
			}
			if(isAktivanTmp == 0) {
				isAktivan = false;
			}
			
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaKnjiznicara;
	}
	
	public static List<Knjiznicar> dohvatiKnjiznicareLog() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementKnjiznicar = veza.createStatement();
		ResultSet resultKnjiznicar = statementKnjiznicar.executeQuery("SELECT * FROM KNJIZNICA.KNJIZNICARI");
		List<Knjiznicar> listaKnjiznicara = new ArrayList<>();
		while (resultKnjiznicar.next()) {
			Integer knjiznicarId = resultKnjiznicar.getInt("ID");
			String ime = resultKnjiznicar.getString("IME");
			String prezime = resultKnjiznicar.getString("PREZIME");
			String mailAdresa = resultKnjiznicar.getString("MAIL");
			Integer brojMobitela = resultKnjiznicar.getInt("MOBITEL");
			String korisnickoIme = resultKnjiznicar.getString("KORISNICKO_IME");
			String lozinka = resultKnjiznicar.getString("LOZINKA");
			Integer isAdminTmp = resultKnjiznicar.getInt("ADMINISTRATOR");
			Boolean isAdmin = null;
			if(isAdminTmp == 1) {
				isAdmin=true;
			}
			if(isAdminTmp == 0) {
				isAdmin = false;
			}
			Integer isAktivanTmp = resultKnjiznicar.getInt("AKTIVAN");
			Boolean isAktivan = null;
			if(isAktivanTmp == 1) {
				isAktivan=true;
			}
			if(isAktivanTmp == 0) {
				isAktivan = false;
			}
			Knjiznicar knjiznicar = new Knjiznicar(ime,prezime,mailAdresa,brojMobitela,korisnickoIme,lozinka,isAdmin,isAktivan);
			knjiznicar.setID(knjiznicarId);
			listaKnjiznicara.add(knjiznicar);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaKnjiznicara;
	}
	
	public static void spremiKnjiznicara(Knjiznicar knjiznicar) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertKnjiznicar = veza
					.prepareStatement("INSERT INTO KNJIZNICA.KNJIZNICARI(IME, PREZIME, MAIL, MOBITEL, KORISNICKO_IME,LOZINKA,ADMINISTRATOR,AKTIVAN)"
							+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
			insertKnjiznicar.setString(1, knjiznicar.getIme());
			insertKnjiznicar.setString(2, knjiznicar.getPrezime());
			insertKnjiznicar.setString(3, knjiznicar.getMailAdresa());
			insertKnjiznicar.setInt(4, knjiznicar.getBrojMobitela());
			insertKnjiznicar.setString(5, knjiznicar.getKorisnickoIme());
			insertKnjiznicar.setString(6, knjiznicar.getLozinka());
			Integer isAdminTmp = null;
			if(knjiznicar.getIsAdmin()) {
				isAdminTmp=1;
			}
			else isAdminTmp=0;
			insertKnjiznicar.setInt(7, isAdminTmp);
			Integer isAktivanTmp = null;
			if(knjiznicar.getIsAktivan()) {
				isAktivanTmp=1;
			}
			else isAdminTmp=0;
			insertKnjiznicar.setInt(8, isAktivanTmp);
			insertKnjiznicar.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static void obrisiKnjiznicara(Knjiznicar knjiznicar) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement updateKnjiznicar = veza
					.prepareStatement("UPDATE KNJIZNICA.KNJIZNICARI SET AKTIVAN = ? WHERE ID = ?");
			updateKnjiznicar.setInt(1,0);
			updateKnjiznicar.setInt(2, knjiznicar.getID());
			updateKnjiznicar.executeUpdate();
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static void updateKnjiznicara(Knjiznicar knjiznicar) throws FileNotFoundException, SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertKnjiznicar = veza
					.prepareStatement("UPDATE KNJIZNICA.KNJIZNICARI SET IME = ?, PREZIME = ?, MAIL = ?, MOBITEL = ?, KORISNICKO_IME = ?,"
							+ "LOZINKA = ?, ADMINISTRATOR = ? WHERE ID = ?");
			insertKnjiznicar.setString(1,knjiznicar.getIme());
			insertKnjiznicar.setString(2, knjiznicar.getPrezime());
			insertKnjiznicar.setString(3, knjiznicar.getMailAdresa());
			insertKnjiznicar.setInt(4, knjiznicar.getBrojMobitela());
			insertKnjiznicar.setString(5, knjiznicar.getKorisnickoIme());
			insertKnjiznicar.setString(6, knjiznicar.getLozinka());
			if(knjiznicar.getIsAdmin()) {
				insertKnjiznicar.setInt(7,1);
			}
			else insertKnjiznicar.setInt(7,0);
			insertKnjiznicar.setInt(8, knjiznicar.getID());
			insertKnjiznicar.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
	}
	
	public static void SpremiKnjigu(Knjiga knjiga) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertKnjiga = veza
					.prepareStatement("INSERT INTO KNJIZNICA.KNJIGE(BARCODE_ID, NASLOV, AUTOR, KATEGORIJA, GODINA_IZDANJA,"
							+ "IZDANJE,IZDAVAC,KOLICINA)"
							+ " VALUES (?, ?, ?, ?, ?, ?, ?,?);");
			insertKnjiga.setLong(1, knjiga.getBarcodeId());
			insertKnjiga.setString(2, knjiga.getNaslov());
			insertKnjiga.setString(3, knjiga.getAutor());
			insertKnjiga.setString(4, knjiga.getKategorija());
			insertKnjiga.setInt(5, knjiga.getGodinaIzdanja());
			insertKnjiga.setInt(6, knjiga.getIzdanje());
			insertKnjiga.setString(7, knjiga.getIzdavac());
			insertKnjiga.setInt(8, knjiga.getKolicina());
			insertKnjiga.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static List<Knjiga> dohvatiKnjige() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementKnjiga = veza.createStatement();
		ResultSet resultKnjiga = statementKnjiga.executeQuery("SELECT * FROM KNJIZNICA.KNJIGE");
		List<Knjiga> listaKnjiga = new ArrayList<>();
		while (resultKnjiga.next()) {
			Integer ID = resultKnjiga.getInt("ID");
			Long barcodeId = resultKnjiga.getLong("BARCODE_ID");
			String naslov = resultKnjiga.getString("NASLOV");
			String autor = resultKnjiga.getString("AUTOR");
			String kategorija = resultKnjiga.getString("KATEGORIJA");
			Integer godinaIzdanja = resultKnjiga.getInt("GODINA_IZDANJA");
			Integer izdanje = resultKnjiga.getInt("IZDANJE");
			String izdavac = resultKnjiga.getString("IZDAVAC");
			Integer kolicina = resultKnjiga.getInt("KOLICINA");
			
			Knjiga knjiga = new Knjiga(barcodeId, naslov, autor, kategorija, izdanje, izdavac, godinaIzdanja, kolicina);
			knjiga.setID(ID);
			listaKnjiga.add(knjiga);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaKnjiga;
	}
	
	public static void updateKnjiga(Knjiga knjiga) throws FileNotFoundException, SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertKnjiga = veza
					.prepareStatement("UPDATE KNJIZNICA.KNJIGE SET BARCODE_ID = ?, NASLOV = ?, AUTOR = ?, KATEGORIJA = ?, GODINA_IZDANJA = ?,"
							+ "IZDANJE = ?, IZDAVAC = ?, KOLICINA = ? WHERE ID = ?");
			insertKnjiga.setLong(1, knjiga.getBarcodeId());
			insertKnjiga.setString(2, knjiga.getNaslov());
			insertKnjiga.setString(3, knjiga.getAutor());
			insertKnjiga.setString(4, knjiga.getKategorija());
			insertKnjiga.setInt(5, knjiga.getGodinaIzdanja());
			insertKnjiga.setInt(6, knjiga.getIzdanje());
			insertKnjiga.setString(7, knjiga.getIzdavac());
			insertKnjiga.setInt(8, knjiga.getKolicina());
			insertKnjiga.setInt(9, knjiga.getID());
			insertKnjiga.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
	}
	
	public static void spremiKorisnika(Korisnik korisnik) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertKorisnika = veza
					.prepareStatement("INSERT INTO KNJIZNICA.KORISNICI(ID_KORISNIKA, IME, PREZIME, OIB, MAIL, MOBITEL,"
							+ " DATUM_UCLANJENJA)"
							+ " VALUES (?, ?, ?, ?, ?, ?, ?);");
			insertKorisnika.setLong(1, korisnik.getIdKorisnika());
			insertKorisnika.setString(2,korisnik.getIme());
			insertKorisnika.setString(3, korisnik.getPrezime());
			insertKorisnika.setLong(4, korisnik.getOib());
			insertKorisnika.setString(5, korisnik.getMailAdresa());
			insertKorisnika.setLong(6, korisnik.getBrojMobitela().longValue());
			insertKorisnika.setDate(7, Date.valueOf(korisnik.getDatumUclanjenja()));
			insertKorisnika.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static List<Korisnik> dohvatiKorisnike() throws SQLException, IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyy.");
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementKorisnik = veza.createStatement();
		ResultSet resultKorisnika = statementKorisnik.executeQuery("SELECT * FROM KNJIZNICA.KORISNICI");
		List<Korisnik> listaKorisnika = new ArrayList<>();
		while (resultKorisnika.next()) {
			Integer ID = resultKorisnika.getInt("ID");
			Long barcodeId = resultKorisnika.getLong("ID_KORISNIKA");
			String ime = resultKorisnika.getString("IME");
			String prezime = resultKorisnika.getString("PREZIME");
			Long oib = resultKorisnika.getLong("OIB");
			String mail = resultKorisnika.getString("MAIL");
			Integer mobitel = resultKorisnika.getInt("MOBITEL");
			Date datum = resultKorisnika.getDate("DATUM_UCLANJENJA");
			LocalDate datumUclanjenja = LocalDate.parse(datum.toString());
			datumUclanjenja.format(dtf);
			
			Korisnik korisnik = new Korisnik(ime, prezime, mail, mobitel, oib, datumUclanjenja, barcodeId);
			korisnik.setID(ID);
			listaKorisnika.add(korisnik);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaKorisnika;
	}
	
	public static void updateKorisnika(Korisnik korisnik) throws FileNotFoundException, SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertKorisnik = veza
					.prepareStatement("UPDATE KNJIZNICA.KORISNICI SET ID_KORISNIKA = ?, IME = ?, PREZIME = ?, OIB = ?, MAIL = ?,"
							+ "MOBITEL = ?, DATUM_UCLANJENJA = ? WHERE ID = ?");
			insertKorisnik.setLong(1, korisnik.getIdKorisnika());
			insertKorisnik.setString(2,korisnik.getIme());
			insertKorisnik.setString(3, korisnik.getPrezime());
			insertKorisnik.setLong(4, korisnik.getOib());
			insertKorisnik.setString(5, korisnik.getMailAdresa());
			insertKorisnik.setLong(6, korisnik.getBrojMobitela().longValue());
			insertKorisnik.setDate(7, Date.valueOf(korisnik.getDatumUclanjenja()));
			insertKorisnik.setInt(8, korisnik.getID());
			insertKorisnik.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
	}
	
	public static void obrisiKorisnika(Korisnik korisnik) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertKorisnik = veza
					.prepareStatement("DELETE FROM KNJIZNICA.KORISNIK WHERE ID_KORISNICI = ?");
			insertKorisnik.setLong(1, korisnik.getIdKorisnika());
			insertKorisnik.executeUpdate();
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static void spremiTransakciju(IzdanaKnjiga izdanaKnjiga) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertIzdanaKnjiga = veza
					.prepareStatement("INSERT INTO KNJIZNICA.IZDANE_KNJIGE(KORISNIK_ID, KNJIGA_ID, DATUM_POSUDBE, DATUM_POVRATKA,"
							+ " KNJIZNICAR_ID)"
							+ " VALUES (?, ?, ?, ?, ?);");
			insertIzdanaKnjiga.setLong(1, izdanaKnjiga.getKorisnik().getIdKorisnika());
			insertIzdanaKnjiga.setInt(2,izdanaKnjiga.getKnjiga().getID());
			insertIzdanaKnjiga.setString(3, izdanaKnjiga.getDatumPosudbe());
			insertIzdanaKnjiga.setString(4, izdanaKnjiga.getDatumPovratka());
			insertIzdanaKnjiga.setInt(5, izdanaKnjiga.getKnjiznicar().getID());
			insertIzdanaKnjiga.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static List<IzdanaKnjiga> dohvtiTransakciju() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		Statement statementIzdanaKnjiga = veza.createStatement();
		ResultSet resultIzdanaKnjiga = statementIzdanaKnjiga.executeQuery("SELECT * FROM KNJIZNICA.IZDANE_KNJIGE");
		List<IzdanaKnjiga> listaIzdanihKnjiga = new ArrayList<>();
		while (resultIzdanaKnjiga.next()) {
			Integer ID = resultIzdanaKnjiga.getInt("ID");
			Long idKorisnika = resultIzdanaKnjiga.getLong("KORISNIK_ID");
			Integer idKnjige = resultIzdanaKnjiga.getInt("KNJIGA_ID");
			String datumPosudbe = resultIzdanaKnjiga.getString("DATUM_POSUDBE");
			String datumPovratka = resultIzdanaKnjiga.getString("DATUM_POVRATKA");
			Integer knjiznicarId = resultIzdanaKnjiga.getInt("KNJIZNICAR_ID");
			Korisnik korisnik = dohvatiKorisnike().stream().filter(kor->kor.getIdKorisnika().equals(idKorisnika)).findFirst().get();
			Knjiga knjiga = dohvatiKnjige().stream().filter(knj->knj.getID().equals(idKnjige)).findFirst().get();
			Knjiznicar knjiznicar = dohvatiKnjiznicareLog().stream().filter(knjiz->knjiz.getID().equals(knjiznicarId)).findFirst().get();
			IzdanaKnjiga izdanaKnjiga = new IzdanaKnjiga(korisnik, knjiga, datumPosudbe, datumPovratka, knjiznicar);
			izdanaKnjiga.setID(ID);
			listaIzdanihKnjiga.add(izdanaKnjiga);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaIzdanihKnjiga;
	}
	
	public static void updateTransakcija(IzdanaKnjiga izdanaKnjiga) throws FileNotFoundException, SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertIzdanaKnjiga = veza
					.prepareStatement("UPDATE KNJIZNICA.IZDANE_KNJIGE SET KORISNIK_ID = ?, KNJIGA_ID = ?, DATUM_POSUDBE = ?,"
							+ " DATUM_POVRATKA = ?, KNJIZNICAR_ID = ? WHERE ID = ?");
			insertIzdanaKnjiga.setLong(1, izdanaKnjiga.getKorisnik().getIdKorisnika());
			insertIzdanaKnjiga.setInt(2,izdanaKnjiga.getKnjiga().getID());
			insertIzdanaKnjiga.setString(3, izdanaKnjiga.getDatumPosudbe());
			insertIzdanaKnjiga.setString(4, izdanaKnjiga.getDatumPovratka());
			insertIzdanaKnjiga.setInt(5, izdanaKnjiga.getKnjiznicar().getID());
			insertIzdanaKnjiga.setInt(6, izdanaKnjiga.getID());
			insertIzdanaKnjiga.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
	}
	

}
