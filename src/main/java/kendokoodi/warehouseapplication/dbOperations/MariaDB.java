package kendokoodi.warehouseapplication.dbOperations;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import kendokoodi.warehouseapplication.App;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * kendokoodi.warehouseapplication.dbOperations.MariaDB is a class
 * that contains database operation methods for CRUD operations on
 * warehouse application MariaDB database.
 * @author mika
 * @author Mika.1.virtala@edu.karelia.fi
 */
public class MariaDB {
    
    //private static final String connectionString = 
    //        "jdbc:mariadb://localhost:3306?user=kayttaja&password=pjger903lk43";
    
    private String dataBaseAddress;
    private String dataBasePort;
    private String dataBaseUser;
    
    /**
     * Opens connection to database and returns Connection
     * @return  Returns Connection
     * @throws SQLException 
     */
    public static Connection openConnection() throws SQLException, FileNotFoundException, IOException, ParseException {
        Object object = new JSONParser().parse(new FileReader("dataBaseUser.json"));
        JSONObject jsonObject = (JSONObject) object;
        String user = (String) jsonObject.get("user");
        String dbAddr = (String) jsonObject.get("dataBaseAddress");
        String port = (String) jsonObject.get("dataBasePort");
        jsonObject = null;
        Connection connection = DriverManager.getConnection ( 
        "jdbc:mariadb://" + dbAddr + ":" + port + "?user=" + user );
        return connection;
    }
    
    /**
     * Closes Connection that is passed as parameter
     * @param c Connection
     * @throws SQLException 
     */
    public static void closeConnection( Connection c ) throws SQLException {
        if ( c != null ) { c.close(); } }
    
    
    /**
     * Adds a serialized product to database.
     * @param serProd object containing attributes of a serialized product.
     * @throws SQLException 
     */
    public static void addSerializedProduct( SerProdInfo serProd ) throws SQLException, IOException, FileNotFoundException, ParseException {
        
        Connection c = openConnection();
        Statement stmt = c.createStatement();
        stmt.execute("SET autocommit=1");
        stmt.execute("USE DemoWarehouseApplicationDB");
        
        PreparedStatement pstmt = c.prepareStatement(
        "INSERT INTO SerializedProduct (productNo, serialNo, manufacturer, name, warranty, "
        + "isOwned, isInProduction, leaseID, roomID, positionID) VALUES "
        + "(?,?,?,?,?,?,?,?,?,?)"
        );
        
        pstmt.setString(1, serProd.productNo);
        pstmt.setString(2, serProd.serialNo);
        pstmt.setString(3, serProd.manufacturer);
        pstmt.setString(4, serProd.name);
        pstmt.setInt(5, serProd.warranty);
        pstmt.setInt(6, serProd.isOwned);
        pstmt.setInt(7, serProd.isInProduction);
        if ( serProd.leaseID == 0 ) { pstmt.setString(8,null); }else{
            pstmt.setInt(8, serProd.leaseID); }
        pstmt.setString(9, serProd.roomID);
        pstmt.setString(10, serProd.positionID);
        
        pstmt.execute();
        stmt.close();
        pstmt.close();
        closeConnection ( c );
    }
    
    /**
     * deleteDB() deletes demonstrational warehouse database.
     * @throws SQLException 
     */
    public static void deleteDB() throws SQLException, IOException, FileNotFoundException, ParseException {
        Connection c = openConnection();
        Statement stmt = c.createStatement();
        stmt.execute("SET autocommit=1");
        stmt.executeQuery("DROP DATABASE IF EXISTS testDB");
        stmt.close();
        closeConnection(c);
    }
    
    /**
     * Deletes a record in database.
     * @param id product id (record key) to be deleted.
     * @throws SQLException 
     */
    public static void deleteRecord(int id) throws SQLException, IOException, FileNotFoundException, ParseException{
        Connection c = openConnection();
        Statement stmt = c.createStatement();
        stmt.execute("SET autocommit=1");
        stmt.execute("USE DemoWarehouseApplicationDB");
        stmt.execute("DELETE FROM SerializedProduct WHERE productID=" + String.valueOf(id));
        //stmt.execute("COMMIT");
        stmt.close();
        closeConnection( c );
    }

