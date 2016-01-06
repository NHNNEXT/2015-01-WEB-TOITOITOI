package cafein.util;


public class Validation {
	
	//null,empty,띄어쓰기한칸" ","" 체크
	public static boolean isValidParameter(String string) {
		boolean status;
		
		if(string!=null && !(string.isEmpty())) {
			System.out.println("what?"+string);
			System.out.println(string.isEmpty());
			System.out.println(string!=null);
			if(!(string.equals("")) && !(string.equals(" "))) {
				return status = true;
			}	
		}
		return false;
	}
	
	//dear < 25,post < 20000, reply <  min/max 체크
	public static boolean isValidMaxLenDear(String dear) {
		return (dear.length() <= 25);
	}
	public static boolean isValidMaxLenPost(String post) {
		return (post.length() <= 20000);
	}
	public static boolean isValidMaxLenReply(String reply) {
		return (reply.length() <= 2000);
	}
	
	//@PathVariable의 EntityId와 pageNumber체크
	public static boolean isValidParameter(Integer number) {
		return (number != null);
	}
	
	public static boolean isValidParameterType(Integer number) {
		return (number >= 1) && (number == (Integer)number);
	}
	
	
	
}
