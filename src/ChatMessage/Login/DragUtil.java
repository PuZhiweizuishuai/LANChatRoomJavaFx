package ChatMessage.Login;

import javafx.stage.Stage;
import javafx.scene.Node;
/**
 * 窗口移动工具
 * @author Pu Zhiwei
 * */

public class DragUtil {
    public static void addDragListener(Stage stage, Node root) {
        new DragListener(stage).enableDrag(root);
    }
}
