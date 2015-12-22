  package byxx.android.huochang;

import java.io.IOException;
import java.util.TimeZone;
//import java.util.List;

import android.content.Context;
//import android.net.wifi.ScanResult;
//import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
//import android.net.wifi.WifiManager.WifiLock;
import android.text.format.Formatter;

/**
 * 检测Wifi状态类
 * 
 *
 */
public class WifiAdmin {
	// 定义一个WifiManager对象
	private WifiManager mWifiManager;
	// 定义一个WifiInfo对象
	private WifiInfo mWifiInfo;
	// 扫描出的网络连接列表
//	private List<ScanResult> mWifiList;
	// 测试连接IP地址
	private final static String ipAddress = "10.167.93.128";
	private Handler handler;
	private Thread thread;
	// 网络连接列表
//	private List<WifiConfiguration> mWifiConfigurations;
//	WifiLock mWifiLock;
	
	public WifiAdmin(Context context, Handler handler) {
		// 取得WifiManager对象
		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		// 取得WifiInfo对象
		mWifiInfo = mWifiManager.getConnectionInfo();
		this.handler = handler;
	}
	
//	// 获取信号强度
//	public int getWifiStrength() {
//		return (mWifiManager == null)? 0 : WifiManager.calculateSignalLevel(mWifiInfo.getRssi(), 5);
//	}
	
	// 获取网关
	public String getGateWay() {
		return (mWifiManager == null)? null : Formatter.formatIpAddress(mWifiManager.getDhcpInfo().gateway);
	}

//	// 打开wifi
//	public void openWifi() {
//		if (!mWifiManager.isWifiEnabled()) {
//			mWifiManager.setWifiEnabled(true);
//		}
//	}
//
//	// 关闭wifi
//	public void closeWifi() {
//		if (mWifiManager.isWifiEnabled()) {
//			mWifiManager.setWifiEnabled(false);
//		}
//	}

	// 检查当前wifi状态
	/* Wifi状态
	 	0:wifiManager.WIFI_STATE_DISABLING	正在断开
		1:wifiManager.WIFI_STATE_DISABLED	不可用
		2:wifiManager.WIFI_STATE_ENABLING	正在连接
		3:wifiManager.WIFI_STATE_ENABLED	可用
	*/
	public String getWifiState() {
		String state = "";
		switch(mWifiManager.getWifiState()) {
			case 0: 
				state = "正在断开";
				break;
			case 1:
				state = "未连接";
				break;
			case 2:
				state = "正在连接";
				break;
			case 3:
				state = "已连接";
				break;
			default:
				state = null;
				break;
		}
		return state;
	}

//	// 锁定wifiLock
//	public void acquireWifiLock() {
//		mWifiLock.acquire();
//	}
//
//	// 解锁wifiLock
//	public void releaseWifiLock() {
//		// 判断是否锁定
//		if (mWifiLock.isHeld()) {
//			mWifiLock.acquire();
//		}
//	}
//
//	// 创建一个wifiLock
//	public void createWifiLock() {
//		mWifiLock = mWifiManager.createWifiLock("test");
//	}
//
//	// 得到配置好的网络
//	public List<WifiConfiguration> getConfiguration() {
//		return mWifiConfigurations;
//	}
//
//	// 指定配置好的网络进行连接
//	public void connetionConfiguration(int index) {
//		if (index > mWifiConfigurations.size()) {
//			return;
//		}
//		// 连接配置好指定ID的网络
//		mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId,
//				true);
//	}

//	// 开始扫描网络
//	public void startScan() {
//		mWifiManager.startScan();
//		// 得到扫描结果
//		mWifiList = mWifiManager.getScanResults();
//		// 得到配置好的网络连接
//	}
//
//	// 得到网络列表
//	public List<ScanResult> getWifiList() {
//		return mWifiList;
//	}

//	// 查看扫描结果
//	public StringBuffer lookUpScan() {
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < mWifiList.size(); i++) {
//			sb.append("Index_" + new Integer(i + 1).toString() + ":");
//			// 将ScanResult信息转换成一个字符串包
//			// 其中包括：BSSID、SSID、capabilities、frequency、level
//			sb.append((mWifiList.get(i)).toString()).append("\n");
//		}
//		return sb;
//	}

	
	/*
	 * getBSSID() 获取BSSID
       getDetailedStateOf() 获取客户端的连通性
       getHiddenSSID() 获得SSID 是否被隐藏
       getIpAddress() 获取IP 地址
       getLinkSpeed() 获得连接的速度
       getMacAddress() 获得Mac 地址
       getSSID() 获得SSID
       getSupplicanState() 返回具体客户端状态的信息
       getRssi() 获取RSSI 返回信号强度0~-100
       getNetworkId 得到连接的ID
	 */
	public int getLinkSpeed() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getLinkSpeed();
	}
	
	public String getSSID() {
		return (mWifiInfo == null) ? "" : mWifiInfo.getSSID();
	}
	
	public String getMacAddress() {
		return (mWifiInfo == null) ? "" : mWifiInfo.getMacAddress();
	}
	
	public String getBSSID() {
		return (mWifiInfo == null) ? "" : mWifiInfo.getBSSID();
	}

	public String getIpAddress() {
		return Formatter.formatIpAddress((mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress());
	}
	
	public int getNetWorkId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}
	
	public int getRssi() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getRssi();
	}

	// 得到wifiInfo的所有信息
	public String getWifiInfo() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
	}
	
	// ping IPAddress测试
	public String pingIpAddress() {
        String mPingIpAddrResult = null;
	    try {
	        Process p = Runtime.getRuntime().exec("ping -c 4 " + ipAddress);
	        int status = p.waitFor();
	        if (status == 0) {
	            mPingIpAddrResult = "网络可达";
	        }
	        else {
	            mPingIpAddrResult = "网络不可达";
	        }
	    }
	    catch (IOException e) {
//	    	MaStation.getInstance().recordExc(e, false);
	    }
	    catch (InterruptedException e) {
//	    	MaStation.getInstance().recordExc(e, false);
	    }
		return mPingIpAddrResult;

	}
	
	public void newThread() {
		thread = new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				try {
					String result = pingIpAddress();
					Message msg = new Message();
					msg.what = Constant.PING;
					Bundle bundle = new Bundle();
					bundle.putString("pingIpAddress", result);
					msg.setData(bundle);
					handler.sendMessage(msg);
				}
				catch (Exception e) {
//					MaStation.getInstance().recordExc(e, false);
				}
			}
			
		});
		thread.start();
	}
	
	public void closeThread() {
		try {
			if (thread != null && thread.isAlive()) {
				thread.interrupt();
			}
		}
		catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		thread = null;
	}
	
	public String getPingIpAddress() {
		return ipAddress;
	}

	// 添加一个网络并连接
//	public void addNetWork(WifiConfiguration configuration) {
//		int wcgId = mWifiManager.addNetwork(configuration);
//		mWifiManager.enableNetwork(wcgId, true);
//	}

	// 断开指定ID的网络
//	public void disConnectionWifi(int netId) {
//		mWifiManager.disableNetwork(netId);
//		mWifiManager.disconnect();
//	}
	
	// 获取当前系统时间
	public String getTime() {
		return DateFormat.format("yyyy-MM-dd kk:mm:ss", System.currentTimeMillis()).toString();
	}
	
	// 获取当前系统时区
	public String getTimeZone() {
		return (TimeZone.getDefault() == null )? null : TimeZone.getDefault().getID();
	}


}
