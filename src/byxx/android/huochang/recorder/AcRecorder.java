package byxx.android.huochang.recorder;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.util.HttpURLConnection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import byxx.android.huochang.AppManager;
import byxx.android.huochang.BaseActivity;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.MyExecutorService;
import byxx.android.huochang.R;

import byxx.android.huochang.stnsh.AcSH;
import byxx.android.huochang.util.ByString;

public class AcRecorder extends ListActivity {
	  private Button mAudioStartBtn;
	    private Button mAudioStopBtn;
	    private File mRecAudioFile;        // 录制的音频文件
	    private File mRecAudioPath;        // 录制的音频文件路徑
		private String path = "byxxdownload";
	    private MediaRecorder mMediaRecorder;// MediaRecorder对象
	    private List<String> mMusicList = new ArrayList<String>();// 录音文件列表
	    private String strTempFile = "recaudio_";// 零时文件的前缀
	    TextView ctime;
//	     Thread threadcount;
	 	Handler handler;
	 	Counttime counttime;
	    @Override
	    protected void onCreate(Bundle savedInstanceState)
	    {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.recorder);

	        mAudioStartBtn = (Button) findViewById(R.id.mediarecorder1_AudioStartBtn);
	        mAudioStopBtn = (Button) findViewById(R.id.mediarecorder1_AudioStopBtn);
	        ctime = (TextView)findViewById(R.id.tvtime);
	        /*按钮状态*/
	        mAudioStartBtn.setEnabled(true);
	        mAudioStopBtn.setEnabled(false);
	        counttime = new Counttime(getHandler());
	        /* 检测是否存在SD卡 */
			String savePAth = Environment.getExternalStorageDirectory() + "/"
					+ path+"/";
	        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
	        {
	            mRecAudioPath =new File(savePAth); //Environment.getExternalStorageDirectory();// 得到SD卡得路径
//	            musicList();// 更新所有录音文件到List中
				if (!mRecAudioPath.exists()) {
					mRecAudioPath.mkdir();

				}
	        }else
	        {
	            Toast.makeText(AcRecorder.this, "没有SD卡", Toast.LENGTH_LONG).show();
	        }
	        


//			File file1 = new File(savePAth);
//
//			if (!file1.exists()) {
//				file1.mkdir();
//			}
//			String savePathString = savePAth + "/" + fileName;
//
//			File file = new File(savePathString);
//
//			if (!file.exists()) {
//				try {
//					file.createNewFile();
//				}
//				catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
	        
