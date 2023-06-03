package com.scripted.web.luckymart;

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
import com.scripted.web.pages.OfferPageLM;
import com.scripted.web.pages.OfferPageSM;
import com.scripted.web.savemart.Login_SM_StepDef;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class OfferLucky_StepDef {
	
	WebDriver driver;
	PropertyDriver propDriver = new PropertyDriver();
	OfferPageLM objOfferPageLM;
	public static Logger LOGGER = LogManager.getLogger(OfferLucky_StepDef.class);
	String luckyMartUrl;
	UtilityClass objUtility = new UtilityClass();

	@Before("@Luckymartoffer")
	public void beforeStep(Scenario scenario) throws IOException {
		BrowserDriver.getCuPalDriver(); //
		BrowserDriver.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/config.properties");
		luckyMartUrl = PropertyDriver.readProp("LUCKY_URL");
	}

	@After("@Luckymartoffer")
	public void tearDown(Scenario scenario) throws ParseException {
		BrowserDriver.closeBrowser();
	}

	@Given("User launch the browser and enter the url for login")
	public void user_launch_the_browser_and_enter_the_lucysupermart_url()
			throws IOException, AWTException, InterruptedException {

		driver = BrowserDriver.getDriver();
		AllureListener.setDriver(BrowserDriver.getDriver());
		objOfferPageLM = PageFactory.initElements(BrowserDriver.getDriver(), OfferPageLM.class);
		BrowserDriver.launchWebURL(luckyMartUrl);
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
	public void verify_available_offers_lucky() throws InterruptedException, ParseException {	
		objOfferPageLM.allCategory();
		
	}
	
	@And("Verify available offer count and expiry date")
	public void verify_available_offers_expirydate_lucky() throws InterruptedException, ParseException {	
		objOfferPageLM.verifyAvailable_offers_foreachcategory();
	}

}
