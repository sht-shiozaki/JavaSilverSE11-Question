import java.util.ArrayList;
import java.util.Arrays;
public class NewMain {
	public static void main(String[] args) {
		String[] fruitNames = { "apple", "orange", "grape", "lemon", "apri cot", "watermelon" };
		var fruits = new ArrayList<>(Arrays.asList(fruitNames));
		fruits.sort((var a, var b) --> a.compareTo(b));
		fruits.forEach(System.out::println);
	}
}