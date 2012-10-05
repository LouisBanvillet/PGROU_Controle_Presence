
public class Main {
	
	/**
	 * Chaque fen�tre utilis�e dans l'application est un attribut,
	 * qui pourra �tre utilis� par toutes les classes. kevon
	 */
	
	protected static PageAccueil fenetreAccueil;
	protected static SelectionCours fenetreSelectionCours;
	protected static ControlePresence fenetreControle;
	protected static FenetreListeAbsents fenetreListeAbsents;
	protected static FenetreRecapitulatif fenetreRecapitulatif;
	protected static FenetreModificationPresence fenetreModificationPresence;
	
	
	/**
	 * Lancement de l'application
	 */
	public static void main(String[] args) {
		
		// On cr�� une instance de chaque fen�tre
		fenetreAccueil = new PageAccueil();
		fenetreSelectionCours = new SelectionCours();
		fenetreControle = new ControlePresence();
		fenetreListeAbsents = new FenetreListeAbsents();
		fenetreModificationPresence = new FenetreModificationPresence();
		
		// On masque toutes les fen�tre sauf la fen�tre d'accueil
		fenetreSelectionCours.setVisible(false);
		fenetreControle.setVisible(false);
		fenetreListeAbsents.setVisible(false);
		fenetreAccueil.setVisible(true);
		fenetreModificationPresence.setVisible(false);
	}

}
