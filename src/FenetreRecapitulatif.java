import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;


public class FenetreRecapitulatif extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<Etudiant> listeAbsents = new ArrayList<Etudiant>();
	JTable tableAbsents;
	private TableRecapitulatif tableModelRecapitulatif;
	
	
	/**
	 * Création de la fenêtre
	 * @param liste des étudiants du groupe concerné par le contrôle de présence
	 */
	public FenetreRecapitulatif() {
		tableModelRecapitulatif = new TableRecapitulatif();
		tableAbsents = new JTable(tableModelRecapitulatif);
		creerListeAbsents();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550, 400);
        this.setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Box boiteVerticale = Box.createVerticalBox();
		boiteVerticale.add(Box.createGlue());
		
		Box boiteHorizontale1= Box.createHorizontalBox();
			boiteHorizontale1.add(Box.createGlue());
			JLabel labelDescription = new JLabel("Liste des absents à ce cours : ");
			boiteHorizontale1.add(labelDescription);
			boiteHorizontale1.add(Box.createGlue());
		boiteVerticale.add(boiteHorizontale1);
		

		Box boiteHorizontale2= Box.createHorizontalBox();
			boiteHorizontale2.add(Box.createGlue());
			boiteHorizontale2.add(tableAbsents.getTableHeader());
			boiteHorizontale2.add(Box.createGlue());
		boiteVerticale.add(boiteHorizontale2);
		
		Box boiteHorizontale3= Box.createHorizontalBox();
			boiteHorizontale3.add(Box.createGlue());
			boiteHorizontale3.add(tableAbsents);
			boiteHorizontale3.add(Box.createGlue());
		boiteVerticale.add(boiteHorizontale3);
		
		boiteVerticale.add(Box.createGlue());
		
		JPanel panel_boutons = new JPanel();
		panel_boutons.setLayout(new FlowLayout());
		
		// Bouton annuler
		JButton boutonAnnuler = new JButton("Annuler");
		boutonAnnuler.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				retourControle();
			}
		});
		
		// Bouton envoyer
		JButton boutonEnvoyer = new JButton("Envoyer");
		boutonEnvoyer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				envoyer();
			}
		});
		
		panel_boutons.add(boutonAnnuler);
		panel_boutons.add(boutonEnvoyer);
		contentPane.add(boiteVerticale,BorderLayout.CENTER);
		contentPane.add(panel_boutons,BorderLayout.SOUTH);
	}

	
	/**
	 * Création de la liste des absents
	 */
	public void creerListeAbsents() {
		
		// On parcours la liste des étudiants pour ajouter les absents
		for(Etudiant etu : Main.fenetreControle.getListeEtudiants()) {
			if(!etu.getPresent()){
				listeAbsents.add(etu);
			}
		}
		
	}
	
	public void envoyer() {
		BDDConnexion.envoyerDonnees(Main.fenetreControle.getCours_id(), Main.fenetreControle.getListeEtudiants());
		System.exit(0);
	}
	
	
	
	/**
	 * Retour à la fenêtre de contrôle des présences
	 */
	public void retourControle() {		
		// On masque la fenêtre avec lal iste des absents, on affiche la fenêtre de contrôle de présence
		Main.fenetreRecapitulatif.setVisible(false);
		Main.fenetreModificationPresence.setVisible(true);
	}
}
