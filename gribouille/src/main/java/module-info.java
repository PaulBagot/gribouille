module uit.gon.gribouille {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
	requires java.desktop;
	requires javafx.graphics;
	requires javafx.base;

    opens uit.gon.gribouille to javafx.fxml;
    exports uit.gon.gribouille;
}
