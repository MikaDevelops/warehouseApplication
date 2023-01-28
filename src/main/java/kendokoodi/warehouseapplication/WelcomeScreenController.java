package kendokoodi.warehouseapplication;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kendokoodi.warehouseapplication.dbOperations.DataDefinition;
import static kendokoodi.warehouseapplication.dbOperations.MariaDB.*;

/**
 * Controller class for welcome screen. Implements Initializable.
 * @author Mika.1.virtala@edu.karelia.fi
 */
public class WelcomeScreenController implements Initializable {
    
    @FXML
    private BorderPane bPaneWelcome;

    @FXML
    private ComboBox<?> cmbDatabases;

    
    @FXML
    private Label lblWSmessage;
    
    @FXML
    private Button btnCreateDB;
    
    @FXML
    private Button btnWScreate;
    
    @FXML
    private TextField txtDBaddress;

    @FXML
    private PasswordField txtRootPassword;

    @FXML
    private TextField txtRootUserName;

    /**
     * Action event handler for menu close. Deletes demo database and
     * closes program.
     * @param event
     */
    @FXML
    private void menuClose(ActionEvent event) {
//        try {
//        //deleteDB();
//        } catch (SQLException ex){ sqlExceptionHandler(ex,
//                "welcomeScreen menuClose action event"); }
        Platform.exit();
        System.exit(0);
        
    }
    
    /**
     * Action event handler for menu -> help -> about. Opens new window
     * containing information about application.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void menuAbout(ActionEvent event) throws IOException {
        Scene aboutScene = new Scene( App.loadFXML("about"), 800, 600 );
        Stage aboutStage = new Stage();
        aboutStage.setScene(aboutScene);
        aboutStage.show();
    }
    
    /**
     * Action event handler to change to create database view.
     * @param event
     * @throws InterruptedException 
     */
    @FXML
    private void btnWScreate(ActionEvent event) throws InterruptedException {
        try {
            bPaneWelcome.setCenter(App.loadFXML("createDB"));
        } catch (IOException ex) {
            Logger.getLogger(WelcomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Action event handler for create database button. 
     * @param event 
     */
    @FXML
    private void btnCreateDBaction(ActionEvent event) {

    }
    
    /**
     * Action event handler for continue button. Changes view to main view.
     * @param event
     * @throws IOException 
     */
    @FXML

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    
}
