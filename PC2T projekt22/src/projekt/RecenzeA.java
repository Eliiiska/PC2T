package projekt;

import java.util.Scanner;

public class RecenzeA extends Recenze {

	private static final int MIN = 1;
	public static final int MAX = 10;

	@Override
	public boolean bodoveHodnoceni() {

		System.out.println("Zadejte pocet bodu od 1-10");
		Scanner vstup = Main.getScanner();
		int pocet = vstup.nextInt();

		if (pocet >= MIN && pocet <= MAX) {
			setBody(pocet);
			return true;
		} else {
			System.out.println("Zadali jste cislo z nepaltneho rozsahu! Zadejte správnou hodnotu");
			bodoveHodnoceni();
		}
		return false;
	}

	@Override
	public String toString() {
		return "Pocet bodu: " + getBody() + "\nSlovni hodnoceni: " + getHodnoceni();
	}


}
