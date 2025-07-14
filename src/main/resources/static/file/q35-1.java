public class Over {
	public void analyze(Object[] o) {
		System.out.println("I am an object array");
	}
	
	public void analyze(long[] l) {
		System.out.print ln("I am an array");
	}
	
	public void analyze(Object o) {
		System.out.println("I am an object");
	}
	
	public s tatic void main (String[] args){
		int[] nums = new int[10];
		new Over().analyze(nums); // line 1
	}
}