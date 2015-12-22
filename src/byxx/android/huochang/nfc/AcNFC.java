package byxx.android.huochang.nfc;


import byxx.android.huochang.AppManager;
import byxx.android.huochang.Constant;
import byxx.android.huochang.MaStation;
import byxx.android.huochang.R;
import byxx.android.huochang.SuperApplication;
import byxx.android.wj.http.cache.LoadingDialog;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnDismissListener;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.widget.TextView;

public class AcNFC extends Activity {
	private NfcAdapter mAdapter;
	private PendingIntent mPendingIntent;
	private IntentFilter[] mFilters;
	private String[][] mTechLists;
	private TextView mText;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nfc);
		SuperApplication.getInstance().addActivity(this);
		mText = (TextView) findViewById(R.id.textView1);
		mText.setText(" ");
		startNFC();
		if(MaStation.getInstance().getMoNfc().isIdOrData()){
			getNfc(getIntent());
		}else{
			processIntent(getIntent());
		}
		AppManager.getAppManager().addActivity(this);
	}

	private void startNFC() {
		try {
			try {
				mAdapter = NfcAdapter.getDefaultAdapter(this);
			} catch (Exception e) {
				MaStation.getInstance().recordExc(e);
			}
			if (mAdapter == null) {
				mText.setText("配备不支持NFC!");
				return;
			}
			if (!mAdapter.isEnabled()) {
				mText.setText("请在系统设置中先启用NFC功能!");
				return;
			}
			mPendingIntent = PendingIntent.getActivity(this, 0,
					new Intent(this, getClass())
							.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
			IntentFilter ndef = new IntentFilter(
					NfcAdapter.ACTION_NDEF_DISCOVERED);
			try {
				ndef.addDataType("*/*");
			} catch (MalformedMimeTypeException e) {
				MaStation.getInstance().recordExc(e);
				throw new RuntimeException("fail", e);
			} catch (Exception e) {
				MaStation.getInstance().recordExc(e);
			}
			mFilters = new IntentFilter[] { ndef, };
			// mTechLists = new String[][] { new String[] { MifareClassic.class
			// .getName() } };
			// mTechLists = new String[][] { new String[] { MifareClassic.class
			// .getName() } };
			mTechLists = new String[][] {
					new String[] { MifareClassic.class.getName() },
					new String[] { MifareUltralight.class.getName() },
					new String[] { NfcA.class.getName() },
					new String[] { NfcB.class.getName() },
					new String[] { NfcF.class.getName() },
					new String[] { NfcV.class.getName() },
					new String[] { Ndef.class.getName() },
					new String[] { NdefFormatable.class.getName() },
					new String[] { IsoDep.class.getName() } };
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	public void onResume() {
		super.onResume();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {  
			processIntent(getIntent());  
	    }  
		
		try {
			if (mAdapter != null && mAdapter.isEnabled()) {
				mAdapter.enableForegroundDispatch(this, mPendingIntent,
						mFilters, mTechLists);
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		try {
			Handler handler = MaStation.getInstance().getMoNfc().getHandler();
			if (handler != null) {
				finish();
			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	public void onNewIntent(Intent intent) {
		getNfc(intent);
		processIntent(getIntent());
	}

	// private void getNfc(Intent intent) {
	// try {
	// byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
	// if (id != null && id.length > 0) {
	// String regionId = bytesToHexString(id);
	// mText.setText("检测到卡Id:" + regionId);
	// //
	// Handler handler = MaStation.getInstance().getMoNfc()
	// .getHandler();
	// if (handler != null) {
	// Message msg = new Message();
	// msg.what = Constant.MSG_ID_NFC;
	// Bundle tBundle = new Bundle();
	// tBundle.putString("regionId", regionId);
	// msg.setData(tBundle);
	// handler.sendMessage(msg);
	// }
	// }
	// } catch (Exception e) {
	// MaStation.getInstance().recordExc(e);
	// }
	// }

	private void getNfc(Intent intent) {
		try {
			byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
			String regionId = null;
			if (id != null && id.length > 0) {
				regionId = bytesToHexString(id);
//				mText.setText("检测到卡Id:" + regionId);
			}
			//
			Handler handler = MaStation.getInstance().getMoNfc().getHandler();
			Handler handlerFunc = MaStation.getInstance().getMoNfc()
					.getHandlerFunc();
			/**
			 * MaStation.getInstance().getMoNfc().isIdOrData()
			 * true 取id
			 * false 代表取内容
			 */
			if (MaStation.getInstance().getMoNfc().isIdOrData() 
					&& (handler != null || handlerFunc != null)) {
				if (regionId != null) {
					String mess = "CardNo:" + regionId;

				}
				if (handler != null) {
					Message msg = new Message();
					msg.what = Constant.MSG_ID_NFC;
					Bundle tBundle = new Bundle();
					if (regionId == null) {
						tBundle.putString("regionId", "NO_CHECK");
					} else {
						tBundle.putString("regionId", regionId);
					}
					msg.setData(tBundle);
					handler.sendMessage(msg);
				} else if (handlerFunc != null && regionId != null) {
					Message msg = new Message();
					msg.what = Constant.MSG_ID_NFC;
					Bundle tBundle = new Bundle();
					tBundle.putString("regionId", regionId);
					msg.setData(tBundle);
					handlerFunc.sendMessage(msg);
				}
			}
//			else if( !MaStation.getInstance().getMoNfc().isIdOrData() 
//					&& (handler != null || handlerFunc != null)){
//				if (regionId != null) {
//					String mess = "CardNo:" + regionId;
//
//				}
//				if (handler != null) {
//					Message msg = new Message();
//					msg.what = Constant.MSG_ID_NFC;
//					Bundle tBundle = new Bundle();
//					if (regionId == null) {
//						tBundle.putString("regionId", "NO_CHECK");
//					} else {
//						tBundle.putString("regionId", regionId);
//					}
//					msg.setData(tBundle);
//					handler.sendMessage(msg);
//				} else if (handlerFunc != null && regionId != null) {
//					Message msg = new Message();
//					msg.what = Constant.MSG_ID_NFC;
//					Bundle tBundle = new Bundle();
//					tBundle.putString("regionId", regionId);
//					msg.setData(tBundle);
//					handlerFunc.sendMessage(msg);
//				}
//			}
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	public void onPause() {
		super.onPause();
		try {
			if (mAdapter != null && mAdapter.isEnabled())
				mAdapter.disableForegroundDispatch(this);
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	// 字符序列转换为16进制字符串
	private String bytesToHexString(byte[] src) {
		try {
			StringBuilder stringBuilder = new StringBuilder("0x");
			if (src == null || src.length <= 0) {
				return null;
			}
			char[] buffer = new char[2];
			for (int i = 0; i < src.length; i++) {
				buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
				buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
				stringBuilder.append(buffer);
			}
			return stringBuilder.toString();
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
		return null;
	}

	/**
	 * Parses the NDEF Message from the intent and prints to the TextView
	 */
	private void processIntent(Intent intent) {
		try {
			byte[] id = intent.getByteArrayExtra(mAdapter.EXTRA_ID);
			System.out.println("Id:" + bytesToHexString(id));
			// 取出封装在intent中的TAG android.nfc.extra.ID

			Tag tagFromIntent = intent.getParcelableExtra(mAdapter.EXTRA_TAG);
			for (String tech : tagFromIntent.getTechList()) {
				System.out.println(tech);
			}
			boolean auth = false;
			// 读取TAG;
			MifareClassic mfc = MifareClassic.get(tagFromIntent);
			if(mfc != null){
				try {
					String metaInfo = "";
					// Enable I/O operations to the tag from this TagTechnology
					// object.
					mfc.connect();//此处出现ioException
					int type = mfc.getType();// 获取TAG的类型
					int sectorCount = mfc.getSectorCount();// 获取TAG中包含的扇区数
					String typeS = "";
					switch (type) {
					case MifareClassic.TYPE_CLASSIC:
						typeS = "TYPE_CLASSIC";
						break;
					case MifareClassic.TYPE_PLUS:
						typeS = "TYPE_PLUS";
						break;
					case MifareClassic.TYPE_PRO:
						typeS = "TYPE_PRO";
						break;
					case MifareClassic.TYPE_UNKNOWN:
						typeS = "TYPE_UNKNOWN";
						break;
					}
					metaInfo += "卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"
							+ mfc.getBlockCount() + "个块\n存储空间: " + mfc.getSize()
							+ "B\n";
//					for (int j = 0; j < sectorCount; j++) {
//						// Authenticate a sector with key A.
//						auth = mfc.authenticateSectorWithKeyA(j,
//								MifareClassic.KEY_DEFAULT);
//						int bCount;
//						int bIndex;
//						if (auth) {
//							metaInfo += "Sector " + j + ":验证成功\n";
//							// 读取扇区中的块
//							bCount = mfc.getBlockCountInSector(j);
//							bIndex = mfc.sectorToBlock(j);
//							for (int i = 0; i < bCount; i++) {
//								byte[] data = mfc.readBlock(bIndex);
//								metaInfo += "Block " + bIndex + " : "
//										+ bytesToHexString(data) + "\n";
//								bIndex++;
//							}
//						} else {
//							metaInfo += "Sector " + j + ":验证失败\n";
//						}
//					}
					auth = mfc.authenticateSectorWithKeyA(0,
							MifareClassic.KEY_DEFAULT);
					String we = null;
					if (auth) {//auth
						byte[] data = mfc.readBlock(1);
						we = new String(data).trim();//jobID
//						String str = "";
//						str = bytesToHexString(data);
//						for (int i = 0; i < data.length; i++) {
////							char a = (char)();
//							if(data[i] == 0){
//								return;
//							}
//							str = str + String.format("%02X", data[i]);
//						}
//						str = str.substring(0, 10);
//					    int number = Integer.parseInt(str);
//					    we = String.valueOf(number); //jobID
						Handler handlerFunc = MaStation.getInstance().getMoNfc().getHandlerFunc();
						if (!MaStation.getInstance().getMoNfc().isIdOrData() && handlerFunc != null) {
							if (we != null) {
								Message msg = new Message();
								msg.what = Constant.MSG_NFC_DATA;
								Bundle tBundle = new Bundle();
								tBundle.putString("blockData", we);
								msg.setData(tBundle);
								handlerFunc.sendMessage(msg);
							}
						}
					}
//					mText.setText(bytesToHexString(id));
					mfc.close();
				} catch (Exception e) {
					MaStation.getInstance().recordExc(e);
				}
			}else{
//				MSG_NFC_NULL
				Handler handlerFunc = MaStation.getInstance().getMoNfc().getHandlerFunc();
				if(handlerFunc != null) {
					Message msg = new Message();
					msg.what = Constant.MSG_NFC_NULL;
					Bundle tBundle = new Bundle();
					tBundle.putString("blockData", null);
					msg.setData(tBundle);
					handlerFunc.sendMessage(msg);
					
				}
			}
			
		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
		}
	}

	public String convertStringToHex(String str){

		char[] chars = str.toCharArray();

		StringBuffer hex = new StringBuffer();
		for(int i = 0; i < chars.length; i++){
			hex.append(Integer.toHexString((int)chars[i]));
		}

		return hex.toString();
	}

//	public static String bytesToHexString(byte[] src){
//		StringBuilder stringBuilder = new StringBuilder("");
//		if (src == null || src.length <= 0) {
//			return null;
//		}
//		for (int i = 0; i < src.length; i++) {
//			int v = src[i] & 0xFF;
//			String hv = Integer.toHexString(v);
//			if (hv.length() < 2) {
//				stringBuilder.append(0);
//			}
//			stringBuilder.append(hv);
//		}
//		return stringBuilder.toString();
//	}
//	public 	String getNfcTag(){
//		
//	}
}