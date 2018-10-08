package hr.java.fuzzy;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		double kolicinaRublja = 0;
		double razinaZaprljanosti = 0;
		
		System.out.println("Količina rublja(kg): ");
		Scanner unos = new Scanner(System.in);
		kolicinaRublja = unos.nextDouble();
		System.out.println("Razina zaprljanosti (0-8): ");
		razinaZaprljanosti = unos.nextDouble();
		unos.close();
		System.out.println(defuzzify(zakljucivanje(fuzzify_laundry(kolicinaRublja), fuzzify_dirty(razinaZaprljanosti))));
	}
	
	private static String fuzzify_laundry(double value)
	{
		if (value < 3d) return "malo";
		else if (value >= 3d && value < 5d) return "srednje";
		else return "puno";
	}

	private static String fuzzify_dirty(double value)
	{
		if (value < 5d) return "malo";            
		else return "puno";
	}

	private static String zakljucivanje(String rublje, String zmazano) {
		
		if(rublje=="malo" && zmazano == "malo")
			return "malo";
		else if (rublje=="srednje" && zmazano == "malo")
			return "srednje";
		else if (rublje=="puno" && zmazano == "malo")
			return "srednje";
		else if (rublje=="malo" && zmazano == "puno")
			return "srednje";
		else if (rublje=="srednje" && zmazano == "puno")
			return "puno";
		else if(rublje=="puno" && zmazano == "puno")
			return "puno";
		else
			return "puno";
	}
	
	private static double defuzzify(String s)
	{
		if (s == "malo")
		{
			return 30d;
		}
		else if (s == "srednje")
		{
			return 90d;
		}
		else
		{
			return 150d;
		}
	}

}
