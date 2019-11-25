package hr.java.vjezbe.utils;

import java.util.ArrayList;
import java.util.StringTokenizer;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.Kategorija;

public class Utils {

	public static ArrayList<Long> rowToArray(String row, String delimiter){
		
		ArrayList<Long> numbers = new ArrayList<>();
		
		StringTokenizer tokenizer = new StringTokenizer(row, delimiter);
		while (tokenizer.hasMoreTokens()) {
			numbers.add(Long.parseLong(tokenizer.nextToken()));
		}
		
		return numbers;
	}

	public static <T extends Entitet> Entitet getObjectById(ArrayList<T> list, Long id) {
		
		T entitet = null;
		
		for (T e : list) {
			if (e.getId().equals(id)) {
				 entitet = e;
			} 
		}
		
		return entitet;
		
	}
	
public static Kategorija<Artikl> getKategorijaById(ArrayList<Kategorija<Artikl>> list, Long id) {	
		for (Kategorija<Artikl> k : list) {
			if (k.getId().equals(id)) {
				return k;
			} 
		}
		
		return null;
		
	}
	
}
