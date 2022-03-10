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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private Label lblAddMsg;

    @FXML
    private Button btnMainListAll;
    
    @FXML
    private Button btnMainSearch;
    
    @FXML
    private TextField txtFieldMainSearch;
    
    @FXML
    private TextField textFieldManufacturer;

    @FXML
    private TextField textFieldProductName;

    @FXML
    private TextField textFieldProductNo;

    @FXML
    private TextField textFieldSerialNo;

    @FXML
    private TextField textFieldWarranty;
    
    @FXML
    private ToggleGroup searchAttribute;
    
    @FXML
    private ToggleGroup productionStorage;
    
    @FXML
    private RadioButton radInProduction;

    @FXML
    private RadioButton radInStorage;
    
    @FXML
    private RadioButton radSerializedName;
    
    @FXML
    private RadioButton radSerializedSN;
    
    @FXML
    private CheckBox chkLeaseAdd;
    
    @FXML
    private ComboBox<?> cmbBoxLease;

    @FXML
    private ComboBox<?> cmbBoxRoom;
    
    @FXML
    private ComboBox<?> cmbBoxStorage;
    
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
    
    /**
     * Event handler for save button.
     * @param event 
     */
    @FXML
    void btnSaveAdd(ActionEvent event) {

    }
    
    /**
     * Event handler for combo box.
     * @param event 
     */
    @FXML
    void cmbBoxLeaseAction(ActionEvent event) {

    }
    
    @FXML
    void cmbBoxRoomAction(ActionEvent event) {

    }
    
    @FXML
    void cmbBoxStorageAction(ActionEvent event) {

    }
    
    @FXML
    void chkLeaseAddAction(ActionEvent event) {
        if (chkLeaseAdd.isSelected()){
            cmbBoxLease.setDisable(false);
        }else if (!chkLeaseAdd.isSelected()) {
            cmbBoxLease.setDisable(true);
        } 
    }
    
    @FXML
    void radInProductionAction(ActionEvent event) {
    	if (radInProduction.isSelected()) {
    		cmbBoxStorage.setDisable(true);
    		cmbBoxRoom.setDisable(false);
    	}
    }
    
    @FXML
    void radProductInStorageAction(ActionEvent event) {
    	if (radInStorage.isSelected()) {
    		cmbBoxRoom.setDisable(true);
    		cmbBoxStorage.setDisable(false);
    	}
    }
}
