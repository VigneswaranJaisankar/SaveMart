package com.scripted.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import com.scripted.dataload.PropertyDriver;

public class ChromeSettings {
	
	public static Logger LOGGER = LogManager.getLogger(ChromeSettings.class);
	private ChromeOptions chromeOptionsObj = new ChromeOptions();
	Map<String, Object> chromePrefs = new HashMap<String, Object>();
	List<String> chromeOptions = new ArrayList<>();

	public ChromeOptions setBychromeOptions(File fileName) {
		try {
		System.setProperty("webdriver.chrome.driver", WebDriverPathUtil.getChromeDriverPath());
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1);
		chromePrefs.put("download.prompt_for_download", false);
		chromePrefs.put("credentials_enable_service", false);
		chromePrefs.put("password_manager_enabled", false);
		chromeOptions.add("disable-plugins");
		chromeOptions.add("disable-extensions");
		chromeOptions.add("allow-running-insecure-content");
		chromeOptions.add("ignore-certificate-errors");
		chromeOptions.add("--always-authorize-plugins");
		chromeOptions.add("--disable-notifications");
		chromeOptions.add("disable-infobars");
		chromeOptions.add("--test-type");
		chromeOptions.add("--disable-web-security");
		chromePrefs.put("useAutomationExtension", false);

		setChromeOptionsPropFile(fileName);
		Properties props = new Properties();
		FileInputStream inputStream = new FileInputStream(fileName);
		props.load(inputStream);
		PropertyDriver p = new PropertyDriver();
		p.setPropFilePath("src/main/resources/Web/Properties/Browser.properties");
		String proxyHost = p.readProp("https.proxyHost");
		String proxyPort = p.readProp("https.proxyPort");
		
		if (!p.readProp("https.proxyHost").isEmpty()
				&& !p.readProp("https.proxyPort").isEmpty()) {
			String Host = p.readProp("https.proxyHost");
			String Port =  p.readProp("https.proxyPort");
			String proxy = "" + Host + " " + ":" + Port + "";
			chromeOptionsObj.addArguments("--proxy-server=http://" + proxy);
		}
		chromeOptionsObj.addArguments(chromeOptions);
		chromeOptionsObj.addArguments("--disable-blink-features=AutomationControlled");
		chromeOptionsObj.setExperimentalOption("useAutomationExtension", false);
		chromeOptionsObj.setPageLoadStrategy(PageLoadStrategy.NONE);
		chromeOptionsObj.setExperimentalOption("prefs", chromePrefs);
		
		}catch(Exception e)
		{
			LOGGER.error("Error occurred while initialising chrome browser, Exception :"+e);
			e.printStackTrace();
			Assert.fail("Error occurred while initialising chrome browser, Exception :"+e);
		}
		return this.chromeOptionsObj;
	}

	public void setChromeOptionsPropFile(File fileName) throws FileNotFoundException {
		Properties props = new Properties();
		try(FileInputStream inputStream = new FileInputStream(fileName)) {
			props.load(inputStream);
			if (props.getProperty("chrome.chromeoptions") != null) {
				String[] chromeOptionspropfile = props.getProperty("chrome.chromeoptions").split(",");
				Collections.addAll(chromeOptions, chromeOptionspropfile);
				chromeOptions = chromeOptions.stream().distinct().collect(Collectors.toList());
			}
			for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements();) {
				String name = (String) e.nextElement();
				String value = props.getProperty(name);
				if (name.toLowerCase().contains("chromeprefs") && !(chromePrefs
						.containsKey(StringUtils.substringAfter(name.toLowerCase(), "chrome.chromeprefs.")))) {
					this.chromePrefs.put(StringUtils.substringAfter(name.toLowerCase(), "chrome.chromeprefs."), value);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error occurred while initialising chrome browser, Exception :"+e);
			e.printStackTrace();
			Assert.fail("Error occurred while initialising chrome browser, Exception :"+e);
		}
	}

}
