package com.scripted.web.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;
import com.scripted.web.WebWaitHelper;

public class LoginPageSM {

	  
	  @FindBy(xpath="//button[contains(text(),'Log In / Sign Up')]") 
	  private WebElement button_pFLogInSignUp;
	  
	  @FindBy(id = ":r2:")
	  private WebElement input_Password;
	  
	  @FindBy(id = ":r1:")
	  private WebElement input_YourEmail;
	    
	  @FindBy(id = ":r3:")
	  private WebElement button_r3;
	    
	  @FindBy(xpath="//p[text()='invalidCredentials']")
	  private WebElement invalidcredentials;
	  
	  @FindBy(xpath="//button[text()='My Rewards']")
	  private WebElement myRewards;
	  
	  @FindBy(xpath="(//span[@class='MuiTouchRipple-root css-w0pj6f'])[13]")
	  private WebElement signOut;
	  
	   	
	  
	  //Functionality to click the LogIn button
	  public void clickLogin() {
		  WebWaitHelper.waitforelemnettobeclickable(button_pFLogInSignUp, 3);
		  WebHandlers.clickByJsExecutor(button_pFLogInSignUp);
		  WebWaitHelper.waitforpageload();
	  }
	  
	  //Functionality to invoke username
	  public void enterUserName(String userName) throws InterruptedException {
			
			WebHandlers.enterText(input_YourEmail, userName);
		}
		
	//Functionality to return username field value
		public String UserNameValue() throws InterruptedException {
			
			return WebHandlers.elementGetValue(input_YourEmail);
		}
		
		//Functionality to invoke userpassword
		public void enterPassword(String password) throws InterruptedException {

			WebHandlers.enterText(input_Password, password);
		}
		
		//Functionality to return password field value
		public String UserPasswordValue() throws InterruptedException {
			
			return WebHandlers.elementGetValue(input_Password);
		}

		//Functionality to click login
		public void login() throws InterruptedException {
		
			WebHandlers.clickByJsExecutor(button_r3);
			
		}
		
		//Functionality to validate the password
		public Boolean invalidCredentialsDisplay() {
			return invalidcredentials.isDisplayed();
		}
		
		//Functionality to verify the rewards
		public boolean validaterewards() throws InterruptedException {
			WebWaitHelper.waitforpageload();
			WebWaitHelper.waitforelement(3000);
			boolean displayed = myRewards.isDisplayed(); 
			return displayed;
		}

		//Functionality to click the rewards button and view the rewards
		public void click_on_MyRewards_button() throws InterruptedException {
			WebWaitHelper.waitforelemnettobeclickable(myRewards, 3);
			WebWaitHelper.waitforelement(3000);
			WebHandlers.clickByJsExecutor(myRewards);	
		}
		
		//Functionality to click the logout button	
		public void signOut() throws InterruptedException {
				
				WebHandlers.clickByJsExecutor(signOut);
				WebWaitHelper.waitforpageload();
				WebWaitHelper.waitforelement(3000);
			}
			
		//Functionality to check the offer count
		public int availablerewardsCount() {
		List<WebElement>rewardscount = BrowserDriver.getDriver().findElements(By.xpath("//div[@class='MuiBox-root css-1di751t']//div"));
			return rewardscount.size();
			}

}
