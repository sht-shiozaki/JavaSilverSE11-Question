public class Person {
	private String name;
	public void setName(String name) {
		String title = "Dr. ";
		name = title + name;
	}
	
	public String toString() {
		return name;
	}
}