	        /* 开始按钮事件监听 */
	        mAudioStartBtn.setOnClickListener(new Button.OnClickListener()    
	        {
	            @Override
	            public void onClick(View arg0)
	            {
	                try
	                {                    
	                    /* ①Initial：实例化MediaRecorder对象 */
	                    mMediaRecorder = new MediaRecorder();
	                    /* ②setAudioSource/setVedioSource*/
	                    mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置麦克风
	                    /* ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
	                     * THREE_GPP(3gp格式，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
	                     * */
	                    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
	                    /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
	                    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
	                    /* ②设置输出文件的路径 */                    
	                    try
	                    {
	                    	
	                        mRecAudioFile = File.createTempFile(strTempFile, ".amr", mRecAudioPath);
	                        
	                    } catch (Exception e)
	                    {
	                        e.printStackTrace(); 
	                    }
	                    mMediaRecorder.setOutputFile(mRecAudioFile.getAbsolutePath());
	                    /* ③准备 */
	                    mMediaRecorder.prepare();
	                    /* ④开始 */
	                    mMediaRecorder.start();
	                    /*按钮状态*/
	                    ctime.setText("");
	                    counttime.setRun(true);
	                    counttime.start();

	                    mAudioStartBtn.setEnabled(false);
	                    mAudioStopBtn.setEnabled(true);
	                } catch (IOException e)
	                {
	                    e.printStackTrace();
	                }
	            }
	        });
	        /* 停止按钮事件监听 */
	        mAudioStopBtn.setOnClickListener(new Button.OnClickListener()
	        {
	            @Override
	            public void onClick(View arg0)
	            {
	                // TODO Auto-generated method stub
	                if (mRecAudioFile != null)
	                {
	                    /* ⑤停止录音 */
	                    mMediaRecorder.stop();
	                    /* 将录音文件添加到List中 */
	                    mMusicList.add(mRecAudioFile.getName());
	                    ArrayAdapter<String> musicList = new ArrayAdapter<String>(AcRecorder.this,
	                            android.R.layout.simple_list_item_1, mMusicList);
	                    setListAdapter(musicList);
	                    /* ⑥释放MediaRecorder */
	                    mMediaRecorder.release();
	                    mMediaRecorder = null;
	                    
	                    /* 按钮状态 */
	                    counttime.setRun(false);
	                    counttime.close();
	                    ctime.setText("");
//	                    update fupdate = new update();
//	                    fupdate.execute(mRecAudioFile);
	                    mAudioStartBtn.setEnabled(true);
	                    mAudioStopBtn.setEnabled(false);
	                }
	            }
	        });
			AppManager.getAppManager().addActivity(this);
	    }

	    /* 播放录音文件 */
	    private void playMusic(File file)
	    {
	        Intent intent = new Intent();
	        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        intent.setAction(android.content.Intent.ACTION_VIEW);
	        /* 设置文件类型 */
	        intent.setDataAndType(Uri.fromFile(file), "audio");
	        startActivity(intent);
	    }

	    @Override
	    /* 当我们点击列表时，播放被点击的音乐 */
	    protected void onListItemClick(ListView l, View v, int position, long id)
	    {
	        /* 得到被点击的文件 */
	        File playfile = new File(mRecAudioPath.getAbsolutePath() + File.separator
	                + mMusicList.get(position));
	        /* 播放 */
	        
	        playMusic(playfile);
	    }

	    /* 播放列表 */
	    public void musicList()
	    {
	        // 取得指定位置的文件设置显示到播放列表
	        File home = mRecAudioPath;
	        if (home.listFiles(new MusicFilter()).length > 0)
	        {
	            for (File file : home.listFiles(new MusicFilter()))
	            {
	                mMusicList.add(file.getName());
	            }
	            ArrayAdapter<String> musicList = new ArrayAdapter<String>(AcRecorder.this,
	                    android.R.layout.simple_list_item_1, mMusicList);
	            setListAdapter(musicList);
	        }
	    }
	    
	    public Handler getHandler() {
			if (handler == null) {
				handler = new Handler() {
					public void handleMessage(Message msg) {
						if(msg != null){
							if(msg.what == 123){
								ctime.setText(ByString.getTimeStr((msg.getData().getLong("count")),"mm分ss秒"));
							}
						}
					}
				};
			}
			return handler;
		}
	    
	    /* 上传文件至Server的方法 */
	      private String uploadFile(File file)
	      {
	        String end ="\r\n";
	        String twoHyphens ="--";
	        String boundary ="*****";
	        try
	        {
	          URL url =new URL("http://10.167.93.128:7001/hyzxWeb/");
	          HttpURLConnection con=(HttpURLConnection)url.openConnection();
	          /* 允许Input、Output，不使用Cache */
	          con.setDoInput(true);
	          con.setDoOutput(true);
	          con.setUseCaches(false);
	          /* 设置传送的method=POST */
	          con.setRequestMethod("POST");
	          /* setRequestProperty */
	          con.setRequestProperty("Connection", "Keep-Alive");
	          con.setRequestProperty("Charset", "UTF-8");
	          con.setRequestProperty("Content-Type",
	                             "multipart/form-data;boundary="+boundary);
	          /* 设置DataOutputStream */
	          DataOutputStream ds =
	            new DataOutputStream(con.getOutputStream());
	          ds.writeBytes(twoHyphens + boundary + end);
	          ds.writeBytes("Content-Disposition: form-data; "+
	                        "name=\"file1\";filename=\""+
	                        123 +"\""+ end);
	          ds.writeBytes(end);  
	          /* 取得文件的FileInputStream */
	          FileInputStream fStream =new FileInputStream(file);
	          /* 设置每次写入1024bytes */
	          int bufferSize =1024;
	          byte[] buffer =new byte[bufferSize];
	          int length =-1;
	          /* 从文件读取数据至缓冲区 */
	          while((length = fStream.read(buffer)) !=-1)
	          {
	            /* 将资料写入DataOutputStream中 */
	            ds.write(buffer, 0, length);
	          }
	          ds.writeBytes(end);
	          ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
	          /* close streams */
	          fStream.close();
	          ds.flush();
	          /* 取得Response内容 */
	          InputStream is = con.getInputStream();
	          int ch;
	          StringBuffer b =new StringBuffer();
	          while( ( ch = is.read() ) !=-1 )
	          {
	            b.append( (char)ch );
	          }
	          ds.close();
	          /* 将Response显示于Dialog */
	         return "上传成功"+b.toString().trim();
	          /* 关闭DataOutputStream */
	        
	        }
	        catch(Exception e)
	        {
	        	 return "上传失败"+e;
	        }
	      }
	      
	      /* 显示Dialog的method */
	      private void showDialog(String mess)
	      {
	        new AlertDialog.Builder(AcRecorder.this).setTitle("Message")
	         .setMessage(mess)
	         .setNegativeButton("确定",new DialogInterface.OnClickListener()
	         {
	           public void onClick(DialogInterface dialog, int which)
	           {          
	           }
	         })
	         .show();
	      }
	      
	      
	      
	      private class update extends AsyncTask<File, Void, String> {
	  		
	  		@Override
	  		protected String doInBackground(File... params) {
	  			
	  			return uploadFile(params[0]);
	  		}
	  		
	  		@Override
	  		protected void onPreExecute() {

	  		}
	  		
	  		@Override
	  		protected void onPostExecute(String result) {

	  			try{
	  				if(isNetworkConnected()||MaStation.getInstance().isTest()){
	  					showDialog(result);
	  				}
	  			}catch(Exception e){

	  			}
	  		
	  		
	  	}
	      
	  	/**
	  	 * 检测网络是否可用
	  	 * @return
	  	 */
	  	public boolean isNetworkConnected() {
	  		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	  		NetworkInfo ni = cm.getActiveNetworkInfo();
	  		return ni != null && ni.isConnectedOrConnecting();
	  	}
	}

	/* 过滤文件类型 */
	class MusicFilter implements FilenameFilter
	{
	    public boolean accept(File dir, String name)
	    {
	        return (name.endsWith(".amr"));
	    }
	    
	    
}
	
	
    class Counttime  implements Runnable{
    	Handler handler;
    	boolean run;
    	private Thread thread = null;
    	public Counttime() {
			// TODO Auto-generated constructor stub
		}
    	public Counttime(Handler handler) {
    		// TODO Auto-generated constructor stub
    		this.handler = handler;
    	}

		public void setRun(boolean run) {
			this.run = run;
		}
		
		public void start() {

			if (thread == null || thread.isInterrupted()) {
				thread = new Thread(this, this.getClass().getName());
//				thread.start();
				MyExecutorService.getInstance().addThread(thread);
			}
		}

		public void close() {
			try {
				if (thread != null && thread.isAlive()) {
					thread.interrupt();
				}
			} catch (Exception e) {
//				MaStation.getInstance().recordExc(e);
			}
			thread = null;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			long count = 0;
			while (run) {
				count = count+1000;
				Message message = Message.obtain();
				message.what = 123;
				Bundle bundle = new Bundle();
				bundle.putLong("count", count);
				message.setData(bundle);
				handler.sendMessage(message);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
    	
    }
	      }
	
