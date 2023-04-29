package projekt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TabulkaFilmu {
	private static final String CONNECTION_DB = "jdbc:sqlite:./db/filmyDB.db";
	private HashMap<String, Film> jednotliveFilmy;
	private Map<String, List<String>> jednotliviUcinkujici = new HashMap<>();

	public void pridejRecenzi(String nazevFilmu) {
		Film film = jednotliveFilmy.get(nazevFilmu);
		if (film != null) {
			film.pridejRecenzi();
			return;
		}
		System.out.println("Film neexistuje: " + nazevFilmu);
	}

	public Map<String, List<String>> addUcinkujici(String nazevFilmu, String jmeno) {
		List<String> filmy = jednotliviUcinkujici.get(jmeno);
		if (filmy == null) {
			List<String> seznamFilmu = new ArrayList<>();
			seznamFilmu.add(nazevFilmu);
			jednotliviUcinkujici.put(jmeno, seznamFilmu);

		} else {

			filmy.add(nazevFilmu);

		}

		return jednotliviUcinkujici;
	}

	private void removeUcinkujici(String nazevFilmu, String jmeno) {
		List<String> filmy = jednotliviUcinkujici.get(jmeno);
		if (filmy == null) {
			return;
		} else {
			filmy.remove(nazevFilmu);
			if (filmy.isEmpty()) {
				jednotliviUcinkujici.remove(jmeno);
			}
		}
	}

	public void vypisFilmuDleUcinkujiciho(String jmeno) {
		List<String> filmy = jednotliviUcinkujici.get(jmeno);
		if (filmy == null || filmy.isEmpty() == true) {
			System.out.println("Tento ucinkujici se v databazi nenachazi!\n");

		} else {
			System.out.println("Seznam filmu aktera " + jmeno);
			for (String film : filmy) {
				System.out.println(film);
			}
		}

	}

	public void vypisUcinkujicichSViceFilmy() {
		boolean akterSViceFilmy = false;
		for(String jmeno: jednotliviUcinkujici.keySet()) {
		List <String> filmy = jednotliviUcinkujici.get(jmeno);
		if (filmy.size() > 1) {
			System.out.println("Akter: " + jmeno + " se podilel na techto filmech: ");
			for (String film : filmy) {
				System.out.println(film);
			}
			System.out.println();
			akterSViceFilmy = true;
		}
		

	}
	if (akterSViceFilmy == false) {
		System.out.println("Neexistuje akter s vice filmy.\n");
		}
	}

	public void odstranDuplikaty(ArrayList<String> list) {
		Set<String> set = new HashSet<>(list);
		list.clear();
		list.addAll(set);
	}

	public TabulkaFilmu() {
		jednotliveFilmy = new HashMap<String, Film>();
	}

	public boolean setFilmA(String druhFilmu, String nazev, String reziser, int rok, List<String> animatori,
			int vek) {

		if (jednotliveFilmy.put(nazev, new FilmA(druhFilmu, nazev, reziser, rok, animatori, vek)) == null) {

			for (String animator : animatori) {
				addUcinkujici(nazev, animator);
			}

			return true;

		}
		return false;
	}

	public boolean setFilmH(String druhFilmu, String nazev, String reziser, int rok, List<String> herci) {

		if (jednotliveFilmy.put(nazev, new FilmH(druhFilmu, nazev, reziser, rok, herci)) == null) {

			for (String herec : herci) {
				addUcinkujici(nazev, herec);

			}

			return true;
		}
		return false;
	}

	public String getDruhFilmu(String nazev) {
		Film film = jednotliveFilmy.get(nazev);
		if (jednotliveFilmy.get(nazev) != null) {
			return film.getDruh();
		}
		return "";

	}

	public List<String> getAnimatori(String nazevFilmu) {
		List<String> animatori = new ArrayList<>();
		if (jednotliveFilmy.containsKey(nazevFilmu)) {
			for (Map.Entry<String, List<String>> entry : jednotliviUcinkujici.entrySet()) {
				if (entry.getValue().contains(nazevFilmu)) {
					animatori.add(entry.getKey());
				}
			}
		}
		return animatori;
	}


	public void editaceFilmuA(String nazev, String novynazev, Integer rok, String reziser, ArrayList<String> animatori,
			Integer vek) {
		FilmA film = (FilmA) jednotliveFilmy.get(nazev);

		if (film == null) {
			System.out.println("Film neexistuje");
		}

		if (novynazev != null && animatori != null) {
			jednotliveFilmy.remove(nazev);
			for (String animator : film.getAnimatori()) {
				removeUcinkujici(nazev, animator);
			}
			film.setNazev(novynazev);
			film.setAnimatori(animatori);
			for (String animator : animatori) {
				addUcinkujici(novynazev, animator);
			}
			jednotliveFilmy.put(novynazev, film);
		} else {
			if (novynazev != null) {
				jednotliveFilmy.remove(nazev);
				film.setNazev(novynazev);
				jednotliveFilmy.put(novynazev, film);
				for (String animator : film.getAnimatori()) {
					removeUcinkujici(nazev, animator);
				}
				for (String animator : film.getAnimatori()) {
					addUcinkujici(novynazev, animator);
				}

			}
		}

		if (animatori != null) {
			List<String> aktualniHerci = film.getAnimatori();
			for (String herec : aktualniHerci) {
				removeUcinkujici(nazev, herec);

			}
			film.setAnimatori(animatori);
			for (String animator : animatori) {
				addUcinkujici(nazev, animator);
			}
		}
		if (rok != null) {
			film.setRok(rok);
		}

		if (reziser != null) {
			film.setReziser(reziser);
			;
		}
		if (vek != null) {
			film.setVek(vek);

		}



	}

	public void editaceFilmuH(String nazev, String novynazev, Integer rok, String reziser, ArrayList<String> herci) {
		FilmH film = (FilmH) jednotliveFilmy.get(nazev);
		if (film == null) {
			System.out.println("Film neexistuje");
		}

		if (novynazev != null && herci != null) {
			jednotliveFilmy.remove(nazev);
			for (String herec : film.getHerci()) {
				removeUcinkujici(nazev, herec);
			}
			film.setNazev(novynazev);
			film.setHerci(herci);
			for (String herec : herci) {
				addUcinkujici(novynazev, herec);
			}
			jednotliveFilmy.put(novynazev, film);
		} else {
			if (novynazev != null) {
				jednotliveFilmy.remove(nazev);
				film.setNazev(novynazev);
				jednotliveFilmy.put(novynazev, film);
				for (String herec : film.getHerci()) {
					removeUcinkujici(nazev, herec);
				}
				for (String herec : film.getHerci()) {
					addUcinkujici(novynazev, herec);
				}

			}
			if (herci != null) {
				List<String> aktualniHerci = film.getHerci();
				for (String herec : aktualniHerci) {
					removeUcinkujici(nazev, herec);

				}
				film.setHerci(herci);
				for (String herec : herci) {
					addUcinkujici(nazev, herec);
				}
			}
		}
		if (rok != null) {
			film.setRok(rok);
		}

		if (reziser != null) {
			film.setReziser(reziser);
		}
	}

	public void vypisVsechnyFilmy() {
		for (String nazevFilmu : jednotliveFilmy.keySet()) {
			Film film = jednotliveFilmy.get(nazevFilmu);
			System.out.println("Název filmu: " + film.getNazev());
			System.out.println("Druh filmu: " + film.getDruh());
			System.out.println("Režisér: " + film.getReziser());
			System.out.println("Rok vydání: " + film.getRok());
			if (film instanceof FilmA) {
				FilmA filmA = (FilmA) film;
				System.out.println("Věk: " + filmA.getVek());
				System.out.println("Animatori: " + filmA.getAnimatori());
			} else if (film instanceof FilmH) {
				FilmH filmH = (FilmH) film;
				System.out.println("Herci: " + filmH.getHerci());
			}
			System.out.println("--------------------");
		}
		if (jednotliveFilmy.isEmpty()) {
			System.out.println("Databaze je praznda");
		}
	}

	public boolean vymazFilm(String nazev) {
		Film film = jednotliveFilmy.get(nazev);
		if (jednotliveFilmy.remove(nazev) != null) {


			if (film.getDruh().equals("H")) {
				FilmH filmH = (FilmH) film;
				for (String ucinkujici : filmH.getHerci()) {
					removeUcinkujici(nazev, ucinkujici);
				}
			} else {
				FilmA filmA = (FilmA) film;
				for (String ucinkujici : filmA.getAnimatori()) {
					removeUcinkujici(nazev, ucinkujici);
				}
			}

			return true;
		}
		return false;
	}

	public void infoOFilmu(String nazev) {
		if (jednotliveFilmy.containsKey(nazev)) {
			Film film = jednotliveFilmy.get(nazev);
			System.out.println("Název: " + film.getNazev());
			System.out.println("Režisér: " + film.getReziser());
			System.out.println("Rok: " + film.getRok());
			if (film instanceof FilmA) {
				FilmA filmA = (FilmA) film;
				System.out.println("Druh filmu: " + filmA.getDruhA());
				System.out.println("Animátoři: " + filmA.getAnimatori());
				System.out.println("Věk: " + filmA.getVek());
			} else if (film instanceof FilmH) {
				FilmH filmH = (FilmH) film;
				System.out.println("Druh filmu: " + filmH.getDruhH());
				System.out.println("Herci: " + filmH.getHerci());
			}
			System.out.println("Recenze: ");
			film.vypisRecenze();
			System.out.println();
			return;
		}
		System.out.println("Film s nazvem " + nazev + " neexistuje v databazi!");
	}

	public boolean zapisDoSouboru(String nazev) {
		try {
			boolean foundNazev = false;
			FileWriter fw = new FileWriter(nazev);
			BufferedWriter bw = new BufferedWriter(fw);

			for (Map.Entry<String, Film> entry : jednotliveFilmy.entrySet()) {
				Film film = entry.getValue();

				if (film.getNazev().equals(nazev)) {

					foundNazev = true;
					bw.write("Nazev: " + film.getNazev() + "\n");
					bw.write("Reziser: " + film.getReziser() + "\n");
					bw.write("Rok vydano: " + film.getRok() + "\n");
					if (film instanceof FilmA) {
						FilmA filmA = (FilmA) film;
						bw.write("Druh: " + filmA.getDruh() + "\n");
						bw.write("Doporuceny vek: " + filmA.getVek() + "\n");
						bw.write("Animatori: " + filmA.getAnimatori() + "\n");
					} else if (film instanceof FilmH) {
						FilmH filmH = (FilmH) film;
						bw.write("Druh: " + filmH.getDruh() + "\n");
						bw.write("Herci: " + filmH.getHerci() + "\n");
					}
					bw.newLine();

				}

			}
			bw.close();
			fw.close();

			if (!foundNazev) {
				System.out.println("Film s nazvem " + nazev + " nebyl nalezen v databazi.");
				return false;
			}
		} catch (IOException e) {
			System.out.println("Nemohlo byt zapsano do souboru.");
			return false;
		}
		return true;
	}

	public boolean vypisZeSouboru(String nazev) {
		boolean foundNazev = false;
		try {
			for (Map.Entry<String, Film> entry : jednotliveFilmy.entrySet()) {
				Film film = entry.getValue();
				if (film.getNazev().equals(nazev)) {
					foundNazev = true;
					break;
				}
			}
			if (foundNazev) {
				FileReader fr = new FileReader(nazev);
				BufferedReader br = new BufferedReader(fr);
				String radek = br.readLine();
				while (radek != null) {
					System.out.println(radek);
					radek = br.readLine();

				}
				br.close();
				return true;

			} else {
				System.out.println("Tento soubor neexistuje!");
				return false;
			}

		}

		catch (IOException e) {
			System.out.println("Nebylo mozne precist ze souboru");
			return false;
		}

	}

	public void nacteniDB() throws SQLException {
		try (Connection conn = DriverManager.getConnection(CONNECTION_DB);) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Filmy");

			while (rs.next()) {
				String nazev = rs.getString(1);
				String reziser = rs.getString(2);
				int rok = rs.getInt(3);

				int vek = rs.getInt(5);

				String druh = rs.getString(7);

				if (druh.equals("H")) {
					List<String> herci = List.of(rs.getString(6).split(","));
					setFilmH(druh, nazev, reziser, rok, herci);
				} else {
					List<String> animatori = List.of(rs.getString(4).split(","));
					setFilmA(druh, nazev, reziser, rok, animatori, vek);
				}

			}
			rs.close();

			rs = stmt.executeQuery("select * from Recenze");
			while (rs.next()) {
				String nazev = rs.getString(1);
				String hodnoceni = rs.getString(2);
				int body = rs.getInt(3);

				Film film = jednotliveFilmy.get(nazev);
				Recenze recenze;

				if (film.getDruh().equals("H")) {
					recenze = new RecenzeH();
				} else {
					recenze = new RecenzeA();
				}
				recenze.setBody(body);
				recenze.setHodnoceni(hodnoceni);


			}
			rs.close();

		}
	}

	public void zapisDB() throws SQLException {


			try (Connection conn = DriverManager.getConnection(CONNECTION_DB);) {
				Statement stmt = conn.createStatement();
				stmt.execute("delete from Filmy");
				for (Film film : jednotliveFilmy.values()) {
					String nazev = film.getNazev();
					String reziser = film.getReziser();
					int rok = film.getRok();
					String druh = film.getDruh();

				if (druh.equals("A")) {
					String animatori;
					FilmA filmA = (FilmA) film;
					animatori = zListuString(filmA.getAnimatori());
					int vek = filmA.getVek();
					stmt.execute("insert into Filmy (nazev, reziser, rok, druh, animatori, vek) values (\"" + nazev
							+ "\",\"" + reziser + "\"," + rok + ",\"" + druh + "\",\"" + animatori + "\"," + vek + ")");
				} else {
					String herci;
					FilmH filmH = (FilmH) film;
					herci = zListuString(filmH.getHerci());

					stmt.execute("insert into Filmy (nazev, reziser, rok, druh, herci) values (\"" + nazev + "\",\""
							+ reziser + "\"," + rok + ",\"" + druh + "\",\"" + herci + "\")");

				}

				for (Recenze recenze : film.getRecenze()) {
					String otevrene = recenze.getHodnoceni();
					int body = recenze.getBody();

					stmt.execute("insert into Recenze (nazev, hodnoceni, body) values (\"" + nazev + "\",\"" + otevrene
							+ "\"," + body + ")");

				}
			}
		}
	}

	private String zListuString(List<String> ucinkujici) {
		StringBuilder sb = new StringBuilder();

		for (String jedenUcinkujici : ucinkujici) {

			sb.append(jedenUcinkujici).append(",");
		}

		return sb.substring(0, sb.length() - 1);
	}


}
