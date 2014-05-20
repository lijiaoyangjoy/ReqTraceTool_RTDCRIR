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

public class RawClassWordsProcessor {

	public static void main(String[] args) throws IOException {
		RawClassWordsProcessor rp = new RawClassWordsProcessor();
		rp.fileStringToLower("./data/codeWordsSupplement.txt",
				"./data/codeWordsSupplement-new.txt");
//		 rp.batchClassWordsProcess("./data/batch/",
//		 "./data/batchClassWords.txt");
	}

	public void batchClassWordsProcess(String sourceFolder, String targetFile)
			throws IOException {
		File dir = new File(sourceFolder);
		File f = new File(targetFile);

		BufferedWriter output = new BufferedWriter(new FileWriter(f));

		if (f.exists()) {
			System.out.println("创建目标文件");
		} else {
			if (f.createNewFile()) {
				System.out.println("文件创建成功！");
			} else {
				System.out.println("文件创建失败！");
			}
		}

		if (dir.exists()) {
			for (File file : dir.listFiles()) {
				if (file.isFile()) {
					String fileName = file.getName();
					if (fileName.endsWith(".txt")) {
						StringBuilder strBuilder = new StringBuilder();
						String tempStr = getClassCodeString(sourceFolder
								+ fileName);
						String className = fileName.substring(0,
								fileName.indexOf(".txt"));
						strBuilder.append(className + "\t" + tempStr + "\n");
						output.write(strBuilder.toString());
					}
				}
			}
		}

		output.close();

	}

	public String getClassCodeString(String filePath) {
		InputStream is;
		String returnStr = "";
		try {
			System.out.println(filePath);
			is = new FileInputStream(filePath);
			String line; // 用来保存每行读取的内容
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			line = reader.readLine(); // 读取第一行
			while (line != null) { // 如果 line 为空说明读完了

				line = line.substring(line.lastIndexOf(".") + 1, line.length());
				if (line.contains("(")) {
					line = line.substring(0, line.indexOf("("));
				}

				// 分词
				PackageUtil pu = new PackageUtil();
				line = pu.splitStringByUpper(line);
				line = pu.splitStringByDollar(line);
				line = pu.splitStringByLine(line);
				line = line.toLowerCase();
				returnStr += line + " ";
				line = reader.readLine(); // 读取下一行
			}
			reader.close();
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return returnStr;
	}

	public void fileStringToLower(String filePath, String output)
			throws IOException {
		InputStream is;
		String returnStr = "";
		File f = new File(output);

		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		is = new FileInputStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line; 
		line = reader.readLine(); // 读取第一行
		while (line != null) { // 如果 line 为空说明读完了
			// 分词
			String[] temp = line.split("\t");
			returnStr = temp[0] + "\t";
			returnStr += temp[1].toLowerCase();
			writer.write(returnStr + "\n");
			line = reader.readLine(); // 读取下一行
		}
		reader.close();
		is.close();
		writer.close();
	}

}
