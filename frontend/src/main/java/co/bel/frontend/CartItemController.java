package co.bel.frontend;
import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
public class CartItemController {

    @FXML
    private GridPane cartGridPane;

    // Other existing methods...

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
}
