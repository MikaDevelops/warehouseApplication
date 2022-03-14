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
    private ComboBox<String> cmbBoxLease;

    @FXML
    private ComboBox<String> cmbBoxRoom;
    
    @FXML
    private ComboBox<String> cmbBoxStorage;
    
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
    private void btnMainListAllAction(ActionEvent event) throws SQLException {
        ArrayList<SerProdInfo> result = listAllSerialized();
        ObservableList ol = FXCollections.observableArrayList();
        for (int i = 0; i < result.size(); i++){
            ol.add(result.get(i).name + " " + result.get(i).serialNo
            + " " + result.get(i).roomID + " " + result.get(i).positionID);
        }
        listViewSerializedMain.setItems( ol );
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
    void btnSaveAdd(ActionEvent event) throws SQLException {
        
        // New instance of SerProdInfo.
        SerProdInfo formInfo = new SerProdInfo();
        
        // Read input from the form.
        formInfo.manufacturer = textFieldManufacturer.getText();
        formInfo.productNo = textFieldProductNo.getText();
        formInfo.serialNo = textFieldSerialNo.getText();
        formInfo.name = textFieldProductName.getText();
        formInfo.warranty = Integer.valueOf(textFieldWarranty.getText());
        
        // If lease is selected, isOwned is set to 0.
        if (chkLeaseAdd.isSelected()){
            formInfo.isOwned = 0;
            if (cmbBoxLease.getValue().isEmpty()){
                formInfo.leaseID = 0; 
            } else {
                formInfo.leaseID = Integer.valueOf(cmbBoxLease.getValue());
            }
        }else{
            formInfo.isOwned = 1;}
        
        if (!radInProduction.isSelected() && !radInStorage.isSelected()){
            formInfo.isInProduction = 0;
            formInfo.roomID = null;
            formInfo.positionID = null;
        }else if (radInProduction.isSelected()){
            formInfo.isInProduction = 1;
            formInfo.positionID = null;
            if (cmbBoxRoom.getValue().isEmpty()){
                formInfo.roomID = null;
            }else{
                formInfo.roomID = cmbBoxRoom.getValue();
            }
        }else if (radInStorage.isSelected()){
            formInfo.isInProduction = 0;
            formInfo.roomID = null;
            if (cmbBoxStorage.getValue().isEmpty()){
                formInfo.positionID = null;
            }else{
                formInfo.positionID = cmbBoxStorage.getValue();
            }
        }
        
        // Pass form information object to database method.
        addSerializedProduct( formInfo );
        
        // Empty form
        textFieldManufacturer.clear();
        textFieldProductNo.clear();
        textFieldSerialNo.clear();
        textFieldProductName.clear();
        textFieldWarranty.clear();
        chkLeaseAdd.setSelected(false);
        cmbBoxLease.setDisable(true);
        radInProduction.setSelected(false);
        cmbBoxRoom.setDisable(true);
        radInStorage.setSelected(false);
        cmbBoxStorage.setDisable(true);
        formInfo = null;
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
    
    /**
     * Lease checkbox event handler. If checkbox is selected, enables
     * lease id combo box and gets leaseID list from database.
     * @param event
     * @throws SQLException 
     */
    @FXML
    void chkLeaseAddAction(ActionEvent event) throws SQLException {
        if (chkLeaseAdd.isSelected()){
            cmbBoxLease.setDisable(false);
            
            ArrayList<String> leaseIdList = getLeaseIDlist();
            ObservableList <String> leaseIdObservableList = 
                    FXCollections.observableArrayList(leaseIdList);
            cmbBoxLease.setItems(leaseIdObservableList);
    
        }else if (!chkLeaseAdd.isSelected()) {
            cmbBoxLease.setDisable(true);
        } 
    }
    
    /**
     * In production radio button event handler. If radio button is
     * selected, enables room combo cox and disable storage combo box.
     * Loads room ID list from database and adds it to room combo box.
     * @param event
     * @throws SQLException 
     */	
    @FXML
    void radInProductionAction(ActionEvent event) throws SQLException {
    	if (radInProduction.isSelected()) {
    		cmbBoxStorage.setDisable(true);
    		cmbBoxRoom.setDisable(false);
                
                ArrayList<String> roomIdList = getRoomIDlist();
                ObservableList <String> roomIdObservableList = 
                    FXCollections.observableArrayList(roomIdList);
                cmbBoxRoom.setItems(roomIdObservableList);
    	}
    }
    
    /**
     * In Storage radio button. If selected, enables storage position combo
     * box and loads storage positions from database to the combo box list.
     * @param event
     * @throws SQLException 
     */
    @FXML
    void radProductInStorageAction(ActionEvent event) throws SQLException {
    	if (radInStorage.isSelected()) {
    		cmbBoxRoom.setDisable(true);
    		cmbBoxStorage.setDisable(false);
                
                ArrayList<String> storageIdList = getStorageIDlist();
                ObservableList <String> storageIdObservableList = 
                    FXCollections.observableArrayList(storageIdList);
                cmbBoxStorage.setItems(storageIdObservableList);
    	}
    }
}