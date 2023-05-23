package com.scripted.mobile;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.openqa.selenium.WebDriver.Options;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;


public class IOSUtil 
{
	public static Logger LOGGER = LogManager.getLogger(IOSUtil.class);
	private IOSDriver<MobileElement> iOSNativeDriver = null;
	private IOSDriver<WebElement> iOSWebDriver = null;
	private WebDriver webdriver;
	private WebDriver driver;
	private IOSDeviceSettings iOSSettings;

	public IOSUtil(String remoteUrl, IOSDeviceSettings iOSSettings) {
		this.iOSSettings = iOSSettings;
		try {
			iOSNativeDriver = new IOSDriver<MobileElement>(new URL(remoteUrl), this.iOSSettings.getDesiredCapabilities());
			setIOSNativeDriver(iOSNativeDriver);
			this.driver = iOSNativeDriver;
		} catch (Exception e) {
			LOGGER.error("IOSDriver initilization issues : " + e.getMessage());
			Assert.fail("IOSDriver initilization issues : " + e.getMessage());
			throw new WebDriverException("Driver initilization issues: " + e.getMessage());
		}
		this.setWebdriver(this.driver);
	}

	public IOSUtil() {
		throw new WebDriverException("Driver initilization issues");
	}

	public void setIOSNativeDriver(IOSDriver<MobileElement> driver) {
		this.iOSNativeDriver = driver;
	}

	public IOSDriver<MobileElement> getIOSNativeDriver() {
		return iOSNativeDriver;
	}

	public void setIOSWebDriver(IOSDriver<WebElement> driver) {
		this.iOSWebDriver = driver;
	}

	public IOSDriver<WebElement> getIOSWebDriver() {
		return iOSWebDriver;
	}

	public Options manage() {
		return this.driver.manage();
	}

	public IOSDeviceSettings getSettings() {
		return iOSSettings;
	}


	public WebDriver getWebdriver() {
		return webdriver;
	}

	public void setWebdriver(WebDriver webdriver) {
		this.webdriver = webdriver;
	}
}
