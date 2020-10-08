package ic20b106.board;

import javafx.beans.DefaultProperty;
import javafx.beans.NamedArg;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StageDragBox extends StackPane {

    public StageDragBox() {
        super();
        setDraggable();
    }

    public StageDragBox(Node... children) {
        super(children);
        setDraggable();
    }

    private void setDraggable() {
        this.setOnMousePressed(mouseEvent -> {
            if (dragScene != null && dragStage != null) {
                dragScene.setCursor(Cursor.CLOSED_HAND);
                deltaX = dragStage.getX() - mouseEvent.getScreenX();
                deltaY = dragStage.getY() - mouseEvent.getScreenY();
            }
        });

        this.setOnMouseReleased(mouseEvent -> {
            if (dragScene != null) {
                dragScene.setCursor(Cursor.HAND);
            }
        });

        this.setOnMouseDragged(mouseEvent -> {
            if (dragScene != null && dragStage != null) {
                double draggedX = mouseEvent.getScreenX() + deltaX;
                double draggedY = mouseEvent.getScreenY() + deltaY;

                Bounds dragBounds = Board.gameBoard.getScene().getRoot().localToScreen(
                  Board.gameBoard.getScene().getRoot().getBoundsInLocal()
                );

                if (dragBounds.contains(draggedX, draggedY, dragStage.getWidth(), dragStage.getHeight())) {
                    dragScene.setCursor(Cursor.CLOSED_HAND);
                    dragStage.setX(draggedX);
                    dragStage.setY(draggedY);
                }
            }
        });

        this.setOnMouseEntered(mouseEvent -> {
            if (dragScene == null) {
                dragScene = this.getScene();
            }
            if (dragStage == null) {
                dragStage = (Stage)dragScene.getWindow();
            }


            if (dragScene != null) {
                dragScene.setCursor(Cursor.HAND);
            }
        });

        this.setOnMouseExited(mouseEvent -> {
            if (dragScene != null) {
                dragScene.setCursor(Cursor.DEFAULT);
            }
        });
    }

    private double deltaX;
    private double deltaY;
    private Scene dragScene;
    private Stage dragStage;
}
