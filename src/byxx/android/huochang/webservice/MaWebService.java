package byxx.android.huochang.webservice;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
//import byxx.android.dto.movebox.JzxHwmlModifyDTO;

import byxx.android.huochang.MaStation;
import byxx.android.huochang.http.PostUtil;
import byxx.android.huochang.http.StandReturnInfo;
import byxx.android.huochang.movebox.JzxHwzwModifyDTO;
import byxx.android.huochang.task.GdContainerPos;
import byxx.android.huochang.traininfo.CarDto;
import byxx.android.huochang.user.User;
//import byxx.android.huochang.user.UserDto;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MaWebService{
	private String nameSpace = null;
	private String URL = null;
	private Context context;
	public MaWebService(Context context){
		this.context = context;
	}
	/**
	 * 分析成键值对应关系
	 * 
	 * @param soapObject
	 * @return
	 */
	public static Hashtable<String, Object> toHashtable(SoapObject soapObject) {
		Hashtable<String, Object> tHashtable = new Hashtable<String, Object>();
		try {
			SoapObject tSoapObject = null;
			SoapPrimitive tSoapPrimitive = null;
			PropertyInfo tPropInfo = null;
			Object tObject = null;
			for (int i = 0; i < soapObject.getPropertyCount(); i++) {
				tPropInfo = new PropertyInfo();
				soapObject.getPropertyInfo(i, tPropInfo);
				tObject = soapObject.getProperty(i);
				if (tObject instanceof SoapPrimitive) {
					tSoapPrimitive = (SoapPrimitive) tObject;
					tHashtable.put(tPropInfo.getName(), tSoapPrimitive
							.toString().trim());
				} else if (tObject instanceof SoapObject) { // 需在下一步继续分析处理
					tSoapObject = (SoapObject) tObject;
					tHashtable.put(tPropInfo.getName(), tSoapObject);
				}
			}
		} catch (Exception e) {
//			MaStation.getInstance().recordExc(e);
		}
		return tHashtable;
	}

	public String getNameSpace() {
		if (nameSpace == null) {
			nameSpace = "http://ws.hyzx.byxx/";   //大朗站

		}
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public String getURL() {
		if (URL == null) {
			URL = MaStation.getInstance().getURL();
		}
		return URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}

//	private SoapObject call(String method, Properties pro,boolean isNeedCache) throws Exception {
//		SoapObject soapObject = null;
//		try {
//			if (isNetworkConnected()||!isNeedCache) {
//				soapObject = callBody(method, pro);
//			}else {
//				CacheDto cacheDto = new CacheDto();
//				cacheDto.setUrl(method);
//				cacheDto.setDto(pro);
//				HttpCacheService.db.addData(cacheDto);
//				soapObject = new SoapObject();
//				SoapPrimitive soPrimitive =new SoapPrimitive("http://www.w3.org/1999/XMLSchema", "0", "当前网络信息不好,已将网络请求缓存至后台");
//				soapObject.addProperty("0", soPrimitive);
//			}
//		} 
//		catch (EOFException e) {
//			e.printStackTrace();
//		} catch (ConnectException e) {
//			e.printStackTrace();
//		} catch (RuntimeException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			if(e instanceof AppException)
//				throw (AppException)e;
//			throw AppException.network(e);
//		} finally {
//		}
//		return soapObject;
//	}

	private SoapObject callBody(String method, Properties pro)
			throws Exception {
//		long ti = MaStation.getInstance().getSystemCurrentTimeMillis();
//		String mess = "";
//		String s1 = "start0:" + ByString.getTimeStr(ti, "HH:mm:ss");
//		mess = mess + s1;
//		System.out.println(s1);

		String tNamespace = getNameSpace();
		SoapObject tRequest = new SoapObject(tNamespace, method);
		if (pro != null) {
			Enumeration<Object> tEn = pro.keys();
			String tKey = null;
			while (tEn.hasMoreElements()) {
				tKey = (String) tEn.nextElement();
				tRequest.addProperty(tKey, pro.get(tKey));
			}
		}
//		s1 = "start1:"
//				+ (MaStation.getInstance().getSystemCurrentTimeMillis() - ti);
//		mess = mess + " " + s1;
//		System.out.println(s1);
		//
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);
		envelope.bodyOut = tRequest; // 设置参数
		envelope.dotNet = false;
		//envelope.encodingStyle=SoapEnvelope.ENC;
		(new MarshalBase64()).register(envelope);

//		s1 = "start2:"
//				+ (MaStation.getInstance().getSystemCurrentTimeMillis() - ti);
//		mess = mess + " " + s1;
//		System.out.println(s1);

		String tURL = getURL();
		HttpTransportSE hTransport = new HttpTransportSE(tURL);

		hTransport.debug = true;
		// hTransport
		// .setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		// 设置请求格式
		String SOAP_ACTION = tNamespace + method;

//		s1 = "start3:"
//				+ (MaStation.getInstance().getSystemCurrentTimeMillis() - ti)
//				+ " " + SOAP_ACTION;
//		mess = mess + " " + s1;
//		System.out.println(s1);
		try{
		hTransport.call(SOAP_ACTION, envelope);
		}catch(Exception e){
			Log.v("wb",e+"");
			//12-10 10:10:44.429: VERBOSE/wb(24509): org.xmlpull.v1.XmlPullParserException: expected: END_TAG {http://schemas.xmlsoap.org/soap/envelope/}Body (position:END_TAG </{http://schemas.xmlsoap.org/soap/envelope/}soap:Fault>@1:332 in java.io.InputStreamReader@4153edb8) 

		}
//		s1 = "start4:"
//				+ (MaStation.getInstance().getSystemCurrentTimeMillis() - ti);
//		mess = mess + " " + s1;
//		System.out.println(s1);
//		noMess.mess = mess;

		return (envelope.getResponse() != null ? (SoapObject) envelope.bodyIn: null);
	}

	public String getjsonString(SoapObject tSoapObject){
		String s = null;
		try {
			if (tSoapObject == null)
				return null;
			PropertyInfo tPropInfo = new PropertyInfo();
			tSoapObject.getPropertyInfo(0, tPropInfo);
			Object tObject = tSoapObject.getProperty(0);
			if (tObject instanceof SoapPrimitive) {
				SoapPrimitive tSoapPrimitive = (SoapPrimitive) tObject;
				if (tSoapPrimitive != null) {
					s = tSoapPrimitive.toString();
				}
			} 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return s;
	}

	/**
	 * 登录
	 * 
	 * @param stationCode
	 * @param userCode
	 * @param password
	 * @param pdaId
	 * @return
	 */
	public User login(String stationCode, String userCode, String password,
			String pdaId) {
//		UserDto mUser = null;
		User nUser = null;
		String s = null;
		try {
			if(!MaStation.getInstance().isTest()){
				Properties tProperties = new Properties();
				tProperties.put("stationCode", stationCode);
				tProperties.put("userCode", userCode);
				tProperties.put("password", password);
				tProperties.put("pdaId", pdaId);

				String url = getURL() + "getUserDto";
				s = PostUtil.getInstance().post(url, tProperties, false);
				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							nUser = JSON.parseObject(info.getData().toString(), User.class);
							MaStation.getInstance().setUser(nUser);
						}

					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						nUser = JSON.parseObject(info.getData().toString(), User.class);
						MaStation.getInstance().setUser(nUser);
					}

				}
			}else{
				s = "{\"code\":\"1012\",\"imgUrl\":\"http://10.167.93.128:7001/hyzx_ws/img/\",\"imgUrlMini\":\"http://10.167.93.128:7001/hyzx_ws/img_mini/\",\"loginState\":0,\"name\":\"装卸南区二组\",\"password\":\"2\",\"postName\":\"大朗装卸南区@二组\",\"stationCode\":\"DLQ\",\"stationName\":\"大朗\",\"sysTime\":1389261533627,\"talkBackNum\":0,\"teams\":\"3\",\"workDay\":\"2014010902\",\"workGdm\":\"010,011,012,013\"}";     
				GsonBuilder gb = new GsonBuilder();
				gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
				Gson gson = gb.create();
				if(s != null){
					MaStation.getInstance().setUser(gson.fromJson(s, User.class));
				}
			}

		} catch (Exception e) {
			MaStation.getInstance().recordExc(e);
			MaStation.getInstance().getUser().setCode(User.CODE_ERR_NW);
		}
		return nUser;
	}
	
