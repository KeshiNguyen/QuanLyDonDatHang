package Controller;

import Items.Items;
import Model.Model;
import com.sun.webkit.Timer.Mode;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import Items.OrderItem;

public class SuaMHController implements Initializable {
  @FXML
  private DatePicker deliveryDate;

  @FXML
  private Button exit_btn;

  @FXML
  private Button cancel_btn;

  @FXML
  private Label merchadiseCode;

  @FXML
  private Label nameItem;

  @FXML
  private TextField quantityOrdered;

  @FXML
  private Button changeMH_btn;

  private OrderItem selectedOrderItem;
  private int max_quantity;

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    deliveryDate.setDayCellFactory(new Callback<DatePicker, DateCell>() {
      @Override
      public DateCell call(final DatePicker datePicker) {
        return new DateCell() {
          @Override
          public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);
            if (item.isBefore(LocalDate.now())) {
              setDisable(true);
              setStyle("-fx-background-color: #ffc0cb;");
            }
          }
        };
      }
    });
    selectedOrderItem = Model.getInstance().getSelecteOrderItem();

    max_quantity = Model.getInstance().getMaxQuantityByMerchandiseCode(selectedOrderItem.getMerchandiseCode());
    if(max_quantity == -1) {
      showAlert(AlertType.ERROR, "Error", "Unable to fetch item quantity");
      quantityOrdered.setText(String.valueOf(selectedOrderItem.getQuantityOrdered()));
    }

    deliveryDate.setPromptText("Nhap ngay giao hang");
    quantityOrdered.setPromptText("Nhap so luong can dat");


    merchadiseCode.setText(selectedOrderItem.getMerchandiseCode());
    nameItem.setText(selectedOrderItem.getNameItem());

    exit_btn.setOnAction(event -> onExit());
    changeMH_btn.setOnAction(event ->onChangeMH());
    cancel_btn.setOnAction(event -> onCancel());
  }


  private void showAlert(Alert.AlertType alertType, String title, String message) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public void onExit() {
    Model.getInstance().resetSelectedOrderItem();
    Stage stage = (Stage) exit_btn.getScene().getWindow();
    Model.getInstance().getViewFactory().closeStage(stage);
  }

  public void onChangeMH() {
    boolean validInput = true;
    int original_quantity = selectedOrderItem.getQuantityOrdered();
    Date original_date = selectedOrderItem.getDeliveryDateDesired();
    //check valid quantity
    int max_quantity = 50;
    try {
      int inputQuantity = Integer.parseInt(quantityOrdered.getText());
      if(inputQuantity <= max_quantity) {
        Model.getInstance().getSelecteOrderItem().setQuantityOrdered(inputQuantity);
      } else {
        validInput = false;
        showAlert(Alert.AlertType.ERROR, "Invalid quantity", "Max quantity is " + max_quantity);
      }
    } catch (NumberFormatException e) {
      validInput = false;
      showAlert(Alert.AlertType.ERROR, "Invalid Number", "Must be a number.");
    }

    //check valid date delivery desired
    LocalDate date = deliveryDate.getValue();
    if(date == null) {
      validInput = false;
      showAlert(Alert.AlertType.ERROR, "Invalid Date", "Please select a delivery date.");
    } else if (date.isBefore(LocalDate.now())) {
      validInput = false;
      showAlert(Alert.AlertType.ERROR, "Invalid Date", "The delivery date cannot be in the past.");
    } else {
      Model.getInstance().getSelecteOrderItem().setDeliveryDateDesired(java.sql.Date.valueOf(date));
    }
    if(validInput) {
//      String successMessage = "Change successfully:\n" +
//                                "Quantity: " + original_quantity + " -> " + selectedOrderItem.getQuantityOrdered() + "\n" +
//                                "Delivery date: " + original_date + " -> " + selectedOrderItem.getDeliveryDateDesired().toString();
      showAlert(AlertType.INFORMATION, "Success", "successMessage");
      Stage stage = (Stage) exit_btn.getScene().getWindow();
      Model.getInstance().getViewFactory().closeStage(stage);
    }
  }

  private void onCancel() {
    Model.getInstance().resetSelectedOrderItem();
    Stage stage = (Stage) exit_btn.getScene().getWindow();
    Model.getInstance().getViewFactory().closeStage(stage);
  }
}
