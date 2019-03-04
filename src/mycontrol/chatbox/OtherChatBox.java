package mycontrol.chatbox;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class OtherChatBox extends HBox {
    @FXML
    private ImageView otherHeadImage;

    @FXML
    private Label otherMessage;

    public OtherChatBox() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OtherChatBoxUI.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMessage(String name) {
        textProperty().set(name);
    }


    private StringProperty textProperty() {
        return otherMessage.textProperty();
    }

    public void setHeadImageView(String imagePath) {
        Image image = new Image(imagePath);
        otherHeadImage.setImage(image);
    }

}
