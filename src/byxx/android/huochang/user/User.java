package byxx.android.huochang.user;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import android.util.Log;


public class User implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		public static final String SP_NAME = "user";
		public static final String CODE_ERR_NW = "ER001"; // 网络中断时 code对应值
		public static final String CODE_ERR_NP = "ER002"; // 用户与密码不对时 code对应值
		public static final String ROLE_FZC = "分整车";
		public static final String ROLE_JZX_ = "集装箱";
		public static final String ROLE_KYHYY = "快运货运员";
		public static final String ROLE_SHHYY = "收货货运员";
		public static final String ROLE_ZXG = "装卸工";
		public static final String ROLE_KYZBY = "货运值班员";
		
		private String stationCode = null;
		private String stationName = null;
		private String name = null;
		private String code = null;
		private String teams = null;
		private String regions = null;
		private String regionGroup = null;
		private String curRegion;// 当班位置
		private String workDay = null; // 当班日期 yyyyMMdd
		private String password;
		private int talkBackNum;// 对讲编号
		private String roleNames;// 权限集合，X,X
		private String postName;
		private String postId;
		private String workGdm;
		private long sysTime;// 系统时间
		private int loginState; // 0 登录成功 1 密码错误 2 已交班不允许登录等待值班主任允许 3工号不存在,4;//重复登录

		private String imgUrlMini;
		private String imgUrl;
		private String workDayTMR = null;//第二天日期yyyyMMdd
		public static int REPEAT_ERROR = 4;//重复登录
		private String functions;//[0,1,2,3,4,5,6]
		private String version; //系统版本号
	
		/**
		 * 大朗 分整车
		 * @return
		 */
		public boolean isFZC(){
			return (getCurRegion().contains(ROLE_FZC)?true:false);
		}
		/**
		 * 大朗 集装箱
		 * @return
		 */
		public boolean isJZX(){
			return (getCurRegion().contains(ROLE_FZC)?true:false);
		}
		/**
		 * 快运货运员(线路上)
		 * @return
		 */
		public boolean isKYHYY(){
			return (getCurRegion().contains(ROLE_KYHYY)?true:false);
		}
		/**
		 * 收货货运员
		 * @return
		 */
		public boolean isSHHYY(){
			return (getCurRegion().contains(ROLE_SHHYY)?true:false);
		}
		/**
		 * 装卸工
		 * @return
		 */
		public boolean isZXG(){
			return (getCurRegion().contains(ROLE_ZXG)?true:false);
		}
		/**
		 * 货运值班员
		 * @return
		 */
		public boolean isKYZBY(){
			return (getCurRegion().contains(ROLE_KYZBY)?true:false);
		}
		
		
		
		public int getLoginState() {
			return loginState;
		}


		public void setLoginState(int loginState) {
			this.loginState = loginState;
		}

		public String getCurRegion() {
			return curRegion;
		}

		public void setCurRegion(String curRegion) {
			this.curRegion = curRegion;
		}

		public long getSysTime() {
			return sysTime;
		}

		public void setSysTime(long sysTime) {
			this.sysTime = sysTime;
		}


		public String getRegionGroup() {
			return regionGroup;
		}

		public void setRegionGroup(String regionGroup) {
			this.regionGroup = regionGroup;
		}


		
		public int getTalkBackNum() {
			return talkBackNum;
		}

		public void setTalkBackNum(int talkBackNum) {
			this.talkBackNum = talkBackNum;
//			StaticClass.setCallNum(this.talkBackNum);
			Log.v("test","talkBackNum  "+talkBackNum+"");
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
			Log.v("test","password "+password);
		}

		public String getRoleNames() {
			return roleNames;
		}

		public void setRoleNames(String roleNames) {
			this.roleNames = roleNames;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
			Log.v("test","name "+name);
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getStationCode() {
			return stationCode;
		}

		public void setStationCode(String stationCode) {
			this.stationCode = stationCode;
		}

		public String getStationName() {
			return stationName;
		}

		public void setStationName(String stationName) {
			this.stationName = stationName;
		}

		public String getTeams() {
			return teams;
		}

		public void setTeams(String teams) {
			this.teams = teams;
			Log.v("test", "teams "+teams);
		}

		public String getRegions() {
			return regions;
		}

		public void setRegions(String regions) {
			this.regions = regions;
		}

		public String getWorkDay() {
			return workDay.substring(0, workDay.length()-2);
		}

		
		
		public void setWorkDay(String workDay) {
			this.workDay = workDay;
//			Log.v("test", "setWorkDay "+workDay);
			setWorkDayTMR(workDay);
		}

		public String getWorkDayAndBc() {
			return workDay;
		}
		
		public String getWorkDayTMR() {
			
			return workDayTMR;
		}

		public void setWorkDayTMR(String workDayTMR) {
			String strDate2 = null;
			try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//参数为要格式化时间日期的模式
			Date  date = df.parse(workDayTMR);	
//			String dateStr = df.format(date);//将日期按照定义的模式转换成字符串
//			Log.v("test","dateStr :"+dateStr);
			
			Calendar cal = Calendar.getInstance();  
			cal.setTime(date);
			cal.set(Calendar.HOUR_OF_DAY, 0); 
			cal.set(Calendar.MINUTE, 0); 
			cal.set(Calendar.SECOND, 0); //设置时分秒都为0  
//			day = cal.getTime();       //day为2008-08-03 00:00:00  
			cal.add(Calendar.DAY_OF_YEAR, 1);  //小时加1  
			Date d2 = cal.getTime();   //d2为2008-08-04 00:00:00  
			strDate2 = df.format(d2);
			Log.v("test", " +1天："+strDate2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
			}//将字符串按照定义的模式转换为Date对象
			this.workDayTMR = strDate2;
			
		}
		
		
		
		public String getWorkGdm() {
			return workGdm;
		}
		public void setWorkGdm(String workGdm) {
			this.workGdm = workGdm;
		}
		
		public String[] getGdm(){
			String[] s = null;
			if(this.workGdm != null){
				s =	this.workGdm.split(",");
			}
			return s;
		}
		
		
		public int[] getFunctions(){
			
			String[] s = null;
			int[] ifunc = null;
			try{
				
				if(this.functions == null){
					Log.v("User.getFunctions()", this.functions);
				}else{
					
					s =	this.functions.split(",");
					ifunc = new int[s.length];
					
					for(int i = 0;i< s.length;i++){
//						if(Integer.parseInt(s[i]) == 5 || Integer.parseInt(s[i]) == 7){//收货，整车外交付屏蔽
//							continue;
//						}
						ifunc[i] = Integer.parseInt(s[i]);
					}
				}
			}catch(Exception e){
				return null;
			}
			return ifunc;
		}
		
		public void setFunctions(String functions) {
			this.functions = functions;
		}
		public String getPostName() {
			return postName;
		}
		public void setPostName(String postName) {
			this.postName = postName;
		}
		public int getGdmSize(){
			return getGdm().length;
			
		}
		
		public String getImgUrlMini() {
			return imgUrlMini;
		}
		public void setImgUrlMini(String imgUrlMini) {
			this.imgUrlMini = imgUrlMini;
		}
		public String getImgUrl() {
			return imgUrl;
		}
		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
		public boolean isLoadErr() {
			boolean b = getLoginState() != 0;
			return b;
		}

		public String getLoadErrMess() {
			String s = "";
			switch (getLoginState()) {
			case 1:
				s = "用户密码错误,请再次登录！";
				break;
			case 2:
				s = "已交班不允许登录,请联系值班主任！";
				break;
			case 3:
				s = "工号有误（非当班人员）,请联系值班主任！";
				break;
			
			case 4:
				//	public static int REPEAT_ERROR = 4;//重复登录

				s = "重复登录,请联系综控室！";
				break;
			}
			return s;
		}
		public Vector<String> getVeRegionGroup() {
			// TODO Auto-generated method stub
			return null;
		}
		public Vector<String> getRegionGroupMan(String regionGroup2) {
			// TODO Auto-generated method stub
			return null;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		public String getPostId() {
			return postId;
		}

		public void setPostId(String postId) {
			this.postId = postId;
		}
		
		
}
