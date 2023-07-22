package kendokoodi.warehouseapplication;

import java.io.FileNotFoundException;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import kendokoodi.warehouseapplication.dbOperations.GetProductInfo;
import kendokoodi.warehouseapplication.dbOperations.ProductInfo;
import kendokoodi.warehouseapplication.dbOperations.SQLExceptionHandler;
import org.json.simple.parser.ParseException;


/**
 * Controller for the main view.
 * @author Mika.1.virtala@edu.karelia.fi
 */
public class MainViewController {
	
    @FXML
    private Label lblEditMsg;
    
    @FXML
    private Label lblAttWarranty;
    
    @FXML
    private Label lblAttProdInfo;
    
    @FXML
    private Button btnMainListAll;
    
    @FXML
    private Button btnMainSearch;
    
    @FXML
    private Button btnEditSelected;
    
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
    private ListView<ProductInfo> listViewSerializedMain;

    /**
     * Event handler for menu close. Closes program and drops demo database.
     * @param event
     */
    @FXML
    private void menuMainClose(ActionEvent event) {
//        try {
//        //deleteDB();
//        } catch (SQLException ex) { sqlExceptionHandler(ex,
//                "mainView menuMainClose action event"); }
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
    private void btnMainListAllAction(ActionEvent event) throws IOException, FileNotFoundException, ParseException {
        try {
        GetProductInfo getInfo = new GetProductInfo();
        ArrayList<ProductInfo> result = getInfo.all();
        ObservableList ol = FXCollections.observableArrayList( result );

        listViewSerializedMain.setItems( ol );
        listViewSerializedMain.setCellFactory(clbck -> new ListCell<ProductInfo>() {
            @Override
            protected void updateItem(ProductInfo item, boolean empty){
            super.updateItem( item, empty );
            
            if (empty || item == null){ setText(null); }
            else {
               
                setText(item.manufacturer + " " + item.name + ", s/n: " + item.serialNo 
                        + ", room: " + item.roomID + ", storage pos.: " + item.positionID);
                }
            }
        });
        } catch (SQLException ex){ 
            SQLExceptionHandler handler = new SQLExceptionHandler(ex, "mainView btnMainListAllAction");
            handler.log();
        }
    }
    
    /**
     * Event handler for search button. Reads text field input and
     * runs search on database. Updates the results to view.
     * @param event 
     */
    @FXML
    private void btnMainSearchAction(ActionEvent event) throws IOException, FileNotFoundException, ParseException {
        
        String inputText = txtFieldMainSearch.getText();
        
        int searchOn;
        
        // Check whitch radiobutton is selected.
        if (radSerializedName.isSelected()){ searchOn = 0; }
        else if (radSerializedSN.isSelected()) { searchOn = 1; }
        else { searchOn = 2; } // will cause exception
        
        try {
        // run search and catch results
        GetProductInfo getInfo = new GetProductInfo();
        ArrayList<ProductInfo> result = getInfo.find(inputText,searchOn);
        
        ObservableList<ProductInfo> ol = FXCollections.observableArrayList(result);

        // update results to view
        listViewSerializedMain.setItems(ol);
        listViewSerializedMain.setCellFactory(clbck -> new ListCell<ProductInfo>() {
            @Override
            protected void updateItem(ProductInfo item, boolean empty){
            super.updateItem( item, empty );
            
            if (empty || item == null){ setText(null); }
            else {
               
                setText(item.manufacturer + " " + item.name + ", s/n: " + item.serialNo 
                        + ", room: " + item.roomID + ", storage pos.: " + item.positionID);
                }
            }
        });
        } catch (SQLException ex) { 
            SQLExceptionHandler handler = new SQLExceptionHandler(ex, "mainView btnMainSearchAction");
            handler.log(); 
        }
    }
    
    /**
     * Main page edit button action. Sets product id from selected listView object
     * to EvenViewController static int variable id.
     * @param event
     * @throws IOExeption
     */
    @FXML
    private void btnEditSelectedAction(ActionEvent event) throws IOException {
        ProductInfo selected = listViewSerializedMain.getSelectionModel().getSelectedItem();
        
        if (selected != null){
        lblEditMsg.setText("");
        EditViewController.id = selected.productID;
        App.setRoot("editView");
        } else { lblEditMsg.setText("Please select an item from list"); }
    }
    
    /**
     * Event handler for save button. Saves form into database
     * and clears form.
     * @param event 
     */
    @FXML
    private void btnSaveAdd(ActionEvent event) throws IOException, FileNotFoundException, ParseException {
        
        // New instance of ProductInfo.
        ProductInfo formInfo = new ProductInfo();
        
        // First try warranty. User must give integer input.
        boolean warrantyOk = false;
        try{
            formInfo.warranty = Integer.valueOf(textFieldWarranty.getText());
            warrantyOk = true;
        } catch (NumberFormatException ne) {
            lblAttWarranty.setVisible(true);
            }
        
        // Check that there is a name and serial number at least
        boolean productInfoOk = false;
        if ( textFieldProductName.getText() != null 
                && !textFieldProductName.getText().trim().isEmpty()
                && textFieldSerialNo.getText() != null
                && !textFieldSerialNo.getText().trim().isEmpty())
            {
                productInfoOk = true;
                lblAttProdInfo.setVisible(false);
            }
        else { lblAttProdInfo.setVisible( true ); }
        
        // If warranty months were given in integers and enough prod. info, proceed
        if (warrantyOk && productInfoOk)
        {
            
            formInfo.manufacturer = textFieldManufacturer.getText();
            formInfo.productNo = textFieldProductNo.getText();
            formInfo.serialNo = textFieldSerialNo.getText();
            formInfo.name = textFieldProductName.getText();


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

            
            
            try {
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
            lblAttWarranty.setVisible(false);
            
            } catch (SQLException ex){ 
             
            SQLExceptionHandler handler = new SQLExceptionHandler(ex, "mainView btnSaveAdd");
            handler.log(); 
        
        }
        }
        
    }
    
    @FXML
    private void cmbBoxLeaseAction(ActionEvent event) {

    }
    
    @FXML
    private void cmbBoxRoomAction(ActionEvent event) {

    }
    
    @FXML
    private void cmbBoxStorageAction(ActionEvent event) {

    }
    
    /**
     * Lease checkbox event handler. If checkbox is selected, enables
     * lease id combo box and gets leaseID list from database.
     * @param event
     */
    @FXML
    private void chkLeaseAddAction(ActionEvent event) throws IOException, FileNotFoundException, ParseException {
        try {
        if (chkLeaseAdd.isSelected()){
            cmbBoxLease.setDisable(false);
            
            ArrayList<String> leaseIdList = getLeaseIDlist();
            ObservableList <String> leaseIdObservableList = 
                    FXCollections.observableArrayList(leaseIdList);
            cmbBoxLease.setItems(leaseIdObservableList);
    
        }else if (!chkLeaseAdd.isSelected()) {
            cmbBoxLease.setDisable(true);
        }
        } catch (SQLException ex){ 
             
            SQLExceptionHandler handler = new SQLExceptionHandler(ex, "mainView chkLeaseAddAction");
            handler.log(); 
        
        }
    }
    
    /**
     * In production radio button event handler. If radio button is
     * selected, enables room combo cox and disable storage combo box.
     * Loads room ID list from database and adds it to room combo box.
     * @param event
     */	
    @FXML
    private void radInProductionAction(ActionEvent event) throws IOException, FileNotFoundException, ParseException {
    	try {
        if (radInProduction.isSelected()) {
    		cmbBoxStorage.setDisable(true);
    		cmbBoxRoom.setDisable(false);
                
                ArrayList<String> roomIdList = getRoomIDlist();
                ObservableList <String> roomIdObservableList = 
                    FXCollections.observableArrayList(roomIdList);
                cmbBoxRoom.setItems(roomIdObservableList);
    	}
        }catch (SQLException ex){ 
             
            SQLExceptionHandler handler = new SQLExceptionHandler(ex, "mainView radInProductionAction");
            handler.log(); 
        
        }
    }
    
    /**
     * In Storage radio button. If selected, enables storage position combo
     * box and loads storage positions from database to the combo box list.
     * @param event
     */
    @FXML
    private void radProductInStorageAction(ActionEvent event) throws IOException, FileNotFoundException, ParseException {
    	try {
        if (radInStorage.isSelected()) {
    		cmbBoxRoom.setDisable(true);
    		cmbBoxStorage.setDisable(false);
                
                ArrayList<String> storageIdList = getStorageIDlist();
                ObservableList <String> storageIdObservableList = 
                    FXCollections.observableArrayList(storageIdList);
                cmbBoxStorage.setItems(storageIdObservableList);
    	}
        } catch (SQLException ex){ 
             
            SQLExceptionHandler handler = new SQLExceptionHandler(ex, "mainView radProductInStorageAction");
            handler.log(); 
        
        }
    }
}