package Controller;

import Items.Items;
import Items.List_Orderred;
import Items.OrderItem;
import Model.Model;
import com.sun.webkit.Timer.Mode;
import java.sql.Date;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SuaDHController implements Initializable {
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

  @FXML
  private Button changeMH_btn;

  @FXML
  private Button save_btn;

  private ObservableList<OrderItem> itemsOrder;

  private HashMap<String, OrderItem> orderItemMap = new HashMap<>();

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    setTable();
    back_btn.setOnAction(event -> onBack());
    changeMH_btn.setOnAction(event -> onChangeMH());
    save_btn.setOnAction(event -> onSaveData());

    // Add a listener to table to handle selection changes
    table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        handleItemSelected(newValue);
      }
    });
  }

  private void setTable() {
    List_Orderred list_Ordered = Model.getInstance().getTableOrderByOrderId(Model.getInstance().getSelecteOrderItem().getOrderId());
    orderItemMap.clear();
    for(OrderItem item : list_Ordered.getListOrdered()) {
      orderItemMap.put(item.getMerchandiseCode(), item);
    }
    //cap nhat tablevieww tu danh sach mat hang trong hashmap
    itemsOrder = FXCollections.observableArrayList(orderItemMap.values());
    table.setItems(itemsOrder);

    merchadiseCode_col.setCellValueFactory(new PropertyValueFactory<>("merchandiseCode"));
    nameItem_col.setCellValueFactory(new PropertyValueFactory<>("nameItem"));
    quantityOrdered_col.setCellValueFactory(new PropertyValueFactory<>("quantityOrdered"));
    unit_col.setCellValueFactory(new PropertyValueFactory<>("unit"));
    deliveryDateDesired_col.setCellValueFactory(new PropertyValueFactory<>("deliveryDateDesired"));

    table.getColumns().clear();
    table.getColumns().addAll(merchadiseCode_col, nameItem_col, quantityOrdered_col, unit_col, deliveryDateDesired_col);

  }

  private void handleItemSelected(OrderItem selectedItem) {
    System.out.println("Selected item: " + selectedItem.getNameItem() + " from Sua DH");

    Model.getInstance().getSelecteOrderItem().setOrderId(selectedItem.getOrderId());
    Model.getInstance().getSelecteOrderItem().setMerchandiseCode(selectedItem.getMerchandiseCode());
    Model.getInstance().getSelecteOrderItem().setNameItem(selectedItem.getNameItem());
    Model.getInstance().getSelecteOrderItem().setQuantityOrdered(selectedItem.getQuantityOrdered());
    Model.getInstance().getSelecteOrderItem().setUnit(selectedItem.getUnit());
    Model.getInstance().getSelecteOrderItem().setDeliveryDateDesired(selectedItem.getDeliveryDateDesired());

    // Additional logic to handle the selected item if necessary
  }

  private void onChangeMH() {
    Stage changeMHStage = Model.getInstance().getViewFactory().showChangeMHScreen();

    changeMHStage.setOnHidden(event -> {
      if (notNull()) {
        Model.getInstance().updateTemporaryOrderItem(Model.getInstance().getSelecteOrderItem());
        orderItemMap.put(Model.getInstance().getSelecteOrderItem().getMerchandiseCode(), Model.getInstance()
            .getSelecteOrderItem());
        itemsOrder.setAll(orderItemMap.values());
        table.refresh();
      }
    });
  }

  private void onSaveData() {
    boolean isSuccessfully = true;
    for (OrderItem item : orderItemMap.values()) {
      Model.getInstance().updateChangeOrderItemTableDB(item);
      if (!isUpdateSuccessful(item)) {
        isSuccessfully = false;
        break;
      }
    }
    if(isSuccessfully) {
      showAlert(Alert.AlertType.INFORMATION, "Success", "Data save successfully");
    } else {
      showAlert(Alert.AlertType.ERROR, "Error", "Failed to save data");
    }
  }
  private boolean isUpdateSuccessful(OrderItem item) {
    return true;
  }

  private boolean notNull() {
    if (Model.getInstance().getSelecteOrderItem().getDeliveryDateDesired() == null || Model.getInstance().getSelecteOrderItem().getQuantityOrdered() == 0) {
      System.out.println("Delivery date or quantity ordered is null");
      return false;
    }
    return true;
  }

  private void onBack() {
    Model.getInstance().getViewFactory().getDVBHScreenSelectedMenuItemProperty().set("XemDH");
  }
  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
