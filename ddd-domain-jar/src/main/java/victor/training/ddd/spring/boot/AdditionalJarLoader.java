package victor.training.ddd.spring.boot;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class AdditionalJarLoader {
	public static void addJarAtRuntime(File jarFile) {
		try {
			System.out.println("Using oracle jar location: " + jarFile);
			if (!jarFile.isFile()) {
				throw new FileNotFoundException("Oracle jar not found at: " + jarFile);
			}
			URL url = jarFile.toURI().toURL();
			Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(ClassLoader.getSystemClassLoader(), new Object[] { url });
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
