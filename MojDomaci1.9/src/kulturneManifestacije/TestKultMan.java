package kulturneManifestacije;

import java.lang.Character.UnicodeScript;
import java.lang.annotation.Documented;
import java.sql.*;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Scanner;

public class TestKultMan {
	public static Scanner sc = new Scanner(System.in);
	public static ArrayList<Grad> listaGradova = new ArrayList<>();
	public static ArrayList<KultMan> listaKultMan = new ArrayList<>();

	public static String adress = "jdbc:mysql://localhost:3306/kulturne_malifestacije_u_gradovima";
	public static String user = "kaca";
	public static String pass = "Miskovci24081990";

	public static void ucitajGradove() {
		try {
			Connection conn = DriverManager.getConnection(adress, user, pass);
			System.out.println("Preuzeti podaci iz baze.");
			Statement stm = conn.createStatement();
			String s1 = "SELECT * FROM kulturne_malifestacije_u_gradovima.gradovi";
			ResultSet rez = stm.executeQuery(s1);
			while (rez.next()) {
				int ptt = rez.getInt("ptt");
				String naziv = rez.getString("naziv_grada");
				Grad g = new Grad(ptt, naziv);
				listaGradova.add(g);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void ucitajManifestacije() {
		try {
			Connection conn = DriverManager.getConnection(adress, user, pass);
			Statement stm = conn.createStatement();
			String s2 = "SELECT * FROM kulturne_malifestacije_u_gradovima.manifestacije "
					+ "left join kulturne_malifestacije_u_gradovima.gradovi \r\n"
					+ "on manifestacije.pttGrad = gradovi.ptt";
			ResultSet rez = stm.executeQuery(s2);
			while (rez.next()) {
				int id = rez.getInt("idkultMan");
				String naziv = rez.getString("naziv");
				int brojP = rez.getInt("brojPosetilaca");
				int ptt = rez.getInt("ptt");
				String nazivG = rez.getString("naziv_grada");
				Grad g = new Grad(ptt, nazivG);

				KultMan k = new KultMan(id, naziv, brojP, g);
				listaKultMan.add(k);
			}

		} catch (SQLException e) {
			System.out.println("Nisi se spojila sa bazom.");
		}

	}

	public static void ispisGradova() {
		System.out.println("Prikaz gradova.");
		for (int i = 0; i < listaGradova.size(); i++) {
			System.out.println(listaGradova.get(i));
		}
	}

	public static void ispisManifestacija() {
		System.out.println("Prikaz manifestacija.");
		for (int i = 0; i < listaKultMan.size(); i++) {
			System.out.println(listaKultMan.get(i));
		}
	}

	// postoji grad sa nazivom X
	public static Grad postojiGrad(String grad) {
		for (int i = 0; i < listaGradova.size(); i++) {
			if (listaGradova.get(i).getNaziv().equalsIgnoreCase(grad))
				return listaGradova.get(i);
		}
		System.out.println("Ne postojeci grad.");
		return null;
	}

	// proverava da li je unos integer dobar i pozitivan
	public static boolean isIntegerAndPositive(String string) {
		try {
			int broj = Integer.parseInt(string);
			if (broj > 0) {
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("Unos nije dobar.");
			return false;
		}

	}

	public static KultMan pretraManifestacijePoId() {
		String idStr;
		do {
			System.out.println("Unesite id:");
			idStr = sc.nextLine();
		} while (!isIntegerAndPositive(idStr));
		int id = Integer.parseInt(idStr);
		for (int i = 0; i < listaKultMan.size(); i++) {
			if (listaKultMan.get(i).getId() == id) {
				return listaKultMan.get(i);
			}
		}
		System.out.println("Nema manifestacije sa navedenim id.");
		return null;

	}

	public static boolean dodavanjeManifestacije() {
		String idStr;
		do {
			System.out.println("Unesite id:");
			idStr = sc.nextLine();
		} while (!isIntegerAndPositive(idStr));
		int id = Integer.parseInt(idStr);

		for (int i = 0; i < listaKultMan.size(); i++) {
			if (id == listaKultMan.get(i).getId()) {
				System.out.println("Id vec postoji.");
				return false;
			}
		}
		System.out.println("Unesite naziv:");
		String naziv = sc.nextLine();
		String brStr;
		do {
			System.out.println("Unesite broj posetilaca:");
			brStr = sc.nextLine();
		} while (!isIntegerAndPositive(brStr));
		int br = Integer.parseInt(brStr);
		String grad;
		do {
			System.out.println("Unesite grad:");
			grad = sc.nextLine().trim();
		} while (postojiGrad(grad) == null);

		KultMan k = new KultMan(id, naziv, br, postojiGrad(grad));
		listaKultMan.add(k);

		System.out.println("Uspesno dodato.");
		return true;
	}

	public static boolean izmenaManifestacije() {
		String idStr;
		KultMan k = new KultMan();
		do {
			System.out.println("Unesite id:");
			idStr = sc.nextLine();
		} while (!isIntegerAndPositive(idStr));
		int id = Integer.parseInt(idStr);
		int brojac = 0;
		for (int i = 0; i < listaKultMan.size(); i++) {
			if (id == listaKultMan.get(i).getId()) {
				k = listaKultMan.get(i);
				System.out.println("Trenutno stanje: " + k);
				brojac = 1;
			}
		}
		if (brojac == 0) {
			System.out.println("Ne postoji takva manifestacija.");
			return false;
		}
		System.out.println("Unesite naziv:");
		k.setNaziv(sc.nextLine());
		String brStr;
		do {
			System.out.println("Unesite broj posetilaca:");
			brStr = sc.nextLine();
		} while (!isIntegerAndPositive(brStr));
		int br = Integer.parseInt(brStr);
		k.setBrPosetilaca(br);
		String grad;
		do {
			System.out.println("Unesite grad:");
			grad = sc.nextLine().trim();
		} while (postojiGrad(grad) == null);
		Grad g = postojiGrad(grad);
		k.setGrad(g);
		System.out.println(k);
		System.out.println("Uspesno izmenjeno.");
		return true;
	}

	// brisanje objekta iz liste
	public static boolean brisanje() {

		String idStr;
		do {
			System.out.println("Unesite id:");
			idStr = sc.nextLine();
		} while (!isIntegerAndPositive(idStr));
		int id = Integer.parseInt(idStr);

		for (int i = 0; i < listaKultMan.size(); i++) {
			if (id == listaKultMan.get(i).getId()) {
				listaKultMan.remove(i);
				System.out.println("Uspesno.");
				return true;
			}
		}
		System.out.println("Neuspesno.");
		return false;
	}

	public static void save() {//ili ce se sve odraditi, ili nista -transakcija
		try {
			Connection conn = DriverManager.getConnection(adress, user, pass);
			conn.setAutoCommit(false); //podesavam transakciju
			
			Statement drop = conn.createStatement();
			String s1 = "SET foreign_key_checks = 0";
			String s2 = " truncate gradovi";
			String s4 = "SET foreign_key_checks = 1";
			String s3 = "truncate manifestacije";
			//String s1 = "drop table kulturne_malifestacije_u_gradovima.gradovi";
			//String s2 = "drop table kulturne_malifestacije_u_gradovima.manifestacije";
			drop.executeUpdate(s1);
			drop.executeUpdate(s2);
			drop.executeUpdate(s3);
			drop.executeUpdate(s4);
			
			for (int i = 0; i< listaGradova.size(); i++) {
				PreparedStatement unos = conn.prepareStatement("Insert into gradovi (ptt, naziv_grada) values (?,?)");
				unos.setInt(1,listaGradova.get(i).getPttbr());
				unos.setString(2, listaGradova.get(i).getNaziv());
				unos.executeUpdate();
			}
			
			for(int i = 0; i< listaKultMan.size(); i++) {
				
				PreparedStatement unos = conn.prepareStatement("Insert into manifestacije (idkultMan, naziv, brojPosetilaca, pttGrad)"
						+ " values (?,?,?,?)");
				unos.setInt(1, listaKultMan.get(i).getId());
				unos.setString(2, listaKultMan.get(i).getNaziv());
				unos.setInt(3, listaKultMan.get(i).getBrPosetilaca());
				unos.setInt(4, listaKultMan.get(i).getGrad().getPttbr());
				unos.executeUpdate();
			}
			
			conn.commit();
			System.out.println("Sacuvano.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {

		
		ucitajGradove();
		ucitajManifestacije();

		String opcija = "";
		do {
			System.out.println();
			System.out.println();
			System.out.println("*********************MENI**********************");
			System.out.println("1. Prikaz gradova.");
			System.out.println("2. Prikaz manifestacija.");
			System.out.println("3. Pretraga manifestacije.");
			System.out.println("4. Dodavanje manifestacije.");
			System.out.println("5. Izmena manifestacije.");
			System.out.println("6. Brisanje manifestacije.");
			System.out.println("x. Izlaz.");
			System.out.println(" Unesite opciju:");
			opcija = sc.nextLine();

			switch (opcija) {
			case "1":
				ispisGradova();
				break;
			case "2":
				ispisManifestacija();
				break;
			case "3":
				System.out.println(pretraManifestacijePoId());
				break;
			case "4":
				dodavanjeManifestacije();
				break;
			case "5":
				izmenaManifestacije();
				break;
			case "6":
				brisanje();
				break;
			case "x":
				System.out.println("Izlaz.");
				break;
			default:
				System.out.println("Nije dobra opcija.");
			}

		} while (!opcija.equalsIgnoreCase("x"));

		save();
	}

}
