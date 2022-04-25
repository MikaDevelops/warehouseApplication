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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import static kendokoodi.warehouseapplication.dbOperations.MariaDB.*;

/**
 * Controller class for welcome screen. Implements Initializable.
 * @author Mika.1.virtala@edu.karelia.fi
 */
public class WelcomeScreenController implements Initializable {
    
    @FXML
    private Label lblWSmessage;
    
    @FXML
    private Button btnWScreate;
    
    @FXML
    private ProgressIndicator progIndicatorWS;
    
    @FXML
    private Button btnContinue;


    /**
     * Action event handler for menu close. Deletes demo database and
     * closes program.
     * @param event
     */
    @FXML
    private void menuClose(ActionEvent event) {
        try {
        deleteDemoDB();
        } catch (SQLException ex){ sqlExceptionHandler(ex,
                "welcomeScreen menuClose action event"); }
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
     * Action event handler for demo database creation button.
     * @param event
     * @throws InterruptedException 
     */
    @FXML
    private void btnWScreatedemoDB(ActionEvent event) throws InterruptedException {
        
        
        new Thread( () -> {
        
            Platform.runLater(()-> progIndicatorWS.setVisible( true ));
            
            try {
            
            Platform.runLater(()-> btnWScreate.setDisable( true ));
            
            createDemoDB();
            
            Platform.runLater(()-> lblWSmessage.setText("Demonstrational database created!"));
            Platform.runLater(()-> btnContinue.setDisable( false ));
            Platform.runLater(()-> progIndicatorWS.setVisible( false ));
            
            } catch (SQLException ex) {
                Logger.getLogger(WelcomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                lblWSmessage.setText("That didn't work out :(");
                sqlExceptionHandler( ex, "btnWScreatedemoDB action event");
                Platform.runLater(()-> progIndicatorWS.setVisible( false ));
            }
        
        }).start();
        
    }
    
    /**
     * Action event handler for continue button. Changes view to main view.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void btnWScontinue(ActionEvent event) throws IOException {
        App.setRoot("mainView");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progIndicatorWS.setVisible(false);
    }


    
}
