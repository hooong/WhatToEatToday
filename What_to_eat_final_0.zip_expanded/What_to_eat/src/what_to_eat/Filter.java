package what_to_eat;

public class Filter {
    static public String position;
    static public String food;
    static public String price;

    public Filter() {

    }

    public static void setPosition(String arg) {
	position = arg;
    }

    public String getPosition() {
	return Filter.position;
    }

    public static void setFood(String arg) {
	food = arg;
    }

    public String getFood() {
	return Filter.food;
    }

    public static void setPrice(String arg) {
	price = arg;
    }

    public String getPrice() {
	return Filter.price;
    }
   
}
