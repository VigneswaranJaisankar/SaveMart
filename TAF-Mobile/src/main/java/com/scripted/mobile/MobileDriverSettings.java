package com.scripted.mobile;

import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;

import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.scripted.dataload.PropertyDriver;
import com.scripted.license.VerifyKey;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class MobileDriverSettings {

	public static Logger LOGGER = LogManager.getLogger(MobileDriverSettings.class);
	public static AndroidDriver<WebElement> androidWebDriver = null;
	public static IOSDriver<WebElement> iosWebDriver = null;
	public static AndroidDriver<MobileElement> androidNativeDriver = null;
	public static IOSDriver<MobileElement> iosNativeDriver = null;
	private static AndroidDeviceSettings androidSettings = null;
	private static IOSDeviceSettings iOSSettings = null;
	private static PcloudyDeviceSettings pcloudyDeviceSettings = null;
	private static SauceDeviceSettings sauceDeviceSettings = null;
	public static boolean bnative = false;

	public static AndroidDriver<MobileElement> funcGetNativeAndroiddriver(String configFileName)
			throws InterruptedException, GeneralSecurityException, IOException, ParseException {
		bnative = true;
		VerifyKey obj = new VerifyKey();
		if (obj.verifyexpiry()) {
			PropertyDriver propertyDriver = new PropertyDriver();
			propertyDriver.setPropFilePath("src/main/resources/Mobile/Properties/" + configFileName + ".properties");
			Map<String, String> androidProperties = propertyDriver.readProp();
			androidProperties.forEach((k, v) -> {
				if (k.equalsIgnoreCase("android.server.ip"))
					getAndroidSettings().setServerIp(v);
				if (k.equalsIgnoreCase("android.server.port"))
					getAndroidSettings().setServerPort(v);
				if (k.equalsIgnoreCase("android.app.path"))
					getAndroidSettings().setAppPath(v);
				if (k.equalsIgnoreCase("android.app.activity"))
					getAndroidSettings().setAppActivity(v);
				if (k.equalsIgnoreCase("android.app.package"))
					getAndroidSettings().setAppPackage(v);
				if (k.equalsIgnoreCase("android.deviceName"))
					getAndroidSettings().setDeviceName(v);
				if (k.equalsIgnoreCase("android.deviceID"))
					getAndroidSettings().setDeviceID(v);
				if (k.equalsIgnoreCase("android.emulator.name"))
					getAndroidSettings().setEmulator(v);
				if (k.equalsIgnoreCase("android.platformVersion"))
					getAndroidSettings().setAndroidVersion(v);

			});
			getAndroidSettings().setBrowserName("");
			AppiumSettings.startAppiumServer(androidProperties);
			AndroidUtil androidDriver = new AndroidUtil(androidSettings.getHubUrl().toString(), androidSettings, true);
			setAndroidNativeDriver(androidDriver.getAndroidNativeDriver());
			androidDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		Thread.sleep(8000);
		return getAndroidNativeDriver();
	}

	public static AndroidDriver<WebElement> funcGetWebAndroidDriver(String configFileName)
			throws GeneralSecurityException, IOException, ParseException, InterruptedException {
		VerifyKey obj = new VerifyKey();
		if (obj.verifyexpiry()) {
			PropertyDriver propertyDriver = new PropertyDriver();
			propertyDriver.setPropFilePath("src/main/resources/Mobile/Properties/" + configFileName + ".properties");
			Map<String, String> androidProperties = propertyDriver.readProp();
			androidProperties.forEach((k, v) -> {
				if (k.equalsIgnoreCase("android.deviceName"))
					getAndroidSettings().setDeviceName(v);
				if (k.equalsIgnoreCase("android.deviceID"))
					getAndroidSettings().setDeviceID(v);
				if (k.equalsIgnoreCase("android.platformVersion"))
					getAndroidSettings().setAndroidVersion(v);
				if (k.equalsIgnoreCase("android.browser.name"))
					getAndroidSettings().setBrowserName(v);
			});
			getAndroidSettings().setAppActivity("");
			getAndroidSettings().setAppPackage("");
			// AppiumSettings.startAppiumServer(androidProperties);
			AndroidUtil androidDriver = new AndroidUtil(androidSettings.getHubUrl().toString(), androidSettings, false);
			setAndroidWebDriver(androidDriver.getAndroidWebDriver());
			androidDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			androidDriver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
			androidDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		}
		return getAndroidWebDriver();
	}

	public static AndroidDriver<MobileElement> funcGetpCloudyNativeAndroiddriver(String configFileName)
			throws GeneralSecurityException, IOException, ParseException, InterruptedException {
		bnative = true;
		VerifyKey obj = new VerifyKey();
		if (obj.verifyexpiry()) {
			Properties mobConfigReader = new Properties();
			try (FileReader reader = new FileReader(
					"src/main/resources/Mobile/pCloudy/Properties/" + configFileName + ".properties")) {
				mobConfigReader.load(reader);
				androidNativeDriver = getPcloudyDeviceSettings().getConnectionPcloudyAndroidNativeApp(mobConfigReader);
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("Error while reading pCloudy Android native Apps Config file" + "Exception :" + e);
			}
		}
		return androidNativeDriver;
	}

	public static AndroidDriver<WebElement> funcGetpCloudyWebAndroiddriver(String configFileName)
			throws GeneralSecurityException, IOException, ParseException, InterruptedException {
		VerifyKey obj = new VerifyKey();
		if (obj.verifyexpiry()) {
			Properties mobConfigReader = new Properties();
			try (FileReader reader = new FileReader(
					"src/main/resources/Mobile/Properties/" + configFileName + ".properties")) {
				mobConfigReader.load(reader);
				androidWebDriver = getPcloudyDeviceSettings().getConnectionPcloudyAndroidWeb(mobConfigReader);
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("Error while reading pCloudy Android web Apps Config file" + "Exception :" + e);
			}
		}
		return (AndroidDriver<WebElement>) androidWebDriver;
	}

	public static IOSDriver<MobileElement> funcGetpCloudyNativeIOSdriver(String configFileName)
			throws GeneralSecurityException, IOException, ParseException, InterruptedException {
		bnative = true;
		VerifyKey obj = new VerifyKey();
		if (obj.verifyexpiry()) {
			Properties mobConfigReader = new Properties();
			try (FileReader reader = new FileReader(
					"src/main/resources/Mobile/Properties/" + configFileName + ".properties")) {
				mobConfigReader.load(reader);
				iosNativeDriver = getPcloudyDeviceSettings().getConnectionPcloudyIOSNativeApp(mobConfigReader);
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("Error while reading pCloudy IOS native Apps Config file" + "Exception :" + e);
			}
		}
		return getIOSNativeDriver();
	}

	public static IOSDriver<WebElement> funcGetpCloudyWebIOSdriver(String configFileName)
			throws GeneralSecurityException, IOException, ParseException, InterruptedException {
		VerifyKey obj = new VerifyKey();
		if (obj.verifyexpiry()) {
			Properties mobConfigReader = new Properties();
			try (FileReader reader = new FileReader(
					"src/main/resources/Mobile/Properties/" + configFileName + ".properties")) {
				mobConfigReader.load(reader);
				iosWebDriver = getPcloudyDeviceSettings().getConnectionPcloudyIOSWeb(mobConfigReader);
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("Error while reading pCloudy IOS web Apps Config file" + "Exception :" + e);
			}
		}
		return getIOSWebDriver();
	}

	public static IOSDriver<MobileElement> funcGetNativeIOSdriver(String configFileName)
			throws GeneralSecurityException, IOException, ParseException, InterruptedException {
		VerifyKey obj = new VerifyKey();
		if (obj.verifyexpiry()) {
			bnative = true;
			PropertyDriver propertyDriver = new PropertyDriver();
			propertyDriver.setPropFilePath("src/main/resources/Mobile/Properties/" + configFileName + ".properties");
			Map<String, String> iOSProperties = propertyDriver.readProp();
			iOSProperties.forEach((k, v) -> {
				if (k.equalsIgnoreCase("iOS.App_Path"))
					getIOSSettings().setAppPath(v);
				if (k.equalsIgnoreCase("iOS.bundleId"))
					getIOSSettings().setAppBundleId(v);
				if (k.equalsIgnoreCase("iOS.DeviceName"))
					getIOSSettings().setDeviceName(v);
				if (k.equalsIgnoreCase("iOS.DeviceID"))
					getIOSSettings().setDeviceUDID(v);
				if (k.equalsIgnoreCase("iOS.Platform_Version"))
					getIOSSettings().setIOSVersion(v);
			});

			getIOSSettings().setBrowserName("");
			AppiumSettings.startAppiumServer(iOSProperties);
			IOSUtil iOSDriver = new IOSUtil(iOSSettings.getHuburl().toString(), iOSSettings);
			setIOSNativeDriver(iOSDriver.getIOSNativeDriver());
			iOSDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		return getIOSNativeDriver();

	}

	public static IOSDriver<WebElement> funcGetWebIOSdriver(String configFileName)
			throws GeneralSecurityException, IOException, ParseException, InterruptedException {
		VerifyKey obj = new VerifyKey();
		if (obj.verifyexpiry()) {
			PropertyDriver propertyDriver = new PropertyDriver();
			propertyDriver.setPropFilePath("src/main/resources/Mobile/Properties/" + configFileName + ".properties");
			Map<String, String> iOSProperties = propertyDriver.readProp();
			iOSProperties.forEach((k, v) -> {

				if (k.equalsIgnoreCase("iOS.browserName"))
					getIOSSettings().setBrowserName(v);
				if (k.equalsIgnoreCase("iOS.DeviceName"))
					getIOSSettings().setDeviceName(v);
				if (k.equalsIgnoreCase("iOS.DeviceID"))
					getIOSSettings().setDeviceUDID(v);
				if (k.equalsIgnoreCase("iOS.Platform_Version"))
					getIOSSettings().setIOSVersion(v);
			});
			getIOSSettings().setAppPath("");
			getIOSSettings().setAppBundleId("");
			AppiumSettings.startAppiumServer(iOSProperties);
			IOSUtil iOSDriver = new IOSUtil(iOSSettings.getHuburl().toString(), iOSSettings);
			setIOSWebDriver(iOSDriver.getIOSWebDriver());
			iOSDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
		return getIOSWebDriver();

	}

	public static void funcplpCloudyWebAndroiddriver(String configFileName) {
		try {
			VerifyKey obj = new VerifyKey();
			if (obj.verifyexpiry()) {
				getPcloudyDeviceSettings().getConnectionPcloudyAndroidWebTh(configFileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error while reading pCloudy IOS web Apps Config file" + "Exception :" + e);
			Thread.currentThread().interrupt();
		}
	}

	public static AndroidDriver<WebElement> getAndroidWebDriver() {
		return androidWebDriver;
	}

	public static AndroidDriver<MobileElement> getAndroidNativeDriver() {
		return androidNativeDriver;
	}

	public static void setAndroidWebDriver(AndroidDriver<WebElement> currandroidDriver) {
		androidWebDriver = currandroidDriver;
	}

	public static void setAndroidNativeDriver(AndroidDriver<MobileElement> currandroidDriver) {
		androidNativeDriver = currandroidDriver;
	}

	public static void setIOSWebDriver(IOSDriver<WebElement> currIOSDriver) {
		iosWebDriver = currIOSDriver;
	}

	public static void setIOSNativeDriver(IOSDriver<MobileElement> currIOSDriver) {
		iosNativeDriver = currIOSDriver;
	}

	public static IOSDriver<MobileElement> getIOSNativeDriver() {
		return iosNativeDriver;

	}

	public static IOSDriver<WebElement> getIOSWebDriver() {
		return iosWebDriver;

	}

	public static AndroidDeviceSettings getAndroidSettings() {
		if (androidSettings == null)
			androidSettings = new AndroidDeviceSettings();
		return androidSettings;
	}

	public static PcloudyDeviceSettings getPcloudyDeviceSettings() {
		if (pcloudyDeviceSettings == null)
			pcloudyDeviceSettings = new PcloudyDeviceSettings();
		return pcloudyDeviceSettings;
	}

	/* Added for SauceLab */
	public static SauceDeviceSettings getSauceDeviceSettings() {
		if (sauceDeviceSettings == null)
			sauceDeviceSettings = new SauceDeviceSettings();
		return sauceDeviceSettings;
	}

	public static void launchURL(String url) throws Exception {
		try {
			if (getCurrentDriver() == null) {
				throw new Exception("CurrentDriver is Null");
			}
			if (getCurrentDriver() != null) {
				MobileDriver<WebElement> driver = getCurrentDriver();
				if (driver != null) {
					driver.get(url);
					LOGGER.info("Application launched successfully");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occurred while launching URL" + "Exception :" + e);
			Assert.fail("Error occurred while launching URL" + "Exception :" + e);
		}
	}

	public static void closeDriver() throws Exception {
		try {

			if (getCurrentDriver() == null) {
				throw new Exception("CurrentDriver is Null");
			}
			if (getCurrentDriver() != null) {
				MobileDriver<MobileElement> driver = getCurrentDriver();
				if (driver != null) {
					driver.closeApp();
					AppiumSettings.stopAppiumServer();
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			LOGGER.error("Error occurred while closing browser" + "Exception :" + e);
			Assert.fail("Error occurred while closing browser" + "Exception :" + e);
		}
	}

	public static MobileDriver getCurrentDriver() {
		if (androidWebDriver != null) {
			return  androidWebDriver;
		} else if (androidNativeDriver != null) {
			return  androidNativeDriver;
		} else if (iosWebDriver!= null) {
			return  iosWebDriver;
		} else if (iosNativeDriver != null) {
			return  iosNativeDriver;
		} else if (androidWebDriver == null) {
			AndroidDriver<MobileElement> thandroidDriver = PcloudyDeviceSettings.thDriver.get();
			return thandroidDriver;
		}
		return null;
	}

	private static IOSDeviceSettings getIOSSettings() {
		if (iOSSettings == null)
			iOSSettings = new IOSDeviceSettings();
		return iOSSettings;
	}

	/* Added for SauceLab */
	public static AndroidDriver<MobileElement> funcGetSauceNativeAndroiddriver(String configFileName)
			throws GeneralSecurityException, IOException, ParseException, InterruptedException {
		Properties mobConfigReader = new Properties();
		VerifyKey obj = new VerifyKey();
//		if (obj.verifyexpiry()) {
//			try (FileReader reader = new FileReader(
//					"src/main/resources/Mobile/SauceLabs/Properties/" + configFileName + ".properties")) {
//				mobConfigReader.load(reader);
//				androidWebDriver = getSauceDeviceSettings().getConnectionSauceAndroidNativeApp(mobConfigReader);
//			} catch (Exception e) {
//				e.printStackTrace();
//				LOGGER.error("Error while reading sauce Android native Apps Config file" + "Exception :" + e);
//			}
//		}
		return androidNativeDriver;
	}

	public static AndroidDriver<WebElement> funcGetSauceWebAndroiddriver(String configFileName)
			throws GeneralSecurityException, IOException, ParseException, InterruptedException {
		Properties mobConfigReader = new Properties();
		VerifyKey obj = new VerifyKey();
//		if (obj.verifyexpiry()) {
//			try (FileReader reader = new FileReader(
//					"src/main/resources/Mobile/SauceLabs/Properties/" + configFileName + ".properties")) {
//
//				mobConfigReader.load(reader);
//				androidWebDriver = getSauceDeviceSettings().getConnectionSauceAndroidWeb(mobConfigReader);
//			} catch (Exception e) {
//				e.printStackTrace();
//				LOGGER.error("Error while reading sauce Android web Apps Config file" + "Exception :" + e);
//			}
//		}
		return androidWebDriver;
	}

	public static IOSDriver<MobileElement> funcGetSauceNativeIOSdriver(String configFileName)
			throws GeneralSecurityException, IOException, ParseException, InterruptedException {
		Properties mobConfigReader = new Properties();
		VerifyKey obj = new VerifyKey();
//		if (obj.verifyexpiry()) {
//			try (FileReader reader = new FileReader(
//					"src/main/resources/Mobile/SauceLabs/Properties/" + configFileName + ".properties")) {
//				mobConfigReader.load(reader);
//				iosWebDriver = getSauceDeviceSettings().getConnectionSauceIOSNativeApp(mobConfigReader);
//			} catch (Exception e) {
//				e.printStackTrace();
//				LOGGER.error("Error while reading sauce IOS native Apps Config file" + "Exception :" + e);
//			}
//		}
		return getIOSNativeDriver();
	}

	public static IOSDriver<MobileElement> funcGetSauceWebIOSdriver(String configFileName)
			throws GeneralSecurityException, IOException, ParseException, InterruptedException {
		Properties mobConfigReader = new Properties();
		VerifyKey obj = new VerifyKey();
//		if (obj.verifyexpiry()) {
//			try (FileReader reader = new FileReader(
//					"src/main/resources/Mobile/SauceLabs/Properties/" + configFileName + ".properties")) {
//				mobConfigReader.load(reader);
//				iosWebDriver = getSauceDeviceSettings().getConnectionSauceIOSWeb(mobConfigReader);
//			} catch (Exception e) {
//				e.printStackTrace();
//				LOGGER.error("Error while reading sauce IOS web Apps Config file" + "Exception :" + e);
//			}
//		}
		return getIOSNativeDriver();
	}

}
