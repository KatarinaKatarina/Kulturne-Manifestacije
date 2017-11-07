package kulturneManifestacije;

public class KultMan {
	private int id;
	private String naziv;
	private int brPosetilaca;
	private Grad grad;
	public KultMan(int id, String naziv, int brPosetilaca, Grad grad) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.brPosetilaca = brPosetilaca;
		this.grad = grad;
	}
	public KultMan() {
		this.grad = new Grad();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public int getBrPosetilaca() {
		return brPosetilaca;
	}
	public void setBrPosetilaca(int brPosetilaca) {
		this.brPosetilaca = brPosetilaca;
	}
	public Grad getGrad() {
		return grad;
	}
	public void setGrad(Grad grad) {
		this.grad = grad;
	}
	@Override
	public String toString() {
		return id + "\t" + naziv + "\t " + grad.getNaziv() + ",\n  broj posetilaca: " + brPosetilaca;
	}
	
	
	

}
