public class BuilderImpl implements Builder {
	@Override
	public B build(String str) {
		return new B(str);
	}
}