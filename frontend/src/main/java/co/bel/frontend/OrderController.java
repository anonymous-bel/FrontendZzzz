package co.bel.frontend;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class OrderController {
	
	@FXML
    private GridPane ordGridPane;

	@FXML
    private ScrollPane scrollPane;

	public void displayOrderItems(List<OrderItem> ordItems) {
		// TODO Auto-generated method stub
		int rowIndex = 1; // Start from row 1 as row 0 is for column headers
	      
        for (OrderItem ord : ordItems) {
            addTextToGrid(String.valueOf(ord.getOrder().getOrder_id()), 0, rowIndex);
            addTextToGrid(String.valueOf(ord.getItem_id().getItem_name()), 1, rowIndex);
            addTextToGrid(String.valueOf(ord.getQuantity()), 2, rowIndex);
            addTextToGrid("₹ " + String.valueOf(ord.getItem_id().getItem_price()), 3, rowIndex);
            addTextToGrid("₹ " + String.valueOf(ord.getQuantity() * ord.getItem_id().getItem_price()), 4, rowIndex);

            rowIndex++;
        }

	}
	
	private void addTextToGrid(String text, int columnIndex, int rowIndex) {
        Text newText = new Text(text);
        newText.wrappingWidthProperty().setValue(100); // Adjust the wrapping width as needed
        ordGridPane.add(newText, columnIndex, rowIndex);
        System.out.println("Text added to GridPane: " + text + ", Column: " + columnIndex + ", Row: " + rowIndex);
    }
}
