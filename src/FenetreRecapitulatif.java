import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
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
import javax.swing.table.DefaultTableModel;


public class FenetreRecapitulatif extends JFrame {

	private JPanel contentPane;
	private ArrayList<Etudiant> listeAbsents = new ArrayList<Etudiant>();
	JTable tableAbsents;
	
	
	/**
	 * Création de la liste des absents
	 */
	public void creerListeAbsents() {
		
		// On parcours la liste des étudiants pour ajouter les absents
		for(Etudiant etu : ListeEtudiants.etudiants) {
			if(!etu.getPresent()){
				listeAbsents.add(etu);
			}
		}

		//On remplit le tableau des absents en indiquant si l'élève est excusé ou pas
		if(listeAbsents.size() > 1)
		{
			String[][] data = new String[listeAbsents.size()][2];
			for (int i = 0; i < listeAbsents.size(); i++) {
				data[i][0] = listeAbsents.get(i).getNom();
				if(listeAbsents.get(i).getExcuse()){
					data[i][1] = "oui";
				}else{data[i][1] = "non";}
			}
			String  title[] = {"Liste absents", "Excusé?"};

			tableAbsents = new JTable(new DefaultTableModel(data, title));
			
		}

	}
	
	
	
	/**
	 * Création de la fenêtre
	 * @param liste des étudiants du groupe concerné par le contrôle de présence
	 */
	public FenetreRecapitulatif() {
		tableAbsents = new JTable(new DefaultTableModel());
		creerListeAbsents();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 450, 300);
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
	
	
	
	public void envoyer() {
		JOptionPane jop = new JOptionPane();
		
		jop.showMessageDialog(null,"Non implémenté pour le moment", "Impossible", JOptionPane.ERROR_MESSAGE);
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
