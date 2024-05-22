package Controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import Items.Items;
import Items.List_Items_Site;
import Items.List_Orderred;
import Items.OrderItem;
import Model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TaoDHMoiController implements Initializable {
	@FXML
	private TableView<Items> Site_table;

	@FXML
	private TableColumn<Items, String> merchandiseCode_col;

	@FXML
	private TableColumn<Items, String> nameItem_col;

	@FXML
	private TableColumn<Items, Integer> quantity_col;

	@FXML
	private TableColumn<Items, String> unit_col;

	@FXML
	private TableColumn<Items, String> describeItem_col;

	@FXML
	private TableView<OrderItem> order_table;

	@FXML
	private TableColumn<OrderItem, String> merchandiseCodeOrder_col;

	@FXML
	private TableColumn<OrderItem, String> nameItemOrder_col;

	@FXML
	private TableColumn<OrderItem, Integer> quantityOrder_col;

	@FXML
	private TableColumn<OrderItem, String> unitOrder_col;

	@FXML
	private TableColumn<OrderItem, Date> deliveryDateDesired_col;

	@FXML
	private TextField tenDH_textfield;

	@FXML
	private TextArea ghiChu_textfield;

	@FXML
	private Button taoDH_btn;

	@FXML
	private Button themMH_btn;

	@FXML
	private Button xoaMH_btn;

	@FXML
	private Button help_btn;

	@FXML
	private Button refresh_btn;

	private ObservableList<Items> itemsSite;

	private ObservableList<OrderItem> itemsOrder;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tenDH_textfield.setPromptText("Không được bỏ trống");
		ghiChu_textfield.setPromptText("Nhập ghi chú của đơn hàng (có thể bỏ trống)");

		taoDH_btn.setOnAction(event -> onTaoDH());
		themMH_btn.setOnAction(event -> onThemMH());
		xoaMH_btn.setOnAction(event -> onXoaMH());
		refresh_btn.setOnAction(event -> onRefresh());

		setSiteTable();

		themMH_btn.setDisable(true);
		// Add a listener to Site_table to handle selection changes
		Site_table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				handleItemSelected(newValue);
				themMH_btn.setDisable(newValue == null);
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void setSiteTable() {
		List_Items_Site list_Items_Sites = Model.getInstance().getTableSite();

		merchandiseCode_col.setCellValueFactory(new PropertyValueFactory<>("merchandiseCode"));
		nameItem_col.setCellValueFactory(new PropertyValueFactory<>("nameItem"));
		quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		unit_col.setCellValueFactory(new PropertyValueFactory<>("unit"));
		describeItem_col.setCellValueFactory(new PropertyValueFactory<>("describeItem"));

		Site_table.getColumns().clear();
		Site_table.getColumns().addAll(merchandiseCode_col, nameItem_col, quantity_col, unit_col, describeItem_col);

		itemsSite = FXCollections.observableArrayList(list_Items_Sites.getListItemsSite());
		Site_table.setItems(itemsSite);
	}

	private void onThemMH() {
		Stage themMHStage = Model.getInstance().getViewFactory().showThemMHScreen();

		themMHStage.setOnHidden(event -> {
			if (checkNotNull()) {
				addToOrderTemp();
				order_table.refresh();
			}

		});
	}

	@SuppressWarnings("unchecked")
	private void addToOrderTemp() {
		// Logic for adding item to order
		Model.getInstance().updateOrderTableDB();
		List_Orderred list_Ordered = Model.getInstance().getTableOrderByOrderId(1);

		merchandiseCodeOrder_col.setCellValueFactory(new PropertyValueFactory<>("merchandiseCode"));
		nameItemOrder_col.setCellValueFactory(new PropertyValueFactory<>("nameItem"));
		quantityOrder_col.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));
		unitOrder_col.setCellValueFactory(new PropertyValueFactory<>("unit"));
		deliveryDateDesired_col.setCellValueFactory(new PropertyValueFactory<>("deliveryDateDesired"));

		order_table.getColumns().clear();
		order_table.getColumns().addAll(merchandiseCodeOrder_col, nameItemOrder_col, quantityOrder_col, unitOrder_col,
				deliveryDateDesired_col);

		itemsOrder = FXCollections.observableArrayList(list_Ordered.getListOrdered());
		order_table.setItems(itemsOrder);
	}

	private void onTaoDH() {
		boolean validInput = true;

		// Kiểm tra nếu tên đơn hàng bỏ trống
		if (tenDH_textfield.getText().trim().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Lỗi");
			alert.setHeaderText(null);
			alert.setContentText("Tên đơn hàng không được bỏ trống!!!");
			alert.showAndWait();
			validInput = false; // Đặt cờ thành false nếu tên đơn hàng không hợp lệ
		}

		// Nếu nhập liệu hợp lệ, thực hiện logic tạo đơn hàng
		if (validInput) {
			// Logic for creating order
			Model.getInstance().getSelecteOrderItem().setNameOrder(tenDH_textfield.getText());
			Model.getInstance().getSelecteOrderItem().setNote(ghiChu_textfield.getText());
			Model.getInstance().updateNameForOrderTemp();

			Model.getInstance().addOrderTempToListOrders();
			Model.getInstance().resetOrderTemp();

			Model.getInstance().getViewFactory().refreshDanhSachDH();
			Model.getInstance().getViewFactory().getDVBHScreenSelectedMenuItemProperty().set("XemDH");
		}
	}


	private void onXoaMH() {
		// Logic for deleting item from order
	}

	private void onRefresh() {
		Model.getInstance().resetOrderTemp();
		Model.getInstance().getViewFactory().refreshTaoDHView();
		Model.getInstance().getViewFactory().getDVBHScreenSelectedMenuItemProperty().set("XemDH");
		Model.getInstance().getViewFactory().getDVBHScreenSelectedMenuItemProperty().set("TaoDH");
	}


	private void handleItemSelected(Items selectedItem) {
		// Handle the selected item
		System.out.println("Selected item: " + selectedItem.getNameItem() +"from tao DH");

		Model.getInstance().getSelecteOrderItem().setOrderId(1);
		Model.getInstance().getSelecteOrderItem().setMerchandiseCode(selectedItem.getMerchandiseCode());
		Model.getInstance().getSelecteOrderItem().setNameItem(selectedItem.getNameItem());
		Model.getInstance().getSelecteOrderItem().setQuantityOrdered(selectedItem.getQuantity());
		Model.getInstance().getSelecteOrderItem().setUnit(selectedItem.getUnit());

		// Additional logic to handle the selected item
	}

	private boolean checkNotNull() {
		if (Model.getInstance().getSelecteOrderItem().getDeliveryDateDesired() == null) {
			return false;
		}

		return true;
	}
}