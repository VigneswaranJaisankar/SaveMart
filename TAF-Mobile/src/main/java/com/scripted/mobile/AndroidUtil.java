package com.scripted.mobile;

import java.net.URL;

import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;


public class AndroidUtil {
	public static Logger LOGGER = LogManager.getLogger(AndroidUtil.class);
	private AndroidDriver<WebElement> androidWebDriver = null;
	private AndroidDriver<MobileElement> androidNativeDriver = null;
	private WebDriver webdriver;
	private WebDriver driver;
	private AndroidDeviceSettings androidSettings;

	public AndroidUtil(String remoteUrl, AndroidDeviceSettings androidSettings, boolean isNative) {
		this.androidSettings = androidSettings;
		try {
			if (isNative){
				androidNativeDriver = new AndroidDriver<MobileElement>(new URL(remoteUrl), this.androidSettings.getDesiredCapabilities());
				setAndroidWebDriver(androidWebDriver);
			} else {
				androidWebDriver = new AndroidDriver<WebElement>(new URL(remoteUrl), this.androidSettings.getDesiredCapabilities());
				setAndroidWebDriver(androidWebDriver);
			}

			this.driver = androidWebDriver;
		} catch (Exception e) {
			LOGGER.error("AndroidDriver initilization issues : " + e.getMessage());
			Assert.fail("AndroidDriver initilization issues : " + e.getMessage());
			throw new WebDriverException("AndroidDriver initilization issues : " + e.getMessage());
			
			
		}
		this.setWebdriver(this.driver);
	}

	public AndroidUtil() {
		throw new WebDriverException("AndroidDriver initilization issues");
	}

	public void setAndroidWebDriver(AndroidDriver<WebElement> driver) {
		this.androidWebDriver = driver;
	}

	public void setAndroidNativeDriver(AndroidDriver<MobileElement> driver) {
		this.androidNativeDriver = driver;
	}


	public AndroidDriver<MobileElement> getAndroidNativeDriver() {
		return androidNativeDriver;
	}

	public AndroidDriver<WebElement> getAndroidWebDriver() {
		return androidWebDriver;
	}

	public Options manage() {
		return this.driver.manage();
	}

	public AndroidDeviceSettings getSettings() {
		return androidSettings;
	}

	public boolean isMobileApp() {
		String check = this.androidSettings.getAppPath() + this.androidSettings.getAppActivity();
		check = check.trim();
		return (check.length() > 0);
	}

	public WebDriver getWebdriver() {
		return webdriver;
	}

	public void setWebdriver(WebDriver webdriver) {
		this.webdriver = webdriver;
	}
}
