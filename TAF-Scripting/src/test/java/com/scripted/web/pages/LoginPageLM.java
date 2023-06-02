package com.scripted.web.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.scripted.dataload.PropertyDriver;
import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;
import com.scripted.web.WebWaitHelper;

import io.cucumber.java.en.And;

public class LoginPageLM {
	PropertyDriver propDriver = new PropertyDriver();

	@FindBy(xpath = "//*[contains(text(),'Log In / Sign Up')]")
	private WebElement LogInChrome;

	@FindBy(xpath = "//div[contains(@class, \"MuiStack-root\")])[2]/button[2]")
	private WebElement selectoption;

	@FindBy(xpath = "(//div[@class=\"MuiBox-root css-8atqhb\"]/div[1]/div/input)[1]")
	private WebElement userName;

	@FindBy(xpath = "(//div[@class=\"MuiBox-root css-8atqhb\"]/div[1]/div/input)[2]")
	private WebElement passWord;

	@FindBy(xpath = "//div[@class=\"MuiFormControl-root css-tzsjye\"]/button")
	private WebElement Login;
/*
	@FindBy(xpath = "(//span[@class='MuiTouchRipple-root css-w0pj6f'])[6]")
	private WebElement Profile;
*/
	@FindBy(xpath = "//p[text()=\"invalidCredentials\"]")
	private WebElement invalidcredentials;

	@FindBy(xpath = "(//*[text()='My Rewards'])[1]")
	private WebElement myrewards;

	@FindBy(xpath = "(//span[@class='MuiTouchRipple-root css-w0pj6f'])[13]")
	private WebElement signout;

	@FindBy(xpath = "(//div[contains(@class, \"MuiStack-root\")])[2]/button[2]")
	private WebElement options;

	//Functionality to click the LogIn button
	public void clickLogin() throws InterruptedException {
		
		WebWaitHelper.waitforelemnettobeclickable(LogInChrome, 10);
		WebHandlers.clickByJsExecutor(LogInChrome);		
		WebWaitHelper.waitforelement(2000);
	}

	//Functionality to invoke username
	public void EnterUserName(String username) throws InterruptedException {
		WebWaitHelper.waitforelement(2000);
		WebHandlers.enterText(userName, username);
	}
	
	//Functionality to return username field value
	public String UserNameValue() throws InterruptedException {

		return userName.getAttribute("value");
	}

	//Functionality to invoke userpassword
	public void EnterPassword(String password) throws InterruptedException {
		WebWaitHelper.waitforelement(2000);
		WebHandlers.enterText(passWord, password);
	}

	//Functionality to return password field value
	public String UserPasswordValue() throws InterruptedException {

		return passWord.getAttribute("value");
	}

	//Functionality to click login
	public void login() throws InterruptedException {

		WebHandlers.clickByJsExecutor(Login);
	}

	//Functionality to validate the password
	public Boolean invalidCredentialsDisplay() {
		return invalidcredentials.isDisplayed();
	}

	//Functionality to verify the rewards
	public Boolean validaterewards() throws InterruptedException {
		WebWaitHelper.waitforpageload();
		WebWaitHelper.waitforelement(3000);
		boolean displayed =  WebHandlers.elementDiplayed(myrewards);
		return displayed;
	
	}

	//Functionality to click the rewards button and view the rewards
	public void click_on_MyRewards_button() throws InterruptedException {
		WebWaitHelper.waitforelemnettobeclickable(myrewards, 3);
		WebWaitHelper.waitforelement(3000);
		WebHandlers.clickByJsExecutor(myrewards);
		WebWaitHelper.waitforelement(3000);

	}

	//Functionality to click the logout button	
	public void signOut() throws InterruptedException {

		WebHandlers.clickByJsExecutor(signout);
		WebWaitHelper.waitforelement(3000);
	}

	//Functionality to check the offer count
	public int availablerewardsCount() {
		List<WebElement> rewardscount = BrowserDriver.getDriver()
				.findElements(By.xpath("//div[@class='MuiBox-root css-1di751t']//div"));
		return rewardscount.size();
	}

}
