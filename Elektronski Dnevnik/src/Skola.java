import java.util.ArrayList;

public class Skola {
	private int ID;
	private String naziv,grad,mjesto,drzava;
	private static ArrayList<Skola> sveSkole=new ArrayList<Skola>();

	
	public Skola(int ID,String naziv,String grad,String mjesto,String drzava) {
		this.ID=ID;
		this.naziv=naziv;
		this.grad=grad;
		this.mjesto=mjesto;
		this.drzava=drzava;
		
		boolean prisutan = false;
		
		for (Skola s:sveSkole) 
			if(s.getNaziv().equals(this.getNaziv()) && s.getDrzava().equals(this.getDrzava()) && s.getGrad().equals(this.getGrad()) && s.getMjesto().equals(this.getMjesto())) {
				System.out.println("Postoji ovakva skola!");
				prisutan=true;
				break;
			}
		if(!prisutan)	
			sveSkole.add(this);
	}

	public Skola(String naziv,String grad,String mjesto,String drzava) {
		this.ID=sveSkole.size()+1;
		this.naziv=naziv;
		this.grad=grad;
		this.mjesto=mjesto;
		this.drzava=drzava;
		
		boolean prisutan = false;
		
		for (Skola s:sveSkole) 
			if(s.getNaziv().equals(this.getNaziv()) && s.getDrzava().equals(this.getDrzava()) && s.getGrad().equals(this.getGrad()) && s.getMjesto().equals(this.getMjesto())) {
				System.out.println("Postoji ovakva skola!");
				prisutan=true;
				break;
			}
		if(!prisutan)	
			sveSkole.add(this);
	}
	

	public int getID() {
		return ID;
	}

	public String getNaziv() {
		return naziv;
	}

	public String getGrad() {
		return grad;
	}

	public String getMjesto() {
		return mjesto;
	}

	public String getDrzava() {
		return drzava;
	}
	
	public static ArrayList<Skola> getSveSkole() {
		return sveSkole;
	}
	
	public static Skola getSkolaByID(int IDS) {
		
		for(Skola s:sveSkole)
			if(s.getID()==IDS)
				return s;
		return null;
	}
	
	public static boolean validacija(String naziv,String mjesto,String grad,String drzava) {
		for(Skola skola:sveSkole)
			if(naziv.equals(skola.getNaziv()) && drzava.equals(skola.getDrzava()) && grad.equals(skola.getGrad()) && mjesto.equals(skola.getMjesto())) 
				return false;
			return true;
	}
/*
	@Override
	public String toString() {
		String s="";
		s+=""+this.getNaziv()+" "+this.getMjesto()+" "+this.getGrad()+" "+this.getDrzava();
		return s;
	}*/

	@Override
	public String toString() {
		String s="";
		s+=""+this.getNaziv()+" "+this.getMjesto()+" "+this.getGrad()+" "+this.getDrzava();
		return s;
	}
	
}
