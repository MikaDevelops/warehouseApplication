/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kendokoodi.warehouseapplication.dbOperations;

import java.sql.SQLException;
import java.time.LocalDateTime;
import kendokoodi.warehouseapplication.App;

/**
 *
 * @author mika
 */
public class SQLExceptionHandler {
    

    private SQLException e;
    private String sourceOfError;
    
    public SQLExceptionHandler(SQLException e, String sourceOfError){
    
        this.e = e;
        this.sourceOfError = sourceOfError;
    }
    
    public void log(){
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
}