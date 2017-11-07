package kulturneManifestacije;

public class Grad {
	private int pttbr;
	private String naziv;
	public Grad(int pttbr, String naziv) {
		super();
		this.pttbr = pttbr;
		this.naziv = naziv;
	}
	public Grad() {
		super();
	}
	public int getPttbr() {
		return pttbr;
	}
	public void setPttbr(int pttbr) {
		this.pttbr = pttbr;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	@Override
	public String toString() {
		return pttbr + " " + naziv ;
	}

	
}
