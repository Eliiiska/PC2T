package projekt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	private static Scanner scanner = new Scanner(System.in);
	static TabulkaFilmu databazeFilmu = new TabulkaFilmu();
	static int rok = 0;
	static int vek = 0;

	public static Scanner getScanner() {
		return scanner;
	}

	public static int testInt(Scanner vstup) {
		int cisloInt = 0;
		try {
			cisloInt = vstup.nextInt();
		} catch (Exception e) {
			System.out.println("Zadejte pouze celociselnou hodnotu!");
			vstup.next();
			cisloInt = testInt(vstup);
		}
		return cisloInt;

	}

	public static String testString(Scanner vstup) {
		String text;
		text = vstup.next();
		while (true) {
			if (text.matches("[a-zA-Z]+")) {

				break;
			} else {
				System.out.println("Zadejte jmeno ve spravnem formatu. ");

			}
			text = vstup.next(); 
		}
		return text;
	}



	public static void main(String[] args) throws SQLException {

		databazeFilmu.nacteniDB();



		int volba = 0;
		Scanner vstup = scanner;
		String nazev = null;
		boolean aktivniAplikace = true;
		System.out.println("Vitejte v systemu pro spravu filmu!");
		while (aktivniAplikace) {
			System.out.println("Zadejte Vami pozadovanou cinnost: ");
			System.out.println("1 ............ Pridani noveho filmu.");
			System.out.println("2 ............ Uprava filmu.");
			System.out.println("3 ............ Vypis vsech filmu.");
			System.out.println("4 ............ Smazani filmu.");
			System.out.println("5 ............ Vypis konkretniho filmu.");
			System.out.println("6 ............ Ulozeni konkretniho filmu do textoveho souboru.");
			System.out.println("7 ............ Vypis konkretniho filmu z textoveho souboru.");
			System.out.println("8 ............ Vypis filmu daneho ucinkujiciho.");
			System.out.println("9 ............ Vypis ucinkujicich s vice filmy.");
			System.out.println("10 ............Pridej recenzi.");
			System.out.println("11 ............Ukoncit program.");

			volba = testInt(vstup);
			
			switch(volba) {
			case 1:
				addFilm(vstup);
				break;

			case 2:
				editFilm(vstup);
				break;

			case 3:
				databazeFilmu.vypisVsechnyFilmy();
				break;

			case 4:
				System.out.println("Zadejte jmeno filmu, kterehy chcete odstranit. ");
				nazev = vstup.next();
				if (databazeFilmu.vymazFilm(nazev)) {
					System.out.println("Film " + nazev + " byl odstranen.");
				} else {
					System.out.println("Tento Film se nenachazi v databazi. ");
				}
				break;

			case 5:
				System.out.println("Zadejte nazev filmu, o kterem si chcete zobrazit informace: ");
				nazev = vstup.next();
				databazeFilmu.infoOFilmu(nazev);
				break;

			case 6:
				System.out.println("Zadejte nazev filmu ktery chcete ulozit do souboru: ");
				nazev = vstup.next();
				if (databazeFilmu.zapisDoSouboru(nazev)) {
					System.out.println("Bylo uspesne zapsano do souboru");
				}

				break;
				
			case 7:
				System.out.println("Zadejte nazev filmu, ktery chcete vypsat ze souboru");
				nazev = vstup.next();
				databazeFilmu.vypisZeSouboru(nazev);
				break;
				
			case 8:

				System.out.println("Zadejte jmeno herce:");
				String jmeno = vstup.next();
				databazeFilmu.vypisFilmuDleUcinkujiciho(jmeno);
				break;

			case 9:
				databazeFilmu.vypisUcinkujicichSViceFilmy();
				break;

			case 10:
				System.out.println("Zadejte nazev filmu:");
				nazev = vstup.next();
				databazeFilmu.pridejRecenzi(nazev);
				break;

			case 11:
				databazeFilmu.zapisDB();
				aktivniAplikace = false;
				break;
			default:
				System.out.println("Vase nabidka je mimo rozsah!");
			}

		}

	}

	public static void addFilm(Scanner vstup) {
		int volba = 0;
		int pocetAnimatoru = 0;
		String herec = null;

		String animator = null;
		String reziser = null;
		int pocetHercu = 0;
		String druh = null;
		String nazev = null;

		FilmH filmH = new FilmH(null, null, null, 0, null);

		ArrayList<String> seznamHercu = null;
		ArrayList<String> seznamAnimatoru = null;
		System.out.println("Zadejte druh filmu symbolem A (animovany) nebo H (Hrany)");
		druh = vstup.next();
		while (filmH.setDruh(druh) == false) {
			System.out.println("Zadejte nazev filmu ve formatu A nebo H.");
			druh = vstup.next();
			if (filmH.setDruh(druh)) {
				break;
			}
		}

		System.out.println("Zadejte nazev filmu. ");
		nazev = vstup.next();
		System.out.println("Zadejte rezisera filmu. ");
		reziser = testString(vstup);
		System.out.println("Zadejte rok vydani filmu. ");
		rok = testInt(vstup);

		if (druh.equals("H")) {

			System.out.println("Zadejte pocet hercu, ktere chcete zadat: ");
			pocetHercu = testInt(vstup);
			seznamHercu = new ArrayList<>();
			for (int i = 0; i < pocetHercu; i++) {
				System.out.println("Zadejte jmeno herce c. " + (i + 1));

				herec = vstup.next();
				seznamHercu.add(herec);
			}
			databazeFilmu.odstranDuplikaty(seznamHercu);

			if (databazeFilmu.setFilmH(druh, nazev, reziser, rok, seznamHercu) == false) {
				System.out.println("Film se jiz nachazi v databazi.");
			}


		} else {
			System.out.println("Zadejte pocet animatoru, ktere chcete zadat: ");
			pocetAnimatoru = testInt(vstup);
			seznamAnimatoru = new ArrayList<>();
			for (int i = 0; i < pocetAnimatoru; i++) {
				System.out.println("Zadejte jmeno animatora c. " + (i + 1));
				animator = vstup.next();
				seznamAnimatoru.add(animator);
			}
			databazeFilmu.odstranDuplikaty(seznamAnimatoru);
			System.out.println("Zadejte doporuceni vek divaka: ");
			vek = testInt(vstup);

			if (databazeFilmu.setFilmA(druh, nazev, reziser, rok, seznamAnimatoru, vek) == false) {
				System.out.println("Film se jiz nachazi v databazi.");
			}


		}
	}

