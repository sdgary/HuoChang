package byxx.android.huochang.util;

import java.sql.Timestamp;
import java.text.CollationKey;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * 公共排序类
 * @author Jayden
 *
 */
@SuppressWarnings("unchecked")
public class CSort {

	private static CSort instance = null;

	/**
	 * 降序
	 */
	public final static Integer SORT_DESC = new Integer(1);

	/**
	 *  升序
	 */
	public final static Integer SORT_ASC = new Integer(2);
	
	public final static RuleBasedCollator COLLATOR = (RuleBasedCollator) Collator.getInstance(java.util.Locale.CHINA);
	
	public static CSort getInstance() {
		if (instance == null) {
			instance = new CSort();
		}
		return instance;
	}

	/**
	 * 
	 * @param list   List 需要排序的集合
	 * @param sp	  SortParameter 排序参数
	 */
	public void sort(List list,SortParameter sp){
		

		String[] fields = sp.getFields();

		Integer[] sortTypes = sp.getSortTypes();
		
		
		StringBuffer[] methods = new StringBuffer[fields.length];
		for(int i = 0 ; i< fields.length;i++){
			StringBuffer methodName =new StringBuffer(fields[i]); 
			methods[i] = new StringBuffer("get"
					+ Character.toUpperCase(methodName.charAt(0))
					+ methodName.delete(0, 1));
		}
		
		Integer[] confirmSortTypes = null;
		if(sortTypes == null || sortTypes.length < fields.length){
			confirmSortTypes = new Integer[fields.length];
			for(int i = 0 ; i < confirmSortTypes.length ; i++){
				if(sortTypes != null){
					if(i + 1 <= sortTypes.length){
						confirmSortTypes[i] = sortTypes[i];
					}else{
						confirmSortTypes[i] = CSort.SORT_ASC;
					}
				}else{
					confirmSortTypes[i] = CSort.SORT_ASC;
				}
				
			}
		}
		if(confirmSortTypes == null){
			Collections.sort(list, comparatorToObject(methods, sp.getCl(), sortTypes));
		}else{
			Collections.sort(list, comparatorToObject(methods, sp.getCl(), confirmSortTypes));
		}
	}
	
	/**
	 * JDK自带方法
	 * @param list
	 * @param com
	 */
	public void sort(List list,Comparator com){
		if (com==null) {
			Collections.sort(list);
		}else {
			Collections.sort(list, com);
		}
	}
	
	/**
	 * 排序
	 * 
	 * @param list
	 *            需要排序的集合
	 * @param classes
	 *            集合里存放的对象类型
	 * @param methodName
	 *            根据哪个属性排序
	 * @param sortType
	 *            排序类型(CSort.SORT_DESC 降序 CSort.SORT_ACS 升序)
	 */
	public void sort(List list, Class classes, String field,
			Integer sortType) {
		
		StringBuffer methodName =new StringBuffer(field); 
		StringBuffer mn = new StringBuffer("get"
				+ Character.toUpperCase(methodName.charAt(0))
				+ methodName.delete(0, 1));
		Collections.sort(list, comparatorToObject(mn, classes, sortType));
	}

	
//	/**
//	 * 多列排序 只对String类型的列排序
//	 * @param list 数据集
//	 * @param classes 数据集中的对象类型
//	 * @param fields 要排列的属性名(按传入的顺序进行排序)
//	 * @param sortType 排序类型
//	 */
//	public void sort(final List list,final Class classes,final String[] fields,final Integer sortType){
//		
//		Collections.sort(list,new Comparator(){
//			public int compare(Object arg0, Object arg1) { 
//				for(int i=0;i<fields.length;i++){
//					String v1=getStringValue(arg0,classes,fields[i]);
//					String v2=getStringValue(arg1,classes,fields[i]);
//					if(sortType==SORT_DESC){//倒序
//						if(!v1.equals(v2)){
//							return v2.compareTo(v1);
//						}
//					}else if(sortType==SORT_ASC){//顺序
//						if(!v1.equals(v2)){
//							return v1.compareTo(v2);
//						}
//					}
//				}
//				return 0;
//			}
//		});
//	} 
//	
//	public String getStringValue(Object obj,Class classes,String field){
//		
//		try{
//		StringBuffer fieldName =new StringBuffer(field); 
//		StringBuffer methodName = new StringBuffer("get"
//				+ Character.toUpperCase(fieldName.charAt(0))
//				+ fieldName.delete(0, 1)); 
//		Object value = Reflect.getInstance().getValueNoIntercept(classes,
//				obj, methodName);
//		if(value!=null){
//			return value.toString();
//		}
//		
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//		return ""; 
//	}

