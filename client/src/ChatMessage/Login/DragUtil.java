package ChatMessage.Login;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
/**
 * 窗口移动工具
 * 窗体拉伸属性
 * @author Pu Zhiwei
 * */

public class DragUtil {

    /**
     * 是否处于右边界调整窗口状态
     * */
    private static boolean isRight;
    /**
     * 是否处于右下角调整窗口状态
     * */
    private static boolean isBottomRight;
    /**
     * 是否处于下边界调整窗口状态
     * */
    private static boolean isBottom;
    /**
     * 判定是否为调整窗口状态的范围与边界距离
     * */
    private final static int RESIZE_WIDTH = 5;
    /**
     * 窗口最小宽度
     * */
    private final static double MIN_WIDTH = 960;
    /**
     * 窗口最小高度
     * */
    private final static double MIN_HEIGHT = 640;

    public static void addDragListener(Stage stage, Node root) {
        new DragListener(stage).enableDrag(root);
    }



    public static void addDrawFunc(Stage stage,Parent root) {


        root.setOnMouseMoved((MouseEvent event) -> {
            event.consume();
            double x = event.getSceneX();
            double y = event.getSceneY();
            double width = stage.getWidth();
            double height = stage.getHeight();
            // 鼠标光标初始为默认类型，若未进入调整窗口状态，保持默认类型
            Cursor cursorType = Cursor.DEFAULT;
            // 先将所有调整窗口状态重置
            isRight = isBottomRight = isBottom = false;
            // 左下角调整窗口状态
            if (y >= height - RESIZE_WIDTH) {
                if (x <= RESIZE_WIDTH) {
                    // 右下角调整窗口状态
                } else if (x >= width - RESIZE_WIDTH) {
                    isBottomRight = true;
                    cursorType = Cursor.SE_RESIZE;
                } else {// 下边界调整窗口状态
                    isBottom = true;
                    cursorType = Cursor.S_RESIZE;
                }
            } else if (x >= width - RESIZE_WIDTH) {
                // 右边界调整窗口状态
                isRight = true;
                cursorType = Cursor.E_RESIZE;
            }
            // 最后改变鼠标光标
            root.setCursor(cursorType);
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            // 保存窗口改变后的x、y坐标和宽度、高度，用于预判是否会小于最小宽度、最小高度
            double nextX = stage.getX();
            double nextY = stage.getY();
            double nextWidth = stage.getWidth();
            double nextHeight = stage.getHeight();
            // 所有右边调整窗口状态
            if (isRight || isBottomRight) {
                nextWidth = x;
            }
            // 所有下边调整窗口状态
            if (isBottomRight || isBottom) {
                nextHeight = y;
            }
            // 如果窗口改变后的宽度小于最小宽度，则宽度调整到最小宽度
            if (nextWidth <= MIN_WIDTH) {
                nextWidth = MIN_WIDTH;
            }
            // 如果窗口改变后的高度小于最小高度，则高度调整到最小高度
            if (nextHeight <= MIN_HEIGHT) {
                nextHeight = MIN_HEIGHT;
            }
            // 最后统一改变窗口的x、y坐标和宽度、高度，可以防止刷新频繁出现的屏闪情况
            stage.setX(nextX);
            stage.setY(nextY);
            stage.setWidth(nextWidth);
            stage.setHeight(nextHeight);
        });
    }

}
