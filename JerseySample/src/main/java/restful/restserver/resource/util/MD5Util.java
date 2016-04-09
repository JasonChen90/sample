package restful.restserver.resource.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author JasonChen1
 */
public class MD5Util {

	public final static char[] hexDigests = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	/**
	 * md5 string
	 * @param s
	 * @return
	 */
	public final static String MD5(String s){
		String r = null;
		try {
			MessageDigest d = MessageDigest.getInstance("MD5");
			byte[] m = d.digest(s.getBytes());//16*8=>128
			char[] c = new char[16 * 2];
			for(int i = 0, k = 0; i < 16; i++){
				byte byte0 = m[i];
				c[k++] = MD5Util.hexDigests[byte0 >> 4 & 0xf];
				c[k++] = MD5Util.hexDigests[byte0 & 0xf];
			}
			r = new String(c).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return r;
	}
}
