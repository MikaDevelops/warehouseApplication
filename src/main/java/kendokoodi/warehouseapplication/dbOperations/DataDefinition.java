/*
 *
 */
package kendokoodi.warehouseapplication.dbOperations;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Data Definition Language methods for creating and deleting WarehouseApplication
 * database
 * @author mika
 */
public class DataDefinition {
    
    // instance should have valid admin and password
    private String admin;
    private String password;
    
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
    
    public void createDatabase(){
        
        String[] sqlString = getCreateSQLstring();
        if (!sqlString[0].equals("0")){
            
            String connectionString =
                    "jdbc:mariadb://localhost:3306?user="+admin+"&password="+password;
            
            try{
                
                Connection c = MariaDB.openConnection( connectionString );
                Statement stmt = c.createStatement();
                for ( String i : sqlString ){
                    stmt.addBatch(i);
                }
                stmt.executeBatch();
                stmt.clearBatch();
                stmt.close();
                c.close();
            }
            catch (SQLException e)
            {
                MariaDB.sqlExceptionHandler(e, "createDB DataDefinition Open connection");
            }
            
        }else {
            System.out.println("no sql string returned by createDatabase");
        }
        
        
        
    }
}
