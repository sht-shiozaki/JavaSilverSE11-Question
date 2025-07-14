import java.util.*;
public class Main {
	static Map<String, String> map = new HashMap<>();
	static List<String> keys = new ArrayList<>(List.of("A", "B", "C", "D"));
	static String[] values = { "one", "two", "three", "four" };
	static {
		for (var i = 0; i < keys.size(); i++) {
			map.put(keys.get(i), values[i]);
		}
	}
	
	public static void main(String[] args) {
		keys.clear();
		values = new String[0];
		System.out.println("Map: " + map.size() + " Keys: " + keys.size() + " Values: " + values.length);
	}
}