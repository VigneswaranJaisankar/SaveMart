package com.scripted.generic;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.scripted.generic.FileUtils;

public class PropertyFileReader {
	private static final Logger log = LogManager.getLogger(PropertyFileReader.class);
	public static String strReturnVal = null;

	public static String funcFetchConfigValue(String strVarName) {
		File file = new File(FileUtils.getFilePath("src/main/resources/properties/config.properties"));
		try (FileInputStream fileInput = new FileInputStream(file)) {
			strVarName = strVarName.replace(" ", "_");
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			Enumeration<?> enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				strReturnVal = properties.getProperty(strVarName);
				if (strReturnVal != null) {
					break;
				}
			}
		} catch (Exception e) {
			log.error("Error occurred while trying to read property file " + "Exception" + e);
			Assert.fail("Error occurred while trying to read property file " + "Exception" + e);
		}
		return strReturnVal;
	}
}
