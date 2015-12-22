package byxx.android.huochang.recorder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import android.graphics.Bitmap;
import byxx.android.huochang.picture.BASE64Encoder;
import byxx.android.huochang.picture.Zipper;

public class MoRecorder {
	
	
	  /**
	    * 读取文件内容
	    * @param filename 文件名称
	    * @return   文件内容
	    * @throws Exception
	    */
	    public String read(String filepath) throws Exception{
	    	//方法openFileInput(filename)会默认从data/当前应
	    	//用包下/files目录下读取
	    FileInputStream inStream =  new FileInputStream(filepath);
	    //从内存输出的数据流对象
	    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	    //从文件中读取数据，先把输入流中的数据读取到一个字节数组中，//输入流中的数据可能很多，应该利用循环实现
	    byte[] buffer = new byte[1024];//定义一个字节数组
	    int len = 0;
	    //若未达到文件尾，就一直读
	    while((len = inStream.read(buffer)) != -1){
	    //得到每次读取到的数据
	    outStream.write(buffer, 0, len);
	    }
	    byte[] data = Zipper.gZipObject(outStream.toByteArray());
	    outStream.flush();
	    outStream.close();
	    return new BASE64Encoder().encode(data);
	    }
	
	    
}
