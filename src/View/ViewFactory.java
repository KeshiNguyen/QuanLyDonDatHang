package View;

import Controller.SuaMHController;
import Items.OrderItem;
import java.io.IOException;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewFactory {
	private final StringProperty DVBHScreenSelectedMenuItemProperty;
	private AnchorPane taoDHView;
	private AnchorPane DanhSachDHView;
	private AnchorPane dSSiteView;
	private AnchorPane dSMHView;
	private AnchorPane suaDHView;

	public ViewFactory() {
		this.DVBHScreenSelectedMenuItemProperty = new SimpleStringProperty("");
	}

	public StringProperty getDVBHScreenSelectedMenuItemProperty() {
		return DVBHScreenSelectedMenuItemProperty;
	}

	public AnchorPane getTaoDHView() {
		if (taoDHView == null) {
			try {
				taoDHView = new FXMLLoader(
						getClass().getResource("/FXMLFile/BoPhanBanHang/TaoDonHangMoi/TaoDHMoi.fxml")).load();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return taoDHView;
	}

	public void refreshTaoDHView() {
		try {
			taoDHView = new FXMLLoader(getClass().getResource("/FXMLFile/BoPhanBanHang/TaoDonHangMoi/TaoDHMoi.fxml"))
					.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AnchorPane getDSSiteView() {
		if (dSSiteView == null) {
			try {
				dSSiteView = new FXMLLoader(
						getClass().getResource("/FXMLFile/BoPhanBanHang/DSMHSiteKinhDoanh/DSSite.fxml")).load();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return dSSiteView;
	}

	public void refreshDSSite() {
		try {
			dSSiteView = new FXMLLoader(getClass().getResource("/FXMLFile/BoPhanBanHang/DSMHSiteKinhDoanh/DSSite.fxml"))
					.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AnchorPane getDanhSachDHView() {
		if (DanhSachDHView == null) {
			try {
				DanhSachDHView = new FXMLLoader(
						getClass().getResource("/FXMLFile/BoPhanBanHang/DSDonHangDaTao/DanhSachDH.fxml")).load();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		return DanhSachDHView;
	}


	public void refreshDanhSachDH() {
		try {
			DanhSachDHView = new FXMLLoader(
					getClass().getResource("/FXMLFile/BoPhanBanHang/DSDonHangDaTao/DanhSachDH.fxml")).load();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public AnchorPane getdSMHView() {
		try {
			dSMHView = new FXMLLoader(
					getClass().getResource("/FXMLFile/BoPhanBanHang/DSDonHangDaTao/DSMH.fxml")).load();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return dSMHView;
	}

	public AnchorPane getSuaDHView() {
			try {
				suaDHView = new FXMLLoader(getClass().getResource("/FXMLFile/BoPhanBanHang/SuaDonHang/SuaDH.fxml")).load();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return suaDHView;
	}


	public Stage showChangeMHScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/FXMLFile/BoPhanBanHang/SuaDonHang/SuaMH.fxml")
			);
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
			return stage;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void showLoginScreen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFile/Login/Login.fxml"));

		createStage(loader);
	}

	public void showDVBHScreen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFile/BoPhanBanHang/DVBHScreen.fxml"));

		createStage(loader);
	}

	public Stage showThemMHScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/FXMLFile/BoPhanBanHang/TaoDonHangMoi/ThemMH.fxml"));
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
			return stage; // Trả về Stage
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void showDanhSachDHScreen() {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/FXMLFile/BoPhanBanHang/DSDonHangDaTao/DanhSachDH.fxml"));

		createStage(loader);
	}

	public void showBPDHQuocTeScreen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFile/BoPhanDatHangQuocTe/BPDHQuocTe.fxml"));

		createStage(loader);
	}

	public void showBPQLKhoScreen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFile/BoPhanQuanLyKho/BPQLKho.fxml"));

		createStage(loader);
	}

	public void showSiteNhapKhauScreen() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFile/BoPhanSiteNhauKhau/SiteNhapKhau.fxml"));

		createStage(loader);
	}

	public void showThongBao() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLFile/Login/LoginError.fxml"));

		createStage(loader);
	}

	private void createStage(FXMLLoader loader) {
		Scene scene = null;
		Parent root = null;
		try {
			root = loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);

		moveStage(stage);

		stage.show();
	}

	public void closeStage(Stage stage) {
		stage.close();
	}

	private void moveStage(Stage stage) {
		final DoubleProperty xOffset = new SimpleDoubleProperty(0);
		final DoubleProperty yOffset = new SimpleDoubleProperty(0);

		stage.initStyle(StageStyle.UNDECORATED);

		stage.getScene().getRoot().setOnMousePressed((MouseEvent event) -> {
			xOffset.set(event.getSceneX());
			yOffset.set(event.getSceneY());
		});

		stage.getScene().getRoot().setOnMouseDragged((MouseEvent event) -> {
			stage.setX(event.getScreenX() - xOffset.get());
			stage.setY(event.getScreenY() - yOffset.get());
		});
	}

}