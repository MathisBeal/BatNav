module org.xen.batnavgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jshell;


    opens org.xen.batnavgui to javafx.fxml;
    exports org.xen.batnavgui;
}