//	public String getGdxc(@WebParam(name = "stationCode") String stationCode,
//			@WebParam(name = "gdm") String gdm);
	/**
	 * 接口方法就是传站码和股道码,返回该条股道的现车信息 
	 * @param stationCode   
	 * @param gdm
	 * @return
	 */
	public List<CarDto> getGdxc(String stationCode,String gdm){
		List<CarDto> nList = null;
		String s  = null;
		try {
			if(!MaStation.getInstance().isTest()){
				Properties tProperties = new Properties();
				tProperties.put("stationCode", stationCode);
				tProperties.put("gdm", gdm);
				
				String url = getURL() + "getGdxc";
				s = PostUtil.getInstance().post(url, tProperties, true);
				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							nList = JSON.parseArray(info.getData().toString(), CarDto.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						nList = JSON.parseArray(info.getData().toString(), CarDto.class);
					}
				}

			}else{
					if(gdm.equals("010")){
					s = "[{\"ch\":\"5231342\",\"cid\":\"DLQ2014010100275231342\",\"cz\":\"X6K\",\"dzh\":\"JCQ\",\"dzm\":\"江村\",\"fzh\":\"DLQ\",\"fzm\":\"大朗\",\"gdm\":\"010\",\"hc\":1.2,\"jsl\":\"扣修\",\"kzbz\":\"0\",\"pb\":0,\"pm\":\"空\",\"swh\":1,\"zaiz\":0,\"ziz\":17.6},{\"ch\":\"5230840\",\"cid\":\"DLQ2014010100275230840\",\"cz\":\"X6K\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"YEJ\",\"fzm\":\"银川南\",\"gdm\":\"010\",\"hc\":1.2,\"jsl\":\"朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"swh\":2,\"zaiz\":60,\"ziz\":17.6},{\"ch\":\"5280542\",\"cid\":\"DLQ2014010100275280542\",\"cz\":\"NX17B\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"YEJ\",\"fzm\":\"银川南\",\"gdm\":\"010\",\"hc\":1.5,\"jsl\":\"朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"swh\":3,\"zaiz\":60,\"ziz\":22.4},{\"ch\":\"5252877\",\"cid\":\"DLQ2014010100275252877\",\"cz\":\"X6B\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"HYQ\",\"fzm\":\"衡阳\",\"gdm\":\"010\",\"hc\":1.5,\"jsl\":\"朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"swh\":4,\"zaiz\":60,\"ziz\":22.4},{\"ch\":\"4897442\",\"cid\":\"DLQ2014010100274897442\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"HYQ\",\"fzm\":\"衡阳\",\"gdm\":\"010\",\"hc\":1.2,\"jsl\":\"朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"swh\":5,\"zaiz\":60,\"ziz\":22.5},{\"ch\":\"4971396\",\"cid\":\"DLQ2014010100274971396\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"HYQ\",\"fzm\":\"衡阳\",\"gdm\":\"010\",\"hc\":1.2,\"jsl\":\"朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"swh\":6,\"zaiz\":60,\"ziz\":22.5},{\"ch\":\"5221391\",\"cid\":\"DLQ2014010204065221391\",\"cz\":\"X6K\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"UBJ\",\"fzm\":\"柳家营\",\"gdm\":\"010\",\"hc\":1.2,\"jsl\":\"朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"佛山市南海区君顺物流有限公司\",\"swh\":7,\"zaiz\":54,\"ziz\":17.6},{\"ch\":\"5253351\",\"cid\":\"DLQ2014010204065253351\",\"cz\":\"X6B\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"UBJ\",\"fzm\":\"柳家营\",\"gdm\":\"010\",\"hc\":1.5,\"jsl\":\"朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"佛山市南海区君顺物流有限公司\",\"swh\":8,\"zaiz\":54,\"ziz\":22.4},{\"ch\":\"5350202\",\"cid\":\"DLQ2014010204065350202\",\"cz\":\"NX17BH\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"EZY\",\"fzm\":\"新筑\",\"gdm\":\"010\",\"hc\":1.5,\"jsl\":\"朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"广州市鸿威货运代理有限公司\",\"swh\":9,\"zaiz\":20,\"ziz\":23},{\"ch\":\"4946830\",\"cid\":\"DLQ2014010204064946830\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"PGF\",\"fzm\":\"盘古寺\",\"gdm\":\"010\",\"hc\":1.2,\"jsl\":\"朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"佛山市泓亚海运代理有限公司\",\"swh\":10,\"zaiz\":55,\"ziz\":22.5},{\"ch\":\"1639347\",\"cid\":\"DLQ2014010204061639347\",\"cz\":\"C70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"PGF\",\"fzm\":\"盘古寺\",\"gdm\":\"010\",\"hc\":1.3,\"jsl\":\"朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"佛山市泓亚海运代理有限公司\",\"swh\":11,\"zaiz\":55,\"ziz\":23.8},{\"ch\":\"4615886\",\"cid\":\"DLQ2014010204064615886\",\"cz\":\"C62B\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"PGF\",\"fzm\":\"盘古寺\",\"gdm\":\"010\",\"hc\":1.2,\"jsl\":\"朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"佛山市泓亚海运代理有限公司\",\"swh\":12,\"zaiz\":55,\"ziz\":22.3},{\"ch\":\"1565279\",\"cid\":\"DLQ2014010204061565279\",\"cz\":\"C70\",\"dzh\":\"CQQ\",\"dzm\":\"春湾\",\"fzh\":\"LEQ\",\"fzm\":\"澧县\",\"gdm\":\"010\",\"hc\":1.3,\"kzbz\":\"1\",\"pb\":0,\"pm\":\"石膏\",\"shr\":\"澧县羊耳山石膏有限责任公司\",\"swh\":13,\"zaiz\":70,\"ziz\":23.8},{\"ch\":\"4649376\",\"cid\":\"DLQ2014010204064649376\",\"cz\":\"C62B\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"ADQ\",\"fzm\":\"益阳东\",\"gdm\":\"010\",\"hc\":1.2,\"jsl\":\"回送朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二空2\",\"swh\":14,\"zaiz\":0,\"ziz\":22.3},{\"ch\":\"4837266\",\"cid\":\"DLQ2014010123264837266\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XCF\",\"fzm\":\"许昌\",\"gdm\":\"010\",\"hc\":1.2,\"jsl\":\"朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"佛山市泓润龙货运代理有限公司\",\"swh\":15,\"zaiz\":55,\"ziz\":22.5},{\"ch\":\"5271243\",\"cid\":\"DLQ2014010204065271243\",\"cz\":\"NX17\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XAQ\",\"fzm\":\"霞凝\",\"gdm\":\"010\",\"hc\":1.3,\"jsl\":\"朗集\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"广州市长程物流有限公司\",\"swh\":16,\"zaiz\":41,\"ziz\":22.1}]";
					}else if(gdm.equals("011")){
					s = "[{\"ch\":\"4934487\",\"cid\":\"DLQ2014010117464934487\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XEQ\",\"fzm\":\"湘潭东\",\"gdm\":\"011\",\"hc\":1.2,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"swh\":1,\"zaiz\":60,\"ziz\":22.5},{\"ch\":\"4201842\",\"cid\":\"DLQ2014010117464201842\",\"cz\":\"C64H\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XEQ\",\"fzm\":\"湘潭东\",\"gdm\":\"011\",\"hc\":1.2,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"swh\":2,\"zaiz\":60,\"ziz\":22.3},{\"ch\":\"4961337\",\"cid\":\"DLQ2014010117464961337\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XEQ\",\"fzm\":\"湘潭东\",\"gdm\":\"011\",\"hc\":1.2,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"swh\":3,\"zaiz\":60,\"ziz\":22.5},{\"ch\":\"4620260\",\"cid\":\"DLQ2014010117464620260\",\"cz\":\"C62B\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XEQ\",\"fzm\":\"湘潭东\",\"gdm\":\"011\",\"hc\":1.2,\"jsl\":\"朗钢窜\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"圆钢\",\"swh\":4,\"zaiz\":55,\"ziz\":22.3},{\"ch\":\"4901412\",\"cid\":\"DLQ2014010123264901412\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"CFW\",\"fzm\":\"城厢\",\"gdm\":\"011\",\"hc\":1.2,\"jsl\":\"朗集集租\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"特二空2\",\"swh\":5,\"zaiz\":0,\"ziz\":22.5},{\"ch\":\"4875179\",\"cid\":\"DLQ2014010123264875179\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"CFW\",\"fzm\":\"城厢\",\"gdm\":\"011\",\"hc\":1.2,\"jsl\":\"朗集集租\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"特二空2\",\"swh\":6,\"zaiz\":0,\"ziz\":22.5},{\"ch\":\"4934918\",\"cid\":\"DLQ2014010123264934918\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"KNM\",\"fzm\":\"王家营西\",\"gdm\":\"011\",\"hc\":1.2,\"jsl\":\"朗集集租班列\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通四重1\",\"shr\":\"广州粤蜀捷运输服务有限公司\",\"swh\":7,\"zaiz\":24,\"ziz\":22.5}]";
					}else if(gdm.equals("012")){
					s = "[{\"ch\":\"4893360\",\"cid\":\"DLQ2014010117464893360\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XEQ\",\"fzm\":\"湘潭东\",\"gdm\":\"012\",\"hc\":1.2,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"swh\":1,\"zaiz\":60,\"ziz\":22.5},{\"ch\":\"4862199\",\"cid\":\"DLQ2014010117464862199\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XEQ\",\"fzm\":\"湘潭东\",\"gdm\":\"012\",\"hc\":1.2,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"swh\":2,\"zaiz\":60,\"ziz\":22.5},{\"ch\":\"4919820\",\"cid\":\"DLQ2014010117464919820\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XUG\",\"fzm\":\"新余\",\"gdm\":\"012\",\"hc\":1.2,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"钢板\",\"swh\":3,\"zaiz\":57,\"ziz\":22.5},{\"ch\":\"1550131\",\"cid\":\"DLQ2014010117461550131\",\"cz\":\"C70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"LDQ\",\"fzm\":\"娄底\",\"gdm\":\"012\",\"hc\":1.3,\"jsl\":\"朗钢限连\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"卷钢\",\"swh\":4,\"zaiz\":70,\"ziz\":23.8},{\"ch\":\"1678812\",\"cid\":\"DLQ2014010117461678812\",\"cz\":\"C70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"LDQ\",\"fzm\":\"娄底\",\"gdm\":\"012\",\"hc\":1.3,\"jsl\":\"朗钢限连\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"卷钢\",\"swh\":5,\"zaiz\":70,\"ziz\":23.8},{\"ch\":\"1604768\",\"cid\":\"DLQ2014010117461604768\",\"cz\":\"C70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"LDQ\",\"fzm\":\"娄底\",\"gdm\":\"012\",\"hc\":1.3,\"jsl\":\"朗钢限连X\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"卷钢\",\"shr\":\"3\",\"swh\":6,\"zaiz\":70,\"ziz\":23.8},{\"ch\":\"4832853\",\"cid\":\"DLQ2014010107114832853\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XEQ\",\"fzm\":\"湘潭东\",\"gdm\":\"012\",\"hc\":1.2,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"swh\":7,\"zaiz\":60,\"ziz\":22.5},{\"ch\":\"1509533\",\"cid\":\"DLQ2014010111461509533\",\"cz\":\"C70H\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XTP\",\"fzm\":\"邢台\",\"gdm\":\"012\",\"hc\":1.3,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"shr\":\"广东鱼珠物流基地有限公司白云分公司\",\"swh\":8,\"zaiz\":70,\"ziz\":23.6},{\"ch\":\"4885983\",\"cid\":\"DLQ2014010111464885983\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XTP\",\"fzm\":\"邢台\",\"gdm\":\"012\",\"hc\":1.2,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"shr\":\"广东鱼珠物流基地有限公司白云分公司\",\"swh\":9,\"zaiz\":60,\"ziz\":22.5},{\"ch\":\"1509990\",\"cid\":\"DLQ2014010111461509990\",\"cz\":\"C70H\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XTP\",\"fzm\":\"邢台\",\"gdm\":\"012\",\"hc\":1.3,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"shr\":\"广东鱼珠物流基地有限公司白云分公司\",\"swh\":10,\"zaiz\":70,\"ziz\":23.6}]";
					}else if(gdm.equals("013")){
					s = "[{\"ch\":\"4926928\",\"cid\":\"DLQ2014010111464926928\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XTP\",\"fzm\":\"邢台\",\"gdm\":\"013\",\"hc\":1.2,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"shr\":\"广东鱼珠物流基地有限公司白云分公司\",\"swh\":1,\"zaiz\":60,\"ziz\":22.5},{\"ch\":\"1636595\",\"cid\":\"DLQ2014010111461636595\",\"cz\":\"C70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XTP\",\"fzm\":\"邢台\",\"gdm\":\"013\",\"hc\":1.3,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"shr\":\"广东鱼珠物流基地有限公司白云分公司\",\"swh\":2,\"zaiz\":70,\"ziz\":23.8},{\"ch\":\"1593917\",\"cid\":\"DLQ2014010111461593917\",\"cz\":\"C70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XTP\",\"fzm\":\"邢台\",\"gdm\":\"013\",\"hc\":1.3,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"shr\":\"广东鱼珠物流基地有限公司白云分公司\",\"swh\":3,\"zaiz\":70,\"ziz\":23.8},{\"ch\":\"4808636\",\"cid\":\"DLQ2014010111464808636\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XTP\",\"fzm\":\"邢台\",\"gdm\":\"013\",\"hc\":1.2,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"shr\":\"广东鱼珠物流基地有限公司白云分公司\",\"swh\":4,\"zaiz\":60,\"ziz\":22.5},{\"ch\":\"1587417\",\"cid\":\"DLQ2014010111461587417\",\"cz\":\"C70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XTP\",\"fzm\":\"邢台\",\"gdm\":\"013\",\"hc\":1.3,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"shr\":\"广东鱼珠物流基地有限公司白云分公司\",\"swh\":5,\"zaiz\":70,\"ziz\":23.8},{\"ch\":\"1500569\",\"cid\":\"DLQ2014010111461500569\",\"cz\":\"C70H\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XTP\",\"fzm\":\"邢台\",\"gdm\":\"013\",\"hc\":1.3,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"shr\":\"广东鱼珠物流基地有限公司白云分公司\",\"swh\":6,\"zaiz\":70,\"ziz\":23.6},{\"ch\":\"1706119\",\"cid\":\"DLQ2014010111461706119\",\"cz\":\"C70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XTP\",\"fzm\":\"邢台\",\"gdm\":\"013\",\"hc\":1.3,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"shr\":\"广东鱼珠物流基地有限公司白云分公司\",\"swh\":7,\"zaiz\":70,\"ziz\":23.8},{\"ch\":\"1683642\",\"cid\":\"DLQ2014010111461683642\",\"cz\":\"C70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XTP\",\"fzm\":\"邢台\",\"gdm\":\"013\",\"hc\":1.3,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"shr\":\"广东鱼珠物流基地有限公司白云分公司\",\"swh\":8,\"zaiz\":70,\"ziz\":23.8},{\"ch\":\"4932596\",\"cid\":\"DLQ2014010111464932596\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"XTP\",\"fzm\":\"邢台\",\"gdm\":\"013\",\"hc\":1.2,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"盘条\",\"shr\":\"广东鱼珠物流基地有限公司白云分公司\",\"swh\":9,\"zaiz\":60,\"ziz\":22.5},{\"ch\":\"4945018\",\"cid\":\"DLQ2014010111464945018\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"UDQ\",\"fzm\":\"冷水江东\",\"gdm\":\"013\",\"hc\":1.2,\"jsl\":\"朗钢\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"螺纹钢\",\"shr\":\"广东鱼珠物流基地有限公司白云分公司\",\"swh\":10,\"zaiz\":61,\"ziz\":22.5}]";
					}
					
					GsonBuilder gb = new GsonBuilder();
					gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
					Gson gson = gb.create();
					nList = gson.fromJson(s, new TypeToken<LinkedList<CarDto>>(){}.getType());
			}
	//		s = "[{\"brokenFlag\":0,\"ch\":\"4910025\",\"cid\":\"DLQ2014092613024910025\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"LXJ\",\"fzm\":\"陇西\",\"gdm\":\"010\",\"hc\":1.2,\"importantFlag\":0,\"jsl\":\"朗集\",\"jzxh\":\"TBJU4690260,TBJU4646400\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"佛山市泓亚物流有限公司\",\"swh\":1,\"zaiz\":55,\"ziz\":22.5},{\"brokenFlag\":1,\"ch\":\"4667536\",\"cid\":\"DLQ2014092613024667536\",\"cz\":\"C62B\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"LXJ\",\"fzm\":\"陇西\",\"gdm\":\"010\",\"hc\":1.2,\"importantFlag\":1,\"jsl\":\"朗集\",\"jzxh\":\"TBJU3857918,TBJU4658783\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"佛山市泓亚物流有限公司\",\"swh\":2,\"zaiz\":55,\"ziz\":22.3},{\"brokenFlag\":0,\"ch\":\"4601127\",\"cid\":\"DLQ2014092613024601127\",\"cz\":\"C62B\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"LXJ\",\"fzm\":\"陇西\",\"gdm\":\"010\",\"hc\":1.2,\"importantFlag\":0,\"jsl\":\"朗集\",\"jzxh\":\"TBJU3797125,TBJU4111477\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"佛山市泓亚物流有限公司\",\"swh\":3,\"zaiz\":55,\"ziz\":22.3},{\"brokenFlag\":0,\"ch\":\"5482930\",\"cid\":\"DLQ2014092613025482930\",\"cz\":\"NX70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"YRM\",\"fzm\":\"玉溪南\",\"gdm\":\"010\",\"hc\":1.5,\"importantFlag\":0,\"jsl\":\"朗集六口F2\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"佛山市南海区吉胜物流有限公司\",\"swh\":4,\"zaiz\":70,\"ziz\":23.8},{\"brokenFlag\":0,\"ch\":\"4951512\",\"cid\":\"DLQ2014092613024951512\",\"cz\":\"C64\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"YRM\",\"fzm\":\"玉溪南\",\"gdm\":\"010\",\"hc\":1.2,\"importantFlag\":0,\"jsl\":\"朗集六口F2\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"佛山市南海区吉胜物流有限公司\",\"swh\":5,\"zaiz\":70,\"ziz\":22.5},{\"brokenFlag\":0,\"ch\":\"5300232\",\"cid\":\"DLQ2014092613025300232\",\"cz\":\"X1K\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"QTJ\",\"fzm\":\"青铜峡\",\"gdm\":\"010\",\"hc\":1.3,\"importantFlag\":0,\"jsl\":\"朗集\",\"jzxh\":\"TBJU4139094,TBJU3981359\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"广州白云广源贸易储运部\",\"swh\":6,\"zaiz\":55,\"ziz\":20},{\"brokenFlag\":0,\"ch\":\"1407187\",\"cid\":\"DLQ2014092613021407187\",\"cz\":\"C62A2\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"BNJ\",\"fzm\":\"白银市\",\"gdm\":\"010\",\"hc\":1.2,\"importantFlag\":0,\"jsl\":\"朗集\",\"jzxh\":\"TBJU3755990,TBJU4398372\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"佛山市泓润龙货运代理有限公司\",\"swh\":7,\"zaiz\":55,\"ziz\":21.7},{\"brokenFlag\":0,\"ch\":\"5225385\",\"cid\":\"DLQ2014092613025225385\",\"cz\":\"X70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"ZYN\",\"fzm\":\"枣阳\",\"gdm\":\"010\",\"hc\":1.2,\"importantFlag\":0,\"jsl\":\"朗集\",\"jzxh\":\"TBJU3729922,TBJU4545766\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"广州市华运达联运贸易有限公司\",\"swh\":8,\"zaiz\":54,\"ziz\":22.1},{\"brokenFlag\":0,\"ch\":\"5229186\",\"cid\":\"DLQ2014092613025229186\",\"cz\":\"X70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"ZYN\",\"fzm\":\"枣阳\",\"gdm\":\"010\",\"hc\":1.2,\"importantFlag\":0,\"jsl\":\"朗集\",\"jzxh\":\"TBJU3842245,TBJU4543573\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"广州市华运达联运贸易有限公司\",\"swh\":9,\"zaiz\":54,\"ziz\":22.1},{\"brokenFlag\":0,\"ch\":\"5222782\",\"cid\":\"DLQ2014092613025222782\",\"cz\":\"X6K\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"ZYN\",\"fzm\":\"枣阳\",\"gdm\":\"010\",\"hc\":1.2,\"importantFlag\":0,\"jsl\":\"朗集\",\"jzxh\":\"TBJU4339153,TBJU3724258\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"广州市华运达联运贸易有限公司\",\"swh\":10,\"zaiz\":54,\"ziz\":17.6},{\"brokenFlag\":0,\"ch\":\"5229220\",\"cid\":\"DLQ2014092613025229220\",\"cz\":\"X70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"ZYN\",\"fzm\":\"枣阳\",\"gdm\":\"010\",\"hc\":1.2,\"importantFlag\":0,\"jsl\":\"朗集\",\"jzxh\":\"TBJU4688474,TBJU4696628\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"广州市华运达联运贸易有限公司\",\"swh\":11,\"zaiz\":54,\"ziz\":22.1},{\"brokenFlag\":0,\"ch\":\"5228845\",\"cid\":\"DLQ2014092613025228845\",\"cz\":\"X70\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"ZYN\",\"fzm\":\"枣阳\",\"gdm\":\"010\",\"hc\":1.2,\"importantFlag\":0,\"jsl\":\"朗集\",\"jzxh\":\"TBJU4246132,TBJU3935712\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"广州市华运达联运贸易有限公司\",\"swh\":12,\"zaiz\":54,\"ziz\":22.1},{\"brokenFlag\":0,\"ch\":\"7701874\",\"cid\":\"DLQ2014092519017701874\",\"cz\":\"PB\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fzh\":\"GBW\",\"fzm\":\"黔灵山\",\"gdm\":\"010\",\"hc\":1.6,\"importantFlag\":0,\"jsl\":\"扣修\",\"kzbz\":\"0\",\"pb\":0,\"pm\":\"空行\",\"swh\":13,\"zaiz\":0,\"ziz\":28},{\"brokenFlag\":0,\"ch\":\"3500706\",\"cid\":\"DLQ2014092519113500706\",\"cz\":\"P65\",\"dzh\":\"JCQ\",\"dzm\":\"江村\",\"fzh\":\"DLQ\",\"fzm\":\"大朗\",\"gdm\":\"010\",\"hc\":1.5,\"importantFlag\":0,\"jsl\":\"F\",\"kzbz\":\"0\",\"pb\":0,\"pm\":\"空\",\"swh\":14,\"zaiz\":0,\"ziz\":25.9}]";

		}catch(Exception e){
			MaStation.getInstance().recordExc(e);
		}
		
		return nList;

	}
	
