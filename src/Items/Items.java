package Items;

public class Items {
	private String merchandiseCode;
	private String nameItem;
	private int quantity;
	private String unit;
	private String describeItem;

	public Items(String merchandiseCode, String nameItem, int quantity, String unit, String describeItem) {
		// TODO Auto-generated constructor stub
		this.merchandiseCode = merchandiseCode;
		this.nameItem = nameItem;
		this.quantity = quantity;
		this.unit = unit;
		this.describeItem = describeItem;

	}
	
	public Items(String merchandiseCode, String nameItem, int quantity, String unit) {
		// TODO Auto-generated constructor stub
		this.merchandiseCode = merchandiseCode;
		this.nameItem = nameItem;
		this.quantity = quantity;
		this.unit = unit;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDescribeItem() {
		return describeItem;
	}

	public void setDescribeItem(String describeItem) {
		this.describeItem = describeItem;
	}

	@Override
    public String toString() {
        return "Items{" +
                "merchandiseCode='" + merchandiseCode + '\'' +
                ", nameItem='" + nameItem + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", describe='" + describeItem + '\'' +
                '}';
    }
	
	public void showItem() {
		System.out.println(toString());
	}
	// Method
}