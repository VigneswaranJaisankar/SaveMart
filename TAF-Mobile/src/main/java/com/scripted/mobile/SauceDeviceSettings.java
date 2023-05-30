package com.scripted.mobile;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.scripted.dataload.PropertyDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class SauceDeviceSettings {

	public static Logger LOGGER = LogManager.getLogger(SauceDeviceSettings.class);
	public static AndroidDriver<MobileElement> androidDriver = null;
	public static IOSDriver<MobileElement> iosDriver = null;
	DesiredCapabilities capability =null;
	
	public SauceDeviceSettings() {
		this.capability = new DesiredCapabilities();
	}
	
	public AndroidDriver<MobileElement> getConnectionSauceAndroidNativeApp(Properties mobConfigProp) {
		
		try {
			capability.setCapability("username", mobConfigProp.getProperty("sauceUserName"));
			capability.setCapability("accessKey", mobConfigProp.getProperty("sauceAccessKey"));
			capability.setCapability("deviceName", mobConfigProp.getProperty("Android.deviceName"));
			capability.setCapability("platformName", mobConfigProp.getProperty("Android.platformName"));
			capability.setCapability("platformVersion", mobConfigProp.getProperty("Android.platformVersion"));
			capability.setCapability("app", mobConfigProp.getProperty("applicationName"));
			capability.setCapability("deviceOrientation", mobConfigProp.getProperty("deviceOrientation"));
			capability.setCapability("appiumVersion", mobConfigProp.getProperty("appiumVersion"));
			capability.setBrowserName("");
			if (!(mobConfigProp.getProperty("httpsproxyHost").isEmpty()) && !(mobConfigProp.getProperty("httpsproxyPort").isEmpty())) {
				System.setProperty("https.proxyHost", mobConfigProp.getProperty("httpsproxyHost"));
				System.setProperty("https.proxyPort", mobConfigProp.getProperty("httpsproxyPort"));
			}
			
			androidDriver = new AndroidDriver<MobileElement>(new URL("https://ondemand.saucelabs.com/wd/hub"),
					capability);
			androidDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Exception e) {

		}
		
	return androidDriver;
}
	
public AndroidDriver<MobileElement> getConnectionSauceAndroidWeb(Properties mobConfigProp) {
		
		try {
			capability.setCapability("username", mobConfigProp.getProperty("sauceUserName"));
			capability.setCapability("accessKey", mobConfigProp.getProperty("sauceAccessKey"));
			capability.setCapability("deviceName", mobConfigProp.getProperty("Android.deviceName"));
			capability.setCapability("platformName", mobConfigProp.getProperty("Android.platformName"));
			capability.setCapability("platformVersion", mobConfigProp.getProperty("Android.platformVersion"));
			capability.setCapability("deviceOrientation", mobConfigProp.getProperty("deviceOrientation"));
			capability.setCapability("appiumVersion", mobConfigProp.getProperty("appiumVersion"));
			if (!(mobConfigProp.getProperty("httpsproxyHost").isEmpty()) && !(mobConfigProp.getProperty("httpsproxyPort").isEmpty())) {
				System.setProperty("https.proxyHost", mobConfigProp.getProperty("httpsproxyHost"));
				System.setProperty("https.proxyPort", mobConfigProp.getProperty("httpsproxyPort"));
			}
			capability.setBrowserName("chrome");
			androidDriver = new AndroidDriver<MobileElement>(new URL("https://ondemand.saucelabs.com/wd/hub"),
					capability);
			androidDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Exception e) {

		}
		
	return androidDriver;
}
	
public IOSDriver<MobileElement> getConnectionSauceIOSNativeApp(Properties mobConfigProp) {
		
		try {
			
			capability.setCapability("username", mobConfigProp.getProperty("sauceUserName"));
			capability.setCapability("accessKey", mobConfigProp.getProperty("sauceAccessKey"));
			capability.setCapability("deviceName", mobConfigProp.getProperty("iOS.deviceName"));
			capability.setCapability("platformName", mobConfigProp.getProperty("iOS.platformName"));
			capability.setCapability("platformVersion", mobConfigProp.getProperty("iOS.platformVersion"));
			capability.setCapability("app", mobConfigProp.getProperty("applicationName"));
			capability.setCapability("deviceOrientation", mobConfigProp.getProperty("deviceOrientation"));
			capability.setCapability("appiumVersion", mobConfigProp.getProperty("appiumVersion"));
			capability.setBrowserName("");
			if (!(mobConfigProp.getProperty("httpsproxyHost").isEmpty()) && !(mobConfigProp.getProperty("httpsproxyPort").isEmpty())) {
				System.setProperty("https.proxyHost", mobConfigProp.getProperty("httpsproxyHost"));
				System.setProperty("https.proxyPort", mobConfigProp.getProperty("httpsproxyPort"));
			}
			iosDriver = new IOSDriver<MobileElement>(new URL("https://ondemand.saucelabs.com/wd/hub"),
					capability);
			iosDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iosDriver;
	}

public IOSDriver<MobileElement> getConnectionSauceIOSWeb(Properties mobConfigProp) {
	
	try {
		
		capability.setCapability("username", mobConfigProp.getProperty("sauceUserName"));
		capability.setCapability("accessKey", mobConfigProp.getProperty("sauceAccessKey"));
		capability.setCapability("deviceName", mobConfigProp.getProperty("iOS.deviceName"));
		capability.setCapability("platformName", mobConfigProp.getProperty("iOS.platformName"));
		capability.setCapability("platformVersion", mobConfigProp.getProperty("iOS.platformVersion"));
		capability.setCapability("deviceOrientation", mobConfigProp.getProperty("deviceOrientation"));
		capability.setCapability("appiumVersion", mobConfigProp.getProperty("appiumVersion"));
		capability.setBrowserName("safari");
		if (!(mobConfigProp.getProperty("httpsproxyHost").isEmpty()) && !(mobConfigProp.getProperty("httpsproxyPort").isEmpty())) {
			System.setProperty("https.proxyHost", mobConfigProp.getProperty("httpsproxyHost"));
			System.setProperty("https.proxyPort", mobConfigProp.getProperty("httpsproxyPort"));
		}
		iosDriver = new IOSDriver<MobileElement>(new URL("https://ondemand.saucelabs.com/wd/hub"),
				capability);
		iosDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return iosDriver;
}
		
}