//	1018(赵玺) 09:23:31
//	public String getGdxc(@WebParam(name = "stationCode") String stationCode,
//				@WebParam(name = "gdm") String gdm);
//	1018(赵玺) 09:24:06
//	获取某条股道的现车,方法已经部署,返回为carDto的集合

	
//	public String heartbeat(@WebParam(name = "stationCode") String stationCode,
//			@WebParam(name = "userCode") String userCode,
//			@WebParam(name = "pdaId") String pdaId,
//			@WebParam(name = "time") String time);
//			@WebParam(name = "postName") String postName);

	//30秒
	public String heartbeat(String stationCode,String userCode,String postName,String pdaId,long time){
		String s = null;
		try {
			if(!MaStation.getInstance().isTest()){
			Properties tProperties = new Properties();
			tProperties.put("stationCode", stationCode);
			tProperties.put("userCode", userCode);
			tProperties.put("postName", postName);
			tProperties.put("pdaId", pdaId);
			tProperties.put("time", time);

			String url = getURL() + "heartbeat";
			s = PostUtil.getInstance().post(url, tProperties, true);//心跳不做三次请求处理
				if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}

				}

			}else {
				s= "test_user_ok";
			}
		} catch (Exception e) {
			// TODO: handle exception
			MaStation.getInstance().recordExc(e);
		}
		return s;
	}
	
	// 获取当前登录用户需要作业的工单集合(装卸汽车)
//		public String getZydTrucks(
//				@WebParam(name = "stationCode") String stationCode,
//				@WebParam(name = "gdm") String gdm);
//	public String getZydTrucksSTRNew(String gdm){
//		String s = null;
//		try {
//			if(!MaStation.getInstance().isTest()){
//				Properties tProperties = new Properties();
//				tProperties.put("stationCode", MaStation.stationCode);
//				tProperties.put("gdm", gdm);
//				tProperties.put("postName", MaStation.getInstance().getUser().getPostName()!= null?MaStation.getInstance().getUser().getPostName():"");
//				SoapObject tSoapObject = call("getZydTrucks", tProperties,false); needcall
//				s = getjsonString(tSoapObject);
//			}else{
//				s= "[]";
//			}
//		}catch(Exception e){
//			
//		}
//		return s;
//	}
	// 获取当前登录用户需要作业的工单集合
//		public String getZyds(@WebParam(name = "stationCode") String stationCode,
//				@WebParam(name = "gdm") String gdm);
//	public String getZydsSTRNew(String gdm){
//		String s = null;
//		try {
//			if(!MaStation.getInstance().isTest()){
//				Properties tProperties = new Properties();
//				tProperties.put("stationCode", MaStation.stationCode);
//				tProperties.put("gdm", gdm);
//				tProperties.put("postName", MaStation.getInstance().getUser().getPostName()!= null?MaStation.getInstance().getUser().getPostName():"");
//				
//				String url = getURL() + "heartbeat";call
//				s = PostUtil.getInstance().post(url, tProperties, false);
//				StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
//				s = JSON.parseObject(info.getData().toString(), String.class);
////				大朗南区*装卸值班员1
//
//			}else{
////		         <return>[{"ch":"5230872","cz":"X70","gdm":"011","hwpl":"集装箱","hwpm":"通二空2","ksTime":"2014-05-16 11:14:28","pbFlag":3,"pbTime":"2014-05-16 13:10:00","pbgw":"南区裝卸值班员","pbr":"邵海华","pbrq":"20140515","state":1,"stnCode":"DLQ","xh":"TBJU4572421,TBJU3939153","zyAddr":"09","zyType":22,"zyTypeHz":"卸火车","zybc":"3","zyds":0,"zydwGb":"三组","zydwmc":"大朗装卸南区","zypbId":35699,"zzTime":"2014-05-16 11:14:28"},{"ch":"4936197","cz":"C","gdm":"011","hpId":"11052014DLQ  E056868","hph":"E056868","hwpl":"集装箱","hwpm":"瓷砖","pbFlag":1,"pbTime":"2014-05-16 13:09:00","pbgw":"南区裝卸值班员","pbr":"邵海华","pbrq":"20140515","state":1,"stnCode":"DLQ","xh":"TBJU4384701,TBJU3771856","xw":"11m3a1,11m3a2","zyType":11,"zyTypeHz":"装火车","zybc":"3","zyds":53,"zydwGb":"三组","zydwmc":"大朗装卸南区","zypbId":35538},{"ch":"4611002","cz":"C62B","gdm":"011","hwpl":"集装箱","hwpm":"通二空2","pbFlag":1,"pbTime":"2014-05-16 13:09:00","pbgw":"南区裝卸值班员","pbr":"邵海华","pbrq":"20140515","state":1,"stnCode":"DLQ","xh":"TBJU4037765,TBJU4259700","zyAddr":"09","zyType":22,"zyTypeHz":"卸火车","zybc":"3","zyds":0,"zydwGb":"三组","zydwmc":"大朗装卸南区","zypbId":35698}]</return>
////		        <return>[{"ch":"4935878","cz":"C64","gdm":"010","hpId":"09052014MLV  E049057","hph":"E049057","hwpl":"集装箱","hwpm":"通二重2","ksTime":"2014-05-16 11:00:41","pbFlag":1,"pbTime":"2014-05-16 12:45:00","pbgw":"南区裝卸值班员","pbr":"邵海华","pbrq":"20140515","state":1,"stnCode":"DLQ","xh":"TBJU4240010,TBJU4690023","zyAddr":"011","zyType":22,"zyTypeHz":"卸火车","zybc":"3","zyds":54,"zydwGb":"一组","zydwmc":"大朗装卸南区","zypbId":36820},{"ch":"4962437","cz":"C64","gdm":"010","hpId":"09052014MLV  E049051","hph":"E049051","hwpl":"集装箱","hwpm":"通二重2","pbFlag":1,"pbTime":"2014-05-16 12:45:00","pbgw":"南区裝卸值班员","pbr":"邵海华","pbrq":"20140515","state":1,"stnCode":"DLQ","xh":"TBJU3848942,TBJU4208739","zyAddr":"011","zyType":22,"zyTypeHz":"卸火车","zybc":"3","zyds":54,"zydwGb":"一组","zydwmc":"大朗装卸南区","zypbId":36821},{"ch":"4812118","cz":"C64","gdm":"010","hpId":"09052014MLV  E049058","hph":"E049058","hwpl":"集装箱","hwpm":"通二重2","pbFlag":1,"pbTime":"2014-05-16 12:45:00","pbgw":"南区裝卸值班员","pbr":"邵海华","pbrq":"20140515","state":1,"stnCode":"DLQ","xh":"TBJU4559306,TBJU6155509","zyAddr":"011","zyType":22,"zyTypeHz":"卸火车","zybc":"3","zyds":54,"zydwGb":"一组","zydwmc":"大朗装卸南区","zypbId":36822},{"ch":"5237996","cz":"X6K","gdm":"010","hpId":"09052014XNO  G091149","hph":"G091149","hwpl":"集装箱","hwpm":"自二重2","pbFlag":1,"pbTime":"2014-05-16 12:45:00","pbgw":"南区裝卸值班员","pbr":"邵海华","pbrq":"20140515","state":1,"stnCode":"DLQ","xh":"NYKU2559419,NYKU2501227","zyAddr":"011","zyType":22,"zyTypeHz":"卸火车","zybc":"3","zyds":55,"zydwGb":"一组","zydwmc":"大朗装卸南区","zypbId":36826},{"ch":"5228590","cz":"X6K","gdm":"010","hph":"G000001","hwpl":"集装箱","hwpm":"自二重2","pbFlag":1,"pbTime":"2014-05-16 12:45:00","pbgw":"南区裝卸值班员","pbr":"邵海华","pbrq":"20140515","state":1,"stnCode":"DLQ","xh":"HJLU1000560,HJLU1000473","zyAddr":"011","zyType":22,"zyTypeHz":"卸火车","zybc":"3","zyds":55,"zydwGb":"一组","zydwmc":"大朗装卸南区","zypbId":36824},{"ch":"5229716","cz":"X6K","gdm":"010","hph":"G000001","hwpl":"集装箱","hwpm":"自二重2","pbFlag":1,"pbTime":"2014-05-16 12:45:00","pbgw":"南区裝卸值班员","pbr":"邵海华","pbrq":"20140515","state":1,"stnCode":"DLQ","xh":"CCLU2247075,CCLU2232809","zyAddr":"011","zyType":22,"zyTypeHz":"卸火车","zybc":"3","zyds":55,"zydwGb":"一组","zydwmc":"大朗装卸南区","zypbId":36825},{"ch":"5252831","cz":"X6B","gdm":"010","hpId":"10052014XNO  G091173","hph":"G091173","hwpl":"集装箱","hwpm":"自二重2","pbFlag":1,"pbTime":"2014-05-16 12:45:00","pbgw":"南区裝卸值班员","pbr":"邵海华","pbrq":"20140515","state":1,"stnCode":"DLQ","xh":"YDLU2002121,YDLU2002456","zyAddr":"011","zyType":22,"zyTypeHz":"卸火车","zybc":"3","zyds":55,"zydwGb":"一组","zydwmc":"大朗装卸南区","zypbId":36823}]</return>
////			       <return>[{"ch":"4870273","cz":"C64","gdm":"012","hwpl":"集装箱","hwpm":"通二空2","pbFlag":1,"pbTime":"2014-05-16 13:10:00","pbgw":"南区裝卸值班员","pbr":"邵海华","pbrq":"20140515","state":1,"stnCode":"DLQ","xh":"TBJU6121828,TBJU4542052","zyAddr":"09","zyType":22,"zyTypeHz":"卸火车","zybc":"3","zyds":0,"zydwGb":"二组","zydwmc":"大朗装卸南区","zypbId":35681}]</return>
//			       
//				s= "[]";
//			}
//		}catch(Exception e){
//			needcall
//		}
//		return s;
//	}
	
	//这个是读取箱位的方法
