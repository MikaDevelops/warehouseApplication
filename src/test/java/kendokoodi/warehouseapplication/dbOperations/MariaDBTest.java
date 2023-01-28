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
public class MariaDBTest {
  
// tulee testattua joka kerta muissa testeissä.
//    /**
//     * Test of openConnection method, of class MariaDB.
//     */
//    @org.junit.jupiter.api.Test
//    public void testOpenConnection() throws Exception {
//    }

    // tulee testattua joka testissä
//    /**
//     * Test of closeConnection method, of class MariaDB.
//     */
//    @org.junit.jupiter.api.Test
//    public void testCloseConnection() throws Exception {
//    }

    /**
     * Test of createDB method, of class MariaDB.
     * What is created into server can be found afterwards.
     */
   /* @org.junit.jupiter.api.Test
    public void testCreateDB() throws Exception {
        
        // luodaan demotietokanta
        MariaDB.createDB( "testDB" );
        
        Connection c = MariaDB.openConnection();
        
        Statement s = c.createStatement();
        
        s.execute("SET autocommit=1");
        
        ResultSet rs = s.executeQuery("SHOW DATABASES LIKE 'testDB'");
        
        rs.first();
        String result = rs.getString(1);
        String expectedResult = "testDB";
        assertEquals(expectedResult, result);
        
        MariaDB.deleteDB();
        ResultSet rs2 = s.executeQuery("SHOW DATABASES LIKE 'testDB'");
        
        String result2;
        if (rs2.next()){
        rs.first();
        result2 = rs2.getString(1);}
        else { result2 = "not found";}
        assertNotEquals(expectedResult, result2);
        
        MariaDB.closeConnection(c);
        
    }

    /**
     * Test of addSerializedProduct method, of class MariaDB.
     * What is is written into database can be returned.
     */
  /*  @org.junit.jupiter.api.Test
    public void testAddSerializedProduct() throws Exception {
        
        // luodaan demotietokanta

        MariaDB.createDB( "testDB" );
        
        // lisätään kantaan testitietue
        SerProdInfo testInfo = new SerProdInfo();
        testInfo.productNo = "j2j2j2";
        testInfo.serialNo = "j3j3j3";
        testInfo.manufacturer = "manu";
        testInfo.name = "nimi";
        testInfo.warranty = 1;
        testInfo.isOwned = 0;
        testInfo.isInProduction = 0;
        testInfo.leaseID = 1;
        testInfo.roomID = null;
        testInfo.positionID = "M1-A1";
        
        // käytä metodia tiedon lisäämiseen palvelimeen
        
        MariaDB.addSerializedProduct(testInfo);
        
        // otetaan uudestaan yhteys palvelimeen ja valitaan tietokanta.
        Connection c = MariaDB.openConnection();
        Statement s = c.createStatement();
        s.execute("SET autocommit=1");
        s.execute("USE testDB");
        
        // haetaan tietuetta
        ResultSet rs = s.executeQuery("SELECT * FROM SerializedProduct "
                + "WHERE productNo='j2j2j2' AND serialNo='j3j3j3' "
                + "AND manufacturer='manu'");
        
        // luodaan olio vastaanottamaan dataa
        SerProdInfo result = new SerProdInfo();
        
        // siirrytään tulossetin ekalle riville
        rs.first();
        
        // otetaan vastausoliolta tiedot ilmentymään.
        result.productNo = rs.getString("productNo");
        result.serialNo = rs.getString("serialNo");
        result.manufacturer = rs.getString("manufacturer");
        result.name = rs.getString("name");
        result.warranty = rs.getInt("warranty");
        result.isOwned = rs.getInt("isOwned");
        result.isInProduction = rs.getInt("isInProduction");
        result.leaseID = rs.getInt("leaseID");
        result.roomID = rs.getString("roomID");
        result.positionID = rs.getString("positionID");
        
        // tietokanta autoinkrementoi id:n tietueelle
        // laiskan miehen tarkistus ilman id:tä
        
        assertEquals( testInfo.productNo, result.productNo );
        assertEquals( testInfo.serialNo, result.serialNo );
        assertEquals( testInfo.manufacturer, result.manufacturer );
        assertEquals( testInfo.name, result.name );
        assertEquals( testInfo.warranty, result.warranty );
        assertEquals( testInfo.isOwned, result.isOwned );
        assertEquals( testInfo.isInProduction, result.isInProduction );
        assertEquals( testInfo.leaseID, result.leaseID );
        assertEquals( testInfo.roomID, result.roomID );
        assertEquals( testInfo.positionID, result.positionID );
      
        // poistetaan demokanta
        MariaDB.deleteDB();
        MariaDB.closeConnection (c);
        
    }

    // tulee käytettyä joka testissä ja testattua createDB testissä
//    /**
//     * Test of deleteDB method, of class MariaDB.
//     */
//    @org.junit.jupiter.api.Test
//    public void testDeleteDemoDB() throws Exception {
//    }

