package projekt;

import java.util.Scanner;

public abstract class Recenze implements Comparable<Recenze> {

	private String hodnoceni;
	private int body;

	public String otevreneHodnoceni() {
		Scanner vstup = Main.getScanner();
		System.out.println("Zadejte text recenze: ");
		hodnoceni = vstup.next();

		return hodnoceni;
	}

	public void setBody(int body) {
		this.body = body;
	}

	public void setHodnoceni(String hodnoceni) {
		this.hodnoceni = hodnoceni;
	}

	public abstract boolean bodoveHodnoceni();

	public String getHodnoceni() {
		return hodnoceni;
	}

	public int getBody() {
		return body;
	}

	@Override
	public int compareTo(Recenze o) {
		if (getBody() < o.getBody()) {
			return 1;
		} else {
			return -1;
		}
	}
}
