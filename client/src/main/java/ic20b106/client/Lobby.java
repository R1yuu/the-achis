package ic20b106.client;

import ic20b106.client.game.board.Cell;
import ic20b106.client.manager.NetworkManager;
import ic20b106.shared.PlayerProfile;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.rmi.RemoteException;
import java.util.List;

public class Lobby {

    @FXML
    private GridPane playerGridPane;


    @FXML
    private void initialize() {
        playerGridPane.getChildren().parallelStream().forEach((node -> GridPane.setHalignment(node, HPos.CENTER)));
        updateTable();
    }

    @FXML
    private void startGame() {

    }

    public void updateTable() {
        try {
            int rowIdx = 1;
            for (PlayerProfile playerProfile : NetworkManager.getInstance().serverStub.showRoom()) {
                for (Node child : playerGridPane.getChildren()) {
                    if(GridPane.getRowIndex(child) == rowIdx) {
                        int colIdx = GridPane.getColumnIndex(child);
                        switch (colIdx) {
                            case 0 -> ((Rectangle) child).setFill(playerProfile.color.toColor());
                            case 1 -> ((Label) child).setText(playerProfile.startPosition.toString());
                            case 2 -> ((CheckBox) child).setSelected(playerProfile.isReady);
                        }
                    }
                }
                rowIdx++;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
