package mycontrol.userlist;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import java.awt.Dimension;
import java.awt.Toolkit;

public class UserListUI extends FlowPane {
    @FXML
    private Label nameLabel;

    @FXML
    private ImageView headImageView;

    @FXML
    private JFXButton messageNumber;

    private String imagePath;

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


    public void setHeadImageView(String imagePath) {
        this.imagePath = imagePath;
        Image image = new Image(imagePath);
        headImageView.setImage(image);
    }

    public String getName() {
        return nameLabel.getText();
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setMessageNumber(String number) {
        messageNumber.setVisible(true);
        messageNumber.setText(number);
    }

    public void closeMessageNumber() {
        messageNumber.setVisible(false);
        messageNumber.setText("0");
    }
}
