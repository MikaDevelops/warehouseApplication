package kendokoodi.warehouseapplication.dbOperations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;

/**
 * Fetches data from database.
 * @author mika
 */
public class GetProductInfo {
    
    /**
     * Searches database with given search string and field.
     * @param searchString string to be searched from database
     * @param searchBase 0 name, 1 serial number
     * @return ArrayList of SerProd objects
     * @throws SQLException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ParseException 
     */
    protected ArrayList<ProductInfo> find(String searchString, int searchBase) throws SQLException, IOException, FileNotFoundException, ParseException{
        ArrayList<ProductInfo> serProdInfo = new ArrayList<>();
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
        
        ConnectionHandler connection = new ConnectionHandler();
        Connection c = connection.getConnection();
        
        Statement stmt = c.createStatement();

        stmt.executeQuery( "USE warehouseApplicationDB" );
        
        PreparedStatement pstmt = c.prepareStatement(
                "SELECT * FROM SerializedProduct WHERE "+ base + "?"
        );
        
        pstmt.setString(1, searchString);
        ResultSet rs = pstmt.executeQuery();
        
        stmt.close();
        pstmt.close();
        connection.closeConnection( c );
        
        // Handle result set
        while (rs.next()){
            
            ProductInfo serialized = new ProductInfo();
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
     * Gets all serialized product database product entries.
     * @return ArrayList of SerProd objects containing all products from database
     * @throws SQLException 
     */
    public ArrayList<ProductInfo> all() throws SQLException, IOException, FileNotFoundException, ParseException{
    
        ArrayList<ProductInfo> serProdInfo = new ArrayList<>();
        
        ConnectionHandler connection = new ConnectionHandler();
        Connection c = connection.getConnection();
        
        Statement stmt = c.createStatement();
        stmt.execute("USE warehouseApplicationDB");
        ResultSet rs = stmt.executeQuery("SELECT * FROM SerializedProduct"
                + " ORDER BY productID");
        //stmt.execute("COMMIT");
        stmt.close();
        connection.closeConnection ( c );
        
        // Handle result set
        while (rs.next()){
            
            ProductInfo serialized = new ProductInfo();
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
}