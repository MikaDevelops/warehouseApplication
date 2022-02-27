package kendokoodi.warehouseapplication.dbOperations;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * kendokoodi.warehouseapplication.dbOperations.MariaDB is a class
 * that contains database operation methods for CRUD operations on
 * warehouse application MariaDB database.
 * @author mika
 * @author Mika.1.virtala@edu.karelia.fi
 */
public class MariaDB {
    
    private static final String connectionString = "jdbc:mariadb://localhost:"
            +"3306?user=kayttaja&password=pjger903lk43";
    
    // create connection
    /**
     * Opens connection to database and returns Connection
     * @return  Returns Connection
     * @throws SQLException 
     */
    public static Connection openConnection ( ) throws SQLException {
        Connection connection = DriverManager.getConnection ( connectionString );
        return connection;
    }
    
    // close connection
    /**
     * Closes Connection that is passed as parameter
     * @param c Connection
     * @throws SQLException 
     */
    public static void closeConnection( Connection c ) throws SQLException {
        if ( c != null ) { c.close(); } }
    
    
    // create demo database
    /**
     * createDemoDB creates a demonstrational warehouse database.
     * @throws SQLException 
     */
    public static void createDemoDB () throws SQLException {
        Connection c = openConnection();
        Statement stmt = c.createStatement();
        
        stmt.executeQuery( "DROP DATABASE IF EXISTS DemoWarehouseApplicationDB" );
        
        stmt.executeQuery( "CREATE DATABASE IF NOT EXISTS DemoWarehouseApplicationDB" );

        stmt.executeQuery( "USE DemoWarehouseApplicationDB" );

        stmt.executeQuery( "CREATE TABLE Room("
          +"building VARCHAR(50) NOT NULL,"
          +"roomID VARCHAR(50) NOT NULL,"
          +"campus VARCHAR(50) NOT NULL,"
          +"PRIMARY KEY (roomID) ) " );

        stmt.executeQuery( "CREATE TABLE StoreRoom("
          +"storeRoomID VARCHAR(50) NOT NULL,"
          +"grossFloorArea FLOAT NOT NULL,"
          +"roomHeight FLOAT NOT NULL,"
          +"PRIMARY KEY (storeRoomID) ) " );

        stmt.executeQuery("CREATE TABLE StoragePosition("
          +"positionID VARCHAR(50) NOT NULL,"
          +"storeRoomID VARCHAR(50) NOT NULL,"
          +"PRIMARY KEY (positionID),"
          +"FOREIGN KEY (storeRoomID) REFERENCES StoreRoom(storeRoomID)"
          +"ON DELETE CASCADE) " );

        stmt.executeQuery("CREATE TABLE Accessory("
          +"name VARCHAR(80) NOT NULL,"
          +"accessoryID INT NOT NULL AUTO_INCREMENT,"
          +"quantity INT NOT NULL,"
          +"productDesc VARCHAR(80),"
          +"manufacturer VARCHAR(50),"
          +"productNo VARCHAR(80),"
          +"PRIMARY KEY (accessoryID) ) " );

        stmt.executeQuery("CREATE TABLE Leasing("
          +"leaseTime INT NOT NULL,"
          +"leasingCompany VARCHAR(80) NOT NULL,"
          +"leaseID INT NOT NULL AUTO_INCREMENT,"
          +"contractNo VARCHAR(50) NOT NULL,"
          +"leaseStartDate DATE NOT NULL,"
          +"PRIMARY KEY (leaseID) ) " );

        stmt.executeQuery("CREATE TABLE AccessoryPos("
          +"accessoryID INT NOT NULL,"
          +"positionID VARCHAR(50) NOT NULL,"
          +"FOREIGN KEY (accessoryID) REFERENCES Accessory(accessoryID),"
          +"FOREIGN KEY (positionID) REFERENCES StoragePosition(positionID) ) " );

        stmt.executeQuery("CREATE TABLE SerializedProduct("
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

        stmt.executeQuery("CREATE TABLE OwnedDevice("
          +"deviceID INT NOT NULL AUTO_INCREMENT,"
          +"purchaseDate DATE NOT NULL,"
          +"orderNo VARCHAR(50) NOT NULL,"
          +"productID INT NOT NULL,"
          +"PRIMARY KEY (deviceID),"
          +"FOREIGN KEY (productID) REFERENCES SerializedProduct(productID) ) " );
        
        // Inserts some data into tables
        stmt.executeQuery("INSERT INTO Leasing (LeaseTime, leasingCompany, contractNo, leaseStartDate)"
        +"VALUES(60,'3StepIT', 'L200220145','2022-02-21')" );

        stmt.executeQuery("INSERT INTO Room (roomID, building, campus)"
        +"VALUES"
        +"('M100','Metria','Joensuu'),"
        +"('M101','Metria','Joensuu'),"
        +"('M102','Metria','Joensuu'),"
        +"('M103','Metria','Joensuu'),"
        +"('M104','Metria','Joensuu'),"
        +"('M105','Metria','Joensuu'),"
        +"('M106','Metria','Joensuu'),"
        +"('M107','Metria','Joensuu'),"
        +"('M108','Metria','Joensuu'),"
        +"('M109','Metria','Joensuu'),"
        +"('M110','Metria','Joensuu'),"
        +"('M201','Metria','Joensuu'),"
        +"('M301','Metria','Joensuu'),"
        +"('M302','Metria','Joensuu'),"
        +"('M303','Metria','Joensuu'),"
        +"('M304','Metria','Joensuu'),"
        +"('M305','Metria','Joensuu'),"
        +"('M306','Metria','Joensuu'),"
        +"('F100','Futura','Joensuu'),"
        +"('F101','Futura','Joensuu'),"
        +"('F102','Futura','Joensuu'),"
        +"('F103','Futura','Joensuu'),"
        +"('F104','Futura','Joensuu'),"
        +"('F105','Futura','Joensuu'),"
        +"('F109','Futura','Joensuu'),"
        +"('F110','Futura','Joensuu'),"
        +"('F111','Futura','Joensuu'),"
        +"('F112','Futura','Joensuu'),"
        +"('F113','Futura','Joensuu'),"
        +"('F200','Futura','Joensuu'),"
        +"('F201','Futura','Joensuu'),"
        +"('F202','Futura','Joensuu'),"
        +"('F210','Futura','Joensuu'),"
        +"('F211','Futura','Joensuu'),"
        +"('F226c','Futura','Joensuu'),"
        +"('F310','Futura','Joensuu'),"
        +"('BOR100','Borealis','Joensuu')," 
        +"('BOR101','Borealis','Joensuu'),"
        +"('BOR102','Borealis','Joensuu'),"   
        +"('BOR114','Borealis','Joensuu'),"  
        +"('BOR201','Borealis','Joensuu'),"   
        +"('BOR301','Borealis','Joensuu') " );

        stmt.executeQuery("INSERT INTO SerializedProduct (productNo, serialNo, manufacturer, name, warranty, isOwned, isInProduction)"
        +"VALUES" 
        +"('60-1382-01','ASDFGHJKL1','Extron','DTP CrossPoint 86 4K',24,0,0),"
        +"('60-1382-01','ASDFGHJKL2','Extron','DTP CrossPoint 86 4K',24,0,1),"
        +"('60-1382-01','ASDFGHJKL3','Extron','DTP CrossPoint 86 4K',24,1,0),"
        +"('60-1382-01','ASDFGHJKL4','Extron','DTP CrossPoint 86 4K',12,0,1),"
        +"('60-1382-01','ASDFGHJKL5','Extron','DTP CrossPoint 86 4K',36,1,1),"
        +"('60-1625-01','QWERTY6','Extron','DTP T SW4 HD 4K',12,0,1),"
        +"('60-1625-01','QWERTY5','Extron','DTP T SW4 HD 4K',12,0,1),"
        +"('60-1625-01','QWERTY1','Extron','DTP T SW4 HD 4K',12,0,1),"
        +"('60-1625-01','QWERTY2','Extron','DTP T SW4 HD 4K',12,0,1),"
        +"('60-1625-01','QWERTY3','Extron','DTP T SW4 HD 4K',12,0,1),"
        +"('60-1594-01','POIUYTT357','Extron','SMP 111',24,0,1),"
        +"('60-1594-01','POIUYTT233','Extron','SMP 111',24,0,1),"
        +"('60-1594-01','POIUYTT313','Extron','SMP 111',24,0,1),"
        +"('60-1594-01','POIUYTT332','Extron','SMP 111',24,0,1),"
        +"('60-1594-01','POIUYTT334','Extron','SMP 111',24,1,0),"
        +"('6511006','tyrieruw844','Crestron','DM-NVX-360',12,1,1),"
        +"(NULL,'wowowow2','Shure','SM58 Microphone',12,1,1),"
        +"(NULL,'wowowow4','Shure','SM58 Microphone',12,1,1),"
        +"(NULL,'wowowow7','Shure','SM58 Microphone',12,1,1),"
        +"(NULL,'wowowow8','Shure','SM58 Microphone',12,1,1),"
        +"(NULL,'wowowow9','Shure','SM58 Microphone',12,1,1),"
        +"(NULL,'wowowo7w0','Shure','SM58 Microphone',12,1,1),"
        +"(NULL,'wowow5ow2','Shure','SM58 Microphone',12,1,1),"
        +"(NULL,'w22ow1w4','Shure','SM58 Microphone',12,1,1) " );

        stmt.executeQuery("INSERT INTO StoreRoom (storeRoomID, grossFloorArea, roomHeight)"
        +"VALUES('128',9.0,2.5) " );

        stmt.executeQuery("INSERT INTO StoragePosition (positionID, storeRoomID) "
        +"VALUES"
        +"('M1-A1','128'),('M1-A2','128'),('M1-A3','128'),('M1-A4','128'),('M1-A5','128'),"
        +"('M1-A6','128'),('M1-B1','128'),('M1-B2','128'),('M1-B3','128'),('M1-B4','128'),"
        +"('M1-B5','128'),('M1-B6','128'),('M1-C1','128'),('M1-C2','128'),('M1-C3','128'),"
        +"('M1-C4','128'),('M1-C5','128'),('M1-C6','128') " );

        stmt.executeQuery("INSERT INTO Accessory (name,quantity) "
        +"VALUES('Yleisruuvi PROF uppokanta Zn T30 6x160mm',300),"
        +"('Switchcraft Open 1/4” Stereo Jack',12),('Kramer HDMI-kaapeli 3m',5)" );

        stmt.executeQuery("INSERT INTO AccessoryPos (accessoryID, positionID) "
        +"VALUES(1,'M1-A1'),(2,'M1-A1'),(3,'M1-A1')" );

        stmt.executeQuery("UPDATE SerializedProduct SET LeaseID=1 WHERE isOwned=0" );

        stmt.executeQuery("UPDATE SerializedProduct SET roomID='M100' "
        +"WHERE isInProduction=1 AND productID IN (2,6,11,17)" );

        stmt.executeQuery("UPDATE SerializedProduct SET roomID='M101' "
        +"WHERE isInProduction=1 AND productID IN (16,18)" );

        stmt.executeQuery("UPDATE SerializedProduct SET positionID='M1-C1' "
        +"WHERE isInProduction=0 AND productID IN (1,3,15)" );
        
        closeConnection(c);
    }
    
    // add a store room to database
    
    // add a storage position
    
    // add a room where products are used 
    
    // add a serialized product, add to owned / leased,
    // add to room / storage postition
    
    // add an accessory, add storage position to accessory
    
    // delete demo database
    /**
     * deleteDemoDB() deletes demonstrational warehouse database.
     * @throws SQLException 
     */
    public static void deleteDemoDB() throws SQLException {
        Connection c = openConnection();
        Statement stmt = c.createStatement();
        stmt.executeQuery("DROP DATABASE IF EXISTS DemoWarehouseApplicationDB");
        
        closeConnection(c);
    }
    
    // delete a store room from database
    
    // delete a storage position
    
    // delete a room where products were used
    
    // delete a serialized product
    
    // delete an accessory
    
    // edit store room
    
    // edit storage postition
    
    // edit room
    
    // edit serialized product
    
    // edit an accessory
    
    // get store room information
    
    // get storage position information
    
    // get room information
    
    // get serialized product information
    
    // get accessory information
    
    
    
}
