package org.soulsight.util;

public class ClassUtils {

	public static String getShortName(String classFullName)
	{
		int pos = classFullName.lastIndexOf(".");
		return classFullName.substring(pos + 1);
	}
	
}
