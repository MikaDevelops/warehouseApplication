package kendokoodi.warehouseapplication.dbOperations;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Handles connection to MariaDB database.
 * @author mika
 */
class ConnectionHandler {
    
    private String dataBaseAddress;
    private String dataBaseName;
    private String dataBasePort;
    private String dataBaseUser;
    private String dataBaseType;
    
    /**
     * Class constructor. Reads database settings from settings file.
     * 
     * @throws IOException
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws ParseException 
     */
    protected ConnectionHandler() throws IOException, SQLException, FileNotFoundException, ParseException{
        
            ArrayList<String> settings = getSettings();
            this.dataBaseAddress = settings.get(0);
            this.dataBaseName = settings.get(1);
            this.dataBasePort = settings.get(2);
            this.dataBaseUser = settings.get(3);
            this.dataBaseType = settings.get(4);
            
    }
    
    /**
     * Loads settings from dataBaseUser.json -file.
     * 
     * @return ArrayList of database settings
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException 
     */
    private ArrayList<String>  getSettings() throws SQLException, FileNotFoundException, IOException, ParseException {
        ArrayList<String> settings = new ArrayList<>();
        Object object = new JSONParser().parse(new FileReader("dataBaseUser.json"));
        JSONObject jsonObject = (JSONObject) object;
        settings.add( (String) jsonObject.get("dataBaseAddress") );
        settings.add( (String) jsonObject.get("databaseName") );
        settings.add( (String) jsonObject.get("dataBasePort") );
        settings.add( (String) jsonObject.get("user") );
        settings.add( (String) jsonObject.get("databaseType") );
        return settings;
    }
    
    /**
     * Makes a connection to database.
     * @return connection object to be used with queries and DML
     * @throws SQLException 
     */
    protected Connection getConnection() throws SQLException{
        
        String connectionString = null;
        
        switch(this.dataBaseType){
            case "mariadb":
                connectionString = "jdbc:mariadb://"+ this.dataBaseAddress+":"+this.dataBasePort+"?user="+this.dataBaseUser;
                break;
            case "sqlite":
                connectionString = "jdbc:sqlite:"+ this.dataBaseName + ".db";
                break;
            default:
                System.out.println("database type not expected");
        }
        
        Connection connection = DriverManager.getConnection( connectionString );
        return connection;

    }
    
    /**
     * Closes database connection.
     * @param c Connection to be closed
     * @throws SQLException 
     */
    protected void closeConnection( Connection c ) throws SQLException {
        
        if ( c != null ) { 
            c.close(); 
        }
    }
}
