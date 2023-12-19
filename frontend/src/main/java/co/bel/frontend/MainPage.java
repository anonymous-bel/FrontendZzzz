package co.bel.frontend;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainPage extends Application {
	
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            
            // Set the controller for the FXML file
            MainController controller = loader.getController();

            // Create the initial scene with the loaded FXML content
            Scene initialScene = new Scene(root);

            // Set the initial scene for the stage
            primaryStage.setScene(initialScene);

            // Set the action for the button in the controller
            controller.loginButton.setOnAction(e -> {
            	if (controller.handlelogin()) {
                    openHomePage(primaryStage);
                    
                } else {
                    // Show an error message or handle the case where credentials are incorrect
                    System.out.println("Invalid credentials. Please try again.");
                }
            });

            primaryStage.setTitle("Login");
            primaryStage.show();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    

    public void openHomePage(Stage primaryStage) {
        
    	try {
            // Load the FXML file and get the root node
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Homepage.fxml"));
            Parent root = loader.load();
            HomepageController controller = loader.getController();

            controller.initialize(null, null);

            // Create a scene with the loaded root node
            Scene homePageScene = new Scene(root);
            
            // Set the second scene for the stage
            primaryStage.setScene(homePageScene);
            primaryStage.setTitle("E-shop");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }
}
