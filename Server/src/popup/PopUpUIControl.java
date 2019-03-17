package popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Pu Zhiwei
 * */
public class PopUpUIControl implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    public Label titleLab;

    @FXML
    public Label textLab;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        Dimension sceneSize = Toolkit.getDefaultToolkit().getScreenSize();
        rootPane.setPrefSize(sceneSize.width * 0.2, sceneSize.height * 0.2);

    }

    public void setText(String title, String text) {
        titleLab.setText(title);
        textLab.setText(text);
    }



    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
}
