package ic20b106;

import javafx.application .Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        GridPane constructionPane = new GridPane();

        Paint paint;

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

                // add(Node, column, row)
                constructionPane.add(field, j, i);
            }
        }


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(constructionPane, 1000, 1000));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
