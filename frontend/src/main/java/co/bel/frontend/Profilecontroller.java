package co.bel.frontend;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class Profilecontroller {

	    @FXML
	    private Label consumerIdLabel;

	    @FXML
	    private Label consumerNameLabel;

	    @FXML
	    private Label emailIdLabel;

	    @FXML
	    private Label passwordLabel;

	    @FXML
	    private Label phoneNoLabel;

	    public void setLabels(String consumerId, String consumerName, String emailId, String password, String phoneNo) {
	        consumerIdLabel.setText(consumerId);
	        consumerNameLabel.setText(consumerName);
	        emailIdLabel.setText(emailId);
	        passwordLabel.setText(password);
	        phoneNoLabel.setText(phoneNo);
	    }
	}


	


