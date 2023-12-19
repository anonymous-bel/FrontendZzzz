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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class CartItemController {

    @FXML
    private GridPane cartGridPane;

    // Other existing methods...
    @FXML Label statusLabel;
    
    public static String namew;
    
    public String getName() {
		MainController hmv = new MainController();
		namew = hmv.getnameString();
		return namew;
	}

    public void displayCartItems(List<CartItem> cartItems) throws IOException {
        int rowIndex = 1; // Start from row 1 as row 0 is for column headers
      
        for (CartItem cartItem : cartItems) {
            addTextToGrid(cartItem.getItem().getItem_name(), 0, rowIndex);
            addTextToGrid(String.valueOf(cartItem.getQuantity()), 1, rowIndex);
            addTextToGrid("₹ " + String.valueOf(cartItem.getItem().getItem_price()), 2, rowIndex);
            addTextToGrid("₹ " + String.valueOf(cartItem.getQuantity() * cartItem.getItem().getItem_price()), 3, rowIndex);

            rowIndex++;
        }

        // Calculate overall price and display it
        double overallPrice = cartItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getItem().getItem_price())
                .sum();
        addTextToGrid("Overall Price: ", 2, rowIndex);
        addTextToGrid("₹ " + String.valueOf(overallPrice), 3, rowIndex);
    }

    private void addTextToGrid(String text, int columnIndex, int rowIndex) {
        Text newText = new Text(text);
        newText.wrappingWidthProperty().setValue(100); // Adjust the wrapping width as needed
        cartGridPane.add(newText, columnIndex, rowIndex);
        System.out.println("Text added to GridPane: " + text + ", Column: " + columnIndex + ", Row: " + rowIndex);
    }
    
    
    public int getConsumerId() {
		String apiUrl = "http://localhost:8080/ecom1/webapi/consumer/concon";
		namew = getName();
		String jsonInputString = "{\n" + "    \"phone_no\": \"" + namew + "\"\n" + "}";
		String response = null;
		try {
			response = performPostRequest(apiUrl, jsonInputString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int res = Integer.parseInt(response);
		return res;
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

	@FXML
	private void handleCheckoutClicked(ActionEvent event) throws IOException {
		
		List<CartItem> cartItems = fetchDataFromUrl("http://localhost:8080/ecom1/webapi/cart/uniqord",
				getConsumerId());
		
		Stage currentStage = null;
		
		javafx.collections.ObservableList<javafx.stage.Window> stageList = javafx.stage.Window.getWindows();

		for (javafx.stage.Window window : stageList) {
			if (window instanceof Stage) {
				// Handle the found stage
				currentStage = (Stage) window;
				System.out.println("Current Stage Title: " + currentStage.getTitle());
			}
		}
		
		if(!cartItems.isEmpty()) {
			int consumerId = getConsumerId();

			// API endpoint
			String apiUrl = "http://localhost:8080/ecom1/webapi/order/createorditem";

			// Prepare the JSON input string
			String jsonInputString = "{\n" + "    \"consumer_id\": " + consumerId + "\n" + "}";

			// Make the POST request
			try {
				URL url = new URL(apiUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				// Set the request method to POST
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setDoOutput(true);

				// Write the JSON input string to the connection's output stream
				try (OutputStream os = connection.getOutputStream()) {
					byte[] input = jsonInputString.getBytes("utf-8");
					os.write(input, 0, input.length);
				}

				// Get the response code
				int responseCode = connection.getResponseCode();

				if(responseCode == HttpURLConnection.HTTP_OK) {
					currentStage.close();
				}
//				System.out.println(responseCode);
				System.out.println("Order Placed Successfully.");
				
				connection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			currentStage.close();
			System.out.println("Cart is empty!!!");
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
    
}
