package com.nuriggum.nuriframe.common.util;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public class NuriPropertiesUtil {
	//파일구분자
	static final String FILE_SEPARATOR = System.getProperty("file.separator");

	public static final String GLOBALS_PROPERTIES_FILE = "classpath:" + FILE_SEPARATOR + "application.properties";

	/**
	 * 인자로 주어진 문자열을 Key값으로 하는 프로퍼티 값을 반환한다(Globals.java 전용)
	 * @param keyName String
	 * @return String
	*/
	public static String getProperty(String keyName) {
		String value = "";

        Resource resources = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader())
			    .getResource(GLOBALS_PROPERTIES_FILE);

		try (InputStream in = resources.getInputStream()) {
			Properties props = new Properties();
			props.load(new BufferedInputStream(in));
			value = props.getProperty(keyName).trim();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
}
