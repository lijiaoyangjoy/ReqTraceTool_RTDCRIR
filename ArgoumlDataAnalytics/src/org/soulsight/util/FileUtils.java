package org.soulsight.util;

import java.io.File;

public class FileUtils {
	public static void delete(String location)
	{
		File file = new File(location);
		if(file.isFile())
		{
			file.delete();
		}
		else
		{
			for(String sub : file.list())
			{
				delete(location + sub);
			}
		}
	}
}
