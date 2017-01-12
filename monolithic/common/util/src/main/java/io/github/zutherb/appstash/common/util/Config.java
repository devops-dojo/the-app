package io.github.zutherb.appstash.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	public static String getProperty(String property) {
		Properties prop = new Properties();
		InputStream input = null;
		String result = "";

		try {

			input = io.github.zutherb.appstash.common.util.Config.class.getResourceAsStream("config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			result = prop.getProperty(property);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}
}
