import java.time.LocalDate;
import static java.time.DayOfWeek.*;
public class Main {
  public static void main(String[] args) {
    var today = LocalDate.now().with(TUESDAY).getDayOfWeek();
    switch (today) {
      case SUNDAY:
      case SATURDAY:
        System.out.println("Weekend");
        break;
      case MONDAY: FRIDAY:
        System.out.println("Working");
      default:
        System.out.println("Unknown");
    }
  }
}
