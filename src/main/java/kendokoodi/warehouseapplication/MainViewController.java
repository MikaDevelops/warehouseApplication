package kendokoodi.warehouseapplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import static kendokoodi.warehouseapplication.dbOperations.MariaDB.*;
import kendokoodi.warehouseapplication.dbOperations.SerProdInfo;

/**
 * Controller for the main view.
 * @author Mika.1.virtala@edu.karelia.fi
 */
public class MainViewController {

    @FXML
    private Button btnMainListAll;
    
    @FXML
    private Button btnMainSearch;
    
    @FXML
    private TextField txtFieldMainSearch;
    
    @FXML
    private ToggleGroup searchAttribute;
    
    @FXML
    private RadioButton radSerializedName;
    
    @FXML
    private RadioButton radSerializedSN;
    @FXML
    private ListView<SerProdInfo> listViewSerializedMain;

    /**
     * Event handler for menu close. Closes program and drops demo database.
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void menuMainClose(ActionEvent event) throws SQLException {
        deleteDemoDB();
        Platform.exit();
        System.exit(0);
    }
    
    /**
     * Opens new window and views about information.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void menuMainAbout(ActionEvent event) throws IOException {
        Scene aboutScene = new Scene( App.loadFXML("about"), 800, 600 );
        Stage aboutStage = new Stage();
        aboutStage.setScene(aboutScene);
        aboutStage.show();
    }
    
    /**
     * Event handler for list all products button.
     * @param event 
     */
    @FXML
    private void btnMainListAllAction(ActionEvent event) {
    }
    
    /**
     * Event handler for search button. Reads text field input and
     * runs search on database. Updates the results to view.
     * @param event 
     */
    @FXML
    private void btnMainSearchAction(ActionEvent event) throws SQLException {
        
        String inputText = txtFieldMainSearch.getText();
        
        int searchOn;
        
        // Check whitch radiobutton is selected.
        if (radSerializedName.isSelected()){ searchOn = 0; }
        else if (radSerializedSN.isSelected()) { searchOn = 1; }
        else { searchOn = 2; } // will cause exception
        
        // run search and catch results
        ArrayList<SerProdInfo> result = searchSerialized(inputText,searchOn);
        
        //ObservableList<SerProdInfo> ol = FXCollections.observableArrayList(result);
        
        ObservableList ol = FXCollections.observableArrayList();       
        
        for (int i=0; i < result.size(); i++){
            ol.add(result.get(i).name + " " + result.get(i).serialNo
            + " " + result.get(i).roomID + " " + result.get(i).positionID);
        }
        
        // update results to view
        listViewSerializedMain.setItems(ol);
    }

}
