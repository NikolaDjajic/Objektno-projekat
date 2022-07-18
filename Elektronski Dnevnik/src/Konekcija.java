import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Konekcija {

	public static Connection getConnection() {
		Connection con = null;
		try {
			// Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ors1_opp_2021_2022", "root", "password");
			// con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void ucitajPodatke() {

		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();

			ResultSet Rs3 = myStmt.executeQuery("select * from pristupni_podaci");
			while (Rs3.next()) {

				new PristupniPodaci(Rs3.getInt("id"), Rs3.getString("korisnicko_ime"), Rs3.getString("email"),
						Rs3.getString("sifra"));
			}

			ResultSet Rs9 = myStmt.executeQuery("select * from pitanje");
			while (Rs9.next()) {

				new Pitanje(Rs9.getInt("id"), Rs9.getString("pitanje"));
			}

			ResultSet Rs = myStmt.executeQuery("select * from profesor");
			while (Rs.next()) {

				new Profesor(Rs.getInt("id"), Rs.getString("ime"), Rs.getString("prezime"), Rs.getInt("pol"),
						Rs.getInt("pristupni_podaci_id"));
			}

			ResultSet Rs2 = myStmt.executeQuery("select * from skola");
			while (Rs2.next()) {

				new Skola(Rs2.getInt("id"), Rs2.getString("naziv"), Rs2.getString("grad"), Rs2.getString("mjesto"),
						Rs2.getString("drzava"));
			}

			ResultSet Rs1 = myStmt.executeQuery("select * from ucenik");
			while (Rs1.next()) {

				new Ucenik(Rs1.getInt("id"), Rs1.getString("ime"), Rs1.getString("prezime"), Rs1.getInt("pol"),
						Rs1.getInt("pristupni_podaci_id"));
			}

			ResultSet Rs5 = myStmt.executeQuery("select * from predmet");
			while (Rs5.next()) {

				new Predmet(Rs5.getInt("id"), Rs5.getString("naziv"), Rs5.getInt("razred"));
			}

			ResultSet Rs6 = myStmt.executeQuery("select * from predmet_u_skoli");
			while (Rs6.next()) {

				new PredmetUSkoli(Rs6.getInt("id"), Rs6.getInt("predmet_id"), Rs6.getInt("skola_id"),
						Rs6.getInt("profesor_id"));
			}

			ResultSet Rs7 = myStmt.executeQuery("select * from ocjena");
			while (Rs7.next()) {

				new Ocjena(Rs7.getInt("id"), Rs7.getInt("ucenik_id"), Rs7.getInt("predmet_u_skoli_id"),
						Rs7.getInt("ocjena"), Rs7.getString("datum"));
			}

			ResultSet Rs8 = myStmt.executeQuery("select * from ocjena_predmeta");
			while (Rs8.next()) {

				new OcjenaPredmeta(Rs8.getInt("id"), Rs8.getInt("predmet_u_skoli_id"), Rs8.getInt("ucenik_id"),
						Rs8.getInt("pitanje_id"), Rs8.getInt("ocjena"));
			}

			ResultSet Rs4 = myStmt.executeQuery("select * from izostanci");
			while (Rs4.next()) {

				new Izostanci(Rs4.getInt("id"), Rs4.getInt("ucenik_id"), Rs4.getInt("predmet_u_skoli_id"),
						Rs4.getString("datum"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void dodajPredmetSQL(Predmet p) {
		Connection myConn = null;
		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();

			String sql = "INSERT INTO predmet VALUES (" + p.getID() + "," + "'" + p.getNaziv() + "'" + ","
					+ p.getRazred() + ")";

			myStmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void dodajIzostanakSQL(Izostanci i) {
		Connection myConn = null;
		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();

			String sql = "INSERT INTO izostanci VALUES (" + i.getID() + "," + i.getUcenik().getID()  + ","
					+ i.getPredmetUSkoli().getID() +"," + "'" + i.getDatum() + "'" + ")";

			myStmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void dodajOcjenuSQL(Ocjena o) {
		Connection myConn = null;
		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();

			String sql = "INSERT INTO ocjena VALUES (" + o.getID() + "," + o.getUcenik().getID()  + ","
					+ o.getPredmetUSkoli().getID() +"," + o.getOcjena() +"," + "'" + o.getDatum() + "'" + ")";

			myStmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void dodajPredmetUSkoliSQL(PredmetUSkoli pus) {
		Connection myConn = null;
		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();

			String sql = "INSERT INTO predmet_u_skoli VALUES (" + pus.getID() + "," +  pus.getPredmet().getID() +  ","
					+ pus.getSkola().getID() + ","+ pus.getProfesor().getID() +")";

			myStmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void dodajOcjenuProfSQL(OcjenaPredmeta op) {
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();
			int predmetUSkoliID = PredmetUSkoli.getIDByPredmetUSkoli(op.getPredmetUSkoli());
			int ucenikID = Ucenik.getIDByUcenik(op.getUcenik());

			String sql = "INSERT INTO ocjena_predmeta VALUES (" + op.getID() + "," + predmetUSkoliID + "," + ucenikID
					+ "," + op.getPitanje().getID() + "," + op.getOcjena() + ")";

			myStmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void dodajNovuSkoluSQL(Skola s) {
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();

			String sql = "INSERT INTO skola VALUES (" + s.getID() + "," + "'" + s.getNaziv() + "'" + "," + "'"
					+ s.getGrad() + "'" + "," + "'" + s.getMjesto() + "'" + "," + "'" + s.getDrzava() + "'" + ")";

			myStmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void dodajNovogProfSQL(Profesor p) {
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();

			dodajNovePPSQL(p.getPristupniPodaci());
			int pol;
			
			if(p.getPol().equals("Muski"))
				pol=1;
			else
				pol=0;
			
			String sql = "INSERT INTO profesor VALUES (" + p.getID() + "," + "'" + p.getIme() + "'" + "," + "'"
					+ p.getPrezime() + "'" + "," + pol + "," + p.getPristupniPodaci().getID() + ")";

			myStmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void dodajNovogUcenikaSQL(Ucenik u) {
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();
			
			dodajNovePPSQL(u.getPristupniPodaci());
			int pol;
			
			if(u.getPol().equals("Muski"))
				pol=1;
			else
				pol=0;
			
			String sql = "INSERT INTO ucenik VALUES (" + u.getID() + "," + "'" + u.getIme() + "'" + "," + "'"
					+ u.getPrezime() + "'" + "," + pol + "," + u.getPristupniPodaci().getID() + ")";

			myStmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void dodajNovePPSQL(PristupniPodaci pp) {
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();
	
			String sql = "INSERT INTO pristupni_podaci VALUES (" + pp.getID() + "," + "'" + pp.getKorisnickoIme() + "'"
					+ "," + "'" + pp.getEmail() + "'" + "," + "'" + pp.getSifra() + "'" + ")";

			myStmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void novaLozinka(PristupniPodaci pp, String novaSifra) {
		Connection myConn = null;

		try {
			myConn = getConnection();
			Statement myStmt = myConn.createStatement();
			novaSifra = PristupniPodaci.getMd5(novaSifra);

			String sql = "UPDATE pristupni_podaci SET sifra=" + '"' + novaSifra + '"' + " WHERE id=" + pp.getID();
			myStmt.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (myConn != null) {
			try {
				myConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
