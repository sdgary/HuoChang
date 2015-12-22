package byxx.android.huochang.picture;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class BASE64Encoder {

	private static char[] codec_table = { 'A', 'B', 'C', 'D', 'E', 'F', 'G',
			'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '+', '/' };

	public BASE64Encoder() {
	}

	public String encode(byte[] a) {
		int totalBits = a.length * 8;
		int nn = totalBits % 6;
		int curPos = 0;// process bits
		StringBuffer toReturn = new StringBuffer();
		while (curPos < totalBits) {
			int bytePos = curPos / 8;
			switch (curPos % 8) {
			case 0:
				toReturn.append(codec_table[(a[bytePos] & 0xfc) >> 2]);
				break;
			case 2:

				toReturn.append(codec_table[(a[bytePos] & 0x3f)]);
				break;
			case 4:
				if (bytePos == a.length - 1) {
					toReturn.append(codec_table[((a[bytePos] & 0x0f) << 2) & 0x3f]);
				} else {
					int pos = (((a[bytePos] & 0x0f) << 2) | ((a[bytePos + 1] & 0xc0) >> 6)) & 0x3f;
					toReturn.append(codec_table[pos]);
				}
				break;
			case 6:
				if (bytePos == a.length - 1) {
					toReturn.append(codec_table[((a[bytePos] & 0x03) << 4) & 0x3f]);
				} else {
					int pos = (((a[bytePos] & 0x03) << 4) | ((a[bytePos + 1] & 0xf0) >> 4)) & 0x3f;
					toReturn.append(codec_table[pos]);
				}
				break;
			default:
				// never hanppen
				break;
			}
			curPos += 6;
		}
		if (nn == 2) {
			toReturn.append("==");
		} else if (nn == 4) {
			toReturn.append("=");
		}
		return toReturn.toString();

	}
/**
 * android 自带base64
 * 
 */
//	import android.util.Base64;
//
//	String 变量=android.util.Base64.encodeToString(字符串.getBytes(),Base64.DEFAULT);
//
//	也可简写为
//
//	String 变量=Base64.encodeToString(字符串.getBytes(),Base64.DEFAULT);
//
//	至于解码
//
//	byte b[]=android.util.Base64.decode(字符串,Base64.DEFAULT);
//
//	String 变量=new String(b);
//	byte b[]=android.util.Base64.decode(字符串,Base64.DEFAULT);
	
	/*
     * 将bitmap转换为base64字节数组
     */
    public byte[] Bitmap2Base64(Bitmap bitmap) {
        try {
            // 先将bitmap转换为普通的字节数组
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            byte[] buffer = out.toByteArray();
            // 将普通字节数组转换为base64数组
            byte[] encode = Base64.decode(buffer, Base64.DEFAULT);
            return encode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



	
	/*
	 * 将base64字节数组转换为bitmap
	 */
	public Bitmap Base642Bitmap(byte[] base64) {
	    // 将base64字节数组转换为普通的字节数组
	    byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
	    // 用BitmapFactory创建bitmap
	    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}
}