//	return json dto :GdContainerPos;
//	public String getContainerPos(String stationCode, String gdm,String postName)
	public String getContainerPos(String gdm){
		String s = null;
//		List<GdContainerPos> nList = null;
		try {
			if(!MaStation.getInstance().isTest()){
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("gdm", gdm);
				//tProperties.put("postName", MaStation.getInstance().getUser().getPostName()!= null?MaStation.getInstance().getUser().getPostName():"");
				String url = getURL() + "getContainerPos";
				s = PostUtil.getInstance().post(url, tProperties, true);
				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					}
				}
//				StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
//				nList = JSON.parseArray(info.getData().toString(), GdContainerPos.class);

			}else{
				s= "[{\"gdm\":\"六区\",\"middlePos\":[{\"middlePosName\":\"34\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"33\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"32\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"31\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"30\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"19\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"18\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"17\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"16\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"15\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"14\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"13\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"12\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"11\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"10\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"9\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"29\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"8\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"28\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"7\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"27\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"6\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"26\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"5\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"25\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"4\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"24\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"3\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"23\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"2\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"22\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"1\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"21\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"20\",\"pos\":[\"A\",\"B\"]}]},{\"gdm\":\"五区\",\"middlePos\":[{\"middlePosName\":\"34\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"33\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"32\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"31\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"30\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"19\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"18\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"17\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"16\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"15\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"14\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"13\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"12\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"11\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"10\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"9\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"29\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"8\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"28\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"7\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"27\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"6\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"26\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"5\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"25\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"4\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"24\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"3\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"23\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"2\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"1\",\"pos\":[\"B\",\"A\"]},{\"middlePosName\":\"22\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"21\",\"pos\":[\"A\",\"B\"]},{\"middlePosName\":\"20\",\"pos\":[\"A\",\"B\"]}]},{\"gdm\":\"10\",\"middlePos\":[{\"middlePosName\":\"W\",\"pos\":[\"17D\",\"17E\",\"16E\",\"1A\",\"1B\",\"1C\",\"1D\",\"1E\",\"2A\",\"2B\",\"2C\",\"2D\",\"2E\",\"3A\",\"3B\",\"3C\",\"3D\",\"3E\",\"4A\",\"4B\",\"4C\",\"4D\",\"4E\",\"5A\",\"5B\",\"5C\",\"5D\",\"5E\",\"6A\",\"6B\",\"6C\",\"6D\",\"6E\",\"7A\",\"7B\",\"7C\",\"7D\",\"7E\",\"8A\",\"8B\",\"8C\",\"9A\",\"8D\",\"8E\",\"9B\",\"9C\",\"9D\",\"9E\",\"10A\",\"10B\",\"10C\",\"10D\",\"10E\",\"11A\",\"11B\",\"11C\",\"11D\",\"11E\",\"12A\",\"12B\",\"12C\",\"12D\",\"12E\",\"13A\",\"13B\",\"13C\",\"13D\",\"13E\",\"14A\",\"14B\",\"14C\",\"14D\",\"14E\",\"15A\",\"15B\",\"15C\",\"15D\",\"15E\",\"16A\",\"16B\",\"16C\",\"16D\",\"17A\",\"17B\",\"17C\"]},{\"middlePosName\":\"北\",\"pos\":[\"5C\",\"5D\",\"5E\",\"5F\",\"5G\",\"4A\",\"4B\",\"4D\",\"4E\",\"4F\",\"4G\",\"3A\",\"3B\",\"3C\",\"3D\",\"3E\",\"3F\",\"3G\",\"5B\",\"2A\",\"2B\",\"2C\",\"2D\",\"2E\",\"2F\",\"2G\",\"1A\",\"1B\",\"1C\",\"1D\",\"1E\",\"1F\",\"1G\",\"6B\",\"6C\",\"6D\",\"6E\",\"6F\",\"6G\",\"5A\",\"7G\",\"6A\",\"4C\",\"8A\",\"8B\",\"8C\",\"8D\",\"8E\",\"8F\",\"8G\",\"7A\",\"7B\",\"7C\",\"7D\",\"7E\",\"7F\"]},{\"middlePosName\":\"E\",\"pos\":[\"17A\",\"1A\",\"1B\",\"1C\",\"1D\",\"1E\",\"2A\",\"2B\",\"2C\",\"2D\",\"2E\",\"3A\",\"3B\",\"3C\",\"3D\",\"3E\",\"4A\",\"4B\",\"4C\",\"4D\",\"4E\",\"5A\",\"5B\",\"5C\",\"5D\",\"5E\",\"6A\",\"6B\",\"6C\",\"6D\",\"6E\",\"7A\",\"7B\",\"7C\",\"7D\",\"7E\",\"8A\",\"8B\",\"8C\",\"8D\",\"8E\",\"9A\",\"9B\",\"9C\",\"9D\",\"9E\",\"10A\",\"10B\",\"10C\",\"10D\",\"10E\",\"11A\",\"11B\",\"11C\",\"11D\",\"11E\",\"12A\",\"12B\",\"12C\",\"12D\",\"12E\",\"13A\",\"13B\",\"13C\",\"13D\",\"13E\",\"14A\",\"14B\",\"14C\",\"14D\",\"14E\",\"15A\",\"15B\",\"15C\",\"15D\",\"15E\",\"16A\",\"16B\",\"16C\",\"16D\",\"16E\",\"17B\",\"17C\",\"17D\",\"17E\"]},{\"middlePosName\":\"M\",\"pos\":[\"12A\",\"13A\",\"14A\",\"15A\",\"16A\",\"17A\",\"18A\",\"19A\",\"20A\",\"21A\",\"22A\",\"23A\",\"24A\",\"25A\",\"26A\",\"27A\",\"28A\",\"29A\",\"30A\",\"31A\",\"33A\",\"34A\",\"1B\",\"2B\",\"3B\",\"4B\",\"5B\",\"6B\",\"7B\",\"8B\",\"9B\",\"10B\",\"11B\",\"12B\",\"13B\",\"14B\",\"15B\",\"16B\",\"17B\",\"18B\",\"19B\",\"20B\",\"21B\",\"22B\",\"23B\",\"24B\",\"25B\",\"26B\",\"27B\",\"28B\",\"29B\",\"10A\",\"11A\",\"32B\",\"33B\",\"34B\",\"1C\",\"2C\",\"3C\",\"4C\",\"5C\",\"6C\",\"7C\",\"8C\",\"9C\",\"10C\",\"11C\",\"12C\",\"13C\",\"14C\",\"15C\",\"16C\",\"17C\",\"18C\",\"19C\",\"20C\",\"21C\",\"22C\",\"23C\",\"24C\",\"25C\",\"26C\",\"27C\",\"28C\",\"29C\",\"30C\",\"31C\",\"32C\",\"33C\",\"34C\",\"1D\",\"2D\",\"3D\",\"4D\",\"5D\",\"6D\",\"7D\",\"8D\",\"9D\",\"10D\",\"11D\",\"12D\",\"13D\",\"14D\",\"15D\",\"16D\",\"17D\",\"18D\",\"19D\",\"20D\",\"21D\",\"22D\",\"23D\",\"24D\",\"25D\",\"26D\",\"27D\",\"28D\",\"29D\",\"30D\",\"31D\",\"32D\",\"33D\",\"34D\",\"1E\",\"2E\",\"3E\",\"4E\",\"5E\",\"6E\",\"7E\",\"8E\",\"9E\",\"10E\",\"11E\",\"12E\",\"13E\",\"14E\",\"30B\",\"31B\",\"18E\",\"19E\",\"20E\",\"21E\",\"22E\",\"23E\",\"24E\",\"25E\",\"26E\",\"27E\",\"28E\",\"29E\",\"30E\",\"31E\",\"32E\",\"33E\",\"34E\",\"1F\",\"2F\",\"3F\",\"4F\",\"5F\",\"6F\",\"7F\",\"8F\",\"9F\",\"10F\",\"11F\",\"12F\",\"13F\",\"14F\",\"15F\",\"16F\",\"17F\",\"18F\",\"19F\",\"20F\",\"21F\",\"22F\",\"23F\",\"24F\",\"25F\",\"26F\",\"27F\",\"28F\",\"29F\",\"30F\",\"31F\",\"32F\",\"33F\",\"34F\",\"15E\",\"16E\",\"17E\",\"32A\",\"1A\",\"2A\",\"3A\",\"4A\",\"5A\",\"6A\",\"7A\",\"8A\",\"9A\"]}]}]";
			}
		}catch(Exception e){

		}
		return s;
	}
	
	// 获取当前登录用户需要作业的工单集合
//	public String getZyds(@WebParam(name = "stationCode") String stationCode,
//			@WebParam(name = "postName") String postName,
//			@WebParam(name = "team") String team,
//			@WebParam(name = "workDate") String workDate);
	
//	public List<ZydDto> getZyds(String stationCode,String postName,String team,String workDate){
//		List<ZydDto> nList = null;
//		String s = null;
//		try {
//			if(!MaStation.getInstance().isTest()){
//		Properties tProperties = new Properties();
//		tProperties.put("stationCode", stationCode);
//		tProperties.put("postName", postName);
//		tProperties.put("team", team);
//		tProperties.put("workDate", workDate);
//		
//		SoapObject tSoapObject = call("getZyds", tProperties,false);
//		s = getjsonString(tSoapObject);
//		}else{
//			s= "[{\"ch\":\"4924427\",\"cz\":\"C64\",\"hpId\":\"16122013QTJ  T095962\",\"hph\":\"T095962\",\"hwpl\":\"集装箱\",\"hwpm\":\"通二重2\",\"hycj\":\"大朗物流车间\",\"hyzx\":\"广州货运中心\",\"ksTime\":\"2013-12-26 17:35:26\",\"pbFlag\":1,\"pbTime\":\"2013-12-26 05:47:00\",\"pbgw\":\"派班值班员\",\"pbr\":\"派班值班员\",\"pbrq\":\"20131226\",\"stnCode\":\"DLQ\",\"stnName\":\"大朗\",\"xh\":\"TBJU4288858\",\"zxZyr\":\"装卸南区二组\",\"zyType\":22,\"zyTypeHz\":\"直卸\",\"zybc\":\"3\",\"zyds\":55,\"zydwGb\":\"二组\",\"zydwmc\":\"大朗装卸南区\",\"zypbId\":6552},{\"ch\":\"4971359\",\"cz\":\"C64\",\"hpId\":\"15122013QTJ  T095763\",\"hph\":\"T095763\",\"hwpl\":\"集装箱\",\"hwpm\":\"通二重2\",\"hycj\":\"大朗物流车间\",\"hyzx\":\"广州货运中心\",\"ksTime\":\"2013-12-26 17:35:46\",\"pbFlag\":1,\"pbTime\":\"2013-12-26 05:42:00\",\"pbgw\":\"派班值班员\",\"pbr\":\"派班值班员\",\"pbrq\":\"20131226\",\"stnCode\":\"DLQ\",\"stnName\":\"大朗\",\"xh\":\"TBJU3913586\",\"zxZyr\":\"装卸南区二组\",\"zyType\":22,\"zyTypeHz\":\"直卸\",\"zybc\":\"3\",\"zyds\":55,\"zydwGb\":\"二组\",\"zydwmc\":\"大朗装卸南区\",\"zypbId\":6553,\"zzTime\":\"2013-12-26 17:35:48\"},{\"ch\":\"4971359\",\"cz\":\"C64\",\"hpId\":\"15122013QTJ  T095763\",\"hph\":\"T095763\",\"hwpl\":\"集装箱\",\"hwpm\":\"通二重2\",\"hycj\":\"大朗物流车间\",\"hyzx\":\"广州货运中心\",\"pbFlag\":1,\"pbTime\":\"2013-12-26 05:57:00\",\"pbgw\":\"派班值班员\",\"pbr\":\"派班值班员\",\"pbrq\":\"20131226\",\"stnCode\":\"DLQ\",\"stnName\":\"大朗\",\"xh\":\"TBJU3723009\",\"zxZyr\":\"装卸南区二组\",\"zyType\":22,\"zyTypeHz\":\"直卸\",\"zybc\":\"3\",\"zyds\":55,\"zydwGb\":\"二组\",\"zydwmc\":\"大朗装卸南区\",\"zypbId\":6554}]";
//		}
//		GsonBuilder gb = new GsonBuilder(); 
//		gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
//		Gson gson = gb.create();
//		nList = gson.fromJson(s, new TypeToken<LinkedList<ZydDto>>(){}.getType());
//		}catch(Exception e){
//			needcall
//		}
//		return nList;
//	}
	
	// 获取当前登录用户需要作业的工单集合
//	public String getZydsSTR(String stationCode,String postName,String team,String workDate){
//		String s = null;
//		try {
//			if(!MaStation.getInstance().isTest()){
//				Properties tProperties = new Properties();
//				tProperties.put("stationCode", stationCode);
//				tProperties.put("postName", postName);
//				tProperties.put("team", team);
//				tProperties.put("workDate", workDate);
//				SoapObject tSoapObject = call("getZyds", tProperties,false);
//				s = getjsonString(tSoapObject);
//			}else{
//				s= "[{\"ch\":\"4924427\",\"cz\":\"C64\",\"hpId\":\"16122013QTJ  T095962\",\"hph\":\"T095962\",\"hwpl\":\"集装箱\",\"hwpm\":\"通二重2\",\"hycj\":\"大朗物流车间\",\"hyzx\":\"广州货运中心\",\"ksTime\":\"2013-12-26 17:35:26\",\"pbFlag\":1,\"pbTime\":\"2013-12-26 05:47:00\",\"pbgw\":\"派班值班员\",\"pbr\":\"派班值班员\",\"pbrq\":\"20131226\",\"stnCode\":\"DLQ\",\"stnName\":\"大朗\",\"xh\":\"TBJU4288858\",\"zxZyr\":\"装卸南区二组\",\"zyType\":22,\"zyTypeHz\":\"直卸\",\"zybc\":\"3\",\"zyds\":55,\"zydwGb\":\"二组\",\"zydwmc\":\"大朗装卸南区\",\"zypbId\":6552},{\"ch\":\"4971359\",\"cz\":\"C64\",\"hpId\":\"15122013QTJ  T095763\",\"hph\":\"T095763\",\"hwpl\":\"集装箱\",\"hwpm\":\"通二重2\",\"hycj\":\"大朗物流车间\",\"hyzx\":\"广州货运中心\",\"ksTime\":\"2013-12-26 17:35:46\",\"pbFlag\":1,\"pbTime\":\"2013-12-26 05:42:00\",\"pbgw\":\"派班值班员\",\"pbr\":\"派班值班员\",\"pbrq\":\"20131226\",\"stnCode\":\"DLQ\",\"stnName\":\"大朗\",\"xh\":\"TBJU3913586\",\"zxZyr\":\"装卸南区二组\",\"zyType\":22,\"zyTypeHz\":\"直卸\",\"zybc\":\"3\",\"zyds\":55,\"zydwGb\":\"二组\",\"zydwmc\":\"大朗装卸南区\",\"zypbId\":6553,\"zzTime\":\"2013-12-26 17:35:48\"},{\"ch\":\"4971359\",\"cz\":\"C64\",\"hpId\":\"15122013QTJ  T095763\",\"hph\":\"T095763\",\"hwpl\":\"集装箱\",\"hwpm\":\"通二重2\",\"hycj\":\"大朗物流车间\",\"hyzx\":\"广州货运中心\",\"pbFlag\":1,\"pbTime\":\"2013-12-26 05:57:00\",\"pbgw\":\"派班值班员\",\"pbr\":\"派班值班员\",\"pbrq\":\"20131226\",\"stnCode\":\"DLQ\",\"stnName\":\"大朗\",\"xh\":\"TBJU3723009\",\"zxZyr\":\"装卸南区二组\",\"zyType\":22,\"zyTypeHz\":\"直卸\",\"zybc\":\"3\",\"zyds\":55,\"zydwGb\":\"二组\",\"zydwmc\":\"大朗装卸南区\",\"zypbId\":6554}]";
//			}
//		}catch(Exception e){
//			needcall
//		}
//		return s;
//	}
	

