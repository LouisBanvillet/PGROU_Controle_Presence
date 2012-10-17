import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;


public class BDDConnexion {

	protected static Connection conn;

	/**
	 * Connexion base de donn�es apr�s lecture des param�tres de connexion fichier MDP_connexionBDD.txt
	 */
	public static void init() {

		try {
			@SuppressWarnings("unused")
			org.postgresql.Driver driver = new org.postgresql.Driver();
			System.out.println("DRIVER OK !");

			InputStream ips = new FileInputStream("MDP_connexionBDD.txt");
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String url = br.readLine();
			String user = br.readLine();
			String passwd = br.readLine();
			br.close();

			conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connection effective !");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cr�e la liste des groupes en fonction de la promotion s�lectionn�e
	 * @param promoChoisie
	 */
	public static ArrayList<String> listeGroupes(String promoChoisie){
		ArrayList<String> listeGroupes = new ArrayList<String>();

		try {
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = ("SELECT DISTINCT cours.cours_groupe "
					+ "FROM cours "
					+ "WHERE cours.cours_promo = '" + promoChoisie + "';");

			ResultSet res = state.executeQuery(query);

			while (res.next()) {
				listeGroupes.add(res.getString("cours_groupe"));
			}

			res.close();
			state.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listeGroupes;
	}

	/**
	 * Cr�e la liste des mati�res en fonction de la promotion s�lectionn�e
	 * @param promoChoisie
	 */
	public static ArrayList<String> listeMatieres(String promoChoisie, String groupeChoisi){
		ArrayList<String> listeMatieres = new ArrayList<String>();

		try {
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = ("SELECT DISTINCT cours.cours_designation "
					+ "FROM cours "
					+ "WHERE cours.cours_promo = '" + promoChoisie + "' AND cours.cours_groupe = '" + groupeChoisi + "';");

			ResultSet res = state.executeQuery(query);

			while (res.next()) {
				listeMatieres.add(res.getString("cours_designation"));
			}

			res.close();
			state.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listeMatieres;
	}

	/**
	 * Renvoie le cours_id connaissant la promo, le groupe et la mati�re
	 * @param promoChoisie
	 * @param groupeChoisie
	 * @param matiereChoisie
	 */
	public static int coursID(String promoChoisie, String groupeChoisi, String matiereChoisie){
		int cours_id = 0;

		try {
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = ("SELECT min(cours.cours_id) "
					+ "FROM cours "
					+ "WHERE cours.cours_promo = '" + promoChoisie 
					+ "' AND cours.cours_groupe = '" + groupeChoisi
					+ "' AND cours.cours_designation = '" + matiereChoisie
					+ "' AND cours.cours_date IS NULL;");

			ResultSet res = state.executeQuery(query);

			while (res.next()) {
				cours_id = res.getInt("min");
			}

			res.close();
			state.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cours_id;
	}

	/**
	 * Cr�e la liste des eleves_id � partir du cours_id
	 * @param cours_id
	 */
	public static ArrayList<Integer> listeElevesID(int cours_id){
		ArrayList<Integer> listeElevesID = new ArrayList<Integer>();

		try {
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = ("SELECT DISTINCT presence.eleves_id "
					+ "FROM presence "
					+ "WHERE presence.cours_id = '" + cours_id + "';");

			ResultSet res = state.executeQuery(query);

			while (res.next()) {
				listeElevesID.add(new Integer(res.getInt("eleves_id")));
			}

			res.close();
			state.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listeElevesID;
	}

	/**
	 * Cr�e la liste des etudiants � partir des eleves_id
	 * @param cours_id
	 */
	public static ArrayList<Etudiant> listeEtudiants(ArrayList<Integer> listeElevesID){
		ArrayList<Etudiant> listeEtudiants = new ArrayList<Etudiant>();

		for(Integer eleve_id : listeElevesID){

			try {
				Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

				String query = ("SELECT eleves.eleves_numeroetudiant, eleves.eleves_mifar, eleves.eleves_nom, eleves.eleves_prenom "
						+ "FROM eleves "
						+ "WHERE eleves.eleves_id = '" + eleve_id + "';");

				ResultSet res = state.executeQuery(query);

				while (res.next()) {
					listeEtudiants.add(new Etudiant(res.getString("eleves_nom"), res.getString("eleves_prenom"), 
							res.getString("eleves_mifar"), res.getString("eleves_numeroetudiant")));
				}

				res.close();
				state.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return listeEtudiants;
	}


	/**
	 * Renvoie eleve_id connaissant numeroEtudiant
	 * @param numeroEtudiant
	 */
	public static int getEleveID(String numeroEtudiant){
		int eleve_id = 0;

		try {
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = ("SELECT eleves.eleves_id "
					+ "FROM eleves "
					+ "WHERE eleves.eleves_numeroetudiant = '" + numeroEtudiant + "';");

			ResultSet res = state.executeQuery(query);

			while (res.next()) {
				eleve_id = res.getInt("eleves_id");
			}

			res.close();
			state.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return eleve_id;
	}


	/**
	 * Envoi les donn�es reuceuillies � la BDD
	 * @param cours_id
	 * @param listeEtudiants
	 */
	public static void envoyerDonnees(int cours_id, ArrayList<Etudiant> listeEtudiants){

		//On ajoute une date au cours correspondant dans la BDD
		try {
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

			String query = ("UPDATE cours "
					+ "SET cours_date = '" + dateFormat.format(date)
					+ "' WHERE cours_id = '" + cours_id +"';");

			ResultSet res = state.executeQuery(query);

			res.close();
			state.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean envoye = false;

		for(Etudiant etu : listeEtudiants){
			try {
				Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

				int eleveStatus = 2;
				if(etu.getExcuse()){eleveStatus = 3;}
				if(etu.getPresent()){eleveStatus = 1;}

				String query = ("INSERT INTO presence (cours_id, eleves_id, presence_status) "
						+ "VALUES (" + cours_id + ", " + getEleveID(etu.getNumeroEtudiant()) + ", " + eleveStatus + ")");

				ResultSet res = state.executeQuery(query);

				res.close();
				state.close();

				envoye = true;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if(envoye){JOptionPane.showConfirmDialog((Component) null,
				"Les donn�es ont �t� envoy�es.",
				"Attention",
				JOptionPane.OK_CANCEL_OPTION);
		}
	}


}