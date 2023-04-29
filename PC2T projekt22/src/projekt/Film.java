package projekt;

import java.util.Set;
import java.util.TreeSet;

public abstract class Film {
	public String druh;
	protected String nazev;
	private String reziser;
	private int rok;
	Set<Recenze> recenzeFilmu = new TreeSet<>();


	public Set<Recenze> getRecenze() {


		return recenzeFilmu;
	}

	public void addRecenze(Recenze recenze) {
		recenzeFilmu.add(recenze);
	}

	public void pridejRecenzi() {
		Recenze recenze;
		if (getDruh().equals("A")) {
			recenze = new RecenzeA();
		} else {
			recenze = new RecenzeH();
		}
		recenze.otevreneHodnoceni();
		recenze.bodoveHodnoceni();
	
	    recenzeFilmu.add(recenze);
		System.out.println("Recenze uspesne pridana.");
	}

	public void vypisRecenze() {


		for (Recenze recenze : recenzeFilmu) {
			System.out.println(recenze.toString());
			System.out.println("\n");
		}
	}


	public Film(String druh, String nazev, String reziser, int rok) {
		this.druh = druh;
		this.nazev = nazev;
		this.reziser = reziser;
		this.rok = rok;

	}

	public String getDruh() {
		String animak = "A";
		String hrany = "H";
		if (druh.equals(animak)) {
			return druh;
		} else if (druh.equals(hrany)) {
			return druh;
		}
		return "";
	}

	public boolean setDruh(String druh) {

		if (druh.equals("A") || druh.equals("H")) {

			return true;
		}
		return false;
	}

	public String getNazev() {
		return nazev;
	}

	public void setNazev(String nazev) {
		this.nazev = nazev;
	}

	public String getReziser() {
		return reziser;
	}

	public void setReziser(String reziser) {
		this.reziser = reziser;
	}

	public int getRok() {
		return rok;
	}

	public void setRok(int rok) {
		this.rok = rok;
	}



}
