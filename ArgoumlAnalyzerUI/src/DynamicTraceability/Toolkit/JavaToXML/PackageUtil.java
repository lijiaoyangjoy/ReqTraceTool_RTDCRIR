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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.dom4j.Document;
import org.dom4j.Element;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


import DynamicTraceability.Toolkit.Entity.JavaClass;
import DynamicTraceability.Toolkit.Entity.JavaCodeElement;
import DynamicTraceability.Toolkit.Entity.JavaPackage;
import DynamicTraceability.Toolkit.Tool.SimpleLoader;
import DynamicTraceability.Toolkit.Tool.XmlOperator;

public class PackageUtil {
	Integer id = 0;
	String jarName = "";
	String packageName = "";
	// String saveXmlName = "";
	String jarFilePath = "";
	public String targetJarPath = "";// jarFilePath + jarName;
	String saveXmlPath = "";
	public static String saveCodeWordsPath = "";
	public static Map<String, String> codeWordsMap = new HashMap<String, String>();
	public static List<String> appClassesList;

	public PackageUtil() {
		jarName = "argouml.jar";
		packageName = "org";
		// saveXmlName = "./data/argouml.xml";
		saveXmlPath = "./data/argouml.xml";
		jarFilePath = "./data/";
		targetJarPath = jarFilePath + jarName;
		saveCodeWordsPath = "./data/argouml-codeWords-test-lalala.txt";

	}

	public static List<String> getClassInJar(String classPath)
			throws IOException {
		List<String> ret = new ArrayList<String>();
		JarInputStream jis = new JarInputStream(new FileInputStream(classPath),
				false);
		JarEntry e = null;
		while ((e = jis.getNextJarEntry()) != null) {
			String entry = e.getName();
			if (entry.endsWith(".class")) {
				ret.add(entry.replace('/', '.')
						.substring(0, entry.length() - 6));
			}
			jis.closeEntry();
		}
		jis.close();

		return ret;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		PackageUtil packageUtil = new PackageUtil();
		// appClassesList = packageUtil
		// .getAppClassesList("./data/argouml-classes.txt");

		appClassesList = getClassInJar(packageUtil.targetJarPath);

		// packageUtil.rewriteSVNLogFile("./data/svn_log.txt","./data/svn_log_revised.txt");

		// TODO 鏆傛椂鍏堜笉杩愯杩欎竴閮ㄥ垎浠ｇ爜
		JavaPackage rootPack = packageUtil.classDetailToMap();
		System.out.println("-------------");
		System.out.println(rootPack.subElementMap);
		packageUtil.mapToXML(rootPack);

		// 鍙敤绫诲悕
		// Map<String,String> mp =
		// packageUtil.getClassWords(packageUtil.targetJarPath);
		// packageUtil.writeCodeBag(saveCodeWordsPath, mp);
		// 鍙敤绫诲悕鈥斺�鈥斺�鈥斺�

		packageUtil.writeCodeBag(saveCodeWordsPath, codeWordsMap);

	}

	public void setClass(JavaClass classNode, Element parentElement) {
		Element classElement = parentElement
				.addElement(JavaToXMLConstant.classNodeName);
		classElement.addAttribute(JavaToXMLConstant.id, id.toString());
		id = id.intValue() + 1;
		classElement.addAttribute(JavaToXMLConstant.classNodeAttrName,
				classNode.elementName);
		classElement.addAttribute(JavaToXMLConstant.classNodeAttrDlyName,
				classNode.displayName);

		String codeWordsValue = "";
		String codeWordsKey = "";

		codeWordsKey = classNode.elementName;
		codeWordsValue = classNode.displayName + " ";
		// 鍔犲己绫诲悕鐨勯噸瑕佹�
		codeWordsValue += classNode.displayName + " ";

		setMethod(classNode.thisClass, classElement);
		setFiled(classNode.thisClass, classElement);
		try {
			Method[] methods = classNode.thisClass.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				String methodName = methods[i].getName();
				String methDisplayName = stringTrim(methodName);
				codeWordsValue += methDisplayName + " ";
			}

		} catch (Exception ex) {

		} catch (Error error) {

		}

