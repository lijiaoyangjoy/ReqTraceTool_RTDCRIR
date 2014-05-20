package org.soulsight.argouml.coauthor.recommend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.soulsight.argouml.coauthor.recommend.ClassRelationMap.ClassRelation;

public class ModuleRecommender {
	private ClassRelationMap crMap;
	private int topK;
	private int maxRec;
	
	public ModuleRecommender(ClassRelationMap crMap)
	{
		this(crMap, 10, 1);
	}
	
	public ModuleRecommender(String crMapFilename) throws IOException
	{
		this(crMapFilename, 10, 1);
	}
	
	public ModuleRecommender(String crMapFilename, int topK, int maxRec) throws IOException
	{
		this(new ClassRelationMap(crMapFilename), topK, maxRec);
	}
	
	public ModuleRecommender(ClassRelationMap crMap, int topK, int maxRec)
	{
		this.crMap = crMap;
		this.topK = topK;
		this.maxRec = maxRec;
	}
	
	public void recommendFile(String inputFilename, String outputFilename) throws IOException
	{
		List<String> originalClasses = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
		String line;
		int acc = 0;
		while((line = reader.readLine()) != null)
		{
			String[] splits = line.split("\t");
			if(splits.length < 2)
			{
				continue;
			}
			
			if(++acc > topK)
			{
				break;
			}
			originalClasses.add(splits[0]);
		}
		reader.close();
		
		Set<String> containedClasses = new HashSet<String>(originalClasses);
		
		List<String> recommendClasses = new ArrayList<String>();
		for(String originalClass : originalClasses)
		{
			List<ClassRelation> relations = crMap.getRelations(originalClass);
			if(relations == null)
			{
				continue;
			}

			for(int i = 0; i < Math.min(maxRec, relations.size()); i++)
			{
				String className = relations.get(i).getClassName();
				if(!containedClasses.contains(className))
				{
					recommendClasses.add(className);
					containedClasses.add(className);
				}
			}
		}
		
		List<String> results = new ArrayList<String>();
		results.addAll(originalClasses);
		results.addAll(recommendClasses);
		
		//Collections.sort(results);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename));
		for(String clazz : results)
		{
			writer.write(clazz + "\t-1\n");
		}
		writer.close();
	}
	
	public void recommendDir(String inputDir, String outputDir) throws IOException
	{
		File idir = new File(inputDir);
		if(!idir.exists() || !idir.isDirectory())
		{
			throw new IOException(inputDir + " not exists or not a dir");
		}
		
		File odir = new File(outputDir);
		if(!odir.exists())
		{
			odir.mkdirs();
		}
		
		for(String filename : idir.list())
		{
			recommendFile(inputDir + filename, outputDir + filename);
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		ModuleRecommender recommeder = 
				new ModuleRecommender("./data/Class-pair-weighted-edges.txt", 10, 1);
		recommeder.recommendDir("./data/result/IR-only/", "./data/result/IR-mod_rec/");
	}

}
