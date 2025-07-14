public class Test {
  public static void main(String[] args) {
    int x;
    int y = 5;
    if (y > 2) {
      x = ++y;
      y = x + 7;
    } else
      y++;
    System.out.println(x + " " + y);
  }
}
