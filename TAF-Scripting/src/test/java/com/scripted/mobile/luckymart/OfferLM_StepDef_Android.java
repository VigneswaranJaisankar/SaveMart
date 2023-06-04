package com.scripted.mobile.luckymart;

import java.awt.AWTException;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import com.scripted.mobile.MobileDriverSettings;
import com.scripted.Allure.AllureListener;
import com.scripted.dataload.PropertyDriver;
import com.scripted.mobile.pages.OfferPageSM_Android;
import com.scripted.utils.UtilityClass;
import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;
import com.scripted.web.WebWaitHelper;
import com.scripted.web.pages.LoginPageSM;
import com.scripted.web.pages.OfferPageSM;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class OfferLM_StepDef_Android {

	
	PropertyDriver propDriver = new PropertyDriver();
	OfferPageSM_Android objOfferPageSMMobile;
	public static Logger LOGGER = LogManager.getLogger(OfferLM_StepDef_Android.class);
	UtilityClass objUtility = new UtilityClass();
	public static AndroidDriver<WebElement> androidDriver = null;
	String luckyUrl;

	@Before("@LucymartofferMobileandroid")
	public void beforeStep(Scenario scenario) throws IOException, GeneralSecurityException, ParseException, InterruptedException {
		/*Refer to this path for mobile settings Give DeviceManafacturer,Device version,browsr name
		 * It will automatically get the available device based on the manufacturer from Pcloudy
		 * path will be src/main/resources/Mobile/Properties/" + pcloudyWebConfig + ".properties
		 */	
		androidDriver = MobileDriverSettings.funcGetpCloudyWebAndroiddriver("pcloudyWebConfig");
		System.out.println("PcloudyWeb started");
		objOfferPageSMMobile = new OfferPageSM_Android(androidDriver);
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/config.properties");
		luckyUrl = PropertyDriver.readProp("LUCKY_URL");
	}

	@After("@LucymartofferMobileandroid")
	public void tearDown(Scenario scenario) throws ParseException {
		androidDriver.quit();
		System.out.println("PcloudyWeb completed");
	}

	@Given("User launch the browser and enter the url in Android")
	public void user_launch_the_browser_and_Android__luckyMart()
			throws Exception {
		MobileDriverSettings.launchURL(luckyUrl);
	}
	
	@Then("Verify available Category and check available offer count in android device")
	public void verify_available_offers_Andorid_luckyMart() throws InterruptedException, ParseException {	
		objOfferPageSMMobile.allCategory();
		
	}
	
	@And("Verify available offer count and expiry date in android device")
	public void verify_available_offers_expirydate_Android__luckyMart() throws InterruptedException, ParseException {	
		objOfferPageSMMobile.verifyAvailable_offers_foreachcategory();
	}
	
}
