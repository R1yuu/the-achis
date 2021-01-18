package ic20b106.client.game.entities;

import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Material;
import javafx.animation.Animation;
import javafx.event.ActionEvent;

import java.util.Map;

public class Builder extends Carrier {
    /**
     * Constructor
     *
     * @param from   Start Node
     * @param to     End Node
     * @param source
     * @param target
     */
    public Builder(Cell from, Cell to, Map<Material, Integer> source, Map<Material, Integer> target) {
        super(from, to, source, target);
    }

    @Override
    protected void taskCompleted() {
        if (this.reverse && !this.transIter.hasPrevious() && this.walkPath.getStatus() == Animation.Status.STOPPED) {
            System.out.println("BUILDER");
            this.reverse = false;
            this.prepareCargo();
        }
    }
}
