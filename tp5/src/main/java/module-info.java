module com.example.tp5 {
  requires javafx.controls;
  requires javafx.fxml;

  exports fr.unicaen.iut.tp5;
  opens fr.unicaen.iut.tp5 to javafx.fxml;
}