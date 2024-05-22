package Controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import Items.List_Orderred;
import Items.OrderItem;
import Model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DSMHController implements Initializable {
	@FXML
	private Button View_btn;

	@FXML
	private Button back_btn;

	@FXML
	private Button help;

	@FXML
	private TableView<OrderItem> table;

	@FXML
	private TableColumn<OrderItem, Integer> merchadiseCode_col;

	@FXML
	private TableColumn<OrderItem, String> nameItem_col;

	@FXML
	private TableColumn<OrderItem, Integer> quantityOrdered_col;

	@FXML
	private TableColumn<OrderItem, String> unit_col;

	@FXML
	private TableColumn<OrderItem, Date> deliveryDateDesired_col;

	private ObservableList<OrderItem> itemsOrder;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setTable();
		back_btn.setOnAction(event -> onBack());
	}
	
	@SuppressWarnings("unchecked")
	private void setTable() {
		List_Orderred list_Ordered = Model.getInstance().getTableOrderByOrderId(Model.getInstance().getSelecteOrderItem().getOrderId());

		merchadiseCode_col.setCellValueFactory(new PropertyValueFactory<>("merchandiseCode"));
		nameItem_col.setCellValueFactory(new PropertyValueFactory<>("nameItem"));
		quantityOrdered_col.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));
		unit_col.setCellValueFactory(new PropertyValueFactory<>("unit"));
		deliveryDateDesired_col.setCellValueFactory(new PropertyValueFactory<>("deliveryDateDesired"));

		table.getColumns().clear();
		table.getColumns().addAll(merchadiseCode_col, nameItem_col, quantityOrdered_col, unit_col,
				deliveryDateDesired_col);

		itemsOrder = FXCollections.observableArrayList(list_Ordered.getListOrdered());
		table.setItems(itemsOrder);
	}
	
	private void onBack() {
		Model.getInstance().getViewFactory().getDVBHScreenSelectedMenuItemProperty().set("XemDH");
	}

}