package kendokoodi.warehouseapplication;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import static kendokoodi.warehouseapplication.dbOperations.MariaDB.*;
import kendokoodi.warehouseapplication.dbOperations.SerProdInfo;

/**
 * Controller for editView. View is for editing existing records of
 * serialized products. Loaded record can be deleted checking delete record
 * check box and after that pressing delete record button.
 * @author Mika.1.virtala@edu.karelia.fi
 */
public class EditViewController implements Initializable {
    
    /**
     * Selected productID to be edited.
     */
    public static int id;
    
    /**
     * Sets data from a SerProdInfo object to edit form. Uses static int id
     * variable as productID.
     */
    public void bindData () throws SQLException{
        
        // Load data from database
        SerProdInfo data = getSerializedProductData(id);
        
        // set data in text fields
        textFieldProductId.setText(String.valueOf( data.productID ) );
        textFieldManufacturer.setText( data.manufacturer );
        textFieldProductName.setText( data.name );
        textFieldProductNo.setText( data.productNo );
        textFieldSerialNo.setText( data.serialNo );
        textFieldWarranty.setText( String.valueOf( data.warranty ) );
        
        // set radio buttons
        if (data.isOwned ==0) {
            radLease.setSelected(true);
            radOwned.setSelected(false);
        }else if (data.isOwned==1){
            radLease.setSelected(false);
            radOwned.setSelected(true); }
        if (data.isInProduction==0){
            radInProduction.setSelected(false);
            radInStorage.setSelected(true);
        }else if (data.isInProduction==1){
            radInProduction.setSelected(true);
            radInStorage.setSelected(false); }
        
        // get lists for combo boxes
        // LeaseId list
        if ( data.isOwned==0 ){
        ArrayList leaseList = getLeaseIDlist();
        ObservableList <String> leaseIdObservableList = 
                    FXCollections.observableArrayList( leaseList );
            cboLeaseId.setItems(leaseIdObservableList);
            cboLeaseId.getSelectionModel().select( String.valueOf( data.leaseID ) );
        }else { cboLeaseId.setDisable(true); }
        
        // RoomId list
        if ( data.isInProduction==1 ){
        ArrayList roomList = getRoomIDlist();
        ObservableList <String> roomIdObservableList = 
                    FXCollections.observableArrayList( roomList ) ;
            cboRoomId.setItems(roomIdObservableList);
            cboRoomId.getSelectionModel().select( data.roomID );
        }else { cboRoomId.setDisable(true); cboStoragePos.setDisable(false); }
        
        // StoragePos list
        if ( data.isInProduction==0 ){
        ArrayList storageList = getStorageIDlist();
        ObservableList <String> storageIdObservableList = 
                    FXCollections.observableArrayList( storageList ) ;
            cboStoragePos.setItems(storageIdObservableList);
            cboStoragePos.getSelectionModel().select( data.positionID );
        }else { cboStoragePos.setDisable(true); }
    }
    
    @FXML
    private TextField textFieldProductId;
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
    private CheckBox chkDelete;
    @FXML
    private RadioButton radLease;
    @FXML
    private ToggleGroup tgLeaseOrOwned;
    @FXML
    private RadioButton radOwned;
    @FXML
    private RadioButton radInProduction;
    @FXML
    private ToggleGroup tgInProdOrStored;
    @FXML
    private RadioButton radInStorage;
    @FXML
    private ComboBox<String> cboLeaseId;
    @FXML
    private ComboBox<String> cboRoomId;
    @FXML
    private ComboBox<String> cboStoragePos;
    
