package kulturneManifestacije;

import java.sql.*;
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
			case "x":
				System.out.println("Izlaz.");
				break;
			default:
				System.out.println("Nije dobra opcija.");
			}

		} while (!opcija.equalsIgnoreCase("x"));

	}

}
