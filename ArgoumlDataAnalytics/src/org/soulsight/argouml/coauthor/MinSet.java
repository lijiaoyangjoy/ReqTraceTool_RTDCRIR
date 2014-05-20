package org.soulsight.argouml.coauthor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class MinSet {

	public static void main(String[] args) {

		MinSet minset = new MinSet();
		List<String[]> authorClassSet = minset
				.getAuthorClassSet("./data/author-class.txt");

		List<String> classList = minset.getClassList(authorClassSet);
		List<String> authorList = minset.getAuthorList(authorClassSet);
//		HashMap<String, List<String>> clazzAuthors = minset.getClassAuthors(
//				authorClassSet, classList);
//
//		minset.writeMapToFile(clazzAuthors, "./data/classAuthors.txt");
//		HashMap<String, Integer> sets = minset.countAuthorsMinSet(clazzAuthors,
//				authorList, 2);
		
		HashMap<String, List<String>> authorClasses = minset.getAuthorClasses(
				authorClassSet, authorList);
				
		HashMap<String, Integer> classesMinSet = minset.countClassesMinSet(authorClasses,
				classList, 2);

	}
	
	public HashMap<String, Integer> countClassesMinSet(
			HashMap<String, List<String>> authorClasses,
			List<String> classList, int k) {

		InputStream is;
		HashMap<String, Integer> min = new HashMap<String, Integer>();

		try {
			BufferedWriter writer;
			writer = new BufferedWriter(new FileWriter("./data/class-edges.txt"));
			for (int i = 0; i < classList.size() - 1; i++) {
				String temp1 = classList.get(i);

				for (int j = i + 1; j < classList.size() - 1; j++) {

					String temp2 = classList.get(j);
					String temp3;

					int num = 0;
					for (Entry<String, List<String>> entry : authorClasses
							.entrySet()) {
						
						if (entry.getValue().contains(temp1)
								&& entry.getValue().contains(temp2)){
							writer.write(temp1 + "," + temp2 + "\n");
							num++;}
					}
					temp3 = temp1 + "," + temp2;
					min.put(temp3, num);
					System.out.println(temp3 + "\t" + num);
				}

			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return min;
	}

	public HashMap<String, Integer> countAuthorsMinSet(
			HashMap<String, List<String>> clazzAuthors,
			List<String> authorList, int k) {

		InputStream is;
		HashMap<String, Integer> min = new HashMap<String, Integer>();

		try {
			BufferedWriter writer;
			writer = new BufferedWriter(new FileWriter("./data/edges.txt"));
			for (int i = 0; i < authorList.size() - 1; i++) {
				String temp1 = authorList.get(i);

				for (int j = i + 1; j < authorList.size() - 1; j++) {

					String temp2 = authorList.get(j);
					String temp3;

					int num = 0;
					for (Entry<String, List<String>> entry : clazzAuthors
							.entrySet()) {
						
						if (entry.getValue().contains(temp1)
								&& entry.getValue().contains(temp2)){
							writer.write(temp1 + "," + temp2 + "\n");
							num++;}
					}
					temp3 = temp1 + "," + temp2;
					min.put(temp3, num);
					System.out.println(temp3 + "\t" + num);
				}

			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return min;
	}

	public void writeMapToFile(HashMap<String, List<String>> hm,
			String outputFilename) {

		String temp = "";
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(outputFilename));
			for (Entry<String, List<String>> entry : hm.entrySet()) {

//				 temp = entry.getKey() + ":";
				temp = "###"+entry.getKey() + ":\n";
				for (int i = 0; i < entry.getValue().size(); i++) {
//					 temp += "\t" + entry.getValue().get(i);
					temp += entry.getValue().get(i) + "\n";

				}
//				temp.substring(0, temp.lastIndexOf("\t"));
				temp += "\n";
				writer.write(temp);
				// writer.flush();
			}
			
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public HashMap<String, List<String>> getAuthorClasses(
			List<String[]> authorClassSet, List<String> authorList) {

		HashMap<String, List<String>> authorClasses = new HashMap<String, List<String>>();

		for (int i = 0; i < authorList.size(); i++) {
			String author = authorList.get(i);
			List<String> classes = new ArrayList<String>();

			for (int j = 0; j < authorClassSet.size(); j++) {
				if (author.equals(authorClassSet.get(j)[0])) {
					classes.add(authorClassSet.get(j)[1]);
				}
			}
			authorClasses.put(author, classes);

		}

		return authorClasses;

	}

	public HashMap<String, List<String>> getClassAuthors(
			List<String[]> authorClassSet, List<String> classList) {

		HashMap<String, List<String>> clazzAuthors = new HashMap<String, List<String>>();
		int n = 0;
		for (int i = 0; i < classList.size(); i++) {
			String clazz = classList.get(i).toString();
			List<String> authors = new ArrayList<String>();

			for (int j = 0; j < authorClassSet.size(); j++) {

				if (clazz.equals(authorClassSet.get(j)[1].toString())) {
					authors.add(authorClassSet.get(j)[0]);
				}
			}
			clazzAuthors.put(clazz, authors);
			n++;
		}
		System.out.println(n);
		return clazzAuthors;

	}

	public List<String> getAuthorList(List<String[]> authorClassSet) {

		List<String> authorList = new ArrayList<String>();

		for (int i = 0; i < authorClassSet.size(); i++) {
			if (!authorList.contains(authorClassSet.get(i)[0])) {
				authorList.add(authorClassSet.get(i)[0]);
			}
		}

		return authorList;
	}

	public List<String> getClassList(List<String[]> authorClassSet) {

		List<String> classList = new ArrayList<String>();

		for (int i = 0; i < authorClassSet.size(); i++) {
			if (!classList.contains(authorClassSet.get(i)[1])) {
				// System.out.println(authorClassSet.get(i)[1]);
				classList.add(authorClassSet.get(i)[1]);
			}
		}

		return classList;
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
				// System.out.println(line);
				String[] s = line.split("\t");
				// System.out.println(s[0]);
				// System.out.println(s[1]);
				authorClassSet.add(s); //
				line = reader.readLine(); // 读取下一行
			}
			reader.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return authorClassSet;
	}
	
	public List<String[]> getClassLogtime(String filePath) {
		InputStream is;
		ArrayList<String[]> classLogtime = new ArrayList<String[]>();
		try {
			is = new FileInputStream(filePath);

			String line; // 用来保存每行读取的内容

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			line = reader.readLine(); // 读取第一行
			while (line != null) { // 如果 line 为空说明读完了
				// System.out.println(line);
				String[] s = line.split("\t");
				// System.out.println(s[0]);
				// System.out.println(s[1]);
				classLogtime.add(s); // 0,log_time; 1,class_name
				line = reader.readLine(); // 读取下一行
			}
			reader.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return classLogtime;
	}

}
