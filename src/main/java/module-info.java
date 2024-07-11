module org.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens org.example.demo1 to javafx.fxml;
    exports org.example.demo1;
}