//	// 获取当前登录用户需要作业的工单集合(装卸汽车)
//		public String getZydTrucks(@WebParam(name = "stationCode") String stationCode,
//				@WebParam(name = "postName") String postName,
//				@WebParam(name = "team") String team,
//				@WebParam(name = "workDate") String workDate);
//	public List<ZydDto> getZydTrucks(String stationCode,String postName,String team,String workDate){
//		List<ZydDto> nList = null;
//		String s = null;
//		try {
//			if(!MaStation.getInstance().isTest()){
//				Properties tProperties = new Properties();
//				tProperties.put("stationCode", stationCode);
//				tProperties.put("postName", postName);
//				tProperties.put("team", team);
//				tProperties.put("workDate", workDate);
//				SoapObject tSoapObject = call("getZydTrucks", tProperties,false);
//				s = getjsonString(tSoapObject);
//			}else{
//				s= "[{\"ch\":\"4924427\",\"cz\":\"C64\",\"hpId\":\"16122013QTJ  T095962\",\"hph\":\"T095962\",\"hwpl\":\"集装箱\",\"hwpm\":\"通二重2\",\"hycj\":\"大朗物流车间\",\"hyzx\":\"广州货运中心\",\"ksTime\":\"2013-12-26 17:35:26\",\"pbFlag\":1,\"pbTime\":\"2013-12-26 05:47:00\",\"pbgw\":\"派班值班员\",\"pbr\":\"派班值班员\",\"pbrq\":\"20131226\",\"stnCode\":\"DLQ\",\"stnName\":\"大朗\",\"xh\":\"TBJU4288858\",\"zxZyr\":\"装卸南区二组\",\"zyType\":22,\"zyTypeHz\":\"直卸\",\"zybc\":\"3\",\"zyds\":55,\"zydwGb\":\"二组\",\"zydwmc\":\"大朗装卸南区\",\"zypbId\":6552},{\"ch\":\"4971359\",\"cz\":\"C64\",\"hpId\":\"15122013QTJ  T095763\",\"hph\":\"T095763\",\"hwpl\":\"集装箱\",\"hwpm\":\"通二重2\",\"hycj\":\"大朗物流车间\",\"hyzx\":\"广州货运中心\",\"ksTime\":\"2013-12-26 17:35:46\",\"pbFlag\":1,\"pbTime\":\"2013-12-26 05:42:00\",\"pbgw\":\"派班值班员\",\"pbr\":\"派班值班员\",\"pbrq\":\"20131226\",\"stnCode\":\"DLQ\",\"stnName\":\"大朗\",\"xh\":\"TBJU3913586\",\"zxZyr\":\"装卸南区二组\",\"zyType\":22,\"zyTypeHz\":\"直卸\",\"zybc\":\"3\",\"zyds\":55,\"zydwGb\":\"二组\",\"zydwmc\":\"大朗装卸南区\",\"zypbId\":6553,\"zzTime\":\"2013-12-26 17:35:48\"},{\"ch\":\"4971359\",\"cz\":\"C64\",\"hpId\":\"15122013QTJ  T095763\",\"hph\":\"T095763\",\"hwpl\":\"集装箱\",\"hwpm\":\"通二重2\",\"hycj\":\"大朗物流车间\",\"hyzx\":\"广州货运中心\",\"pbFlag\":1,\"pbTime\":\"2013-12-26 05:57:00\",\"pbgw\":\"派班值班员\",\"pbr\":\"派班值班员\",\"pbrq\":\"20131226\",\"stnCode\":\"DLQ\",\"stnName\":\"大朗\",\"xh\":\"TBJU3723009\",\"zxZyr\":\"装卸南区二组\",\"zyType\":22,\"zyTypeHz\":\"直卸\",\"zybc\":\"3\",\"zyds\":55,\"zydwGb\":\"二组\",\"zydwmc\":\"大朗装卸南区\",\"zypbId\":6554}]";
//			}
//			GsonBuilder gb = new GsonBuilder(); 
//			gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
//			Gson gson = gb.create();
//			nList = gson.fromJson(s, new TypeToken<LinkedList<ZydDto>>(){}.getType());
//		}catch(Exception e){
//			needcall
//		}
//		return nList;
//	}
	
	//// 获取当前登录用户需要作业的工单集合(装卸汽车)
//	public String getZydTrucksSTR(String stationCode,String postName,String team,String workDate){
//		String s = null;
//		try {
//			if(!MaStation.getInstance().isTest()){
//				Properties tProperties = new Properties();
//				tProperties.put("stationCode", stationCode);
//				tProperties.put("postName", postName);
//				tProperties.put("team", team);
//				tProperties.put("workDate", workDate);
//				SoapObject tSoapObject = call("getZydTrucks", tProperties,false);
//				s = getjsonString(tSoapObject);
//			}else{
//				s= "[{\"ch\":\"4924427\",\"cz\":\"C64\",\"hpId\":\"16122013QTJ  T095962\",\"hph\":\"T095962\",\"hwpl\":\"集装箱\",\"hwpm\":\"通二重2\",\"hycj\":\"大朗物流车间\",\"hyzx\":\"广州货运中心\",\"ksTime\":\"2013-12-26 17:35:26\",\"pbFlag\":1,\"pbTime\":\"2013-12-26 05:47:00\",\"pbgw\":\"派班值班员\",\"pbr\":\"派班值班员\",\"pbrq\":\"20131226\",\"stnCode\":\"DLQ\",\"stnName\":\"大朗\",\"xh\":\"TBJU4288858\",\"zxZyr\":\"装卸南区二组\",\"zyType\":22,\"zyTypeHz\":\"直卸\",\"zybc\":\"3\",\"zyds\":55,\"zydwGb\":\"二组\",\"zydwmc\":\"大朗装卸南区\",\"zypbId\":6552},{\"ch\":\"4971359\",\"cz\":\"C64\",\"hpId\":\"15122013QTJ  T095763\",\"hph\":\"T095763\",\"hwpl\":\"集装箱\",\"hwpm\":\"通二重2\",\"hycj\":\"大朗物流车间\",\"hyzx\":\"广州货运中心\",\"ksTime\":\"2013-12-26 17:35:46\",\"pbFlag\":1,\"pbTime\":\"2013-12-26 05:42:00\",\"pbgw\":\"派班值班员\",\"pbr\":\"派班值班员\",\"pbrq\":\"20131226\",\"stnCode\":\"DLQ\",\"stnName\":\"大朗\",\"xh\":\"TBJU3913586\",\"zxZyr\":\"装卸南区二组\",\"zyType\":22,\"zyTypeHz\":\"直卸\",\"zybc\":\"3\",\"zyds\":55,\"zydwGb\":\"二组\",\"zydwmc\":\"大朗装卸南区\",\"zypbId\":6553,\"zzTime\":\"2013-12-26 17:35:48\"},{\"ch\":\"4971359\",\"cz\":\"C64\",\"hpId\":\"15122013QTJ  T095763\",\"hph\":\"T095763\",\"hwpl\":\"集装箱\",\"hwpm\":\"通二重2\",\"hycj\":\"大朗物流车间\",\"hyzx\":\"广州货运中心\",\"pbFlag\":1,\"pbTime\":\"2013-12-26 05:57:00\",\"pbgw\":\"派班值班员\",\"pbr\":\"派班值班员\",\"pbrq\":\"20131226\",\"stnCode\":\"DLQ\",\"stnName\":\"大朗\",\"xh\":\"TBJU3723009\",\"zxZyr\":\"装卸南区二组\",\"zyType\":22,\"zyTypeHz\":\"直卸\",\"zybc\":\"3\",\"zyds\":55,\"zydwGb\":\"二组\",\"zydwmc\":\"大朗装卸南区\",\"zypbId\":6554}]";
//			}
//
//		}catch(Exception e){
//			needcall
//		}
//		return s;
//	}

	
	
	// 上报作业单作业开始,isStart为false时表示取消作业开始
//	public String reportZydStart(@WebParam(name = "zydID") int zydID,
//			@WebParam(name = "startT") long startT,
//			@WebParam(name = "zyr") String zyr,
//			@WebParam(name = "isStart") boolean isStart);
	
//	public String reportZydStart(int zydID,long startT, String zyr,boolean isStart){
//		String s = null;
//		try {
//			Properties tProperties = new Properties();
//			tProperties.put("stationCode", MaStation.stationCode);
//			tProperties.put("postName", MaStation.getInstance().getUser().getPostName());
//			
//			tProperties.put("zydID", zydID); //6510
//			tProperties.put("startT", startT); //1387877956572
//			tProperties.put("zyr", zyr); //装卸南区二组
//			tProperties.put("isStart", isStart);
//			
//			String url = getURL() + "heartbeat";call
//			s = PostUtil.getInstance().post(url, tProperties, false);
//			StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
//			s = JSON.parseObject(info.getData().toString(), String.class);
//
//		} catch (Exception e) {
//			// TODO: handle exception
//	needcall
//		}
//		return s;
//	}

	

	// 上报作业单作业完成,isOver为false时表示取消作业完成
//		public String reportZydOver(
//				@WebParam(name = "stationCode") String stationCode,
//				@WebParam(name = "postName") String postName,
//				@WebParam(name = "zydID") int zydID,
//				@WebParam(name = "zyr") String zyr,
//				@WebParam(name = "hw") String hw,
//				@WebParam(name = "isOver") boolean isOver);
	
	// 上报作业单作业完成,isOver为false时表示取消作业完成
//	public String reportZydOver(@WebParam(name = "zydID") int zydID,
//			@WebParam(name = "overT") long overT,
//			@WebParam(name = "zyr") String zyr,
//			@WebParam(name = "isOver") boolean isOver);
//	public String reportZydOver(int zydID,long startT, String zyr,boolean isStart,String hw){
//		String s = null;
//		try {
//			Properties tProperties = new Properties();
//			tProperties.put("stationCode", MaStation.stationCode);
//			tProperties.put("postName", MaStation.getInstance().getUser().getPostName());
//			tProperties.put("zydID", zydID);
////			tProperties.put("overT", startT);
//			tProperties.put("zyr", zyr);
//			tProperties.put("hw", hw);
//			tProperties.put("isOver", isStart);
//			SoapObject tSoapObject = call("reportZydOver", tProperties,true);
//			s = getjsonString(tSoapObject);
//		} catch (Exception e) {
//			// TODO: handle exception needcall
//		}
//		return s;
//	}
	

	/**
	 * 保存照片saveImg

	 * @return
	 */
//	public String saveImg(String ydNum,String jzxNum,String imgData){
//		String s = null;
//		try {
//			Properties tProperties = new Properties();
//			tProperties.put("ydNum", ydNum);
//			tProperties.put("jzxNum", jzxNum);
//			tProperties.put("imgData", imgData);
//			long sl = System.currentTimeMillis();
//			SoapObject tSoapObject = call("saveImg", tProperties,true);
//			s = getjsonString(tSoapObject);
//			long el = System.currentTimeMillis();
//			Log.v("times", el-sl +"");needcall
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return s;
//	}
	
	

//	public String getImg(String imgID) {
//		String s = null;
//		try {
//			Properties tProperties = new Properties();
//			tProperties.put("imgID", imgID);
//			long sl = System.currentTimeMillis();
//			SoapObject tSoapObject = call("getImg", tProperties,false);
//			s = getjsonString(tSoapObject);
////			s = "H4sIAAAAAAAAAO1cbWwbZx1/7mznndK0tzTZNHXt5uJDc1UvoRv9gtvhrD5nU+4yYJh+aAIUB2kSbQnbQEjptnZwiibmGTfLhOQ5dtaifWi3K2wIRLc140VEikMYbeDDukEZFZM2xsvgS/g/9+Z7eezc+SXJih39rMe+83P3f395nstz7yDf2BFEf2Hfc+9v+aCp5d43aIQe/BpCws+XLy6/gTZy/dF+RFEIUfCHlv+A7kTL1b5gEqrqSc6jzhbfRs9vPNR2RHdSnk5q+QLaCvfpo+QXUl8U7fH6mppbWtva4YRzGxFNeTy01+Pzeb1w9NtwHHk7fZu2hfY2beaHm7cfZm479sQzLTfvO/vydULh3Vt6R4483NrWtaW753r/jo8F2I/3fWL37Xd8cs+dn47037U/yg3d+5nPfu6+z8e/+KUvH/pKYvSrR78+9o0HHnzom488evzEY9/5rph8MvX99MnJp6ay07n8zLOnTv/w+Rekcz/68Ysv/eSVVy/MvvaLX/7q1wu/Xfzd67+/eGnp8ptv/enPV/7y9l+vvvf39//xz3/9+4P//BfTRSEPpb2IdHUCXbTX6/E2Y7oo+gF8QqfXty3UtGkv3zx8ePP22461MPueeObsy6039wrvXjdypNDWdUvfZf97mDSZMmeEPVwRZTphRbqWUIeHAuF5OtGn0ONiIEM30EDVYBNUIJ8d37TmN7KKSIsBlDWDCmRFdO0gbREolvKa35QD5HNws3EOKSoZD1DmAUFwrpGDidoCRRAuU3bA1ZBa08zlFDYpojgbyPiyNRjIdOfgQw2YKVtOkYycKzv0ZSd48SDDu75mHHFsIuPNpkXDIBSLoB1wL8daI0Gmg1Ok+z0fw4cVCPM0H4vCQBqk+QiDMFJCIYxPr3TARxBTJfaIgRlqyqCPbSyb0GnN5+CLOBegEjDoEtXBFkbgw+1iKBoLNwVmmRR6cZpJDVJiKNKT8TAjwI0BXoz3yoQ/auRSkx+o9ySe5cMfyStSuyOzj4/dxaAldvHgYEgaOtsf2LjEXpwGDrKHYZwS3pwAfsXEUPi1An5vjewMzCJ/sHuaQYJ+cL83e5LR+XqeoO1hleBdJqn4zXxdGKQijA2eU7ns5HHQ3aYgkLlNY0W3JAjzN5E0JG9kFnEQRwF5UO9gowlM3IXN73zuPDAj1A9S7gCtzwyDxBg0MgGcAXby4VZRUeJZYMbkNFbUkQmhsF/Rb25MlpIivtYII13wy6ocU/QIOElknmPEokQF9YC8jinyklJIMxwjhAXNxPjwLEjJi6VEF0A5wei0gfxNBAXhG+1qhpl546m8BepPjfeEBbe3GmLrg6vofuZqgJKGTmWaCvfMdhPfFSNiJE3ve8AyT/YdAmM8YPMvKTyb8qkbn8aM4NN4bNBgnOBwYcYRPgbDw/1sIkdjpcIOIIn1CzxyCL6doUfsYSz5g6h8iRaCI63vIA8WwI6yR0GwhyYWuvn+xByIdKpX/jwPktaGVKQnO35DLBobkCUdlX03H36lKHpgoTSZL1pZVxIIx0p13KJDG7IiK3NV9jdYZ08AJ8jBLIsjUoQ2ZgjukQeCif5olhG6i1HIJpcbIkF/yG4BKwNk6S8XYnK7nEVWdhy4M25IxZT72qwHFUWnMt7ZbjiuxJpyEjc5rROFezCJe8SDmufaJktqh01B8A1wXDw0sE9WbtBzh1FXd4n+Ut4K0DHG8PvtyYgxlYEYnEF8WJbnpC/oD95kCsu1yDv1/LPSpDZHTB/1YNLEkoIJxBh8XQ7S2DGrHlkiiBzN2wJ6eJDduFSKqa6Rktyr+Yrwl0kjdBfSbgx5QRJNWMVo0afFN2F+HQQXR6BJnBZUhtAFS9RuJ/3aIvE6CKke8JMcQ0r2AUHlgy17tws8oBSf8ap8/woo+rrRmpRfBldgcGhyJMR1w6JgF7PNKNqLb7XQQyWTjOEsx6xehaK9+fGgdqqvmLbq8hV9xxKOmSOC3VyEodMBahREobwlXFbwlura4uXL9BHAAdsOVV9veI36YNE2eZC0lzxYU7qKb2WvoPYK62UbOBK6biIYCIHEUibTlnmxWt3HmkXM6oInSD8tywZYabifdFJprCm9E3edl7Q7XlTaaGINfqV0/VskltUoZ0kGwCbUZMF2mVGH9CqGwVXSozJZkXut8JbhgkZJLm+wAtPA1nrLkZg9amvO6TSTWWKjkNVnUPJL3SsQnHwlPFBKIo3+fDn7KcWCsv6gyA2vuVuglGq0WFLIMvNMGTg+ylXrBo1yHlW5vCI52HnYnLvRJE3+GluFLiJzXq/3VTmDKsjqL4uXkwm0qAJro11flHHdf2YJxZjqEPKGU4gE27XbUvLkrAyoazgo7RodR4RkMQrmNcqd/DJt9dacYQWitIXWGY6XcNQYoLTJXVlPacI1scvfmORfPOQo/9V1yhgTZLtxGBwccyBfzt2VcmgKaUbnz1mcv9FFuY7rK8PKCT3OuFL7uNzidkd/pYtKFTZCnK23uXT/SVMbSW/2u8vlNVe94iqZG+ETMv6a1gBJq9MvH+5NcO/LnBu8Ibm3TaB5UYplFyczSrtRTbxXZb26rpV+LWBOvbW8TLdtuZ5Jajr+ZB+SHK46Bv2E3pFantsG+urMVG+4PeLvMZmJIdcxu/cqQE6KSui87vJxu9PFKpjkftFXWzQlpGDWKhGbuwNiOWNpoLKOVGjURfmLPYucyQeomY7RuWE9tDrI+uU6hs52hc2AsjmOmoWr0V2VZjHZJVJYyw0Y5QeV08Y6b2DZUvtVgJtNNitUO+QlJ2fSxy0cW5q7XlHO+bnS+Q8T1au1F3EVjLmCgRuRyzlfPFBcNC2PtNGi7Lrg1jZrNKhqb2LS0OOOOzaG1cv8dIYrDVNykMsaMl4XEc8d1UZXsDq+y5LEm4Nq1dFubWDqQJhq9JLUpCs2bx3qAobM1jjXGz4Ouf4hSPsHCjghZzrU6fH+GXlB009e1q4X3C3lC+aF6NLwW7Z2pKQL8gcpZVygFgTc5p0srlz6tQVfvGwPH7RaB1cz2jYOdpyPxpbGcbcFc3ivaUcAoTYybhcgHpT318z1gB709mNLV4eJ7HjHwhWQ+wG5lFKPgzgvTfctVfyelSe8Ozt+Y2S32LRHvJ9BV1lpkJrqjUa2YYU4jxXiVnzA9xivnofGGPjt4lAi02z/ynIu/erbM/z+5sJAdnxr9O7s5Ibs0xFqeHHoIe3wqZlC+D7863bt0NH+BB5DiQjf/7Ewhc+5MjHfdWPkdlDVM1fZl6b78AXmdiba2yM7EnPUcCI315O5Ncg8BUcUjT4jCXgT2IK6HwwXtIK6K+wACLNnug8RN4xtyOXmdmIV+alglUwKSZBFUZdyuBf0uLbaMhQFDZNmngY1+RuQPwyipbuSYqD7cjSK57HvDyrpEHLZNU8Zqqs10g57qg000EADDax/mDYmrHXOXJO8e7W6og000MD6BwtlDnoLqpbB3dU/r7HWyGfQoJsl/msBJ/tSg54zH+UGrucOTKFQtC69iSCu5VbqR8zbd82rTwqUPsl2QsmTHP2ygf8fkPTtGlcM3JMiPjfaTjJ7SSDMEoQZpFTph7osUwvz+uM+RrSxJP8b58gLlqMunHiXvtvQCvKDHnHE1elkAOlWrFvj9ZldXbJa1ITVj1haw2V3upRo2NILhMfE6Hn2w/KfMhpYF8DriJvwjhLbAdJTE9qqK2mF20fc3y83ZwkH4GwGfGFsyW7pXcmT50j2wSmrboFMwepZg9qjcu5TLCm1BhFFiQWEMLOULLH1RF89c+CLGmiggVqhVFa0nsCXeGC17JPWpPzBgBm7k8c7LzYzPzvNfov8nPZgIt9H/O8GQYZ/vVTMeKf6qmHr8qX/AYZSATvaUgAA";
//			long el = System.currentTimeMillis();
//			Log.v("times", el-sl +"");
//		} catch (Exception e) {needcall
//			// TODO: handle exception
//		}
//		return s;
//	}
	

	
//	public Bitmap getIBitmap(String ImgID){
//		long t = System.currentTimeMillis();needcall
//		Bitmap bitmap = null;
//		GsonBuilder gb = new GsonBuilder();
//		Gson gson = gb.create();
//		String Str = gson.fromJson(getImg(ImgID), String.class);
//		try {
//			byte[] b = Str.getBytes();
//			byte[] bytes = android.util.Base64.decode(b,Base64.DEFAULT);// android.util.Base64.decode(Str,Base64.DEFAULT);//new sun.misc.BASE64Decoder().decodeBuffer(testStr);
//			byte[] unzipbytes =  Zipper.unGZip(bytes);
//			bitmap = BitmapFactory.decodeByteArray(unzipbytes, 0, unzipbytes.length);
//
//		}catch(Exception e){
//			Log.v("123", e+"")	;
//		}
//		return bitmap;
//	}
	
