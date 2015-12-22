package byxx.android.huochang.picture;

import java.io.FileOutputStream;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MoPicture {
	
	
//	@Override
//	public String getImg(int imgID) {
//		long t = System.currentTimeMillis();
//		ImageIcon img = new ImageIcon(getClass().getResource("/byxx/hyzx/ws/test.jpg"));
//		byte[] tb = Zipper.gZipObject(img);
//		String rs = new BASE64Encoder().encode(tb);
//		String a = HyzxJson.getJson(rs);
//
//		System.out.println("转码:" + (float) (System.currentTimeMillis() - t)
//				/ 1000);
//		return a;
//	}

//	@Override
//	public String saveImg(String imgData) {
//		long t = System.currentTimeMillis();
//
//		GsonBuilder gb = new GsonBuilder();
//		Gson gson = gb.create();
//		String testStr = gson.fromJson(imgData, String.class);
//		try {
//			byte[] bt = new sun.misc.BASE64Decoder().decodeBuffer(testStr);
//			ImageIcon icon = (ImageIcon) Zipper.unGZipObject(bt);
//
//			BufferedImage image = new BufferedImage(icon.getIconWidth(),
//					icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
//			Graphics g = image.getGraphics();
//			g.drawImage(icon.getImage(), 0, 0, null);
//
//			// 保存文件PNG
//			boolean encodeAlpha = false;
//			int filter = 0;
//			int compressionLevel = 1;
//			byte[] pngbytes;
//			PngEncoderB png = new PngEncoderB(image,
//					(encodeAlpha) ? PngEncoder.ENCODE_ALPHA
//							: PngEncoder.NO_ALPHA, filter, compressionLevel);
//
//			FileOutputStream outfile = new FileOutputStream("c:/" + t + ".png");
//			pngbytes = png.pngEncode();
//			if (pngbytes != null) {
//				outfile.write(pngbytes);
//			} else {
//				System.out.println("Null image");
//			}
//			outfile.flush();
//			outfile.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("解码:" + (float) (System.currentTimeMillis() - t)
//				/ 1000);
//		return null;
//	}
}
