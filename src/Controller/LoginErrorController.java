package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginErrorController implements Initializable{
	@FXML
	private Button ok_btn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ok_btn.setOnAction(event -> onOk());
	}
	
	private void onOk() {
		Stage stage = (Stage) ok_btn.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
	}
	
}