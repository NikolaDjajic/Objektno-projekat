import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Ocjena {
	
	private int ID,ocjena;
	private Ucenik ucenik;
	private PredmetUSkoli predmetUSkoli; 
	private String datum;
	private static ArrayList<Ocjena> sveOcjene=new ArrayList<Ocjena>();
	
	public Ocjena(int ID,int ucenikID,int predmetUSkoliID,int ocjena,String datum) {
		this.ID=ID;
		this.ucenik=Ucenik.getUcenikByID(ucenikID);
		this.predmetUSkoli=PredmetUSkoli.getPredmetUSkoliByID(predmetUSkoliID);
		this.ocjena=ocjena;
		this.datum=datum;
		
		sveOcjene.add(this);
	}
	
	public Ocjena(Ucenik ucenik,PredmetUSkoli predmetUSkoli,int ocjena,String datum) {
		this.ID=sveOcjene.size()+1;
		this.ucenik=ucenik;
		this.predmetUSkoli=predmetUSkoli;
		this.ocjena=ocjena;
		this.datum=datum;
		
		sveOcjene.add(this);
	}
	
	// Da li ga je ocjenjivao ili napisao izostanak drugi profesor iz istog predmeta
	public static boolean validacija1(Ucenik u,PredmetUSkoli pus) {
		ArrayList<Izostanci> sviIzostanci=Izostanci.getSviIzostanci();
		
		for(Ocjena o:sveOcjene)
			if(u.equals(o.getUcenik()) && o.getPredmetUSkoli().getPredmet().equals(pus.getPredmet()) && !o.getPredmetUSkoli().getProfesor().equals(pus.getProfesor()))
				return false;
		for(Izostanci i:sviIzostanci) 
			if(u.equals(i.getUcenik()) && i.getPredmetUSkoli().getPredmet().equals(pus.getPredmet()) && !pus.getProfesor().equals(i.getPredmetUSkoli().getProfesor()) )  
				return false;
		
		return true;
	}
	
	//Na taj datum, ako vec imaju 2 ocjene tog dana
	public static boolean validacija2(Ucenik u,PredmetUSkoli pus,String datum) {
		ArrayList<Izostanci> sviIzostanci=Izostanci.getSviIzostanci();
		
		HashMap<String, Integer> brOcj= new HashMap<>();
		
		for(Izostanci i:sviIzostanci) 
			if(i.getDatum().equals(datum) && i.getPredmetUSkoli().equals(pus) && i.getUcenik().equals(u))
				return false;
		
		for(Ocjena o :sveOcjene) {
			if(o.getUcenik().equals(u)) {
				for(Map.Entry<String, Integer> ulaz:brOcj.entrySet()) {
					if(ulaz.getKey().equals(o.getDatum())) {
						ulaz.setValue(ulaz.getValue()+1);
					}
					else {
						brOcj.put(o.getDatum(), 1);
					}				
					if(ulaz.getValue()==2)
						return false;
				}}}
		
		return true;
	}
	// Da li je dobio zadnju ocjenu iz tog predmeta prije ili poslije 7 dana
	public static boolean validacija3(Ucenik u,PredmetUSkoli pus,String datum) {

		long raz=10;
		for(Ocjena o:sveOcjene) {
			if(u.equals(o.getUcenik()) && o.getPredmetUSkoli().equals(pus))
				raz=razlikaDatumaVal(datum,o.getDatum());
			
			if(raz<7 && raz>-7)
				return false;			
		}
		return true;
	}

	public static ArrayList<Ocjena> getOcjenaByPuSiU(PredmetUSkoli pus,Ucenik u) {
		ArrayList<Ocjena> ocjene=new ArrayList<Ocjena>();
		
		for(Ocjena o :sveOcjene)
			if(o.getPredmetUSkoli().equals(pus) && o.getUcenik().equals(u))
				ocjene.add(o);
		
		return ocjene;
	}
	
	public static long razlikaDatumaVal(String datum1S,String datum2S) {
		LocalDate datum1 = LocalDate.parse(datum1S);
		LocalDate datum2 = LocalDate.parse(datum2S);
		long razlika= ChronoUnit.DAYS.between(datum1, datum2);
		
		return razlika;
	}
	
	public int getID() {
		return ID;
	}

	public int getOcjena() {
		return ocjena;
	}

	public Ucenik getUcenik() {
		return ucenik;
	}

	public PredmetUSkoli getPredmetUSkoli() {
		return predmetUSkoli;
	}

	public String getDatum() {
		return datum;
	}

	public static ArrayList<Ocjena> getSveOcjene() {
		return sveOcjene;
	}

}
