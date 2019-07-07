package controller;

import controller.EditController;
import mapping.ORM;
import model.User;
import model.UserProp;
import path.CFG;
import view.SceneChanger;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ListOfMembersController implements Initializable{
    ORM orm = new ORM();
    Alert alert = null;
    @FXML Button bback;
    @FXML VBox vb;
    @FXML VBox vb2;
    @FXML Button edit;
    @FXML Button delete;
    @FXML TextField tfsearch;
    @FXML ChoiceBox cbsearch;
    @FXML Button btnsearch;
    SceneChanger sch = new SceneChanger();
    TableView<UserProp> tv ;
    List userObjs;
    User user;
    List users;
    UserProp up;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tv = new TableView();
        users = new ArrayList<>();
        
        try {
            userObjs= orm.getUsers();
        } catch (SQLException ex) {
            Logger.getLogger(ListOfMembersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=0;i<userObjs.size();i++){
            up = (UserProp) userObjs.get(i);
            users.add(up);
        }
        cbsearch.getItems().add("id");        
        cbsearch.getItems().add("ime");
        cbsearch.getItems().add("prezime");
        
                final ObservableList<UserProp> data = FXCollections.observableList(users);
        tv.setItems(data);
        TableColumn<UserProp,String> tc_id = new TableColumn("ID");
        TableColumn<UserProp,String> tc_name = new TableColumn<>("Name");
        TableColumn<UserProp,String> tc_prename = new TableColumn<>("Prename");
        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc_prename.setCellValueFactory(new PropertyValueFactory<>("prename"));
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv.getColumns().add(tc_id);
        tv.getColumns().add(tc_name);
        tv.getColumns().add(tc_prename);
        tv.getVisibleLeafColumns();
        tv.setMinWidth(600);
        
        vb2.getChildren().add(tv);
        
        //napuniti tableview
    }
    public void back(ActionEvent event) throws IOException{
        String scena = "Main.fxml";
        sch.change(scena, event);
    }
    public void edit(ActionEvent event) throws IOException, InterruptedException{
        File file1 = new File(CFG.path+"Edit.fxml");
        URI uri1 = file1.toURI();
        URL url1 = uri1.toURL();
        FXMLLoader loader = new FXMLLoader(url1);
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                File file = new File(CFG.path + "style.css");
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
        int id  = tablePosition();
        List list = new ArrayList();
        list.add(id);
        EditController edc = loader.getController();
        edc.getParameter(list);
    }
    public void delete(ActionEvent event) throws SQLException{
        try{
        int id = tablePosition();
        orm.deleteUser(id);
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("");
        alert.setContentText("Uspešno izbrisan član!");
        alert.setTitle("Potvrda");
        alert.show();
        }catch(Exception ex){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("");
            alert.setContentText("Brisanje nije uspelo. Pokušajte kasnije.");
            alert.setTitle("Greška");
            alert.show();
        }
        
    }
    public int tablePosition(){
        TablePosition pos = null;
        int id = 0;
        try{
            id = (int) tv.getSelectionModel().getFocusedIndex();
//        pos = (TablePosition) tv.getSelectionModel().getSelectedCells().get(0);
        }catch(Exception ex){
            ex.printStackTrace();
        }
//        int row = pos.getRow();
        int dataint = 0;
        UserProp userprop = (UserProp) tv.getItems().get(id);
        
        try{
        dataint = userprop.getId();
        }catch(Exception ex){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Pritisnite na broj clana");
        alert.show();
        }
        return dataint;
    }
    public void search(ActionEvent event){
      String searchBy = (String) cbsearch.getValue();
      String parameter = (String) tfsearch.getText();
      tv.getItems().clear();
              try {
            userObjs = orm.search(searchBy, parameter);
        } catch (SQLException ex) {
            Logger.getLogger(ListOfMembersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=0;i<userObjs.size();i++){
            user = (User) userObjs.get(i);
            up = new UserProp(user);
            users.add(up);
        }
        final ObservableList<UserProp> data = FXCollections.observableList(users);
        tv.setItems(data);
    }
}
