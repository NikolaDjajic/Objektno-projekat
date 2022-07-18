import java.util.ArrayList;

public class Izostanci {
	
	private int ID;
	private Ucenik ucenik;
	private PredmetUSkoli predmetUSkoli;
	private String datum;
	private static ArrayList<Izostanci> sviIzostanci=new ArrayList<Izostanci>();
	
	public Izostanci(int iD, int ucenikID, int predmetUSkoliID, String datum) {
		this.ID = iD;
		this.ucenik = Ucenik.getUcenikByID(ucenikID);
		this.predmetUSkoli = PredmetUSkoli.getPredmetUSkoliByID(predmetUSkoliID);
		this.datum = datum;
		
		sviIzostanci.add(this);
	}
	
	public Izostanci( Ucenik ucenik, PredmetUSkoli predmetUSkoli, String datum) {
		this.ID = sviIzostanci.size()+1;
		this.ucenik = ucenik;
		this.predmetUSkoli = predmetUSkoli;
		this.datum = datum;
		
		sviIzostanci.add(this);
	}
	
	//Na taj datum
	public static boolean validacija2(Ucenik u,PredmetUSkoli pus,String datum) {
		ArrayList<Ocjena> sveOcjene=Ocjena.getSveOcjene();
		
		for(Ocjena o:sveOcjene)
			if(o.getDatum().equals(datum) && o.getPredmetUSkoli().equals(pus) && o.getUcenik().equals(u))
				return false;
		for(Izostanci i:sviIzostanci) 
			if(i.getDatum().equals(datum) && i.getPredmetUSkoli().equals(pus) && i.getUcenik().equals(u))
				return false;
		
		return true;
	}
	
	// Da li ga je ocjenjivao ili napisao izostanak drugi profesor iz istog predmeta
	public static boolean validacija1(Ucenik u,PredmetUSkoli pus) {
		ArrayList<Ocjena> sveOcjene=Ocjena.getSveOcjene();
			
		for(Ocjena o:sveOcjene)
			if(u.equals(o.getUcenik()) && o.getPredmetUSkoli().getPredmet().equals(pus.getPredmet()) && !o.getPredmetUSkoli().getProfesor().equals(pus.getProfesor()))
				return false;
		for(Izostanci i:sviIzostanci) 
			if(u.equals(i.getUcenik()) && i.getPredmetUSkoli().getPredmet().equals(pus.getPredmet()) && !pus.getProfesor().equals(i.getPredmetUSkoli().getProfesor()) )  
				return false;
		
		return true;
	}
	
	public int getID() {
		return ID;
	}

	public Ucenik getUcenik() {
		return ucenik;
	}

	public String getDatum() {
		return datum;
	}

	public PredmetUSkoli getPredmetUSkoli() {
		return predmetUSkoli;
	}

	public static ArrayList<Izostanci> getSviIzostanci() {
		return sviIzostanci;
	}
}
	

