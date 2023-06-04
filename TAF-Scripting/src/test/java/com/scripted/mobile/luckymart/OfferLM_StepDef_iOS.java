package com.scripted.mobile.luckymart;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.scripted.dataload.PropertyDriver;
import com.scripted.mobile.MobileDriverSettings;
import com.scripted.mobile.pages.OfferPageLM_iOS;
import com.scripted.mobile.pages.OfferPageSM_Android;
import com.scripted.utils.UtilityClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class OfferLM_StepDef_iOS {
	

	
	PropertyDriver propDriver = new PropertyDriver();
	OfferPageLM_iOS objOfferPageLM_iOS;
	public static Logger LOGGER = LogManager.getLogger(OfferLM_StepDef_Android.class);
	UtilityClass objUtility = new UtilityClass();
	public static IOSDriver<WebElement> iOSDriver = null;
	String luckyUrl;

	@Before("@LucymartofferMobileiOS")
	public void beforeStep(Scenario scenario) throws IOException, GeneralSecurityException, ParseException, InterruptedException {
		/*Refer to this path for mobile settings Give DeviceManafacturer,Device version,browsr name
		 * It will automatically get the available device based on the manufacturer from Pcloudy
		 * path will be src/main/resources/Mobile/Properties/" + pcloudyWebConfig + ".properties
		 */	
		iOSDriver = MobileDriverSettings.funcGetpCloudyWebIOSdriver("pcloudyIOSWeb");
		System.out.println("PcloudyWeb started");
		objOfferPageLM_iOS = new OfferPageLM_iOS(iOSDriver);
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/config.properties");
		luckyUrl = PropertyDriver.readProp("LUCKY_URL");
	}

	@After("@LucymartofferMobileiOS")
	public void tearDown(Scenario scenario) throws ParseException {
		iOSDriver.quit();
		System.out.println("PcloudyWeb completed");
	}

	@Given("User launch the browser and enter the url in iOS device")
	public void user_launch_the_browser_and_iOS__luckyMart()
			throws Exception {
		MobileDriverSettings.launchURL(luckyUrl);
	}
	
	@Then("Verify available Category and check available offer count in iOS device")
	public void verify_available_offers_iOS_luckyMart() throws InterruptedException, ParseException {	
		objOfferPageLM_iOS.allCategory();
		
	}
	
	@And("Verify available offer count and expiry date in iOS device")
	public void verify_available_offers_expirydate_Android__luckyMart() throws InterruptedException, ParseException {	
		objOfferPageLM_iOS.verifyAvailable_offers_foreachcategory();
	}
	

}