//	public String getshImgURL(String xh){
//		String s = null;
//		try {
//			Properties tProperties = new Properties();
//			tProperties.put("xh", xh);
//			SoapObject tSoapObject = call("getshImgURL", tProperties,false);
//			s = getjsonString(tSoapObject);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return s;
//	}
	
	


		// 使用运单号码查询集装箱号码
//	select * from freight t order by sub_time desc
//		public String getJzxnum(@WebParam(name = "ydNum") String ydNum);
//		public String getJzxnum(String ydNum){
//			String s = null;needcall
//			try {
//				Properties tProperties = new Properties();
//				tProperties.put("ydNum", ydNum);
//				SoapObject tSoapObject = call("getJzxnum", tProperties,false);
//				s = getjsonString(tSoapObject);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			return s;
//		}
		
		// 根据箱号获取照片ID
//		public String getJzxPhotos(@WebParam(name = "jzxNum") String jzxNum);
//		public String getJzxPhotos(String jzxNum,String ydNum){
//			String s = null;
//			try {
//				Properties tProperties = new Properties();
//				tProperties.put("jzxNum", jzxNum);
//				tProperties.put("ydNum", ydNum);
//				SoapObject tSoapObject = call("getJzxPhotos", tProperties,false);
//				s = getjsonString(tSoapObject);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			return s;
//		}needcall


	// 删除照片
//		public String delJzxPhoto(
//				@WebParam(name = "stationCode") String stationCode,
//				@WebParam(name = "userCode") String userCode,
//				@WebParam(name = "photoID") String photoID);
//		public String delJzxPhoto(String jzxNum,String ydNum, String photoID){
//			String s = null;
//			try {
//				Properties tProperties = new Properties();
//				tProperties.put("stationCode", MaStation.stationCode);
//				tProperties.put("userCode", MaStation.getInstance().getUser().getCode());
//				tProperties.put("photoID", photoID);
//				tProperties.put("ydNum", ydNum);
//				tProperties.put("jzxNum", jzxNum);
//				SoapObject tSoapObject = call("delJzxPhoto", tProperties,true);
//				s = getjsonString(tSoapObject);
//			} catch (Exception e) {
//				// TODO: handle exception needcall
//			}
//			return s;
//		}
		
//		// 上报作业刷卡信息
//		public String reportZydd(
//				@WebParam(name = "stationCode") String stationCode,
//				@WebParam(name = "postName") String postName,
//				@WebParam(name = "userCode") String userCode,
//				@WebParam(name = "taskTime") String taskTime,
//				@WebParam(name = "deviceNO") String deviceNO,
//				@WebParam(name = "taskType") String taskType,
//				@WebParam(name = "taskContent") String taskContent,
//				@WebParam(name = "pdaID") String pdaID);
		public String reportZydd(String deviceNO,String taskType,String taskContent,String pdaid){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("postName", MaStation.getInstance().getUser().getName());
				tProperties.put("userCode", MaStation.getInstance().getUser().getCode());
//				tProperties.put("taskTime", System.currentTimeMillis());
				tProperties.put("deviceNO", deviceNO);
				tProperties.put("taskType", taskType);
				tProperties.put("taskContent",taskContent);
				tProperties.put("pdaID",pdaid);
				
				String url = getURL() + "reportZydd";
				s = PostUtil.getInstance().post(url, tProperties, true);
				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}

	
		// 查询待交付整车
//		public String getDjfGoods(
//				@WebParam(name = "stationCode") String stationCode,
//				@WebParam(name = "gdm") String gdm);	
		//GdmsUnloadJfDTO
		public String getDjfGoods(String gdm){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("gdm",gdm);
				
				String url = getURL() + "getDjfGoods";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}

				Log.v("jf",s +"  "+gdm);
			} catch (Exception e) {
				// TODO: handle exception
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}
		//屏蔽
		//上报整车交付情况
//		public String reportJfGoods(@WebParam(name = "hpid") String hpid,
//				@WebParam(name = "userCode") String userCode);	
//		public String reportJfGoods(String hpid){
//			String s = null;
//			try {
//				Properties tProperties = new Properties();
//				tProperties.put("stationCode", MaStation.stationCode);
//				tProperties.put("hpid", hpid);
//				tProperties.put("userCode",MaStation.getInstance().getUser().getCode());
//				SoapObject tSoapObject = call("reportJfGoods", tProperties);
//				s = getjsonString(tSoapObject);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			return s;
//		}
		
		
		//读取当前岗位消息
//		public String getMsg(@WebParam(name = "stationCode") String stationCode,
//				@WebParam(name = "postName") String postName);
		public String getMsg(){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("postName",MaStation.getInstance().getUser().getPostName());
				String url = getURL() + "getMsg";
				s = PostUtil.getInstance().post(url, tProperties, true);
				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					}
				}
//				StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
//				s = JSON.parseObject(info.getData().toString(), String.class);

			} catch (Exception e) {
				// TODO: handle exception
			}
			return s;
		}
		
