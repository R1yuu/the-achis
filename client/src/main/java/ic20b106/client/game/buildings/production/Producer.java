package ic20b106.client.game.buildings.production;

import ic20b106.client.Game;
import ic20b106.client.game.board.Cell;
import ic20b106.client.game.buildings.Building;
import ic20b106.client.game.buildings.storage.Storable;
import ic20b106.client.game.entities.Carrier;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public abstract class Producer extends Building {

    protected Thread productionThread = new Thread(this::produce);
    protected final ObservableMap<Storable, Integer> products = FXCollections.observableHashMap();

    /**
     * Constructor
     *
     * @param texture
     * @param buildingCost
     * @param storage
     * @param cell
     */
    protected Producer(Image texture, Map<Storable, Integer> buildingCost, HashMap<Storable, Integer> storage,
                       Cell cell) {
        super(texture, buildingCost, storage, cell);
        this.products.addListener(this::producedMaterialsChangeListener);
    }

    /**
     * Listens Produced Materials
     *
     * @param mapChange Changes in Map
     */
    protected void producedMaterialsChangeListener(
      MapChangeListener.Change<? extends Storable, ? extends Integer> mapChange) {
        if (mapChange.getValueAdded() > Objects.requireNonNullElse(mapChange.getValueRemoved(), 0)) {
            Carrier carrier = new Carrier(this.cell, Game.playerHQ.getCell(),
              this.products, Game.playerHQ.getStorage(), this.getStorableQueue());
            carrier.start();
        }
    }

    /**
     * Produces Storables
     */
    protected abstract void produce();

    /**
     * Returns Queue of Transferable Items
     *
     * @return Queue of Storables
     */
    @Override
    public Queue<Storable> getStorableQueue() {
        return Objects.requireNonNullElseGet(super.getStorableQueue(), () -> {
            final Queue<Storable> productQueue = new LinkedList<>();
            this.products.forEach((storable, amount) -> {
                for (int i = 0; i < amount; i++) {
                    productQueue.add(storable);
                }
            });
            return productQueue;
        });

    }

    /**
     * Listens Construction
     *
     * @param mapChange Changes in Map
     */
    @Override
    protected void constructionListener(MapChangeListener.Change<? extends Storable, ? extends Integer> mapChange) {
        super.constructionListener(mapChange);
        if (!this.isConstructionSite() && this.productionThread.getState() == Thread.State.NEW) {
            this.productionThread.start();
        }
    }

    /**
     * What happenes when Building gets Destroyed
     */
    @Override
    public void demolish() {
        this.productionThread.interrupt();
    }
}
