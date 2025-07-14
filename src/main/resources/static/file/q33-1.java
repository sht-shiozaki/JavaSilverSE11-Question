public class Test {
	public static void main(String[] args) {
		AnotherClass ac = new AnotherClass();
		SomeClass sc = new AnotherClass();
		ac = sc;
		sc.methodA();
		ac.methodA();
	
	}
}

public class SomeClass {
	public void methodA() {
		Syste m.out.println("SomeClass#methodA()");
	}
	
public class AnotherClass extends SomeClass {
	public void methodA() {
		System.out.println("AnotherClass#methodA()");
	}
}