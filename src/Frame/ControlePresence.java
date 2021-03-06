package Frame;

import BDD.BDDConnexion;
import BDD.Etudiant;
import Main.Main;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

public class ControlePresence extends JFrame {

	private static final long serialVersionUID = 1L;

	private String cheminAnonyme = "./img/anonyme.jpg";
	private Etudiant dernierEtudiant;

	private JPanel contentPane, panelDroite, panelGauche;
	private JTextField textFieldInput;
	private JSplitPane splitPane;
	private JLabel labelphotoEleve ;
	private JList JListeEtudiants;

	protected int cours_id;
	protected ArrayList<Etudiant> listeEtudiants = new ArrayList<Etudiant>();

	public ControlePresence() {
		this.dernierEtudiant = null;
		majFenetre();
	}	

	/**
	 * Création de la fenêtre de contrôle de présence
	 */
	public void majFenetre() {
		remplirListeEtudiants();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550, 300);
        this.setLocationRelativeTo(null);
		setResizable(true);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);


		// Affichage du titre de la fenêtre
		String labelTitre = "Contrôle de présence - " +
				Main.fenetreSelectionCours.getMatiereChoisie() + " - " +
				Main.fenetreSelectionCours.getGroupeChoisi();
		JLabel titreFenetre = new JLabel(labelTitre);
		titreFenetre.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 17));


		// Gestion de la partie droite (image + champ de texte)
		panelDroite = new JPanel();
		panelDroite.setLayout(new BorderLayout());

		Box boiteVerticale = Box.createVerticalBox();
		boiteVerticale.add(Box.createGlue());
		Box boiteHorizontale1= Box.createHorizontalBox();
		boiteHorizontale1.add(Box.createGlue());

		String cheminImage = "";

		// Affichage de la photo
		if(this.dernierEtudiant == null) {
			cheminImage = this.cheminAnonyme;
		}
		else {
			cheminImage = this.dernierEtudiant.getLienPhotoDisque();
		}

		ImageIcon icone = createImageIcon(cheminImage);


		labelphotoEleve = new JLabel(icone, JLabel.CENTER);
		labelphotoEleve.setPreferredSize(new Dimension(100, 100));
		boiteHorizontale1.add(labelphotoEleve);
		boiteHorizontale1.add(Box.createGlue());

		boiteVerticale.add(boiteHorizontale1);

		Box boiteHorizontale2= Box.createHorizontalBox();
		boiteHorizontale2.add(Box.createGlue());
		textFieldInput = new JTextField();
		textFieldInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rechercheEtUpdate();
			}
		});

		textFieldInput.setPreferredSize(new Dimension(100, 20));
		textFieldInput.setMaximumSize(new Dimension(100, 20));
		boiteHorizontale2.add(textFieldInput);
		boiteHorizontale2.add(Box.createGlue());

		boiteVerticale.add(boiteHorizontale2);


		// Bouton de fin du contrôle de présence
		Box boiteHorizontale3= Box.createHorizontalBox();
		boiteHorizontale3.add(Box.createGlue());
		JButton boutonFinControle = new JButton("Fin du contrôle");
		boutonFinControle.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				terminerControle();
			}
		});

		boiteHorizontale3.add(boutonFinControle);
		boiteHorizontale3.add(Box.createGlue());
		boiteVerticale.add(boiteHorizontale3);

		boiteVerticale.add(Box.createGlue());
		panelDroite.add(boiteVerticale,BorderLayout.CENTER);


		// Bouton de retour
		Box boiteHorizontale4= Box.createHorizontalBox();
		boiteHorizontale4.add(Box.createGlue());
		JButton boutonRetour = new JButton("Retour");
		boutonRetour.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				retourSelectionCours();
			}
		});

		boiteHorizontale4.add(boutonRetour);
		boiteHorizontale4.add(Box.createGlue());
		boiteVerticale.add(boiteHorizontale4);

		boiteVerticale.add(Box.createGlue());
		panelDroite.add(boiteVerticale,BorderLayout.CENTER);


		// Gestion de la partie gauche (liste des étudiants)
		panelGauche = new JPanel();
		panelGauche.setLayout(new BorderLayout());


		JListeEtudiants = majListeEtudiants();
		JListeEtudiants.setCellRenderer(new MaListeEleves());

		panelGauche.add(JListeEtudiants, BorderLayout.CENTER);

		splitPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelGauche, panelDroite);
		splitPane.setDividerLocation(300);

		contentPane.add(splitPane, BorderLayout.CENTER);
		contentPane.add(titreFenetre, BorderLayout.NORTH);
	}



	public void remplirListeEtudiants(){
		ArrayList<Integer> listeEtudiantsID = new ArrayList<Integer>();
		listeEtudiantsID = BDDConnexion.listeElevesID(cours_id);
		listeEtudiants = BDDConnexion.listeEtudiants(listeEtudiantsID);
	}


	public JList majListeEtudiants() {
		int nombreEtudiants = listeEtudiants.size();
		int position = 0;
		Etudiant[] vecteurEtudiants = new Etudiant[nombreEtudiants];

		for (Etudiant etu : listeEtudiants) {
			vecteurEtudiants[position] = etu;
			position ++;
		}

		JList liste = new JList(vecteurEtudiants);
		return liste;
	}


	public void majPhoto() {
		String cheminImage = "";

		if(this.dernierEtudiant == null) {
			cheminImage = this.cheminAnonyme;
		}
		else {
			cheminImage = this.dernierEtudiant.getLienPhotoDisque();
		}

		ImageIcon icone = createImageIcon(cheminImage);

		labelphotoEleve.setIcon(icone);
	}
	

	public void rechercheEtUpdate() {
		String myfareTrouve = convert(textFieldInput.getText());

		for (Etudiant etu : listeEtudiants) {
			if (etu.getNumeroMifare().equals(myfareTrouve)) {
				etu.setPresent(true);
				etu.setPresenceMyFare(true);
				dernierEtudiant = etu;
			}
		}
		textFieldInput.setText("");
		majListeEtudiants();
		this.majPhoto();

		this.repaint();
	}

	public String convert(String num) {
		String ret = "";
		char[] c = num.toLowerCase().toCharArray();

		for(int i = 0 ; i < num.length() ; i++) {
			if(c[i] == '&' || (c[i] == '1')) {
				ret += "1";
			} else if((c[i] == 'é') || (c[i] == '2')) {
				ret += "2";
			} else if((c[i] == '"') || (c[i] == '3')) {
				ret += "3";
			} else if((c[i] == '\'') || (c[i] == '4')) {
				ret += "4";
			} else if((c[i] == '(') || (c[i] == '5')) {
				ret += "5";
			} else if((c[i] == '-') || (c[i] == '6')) {
				ret += "6";
			} else if((c[i] == 'è') || (c[i] == '7')) {
				ret += "7";
			} else if((c[i] == '_') || (c[i] == '8')) {
				ret += "8";
			} else if((c[i] == 'ç') || (c[i] == '9')) {
				ret += "9";
			} else if((c[i] == 'à') || (c[i] == '0')) {
				ret += "0";
			}
		}

		return ret;
	}



	public static ImageIcon createImageIcon(String path) {
		ImageIcon retour = new ImageIcon() ;


		try {
			Image img = ImageIO.read(new File(path));
			Image mieux = img.getScaledInstance(80, 100, 1);
			retour.setImage(mieux);

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return retour;
	}



	public void setDernierEtudiant(Etudiant etu){
		this.dernierEtudiant = etu;
	}



	/**
	 * Terminer le contrôle de présence
	 */
	public void terminerControle() {
		// On désactive la touche VERR MAJ
		//Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, false);

		// On masque la fenêtre de contrôle et on affiche la fenêtre de vérification
		Main.fenetreControle.setVisible(false);
		Main.fenetreModificationPresence.setVisible(true);
	}



	/**
	 * Retourner à la fenêtre de séléction du cours
	 */
	public void retourSelectionCours() {
		// On affiche un message d'information
		int choix = JOptionPane.showConfirmDialog((Component) null,
				"Toutes les données seront perdues ! Vous devrez scanner à nouveau tous les étudiants.",
				"Attention",
				JOptionPane.OK_CANCEL_OPTION);

		if(choix == 0) {
			// On désactive la touche VERR MAJ
			//Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, false);

			// On masque la fenêtre de contrôle de présence, on affiche la fenêtre de séléction du cours
			Main.fenetreControle.setVisible(false);
			Main.fenetreSelectionCours.setVisible(true);
			this.dernierEtudiant = null;
		}
	}



	public void textFocus(){
		textFieldInput.requestFocus();
	}

	//getters et setters
	public int getCours_id() {
		return cours_id;
	}

	public void setCours_id(int cours_id) {
		this.cours_id = cours_id;
	}

	public ArrayList<Etudiant> getListeEtudiants() {
		return listeEtudiants;
	}

	public void setListeEtudiants(ArrayList<Etudiant> listeEtudiants) {
		this.listeEtudiants = listeEtudiants;
	}

}