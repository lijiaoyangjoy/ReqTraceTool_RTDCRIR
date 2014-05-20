package DynamicTraceability.Toolkit.JavaToXML;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ArgoUMLAppClassesFilter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArgoUMLAppClassesFilter a = new ArgoUMLAppClassesFilter();
		List<String> argoumlAppClassLists = a.getClassInPackage("org");
		a.writeAppClassesList("./data/argouml-app-classes.txt",argoumlAppClassLists);
		

	}
	
	public List<String> getClassInPackage(String pkgName) {
		List<String> ret = new ArrayList<String>();
		String rPath = pkgName.replace('.', '/') + "/";
		try {
			for (File classPath : CLASS_PATH_ARRAY) {
				if (!classPath.exists())
					continue;
				if (classPath.isDirectory()) {
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
								ret.add(clsName);
							} else {
							}
						}
					}
				} else {
					FileInputStream fis = new FileInputStream(classPath);
					JarInputStream jis = new JarInputStream(fis, false);
					JarEntry e = null;
					while ((e = jis.getNextJarEntry()) != null) {
						String eName = e.getName();

						if (eName.startsWith(rPath) && eName.endsWith(".class")) {
							ret.add(eName.replace('/', '.').substring(0,
									eName.length() - 6));
						}

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
	
	private String[] CLASS_PATH_PROP = {};

	private List<File> CLASS_PATH_ARRAY = getClassPath();

	private List<File> getClassPath() {
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

	public void writeAppClassesList(String path, List<String> classesList) {

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

			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			Iterator<String> iter = classesList.iterator();
			while (iter.hasNext()) {
				String appClassName = iter.next().toString();
				s1 = appClassName + "\n";
				output.write(s1);

			}
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}