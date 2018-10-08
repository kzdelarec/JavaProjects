package tvz.ai.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**Objekt populacija sadrzi 30 jedinki generiranih i unesenih u listu.
 * @author user
 *
 */
public class Populacija {
	
	List<Jedinka> sveJedinke = new ArrayList<>();
	double srednjaDobrota;

	public List<Jedinka> getSveJedinke() {
		return sveJedinke;
	}

	public void setSveJedinke(Jedinka jedinka) {
		sveJedinke.add(jedinka);
	}
	
	public String toString()
	{
		return "RADI";
	}
	
}
