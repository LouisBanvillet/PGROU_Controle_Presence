package Main;

import BDD.BDDConnexion;
import Frame.ControlePresence;
import Frame.FenetreModificationPresence;
import Frame.FenetreRecapitulatif;
import Frame.FenetreSelectionCours;
import Frame.PageAccueil;
import Frame.SelectionCours;


public class Main {
	
	/**
	 * Chaque fenêtre utilisée dans l'application est un attribut,
	 * qui pourra être utilisé par toutes les classes. kevon
	 */
	
	public static PageAccueil fenetreAccueil;
//	public static SelectionCours fenetreSelectionCours;
	public static ControlePresence fenetreControle;
	public static FenetreRecapitulatif fenetreRecapitulatif;
	public static FenetreModificationPresence fenetreModificationPresence;
	public static FenetreSelectionCours fenetreSelectionCours;
	
	/**
	 * Lancement de l'application
	 */
	public static void main(String[] args) {
		BDDConnexion.init();
				
		// On crée une instance de chaque fenêtre
		fenetreAccueil = new PageAccueil();
//		fenetreSelectionCours = new SelectionCours();
		fenetreControle = new ControlePresence();
		fenetreModificationPresence = new FenetreModificationPresence();
		fenetreSelectionCours = new FenetreSelectionCours();
		// On masque toutes les fenêtres sauf la fenêtre d'accueil
		fenetreSelectionCours.setVisible(false);
		fenetreControle.setVisible(false);
		fenetreAccueil.setVisible(true);
		fenetreModificationPresence.setVisible(false);
	}

}
