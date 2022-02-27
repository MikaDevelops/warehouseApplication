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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import static kendokoodi.warehouseapplication.dbOperations.MariaDB.*;

public class WelcomeScreenController implements Initializable {
    
    @FXML
    private Label lblWSmessage;
    
    @FXML
    private Button btnWScreate;
    
    @FXML
    private ProgressIndicator progIndicatorWS;

    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void menuClose(ActionEvent event) throws SQLException {
        deleteDemoDB();
        Platform.exit();
        System.exit(0);
        
    }

    @FXML
    private void btnWScreatedemoDB(ActionEvent event) throws InterruptedException {
        try {
            // probably needs own thread to run -> todo
            //progIndicatorWS.setVisible(true);
            btnWScreate.setDisable(true);
            createDemoDB();
            //progIndicatorWS.setVisible(false);
            lblWSmessage.setText("Demonstrational database created!");
            
            
        } catch (SQLException ex) {
            Logger.getLogger(WelcomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
            lblWSmessage.setText("That didn't work out :(");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progIndicatorWS.setVisible(false);
    }
    
}