//		// 获取消息接收者列表
//		public String getReceivers(
//				@WebParam(name = "stationCode") String stationCode,
//				@WebParam(name = "postName") String postName);
		public String getReceivers(){
		String s = null;
		try {
			Properties tProperties = new Properties();
			tProperties.put("stationCode", MaStation.stationCode);
			tProperties.put("postName",MaStation.getInstance().getUser().getPostName());
			String url = getURL() + "getReceivers";
			s = PostUtil.getInstance().post(url, tProperties, true);
			if("".equals(s)){//第一次请求失败
				s = PostUtil.getInstance().post(url, tProperties, false);
				if("".equals(s)){//第二次请求失败，准备第三次请求
					s = PostUtil.getInstance().post(url, tProperties, false);
				}
			}
//			SoapObject tSoapObject = call("getReceivers", tProperties,false);
//			s = getjsonString(tSoapObject);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return s;
	}
		
		// 发送消息给指定接收者
//		public String sendMsg(@WebParam(name = "stationCode") String stationCode,
//				@WebParam(name = "postName") String postName,
//				@WebParam(name = "receiverID") String receiverID,
//				@WebParam(name = "msg") String msg);
		public String sendMsg(String receiverID,String msg){
		String s = null;
		try {
			Properties tProperties = new Properties();
			tProperties.put("stationCode", MaStation.stationCode);
			tProperties.put("postName",MaStation.getInstance().getUser().getPostName());
			tProperties.put("receiverID", receiverID);
			tProperties.put("msg",msg);
			String url = getURL() + "sendMsg";
			s = PostUtil.getInstance().post(url, tProperties, true);
			if("".equals(s)){//第一次请求失败
				s = PostUtil.getInstance().post(url, tProperties, false);
				if("".equals(s)){//第二次请求失败，准备第三次请求
					s = PostUtil.getInstance().post(url, tProperties, false);
				}
			}
//			SoapObject tSoapObject = call("sendMsg", tProperties,true);
//			s = getjsonString(tSoapObject);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return s;
	}
	
		
		// 上报整车交付情况
//		状态proflag 为4 是整车 ，5为部分
//		public String reportJfGoods(@WebParam(name = "hpid") String hpid,
//				@WebParam(name = "userCode") String userCode,
//				@WebParam(name = "chp") String chp,
//				@WebParam(name = "proFlag") int proFlag);
		public String reportJfGoods(String hpid,String chp,int proFlag){
		String s = null;
		try {
			Properties tProperties = new Properties();
			tProperties.put("stationCode", MaStation.stationCode);
			tProperties.put("stationCode", MaStation.getInstance().getUser().getPostName());
			tProperties.put("userCode", MaStation.getInstance().getUser().getCode());
			tProperties.put("hpid", hpid);
			tProperties.put("chp",chp);
			tProperties.put("proFlag",proFlag);
			
			String url = getURL() + "reportJfGoods";
			s = PostUtil.getInstance().post(url, tProperties, true);

			if("".equals(s)){//第一次请求失败
				s = PostUtil.getInstance().post(url, tProperties, false);
				if("".equals(s)){//第二次请求失败，准备第三次请求
					s = PostUtil.getInstance().post(url, tProperties, false);
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}
			} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
				StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
				if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
					s = JSON.parseObject(info.getData().toString(), String.class);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			MaStation.getInstance().recordExc(e);
		}
		return s;
	}

		
		// 查询待交付整车,按货票号查询
//		public String getDjfGoodsByHph(
//				@WebParam(name = "stationCode") String stationCode,
//				@WebParam(name = "hph") String hph);	
		public String getDjfGoodsByHph(String hph){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.getInstance().stationCode);
				tProperties.put("hph", hph);
				
				String url = getURL() + "getDjfGoodsByHph";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}
		
		
		
		// 查询取送勾计划(包含车辆信息)
//		public String getQsgjh(@WebParam(name = "stationCode") String stationCode,
//				@WebParam(name = "gdm") String gdm);	
		public String getQsgjh(String gdm){
			String s = null;
			try {
				if(!MaStation.getInstance().isTest()){
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("gdm", gdm);
				
				String url = getURL() + "getQsgjh";
				s = PostUtil.getInstance().post(url, tProperties, true);

					if("".equals(s)){//第一次请求失败
						s = PostUtil.getInstance().post(url, tProperties, false);
						if("".equals(s)){//第二次请求失败，准备第三次请求
							s = PostUtil.getInstance().post(url, tProperties, false);
						} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
							StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
							if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
								s = JSON.parseObject(info.getData().toString(), String.class);
							}
						}
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}

				Log.v("mas",gdm +   "== " + s);
				System.out.print(gdm +   "== " + s);
				}else{
					if(gdm.equals("010")){
						s ="[{\"cb\":\"O\",\"ch\":\"4625745\",\"cs\":1,\"dj\":\"2调\",\"errorType\":0,\"factTime\":\"2014-02-28 14:05:04\",\"gdm\":\"010\",\"gjhid\":5118,\"jhsj\":\"2014-02-28 12:42:12\",\"notifyTime\":\"2014-02-28 12:51:27\",\"stepContent\":\"gdm:010,zylx:－,cs:1,swbz:1,ajsl:,mjsl:,cc:\",\"swbz\":\"1\",\"swh\":2,\"xClkDtos\":[{\"bjzz\":60,\"bqbz\":\"0\",\"ch\":\"4625745\",\"cid\":\"DLQ2014022808214625745\",\"cjbc\":0,\"clsz\":\"0\",\"ct\":\"0\",\"cz\":\"C62B\",\"czdm\":\"C62B\",\"czjm\":\"C\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"WBR\",\"fzm\":\"乌北\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"F005473\",\"hpid\":\"F005473\",\"hsbz\":\"0\",\"jsl\":\"F\",\"jybz\":\"0\",\"jzxh\":\"WDSA7896465,SWSW3506132\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":8,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.3,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"1\",\"zycs\":0}],\"zxsj\":\"2014-02-28 12:42:12\",\"zylx\":\"－\"},{\"cb\":\"O\",\"ch\":\"4698732\",\"cs\":2,\"dj\":\"2调\",\"errorType\":0,\"gdm\":\"010\",\"gjhid\":5118,\"jhsj\":\"2014-02-28 12:42:13\",\"notifyTime\":\"2014-02-28 12:51:35\",\"stepContent\":\"gdm:010,zylx:－,cs:2,swbz:1,ajsl:,mjsl:,cc:\",\"swbz\":\"1\",\"swh\":3,\"xClkDtos\":[{\"bjzz\":60,\"bqbz\":\"0\",\"ch\":\"4698732\",\"cid\":\"DLQ2014022808214698732\",\"cjbc\":0,\"clsz\":\"0\",\"ct\":\"0\",\"cz\":\"C62B\",\"czdm\":\"C62B\",\"czjm\":\"C\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"WBR\",\"fzm\":\"乌北\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"F005485\",\"hpid\":\"F005485\",\"hsbz\":\"0\",\"jsl\":\"F\",\"jybz\":\"0\",\"jzxh\":\"QWZA6564845,SFWS1053456\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":6,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.3,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"1\",\"zycs\":0},{\"bjzz\":60,\"bqbz\":\"0\",\"ch\":\"4625751\",\"cid\":\"DLQ2014022808214625751\",\"cjbc\":0,\"clsz\":\"0\",\"ct\":\"0\",\"cz\":\"C62B\",\"czdm\":\"C62B\",\"czjm\":\"C\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"WBR\",\"fzm\":\"乌北\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"F005675\",\"hpid\":\"F005675\",\"hsbz\":\"0\",\"jsl\":\"F\",\"jybz\":\"0\",\"jzxh\":\"LKJL1564564,SFES5945631\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":7,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.3,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"1\",\"zycs\":0}],\"zxsj\":\"2014-02-28 12:42:13\",\"zylx\":\"－\"},{\"cb\":\"O\",\"ch\":\"5225563\",\"cs\":3,\"dj\":\"2调\",\"errorType\":0,\"gdm\":\"010\",\"gjhid\":5118,\"jhsj\":\"2014-02-28 12:42:14\",\"stepContent\":\"gdm:010,zylx:－,cs:3,swbz:1,ajsl:,mjsl:,cc:\",\"swbz\":\"1\",\"swh\":4,\"xClkDtos\":[{\"bjzz\":70,\"bqbz\":\"0\",\"ch\":\"5225563\",\"cid\":\"DLQ2014022808215225563\",\"cjbc\":0,\"clsz\":\"1\",\"cltz\":\"LJ\",\"ct\":\"0\",\"cz\":\"X70\",\"czdm\":\"X70\",\"czjm\":\"X\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"UBJ\",\"fzm\":\"柳家营\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"J000025\",\"hpid\":\"J000025\",\"hsbz\":\"0\",\"jsl\":\"朗集\",\"jybz\":\"0\",\"jzxh\":\"TBLU1536115,TBGU1510332\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":3,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.1,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"0\",\"zycs\":0},{\"bjzz\":70,\"bqbz\":\"0\",\"ch\":\"5231828\",\"cid\":\"DLQ2014022808215231828\",\"cjbc\":0,\"clsz\":\"1\",\"cltz\":\"LJ\",\"ct\":\"0\",\"cz\":\"X70\",\"czdm\":\"X70\",\"czjm\":\"X\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"UBJ\",\"fzm\":\"柳家营\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"J000332\",\"hpid\":\"J000332\",\"hsbz\":\"0\",\"jsl\":\"朗集\",\"jybz\":\"0\",\"jzxh\":\"TBLU9109016,TBPU9040156\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":4,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.1,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"0\",\"zycs\":0},{\"bjzz\":60,\"bqbz\":\"0\",\"ch\":\"4682123\",\"cid\":\"DLQ2014022808214682123\",\"cjbc\":0,\"clsz\":\"0\",\"ct\":\"0\",\"cz\":\"C62B\",\"czdm\":\"C62B\",\"czjm\":\"C\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"WBR\",\"fzm\":\"乌北\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"F005498\",\"hpid\":\"F005498\",\"hsbz\":\"0\",\"jsl\":\"F\",\"jybz\":\"0\",\"jzxh\":\"SWAQ6156156,SFJU1561231\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":5,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.3,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"1\",\"zycs\":0}],\"zxsj\":\"2014-02-28 12:42:14\",\"zylx\":\"－\"},{\"cb\":\"O\",\"ch\":\"4608581\",\"cs\":2,\"dj\":\"2调\",\"errorType\":0,\"gdm\":\"010\",\"gjhid\":5118,\"jhsj\":\"2014-02-28 12:42:15\",\"stepContent\":\"gdm:010,zylx:－,cs:2,swbz:1,ajsl:,mjsl:,cc:\",\"swbz\":\"1\",\"swh\":5,\"xClkDtos\":[{\"bjzz\":60,\"bqbz\":\"0\",\"ch\":\"4608581\",\"cid\":\"DLQ2014022808214608581\",\"cjbc\":0,\"clsz\":\"0\",\"cltz\":\"LJ\",\"ct\":\"0\",\"cz\":\"C62B\",\"czdm\":\"C62B\",\"czjm\":\"C\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"GRO\",\"fzm\":\"格尔木\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"C074002\",\"hpid\":\"C074002\",\"hsbz\":\"0\",\"jsl\":\"朗集\",\"jybz\":\"0\",\"jzxh\":\"TBQU1022155,TBQU1561561\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":1,\"sysx\":\"1\",\"zaiz\":60,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.3,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"1\",\"zycs\":0},{\"bjzz\":61,\"bqbz\":\"0\",\"ch\":\"4896080\",\"cid\":\"DLQ2014022808214896080\",\"cjbc\":0,\"clsz\":\"0\",\"cltz\":\"LJ\",\"ct\":\"0\",\"cz\":\"C64\",\"czdm\":\"C64\",\"czjm\":\"C\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"UBJ\",\"fzm\":\"柳家营\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"J000001\",\"hpid\":\"J000001\",\"hsbz\":\"0\",\"jsl\":\"朗集\",\"jybz\":\"0\",\"jzxh\":\"TBPU1012121,TBPU1066461\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":2,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.5,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"1\",\"zycs\":0}],\"zxsj\":\"2014-02-28 12:42:15\",\"zylx\":\"－\"},{\"cb\":\"O\",\"ch\":\"4625745\",\"cs\":8,\"dj\":\"2调\",\"errorType\":0,\"gdm\":\"010\",\"gjhid\":5118,\"jhsj\":\"2014-02-28 14:01:52\",\"stepContent\":\"gdm:010,zylx:＋,cs:8,swbz:1,ajsl:,mjsl:,cc:\",\"swbz\":\"1\",\"swh\":6,\"xClkDtos\":[{\"bjzz\":60,\"bqbz\":\"0\",\"ch\":\"4608581\",\"cid\":\"DLQ2014022808214608581\",\"cjbc\":0,\"clsz\":\"0\",\"cltz\":\"LJ\",\"ct\":\"0\",\"cz\":\"C62B\",\"czdm\":\"C62B\",\"czjm\":\"C\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"GRO\",\"fzm\":\"格尔木\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"C074002\",\"hpid\":\"C074002\",\"hsbz\":\"0\",\"jsl\":\"朗集\",\"jybz\":\"0\",\"jzxh\":\"TBQU1022155,TBQU1561561\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":1,\"sysx\":\"1\",\"zaiz\":60,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.3,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"1\",\"zycs\":0},{\"bjzz\":61,\"bqbz\":\"0\",\"ch\":\"4896080\",\"cid\":\"DLQ2014022808214896080\",\"cjbc\":0,\"clsz\":\"0\",\"cltz\":\"LJ\",\"ct\":\"0\",\"cz\":\"C64\",\"czdm\":\"C64\",\"czjm\":\"C\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"UBJ\",\"fzm\":\"柳家营\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"J000001\",\"hpid\":\"J000001\",\"hsbz\":\"0\",\"jsl\":\"朗集\",\"jybz\":\"0\",\"jzxh\":\"TBPU1012121,TBPU1066461\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"通二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":2,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.5,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"1\",\"zycs\":0},{\"bjzz\":70,\"bqbz\":\"0\",\"ch\":\"5225563\",\"cid\":\"DLQ2014022808215225563\",\"cjbc\":0,\"clsz\":\"1\",\"cltz\":\"LJ\",\"ct\":\"0\",\"cz\":\"X70\",\"czdm\":\"X70\",\"czjm\":\"X\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"UBJ\",\"fzm\":\"柳家营\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"J000025\",\"hpid\":\"J000025\",\"hsbz\":\"0\",\"jsl\":\"朗集\",\"jybz\":\"0\",\"jzxh\":\"TBLU1536115,TBGU1510332\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":3,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.1,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"0\",\"zycs\":0},{\"bjzz\":70,\"bqbz\":\"0\",\"ch\":\"5231828\",\"cid\":\"DLQ2014022808215231828\",\"cjbc\":0,\"clsz\":\"1\",\"cltz\":\"LJ\",\"ct\":\"0\",\"cz\":\"X70\",\"czdm\":\"X70\",\"czjm\":\"X\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"UBJ\",\"fzm\":\"柳家营\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"J000332\",\"hpid\":\"J000332\",\"hsbz\":\"0\",\"jsl\":\"朗集\",\"jybz\":\"0\",\"jzxh\":\"TBLU9109016,TBPU9040156\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":4,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.1,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"0\",\"zycs\":0},{\"bjzz\":60,\"bqbz\":\"0\",\"ch\":\"4682123\",\"cid\":\"DLQ2014022808214682123\",\"cjbc\":0,\"clsz\":\"0\",\"ct\":\"0\",\"cz\":\"C62B\",\"czdm\":\"C62B\",\"czjm\":\"C\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"WBR\",\"fzm\":\"乌北\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"F005498\",\"hpid\":\"F005498\",\"hsbz\":\"0\",\"jsl\":\"F\",\"jybz\":\"0\",\"jzxh\":\"SWAQ6156156,SFJU1561231\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":5,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.3,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"1\",\"zycs\":0},{\"bjzz\":60,\"bqbz\":\"0\",\"ch\":\"4698732\",\"cid\":\"DLQ2014022808214698732\",\"cjbc\":0,\"clsz\":\"0\",\"ct\":\"0\",\"cz\":\"C62B\",\"czdm\":\"C62B\",\"czjm\":\"C\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"WBR\",\"fzm\":\"乌北\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"F005485\",\"hpid\":\"F005485\",\"hsbz\":\"0\",\"jsl\":\"F\",\"jybz\":\"0\",\"jzxh\":\"QWZA6564845,SFWS1053456\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":6,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.3,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"1\",\"zycs\":0},{\"bjzz\":60,\"bqbz\":\"0\",\"ch\":\"4625751\",\"cid\":\"DLQ2014022808214625751\",\"cjbc\":0,\"clsz\":\"0\",\"ct\":\"0\",\"cz\":\"C62B\",\"czdm\":\"C62B\",\"czjm\":\"C\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"WBR\",\"fzm\":\"乌北\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"F005675\",\"hpid\":\"F005675\",\"hsbz\":\"0\",\"jsl\":\"F\",\"jybz\":\"0\",\"jzxh\":\"LKJL1564564,SFES5945631\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":7,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.3,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"1\",\"zycs\":0},{\"bjzz\":60,\"bqbz\":\"0\",\"ch\":\"4625745\",\"cid\":\"DLQ2014022808214625745\",\"cjbc\":0,\"clsz\":\"0\",\"ct\":\"0\",\"cz\":\"C62B\",\"czdm\":\"C62B\",\"czjm\":\"C\",\"czlb\":\"H\",\"dbid\":5420,\"ddcc\":\"45507\",\"ddrq\":\"2014-02-28 08:21:00\",\"dfj\":\"63\",\"dj\":\"09\",\"dzh\":\"DLQ\",\"dzm\":\"大朗\",\"fbid\":0,\"fxh\":\"5\",\"fxh2\":\"5\",\"fybz\":\"0\",\"fzh\":\"WBR\",\"fzm\":\"乌北\",\"gdm\":\"15\",\"hc\":1.2,\"hph\":\"F005473\",\"hpid\":\"F005473\",\"hsbz\":\"0\",\"jsl\":\"F\",\"jybz\":\"0\",\"jzxh\":\"WDSA7896465,SWSW3506132\",\"kzbz\":\"1\",\"pb\":0,\"pm\":\"自二重2\",\"shr\":\"广东鱼珠物流基地有限公司白云分\",\"shrjc\":\"广东鱼珠物\",\"swh\":8,\"sysx\":\"1\",\"zaiz\":54,\"zdzh\":\"DLQ\",\"zdzm\":\"大朗\",\"ziz\":22.3,\"zlbz\":\"0\",\"zmlm\":\"DLQ\",\"zubz\":\"1\",\"zycs\":0}],\"zxsj\":\"2014-02-28 14:01:52\",\"zylx\":\"＋\"}]";
					}else if(gdm.equals("011")){
						s = "[]";
					}else if(gdm.equals("012")){
						s = "[]";
					}else if(gdm.equals("013")){
						s = "[]";
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}	
		
		

//		// 根据照片名获取照片ID
//			public String getNormalPhotos(@WebParam(name = "photoName") String photoName);
		public String getNormalPhotos(String photoName){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("photoName", photoName);
				String url = getURL() + "getNormalPhotos";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}
//
//			// 上传普通图片
//			public String saveNormalImg(@WebParam(name = "photoName") String photoName,
//					@WebParam(name = "imgData") String imgData);
		public String saveNormalImg(String photoName,String imgData){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("photoName", photoName);
				tProperties.put("imgData", imgData);
				
				String url = getURL() + "saveNormalImg";
				s = PostUtil.getInstance().post(url, tProperties, true);
				//待处理
				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}
//				if(!"".equals(s) && s!=null && !"cache".equals(s)){
//					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
//					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
//						s = JSON.parseObject(info.getData().toString(), String.class);
//					}
//
//				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}
		
		//取送计划中上传图片和车信息(重车,坏车)
		public String saveTypeImg(String photoName,int type,String imgData){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("photoName", photoName);
				tProperties.put("type", type);
				tProperties.put("imgData", imgData);
				
				String url = getURL() + "saveTypeImg";
				s = PostUtil.getInstance().post(url, tProperties, true);
				//待处理
				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}
//				if(!"".equals(s) && s!=null && !"cache".equals(s)){
//					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
//					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
//						s = JSON.parseObject(info.getData().toString(), String.class);
//					}
//				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}
		
//
//			// 删除普通照片
//			public String delNormalPhoto(
//					@WebParam(name = "stationCode") String stationCode,
//					@WebParam(name = "userCode") String userCode,
//					@WebParam(name = "photoName") String photoName,
//					@WebParam(name = "photoID") String photoID);
		public String delNormalPhoto(String photoName, String photoID){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("userCode", MaStation.getInstance().getUser().getCode());
				tProperties.put("photoName", photoName); 
				tProperties.put("photoID", photoID); 

				String url = getURL() + "delNormalPhoto";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}
		
		// 上报取送勾计划执行时间
//			public String reportQsgjhFact(
//					@WebParam(name = "stationCode") String stationCode,
//					@WebParam(name = "postName") String postName,
//					@WebParam(name = "gjhID") String gjhID,
//					@WebParam(name = "swh") String swh);
//			@WebParam(name = "gdm") String gdm)
		public String reportQsgjhFact(String gjhID,String swh,String gdm){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("postName", MaStation.getInstance().getUser().getPostName());
				tProperties.put("gjhID", gjhID); 
				tProperties.put("swh", swh); 
				tProperties.put("gdm", gdm); 
				String url = getURL() + "reportQsgjhFact";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}
		
//			// 巡查作业刷卡
//			public String doCheckTask(
//					@WebParam(name = &quot;stationCode&quot;) String stationCode,
//					@WebParam(name = &quot;userCode&quot;) String userCode,
//					@WebParam(name = &quot;taskID&quot;) String taskID,
//					@WebParam(name = &quot;icCardNo&quot;) String icCardNo);
		public String doCheckTask(String taskID,String icCardNo){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("userCode", MaStation.getInstance().getUser().getCode());
				tProperties.put("taskID", taskID); 
				tProperties.put("icCardNo", icCardNo); 
				
				String url = getURL() + "doCheckTask";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}
		
//			// 获取某条股道某个类型的巡查作业
//			public String getCheckTasks(
//					@WebParam(name = &quot;stationCode&quot;) String stationCode,
//					@WebParam(name = &quot;gdm&quot;) String gdm);
		public String getCheckTasks(String gdm){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("gdm", gdm); 
				String url = getURL() + "getCheckTasks";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}
		
		 // 上报取送勾计划执行时间
//			public String reportQsgjhFact(
//					@WebParam(name = "stationCode") String stationCode,
//					@WebParam(name = "postName") String postName,
//					@WebParam(name = "gjhID") String gjhID,
//					@WebParam(name = "swh") String swh,
//					@WebParam(name = "gdm") String gdm,
//					@WebParam(name = "icCard") String icCard);
		public String reportQsgjhFact(String icCardNo,String gjhID,String swh,String gdm){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("postName", MaStation.getInstance().getUser().getPostName());
				tProperties.put("gjhID", gjhID);
				tProperties.put("swh", swh);
				tProperties.put("gdm", gdm);
				tProperties.put("icCard", icCardNo); 
				String url = getURL() + "reportQsgjhFact";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}
		
		// 派班
//			public String switchWork
//			@WebParam(name = "cid") String cid,
//			@WebParam(name = "gdm") String gdm,
//			@WebParam(name = "workDay") String workDay,
//			@WebParam(name = "userName") String userName,
//			@WebParam(name = "postName") String postName);
//		public String paiBanFact(String cid,String gdm){
//			String s = null;
//			try {
//				Properties tProperties = new Properties();
//				tProperties.put("cid", cid);
//				tProperties.put("gdm", gdm);
//				tProperties.put("workDay", MaStation.getInstance().getUser().getWorkDay());
//				tProperties.put("userName", MaStation.getInstance().getUser().getName());
//				tProperties.put("postName", MaStation.getInstance().getUser().getPostName()); 
//				SoapObject tSoapObject = call("switchWork", tProperties,true);
//				s = getjsonString(tSoapObject);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}needcall
//			return s;
//		}
		
//		public String getMoveBoxMainData(){
//			String s = null;
//			try {
//				Properties tProperties = new Properties();
//				tProperties.put("workDay", MaStation.getInstance().getUser().getWorkDay());
//				tProperties.put("userName", MaStation.getInstance().getUser().getName());
//				tProperties.put("postName", MaStation.getInstance().getUser().getPostName()); 
//				SoapObject tSoapObject = call("switchWork", tProperties,false);
//				s = getjsonString(tSoapObject);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			return s;
//		}
		
		public String getWhGoodsDtoByBarCode(String barCode){
//			List<JzxHwzwModifyDTO> nList = null;
			String s = "";
			try {
				Properties tProperties = new Properties();
				tProperties.put("postName", MaStation.getInstance().getUser().getPostName());
				MaStation.getInstance();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("barCode", barCode); 

//				String url = getURL() + "getYxjh";
				String url = "http://10.167.93.128:7001/hyzx/pda/getWhGoodsDtoByBarCode";
//				String url = "http://192.168.3.236:7001/lqmsNutz/getYxjh";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					}
				}

//				StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
//				nList =JSON.parseArray(info.getData().toString(), JzxHwzwModifyDTO.class);
//				s = JSON.parseObject(info.getData().toString(), String.class);


			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;
		}
		
		public String getYxjh(){
//			List<JzxHwzwModifyDTO> nList = null;
			String s = "";
			try {
				Properties tProperties = new Properties();
				tProperties.put("workDay", MaStation.getInstance().getUser().getWorkDayAndBc());
				MaStation.getInstance();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("gdms", MaStation.getInstance().getUser().getWorkGdm()); 

//				String url = getURL() + "getYxjh";
				String url = "http://10.167.93.128:7001/hyzx/pda/getGdxc/getYxjh";
//				String url = "http://192.168.3.236:7001/lqmsNutz/getYxjh";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					}
				}
//				StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
//				nList =JSON.parseArray(info.getData().toString(), JzxHwzwModifyDTO.class);
//				s = JSON.parseObject(info.getData().toString(), String.class);


			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;
		}
		
		/**
		 * 上报移箱计划
		 * @param pk
		 * @param flag
		 * @param xw
		 * @param gdm
		 * @return
		 */
		public String reportYxjh(String pk,int flag,String xw, String gdm){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.getInstance().stationCode);
				tProperties.put("postName", MaStation.getInstance().getUser().getPostName());
				tProperties.put("pk", pk); 
				tProperties.put("flag", flag);
				tProperties.put("xw", xw);
				tProperties.put("gdm", gdm);
				String url = getURL() + "reportYxjh";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}
		
		/**
		 * 拒绝移箱计划
		 * @param pk
		 * @param flag
		 * @return
		 */
		public String refuseYxjh(String pk,int flag){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.getInstance().stationCode);
				tProperties.put("postName", MaStation.getInstance().getUser().getPostName());
				tProperties.put("pk", pk); 
				tProperties.put("flag", flag);

				String url = getURL() + "refuseYxjh";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;
		}
		
		/**
		 * 仓位管理
		 * @return
		 */
		public String getWhjh(){
//			List<JzxHwzwModifyDTO> nList = null;
			String s = "";
			try {
				Properties tProperties = new Properties();
//			"2015061102");
				tProperties.put("workDay", MaStation.getInstance().getUser().getWorkDayAndBc());
				MaStation.getInstance();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("gdms", MaStation.getInstance().getUser().getWorkGdm()); 

//				String url = getURL() + "getYxjh";
				String url = "http://10.167.93.128:7001/hyzx/pda/getGdxc/getWhjh";
//				String url = "http://192.168.3.236:7001/lqmsNutz/getYxjh";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					}
				}

//				StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
//				nList =JSON.parseArray(info.getData().toString(), JzxHwzwModifyDTO.class);
//				s = JSON.parseObject(info.getData().toString(), String.class);


			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;
		}
		
		/**
		 * 执行撤销仓库进出计划
		 * @param zydId
		 * @param flag
		 * @return
		 */
		public String reportWhjh(String zydId,int flag){
			String s = null;
			try {
				Properties tProperties = new Properties();
				tProperties.put("stationCode", MaStation.getInstance().stationCode);
				tProperties.put("postName", MaStation.getInstance().getUser().getPostName());
				tProperties.put("zydId", zydId); 
				tProperties.put("flag", flag);
				String url = getURL() + "reportWhjh";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
						StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
						if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
							s = JSON.parseObject(info.getData().toString(), String.class);
						}
					}
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}
	/**
	 * 执行撤销仓库进出计划
	 * @param xh
	 * @param xw
	 * @return
	 */
	public String updateJzxXw(String xh, String xw){
		String s = null;
		try {
			Properties tProperties = new Properties();
			tProperties.put("stationCode", MaStation.getInstance().stationCode);
			tProperties.put("postName", MaStation.getInstance().getUser().getPostName());
			tProperties.put("xw", xw);
			tProperties.put("xh", xh);
			String url = getURL() + "updateJzxXw";
			s = PostUtil.getInstance().post(url, tProperties, true);

			if("".equals(s)){//第一次请求失败
				s = PostUtil.getInstance().post(url, tProperties, false);
				if("".equals(s)){//第二次请求失败，准备第三次请求
					s = PostUtil.getInstance().post(url, tProperties, false);
				} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}
				}
			} else if(!"".equals(s) && s!=null && !"cache".equals(s)){
				StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
				if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
					s = JSON.parseObject(info.getData().toString(), String.class);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			MaStation.getInstance().recordExc(e);
		}
		return s;
	}
		/**
		 * 
		 * @return
		 */
		public String getZxzypb(){
//			List<JzxHwzwModifyDTO> nList = null;
			String s = "";
			try {
				Properties tProperties = new Properties();
				tProperties.put("workDay", MaStation.getInstance().getUser().getWorkDayAndBc());
				MaStation.getInstance();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("postName", MaStation.getInstance().getUser().getPostName()); 

				String url = getURL() + "getZxzypb";
//				String url = "http://10.167.93.128:7001/hyzx/pda/getGdxc/getYxjh";
//				String url = "http://192.168.3.236:7001/lqmsNutz/getYxjh";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					}
				}

//				StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
//				nList =JSON.parseArray(info.getData().toString(), JzxHwzwModifyDTO.class);
//				s = JSON.parseObject(info.getData().toString(), String.class);


			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;
		}
		
		/**
		 * 上报装卸作业单开始
		 * 暂不使用
		 * @param zypbId
		 * @param flag
		 * @return
		 */
		public String reportZxzyStart(String zypbId, int flag){
			String s = null;
			try {
				Properties tProperties = new Properties();
				MaStation.getInstance();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("postName", MaStation.getInstance().getUser().getPostName());
				tProperties.put("userName", MaStation.getInstance().getUser().getName()); 
				tProperties.put("flag", flag);
				tProperties.put("zypbId", zypbId);
				String url = getURL() + "reportZxzyStart";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					}
				}

