package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DVBHScreenController implements Initializable {

	@FXML
	private BorderPane DVBH_parent;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		Model.getInstance().getViewFactory().getDVBHScreenSelectedMenuItemProperty()
				.addListener((observableValue, oldVal, newVal) -> {
					switch (newVal) {

					case "DSSite" -> DVBH_parent.setCenter(Model.getInstance().getViewFactory().getDSSiteView());
					
					case "TaoDH" -> DVBH_parent.setCenter(Model.getInstance().getViewFactory().getTaoDHView());

					case "XemDH" -> DVBH_parent.setCenter(Model.getInstance().getViewFactory().getDanhSachDHView());
					
					case "XemDSMH" -> DVBH_parent.setCenter(Model.getInstance().getViewFactory().getdSMHView());

					case "SuaDH" -> DVBH_parent.setCenter(Model.getInstance().getViewFactory().getSuaDHView());
					
					case "LogOut" -> { 
						Stage stage = (Stage) DVBH_parent.getScene().getWindow();
						Model.getInstance().getViewFactory().showLoginScreen();
						Model.getInstance().getViewFactory().closeStage(stage);
					}

					default -> DVBH_parent.setCenter(Model.getInstance().getViewFactory().getTaoDHView());

					}

				});
	}
	
}