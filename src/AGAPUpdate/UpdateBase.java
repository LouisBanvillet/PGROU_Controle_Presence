/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AGAPUpdate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UpdateBase {
    
    private DbConnection dbTo;
    private DbConnection dbFrom;    
    private String tables[];
    public UpdateBase(DbConnection dbCopyTo, DbConnection dbCopyFrom, String tables[]){
        dbTo = dbCopyTo;
        dbFrom = dbCopyFrom;        
        this.tables = tables;
    }
    
    /**
     * Efface tous les registres de les tables locales
     */
    private void effacerLocal(){
        String query = "TRUNCATE TABLE ";
        for(int i = 0; i < AGAPupdate.tablesLocal.length; i++)
        {
            if(i != AGAPupdate.tablesLocal.length - 1){
                query += AGAPupdate.tablesLocal[i] + ", ";
            }else{
                query += AGAPupdate.tablesLocal[i] + " CASCADE;";
            }
        }
        System.out.println(query);
        dbTo.executeQuery(query);
    }
    
    /**
     * desactive tous les FK's de la base locale
     */
    private void removeFKCheck(){
        for(String table:tables){
            String query = "ALTER TABLE " + table + " DISABLE TRIGGER ALL;";
            this.dbLocal.executeQuery(query);
        }
    }
    /**
     * Active tous les FK's de la base locale
     */
    private void addFKCheck(){
        for(String table:tables){
            String query = "ALTER TABLE " + table + " ENABLE TRIGGER ALL;";
            this.dbLocal.executeQuery(query);
        }
    }
    
    /**
     * Mèthode pour faire le update de tous les données locales
     */
    public void update(){
        effacerLocal();
        removeFKCheck();
        for(String tablename : AGAPupdate.tablesLocal)
        {
            copierPourBaseLocale(tablename);
        }
        addFKCheck();
        //commit
        try {
            dbTo.Commit();
        } catch (SQLException ex) {
            Logger.getLogger(UpdateBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Copie tous les données d'une table remote pour la base locale
     * @param tablename 
     */
    private void copierPourBaseLocale(String tablename) {
        String query = "SELECT * FROM " + tablename + ";";
        ResultSet rs = dbRemote.executeQuery(query);
        ArrayList<String> columnNames = new ArrayList<String>();
        ArrayList<String> columnTypes = new ArrayList<String>();
        //on prend le nom de les columnes et les types
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            for(int i = 0; i < rsmd.getColumnCount(); i++){
                columnNames.add(rsmd.getColumnName(i));
                columnTypes.add(rsmd.getColumnTypeName(i));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        String insertQueryBase = "INSERT INTO " + tablename + " VALUES (";
        for(int i = 0; i < columnNames.size();i++)
            insertQueryBase +="?,";
        insertQueryBase = insertQueryBase.substring(0, insertQueryBase.length()-1);
        insertQueryBase+=");";
        //et on copie les données!
        try {    
            while(rs.next()){
                PreparedStatement pStatementet = dbTo.getPreparedStatement(insertQueryBase);
                
                int cnt = 0;
                for(String colname : columnNames){
                    //
                    if(columnTypes.get(cnt) == "integer"){
                    
                    }else if(columnTypes.get(cnt) == "numeric"){
                    
                    }else if(columnTypes.get(cnt) == "date"){
                    
                    }else if(columnTypes.get(cnt).contains("character")){
                    
                    }else{
                        try {
                            throw new Exception("Not implemented type " + columnTypes.get(cnt));
                        } catch (Exception ex) {
                            Logger.getLogger(UpdateBase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    rs.getObject(colname);
                    cnt++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
