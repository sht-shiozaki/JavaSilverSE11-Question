package test;
import java.time.*;
public class Diary {
	private LocalDate now = LocalDate.now();
	public LocalDate getDate() {
		return now;
	}
}