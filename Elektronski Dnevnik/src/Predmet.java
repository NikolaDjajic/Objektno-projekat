import java.util.ArrayList;

public class Predmet {
	private int ID,razred;
	private String naziv;
	private static ArrayList<Predmet> sviPredmeti = new ArrayList<Predmet>();
	
	
	public Predmet(int ID,String naziv,int razred) {
		this.ID=ID;
		this.naziv=naziv;
		this.razred=razred;
		
		boolean postoji=false;
		
		for(Predmet p : sviPredmeti)
			if(p.getNaziv().equals(this.getNaziv()) && p.getRazred()==this.getRazred()) {
				postoji=true;
				System.out.println("Premet sa ovim nazivom i razredom vec postoji!");
				break;
			}
		if(!postoji)
			sviPredmeti.add(this);
	}
	
	public Predmet(String naziv,int razred) {
		this.ID=sviPredmeti.size()+1;
		this.naziv=naziv;
		this.razred=razred;
		
		boolean postoji=false;
		
		for(Predmet p : sviPredmeti)
			if(p.getNaziv().equals(this.getNaziv()) && p.getRazred()==this.getRazred()) {
				postoji=true;
				System.out.println("Premet sa ovim nazivom i razredom vec postoji!");
				break;
			}
		if(!postoji)
			sviPredmeti.add(this);
	}

	public int getID() {
		return ID;
	}
	
	public static boolean validacija(String naziv,int razred) {
		for(Predmet p : sviPredmeti)
			if(p.getNaziv().equals(naziv) && p.getRazred()==razred) 
				return false;
		return true;
	}

	public int getRazred() {
		return razred;
	}

	public String getNaziv() {
		return naziv;
	}
	
	public static int getSize() {
		return sviPredmeti.size();
	}
	
	public static Predmet getPredmetByID(int ID) {
		for(Predmet p :sviPredmeti)
			if(p.getID()==ID)
				return p;
		return null;
	}

	@Override
	public String toString() {
		String s="";
		s+=""+this.getNaziv()+" "+this.getRazred();
		return s;
	}

	public static ArrayList<Predmet> getSviPredmeti() {
		return sviPredmeti;
	}
}
