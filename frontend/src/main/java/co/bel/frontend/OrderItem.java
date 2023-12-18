package co.bel.frontend;

public class OrderItem {
	private int orderitem_id;

	private Order order_id;
	
	private Consumer consumer_id;

	private Item item_id;
	
	private int quantity;

	Order ord = new Order();
	
	public OrderItem() {}
	

	public OrderItem(int orderId, Consumer consumer, Item item, int quantity2) {
		// TODO Auto-generated constructor stub
		ord.setOrder_id(orderId);
		
		consumer_id = consumer;
		
		item_id = item;
		
		quantity = quantity2;
	}


	public int getOrderitem_id() {
		return orderitem_id;
	}

	public void setOrderitem_id(int orderitem_id) {
		this.orderitem_id = orderitem_id;
	}

	public Order getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Order order_id) {
		this.order_id = order_id;
	}

	public Consumer getConsumer_id() {
		return consumer_id;
	}

	public void setConsumer_id(Consumer consumer_id) {
		this.consumer_id = consumer_id;
	}

	public Item getItem_id() {
		return item_id;
	}

	public void setItem_id(Item item_id) {
		this.item_id = item_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
