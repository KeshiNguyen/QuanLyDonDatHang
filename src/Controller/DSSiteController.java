package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import Items.Items;
import Items.List_Items_Site;
import Model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DSSiteController implements Initializable{
	@FXML
	private TableView<Items> site_table;

	@FXML
    private TableColumn<Items, String> describeItem_col;

    @FXML
    private TableColumn<Items, String> merchandiseCode_col;

    @FXML
    private TableColumn<Items, String> nameItem_col;

    @FXML
    private TableColumn<Items, Integer> quantity_col;

    @FXML
    private TableColumn<Items, String> unit_col;

	private ObservableList<Items> itemsSite;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		List_Items_Site list_Items_Site = Model.getInstance().getTableSite();
		
	    // Khởi tạo các cột của TableView và gán các PropertyValueFactory
	    merchandiseCode_col.setCellValueFactory(new PropertyValueFactory<>("merchandiseCode"));
	    nameItem_col.setCellValueFactory(new PropertyValueFactory<>("nameItem"));
	    quantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
	    unit_col.setCellValueFactory(new PropertyValueFactory<>("unit"));
	    describeItem_col.setCellValueFactory(new PropertyValueFactory<>("describeItem"));


	    // Xóa tất cả các cột hiện có trong TableView
	    site_table.getColumns().clear();

	    // Thêm các cột vào TableView
	    site_table.getColumns().addAll(merchandiseCode_col, nameItem_col, quantity_col, unit_col, describeItem_col);

	    // Khởi tạo danh sách các mục và thêm vào TableView
	    itemsSite = FXCollections.observableArrayList(list_Items_Site.getListItemsSite());
	    site_table.setItems(itemsSite);
	}

	
	
}