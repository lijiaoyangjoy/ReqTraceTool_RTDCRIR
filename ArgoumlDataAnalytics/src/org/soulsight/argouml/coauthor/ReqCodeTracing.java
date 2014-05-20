package org.soulsight.argouml.coauthor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.soulsight.argouml.coauthor.cluster.ClassCluster;
import org.soulsight.argouml.coauthor.cluster.NaiveClassCluster;
import org.soulsight.argouml.coauthor.cluster.NewNaiveClassCluster;
import org.soulsight.argouml.coauthor.data.AuClass;
import org.soulsight.argouml.coauthor.data.AuRequirement;
import org.soulsight.argouml.coauthor.evaluation.Evaluation;
import org.soulsight.argouml.coauthor.evaluation.FMeasureEval;
import org.soulsight.argouml.coauthor.evaluation.TopKPrecisionEval;
import org.soulsight.argouml.coauthor.evaluation.TopKRecallEval;
import org.soulsight.argouml.coauthor.evaluation.ThresholdRecallEval;
import org.soulsight.argouml.coauthor.evaluation.ThresholdPrecisionEval;
import org.soulsight.argouml.coauthor.modularity.DeltaScore;
import org.soulsight.argouml.coauthor.modularity.ModuleMap;
import org.soulsight.argouml.coauthor.similarity.HybridSim;
import org.soulsight.argouml.coauthor.similarity.Similarity;
import org.soulsight.argouml.coauthor.similarity.TextSim;

import weka.core.Instance;

public class ReqCodeTracing {
	private static Similarity similarity;
	//private static Evaluation evaluation;
	
	private static Evaluation[] evaluations;

	public static void setSimilarity(Similarity similarity) {
		ReqCodeTracing.similarity = similarity;
	}
	
	public static void setEvaluation(Evaluation evaluation) {
		//ReqCodeTracing.evaluation = evaluation;
		evaluations = new Evaluation[1];
		evaluations[0] = evaluation;
	}
	
	public static void setEvaluations(Evaluation[] evaluations) {
		ReqCodeTracing.evaluations = evaluations;
	}
	
