package kendokoodi.warehouseapplication;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.Initializable;
import static kendokoodi.warehouseapplication.dbOperations.MariaDB.*;
import kendokoodi.warehouseapplication.dbOperations.SerProdInfo;

/**
 * Controller for editView.
 * @author Mika.1.virtala@edu.karelia.fi
 */
public class EditViewController implements Initializable {
    
    /**
     * Selected productID to be edited.
     */
    public static int id;
    
    /**
     * Sets data from object to edit form.
     * @param record SerProdInfo object containing information.
     */
    public void bindData () throws SQLException{
        
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
    private ComboBox<?> cboLeaseId;
    @FXML
    private ComboBox<?> cboRoomId;
    @FXML
    private ComboBox<?> cboStoragePos;

    @FXML
    private void radLeaseAction(ActionEvent event) {
    }

    @FXML
    private void radOwnedAction(ActionEvent event) {
    }

    @FXML
    private void radInProductionAction(ActionEvent event) {
    }

    @FXML
    private void radInStorageAction(ActionEvent event) {
    }

    @FXML
    private void btnCloseAndSave(ActionEvent event) {
    }

    @FXML
    private void btnCloseWOsave(ActionEvent event) throws IOException {
        App.setRoot("mainView");
    }

    @FXML
    private void btnDelete(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            bindData();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(EditViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        
    
    }
}
