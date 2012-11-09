import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;


public class Etudiant {
	
	protected String nom;
	protected String prenom;
	protected String numeroMifare;
	protected String numeroEtudiant;
	protected static String urlPhoto = "http://agap.ec-nantes.fr/AGAP/Photo/";
	protected Boolean present = false;
	protected Boolean presenceMyFare = false;
	protected Boolean excuse = false;


	public Etudiant(String nom, String prenom, String numeroMifare, String numeroEtudiant) {
		this.nom = nom;
		this.prenom = prenom;
		this.numeroMifare = numeroMifare;
		this.numeroEtudiant = numeroEtudiant;
	}
	
	
	public Boolean getPresenceMyFare(){
		return this.presenceMyFare;		
	}
	
	public void setPresenceMyFare(Boolean value){
		this.presenceMyFare = value;		
	}
	
	
	/**
	 * Télécharge la photo d'un étudiant
	 * @return Booléen : true si la photo a été téléchargée ou existe déjà, false sinon
	 */
	public boolean telechargerPhoto() {
		try {
			URL adresse = new URL(Etudiant.urlPhoto + this.numeroEtudiant + ".jpg");
			File photo = new File("img/" + this.numeroEtudiant + ".jpg");
			
			if(!photo.exists()) {
				URLConnection con = adresse.openConnection();
				BufferedInputStream in = new BufferedInputStream(con.getInputStream());
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("img/" + this.numeroEtudiant + ".jpg"));
				int len = 1024;
				byte[] read = new byte[len];

				while ((len = in.read(read)) > 0) {
					out.write(read, 0, len);
				}
				
				out.flush();
				out.close();
				in.close();
			}
			
			return true;
		}
		catch (Exception e) {
			System.out.println("Erreur lors du téléchargement de la photo de " + this.nom + " " + this.prenom);
			return false;
		}
	}
	
	
	
	public String getLienPhotoDisque(){
		return  "./img/" + this.numeroEtudiant + ".jpg";
	}
	
	
	
	/*
	 * Getters et Setters
	 */
	public String getNom(){
		return this.nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNumeroMifare() {
		return numeroMifare;
	}
	public void setNumeroMifare(String numeroMifare) {
		this.numeroMifare = numeroMifare;
	}
	public String getNumeroEtudiant() {
		return numeroEtudiant;
	}
	public void setNumeroEtudiant(String numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Boolean getPresent() {
		return present;
	}
	public void setPresent(Boolean present) {
		this.present = present;
	}
	public Boolean getExcuse() {
		return excuse;
	}
	public void setExcuse(Boolean excuse) {
		this.excuse = excuse;
	}


	
}
