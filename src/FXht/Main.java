package FXht;
	
import java.util.Optional;

import ht.wt.WeatherTracker;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;


/**
 * @author Joonas Uusi-Autti & Sini Lällä
 * @version 17.1.2020
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    final FXMLLoader ldr = new FXMLLoader(getClass().getResource("paaikkuna.fxml"));
		    final Pane root = (Pane)ldr.load();
		    final PaaIkkunaController paaCtrl = (PaaIkkunaController)ldr.getController();
		            
			final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("ht.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("WeatherTracker");
			
            WeatherTracker weathertracker = new WeatherTracker();
            
			primaryStage.show();
			
			paaCtrl.setWeatherTracker(weathertracker);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
