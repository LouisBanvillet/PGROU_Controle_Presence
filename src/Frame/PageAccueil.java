package Frame;

import Main.Main;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class PageAccueil extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton majEtudiant;
	private String boutonTelechargement;



	/**
	 * Création de la fenêtre
	 */
	public PageAccueil() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 200);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));


		// Affichage du titre
		JPanel haut = new JPanel();
		JLabel Titre = new JLabel("Contrôle de présence");
		Titre.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 17));
		haut.add(Titre);


		// Gestion du bouton de lancement du contrôle de présence
		JButton controler = new JButton("Commencer le contrôle de présence");
		controler.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				commencerControle();
			}
		});  


		//Gestion du bouton de mise a jour du fichier xml
		boutonTelechargement = "Mis à jour de les données";
//		File fichierXml = new File("etudiants.xml");
//
//		if(fichierXml.exists()) {
//			boutonTelechargement = "Mettre à jour la liste des étudiants";
//		}

		majEtudiant = new JButton(boutonTelechargement);
		majEtudiant.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				montrerFenetreAGAP();
			}
		});


		//Gestion du bouton pour quitter
		JButton quitter = new JButton("Quitter");
		quitter.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				quitter();
			}
		});


		Box boiteVerticale = Box.createVerticalBox();
		boiteVerticale.add(Box.createGlue());
		Box boiteHorizontale1= Box.createHorizontalBox();
		boiteHorizontale1.add(Box.createGlue());
		boiteHorizontale1.add(controler);
		boiteHorizontale1.add(Box.createGlue());
		boiteVerticale.add(boiteHorizontale1);
		Box boiteHorizontale2= Box.createHorizontalBox();
		boiteHorizontale2.add(Box.createGlue());
		boiteHorizontale2.add(majEtudiant);
		boiteHorizontale2.add(Box.createGlue());
		boiteVerticale.add(boiteHorizontale2);
		Box boiteHorizontale3= Box.createHorizontalBox();
		boiteHorizontale3.add(Box.createGlue());
		boiteHorizontale3.add(quitter);
		boiteHorizontale3.add(Box.createGlue());
		boiteVerticale.add(boiteHorizontale3);
		boiteVerticale.add(Box.createGlue());


		contentPane.add(haut, BorderLayout.NORTH);
		contentPane.add(boiteVerticale, BorderLayout.CENTER);
	}


	/**
	 * Lancement du contrôle de présence
	 */
	public void commencerControle() {
		Main.fenetreAccueil.setVisible(false);
		Main.fenetreSelectionCours.setVisible(true);
	}

	/**
	 * Télécharge la liste des étudiants depuis la base de données AGAP
	 */
	public void montrerFenetreAGAP() {
            Main.fenetreMisAJour.setVisible(true);
	}

	
	public void quitter() {
		System.exit(0);
	}
}
