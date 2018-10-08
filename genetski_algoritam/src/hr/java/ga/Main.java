package hr.java.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
	public static final int velicnaPopulacije = 20;
	public static final int brojIteracija = 30;
	public static final double vjerojatnostKrizanja = 0.9;
	public static final double vjerojatnostMutacije = 0.1;
	

	public static void main(String[] args) {
		Random randomGenerator = new Random();
		List<Jedinka> generacija = new ArrayList<>();
		int generacijaId = 1;
		/***************inicijalizacija pocetne generacije*/
		//System.out.println("POCETNA GENERACIJA");
		double sumDobrota = 0;
		for(int i=0; i<velicnaPopulacije;i++) {
			Jedinka jedinka = new Jedinka();
			
			jedinka.setDecGen(randomGenerator.nextInt(1023));
			jedinka.pretvoriUbin();
			jedinka.setId(i+1);
			generacija.add(jedinka);
			sumDobrota+=jedinka.getDobrotaGena();
			
			System.out.println("Redni broj: " + (i+1)+"\t Dec: " + jedinka.getDecGen() + "\t Bin: " + jedinka.getBinGen() + "\t Dobrota: " + jedinka.getDobrotaGena());
			
		}
		System.out.println("\nUkupna dobrota gneracije: " + sumDobrota);
		/******************************************************************************************************************/
		
		for(int j = 0; j<brojIteracija; j++) {
			sumDobrota=0;
			System.out.println("Generacija br: " + generacijaId);
			/***************sortiranje************************/
			List<Jedinka> sorted = generacija;
			Collections.sort(sorted, new Comparator<Jedinka>() {
			    @Override
			    public int compare(Jedinka c1, Jedinka c2) {
			        return Double.compare(c1.getDobrotaGena(), c2.getDobrotaGena());
			    }
			});
			
			//System.out.println("SORTIRANO");
			for(int i = 0; i< sorted.size();i++) {
				
				//System.out.println("Dec: " + sorted.get(i).getDecGen() + "\t Bin: " + sorted.get(i).getBinGen() + "\t Dobrota: " + sorted.get(i).getDobrotaGena());
			}
			/***********************************************************************************/
			
			
			//******************elitizam***********************
			sorted.get(sorted.size()-1).setElitni(true);
			
			/***************************************************/
			
			
			
			/****************Selekcija**************************/
			
			//System.out.println("SELEKCIJA");
			//System.out.println(idjevi);
			for(int i = 0; i< sorted.size();i++) {
				//System.out.println("Dec: " + sorted.get(i).getDecGen() + "\t Bin: " + sorted.get(i).getBinGen() + "\t Dobrota: " + sorted.get(i).getDobrotaGena());
			}
			
			/******************************************************************/
			
			
			/*******************krizanje*********************/
			Integer sansaZaKrizanje = randomGenerator.nextInt(100);
			List<Integer>idjevi = new ArrayList<>();
			if(sansaZaKrizanje <= 90) {
				
				for(int i=0;i<=5;i++) {
					int random = randomGenerator.nextInt(sorted.size()-2);
					idjevi.add(sorted.get(random).getId());
					sorted.remove(random);
				}
				for(int i=0;i<=5;i++) {
					int x = randomGenerator.nextInt(10);
					int rand1 = randomGenerator.nextInt(sorted.size()-1);
					int rand2 = randomGenerator.nextInt(sorted.size()-1);
					Jedinka novaJedinka = new Jedinka();
					novaJedinka.setBinGen(sorted.get(rand1).getBinGen().substring(0, x)
							+ sorted.get(rand2).getBinGen().substring(x));
					novaJedinka.setDecGen(Integer.parseInt(novaJedinka.getBinGen(),2));
					novaJedinka.setDobrotaGena(novaJedinka.izracunajDobrotu(novaJedinka.getDecGen()));
					novaJedinka.setId(idjevi.get(i));
					sorted.add(novaJedinka);
					/*System.out.println(sorted.get(rand1).getBinGen());
					System.out.println(sorted.get(rand2).getBinGen());
					System.out.println("----------------------------------");
					System.out.println(novaJedinka.getBinGen() +"-> " + novaJedinka.getDecGen());*/
					Collections.sort(sorted, new Comparator<Jedinka>() {
					    @Override
					    public int compare(Jedinka c1, Jedinka c2) {
					        return Double.compare(c1.getDobrotaGena(), c2.getDobrotaGena());
					    }
					});
				}
			}
			idjevi.clear();
			
			/*************************************************/
			
			
			
			/******************mutacija**********************/
			
			for(int k = 0; k < sorted.size()-1;k++) {
				char x[] = sorted.get(k).getBinGen().toCharArray();
		        String string = "";
				for (int i = 0; i < 10; i++) 
				{
		            if (Math.random() <= vjerojatnostMutacije) {
		                int gen = (int) Math.round(Math.random());
		                if(gen == 0)
		                x[i] = '0';
		                else 
		                x[i] = '1';
		                string += x[i];
		                }
		            else 
		            	string += sorted.get(i).getBinGen().charAt(i);   
				}
				sorted.get(k).setBinGen(string);
			}
			
			/************************************************/
			
			for(int i = 0; i<sorted.size();i++) {
				sumDobrota+=sorted.get(i).getDobrotaGena();
				System.out.println("Redni broj: " + (i+1) + "\t Dec: " + sorted.get(i).getDecGen() + "\t Bin: " + sorted.get(i).getBinGen() + "\t Dobrota: " + sorted.get(i).getDobrotaGena());
			}
			System.out.println("\nUkupna dobrota gneracije: " + sumDobrota + "\t prosjecna dobrota: " + sumDobrota/20);
			generacijaId++;
		}
		

	}

	private static double dobrota(int jedinka) {
		// Funkcija računa dobrotu jedinke (int jedinka) prema funkciji prikaznoj u
		// tekstu zadatka
		// Dozvoljene ulazne vrijednosti su u otvorenom intervalu [0, 1023]
		// Funkcija vraća -1 ako je zadana nedozvoljena vrijednost

		if (jedinka < 0 || jedinka >= 1024) {
			return -1;
		}

		if (jedinka >= 0 && jedinka < 30) {
			return 60.0;
		} else if (jedinka >= 30 && jedinka < 90) {
			return (double) jedinka + 30.0;
		} else if (jedinka >= 90 && jedinka < 120) {
			return 120.0;
		} else if (jedinka >= 120 && jedinka < 210) {
			return -0.83333 * (double) jedinka + 220;
		} else if (jedinka >= 210 && jedinka < 270) {
			return 1.75 * (double) jedinka - 322.5;
		} else if (jedinka >= 270 && jedinka < 300) {
			return 150.0;
		} else if (jedinka >= 300 && jedinka < 360) {
			return 2.0 * (double) jedinka - 450;
		} else if (jedinka >= 360 && jedinka < 510) {
			return -1.8 * (double) jedinka + 918;
		} else if (jedinka >= 510 && jedinka < 630) {
			return 1.5 * (double) jedinka - 765;
		} else if (jedinka >= 630 && jedinka < 720) {
			return -1.33333 * (double) jedinka + 1020;
		} else if (jedinka >= 720 && jedinka < 750) {
			return 60.0;
		} else if (jedinka >= 750 && jedinka < 870) {
			return 1.5 * (double) jedinka - 1065;
		} else if (jedinka >= 870 && jedinka < 960) {
			return -2.66667 * (double) jedinka + 2560;
		} else {
			return 0;
		}
	}
}
