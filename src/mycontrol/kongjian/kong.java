package mycontrol.kongjian;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import mycontrol.userlist.UserListUI;
import java.net.URL;
import java.util.ResourceBundle;
import mycontrol.test.CustomControl;

public class kong implements Initializable {
    @FXML
    private VBox root;

    @FXML
    private JFXListView<UserListUI> listJFX;
    @Override
    public void initialize(URL location, ResourceBundle resource) {
        UserListUI userListUI = new UserListUI();
        userListUI.setNameLabel("dfasd   ");
        listJFX.getItems().add(userListUI);
        /*ScrollBar sc = new ScrollBar();
        sc.setOrientation(Orientation.VERTICAL);

        for(int i = 0; i < 30; i++) {
            UserListUI userListUI = new UserListUI();
            userListUI.setNameLabel("dfasd   " + i);
            root.getChildren().add(userListUI);
        }

        CustomControl customControl = new CustomControl();
        customControl.setText("Hello World!");
        root.getChildren().add(customControl);
        root.getChildren().add(sc);*/

    }
}
