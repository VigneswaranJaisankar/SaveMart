package com.scripted.mobile.foodmaxx;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.scripted.dataload.PropertyDriver;
import com.scripted.mobile.MobileDriverSettings;
import com.scripted.mobile.pages.OfferPageFM_Android;
import com.scripted.mobile.pages.OfferPageSM_Android;
import com.scripted.mobile.savemart.OfferSM_StepDef_Android;
import com.scripted.utils.UtilityClass;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class OfferFM_StepDef_Android {
	

	PropertyDriver propDriver = new PropertyDriver();
	OfferPageFM_Android objOfferPageFM_Android;
	public static Logger LOGGER = LogManager.getLogger(OfferFM_StepDef_Android.class);
	UtilityClass objUtility = new UtilityClass();
	public static AndroidDriver<WebElement> androidDriver = null;
	String foodMaxxUrl;

	@Before("@FoodmaxofferMobileandroid")
	public void beforeStep(Scenario scenario) throws IOException, GeneralSecurityException, ParseException, InterruptedException {
		/*Refer to this path for mobile settings Give DeviceManafacturer,Device version,browsr name
		 * It will automatically get the available device based on the manufacturer from Pcloudy
		 * path will be src/main/resources/Mobile/Properties/" + pcloudyWebConfig + ".properties
		 */	
		androidDriver = MobileDriverSettings.funcGetpCloudyWebAndroiddriver("pcloudyWebConfig");
		System.out.println("PcloudyWeb started");
		objOfferPageFM_Android = new OfferPageFM_Android(androidDriver);
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/config.properties");
		foodMaxxUrl = PropertyDriver.readProp("FOODMAXX_URL");
	}

	@After("@FoodmaxofferMobileandroid")
	public void tearDown(Scenario scenario) throws ParseException {
		androidDriver.quit();
		System.out.println("PcloudyWeb completed");
	}

	@Given("User launch the browser and enter the url in android device")
	public void user_launch_the_browser_and_foodmax_Android()
			throws Exception {
		MobileDriverSettings.launchURL(foodMaxxUrl);
	}
	
	@Then("Verify available Category and check available offer count in android device")
	public void verify_available_offers__foodmax_Android() throws InterruptedException, ParseException {		
		objOfferPageFM_Android.allCategory();
		
	}
	
	@And("Verify available offer count and expiry date in android device")
	public void verify_available_offers_expirydate_foodmax_Android() throws InterruptedException, ParseException {	
		objOfferPageFM_Android.verifyAvailable_offers_foreachcategory();
	}

}
