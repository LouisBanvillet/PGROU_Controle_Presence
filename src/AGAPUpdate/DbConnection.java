/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AGAPUpdate;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lambda
 */
class DbConnection{
    
    private Connection connection = null;
    
    public DbConnection(String hostname, 
                        String port,
                        String dbName,
                        String username,
                        String password) throws SQLException{    
        
        connection = DriverManager.getConnection("jdbc:postgresql://"+hostname+":"+port+"/"+dbName,username, password);
        connection.close();
        connection.setAutoCommit(true);
    }
    
    public void setAutoCommit(boolean v){
        try {
            connection.setAutoCommit(v);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     
    
    public void Commit() throws SQLException{
        connection.commit();
    }
    
    public void executeUpdate(String query){
        Statement st;
        try{
            st = connection.createStatement();
            st.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    
    public PreparedStatement getPreparedStatement(String sql){
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void execute(String query){
        Statement st;
        try{
            st = connection.createStatement();
            st.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    
    public ResultSet executeQuery(String query){
        Statement st;
        ResultSet rs = null;
        try{
            st = connection.createStatement();
            st.execute(query);
        }catch(Exception e){
            e.printStackTrace();
        }        
        
        return rs;
    }
    
    protected void finalize() throws Throwable{
        super.finalize();
        connection.close();
    }
}