	/**
	 * 复制一个目录及其子目录、文件到另外一个目录
	 * @param src
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFolder(File src, File dest) throws IOException {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// 递归复制
				copyFolder(srcFile, destFile);
			}
		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;
			
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}
	}
	
	public static String evalAll(String rootDirname) throws IOException
	{
		System.out.println("evalAll : " + rootDirname);
		File rootDir = new File(rootDirname);
		
		if(!rootDir.exists() || !rootDir.isDirectory())
		{
			throw new IOException();
		}
		
		StringBuilder ret = new StringBuilder();
		
		String[] dirs = rootDir.list();
		for(String dir : dirs)
		{
			
			ret.append("\t"+dir + "\n");
			String temp = eval(rootDirname + dir);
			ret.append(temp);
//			System.out.println(dir+"\t"+eval(temp).toString());
		}
		ret.append("\n");
		return ret.toString();
	}
	
	public static String eval(String dirname) throws IOException
	{
		System.out.println("eval : " + dirname);
		File dir = new File(dirname);
		
		if(!dir.exists() || !dir.isDirectory())
		{
			System.out.println(dir);
			throw new IOException();
		}
		
		StringBuilder ret = new StringBuilder();
		
		String[] files = dir.list();
		System.out.println("dir:"+dir);
		System.out.println("files:"+files);
		for(String file : files)
		{
			ret.append(file+"\t");
			System.out.println("File:"+file);
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&");
			double[] scores = evalFile(dirname + "/" + file);
			for(int i = 0; i < scores.length; i++)
			{
				String score = String.format("%1$.4f", scores[i]);
				if(i != 0)
				{
					ret.append("\t");
				}
				ret.append(score);
			}
			ret.append("\n" );
		}
		ret.append("\n");
		return ret.toString();
	}
	
	private static double[] evalFile(String filename) throws IOException
	{
		System.out.println("evalFile : " + filename);
		String reqName = filename.substring(filename.lastIndexOf('/') + 1);
		List<AuClass> predictList = new ArrayList<AuClass>();
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;
		while((line = reader.readLine()) != null)
		{
			String[] splits = line.split("\t");
			if(splits.length < 2)
			{
				continue;
			}
			predictList.add(new AuClass(splits[0], Double.parseDouble(splits[1])));
		}
		reader.close();
		List<String> gts = GroundTruth.getGroundTruth(reqName);
		
//		System.out.println("-----");
		System.out.println("evalFile reqName: " + reqName);
		System.out.println("GroundTruth Size: "+gts.size());
		System.out.println("-----");
		
		return evalReq(predictList, gts);
		
	}
	
	private static double[] evalReq(List<AuClass> predict, List<String> groundtruth)
	{
		double[] scores = new double[evaluations.length];
		
		for(int i = 0; i < evaluations.length; i++)
		{
			scores[i] = evaluations[i].evaluate(predict, groundtruth);
		}
		
		return scores;
	}
	
	public static List<AuClass> predictReq(AuRequirement requirement, List<AuClass> classes)
	{
		List<AuClass> list = new ArrayList<AuClass>();
		for(AuClass clazz : classes)
		{
			double score = similarity.getScore(requirement, clazz);
			if(score < 0)
			{
				continue;
			}
			clazz.setScore(score);
			list.add(clazz);
		}
		
		Collections.sort(list);
		
		return list;
	}

	public static void predictReqs(String ouputDir) throws Exception
	{
		System.out.println(ouputDir);
		File file = new File(ouputDir);
		if(!file.exists())
		{
			file.mkdirs();
		}
		
		List<AuClass> classes = new ArrayList<AuClass>();
		List<AuRequirement> reqs = new ArrayList<AuRequirement>();
		
		for(Instance inst : DataRespo.getAllInstances() )
		{
			String instName = inst.stringValue(0);
			if(instName.toLowerCase().startsWith("r"))
			{
				AuRequirement req = new AuRequirement();
				System.out.println(instName);
				req.setName(instName);
				reqs.add(req);
			}
			else
			{
				AuClass clazz = new AuClass(instName);
				classes.add(clazz);
			}
		}
		
		for(AuRequirement req : reqs)
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(ouputDir + req.getName())); 
			for(AuClass ac : predictReq(req, classes))
			{
				writer.write(ac.getName() + "\t" + ac.getScore() + "\n");
			}
			writer.close();
		}

	}
	
	public static void printEval() throws IOException
	{
		System.out.println("printEval()");
		
		String allTempStr = evalAll("./data/result/");
		System.out.println(allTempStr);
	}
	
	public static void predict(String outputDir) {
		try
		{
			TextSim sim = new TextSim();
			
			setSimilarity(sim);
			predictReqs(outputDir);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("input [tfidf] | [cluster] | [predict] | [eval]:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = reader.readLine();
		input = input.trim();
		if("cluster".equals(input))
		{
			//String inputFile = "./data/SVN_LOG_by_Versions/";
			//String outputFile = "./data/clustered/SVN_LOG_by_Versions_2/";
			//cluster.batchCluster(inputFile, outputFile);
			
			ClassCluster cluster = new NewNaiveClassCluster();
			cluster.cluster("./data/clustered/log.txt", "./data/clustered/log_class_cluster.txt");
		}
		else if("predict".equals(input))
		{
			DataRespo.init("./data/text/WordSpace(req+code)_result_cmd.arff");
			HybridSim.init("./data/clustered/log_class_cluster.txt");
			predict("./data/predict/");
			ModuleMap map = new ModuleMap("./data/module.txt");
			DeltaScore ds = new DeltaScore(map, 0.8);
			ds.moduleDir("./data/predict/", "./data/result/IR-Mod/");
		}
		else if("eval".equals(input))
		{
			DataRespo.init("./data/text/WordSpace(req+code)_result_cmd.arff");
			GroundTruth.init("./data/eval_filter/");
			
			File src = new File("./data/predict/");
			File dest = new File("./data/result/IR-only/");
			ReqCodeTracing.copyFolder(src, dest);
			
			/* TopK Recall Evaluation */
			//int recallTopK = 30;
			//setEvaluation(new RecallEval(recallTopK));

			
			/* Threshold Precision Evaluation */
			//double precisionThreshold = 0.1;
			//setEvaluation(new PrecisionEval(precisionThreshold));
			
			
			/* Threshold FMeasure Evaluation */
			//double threshold = 0.0;
			//int b = 1;
			//setEvaluation(new FMeasureEval(threshold, b));
			
			
			/* Threshold 3-Measurements Evaluation */
			double threshold = 0.2; // 0.15
			
			int topK=10;
			int b = 1;

			Evaluation[] es = new Evaluation[3];
//			es[0] = new ThresholdRecallEval(threshold);
//			es[1] = new ThresholdPrecisionEval(threshold);
			
			es[0] = new TopKRecallEval(topK);
			es[1] = new TopKPrecisionEval(topK);
			
			es[2] = new FMeasureEval(threshold, b);
			setEvaluations(es);
			
			printEval();
		}
		else if("tfidf".equals(input))
		{
//			DataRespo.tfIdf("./data/original/R1_4_Requirement_Code.arff", "./data/text/R1_4_Requirement_Code_result_cmd.arff");
			DataRespo.tfIdf("./data/original/WordSpace(req+code).arff", "./data/text/WordSpace(req+code)_result_cmd.arff");
		}
	}
}
