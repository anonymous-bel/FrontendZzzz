package co.bel.frontend;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.animation.ScaleTransition;
import javafx.util.Duration;


public class HomepageController implements Initializable {

	@FXML
	Button loginButton;

	@FXML
	private VBox itemsContainer;
	@FXML
	private MainPage mainPage;

	@FXML
	private TextField emailField;

	@FXML
	private TextField phnoField;

	@FXML
	private Button registerButton;

	@FXML
	private PasswordField confirmPasswordField;

	@FXML
	private Label passwordMatchLabel;

	@FXML
	private PasswordField passwordField;

	@FXML
	private TextField usernameField;

	@FXML
	private Button browseProductsButton;

	@FXML
	private Label statusLabel;

	@FXML
	private GridPane produtGrid;

	@FXML
	private Text consumerIdLabel;

	@FXML
	private Text orderIdLabel;

	@FXML
	private Text itemsLabel;

	@FXML
	private Text timestampLabel;

	@FXML
	private Text priceLabel;

	@FXML
	private int consumerId;

	@FXML
	private Label consumerIdLabel1;

	@FXML
	private Label consumerNameLabel;

	@FXML
	private TableView<Item> itemTableView;

	@FXML
	private Label emailIdLabel;

	@FXML
	private Label passwordLabel;

	@FXML
	private Label phoneNoLabel;

	public static String namew;

	public static String consumerId1;
	
	@FXML
    private ScrollPane scrollPane;

	public String getName() {
		MainController hmv = new MainController();
		namew = hmv.getnameString();
		return namew;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		namew = getName();
		String apiUrl = "http://localhost:8080/ecom1/webapi/item";
		String jsonResponse = sendGetRequest(apiUrl);

		if (jsonResponse != null) {
			
			List<Item> items = parseJsonResponse(jsonResponse);

			createItemContainer(items);

		} else {
			System.out.println("Failed to fetch JSON response from the API.");
		}
	}

