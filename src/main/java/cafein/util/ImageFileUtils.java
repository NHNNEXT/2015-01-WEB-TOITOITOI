package cafein.util;

import java.util.UUID;

public class ImageFileUtils {
	
	public static String getRandomString(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
