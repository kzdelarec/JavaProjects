package tvz.ai.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Main {
	
	public static final int VEL_POPULACIJE = 20;
	public static final double VJ_KRIZANJA = 0.9;
	public static final double VJ_MUTACIJE = 0.01;
	/**
	 * 
	 */
	public static final int BR_ITERACIJA = 30;

	public static Random rand = new Random();
	public static List<Jedinka> listaJedinki = new ArrayList<>();
	public static List<Populacija> listaPopulacija = new ArrayList<>();
	public static Jedinka elitnaJedinka;
	public static Jedinka jedinkaNastalaKrizanjem;

	
	
	public static void main(String[] args) {
		
		for(int x = 0; x < BR_ITERACIJA; x++) 
		{
			listaJedinki.clear();	
			System.out.println("\nBroj Iteracije: " + (x+1));
			napraviPopulaciju(x);
			elitizam(listaPopulacija.get(x));
			Selekcija(x);
			//Krizanje(x);
						
			for(int i = 0; i < VEL_POPULACIJE; i++) 
			{
				System.out.println(dobrota(listaPopulacija.get(x).sveJedinke.get(i).genDecimal));
				if(listaPopulacija.get(x).sveJedinke.get(i).elitizam)
					System.out.println("Elitna Jedinka: " 
							+ dobrota(listaPopulacija.get(x).sveJedinke.get(i).genDecimal));		
				System.out.println("Vjerojatnost da bude izabrana: " + listaPopulacija.get(x).sveJedinke.get(i).vjerojatnostOdabira);
				mutacijaGena(listaJedinki.get(i));
			}
			
			/*for(Jedinka jed: listaJedinki)
			{
				System.out.println(jed.elitizam);
			}		*/
		}
		
		for(int x = 0; x < BR_ITERACIJA; x++) 
		{				
			for(int i = 0; i < VEL_POPULACIJE; i++) 
			{
				System.out.println(dobrota(listaPopulacija.get(x).sveJedinke.get(i).genDecimal));
				if(listaPopulacija.get(x).sveJedinke.get(i).elitizam)
					System.out.println("Elitna Jedinka: " 
							+ dobrota(listaPopulacija.get(x).sveJedinke.get(i).genDecimal));	
			}}
	}
	
	/**Generira 20 jedinki sa 10 random bitova u genu i doda te jedinke u populaciju. 
	 * 
	 */
	public static void napraviPopulaciju(int x)
	{
		int sumDobrota = 0;
		Populacija populacija = new Populacija();
				
		for(int i = 0; i<VEL_POPULACIJE; i++)
		{
			Jedinka jedinka = new Jedinka();
			
			//if(x==0) {
			jedinka.genDecimal = rand.nextInt(1024);
			jedinka.genBinary = String.format("%10s", Integer.toBinaryString(jedinka.genDecimal))
										.replace(' ', '0');			
			jedinka.dobrota = dobrota(jedinka.genDecimal);			
			sumDobrota += jedinka.dobrota;
			//}
			
			if(x>0) {	
				jedinka.genDecimal = rand.nextInt(1024);
				jedinka.genBinary = String.format("%10s", Integer.toBinaryString(jedinka.genDecimal))
											.replace(' ', '0');			
				
				if(i==19) {
				jedinka.genDecimal = listaPopulacija.get(x-1).sveJedinke.get(i).genDecimal;
				jedinka.genBinary = String.format("%10s", Integer.toBinaryString(jedinka.genDecimal))
						.replace(' ', '0');}
				jedinka.dobrota = dobrota(jedinka.genDecimal);			
				sumDobrota += jedinka.dobrota;
				}
			
			listaJedinki.add(jedinka);
			//System.out.println(jedinka.genBinary);
			populacija.setSveJedinke(jedinka);
		}
		
		
		populacija.srednjaDobrota = sumDobrota/VEL_POPULACIJE;
		
		for(int i = 0;i<VEL_POPULACIJE;i++)
		{
			populacija.sveJedinke.get(i).vjerojatnostOdabira = (2*3.14/VEL_POPULACIJE) * (listaJedinki.get(i).dobrota /populacija.srednjaDobrota);
		}	
		listaPopulacija.add(populacija);
	}
	
	/**Provjerava koja jedinka ima najvisu dobrotu te je oznacava kao elitnu. 
	 * Radi tako sto sortira jedinke po dobroti pa je zadnja jedinka ujedno i elitna.
	 * @param populacija - trenutna populacija koju gledamo
	 */
	public static void elitizam(Populacija populacija)
	{		
		//Sort jedinki u populaciji
		Collections.sort(populacija.sveJedinke, new Comparator<Jedinka>() {
			@Override
			public int compare(Jedinka arg0, Jedinka arg1) {
				if(dobrota(arg0.genDecimal) < dobrota(arg1.genDecimal))
				return -1;
				else if(arg0.genDecimal > arg1.genDecimal)
					return 1;
				else 
				return 0;
			}
		});
		/*int max = Collections.max(listDobrote);
		System.out.println("MAX je: " + dobrota(max));
		*/
		System.out.println("sortirano");
		//Ispis dobrote za TESTIRANJE
		for(int i = 0; i < VEL_POPULACIJE; i++)
		{	
			
			//System.out.println(populacija.sveJedinke.get(i).dobrota);
		}
				populacija.sveJedinke.get(19).elitizam = true;
				elitnaJedinka = populacija.sveJedinke.get(19);
				System.out.println("MAX dobrota: " + populacija.sveJedinke.get(19).dobrota + "A dobrota sth: " 
				+ dobrota(populacija.sveJedinke.get(19).genDecimal));		
	}

	public static void Selekcija(int index)
	{
		double randFloat = (double) Math.random() * 100;
		System.out.println("Random Float " + (int)randFloat);
		double randFloat2 = (double) Math.random() * 100;
		System.out.println("Random Float2 " + (int)randFloat2);
		String prviRoditelj = "";
		String drugiRoditelj = "";
		
		boolean pronadjenPrvi = false, pronadjenDrugi= false;

		for(int i = 0; i < VEL_POPULACIJE; i++)
		{
			if (listaPopulacija.get(index).sveJedinke.get(i).vjerojatnostOdabira * 100 > (int)randFloat && pronadjenPrvi == false &&
					listaPopulacija.get(index).sveJedinke.get(i).genBinary != drugiRoditelj) {
			 prviRoditelj = listaPopulacija.get(index).sveJedinke.get(i).genBinary;
			System.out.println("Pronadjen prvi: " + listaPopulacija.get(index).sveJedinke.get(i).dobrota);
			pronadjenPrvi = true;
			}
			if (listaPopulacija.get(index).sveJedinke.get(i).vjerojatnostOdabira * 100 > (int)randFloat2 && pronadjenDrugi == false && 
					listaPopulacija.get(index).sveJedinke.get(i).genBinary != prviRoditelj) {
			 drugiRoditelj = listaPopulacija.get(index).sveJedinke.get(i).genBinary;
			System.out.println("Pronadjen drugi" + listaPopulacija.get(index).sveJedinke.get(i).dobrota);
			pronadjenDrugi = true;
			}
			
			if(i == 19 && (pronadjenPrvi == false || pronadjenDrugi == false)) {
				i=0;
				 randFloat = (double) Math.random() * 100;
				System.out.println("Random Float " + (int)randFloat);
				 randFloat2 = (double) Math.random() * 100;
				System.out.println("Random Float2 " + (int)randFloat2);}
		}
	}
	
	
	
	public static void Krizanje(int index) {
		
		if(Math.random() < VJ_KRIZANJA) {
		
		}
	}

	
	/**Nakon selekije i krizanja, generira random broj izmedju 0 i 1 te, ako je ispunjen uvijet,
	 * mijenja bitove u genomu jedinke.
	 * @param jedinka - trenutna jedinka
	 */
	public static void mutacijaGena(Jedinka jedinka)
	{
        char x[] = jedinka.genBinary.toCharArray();
        String string = "";
		for (int i = 0; i < 10; i++) 
		{
            if (Math.random() <= VJ_MUTACIJE) {
                int gen = (int) Math.round(Math.random());
                if(gen == 0)
                x[i] = '0';
                else 
                x[i] = '1';
                string += x[i];
                }
            else 
            	string += jedinka.genBinary.charAt(i);   
		}
		jedinka.genBinary = string;
	}
	
	public static double dobrota(int jedinka)
	{
		// Funkcija racuna dobrotu jedinke (int jedinka) prema funkciji prikaznoj u tekstu zadatka
		// Dozvoljene ulazne vrijednosti su u otvorenom intervalu [0, 1023]
		// Funkcija vraca -1 ako je zadana nedozvoljena vrijednost
		
		if (jedinka < 0 || jedinka >= 1024)
		{
			return -1;
		}

		if (jedinka >= 0 && jedinka < 30)
		{
			return 60.0;
		}
		else if (jedinka >= 30 && jedinka < 90)
		{
			return (double)jedinka + 30.0;
		}
		else if (jedinka >= 90 && jedinka < 120)
		{
			return 120.0;
		}
		else if (jedinka >= 120 && jedinka < 210)
		{
			return -0.83333 * (double)jedinka + 220;
		}
		else if (jedinka >= 210 && jedinka < 270)
		{
			return 1.75 * (double)jedinka - 322.5;
		}
		else if (jedinka >= 270 && jedinka < 300)
		{
			return 150.0;
		}
		else if (jedinka >= 300 && jedinka < 360)
		{
			return 2.0 * (double)jedinka - 450;
		}
		else if (jedinka >= 360 && jedinka < 510)
		{
			return -1.8 * (double)jedinka + 918;
		}
		else if (jedinka >= 510 && jedinka < 630)
		{
			return 1.5 * (double)jedinka - 765;
		}
		else if (jedinka >= 630 && jedinka < 720)
		{
			return -1.33333 * (double)jedinka + 1020;
		}
		else if (jedinka >= 720 && jedinka < 750)
		{
			return 60.0;
		}
		else if (jedinka >= 750 && jedinka < 870)
		{
			return 1.5 * (double)jedinka - 1065;
		}
		else if (jedinka >= 870 && jedinka < 960)
		{
			return -2.66667 * (double)jedinka + 2560;
		}
		else
		{
			return 0;
		}
	}        
}
