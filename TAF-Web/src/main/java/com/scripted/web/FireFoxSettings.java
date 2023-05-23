package com.scripted.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;

public class FireFoxSettings {
	public static Logger LOGGER = LogManager.getLogger(FireFoxSettings.class);
	private FirefoxOptions firefoxOptions = new FirefoxOptions();
	Map<String, Object> firefoxOpts =  new HashMap<String, Object>();

	public FirefoxOptions setByFirefoxOptions(File fileName) {
		try {
		System.setProperty("webdriver.gecko.driver", WebDriverPathUtil.getGeckoDriverPath());
		firefoxOpts.put("CapabilityType.ACCEPT_SSL_CERTS", true);
		firefoxOpts.put("javascriptEnabled", true);
		firefoxOpts.put("CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR", "UnexpectedAlertBehaviour.IGNORE");
		setFirefoxOptionsPropFile(fileName);
		}catch(Exception e)
		{
			LOGGER.error("Error occurred while initialising Firefox browser, Exception :"+e);
			e.printStackTrace();
			Assert.fail("Error occurred while initialising Firefox browser, Exception :"+e);
		}
		return firefoxOptions;
	}

	public void setFirefoxOptionsPropFile(File fileName) {
		Properties props = new Properties();
		try(FileInputStream inputStream = new FileInputStream(fileName)) {
			props.load(inputStream);
			for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements();) {
				String name = (String) e.nextElement();
				String value = props.getProperty(name);
				String capabilityName = StringUtils.substringAfter(name.toLowerCase(),"firefox.firefoxoptions.");
				if (name.toLowerCase().contains("firefoxoptions") ) {
					firefoxOpts.put(StringUtils.substringAfter(name.toLowerCase(),"firefox.firefoxoptions."),value);
				}
			}
		this.firefoxOptions	= setFirefoxOptionsFromMap(firefoxOpts);
		} catch (Exception e) {
			LOGGER.error("Error occured while configuring firefox , Exception: " + e);
			Assert.fail("Error occured while configuring firefox , Exception: " + e);
			e.printStackTrace();
		}
	}
	
	public FirefoxOptions setFirefoxOptionsFromMap(Map<String,Object>firefoxOptsMap) {
		FirefoxOptions fOptions = new FirefoxOptions();
		Set<String> existing = new HashSet<>();
		try
		{
		firefoxOptsMap.forEach((k,v)->fOptions.setCapability(k, v));
		}
		catch(Exception e)
		{
			LOGGER.error("Error while setting capabilities for firefox"+e);
			Assert.fail("Error while setting capabilities for firefox"+e);
		}
		return fOptions;
	}
}
