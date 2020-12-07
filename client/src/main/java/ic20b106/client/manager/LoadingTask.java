package ic20b106.client.manager;

import ic20b106.client.Game;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * Task with an Loading Screen in the Background
 */
public class LoadingTask extends Task<Void> {

    private final Runnable runnable;

    /**
     * Constructor
     *
     * @param runnable Method to run in the Task
     */
    public LoadingTask(Runnable runnable) {
        this.runnable = runnable;
        this.setOnSucceeded(workerStateEvent -> Game.primaryPane.getChildren().remove(Game.loadingBox));
    }

    /**
     * Callable run at Threading
     *
     * @return returns Nothing (Void)
     */
    @Override
    protected Void call() {
        Platform.runLater(() -> Game.openLoadingScreen(progressProperty()));
        runnable.run();
        updateProgress(1, 1);
        return null;
    }

    /**
     * Starts the LoadingTask in a Thread
     */
    public void startInThread() {
        new Thread(this).start();
    }
}
