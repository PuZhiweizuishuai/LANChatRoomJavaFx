package ChatMessage.Main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.awt.*;
import java.util.ResourceBundle;

public class MainUIControl implements Initializable {
    @FXML
    private GridPane rootPane;

    @FXML
    private ColumnConstraints rightCol;

    @FXML
    private ColumnConstraints leftCol;

    @FXML
    private RowConstraints topRow;

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        Dimension sceenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double sceenWidth = sceenSize.width * 0.8;
        double sceenHeight = sceenSize.height * 0.8;
        rootPane.setPrefWidth(sceenWidth);
        rootPane.setPrefHeight(sceenHeight);
        rightCol.setPercentWidth(sceenWidth * 0.65);
        leftCol.setPrefWidth(sceenWidth - (sceenWidth * 0.65));
        topRow.setPrefHeight(sceenHeight * 0.1);
        System.out.println(rootPane.getPrefHeight() + "         " + rootPane.getPrefWidth());
        System.out.println(rightCol.getPrefWidth() + "          " + leftCol.getPrefWidth());
        System.out.println(topRow.getPrefHeight());
    }

    @FXML
    public void test(ActiveEvent event) {
        System.out.println(rootPane.getHeight());
    }

}
