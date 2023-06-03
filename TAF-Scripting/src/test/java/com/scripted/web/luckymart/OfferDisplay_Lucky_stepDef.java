package com.scripted.web.luckymart;

import java.util.Set;
import java.util.List;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.net.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.scripted.Allure.AllureListener;
import com.scripted.dataload.PropertyDriver;
import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;
import com.scripted.web.WebWaitHelper;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OfferDisplay_Lucky_stepDef {

	
	  WebDriver driver; 
	  PropertyDriver propDriver = new PropertyDriver();
	  OfferDisplayPage_Luckysuper offerPage;
	  
	  @Before("@Luckysupermarket") 
	  public void beforeStep() {
	  BrowserDriver.getCuPalDriver(); //
	  BrowserDriver.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
	  }
	  
	  @Given("the user launch the browser and enter url luckymart") 
	  public void the_user_launch_the_browser_and_enter_url_luckymart() {
	  
	  driver = BrowserDriver.getDriver();
	  AllureListener.setDriver(BrowserDriver.getDriver());
	  offerPage=PageFactory.initElements(BrowserDriver.getDriver(),OfferDisplayPage_Luckysuper.class); 
	  propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/config.properties"); 
	  String url= PropertyDriver.readProp("LUCKY_URL"); 
	  BrowserDriver.launchWebURL(url);
	  
	  }
	  
	  @When("the user clicks the Log In button") 
	  public void user_clicks_the_Log_In_button_luckymart() { 
		  
		  offerPage.clickLogin(); 
		  }
	  
	  @Then("the user enters the username") 
	  public void user_enter_username_luckymart() throws InterruptedException{ 
		  String userName = PropertyDriver.readProp("LuckyUserName"); 
		  offerPage.enterUserName(userName); 
		  String value = offerPage.UserNameValue();
		  Assert.assertEquals(value, "tsmcsavemart@gmail.com");
	  
	  }
	 
	 @Then("the user enters the password") 
	 public void user_enter_password_luckymart() throws InterruptedException { 
		 
	 String passWord = PropertyDriver.readProp("LuckyPassword"); 
	 offerPage.enterPassword(passWord); 
	 String value = offerPage.UserPasswordValue(); 
	 Assert.assertEquals(value, "Savemart@1"); 
	 
	 }
	  
	 @And("user clicks login button to enter homepage") 
	 public void user_clicks_login_button_to_enter_homepage_lucky() throws InterruptedException {
	
		 offerPage.login(); 
	 }
	  
	 @Then("user click the Lucky Coupons Icon") 
	 public void the_user_click_the_Lucky_Coupons_Icon_luckymart() throws InterruptedException {
	  /*
	  Actions action = new Actions(driver); 
	  
	  WebElement luckyCoupon = driver.findElement(By.xpath("(//button[contains(text(),'Coupons')])"));
	  
	  action.moveToElement(luckyCoupon).click().perform();*/
	  offerPage.clickLuckyrewards();
	  	
	  }
	  
	  @Then("user check the available offers") 
	  public void user_check_the_available_offers_luckysuper() throws InterruptedException {
		  
		  List<WebElement> products = driver.findElements(By.
				  xpath("//ul[@class='MuiList-root MuiList-padding css-jd7km1']//following:: div[@class='sk-CouponCard MuiBox-root css-pm59h0']//p[@class='MuiTypography-root MuiTypography-body1 sk-CouponCard__description css-1hpegke']"));

		  	List<String> productNames = new ArrayList();
		  	
		  	for (WebElement names : products) {
				
		  		productNames.add(names.getText());
		  		System.out.println(names.getText());
			}
		  	
			  
			  WebElement clipp = driver.findElement(By.xpath("//button[contains(text(),'Clipped Coupons (1)')]")); 
			  clipp.click();
	  }
	  
	  @And("User see the displayed offers") 
	  public void user_see_the_displayed_offers_luckymart() throws MalformedURLException, IOException{
	  
	  offerPage.validateInvalidImages();
	  
	  List<WebElement> imageDisplay = driver.findElements(By.tagName("a"));
	  imageDisplay.addAll(driver.findElements(By.tagName("img")));
	  System.out.println("size of full links and images--->"+ imageDisplay.size());
	  	  
	  List<WebElement> activeLinks = new ArrayList<WebElement>();
	  for (int i = 0; i < activeLinks.size(); i++) {
		System.out.println(imageDisplay.get(i).getAttribute("img"));
		if (imageDisplay.get(i).getAttribute("href") !=null) {
			System.out.println(imageDisplay.get(i));
		}
	}
	  System.out.println("size of active links and images--->"+ activeLinks.size());
	  
	  for (int j = 0; j < activeLinks.size(); j++) {
		
		  HttpURLConnection connection = (HttpURLConnection) new URL(activeLinks.get(j).getAttribute("href")).openConnection();
		  connection.connect();
		  String response = connection.getResponseMessage();
		  connection.disconnect();
		  System.out.println(activeLinks.get(j).getAttribute("href")+"--->"+response);
		  
	}
	  }
	  
	  @Then ("User check the purchase order limit for all the offers")
	  public void user_clicks_the_product_to_check_the_limit_lsm() throws InterruptedException{
		  
			System.out.println("User check the purchase order limit for all the offers");
	  }
	  
	  @And("User should see the correct purchase order limits for each offer")
	  public void user_should_see_the_correct_purchase_order_limits_for_each_offer() {
		  System.out.println("User should see the correct purchase order limits for each offer");
	  }
	  
	  @After("@Luckysupermarket") 
	  public void tearDown() { 
		  // driver.close(); 
		  }
		  
  }

