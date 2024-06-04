package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	static String url;
	static Properties prop;
	
	public static String getpropObject() throws IOException {
		FileInputStream fp = new FileInputStream("src\\test\\resources\\config.properties");
		prop = new Properties();
		prop.load(fp);
		String db_mode = prop.getProperty("debugmode");
		if(db_mode.equalsIgnoreCase("on")){
		       url= prop.getProperty("correctURL");
		    }
		    else{
		        url=System.getProperty("url");
		    }
		return url;
	}
	
//	public static String getCorrectURL() throws IOException {
//		if(prop.getProperty("debugmode").equals("on")){
//		return getpropObject().getProperty("correctURL");
//		}
//		else {
//		return 	System.getProperty("url");
//		}
//	}
//	
//	public static String getWorngURL() throws IOException {
//		return getpropObject().getProperty("wrongURL");
//	}
//	
//	public static String getAPIKey() throws IOException {
//		return getpropObject().getProperty("key");
//	}

}
