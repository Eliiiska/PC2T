package projekt;

import java.util.ArrayList;
import java.util.List;

public class FilmH extends Film {

	private List<String> herci = new ArrayList<String>();



	public FilmH(String druh, String nazev, String reziser, int rok, List<String> herci) {
		super(druh, nazev, reziser, rok);
		this.herci = herci;
	}



	public String getDruhH() {

	return druh;
}

@Override
public String getNazev() {

	return nazev;
}

public List<String> getHerci() {
		return herci;
	}

	public void setHerci(ArrayList<String> herci) {
		this.herci = herci;
	}




}




