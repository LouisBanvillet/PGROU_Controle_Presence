package BDD;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class BDDConnexion {

	protected static Connection conn;

	/**
	 * Connexion base de données après lecture des paramètres de connexion fichier MDP_connexionBDD.txt
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
         * Retourne les cours données pour un enseignant
         * @param myFare String avec le numéro myFare du enseignant
         */
        public static ArrayList<String> listeCoursEnseignant(String myFare){
            ArrayList<String> listeCours = new ArrayList<String>();
            try{
                Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                String query = "SELECT c.cours_designation " +
                               "  FROM cours c, " +
                               "       enseignants e," +
                               "       cours_enseignant ce " +
                               " WHERE e.enseignant_miFar = '" + myFare + "' " +
                               "   AND e.enseignant_id = ce.enseignant_id " +
                               "   AND c.cours_id = ce.cours_id;";
                ResultSet res = state.executeQuery(query);
                while (res.next()) {
                    listeCours.add(res.getString("cours_designation"));
		}
		res.close();
		state.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            return listeCours;        
        }

	/**
	 * Crée la liste des groupes en fonction de la promotion sélectionnée
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
	 * Crée la liste des matières en fonction de la promotion sélectionnée
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
	 * Renvoie le cours_id connaissant la promo, le groupe et la matière
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
	 * Crée la liste des eleves_id à partir du cours_id
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
	 * Crée la liste des etudiants à partir des eleves_id
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
	 * Envoi les données reuceuillies à la BDD
	 * @param cours_id
	 * @param listeEtudiants
	 */
	public static void envoyerDonnees(int cours_id, ArrayList<Etudiant> listeEtudiants){

		//On ajoute une date au cours correspondant dans la BDD
		try {
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = ("UPDATE cours "
					+ "SET cours_date = NOW() WHERE cours_id = '" + cours_id +"';");

			state.executeUpdate(query);

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

				String query = ("UPDATE presence "
						+ "SET presence_status = " + eleveStatus
						+ " WHERE presence.cours_id = '" + cours_id 
						+ "' AND presence.eleves_id = '" + getEleveID(etu.getNumeroEtudiant()) 
						+ "';");
				
				state.executeUpdate(query);

				state.close();

				envoye = true;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if(envoye){JOptionPane.showMessageDialog((Component) null,
				"Les données ont été envoyées.",
				"Attention",
				JOptionPane.OK_CANCEL_OPTION);
		}
	}


}
