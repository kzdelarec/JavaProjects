package hr.java.vjezbe.entitet;

public interface RadioSondaza {
	void podesiVisinuPostaje(int visinaPostaje);

	int dohvatiVisinuPostaje();

	private void provjeriVisinu(int visinaPostaje) {
		if (visinaPostaje > 1000)
			visinaPostaje = 1000;
	}

	default int povecajVisinu(int visinaPostaje) {
		visinaPostaje=visinaPostaje+1;
		provjeriVisinu(visinaPostaje);
		return visinaPostaje;
	}

}
