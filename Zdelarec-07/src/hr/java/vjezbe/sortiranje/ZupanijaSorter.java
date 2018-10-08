package hr.java.vjezbe.sortiranje;

import java.util.Comparator;

import hr.java.vjezbe.entitet.Zupanija;

/**
 * Usporeduje jesu li nazivi zupanija jednaki.
 * 
 * @author Kristijan Zdelarec
 * 
 *
 */
public class ZupanijaSorter implements Comparator<Zupanija> {

	public ZupanijaSorter() {
		// TODO Auto-generated constructor stub
	}


	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Zupanija zup1, Zupanija zup2) {
		String imePrve = zup1.getNaziv();
		String imeDruge = zup2.getNaziv();

		return imePrve.compareTo(imeDruge);
	}
}
