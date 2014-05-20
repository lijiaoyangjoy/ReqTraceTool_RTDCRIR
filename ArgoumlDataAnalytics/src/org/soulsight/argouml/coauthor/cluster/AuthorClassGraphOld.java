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

public class AuthorClassGraphOld {
	
	public static int coauthorIndex;
	
	public static void naiveClusterClasses(String inputFilename, String outputFilename) throws IOException
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
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename));
		writer.write(coauthorStrBuilder.toString());
		writer.write(classStrBuilder.toString());
		writer.close();
	}
	
	public static void batchCluster(String srcDir, String desDir) throws IOException
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
			naiveClusterClasses(srcDir + filename, desDir + filename);
		}
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException 
	{
		//String input = "D:/argouml-实验数据/数据库数据/开发者表（txt）/log.txt";
		//String output = "D:/argouml-实验数据/数据库数据/开发者表（txt）/log_class_cluster.txt";
		//naiveClusterClasses(input, output);
		
		String input = "./data/SVN_LOG_by_Versions/";
		String output = "./data/clustered/SVN_LOG_by_Versions/";
		coauthorIndex = 0;
		
		batchCluster(input, output);
	}

}
