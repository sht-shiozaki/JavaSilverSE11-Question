import java.util.function.BiFunction;
public class Pair<T> {
	final BiFunction<T, T, Boolean> validator;
	T left = null;
	T right = null;
	private Pair() {
		validator = null;
	}
	Pair(BiFunction<T, T, Boolean> v, T x, T y) {
		validator = v;
		set(x, y);
	}
	voi d set(T x, T y) {
		if (!validator.apply(x, y))
			throw new IllegalArgumentException();
		setLeft(x);
		setRight(y);
	}
	void setLeft(T x) {
		left = x;
	}
	void setRight(T y) {
		right = y;
	}
	final boolean isValid() {
		return validator.apply(left, right);
	}
}