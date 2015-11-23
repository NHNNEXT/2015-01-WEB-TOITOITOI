package cafein.util;

public class Validation {
	public static boolean isValidParameter(String parameter) {
		// {latitude, "".equals(latitude) , latitude == null, latitude.isEmpty()}
		// "?lat=" {, true, false, true}
		// "?" {null, false, true, ERROR}
		// "?lat=37.51" {37.51, false, false, false}
		return (parameter!=null) && !(parameter.isEmpty());
	}
}
