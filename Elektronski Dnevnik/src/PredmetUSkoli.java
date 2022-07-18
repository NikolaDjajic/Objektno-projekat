import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PredmetUSkoli {
	
	private int ID;
	private Predmet predmet;
	private Skola skola;
	private Profesor profesor;
	private static ArrayList<PredmetUSkoli> sviPredmetiUSkoli=new ArrayList<PredmetUSkoli>();
	
	public PredmetUSkoli(int ID,int predmetID,int skolaID,int profesorID) {
		
		this.ID=ID;
		this.predmet=Predmet.getPredmetByID(predmetID);
		this.skola=Skola.getSkolaByID(skolaID);
		this.profesor=Profesor.getProfByID(profesorID);
		
		sviPredmetiUSkoli.add(this);
	}
	
	public PredmetUSkoli(Predmet predmet,Skola skola,Profesor profesor) {
		
		this.ID=sviPredmetiUSkoli.size()+1;
		this.predmet=predmet;
		this.skola=skola;
		this.profesor=profesor;
		
		sviPredmetiUSkoli.add(this);
	}
	
	public int getID() {
		return ID;
	}

	public Predmet getPredmet() {
		return predmet;
	}

	public Skola getSkola() {
		return skola;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public static ArrayList<PredmetUSkoli> getSviPredmetiUSkoli() {
		return sviPredmetiUSkoli;
	}
	
	public static PredmetUSkoli getPredmetUSkoli(int predmetID,int skolaID,int profesorID) {
		for(PredmetUSkoli p:sviPredmetiUSkoli)
			if(p.getPredmet().getID()==predmetID && p.getProfesor().getID()==profesorID && p.getSkola().getID()==skolaID)
				return p;
		return null;
	}
	
	public static PredmetUSkoli getPredmetUSkoliByID(int ID) {
		for(PredmetUSkoli p:sviPredmetiUSkoli)
			if(p.getID()==ID)
				return p;
		return null;
	}
	
	public static ArrayList<PredmetUSkoli> getPredmetUSkoliByProf(Profesor prof,ArrayList<Skola> skole) {
		ArrayList<PredmetUSkoli> sviPuS=new ArrayList<PredmetUSkoli>();
		for(Skola s:skole) 
			for(PredmetUSkoli p:sviPredmetiUSkoli)
				if(p.getProfesor().equals(prof) && p.getSkola().equals(s))
					sviPuS.add(p);
		
		return sviPuS;
	}
	
	public static ArrayList<Skola> getSkoleByProf(Profesor p){
		ArrayList<Skola> skole = new ArrayList<Skola>();
		Set s= new HashSet<Skola>();
		
		for(PredmetUSkoli pus:sviPredmetiUSkoli) 
			if(pus.getProfesor().equals(p))
				skole.add(pus.getSkola());
		s.addAll(skole);
		skole.clear();
		skole.addAll(s);
		return skole;
	}
	
	public ArrayList<Ucenik> getUcenikeByPuS() {
		ArrayList<Ucenik> ucenici = new ArrayList<>();
		ArrayList<Ucenik> sviUcenici=Ucenik.getSviUcenici();
		ArrayList<Ocjena> sveOcjene=Ocjena.getSveOcjene();
		ArrayList<Izostanci> sviIzostanci=Izostanci.getSviIzostanci();

		Set<Ucenik> s=new HashSet<Ucenik>();

		for(Ucenik u:sviUcenici) {
			for(Izostanci i :sviIzostanci)
				if(i.getUcenik().equals(u) && i.getPredmetUSkoli().equals(this))
					ucenici.add(u);
		
			for(Ocjena o :sveOcjene)
				if(o.getUcenik().equals(u) && o.getPredmetUSkoli().equals(this))
					ucenici.add(u);	
		}
		s.addAll(ucenici);
		ucenici.clear();
		ucenici.addAll(s);
		return ucenici;
	}
		
	
	public static int getIDByPredmetUSkoli(PredmetUSkoli ps) {
		for(PredmetUSkoli p:sviPredmetiUSkoli)
			if(p.equals(ps))
				return p.getID();
		return 0;
	}

	
	
/*	@Override
	public String toString() {
		String s="";
		s+=""+this.getPredmet()+" "+this.getProfesor()+" "+this.getSkola();
		return s;
	}*/
	
	@Override
	public String toString() {
		String s="";
		s+=""+this.getPredmet();
		return s;
	}
	
	
}
