package ht.java.pictionary.javafx;

public class PictionaryWord {
	Integer ID;
	String word;
	String translatin;
	String difficulty;
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}

	public String getTranslatin() {
		return translatin;
	}

	public void setTranslatin(String translatin) {
		this.translatin = translatin;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public PictionaryWord(Integer ID, String word, String translation, String difficulty) {
		this.ID = ID;
		this.word = word;
		this.translatin = translation;
		this.difficulty = difficulty;
	}
}
