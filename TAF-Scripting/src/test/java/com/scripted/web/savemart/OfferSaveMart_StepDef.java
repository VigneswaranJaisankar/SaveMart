package com.scripted.web.savemart;

import java.awt.AWTException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
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
import com.scripted.web.WebHandlers;
import com.scripted.web.WebWaitHelper;
import com.scripted.web.pages.LoginPageSM;
import com.scripted.web.pages.OfferPageSM;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class OfferSaveMart_StepDef {

	WebDriver driver;
	PropertyDriver propDriver = new PropertyDriver();
	OfferPageSM objOfferPageSM;
	public static Logger LOGGER = LogManager.getLogger(Login_SM_StepDef.class);
	String saveMartUrl;
	UtilityClass objUtility = new UtilityClass();

	@Before("@Savemartoffer")
	public void beforeStep(Scenario scenario) throws IOException {
		BrowserDriver.getCuPalDriver(); //
		BrowserDriver.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/config.properties");
		saveMartUrl = PropertyDriver.readProp("SAVEMART_URL");
	}

	@After("@Savemartoffer")
	public void tearDown(Scenario scenario) throws ParseException {
		BrowserDriver.closeBrowser();
	}

	@Given("User launch the browser and enter the url for login")
	public void user_launch_the_browser_and_enter_the_savemart_url()
			throws IOException, AWTException, InterruptedException {

		driver = BrowserDriver.getDriver();
		AllureListener.setDriver(BrowserDriver.getDriver());
		objOfferPageSM = PageFactory.initElements(BrowserDriver.getDriver(), OfferPageSM.class);
		BrowserDriver.launchWebURL(saveMartUrl);
		WebWaitHelper.waitforpageload();
		WebWaitHelper.waitforelement(1000);
		FileReader reader = new FileReader("src/main/resources/Web/Properties/Browser.properties");
		Properties pf = new Properties();
		pf.load(reader);
		if (pf.getProperty("browserName").contains("edge")) {
			objUtility.browserZoomOut();
		}

	}
	
	@Then("Verify available Category and check available offer count")
	public void verify_available_offers() throws InterruptedException, ParseException {	
		objOfferPageSM.allCategory();
		
	}
	
	@And("Verify available offer count and expiry date")
	public void verify_available_offers_expirydate() throws InterruptedException, ParseException {	
		objOfferPageSM.verifyAvailable_offers_foreachcategory();
	}
	
}