		try {
			Field[] fields = classNode.thisClass.getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].getName();
				String fieldDisplayName = stringTrim(fieldName);
				codeWordsValue += fieldDisplayName + " ";
			}

		} catch (Exception ex) {

		} catch (Error error) {

		}

		Iterator<JavaCodeElement> itClass = classNode.subElementMap.values()
				.iterator();
		while (itClass.hasNext()) {
			JavaClass nestClass = (JavaClass) itClass.next();
			setClass(nestClass, classElement);
		}

		codeWordsMap.put(codeWordsKey, codeWordsValue);
	}

	public String stringTrim(String initStr) {
		String tmp = "";
		boolean hasLine = initStr.contains("_");
		if (initStr.equals(initStr.toUpperCase()) && !hasLine) {
			tmp = initStr.toLowerCase();
		} else {
			if (hasLine) {
				// char[] methChars = methName.toCharArray();
				tmp = splitStringByLine(initStr);
			} else {
				// char[] methChars = methName.toCharArray();
				tmp = splitStringByUpper(initStr);
			}
		}
		if (tmp.contains("$")) {
			tmp = splitStringByDollar(tmp);
		}
		return tmp;
	}

	public void setMethod(Class classNode, Element classElement) {
		try {
			Method[] meths = classNode.getDeclaredMethods();
			for (int i = 0; i < meths.length; i++) {
				String methName = meths[i].getName();
				String methDisplayName = "";

				boolean hasLine = methName.contains("_");
				if (methName.equals(methName.toUpperCase()) && !hasLine) {
					methDisplayName = methName.toLowerCase();
				} else {
					if (hasLine) {
						// char[] methChars = methName.toCharArray();
						methDisplayName = splitStringByLine(methName);
					} else {
						// char[] methChars = methName.toCharArray();
						methDisplayName = splitStringByUpper(methName);
					}
				}

				Element methodElement = classElement
						.addElement(JavaToXMLConstant.mehtodNodeName);
				methodElement.addAttribute(JavaToXMLConstant.id, id.toString());
				id = id.intValue() + 1;

				methodElement.addAttribute(
						JavaToXMLConstant.mehtodNodeAttrName, methName);
				methodElement.addAttribute(
						JavaToXMLConstant.mehtodNodeAttrDlyName,
						methDisplayName);
			}
		} catch (Exception ex) {

		} catch (Error error) {

		}
	}

	public void setFiled(Class classNode, Element classElement) {
		try {
			Field[] fields = classNode.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].getName();
				String fieldDisplayName = "";

				boolean hasLine = fieldName.contains("_");
				if (fieldName.equals(fieldName.toUpperCase()) && !hasLine) {
					fieldDisplayName = fieldName.toLowerCase();
				} else {
					if (hasLine) {
						fieldDisplayName = splitStringByLine(fieldName);
					} else {
						fieldDisplayName = splitStringByUpper(fieldName);
					}
				}

				Element fieldElement = classElement
						.addElement(JavaToXMLConstant.fieldNodeName);
				fieldElement.addAttribute(JavaToXMLConstant.id, id.toString());
				id = id.intValue() + 1;

				fieldElement.addAttribute(JavaToXMLConstant.fieldNodeAttrName,
						fieldName);
				fieldElement.addAttribute(
						JavaToXMLConstant.fieldNodeAttrDlyName,
						fieldDisplayName);
			}
		} catch (Exception ex) {

		} catch (Error error) {

		}
	}

	public void saveToXml(Map<String, JavaCodeElement> packLevelMap,
			Element packageElement) {
		Iterator<JavaCodeElement> nodeIt = packLevelMap.values().iterator();
		List<JavaCodeElement> javaClassList = new LinkedList<JavaCodeElement>();
		while (nodeIt.hasNext()) {
			JavaCodeElement element = nodeIt.next();
			if (element instanceof JavaPackage) {
				Element packElement = packageElement
						.addElement(JavaToXMLConstant.packNodeName);
				packElement.addAttribute(JavaToXMLConstant.id, id.toString());
				id = id.intValue() + 1;
				packElement.addAttribute(JavaToXMLConstant.packNodeAttrNs,
						element.elementName);
				saveToXml(element.subElementMap, packElement);
			} else if (element instanceof JavaClass) {
				javaClassList.add(element);
			}
		}

		Iterator<JavaCodeElement> classIt = javaClassList.iterator();
		while (classIt.hasNext()) {
			JavaClass classNode = (JavaClass) classIt.next();
			setClass(classNode, packageElement);

		}
	}

	public void mapToXML(JavaPackage rootPack) throws IOException,
			ClassNotFoundException, IllegalAccessError, NullPointerException {
		XmlOperator jarXml = new XmlOperator();
		Document doc = jarXml.createDoc();

		// 锟斤拷痈锟斤拷锟�
		Element rootElement = doc.addElement(JavaToXMLConstant.packNodeName);
		rootElement.addAttribute(JavaToXMLConstant.id, id.toString());
		id = id.intValue() + 1;
		rootElement.addAttribute(JavaToXMLConstant.packNodeAttrNs, packageName);

		// Map packLevelMap = (Map)codeMap.get(packageName);

		saveToXml(rootPack.subElementMap, rootElement);

		rootElement.addAttribute(JavaToXMLConstant.packNodeAttrClsNo, "0");
		jarXml.saveDoc(saveXmlPath);
	}

	public JavaPackage classDetailToMap() throws IOException,
			ClassNotFoundException {

		JavaPackage rootPack = new JavaPackage();
		rootPack.elementName = packageName;

		Map<String, JavaPackage> codeMap = new HashMap<String, JavaPackage>();

		// Map codeWordsMap = new HashMap();
		// String codeWordsValue= null;
		// String codeWordsKey =null;

		codeMap.put(packageName, rootPack);

		List<String> cls = getClassInPackage(packageName);

		for (String s : cls) {
			try {
				// System.out.println("%%%%%%%%%%%%%%%%" + s);
				Class classanyway = SimpleLoader.findClass(s, targetJarPath);
				if (classanyway == null) {
					continue;
				}

				String className = classanyway.getName();
				String packName = className.substring(0,
						className.lastIndexOf("."));
				String classSimpleName = className.substring(className
						.lastIndexOf(".") + 1);
				String displayName = splitStringByUpper(classSimpleName);

				JavaClass classNode = new JavaClass();
				classNode.elementName = className;
				classNode.displayName = displayName;
				classNode.thisClass = classanyway;

				if (codeMap.containsKey(packName)) {
					JavaPackage packNode = codeMap.get(packName);

					if (classSimpleName.contains("$")) {
						String nestingName = classSimpleName
								.substring(classSimpleName.lastIndexOf("$") + 1);
						classNode.displayName = splitStringByUpper(nestingName);

						String nestFatherName = classSimpleName.substring(0,
								classSimpleName.lastIndexOf("$"));
						String nestFatherFullName = packName + "."
								+ nestFatherName;
						if (packNode.subElementMap
								.containsKey(nestFatherFullName)) {
							JavaClass classNestFatherNode = (JavaClass) packNode.subElementMap
									.get(nestFatherFullName);
							classNestFatherNode.subElementMap.put(className,
									classNode);
							// //鑾峰彇words
							// codeWordsKey = classNestFatherNode.elementName;
							// codeWordsValue =
							// classNestFatherNode.displayName+" " +
							// classNode.displayName +" ";
						} else {
							// the nested father class
							JavaClass classNestFatherNode = new JavaClass();
							classNestFatherNode.elementName = nestFatherFullName;
							classNestFatherNode.displayName = splitStringByUpper(nestFatherName);
							classNestFatherNode.subElementMap.put(className,
									classNode);
							packNode.subElementMap.put(nestFatherFullName,
									classNestFatherNode);
							// //鑾峰彇words
							// codeWordsKey = classNestFatherNode.elementName;
							// codeWordsValue =
							// classNestFatherNode.displayName+" " +
							// classNode.displayName +" ";
						}
					} else {
						if (packNode.subElementMap.containsKey(className)) {
							classNode = (JavaClass) packNode.subElementMap
									.get(className);
							classNode.thisClass = classanyway;
						} else {
							packNode.subElementMap.put(classNode.elementName,
									classNode);
						}

						// //鑾峰彇words
						// codeWordsKey = classNode.elementName;
						// codeWordsValue = classNode.displayName +" ";

					}
				} else {
					JavaPackage packNode = new JavaPackage();
					packNode.elementName = packName;

					if (classSimpleName.contains("$")) {
						String nestingName = classSimpleName
								.substring(classSimpleName.lastIndexOf("$") + 1);
						classNode.displayName = splitStringByUpper(nestingName);

						String nestFatherName = classSimpleName.substring(0,
								classSimpleName.lastIndexOf("$"));
						// the nested father class
						JavaClass classNestFatherNode = new JavaClass();
						classNestFatherNode.elementName = packName + "."
								+ nestFatherName;
						classNestFatherNode.displayName = splitStringByUpper(nestFatherName);

						classNestFatherNode.subElementMap.put(className,
								classNode);
						packNode.subElementMap.put(
								classNestFatherNode.elementName,
								classNestFatherNode);

						// //鑾峰彇words
						// codeWordsKey = classNestFatherNode.elementName;
						// codeWordsValue = classNestFatherNode.displayName+" "
						// + classNode.displayName +" ";

					} else {
						packNode.subElementMap.put(className, classNode);

						// 鑾峰彇words
						// codeWordsKey = classNode.elementName;
						// codeWordsValue = classNode.displayName +" ";
					}

					codeMap.put(packName, packNode);
					// codeWordsMap.put(codeWordsKey,codeWordsValue);

				}

			} catch (Exception ex) {
			}
		}

		System.out.println("simple loader acc " + SimpleLoader.acc);

		setPackageMap(codeMap);
		// writeCodeBag("codeWords.txt",codeWordsMap);
		return rootPack;
	}

	/**
	 * 锟斤拷锟矫帮拷锟斤拷影锟�
	 * 
	 */
	public void setPackageMap(Map<String, JavaPackage> codeMap) {
		HashMap<String, JavaPackage> addMap = new HashMap<String, JavaPackage>();
		Iterator<JavaPackage> it = codeMap.values().iterator();
		while (it.hasNext()) {
			JavaPackage curPack = it.next();
			String packName = curPack.elementName;
			if (!packName.equalsIgnoreCase(packageName)) {
				// System.out.println(packName);
				// if (packName.contains(".")) {
				// String parentName = packName.substring(0,
				// packName.lastIndexOf("."));
				// JavaPackage parentPackage = (JavaPackage) codeMap
				// .get(parentName);
				// if (parentPackage != null) {
				// parentPackage.subElementMap.put(packName, curPack);
				// }
				// }
				fixPackageMap(curPack, codeMap, addMap);
			} else {
				// System.out.println(packName);
			}
		}

		codeMap.putAll(addMap);
	}

	public void fixPackageMap(JavaPackage jPackage,
			Map<String, JavaPackage> codeMap, Map<String, JavaPackage> addMap) {
		String packName = jPackage.elementName;
		if (packName.contains(".")) {
			String parentName = packName
					.substring(0, packName.lastIndexOf("."));
			JavaPackage parentPackage;
			if (!codeMap.containsKey(parentName)) {
				if (!addMap.containsKey(parentName)) {
					JavaPackage pPackage = new JavaPackage();
					pPackage.elementName = parentName;
					addMap.put(parentName, pPackage);
				}
				parentPackage = addMap.get(parentName);
			} else {
				parentPackage = codeMap.get(parentName);
			}
			parentPackage.subElementMap.put(packName, jPackage);

			fixPackageMap(parentPackage, codeMap, addMap);
		}
	}

	/**
	 * 
	 * @param methName
	 * @return
	 */
	public String splitStringByLine(String methName) {
		String methDisplayName = "";
		String[] wordList = methName.split("_");
		for (int i = 0; i < wordList.length; i++) {
			methDisplayName += wordList[i].toLowerCase() + " ";
		}
		methDisplayName = methDisplayName.substring(0,
				methDisplayName.length() - 1);
		return methDisplayName;
	}

	/**
	 * 
	 * @param methName
	 * @return
	 */
	public String splitStringByDollar(String methName) {
		String methDisplayName = methName.replaceAll("[${}]", " ");
		return methDisplayName;
	}

	/**
	 * 
	 * @param methName
	 * @return
	 */
	public String splitStringByUpper(String methName) {
		String methDisplayName = "";
		char[] methChars = methName.toCharArray();
		int head = 0;
		List<String> methWordList = new LinkedList<String>();
		for (int j = 0; j < methChars.length; j++) {
			boolean isUpper = Character.isUpperCase(methChars[j]);
			if (isUpper && j != 0) {
				String word = methName.substring(head, j);
				head = j;

				methWordList.add(word);
				methDisplayName += (word.toLowerCase() + " ");
			}

			if (j == methChars.length - 1) {
				String word = methName.substring(head, j + 1);
				methWordList.add(word);
				methDisplayName += (word.toLowerCase());
			}
		}

		return methDisplayName;
	}

	public List<String> getClassInPackage(String pkgName) {
		List<String> ret = new ArrayList<String>();
		String rPath = pkgName.replace('.', '/') + "/";
		try {
			for (File classPath : CLASS_PATH_ARRAY) {
				// System.out.println(CLASS_PATH_PROP);
				// System.out.println(CLASS_PATH_ARRAY);
				if (!classPath.exists())
					continue;
				if (classPath.isDirectory()) {
					// System.out.println(classPath);
					File dir = new File(classPath, rPath);
					if (!dir.exists())
						continue;
					for (File file : dir.listFiles()) {
						if (file.isFile()) {
							String clsName = file.getName();
							if (clsName.endsWith(".class")) {

								clsName = pkgName
										+ "."
										+ clsName.substring(0,
												clsName.length() - 6);
								// System.out.println("clsname1:" + clsName);
								ret.add(clsName);
							} else {
								// System.out.println("clsname0:" + clsName);
							}
						}
					}
				} else {
					// System.out.println(classPath);
					FileInputStream fis = new FileInputStream(classPath);
					JarInputStream jis = new JarInputStream(fis, false);
					JarEntry e = null;
					while ((e = jis.getNextJarEntry()) != null) {
						String eName = e.getName();

						if (eName.startsWith(rPath) && eName.endsWith(".class")) {
							ret.add(eName.replace('/', '.').substring(0,
									eName.length() - 6));
							// System.out.println("eName:" + eName);
							// System.out.println("eName = " + eName);
						}
						// else
						// System.out.println(eName);

						jis.closeEntry();
					}
					jis.close();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return ret;
	}

	public String[] CLASS_PATH_PROP = {};

	public List<File> CLASS_PATH_ARRAY = getClassPath();

	public List<File> getClassPath() {
		List<File> ret = new ArrayList<File>();
		String delim = ":";
		if (System.getProperty("os.name").indexOf("Windows") != -1) {
			delim = ";";
			System.out.println("......................");
		}
		for (String pro : CLASS_PATH_PROP) {
			String[] pathes = System.getProperty(pro).split(delim);
			for (String path : pathes)
				ret.add(new File(path));
		}

		ret.add(new File("./data/argouml.jar"));

		return ret;
	}

	/*
	 * 鍙敤绫诲悕浣滀负鍏抽敭璇嶏紝鑾峰彇浠ｇ爜鐨勫叧閿瘝
	 */
	public Map<String, String> getClassWords(String classPath)
			throws IOException {
		Map<String, String> ret = new HashMap<String, String>();
		JarInputStream jis = new JarInputStream(new FileInputStream(classPath),
				false);
		JarEntry e = null;
		while ((e = jis.getNextJarEntry()) != null) {
			String entry = e.getName();
			if (entry.endsWith(".class")) {
				String s = entry.replace('/', '.').substring(0,
						entry.length() - 6);
				String s1 = s.substring(s.lastIndexOf(".") + 1, s.length());
				s1 = splitStringByDollar(s1);
				s1 = splitStringByUpper(s1);
				ret.put(s, s1);
			}
			jis.closeEntry();
		}
		jis.close();
		return ret;
	}

	public void writeCodeBag(String path, Map<String, String> codeWordsMap) {

		String s = new String();
		String s1 = new String();
		try {
			File f = new File(path);
			if (f.exists()) {
				System.out.println("鏂囦欢ok");
			} else {
				if (f.createNewFile()) {
					System.out.println("鏂囦欢鍒涘缓鎴愬姛锛�");
				} else {
					System.out.println("鏂囦欢鍒涘缓澶辫触锛�");
				}
			}

			// 鐢ㄦ煇涓�釜鏃堕棿娈典唬鐮佹彁浜や俊鎭腑鎶藉彇鍒扮殑浠ｇ爜鏉ヨ繃婊ゆ墍鏈夌殑绫伙紝
			// 缂╁皬鏌ユ壘鑼冨洿銆�
			List<String> logtimeClassList = getLogtimeClassList("./data/logtimeclasses.txt");
			// 浠ヤ笂浠ｇ爜鐢ㄦ煇涓�釜鏃堕棿娈典唬鐮佹彁浜や俊鎭腑鎶藉彇鍒扮殑浠ｇ爜鏉ヨ繃婊ゆ墍鏈夌殑绫伙紝

			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			Iterator iter = codeWordsMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();

				// 浠ヤ笅浠ｇ爜鐢ㄦ煇涓�釜鏃堕棿娈典唬鐮佹彁浜や俊鎭腑鎶藉彇鍒扮殑浠ｇ爜鏉ヨ繃婊ゆ墍鏈夌殑绫伙紝
				for (String temp : logtimeClassList) {
					String compstr1 = key.toString();
					compstr1 = compstr1
							.substring(compstr1.lastIndexOf(".") + 1);
					String compstr2 = temp;
					compstr2 = compstr2
							.substring(compstr2.lastIndexOf(".") + 1);

					if (compstr1.equals(compstr2)) {

						String content = key.toString() + "\t" + " " + val;
						s1 = content + "\n";
						output.write(s1);
						break;
					} else
						continue;
				}
				// 浠ヤ笂浠ｇ爜鐢ㄦ煇涓�釜鏃堕棿娈典唬鐮佹彁浜や俊鎭腑鎶藉彇鍒扮殑浠ｇ爜鏉ヨ繃婊ゆ墍鏈夌殑绫伙紝

				/*
				 * 浠ヤ笅浠ｇ爜琚笂杩颁唬鐮佹鏆傛椂鍙栦唬銆� for (String temp : appClassesList) {
				 * 
				 * if (temp.equals(key)) { String content = key.toString() +
				 * "\t" + " " + val; s1 = content + "\n"; output.write(s1);
				 * break; } else continue; }
				 */

			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<String> getLogtimeClassList(String filePath) {
		InputStream is;
		ArrayList<String> appClassesListTmp = new ArrayList<String>();
		try {
			is = new FileInputStream(filePath);
			String line; // 鐢ㄦ潵淇濆瓨姣忚璇诲彇鐨勫唴瀹�
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			line = reader.readLine(); // 璇诲彇绗竴琛�
			while (line != null) { // 濡傛灉 line 涓虹┖璇存槑璇诲畬浜�
				appClassesListTmp.add(line); //
				line = reader.readLine(); // 璇诲彇涓嬩竴琛�
			}
			reader.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return appClassesListTmp;
	}

	public void rewriteSVNLogFile(String inputFile, String outputFile) {
		String line; // 鐢ㄦ潵淇濆瓨姣忚璇诲彇鐨勫唴瀹�
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(inputFile)));
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					outputFile));
			line = reader.readLine(); // 璇诲彇绗竴琛�鐢变簬绗竴琛屾槸鏍囬锛屾墍浠ュ啀璇讳竴琛屻�
			writer.write(line + "\n");
			line = reader.readLine();
			while (line != null) {
				String[] wordList = line.split("\t");
				String key = wordList[1];

				for (String temp : appClassesList) {
					if (temp.equals(key)) {

						String s1 = line + "\n";

						writer.write(s1);
						break;
					} else
						continue;
				}
				line = reader.readLine();
			}
			reader.close();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// BufferedWriter output = new BufferedWriter(new FileWriter(f));

	}

}