public static void editFilm(Scanner vstup) {
	String herec = null;

	int volba = 0;
	String nazev = null;
	String animator = null;
	String novynazev = null;
	int pocetHercu = 0;
	int pocetAnimatoru = 0;
	String reziser = null;

	System.out.println("Zadejte nazev filmu, ktery chcete editovat.");
	nazev = vstup.next();
	String animak = "A";
	String hrany = "H";
	boolean aktivniAplikace = true;

	ArrayList<String> seznamHercu = null;
	ArrayList<String> seznamAnimatoru = null;
	if (databazeFilmu.getDruhFilmu(nazev).equals(hrany)) {

		while (aktivniAplikace) {
		System.out.println("Vyberte moznost: ");
		System.out.println("1...zmena nazvu ");
		System.out.println("2...zmena rezisera ");
		System.out.println("3...zmena roku ");
		System.out.println("4...zmena harcu ");
		System.out.println("5...ukonceni editace");
		

			volba = testInt(vstup);
			switch (volba) {
			case 1:
				System.out.println("Zadejte novy nazev: ");
				novynazev = vstup.next();
				if (novynazev.equals("")) {
					novynazev = nazev;
				}

				break;

			case 2:
				System.out.println("Zadejte novy nazev rezisera");
				reziser = testString(vstup);

				break;
			case 3:
				System.out.println("Zadejte novy rok");
				rok = testInt(vstup);


				break;
				
			case 4:
				System.out.println("Zadejte pocet novych hercu: ");
				pocetHercu = testInt(vstup);
				seznamHercu = new ArrayList<>();
			for (int i = 0; i < pocetHercu; i++) {
				System.out.println("Zadejte jmeno herce c. " + (i + 1));
				herec = vstup.next();
				seznamHercu.add(herec);
			}
			databazeFilmu.odstranDuplikaty(seznamHercu);


			break;
		case 5:
			databazeFilmu.editaceFilmuH(nazev, novynazev, rok, reziser, seznamHercu);
			aktivniAplikace = false;
			break;
			default:
				System.out.println("Vase nabidka je mimo rozsah!");

			}

		}
	}
		
		
	else if (databazeFilmu.getDruhFilmu(nazev).equals(animak)) {
		while (aktivniAplikace) {
			System.out.println("Vyberte moznost: ");
			System.out.println("1...zmena nazvu ");
			System.out.println("2...zmena rezisera ");
			System.out.println("3...zmena roku ");
			System.out.println("3...zmena rezisera ");
			System.out.println("5...zmena animatoru ");
			System.out.println("6...zmena veku ");
			System.out.println("7...ukonceni editace");
			

			volba = testInt(vstup);
				switch (volba) {
				case 1:
					System.out.println("Zadejte novy nazev: ");

					novynazev = vstup.next();
					if (novynazev.equals("")) {
						novynazev = nazev;
					}
					break;
				case 2:
					System.out.println("Zadejte novy nazev rezisera");
					reziser = testString(vstup);
					break;
				case 3:
					System.out.println("Zadejte novy rok");
					rok = testInt(vstup);
					break;
					
				case 4:
					System.out.println("Zadejte noveho rezisera: ");
					reziser = testString(vstup);
					break;

				case 5:
					System.out.println("Zadejte pocet novych animaotru: ");
					pocetAnimatoru = testInt(vstup);
					seznamAnimatoru = new ArrayList<>();
				for (int i = 0; i < pocetAnimatoru; i++) {
					System.out.println("Zadejte jmeno animatora c. " + (i + 1));
					animator = vstup.next();
					seznamAnimatoru.add(animator);
				}
				databazeFilmu.odstranDuplikaty(seznamAnimatoru);
				break;

				
			case 6:
				System.out.println("Zadejte doporuceny vek divaka: ");
				vek = testInt(vstup);
				
				break;
				
			case 7:
				databazeFilmu.editaceFilmuA(nazev, novynazev, rok, reziser, seznamAnimatoru, vek);

				aktivniAplikace = false;
				break;

					
				default:
					System.out.println("Vase nabidka je mimo rozsah!");

				}
			}

}
else {
	System.out.println("Tento film se nenachazi v databazi!");
}
}

}






	


