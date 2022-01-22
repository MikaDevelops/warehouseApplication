module kendokoodi.warehouseapplication {
    requires javafx.controls;
    requires javafx.fxml;

    opens kendokoodi.warehouseapplication to javafx.fxml;
    exports kendokoodi.warehouseapplication;
}
