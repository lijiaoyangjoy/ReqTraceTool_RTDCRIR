package org.soulsight.argouml.coauthor.cluster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.soulsight.argouml.coauthor.MinSet;

public class AuthorCommitClassCluster implements ClassCluster {

	public static void main(String[] args) {

		AuthorCommitClassCluster minset = new AuthorCommitClassCluster();
		List<String[]> logtimeClassSet = minset
				.getAuthorClassSet("./data/logtime-class.txt");
		String edgeOutputFilename = "./data/Class-pair-weighted-edges.txt";

		List<String> classList = minset.getClassList(logtimeClassSet);
		List<String> logtimeList = minset.getLogtimeList(logtimeClassSet);
		HashMap<String, List<String>> logtimeClasses = minset
				.getLogtimeClasses(logtimeClassSet, logtimeList);

		System.out.println(classList.size());
		System.out.println(logtimeClasses.size());
		
//		HashMap<String, Integer> classesMinSet = minset.countClassesMinSet(
//				logtimeClasses, classList);
		
		Map<String,Integer> classCountMap = minset.getClassCount(logtimeClassSet);
		HashMap<String, Double> classesEdges= minset.countClassesEdges(logtimeClasses,classList,(HashMap<String, Integer>) classCountMap, edgeOutputFilename);
		System.out.println("Function classesMinSet Done!");

	}
	
	public static void preprocessLogFile(String logFilename, String edgePairFilename) {

		AuthorCommitClassCluster minset = new AuthorCommitClassCluster();
		List<String[]> logtimeClassSet = minset
				.getAuthorClassSet(logFilename);

		List<String> classList = minset.getClassList(logtimeClassSet);
		List<String> logtimeList = minset.getLogtimeList(logtimeClassSet);
		HashMap<String, List<String>> logtimeClasses = minset
				.getLogtimeClasses(logtimeClassSet, logtimeList);

		System.out.println(classList.size());
		System.out.println(logtimeClasses.size());
		
//		HashMap<String, Integer> classesMinSet = minset.countClassesMinSet(
//				logtimeClasses, classList);
		
		Map<String,Integer> classCountMap = minset.getClassCount(logtimeClassSet);
		minset.countClassesEdges(logtimeClasses,classList,(HashMap<String, Integer>) classCountMap, edgePairFilename);
	}

	@Override
	public void batchCluster(String srcDir, String desDir) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cluster(String inputFilename, String outputFilename)
			throws IOException {
		// TODO Auto-generated method stub

	}

	public List<String[]> getAuthorClassSet(String filePath) {
		InputStream is;
		ArrayList<String[]> authorClassSet = new ArrayList<String[]>();
		try {
			is = new FileInputStream(filePath);

			String line; // 用来保存每行读取的内容

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			line = reader.readLine(); // 读取第一行
			while (line != null) { // 如果 line 为空说明读完了
				String[] s = line.split("\t");
				authorClassSet.add(s); //
				line = reader.readLine(); // 读取下一行
			}
			reader.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return authorClassSet;
	}

	public List<String> getClassList(List<String[]> logtimeClassSet) {

		List<String> classList = new ArrayList<String>();

		for (int i = 0; i < logtimeClassSet.size(); i++) {
			if (!classList.contains(logtimeClassSet.get(i)[1])) {
				// System.out.println(authorClassSet.get(i)[1]);
				classList.add(logtimeClassSet.get(i)[1]);
			}
		}

		return classList;
	}
	
	public Map<String,Integer> getClassCount(List<String[]> logtimeClassSet) {

		Map<String,Integer> classCountMap = new HashMap<String,Integer>();

		for (int i = 0; i < logtimeClassSet.size(); i++) {
			if (!classCountMap.containsKey(logtimeClassSet.get(i)[1])) 
				classCountMap.put(logtimeClassSet.get(i)[1],1);
			else
				classCountMap.put(logtimeClassSet.get(i)[1],classCountMap.get(logtimeClassSet.get(i)[1])+1);
		}

		return classCountMap;
	}


	public List<String> getLogtimeList(List<String[]> logtimeClassSet) {

		List<String> logtimeList = new ArrayList<String>();

		for (int i = 0; i < logtimeClassSet.size(); i++) {
			if (!logtimeList.contains(logtimeClassSet.get(i)[0])) {
				logtimeList.add(logtimeClassSet.get(i)[0]);
			}
		}

		return logtimeList;
	}

	public HashMap<String, List<String>> getLogtimeClasses(
			List<String[]> logtimeClassSet, List<String> logtimeList) {

		HashMap<String, List<String>> logtimeClasses = new HashMap<String, List<String>>();

		for (int i = 0; i < logtimeList.size(); i++) {
			String logtime = logtimeList.get(i);
			List<String> classes = new ArrayList<String>();

			for (int j = 0; j < logtimeClassSet.size(); j++) {
				if (logtime.equals(logtimeClassSet.get(j)[0])) {
					classes.add(logtimeClassSet.get(j)[1]);
				}
			}
			logtimeClasses.put(logtime, classes);

		}

		return logtimeClasses;

	}

	public HashMap<String, Integer> countClassesMinSet(
			HashMap<String, List<String>> logtimeClasses,
			List<String> classList) {


		HashMap<String, Integer> min = new HashMap<String, Integer>();

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"./data/class-edges.txt"));

