package org.soulsight.argouml.coauthor.recommend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassRelationMap {
	
	public static class ClassRelation implements Comparable<ClassRelation>
	{
		private String className;
		private double score;
		
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		
		public double getScore() {
			return score;
		}
		public void setScore(double score) {
			this.score = score;
		}
		@Override
		public int compareTo(ClassRelation o) {
			return - Double.compare(score, o.score);
		}
	}
	
	private Map<String, List<ClassRelation>> crMap = 
			new HashMap<String, List<ClassRelation>>();
	
	public ClassRelationMap(String filename) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		while((line = reader.readLine()) != null)
		{
			String[] splits = line.split("\t");
			if(splits.length < 3)
			{
				continue;
			}
			
			List<ClassRelation> relations = crMap.get(splits[0]);
			if(relations == null)
			{
				relations = new ArrayList<ClassRelation>();
				crMap.put(splits[0], relations);
			}
			
			ClassRelation cr = new ClassRelation();
			cr.setClassName(splits[1]);
			cr.setScore(Double.parseDouble(splits[2]));
			
			relations.add(cr);
		}
		reader.close();
		
		for(List<ClassRelation> value : crMap.values())
		{
			Collections.sort(value);
		}
	}
	
	public List<ClassRelation> getRelations(String className)
	{
		return crMap.get(className);
	}
	
	public static void main(String[] args) throws IOException
	{
		String filename = "./data/Class-pair-weighted-edges.txt";
		ClassRelationMap crMap = new ClassRelationMap(filename);
		
		String testClassName = "org.argouml.uml.diagram.layout.LayoutedEdge";
		
		System.out.println(crMap.getRelations(testClassName).size());
	}

}
