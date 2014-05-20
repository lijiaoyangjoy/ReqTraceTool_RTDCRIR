package org.soulsight.argouml.coauthor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.soulsight.argouml.coauthor.data.AuClass;
import org.soulsight.argouml.coauthor.modularity.ModuleMap;

public class FindRelativeClasses {

	public static int recallTopK = 20;
	public static Map<String, List<Integer>> classModuleIdMap;
	public static Map<Integer, List<String>> moduleIdClassesMap;

	public static void main(String[] args) throws IOException {
		FindRelativeClasses instance = new FindRelativeClasses();
		ModuleMap moduleMap = new ModuleMap("./data/module.txt");
		classModuleIdMap = moduleMap.getClassModuleIdMap();
		moduleIdClassesMap = moduleMap.getModuleIdClassesMap();

		instance.recommend("./data/predict");
	}

	public void recommend(String dirname) throws IOException {
		System.out.println("eval : " + dirname);
		File dir = new File(dirname);

		if (!dir.exists() || !dir.isDirectory()) {
			System.out.println(dir);
			throw new IOException();
		}

		StringBuilder ret = new StringBuilder();

		String[] files = dir.list();
		System.out.println("dir:" + dir);
		System.out.println("files:" + files);
		for (String file : files) {
			ret.append(file + "\t");
			System.out.println("File:" + file);
			Map<String, List<Integer>> predictClassModuleIdMap = checkFile(dirname
					+ "/" + file);
			Map<Integer, List<String>> predictModuleIdClassMap = getPredictModuleIdClassMap(predictClassModuleIdMap);
			Map<Integer, List<String>> multiModuleIdClassMap = multiModuleIdClassMap(predictModuleIdClassMap);
			for (Entry<Integer, List<String>> entry : multiModuleIdClassMap
					.entrySet()) {
				int moduleId = entry.getKey();
				List<String> classes = entry.getValue();
				List<String> allModuleClasses = moduleIdClassesMap
						.get(moduleId);
				// List<String> recommendationClasses=null;
				for (int i = 0; i < classes.size(); i++) {
					for (int j = 0; j < allModuleClasses.size(); j++) {
						String temp = null;
						temp = compareClassRelativity(classes.get(i),
								allModuleClasses.get(j));
//						System.out.println(temp);
						if (temp!=null) {
							System.out.println(classes.get(i) + " recommend : "
									+ temp);
							// recommendationClasses.add(temp);
						}
					}
				}

			}

		}
	}

	private Map<String, List<Integer>> checkFile(String filename)
			throws IOException {
		System.out.println("checkFile : " + filename);
		String reqName = filename.substring(filename.lastIndexOf('/') + 1);
		List<AuClass> predictList = new ArrayList<AuClass>();

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = null;
		int lineCount = 0;
		while ((line = reader.readLine()) != null && lineCount < recallTopK) {
			String[] splits = line.split("\t");
			if (splits.length < 2) {
				continue;
			}
			predictList.add(new AuClass(splits[0], Double
					.parseDouble(splits[1])));
			lineCount++;
		}
		reader.close();
		return getPredictClassModuleIdMap(predictList, classModuleIdMap);

	}

	public String compareClassRelativity(String className1, String className2) {
		if (className1.equals(className2)) {
			return null;
		} else {
			String prefix1 = className1.substring(0,
					className1.lastIndexOf("."));
			String prefix2 = className2.substring(0,
					className2.lastIndexOf("."));
			String name1 = className1
					.substring(className1.lastIndexOf(".") + 1);
			String name2 = className2
					.substring(className2.lastIndexOf(".") + 1);
			int count = 0;
			if (prefix1.equals(prefix2)) {
				count++;
//				return className2;
			} else {
				List<String> nameWords1 = splitStringByUpper(name1);
				List<String> nameWords2 = splitStringByUpper(name2);

				for (int i = 0; i < nameWords1.size(); i++) {
					if (nameWords2.contains(nameWords1.get(i)))
						count++;
					else
						continue;
				}

				
			}
			if (count > 2) {
				return className2;
			} else
				return null;
		}

	}

	public List<String> splitStringByUpper(String methName) {
//		List<String> methDisplayName = null;
		char[] methChars = methName.toCharArray();
		int head = 0;
		List<String> methWordList = new LinkedList<String>();
		for (int j = 0; j < methChars.length; j++) {
			boolean isUpper = Character.isUpperCase(methChars[j]);
			if (isUpper && j != 0) {
				String word = methName.substring(head, j);
				head = j;

				methWordList.add(word);
//				methDisplayName.add(word.toLowerCase());
			}

			if (j == methChars.length - 1) {
				String word = methName.substring(head, j + 1);
				methWordList.add(word);
//				methDisplayName.add(word.toLowerCase());
			}
		}

		return methWordList;
	}

	public Map<Integer, List<String>> multiModuleIdClassMap(
			Map<Integer, List<String>> predictModuleIdClassMap) {
		Map<Integer, List<String>> multiModuleIdClassMap = new HashMap<Integer, List<String>>();
		for (Entry<Integer, List<String>> entry : predictModuleIdClassMap
				.entrySet()) {
			List<String> temp = entry.getValue();
			if (temp.size() > 0) {
				multiModuleIdClassMap.put(entry.getKey(), entry.getValue());
			}
		}
		return multiModuleIdClassMap;
	}

	public Map<Integer, List<String>> getPredictModuleIdClassMap(
			Map<String, List<Integer>> predictClassModuleIdMap) {

		Map<Integer, List<String>> predictModuleIdClassMap = new HashMap<Integer, List<String>>();
		for (Entry<String, List<Integer>> entry : predictClassModuleIdMap
				.entrySet()) {

			String className = entry.getKey();
			List<Integer> moduleId = entry.getValue();
			for (int i = 0; i < moduleId.size(); i++) {
				int key = moduleId.get(i);

				if (predictModuleIdClassMap.get(key) != null) {

					if (predictModuleIdClassMap.get(key).contains(className)) {
						continue;
					} else {
						predictModuleIdClassMap.get(key).add(className);
					}
				} else {
					List<String> temp = new ArrayList<String>();
					temp.add(className);
					predictModuleIdClassMap.put(key, temp);
				}
			}

		}
		return predictModuleIdClassMap;

	}

	public Map<String, List<Integer>> getPredictClassModuleIdMap(
			List<AuClass> predict, Map<String, List<Integer>> classModuleIdMap) {

		Map<String, List<Integer>> predictClassModuleIdMap = new HashMap<String, List<Integer>>();

		for (int i = 0; i < predict.size(); i++) {

			String className = predict.get(i).getName();
			className = className.substring(className.lastIndexOf(".") + 1);

			for (Entry<String, List<Integer>> entry : classModuleIdMap
					.entrySet()) {
				String className2 = entry.getKey();
				className2 = className2.substring(
						className2.lastIndexOf(".") + 1, className2.length());

				if (className.equals(className2)) {
					predictClassModuleIdMap.put(entry.getKey(),
							entry.getValue());
				}

			}

		}

		return predictClassModuleIdMap;
	}

}
