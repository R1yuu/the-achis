package ic20b106.client.game.buildings.production;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.entities.Carrier;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Objects;

public abstract class Producer extends Building {

    protected Thread productionThread = new Thread(this::produce);
    protected final ObservableMap<Material, Integer> producedMaterials = FXCollections.observableHashMap();

    protected Producer(Image texture, HashMap<Material, Integer> buildingCost,
                       Cell cell) {
        super(texture, buildingCost, cell);
        this.producedMaterials.addListener((MapChangeListener<Material, Integer>) change -> {
            if (change.getValueAdded() >  Objects.requireNonNullElse(change.getValueRemoved(), 0)) {
                Carrier woodCarrier = new Carrier(this.cell, Game.playerHQ.getCell(),
                  this.producedMaterials, Game.playerHQ.getNeededMaterials());
                woodCarrier.start();
            }
        });
    }

    protected abstract void produce();

    @Override
    protected void constructionListener(ObservableValue<? extends Boolean> obsVal, Boolean oldVal, Boolean newVal) {
        if (!newVal && this.productionThread.getState() == Thread.State.NEW) {
            this.productionThread.start();
        }
    }

    @Override
    public void demolish() {
        this.productionThread.interrupt();
    }
}