    /**
     * Updates a record into database.
     * @param serializedProduct data to be updated to database.
     * @throws SQLException 
     */
    public static void updateSerializedProduct( SerProdInfo serializedProduct ) throws SQLException, IOException, FileNotFoundException, ParseException {
        Connection c = openConnection();
        Statement stmt = c.createStatement();
        stmt.execute("SET autocommit=1");
        stmt.execute("USE DemoWarehouseApplicationDB");

        PreparedStatement pstmt = c.prepareStatement("UPDATE SerializedProduct "
                + "SET productNo=?, serialNo=?, manufacturer=?, "
                + "name=?, warranty=?, isOwned=?, isInProduction=?, "
                + "leaseID=?, roomID=?, positionID=? WHERE productID=?"
        );
        
        pstmt.setString(1, serializedProduct.productNo);
        pstmt.setString(2, serializedProduct.serialNo);
        pstmt.setString(3, serializedProduct.manufacturer);
        pstmt.setString(4, serializedProduct.name);
        pstmt.setInt(5, serializedProduct.warranty);
        pstmt.setInt(6, serializedProduct.isOwned);
        pstmt.setInt(7, serializedProduct.isInProduction);
        if ( serializedProduct.leaseID == 0 ) { pstmt.setString(8,null); }else{
            pstmt.setInt(8, serializedProduct.leaseID); }
        if ( serializedProduct.roomID == "0" ){ pstmt.setString(9, null); }else{
            pstmt.setString(9, serializedProduct.roomID); }
        if ( serializedProduct.positionID == "0" ){pstmt.setString(10, null); }else{
            pstmt.setString(10, serializedProduct.positionID); }
        pstmt.setInt(11, serializedProduct.productID);
        pstmt.execute();
        //stmt.execute("COMMIT");
        stmt.close();
        closeConnection( c );
    }
    
    /**
     * Loads serialized product information from database.
     * @param prodID product id.
     * @return product information from database
     * @throws SQLException 
     */
    public static SerProdInfo getSerializedProductData(int prodID) throws SQLException, IOException, FileNotFoundException, ParseException{
        Connection c = openConnection();
        Statement stmt = c.createStatement();
        stmt.execute("SET autocommit=1");
        stmt.execute("USE DemoWarehouseApplicationDB");
        
        ResultSet rs = stmt.executeQuery("SELECT * FROM SerializedProduct "
        + "WHERE productID=" + prodID
        );
        //stmt.execute("COMMIT");
        
        closeConnection ( c );
        
        SerProdInfo result = new SerProdInfo();
        
        // move cursor to first row
        rs.first();
        
        result.productID = rs.getInt("productID");
        result.productNo = rs.getString("productNo");
        result.serialNo = rs.getString("serialNo");
        result.manufacturer = rs.getString("manufacturer");
        result.name =rs.getString("name");
        result.warranty = rs.getInt("warranty");
        result.isOwned = rs.getInt("isOwned");
        result.isInProduction = rs.getInt("isInProduction");
        result.leaseID = rs.getInt("leaseID");
        result.roomID = rs.getString("roomID");
        result.positionID = rs.getString("positionID");
        
        stmt.close();
        return result;
        
    }
    
    /**
     * Method gets lease IDs from database.
     * @return ArrayList of lease IDs
     * @throws SQLException 
     */
    public static ArrayList<String> getLeaseIDlist() throws SQLException, IOException, FileNotFoundException, ParseException{
        
        ArrayList<String> leaseIdList = new ArrayList<>();
        
        Connection c = openConnection();
        
        Statement stmt = c.createStatement();
        stmt.execute("SET autocommit=1");
        stmt.executeQuery("USE DemoWarehouseApplicationDB");
        ResultSet rs = stmt.executeQuery("SELECT leaseID FROM Leasing");
        //stmt.execute("COMMIT");
        stmt.close();
        closeConnection ( c );
        
        while (rs.next()){
        
            leaseIdList.add(String.valueOf(rs.getInt("leaseID")));
        
        }
        
        return leaseIdList;
    }
    
    /**
     * Methods gets room IDs from database.
     * @return ArrayList of room ID.
     * @throws SQLException 
     */
    public static ArrayList<String> getRoomIDlist() throws SQLException, IOException, FileNotFoundException, ParseException{
        
        ArrayList<String> roomIdList = new ArrayList<>();
        
        Connection c = openConnection();
        
        Statement stmt = c.createStatement();
        stmt.execute("SET autocommit=1");
        stmt.executeQuery("USE DemoWarehouseApplicationDB");
        ResultSet rs = stmt.executeQuery("SELECT roomID FROM Room");
        //stmt.execute("COMMIT");
        stmt.close();
        closeConnection ( c );
        
        while (rs.next()){
        
            roomIdList.add(rs.getString("roomID"));
        
        }
        
        return roomIdList;
    
    }
    
    /**
     * Method gets and returns storage ID list from database.
     * @return ArrayList of storage positions.
     * @throws SQLException 
     */
    public static ArrayList<String> getStorageIDlist() throws SQLException, IOException, FileNotFoundException, ParseException {
        
        ArrayList<String> storageIdList = new ArrayList<>();
        
        Connection c = openConnection();
        
        Statement stmt = c.createStatement();
        stmt.execute("SET autocommit=1");
        stmt.executeQuery("USE DemoWarehouseApplicationDB");
        ResultSet rs = stmt.executeQuery("SELECT positionID FROM StoragePosition");
        //stmt.execute("COMMIT");
        stmt.close();
        closeConnection ( c );
        
        while (rs.next()){
        
            storageIdList.add(rs.getString("positionID"));
        
        }
        
        return storageIdList;
    }

