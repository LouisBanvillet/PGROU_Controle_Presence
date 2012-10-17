

public class Main {
	
	/**
	 * Chaque fen�tre utilis�e dans l'application est un attribut,
	 * qui pourra �tre utilis� par toutes les classes. kevon
	 */
	
	protected static PageAccueil fenetreAccueil;
	protected static SelectionCours fenetreSelectionCours;
	protected static ControlePresence fenetreControle;
	protected static FenetreRecapitulatif fenetreRecapitulatif;
	protected static FenetreModificationPresence fenetreModificationPresence;
	
	
	/**
	 * Lancement de l'application
	 */
	public static void main(String[] args) {
		BDDConnexion.init();
				
		// On cr�� une instance de chaque fen�tre
		fenetreAccueil = new PageAccueil();
		fenetreSelectionCours = new SelectionCours();
		fenetreControle = new ControlePresence();
		fenetreModificationPresence = new FenetreModificationPresence();
		
		// On masque toutes les fen�tre sauf la fen�tre d'accueil
		fenetreSelectionCours.setVisible(false);
		fenetreControle.setVisible(false);
		fenetreAccueil.setVisible(true);
		fenetreModificationPresence.setVisible(false);
	}

}
