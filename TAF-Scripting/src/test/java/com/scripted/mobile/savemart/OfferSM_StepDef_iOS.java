package com.scripted.mobile.savemart;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.scripted.dataload.PropertyDriver;
import com.scripted.mobile.MobileDriverSettings;
import com.scripted.mobile.pages.OfferPageSM_Android;
import com.scripted.mobile.pages.OfferPageSM_iOS;
import com.scripted.utils.UtilityClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class OfferSM_StepDef_iOS {
	PropertyDriver propDriver = new PropertyDriver();
	OfferPageSM_iOS objOfferPageSM_iOS;
	public static Logger LOGGER = LogManager.getLogger(OfferSM_StepDef_iOS.class);
	UtilityClass objUtility = new UtilityClass();
	public static IOSDriver<WebElement> iOSDriver = null;
	String saveMartUrl;

	@Before("@SavemartofferMobileiOS")
	public void beforeStep(Scenario scenario) throws IOException, GeneralSecurityException, ParseException, InterruptedException {
		/*Refer to this path for mobile settings Give DeviceManafacturer,Device version,browsr name
		 * It will automatically get the available device based on the manufacturer from Pcloudy
		 * path will be src/main/resources/Mobile/Properties/" + pcloudyWebConfig + ".properties
		 */	
		iOSDriver = MobileDriverSettings.funcGetpCloudyWebIOSdriver("pcloudyIOSWeb");
		System.out.println("PcloudyWeb started");
		objOfferPageSM_iOS = new OfferPageSM_iOS(iOSDriver);
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/config.properties");
		saveMartUrl = PropertyDriver.readProp("SAVEMART_URL");
	}

	@After("@SavemartofferMobileiOS")
	public void tearDown(Scenario scenario) throws ParseException {
		iOSDriver.quit();
		System.out.println("PcloudyWeb completed");
	}

	@Given("User launch the browser and launch the application")
	public void user_launch_the_browser_and_enter_the_savemart_url_iOS()
			throws Exception {
		MobileDriverSettings.launchURL(saveMartUrl);
	}
	
	@Then("Verify available Category and check available offers in iOS device")
	public void verify_available_offers_SM_iOS() throws InterruptedException, ParseException {	
		objOfferPageSM_iOS.allCategory();
		
	}
	
	@And("Verify available offer count and verify expiry date in safari browser")
	public void verify_available_offers_expirydate_SMiOS() throws InterruptedException, ParseException {	
		objOfferPageSM_iOS.verifyAvailable_offers_foreachcategory();
	}
	

}
