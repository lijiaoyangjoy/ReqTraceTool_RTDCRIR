package org.soulsight.argouml.coauthor.cluster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class NaiveClassCluster implements ClassCluster{
	
	public static int coauthorIndex = 0;
	
	public void cluster(String inputFilename, String outputFilename) throws IOException
	{
		Map<String, HashSet<String>> classAuthorSetMap = new HashMap<String, HashSet<String>>();
		
		BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
		String line = "";
		while((line = reader.readLine()) != null)
		{
			String[] splits = line.split("\t");
			if(splits.length < 3)
			{
				continue;
			}
			String className = splits[2] + "." + splits[1];
			if(!classAuthorSetMap.containsKey(className))
			{
				classAuthorSetMap.put(className, new HashSet<String>());
			}
			classAuthorSetMap.get(className).add(splits[0]);
		}
		reader.close();
		
		Map<HashSet<String>, HashSet<String>> coauthorClassesMap = new HashMap<HashSet<String>, HashSet<String>>();
		for(Map.Entry<String, HashSet<String>> entry : classAuthorSetMap.entrySet())
		{
			if(!coauthorClassesMap.containsKey(entry.getValue()))
			{
				coauthorClassesMap.put(entry.getValue(), new HashSet<String>());
			}
			coauthorClassesMap.get(entry.getValue()).add(entry.getKey());
		}
		
		StringBuilder coauthorStrBuilder = new StringBuilder();
		StringBuilder classStrBuilder = new StringBuilder();
//		int coauthorIndex = 0;
		for(Map.Entry<HashSet<String>, HashSet<String>> entry : coauthorClassesMap.entrySet())
		{
			if(entry.getKey().size() > 1)
			{
				coauthorStrBuilder.append(coauthorIndex);
				for(String author : entry.getKey())
				{
					coauthorStrBuilder.append("\t" + author); 
				}
				coauthorStrBuilder.append("\n");
				
				for(String clazz : entry.getValue())
				{
					classStrBuilder.append(clazz + "\t" + coauthorIndex + "\n");
				}
				
				coauthorIndex++;
			}
			else
			{
				String authorName = "";
				for(String author : entry.getKey())
				{
					authorName = author; 
				}
				
				for(String clazz : entry.getValue())
				{
					coauthorStrBuilder.append(coauthorIndex + "\t" + authorName); 
					coauthorStrBuilder.append("\n");
					
					classStrBuilder.append(clazz + "\t" + coauthorIndex + "\n");
					coauthorIndex++;
				}
			}
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename));
		writer.write(coauthorStrBuilder.toString());
		writer.write(classStrBuilder.toString());
		writer.close();
	}
	
	public void batchCluster(String srcDir, String desDir) throws IOException
	{
		File src = new File(srcDir);
		if(!src.isDirectory())
		{
			throw new IOException(srcDir + " not dir!");
		}
		
		File des = new File(desDir);
		
		if(!des.exists())
		{
			des.mkdirs();
		}
		
		for(String filename : src.list()){
			cluster(srcDir + filename, desDir + filename);
		}
	}
	
}
