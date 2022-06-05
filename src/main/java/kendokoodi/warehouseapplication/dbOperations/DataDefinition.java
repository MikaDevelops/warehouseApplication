/*
 *
 */
package kendokoodi.warehouseapplication.dbOperations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

/**
 * Data Definition Language methods for creating and deleting WarehouseApplication
 * database
 * @author mika
 */
public class DataDefinition {
    
    // instance should have valid admin and password
    private String admin;
    private String password;
    private String user;
    
    // constructor for class instance.
    public DataDefinition ( String admin, String password ){
        this.admin      = admin;
        this.password   = password;
    }
    
    // Method to create a string from sql-file.
    private String[] getCreateSQLstring(){
        
        String sqlString = "";
        try{
            
            File sqlStringFile = new File ( "createDatabaseSQL.sql" );
            Scanner reader = new Scanner ( sqlStringFile );
            while (reader.hasNextLine()){
                sqlString = sqlString + " " + reader.nextLine();
            }
            reader.close();
            String[] sqlArray = sqlString.split(";");
            return sqlArray;
            
        }catch(FileNotFoundException fnfe){fnfe.printStackTrace();
        String[] empty = {"0"};
        return empty;}
        
    }
    
    private Connection openConnection() throws SQLException{
        String connectionString =
                    "jdbc:mariadb://localhost:3306?user="+admin+"&password="+password;
        Connection c = DriverManager.getConnection( connectionString );
        return c;
    }
    
    private void closeConnection(Connection c) throws SQLException {
        if (c != null){
            c.close();
        }
    }
    
    private Statement createStatement( Connection c ) throws SQLException {
        
        Statement stmt = c.createStatement();
        stmt.execute("SET autocommit=1");
        return stmt;
        
    }
    
    private void closeStatement( Statement stmt ) throws SQLException {
        if (stmt != null){
            stmt.close();
        }
    }
    
    public void createDatabase(){
        
        // read sql-script from file
        String[] sqlString = getCreateSQLstring();
        if (!sqlString[0].equals("0")){
            

            
            // create database using script
            try{
                Connection c = openConnection();
                Statement stmt = createStatement( c );
                for ( String i : sqlString ){
                    stmt.addBatch(i);
                }
                stmt.executeBatch();
                stmt.clearBatch();
                closeStatement ( stmt );
                closeConnection ( c );
               

            }
            catch (SQLException e)
            {
                MariaDB.sqlExceptionHandler(e, "createDB DataDefinition Open connection");
            }
            
            // get database user name from file
        try{
            
            Object object = new JSONParser().parse(new FileReader("dataBaseUser.json"));
            JSONObject jsonObject = (JSONObject) object;
            this.user = (String) jsonObject.get("user");
            
            }catch(IOException | ParseException ioe){
                ioe.printStackTrace();
            }
        
        // create user
        try{
            
            Connection c = openConnection( );
            Statement stmt = createStatement( c );
            stmt.execute( "CREATE OR REPLACE USER '" + this.user + "'@'localhost'");
            
        // grant sufficient privileges to database
            stmt.execute( "GRANT SELECT,INSERT,UPDATE,DELETE ON warehouseApplicationDB.* "
            + "TO '" + this.user +"'@'localhost'");
            closeStatement( stmt );
            closeConnection ( c );
            
            }catch( SQLException e ){
                e.printStackTrace();
            }
            
        }else {
            System.out.println("no sql string returned by createDatabase method");
        }
    }
}
