package hr.java.vjezbe.entitet;

import java.io.Serializable;

public abstract class BazniEntitet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
