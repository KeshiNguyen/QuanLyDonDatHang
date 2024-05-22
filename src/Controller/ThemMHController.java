package Controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import Model.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ThemMHController implements Initializable {
	@FXML
	private TextField deliveryDate;

	@FXML
	private Button exit_btn;

	@FXML
	private Button huy_btn;

	@FXML
	private Label merchadiseCode;

	@FXML
	private TextField quantityOrdered;

	@FXML
	private Button themMH_btn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Thiết lập chữ mờ cho các trường nhập liệu
		deliveryDate.setPromptText("Nhập ngày giao hàng (dd/MM/yyyy)");
		quantityOrdered.setPromptText("Nhập số lượng");

		merchadiseCode.setText(Model.getInstance().getSelecteOrderItem().getMerchandiseCode());

		exit_btn.setOnAction(event -> onExit());
		themMH_btn.setOnAction(event -> onThemMH());
		huy_btn.setOnAction(event -> onHuy());
	}

	private void onExit() {
		Model.getInstance().resetSelectedOrderItem();
		Stage stage = (Stage) exit_btn.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}

	private void onThemMH() {
		boolean validInput = true;

		// Kiểm tra số lượng
		try {
			int enteredQuantity = Integer.parseInt(quantityOrdered.getText());
			if (enteredQuantity <= Model.getInstance().getSelecteOrderItem().getQuantityOrdered()) {
				Model.getInstance().getSelecteOrderItem().setQuantityOrdered(enteredQuantity);
			} else {
				// Hiển thị thông báo lỗi số lượng
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Lỗi số lượng");
				alert.setHeaderText(null);
				alert.setContentText("Số lượng nhập không phải là số hợp lệ!!!");
				alert.showAndWait();
				validInput = false;
			}
		} catch (NumberFormatException e) {
			// Hiển thị thông báo lỗi số lượng không phải là số hợp lệ
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Lỗi số lượng");
			alert.setHeaderText(null);
			alert.setContentText("Số lượng nhập không phải là số hợp lệ!!!");
			alert.showAndWait();
			validInput = false;
		}

		// Xử lý ngày tháng
		String dateString = deliveryDate.getText(); // Chuỗi chứa ngày cần chuyển đổi
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		try {
			// Chuyển đổi từ chuỗi sang java.util.Date
			Date parsedDate = dateFormat.parse(dateString);

			// Kiểm tra năm có đúng 4 chữ số không
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parsedDate);
			int year = calendar.get(Calendar.YEAR);
			if (String.valueOf(year).length() != 4) {
				throw new ParseException("Ngày tháng không hợp lệ!!!", 0);
			}

			// Chuyển đổi từ java.util.Date sang java.sql.Date
			java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
			System.out.println("Ngày: " + sqlDate);
			Model.getInstance().getSelecteOrderItem().setDeliveryDateDesired(sqlDate);
		} catch (ParseException e) {
			// Hiển thị thông báo lỗi ngày tháng
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Lỗi ngày tháng");
			alert.setHeaderText(null);
			alert.setContentText("Ngày tháng không hợp lệ!!!");
			alert.showAndWait();
			validInput = false;
		}

		// Đóng Stage nếu mọi thứ hợp lệ
		if (validInput) {
			Stage stage = (Stage) exit_btn.getScene().getWindow();
			Model.getInstance().getViewFactory().closeStage(stage);
		}
	}

	private void onHuy() {
		Model.getInstance().resetSelectedOrderItem();

		Stage stage = (Stage) exit_btn.getScene().getWindow();
		Model.getInstance().getViewFactory().closeStage(stage);
	}
}