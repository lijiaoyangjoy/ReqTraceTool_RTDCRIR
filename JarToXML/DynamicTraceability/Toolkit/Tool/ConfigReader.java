package DynamicTraceability.Toolkit.Tool;


/**
 * ��ȡ�����ļ���Ϣ
 * @author xOiuNt
 *
 */
public class ConfigReader {
	private String configName = "config.xml";
	private String applicationPath = "C:/workspace/JarToXML/data/";
	
	public String jarFilePath ; 
	public String outputXmlPath ; 
	public String packagaName ; 
	public String jarName;
	
	private ConfigReader () {
		
	}
	
	private static ConfigReader configReader;
	public static ConfigReader getInstance()  {
		if ( configReader == null)  {
			configReader = new ConfigReader();
		}
		return configReader;
	}
	
	/**
	 * ��ȡ������Ϣ
	 *
	 */
	public void readConfig() {
		try {
			XmlOperator xmlOpt = new XmlOperator();
			
			xmlOpt.openDoc(applicationPath + configName);
			outputXmlPath = xmlOpt.getAttributeByName("SaveXmlPath").get(0);
			jarFilePath = xmlOpt.getAttributeByName("JarFilePath").get(0);
			packagaName = xmlOpt.getAttributeByName("PackagaName").get(0);
			jarName = xmlOpt.getAttributeByName("JarName").get(0);
			System.out.println(outputXmlPath);
		}
		catch(Exception ex) {
			System.out.println(ex);
		}
	}
}
