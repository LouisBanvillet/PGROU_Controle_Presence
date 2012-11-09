public class Main {
	
	/**
	 * Chaque fenêtre utilisée dans l'application est un attribut,
	 * qui pourra être utilisé par toutes les classes. kevon
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
				
		// On crée une instance de chaque fenêtre
		fenetreAccueil = new PageAccueil();
		fenetreSelectionCours = new SelectionCours();
		fenetreControle = new ControlePresence();
		fenetreModificationPresence = new FenetreModificationPresence();
		
		// On masque toutes les fenêtres sauf la fenêtre d'accueil
		fenetreSelectionCours.setVisible(false);
		fenetreControle.setVisible(false);
		fenetreAccueil.setVisible(true);
		fenetreModificationPresence.setVisible(false);
	}

}
