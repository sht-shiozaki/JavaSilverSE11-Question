public interface A {
	public Iterable a();
}

public interface B extends A {
	public Collection a();
}

public interface C extends A {
	public Path a();
}

public interface D extends B, C {
}