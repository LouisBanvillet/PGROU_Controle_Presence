package Frame;

import BDD.BDDConnexion;
import Main.Main;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class SelectionCours extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<String> listePromo, listeGroupes, listeMatieres;
	private static String promoChoisie = "";
	private static String groupeChoisi = "";
	private static String matiereChoisie = "";
	
	private JComboBox jcPromo, jcGroupe, jcMatiere;
	
	
	/**
	 * Create the frame.
	 */
	public SelectionCours() {
		
		// Liste des promotions
		listePromo = new ArrayList<String>();
		listePromo.add("                ");listePromo.add("Ei1");listePromo.add("Ei2");listePromo.add("Ei3");
		
		// Liste des groupes
		listeGroupes = new ArrayList<String>();
		listeGroupes.add("                ");
		
		// Liste des matières
		listeMatieres = new ArrayList<String>();
		listeMatieres.add("                ");
				
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(349, 254);
        this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		// Titre de la fenêtre
		JPanel haut = new JPanel();
		JLabel Titre = new JLabel("Selection d\'un cours");
		Titre.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 17));
		haut.add(Titre);
		
		// Milieu de la fenêtre
		JPanel milieux = new JPanel();
		milieux.setLayout(new GridLayout(4,0));

		// Liste déroulante des promotions
		JPanel milieux0 = new JPanel();
		milieux0.setLayout(new FlowLayout());
		JLabel jlPromo = new JLabel("Sélectionnez la promotion");
		jcPromo = new JComboBox();
		remplirComboPromo();
		milieux0.add(jlPromo);
		milieux0.add(jcPromo);
		milieux.add(milieux0);
        jcPromo.addActionListener(new PromoAction());
		
		// Liste déroulante des groupes
		JPanel milieux1 = new JPanel();
		milieux1.setLayout(new FlowLayout());
		JLabel jlgroupe = new JLabel("Sélectionnez le groupe");
		jcGroupe = new JComboBox();
		remplirComboGroupe();
		milieux1.add(jlgroupe);
		milieux1.add(jcGroupe);	
		milieux.add(milieux1);
        jcGroupe.addActionListener(new GroupeAction());
		
		// Liste déroulante des matières
		JPanel milieux2 = new JPanel();
		milieux2.setLayout(new FlowLayout());
		JLabel jlMatiere = new JLabel("Sélectionnez la matière");
		jcMatiere = new JComboBox();
		remplirComboMatiere();
		milieux2.add(jlMatiere);
		milieux2.add(jcMatiere);
		milieux.add(milieux2);
		
		// Date
		JPanel milieux3 = new JPanel();
		milieux3.setLayout(new FlowLayout());
		JLabel jlDate1 = new JLabel("Date :");
		Date time = new Date();
        DateFormat dfl = DateFormat.getDateInstance(DateFormat.FULL);
		JLabel jlDate2 = new JLabel(dfl.format(time));
		milieux3.add(jlDate1);
		milieux3.add(jlDate2);
		
		milieux.add(milieux3);
		
		// Bouton valider
		JPanel bas = new JPanel();
		bas.setLayout(new FlowLayout());
		JButton validerChoix = new JButton("Valider");
		validerChoix.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				commencer();
			}
		});
		
		// Bouton retour
		JButton retour = new JButton("Retour");
		retour.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				annuler();
			}
		});
		
		
		bas.add(retour);
		bas.add(validerChoix);

		contentPane.add(haut, BorderLayout.NORTH);
		contentPane.add(milieux, BorderLayout.CENTER);
		contentPane.add(bas,BorderLayout.SOUTH);
	}
	
	/**
     * La liste des groupes est initialisée selon l'item selectionné de la liste déroulante des promotions
     * @param args the command line arguments
     */
    class PromoAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	promoChoisie = (String) jcPromo.getSelectedItem();
        	listeGroupes = BDDConnexion.listeGroupes(promoChoisie);
        	remplirComboGroupe();
        }
    }
    
    /**
     * La liste des matières est initialisée selon les items selectionnés dans la liste déroulante des promotions et celle des groupes
     * @param args the command line arguments
     */
    class GroupeAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	promoChoisie = (String) jcPromo.getSelectedItem();
        	groupeChoisi = (String) jcGroupe.getSelectedItem();
        	listeMatieres = BDDConnexion.listeMatieres(promoChoisie, groupeChoisi);
        	remplirComboMatiere();
        }
    }
    
	public void remplirComboPromo() {
		for(String s : listePromo) {
			jcPromo.addItem(s);
		}
	}
	
	public void remplirComboMatiere() {
		for(String s : listeMatieres) {
			jcMatiere.addItem(s);
		}
	}
	
	public void remplirComboGroupe() {
		for(String s : listeGroupes) {
			jcGroupe.addItem(s);
		}
	}
	
	
	
	/**
	 * Retourner à la fenêtre d'accueil
	 */
	public void annuler() {
		// On masque la fenêtre de séléction du cours, on affiche la fenêtre d'accueil
		Main.fenetreSelectionCours.setVisible(false);
		Main.fenetreAccueil.setVisible(true);
	}
	
	
	
	/**
	 * Commencer le contrôle de présence (passage à la fenêtre de contrôle)
	 */
	public void commencer() {
		// On récupère les valeurs des listes déroulantes
		promoChoisie = (String) jcPromo.getSelectedItem();
		groupeChoisi = (String) jcGroupe.getSelectedItem();
		matiereChoisie = (String) jcMatiere.getSelectedItem();
		
		int cours_id = BDDConnexion.coursID(promoChoisie, groupeChoisi, matiereChoisie);
		Main.fenetreControle.setCours_id(cours_id);
		
		// On affiche la fenêtre de contrôle, on masque la fenêtre de séléction du cours
		Main.fenetreSelectionCours.setVisible(false);
		Main.fenetreControle.majFenetre();
		Main.fenetreControle.setVisible(true);
		Main.fenetreControle.textFocus();
	}
	
	
	public static String getMatiereChoisie() {
		return matiereChoisie;
	}

	public static String getGroupeChoisi() {
		return groupeChoisi;
	}
}
