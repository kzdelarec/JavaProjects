package hr.java.vjezbe.entitet;

public abstract class BazniEntitet {

	Integer id;
	public BazniEntitet( Integer id) {
		this.id = id;
	}
	public BazniEntitet() {
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
