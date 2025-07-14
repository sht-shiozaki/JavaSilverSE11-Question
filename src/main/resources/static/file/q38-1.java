public class Tester {
	public static void main(String[] args) {
		String s = "this is it";
		int x = s.indexOf("is");
		s.substring(x + 3);
		x = s.indexOf("is");
		System.out.println(s + " " + x);
	}
}