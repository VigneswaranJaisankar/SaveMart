package com.scripted.mobile.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import com.scripted.mobile.MobileHandlers;
import com.scripted.utils.UtilityClass;
import com.scripted.web.BrowserDriver;
import com.scripted.web.WebHandlers;
import com.scripted.web.WebWaitHelper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class OfferPageSM_iOS {
	public static Logger LOGGER = LogManager.getLogger(OfferPageSM_Android.class);
	UtilityClass objUtility = new UtilityClass();
	HashMap<String, String> categoryDetails = new HashMap<>();
	IOSDriver<WebElement> iOSDriver ;
	
	@FindBy(xpath = "//button[contains(text(),'Log In / Sign Up')]")
	private MobileElement logIn;

	@FindBy(xpath = "//*[@placeholder='Your Email']")
	private MobileElement userName;

	@FindBy(xpath = "//*[@placeholder='Password']")
	private MobileElement password;

	@FindBy(xpath = "(//button[contains(text(),'Log In')])[2]")
	private MobileElement loginButton;

	@FindBy(xpath = "//button[contains(text(),'Coupons')]")
	private MobileElement savemartCoupons;
	
	@FindBy(xpath = "//*[@class='MuiListItemText-root css-1hqjvbr']/span")
	private MobileElement coupounCategory;
	
	@FindBy(xpath = "//div[@class='sk-CouponCard MuiBox-root css-pm59h0']/div[1]")
	private MobileElement availableOffers;
	
	public OfferPageSM_iOS(IOSDriver<WebElement> IOSDriver) {
		this.iOSDriver =IOSDriver;
		PageFactory.initElements(new AppiumFieldDecorator(iOSDriver), this);
	}
	// Functionality to click the LogIn button
	public void clickLogin() {
		WebWaitHelper.waitforelemnettobeclickable(logIn, 3);
		MobileHandlers.clickByJsExecutor(logIn);
		WebWaitHelper.waitforpageload();
	}

	// Functionality to invoke username
	public void enterUserName(String username) throws InterruptedException {

		MobileHandlers.enterText(userName, username);
	}


	// Functionality to invoke userpassword
	public void enterPassword(String Password) throws InterruptedException {

		MobileHandlers.enterText(password, Password);
	}


	// Functionality to click login
	public void login() throws InterruptedException {

		MobileHandlers.clickByJsExecutor(loginButton);

	}

	// Functionality to verify categoryDetails
	public void allCategory() throws InterruptedException {
		WebWaitHelper.waitforelemnettobepresent(availableOffers, 30);
		List<WebElement> categoryoffers = BrowserDriver.getDriver()
				.findElements(By.xpath("//*[@class='MuiListItemText-root css-1hqjvbr']/span"));
		for (int i = 0; i < categoryoffers.size(); i++) {
			String category = categoryoffers.get(i).getText();		
			String[] categorySplit = category.split("[(]");
			String categoryName = categorySplit[0].trim();
			String count = categorySplit[1].replaceAll("[)]", "");
			categoryDetails.put(categoryName, count);
		}	
	}
	
	public void verifyAvailable_offers_foreachcategory() throws InterruptedException, ParseException {
		WebHandlers.scrollBy(0, 150);
		List<WebElement> categoryoffers = BrowserDriver.getDriver()
				.findElements(By.xpath("//*[@class='MuiListItemText-root css-1hqjvbr']/span"));
		for (int i = 0; i < categoryoffers.size(); i++) {
			String category = categoryoffers.get(i).getText();		
			String[] categorySplit = category.split("[(]");
			String categoryName = categorySplit[0].trim();
			String count = categorySplit[1].replaceAll("[)]", "");			
			for (Entry<String, String> string : categoryDetails.entrySet()) {
				//Getting the list of available category
				if(string.getKey().contains(categoryName)) {
					categoryoffers.get(i).click();
					LOGGER.info("Click on category:- " + categoryName );
					Thread.sleep(4000);
					//Verfying the count of available offers against the category
					List<WebElement>offerCount = BrowserDriver.getDriver().findElements(By.xpath("//div[@class='sk-CouponCard MuiBox-root css-pm59h0']/div[1]"));
				    int offersize=  offerCount.size();
					if(string.getValue().equals(String.valueOf(offersize))) {
						LOGGER.info("offer displays as per the category count");	
					}else if(!string.getValue().equals(String.valueOf(offersize))) {
						SoftAssert objassert = new SoftAssert();
						objassert.assertFalse(true, "offers not displays as per the category count");
						objassert.assertAll();
					}	
					//Verfying exp date for the available offers
					List<WebElement>expDate = BrowserDriver.getDriver().findElements(By.xpath("//div[@class='sk-CouponCard MuiBox-root css-pm59h0']/div[2]/div/div/p"));
                    for(int a=0 ; a<expDate.size(); a++) {
                    	String expdate1 = expDate.get(a).getText();
            			java.lang.String[] expiry = expdate1.split("[.]");
            			String expiry1 = expiry[1].trim();
            			System.out.println(expiry1);
            			SimpleDateFormat date1 =new SimpleDateFormat("MM-dd-yyyy");
            		    Calendar cal1 = Calendar.getInstance();
            		    int expiryyear = cal1.get(Calendar.YEAR);  
            		    String expirydate = expiry1 +"/"+ expiryyear;
            		    String expdatevalue = expirydate.replaceAll("/", "-");
            			if(objUtility.dateVerification(expdatevalue)){
            				LOGGER.info("offer date is greater than the current date");
            			}else if (!objUtility.dateVerification(objUtility.dateExpiryFormat(expdate1))){
            				LOGGER.info("offer date is less than the current date");
            			}
                    	
                    }
				}
			}
			
		}
		
	}

}
