package io.transport.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

/**
 * 对象操作工具类, 继承org.apache.commons.lang3.ObjectUtils类
 * 
 * @author Wangl.sir <983708408@qq.com>
 * @version v1.0
 * @time 2017年4月13日
 * @since
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

	/**
	 * 注解到对象复制，只复制能匹配上的方法。
	 * 
	 * @param annotation
	 * @param object
	 */
	public static void annotationToObject(Object annotation, Object object) {
		if (annotation != null && object != null) {
			Class<?> annotationClass = annotation.getClass();
			Class<?> objectClass = object.getClass();
			if (objectClass != null) {
				for (Method m : objectClass.getMethods()) {
					if (StringUtils.startsWith(m.getName(), "set")) {
						try {
							String s = StringUtils.uncapitalize(StringUtils.substring(m.getName(), 3));
							Object obj = annotationClass.getMethod(s).invoke(annotation);
							if (obj != null && !"".equals(obj.toString())) {
								if (object == null) {
									object = objectClass.newInstance();
								}
								m.invoke(object, obj);
							}
						} catch (Exception e) {
							// 忽略所有设置失败方法
						}
					}
				}
			}
		}
	}

	/**
	 * 序列化对象
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			if (object != null) {
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(object);
				return baos.toByteArray();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
				oos = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (baos != null)
					baos.close();
				baos = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 反序列化对象
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			if (bytes != null && bytes.length > 0) {
				bais = new ByteArrayInputStream(bytes);
				ois = new ObjectInputStream(bais);
				return ois.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
				ois = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (bais != null)
					bais.close();
				bais = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
