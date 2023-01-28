/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package kendokoodi.warehouseapplication.dbOperations;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author mika
 */
public class DataDefinitionTest {
    
//    DataDefinition dataDefinition;
    
//    @BeforeAll
//    public void beforeAll(){
//        DataDefinition dataDefinition = new DataDefinition("kayttaja","pjger903lk43");
//    }
    
//    @AfterAll
//    public void afterAll(){
//        dataDefinition = null;
//    }
    
    //public DataDefinitionTest() {
    //}

    /**
     * Test of createDatabase method, of class DataDefinition.
     */
    @Test
    public void testCreateDatabase() {
        DataDefinition dataDefinition = new DataDefinition ("localhost","kayttaja","pjger903lk43");
        boolean response = dataDefinition.createDatabase();
        assertTrue(response);
    }
    
}
