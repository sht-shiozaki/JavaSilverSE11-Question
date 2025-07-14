import java.io.FileNotFoundException;
import java.io.IOException;

public class Tester {
	public static void main(String[] args) {
		try {
			doA();
		} // line 1
	}	
	
	private static void doA() throws IOException, IndexOutOfBoundsException {
		if (false) {
			throw new FileNotFoundException();
		} else {
			throw new IndexOutOfBoundsException();
		}
	}
}