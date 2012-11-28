/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AGAPUpdate;

import java.awt.Component;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.DefaultListModel; 

public class UpdateBase {
    
    private DbConnection dbTo;
    private DbConnection dbFrom;    
    private String tables[];
    private JList jList;
    
    public UpdateBase(DbConnection dbCopyTo, DbConnection dbCopyFrom, String tables[], JList listMessages){
        dbTo = dbCopyTo;
        dbFrom = dbCopyFrom;        
        this.tables = tables;
        jList = listMessages;
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
        dbTo.execute(query);
    }
    
    /**
     * desactive tous les FK's de la base locale
     */
    private void removeFKCheck(){
        for(String table:tables){
            String query = "ALTER TABLE " + table + " DISABLE TRIGGER ALL;";
            this.dbTo.execute(query);
        }
    }
    /**
     * Active tous les FK's de la base locale
     */
    private void addFKCheck(){
        for(String table:tables){
            String query = "ALTER TABLE " + table + " ENABLE TRIGGER ALL;";
            this.dbTo.execute(query);
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
    }

    public void setList(JList list){
        jList = list;
    }
    
    /**
     * Copie tous les données d'une table remote pour la base locale
     * @param tablename 
     */
    private void copierPourBaseLocale(String tablename) {
        System.out.println("En train de copier "+tablename);
        if(jList!=null){
//            DefaultListModel<String> model = jList.getModel();  
//            model.addElement("En train de copier "+tablename);  
        }
        String query = "SELECT * FROM " + tablename + ";";
        ResultSet rs = dbFrom.executeQuery(query);
        ArrayList<String> columnNames = new ArrayList<String>();
        ArrayList<String> columnTypes = new ArrayList<String>();
        //on prend le nom de les columnes et les types
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            for(int i = 1; i <= rsmd.getColumnCount(); i++){
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
                    if(columnTypes.get(cnt).equals("integer") ||
                       columnTypes.get(cnt).equals("int4") ||
                       columnTypes.get(cnt).equals("int2") ||
                       columnTypes.get(cnt).equals("int8")){
                        pStatementet.setInt(cnt + 1, rs.getInt(cnt+1));
                    }else if(columnTypes.get(cnt) == "numeric"){
                        pStatementet.setDouble(cnt + 1, rs.getDouble(cnt+1));
                    }else if(columnTypes.get(cnt) == "date"){
                        pStatementet.setDate(cnt + 1, rs.getDate(cnt + 1));
                    }else if(columnTypes.get(cnt).contains("character") ||
                             columnTypes.get(cnt).equals("varchar") ||
                             columnTypes.get(cnt).equals("bpchar") ){
                        pStatementet.setString(cnt + 1, rs.getString(cnt + 1));
                    }else{
                        try {
                            throw new Exception("Not implemented type " + columnTypes.get(cnt));
                        } catch (Exception ex) {
                            Logger.getLogger(UpdateBase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    cnt++;
                }
                pStatementet.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UpdateBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
