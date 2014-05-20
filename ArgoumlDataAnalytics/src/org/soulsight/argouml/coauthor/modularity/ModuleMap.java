package org.soulsight.argouml.coauthor.modularity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleMap {
	private Map<String, List<Integer>> classModuleIdMap = new HashMap<String, List<Integer>>();
	public Map<String, List<Integer>> getClassModuleIdMap() {
		return classModuleIdMap;
	}

	public Map<Integer, List<String>> getModuleIdClassesMap() {
		return moduleIdClassesMap;
	}

	private Map<Integer, List<String>> moduleIdClassesMap = new HashMap<Integer, List<String>>();
	
	public ModuleMap(String filename) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		while((line = reader.readLine()) != null)
		{
			String[] splits = line.split("\t");
			if(splits.length < 2)
			{
				continue;
			}
			try
			{
				int module = Integer.parseInt(splits[1]);
				add(splits[0], module);
			}
			catch(Exception e)
			{
				//System.out.println(e);
			}
		}
		reader.close();
	}
	
	public void add(String className, int module)
	{
		if(!classModuleIdMap.containsKey(className))
		{
			List<Integer> modules = new ArrayList<Integer>();
			modules.add(module);
			classModuleIdMap.put(className, modules);
		}
		else
		{
			classModuleIdMap.get(className).add(module);
		}
		
		if(!moduleIdClassesMap.containsKey(module))
		{
			List<String> classes = new ArrayList<String>();
			classes.add(className);
			moduleIdClassesMap.put(module, classes);
		}
		else
		{
			moduleIdClassesMap.get(module).add(className);
		}
	}
	
	public List<String> getModuleClasses(int module)
	{
		return moduleIdClassesMap.get(module);
	}
	
	public List<Integer> getClassModules(String className)
	{
		return classModuleIdMap.get(className);
	}
	
	public static void main(String[] args) throws IOException
	{
		ModuleMap map = new ModuleMap("./data/module.txt");
		
		System.out.println(map.getClassModules("org.argouml.model.ModelCommand").get(0));
		
		System.out.println(map.getModuleClasses(2).get(0));
	}
}
