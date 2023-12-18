package co.bel.frontend;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
//    @FXML
//    private Label statusLabel1;
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
			// Parse JSON and create UI components for each item
			// Replace this part with actual JSON parsing logic based on your project
			// structure
			// Assuming there's a class Item representing each item
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

	private void createItemContainer(List<Item> items) {
		// Create UI components for each item
		 itemsContainer.getChildren().clear();

	        // Create UI components for each item
	        for (Item item : items) {
	            VBox itemContainer = new VBox();
	            itemContainer.setSpacing(5);

	            Spinner<Integer> quantitySpinner = new Spinner<>(1, 10, 1); // Example range and initial value
	            quantitySpinner.setStyle("-fx-font-size: 10px;");
	            quantitySpinner.setPrefWidth(50);
	            quantitySpinner.setPrefHeight(20);

	            Button addToCartButton = new Button("Add to Cart");

	            // Add item details, spinner, and button to the container
	            // Customize this part based on your actual item structure
	            itemContainer.getChildren().addAll(
	                    new Label(item.getItem_name()),  // Assuming you have a method getItemName() in your Item class
	                    new Label("₹ " + item.getItem_price()),    // Assuming you have a method getItemPrice() in your Item class
	                    quantitySpinner,
	                    addToCartButton
	            );

	            // Add event handlers for the "Add to Cart" button, if needed
	            addToCartButton.setOnAction(event -> {
	                // Handle adding to cart logic
	                System.out.println("Item ID: " + item.getItem_id() + ", Quantity: " + quantitySpinner.getValue());
	                
	                String apiUrl = "http://localhost:8080/ecom1/webapi/consumer/concon";
	    			String jsonInputString = "{\n" + "    \"phone_no\": \"" + namew  + "\"\n" + "}";
	    			String response = null;
					try {
						response = performPostRequest(apiUrl, jsonInputString);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			System.out.println(response);
	    			int res = Integer.parseInt(response);

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
	                String jsonInputString1 = "{\n" + 
	                        "    \"item_id\": \"" + item.getItem_id() + "\", \n" +
	                        "    \"consumer_id\": \"" + consumerId1 + "\",\n" +
	                        "    \"quantity\": " + quantitySpinner.getValue()+ "\n" +
	                        "}";
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
	    				if(responseCode == 204) {
	    					System.out.println("Quantity updated successfully.");
	    				}
	    				else {
	    					// Handle error response
	    					System.out.println("Error: " + responseCode);
	    				}
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    			}
	                
	            });

	            // Add the item container to the main container
	            itemsContainer.getChildren().add(itemContainer);
	        }
	    }
	
	@FXML
	private void onCartMenuClicked(ActionEvent event) {
		
		 try {
			 	System.out.println("Hello world");
		        // Assume you have a method to fetch data from the URL and parse it into a list of CartItems
		        List<CartItem> cartItems = fetchDataFromUrl("http://localhost:8080/ecom1/webapi/cart/");

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


//	@FXML
//	private void onOrderMenuClicked(ActionEvent event) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("Orderdetails.fxml"));
//			Parent root = loader.load();
//
//			// Create a new stage
//			Stage stage = new Stage();
//			stage.setTitle("Order Details");
//			stage.setScene(new Scene(root));
//
//			// Show the stage
//			stage.show();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	 private List<CartItem> fetchDataFromUrl(String url) {
	        try {
	            HttpClient httpClient = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create(url))
	                    .build();

	            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	            // Assuming CartItem is a class representing your JSON structure
	            ObjectMapper objectMapper = new ObjectMapper();
	            CartItem[] cartItemsArray = objectMapper.readValue(response.body(), CartItem[].class);

	            return Arrays.asList(cartItemsArray);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null; // Handle error appropriately in your application
	        }
	 }
	@FXML
	private void handleOrderMenuClick() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Orderdetails.fxml"));
			Parent root = loader.load();

			OrderController orderController = loader.getController();
			// Assuming you have the data, replace these placeholders with actual data
			orderController.initializeData("123", "456", "Item1, Item2", "2023-12-07 12:34:56", "$100.00");

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void getAccountDetails() {

		try {
			String apiUrl = "http://localhost:8080/ecom1/webapi/consumer/concon";
			String jsonInputString = "{\n" + "    \"phone_no\": \"" + namew  + "\"\n" + "}";
			String response = performPostRequest(apiUrl, jsonInputString);
			System.out.println(response);
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
}