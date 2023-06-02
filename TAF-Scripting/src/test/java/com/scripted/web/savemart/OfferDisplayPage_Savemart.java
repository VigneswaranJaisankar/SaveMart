package com.scripted.web.savemart;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.scripted.web.WebHandlers;

public class OfferDisplayPage_Savemart {
	
	  WebDriver driver1;
	  
	  URL myURL;
	  
	  File files;
	  
	  private static final By driver = null;
	  
	  @FindBy(xpath="//button[contains(text(),'Log In / Sign Up')]") 
	  private WebElement LogIn;
	  
	  @FindBy(xpath="//*[@placeholder='Your Email']") 
	  private WebElement USERNAME;
	  
	  @FindBy(xpath="//*[@placeholder='Password']") 
	  private WebElement PASSWORD;
	  
	  @FindBy(xpath="(//button[contains(text(),'Log In')])[2]") 
	  private WebElement LOGIN;
	  
	  @FindBy(xpath="//button[contains(text(),'Coupons')]") 
	  private WebElement SAVEMARTCOUPONS;
	  
	  @FindBy(xpath="//span[contains(text(),'All Categories (9)')]") 
	  private WebElement Available;
	  
	  
	  @FindBy(
	  tagName="//div[@class='MuiGrid-root MuiGrid-container css-1h77wgb']//img")
	  private List<WebElement> DISPLAYEDIMAGES;
	  
	  private int invalidImageCount;
	  
	  private int testImages;
	  
	  
	  public void clickLogin() { WebHandlers.click(LogIn); }
	  
	  public void enterUserName(String username) {
	  
	  WebHandlers.enterText(USERNAME, username); //USERNAME.sendKeys(username);
	  
	  }
	  
	  public String UserNameValue() throws InterruptedException {
	  
	  return USERNAME.getAttribute("value"); }
	  
	  public void enterPassword(String password) {
	  
	  WebHandlers.enterText(PASSWORD, password);
	  
	  }
	  
	  public String UserPasswordValue() throws InterruptedException {
	  
	  return PASSWORD.getAttribute("value"); }
	  
	  public void login() {
	  
	  WebHandlers.click(LOGIN);
	  
	  }
	  
	  public void clickSaverewards() {
	  
	  WebHandlers.click(SAVEMARTCOUPONS);
	  
	  }    
	  
	  public void validateInvalidImages() { try {
	  
	  List<WebElement> imagesList = DISPLAYEDIMAGES;
	  System.out.println("Total no. of images are " + imagesList.size()); 
	  for(WebElement imgElement : imagesList) { 
		  if (imgElement != null) {
	 
			  verifyimageActive(imgElement); } 
		  }
	  System.out.println("Total no. of invalid images are " + invalidImageCount); }
	  catch (Exception e) { e.printStackTrace();
	  System.out.println(e.getMessage()); } }
	  
	  public void verifyimageActive(WebElement imgElement) { 
		  try { HttpClient client = HttpClientBuilder.create().build(); 
		  HttpGet request = new HttpGet(imgElement.getAttribute("src")); 
		  HttpResponse response = client.execute(request); 
		  
		  // verifying response code he HttpStatus should be 200 if not, 
		  // increment as invalid images count 
		  
		  if(response.getStatusLine().getStatusCode() != 200) invalidImageCount++; 
	 }
	  catch (Exception e) { e.printStackTrace(); } 
		  
	  }
	  
	  public void testImages()throws Exception{
	  
	  // File files = null; 
		  Integer counter=0;
	  
	  Thread.sleep(5000); 
	  List<WebElement>listImages=driver1.findElements(By.tagName("img"));
	  System.out.println("No. of Images: "+listImages.size());
	  
	  for(WebElement image:listImages) { 
		  if(image.isDisplayed()) { 
			  counter++;
	  String[] names = image.getAttribute("src").split("/"); 
	  String name = names[names.length-1]; 
	  System.out.println(name); 
	  
	  URL myURL = new URL(image.getAttribute("src")); 
	 // files= new File("C:\\downloadedPictures\\"+ System.currentTimeMillis()+ "." +name);
	//  org.apache.commons.io.FileUtils.copyURLToFile(myURL, files); 
	  } 
	  }
	  System.out.println("No. of total displable images: "+counter); 
	
	  }
	
	 }
	 

