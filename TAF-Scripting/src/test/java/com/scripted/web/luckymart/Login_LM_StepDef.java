package com.scripted.web.luckymart;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.scripted.Allure.AllureListener;
import com.scripted.dataload.PropertyDriver;
import com.scripted.utils.UtilityClass;
import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;

import java.lang.reflect.Field;

import com.scripted.web.WebWaitHelper;
import com.scripted.web.pages.LoginPageLM;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import cucumber.api.Result;
import io.cucumber.core.api.Scenario;
import io.cucumber.core.logging.LoggerFactory;
import io.cucumber.java.After;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Login_LM_StepDef {

	WebDriver driver;
	PropertyDriver propDriver = new PropertyDriver();
	LoginPageLM objLoginPageLM;
	HashMap<String, String> reportdetails = new HashMap<>();
	UtilityClass objUtility = new UtilityClass();
	String starttime;
	public static Logger LOGGER = LogManager.getLogger(Login_LM_StepDef.class);
	LocalDateTime start;

	String url;

	String scenarioName1;

	String startat;

	String browser;

	String imgpath;
	
	FileReader reader;
	String urlLucky="";

	@Before("@Luckysupermarketlogin")
	public void beforestep(Scenario scenario) throws IOException {

		BrowserDriver.getCuPalDriver();
		BrowserDriver.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/config.properties");
		urlLucky = PropertyDriver.readProp("LUCKY_URL_LOGIN");
		reader = new FileReader("src/main/resources/Web/Properties/Browser.properties");
		Properties pf = new Properties();
		pf.load(reader);

		String scenarioName = scenario.getName();

		String[] arrayScenarioName = scenarioName.split("--");

		scenarioName1 = arrayScenarioName[0];

		
		startat = objUtility.currentDate();

		starttime = objUtility.currentTime();

		url = urlLucky;

		browser = pf.getProperty("browserName");
	}

	@Given("User launch the browser and enter lucky url")
	public void user_launch_the_browser_and_enter_lucky_url() throws AWTException, IOException {

		driver = BrowserDriver.getDriver();
		AllureListener.setDriver(BrowserDriver.getDriver());
		objLoginPageLM = PageFactory.initElements(BrowserDriver.getDriver(), LoginPageLM.class);
		BrowserDriver.launchWebURL(urlLucky);
		WebWaitHelper.waitforpageload();
		FileReader reader = new FileReader("src/main/resources/Web/Properties/Browser.properties");
		Properties pf = new Properties();
		pf.load(reader);
		if (pf.getProperty("browserName").contains("edge")) {
			objUtility.browserZoomOut();
		}
	}

	@When("User clicks the Log In button")
	public void user_clicks_the_Log_In_button() throws InterruptedException {
         Thread.sleep(2000);    
		objLoginPageLM.clickLogin();
	}

	@When("User enter username")
	public void user_enter_the_username() throws InterruptedException {

		String userName = PropertyDriver.readProp("LuckyUserName_LOGIN");
		WebWaitHelper.waitforelement(4000);
	    objLoginPageLM.EnterUserName(userName);
		String value = objLoginPageLM.UserNameValue();
		Assert.assertEquals(value, "testbrown@testing.net", "Invalid username");

	}

	@Then("User enter password")
	public void user_enter_the_password() throws InterruptedException {

		String password = PropertyDriver.readProp("LuckyPassword_LOGIN");
		objLoginPageLM.EnterPassword(password);
		String value = objLoginPageLM.UserPasswordValue();
		Assert.assertEquals(value, "Test123$", "Invalid password");

	}

	@And("User clicks login button to navigates homepage")
	public void user_clicks_login_button_to_navigates_homepage() throws InterruptedException {
		objLoginPageLM.login();
		WebWaitHelper.waitforpageload();
		WebWaitHelper.waitforelement(1000);
	
		if(objLoginPageLM.validaterewards()) {
			Assert.assertTrue(objLoginPageLM.validaterewards());
			LOGGER.info("Login to LuckyMart application successfully");		
		}else {
			if(objLoginPageLM.invalidCredentialsDisplay()) {
				Assert.assertTrue(false, "Login failed due to invalid credentials");
				LOGGER.info("Login failed due to invalid credentials");
			} 
		}
	}

	@And("User click on MyRewards button and verify available rewards")
	public void user_click_on_MyRewards_button() throws Exception {
		objLoginPageLM.click_on_MyRewards_button();
		if (objLoginPageLM.availablerewardsCount() > 0) {
			Assert.assertTrue(true, "Myrewards are available for the selection");
		} else {
			Assert.assertTrue(false, "Myrewards are not available for the selection");
		}
		imgpath = objUtility.takeSnapShot(driver);

	}

	@Then("User click on signout button")
	public void user_click_on_signout_button() throws InterruptedException {

		objLoginPageLM.signOut();

	}

	@After("@Luckysupermarketlogin")
	public void afterstep(Scenario scenario) throws ParseException {

		String error = "";

		BrowserDriver.closeBrowser();
		if (!scenario.getStatus().isOk(true)) {

			error = objUtility.logError(scenario);
		}

		

		String endtime = objUtility.currentTime();

		String duration = objUtility.duration(starttime, endtime);

		reportdetails.put("TestcaseName", scenarioName1);

		reportdetails.put("browser", browser);

		reportdetails.put("url", url);

		reportdetails.put("status", scenario.getStatus().toString());

		reportdetails.put("Startat", startat);

		reportdetails.put("duration", duration);

		reportdetails.put("image", imgpath);

		reportdetails.put("reason", error);

		WebHandlers.writeExcelSavemart("C:\\Users\\V987920\\eclipse-workspace\\Savemart-main\\TAF-Scripting\\ConsolidatedReport\\TestDataSavemart.xlsx", "login_LM",reportdetails);

	}
}
