package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class TaskBarController implements Initializable{
	@FXML
    private Button DSSite_btn;

    @FXML
    private Button TaoDH_btn;

    @FXML
    private Button XemDH_btn;

    @FXML
    private Button logout_btn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		DSSite_btn.setOnAction(event -> onDSSite());
		TaoDH_btn.setOnAction(event -> onTaoDH());
		XemDH_btn.setOnAction(event -> onXemDH());
		logout_btn.setOnAction(event -> onLogout());
		
		DSSite_btn.setOnMousePressed(event -> {
            DSSite_btn.setStyle("-fx-background-color: #d1d1d1;");
            TaoDH_btn.setStyle("-fx-background-color: #f2f2f2;");
            XemDH_btn.setStyle("-fx-background-color: #f2f2f2;");
            logout_btn.setStyle("-fx-background-color: #f2f2f2;");
            DSSite_btn.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 3, 0, 2, 0);");
        });

		TaoDH_btn.setOnMousePressed(event -> {
            DSSite_btn.setStyle("-fx-background-color: #f2f2f2;");
            TaoDH_btn.setStyle("-fx-background-color: #d1d1d1;");
            XemDH_btn.setStyle("-fx-background-color: #f2f2f2;");
            logout_btn.setStyle("-fx-background-color: #f2f2f2;");
            TaoDH_btn.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 3, 0, 2, 0);");

        });
		
		XemDH_btn.setOnMousePressed(event -> {
            DSSite_btn.setStyle("-fx-background-color: #f2f2f2;");
            TaoDH_btn.setStyle("-fx-background-color: #f2f2f2;");
            XemDH_btn.setStyle("-fx-background-color: #d1d1d1;");
            logout_btn.setStyle("-fx-background-color: #f2f2f2;");
            XemDH_btn.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 3, 0, 2, 0);");
        });
		
		logout_btn.setOnMousePressed(event -> {
            DSSite_btn.setStyle("-fx-background-color: #f2f2f2;");
            TaoDH_btn.setStyle("-fx-background-color: #f2f2f2;");
            XemDH_btn.setStyle("-fx-background-color: #f2f2f2;");
            logout_btn.setStyle("-fx-background-color: #d1d1d1;");
            logout_btn.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 3, 0, 2, 0);");

        });
	}
	
	private void onDSSite() {
		Model.getInstance().getViewFactory().getDVBHScreenSelectedMenuItemProperty().set("DSSite");
	}
	
	private void onTaoDH() {
		Model.getInstance().getViewFactory().getDVBHScreenSelectedMenuItemProperty().set("TaoDH");
	}
	
	private void onXemDH() {
		Model.getInstance().getViewFactory().getDVBHScreenSelectedMenuItemProperty().set("XemDH");
	}
	
	private void onLogout() {
		Model.getInstance().getViewFactory().getDVBHScreenSelectedMenuItemProperty().set("LogOut");
	}
}