	private String sendGetRequest(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Set the request method to GET
			connection.setRequestMethod("GET");

			// Set timeouts (optional)
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			// Get the response code
			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				// Read the response from the server
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;

				while ((line = reader.readLine()) != null) {
					response.append(line);
				}

				reader.close();
				return response.toString();
			} else {
				// Handle non-OK response codes
				System.out.println("GET request failed. Response Code: " + responseCode);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<Item> parseJsonResponse(String jsonResponse) {
		// Replace this part with actual JSON parsing logic based on your project
		// structure
		// Using Jackson library as an example
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode jsonNode = objectMapper.readTree(jsonResponse);
			return objectMapper.convertValue(jsonNode, new TypeReference<List<Item>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public int getConsumerId() {
		String apiUrl = "http://localhost:8080/ecom1/webapi/consumer/concon";
		String jsonInputString = "{\n" + "    \"phone_no\": \"" + namew + "\"\n" + "}";
		String response = null;
		try {
			response = performPostRequest(apiUrl, jsonInputString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(response);
		int res = Integer.parseInt(response);
		return res;
	}

	private void createItemContainer(List<Item> items) {
		
		// Create UI components for each item
		itemsContainer.getChildren().clear();
		
		// Create a single ScrollPane outside the loop
	    scrollPane.setPrefHeight(444);
	    scrollPane.setPrefWidth(600);

		// Create UI components for each item
		for (Item item : items) {
			VBox itemContainer = new VBox();
			itemContainer.setSpacing(10);
			
			HBox itemContainer12 = new HBox(); // Use HBox for horizontal layout
	        itemContainer12.setSpacing(10);

	        // Create an ImageView to display the item photo (assuming you have a method getPhotoUrl() in your Item class)
	        ImageView imageView = new ImageView();
	        String localImagePath = "file:///F:\\Zzz\\frontend\\src\\main\\java\\co\\bel\\frontend\\pexels.jpg"; // Replace this with the actual local file path
	        Image itemImage = new Image(localImagePath);
	        imageView.setImage(itemImage);
	        imageView.setFitHeight(400);
	        imageView.setFitWidth(150);
	        imageView.setPreserveRatio(true);
	        
	        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(1500), imageView);
	        scaleTransition.setFromX(0.5); // Initial scale factor in the X direction
	        scaleTransition.setFromY(0.5); // Initial scale factor in the Y direction
	        scaleTransition.setToX(1);   // Target scale factor in the X direction (adjust as needed)
	        scaleTransition.setToY(1);   // Target scale factor in the Y direction (adjust as needed)

	        // Set up the mouse event handlers
	        imageView.setOnMouseEntered(event -> {
	            // Play the scale transition when the mouse enters the ImageView
	            scaleTransition.playFromStart();
	        });

			Spinner<Integer> quantitySpinner = new Spinner<>(1, 10, 1); // Example range and initial value
			quantitySpinner.setStyle("-fx-font-size: 10px;");
			quantitySpinner.setPrefWidth(50);
			quantitySpinner.setPrefHeight(20);

			Button addToCartButton = new Button("Add to Cart");

			// Add item details, spinner, and button to the container
			// Customize this part based on your actual item structure
			itemContainer.getChildren().addAll(new Label(item.getItem_name()), // Assuming you have a method
																				// getItemName() in your Item class
					new Label("₹ " + item.getItem_price()), // Assuming you have a method getItemPrice() in your Item
															// class
					quantitySpinner, addToCartButton);

			// Add event handlers for the "Add to Cart" button, if needed
			addToCartButton.setOnAction(event -> {
				// Handle adding to cart logic
				System.out.println("Item ID: " + item.getItem_id() + ", Quantity: " + quantitySpinner.getValue());

				int res = getConsumerId();
				String new_Api = "http://localhost:8080/ecom1/webapi/consumer/getcon";
				String jString = "{\n" + "    \"consumer_id\": \"" + res + "\"\n" + "}";
				String rewspnse2 = null;
				try {
					rewspnse2 = performPostRequest(new_Api, jString);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				consumerId1 = parseJsonValue(rewspnse2, "consumer_id");

				String apiUrl1 = "http://localhost:8080/ecom1/webapi/cart/createcart";
				String jsonInputString1 = "{\n" + "    \"item_id\": \"" + item.getItem_id() + "\", \n"
						+ "    \"consumer_id\": \"" + consumerId1 + "\",\n" + "    \"quantity\": "
						+ quantitySpinner.getValue() + "\n" + "}";
				try {
					URL url = new URL(apiUrl1);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("POST");
					connection.setRequestProperty("Content-Type", "application/json");
					connection.setDoOutput(true);

					try (OutputStream os = connection.getOutputStream()) {
						byte[] input = jsonInputString1.getBytes(StandardCharsets.UTF_8);
						os.write(input, 0, input.length);
					}

					int responseCode = connection.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						System.out.println("Added to Cart successfully.");

					}
					if (responseCode == 204) {
						System.out.println("Quantity updated successfully.");
					} else {
						// Handle error response
						System.out.println("Error: " + responseCode);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			});
			// Set the VBox as the content of the ScrollPane
		    scrollPane.setContent(itemsContainer);
		    
			// Add the image and details container to the main container
	        itemContainer12.getChildren().addAll(imageView, itemContainer);

			// Add the item container to the main container
			itemsContainer.getChildren().add(itemContainer12);
		}
	}

	@FXML
	private void onCartMenuClicked(ActionEvent event) {

		try {
			// Assume you have a method to fetch data from the URL and parse it into a list
			// of CartItems
			List<CartItem> cartItems = fetchDataFromUrl("http://localhost:8080/ecom1/webapi/cart/uniqord",
					getConsumerId());
			FXMLLoader loader = new FXMLLoader(getClass().getResource("cart_ex.fxml"));
			Parent root = loader.load();
			CartItemController controller = loader.getController();
			controller.displayCartItems(cartItems);
			
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Cart Details");
			stage.show();
		} catch (IOException e) {
			System.err.println("Error loading cart_ex.fxml: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public List<CartItem> fetchDataFromUrl(String url, int consumerId) {
		List<CartItem> cartItems = new ArrayList<>();

		try {
			URL apiUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

			// Set the request method to POST
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			// Prepare the JSON input string
			String jsonInputString = "{\n" + "    \"consumer_id\": " + consumerId + "\n" + "}";

			// Write the JSON input string to the connection's output stream
			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			// Get the response code
			int responseCode = connection.getResponseCode();

			// Read the response or error stream
			try (BufferedReader in = new BufferedReader(
					new InputStreamReader(responseCode == HttpURLConnection.HTTP_OK ? connection.getInputStream()
							: connection.getErrorStream()))) {
				StringBuilder response = new StringBuilder();
				String line;

				while ((line = in.readLine()) != null) {
					response.append(line);
				}

				// Print the response for debugging purposes
				System.out.println(response);

				// Parse the JSON response and populate the list of CartItems
				// (You need to implement this parsing logic based on your JSON structure)
				cartItems = parseJsonResponse1(response.toString());
			}

			// Check the response code for success
			if (responseCode != HttpURLConnection.HTTP_OK) {
				System.out.println("POST request failed. Response Code: " + responseCode);
			}

			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cartItems;
	}

	private List<CartItem> parseJsonResponse1(String jsonResponse) {
		List<CartItem> cartItems = new ArrayList<>();

		try {
			JSONArray jsonArray = new JSONArray(jsonResponse);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject cartItemJson = jsonArray.getJSONObject(i);

				int cartId = cartItemJson.getInt("cart_id");

				JSONObject consumerJson = cartItemJson.getJSONObject("consumer");
				int consumerId = consumerJson.getInt("consumer_id");
				String consumerName = consumerJson.getString("consumer_name");
				String email = consumerJson.getString("email_id");
				String password = consumerJson.getString("password");
				String phoneNo = consumerJson.getString("phone_no");

				Consumer consumer = new Consumer(consumerId, consumerName, email, password, phoneNo);

				JSONObject itemJson = cartItemJson.getJSONObject("item");
				int itemId = itemJson.getInt("item_id");
				String itemName = itemJson.getString("item_name");
				double itemPrice = itemJson.getDouble("item_price");

				Item item = new Item(itemId, itemName, itemPrice);

				int quantity = cartItemJson.getInt("quantity");

				CartItem cartItem = new CartItem(cartId, consumer, item, quantity);
				cartItems.add(cartItem);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cartItems;
	}

	@FXML
	private void onOrderMenuClicked(ActionEvent event) {
		try {
			// Assume you have a method to fetch data from the URL and parse it into a list
			// of CartItems
			List<OrderItem> ordItems = fetchDataFromUrl1("http://localhost:8080/ecom1/webapi/order/checkout",
					getConsumerId());
			//System.out.println(ordItems);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Orderdetails.fxml"));
			Parent root = loader.load();

			OrderController controller = loader.getController();
			//System.out.println(ordItems);
			controller.displayOrderItems(ordItems);
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Order Details");
			stage.show();
		} catch (IOException e) {
			System.err.println("Error loading Orderdetails.fxml: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public List<OrderItem> fetchDataFromUrl1(String url, int consumerId) {
		List<OrderItem> ordItems = new ArrayList<>();

		try {
			URL apiUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

			// Set the request method to POST
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			// Prepare the JSON input string
			String jsonInputString = "{\n" + "    \"consumer_id\": " + consumerId + "\n" + "}";

			// Write the JSON input string to the connection's output stream
			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			// Get the response code
			int responseCode = connection.getResponseCode();

			// Read the response or error stream
			try (BufferedReader in = new BufferedReader(
					new InputStreamReader(responseCode == HttpURLConnection.HTTP_OK ? connection.getInputStream()
							: connection.getErrorStream()))) {
				StringBuilder response = new StringBuilder();
				String line;

				while ((line = in.readLine()) != null) {
					response.append(line);
				}

				// Print the response for debugging purposes
				System.out.println(response);

				// Parse the JSON response and populate the list of CartItems
				// (You need to implement this parsing logic based on your JSON structure)
				ordItems = parseJsonResponse12(response.toString());
			}

			// Check the response code for success
			if (responseCode != HttpURLConnection.HTTP_OK) {
				System.out.println("POST request failed. Response Code: " + responseCode);
			}

			connection.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ordItems;
	}

	private List<OrderItem> parseJsonResponse12(String jsonResponse) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<OrderItem> orderItemList = new ArrayList<>();

		try

		{
			JsonNode jsonNode = objectMapper.readTree(jsonResponse);

			if (jsonNode.isArray()) {
				for (JsonNode node : jsonNode) {
					OrderItem orderItem = objectMapper.treeToValue(node, OrderItem.class);
					orderItemList.add(orderItem);
				}
			}

		} catch (IOException e) {
			e.printStackTrace(); // Handle the exception according to your needs
		}

		return orderItemList;
	}

	@FXML
	public void getAccountDetails() {

		try {
			String apiUrl = "http://localhost:8080/ecom1/webapi/consumer/concon";
			String jsonInputString = "{\n" + "    \"phone_no\": \"" + namew + "\"\n" + "}";
			String response = performPostRequest(apiUrl, jsonInputString);
			int res = Integer.parseInt(response);

			String new_Api = "http://localhost:8080/ecom1/webapi/consumer/getcon";
			String jString = "{\n" + "    \"consumer_id\": \"" + res + "\"\n" + "}";
			String rewspnse2 = performPostRequest(new_Api, jString);

			consumerId1 = parseJsonValue(rewspnse2, "consumer_id");
			String consumerName = parseJsonValue(rewspnse2, "consumer_name");
			String emailId = parseJsonValue(rewspnse2, "email_id");
			String password = parseJsonValue(rewspnse2, "password");
			String phoneNo = parseJsonValue(rewspnse2, "phone_no");
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
				Parent root = loader.load();
				MainController controller = loader.getController();
				setLabels(controller, consumerId1, consumerName, emailId, password, phoneNo);
				Stage primaryStage = new Stage();
				primaryStage.setScene(new Scene(root));
				primaryStage.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			// Handle the exception as needed
		}
	}

	public void setLabels(MainController controller, String consumerId, String consumerName, String emailId,
			String password, String phoneNo) {

		controller.consumerIdLabel1.setText(consumerId);
		controller.consumerNameLabel.setText(consumerName);
		controller.emailIdLabel.setText(emailId);
		controller.passwordLabel.setText(password);
		controller.phoneNoLabel.setText(phoneNo);
	}

	private String performPostRequest(String apiUrl, String jsonInput) throws IOException {
		URL url = new URL(apiUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
			byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
			outputStream.write(input, 0, input.length);
		}

		int responseCode = connection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				StringBuilder response = new StringBuilder();
				String line;

				while ((line = reader.readLine()) != null) {
					response.append(line);
				}

				return response.toString().trim();
			}
		} else {
			System.out.println("Error: " + responseCode);
			throw new IOException("POST request failed. HTTP response code: " + responseCode);
		}
	}

	private String parseJsonValue(String json, String key) {
		int startIndex = json.indexOf("\"" + key + "\":") + key.length() + 3;
		int endIndex = json.indexOf(",", startIndex);
		if (endIndex == -1) {
			endIndex = json.indexOf("}", startIndex);
		}
		return json.substring(startIndex, endIndex).replace("\"", "");
	}
	@FXML
	public void sessionClose() {
		Platform.exit();
	}
	
	
	@FXML
    private MenuItem logoutMenuItem;

    @FXML
    private void handleLogout(ActionEvent event) {
        // Display a confirmation dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Confirm Logout");
        alert.setContentText("Are you sure you want to logout?");
        
        // Handle the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                // Get the Scene and Stage directly
                Scene scene = logoutMenuItem.getParentPopup().getOwnerWindow().getScene();
                Stage stage = (Stage) scene.getWindow();

                // Create a new instance of the main page
                MainPage newMainPage = new MainPage();

                // Close the current stage (window)
                stage.close();

                // Start the new instance of the application
                try {
                    newMainPage.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
}