void myLambda() {
    int i = 25;

    Supplier<Integer> foo = () -> i;
    i++;

    System.out.println(foo.get());
}
