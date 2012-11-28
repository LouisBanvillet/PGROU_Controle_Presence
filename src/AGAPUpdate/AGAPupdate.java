/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AGAPUpdate;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lambda
 */
public class AGAPupdate {
    //connections pour les bases de donnés
    static DbConnection baseLocale;
    static DbConnection baseRemote;
    //informations d'autentification locaux
    private static String hostnameLocal = "127.0.0.1";
    private static String portLocal = "5432";
    private static String dbNameLocal = "AGAP";
    private static String userLocal = "postgres";
    private static String passLocal = "sagara";
    //informations d'autentification remotes
    private static String hostnameRemote = "127.0.0.1";
    private static String portRemote = "5432";
    private static String dbNameRemote = "AGAP2";
    private static String userRemote = "postgres";
    private static String passRemote = "sagara";
    
    //infos sûr quelles tables doivent être mise à jour dans la base locale
    public static String tablesLocal[] = {"actionformation","actionstructure","civilite",
                                          "cursusprepare","cycle","dossierscolaire","eleve",
                                          "elevedansgroupe","elevedansgroupeaction","enseignant",
                                          "enseigneaction","ensembledegroupes","etatinscription",
                                          "formation","groupe","groupeaction","groupestructure",
                                          "horaires","infospersonne","inscription","inscriptionaction",
                                          "inscriptionstructure","listecursus","listetypecursus","matiere",
                                          "personne","progpedagogique","sexe","statutenseignement",
                                          "statutinscription","structures","structuresliens",
                                          "typeactivite","typecursus",
                                          "typelistecursus","typestructure"};
    
    /**
     * @param args the command line arguments
     */
    public static void AGAPupdateInitializer() {
        try {
            AGAPupdate.baseLocale = new DbConnection(AGAPupdate.hostnameLocal,
                                                     AGAPupdate.portLocal, 
                                                     AGAPupdate.dbNameLocal, 
                                                     AGAPupdate.userLocal,
                                                     AGAPupdate.passLocal);
            AGAPupdate.baseRemote = new DbConnection(AGAPupdate.hostnameRemote, 
                                                     AGAPupdate.portRemote, 
                                                     AGAPupdate.dbNameRemote, 
                                                     AGAPupdate.userRemote, 
                                                     AGAPupdate.passRemote);
        } catch (SQLException ex) {
            Logger.getLogger(AGAPupdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
