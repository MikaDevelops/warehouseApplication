module kendokoodi.warehouseapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens kendokoodi.warehouseapplication to javafx.fxml;
    exports kendokoodi.warehouseapplication;
}
