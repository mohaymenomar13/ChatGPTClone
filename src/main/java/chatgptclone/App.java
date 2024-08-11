package chatgptclone;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
 
public class App extends Application {

  private static Stage primaryStage;
  
  private double xOffset = 0;
  private double yOffset = 0;
    @Override
    public void start(Stage primaryStage) throws Exception {
      
      App.primaryStage = primaryStage;
      MainSceneController.loadData();

      String window = "loginUsername.fxml";
      Parent root = FXMLLoader.load(getClass().getResource(window));
      Scene scene = new Scene(root);

      primaryStage.initStyle(StageStyle.DECORATED.UNDECORATED);
      
      if (window != "mainPage.fxml") {
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
            System.out.println(xOffset + " " + yOffset);
          }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
          }
        });
      }
      
      primaryStage.setTitle("Hello World!");
      primaryStage.setScene(scene);
      primaryStage.show();
    }
 
  public static void setRoot(String fxml) throws Exception {
    System.out.println("Switching to the scene: "+fxml);
    Parent root = FXMLLoader.load(App.class.getResource(fxml+".fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
    if (fxml == "mainPage") {
      primaryStage.setFullScreen(true);
    }
  }
 public static void main(String[] args) {
      launch(args);
    }
}