    /**
     * Lists all serialized products in SerializedProduct table.
     * @return ArrayList of SerProd object containing all products from database.
     * @throws SQLException 
     */
    public static ArrayList<SerProdInfo> listAllSerialized() throws SQLException, IOException, FileNotFoundException, ParseException{
        ArrayList<SerProdInfo> serProdInfo = new ArrayList<>();
        
        Connection c = openConnection();
        
        Statement stmt = c.createStatement();
        stmt.execute("SET autocommit=1");
        stmt.execute("USE DemoWarehouseApplicationDB");
        ResultSet rs = stmt.executeQuery("SELECT * FROM SerializedProduct"
                + " ORDER BY productID");
        //stmt.execute("COMMIT");
        stmt.close();
        closeConnection ( c );
        
        // Handle result set
        while (rs.next()){
            
            SerProdInfo serialized = new SerProdInfo();
            serialized.productID = rs.getInt("productID");
            serialized.productNo = rs.getString("productNo");
            serialized.serialNo = rs.getString("serialNo");
            serialized.manufacturer = rs.getString("manufacturer");
            serialized.name =rs.getString("name");
            serialized.warranty = rs.getInt("warranty");
            serialized.isOwned = rs.getInt("isOwned");
            serialized.leaseID = rs.getInt("leaseID");
            serialized.roomID = rs.getString("roomID");
            serialized.positionID = rs.getString("positionID");
            
            // add to array list
            serProdInfo.add(serialized);
        }
        
        return serProdInfo;
    }

    /**
     * Search database on product name or serial number.
     * @param searchString String is used for searching the database.
     * @param searchBase int 0 or 1. 0 uses searchString for product names 1
     * uses searchString for product serial numbers.
     * @return ArrayList of SerProdInfo objects containing search results.
     * @throws SQLException
     */
    public static ArrayList<SerProdInfo> searchSerialized( String searchString, int searchBase ) throws SQLException, IOException, FileNotFoundException, ParseException {
        
        ArrayList<SerProdInfo> serProdInfo = new ArrayList<>();
        String base;
        
        switch (searchBase){
            case 0:
                base = "name LIKE ";
            break;
            
            case 1:
                base = "serialNo=";
            break;
            
            default:
                base = "notDefined"; // will cause SQL exception
        }
        
        Connection c = openConnection();
        
        Statement stmt = c.createStatement();
        stmt.execute("SET autocommit=1");
        
        // select demo database
        stmt.executeQuery( "USE DemoWarehouseApplicationDB" );
        
        PreparedStatement pstmt = c.prepareStatement(
                "SELECT * FROM SerializedProduct WHERE "+ base + "?"
        );
        
        pstmt.setString(1, searchString);
        ResultSet rs = pstmt.executeQuery();
        
        stmt.close();
        pstmt.close();
        closeConnection( c );
        
        // Handle result set
        while (rs.next()){
            
            SerProdInfo serialized = new SerProdInfo();
            serialized.productID = rs.getInt("productID");
            serialized.productNo = rs.getString("productNo");
            serialized.serialNo = rs.getString("serialNo");
            serialized.manufacturer = rs.getString("manufacturer");
            serialized.name =rs.getString("name");
            serialized.warranty = rs.getInt("warranty");
            serialized.isOwned = rs.getInt("isOwned");
            serialized.leaseID = rs.getInt("leaseID");
            serialized.roomID = rs.getString("roomID");
            serialized.positionID = rs.getString("positionID");
            
            // add to array list
            serProdInfo.add(serialized);
        }
        
        // return array list of products.
        return serProdInfo;
    
    }
    
    /**
     * Writes SQLException information to log file.
     * @param e SQLException
     * @param sourceOfError String that describes from where error came.
     */
    public static void sqlExceptionHandler( SQLException e, String sourceOfError ){
        
        for (Throwable ex : e) {
        if (ex instanceof SQLException){  
        String causeString = "Cause: ";
            Throwable th = ex.getCause();
            while( th != null ){
                causeString =causeString + th + ";\n";
                System.out.println("Cause: " + th);
                th = th.getCause();
            }
            
        
            String exceptionString = "source: " + sourceOfError +";\n"
                    + LocalDateTime.now().toString() + ";\n"
                    + "State: " + ((SQLException) ex).getSQLState() + ";\n"
                    + "Error code: " + ((SQLException) ex).getErrorCode() + ";\n"
                    + "Message: " + ex.getMessage() +";\n"
                    + causeString + ";\n\n\n";
            
            App.writeErrorToFile( exceptionString );
            
        }
        }
    }
    
    // TODO: get accessory information
    // TODO: edit an accessory
    // TODO: delete a store room from database
    // TODO: delete a storage position
    // TODO: delete a room where products were used
    // TODO: get store room information
    // TODO: get storage position information
    // TODO: get room information
    // TODO: delete an accessory
    // TODO: edit store room
    // TODO: edit storage postition
    // TODO: edit room
    // TODO: add an accessory, add storage position to accessory
    // TODO: add a store room to database
    // TODO: add a storage position
    // TODO: add a room where products are used 
}
