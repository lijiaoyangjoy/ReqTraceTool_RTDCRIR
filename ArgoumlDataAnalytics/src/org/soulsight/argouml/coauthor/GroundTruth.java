package org.soulsight.argouml.coauthor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroundTruth {
	
	private static Map<String, List<String>> groundTruthMap = new HashMap<String, List<String>>();
	
	public static void init(String dirname) throws IOException
	{
		File dir = new File(dirname);
		String[] files = null;
		if(dir.exists() && dir.isDirectory())
		{
			files = dir.list();
		}
		
		System.out.println(dirname);
		
		for(String filename : files)
		{
			System.out.println(filename);
			initReqGroudTruth(dirname + filename);
		}
		System.out.println(groundTruthMap);
	}
	
	private static void initReqGroudTruth(String filename) throws IOException
	{
		int dirSep = -1;
		if(filename.lastIndexOf('/') == -1)
		{
			dirSep = filename.lastIndexOf('\\');
		}
		
		String reqName = filename.substring(dirSep + 1, filename.lastIndexOf('.'));
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;
		List<String> gtList = new ArrayList<String>();
		while((line = reader.readLine()) != null)
		{
			String gt = line.trim();
			if(gt.isEmpty())
			{
				continue;
			}
			gtList.add(gt);
		}
		reader.close();
		groundTruthMap.put(reqName.toLowerCase(), gtList);
	}
	
	public static List<String> getGroundTruth(String reqName)
	{
		return groundTruthMap.get(reqName.toLowerCase());
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		init("./data/eval/");
		System.out.println(groundTruthMap);
	}

}
