package Model;

import java.sql.*;
import Items.Items;
import Items.List_Items_Site;
import Items.List_Orderred;
import Items.OrderItem;
import View.ViewFactory;
import java.util.HashMap;
import java.util.Map;

public class Model {
	private final ViewFactory viewFactory;
	private static Model model;
	private OrderItem selecteOrderItem;
	private Map<String, Integer> siteItemMap;


	private static final String DRIVER_NAME = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost:5432/PTPM";
	private static final String USER = "postgres";
	private static final String PASS = "keshi0611";

	private Model() {
		this.viewFactory = new ViewFactory();
		this.selecteOrderItem = new OrderItem(0, null, null, 0, null, null, null, null);
		this.siteItemMap = new HashMap<>();


		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static synchronized Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}

	public OrderItem getSelecteOrderItem() {
		return selecteOrderItem;
	}

	public void setSelecteOrderItem(OrderItem selecteOrderItem) {
		this.selecteOrderItem = selecteOrderItem;
	}

	public ViewFactory getViewFactory() {
		return viewFactory;
	}

	private Connection createConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}

	/* GET DATA OF DATABASE */

	public List_Orderred getListOrderTable() {
		List_Orderred listOrderred = new List_Orderred();
		String sql = "SELECT * FROM public.\"ListOrder\" WHERE \"OrderId\" > 1;";

		try (Connection connection = createConnection();
				Statement stmt = connection.createStatement();
				ResultSet result = stmt.executeQuery(sql)) {

			while (result.next()) {
				int orderId = result.getInt("OrderId");
				String nameOrder = result.getString("NameOrder");
				String note = result.getString("Note");
				int status = result.getInt("order_status");

				// Check for null values before calling trim()
				if (nameOrder != null) {
					nameOrder = nameOrder.trim();
				}
				if (note != null) {
					note = note.trim();
				}

				OrderItem orderItem = new OrderItem(orderId, nameOrder, note, status);
				listOrderred.addOrder(orderItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOrderred;
	}

	public List_Items_Site getTableSite() {
		List_Items_Site listItemsSite = new List_Items_Site();
		String sql = "SELECT * FROM public.\"SiteItem\"";

		try (Connection connection = createConnection();
				Statement stmt = connection.createStatement();
				ResultSet result = stmt.executeQuery(sql)) {

			while (result.next()) {
				String merchandiseCode = result.getString("MerchandiseCode");
				String nameItem = result.getString("NameItem");
				String unit = result.getString("Unit");
				String describeItem = result.getString("DescribeItem");
				int quantity = result.getInt("Quantity");

				// Check for null values before calling trim()
				if (merchandiseCode != null) {
					merchandiseCode = merchandiseCode.trim();
				}
				if (nameItem != null) {
					nameItem = nameItem.trim();
				}
				if (unit != null) {
					unit = unit.trim();
				}
				if (describeItem != null) {
					describeItem = describeItem.trim();
				}

				Items item = new Items(merchandiseCode, nameItem, quantity, unit, describeItem);
				listItemsSite.addItems(item);

				siteItemMap.put(merchandiseCode, quantity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listItemsSite;
	}

	public int getMaxQuantityByMerchandiseCode(String merchandiseCode) {
		return siteItemMap.getOrDefault(merchandiseCode, -1);
	}

	public List_Orderred getTableOrderByOrderId(int orderId) {
		List_Orderred listOrderred = new List_Orderred();
		String sql = "SELECT o.\"OrderId\", si.\"MerchandiseCode\", si.\"NameItem\", o.\"QuantityOrdered\", si.\"Unit\", o.\"DeliveryDateDesired\", lo.\"NameOrder\", lo.\"Note\" "
				+ "FROM public.\"Orders\" o "
				+ "JOIN public.\"SiteItem\" si ON o.\"MerchandiseCode\" = si.\"MerchandiseCode\" "
				+ "JOIN public.\"ListOrder\" lo ON o.\"OrderId\" = lo.\"OrderId\" "
				+ "WHERE o.\"OrderId\" = ?";

		try (Connection connection = createConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

			pstmt.setInt(1, orderId);
			try (ResultSet result = pstmt.executeQuery()) {
				while (result.next()) {
					String merchandiseCode = result.getString("MerchandiseCode").trim();
					String nameItem = result.getString("NameItem").trim();
					int quantity = result.getInt("QuantityOrdered");
					String unit = result.getString("Unit").trim();
					Date date = result.getDate("DeliveryDateDesired");
					String nameOrder = result.getString("NameOrder").trim();
					String note = result.getString("Note").trim();
					OrderItem orderItem = new OrderItem(orderId, merchandiseCode, nameItem, quantity, unit, date,
							nameOrder, note);
					listOrderred.addOrder(orderItem);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOrderred;
	}

	public void resetSelectedOrderItem() {
		selecteOrderItem = new OrderItem(0, null, null, 0, null, null, null, null);
	}

	public void updateTemporaryOrderItem(OrderItem updatedItem) {
		List_Orderred currentOrder = getTableOrderByOrderId(updatedItem.getOrderId());
		for (OrderItem item : currentOrder.getListOrdered()) {
			if(item.getMerchandiseCode().equals(updatedItem.getMerchandiseCode())) {
				item.setQuantityOrdered(updatedItem.getQuantityOrdered());
				item.setDeliveryDateDesired(updatedItem.getDeliveryDateDesired());
				System.out.println("Check quantity change: " + item.getQuantityOrdered() +" from model/updateTemporaryOrderItem");
				break;
			}
		}
	}


	/* UPDATE DATA OF DATABASE */

	public void updateOrderTableDB() {
		String sql = "INSERT INTO public.\"Orders\" (\"OrderId\", \"MerchandiseCode\", \"QuantityOrdered\", \"DeliveryDateDesired\") "
				+ "VALUES (?, ?, ?, ?)";

		try (Connection connection = createConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

			pstmt.setInt(1, selecteOrderItem.getOrderId());
			pstmt.setString(2, selecteOrderItem.getMerchandiseCode());
			pstmt.setInt(3, selecteOrderItem.getQuantityOrdered());
			pstmt.setDate(4, selecteOrderItem.getDeliveryDateDesired());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void resetOrderTemp() {
		String deleteOrdersSQL = "DELETE FROM public.\"Orders\" WHERE \"OrderId\" = 1";
		String updateListOrderSQL = "UPDATE public.\"ListOrder\" SET \"NameOrder\" = 'Order Temp', \"Note\" = 'Repo for order temp' WHERE \"OrderId\" = 1";

		try (Connection connection = createConnection(); Statement stmt = connection.createStatement()) {

			stmt.executeUpdate(deleteOrdersSQL);
			stmt.executeUpdate(updateListOrderSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateNameForOrderTemp() {
		String sql = "UPDATE public.\"ListOrder\" SET \"NameOrder\" = ?, \"Note\" = ? WHERE \"OrderId\" = 1";

		try (Connection connection = createConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {

			pstmt.setString(1, selecteOrderItem.getNameOrder());
			if (selecteOrderItem.getNote() == null || selecteOrderItem.getNote().isEmpty()) {
				pstmt.setNull(2, java.sql.Types.VARCHAR);
			} else {
				pstmt.setString(2, selecteOrderItem.getNote());
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addOrderTempToListOrders() {
		String insertListOrderSQL = "INSERT INTO public.\"ListOrder\" (\"NameOrder\", \"Note\") SELECT \"NameOrder\", \"Note\" FROM public.\"ListOrder\" WHERE \"OrderId\" = 1";
		String insertOrdersSQL = "INSERT INTO public.\"Orders\" (\"OrderId\", \"MerchandiseCode\", \"QuantityOrdered\", \"DeliveryDateDesired\") "
				+ "SELECT ?, \"MerchandiseCode\", \"QuantityOrdered\", \"DeliveryDateDesired\" FROM public.\"Orders\" WHERE \"OrderId\" = 1";

		try (Connection connection = createConnection()) {
			connection.setAutoCommit(false);

			try (Statement stmt = connection.createStatement()) {
				stmt.executeUpdate(insertListOrderSQL, Statement.RETURN_GENERATED_KEYS);

				ResultSet generatedKeys = stmt.getGeneratedKeys();
				if (generatedKeys.next()) {
					long newOrderId = generatedKeys.getLong(1);

					try (PreparedStatement pstmt = connection.prepareStatement(insertOrdersSQL)) {
						pstmt.setLong(1, newOrderId);
						pstmt.executeUpdate();
					}
					connection.commit();
				} else {
					connection.rollback();
					throw new SQLException("Tạo ListOrder thất bại, không có ID nào được lấy.");
				}
			} catch (SQLException e) {
				connection.rollback();
				e.printStackTrace();
				throw e;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateChangeOrderItemTableDB(OrderItem item) {
		String sql = "UPDATE public.\"Orders\" SET \"QuantityOrdered\" = ?, \"DeliveryDateDesired\" = ? WHERE \"OrderId\" = ? AND \"MerchandiseCode\" = ?";
		try(Connection connection = createConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, item.getQuantityOrdered());
			pstmt.setDate(2, item.getDeliveryDateDesired());
			pstmt.setInt(3, item.getOrderId());
			pstmt.setString(4, item.getMerchandiseCode());

			pstmt.executeUpdate();
			System.out.println("Cap nha thanh cong don hang");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Loi cap nhat don hang");
		}
	}
}
