package DynamicTraceability.Toolkit.Tool;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import com.sun.org.apache.bcel.internal.util.ClassPath;

@SuppressWarnings("restriction")
public class SimpleLoader extends ClassLoader {

	// public SimpleLoader()
	// {
	// findClass("TestClass$TestInnerClass");
	// }
	public static int acc = 0;

	public static Class<?> findClass(String className, String classPath) {
		try{
//			System.out.println(className);
			return Class.forName(className);
		} catch (VerifyError e) {
//			System.out.println(className);
//			e.printStackTrace();
			acc++;
		} catch (ClassNotFoundException e) {
//			System.out.println(className);
//			e.printStackTrace();
			acc++;
		} catch (ExceptionInInitializerError e){
//			System.out.println(className);
//			e.printStackTrace();
			acc++;
		} catch(NoClassDefFoundError e) {
//			System.out.println(className);
//			e.printStackTrace();
			acc++;
		}
		return null;
	}

}
