package projekt;

import java.util.Scanner;

public class RecenzeH extends Recenze {

	private static final int MIN = 1;
	public static final int MAX = 5;

	@Override
	public boolean bodoveHodnoceni() {

		System.out.println("Zadejte pocet bodu od 1-5");
		Scanner vstup = Main.getScanner();
		int pocet = Main.testInt(vstup);
		if (pocet >= MIN && pocet <= MAX) {
			setBody(pocet);
		} else {
			System.out.println("Zadali jste cislo z nepaltneho rozsahu! Zadejte správnou hodnotu");
			bodoveHodnoceni();
		}
		return true;

	}

	@Override
	public String toString() {
		String hvezdicky = "";
		for (int i = 1; i <= getBody(); i++) {
			hvezdicky += "*";
		}

		return "Pocet bodu: " + hvezdicky + "\nSlovni hodnoceni: " + getHodnoceni();
	}

}



