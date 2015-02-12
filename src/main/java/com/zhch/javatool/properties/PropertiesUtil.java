package com.lifeix.tool.properties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author lifeix
 * @version 0.0.1
 * @since 2012-04-21
 */
public class PropertiesUtil {

	private static Logger LOG = LoggerFactory.getLogger(PropertiesUtil.class);

	/**
	 * 根据key读取value
	 * 
	 * @param filePath
	 * @param key
	 * @return
	 */
	public static String readValue(String filePath, String key) {
		Properties props = readProperties(filePath);
		return props.getProperty(key);
	}

	/**
	 * 读取properties的全部信息
	 * 
	 * @param filePath
	 */
	public static Properties readProperties(String filePath) {
		Properties props = new Properties();
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
		} catch (FileNotFoundException e) {
			LOG.error("读取配置文件错误。 filename: " + filePath, e);
		} catch (IOException e) {
			LOG.error("读取配置文件错误。 filename: " + filePath, e);
		}
		return props;
	}

	/**
	 * 读取classpath下的配置文件
	 * 
	 * @param filename
	 * @return
	 */
	public static Properties loadClasspathProperties(String filename) {
		Properties props = new Properties();
		ClassLoader loader = PropertiesUtil.class.getClassLoader();
		try {
			props.load(loader.getResourceAsStream(filename));
		} catch (IOException e) {
			LOG.error("读取classpath配置文件错误。 filename: " + filename, e);
		}
		return props;
	}

	/**
	 * 写入properties信息
	 * 
	 * @param filePath
	 * @param parameterName
	 * @param parameterValue
	 */
	public static void writeProperties(String filePath, String parameterName, String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			prop.load(fis);
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (FileNotFoundException e) {
			LOG.error("写入配置文件错误。 filename: " + filePath, e);
		} catch (IOException e) {
			LOG.error("写入配置文件错误. filePath: {}, parameterName: {}, parameterValue: {}", new Object[] { filePath,
					parameterName, parameterValue });
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * 取得classpath
	 * 
	 * @return
	 */
	public static String getClassPath() {
		String classPath = null;
		try {
			classPath = PropertiesUtil.class.getResource("/").getPath().replaceAll("%20", " ");
		} catch (Exception e) {
			LOG.info("Get /WEB-INF/class path failed, try to use [user.dir] path.");
			classPath = System.getProperty("user.dir");
		}
		return classPath;
	}

}
