package hr.java.vjezbe.baza.podataka;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import hr.java.vjezbe.entitet.Drzava;
import hr.java.vjezbe.entitet.GeografskaTocka;
import hr.java.vjezbe.entitet.MjernaPostaja;
import hr.java.vjezbe.entitet.Mjesto;
import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import hr.java.vjezbe.entitet.SenzorTlakaZraka;
import hr.java.vjezbe.entitet.SenzorVlage;
import hr.java.vjezbe.entitet.VrstaMjesta;
import hr.java.vjezbe.entitet.Zupanija;

public class BazaPodataka {
	private static final String DATABASE_FILE = "bazaPodataka.properties";
	public static List<SenzorTemperature> listaTemp = new ArrayList<>();
	public static List<SenzorTlakaZraka> listaTlaka = new ArrayList<>();
	public static List<SenzorVlage> listaVlage = new ArrayList<>();

	// spajanje na bazu

	private static Connection spajanjeNaBazuPodataka() throws SQLException, FileNotFoundException, IOException {
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

	// spremanje u bazu

	public static void spremiDrzavu(Drzava drzava) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertDrzava = veza
					.prepareStatement("INSERT INTO POSTAJE.DRZAVA(NAZIV, POVRSINA) VALUES (?, ?);");
			insertDrzava.setString(1, drzava.getNaziv());
			insertDrzava.setDouble(2, drzava.getPovrsina().doubleValue());
			insertDrzava.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	public static void spremiZupaniju(Zupanija zupanija) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertZupaija = veza
					.prepareStatement("INSERT INTO POSTAJE.ZUPANIJA(NAZIV, DRZAVA_ID) VALUES (?, ?);");
			insertZupaija.setString(1, zupanija.getNaziv());
			insertZupaija.setInt(2, zupanija.getDrzava().getId());
			insertZupaija.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	public static void spremiMjesto(Mjesto mjesto) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertMjesto = veza
					.prepareStatement("INSERT INTO POSTAJE.MJESTO(NAZIV, VRSTA, ZUPANIJA_ID) VALUES (?, ?, ?);");
			insertMjesto.setString(1, mjesto.getNaziv());
			insertMjesto.setString(2, mjesto.getVrstaMjest().name());
			insertMjesto.setInt(3, mjesto.getZupanija().getId());
			insertMjesto.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	public static void spremiMjernuPostaju(MjernaPostaja mjernaPostaja) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insetMjernaPostaja = veza.prepareStatement("INSERT INTO POSTAJE.MJERNA_POSTAJA"
					+ "(NAZIV, MJESTO_ID, LAT, LNG, SENZOR_PRVI_ID,SENZOR_DRUGI_ID,SENZOR_TRECI_ID)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?);");
			insetMjernaPostaja.setString(1, mjernaPostaja.getNaziv());
			insetMjernaPostaja.setInt(2, mjernaPostaja.getMjesto().getId());
			insetMjernaPostaja.setDouble(3, mjernaPostaja.getGeografskaTocka().getX().doubleValue());
			insetMjernaPostaja.setDouble(4, mjernaPostaja.getGeografskaTocka().getY().doubleValue());
			insetMjernaPostaja.setInt(5, mjernaPostaja.getSviSenzori().get(0).getId());
			insetMjernaPostaja.setInt(6, mjernaPostaja.getSviSenzori().get(1).getId());
			insetMjernaPostaja.setInt(7, mjernaPostaja.getSviSenzori().get(2).getId());
			insetMjernaPostaja.executeUpdate();
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	public static void spremiSenzorTemp(SenzorTemperature senzor) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insetSenzor = veza.prepareStatement(
					"INSERT INTO POSTAJE.SENZOR_TEMP(KOMPONENTA, VRIJEDNOST, RAD_SENZORA) VALUES (?, ?, ?);");
			insetSenzor.setString(1, senzor.getNazivKomponente());
			insetSenzor.setDouble(2, senzor.getVrijednost().doubleValue());
			insetSenzor.setString(3, senzor.getRadSenzora().name());
			insetSenzor.executeUpdate();
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	public static void spremiSenzorTlaka(SenzorTlakaZraka senzor) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insetSenzor = veza.prepareStatement(
					"INSERT INTO POSTAJE.SENZOR_TLAKA(OPIS, VRIJEDNOST, RAD_SENZORA) VALUES (?, ?, ?);");
			insetSenzor.setString(1, senzor.getOpis());
			insetSenzor.setDouble(2, senzor.getVrijednost().doubleValue());
			insetSenzor.setString(3, senzor.getRadSenzora().name());
			insetSenzor.executeUpdate();
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	public static void spremiSenzorVlage(SenzorVlage senzor) throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insetSenzor = veza
					.prepareStatement("INSERT INTO POSTAJE.SENZOR_VLAGE(VRIJEDNOST, RAD_SENZORA) VALUES (?, ?);");
			insetSenzor.setDouble(1, senzor.getVrijednost().doubleValue());
			insetSenzor.setString(2, senzor.getRadSenzora().name());
			insetSenzor.executeUpdate();
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	// citanje iz baze

	public static List<Drzava> dohvatiDrzave() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementDrzave = veza.createStatement();
		ResultSet resultSetDrzave = statementDrzave.executeQuery("SELECT * FROM POSTAJE.DRZAVA");
		List<Drzava> listaDrzava = new ArrayList<>();
		while (resultSetDrzave.next()) {
			Integer drzavaId = resultSetDrzave.getInt("ID");
			String naziv = resultSetDrzave.getString("NAZIV");
			BigDecimal povrsina = new BigDecimal(resultSetDrzave.getDouble("POVRSINA"));
			Drzava drzava = new Drzava(naziv, povrsina);
			drzava.setId(drzavaId);
			listaDrzava.add(drzava);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaDrzava;
	}
	
	public static List<Zupanija> dohvatiZupanije() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementZupanije = veza.createStatement();
		ResultSet resultSetZupaije = statementZupanije.executeQuery("SELECT * FROM POSTAJE.ZUPANIJA");
		List<Zupanija> listaZupanija = new ArrayList<>();
		//List<Drzava> drzave = dohvatiDrzave();
		while (resultSetZupaije.next()) {
			Integer zupanijaId = resultSetZupaije.getInt("ID");
			String naziv = resultSetZupaije.getString("NAZIV");
			int id = Integer.parseInt(resultSetZupaije.getString("DRZAVA_ID"));
			Zupanija zupanija = new Zupanija(naziv, dohvatiDrzave().stream().
				    filter(drz->drz.getId()==id).
				    findFirst().get());
			zupanija.setId(zupanijaId);
			listaZupanija.add(zupanija);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaZupanija;
	}
	
	public static List<Mjesto> dohvatiMjesto() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementMjesto = veza.createStatement();
		ResultSet resultSetMjesto = statementMjesto.executeQuery("SELECT * FROM POSTAJE.MJESTO");
		List<Mjesto> listaMjesta = new ArrayList<>();
		while (resultSetMjesto.next()) {
			Integer mjestoId = resultSetMjesto.getInt("ID");
			String naziv = resultSetMjesto.getString("NAZIV");
			int id = Integer.parseInt(resultSetMjesto.getString("ZUPANIJA_ID"));
			Mjesto mjesto = new Mjesto(naziv, dohvatiZupanije().stream().
				    filter(zup->zup.getId()==id).
				    findFirst().get());
			for (VrstaMjesta vrsta : VrstaMjesta.values()) {
				if (resultSetMjesto.getString("VRSTA").equals(vrsta.name())) {
					mjesto.setVrstaMjest(vrsta);
				}
			}
			mjesto.setId(mjestoId);
			listaMjesta.add(mjesto);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaMjesta;
	}
	
	public static List<Senzor> dohvatiSenzore() throws SQLException, IOException {
		List<Senzor> listaSenzora = new ArrayList<>();
		listaTemp.clear();
		listaTlaka.clear();
		listaVlage.clear();
		//senzor temp
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementSenzorTopline = veza.createStatement();
		ResultSet resultSetSenzorTemp = statementSenzorTopline.executeQuery("SELECT * FROM POSTAJE.SENZOR_TEMP");
		while (resultSetSenzorTemp.next()) {
			Integer tempId = resultSetSenzorTemp.getInt("ID");
			String komponenta = resultSetSenzorTemp.getString("KOMPONENTA");
			BigDecimal vrijednost = new BigDecimal(resultSetSenzorTemp.getString("VRIJEDNOST"));
			SenzorTemperature senzorTemp = new SenzorTemperature("°C", (byte) 2, komponenta);
			senzorTemp.setId(tempId);
			for (RadSenzora rad : RadSenzora.values()) {
				if (resultSetSenzorTemp.getString("RAD_SENZORA").equals(rad.name())) {
					senzorTemp.setRadSenzora(rad);
				}
			}
			senzorTemp.setVrijednost(vrijednost);
			listaSenzora.add(senzorTemp);
			listaTemp.add(senzorTemp);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
		//senzor tlaka
		Connection veza2 = spajanjeNaBazuPodataka();
		Statement statementSenzorTlaka = veza2.createStatement();
		ResultSet resultSetSenzorTlaka = statementSenzorTlaka.executeQuery("SELECT * FROM POSTAJE.SENZOR_TLAKA");
		while (resultSetSenzorTlaka.next()) {
			Integer tlakId = resultSetSenzorTlaka.getInt("ID");
			String opis = resultSetSenzorTlaka.getString("OPIS");
			BigDecimal vrijednost = new BigDecimal(resultSetSenzorTlaka.getString("VRIJEDNOST"));
			SenzorTlakaZraka senzorTlaka = new SenzorTlakaZraka("hPa", (byte) 4, opis);
			senzorTlaka.setId(tlakId);
			for (RadSenzora rad : RadSenzora.values()) {
				if (resultSetSenzorTlaka.getString("RAD_SENZORA").equals(rad.name())) {
					senzorTlaka.setRadSenzora(rad);
				}
			}
			senzorTlaka.setVrijednost(vrijednost);
			listaSenzora.add(senzorTlaka);
			listaTlaka.add(senzorTlaka);
		}
		zatvaranjeVezeNaBazuPodataka(veza2);
		
		//senzor vlage
		Connection veza3 = spajanjeNaBazuPodataka();
		Statement statementSenzorVlage = veza3.createStatement();
		ResultSet resultSetSenzorVlage = statementSenzorVlage.executeQuery("SELECT * FROM POSTAJE.SENZOR_VLAGE");
		while (resultSetSenzorVlage.next()) {
			Integer vlagaId = resultSetSenzorVlage.getInt("ID");
			BigDecimal vrijednost = new BigDecimal(resultSetSenzorVlage.getString("VRIJEDNOST"));
			SenzorVlage senzorVlage = new SenzorVlage("%", (byte)3);
			senzorVlage.setId(vlagaId);
			for (RadSenzora rad : RadSenzora.values()) {
				if (resultSetSenzorVlage.getString("RAD_SENZORA").equals(rad.name())) {
					senzorVlage.setRadSenzora(rad);
				}
			}
			senzorVlage.setVrijednost(vrijednost);
			listaSenzora.add(senzorVlage);
			listaVlage.add(senzorVlage);
		}
		zatvaranjeVezeNaBazuPodataka(veza3);
		
		return listaSenzora;
		
	}
	
	public static List<MjernaPostaja> dohvatiPostaje() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementPostaja = veza.createStatement();
		ResultSet resultSetPostaja = statementPostaja.executeQuery("SELECT * FROM POSTAJE.MJERNA_POSTAJA");
		List<MjernaPostaja> listaPostaja = new ArrayList<>();
		while (resultSetPostaja.next()) {
			List<Senzor> senzori = new ArrayList<>();
			Integer postajaId = resultSetPostaja.getInt("ID");
			String naziv = resultSetPostaja.getString("NAZIV");
			int idMjesta = Integer.parseInt(resultSetPostaja.getString("MJESTO_ID"));
			Mjesto mjesto = dohvatiMjesto().stream().filter(mj->mj.getId()==idMjesta).findFirst().get();
			BigDecimal geoX = new BigDecimal(resultSetPostaja.getString("LAT"));
			BigDecimal geoY = new BigDecimal(resultSetPostaja.getString("LNG"));
			GeografskaTocka geo = new GeografskaTocka(geoX, geoY);
			int idTemp = Integer.parseInt(resultSetPostaja.getString("SENZOR_PRVI_ID"));
			SenzorTemperature temp = listaTemp.stream().filter(tmp->tmp.getId()==idTemp).findFirst().get();
			senzori.add(temp);
			int idTlak = Integer.parseInt(resultSetPostaja.getString("SENZOR_DRUGI_ID"));
			SenzorTlakaZraka tlak = listaTlaka.stream().filter(tl->tl.getId()==idTlak).findFirst().get();
			senzori.add(tlak);
			int idVlaga = Integer.parseInt(resultSetPostaja.getString("SENZOR_TRECI_ID"));
			SenzorVlage vlaga = listaVlage.stream().filter(vl->vl.getId()==idVlaga).findFirst().get();
			senzori.add(vlaga);
			MjernaPostaja postaja = new MjernaPostaja(naziv, mjesto, geo, senzori);
			postaja.setId(postajaId);
			listaPostaja.add(postaja);
	
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaPostaja;
	}

	
	public static void updateDrzava(Drzava drzava) throws FileNotFoundException, SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertDrzava = veza
					.prepareStatement("UPDATE POSTAJE.DRZAVA SET NAZIV = ?, POVRSINA = ? WHERE ID = ?");
			insertDrzava.setString(1, drzava.getNaziv());
			insertDrzava.setDouble(2, drzava.getPovrsina().doubleValue());
			insertDrzava.setInt(3, drzava.getId());
			insertDrzava.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
	}
	

	public static void updateZupanija(Zupanija zupanija) throws FileNotFoundException, SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertZupaija = veza
					.prepareStatement("UPDATE POSTAJE.ZUPANIJA SET NAZIV = ?, DRZAVA_ID = ? WHERE ID = ?");
			insertZupaija.setString(1, zupanija.getNaziv());
			insertZupaija.setInt(2,zupanija.getDrzava().getId());
			insertZupaija.setInt(3, zupanija.getId());
			insertZupaija.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
	}
	
