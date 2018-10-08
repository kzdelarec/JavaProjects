package hr.knjiznica.enumeracije;

public enum KategorijaZaPretrazivanjeKnjigaEnum {
	IdKnjige("Barkod"), Naslov("Naslov"), Autor("Autor"), Kategorija("Kategorija"),
	Godina_izdanja("Godina izdanja"), Izdavac("Izdavač");
	
	private final String opis;
	
	KategorijaZaPretrazivanjeKnjigaEnum(final String opis) {
        this.opis = opis;
    }

   
    @Override
    public String toString() {
        return opis;
    }
}

	