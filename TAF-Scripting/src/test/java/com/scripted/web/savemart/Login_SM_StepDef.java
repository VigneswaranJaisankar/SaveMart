package com.scripted.web.savemart;

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
import org.testng.Assert;

import com.scripted.Allure.AllureListener;
import com.scripted.dataload.PropertyDriver;
import com.scripted.utils.UtilityClass;
import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;
import com.scripted.web.WebWaitHelper;
import com.scripted.web.luckymart.Login_LM_StepDef;
import com.scripted.web.pages.LoginPageSM;

import cucumber.api.Result;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileReader;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Login_SM_StepDef {

	WebDriver driver; 
	  PropertyDriver propDriver = new PropertyDriver();
	  LoginPageSM objLoginPageSM;
	  public static Logger LOGGER = LogManager.getLogger(Login_SM_StepDef.class);
	  HashMap<String, String> reportdetails = new HashMap<>();
	  UtilityClass objUtility = new UtilityClass();
		String starttime;

		LocalDateTime start;

		String url;

		String scenarioName1;

		String startat;

		String browser;

		String imgpath;
		
		String saveMartUrl;
	  
	  @Before("@Savemartlogin") 
	  public void beforeStep(Scenario scenario) throws IOException {
	  BrowserDriver.getCuPalDriver(); //
	  BrowserDriver.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/config.properties");
		saveMartUrl = PropertyDriver.readProp("SAVEMART_URL_LOGIN");
		FileReader reader = new FileReader("src/main/resources/Web/Properties/Browser.properties");
		Properties pf = new Properties();
		pf.load(reader);

		String scenarioName = scenario.getName();

		String[] arrayScenarioName = scenarioName.split("--");

		scenarioName1 = arrayScenarioName[0];
		
		startat = objUtility.currentDate();

		starttime = objUtility.currentTime();

		url = saveMartUrl;

		browser = pf.getProperty("browserName");
	  
	  }
	  
	  @Given("User launch the browser and enter the savemart url") 
	  public void user_launch_the_browser_and_enter_the_savemart_url() throws IOException, AWTException, InterruptedException {
	  
	  driver = BrowserDriver.getDriver();
	  AllureListener.setDriver(BrowserDriver.getDriver());
	  objLoginPageSM=PageFactory.initElements(BrowserDriver.getDriver(),LoginPageSM.class); 
	  BrowserDriver.launchWebURL(saveMartUrl);
	  WebWaitHelper.waitforpageload();
	  WebWaitHelper.waitforelement(1000);
	  FileReader reader = new FileReader("src/main/resources/Web/Properties/Browser.properties");
		Properties pf = new Properties();
		pf.load(reader);
		if(pf.getProperty("browserName").contains("edge")) {
			objUtility.browserZoomOut();		
		}
	  
	  }
	  
	  @When("User click on Log In button") 
	  public void user_click_on_Log_In_button_for_savemart() {
	  objLoginPageSM.clickLogin(); 
	  }
	  
	  @When("User enter the valid username") 
	  public void user_enter_the_valid_username() throws InterruptedException{ 
	  String userName = PropertyDriver.readProp("SavemartUserName_LOGIN");
	  WebWaitHelper.waitforelement(1000);
	  objLoginPageSM.enterUserName(userName); 
	  String value = objLoginPageSM.UserNameValue();
	  Assert.assertEquals(value, "testbrown@testing.net", "Invalid username");
	  
	  } 
	  	  
	  @Then("User enter the valid password") 
	  public void user_enter_the_valid_password() throws InterruptedException {
	  
	  String passWord = PropertyDriver.readProp("SavemartPassword_LOGIN");
	  objLoginPageSM.enterPassword(passWord); 
	  String value = objLoginPageSM.UserPasswordValue(); 
	  Assert.assertEquals(value, "Test123$", "Invalid password");
	  
	  }
	  
	  @And("User clicks on login button to enter homepage") 
	  public void user_clicks_on_login_button_to_enter_homepage() throws InterruptedException{
	  objLoginPageSM.login();  
	  WebWaitHelper.waitforpageload();
	  WebWaitHelper.waitforelement(1000);
		if(objLoginPageSM.validaterewards()) {
			Assert.assertTrue(objLoginPageSM.validaterewards());
			LOGGER.info("Login to LuckyMart application successfully");		
		}else {
			if(objLoginPageSM.invalidCredentialsDisplay()) {
				Assert.assertTrue(false, "Login failed due to invalid credentials");
				LOGGER.info("Login failed due to invalid credentials");
			} 
		}
	
	  }
	  
	  @Then("User verify the MyRewards option in savemart")
	  public void user_verify_MyRewards_option_in_savemart() throws Exception {
		  objLoginPageSM.validaterewards();
		  

	  }
	  	  
	  @And("User click on MyRewards button and verify available rewards in saveMart")
	  public void user_click_on_MyRewards_button_and_verify_available_rewards_savemart() throws Exception {
		  objLoginPageSM.click_on_MyRewards_button();
		  
		  if (objLoginPageSM.availablerewardsCount() > 0) {
				Assert.assertTrue(true, "Myrewards are available for the selection");
			} else {
				Assert.assertTrue(false, "Myrewards are not available for the selection");
			}
			imgpath = objUtility.takeSnapShot(driver);
	  }
	  
	  
	  @Then("User click on signout button for savemart")
	  public void user_click_on_signout_button_Savemart() throws InterruptedException {
		  objLoginPageSM.signOut();
		  
		  

	  }
	  
	  @After("@Savemartlogin") 
	  public void tearDown(Scenario scenario) throws ParseException { 
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

			WebHandlers.writeExcelSavemart("C:\\Users\\V987920\\eclipse-workspace\\Savemart-main\\TAF-Scripting\\ConsolidatedReport\\TestDataSavemart.xlsx", "login_SM",reportdetails);

	  }
	  
}
