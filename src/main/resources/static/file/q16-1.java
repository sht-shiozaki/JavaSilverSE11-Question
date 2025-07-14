{
	Iterator iter = List.of(1, 2, 3).iterator();
	while (iter.hasNext()) {
		foo(iter.next());
	}
	Iterator iter2 = List.of(1, 2, 3).iterator();
	while (iter.hasNext()) {
		bar(iter2.next());
	}
}
	for (Iterator iter = List.of(1, 2, 3).iterator(); iter.hasNext();) {
		foo(iter.next());
	}
	for (Iterator iter2 = List.of(1, 2, 3).iterator(); iter.hasNext();) {
		bar(iter2.next());
	}