package hr.java.vjezbe.entitet;

/**
 * Predstavlja zajednicka svojstva svih korisnika koji ce naslijediti klasu Korisnik
 * 
 * @author Patricija Ku�e
 *
 */

public abstract class Korisnik extends Entitet {
	private String email;
	private String telefon;
	private Long id;	
	//apstraktna funckija preko koje dohvacamo kontakt
	public abstract String dohvatiKontakt();
	
	/**
	 * Inicijalizira podatke o emailu i telefonu korisnika
	 * 
	 * @param email vrijednost emaila korisnika
	 * @param telefon vrijednost telefona korisnika
	 */
	public Korisnik(Long id, String email, String telefon) {
		super(id);
		this.email = email;
		this.telefon = telefon;
		this.id = id;
	}
	
	/**
	 * Dohvaca uneseni mail korisnika
	 * @return email vraca email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Postavlja email korisnika
	 * @param email vraca email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Dohvaca telefon korisnika
	 * @return telefon
	 */
	public String getTelefon() {
		return telefon;
	}
	
	/**
	 * Postavlja telefon korisnika
	 * @param telefon
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
