package byxx.android.huochang.util;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("unchecked")
public  class Reflect {
	private static Reflect instance = null;

	public static Reflect getInstance() {
		if (instance == null) {
			instance = new Reflect();
		}
		return instance;
	}

	/**
	 * 得到指定方法的返回值(无参方法)
	 * 
	 * @param classes
	 *            类类型
	 * @param obj
	 *            对象
	 * @param methodName
	 *            方法名称(只传对应的属性名)
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Object getValue(Class classes, Object obj, StringBuffer methodName)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Object value = null;
		try {
			value = classes.getMethod(
					"get" + Character.toUpperCase(methodName.charAt(0))
							+ methodName.substring(1, methodName.length()),
					new Class[] {}).invoke(obj);
		} catch (NoSuchMethodException e) {
			System.out.println("没有指定的方法：'" + methodName + "'");
			throw e;
		}
		return value;
	}
	/**
	 * 反射对象中属性的值
	 * @param classes 类型
	 * @param obj 实体对象
	 * @param field 属性名
	 * @return
	 */
	public Object getValue(Class classes, Object obj, String field) {
		try {

			return classes.getMethod(
					"get" + Character.toUpperCase(field.charAt(0))
							+ field.substring(1, field.length()),
					new Class[] {}).invoke(obj); 
			 
		} catch (Exception ex) {
			return null;
		}
	}
	/**
	 * 得到指定方法的返回值(无参方法)
	 * 
	 * @param classes
	 *            类类型
	 * @param obj
	 *            对象
	 * @param methodName
	 *            方法名称(传指定的方法名)
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Object getValueNoIntercept(Class classes, Object obj,
			StringBuffer methodName) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Object value = null;
		try {
			value = classes.getMethod(methodName.toString(), new Class[] {})
					.invoke(obj);
		} catch (NoSuchMethodException e) {
			System.out.println("没有指定的方法：'" + methodName + "'");
			throw e;
		}
		return value;
	}

	/**
	 * 得到指定方法的返回值(有参方法)
	 * 
	 * @param classes
	 *            类类型
	 * @param obj
	 *            对象
	 * @param methodName
	 *            方法名称
	 * @param parameterType
	 *            参数类型集合
	 * @param args
	 *            参数集合
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Object getValue(Class classes, Object obj, String methodName,
			Class[] parameterType, Object[] args)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Object value = null;
		value = classes.getMethod(methodName, parameterType).invoke(obj, args);
		return value;
	}

	/**
	 * 设置值
	 * @param classes
	 * @param obj
	 * @param methodName
	 * @param parameterType
	 * @param args
	 * @throws Exception
	 */
	public void setValue(Class classes, Object obj, String methodName,
			Class[] parameterType, Object[] args) throws Exception {
		classes.getMethod(
				"set" + Character.toUpperCase(methodName.charAt(0))
						+ methodName.substring(1, methodName.length()),
				parameterType).invoke(obj, args);
	} 
	/**
	 * 得到指定方法的返回值类型
	 * 
	 * @param classes
	 *            类类型
	 * @param obj
	 *            对象
	 * @param methodName
	 *            方法名称
	 * @return
	 * @throws NoSuchMethodException
	 */
	public Class getReturnType(Class classes, Object obj,
			StringBuffer methodName) throws NoSuchMethodException {
		try {
			return classes.getMethod(
					"get" + Character.toUpperCase(methodName.charAt(0))
							+ methodName.substring(1, methodName.length()),
					new Class[] {}).getReturnType();
		} catch (NoSuchMethodException e) {
			System.out.println("没有指定的方法：'" + methodName + "'");
			throw e;
		}
	}
}
