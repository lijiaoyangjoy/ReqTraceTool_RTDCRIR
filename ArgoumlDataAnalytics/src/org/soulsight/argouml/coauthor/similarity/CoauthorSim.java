package org.soulsight.argouml.coauthor.similarity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.soulsight.argouml.coauthor.data.AuClass;
import org.soulsight.argouml.coauthor.data.AuRequirement;

public class CoauthorSim implements Similarity{

	@Override
	public double getScore(AuRequirement requirement, AuClass clazz) {
		TextSim sim = new TextSim();
		Integer clusterId = classClusterIdMap.get(clazz.getName());
		
		if(clusterId == null)
		{
			System.out.println(clazz.getName() + " not found");
			return sim.getScore(requirement, new AuClass(clazz.getName()));
		}
		
		List<String> classes = clusterClasses.get(clusterId);

		double clusterScore = 0.0;
		
		for(String className : classes)
		{
			clusterScore += sim.getScore(requirement, new AuClass(className));
		}
		return clusterScore / classes.size();
	}
	
	private static Map<Integer, List<String>> clusterClasses = new HashMap<Integer, List<String>>();
	private static Map<String, Integer> classClusterIdMap = new HashMap<String, Integer>();
	
	public static void init(String clustedFilename) throws IOException
	{
		System.out.println(clustedFilename);
		BufferedReader reader = new BufferedReader(new FileReader(clustedFilename));
		String line = "";
		while((line = reader.readLine()) != null)
		{
			String[] splits = line.split("\t");
			if(splits.length < 2)
			{
				continue;
			}
			
			Integer clusterId = null;
			try
			{
				clusterId = Integer.parseInt(splits[1]);
			}
			catch (NumberFormatException e) 
			{
				System.out.println("ClustedFilename" + line);
				continue;
			}	
			if(!clusterClasses.containsKey(clusterId))
			{
				clusterClasses.put(clusterId, new ArrayList<String>());
			}
			clusterClasses.get(clusterId).add(splits[0]);
			
			classClusterIdMap.put(splits[0], clusterId);
			
		}
		reader.close();
		
		System.out.println(clusterClasses);
		System.out.println(classClusterIdMap);
	}
}
