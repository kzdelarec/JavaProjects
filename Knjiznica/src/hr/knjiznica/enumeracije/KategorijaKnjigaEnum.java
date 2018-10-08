package hr.knjiznica.enumeracije;

public enum KategorijaKnjigaEnum {
	Roman("Roman"),Fantasticni_roman("Fantastični roman"),Djecji_roman("Dječji roman"),Akcijski("Akcijski roman"), 
	Ljubavni("Ljubavni roman"), Krimic("Kriminalistički roman"), Pustolovni ("Pustolovni roman"),
	Enciklopedija("Enciklopedija"), Edukativni_sadrzaj("Edukativni sadržaj"), Filozofija("Filozofija"), Bajka("Bajka"), Lirika("Lirika"),
	Epika("Epika"), Drama("Drama"),Novela("Novela"), Psihologija("Psihologija"), Ostalo("Ostalo");
	
	private final String opis;

    
	KategorijaKnjigaEnum(final String opis) {
        this.opis = opis;
    }

   
    @Override
    public String toString() {
        return opis;
    }
}
