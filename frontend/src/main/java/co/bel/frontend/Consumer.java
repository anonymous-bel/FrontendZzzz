package co.bel.frontend;

public class Consumer {

	private int consumer_id;

	private String consumer_name;

	private String phone_no;

	private String email_id;

	private String password;

	public Consumer() {
	}

	public Consumer(int consumerId, String consumerName, String email, String password2, String phoneNo) {
		consumer_id = consumerId;
		consumer_name = consumerName;
		email_id = email;
		password = password2;
		phone_no = phoneNo;
	}

	public int getConsumer_id() {
		return consumer_id;
	}

	public void setConsumer_id(int consumer_id) {
		this.consumer_id = consumer_id;
	}

	public String getConsumer_name() {
		return consumer_name;
	}

	public void setConsumer_name(String consumer_name) {
		this.consumer_name = consumer_name;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

//	@Override
//	public int hashCode() {
//		return Objects.hash(password);
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Consumer other = (Consumer) obj;
//		return Objects.equals(password, other.password);
//	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