	public static void updateTemp(SenzorTemperature temp) throws FileNotFoundException, SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertZupaija = veza
					.prepareStatement("UPDATE POSTAJE.SENZOR_TEMP SET KOMPONENTA = ?, VRIJEDNOST = ?, RAD = ?, AKTIVAN = ? WHERE ID = ?");
			insertZupaija.setString(1, temp.getNazivKomponente());
			insertZupaija.setDouble(2,temp.getVrijednost().doubleValue());
			insertZupaija.setString(3, temp.getRadSenzora().name());
			insertZupaija.setInt(4, temp.getId());
			insertZupaija.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
	}
	
	public static void updatePostaja(MjernaPostaja postaja) throws FileNotFoundException, SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try {
			PreparedStatement insertPostaja = veza
					.prepareStatement("UPDATE POSTAJE.MJERNA_POSTAJA "
							+ "SET NAZIV = ?, MJESTO_ID = ?,LAT = ?, LNG = ?, SENZOR_PRVI_ID = ?,"
							+ "SENZOR_DRUGI_ID = ?, SENZOR_TRECI_ID = ? WHERE ID = ?");
			insertPostaja.setString(1, postaja.getNaziv());
			insertPostaja.setInt(2,postaja.getMjesto().getId());
			insertPostaja.setDouble(3, postaja.getGeografskaTocka().getX().doubleValue());
			insertPostaja.setDouble(4, postaja.getGeografskaTocka().getY().doubleValue());
			insertPostaja.setInt(5,postaja.getSviSenzori().get(0).getId());
			insertPostaja.setInt(6,postaja.getSviSenzori().get(1).getId());
			insertPostaja.setInt(7,postaja.getSviSenzori().get(2).getId());
			insertPostaja.setInt(8, postaja.getId());
			insertPostaja.executeUpdate();
			
			veza.commit();
			veza.setAutoCommit(true);
		} catch (Throwable ex) {
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
	}
	public static Integer brojPostaja() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementPostaje = veza.createStatement();
		ResultSet resultPostaje = statementPostaje.executeQuery("SELECT COUNT(*) AS BROJ FROM POSTAJE.MJERNA_POSTAJA;");
		while (resultPostaje.next()) {
			return resultPostaje.getInt(1);
			
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return resultPostaje.getInt(1);
	}

}
