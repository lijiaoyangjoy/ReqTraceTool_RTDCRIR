package org.soulsight.argouml.stat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AuthorPackageMatrix {

	private static void GetAuthorPackageFrequencyMatrix(String srcFilename, String outputFilename, String outputFilename2) throws IOException
	{
		Map<String, Integer> authorIdMap = new HashMap<String, Integer>();
		Map<String, Integer> packageIdMap = new HashMap<String, Integer>();
		
		List<String> authorNameList = new ArrayList<String>();
		List<String> packageNameList = new ArrayList<String>();
		
		String line = "";
		int authorIdIndex = 0;
		int packageIdIndex = 0;
		BufferedReader reader = new BufferedReader(new FileReader(srcFilename));
		while((line = reader.readLine()) != null)
		{
			String[] splits = line.split("\t");
			if(splits.length < 3)
			{
				continue;
			}
			String authorName = splits[0];
			//String className = splits[1];
			String packageName = splits[2];
			if(!authorIdMap.containsKey(authorName))
			{
				authorIdMap.put(authorName, authorIdIndex++);
				authorNameList.add(authorName);
			}
			if(!packageIdMap.containsKey(packageName))
			{
				packageIdMap.put(packageName, packageIdIndex++);
				packageNameList.add(packageName);
			}
		}
		reader.close();
		
		int[][] matrix = new int[authorIdMap.size()][packageIdMap.size()];
		int[][] matrix2 = new int[authorIdMap.size()][packageIdMap.size()];
		
		reader = new BufferedReader(new FileReader(srcFilename));
		Set<String> countedLines = new HashSet<String>(); 

		while((line = reader.readLine()) != null)
		{
			String[] splits = line.split("\t");
			if(splits.length < 3)
			{
				continue;
			}
			String authorName = splits[0];
			//String className = splits[1];
			String packageName = splits[2];
			
			int authorIndex = authorIdMap.get(authorName);
			int packageIndex = packageIdMap.get(packageName);
			matrix[authorIndex][packageIndex]++;
			
			if(!countedLines.contains(line))
			{
				matrix2[authorIndex][packageIndex]++;
			}
			countedLines.add(line);
		}
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename));
		
		for(String pack : packageNameList)
		{
			writer.write("\t" + pack);
		}
		writer.write("\n");
		for(int authorIndex = 0; authorIndex < authorIdMap.size(); authorIndex++)
		{
			writer.write(authorNameList.get(authorIndex));
			for(int packageIndex = 0; packageIndex < packageIdMap.size(); packageIndex++)
			{
				writer.write("\t" + matrix[authorIndex][packageIndex]);
			}
			writer.write("\n");
		}
		writer.close();
		
		BufferedWriter writer2 = new BufferedWriter(new FileWriter(outputFilename2));
		
		for(String pack : packageNameList)
		{
			writer2.write("\t" + pack);
		}
		writer2.write("\n");
		for(int authorIndex = 0; authorIndex < authorIdMap.size(); authorIndex++)
		{
			writer2.write(authorNameList.get(authorIndex));
			for(int packageIndex = 0; packageIndex < packageIdMap.size(); packageIndex++)
			{
				writer2.write("\t" + matrix2[authorIndex][packageIndex]);
			}
			writer2.write("\n");
		}
		writer2.close();
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException 
	{
		//String input = "D:\\argouml-实验数据\\数据库数据\\开发者表（txt）\\log.txt";
		//String output = "D:\\argouml-实验数据\\数据库数据\\开发者表（txt）\\log_frequency_matrix.txt";
		//String output2 = "D:\\argouml-实验数据\\数据库数据\\开发者表（txt）\\log_distinct_matrix.txt";
		String input = "";
		String output = "";
		String output2 = "";
		GetAuthorPackageFrequencyMatrix(input, output, output2);
	}

}
