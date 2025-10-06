module uit.gon.gribouille {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
	requires java.desktop;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.swing;

    opens uit.gon.gribouille.controleurs to javafx.fxml;
    exports uit.gon.gribouille;
}
