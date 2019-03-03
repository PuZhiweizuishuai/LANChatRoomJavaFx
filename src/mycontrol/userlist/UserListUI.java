package mycontrol.userlist;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class UserListUI extends FlowPane {
    @FXML
    private Label nameLabel;


    public UserListUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserListUI.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setNameLabel(String name) {
        textProperty().set(name);
    }


    public StringProperty textProperty() {
        return nameLabel.textProperty();
    }

}