    /**
     * Test of deleteRecord method, of class MariaDB.
     * Expectation is that given read record is not anymore found
     * after deletion.
     */
  /*  @org.junit.jupiter.api.Test
    public void testDeleteRecord() throws Exception {
        // odotusarvo id:lle
        int iDexpected = 1;
        
        // luodaan demotietokanta
        MariaDB.createDB( "testDB" );
        Connection c = MariaDB.openConnection();
        Statement s = c.createStatement();
        s.execute("SET autocommit=1");
        s.execute("USE DemoWarehouseApplicationDB");
        
        // luetaan tiedot id 1 ja varmistetaan olemassa olo
        ResultSet rs = s.executeQuery("SELECT * FROM SerializedProduct "
                + "WHERE productID=1");
        SerProdInfo infoFromDB = new SerProdInfo();
        
        rs.first();
        
        // otetaan vastausoliolta tiedot ilmentymään.
        infoFromDB.productID = rs.getInt("productID");
        infoFromDB.productNo = rs.getString("productNo");
        infoFromDB.serialNo = rs.getString("serialNo");
        infoFromDB.manufacturer = rs.getString("manufacturer");
        infoFromDB.name = rs.getString("name");
        infoFromDB.warranty = rs.getInt("warranty");
        infoFromDB.isOwned = rs.getInt("isOwned");
        infoFromDB.isInProduction = rs.getInt("isInProduction");
        infoFromDB.leaseID = rs.getInt("leaseID");
        infoFromDB.roomID = rs.getString("roomID");
        infoFromDB.positionID = rs.getString("positionID");
        
        assertEquals(infoFromDB.productID, iDexpected);
        
        // poistetaan tietue
        MariaDB.deleteRecord(iDexpected);
        
        // haetaan tietuetta uudestaan poisto-operaation jälkeen
        ResultSet rs2 = s.executeQuery("SELECT * FROM SerializedProduct "
                + "WHERE productID=1");
        SerProdInfo infoFromDB2 = new SerProdInfo();
        if (rs2.next()){
        rs.first();
        infoFromDB.productID = rs.getInt("productID");
        infoFromDB.productNo = rs.getString("productNo");
        infoFromDB.serialNo = rs.getString("serialNo");
        infoFromDB.manufacturer = rs.getString("manufacturer");
        infoFromDB.name = rs.getString("name");
        infoFromDB.warranty = rs.getInt("warranty");
        infoFromDB.isOwned = rs.getInt("isOwned");
        infoFromDB.isInProduction = rs.getInt("isInProduction");
        infoFromDB.leaseID = rs.getInt("leaseID");
        infoFromDB.roomID = rs.getString("roomID");
        infoFromDB.positionID = rs.getString("positionID");
        }else infoFromDB2.productID=0;
        
        assertNotEquals(infoFromDB2.productID, iDexpected);
        
        // lopuksi pistetään pakka bittitaivaaseen.
        MariaDB.deleteDB();
        
        MariaDB.closeConnection(c);
    }

    /**
     * Test of updateSerializedProduct method, of class MariaDB.
     */
  /*  @org.junit.jupiter.api.Test
    public void testUpdateSerializedProduct() throws Exception {
        MariaDB.createDB( "testDB" );
        Connection c = MariaDB.openConnection();
        Statement s = c.createStatement();
        s.execute("SET autocommit=1");
        s.execute("USE DemoWarehouseApplicationDB");
        
        // luodaan päivitysolio
        SerProdInfo newData = new SerProdInfo();
        newData.productID = 1;
        newData.isInProduction = 1;
        newData.isOwned = 0;
        newData.leaseID = 1;
        newData.manufacturer = "testmanufacturer";
        newData.name = "testname";
        newData.positionID = "0";
        newData.productNo = "testproductno";
        newData.roomID = "M100";
        newData.serialNo = "testserialno";
        newData.warranty = 1;
        
        // päivitetään tiedot
        MariaDB.updateSerializedProduct( newData );
        
        // luetaan päivitetyt tiedot
        ResultSet rs = s.executeQuery("SELECT * FROM SerializedProduct "
                + "WHERE productID=1");
        SerProdInfo resultInfo = new SerProdInfo();
        rs.first();
        resultInfo.productID = rs.getInt("productID");
        resultInfo.productNo = rs.getString("productNo");
        resultInfo.serialNo = rs.getString("serialNo");
        resultInfo.manufacturer = rs.getString("manufacturer");
        resultInfo.name = rs.getString("name");
        resultInfo.warranty = rs.getInt("warranty");
        resultInfo.isOwned = rs.getInt("isOwned");
        resultInfo.isInProduction = rs.getInt("isInProduction");
        resultInfo.leaseID = rs.getInt("leaseID");
        resultInfo.roomID = rs.getString("roomID");
        resultInfo.positionID = rs.getString("positionID");
        
        assertEquals(resultInfo.isInProduction, newData.isInProduction);
        assertEquals(resultInfo.isOwned, newData.isOwned);
        assertEquals(resultInfo.leaseID, newData.leaseID);
        assertEquals(resultInfo.manufacturer, newData.manufacturer);
        assertEquals(resultInfo.name, newData.name);
        assertEquals(resultInfo.productNo, newData.productNo);
        assertEquals(resultInfo.roomID, newData.roomID);
        assertEquals(resultInfo.serialNo, newData.serialNo);
        assertEquals(resultInfo.warranty, newData.warranty);
        
        MariaDB.deleteDB();
        MariaDB.closeConnection(c); 
    }

    /**
     * Test of getSerializedProductData method, of class MariaDB.
     */
  /*  @org.junit.jupiter.api.Test
    public void testGetSerializedProductData() throws Exception {
        MariaDB.createDB( "testDB" );
        
        SerProdInfo expected = new SerProdInfo();
        
        expected.productID = 2;
        expected.productNo = "60-1625-01";
        expected.serialNo = "QWERTY6";
        expected.manufacturer = "Extron";
        expected.name = "DTP T SW4 HD 4K";
        expected.warranty = 12;
        expected.isOwned = 0;
        expected.isInProduction = 1;
        
        // haetaan id 2:n tiedot
        SerProdInfo result = MariaDB.getSerializedProductData(2);
        
        assertEquals(expected.productID, result.productID);
        assertEquals(expected.productNo, result.productNo);
        assertEquals(expected.serialNo, result.serialNo);
        assertEquals(expected.manufacturer,result.manufacturer);
        assertEquals(expected.name,result.name);
        assertEquals(expected.warranty,result.warranty);
        assertEquals(expected.isOwned,result.isOwned);
        assertEquals(expected.isInProduction,result.isInProduction);
        
        MariaDB.deleteDB();
    }

    /**
     * Test of getLeaseIDlist method, of class MariaDB.
     */
  /*  @org.junit.jupiter.api.Test
    public void testGetLeaseIDlist() throws Exception {
        MariaDB.createDB( "testDB" );
        
        ArrayList result = MariaDB.getLeaseIDlist();
        ArrayList<String> expected = new ArrayList<>();
        expected.add("1");
        expected.add("2");
        
        assertIterableEquals(expected, result);
        
        MariaDB.deleteDB();
    }

    /**
     * Test of getRoomIDlist method, of class MariaDB.
     */
 /*   @org.junit.jupiter.api.Test
    public void testGetRoomIDlist() throws Exception {
        MariaDB.createDB( "testDB" );
        
        ArrayList result = MariaDB.getRoomIDlist();
        ArrayList<String> expected = new ArrayList<>();
        
        expected.add("M100");
        expected.add("M101");
        expected.add("M102");
        expected.add("M103");
        expected.add("M104");
        expected.add("M105");
        expected.add("M106");
        expected.add("M107");
        
        assertIterableEquals(expected, result);
        
        MariaDB.deleteDB();
    }

    /**
     * Test of getStorageIDlist method, of class MariaDB.
     */
 /*  @org.junit.jupiter.api.Test
    public void testGetStorageIDlist() throws Exception {
       MariaDB.createDB( "testDB" );
        
        ArrayList result = MariaDB.getStorageIDlist();
        ArrayList<String> expected = new ArrayList<>();
        
        expected.add("M1-A1");
        expected.add("M1-A2");
        expected.add("M1-A3");
        
        assertIterableEquals(expected, result);
        
        MariaDB.deleteDB();
    }

    /**
     * Test of listAllSerialized method, of class MariaDB.
     */
   /* @org.junit.jupiter.api.Test
    public void testListAllSerialized() throws Exception {
        
        MariaDB.createDB( "testDB" );
        
        ArrayList<SerProdInfo> expected = new ArrayList<>();
        
        SerProdInfo spi1 = new SerProdInfo();
        SerProdInfo spi2 = new SerProdInfo();
        SerProdInfo spi3 = new SerProdInfo();
        SerProdInfo spi4 = new SerProdInfo();
        SerProdInfo spi5 = new SerProdInfo();
        
        spi1.productID = 1;
        spi1.productNo = "60-1382-01";
        spi1.serialNo = "ASDFGHJKL1";
        spi1.manufacturer = "Extron";
        spi1.name = "DTP CrossPoint 86 4K";
        spi1.warranty = 24;
        spi1.isOwned = 0;
        spi1.isInProduction = 0;
        
        spi2.productID = 2;
        spi2.productNo = "60-1625-01";
        spi2.serialNo = "QWERTY6";
        spi2.manufacturer = "Extron";
        spi2.name = "DTP T SW4 HD 4K";
        spi2.warranty = 12;
        spi2.isOwned = 0;
        spi2.isInProduction = 1;
        
        spi3.productID = 3;
        spi3.productNo = "60-1594-01";
        spi3.serialNo = "POIUYTT357";
        spi3.manufacturer = "Extron";
        spi3.name = "SMP 111";
        spi3.warranty = 24;
        spi3.isOwned = 0;
        spi3.isInProduction = 1;
        
        spi4.productID = 4;
        spi4.productNo = "6511006";
        spi4.serialNo = "tyrieruw844";
        spi4.manufacturer = "Crestron";
        spi4.name = "DM-NVX-360";
        spi4.warranty = 12;
        spi4.isOwned = 1;
        spi4.isInProduction = 1;
        
        spi5.productID = 5;
        spi5.productNo = "null";
        spi5.serialNo = "wowowow2";
        spi5.manufacturer = "Shure";
        spi5.name = "SM58 Microphone";
        spi5.warranty = 12;
        spi5.isOwned = 1;
        spi5.isInProduction = 1;
        
        expected.add(0, spi1);
        expected.add(1, spi2);
        expected.add(2, spi3);
        expected.add(3, spi4);
        expected.add(4, spi5);
////        (productNo, serialNo, manufacturer, name, warranty, isOwned, isInProduction)"
//        +"VALUES" 
//        +"('60-1382-01','ASDFGHJKL1','Extron','DTP CrossPoint 86 4K',24,0,0),"
//        +"('60-1625-01','QWERTY6','Extron','DTP T SW4 HD 4K',12,0,1),"
//        +"('60-1594-01','POIUYTT357','Extron','SMP 111',24,0,1),"
//        +"('6511006','tyrieruw844','Crestron','DM-NVX-360',12,1,1),"
//        +"(NULL,'wowowow2','Shure','SM58 Microphone',12,1,1)"
        
        ArrayList<SerProdInfo> result = MariaDB.listAllSerialized();
        
        for (int i=0; i < expected.size(); i++){
        assertEquals(expected.get(i).productID,result.get(i).productID);
        // joissain tietueissa productNo null, koska ei pakollinen tieto
        //assertEquals(expected.get(i).productNo, result.get(i).productNo);
        assertEquals(expected.get(i).serialNo, result.get(i).serialNo);
        assertEquals(expected.get(i).manufacturer,result.get(i).manufacturer);
        assertEquals(expected.get(i).name,result.get(i).name);
        assertEquals(expected.get(i).warranty,result.get(i).warranty);
        assertEquals(expected.get(i).isOwned,result.get(i).isOwned);
        }
        
        MariaDB.deleteDB();

    }

    /**
     * Test of searchSerialized method, of class MariaDB.
     */
  /*  @org.junit.jupiter.api.Test
    public void testSearchSerialized() throws Exception {
        
        MariaDB.createDB( "testDB" );
        
        ArrayList<SerProdInfo> expected = new ArrayList<>();
        SerProdInfo spi1 = new SerProdInfo();
        spi1.productID = 1;
        spi1.productNo = "60-1382-01";
        spi1.serialNo = "ASDFGHJKL1";
        spi1.manufacturer = "Extron";
        spi1.name = "DTP CrossPoint 86 4K";
        spi1.warranty = 24;
        spi1.isOwned = 0;
        spi1.isInProduction = 0;
        expected.add(spi1);
        
        ArrayList<SerProdInfo> result = MariaDB.searchSerialized("DTP CrossPoint 86 4K", 0);
        
        for (int i=0; i < expected.size(); i++){
        assertEquals(expected.get(i).productID,result.get(i).productID);
        // joissain tietueissa productNo null, koska ei pakollinen tieto
        //assertEquals(expected.get(i).productNo, result.get(i).productNo);
        assertEquals(expected.get(i).serialNo, result.get(i).serialNo);
        assertEquals(expected.get(i).manufacturer,result.get(i).manufacturer);
        assertEquals(expected.get(i).name,result.get(i).name);
        assertEquals(expected.get(i).warranty,result.get(i).warranty);
        assertEquals(expected.get(i).isOwned,result.get(i).isOwned);
        }
        
        result.clear();
        result = MariaDB.searchSerialized("ASDFGHJKL1", 1);
        
        for (int i=0; i < expected.size(); i++){
        assertEquals(expected.get(i).productID,result.get(i).productID);
        // joissain tietueissa productNo null, koska ei pakollinen tieto
        //assertEquals(expected.get(i).productNo, result.get(i).productNo);
        assertEquals(expected.get(i).serialNo, result.get(i).serialNo);
        assertEquals(expected.get(i).manufacturer,result.get(i).manufacturer);
        assertEquals(expected.get(i).name,result.get(i).name);
        assertEquals(expected.get(i).warranty,result.get(i).warranty);
        assertEquals(expected.get(i).isOwned,result.get(i).isOwned);
        }
        
        MariaDB.deleteDB();
    }

    /**
     * Test of sqlExceptionHandler method, of class MariaDB.
     */
//    @org.junit.jupiter.api.Test
//    public void testSqlExceptionHandler() {
//    }


}
