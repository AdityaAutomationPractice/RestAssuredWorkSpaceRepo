package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	public static Properties getpropObject() throws IOException {
		FileInputStream fp = new FileInputStream("src\\test\\resources\\config.properties");
		Properties prop = new Properties();
		prop.load(fp);
		return prop;
	}
	
	public static String getWorngURL() throws IOException {
		return getpropObject().getProperty("wrongURL");
	}
	
	public static String getAPIKey() throws IOException {
		return getpropObject().getProperty("Key");
	}

}
