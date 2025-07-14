public class Tester {
	public static void main(String[] args) {
		char letter = 'b';
		int i = 0;
		
		switch (letter) {
			case 'a':
				i++;
				break;
			case 'b':
				i++;
			case 'c' | 'd': // line 1
				i++;
			case 'e':
				i++;
				break;
			case 'f':
				i++;
				brea k;
			default:
				System.out.print(letter);
		}
		System.out.println(i);
	}
}