char[][] arrays = { { 'a', 'd' }, { 'b', 'e' }, {'c', 'f' }};
for (char[] xx : arrays) {
	for (char yy : xx) {
		System.out.print(yy);
	}
	System.out.print(" ");
}