    /**
     * Action event for lease radio button. If checked will get lease id
     * numbers from database to combo box.
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void radLeaseAction(ActionEvent event) throws SQLException {
        
        if (radLease.isSelected()){
        cboLeaseId.setDisable(false);
        ArrayList leaseList = getLeaseIDlist();
        ObservableList <String> leaseIdObservableList = 
                    FXCollections.observableArrayList( leaseList );
        cboLeaseId.setItems(leaseIdObservableList);
        
        // if leaseID is 0, select nothing from the list 
        // (prevents 0 value in the cbo list if toggled between)
//        if (data.leaseID==0){ cboLeaseId.getSelectionModel().select( null ); }
//        else{
//        cboLeaseId.getSelectionModel().select( String.valueOf( EditViewController.data.leaseID ) );}
        }
    }
    
    /**
     * Action event for product is owned radio button. Disables lease combo box.
     * @param event 
     */
    @FXML
    private void radOwnedAction(ActionEvent event) {
        if (radOwned.isSelected()){
        cboLeaseId.setDisable(true);
        cboLeaseId.setItems(null);
//        EditViewController.data.leaseID = 0;
//        EditViewController.data.isOwned = 1;
        }
    }
    
    /**
     * Action event for product is in production radio button. If checked,
     * gets room IDs from database and populates room id combo box.
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void radInProductionAction(ActionEvent event) throws SQLException {
        if (radInProduction.isSelected()){
        cboRoomId.setDisable(false);
        cboStoragePos.setDisable(true);
        ArrayList roomList = getRoomIDlist();
        ObservableList <String> roomIdObservableList = 
                    FXCollections.observableArrayList( roomList ) ;
        cboRoomId.setItems(roomIdObservableList);
        }
    }
    
    /**
     * Action event for product in storage radio button. If checked gets
     * storage positions from database and populates storage id combo box.
     * @param event
     * @throws SQLException 
     */
    @FXML
    private void radInStorageAction(ActionEvent event) throws SQLException {
        if (radInStorage.isSelected()){
        cboRoomId.setDisable(true);
        cboStoragePos.setDisable(false);
        ArrayList storageList = getStorageIDlist();
        ObservableList <String> storageIdObservableList = 
                    FXCollections.observableArrayList( storageList ) ;
        cboStoragePos.setItems(storageIdObservableList);
        }
    }
    
    /**
     * Action event for close and save button.
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
    @FXML
    private void btnCloseAndSave(ActionEvent event) throws SQLException, IOException {
        
        SerProdInfo formInfo = new SerProdInfo();
        // read data from form to data.
        formInfo.productID = Integer.parseInt(textFieldProductId.getText());
        formInfo.manufacturer = textFieldManufacturer.getText();
        formInfo.productNo = textFieldProductNo.getText();
        formInfo.serialNo = textFieldSerialNo.getText();
        formInfo.name = textFieldProductName.getText();
        formInfo.warranty = Integer.parseInt(textFieldWarranty.getText());
        
        // If lease is selected, isOwned is set to 0.
        if (radLease.isSelected()){
            formInfo.isOwned = 0;
            if (cboLeaseId.getValue().isEmpty()){
                formInfo.leaseID = 0; 
            } else {
                formInfo.leaseID = Integer.valueOf(cboLeaseId.getValue());
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
            if (cboRoomId.getValue().isEmpty()){
                formInfo.roomID = null;
            }else{
                formInfo.roomID = cboRoomId.getValue();
            }
        }
        
        if (radInStorage.isSelected()){
            formInfo.isInProduction = 0;
            formInfo.roomID = null;
            if (cboStoragePos.getValue().isEmpty()){
                formInfo.positionID = null;
            }else{
                formInfo.positionID = cboStoragePos.getValue();
            }}

        updateSerializedProduct( formInfo );
        formInfo = null;
        App.setRoot( "mainView" );
    }
    
    /**
     * Action event for close without saving button.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void btnCloseWOsave(ActionEvent event) throws IOException {
        App.setRoot( "mainView" );
    }
    
    /**
     * Action event for delete record button.
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
    @FXML
    private void btnDelete(ActionEvent event) throws SQLException, IOException {
        if (chkDelete.isSelected()){
        deleteRecord(id);
        App.setRoot( "mainView" );
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            bindData();
            
        } catch (SQLException ex) {
            Logger.getLogger(EditViewController.class.getName()).
                    log(Level.SEVERE, null, ex); }
    }
    
}
