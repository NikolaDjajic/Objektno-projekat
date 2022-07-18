import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Profesor {
	
	private String ime,prezime,pol;
	private PristupniPodaci pristupniPodaci;
	private int ID;
	private static ArrayList <Profesor> sviProfesori=new ArrayList<>();

	public Profesor(int ID,String ime,String prezime,int pol,int pristupniPodaci) {
		this.ime=ime;
		this.prezime=prezime;
		this.ID=ID;
		this.pristupniPodaci=PristupniPodaci.getPristupniPodaciByID(pristupniPodaci);
		
		if(pol==1)
			this.pol="Muski";
		else
			this.pol="Zenski";
		
		boolean prisutan = false;
		
		for (Profesor p:sviProfesori) 
			if(p.getID()==this.getID()) {
				System.out.println("POSTOJI");
				prisutan=true;
				break;
			}
		if(!prisutan)
			sviProfesori.add(this);
	}
	
	public Profesor(String ime,String prezime,String pol,PristupniPodaci pristupniPodaci) {
		this.ime=ime;
		this.prezime=prezime;
		this.ID=Profesor.getSviProfesori().size()+1;
		this.pristupniPodaci=pristupniPodaci;
		this.pol=pol;
		
		boolean prisutan = false;
		
		for (Profesor p:sviProfesori) 
			if(p.getID()==this.getID()) {
				System.out.println("POSTOJI");
				prisutan=true;
				break;
			}
		if(!prisutan)
			sviProfesori.add(this);
	}
	
	public static boolean validacija(String ime,String prezime) {
		for(Profesor p : sviProfesori)
			if(p.getIme().equals(ime) && p.getPrezime().equals(prezime)) 
				return false;
		return true;
	}
	
	public ArrayList<PredmetUSkoli> getProfPredmete(){
		
		ArrayList<PredmetUSkoli> predmeti= new ArrayList<>();
		
		ArrayList<PredmetUSkoli > predmeti1= PredmetUSkoli.getSviPredmetiUSkoli();
		
		for(PredmetUSkoli pus:predmeti1)
			if(pus.getProfesor().equals(this))
				predmeti.add(pus);
		return predmeti;
	}
	
	public ArrayList<OcjenaPredmeta> getProfOcjenePredmeta(){
	
		ArrayList<OcjenaPredmeta> OP=new ArrayList<>();
		ArrayList<OcjenaPredmeta> sveOP=OcjenaPredmeta.getSveOcjenePredmeta();
		
		for(OcjenaPredmeta o:sveOP)
			if(o.getPredmetUSkoli().getProfesor().equals(this))
				OP.add(o);
		return OP;
	}
	
	
	public static void ispisiProfesore() {
		for(Profesor p:sviProfesori)
		System.out.println(p.ime);
	}

	public String getIme() {
		return ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public int getID() {
		return ID;
	}

	public String getPol() {
		return pol;
	}

	public PristupniPodaci getPristupniPodaci() {
		return pristupniPodaci;
	}

	public static ArrayList<Profesor> getSviProfesori() {
		return sviProfesori;
	}
	
	public static Profesor getProfByImeIPrezime(String ime,String prezime) {
		for(Profesor p:sviProfesori)
			if(p.getIme().equals(ime) && p.getPrezime().equals(prezime))
				return p;
		return null;
	}
	
	public static Profesor getProfByID(int ID) {
		for(Profesor p:sviProfesori)
			if(p.getID()==ID)
				return p;
		return null;
	}
	
	public static Profesor getProfesorByPP(int ID) {
		
		for(Profesor p : sviProfesori)
			if(p.pristupniPodaci.getID()==ID)
				return p;
		return null;
	}
	
	

	@Override
	public String toString() {
		String s="";
		s+=""+this.getIme()+" "+this.getPrezime();
		return s;
	}
	
}
