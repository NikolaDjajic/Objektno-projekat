import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Ucenik {
	
	private String ime,prezime,pol;
	private PristupniPodaci pristupniPodaci;
	private int ID;
	private Skola skola=null;
	private int razred;
	private static ArrayList <Ucenik> sviUcenici=new ArrayList<>();
	private ArrayList<PredmetUSkoli> pohadjaniPredmeti=new ArrayList<>();
	
	public Ucenik(int ID,String ime,String prezime,int pol,int pristupniPodaciID) {
		this.ime=ime;
		this.prezime=prezime;
		this.ID=ID;
		this.pristupniPodaci=PristupniPodaci.getPristupniPodaciByID(pristupniPodaciID);
		this.razred=0;
		this.skola=null;
		
		if(pol==1)
			this.pol="Muski";
		else
			this.pol="Zenski";
		
		boolean postoji = false;
		
		for(Ucenik u : sviUcenici)
			if(u.getID()==this.ID || (u.getIme().equals(this.getIme()) && u.getPrezime().equals(this.getPrezime()))) {
				System.err.println("Ucenik sa ovim ID ili imenom i prezimenom vec postoji!");
				postoji=true;
				break;
			}
		if(!postoji)
			sviUcenici.add(this);
	}
	
	public Ucenik(String ime,String prezime,String pol,PristupniPodaci pristupniPodaci) {
		this.ime=ime;
		this.prezime=prezime;
		this.ID=sviUcenici.size()+1;
		this.pristupniPodaci=pristupniPodaci;
		this.pol=pol;
		this.razred=0;
		
		boolean postoji = false;
		
		for(Ucenik u : sviUcenici)
			if(u.getID()==this.ID || (u.getIme().equals(this.getIme()) && u.getPrezime().equals(this.getPrezime()))) {
				System.err.println("Ucenik sa ovim ID ili imenom i prezimenom vec postoji!");
				postoji=true;
				break;
			}
		if(!postoji)
			sviUcenici.add(this);
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
	
	public Skola getSkola() {
		return skola;
	}
	
	public static boolean validacija(String ime,String prezime) {
		for(Ucenik p:sviUcenici)
			if(p.getIme().equals(ime) && p.getPrezime().equals(prezime)) 
				return false;
		return true;
	}
	
	public void setSkola() {
		ArrayList<Izostanci> sviIzostanci=Izostanci.getSviIzostanci();
		ArrayList<Ocjena> sveOcjene=Ocjena.getSveOcjene();
		ArrayList<PredmetUSkoli> pohadjaniPredmeti= new ArrayList<>();
		Set s=new HashSet<PredmetUSkoli>();
		this.skola=null;
		for(Izostanci i:sviIzostanci) 
			if(i.getUcenik().equals(this)) {
				this.skola=i.getPredmetUSkoli().getSkola();
				pohadjaniPredmeti.add(i.getPredmetUSkoli());
				this.razred=i.getPredmetUSkoli().getPredmet().getRazred();
			}
		for(Ocjena o:sveOcjene) 
			if(o.getUcenik().equals(this)) {
				this.skola=o.getPredmetUSkoli().getSkola();
				pohadjaniPredmeti.add(o.getPredmetUSkoli());
				this.razred=o.getPredmetUSkoli().getPredmet().getRazred();
			}
		s.addAll(pohadjaniPredmeti);
		pohadjaniPredmeti.clear();
		pohadjaniPredmeti.addAll(s);
		this.pohadjaniPredmeti=pohadjaniPredmeti;
	}
	
	public String ispisiPredmete() {
		String s="";
		for(int i=0;i<pohadjaniPredmeti.size();i++) {
			if(i==0)
				s+=""+pohadjaniPredmeti.get(i).getPredmet().getNaziv();
			else
				s+=", "+pohadjaniPredmeti.get(i).getPredmet().getNaziv();	
		}
		return s;
	}
	
	public PristupniPodaci getPristupniPodaci() {
		return pristupniPodaci;
	}

	public static ArrayList<Ucenik> getSviUcenici() {
		return sviUcenici;
	}
	
	public ArrayList<PredmetUSkoli> getPohadjaniPredmeti() {
		return pohadjaniPredmeti;
	}
	
	public static Ucenik getUcenikByID(int ID) {
		
		for(Ucenik u:sviUcenici)
			if(u.getID()==ID)
				return u;
		return null;
	}
	
	public static Ucenik getUcenikByPP(int ID) {
		
		for(Ucenik u:sviUcenici)
			if(u.pristupniPodaci.getID()==ID)
				return u;
		return null;
	}
	
	public static boolean postojiUcenik(int ID) {
		
		for(Ucenik u:sviUcenici)
			if(u.pristupniPodaci.getID()==ID)
				return true;
		return false;
	}
	
	public ArrayList<Izostanci> getIzostanci(){
		ArrayList<Izostanci> izostanci=new ArrayList<Izostanci>();	
		ArrayList<Izostanci> sviIzostanci=Izostanci.getSviIzostanci();
		
		for(Izostanci i : sviIzostanci)
			if(i.getUcenik().equals(this))
				izostanci.add(i);
		return izostanci;
	}
	
	public ArrayList<Ocjena> getOcjene(){
		ArrayList<Ocjena> ocjene=new ArrayList<Ocjena>();	
		ArrayList<Ocjena> sveOcjene=Ocjena.getSveOcjene();
		
		for(Ocjena i : sveOcjene)
			if(i.getUcenik().equals(this))
				ocjene.add(i);
		return ocjene;
	}
	
	public ArrayList<OcjenaPredmeta> getOcjenePredmeta(){
		ArrayList<OcjenaPredmeta> ocjenePredmeta=new ArrayList<OcjenaPredmeta>();	
		ArrayList<OcjenaPredmeta> sveOcjenePredmeta=OcjenaPredmeta.getSveOcjenePredmeta();
		
		for(OcjenaPredmeta i : sveOcjenePredmeta)
			if(i.getUcenik().equals(this))
				ocjenePredmeta.add(i);
		return ocjenePredmeta;
	}
	

	
	public ArrayList<Profesor> getPrefoesori(){
		ArrayList<Profesor> profesori=new ArrayList<Profesor>();
		ArrayList<Ocjena> sveOcjene=Ocjena.getSveOcjene();
		ArrayList<Izostanci> sviIzostanci=Izostanci.getSviIzostanci();
		Set s=new HashSet<PredmetUSkoli>();
		
		for(Ocjena o : sveOcjene)
			if(o.getUcenik().equals(this))
				profesori.add(o.getPredmetUSkoli().getProfesor());
				
		for(Izostanci i : sviIzostanci)
			if(i.getUcenik().equals(this))
				profesori.add(i.getPredmetUSkoli().getProfesor());
				
		s.addAll(profesori);
		profesori.clear();
		profesori.addAll(s);
		return profesori;
	}
	
	public static int getIDByUcenik(Ucenik u) {
		for(Ucenik uc :sviUcenici)
			if(uc.equals(u))
				return uc.getID();
		return 0;
	}
	
	public void setRazred(int razred) {
		this.razred=razred;
	}
	
	public void setSk(Skola s) {
		this.skola=s;
	}
	
	public int getRazred() {
		return this.razred;
	}
	
	@Override
	public String toString() {
		String s="";
		s+=""+this.ime+" "+ this.prezime;
		
		return s;
	}

}
