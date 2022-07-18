import java.util.ArrayList;

public class Pitanje {
	
	private int ID;
	private String tekst;
	private static ArrayList<Pitanje> svaPitanja=new ArrayList<Pitanje>();
	
	public Pitanje(int ID,String tekst) {
		this.ID=ID;
		this.tekst=tekst;
		
		svaPitanja.add(this);
	}

	public int getID() {
		return ID;
	}

	public String getTekst() {
		return tekst;
	}

	public ArrayList<Pitanje> getSvaPitanja() {
		return svaPitanja;
	}
	
	public static Pitanje getPitanjeByID(int ID) {
		for(Pitanje p:svaPitanja)
			if(p.getID()==ID)
				return p;
		return null;
	}
}
