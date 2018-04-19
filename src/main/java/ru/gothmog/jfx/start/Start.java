package ru.gothmog.jfx.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.gothmog.jfx.db.DBModel;

import java.io.IOException;

public class Start extends Application{

    final Logger logger = LoggerFactory.getLogger(Start.class);

    public Start(){
        DBModel model = new DBModel();
        model.createDataBase();
    }


    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Добро пожаловать в StoreKeeper -Login");
            primaryStage.getIcons().add(new Image("/image/icon.png"));
            primaryStage.setMaximized(false);
            primaryStage.setMaxHeight(500.0);
            primaryStage.setMinWidth(850.0);
            primaryStage.show();
        }catch (IOException ex){
            logger.error("not start " + ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
