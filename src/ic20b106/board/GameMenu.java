package ic20b106.board;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameMenu extends VBox {


    public GameMenu(Node ... children) {
        this(true, children);
    }

    public GameMenu(boolean draggable, Node ... children) {
        super(children);

        Button closeButton = new Button("X");

        // Building Menu Close Button
        closeButton.setOnMouseClicked(mouseEvent -> {
            Scene buildScene;
            Stage buildStage;
            if ((buildScene = this.getScene()) != null && (buildStage = (Stage)buildScene.getWindow()) != null) {
                buildStage.close();
            }
        });
        HBox optionsBar = new HBox(closeButton);

        optionsBar.setOnMousePressed(mouseEvent -> {
            Scene buildScene;
            Stage buildStage;
            if (this.draggable && (buildScene = this.getScene()) != null && (buildStage = (Stage)buildScene.getWindow()) != null) {
                deltaX = buildStage.getX() - mouseEvent.getScreenX();
                deltaY = buildStage.getY() - mouseEvent.getScreenY();
                buildScene.setCursor(Cursor.CLOSED_HAND);
            }
        });

        optionsBar.setOnMouseReleased(mouseEvent -> {
            Scene buildScene;
            if (this.draggable && (buildScene = this.getScene()) != null) {
                buildScene.setCursor(Cursor.HAND);
            }
        });

        optionsBar.setOnMouseDragged(mouseEvent -> {
            Scene buildScene;
            Stage buildStage;
            if (this.draggable && (buildScene = this.getScene()) != null && (buildStage = (Stage)buildScene.getWindow()) != null) {
                buildScene.setCursor(Cursor.CLOSED_HAND);
                buildStage.setX(mouseEvent.getScreenX() + deltaX);
                buildStage.setY(mouseEvent.getScreenY() + deltaY);
            }
        });

        optionsBar.setOnMouseEntered(mouseEvent -> {
            Scene buildScene;
            if (this.draggable && (buildScene = this.getScene()) != null) {
                buildScene.setCursor(Cursor.HAND);
            }
        });

        optionsBar.setOnMouseExited(mouseEvent -> {
            Scene buildScene;
            if (this.draggable && (buildScene = this.getScene()) != null) {
                buildScene.setCursor(Cursor.DEFAULT);
            }
        });

        this.getChildren().add(0, optionsBar);
        this.draggable = draggable;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public Cell getActivationCell() {
        return activationCell;
    }

    public void setActivationCell(Cell cell) {
        this.activationCell = cell;
    }

    private boolean draggable;
    private double deltaX = 0;
    private double deltaY = 0;
    private Cell activationCell;
}
