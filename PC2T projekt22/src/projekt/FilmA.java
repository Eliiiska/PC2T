package projekt;

import java.util.ArrayList;
import java.util.List;

public class FilmA extends Film {
	private int vek;
	private List<String> animatori = new ArrayList<String>();


	public FilmA(String druh, String nazev, String reziser, int rok, List<String> animatori, int vek) {
		super(druh, nazev, reziser, rok);
		this.animatori = animatori;
		this.vek = vek;
	}



	public List<String> getAnimatori() {
		return animatori;
	}

	public void setAnimatori(List<String> animatori) {
		this.animatori = animatori;
	}

	public void setVek(int vek) {
		this.vek = vek;
	}

	public int getVek() {
		return vek;
	}

	public String getDruhA() {

		return druh;
	}

	@Override
	public String getNazev() {

		return nazev;
	}

}