			for (Entry<String, List<String>> entry : logtimeClasses.entrySet()) {

				List<String> classes = entry.getValue();
				for (int i = 0; i < classes.size(); i++) {
					String class1 = classes.get(i);
					for (int j = i + 1; j < classes.size(); j++) {
						String class2 = classes.get(j);
						String classPair;
						if (class1.compareTo(class2) < 0) {
							classPair = class1 + "\t" + class2;
						} else {
							classPair = class2 + "\t" + class1;
						}
						writer.write(classPair+"\n");
						if (!min.containsKey(classPair)) {
							min.put(classPair, 1);
						} else {
							int weight = min.get(classPair).intValue() + 1;
							min.put(classPair, weight);
							// System.out.println(classPair+"\t" + num);
						}
					}
				}
			}
			writer.close();

			BufferedWriter writer2 = new BufferedWriter(new FileWriter(
					"./data/class-edges-weight.txt"));
			for (Entry<String, Integer> entry : min.entrySet()) {
				writer2.write(entry.getKey() + "\t" + entry.getValue()+"\n");
			}

			writer2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return min;
	}
	
	
	public HashMap<String, Double> countClassesEdges(
			HashMap<String, List<String>> logtimeClasses,
			List<String> classList,HashMap<String,Integer> classCountMap, String edgeFilename) {

		HashMap<String, Double> min = new HashMap<String, Double>();

		try {
//			BufferedWriter writer = new BufferedWriter(new FileWriter(
//					"./data/class-edges.txt"));

			for (Entry<String, List<String>> entry : logtimeClasses.entrySet()) {

				List<String> classes = entry.getValue();
				for (int i = 0; i < classes.size(); i++) {
					String class1 = classes.get(i);
					for (int j = i + 1; j < classes.size(); j++) {
						String class2 = classes.get(j);
						String classPair;
//						if (class1.compareTo(class2) < 0) {
//							classPair = class1 + "\t" + class2;
//						} else {
//							classPair = class2 + "\t" + class1;
//						}
//						writer.write(classPair+"\n");
						
						classPair = class1 + "\t" + class2;
						
						double count1= (double)classCountMap.get(class1);
						double count2 = (double) classCountMap.get(class2);
						
						System.out.println(count1);
						System.out.println(count2);
						if (!min.containsKey(classPair)) {
							min.put(classPair, (double)(1/(count1)));
						} else {
							double foreWeight = (double) ((min.get(classPair))*(count1));
							double weight = (double)(foreWeight+ 1)/(count1);
							System.out.println(weight);
							min.put(classPair, (double) (weight));
							
						}
					}
				}
			}
//			writer.close();

			//BufferedWriter writer2 = new BufferedWriter(new FileWriter(
			//		"./data/Class-pair-weighted-edges.txt"));
			BufferedWriter writer2 = new BufferedWriter(new FileWriter(edgeFilename));
			for (Entry<String, Double> entry : min.entrySet()) {
				writer2.write(entry.getKey() + "\t" + entry.getValue()+"\n");
			}

			writer2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return min;
	}
}
