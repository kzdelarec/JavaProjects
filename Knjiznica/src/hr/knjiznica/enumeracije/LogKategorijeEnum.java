package hr.knjiznica.enumeracije;

public enum LogKategorijeEnum {

	IdKorisnika("Id korisnika"), IdKnjige("Barkod"), Naslov("Naslov"), Autor("Autor"), DatumPosudbe("Datum posudbe"), DatumPovratka("Datum povratka"),
	KorisnickoIme("Korisnicko ime knjiznicara");
	
	private final String opis;
	
	LogKategorijeEnum(final String opis) {
        this.opis = opis;
    }

   
    @Override
    public String toString() {
        return opis;
    }
}
