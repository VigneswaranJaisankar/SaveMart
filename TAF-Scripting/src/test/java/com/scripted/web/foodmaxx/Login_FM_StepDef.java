package com.scripted.web.foodmaxx;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.web.server.WebHandler;
import org.testng.Assert;

import com.scripted.Allure.AllureListener;
import com.scripted.dataload.PropertyDriver;
import com.scripted.utils.UtilityClass;
import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;
import com.scripted.web.WebWaitHelper;
import com.scripted.web.luckymart.Login_LM_StepDef;
import com.scripted.web.pages.LoginPageFM;

import cucumber.api.Result;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Login_FM_StepDef {

	WebDriver driver;
	PropertyDriver propDriver = new PropertyDriver();
	LoginPageFM objLoginPageFM;
	HashMap<String, String> reportdetails = new HashMap<>();
	UtilityClass objUtility = new UtilityClass();
	String starttime;
	public static Logger LOGGER = LogManager.getLogger(Login_FM_StepDef.class);
	LocalDateTime start;

	String url;

	String scenarioName1;

	String startat;

	String browser;

	String imgpath;
	FileReader reader;
	String urlFood = "";

	@Before("@Foodmaxlogin")
	public void beforestep(Scenario scenario) throws IOException {

		BrowserDriver.getCuPalDriver();
		BrowserDriver.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/config.properties");
		urlFood = PropertyDriver.readProp("FOODMAXX_URL_LOGIN");
		reader = new FileReader("src/main/resources/Web/Properties/Browser.properties");
		Properties pf = new Properties();
		pf.load(reader);

		String scenarioName = scenario.getName();

		String[] arrayScenarioName = scenarioName.split("--");

		scenarioName1 = arrayScenarioName[0];

		startat = objUtility.currentDate();

		starttime = objUtility.currentTime();

		url = urlFood;

		browser = pf.getProperty("browserName");
	}

	@Given("User launch the browser and enter the foodmaxx url")
	public void user_launch_the_browser_and_enter_the_foodmaxx_url() throws IOException, AWTException {

		driver = BrowserDriver.getDriver();
		AllureListener.setDriver(BrowserDriver.getDriver());
		objLoginPageFM = PageFactory.initElements(BrowserDriver.getDriver(), LoginPageFM.class);
		BrowserDriver.launchWebURL(urlFood);
		FileReader reader = new FileReader("src/main/resources/Web/Properties/Browser.properties");
		Properties pf = new Properties();
		pf.load(reader);
		if (pf.getProperty("browserName").contains("edge")) {
			objUtility.browserZoomOut();

		}
	}

	@When("User click on the Log In button")
	public void user_click_on_the_Log_In_button_foodmax() {

		objLoginPageFM.clickLogin();
	}

	@And("User enter the username")
	public void user_enter_the_username_foodmax() throws InterruptedException {

		String userName = PropertyDriver.readProp("FoodmaxxUserName_LOGIN");
		WebWaitHelper.waitforelement(1000);
		objLoginPageFM.enterUserName(userName);
		String value = objLoginPageFM.UserNameValue();
		Assert.assertEquals(value, "testbrown@testing.net", "Invalid username");

	}

	@Then("User enter the password")
	public void user_enter_the_password_foodmax() throws InterruptedException {

		String password = PropertyDriver.readProp("FoodmaxxPassword_LOGIN");
		objLoginPageFM.enterPassword(password);
		String value = objLoginPageFM.UserPasswordValue();
		Assert.assertEquals(value, "Test123$", "Invalid password");

	}

	@Then("User click on login")
	public void login() throws InterruptedException {
		objLoginPageFM.login();
		
//		if(objLoginPageFM.returnProfile()) {
//			Assert.assertTrue(objLoginPageFM.validatecoupons());
//			LOGGER.info("Login to LuckyMart application successfully");		
//		}else {
//			if(objLoginPageFM.invalidCredentials()) {
//				Assert.assertTrue(false, "Login failed due to invalid credentials");
//				LOGGER.info("Login failed due to invalid credentials");
//			} 
//		}
		
	}

	@And("User verify MyCoupons option in foodmaxx")
	public void user_verify_MyRewards_option_in_foodmaxx() throws Exception {
		WebWaitHelper.waitforpageload();
		WebWaitHelper.waitforelement(2000);
		objLoginPageFM.profile();
		WebWaitHelper.waitforelement(2000);
		Boolean validateCoupoun = objLoginPageFM.validatecoupons();
		Assert.assertTrue(validateCoupoun, "Login failed to redirect to Homepage");
		imgpath = objUtility.takeSnapShot(driver);

	}

	@And("the User click on profile button")
	public void profile_foodmax() throws InterruptedException {
		objLoginPageFM.profile();
	}

	@And("User click on MyCoupons button and verify available rewards")
	public void user_click_on_MyRewards_button_and_verify_available_rewards_foodmart() throws InterruptedException {
		objLoginPageFM.click_on_MyCoupons_button();

	}

	@Then("User clicks on the signout button for foodmax")
	public void user_clicks_on_the_signout_button_foodmx() throws InterruptedException {
		WebWaitHelper.waitforelement(1000);
		objLoginPageFM.signOut();
		if (objLoginPageFM.clickLoginDisplayed()) {
			Assert.assertTrue(true, "LogOut Successfully");
		}

	}

	@After("@Foodmaxlogin")
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

		WebHandlers.writeExcelSavemart(
				"C:\\Users\\V987920\\eclipse-workspace\\Savemart-main\\TAF-Scripting\\ConsolidatedReport\\TestDataSavemart.xlsx",
				"login_FM", reportdetails);

	}

}
