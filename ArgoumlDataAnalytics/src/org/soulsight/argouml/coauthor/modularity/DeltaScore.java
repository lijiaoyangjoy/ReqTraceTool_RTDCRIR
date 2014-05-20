package org.soulsight.argouml.coauthor.modularity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeltaScore {
	private ModuleMap moduleMap;
	private double alpha;
	
	public DeltaScore(ModuleMap moduleMap)
	{
		//this.moduleMap = moduleMap;
		this(moduleMap, 0.0);
	}
	
	public DeltaScore(ModuleMap moduleMap, double alpha)
	{
		this.moduleMap = moduleMap;
		this.alpha = alpha;
	}
	
	public void moduleFile(String inputFilename, String outputFilename) throws IOException
	{
		Map<String, Double> orginalScores = new HashMap<String, Double>();
		
		BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
		String line;
		while((line = reader.readLine()) != null)
		{
			String[] splits = line.split("\t");
			if(splits.length < 2)
			{
				continue;
			}
			orginalScores.put(splits[0], Double.parseDouble(splits[1]));
		}
		reader.close();
		
		Map<String, Double> deltaScores = new HashMap<String, Double>();
		for(Map.Entry<String, Double> entry : orginalScores.entrySet())
		{
			double score = entry.getValue();
			List<Integer> modules = moduleMap.getClassModules(entry.getKey());
			if(modules == null)
			{
				deltaScores.put(entry.getKey(), score * alpha);
				continue;
			}
			
			for(Integer module : modules)
			{
				List<String> classes = moduleMap.getModuleClasses(module);
				
				for(String clazz : classes)
				{
					double s = 0;
					if(deltaScores.containsKey(clazz))
					{
						s = deltaScores.get(clazz);
					}
					//deltaScores.put(clazz, s + score / classes.size());
					deltaScores.put(clazz, s + score * alpha / classes.size());
					// Sum(modular class) * alpha
				}
			}
		}
		
		List<ScoredClass> results = new ArrayList<ScoredClass>();
		for(Map.Entry<String, Double> entry : orginalScores.entrySet())
		{
			ScoredClass scoredClass = new ScoredClass();
			scoredClass.setClassName(entry.getKey());
			
			double score = entry.getValue();
			if(deltaScores.containsKey(entry.getKey()))
			{
				score += deltaScores.get(entry.getKey());
			}
			scoredClass.setScore(score);
			
			results.add(scoredClass);
		}
		
		Collections.sort(results);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename));
		for(ScoredClass clazz : results)
		{
			writer.write(clazz.getClassName() + "\t" + clazz.getScore() + "\n");
		}
		writer.close();
	}
	
	public void moduleDir(String inputDir, String outputDir) throws IOException
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
			moduleFile(inputDir + filename, outputDir + filename);
		}
	}

}
