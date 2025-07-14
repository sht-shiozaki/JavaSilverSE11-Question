public class Test {
	static String prefix = "Global:";
	private String name = "namescope";
	public static String getName() {
		return new Test().name;
	}
	
	public static void main(String[ String[] args){
		Test t = new Test();
		System.out.println(/* Insert code here */);
	}
}