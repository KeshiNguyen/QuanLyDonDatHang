package Items;

import java.sql.Date;

public class OrderItem {
	private int orderId;
	private int quantityOrdered;
	private Date deliveryDateDesired;
	private String nameOrder;
	private String note;
	private String merchandiseCode;
	private String nameItem;
	private String unit;
	private int order_status;

	public OrderItem(int orderId, String merchadiseCode, String nameItem, int quantityOrdered, String unit, Date date, String nameOrder, String note) {
		// TODO Auto-generated constructor stub
		this.merchandiseCode = merchadiseCode;
		this.nameItem =  nameItem;
		this.quantityOrdered = quantityOrdered;
		this.unit =  unit;
		this.orderId = orderId;
		this.deliveryDateDesired = date;
		this.nameOrder = nameOrder;
		this.note = note;
	}
	
	
	public OrderItem(int orderId2, String nameOrder2, String note2, int order_status2) {
		this.orderId = orderId2;
		this.nameOrder = nameOrder2;
		this.note = note2;
		this.order_status = order_status2;
	}


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public int getQuantityOrdered() {
		return quantityOrdered;
	}


	public void setQuantityOrdered(int quantityOrdered) {
		this.quantityOrdered = quantityOrdered;
	}


	public Date getDeliveryDateDesired() {
		return deliveryDateDesired;
	}


	public void setDeliveryDateDesired(Date date) {
		this.deliveryDateDesired = date;
	}


	public String getNameOrder() {
		return nameOrder;
	}


	public void setNameOrder(String nameOrder) {
		this.nameOrder = nameOrder;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public String getMerchandiseCode() {
		return merchandiseCode;
	}


	public void setMerchandiseCode(String merchandiseCode) {
		this.merchandiseCode = merchandiseCode;
	}


	public String getNameItem() {
		return nameItem;
	}


	public void setNameItem(String nameItem) {
		this.nameItem = nameItem;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getOrder_status() {
		return order_status;
	}
	
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
		
	@Override
    public String toString() {
        return "Order{" +
        		"OrderId='" + orderId + '\'' +
                ", merchandiseCode='" + merchandiseCode + '\'' +
                ", nameItem='" + nameItem + '\'' +
                ", quantityOrdered=" + quantityOrdered + '\'' +
                ", unit='" + unit + '\'' +
                ", deliveryDateDesired='" + deliveryDateDesired + '\'' +
                ", NameOrder" + nameOrder + '\'' +
                ", Note" + note + '\'' +
                '}';
    }
	
	public void showOrders() {
		System.out.println(toString());
	}

}