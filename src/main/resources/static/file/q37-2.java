public class ConSub extends ConSuper {
	ConSub() {
		this(4);
		System.out.print("3");
	}
	ConSub(int a) {
		System.out.print(a);
	}
	public static void main(String[] args) {
		new ConSub(4);
	}
}