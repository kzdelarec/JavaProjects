package hr.java.vjezbe.niti;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import hr.java.vjezbe.entitet.RadSenzora;
import hr.java.vjezbe.entitet.Senzor;
import hr.java.vjezbe.entitet.SenzorTemperature;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SenzoriNit implements Runnable {

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

	public List<Senzor> dohvatiSenzore() throws SQLException, IOException {
		List<Senzor> listaSenzora = new ArrayList<>();
		// senzor temp
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementSenzorTopline = veza.createStatement();
		ResultSet resultSetSenzorTemp = statementSenzorTopline
				.executeQuery("SELECT*  FROM POSTAJE.SENZOR_TEMP WHERE VRIJEDNOST < 0 AND AKTIVAN = TRUE;");
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
		}
		Platform.runLater(new Runnable() {
			public void run() {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Informacija o senzoru");
				alert.setHeaderText("Senzor ispod nule! \t"+LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
				listaSenzora.forEach(senzor->alert.setContentText(alert.getContentText()+
						"\nID: " + senzor.getId() + "\t Vrijednost: " + senzor.getVrijednost()+"°C"));
				alert.show();
			}
		});
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaSenzora;

	}

	@Override
	public void run() {
		try {
			Thread.sleep(10*1000);
			dohvatiSenzore();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
