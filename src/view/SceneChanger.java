package view;

import path.CFG;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
//this class is used for easier changing scenes
public class SceneChanger{
    
    public void change(String scena, ActionEvent evt) throws IOException{
        File file1 = new File(CFG.path+scena);
        URI uri1 = file1.toURI();
        URL url1 = uri1.toURL();
                Parent tableViewParent = FXMLLoader.load(getClass().getResource(scena));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)evt.getSource()).getScene().getWindow();
        File file = new File(CFG.path+"style.css");
        URI uri = file.toURI();
        URL url = uri.toURL();
        tableViewScene.getStylesheets().addAll(url.toExternalForm());
        
            Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
        window.setX(bounds.getMinX());
        window.setY(bounds.getMinY());
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());
        window.setMaximized(true);
        window.setScene(tableViewScene);
        window.show();
    }
}
