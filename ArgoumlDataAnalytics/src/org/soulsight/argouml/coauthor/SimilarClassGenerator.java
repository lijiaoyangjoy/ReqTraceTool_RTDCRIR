package org.soulsight.argouml.coauthor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SimilarClassGenerator {
	private static double BONUS_PARAM = 0.8;

	public static void main(String[] args) {
		SimilarClassGenerator instance = new SimilarClassGenerator();
		MinSet minset = new MinSet();
		List<String[]> authorClassSet = minset
				.getAuthorClassSet("./data/author-class.txt");

		List<String> classList = minset.getClassList(authorClassSet);
		
//		List<String> authorList = minset.getAuthorList(authorClassSet);
//
//		HashMap<String, List<String>> clazzAuthors = minset.getClassAuthors(
//				authorClassSet, classList);
//		HashMap<String, List<String>> authorClasses = minset.getAuthorClasses(
//				authorClassSet, authorList);
//
//		HashMap<String, List<String>> classSimilarClasses = instance
//				.getSimilarClasses(clazzAuthors, authorClasses);
//		minset.writeMapToFile(classSimilarClasses,
//				"./data/classSimilarClasses.txt");
		
		List<String[]> classLogtime = minset.getClassLogtime("./data/class_logtime.txt");
		
		HashMap<String,HashMap<String, List<String>>> simultaneousClasses = instance.getSimultaneousClasses(classLogtime,classList);
//		minset.writeMapToFile(simultaneousClasses,
//				"./data/simultaneousClasses.txt");
	}

	public HashMap<String,HashMap<String,List<String>>> getSimultaneousClasses(List<String[]> classLogtime,List<String> classList){
		HashMap<String,HashMap<String,List<String>>> hm = new HashMap<String,HashMap<String,List<String>>>();
		
		HashMap<String,List<String>> logtimeClasses = new HashMap<String,List<String>>();
		for(int i=0;i<classList.size();i++){
			String className= classList.get(i);
			System.out.println(className+"**********");
//			List<List<String>> logtimeList = new ArrayList<List<String>>();
			for(int j=0;j<classLogtime.size();j++){
				if(classLogtime.get(j)[1].equals(className)){
					String logTime = classLogtime.get(j)[0];
					List<String> simultaneousClassList = new ArrayList<String>();
					System.out.println(logTime);
					for(int k=0;k<classLogtime.size();k++){
						if((!className.equals((classLogtime.get(k)[1])))
								&&(logTime.equals(classLogtime.get(k)[0]))){
							simultaneousClassList.add(classLogtime.get(k)[1]);
							System.out.println(classLogtime.get(k)[1]);
						}
					}
					logtimeClasses.put(logTime, simultaneousClassList);
				}
			}
//			logtimeList.add(logtimeClasses);
			hm.put(className, logtimeClasses);
		}
		
		return hm;
	}

	public HashMap<String, List<String>> getSimilarClasses(
			HashMap<String, List<String>> clazzAuthors,
			HashMap<String, List<String>> authorClasses) {

		HashMap<String, List<String>> hm = new HashMap<String, List<String>>();
		int authorSize;

		for (Entry<String, List<String>> entry : clazzAuthors.entrySet()) {

			String className = entry.getKey();
			List<String> authorNames = entry.getValue();
			authorSize = authorNames.size();

			HashMap<String, Integer> classScore = new HashMap<String, Integer>();

			for (int i = 0; i < authorNames.size(); i++) {
				String author1 = authorNames.get(i);
				for (Entry<String, List<String>> entry2 : authorClasses
						.entrySet()) {
					String author2 = entry2.getKey();
					List<String> author2Classes = entry2.getValue();
					if (author2.equals(author1)) {
						for (int j = 0; j < author2Classes.size(); j++) {
							String tempClass = author2Classes.get(j);
							if ((!tempClass.equals(className))
									&& classScore.containsKey(tempClass)) {
								classScore.put(tempClass,
										classScore.get(tempClass) + 1);
							} else
								classScore.put(tempClass, 1);
						}
					}
				}
			}

			List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>();
			list.addAll(classScore.entrySet());
			ValueComparator vc = new ValueComparator();
			Collections.sort(list, vc);
			List<String> classes = new ArrayList<String>();
			int max = list.get(0).getValue();
			// System.out.println(max);
			if ((max > 2) && (max > authorSize * BONUS_PARAM)) {

				for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it
						.hasNext();) {
					Map.Entry<String, Integer> haha = it.next();
					// System.out.println(haha.getKey());
					if (classScore.get(haha.getKey()).intValue() == max) {
						// System.out.println(haha.getKey());
						classes.add(haha.getKey());
					}
					// System.out.println(haha);
				}
			}

			hm.put(className, classes);

		}

		return hm;
	}

	private static class ValueComparator implements
			Comparator<Map.Entry<String, Integer>> {
		public int compare(Map.Entry<String, Integer> m,
				Map.Entry<String, Integer> n) {
			return n.getValue() - m.getValue();
		}
	}

	public static <K, V extends Number> Map<String, V> sortMap(
			Map<String, V> map) {
		class MyMap<M, N> {
			private M key;
			private N value;

			private M getKey() {
				return key;
			}

			private void setKey(M key) {
				this.key = key;
			}

			private N getValue() {
				return value;
			}

			private void setValue(N value) {
				this.value = value;
			}
		}

		List<MyMap<String, V>> list = new ArrayList<MyMap<String, V>>();
		for (Iterator<String> i = map.keySet().iterator(); i.hasNext();) {
			MyMap<String, V> my = new MyMap<String, V>();
			String key = i.next();
			my.setKey(key);
			my.setValue(map.get(key));
			list.add(my);
		}

		Collections.sort(list, new Comparator<MyMap<String, V>>() {
			public int compare(MyMap<String, V> o1, MyMap<String, V> o2) {
				if (o1.getValue() == o2.getValue()) {
					return o1.getKey().compareTo(o2.getKey());
				} else {
					return (int) (o1.getValue().doubleValue() - o2.getValue()
							.doubleValue());
				}
			}
		});

		Map<String, V> sortMap = new LinkedHashMap<String, V>();
		for (int i = 0, k = list.size(); i < k; i++) {
			MyMap<String, V> my = list.get(i);
			sortMap.put(my.getKey(), my.getValue());
		}
		return sortMap;
	}

}
