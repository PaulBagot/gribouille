module uit.gon.gribouille {
    requires javafx.controls;
    requires javafx.fxml;

    opens uit.gon.gribouille to javafx.fxml;
    exports uit.gon.gribouille;
}
