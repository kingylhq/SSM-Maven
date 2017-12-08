package com.lq.util.log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

/**
 * 记录系统日志打印
 * @author liqian
 * 2017-12-7 14:53:03
 */
public class AroundAdvice implements MethodInterceptor {

	private static final Logger logger = Logger.getLogger(AroundAdvice.class);

	 //在测试中检查是否正确
	public String getBeanValue(Object obj) {
		if (obj == null) {
			return "null";
		}
		if ((obj instanceof String)) {
			return (String) obj;
		}
		if ((obj instanceof Integer)) {
			return String.valueOf(((Integer) obj).intValue());
		}
		if ((obj instanceof Float)) {
			return String.valueOf(((Float) obj).floatValue());
		}
		if ((obj instanceof Long)) {
			return String.valueOf(((Long) obj).longValue());
		}
		if ((obj instanceof Character)) {
			return String.valueOf(((Character) obj).charValue());
		}
		if ((obj instanceof Short)) {
			return String.valueOf(((Short) obj).shortValue());
		}
		if ((obj instanceof Byte)) {
			return String.valueOf(((Byte) obj).byteValue());
		}
		if ((obj instanceof Boolean)) {
			return String.valueOf(((Boolean) obj).booleanValue());
		}
		if ((obj instanceof Double)) {
			return String.valueOf(((Double) obj).doubleValue());
		}
		if (obj.getClass().getName().startsWith("java.")) {
			return obj.toString();
		}

		StringBuffer buff = new StringBuffer();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0, j = fields.length; i < j; i++) {
			String propertyName = fields[i].getName();
			if (!"serialVersionUID".toLowerCase().endsWith(
					propertyName.toLowerCase())) {
				Object propertyValue = null;
				try {
					propertyValue = BeanUtils.getProperty(obj, propertyName);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				buff.append(propertyName).append("=[").append(propertyValue)
				.append("] ");
			}
		}
		return buff.toString();

	}

	public String getBeanValue11(Object obj) {

		StringBuffer buff = new StringBuffer();

		Method[] m = obj.getClass().getDeclaredMethods();
		final int idx = 3;
		try {
			for (int i = 0; i < m.length; i++) {
				String methodName = m[i].getName();
				if (methodName.startsWith("get")
						&& !methodName.endsWith("Class")
						&& !methodName.endsWith("UserPwd")) {
					Object o = m[i].invoke(obj, new Object[] {});
					if (o != null) {
						buff.append(methodName.substring(idx)).append("=[")
								.append(o).append("] ");
					}
				}
			}
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		} catch (InvocationTargetException ite) {
			ite.printStackTrace();
		}
		return buff.toString();
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object args[] = invocation.getArguments();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("AOP invoke start--"
				+ invocation.getMethod().getDeclaringClass() + "."
				+ invocation.getMethod().getName() + " ");

		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				stringBuffer.append("args[" + i + "]:" + "null");
			} else {
				stringBuffer.append(args[i].getClass() + " : "
						+ getBeanValue(args[i]));
			}

		}
		logger.info(stringBuffer.toString());
		 // 调用目标方法，如不调用，目标方法将不被执行
		Object obj = invocation.proceed();
		logger.info("AOP invoke end--"
				+ invocation.getMethod().getDeclaringClass() + "."
				+ invocation.getMethod().getName() + " return:[class:"
				+ (obj == null ? "null" : obj.getClass().getName()) + " value:"
				+ (obj == null ? "null" : getBeanValue(obj)) + "]");
		return obj;

	}

}
