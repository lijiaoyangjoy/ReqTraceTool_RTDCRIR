package DynamicTraceability.Toolkit.JavaToXML;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class CodePreProcessor {

	public static void main(String[] args) {

		CodePreProcessor cp = new CodePreProcessor();

		HashMap<String, String> newCodeWords = new HashMap<String, String>();
		
//		List<String[]> stringReplacementList = cp
//				.getStringReplacementList("./data/string-replacement.txt");
//		newCodeWords = cp.codePreprocess("./data/argouml-codeWords.txt",
//				stringReplacementList);
//
//		cp.writeCodeBag("./data/argouml-codeWords-preprocessed.txt", newCodeWords);
//		
		
		List<String[]> stringReplacementList = cp
				.getStringReplacementList("./data/eliminateWords.txt");
		newCodeWords = cp.codePreprocess("./data/long-query-code-to-eliminate.txt",
				stringReplacementList);

		cp.writeCodeBag("./data/long-query-code-eliminated.txt", newCodeWords);
		
		System.out.println("Write Over!");

	}

	public HashMap<String, String> codePreprocess(String sourcefile,
			List<String[]> stringReplacementList) {
		HashMap<String, String> hm = new HashMap<String, String>();

		Pattern pattern = Pattern.compile(".*\\$[0-9]*$");

		// Matcher matcher;
		InputStream is;
		try {
			is = new FileInputStream(sourcefile);
			String line; // 鐢ㄦ潵淇濆瓨姣忚璇诲彇鐨勫唴瀹�
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			line = reader.readLine(); // 璇诲彇绗竴琛�
			while (line != null) { // 濡傛灉 line 涓虹┖璇存槑璇诲畬浜�
				String[] strs = line.split("\t");
				String str0 = strs[0];
				String str1 = " " + strs[1] + " ";
				if (!pattern.matcher(str0).matches()) {
					
					if(str0.contains("$")){
						
						String fatherClassName = str0.substring(0, str0.indexOf("$"));
						str1 = stringReplace(str1, stringReplacementList);
						str1 = replaceNumerics(str1);
						if(hm.containsKey(fatherClassName))
							hm.put(fatherClassName, hm.get(fatherClassName)+str1);
						else 
							hm.put(fatherClassName, str1);
					}
					else{
					str1 = stringReplace(str1, stringReplacementList);
					str1 = replaceNumerics(str1);
					hm.put(str0, str1); //
					}
				}
				line = reader.readLine(); // 璇诲彇涓嬩竴琛�
			}
			reader.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return hm;
	}
	
	public String replaceNumerics(String source){
		for(int i=0;i<source.length();i++){
			 Character chr = source.charAt(i);
			if(Character.isDigit(chr)){
				source = source.replace(chr, ' ');
			}
		}
		return source;
	}

	public String stringReplace(String source,
			List<String[]> stringReplacementList) {

		for (int i = 0; i < stringReplacementList.size(); i++) {
			String str1 = " " + stringReplacementList.get(i)[0] + " ";
			String str2 = " " + stringReplacementList.get(i)[1] + " ";
			while (source.contains(str1)) {
//				System.out.println("杩樻湁"+str1+"!");
				source = source.replace(str1, str2);
			}
		}
//		System.out.println(source);
		return source;
	}

	public List<String[]> getStringReplacementList(String filename) {
		List<String[]> stringReplacementList = new ArrayList<String[]>();
		InputStream is;
		try {
			is = new FileInputStream(filename);
			String line; // 鐢ㄦ潵淇濆瓨姣忚璇诲彇鐨勫唴瀹�
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			line = reader.readLine(); // 璇诲彇绗竴琛�
			while (line != null) { // 濡傛灉 line 涓虹┖璇存槑璇诲畬浜�
				String[] strs = line.split("\t");
//				System.out.println(strs[0]+"\t"+strs[1]);
				stringReplacementList.add(strs);
				line = reader.readLine(); // 璇诲彇涓嬩竴琛�
			}
			reader.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return stringReplacementList;
	}

	public void writeCodeBag(String outputFilename,
			Map<String, String> newCodeWords) {

		try {
			File f = new File(outputFilename);
			if (f.exists()) {
				System.out.println("鏂囦欢ok");
			} else {
				if (!f.createNewFile()) {
					System.out.println("鏂囦欢鍒涘缓澶辫触锛�");
				}
			}
			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			Iterator<Entry<String, String>> iter = newCodeWords
					.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				String content;
				if(!val.equals(null)){
					content = key.toString() + "\t" + val.toString() + "\n";
					
				}else{
					content = key.toString() + "\t" + "\n";
				}
				
				output.write(content);
			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
}
