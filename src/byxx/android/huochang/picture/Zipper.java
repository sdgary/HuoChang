package byxx.android.huochang.picture;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zipper {

	// 压缩GZip
	public static byte[] gZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(data);
			gzip.finish();
			gzip.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	// 解压GZip
	public static byte[] unGZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			GZIPInputStream gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			b = baos.toByteArray();
			baos.flush();
			baos.close();
			gzip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	// 压缩Zip
	public static byte[] zip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ZipOutputStream zip = new ZipOutputStream(bos);
			ZipEntry entry = new ZipEntry("zip");
			entry.setSize(data.length);
			zip.putNextEntry(entry);
			zip.write(data);
			zip.closeEntry();
			zip.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	// 解压Zip
	public static byte[] unZip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ZipInputStream zip = new ZipInputStream(bis);
			while (zip.getNextEntry() != null) {
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((num = zip.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				b = baos.toByteArray();
				baos.flush();
				baos.close();
			}
			zip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	// 将对象转换为byte[]
	public static byte[] getObjectBytes(Object object) {
		byte[] ob = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.close();
			bos.close();
			ob = bos.toByteArray();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return ob;
	}

	public static Object getBytesObject(byte[] ob) {
		ByteArrayInputStream bis = new ByteArrayInputStream(ob, 0, ob.length);
		try {
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object obj = ois.readObject();
			ois.close();
			bis.close();
			return obj;
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 压缩GZip
	public static byte[] gZipObject(Object object) {
		byte[] b = getObjectBytes(object);
		if (b != null) {
			return gZip(b);
		}
		return b;
	}

	// 解压GZip
	public static Object unGZipObject(byte[] data) {
		byte[] b = unGZip(data);
		if (b != null) {
			return getBytesObject(b);
		}
		return null;
	}

	// 压缩Zip
	public static byte[] zipObject(Object object) {
		byte[] b = getObjectBytes(object);
		if (b != null) {
			return zip(b);
		}
		return b;
	}

	// 解压Zip
	public static Object unZipObject(byte[] data) {
		byte[] b = unZip(data);
		if (b != null) {
			return getBytesObject(b);
		}
		return null;
	}

	public static void main(String[] args) {
		Vector a = new Vector();
		a.addElement("我们都是好孩子1");
		a.addElement("我们都是好孩子2");
		a.addElement("我们都是好孩子3");
		a.addElement("我们都是好孩子4");

		byte[] b1 = zipObject(a);
		System.out.println(b1.length);
		System.out.println("unZip:" + (Vector) unZipObject(b1));

		byte[] b5 = gZipObject(a);
		System.out.println(b5.length);
		System.out.println("unGZip2:" + (Vector) unGZipObject(b5));

	}
}
