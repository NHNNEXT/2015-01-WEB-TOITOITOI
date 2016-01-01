package cafein.util;

import java.util.UUID;

public class FileUtils {
	
	public static String getRandomString(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
