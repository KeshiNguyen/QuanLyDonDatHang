package Controller;

import View.ViewFactory;
import com.sun.webkit.Timer.Mode;
import java.net.URL;
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
import javafx.stage.Stage;

public class DanhSachDHController implements Initializable {

  @FXML
  private TableColumn<OrderItem, Integer> orderId_col;

  @FXML
  private TableColumn<OrderItem, String> nameOrder_col;

  @FXML
  private TableColumn<OrderItem, String> note_col;

  @FXML
  private TableView<OrderItem> table;

  @FXML
  private TableColumn<OrderItem, String> state_col;

  @FXML
  private Button xemDH_btn;

  @FXML
  private Button dathang_btn;

  @FXML
  private Button help;

  @FXML
  private Button xoaDH_btn;

  @FXML
  private Button suaDH_btn;

  private ObservableList<OrderItem> orderItems;

//  private ViewFactory viewFactory;
//
//  public void setViewFactory(ViewFactory viewFactory) {
//    this.viewFactory = viewFactory;
//  }
  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    // TODO Auto-generated method stub

    setTable();

    xemDH_btn.setDisable(true);
    xoaDH_btn.setDisable(true);
    suaDH_btn.setDisable(true);
    dathang_btn.setDisable(true);
    // Add a listener to Site_table to handle selection changes
    table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        handleItemSelected(newValue);
        xemDH_btn.setDisable(newValue == null);
        suaDH_btn.setDisable(newValue == null);
        xoaDH_btn.setDisable(newValue == null);
        dathang_btn.setDisable(newValue == null);
      }
    });

    xemDH_btn.setOnAction(event -> onXemDH());
    suaDH_btn.setOnAction(event -> onSuaDH());
  }



  @SuppressWarnings("unchecked")
  private void setTable() {
    List_Orderred list_Orderred = Model.getInstance().getListOrderTable();
    orderId_col.setCellValueFactory(new PropertyValueFactory<>("orderId"));
    nameOrder_col.setCellValueFactory(new PropertyValueFactory<>("nameOrder"));
    note_col.setCellValueFactory(new PropertyValueFactory<>("note"));
    state_col.setCellValueFactory(new PropertyValueFactory<>("order_status"));


    table.getColumns().clear();
    table.getColumns().addAll(orderId_col, nameOrder_col, note_col, state_col);

    orderItems = FXCollections.observableArrayList(list_Orderred.getListOrdered());
    table.setItems(orderItems);
  }

  private void onXemDH() {
    Model.getInstance().getViewFactory().getDVBHScreenSelectedMenuItemProperty().set("XemDSMH");
  }

  private void onSuaDH() {
    Model.getInstance().getViewFactory().getDVBHScreenSelectedMenuItemProperty().set("SuaDH");
  }
  private void handleItemSelected(OrderItem newValue) {
    // TODO Auto-generated method stub
    Model.getInstance().getSelecteOrderItem().setOrderId(newValue.getOrderId());
  }
}