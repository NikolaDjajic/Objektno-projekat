import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class PristupniPodaci {

	private int ID;
	private String korisnickoIme, sifra, email;
	private static ArrayList<PristupniPodaci> sviPristupniPodaci = new ArrayList<PristupniPodaci>();

	public PristupniPodaci(int ID, String korisnickoIme, String email, String sifra) {
		this.ID = ID;
		this.korisnickoIme = korisnickoIme;
		this.email = email;
		this.sifra = sifra;

		boolean prisutan = false;
		for (PristupniPodaci p : sviPristupniPodaci)
			if (p.getEmail().equals(this.getEmail()) || this.getKorisnickoIme().equals(p.getKorisnickoIme())) {
				System.err.println("PP sa ovim korisnickim imenom i emailom vec postoje!");
				prisutan = true;
				break;
			}
		if (!prisutan)
			sviPristupniPodaci.add(this);
	}

	public PristupniPodaci(String korisnickoIme, String email, String sifra) {
		this.ID = sviPristupniPodaci.size()+1;
		this.korisnickoIme = korisnickoIme;
		this.email = email;
		this.sifra = getMd5(sifra);

		boolean prisutan = false;
		for (PristupniPodaci p : sviPristupniPodaci)
			if (p.getEmail().equals(this.getEmail()) || this.getKorisnickoIme().equals(p.getKorisnickoIme())) {
				System.err.println("PP sa ovim korisnickim imenom i emailom vec postoje1!");
				prisutan = true;
				break;
			}
		if (!prisutan)
			sviPristupniPodaci.add(this);

	}

	public static PristupniPodaci getPoKorisnickomImenu(String prijava) {
		for (PristupniPodaci p : sviPristupniPodaci)
			if (p.getEmail().equals(prijava) || prijava.equals(p.getKorisnickoIme()))
				return p;
		return null;
	}

	public static PristupniPodaci getPristupniPodaciByID(int ID) {
		for (PristupniPodaci p : sviPristupniPodaci)
			if (p.getID() == ID)
				return p;
		return null;
	}

	public static int prijava(String korisnickoIme, String sifra) {
		PristupniPodaci podaci = getPoKorisnickomImenu(korisnickoIme);
		String sifra1 = getMd5(sifra);

		if (podaci != null) {
			if (podaci.getSifra().equals(sifra1))
				return podaci.getID();
		}
		return -1;
	}

	public static String getMd5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static boolean validacija(String korisnicko,String email) {
		for (PristupniPodaci p : sviPristupniPodaci)
			if (p.getEmail().equals(email) || korisnicko.equals(p.getKorisnickoIme())) 
				return false;
		return true;
	}

	public int getID() {
		return ID;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public String getSifra() {
		return sifra;
	}

	public String getEmail() {
		return email;
	}

	public static ArrayList<PristupniPodaci> getSviPristupniPodaci() {
		return sviPristupniPodaci;
	}

	public static void ispisiPodatke() {

		for (PristupniPodaci p : sviPristupniPodaci)
			System.out.println(p.getKorisnickoIme() + " " + p.getEmail() + " " + p.getSifra());
	}
}
