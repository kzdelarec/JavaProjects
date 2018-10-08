package tvz.ai.main;

/**Jedinka u populaciji koju proucavamo.
 * Sadrzi binarni genom, gen prikazan u dekadskom brojevnom sustavu, dobrotu 
 * te vjerojatnost odabira direktno povezana sa dobrotom. Ona odredjuje vjerojatnost da jedinka bude izabrana
 * za krizanje u selekciji. Boolean elitizam odredjuje je li jedinka elitna ili ne.
 * @author user
 *
 */
public class Jedinka {
	
	String genBinary;
	int genDecimal;	
	double dobrota;
	
	double vjerojatnostOdabira;	
	boolean elitizam = false;	
}

