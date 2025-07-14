String[][] arr = {
	{ "Red", "White"},
	{ "Black"},
	{ "Blue", "Yellow", "Green", "Violet"}
};

for (int row = 0; row < arr.length; row++) {
	int column = 0;
	for (; column < arr[row].length; column++) {
		System.out.println("[" + row + "," + column + "] = " + arr[row][column]);
	}
}
