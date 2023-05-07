package kendokoodi.warehouseapplication.dbOperations;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author mika
 */
public class GetProductInfoTest {
    /**
     * Test of find() method, of class GetProductInfo.
     */
    /*
    @Test
    public void testFind() throws Exception {
    }*/
    
    /**
     * Test of all() method, of class GetProductInfo.
     */
    @Test
    public void testGetAllProductInfo() throws Exception {
        GetProductInfo getProductInfo = new GetProductInfo();
        
        ArrayList<ProductInfo> expected = new ArrayList<>();
        ProductInfo expectedInfo = new ProductInfo();
        expectedInfo.name = "test";
        expectedInfo.productNo = "testingProductNo";
        expectedInfo.serialNo = "testingSerialNo";
        expectedInfo.manufacturer = "testingManuf";
        expectedInfo.warranty = 5;
        expectedInfo.isOwned = 1;
        expectedInfo.isInProduction = 0;
        expectedInfo.productID = 1;
        
        ArrayList<ProductInfo> result = getProductInfo.all();
        assertEquals(expectedInfo.name, result.get(0).name, "test name");
        assertEquals(expectedInfo.productNo, result.get(0).productNo, "test prodno");
        assertEquals(expectedInfo.serialNo, result.get(0).serialNo, "test serialno");
        assertEquals(expectedInfo.manufacturer, result.get(0).manufacturer,"test manufacturer");
      
    }
    
}
