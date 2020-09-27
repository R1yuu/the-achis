package ic20b106;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Andre Schneider
 * @version 1.0
 *
 * A The Settlers 1/2 and Achikaps inspired Game
 * The Settlers: https://de.wikipedia.org/wiki/Die_Siedler
 * Achikaps: https://achikaps.fandom.com/wiki/Achikaps_Wiki
 */
public class Main extends Application {

    /**
     * Start Method of JavaFX
     *
     * @param primaryStage Main Window of the Game
     * @throws IOException Thrown by FXMLLoader if .fxml not found
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        VBox root = FXMLLoader.load(getClass().getResource("menus/MainMenu.fxml"));

        /*
        GridPane constructionPane = new GridPane();

        Paint paint;
        Random rdm = new Random();
        int rdmInt = Math.abs(rdm.nextInt() % 10);

        System.out.println(rdmInt);

        for (int i=0; i < 10; i++) {
            for (int j=0; j < 10; j++) {

                if ((j + i) % 2 == 0) {
                    paint = new Color(1,0,0, 1);
                } else {
                    paint = new Color(0,1,0, 1);
                }

                Rectangle field = new Rectangle();
                field.setHeight(50);
                field.setWidth(50);
                field.setFill(paint);

                if (i % 2 == 1 && j == 0) {
                    Insets in = new Insets(0, 0, 0, 25);
                    GridPane.setMargin(field, in);
                } else if (i % 2 == 0 && j > 0) {
                    Insets in = new Insets(0, 0, 0, -25);
                    GridPane.setMargin(field, in);
                }

                if (i == rdmInt && j == rdmInt) {
                    Image img = new Image("/buildings/core.png", 50, 0, true, false, true);
                    ImageView imgView = new ImageView();
                    imgView.setFitWidth(50);
                    imgView.setFitHeight(50);
                    imgView.setImage(img);

                    constructionPane.add(imgView, j, i);
                } else {
                    // add(Node, column, row)
                    constructionPane.add(field, j, i);
                }


            }
        }

        Circle circle = new Circle();
        circle.setRadius(25);
        circle.setFill(new Color(0, 0, 1, 1));
        constructionPane.add(circle, 11, 11);
        */

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.show();
    }


    /**
     * Main Method of Program
     *
     * @param args Console Parameters
     */
    public static void main(String[] args) {
        launch(args);
    }
}
