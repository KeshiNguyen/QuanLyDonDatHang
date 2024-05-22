package Items;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class List_Orderred {

	private ObservableList<OrderItem> listOrdered = FXCollections.observableArrayList();

	public List_Orderred() {
		// TODO Auto-generated constructor stub
	}

	public void addOrder(OrderItem orderItem) {
		if (!listOrdered.contains(orderItem)) {
			listOrdered.add(orderItem);
		} else {
			System.out.println("Add order to Error!");
		}
	}

	public void addOrder(OrderItem... orders) {
		for (OrderItem orderItem : orders) {
			addOrder(orderItem);
		}
	}

	public void removeOrder(OrderItem orderItem) {
		if (listOrdered.contains(orderItem)) {
			listOrdered.remove(orderItem);
			System.out.println("Order removed successfully!");
		} else {
			System.out.println("Order not found in the repository. Unable to remove.");
		}
	}

	public void removeOrder(OrderItem... orders) {
		for (OrderItem orderItem : orders) {
			removeOrder(orderItem);
		}
	}

	public void showListOrder() {
		for (OrderItem orderItem : listOrdered) {
			orderItem.showOrders();
		}
	}

	public OrderItem seachItems(OrderItem order_search) {
		OrderItem orderItem = null;
		for (OrderItem orderT : listOrdered) {
			if (orderT == order_search) {
				orderItem = orderT;
			}
		}
		return orderItem;
	}


	public ObservableList<OrderItem> getListOrdered() {
		return listOrdered;
	}
}