package co.bel.frontend;

public class Order {
	
	private int order_id;
	
	private Consumer consumer_id;
	
	private double total_price;
	
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public Consumer getConsumer_id() {
		return consumer_id;
	}
	public void setConsumer_id(Consumer consumer_id) {
		this.consumer_id = consumer_id;
	}
	public double getTotal_price() {
		return total_price;
	}
	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}
}
