package com.scripted.web.foodmaxx;
import java.awt.AWTException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.scripted.Allure.AllureListener;
import com.scripted.dataload.PropertyDriver;
import com.scripted.utils.UtilityClass;
import com.scripted.web.BrowserDriver;
import com.scripted.web.WebWaitHelper;
import com.scripted.web.pages.OfferPageFM;
import com.scripted.web.pages.OfferPageLM;
import com.scripted.web.pages.OfferPageSM;
import com.scripted.web.savemart.Login_SM_StepDef;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class OfferFoodmaxx_StepDef {
	
		
		WebDriver driver;
		PropertyDriver propDriver = new PropertyDriver();
		OfferPageFM objOfferPageFM;
		public static Logger LOGGER = LogManager.getLogger(OfferFoodmaxx_StepDef.class);
		String foodmaxxUrl;
		UtilityClass objUtility = new UtilityClass();

		@Before("@Foodmaxxoffer")
		public void beforeStep(Scenario scenario) throws IOException {
			BrowserDriver.getCuPalDriver(); //
			BrowserDriver.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/config.properties");
			foodmaxxUrl = PropertyDriver.readProp("FOODMAXX_URL");
		}

		@After("@Foodmaxxoffer")
		public void tearDown(Scenario scenario) throws ParseException {
			BrowserDriver.closeBrowser();
		}

		@Given("User launch the browser and enter the url")
		public void user_launch_the_browser_and_enter_the_foodmaxx_url()
				throws IOException, AWTException, InterruptedException {

			driver = BrowserDriver.getDriver();
			AllureListener.setDriver(BrowserDriver.getDriver());
			objOfferPageFM = PageFactory.initElements(BrowserDriver.getDriver(), OfferPageFM.class);
			BrowserDriver.launchWebURL(foodmaxxUrl);
			WebWaitHelper.waitforpageload();
			WebWaitHelper.waitforelement(1000);
			FileReader reader = new FileReader("src/main/resources/Web/Properties/Browser.properties");
			Properties pf = new Properties();
			pf.load(reader);
			if (pf.getProperty("browserName").contains("edge")) {
				objUtility.browserZoomOut();
			}

		}
		
		@Then("Verify available Category in app and check available offer count")
		public void verify_available_offers_foodmax() throws InterruptedException, ParseException {	
			objOfferPageFM.allCategory();
			
		}
		
		@And("Verify available offer count and expiry date for the offers")
		public void verify_available_offers_expirydate_foodmax() throws InterruptedException, ParseException {	
			objOfferPageFM.verifyAvailable_offers_foreachcategory();
		}

	}
