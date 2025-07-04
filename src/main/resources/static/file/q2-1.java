public interface API { // line 1
	public void checkValue(Object value)
		throws IllegalArgumentException; // line 2
	public boolean isValueANumber (Object val) {
		if(val instanceof Number) {
			return true;
		}else {
			try {
				Double.parseDouble(val.toStri ng());
				return true;
			}catch (NumberFormatException ex) {
				return false;
			}
		}
	}
}