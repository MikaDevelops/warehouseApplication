package kendokoodi.warehouseapplication.dbOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import kendokoodi.warehouseapplication.App;

/**
 * kendokoodi.warehouseapplication.dbOperations.MariaDB is a class
 * that contains database operation methods for CRUD operations on
 * warehouse application MariaDB database.
 * @author mika
 * @author Mika.1.virtala@edu.karelia.fi
 */
public class MariaDB {
    
    private static final String connectionString = 
            "jdbc:mariadb://localhost:3306?user=kayttaja&password=pjger903lk43";
    
    /**
     * Opens connection to database and returns Connection
     * @return  Returns Connection
     * @throws SQLException 
     */
    public static Connection openConnection ( ) throws SQLException {
        Connection connection = DriverManager.getConnection ( connectionString );
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
     * createDemoDB creates a demo warehouse database.
     * <p>
     * Executes a drop database query, which will delete DemoWarehouseApplicationDB
     * named database if exists. Creates a new DemoWarehouseApplicationDB named database
     * and adds some rows to it.
     * @throws SQLException 
     */
    public static void createDemoDB () throws SQLException {
        Connection c = openConnection();
        Statement stmt = c.createStatement();
        
        stmt.execute("SET autocommit=1");
        
      //  stmt.execute( "DROP DATABASE IF EXISTS DemoWarehouseApplicationDB" );
        deleteDemoDB();
      
        stmt.execute( "CREATE DATABASE IF NOT EXISTS DemoWarehouseApplicationDB" );
        
        stmt.execute( "USE DemoWarehouseApplicationDB" );
        
        
        stmt.execute( "CREATE TABLE Room("
//          +"building VARCHAR(50) NOT NULL,"
          +"roomID VARCHAR(50) NOT NULL,"
//          +"campus VARCHAR(50) NOT NULL,"
          +"PRIMARY KEY (roomID) ) " );

//        stmt.executeQuery( "CREATE TABLE StoreRoom("
//          +"storeRoomID VARCHAR(50) NOT NULL,"
//          +"grossFloorArea FLOAT NOT NULL,"
//          +"roomHeight FLOAT NOT NULL,"
//          +"PRIMARY KEY (storeRoomID) ) " );
        
       
        stmt.execute("CREATE TABLE StoragePosition("
          +"positionID VARCHAR(50) NOT NULL,"
//          +"storeRoomID VARCHAR(50) NOT NULL,"
          +"PRIMARY KEY (positionID))"
               
//          +"FOREIGN KEY (storeRoomID) REFERENCES StoreRoom(storeRoomID)"
//          +"ON DELETE CASCADE) " 
        );

//        stmt.executeQuery("CREATE TABLE Accessory("
//          +"name VARCHAR(80) NOT NULL,"
//          +"accessoryID INT NOT NULL AUTO_INCREMENT,"
//          +"quantity INT NOT NULL,"
//          +"productDesc VARCHAR(80),"
//          +"manufacturer VARCHAR(50),"
//          +"productNo VARCHAR(80),"
//          +"PRIMARY KEY (accessoryID) ) " );
        
       
        stmt.execute("CREATE TABLE Leasing("
          +"leaseTime INT NOT NULL,"
          +"leasingCompany VARCHAR(80) NOT NULL,"
          +"leaseID INT NOT NULL AUTO_INCREMENT,"
          +"contractNo VARCHAR(50) NOT NULL,"
          +"leaseStartDate DATE NOT NULL,"
          +"PRIMARY KEY (leaseID) ) " );

//        stmt.executeQuery("CREATE TABLE AccessoryPos("
//          +"accessoryID INT NOT NULL,"
//          +"positionID VARCHAR(50) NOT NULL,"
//          +"FOREIGN KEY (accessoryID) REFERENCES Accessory(accessoryID),"
//          +"FOREIGN KEY (positionID) REFERENCES StoragePosition(positionID) ) " );
        
        
        stmt.execute("CREATE TABLE SerializedProduct("
          +"productNo VARCHAR(80),"
          +"serialNo VARCHAR(80) NOT NULL,"
          +"manufacturer VARCHAR(80) NOT NULL,"
          +"name VARCHAR(80) NOT NULL,"
          +"warranty INT NOT NULL,"
          +"productID INT NOT NULL AUTO_INCREMENT,"
          +"isOwned INT NOT NULL,"
          +"isInProduction INT NOT NULL,"
          +"leaseID INT,"
          +"roomID VARCHAR(50),"
          +"positionID VARCHAR(50),"
          +"PRIMARY KEY (productID),"
          +"FOREIGN KEY (leaseID) REFERENCES Leasing(leaseID),"
          +"FOREIGN KEY (roomID) REFERENCES Room(roomID),"
          +"FOREIGN KEY (positionID) REFERENCES StoragePosition(positionID) ) " );

//        stmt.executeQuery("CREATE TABLE OwnedDevice("
//          +"deviceID INT NOT NULL AUTO_INCREMENT,"
//          +"purchaseDate DATE NOT NULL,"
//          +"orderNo VARCHAR(50) NOT NULL,"
//          +"productID INT NOT NULL,"
//          +"PRIMARY KEY (deviceID),"
//          +"FOREIGN KEY (productID) REFERENCES SerializedProduct(productID) ) " );
       
        // Inserts some data into tables
        
        stmt.execute("INSERT INTO Leasing (LeaseTime, leasingCompany, contractNo, leaseStartDate)"
        +"VALUES(60,'3StepIT', 'L200220145','2022-02-21'),(36,'IT-solutions','ew38-22','2018-09-01')" );
        //stmt.execute("COMMIT");
        
        
        stmt.executeQuery("INSERT INTO Room (roomID)"
        +"VALUES"
        +"('M100'),"
        +"('M101'),"
        +"('M102'),"
        +"('M103'),"
        +"('M104'),"
        +"('M105'),"
        +"('M106'),"
        +"('M107')"
        );
        //stmt.execute("COMMIT");
        
        
        stmt.execute("INSERT INTO SerializedProduct (productNo, serialNo, manufacturer, name, warranty, isOwned, isInProduction)"
        +"VALUES" 
        +"('60-1382-01','ASDFGHJKL1','Extron','DTP CrossPoint 86 4K',24,0,0),"
        +"('60-1625-01','QWERTY6','Extron','DTP T SW4 HD 4K',12,0,1),"
        +"('60-1594-01','POIUYTT357','Extron','SMP 111',24,0,1),"
        +"('6511006','tyrieruw844','Crestron','DM-NVX-360',12,1,1),"
        +"(NULL,'wowowow2','Shure','SM58 Microphone',12,1,1)"
        );
        //stmt.execute("COMMIT");

        //stmt.executeQuery("INSERT INTO StoreRoom (storeRoomID, grossFloorArea, roomHeight)"
        //+"VALUES('128',9.0,2.5) " );
        
       
        stmt.execute("INSERT INTO StoragePosition (positionID) "
        +"VALUES"
        +"('M1-A1'),('M1-A2'),('M1-A3')" );
        //stmt.execute("COMMIT");

//        stmt.executeQuery("INSERT INTO Accessory (name,quantity) "
//        +"VALUES('Yleisruuvi PROF uppokanta Zn T30 6x160mm',300),"
//        +"('Switchcraft Open 1/4” Stereo Jack',12),('Kramer HDMI-kaapeli 3m',5)" );

//        stmt.executeQuery("INSERT INTO AccessoryPos (accessoryID, positionID) "
//        +"VALUES(1,'M1-A1'),(2,'M1-A1'),(3,'M1-A1')" );

        stmt.close();
        closeConnection( c );
    }
    
    /**
     * Adds a serialized product to database.
     * @param serProd object containing attributes of a serialized product.
     * @throws SQLException 
     */
    public static void addSerializedProduct( SerProdInfo serProd ) throws SQLException {
        
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
        //stmt.execute("COMMIT");
        stmt.close();
        closeConnection ( c );
    }
    
    /**
     * deleteDemoDB() deletes demonstrational warehouse database.
     * @throws SQLException 
     */
    public static void deleteDemoDB() throws SQLException {
        Connection c = openConnection();
        Statement stmt = c.createStatement();
        stmt.execute("SET autocommit=1");
        stmt.executeQuery("DROP DATABASE IF EXISTS DemoWarehouseApplicationDB");
        stmt.close();
        closeConnection(c);
    }
    
    /**
     * Deletes a record in database.
     * @param id product id (record key) to be deleted.
     * @throws SQLException 
     */
    public static void deleteRecord(int id) throws SQLException{
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
    public static void updateSerializedProduct( SerProdInfo serializedProduct ) throws SQLException {
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
    public static SerProdInfo getSerializedProductData(int prodID) throws SQLException{
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
    public static ArrayList<String> getLeaseIDlist() throws SQLException{
        
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
    public static ArrayList<String> getRoomIDlist() throws SQLException{
        
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
    public static ArrayList<String> getStorageIDlist() throws SQLException {
        
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
    public static ArrayList<SerProdInfo> listAllSerialized() throws SQLException{
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
    public static ArrayList<SerProdInfo> searchSerialized( String searchString, int searchBase ) throws SQLException {
        
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
