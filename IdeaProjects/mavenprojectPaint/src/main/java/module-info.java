module com.mycompany.mavenprojectpaint {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.mavenprojectpaint to javafx.fxml;
    exports com.mycompany.mavenprojectpaint;
}
