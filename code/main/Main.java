package main;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/Login.fxml"));
		Parent login = loader.load();
		
		Controller controller = new Controller();
		controller.setLoginCon(loader.getController());
		controller.getLoginCon().setLoginForm(login);
		primaryStage.setTitle("PC방 Login");
		primaryStage.setScene(new Scene(login));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
