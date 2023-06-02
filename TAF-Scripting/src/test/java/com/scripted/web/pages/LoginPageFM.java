package com.scripted.web.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.scripted.dataload.PropertyDriver;
import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;
import com.scripted.web.WebWaitHelper;

public class LoginPageFM {
	
	PropertyDriver propDriver = new PropertyDriver();

	@FindBy(xpath = "//button[contains(text(),'Log In / Sign Up')]")
	private WebElement button_UcLogInSignUp;

	@FindBy(id = ":r1:")
	private WebElement input_YourEmail;
	
	@FindBy(id = ":r2:")
	private WebElement input_Password;

	@FindBy(id = ":r3:")
	private WebElement button_r3;

	@FindBy(xpath = "//*[text()='Brown']")
	private WebElement Profile;
	
	@FindBy(xpath = "//*[@class=\"MuiStack-root css-gxm63c\"]//button[2]")
	private WebElement myCoupouns;
	
	@FindBy(xpath = "//p[text()='invalidCredentials']")
	private WebElement invalidcredentials;

	@FindBy(xpath = "//button[text()='My Coupons']")
	private WebElement button_hjMyCoupons;

	@FindBy(xpath = "(//span[@class='MuiTouchRipple-root css-w0pj6f'])[12]")
	private WebElement p_JUSignOut;

	//Functionality to click the LogIn button
	public void clickLogin() {
		WebHandlers.clickByJsExecutor(button_UcLogInSignUp);
		WebWaitHelper.waitforpageload();
	}
      //Functionality to enter username 
	public void enterUserName(String userName) throws InterruptedException {

		WebHandlers.enterText(input_YourEmail, userName);
	}
	//Functionality to return username field value
	public String UserNameValue() throws InterruptedException {
		return WebHandlers.elementGetValue(input_YourEmail);
	}
	//Functionality to enter Password 
	public void enterPassword(String password) throws InterruptedException {

		WebHandlers.enterText(input_Password, password);
	}
	//Functionality to return password field value
	public String UserPasswordValue() throws InterruptedException {

		return WebHandlers.elementGetValue(input_Password);
	}
	//Functionality to click login button
	public void login() throws InterruptedException {

		WebHandlers.clickByJsExecutor(button_r3);

	}
	//Functionality to check invalidcredntials are displayed
	public Boolean invalidCredentials() {
		return invalidcredentials.isDisplayed();
		
	}
	
	public Boolean returnProfile() throws InterruptedException {
		WebWaitHelper.waitforpageload();
		WebWaitHelper.waitforelement(3000);
		return Profile.isDisplayed();
	}
	
	//Functionality to check Profile
	public void profile() throws InterruptedException {
		WebWaitHelper.waitforpageload();
		Thread.sleep(4000);
		WebWaitHelper.waitforelemnettobeclickable(Profile, 3);
		System.out.println("Profile Button is visible");
		BrowserDriver.getDriver().findElement(By.xpath("//*[text()='Brown']")).click();
		System.out.println("Profile Button is clicked");
	}
	//Functionality to check validate coupons are displayed
	public Boolean validatecoupons() throws InterruptedException {
		WebWaitHelper.waitforpageload();
		boolean displayed =  WebHandlers.elementDiplayed(button_hjMyCoupons);
		return displayed;

	}
	//Functionality to click on  MyCoupons
	public void click_on_MyCoupons_button() throws InterruptedException {
		WebWaitHelper.waitforelemnettobeclickable(button_hjMyCoupons,5);
		WebHandlers.clickByJsExecutor(button_hjMyCoupons);
	}
	//Functionality to click on Signout button
	public void signOut() throws InterruptedException {

		WebHandlers.clickByJsExecutor(p_JUSignOut);
		WebWaitHelper.waitforpageload();
	}
	
	public Boolean clickLoginDisplayed() {
		WebWaitHelper.waitforpageload();
		return WebHandlers.elementDiplayed(button_UcLogInSignUp);
		
	}

}
