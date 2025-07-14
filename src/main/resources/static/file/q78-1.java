public class Main {
	public static void main(String[] args) {
		int i = 1;
		for (String s : args) {
			System.out.println((i++) + ") " + s);
		}
	}
}