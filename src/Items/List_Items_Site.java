package Items;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class List_Items_Site {
	private ObservableList<Items> listItemsSite = FXCollections.observableArrayList();
	
	public List_Items_Site() {
		// TODO Auto-generated constructor stub
	}
	
	public void addItems(Items item) {
		if (!listItemsSite.contains(item)) {
			listItemsSite.add(item);
		} else {
			System.out.println("Add items to Error!");
		}
	}
	
	public void addItems(Items... items) {
		for (Items item : items) {
			addItems(item);
		}
	}
	
	public void removeItems(Items item) {
		if (listItemsSite.contains(item)) {
			listItemsSite.remove(item);
			System.out.println("Item removed successfully!");
		} else {
			System.out.println("Item not found in the repository. Unable to remove.");
		}
	}
	
	public void removeItems(Items... items) {
		for (Items item : items) {
			removeItems(item);
		}
	}
	
	public void showListItemsSite() {
		for (Items items : listItemsSite) {
			items.showItem();
		}
	}
	
	public Items seachItems(Items item_search) {
		Items item = null;
		for (Items items : listItemsSite) {
			if (items == item_search) {
				item = items;
			}
		}
		return item;
	}
	
	public ObservableList<Items> getListItemsSite() {
		return listItemsSite;
	}
}