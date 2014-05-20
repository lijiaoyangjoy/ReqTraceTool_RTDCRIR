package org.soulsight.argouml.coauthor;

import java.util.HashMap;
import java.util.Map;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class DataRespo {
	private static DataSource ds;
	private static Map<String, Instance> dsMap = new HashMap<String, Instance>();
	
	public static void init(String dataRespo) throws Exception
	{	

		ds = new DataSource(dataRespo);
		Instances insts = ds.getDataSet();
		
		for(Instance inst : insts)
		{
			dsMap.put(inst.stringValue(0), inst);
		}

	}
	
	public static Instance getInstance(String name)
	{
		return dsMap.get(name);
	}
	
	public static Instances getAllInstances() throws Exception
	{
		return ds.getDataSet(0);
	}
	
	public static void tfIdf(String input, String output) throws Exception {
		String[] options = { "-C",
				"-i", input, 
				"-o", output, 
				"-R","first-last", "-W", "20000", //1000-->20000
				"-prune-rate", "-1.0", "-T", "-I", "-N","0","-S", "-L",
//				"-prune-rate", "-1.0", "-T", "-I", "-N", "0",
//				"-stopwords","./data/original/stopWords.txt",
				"-stemmer", "weka.core.stemmers.NullStemmer", 
				"-c", "last",
				"-M", "1"};
		
		StringToWordVector.runFilter(new StringToWordVector(), options);
	}
	
}