//				StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
//				s = JSON.parseObject(info.getData().toString(), String.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;
		}
		
		/**
		 * 上报装卸作业单开始(增加箱位参数)
		 * @param zypbId
		 * @param flag
		 * @param xw
		 * @return
		 */
		public String reportZxzyStart(String zypbId, int flag,String xw){
			String s = null;
			try {
				Properties tProperties = new Properties();
				MaStation.getInstance();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("postName", MaStation.getInstance().getUser().getPostName());
				tProperties.put("userName", MaStation.getInstance().getUser().getName()); 
				tProperties.put("xw", xw);
				tProperties.put("flag", flag);
				tProperties.put("zypbId", zypbId);
				String url = getURL() + "reportZxzyStart";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					}
				}

//				StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
//				s = JSON.parseObject(info.getData().toString(), String.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;
		}

		
		/**
		 * 上报装卸作业单结束
		 * @param zypbId
		 * @param flag
		 * @return
		 */
		public String reportZxzyEnd(String zypbId, int flag){
			String s = null;
			try {
				Properties tProperties = new Properties();
				MaStation.getInstance();
				tProperties.put("stationCode", MaStation.stationCode);
				tProperties.put("postName", MaStation.getInstance().getUser().getPostName());
				tProperties.put("userName", MaStation.getInstance().getUser().getName()); 
				tProperties.put("flag", flag);
				tProperties.put("zypbId", zypbId);
				String url = getURL() + "reportZxzyEnd";
				s = PostUtil.getInstance().post(url, tProperties, true);

				if("".equals(s)){//第一次请求失败
					s = PostUtil.getInstance().post(url, tProperties, false);
					if("".equals(s)){//第二次请求失败，准备第三次请求
						s = PostUtil.getInstance().post(url, tProperties, false);
					}
				}
//				StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
//				s = JSON.parseObject(info.getData().toString(), String.class);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;
		}

	/**
	 * 缓存的求情进行提交，不做三次请求提交处理
	 * @param url
	 * @param tProperties
	 * @return
	 */
		public String cacheCommonLink(String url,Properties tProperties){
			String s = null;
			try {
				String direct = getURL() + url;
				s = PostUtil.getInstance().post(direct, tProperties, false);
				if(!"".equals(s) && s!=null  && !"cache".equals(s)){
					StandReturnInfo info = JSON.parseObject(s, StandReturnInfo.class);
					if(info!=null && info.getData() != null && !info.getData().toString().equals("")){
						s = JSON.parseObject(info.getData().toString(), String.class);
					}

				}


			} catch (Exception e) {
				e.printStackTrace();
				MaStation.getInstance().recordExc(e);
			}
			return s;
		}
		
		/**
		 * 检测网络是否可用
		 * @return
		 */
		public boolean isNetworkConnected() {
			NetworkInfo ni = null;
			try {
				ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
				ni = cm.getActiveNetworkInfo();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ni != null && ni.isConnectedOrConnecting();
		}
		
	
}
