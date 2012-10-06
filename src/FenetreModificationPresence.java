import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


public class FenetreModificationPresence extends JFrame {

	private JPanel contentPane;
	private ArrayList<Etudiant> listeAbsents;
	private JTable tableEtudiants;
	private TableModificationPresence mTablePresence;
	
	/**
	 * Cr�ation de la liste des absents
	 */
	public void creerListeAbsents() {

		listeAbsents = new ArrayList<Etudiant>();
		//textAreaEtudiantsAbsents.setText("");
		// On parcours la liste des �tudiants pour ajouter les absents
		for(Etudiant etu : ListeEtudiants.etudiants) {
			if(!etu.getPresent()){
				listeAbsents.add(etu);
				//textAreaEtudiantsAbsents.setText(textAreaEtudiantsAbsents.getText()+etu.getPrenom() + " " + etu.getNom()+ "\n");
			}
		}
	}
	
	
	
	/**
	 * Cr�ation de la fen�tre
	 * @param liste des �tudiants du groupe concern� par le contr�le de pr�sence
	 */
	public FenetreModificationPresence() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mTablePresence = new TableModificationPresence();
		tableEtudiants = new JTable(new TableModificationPresence());
		
		
		JScrollPane scrollPane = new JScrollPane(tableEtudiants);
		
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		creerListeAbsents();
		
		Box boiteVerticale = Box.createVerticalBox();
		boiteVerticale.add(Box.createGlue());
		
		Box boiteHorizontale1= Box.createHorizontalBox();
			boiteHorizontale1.add(Box.createGlue());
			JLabel labelDescription = new JLabel("Liste des �tudiants dans ce cours : ");
			boiteHorizontale1.add(labelDescription);
			boiteHorizontale1.add(Box.createGlue());
		boiteVerticale.add(boiteHorizontale1);
		
		Box boiteHorizontale2= Box.createHorizontalBox();
			boiteHorizontale2.add(Box.createGlue());
			boiteHorizontale2.add(scrollPane);
			boiteHorizontale2.add(Box.createGlue());
		boiteVerticale.add(boiteHorizontale2);
		
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
		
		// Bouton valider
		JButton boutonEnvoyer = new JButton("Valider");
		boutonEnvoyer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				valider();
			}
		});
		
		panel_boutons.add(boutonAnnuler);
		panel_boutons.add(boutonEnvoyer);
		contentPane.add(boiteVerticale,BorderLayout.CENTER);
		contentPane.add(panel_boutons,BorderLayout.SOUTH);
	}
	
	
	
	public void valider() {
		Main.fenetreModificationPresence.setVisible(false);
		Main.fenetreRecapitulatif = new FenetreRecapitulatif();
		Main.fenetreRecapitulatif.setVisible(true);
	}
	
	
	
	/**
	 * Retour � la fen�tre de contr�le des pr�sences
	 */
	public void retourControle() {		
		// On masque la fen�tre avec lal iste des absents, on affiche la fen�tre de contr�le de pr�sence
		Main.fenetreModificationPresence.setVisible(false);
		Main.fenetreControle.majFenetre();
		Main.fenetreControle.setVisible(true);
		Main.fenetreControle.textFocus();
	}
}
