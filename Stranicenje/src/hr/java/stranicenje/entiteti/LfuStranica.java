package hr.java.stranicenje.entiteti;

public class LfuStranica {
	
	Integer vrijednost;
	Integer frekvencija = 0;
	Integer vrijeme = 0;
	
	public Integer getVrijeme() {
		return vrijeme;
	}
	public void setVrijeme(Integer vrijeme) {
		this.vrijeme = vrijeme;
	}
	public Integer getVrijednost() {
		return vrijednost;
	}
	public void setVrijednost(Integer vrijednost) {
		this.vrijednost = vrijednost;
	}
	public Integer getFrekvencija() {
		return frekvencija;
	}
	public void setFrekvencija(Integer frekvencija) {
		this.frekvencija = frekvencija;
	}

}
