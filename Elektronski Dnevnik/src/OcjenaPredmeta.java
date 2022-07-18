import java.util.ArrayList;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

public class OcjenaPredmeta {

	private int ID, ocjena;
	private PredmetUSkoli predmetUSkoli;
	private Ucenik ucenik;
	private Pitanje pitanje;
	private static ArrayList<OcjenaPredmeta> sveOcjenePredmeta = new ArrayList<OcjenaPredmeta>();

	public OcjenaPredmeta(int ID, int predmetUSkoliID, int ucenikID, int pitanjeID, int ocjena) {
		this.ID = ID;
		this.predmetUSkoli = PredmetUSkoli.getPredmetUSkoliByID(predmetUSkoliID);
		this.ucenik = Ucenik.getUcenikByID(ucenikID);
		this.pitanje = Pitanje.getPitanjeByID(pitanjeID);
		this.ocjena = ocjena;

		boolean postoji = false;

		for (OcjenaPredmeta op : sveOcjenePredmeta)
			if (op.getUcenik().equals(this.getUcenik())
					&& op.getPredmetUSkoli().equals(this.getPredmetUSkoli())
					&& op.getPitanje().equals(this.getPitanje())) {
				postoji = true;
				System.err.println("Ova ocjena predmeta postoji!");
				break;
			}
		
		if (!postoji)
			sveOcjenePredmeta.add(this);
	}

	public OcjenaPredmeta(int ID,PredmetUSkoli pus, Ucenik ucenik, Pitanje pitanje, int ocjena) {
		this.ID = ID;
		this.predmetUSkoli = pus;
		this.ucenik = ucenik;
		this.pitanje =pitanje;
		this.ocjena = ocjena;
		
		sveOcjenePredmeta.add(this);
	}
	
	public int getID() {
		return ID;
	}

	public int getOcjena() {
		return ocjena;
	}

	public PredmetUSkoli getPredmetUSkoli() {
		return predmetUSkoli;
	}

	public Ucenik getUcenik() {
		return ucenik;
	}

	public Pitanje getPitanje() {
		return pitanje;
	}

	public static ArrayList<OcjenaPredmeta> getSveOcjenePredmeta() {
		return sveOcjenePredmeta;
	}

	public static boolean postojiOcjenaPr(Ucenik u, Profesor p) {
		for (OcjenaPredmeta op : sveOcjenePredmeta)
			if (op.getUcenik().equals(u) && op.getPredmetUSkoli().getProfesor().equals(p))
				return true;
		return false;
	}

	public static OcjenaPredmeta getOcjenaPredmetaByID(int ID) {
		for (OcjenaPredmeta o : sveOcjenePredmeta)
			if (o.getID() == ID)
				return o;
		return null;
	}
	
	public static ArrayList<OcjenaPredmeta> getOcjeneByPred(PredmetUSkoli pus){
		ArrayList<OcjenaPredmeta> ocjenePredmeta=new ArrayList<OcjenaPredmeta>();
	
		for(OcjenaPredmeta op:sveOcjenePredmeta)
			if(op.getPredmetUSkoli().equals(pus))
				ocjenePredmeta.add(op);
	
		return ocjenePredmeta;
	}

	@Override
	public String toString() {
		String s="";
		s+=""+this.getPredmetUSkoli()+" "+this.getPitanje().getTekst()+" "+this.ocjena;
		
		
		return s;
	}
}
