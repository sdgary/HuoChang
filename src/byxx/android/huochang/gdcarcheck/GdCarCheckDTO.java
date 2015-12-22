package byxx.android.huochang.gdcarcheck;

import java.io.Serializable;
import java.sql.Timestamp;

public class GdCarCheckDTO implements Serializable {
	/**
    *
    */
   private static final long serialVersionUID = 6415941501806650632L;
   public static final int TYPE_CHECK_FL = 0; //检查类型、防溜
   public static final int TYPE_CHECK_CC = 1; //检查类型、查车
   public static final int TYPE_CHECK_CK = 2; //检查类型、仓库巡查
   public static final long FL_CHECK_TIME = 4 * 60 * 60 * 1000; //防溜 检查 时间间隔
   public static final long CC_CHECK_TIME = 30 * 60 * 1000; //查车 检查 时间间隔
   public static final long CK_CHECK_TIME = 2 * 60 * 60 * 1000; //仓库 检查 时间间隔
   public static final int CHECK_CS_FL = 2; //每次检查防溜次数
   public static final int CHECK_CS_CC = 1; //每次查车检查次数
   public static final int CHECK_CS_CK = 1; //每次仓库检查次数
   public static final String[] zyTypeStates = {"防溜检查", "列车检查", "仓库巡查"};
   public static final int[] zyCheckCs = {CHECK_CS_FL, CHECK_CS_CC, CHECK_CS_CK};
   private Integer checkId;
   private int checkType;
   private String zmlm;
   private String gdm;
   private Timestamp startTime;
   private Timestamp endTime;
   private int photoId;
   private String icCardId1;
   private Timestamp icCardTime1;
   private String icCardId2;
   private Timestamp icCardTime2;
   private String icCardId3;
   private Timestamp icCardTime3;
   private String icCardId4;
   private Timestamp icCardTime4;
   private String icCardId5;
   private Timestamp icCardTime5;
   private String gjh;
   private Timestamp planEndTime;
   private Timestamp createTime;
   private String rummager;
   public Integer getCheckId() {
       return checkId;
   }

   public void setCheckId(Integer checkId) {
       this.checkId = checkId;
   }

   public int getCheckType() {
       return checkType;
   }

   public void setCheckType(int checkType) {
       this.checkType = checkType;
   }

   public String getZmlm() {
       return zmlm;
   }

   public void setZmlm(String zmlm) {
       this.zmlm = zmlm;
   }

   public String getGdm() {
       return gdm;
   }

   public void setGdm(String gdm) {
       this.gdm = gdm;
   }

   public Timestamp getStartTime() {
       return startTime;
   }

   public void setStartTime(Timestamp startTime) {
       this.startTime = startTime;
   }

   public Timestamp getCreateTime() {
       return createTime;
   }

   public void setCreateTime(Timestamp createTime) {
       this.createTime = createTime;
   }

   public Timestamp getEndTime() {
       return endTime;
   }

   public void setEndTime(Timestamp endTime) {
       this.endTime = endTime;
   }

   public int getPhotoId() {
       return photoId;
   }

   public void setPhotoId(int photoId) {
       this.photoId = photoId;
   }

   public String getIcCardId1() {
       return icCardId1;
   }

   public void setIcCardId1(String icCardId1) {
       this.icCardId1 = icCardId1;
   }

   public Timestamp getIcCardTime1() {
       return icCardTime1;
   }

   public void setIcCardTime1(Timestamp icCardTime1) {
       this.icCardTime1 = icCardTime1;
   }

   public String getIcCardId2() {
       return icCardId2;
   }

   public void setIcCardId2(String icCardId2) {
       this.icCardId2 = icCardId2;
   }

   public Timestamp getIcCardTime2() {
       return icCardTime2;
   }

   public void setIcCardTime2(Timestamp icCardTime2) {
       this.icCardTime2 = icCardTime2;
   }

   public String getIcCardId3() {
       return icCardId3;
   }

   public void setIcCardId3(String icCardId3) {
       this.icCardId3 = icCardId3;
   }

   public Timestamp getIcCardTime3() {
       return icCardTime3;
   }

   public void setIcCardTime3(Timestamp icCardTime3) {
       this.icCardTime3 = icCardTime3;
   }

   public String getIcCardId4() {
       return icCardId4;
   }

   public void setIcCardId4(String icCardId4) {
       this.icCardId4 = icCardId4;
   }

   public Timestamp getIcCardTime4() {
       return icCardTime4;
   }

   public void setIcCardTime4(Timestamp icCardTime4) {
       this.icCardTime4 = icCardTime4;
   }

   public String getIcCardId5() {
       return icCardId5;
   }

   public void setIcCardId5(String icCardId5) {
       this.icCardId5 = icCardId5;
   }

   public Timestamp getIcCardTime5() {
       return icCardTime5;
   }

   public void setIcCardTime5(Timestamp icCardTime5) {
       this.icCardTime5 = icCardTime5;
   }

   public String getGjh() {
       return gjh;
   }

   public void setGjh(String gjh) {
       this.gjh = gjh;
   }

   public Timestamp getPlanEndTime() {
       return planEndTime;
   }

   public void setPlanEndTime(Timestamp planEndTime) {
       this.planEndTime = planEndTime;
   }

   public String getRummager() {
       return rummager;
   }

   public void setRummager(String rummager) {
       this.rummager = rummager;
   }

   public int getCheckCs() {
       return zyCheckCs[checkType];
   }
    public String getCheckTypeName(){
    	return zyTypeStates[getCheckType()];
    }
    

    
    public int getNumStars(){
    	int i = 0;
    	if(getIcCardTime1() != null){
    		i+=1;
    	}else if(getIcCardTime2() != null){
    		i+=1;
    	}
    	return i;
    }
}
