package what_to_eat;

public class Restaurants {
	private String id;
	private String place;
	private String style;
	private String restaurant;
	private String menu;
	private String price;
	private String delivery;
	private String phone_number;
	private String time_m;

	public Restaurants(String id, String place, String style, String restaurant, String menu, String price, String delivery,
			String phone_number, String time_m) {
		this.id = id;
		this.place = place;
		this.style = style;
		this.restaurant = restaurant;
		this.menu = menu;
		this.price = price;
		this.delivery = delivery;
		this.phone_number = phone_number;
		this.time_m = time_m;
	}

	public Restaurants(String place, String style, String price) {
		this.place = place;
		this.style = style;
		this.price = price;
		
	}
	
	public Restaurants() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getTime_m() {
		return time_m;
	}

	public void setTime_m(String time_m) {
		this.time_m = time_m;
	}

	@Override
	public String toString() {
		return "Restaurants [id =" + id + ", place =" + place + ", style =" + style + ", name =" + restaurant
				+ ", menu =" + menu + ", price =" + price + ", delivery" + delivery + ", phone_number =" + phone_number + ", time_m =" + time_m;

	}

}
