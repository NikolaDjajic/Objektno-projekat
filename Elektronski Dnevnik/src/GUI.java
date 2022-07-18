import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;  
import javax.mail.*;  
import javax.mail.internet.*;  
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GUI extends Application {
	private static Konekcija konekcija = new Konekcija();
	private ArrayList<Skola> skoleProf;
	private ArrayList<PredmetUSkoli> predmetiProf;
	private ArrayList<OcjenaPredmeta> ocjenePredmeta;
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		VBox vboxLogin = new VBox();
		vboxLogin.setPadding(new Insets(40, 50, 20, 50));
		vboxLogin.setAlignment(Pos.BASELINE_CENTER);

		Label naslov = new Label("Elektronski dnevnik");	
		naslov.setId("naslov");
		
		Label ime = new Label("Unesite korisnicko ime ili email");
		ime.setId("l1");
		
		TextField tfime = new TextField();
		tfime.setMinWidth(250);
		tfime.setMaxWidth(250);
		VBox vbime = new VBox();
		vbime.setAlignment(Pos.BASELINE_CENTER);
		vbime.getChildren().addAll(ime, tfime);
		vbime.setPadding(new Insets(15,0,0,0));

		Label lblozinka = new Label("Unesite lozinku");
		lblozinka.setId("l1");
		
		TextField tflozinka = new TextField();
		tflozinka.setMinWidth(250);
		tflozinka.setMaxWidth(250);
		VBox vblozinka = new VBox();
		vblozinka.setAlignment(Pos.BASELINE_CENTER);
		vblozinka.getChildren().addAll(lblozinka, tflozinka);
		vblozinka.setPadding(new Insets(25,0,0,0));
		
		Button prijava = new Button("Prijava");
		Label obavjestenje = new Label("");
		
		obavjestenje.setFont(new Font("Arial", 20));
		obavjestenje.setTextFill(Color.DARKRED);

		VBox vbprijava = new VBox(25);
		vbprijava.setAlignment(Pos.BASELINE_CENTER);
		vbprijava.getChildren().addAll(obavjestenje, prijava);

		vboxLogin.getChildren().addAll(naslov, vbime, vblozinka, vbprijava);

		Scene scenaLogin = new Scene(vboxLogin, 520, 550);
		scenaLogin.getStylesheets().add("Prijava.css");
		primaryStage.setScene(scenaLogin);
		primaryStage.setResizable(false);
		primaryStage.show();

		// DUGME

		prijava.setOnAction(e -> {

			if (tfime.getText().isEmpty()) {
				obavjestenje.setText("Korisnicko ime ili email nije uneseno!");
				return;
			}
			if (tflozinka.getText().isEmpty()) {
				obavjestenje.setText("Sifra nije unesena!");
				return;
			}
			obavjestenje.setText("");

			 String korisnickoIme=tfime.getText();
			 String lozinka=tflozinka.getText();
			
			int pristupni = PristupniPodaci.prijava(korisnickoIme, lozinka);

			if (pristupni == -1) {
				obavjestenje.setText("Pogresno uneseni podaci!");
				return;
			}

			//---------------------------------- UCENIK ------------------------------	
			
			boolean ucen = Ucenik.postojiUcenik(pristupni);
			
			if (ucen) {
				HBox root = new HBox(20);
				Scene scena = new Scene(root, 1000, 500);
				scena.getStylesheets().add("Ucenik.css");
				VBox vbLijevi = new VBox();
				Ucenik ucenik = Ucenik.getUcenikByPP(pristupni);
				ucenik.setSkola();
				root.getChildren().clear();

				ArrayList<Ocjena> ocjene = ucenik.getOcjene();
				ArrayList<Izostanci> izostanci = ucenik.getIzostanci();
				ArrayList<Profesor> profesori = ucenik.getPrefoesori();
				ocjenePredmeta = ucenik.getOcjenePredmeta();

				Button bOcjene = new Button("Ocjene");
				Button bIzostanci = new Button("Izostanci");
				Button bPocetna = new Button("Pocetna");
				Button bOcijeni = new Button("Profesori");
				Button bNovaLozinka=new Button("Nova lozinka");
				Button odjava=new Button("Odjavi se");
				
				bOcjene.setId("menuB");
				bIzostanci.setId("menuB");
				bPocetna.setId("menuB");
				bOcijeni.setId("menuB");
				bNovaLozinka.setId("menuB");
				odjava.setId("menuB");
				
				bOcjene.setMaxSize(120, 60);
				bIzostanci.setMaxSize(120, 60);
				bPocetna.setMaxSize(120, 60);
				bOcijeni.setMaxSize(120, 60);
				bNovaLozinka.setMaxSize(120, 60);
				odjava.setMaxSize(120, 60);
				
				bOcjene.setMinSize(120, 60);
				bIzostanci.setMinSize(120, 60);
				bPocetna.setMinSize(120, 60);
				bOcijeni.setMinSize(120, 60);
				bNovaLozinka.setMinSize(120, 60);
				odjava.setMinSize(120, 60);

				vbLijevi.getChildren().addAll(bPocetna, bOcjene, bIzostanci, bOcijeni,bNovaLozinka,odjava);

				VBox desniPoc = new VBox(15);
				Label dd = new Label("Dobro dosli u eDnevnik " + ucenik.getPristupniPodaci().getKorisnickoIme());

				dd.setAlignment(Pos.CENTER);
				
				Label imeL=new Label("Ime: " + ucenik.getIme());
				Label prezimeL=new Label("Prezime: " + ucenik.getPrezime());
				Label polL=new Label("Pol: " + ucenik.getPol());
				Label skolaL=new Label("");
				Label mjestoL=new Label("");
				Label gradL=new Label("");
				Label drzavaL=new Label("");
				Label predmetiL=new Label("");
				
				imeL.setId("wLabel");
				prezimeL.setId("wLabel");
				polL.setId("wLabel");
				skolaL.setId("wLabel");
				mjestoL.setId("wLabel");
				gradL.setId("wLabel");
				drzavaL.setId("wLabel");
				predmetiL.setId("wLabel");
				
				imeL.setPadding(new Insets(25,0,0,0));
				if(ucenik.getSkola()==null) {
					skolaL.setText("Jos uvijek ne pohadjate skolu.");
				}
				else {
					skolaL.setText("Skola koju pohadjate: " + ucenik.getSkola().getNaziv());
					mjestoL.setText("Mjesto: " +ucenik.getSkola().getMjesto());
					gradL.setText("Grad: "+ucenik.getSkola().getGrad());
					drzavaL.setText("Drzava: "+ucenik.getSkola().getDrzava());
					predmetiL.setText("Predmeti koje pohadjate: "+ucenik.ispisiPredmete());
				}
				
				desniPoc.getChildren().addAll(dd,imeL,prezimeL,skolaL,mjestoL,gradL,drzavaL,predmetiL);
				desniPoc.setMinSize(scena.getWidth()-bPocetna.getWidth()-130, scena.getHeight());
				desniPoc.setAlignment(Pos.TOP_CENTER);

				dd.setId("naslov");

				HBox hPocetni = new HBox(20);
				hPocetni.getChildren().addAll(vbLijevi, desniPoc);
				root.getChildren().setAll(hPocetni);

				
				primaryStage.setScene(scena);
				primaryStage.show();

				bPocetna.setOnAction(e1 -> {

					root.getChildren().clear();
					root.getChildren().addAll(vbLijevi, desniPoc);
					primaryStage.setScene(scena);
					primaryStage.show();

				});

				bOcjene.setOnAction(e1 -> {
					VBox desni1 = new VBox(10);
					desni1.setAlignment(Pos.TOP_CENTER);

					root.getChildren().clear();
					root.getChildren().addAll(vbLijevi,desni1);
					
					Label gr = new Label("");
					HBox hbDg=new HBox(25);
					Button sveOc=new Button("Sve ocjene");
					Button prekoPr=new Button("Predmeti");
					sveOc.setId("obButton");
					prekoPr.setId("obButton");
					gr.setId("gLabel");
					
					hbDg.setMinWidth(scena.getWidth()-bOcjene.getWidth());
					hbDg.setAlignment(Pos.TOP_CENTER);
					hbDg.setPadding(new Insets(20,0,0,0));
					hbDg.getChildren().addAll(sveOc,prekoPr);
					
					sveOc.setOnAction(e2->{
				
						desni1.getChildren().clear();
						desni1.setAlignment(Pos.TOP_CENTER);

						if(ocjene.isEmpty()) {
							root.getChildren().clear();
							gr.setTextFill(Color.RED);
							gr.setText("Jos uvijek nemate ocjena!");
							VBox vbGr=new VBox();
							vbGr.getChildren().add(gr);
							vbGr.setAlignment(Pos.CENTER);
							vbGr.setPadding(new Insets(50,0,0,0));
							desni1.getChildren().addAll(hbDg,vbGr);
							root.getChildren().addAll(vbLijevi, desni1);
							return;
						}
						
						Label l = new Label("Vase ocjene:");
						l.setAlignment(Pos.CENTER);
						l.setMinWidth(scena.getWidth()-bOcjene.getWidth());
						l.setId("wLabel");
						
						TableView t = new TableView();
					
						TableColumn<Map, String> tc1 = new TableColumn("Naziv predmeta");
						tc1.setCellValueFactory(new MapValueFactory<>("nazivpr"));

						TableColumn<Map, String> tc2 = new TableColumn("Predmetni profesor");
						tc2.setCellValueFactory(new MapValueFactory<>("prpf"));

						TableColumn<Map, String> tc3 = new TableColumn("Datum");
						tc3.setCellValueFactory(new MapValueFactory<>("datum"));

						TableColumn<Map, Integer> tc4 = new TableColumn("Ocjena");
						tc4.setCellValueFactory(new MapValueFactory<>("ocjena"));

						t.getColumns().addAll(tc1, tc2, tc3, tc4);

						ObservableList<Map<String, Object>> items = FXCollections
								.<Map<String, Object>>observableArrayList();

						List<Ocjena> ocjene1 = new ArrayList<>();
						for (Ocjena o : ocjene)
							ocjene1.add(o);
						
						
						 Collections.sort(ocjene1, new Comparator<Ocjena>(){
							 @Override
					           public int compare (Ocjena o, Ocjena o1){
					        	   String niz[]=o.getDatum().split("-");
					        	   String niz1[]=o1.getDatum().split("-");
					        	   if(Integer.parseInt(niz[0])>Integer.parseInt(niz1[0]))
					        		   return -1;
					        	   else if(Integer.parseInt(niz[0])<Integer.parseInt(niz1[0]))
					        		   return 1;
					        	   else {
					        		   if(Integer.parseInt(niz[1])>Integer.parseInt(niz1[1]))
						        		   return -1;
						        	   else if(Integer.parseInt(niz[1])<Integer.parseInt(niz1[1]))
						        		   return 1;
						        	   else {       		   
						        		   if(Integer.parseInt(niz[2])>Integer.parseInt(niz1[2]))
							        		   return -1;
							        	   else if(Integer.parseInt(niz[2])<Integer.parseInt(niz1[2]))
							        		   return 1;
						        	   }        		   
					        	   }
					        	   return 1;
					           	}
						 });
						
						for (Ocjena o : ocjene1) {
							Map<String, Object> nm = new HashMap<>();
							nm.put("nazivpr", o.getPredmetUSkoli().getPredmet().getNaziv());
							nm.put("prpf", o.getPredmetUSkoli().getProfesor().getIme() + " "
									+ o.getPredmetUSkoli().getProfesor().getPrezime());
							nm.put("datum", o.getDatum());
							nm.put("ocjena", o.getOcjena());
							items.add(nm);
						}

						t.setMinSize(scena.getWidth()-200, scena.getHeight()-250);
						t.setMaxSize(scena.getWidth()-200, scena.getHeight()-250);
						t.getItems().addAll(items);
						
						desni1.getChildren().addAll(hbDg,l, t);
						primaryStage.setScene(scena);
						primaryStage.show();
					});
					
					prekoPr.setOnAction(e2->{
						desni1.getChildren().clear();
						Label l1=new Label("Izaberite predmet");
						
						l1.setId("wLabel");
						ChoiceBox<PredmetUSkoli> cb=new ChoiceBox<>();
						desni1.setAlignment(Pos.TOP_CENTER);
						desni1.getChildren().addAll(hbDg,cb,l1);

						if(ucenik.getPohadjaniPredmeti().isEmpty()) {
							root.getChildren().clear();
							gr.setTextFill(Color.RED);
							gr.setPadding(new Insets(30,0,0,0));
							gr.setText("Jos uvijek nemate ocjena!");
							desni1.getChildren().addAll(gr);
							root.getChildren().addAll(vbLijevi, desni1);
							return;
						}
						
						cb.getItems().addAll(ucenik.getPohadjaniPredmeti());
						cb.setOnAction(e3->{
							desni1.getChildren().clear();
							
							if(cb.getValue()==null) {
								gr.setTextFill(Color.RED);
								gr.setText("Izaberite predmet");
								return;
							}
							
							Label l2 = new Label("Vase ocjene:");
							l2.setAlignment(Pos.CENTER);
							l2.setMinWidth(scena.getWidth()-bOcjene.getWidth());
							l2.setId("wLabel");
							
							TableView t = new TableView();
					
							TableColumn<Map, String> tc1 = new TableColumn("Naziv predmeta");
							tc1.setCellValueFactory(new MapValueFactory<>("nazivpr"));
							
							TableColumn<Map, String> tc2 = new TableColumn("Predmetni profesor");
							tc2.setCellValueFactory(new MapValueFactory<>("prpf"));

							TableColumn<Map, String> tc3 = new TableColumn("Datum");
							tc3.setCellValueFactory(new MapValueFactory<>("datum"));
						
							TableColumn<Map, Integer> tc4 = new TableColumn("Ocjena");
							tc4.setCellValueFactory(new MapValueFactory<>("ocjena"));

							t.getColumns().addAll(tc1, tc2, tc3, tc4);

							ObservableList<Map<String, Object>> items = FXCollections
									.<Map<String, Object>>observableArrayList();
							
							List<Ocjena> ocjene1 = new ArrayList<>();
							for (Ocjena o : ocjene) 
								if(o.getPredmetUSkoli().equals(cb.getValue())) 
									ocjene1.add(o);
							
							 Collections.sort(ocjene1, new Comparator<Ocjena>(){
								 @Override
						           public int compare (Ocjena o, Ocjena o1){
						        	   String niz[]=o.getDatum().split("-");
						        	   String niz1[]=o1.getDatum().split("-");
						        	   if(Integer.parseInt(niz[0])>Integer.parseInt(niz1[0]))
						        		   return -1;
						        	   else if(Integer.parseInt(niz[0])<Integer.parseInt(niz1[0]))
						        		   return 1;
						        	   else {
						        		   if(Integer.parseInt(niz[1])>Integer.parseInt(niz1[1]))
							        		   return -1;
							        	   else if(Integer.parseInt(niz[1])<Integer.parseInt(niz1[1]))
							        		   return 1;
							        	   else {       		   
							        		   if(Integer.parseInt(niz[2])>Integer.parseInt(niz1[2]))
								        		   return -1;
								        	   else if(Integer.parseInt(niz[2])<Integer.parseInt(niz1[2]))
								        		   return 1;
							        	   }        		   
						        	   }
						        	   return 1;
						           	}
							 });
							int p=1;
							
							for (Ocjena o : ocjene1) {	
									p=0;
									Map<String, Object> nm = new HashMap<>();
									nm.put("nazivpr", o.getPredmetUSkoli().getPredmet().getNaziv());
									nm.put("prpf", o.getPredmetUSkoli().getProfesor().getIme() + " "
											+ o.getPredmetUSkoli().getProfesor().getPrezime());
									nm.put("datum", o.getDatum());
									nm.put("ocjena", o.getOcjena());
									items.add(nm);
							}
							if(p==1) {
								gr.setText("Jos uvijek nemate ocjenu iz ovog predmeta!");
								desni1.getChildren().addAll(hbDg,l1,cb,gr);
								primaryStage.setScene(scena);
								primaryStage.show();
								return;
							}

							t.setMinSize(scena.getWidth()-200, scena.getHeight()-250);
							t.setMaxSize(scena.getWidth()-200, scena.getHeight()-250);
							t.getItems().addAll(items);
						
							l1.setPadding(new Insets(0,0,9,0));
							desni1.getChildren().addAll(hbDg,cb,l1,l2,t);
							primaryStage.setScene(scena);
							primaryStage.show();
						});
						
						primaryStage.setScene(scena);
						primaryStage.show();
					});
					
					desni1.getChildren().add(hbDg);
					primaryStage.setScene(scena);
					primaryStage.show();
				});

				bIzostanci.setOnAction(e1 -> {

					VBox desni2 = new VBox(20);
					root.getChildren().clear();
					root.getChildren().addAll(vbLijevi, desni2);

					desni2.setAlignment(Pos.TOP_CENTER);
					desni2.setMinWidth(scena.getWidth()-bIzostanci.getWidth());
					desni2.setPadding(new Insets(25,0,0,0));
					
					if (!izostanci.isEmpty()) {
						root.getChildren().clear();

						Label l = new Label("Vasi izostanci:");
						l.setId("wLabel");
						TableView t = new TableView();

						TableColumn<Map, String> tc1 = new TableColumn("Naziv predmeta");
						tc1.setCellValueFactory(new MapValueFactory<>("nazivpr"));

						TableColumn<Map, String> tc2 = new TableColumn("Predmetni profesor");
						tc2.setCellValueFactory(new MapValueFactory<>("prpf"));

						TableColumn<Map, String> tc3 = new TableColumn("Datum");
						tc3.setCellValueFactory(new MapValueFactory<>("datum"));
						
						t.getColumns().addAll(tc1, tc2, tc3);

						ObservableList<Map<String, Object>> items = FXCollections
								.<Map<String, Object>>observableArrayList();	
				
						List<Izostanci> izostanci1 = new ArrayList<>();
						for (Izostanci i : izostanci)
							izostanci1.add(i);
						
						
						 Collections.sort(izostanci1, new Comparator<Izostanci>(){
							 @Override
					           public int compare (Izostanci i, Izostanci i1){
					        	   String niz[]=i.getDatum().split("-");
					        	   String niz1[]=i1.getDatum().split("-");
					        	   if(Integer.parseInt(niz[0])>Integer.parseInt(niz1[0]))
					        		   return -1;
					        	   else if(Integer.parseInt(niz[0])<Integer.parseInt(niz1[0]))
					        		   return 1;
					        	   else {
					        		   if(Integer.parseInt(niz[1])>Integer.parseInt(niz1[1]))
						        		   return -1;
						        	   else if(Integer.parseInt(niz[1])<Integer.parseInt(niz1[1]))
						        		   return 1;
						        	   else {       		   
						        		   if(Integer.parseInt(niz[2])>Integer.parseInt(niz1[2]))
							        		   return -1;
							        	   else if(Integer.parseInt(niz[2])<Integer.parseInt(niz1[2]))
							        		   return 1;
						        	   }        		   
					        	   }
					        	   return 1;
					           	}
						 });
					
						for (Izostanci i : izostanci1) {							
							Map<String, Object> nm = new HashMap<>();
							nm.put("nazivpr", i.getPredmetUSkoli().getPredmet().getNaziv());
							nm.put("prpf", i.getPredmetUSkoli().getProfesor().getIme() + " "
									+ i.getPredmetUSkoli().getProfesor().getPrezime());
							nm.put("datum", i.getDatum());
							items.add(nm);
						}

						t.setMinSize(scena.getWidth()-200, scena.getHeight()-250);
						t.setMaxSize(scena.getWidth()-200, scena.getHeight()-250);	
						t.getItems().addAll(items);

						desni2.getChildren().addAll(l, t);
						root.getChildren().addAll(vbLijevi, desni2);
					}
					else {
						root.getChildren().clear();
						Label gr = new Label("Jos uvijek nemate izostanaka!");
						gr.setTextFill(Color.RED);
						gr.setId("gLabel");
						desni2.getChildren().addAll(gr);
						desni2.setAlignment(Pos.TOP_CENTER);
						desni2.setPadding(new Insets(100,0,0,0));
						root.getChildren().addAll(vbLijevi, desni2);
					}
					primaryStage.setScene(scena);
					primaryStage.show();
				});

				bOcijeni.setOnAction(e1 -> {
					root.getChildren().clear();
			
					VBox desni3 = new VBox(25);
					desni3.setAlignment(Pos.TOP_CENTER);
					HBox header = new HBox(30);
					root.getChildren().addAll(vbLijevi,desni3);

					Button prikaziTROC = new Button("Ocijenjeni profesori");
					Button ocijeniPR = new Button("Ocijeni profesora");
					prikaziTROC.setId("obButton");
					ocijeniPR.setId("obButton");
					
					TableView t1 = new TableView();
					t1.setMaxSize(scena.getWidth()-bIzostanci.getWidth()-106,300);
					
					Label gr1= new Label();
					gr1.setId("gLabel");
					desni3.getChildren().clear();
					desni3.setPadding(new Insets(20,0,0,0));
					desni3.setMinWidth(scena.getWidth()-bOcijeni.getWidth());
					desni3.setMaxWidth(scena.getWidth()-bOcijeni.getWidth());
					header.setAlignment(Pos.CENTER);
					header.getChildren().addAll(prikaziTROC, ocijeniPR);
					
					Label l1 = new Label("Profesori koje ste ocijenili:");
					l1.setId("wLabel");
					
					if(ocjenePredmeta.isEmpty()) {
						
						desni3.getChildren().clear();
						desni3.getChildren().addAll(header,gr1);
						desni3.setAlignment(Pos.TOP_CENTER);
						gr1.setPadding(new Insets(40,0,0,0));
						gr1.setTextFill(Color.RED);
						gr1.setText("Za sada niste ocijenili nijednog profesora!");
						
						primaryStage.setScene(scena);
						primaryStage.show();
					}
					else {
					t1.getItems().clear();
					
					TableColumn<Map, String> tc1 = new TableColumn("Naziv predmeta");
					tc1.setCellValueFactory(new MapValueFactory<>("nazivpr"));
					
					TableColumn<Map, String> tc2 = new TableColumn("Razred");
					tc2.setCellValueFactory(new MapValueFactory<>("raz"));

					TableColumn<Map, String> tc3 = new TableColumn("Predmetni profesor");
					tc3.setCellValueFactory(new MapValueFactory<>("prpf"));

					TableColumn<Map, String> tc4 = new TableColumn("Pitanje");
					tc4.setCellValueFactory(new MapValueFactory<>("pitanje"));

					TableColumn<Map, Integer> tc5 = new TableColumn("Ocjena");
					tc5.setCellValueFactory(new MapValueFactory<>("ocjena"));

					t1.getColumns().addAll(tc1, tc2, tc3, tc4,tc5);

					ObservableList<Map<String, Object>> items = FXCollections
							.<Map<String, Object>>observableArrayList();

					for (OcjenaPredmeta o : ocjenePredmeta) {
						Map<String, Object> nm = new HashMap<>();
						nm.put("nazivpr", o.getPredmetUSkoli().getPredmet().getNaziv());
						nm.put("raz", o.getPredmetUSkoli().getPredmet().getRazred());
						nm.put("prpf", o.getPredmetUSkoli().getProfesor().getIme() + " "
								+ o.getPredmetUSkoli().getProfesor().getPrezime());
						nm.put("pitanje", o.getPitanje().getTekst());
						nm.put("ocjena", o.getOcjena());
						items.add(nm);
					}

					t1.getItems().addAll(items);
					t1.setPlaceholder(new Label("Za sada niste ocijenili nijednog profesora."));
					desni3.getChildren().addAll(header,l1, t1);
					}
					prikaziTROC.setOnAction(e4 -> {
						desni3.getChildren().clear();						
						header.setAlignment(Pos.CENTER);

						if(ocjenePredmeta.isEmpty()) {
							
							desni3.getChildren().addAll(header,gr1);
							gr1.setTextFill(Color.RED);
							gr1.setText("Za sada niste ocijenili nijednog profesora!");
						}
						else {
						t1.getItems().clear();
						
						TableColumn<Map, String> tc1 = new TableColumn("Naziv predmeta");
						tc1.setCellValueFactory(new MapValueFactory<>("nazivpr"));
						
						TableColumn<Map, String> tc2 = new TableColumn("Razred");
						tc2.setCellValueFactory(new MapValueFactory<>("raz"));

						TableColumn<Map, String> tc3 = new TableColumn("Predmetni profesor");
						tc3.setCellValueFactory(new MapValueFactory<>("prpf"));

						TableColumn<Map, String> tc4 = new TableColumn("Pitanje");
						tc4.setCellValueFactory(new MapValueFactory<>("pitanje"));

						TableColumn<Map, Integer> tc5 = new TableColumn("Ocjena");
						tc5.setCellValueFactory(new MapValueFactory<>("ocjena"));

						t1.getColumns().addAll(tc1, tc2, tc3, tc4,tc5);

						ObservableList<Map<String, Object>> items = FXCollections
								.<Map<String, Object>>observableArrayList();

						for (OcjenaPredmeta o : ocjenePredmeta) {
							Map<String, Object> nm = new HashMap<>();
							nm.put("nazivpr", o.getPredmetUSkoli().getPredmet().getNaziv());
							nm.put("raz", o.getPredmetUSkoli().getPredmet().getRazred());
							nm.put("prpf", o.getPredmetUSkoli().getProfesor().getIme() + " "
									+ o.getPredmetUSkoli().getProfesor().getPrezime());
							nm.put("pitanje", o.getPitanje().getTekst());
							nm.put("ocjena", o.getOcjena());
							items.add(nm);
						}

						t1.getItems().addAll(items);
						t1.setPlaceholder(new Label("Za sada niste ocijenili nijednog profesora."));
						desni3.getChildren().addAll(header, l1, t1);
						}
						primaryStage.setScene(scena);
						primaryStage.show();
					});

					ocijeniPR.setOnAction(e2 -> {

						root.getChildren().clear();
						desni3.getChildren().clear();

						VBox vBoxcb = new VBox(10);
						Label l = new Label("Izaberite profesora kojeg zelite ocijeniti");
						l.setId("wLabel");
						ChoiceBox<String> cb = new ChoiceBox();

						for (Profesor p : profesori)
							cb.getItems().add(p.getIme() + " " + p.getPrezime());

						vBoxcb.setAlignment(Pos.CENTER);
						vBoxcb.getChildren().addAll(l, cb);

						ToggleGroup tg1 = new ToggleGroup();
						RadioButton rb11 = new RadioButton("1");
						RadioButton rb12 = new RadioButton("2");
						RadioButton rb13 = new RadioButton("3");
						RadioButton rb14 = new RadioButton("4");
						RadioButton rb15 = new RadioButton("5");

						rb11.setId("rButton");
						rb12.setId("rButton");
						rb13.setId("rButton");
						rb14.setId("rButton");
						rb15.setId("rButton");
						
						rb11.setToggleGroup(tg1);
						rb12.setToggleGroup(tg1);
						rb13.setToggleGroup(tg1);
						rb14.setToggleGroup(tg1);
						rb15.setToggleGroup(tg1);

						Label p1 = new Label();
						p1.setText(Pitanje.getPitanjeByID(1).getTekst() + ":\t\t\t\t\t\t");
						p1.setId("wLabel");
						HBox h1 = new HBox(30);
						h1.getChildren().addAll(p1, rb11, rb12, rb13, rb14, rb15);

						ToggleGroup tg2 = new ToggleGroup();
						RadioButton rb21 = new RadioButton("1");
						RadioButton rb22 = new RadioButton("2");
						RadioButton rb23 = new RadioButton("3");
						RadioButton rb24 = new RadioButton("4");
						RadioButton rb25 = new RadioButton("5");

						rb21.setId("rButton");
						rb22.setId("rButton");
						rb23.setId("rButton");
						rb24.setId("rButton");
						rb25.setId("rButton");
						
						rb21.setToggleGroup(tg2);
						rb22.setToggleGroup(tg2);
						rb23.setToggleGroup(tg2);
						rb24.setToggleGroup(tg2);
						rb25.setToggleGroup(tg2);

						Label p2 = new Label();
						p2.setText(Pitanje.getPitanjeByID(2).getTekst() + ":\t\t");
						p2.setId("wLabel");
						HBox h2 = new HBox(30);
						h2.getChildren().addAll(p2, rb21, rb22, rb23, rb24, rb25);

						ToggleGroup tg3 = new ToggleGroup();
						RadioButton rb31 = new RadioButton("1");
						RadioButton rb32 = new RadioButton("2");
						RadioButton rb33 = new RadioButton("3");
						RadioButton rb34 = new RadioButton("4");
						RadioButton rb35 = new RadioButton("5");
						
						rb31.setId("rButton");
						rb32.setId("rButton");
						rb33.setId("rButton");
						rb34.setId("rButton");
						rb35.setId("rButton");
						
						rb31.setToggleGroup(tg3);
						rb32.setToggleGroup(tg3);
						rb33.setToggleGroup(tg3);
						rb34.setToggleGroup(tg3);
						rb35.setToggleGroup(tg3);

						Label p3 = new Label();
						p3.setText(Pitanje.getPitanjeByID(3).getTekst() + ":\t\t\t\t\t");
						p3.setId("wLabel");
						HBox h3 = new HBox(30);
						h3.getChildren().addAll(p3, rb31, rb32, rb33, rb34, rb35);

						ToggleGroup tg4 = new ToggleGroup();
						RadioButton rb41 = new RadioButton("1");
						RadioButton rb42 = new RadioButton("2");
						RadioButton rb43 = new RadioButton("3");
						RadioButton rb44 = new RadioButton("4");
						RadioButton rb45 = new RadioButton("5");

						rb41.setId("rButton");
						rb42.setId("rButton");
						rb43.setId("rButton");
						rb44.setId("rButton");
						rb45.setId("rButton");
						
						rb41.setToggleGroup(tg4);
						rb42.setToggleGroup(tg4);
						rb43.setToggleGroup(tg4);
						rb44.setToggleGroup(tg4);
						rb45.setToggleGroup(tg4);

						Label p4 = new Label();
						p4.setText(Pitanje.getPitanjeByID(4).getTekst() + ":\t");
						p4.setId("wLabel");
						HBox h4 = new HBox(30);
						h4.getChildren().addAll(p4, rb41, rb42, rb43, rb44, rb45);

						VBox dg = new VBox(20);
						dg.setAlignment(Pos.CENTER);
						dg.setPadding(new Insets(20, 0, 0, 0));
						Button potvrdi = new Button("Potvrdi");
						potvrdi.setId("obButton");
						
						Label greska = new Label();
						greska.setTextFill(Color.RED);
						greska.setId("gLabel");

						dg.getChildren().addAll(greska, potvrdi);
						desni3.getChildren().addAll(header, vBoxcb, h1, h2, h3, h4, dg);
						root.getChildren().addAll(vbLijevi, desni3);

						potvrdi.setOnAction(e3 -> {

							if (cb.getValue() == null) {
								greska.setText("Niste izabrali profesora!");
								return;
							}
							if (tg1.getSelectedToggle() == null) {
								greska.setTextFill(Color.RED);
								greska.setText("Niste dali ocjenu za prvo pitanje!");
								return;
							}
							if (tg2.getSelectedToggle() == null) {
								greska.setTextFill(Color.RED);
								greska.setText("Niste dali ocjenu za drugo pitanje!");
								return;
							}
							if (tg3.getSelectedToggle() == null) {
								greska.setTextFill(Color.RED);
								greska.setText("Niste dali ocjenu za trece pitanje!");
								return;
							}
							if (tg4.getSelectedToggle() == null) {
								greska.setTextFill(Color.RED);
								greska.setText("Niste dali ocjenu za cetvrto pitanje!");
								return;
							}
							greska.setText("");

							String prof = cb.getValue();
							String niz[] = prof.split(" ");
							Profesor profa = Profesor.getProfByImeIPrezime(niz[0], niz[1]);

							if (OcjenaPredmeta.postojiOcjenaPr(ucenik, profa)) {
								greska.setTextFill(Color.RED);
								greska.setText("Ovoga profesora ste vec ocijenili!");
							}

							else {
								int ocjenaPitanja1 = 0;
								int ocjenaPitanja2 = 0;
								int ocjenaPitanja3 = 0;
								int ocjenaPitanja4 = 0;

								// prvo pitanje

								if (rb11.isSelected())
									ocjenaPitanja1 = 1;
								if (rb12.isSelected())
									ocjenaPitanja1 = 2;
								if (rb13.isSelected())
									ocjenaPitanja1 = 3;
								if (rb14.isSelected())
									ocjenaPitanja1 = 4;
								if (rb15.isSelected())
									ocjenaPitanja1 = 5;

								// drugo pitanje

								if (rb21.isSelected())
									ocjenaPitanja2 = 1;
								if (rb22.isSelected())
									ocjenaPitanja2 = 2;
								if (rb23.isSelected())
									ocjenaPitanja2 = 3;
								if (rb24.isSelected())
									ocjenaPitanja2 = 4;
								if (rb25.isSelected())
									ocjenaPitanja2 = 5;

								// trece pitanje

								if (rb31.isSelected())
									ocjenaPitanja3 = 1;
								if (rb32.isSelected())
									ocjenaPitanja3 = 2;
								if (rb33.isSelected())
									ocjenaPitanja3 = 3;
								if (rb34.isSelected())
									ocjenaPitanja3 = 4;
								if (rb35.isSelected())
									ocjenaPitanja3 = 5;

								// cetvrto pitanje

								if (rb41.isSelected())
									ocjenaPitanja4 = 1;
								if (rb42.isSelected())
									ocjenaPitanja4 = 2;
								if (rb43.isSelected())
									ocjenaPitanja4 = 3;
								if (rb44.isSelected())
									ocjenaPitanja4 = 4;
								if (rb45.isSelected())
									ocjenaPitanja4 = 5;

								ArrayList<PredmetUSkoli> pohadjani = ucenik.getPohadjaniPredmeti();
								PredmetUSkoli pus = null;

								for (PredmetUSkoli p : pohadjani)
									if (p.getProfesor().equals(profa))
										pus = p;
								
								OcjenaPredmeta oc1 = new OcjenaPredmeta(
										OcjenaPredmeta.getSveOcjenePredmeta().size() + 1, pus, ucenik,
										Pitanje.getPitanjeByID(1), ocjenaPitanja1);
								OcjenaPredmeta oc2 = new OcjenaPredmeta(
										OcjenaPredmeta.getSveOcjenePredmeta().size() + 1, pus, ucenik,
										Pitanje.getPitanjeByID(2), ocjenaPitanja2);
								OcjenaPredmeta oc3 = new OcjenaPredmeta(
										OcjenaPredmeta.getSveOcjenePredmeta().size() + 1, pus, ucenik,
										Pitanje.getPitanjeByID(3), ocjenaPitanja3);
								OcjenaPredmeta oc4 = new OcjenaPredmeta(
										OcjenaPredmeta.getSveOcjenePredmeta().size() + 1, pus, ucenik,
										Pitanje.getPitanjeByID(4), ocjenaPitanja4);
								
								ocjenePredmeta=ucenik.getOcjenePredmeta();
								
								konekcija.dodajOcjenuProfSQL(oc1);
								konekcija.dodajOcjenuProfSQL(oc2);
								konekcija.dodajOcjenuProfSQL(oc3);
								konekcija.dodajOcjenuProfSQL(oc4);
								greska.setTextFill(Color.LIMEGREEN);
								greska.setText("Uspjesno ste ocijenili profesora!");

							}
						});
						primaryStage.setScene(scena);
						primaryStage.show();
					});
					primaryStage.setScene(scena);
					primaryStage.show();
				});
				
				bNovaLozinka.setOnAction(e1->{
					VBox vbLozinka = new VBox(15);
					root.getChildren().clear();
					root.getChildren().addAll(vbLijevi,vbLozinka);
					
					vbLozinka.setMinWidth(scena.getWidth()-bNovaLozinka.getWidth());
					vbLozinka.setAlignment(Pos.BASELINE_CENTER);
					vbLozinka.setPadding(new Insets(120,0,0,0));
					
					Label l=new Label("Unesite svoju novu lozinku");
					Label greska=new Label("");
					l.setId("wLabel");
					greska.setId("gLabel");
					TextField tf=new TextField();
					Button potvrdi=new Button ("Potvrdi");
					potvrdi.setId("obButton");
					potvrdi.setOnAction(e2->{
						
						if(tf.getText().equals("")) {
							greska.setTextFill(Color.RED);
							greska.setText("Unesite novu lozinku!");
							return;
						}
						if(tf.getText().length()<6) {
							greska.setTextFill(Color.RED);
							greska.setText("Lozinka mora da ima barem 6 karaktera!");
							return;
						}
						konekcija.novaLozinka(ucenik.getPristupniPodaci(), tf.getText());
						greska.setTextFill(Color.LIMEGREEN);
						greska.setText("Uspjesno ste promijenili lozinku!");
						String text="Upravo ste promjenili vasu lozinku na eDnevniku.\n\nVasa lozinka je"+tf.getText()+".";		
						posaljiMejl(ucenik.getPristupniPodaci().getEmail(), "Promjena lozinke.",text);
					});
					
					
					
					tf.setMaxWidth(180);
					vbLozinka.getChildren().addAll(l,tf,greska,potvrdi);
					
					
					primaryStage.setScene(scena);
					primaryStage.show();	
				});
				
				odjava.setOnAction(e2->{
					tfime.setText("");
					tflozinka.setText("");
					primaryStage.setScene(scenaLogin);
					primaryStage.show();
				});
				
				//---------------------------------- PROFESOR ------------------------------
			} else {
				
				HBox root = new HBox(0);
				Scene scena = new Scene(root, 950, 520);
				scena.getStylesheets().add("Profesor.css");
				Profesor profesor=Profesor.getProfesorByPP(pristupni);
				skoleProf=PredmetUSkoli.getSkoleByProf(profesor);
				predmetiProf=PredmetUSkoli.getPredmetUSkoliByProf(profesor, skoleProf);
				
				VBox vbLijevi = new VBox();
				
				Button bPocetna=new Button("Pocetna");
				Button bRad= new Button("Rad sa ucenicima");
				Button bDodajUcenika=new Button("Dodaj ucenika");
				Button bDodajPredmet=new Button("Dodaj predmet");
				Button bDodajSkolu=new Button("Dodaj skolu");
				Button bDodajProfesora=new Button("Dodaj profesora");
				Button bDodajPuS=new Button("Izaberi predmet");
				Button bNovaLozinka=new Button("Nova lozinka");
				Button odjava=new Button("Odjavi se");

				bPocetna.setId("menuB");
				bRad.setId("menuB");
				bDodajUcenika.setId("menuB");
				bDodajPredmet.setId("menuB");
				bDodajProfesora.setId("menuB");
				bDodajPuS.setId("menuB");
				bDodajSkolu.setId("menuB");
				bNovaLozinka.setId("menuB");
				odjava.setId("menuB");
				
				vbLijevi.getChildren().addAll(bPocetna,bRad,bDodajPuS,bDodajUcenika,bDodajPredmet,bDodajSkolu,bDodajProfesora,bNovaLozinka,odjava);
				
				bPocetna.setMaxSize(145, 55);
				bDodajPredmet.setMaxSize(145, 55);
				bDodajProfesora.setMaxSize(145, 55);
				bDodajSkolu.setMaxSize(145, 55);
				bDodajUcenika.setMaxSize(145, 55);
				bRad.setMaxSize(145, 55);
				bDodajPuS.setMaxSize(145, 55);
				bNovaLozinka.setMaxSize(145, 55);
				odjava.setMaxSize(145, 55);
				
				bPocetna.setMinSize(145, 55);
				bDodajPredmet.setMinSize(145, 55);
				bDodajProfesora.setMinSize(145, 55);
				bDodajSkolu.setMinSize(145, 55);
				bDodajUcenika.setMinSize(145, 55);
				bRad.setMinSize(145, 55);
				bDodajPuS.setMinSize(145, 55);
				bNovaLozinka.setMinSize(145, 55);
				odjava.setMinSize(145, 55);
				
				VBox desniPoc = new VBox(12);
				Label dd = new Label("Dobro dosli u eDnevnik " + profesor.getPristupniPodaci().getKorisnickoIme());

				dd.setAlignment(Pos.CENTER);
				
				Label imeL=new Label("Ime: " + profesor.getIme());
				Label prezimeL=new Label("Prezime: " + profesor.getPrezime());
				Label polL=new Label("Pol: " + profesor.getPol());
				Label skolaL=new Label("");
				Label predmetiL=new Label("");
				predmetiL.setAlignment(Pos.CENTER);
				
				imeL.setId("wLabel");
				prezimeL.setId("wLabel");
				polL.setId("wLabel");
				skolaL.setId("wLabel");
				predmetiL.setId("wLabel");
				
				VBox skole=new VBox();
				skole.setAlignment(Pos.TOP_CENTER);
				VBox predPr=new VBox();
				predPr.setAlignment(Pos.TOP_CENTER);
				if(skoleProf.size()==0) {
					skolaL.setText("Jos uvijek ne predajete u bilo kojoj skoli.");
				}
				else {
					
					for(Skola s:skoleProf) {
						Label sk=new Label(s.toString());
						sk.setId("wLabel");
						skole.getChildren().add(sk);
					}
					for(PredmetUSkoli pus:predmetiProf) {
						Label p=new Label(pus.toString());
						p.setId("wLabel");
						predPr.getChildren().add(p);
					}
					skolaL.setText("Skole u kojima predajete: ");
					predmetiL.setText("Predmeti koje predajete: ");
				}
				
				desniPoc.getChildren().addAll(dd,imeL,prezimeL,polL,skolaL,skole,predmetiL,predPr);
				desniPoc.setMinSize(scena.getWidth()-bPocetna.getWidth()-130, scena.getHeight());
				desniPoc.setAlignment(Pos.TOP_CENTER);

				dd.setId("naslov");
				
				bPocetna.setOnAction(e1 -> {

					root.getChildren().clear();
					root.getChildren().addAll(vbLijevi, desniPoc);
					primaryStage.setScene(scena);
					primaryStage.show();

				});
				
				bDodajPuS.setOnAction(e1->{
					VBox vbDesniPuS=new VBox();
					vbDesniPuS.setAlignment(Pos.TOP_CENTER);
					vbDesniPuS.setPadding(new Insets(0,0,0,0));
					root.getChildren().clear();
					root.getChildren().addAll(vbLijevi,vbDesniPuS);
					
					ChoiceBox<Skola> cb1= new ChoiceBox<>();
					ChoiceBox<Predmet> cb2= new ChoiceBox<>();
					Button dodaj=new Button("Dodaj");
					dodaj.setId("obButton");
					
					ArrayList<Skola> sveSk=Skola.getSveSkole();
					ArrayList<Predmet> sviPr=Predmet.getSviPredmeti();
					Label l1=new Label("Izaberite skolu:");
					Label l2=new Label("Izaberite predmet:");
					l1.setId("wLabel");
					l2.setId("wLabel");
					Label gr=new Label();
					gr.setPadding(new Insets(20,0,20,0));
					gr.setId("gLabel");
					VBox vb1=new VBox();
					VBox vb2=new VBox();
					vb1.getChildren().addAll(l1,cb1);
					vb2.getChildren().addAll(l2,cb2);
					vb1.setAlignment(Pos.CENTER);
					vb2.setAlignment(Pos.CENTER);
					
					HBox header=new HBox(40);
					header.setAlignment(Pos.CENTER);
					header.setMinWidth(scena.getWidth()- bDodajPuS.getWidth()-30);
					header.setMaxWidth(scena.getWidth()- bDodajPuS.getWidth()-30);
					header.getChildren().addAll(vb1,vb2);
					header.setPadding(new Insets(70,0,0,0));
					
					cb1.getItems().addAll(sveSk);
					cb2.getItems().addAll(sviPr);
					
					dodaj.setOnAction(e2->{
					
						if(cb1.getValue()==null) {
							gr.setTextFill(Color.RED);
							gr.setText("Odaberite skolu!");
							return;
						}
						if(cb2.getValue()==null) {
							gr.setTextFill(Color.RED);
							gr.setText("Odaberite predmet!");
							return;
						}
					
						for(PredmetUSkoli pu:predmetiProf) 
							if(pu.getProfesor().equals(profesor) && pu.getSkola().equals(cb1.getValue()) && pu.getPredmet().equals(cb2.getValue())) {
								gr.setTextFill(Color.RED);
								gr.setText("Vi vec predajete ovaj predmet u ovoj skoli");
								return;
							}			
						PredmetUSkoli novi = new PredmetUSkoli(cb2.getValue(), cb1.getValue(),profesor);
						konekcija.dodajPredmetUSkoliSQL(novi);
						gr.setTextFill(Color.LIMEGREEN);
						gr.setText("Uspjesno ste odabrali predmet koji cete predavati!");
						skoleProf=PredmetUSkoli.getSkoleByProf(profesor);
						predmetiProf=PredmetUSkoli.getPredmetUSkoliByProf(profesor, skoleProf);
					});
					vbDesniPuS.getChildren().addAll(header,gr,dodaj);
					primaryStage.setScene(scena);
					primaryStage.show();
				});
				
				bRad.setOnAction(e1->{
					VBox vbDesniRad=new VBox();
					root.getChildren().clear();
					root.getChildren().addAll(vbLijevi,vbDesniRad);
					
					ChoiceBox<Skola> cb1= new ChoiceBox<>();
					ChoiceBox<PredmetUSkoli> cb2= new ChoiceBox<>();
					ChoiceBox<Ucenik> cb3= new ChoiceBox<>();
					
					VBox vbCB1=new VBox();
					VBox vbCB2=new VBox();
					VBox vbCB3=new VBox();
					
					vbCB1.setAlignment(Pos.CENTER);
					vbCB2.setAlignment(Pos.CENTER);
					vbCB3.setAlignment(Pos.CENTER);
					
					HBox h1=new HBox(20);
					HBox h2= new HBox(30);
					VBox header=new VBox(3);
					
					Label l1=new Label("Izaberite skolu");
					Label l2=new Label("Izaberite predmet");
					Label l3=new Label("Izaberite ucenika");
					Label g=new Label("");
					
					l1.setId("wLabel");
					l2.setId("wLabel");
					l3.setId("wLabel");
					g.setId("gLabel");
					
					Button bOcjeneProf = new Button("Vase ocjene");
					Button bOcijeni = new Button("Ocijeni");
					Button bIzostanak = new Button("Dodaj izostanak");
					
					bOcjeneProf.setId("obButton");
					bOcijeni.setId("obButton");
					bIzostanak.setId("obButton");
	
					vbCB1.getChildren().addAll(cb1,l1);
					vbCB2.getChildren().addAll(cb2,l2);
					vbCB3.getChildren().addAll(cb3,l3);
					h1.getChildren().addAll(vbCB1,vbCB2,vbCB3);
					h2.getChildren().addAll(bOcjeneProf,bOcijeni,bIzostanak);
					h2.setAlignment(Pos.CENTER);
					h1.setAlignment(Pos.CENTER);
					header.getChildren().addAll(h2,g,h1);
					header.setMinWidth(scena.getWidth()-bRad.getWidth()-20);
					header.setMaxWidth(scena.getWidth()-bRad.getWidth()-20);
					header.setAlignment(Pos.TOP_CENTER);
					header.setPadding(new Insets(15,0,15,0));
					
					bIzostanak.setOnAction(e2->{
						VBox vbDesniIzostanak=new VBox();
						vbDesniIzostanak.setAlignment(Pos.TOP_CENTER);
						vbDesniIzostanak.setMinWidth(scena.getWidth()-bRad.getWidth()-30);
						vbDesniIzostanak.setMaxWidth(scena.getWidth()-bRad.getWidth()-30);
						root.getChildren().clear();
						root.getChildren().addAll(vbLijevi,vbDesniIzostanak);
						
						Button nazad=new Button("Nazad");
						nazad.setId("nazadButton");
						HBox he1=new HBox();
						he1.setPadding(new Insets(0,0,20,0));
						he1.getChildren().add(nazad);
						
						nazad.setOnAction(e6->{
							root.getChildren().clear();
							root.getChildren().addAll(vbLijevi,header);
							g.setText("");
							cb1.setValue(null);
							cb2.setValue(null);
							cb3.setValue(null);
							primaryStage.setScene(scena);
							primaryStage.show();
						});
						
						ArrayList<Ucenik> sviUcenici=Ucenik.getSviUcenici();
						for(Ucenik u:sviUcenici) 
							u.setSkola();
						
						ChoiceBox<Skola> cb11= new ChoiceBox<>();
						ChoiceBox<PredmetUSkoli> cb22= new ChoiceBox<>();
						ChoiceBox<Ucenik> cb33= new ChoiceBox<>();
						
						Button dodaj=new Button("Dodaj");
						dodaj.setId("obButton");
						
						Label format=new Label("Upisite datum (g-m-d)");
						Label lab1=new Label("-");
						Label lab2=new Label("-");
						Label greska=new Label("");
						Label la1=new Label("Izaberite skolu");
						Label la2=new Label("Izaberite predmet");
						Label la3=new Label("Izaberite ucenika");
						
						format.setId("wLabel");
						lab1.setId("wLabel");
						lab2.setId("wLabel");
						la1.setId("wLabel");
						la2.setId("wLabel");
						la3.setId("wLabel");
						greska.setId("gLabel");
						
						la1.setPadding(new Insets(15,0,0,0));
						la2.setPadding(new Insets(15,0,0,0));
						la3.setPadding(new Insets(15,0,0,0));
						greska.setPadding(new Insets(15,0,20,0));
						format.setPadding(new Insets(15,0,0,0));

						TextField tf1=new TextField();
						TextField tf2=new TextField();
						TextField tf3=new TextField();
						HBox dtm=new HBox(10);
						dtm.setMinWidth(scena.getWidth()-bRad.getWidth()-30);
						dtm.setAlignment(Pos.CENTER);
						
						tf1.setMinWidth(60);
						tf2.setMinWidth(39);
						tf3.setMinWidth(39);
						tf1.setMaxWidth(60);
						tf2.setMaxWidth(39);
						tf3.setMaxWidth(39);

						cb11.getItems().addAll(skoleProf);
						cb11.setOnAction(e3->{
							cb22.getItems().clear();
							cb33.getItems().clear();
							
							for(PredmetUSkoli s:predmetiProf)
								if(s.getSkola().equals(cb11.getValue()))
									cb22.getItems().add(s);
							
							cb22.setOnAction(e4->{
								cb33.getItems().clear();
								
								if(cb22.getValue()==null) 
									return;

								for(Ucenik u : sviUcenici) 
									if((u.getRazred()==cb22.getValue().getPredmet().getRazred() || u.getRazred()==0)) 
										if(u.getSkola()==null || u.getSkola().equals(cb11.getValue()))
											cb33.getItems().add(u);
							});
						});
						
						dodaj.setOnAction(e3->{
							
							if(cb11.getValue()==null) {
								greska.setTextFill(Color.RED);
								greska.setText("Odaberite skolu!");
							}
							if(cb22.getValue()==null) {
								greska.setTextFill(Color.RED);
								greska.setText("Odaberite predmet!");
							}
							if(cb33.getValue()==null) {
								greska.setTextFill(Color.RED);
								greska.setText("Odaberite ucenika!");
							}
							if(tf1.getText().equals("") || tf2.getText().equals("") || tf3.getText().equals("")) {
								greska.setTextFill(Color.RED);
								greska.setText("Unesite sva polja!");
								return;
							}
							if(!datumValja(tf3.getText(), tf2.getText(), tf1.getText())) {
								greska.setTextFill(Color.RED);
								greska.setText("Taj datum ne postoji!");
								return;
							}
							
							String god=tf1.getText();
							String mj=tf2.getText();
							String dan=tf3.getText();
							
							if(mj.length()==1)
								mj="0"+mj;
							if(dan.length()==1)
								dan="0"+dan;
							
							String datum=""+god+"-"+mj+"-"+dan;
						
							boolean	val1=Izostanci.validacija1(cb33.getValue(), cb22.getValue());
							boolean val2=Izostanci.validacija2(cb33.getValue(), cb22.getValue(),datum);
							
							if(!val1) {
								greska.setTextFill(Color.RED);
								greska.setText("Ne mozete napisati izostanak.(Drugi profesor je zaduzen za ovog ucenika.)");
								return;
							}
							if(!val2) {
								greska.setTextFill(Color.RED);
								greska.setText("Ne mozete napisati izostanak.(Ucenik vec ima izostanak ili ocjenu na ovaj dan.)");
								return;
							}
							if(val1 && val2) {
								Izostanci novi = new Izostanci(cb33.getValue(),cb22.getValue(), datum);
								konekcija.dodajIzostanakSQL(novi);
								cb33.getValue().setRazred(cb22.getValue().getPredmet().getRazred());
								cb33.getValue().setSk(cb11.getValue());
								greska.setTextFill(Color.LIMEGREEN);
								greska.setText("Uspjesno dodan izostanak!");
							}		
						});
						dtm.getChildren().addAll(tf1,lab1,tf2,lab2,tf3);
						vbDesniIzostanak.getChildren().addAll(he1,la1,cb11,la2,cb22,la3,cb33,format,dtm,greska,dodaj);
						primaryStage.setScene(scena);
						primaryStage.show();
					});
					
					bOcijeni.setOnAction(e2->{
						VBox vbDesniOcijeni=new VBox();
						vbDesniOcijeni.setAlignment(Pos.TOP_CENTER);
						vbDesniOcijeni.setMinWidth(scena.getWidth()-bRad.getWidth()-30);
						vbDesniOcijeni.setMaxWidth(scena.getWidth()-bRad.getWidth()-30);
						root.getChildren().clear();
						root.getChildren().addAll(vbLijevi,vbDesniOcijeni);

						Button nazad=new Button("Nazad");
						nazad.setId("nazadButton");
						HBox hb1=new HBox();
						hb1.getChildren().add(nazad);
						
						nazad.setOnAction(e6->{
							root.getChildren().clear();
							root.getChildren().addAll(vbLijevi,header);
							g.setText("");
							cb1.setValue(null);
							cb2.setValue(null);
							cb3.setValue(null);
							primaryStage.setScene(scena);
							primaryStage.show();
						});
						
						ArrayList<Ucenik> sviUcenici=Ucenik.getSviUcenici();
						for(Ucenik u:sviUcenici) 
							u.setSkola();
						
						ChoiceBox<Skola> cb11= new ChoiceBox<>();
						ChoiceBox<PredmetUSkoli> cb22= new ChoiceBox<>();
						ChoiceBox<Ucenik> cb33= new ChoiceBox<>();
						
						Label format=new Label("Upisite datum (g-m-d)");
						Label lab1=new Label("-");
						Label lab2=new Label("-");
						Label greska=new Label("");
						Label la1=new Label("Izaberite skolu");
						Label la2=new Label("Izaberite predmet");
						Label la3=new Label("Izaberite ucenika");
						Label la4=new Label("Ocjena");

						format.setId("wLabel");
						lab1.setId("wLabel");
						lab2.setId("wLabel");
						la1.setId("wLabel");
						la2.setId("wLabel");
						la3.setId("wLabel");
						la4.setId("wLabel");
						greska.setId("gLabel");
						
						la1.setPadding(new Insets(15,0,0,0));
						la2.setPadding(new Insets(15,0,0,0));
						la3.setPadding(new Insets(15,0,0,0));
						la4.setPadding(new Insets(15,0,0,0));
						greska.setPadding(new Insets(15,0,20,0));
						format.setPadding(new Insets(15,0,0,0));
						
						TextField tf1=new TextField();
						TextField tf2=new TextField();
						TextField tf3=new TextField();
						tf1.setMinWidth(60);
						tf2.setMinWidth(39);
						tf3.setMinWidth(39);
						tf1.setMaxWidth(60);
						tf2.setMaxWidth(39);
						tf3.setMaxWidth(39);
						
						HBox dtm=new HBox(10);
						dtm.setMinWidth(scena.getWidth()-bRad.getWidth()-30);
						dtm.setAlignment(Pos.CENTER);
						
						Button dodaj=new Button("Dodaj");
						dodaj.setId("obButton");
						cb11.getItems().addAll(skoleProf);
						cb11.setOnAction(e3->{
							cb22.getItems().clear();
							cb33.getItems().clear();
							
							for(PredmetUSkoli s:predmetiProf)
								if(s.getSkola().equals(cb11.getValue()))
									cb22.getItems().add(s);
							
							cb22.setOnAction(e4->{
								cb33.getItems().clear();
								
								if(cb22.getValue()==null) 
									return;

								for(Ucenik u : sviUcenici) 
									if((u.getRazred()==cb22.getValue().getPredmet().getRazred() || u.getRazred()==0)) 
										if(u.getSkola()==null || u.getSkola().equals(cb11.getValue()))
											cb33.getItems().add(u);
							});
						});			
						ChoiceBox<Integer> biranjeOcjena= new ChoiceBox<>();
						biranjeOcjena.getItems().addAll(1,2,3,4,5);
						
						dodaj.setOnAction(e3->{	
							if(cb11.getValue()==null) {
								greska.setTextFill(Color.RED);
								greska.setText("Odaberite skolu!");
							}
							if(cb22.getValue()==null) {
								greska.setTextFill(Color.RED);
								greska.setText("Odaberite predmet!");
							}
							if(cb33.getValue()==null) {
								greska.setTextFill(Color.RED);
								greska.setText("Odaberite ucenika!");
							}
							if(tf1.getText().equals("") || tf2.getText().equals("") || tf3.getText().equals("") || biranjeOcjena.getValue()==null) {
								greska.setTextFill(Color.RED);
								greska.setText("Unesite sva polja!");
								return;
							}
							if(!datumValja(tf3.getText(), tf2.getText(), tf1.getText())) {
								greska.setTextFill(Color.RED);
								greska.setText("Taj datum ne postoji!");
								return;
							}
							
							String god=tf1.getText();
							String mj=tf2.getText();
							String dan=tf3.getText();
							
							if(mj.length()==1)
								mj="0"+mj;
							if(dan.length()==1)
								dan="0"+dan;
							if(god.length()<4) 
								for(int i=god.length();i<4;i++)
									god="0"+god;
							
							String datum=""+god+"-"+mj+"-"+dan;
						
							boolean val1=Ocjena.validacija1(cb33.getValue(), cb22.getValue());
							boolean val2=Ocjena.validacija2(cb33.getValue(), cb22.getValue(),datum);
							boolean val3=Ocjena.validacija3(cb33.getValue(), cb22.getValue(),datum);
							
							if(!val1) {
								greska.setTextFill(Color.RED);
								greska.setText("Ne mozete napisati ocjenu.(Drugi profesor je zaduzen za ovog ucenika.)");
								return;
							}
							if(!val2) {
								greska.setTextFill(Color.RED);
								greska.setText("Ne mozete napisati ocjenu.(Ucenik vec ima izostanak ili 2 ocjene na ovaj dan.)");
								return;
							}
							if(!val3) {
								greska.setTextFill(Color.RED);
								greska.setText("Ne mozete napisati ocjenu.(Rok od 7 dana nije prosao.)");
								return;
							}
							if(val1 && val2 && val3) {
								
								Ocjena nova = new Ocjena(cb33.getValue(), cb22.getValue(), biranjeOcjena.getValue(),datum);
								konekcija.dodajOcjenuSQL(nova);
								cb33.getValue().setRazred(cb22.getValue().getPredmet().getRazred());
								cb33.getValue().setSk(cb11.getValue());
								greska.setTextFill(Color.LIMEGREEN);
								greska.setText("Uspjesno dodana ocjena!");
							}
						});
						dtm.getChildren().addAll(tf1,lab1,tf2,lab2,tf3);
						vbDesniOcijeni.getChildren().addAll(hb1,la1,cb11,la2,cb22,la3,cb33,format,dtm,la4,biranjeOcjena,greska,dodaj);
						primaryStage.setScene(scena);
						primaryStage.show();
					});
					
					bOcjeneProf.setOnAction(e2->{
						
						Label greska = new Label();
						Label nas = new Label("Vase ocjene");
						VBox vbDesniOcjenaProf=new VBox();
						root.getChildren().clear();
						root.getChildren().addAll(vbLijevi,vbDesniOcjenaProf);
						vbDesniOcjenaProf.getChildren().addAll(header,greska);
						cb3.setValue(null);
						g.setId("gLabel");
						nas.setId("wLabel");

						if(cb2.getValue()==null ||cb1.getValue()==null) {

//-------------------------------------------------------------------------------------------------------------------------
							vbDesniOcjenaProf.getChildren().clear();
							vbDesniOcjenaProf.getChildren().addAll(header);
							
							g.setTextFill(Color.RED);
							g.setText("Izaberite skolu i predmet ispod!");
							
							primaryStage.setScene(scena);
							primaryStage.show();
							}
						else {
							g.setText("");
							ArrayList<OcjenaPredmeta> ocjenePredmeta = OcjenaPredmeta.getOcjeneByPred(cb2.getValue());
							
							VBox vbDesniOcjenaProfT= new VBox();
							root.getChildren().clear();
													
							TableView t1 = new TableView();
							
							TableColumn<Map, String> tc1 = new TableColumn("Naziv predmeta");
							tc1.setCellValueFactory(new MapValueFactory<>("nazivpr"));

							TableColumn<Map, Integer> tc2 = new TableColumn("Razred");
							tc2.setCellValueFactory(new MapValueFactory<>("raz"));

							TableColumn<Map, String> tc3 = new TableColumn("Skola");
							tc3.setCellValueFactory(new MapValueFactory<>("skola"));
							
							TableColumn<Map, String> tc4 = new TableColumn("Pitanje");
							tc4.setCellValueFactory(new MapValueFactory<>("pitanje"));

							TableColumn<Map, Integer> tc5 = new TableColumn("Ocjena");
							tc5.setCellValueFactory(new MapValueFactory<>("ocjena"));

							t1.getColumns().addAll(tc1, tc2, tc3, tc4,tc5);

							ObservableList<Map<String, Object>> items = FXCollections
									.<Map<String, Object>>observableArrayList();

							for (OcjenaPredmeta o : ocjenePredmeta) {
								Map<String, Object> nm = new HashMap<>();
								nm.put("nazivpr", o.getPredmetUSkoli().getPredmet().getNaziv());
								nm.put("raz", o.getPredmetUSkoli().getPredmet().getRazred());
								nm.put("skola", o.getPredmetUSkoli().getSkola());
								nm.put("pitanje", o.getPitanje().getTekst());
								nm.put("ocjena", o.getOcjena());
								items.add(nm);
							}

							t1.setMaxSize(755, 300);
							t1.setMinSize(755, 300);
							t1.getItems().addAll(items);
							t1.setPlaceholder(new Label("Ovaj predmet vam jos uvijek nije ocijenjen."));
							
							vbDesniOcjenaProfT.setAlignment(Pos.TOP_CENTER);
							vbDesniOcjenaProfT.getChildren().addAll(header,nas,t1);
							root.getChildren().addAll(vbLijevi,vbDesniOcjenaProfT);
							primaryStage.setScene(scena);
							primaryStage.show();
							}
					});
					
					for(Skola s:skoleProf)
						cb1.getItems().add(s);
					
					cb1.setOnAction(e2->{
						cb2.getItems().clear();
						cb3.getItems().clear();
						
						for(PredmetUSkoli s:predmetiProf)
							if(s.getSkola().equals(cb1.getValue()))
								cb2.getItems().add(s);
						
						cb2.setOnAction(e3->{
							cb3.getItems().clear();
							
							if(cb2.getValue()!=null) {
							
							ArrayList<Ucenik> ucenici=cb2.getValue().getUcenikeByPuS();
							for(Ucenik u:ucenici)
								cb3.getItems().addAll(u);
							}
							
							cb3.setOnAction(e4->{
								if(cb2.getValue()==null || cb3.getValue()==null)
									return;
								
								g.setText("");
								vbDesniRad.getChildren().clear();
								vbDesniRad.setAlignment(Pos.TOP_CENTER);
								root.getChildren().clear();
								root.getChildren().addAll(vbLijevi,vbDesniRad);
								
								ArrayList<Ocjena> ocjeneUcenika=Ocjena.getOcjenaByPuSiU(cb2.getValue(),cb3.getValue());
								
								TableView t2 = new TableView();
								
								TableColumn<Map, String> tc1 = new TableColumn("Naziv predmeta");
								tc1.setCellValueFactory(new MapValueFactory<>("nazivpr"));

								TableColumn<Map, Integer> tc2 = new TableColumn("Razred");
								tc2.setCellValueFactory(new MapValueFactory<>("raz"));

								TableColumn<Map, String> tc3 = new TableColumn("Skola");
								tc3.setCellValueFactory(new MapValueFactory<>("skola"));
								
								TableColumn<Map, String> tc4 = new TableColumn("Datum");
								tc4.setCellValueFactory(new MapValueFactory<>("datum"));

								TableColumn<Map, Integer> tc5 = new TableColumn("Ocjena");
								tc5.setCellValueFactory(new MapValueFactory<>("ocjena"));

								t2.getColumns().addAll(tc1, tc2, tc3, tc4,tc5);

								ObservableList<Map<String, Object>> items = FXCollections
										.<Map<String, Object>>observableArrayList();

								for (Ocjena o : ocjeneUcenika) {
									Map<String, Object> nm = new HashMap<>();
									nm.put("nazivpr", o.getPredmetUSkoli().getPredmet().getNaziv());
									nm.put("raz", o.getPredmetUSkoli().getPredmet().getRazred());
									nm.put("skola", o.getPredmetUSkoli().getSkola());
									nm.put("datum",o.getDatum() );
									nm.put("ocjena", o.getOcjena());
									items.add(nm);
								}

								t2.setMinSize(scena.getWidth()-bRad.getWidth()-80, 280);
								t2.setMaxSize(scena.getWidth()-bRad.getWidth()-80, 280);
								t2.getItems().addAll(items);
								t2.setPlaceholder(new Label("Ucenik nema ocjena."));
								
								Label greska1=new Label("");
								HBox footer = new HBox(40);
								footer.setAlignment(Pos.CENTER);
								
								Button bNOcjena=new Button("Upisi ocjenu");
								Button bNIzostanak=new Button("Upisi izostanak");
								bNOcjena.setId("obButton");
								bNIzostanak.setId("obButton");
								
								footer.getChildren().addAll(bNOcjena,bNIzostanak);
								
								bNIzostanak.setOnAction(e6->{
									
									if(cb2.getValue()==null || cb3.getValue()==null) {
										greska1.setTextFill(Color.RED);
										greska1.setText("Odaberite predmet i ucenika");
										return;
									}
									
									VBox vbNIzos=new VBox();
									vbNIzos.setAlignment(Pos.TOP_CENTER);
									vbNIzos.setMinWidth(scena.getWidth()-bRad.getWidth()-30);
									vbNIzos.setMaxWidth(scena.getWidth()-bRad.getWidth()-30);
									root.getChildren().clear();
									root.getChildren().addAll(vbLijevi,vbNIzos);
									
									Button nazad=new Button("Nazad");
									nazad.setId("nazadButton");
									HBox he=new HBox();
									he.getChildren().add(nazad);
									
									nazad.setOnAction(e7->{
										root.getChildren().clear();
										root.getChildren().addAll(vbLijevi,header);
										g.setText("");
										cb1.setValue(null);
										cb2.setValue(null);
										cb3.setValue(null);
										primaryStage.setScene(scena);
										primaryStage.show();
									});
									
									Label l=new Label("Dodajte izostanak za ucenika "+ cb3.getValue()+" iz predmeta "+ cb2.getValue());
									Label format=new Label("Upisite datum (g-m-d)");
									Label lab1=new Label("-");
									Label lab2=new Label("-");
									Label greska=new Label("");
									
									l.setId("wLabel");
									format.setId("wLabel");
									lab1.setId("wLabel");
									lab2.setId("wLabel");
									greska.setId("gLabel");
									
									l.setPadding(new Insets(10,0,25,0));
									greska.setPadding(new Insets(20,0,20,0));
									he.setPadding(new Insets(0,0,15,0));
									
									TextField tf1=new TextField();
									TextField tf2=new TextField();
									TextField tf3=new TextField();
									
									tf1.setMinWidth(60);
									tf2.setMinWidth(39);
									tf3.setMinWidth(39);
									tf1.setMaxWidth(60);
									tf2.setMaxWidth(39);
									tf3.setMaxWidth(39);
									
									Button napisi=new Button("Napisi");
									napisi.setId("obButton");
									HBox dtm=new HBox(10);
									dtm.setAlignment(Pos.TOP_CENTER);
									dtm.setMinWidth(scena.getWidth()-bRad.getWidth()-30);
									dtm.setMaxWidth(scena.getWidth()-bRad.getWidth()-30);
									
									napisi.setOnAction(e7->{	
										if(tf1.getText().equals("") || tf2.getText().equals("") || tf3.getText().equals("")) {
											greska.setTextFill(Color.RED);
											greska.setText("Unesite sva polja!");
											return;
										}
										if(!datumValja(tf3.getText(), tf2.getText(), tf1.getText())) {
											greska.setTextFill(Color.RED);
											greska.setText("Taj datum ne postoji!");
											return;
										}
										
										String god=tf1.getText();
										String mj=tf2.getText();
										String dan=tf3.getText();
										
										if(mj.length()==1)
											mj="0"+mj;
										if(dan.length()==1)
											dan="0"+dan;
										
										String datum=""+god+"-"+mj+"-"+dan;
										boolean val1=Izostanci.validacija1(cb3.getValue(), cb2.getValue());
										boolean val2=Izostanci.validacija2(cb3.getValue(), cb2.getValue(),datum);
										
										if(!val1) {
											greska.setTextFill(Color.RED);
											greska.setText("Ne mozete napisati izostanak.(Drugi profesor je zaduzen za ovog ucenika.)");
											return;
										}
										if(!val2) {
											greska.setTextFill(Color.RED);
											greska.setText("Ne mozete napisati izostanak.(Ucenik vec ima izostanak ili ocjenu na ovaj dan.)");
											return;
										}
										if(val1 && val2) {
											Izostanci novi = new Izostanci(cb3.getValue(),cb2.getValue(), datum);
											konekcija.dodajIzostanakSQL(novi);
											greska.setTextFill(Color.LIMEGREEN);
											greska.setText("Uspjesno dodan izostanak!");
										}		
									});
									dtm.getChildren().addAll(tf1,lab1,tf2,lab2,tf3);
									vbNIzos.getChildren().addAll(he,l,format,dtm,greska,napisi);	
									primaryStage.setScene(scena);
									primaryStage.show();
								});
								
								bNOcjena.setOnAction(e6->{
									
									if(cb2.getValue()==null || cb3.getValue()==null) {
										greska1.setTextFill(Color.RED);
										greska1.setText("Odaberite predmet i ucenika");
										return;
									}
									
									VBox vbNOcjena=new VBox();
									vbNOcjena.setAlignment(Pos.TOP_CENTER);
									vbNOcjena.setMinWidth(scena.getWidth()-bRad.getWidth()-30);
									vbNOcjena.setMaxWidth(scena.getWidth()-bRad.getWidth()-30);
									root.getChildren().clear();
									root.getChildren().addAll(vbLijevi,vbNOcjena);
									
									Button nazad=new Button("Nazad");						
									nazad.setId("nazadButton");
									HBox he= new HBox();
									he.getChildren().add(nazad);

									nazad.setOnAction(e7->{
										root.getChildren().clear();
										root.getChildren().addAll(vbLijevi,header);
										g.setText("");
										cb1.setValue(null);
										cb2.setValue(null);
										cb3.setValue(null);

										primaryStage.setScene(scena);
										primaryStage.show();
									});
									Label l=new Label("Dodajte ocjenu za ucenika "+ cb3.getValue()+" iz predmeta "+ cb2.getValue());
									Label la=new Label("Izaberite ocjenu");
									Label format=new Label("Upisite datum (g-m-d)");
									Label lab1=new Label("-");
									Label lab2=new Label("-");
									Label greska=new Label("");
									
									l.setId("wLabel");
									format.setId("wLabel");
									la.setId("wLabel");
									lab1.setId("wLabel");
									lab2.setId("wLabel");
									greska.setId("gLabel");
									
									format.setPadding(new Insets(15,0,0,0));
									l.setPadding(new Insets(10,0,25,0));
									greska.setPadding(new Insets(20,0,20,0));
									he.setPadding(new Insets(0,0,15,0));
									
									TextField tf1=new TextField();
									TextField tf2=new TextField();
									TextField tf3=new TextField();
									
									tf1.setMinWidth(60);
									tf2.setMinWidth(39);
									tf3.setMinWidth(39);
									tf1.setMaxWidth(60);
									tf2.setMaxWidth(39);
									tf3.setMaxWidth(39);	
									ChoiceBox<Integer> biranjeOcjena= new ChoiceBox<>();
									biranjeOcjena.getItems().addAll(1,2,3,4,5);
									
									Button napisi=new Button("Napisi");
									napisi.setId("obButton");
									HBox dtm=new HBox(10);
									dtm.setAlignment(Pos.TOP_CENTER);
									dtm.setMinWidth(scena.getWidth()-bRad.getWidth()-30);
									dtm.setMaxWidth(scena.getWidth()-bRad.getWidth()-30);
									
									napisi.setOnAction(e7->{
										
										if(tf1.getText().equals("") || tf2.getText().equals("") || tf3.getText().equals("") || biranjeOcjena.getValue()==null) {
											greska.setTextFill(Color.RED);
											greska.setText("Unesite sva polja");
											return;
										}
										
										if(!datumValja(tf3.getText(), tf2.getText(), tf1.getText())) {
											greska.setTextFill(Color.RED);
											greska.setText("Taj datum ne postoji!");
											return;
										}
										
										String god=tf1.getText();
										String mj=tf2.getText();
										String dan=tf3.getText();
										
										if(mj.length()==1)
											mj="0"+mj;
										if(dan.length()==1)
											dan="0"+dan;
										if(god.length()<4) 
											for(int i=god.length();i<4;i++)
												god="0"+god;
										
										
										String datum=""+god+"-"+mj+"-"+dan;
										boolean val1=Ocjena.validacija1(cb3.getValue(), cb2.getValue());
										boolean val2=Ocjena.validacija2(cb3.getValue(), cb2.getValue(),datum);
										boolean val3=Ocjena.validacija3(cb3.getValue(), cb2.getValue(),datum);
										
										if(!val1) {
											greska.setTextFill(Color.RED);
											greska.setText("Ne mozete napisati ocjenu.(Drugi profesor je zaduzen za ovog ucenika.)");
											return;
										}
										if(!val2) {
											greska.setTextFill(Color.RED);
											greska.setText("Ne mozete napisati ocjenu.(Ucenik vec ima izostanak ili ocjenu na ovaj dan.)");
											return;
										}
										if(!val3) {
											greska.setTextFill(Color.RED);
											greska.setText("Ne mozete napisati ocjenu.(Rok od 7 dana nije prosao.)");
											return;
										}
										if(val1 && val2 && val3) {
											
											Ocjena nova = new Ocjena(cb3.getValue(), cb2.getValue(), biranjeOcjena.getValue(),datum);
											konekcija.dodajOcjenuSQL(nova);
											greska.setTextFill(Color.LIMEGREEN);
											greska.setText("Uspjesno dodana ocjena");
										}
									});
									dtm.getChildren().addAll(tf1,lab1,tf2,lab2,tf3);
									vbNOcjena.getChildren().addAll(he,l,la,biranjeOcjena,format,dtm,greska,napisi);
									
									primaryStage.setScene(scena);
									primaryStage.show();
								});
								
								vbDesniRad.getChildren().addAll(header,t2,greska1,footer);
								primaryStage.setScene(scena);
								primaryStage.show();	
							});						
						});
					});						
					vbDesniRad.getChildren().addAll(header);
					primaryStage.setScene(scena);
					primaryStage.show();
				});
				
				bDodajPredmet.setOnAction(e1->{
					VBox vbDesniPredmet=new VBox();
					vbDesniPredmet.setAlignment(Pos.TOP_CENTER);
					vbDesniPredmet.setMinWidth(scena.getWidth()-bDodajPredmet.getWidth()-40);
					vbDesniPredmet.setMaxWidth(scena.getWidth()-bDodajPredmet.getWidth()-40);
					
					root.getChildren().clear();
					root.getChildren().addAll(vbLijevi,vbDesniPredmet);
					
					Label l1=new Label("Naziv novog predmeta");
					Label l2=new Label("Razred novog predmeta");
					Label greska=new Label("");
					l1.setId("wLabel");
					l2.setId("wLabel");
					greska.setId("gLabel");
					
					
					l1.setPadding(new Insets(70,0,0,0));
					l2.setPadding(new Insets(15,0,0,0));
					greska.setPadding(new Insets(20,0,20,0));

					TextField tf1=new TextField();
					tf1.setMaxWidth(160);
					
					ComboBox<Integer> cb = new ComboBox<>();
					cb.getItems().addAll(1,2,3,4,5,6,7,8,9);
					
					Button potvrdi=new Button("Dodaj");
					potvrdi.setId("obButton");
					potvrdi.setOnAction(e2->{
						
						if(tf1.getText().equals("") || cb.getValue()==null) {
							greska.setTextFill(Color.RED);
							greska.setText("Popunite sva polja!");
							return;
						}
						boolean val=Predmet.validacija(tf1.getText(),cb.getValue());
						
						if(val) {
							Predmet novi=new Predmet(tf1.getText(),cb.getValue());
							konekcija.dodajPredmetSQL(novi);
							greska.setTextFill(Color.LIMEGREEN);
							greska.setText("Predmet "+novi.getNaziv()+" za "+novi.getRazred()+". razred je dodan u sistem.");
						}
						else {
							greska.setTextFill(Color.RED);
							greska.setText("Predmet sa ovim nazivom i razredom vec postoji!");
						}		
					});
					vbDesniPredmet.getChildren().addAll(l1,tf1,l2,cb,greska,potvrdi);
					primaryStage.setScene(scena);
					primaryStage.show();
				});
			
				bDodajSkolu.setOnAction(e1->{
					VBox vbDesniSkola=new VBox();
					vbDesniSkola.setAlignment(Pos.TOP_CENTER);
					vbDesniSkola.setMinWidth(scena.getWidth()-bDodajPredmet.getWidth()-40);
					vbDesniSkola.setMaxWidth(scena.getWidth()-bDodajPredmet.getWidth()-40);
					vbDesniSkola.setPadding(new Insets(20,0,0,0));
					
					root.getChildren().clear();
					root.getChildren().addAll(vbLijevi,vbDesniSkola);
					
					Label l1=new Label("Naziv skole");
					Label l2=new Label("Mjesto");
					Label l3=new Label("Grad");
					Label l4=new Label("Drzava");
					Label greska=new Label("");
					
					l1.setId("wLabel");
					l2.setId("wLabel");
					l3.setId("wLabel");
					l4.setId("wLabel");
					greska.setId("gLabel");

					
					l1.setPadding(new Insets(15,0,0,0));
					l2.setPadding(new Insets(15,0,0,0));
					l3.setPadding(new Insets(15,0,0,0));
					l4.setPadding(new Insets(15,0,0,0));
					greska.setPadding(new Insets(20,0,20,0));
					
					TextField tf1=new TextField();
					TextField tf2=new TextField();
					TextField tf3=new TextField();
					TextField tf4=new TextField();
					
					tf1.setMaxWidth(160);
					tf2.setMaxWidth(160);
					tf3.setMaxWidth(160);					
					tf4.setMaxWidth(160);


					Button potvrdi=new Button("Dodaj");
					potvrdi.setId("obButton");
					potvrdi.setOnAction(e2->{
						
						if(tf1.getText().equals("") || tf2.getText().equals("") || tf3.getText().equals("") || tf4.getText().equals("")) {
							greska.setTextFill(Color.RED);
							greska.setText("Popunite sva polja!");
							return;
						}
						
						boolean val=Skola.validacija(tf1.getText(), tf2.getText(), tf3.getText(), tf4.getText());
						
						if(val) {
							Skola nova=new Skola(tf1.getText(),tf2.getText(),tf3.getText(),tf4.getText());
							konekcija.dodajNovuSkoluSQL(nova);
							greska.setTextFill(Color.LIMEGREEN);
							greska.setText("Skola "+nova+" je dodana u sistem.");
						}
						else {						
							greska.setTextFill(Color.RED);
							greska.setText("Ovakva skola vec postoji!");
						}
					});
					
					vbDesniSkola.getChildren().addAll(l1,tf1,l2,tf2,l3,tf3,l4,tf4,greska,potvrdi);
					primaryStage.setScene(scena);
					primaryStage.show();
				});
				
				bDodajUcenika.setOnAction(e1->{
					VBox vbDesniUcenik=new VBox();
					vbDesniUcenik.setAlignment(Pos.TOP_CENTER);
					vbDesniUcenik.setMinWidth(scena.getWidth()-bDodajPredmet.getWidth()-40);
					vbDesniUcenik.setMaxWidth(scena.getWidth()-bDodajPredmet.getWidth()-40);
					vbDesniUcenik.setPadding(new Insets(20,0,0,0));
					root.getChildren().clear();
					root.getChildren().addAll(vbLijevi,vbDesniUcenik);

					Label l1=new Label("Ime ucenika");
					Label l2=new Label("Prezime ucenika");
					Label l3=new Label("Izaberite pol");
					Label l4=new Label("Korisnicko ime");
					Label l5=new Label("Email");
					Label greska=new Label("");
					
					l1.setId("wLabel");
					l2.setId("wLabel");
					l3.setId("wLabel");
					l4.setId("wLabel");
					l5.setId("wLabel");
					greska.setId("gLabel");

					l1.setPadding(new Insets(15,0,0,0));
					l2.setPadding(new Insets(15,0,0,0));
					l3.setPadding(new Insets(15,0,0,0));
					l4.setPadding(new Insets(15,0,0,0));
					l5.setPadding(new Insets(15,0,0,0));
					greska.setPadding(new Insets(20,0,20,0));
					greska.setAlignment(Pos.CENTER);
					
					TextField tf1=new TextField();
					TextField tf2=new TextField();
					TextField tf4=new TextField();
					TextField tf5=new TextField();
					
					tf1.setMaxWidth(160);
					tf2.setMaxWidth(160);
					tf4.setMaxWidth(160);					
					tf5.setMaxWidth(160);
					
					HBox hb1=new HBox(20);
					VBox vb1=new VBox(5);
					VBox vb2=new VBox(5);
					Label la1=new Label("Muski");
					Label la2=new Label("Zenski");
					
					la1.setId("wLabel");
					la2.setId("wLabel");	
					
					ToggleGroup tg1 = new ToggleGroup();
					RadioButton rb1 = new RadioButton();
					RadioButton rb2 = new RadioButton();
					rb1.setToggleGroup(tg1);
					rb2.setToggleGroup(tg1);
					vb1.setAlignment(Pos.CENTER);
					vb2.setAlignment(Pos.CENTER);
					vb1.getChildren().addAll(rb1,la1);
					vb2.getChildren().addAll(rb2,la2);

					hb1.getChildren().addAll(vb1,vb2);
					hb1.setMinWidth(scena.getWidth()-bDodajPredmet.getWidth()-40);
					hb1.setMaxWidth(scena.getWidth()-bDodajPredmet.getWidth()-40);
					hb1.setAlignment(Pos.CENTER);
					
					Button potvrdi=new Button("Dodaj");
					potvrdi.setId("obButton");
					potvrdi.setOnAction(e2->{
						
					if(tf1.getText().equals("") || tf2.getText().equals("") || tg1.getSelectedToggle()==null || tf4.getText().equals("") || tf5.getText().equals("")) {
						greska.setTextFill(Color.RED);
						greska.setText("Popunite sva polja!");
						return;
					}
					
					boolean val1=Ucenik.validacija(tf1.getText(), tf2.getText());
					boolean val2=PristupniPodaci.validacija(tf4.getText(), tf5.getText());
					String pol="";
					if(rb1.isSelected())
						pol="Muski";
					if(rb2.isSelected())
						pol="Zenski";
					
					if(!val1) {
						greska.setTextFill(Color.RED);
						greska.setText("Ucenik sa ovim imenom i prezimenom vec postoji!");
						return;
					}
					if(!val2) {
						greska.setTextFill(Color.RED);
						greska.setText("Korisnicko ime ili email je vec u upotrebi!");
						return;
					}
					
					PristupniPodaci noviPP = new PristupniPodaci(tf4.getText(), tf5.getText(), tf4.getText()+"123");
					Ucenik novi = new Ucenik(tf1.getText(),tf2.getText(),pol,noviPP);
					
					konekcija.dodajNovogUcenikaSQL(novi);
					greska.setTextFill(Color.LIMEGREEN);
					greska.setText("Ucenik " +novi.getIme()+" "+novi.getPrezime()+" je dodan u sistem.Email sa pristupnim podacima je poslan na adresu "+novi.getPristupniPodaci().getEmail());
					
					String kome=novi.getPristupniPodaci().getEmail();
					String naslovMejla="Registracija";
					String textMejla="Dobrodosli "+novi.getIme()+" "+novi.getPrezime()+" u eDnevnik!\nProfesor "+profesor.getIme()+" "+profesor.getPrezime()+ " vas je registrovao u sistem kao ucenika!";
					textMejla+="\n\nVasi podaci:\nKorisnicko ime: "+novi.getPristupniPodaci().getKorisnickoIme();
					textMejla+="\nEmail:"+" "+novi.getPristupniPodaci().getEmail()+"\nLozinka: " + novi.getPristupniPodaci().getKorisnickoIme()+"123";
					
					posaljiMejl(kome, naslovMejla, textMejla);
					});
					
					vbDesniUcenik.getChildren().addAll(l1,tf1,l2,tf2,l3,hb1,l4,tf4,l5,tf5,greska,potvrdi);
					
					primaryStage.setScene(scena);
					primaryStage.show();
				});
				
				bDodajProfesora.setOnAction(e1->{
					VBox vbDesniProfesor=new VBox();
					vbDesniProfesor.setAlignment(Pos.TOP_CENTER);
					vbDesniProfesor.setMinWidth(scena.getWidth()-bDodajPredmet.getWidth()-40);
					vbDesniProfesor.setMaxWidth(scena.getWidth()-bDodajPredmet.getWidth()-40);
					vbDesniProfesor.setPadding(new Insets(20,0,0,0));
					root.getChildren().clear();
					root.getChildren().addAll(vbLijevi,vbDesniProfesor);
					
					Label l1=new Label("Ime profesora");
					Label l2=new Label("Prezime profesora");
					Label l3=new Label("Izaberite pol");
					Label l4=new Label("Korisnicko ime");
					Label l5=new Label("Email");
					Label greska=new Label("");
					
					l1.setPadding(new Insets(15,0,0,0));
					l2.setPadding(new Insets(15,0,0,0));
					l3.setPadding(new Insets(15,0,0,0));
					l4.setPadding(new Insets(15,0,0,0));
					l5.setPadding(new Insets(15,0,0,0));
					greska.setPadding(new Insets(20,0,20,0));
					
					l1.setId("wLabel");
					l2.setId("wLabel");
					l3.setId("wLabel");
					l4.setId("wLabel");
					l5.setId("wLabel");
					greska.setId("gLabel");
					
					TextField tf1=new TextField();
					TextField tf2=new TextField();
					TextField tf4=new TextField();
					TextField tf5=new TextField();
					
					tf1.setMaxWidth(160);
					tf2.setMaxWidth(160);
					tf4.setMaxWidth(160);					
					tf5.setMaxWidth(160);
					
					HBox hb1=new HBox(20);
					VBox vb1=new VBox(5);
					VBox vb2=new VBox(5);
					Label la1=new Label("Muski");
					Label la2=new Label("Zenski");
					
					la1.setId("wLabel");
					la2.setId("wLabel");		
					
					ToggleGroup tg1 = new ToggleGroup();
					RadioButton rb1 = new RadioButton();
					RadioButton rb2 = new RadioButton();
					rb1.setToggleGroup(tg1);
					rb2.setToggleGroup(tg1);
					
					rb1.setToggleGroup(tg1);
					rb2.setToggleGroup(tg1);
					vb1.setAlignment(Pos.CENTER);
					vb2.setAlignment(Pos.CENTER);
					vb1.getChildren().addAll(rb1,la1);
					vb2.getChildren().addAll(rb2,la2);

					hb1.getChildren().addAll(vb1,vb2);
					hb1.setMinWidth(scena.getWidth()-bDodajPredmet.getWidth()-40);
					hb1.setMaxWidth(scena.getWidth()-bDodajPredmet.getWidth()-40);
					hb1.setAlignment(Pos.CENTER);
					
					Button potvrdi=new Button("Dodaj");
					potvrdi.setId("obButton");
					potvrdi.setOnAction(e2->{
						
					if(tf1.getText().equals("") || tf2.getText().equals("") || tg1.getSelectedToggle()==null || tf4.getText().equals("") || tf5.getText().equals("")) {
						greska.setTextFill(Color.RED);
						greska.setText("Popunite sva polja!");
						return;
					}
					
					boolean val1=Profesor.validacija(tf1.getText(), tf2.getText());
					boolean val2=PristupniPodaci.validacija(tf4.getText(), tf5.getText());
					String pol="";
					if(rb1.isSelected())
						pol="Muski";
					if(rb2.isSelected())
						pol="Zenski";
					
					if(!val1) {
						greska.setTextFill(Color.RED);
						greska.setText("Profesor sa ovim imenom i prezimenom vec postoji!");
						return;
					}
					if(!val2) {
						greska.setTextFill(Color.RED);
						greska.setText("Korisnicko ime ili email je vec u upotrebi!");
						return;
					}
					
					PristupniPodaci noviPP = new PristupniPodaci(tf4.getText(), tf5.getText(), tf4.getText()+"123");
					Profesor novi = new Profesor(tf1.getText(),tf2.getText(),pol,noviPP);
					
					konekcija.dodajNovogProfSQL(novi);
					greska.setTextFill(Color.LIMEGREEN);
					greska.setText("Profesor " +novi.getIme()+" "+novi.getPrezime()+" je dodan u sistem.Email sa pristupnim podacima je poslan na adresu "+novi.getPristupniPodaci().getEmail());
				
					String kome=novi.getPristupniPodaci().getEmail();
					String naslovMejla="Registracija";
					String textMejla="Dobrodosli "+novi.getIme()+" "+novi.getPrezime()+" u eDnevnik!\nProfesor "+profesor.getIme()+" "+profesor.getPrezime()+ " vas je registrovao u sistem kao novog profesora!";
					textMejla+="\n\nVasi podaci:\nKorisnicko ime: "+novi.getPristupniPodaci().getKorisnickoIme();
					textMejla+="\nEmail:"+" "+novi.getPristupniPodaci().getEmail()+"\nLozinka: " + novi.getPristupniPodaci().getKorisnickoIme()+"123";
					
					posaljiMejl(kome, naslovMejla, textMejla);
					});
					vbDesniProfesor.getChildren().addAll(l1,tf1,l2,tf2,l3,hb1,l4,tf4,l5,tf5,greska,potvrdi);
					primaryStage.setScene(scena);
					primaryStage.show();
				});
				
				bNovaLozinka.setOnAction(e1->{
					VBox vbLozinka = new VBox(15);
					root.getChildren().clear();
					root.getChildren().addAll(vbLijevi,vbLozinka);
					
					vbLozinka.setMinWidth(scena.getWidth()-bNovaLozinka.getWidth()-40);
					vbLozinka.setAlignment(Pos.BASELINE_CENTER);
					vbLozinka.setPadding(new Insets(120,0,0,0));
					
					Label l=new Label("Unesite svoju novu lozinku");
					Label greska=new Label("");
					l.setId("wLabel");
					greska.setId("gLabel");
					TextField tf=new TextField();
					Button potvrdi=new Button ("Potvrdi");
					potvrdi.setId("obButton");
					potvrdi.setOnAction(e2->{
						
						if(tf.getText().equals("")) {
							greska.setTextFill(Color.RED);
							greska.setText("Unesite novu lozinku!");
							return;
						}
						if(tf.getText().length()<6) {
							greska.setTextFill(Color.RED);
							greska.setText("Lozinka mora da ima barem 6 karaktera!");
							return;
						}
						konekcija.novaLozinka(profesor.getPristupniPodaci(), tf.getText());
						greska.setTextFill(Color.LIMEGREEN);
						greska.setText("Uspjesno ste promijenili lozinku!");
						String text="Upravo ste promjenili vasu lozinku na eDnevniku.\n\nVasa lozinka je"+tf.getText()+".";
						
						posaljiMejl(profesor.getPristupniPodaci().getEmail(), "Promjena lozinke.",text);
					});
					
					tf.setMaxWidth(180);
					vbLozinka.getChildren().addAll(l,tf,greska,potvrdi);
					primaryStage.setScene(scena);
					primaryStage.show();	
				});
				
				odjava.setOnAction(e2->{
					tfime.setText("");
					tflozinka.setText("");
					skoleProf=null;
					predmetiProf=null;
					ocjenePredmeta=null;
					primaryStage.setScene(scenaLogin);
					primaryStage.show();
				});
				
				root.getChildren().clear();
				root.getChildren().addAll(vbLijevi,desniPoc);
				
				primaryStage.setScene(scena);
				primaryStage.show();	
			}
		});
		
		
	}	
	
	private static boolean datumValja(String dan,String mjesec,String god) {
		String formatS="MM/dd/yyyy";
		String noviDatum=""+mjesec+"/"+dan+"/"+god;
		
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatS);
            format.setLenient(false);
            format.parse(noviDatum);
        } catch (ParseException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
	
	private static void posaljiMejl(String kome,String naslov,String text) {
		
		final String mailPosiljaoca="ednevnik1234@gmail.com";
		final String lozinka="sifra123";
		
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
		
        Session sesija = Session.getDefaultInstance(prop,  
    		    new javax.mail.Authenticator() {  
    		      protected PasswordAuthentication getPasswordAuthentication() {  
    		    return new PasswordAuthentication(mailPosiljaoca,lozinka);  
    		      }
    		    }); 

        try {
		     MimeMessage poruka = new MimeMessage(sesija);  
		     poruka.setFrom(new InternetAddress(mailPosiljaoca));  
		     poruka.setRecipient(Message.RecipientType.TO,new InternetAddress(kome));  
		     poruka.setSubject(naslov);  
		     poruka.setText(text);  
		     
		     Transport.send(poruka);  
		     System.out.println("Poruka poslana!");  
		   
		     } catch (MessagingException e) {
		    	 e.printStackTrace();
		  } 
	}

	public static void main(String[] args) {

		konekcija.ucitajPodatke();
		
		
		
		
		launch(args);
	}
}
