package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController implements Initializable{
    @FXML
    private Button exit_btn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		exit_btn.setOnAction(event -> onExit());
	}
    
	private void onExit() {
		Stage stage = (Stage) exit_btn.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
    
}