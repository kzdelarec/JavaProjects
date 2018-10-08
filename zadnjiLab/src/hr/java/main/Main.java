package hr.java.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	static List<String> stopList = new ArrayList<>();
	static List<Word> wordList1 = new ArrayList<>();
	static List<Word> wordList2 = new ArrayList<>();
	static List<String> missing1 = new ArrayList<>();
	static List<String> missing2 = new ArrayList<>();

	public static void main(String[] args) throws FileNotFoundException {

		String firstText;
		String secondText;
		Scanner input = new Scanner(System.in);

		readStopWords();
		readText1();
		readText2();
		fillListsWithMissingWords();
		calculateSSD();
		calculateJaccard();
	}

	public static void readStopWords() {
		try (Stream<String> stream = Files.lines(new File("dat/stopWords.txt").toPath())) {
			stopList = stream.collect(Collectors.toList());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static void readText1() throws FileNotFoundException {

		File mailFile = new File("dat/text1.txt");
		Scanner scanner = new Scanner(mailFile);

		while (scanner.hasNext()) {
			Boolean exists = false;
			String[] tokens = scanner.next().split("[\n\\s,.\\(\\)]");
			String last = tokens[tokens.length - 1].toLowerCase();
			if (!stopList.contains(last)) {
				for (int i = 0; i < wordList1.size(); i++) {
					if (wordList1.get(i).getWord().equals(last)) {
						wordList1.get(i).setFreq(wordList1.get(i).getFreq() + 1);
						exists = true;
					}

				}
				if (!exists) {
					Word newWord = new Word();
					newWord.setWord(last);
					wordList1.add(newWord);
				}

			}

		}

		

	}

	public static void readText2() throws FileNotFoundException {

		File mailFile = new File("dat/text2.txt");
		Scanner scanner = new Scanner(mailFile);

		while (scanner.hasNext()) {
			Boolean exists = false;
			String[] tokens = scanner.next().split("[\n\\s,.\\(\\)!]");
			String last = tokens[tokens.length - 1].toLowerCase();
			if (!stopList.contains(last)) {
				for (int i = 0; i < wordList2.size(); i++) {
					if (wordList2.get(i).getWord().equals(last)) {
						wordList2.get(i).setFreq(wordList2.get(i).getFreq() + 1);
						exists = true;
					}

				}
				if (!exists) {
					Word newWord = new Word();
					newWord.setWord(last);
					wordList2.add(newWord);
				}

			}

		}

		

	}

	public static void fillListsWithMissingWords () {
		for (Word w : wordList1) {
			missing1.add(w.word);
		}
		for (Word w : wordList2) {
			missing2.add(w.word);
		}
		for (String w : missing1) {
			if(!missing2.contains(w)) {
				Word nw = new Word();
				nw.setWord(w);
				nw.setFreq(0);
				wordList2.add(nw);
			}
		}
		
		for (String w : missing2) {
			if(!missing1.contains(w)) {
				Word nw = new Word();
				nw.setWord(w);
				nw.setFreq(0);
				wordList1.add(nw);
			}
		}
		
	}
	
	public static void calculateSSD() {
		wordList1 = wordList1.stream().sorted((o1, o2)->o1.getWord().
                compareTo(o2.getWord())).
                collect(Collectors.toList());
		for (Word w : wordList1) {
			System.out.println(w.getWord() + " - freq: " + w.getFreq());
		}
		System.out.println("");
		wordList2 = wordList2.stream().sorted((o1, o2)->o1.getWord().
                compareTo(o2.getWord())).
                collect(Collectors.toList());
		for (Word w : wordList2) {
			System.out.println(w.getWord() + " - freq: " + w.getFreq());
		}
		double ssd = 0;
		for(int i = 0; i < wordList1.size();i++) {
			ssd+=Math.pow(wordList1.get(i).getFreq()-wordList2.get(i).getFreq(),2);
		}
		
		System.out.println("\nSSD: " + ssd);
	}
	
	public static void calculateJaccard() {
		
		double jaccard = 0;
		Integer brojnik = 0;
		Integer nazivnik = 0;
		Integer a = 0;
		Integer b = 0;
		
		if(wordList1.isEmpty() && wordList2.isEmpty()) {
			jaccard = 1;
		}
		else {
			for(int i = 0; i < wordList1.size();i++) {
				for(int j = 0; j < wordList2.size();j++) {
					if(wordList1.get(i).getFreq() > 0 
							&& wordList2.get(j).getWord().equals(wordList1.get(i).getWord()) 
							&& wordList2.get(j).getFreq()>0) {
						brojnik += (wordList1.get(i).getFreq());
					}
				}
			}
			
			
			for(int i = 0; i< wordList1.size();i++) {
				a += wordList1.get(i).getFreq();
			}
			for(int i = 0; i< wordList2.size();i++) {
				b += wordList2.get(i).getFreq();
			}
			

			nazivnik = a+b-brojnik;
			
			jaccard = brojnik.doubleValue()/nazivnik.doubleValue();
		}
		
		System.out.println("Jaccardov index: " + jaccard);
		
			
	}

}
