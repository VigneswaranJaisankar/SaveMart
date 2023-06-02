//package com.scripted.web.savemart;
//
//import java.util.concurrent.TimeUnit;
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.*;
//import org.openqa.selenium.By;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.PageFactory;
//import org.testng.Assert;
//
//import com.scripted.Allure.AllureListener;
//import com.scripted.dataload.PropertyDriver;
//import com.scripted.web.BrowserDriver;
//import com.scripted.web.WebHandlers;
//
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//public class OfferDisplay_Savemart_stepDef {
//
//	
//	  WebDriver driver; 
//	  PropertyDriver propDriver = new PropertyDriver();
//	  OfferDisplayPage_Savemart offerPage;
//	  
//	  @Before("@Savemart") 
//	  public void beforeStep() {
//	  BrowserDriver.getCuPalDriver(); //
//	  BrowserDriver.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//	  
//	  }
//	  
//	  @Given("User launch the browser and enter the savemart url for offer") 
//	  public void user_launch_the_browser_and_enter_the_savemart_urlforofferurl() {
//	  
//	  driver = BrowserDriver.getDriver();
//	  AllureListener.setDriver(BrowserDriver.getDriver());
//	  offerPage=PageFactory.initElements(BrowserDriver.getDriver(),OfferDisplayPage_Savemart.class); 
//	  propDriver.setPropFilePath("src/main/resources/DataFiles/Properties/config.properties"); 
//	  String url=PropertyDriver.readProp("SAVEMART_URL");
//	  BrowserDriver.launchWebURL(url);
//	  
//	  }
//	  
//	  @When("user click on Log In button for offer") 
//	  public void user_click_on_Log_In_button() {
//	  offerPage.clickLogin(); 
//	  }
//	  
//	  @Then("enter the valid username") 
//	  public void user_enter_the_valid_username_savemart() throws InterruptedException{ 
//	  String userName = PropertyDriver.readProp("SavemartUserName");
//	  offerPage.enterUserName(userName); 
//	  String value = offerPage.UserNameValue();
//	  Assert.assertEquals(value, "tsmcsavemart@gmail.com");
//	  
//	  } 
//	  
//	  
//	  @Then("enter the valid password") 
//	  public void user_enter_the_valid_password_savemart() throws InterruptedException {
//	  
//	  String passWord = PropertyDriver.readProp("SavemartPassword");
//	  offerPage.enterPassword(passWord); 
//	  String value = offerPage.UserPasswordValue(); 
//	  Assert.assertEquals(value, "Savemart@1");
//	  Thread.sleep(30000); }
//	  
//	  @And("click on login button to enter homepage") 
//	  public void user_click_on_login_button_to_enter_homepage_savemart() throws InterruptedException{
//	  offerPage.login();
//	  
//	  }
//	  
//	  @When("user click on Savemart Coupons Icon") public void
//	  user_click_on_Savemart_Coupons_Icon() throws InterruptedException {
//	  
//	  Thread.sleep(30000); 
//	  WebElement saveReward = driver.findElement(By.xpath("//button[contains(text(),'Coupons')]"));
//	  saveReward.click(); 
//	  Thread.sleep(30000);
//	  
//	  List<WebElement> products = driver.findElements(By.
//			  xpath("//ul[@class='MuiList-root MuiList-padding css-jd7km1']//following:: div[@class='sk-CouponCard__card-container MuiBox-root css-n2r3b1']//p[@class='MuiTypography-root MuiTypography-body1 sk-CouponCard__description css-1hpegke']"));
//	  List<String> productNames = new ArrayList();
//	  
//	  for (WebElement names : products) {
//		  productNames.add(names.getText());
//		System.out.println(names.getText());
//	}
//	  
// 
//	 }
//	  
//	  @Then("user view the offers and click on image") 
//	  public void user_view_the_offers_and_click_on_image_savemart() throws InterruptedException {
//	  
//	  List<WebElement> imageDisplay = driver.findElements(By.tagName("img")); 
//	//  boolean displayed = imageDisplay.isDisplayed();
//	  
//	//  System.out.println("Image Display: "+displayed); 
//	  }
//	  
//	  @And("User view the displayed offers") 
//	  public void user_see_the_displayed_offers() throws MalformedURLException, IOException {
//	  
//	  offerPage.validateInvalidImages();
//	  
//	  List<WebElement> imageDisplay = driver.findElements(By.tagName("a"));
//	  imageDisplay.addAll(driver.findElements(By.tagName("img")));
//	  System.out.println("size of full links and images--->"+ imageDisplay.size());
//	  	  
//	  List<WebElement> activeLinks = new ArrayList<WebElement>();
//	  for (int i = 0; i < activeLinks.size(); i++) {
//		System.out.println(imageDisplay.get(i).getAttribute("img"));
//		if (imageDisplay.get(i).getAttribute("href") !=null) {
//			System.out.println(imageDisplay.get(i));
//		}
//	}
//	  System.out.println("size of active links and images--->"+ activeLinks.size());
//	  
//	  for (int j = 0; j < activeLinks.size(); j++) {
//		
//		  HttpURLConnection connection = (HttpURLConnection) new URL(activeLinks.get(j).getAttribute("href")).openConnection();
//		  connection.connect();
//		  String response = connection.getResponseMessage();
//		  connection.disconnect();
//		  System.out.println(activeLinks.get(j).getAttribute("href")+"--->"+response);
//		  
//	}
//	 
//	  }
//	  
//	  @Then("User click on product to check the limit")
//	  public void user_click_on_product_to_check_the_limit() {
//		  WebElement imageDisplay1 = driver.findElement(By.tagName("img"));
//		  boolean displayed = imageDisplay1.isDisplayed();
//		  System.out.println("Image Display: "+displayed);
//	  }
//	  
//	  @After("@Savemart") 
//	  public void tearDown() { 
//		  // driver.close(); 
//	  }
//	  
//	 }
//
