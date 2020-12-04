package ic20b106.client.util.javafx;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Draggable Pane
 */
public class DragBox extends Pane {

    private double deltaX;
    private double deltaY;

    /**
     * Constructor
     */
    public DragBox() {
        super();
    }

    /**
     * Constructor
     *
     * @param children Adds children Nodes
     */
    public DragBox(Node... children) {
        super(children);
    }

    /**
     * Changes Position of the target Node according to the
     * drag on the DragBox
     *
     * @param target Node to move when dragging
     */
    public void setDraggable(Node target) {
        this.setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                target.getScene().setCursor(Cursor.HAND);
            }
        });
        this.setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                target.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        this.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                target.getScene().setCursor(Cursor.DEFAULT);
            }
            deltaX = mouseEvent.getX();
            deltaY = mouseEvent.getY();
            target.getScene().setCursor(Cursor.CLOSED_HAND);
        });
        this.setOnMouseReleased(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                target.getScene().setCursor(Cursor.DEFAULT);
            }
        });
        this.setOnMouseDragged(mouseEvent -> {
            target.setTranslateX(target.getTranslateX() + mouseEvent.getX() - deltaX);
            target.setTranslateY(target.getTranslateY() + mouseEvent.getY() - deltaY);
        });
    }
}
