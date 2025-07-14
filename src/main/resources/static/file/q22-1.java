class Employee {
	String office;
}
public class HRApp {
	var employee = new ArrayList<Employee>();
	public var display() {
		var employee = new Employee();
		var offices = new ArrayList<>();
		offices.add("Chicago");
		offices.add("Bangalore");
		for (var office : offices) {
			System.out.print("Employee Location" + office);
		}
	}
}