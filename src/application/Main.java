package application;
	
import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			FXMLLoader loader = new FXMLLoader();
//			loader.setLocation(getClass().getResource("MainWindow.fxml"));
			GameController controller = new GameController(primaryStage);
			Pane root = controller.board;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Shlomi Fridman - Pyramid Solitaire");
			primaryStage.setX(50);
			primaryStage.setY(50);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