	private Comparator comparatorToObject(final StringBuffer[] methodName,
			final Class classes, final Integer[] sortType) {
		Comparator c = new Comparator() {
			public int compare(Object o1, Object o2) {
				
				int sortMark = 0;
				for(int i = 0;i<methodName.length;i++){
					sortMark = compareMethod(o1,o2,methodName[i],classes,sortType[i]);
					if(sortMark != 0) break;
				}
				return sortMark;
			}
		};
		return c;
	}
	
	private Comparator comparatorToObject(final StringBuffer methodName,
			final Class classes, final Integer sortType) {
		Comparator c = new Comparator() {
			public int compare(Object o1, Object o2) {
				return compareMethod(o1,o2,methodName,classes,sortType);
			}
		};
		return c;
	}
	
	private int compareMethod(Object o1, Object o2,StringBuffer methodName,
			 Class classes, Integer sortType) {
		Object value1 = null;
		Object value2 = null;
		int returnValue = 1;
		try {
			// 第一个对象的值
			value1 = Reflect.getInstance().getValueNoIntercept(classes,
					o1, methodName);
			// 第二个对象的值
			value2 = Reflect.getInstance().getValueNoIntercept(classes,
					o2, methodName);
		} catch (Exception e) {
		}
		if (value1 instanceof Integer) {// Integer 类型
			if (value1 == null || value2 == null) {
				returnValue = 1;
			} else {
				if (Integer.parseInt(value1.toString()) < Integer
						.parseInt(value2.toString())) {
					if (sortType.intValue() == CSort.SORT_ASC
							.intValue()) {
						returnValue = -1;
					} else {
						returnValue = 1;
					}
				} else if (Integer.parseInt(value1.toString()) == Integer
						.parseInt(value2.toString())) {
					returnValue = 0;
				} else {
					if (sortType.intValue() == CSort.SORT_ASC
							.intValue()) {
						returnValue = 1;
					} else {
						returnValue = -1;
					}
				}
			}
		} else if (value1 instanceof Double) {// Double 类型
			if (value1 == null || value2 == null) {
				returnValue = 1;
			} else {
				if (Double.parseDouble(value1.toString()) < Double
						.parseDouble(value2.toString())) {
					if (sortType.intValue() == CSort.SORT_ASC
							.intValue()) {
						returnValue = -1;
					} else {
						returnValue = 1;
					}
				} else if (Double.parseDouble(value1.toString()) == Double
						.parseDouble(value2.toString())) {
					returnValue = 0;
				} else {
					if (sortType.intValue() == CSort.SORT_ASC
							.intValue()) {
						returnValue = 1;
					} else {
						returnValue = -1;
					}
				}
			}
		} else if (value1 instanceof Long) {// Long 类型
			if (value1 == null || value2 == null) {
				returnValue = 1;
			} else {
				if (Long.parseLong(value1.toString()) < Long
						.parseLong(value2.toString())) {
					if (sortType.intValue() == CSort.SORT_ASC
							.intValue()) {
						returnValue = -1;
					} else {
						returnValue = 1;
					}
				} else if (Long.parseLong(value1.toString()) < Long
						.parseLong(value2.toString())) {
					returnValue = 0;
				} else {
					if (sortType.intValue() == CSort.SORT_ASC
							.intValue()) {
						returnValue = 1;
					} else {
						returnValue = -1;
					}
				}
			}
		} else if (value1 instanceof Float) {// Float 类型
			if (value1 == null || value2 == null) {
				returnValue = 1;
			} else {
				if (Float.parseFloat(value1.toString()) < Float
						.parseFloat(value2.toString())) {
					if (sortType.intValue() == CSort.SORT_ASC
							.intValue()) {
						returnValue = -1;
					} else {
						returnValue = 1;
					}
				} else if (Float.parseFloat(value1.toString()) == Float
						.parseFloat(value2.toString())) {
					returnValue = 0;
				} else {
					if (sortType.intValue() == CSort.SORT_ASC
							.intValue()) {
						returnValue = 1;
					} else {
						returnValue = -1;
					}
				}
			}
		} else if (value1 instanceof Boolean) {// Boolean 类型
			if (value1 == null || value2 == null) {
				returnValue = 1;
			} else {
				if (Boolean.valueOf(value1.toString()).booleanValue()
						&& Boolean.valueOf(value2.toString())
								.booleanValue()) {// 两个都是 true
					returnValue = 0;
				} else if (Boolean.valueOf(value1.toString())
						.booleanValue()
						&& !Boolean.valueOf(value2.toString())
								.booleanValue()) {// 第一个 true 第二个false
					if (sortType.intValue() == CSort.SORT_ASC
							.intValue()) {
						returnValue = 1;
					} else {
						returnValue = -1;
					}
				} else {
					if (sortType.intValue() == CSort.SORT_ASC
							.intValue()) {
						returnValue = -1;
					} else {
						returnValue = 1;
					}
				}
			}
		}
//		else if (value1 instanceof String) {// String 类型
//			if (value1 == null || value2 == null) {
//				returnValue = 1;
//			} else {
//				returnValue = value1.toString().compareTo(
//						value2.toString());
//				if (returnValue <= -1) {
//					if (sortType.intValue() == CSort.SORT_ASC
//							.intValue())
//						returnValue = -1;
//					else
//						returnValue = 1;
//	
//				} else if (returnValue >= 1) {
//					if (sortType.intValue() == CSort.SORT_ASC
//							.intValue())
//						returnValue = 1;
//					else
//						returnValue = -1;
//				} else {
//					returnValue = 0;
//				}
//		}
		else if (value1 instanceof Timestamp) {// Timestamp 类型
//			if (value1 == null || value2 == null) {
//				returnValue = -1;
//			} 
			if(value1 == null && value2 != null){
				returnValue = -1;
			}else if(value1 != null && value2 == null){
				returnValue = 1;
			}else if (value1 == null && value2 == null) {
				returnValue = 0;
			}else {
				if (((Timestamp) value1).before((Timestamp) value2)) {
					if (sortType.intValue() == CSort.SORT_ASC
							.intValue())
						returnValue = -1;
					else
						returnValue = 1;
				} else if (((Timestamp) value1)
						.after(((Timestamp) value2))) {
					if (sortType.intValue() == CSort.SORT_ASC
							.intValue())
						returnValue = 1;
					else
						returnValue = -1;
				} else {
					returnValue = 0;
				}
			}
		} else {// 其它对象类型(与String类型使用同样的处理方案)
			if(value1 == null && value2 != null){
//				returnValue = -1;
				return -1;
			}
			if(value1 != null && value2 == null){
//				returnValue = 1;
				return 1;
			}
			if (value1 == null && value2 == null) {
//				returnValue = 0;
				return 0;
			}
			String str1 = value1.toString();
			String str2 = value2.toString();
			CollationKey c1 = COLLATOR.getCollationKey(str1);
			CollationKey c2 = COLLATOR.getCollationKey(str2);
			if (sortType.intValue() == CSort.SORT_ASC.intValue()){
				if(str1.trim().equals("") && !str2.trim().equals("")) return  -1;
				if(!str1.trim().equals("") && str2.trim().equals("")) return 1;
				if(str1.startsWith("-") && !str2.startsWith("-")){
					return -1;
				}
				if(!str1.startsWith("-") && str2.startsWith("-")){
					return 1;
				}
				return COLLATOR.compare(((CollationKey) c1).getSourceString(),
						((CollationKey) c2).getSourceString());
			}else if( sortType.intValue() == CSort.SORT_DESC.intValue()){
				if(str1.trim().equals("") && !str2.trim().equals("")) return 1;
				if(!str1.trim().equals("") && str2.trim().equals("")) return -1;
				if(str1.startsWith("-") && !str2.startsWith("-")){
					return 1;
				}
				if(!str1.startsWith("-") && str2.startsWith("-")){
					return -1;
				}
				return COLLATOR.compare(((CollationKey) c2).getSourceString(),
						((CollationKey) c1).getSourceString());
			}
		}
		return returnValue;
	}
	
//	public static void main(String[] args) {
//		CSort.getInstance().sort(list, People.class, "age", CSort.SORT_ASC);
//	}
	
}
