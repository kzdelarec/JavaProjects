package hr.java.vjezbe.niti;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import hr.java.vjezba.javafx.PocetniEkranController;
import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Zupanija;
import javafx.application.Platform;

public class ZadnjaZupnijaNit implements Runnable {
	
	private static final String DATABASE_FILE = "bazaPodataka.properties";

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
	//SELECT * FROM    TABLE WHERE   ID = (SELECT MAX(ID)  FROM TABLE)
	
	public  List<Zupanija> dohvatiZupanije() throws SQLException, IOException {
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementZupanije = veza.createStatement();
		ResultSet resultSetZupaije = statementZupanije.executeQuery("SELECT * FROM POSTAJE.ZUPANIJA WHERE ID = (SELECT MAX(ID) FROM POSTAJE.ZUPANIJA)");
		List<Zupanija> listaZupanija = new ArrayList<>();
		//List<Drzava> drzave = dohvatiDrzave();
		while (resultSetZupaije.next()) {
			Integer zupanijaId = resultSetZupaije.getInt("ID");
			String naziv = resultSetZupaije.getString("NAZIV");
			int id = Integer.parseInt(resultSetZupaije.getString("DRZAVA_ID"));
			Zupanija zupanija = new Zupanija(naziv, BazaPodataka.dohvatiDrzave().stream().
				    filter(drz->drz.getId()==id).
				    findFirst().get());
			zupanija.setId(zupanijaId);
			listaZupanija.add(zupanija);
			System.out.println(zupanija.getNaziv());
			/*Platform.runLater(new Runnable() {
			    @Override public void run() {
			    	
					 
			    	
			    }
			});*/
			
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		
		
		return listaZupanija;
	}

	@Override
	public void run() {
		try {
			dohvatiZupanije();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
