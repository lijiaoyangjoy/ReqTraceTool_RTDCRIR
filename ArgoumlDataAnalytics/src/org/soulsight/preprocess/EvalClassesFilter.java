package org.soulsight.preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import org.soulsight.util.ClassUtils;
import org.soulsight.util.FileUtils;

public class EvalClassesFilter {

	public static void filterByClasses(String filterFile, String tarDir, String outputDir) throws IOException
	{
		HashSet<String> tarClasses = new HashSet<String>();
		BufferedReader reader = new BufferedReader(new FileReader(filterFile));
		String line;
		while((line = reader.readLine()) != null)
		{
			String[] splits = line.split("\t");
			if(splits.length < 2)
			{
				continue;
			}
			
			tarClasses.add(ClassUtils.getShortName(splits[0]));
		}
		reader.close();
		
		File tarDirFile = new File(tarDir);
		if(!tarDirFile.exists() || !tarDirFile.isDirectory())
		{
			throw new IOException();
		}
		
		File outputDirFile = new File(outputDir);
		if(outputDirFile.exists())
		{
			FileUtils.delete(outputDir);
		}
		outputDirFile.mkdirs();
		
		for(String tarFile : tarDirFile.list())
		{
			BufferedReader r = new BufferedReader(new FileReader(tarDir + tarFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputDir + tarFile));
			String lineStr;
			while((lineStr = r.readLine()) != null)
			{
				if(tarClasses.contains(ClassUtils.getShortName(lineStr)))
				{
					writer.write(lineStr + "\n");
				}
			}
			r.close();
			writer.close();
		}
	}

	
	public static void main(String[] args) throws IOException {
		String filterFilename = "./data/original/argouml-codeWords-preprocessed.txt";
		String tarDir = "./data/eval/";
		String outputDir = "./data/eval_filter/";
		filterByClasses(filterFilename, tarDir, outputDir);
	}

}
