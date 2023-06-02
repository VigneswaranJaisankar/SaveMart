package com.scripted.web.foodmaxx;

import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

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

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OfferDisplay_Food_StepDef {

	/*
	  WebDriver driver; 
	  PropertyDriver propDriver = new PropertyDriver();
	  OfferDisplay_Foodmaxx offerPage;
	  
	  @Before("@Foodmaxx") public void beforeStep() {
	  
	  BrowserDriver.getCuPalDriver();
	  BrowserDriver.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  
	  }
	  
	  @Given("User launch the browser and enter the foodmaxx url") 
	  public void user_launch_the_browser_and_enter_the_foodmaxx_url() {
	  
	  driver = BrowserDriver.getDriver();
	  AllureListener.setDriver(BrowserDriver.getDriver());
	  offerPage=PageFactory.initElements(BrowserDriver.getDriver(), OfferDisplay_Foodmaxx.class); 
	  propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/config.properties"); 
	  String url= PropertyDriver.readProp("FOODMAXX_URL"); 
	  BrowserDriver.launchWebURL(url);
	  
	  }
	  
	  @When("User click on the Log In button")
	  public void user_click_on_the_Log_In_button() {
		  offerPage.clickLogin();
	  }
	  
	 @When("User enter username") 
	 public void user_enter_username() throws InterruptedException{ 
	 String userName = PropertyDriver.readProp("FoodmaxxUserName"); 
	 offerPage.enterUserName(userName); 
	 String value = offerPage.UserNameValue();
	 Assert.assertEquals(value, "tsmcsavemart@gmail.com");
	  
	  }
	  
	  @Then("User enter password") 
	  public void user_enter_password() throws InterruptedException { 
	  String passWord = PropertyDriver.readProp("FoodmaxxPassword");
	  offerPage.enterPassword(passWord); 
	  String value = offerPage.UserPasswordValue(); 
	  Assert.assertEquals(value, "Savemart@1");
	  Thread.sleep(30000); 
	  }
	  
	  @And("User clicks the login button to enter homepage") 
	  public void user_clicks_the_login_button_to_enter_homepage() throws InterruptedException{ 
		  offerPage.login(); 
	  }
	  
	  @Then("User clicks the Foodmaxx Coupons Icon") 
	  public void user_clicks_the_Foodmaxx_Coupons_Icon() throws InterruptedException {
	  
	  Thread.sleep(30000);
	  Actions action = new Actions(driver); 
	  WebElement foodCoupon = driver.findElement(By.xpath("(//button[contains(text(),'Coupons')])"));  
	  action.moveToElement(foodCoupon).click().perform();
	  	  	  
	  }
	
	  
	  @Then("User see the offers and clicks the image") 
	  public void user_see_the_offers_and_clicks_the_image() throws InterruptedException {
		  List<WebElement> products = driver.findElements(By.
				  xpath("//ul[@class='MuiList-root MuiList-padding css-jd7km1']//following:: div[@class='sk-CouponCard__card-container MuiBox-root css-n2r3b1']//p[@class='MuiTypography-root MuiTypography-body1 sk-CouponCard__description css-1hpegke']"));

		  	List<String> productNames = new ArrayList();
		  	
		  	for (WebElement names : products) {
				
		  		productNames.add(names.getText());
		  		System.out.println(names.getText());
			}
		  	
		  		  
	  }
	  
	  @And("User see the displayed offers with images") 
	  public void user_see_the_displayed_offers_with_images() throws MalformedURLException, IOException {
	  
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
	  
	  WebElement imageDisplay1 = driver.findElement(By.tagName("img")); 
	  boolean displayed = imageDisplay1.isDisplayed();
	  
	  System.out.println("Image Display: "+displayed);
	  
	  }
	  
	  @After("@Foodmaxx") 
	  public void tearDown() { 
		  // driver.close(); 
		  }
		   */
	  }
	 	  
	
