package main;

import path.CFG;
import java.io.File;
import java.net.URI;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Gym extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        //class CFG and it's methods are used for getting files from different packages.
        File file1 = new File(CFG.path+"Main.fxml");
        URI uri1 = file1.toURI();
        URL url1 = uri1.toURL();
        Parent root = FXMLLoader.load(url1);
        
        root.setId("Main");
        Scene scene = new Scene(root);
        File file = new File(CFG.path+"style.css");
        URI uri = file.toURI();
        URL url = uri.toURL();
        scene.getStylesheets().addAll(url.toExternalForm());
        stage.setScene(scene);
        stage.show();


        stage.setMaximized(true);
    }

    public static void main(String[] args) {
        
        launch(args);
        
    }
    
}
