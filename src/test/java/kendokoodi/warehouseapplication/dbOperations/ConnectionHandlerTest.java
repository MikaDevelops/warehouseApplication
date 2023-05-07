package kendokoodi.warehouseapplication.dbOperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author mika
 */
public class ConnectionHandlerTest {
  

    /**
     * Test of getConnection method, of class ConnectionHandler.
     */
    @org.junit.jupiter.api.Test
    public void testGetConnection() throws Exception {
        
        ConnectionHandler connHandler = new ConnectionHandler();
        Connection conn = connHandler.getConnection();
        
        // Connection should not be closed
        assertFalse(conn.isClosed());
        
        connHandler.closeConnection(conn);
        
        // Connection should be closed
        assertTrue(conn.isClosed());
      
    }

    /**
     * Test of closeConnection method, of class ConnectionHandler.
     */
    /*
    @org.junit.Test
    public void testCloseConnection() throws Exception {
    }*/

}
