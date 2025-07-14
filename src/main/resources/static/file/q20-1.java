interface Pastry {
	void getIngredients();
}
abstract class Cookie implements Pastry { }

class ChocolateCookie implements Cookie {
	public void getIngredients() { }
}
class CoconutChocolateCookie extends ChocolateCookie {
	void getIngredients(int x) { }
}