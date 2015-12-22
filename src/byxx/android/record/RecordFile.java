package byxx.android.record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.os.Environment;
import android.util.Log;

public class RecordFile {

	private static String path = "byxxdownload";

	/**
	 * 写入文件
	 * 
	 * @param directory
	 * @param file
	 * @param obj
	 */
	public static boolean writeFile(String fileName, Object obj) {
		boolean b = false;
		String filepath = null;
		ObjectOutputStream oos = null;
		try {
			// 检查文件目录
			String savePAth = Environment.getExternalStorageDirectory() + "/"
					+ path;
			File file1 = new File(savePAth);
			if (!file1.exists()) {
				file1.mkdir();
			}
			filepath = savePAth + "/" + fileName;
			//
			oos = new ObjectOutputStream(new FileOutputStream(filepath));
			oos.writeObject(obj);
			oos.close();
//			Log.v("1011","-writeFile-- "+fileName+" "+obj.toString());
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return b;
	}

	/**
	 * 从文件中读取数据
	 * 
	 * @param directory
	 * @param file
	 * @return
	 */
	public static Object readFile(String fileName) {
		// 检查文件目录
		String savePAth = Environment.getExternalStorageDirectory() + "/"
				+ path;
		String filepath = savePAth + "/" + fileName;
		//
		ObjectInputStream ois = null;
		Object obj = null;
		try {
			File f1 = new File(filepath);
			if (!f1.exists()) {
				return obj;
			}
			ois = new ObjectInputStream(new FileInputStream(filepath));
			obj = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			obj = null;
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

	/**
	 * 写入文件
	 * 
	 * @param directory
	 * @param file
	 * @param obj
	 */
	public static boolean writeFile(String fileName, String[] ss, boolean append) {
		boolean b = false;
		String filepath = null;
		ObjectOutputStream oos = null;
		try {
			// 检查文件目录
			String savePAth = Environment.getExternalStorageDirectory() + "/"
					+ path;
			File file1 = new File(savePAth);
			if (!file1.exists()) {
				file1.mkdir();
			}
			filepath = savePAth + "/" + fileName;
			//
			oos = new ObjectOutputStream(new FileOutputStream(filepath, append));
			for (int i = 0; i < ss.length; i++) {
				oos.writeBytes(ss[i]);
			}
			oos.flush();
			oos.close();
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return b;
	}

	/**
	 * 写入文件
	 * 
	 * @param directory
	 * @param file
	 * @param obj
	 */
	public static boolean writeFile(String fileName, String ss, boolean append) {
		boolean b = false;
		String filepath = null;
		ObjectOutputStream oos = null;
		try {
			// 检查文件目录
			String savePAth = Environment.getExternalStorageDirectory() + "/"
					+ path;
			File file1 = new File(savePAth);
			if (!file1.exists()) {
				file1.mkdir();
			}
			filepath = savePAth + "/" + fileName;
			//
			oos = new ObjectOutputStream(new FileOutputStream(filepath, append));
			oos.writeBytes(ss);
			oos.flush();
			oos.close();
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return b;
	}
}
