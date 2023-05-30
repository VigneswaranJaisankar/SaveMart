package com.scripted.Reportportal;

import java.util.Base64;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;


public class ReportPortalListener implements ITestListener {
	
	public static Logger LOGGER = LogManager.getLogger(ReportPortalListener.class);
	private static final Logger LOG = LogManager.getLogger("binary_data_logger");
	public static  WebDriver driver = null;
	
	public  WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		
			ReportPortalListener.driver = driver;
		
	}
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		try {
			reportportalScreenshot("passed",ReportPortalListener.driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override @AfterMethod
	public void onTestFailure(ITestResult result) {
		try {
			reportportalScreenshot("failed",ReportPortalListener.driver);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
	
	 public static void reportportalScreenshot(String outmsg,WebDriver driver) throws Exception {
		  Thread.sleep(2000);
		  System.out.println("inside screenshot");
		//  File scrfile =  ((TakesScreenshot)BrowserDriver.getDriver()).getScreenshotAs(OutputType.FILE);
		  String base64 = ((TakesScreenshot)ReportPortalListener.driver).getScreenshotAs(OutputType.BASE64) ;
		 // byte[] base64 =  ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES) ;
		  
		//  Thread.sleep(2000);
		LOG.info("RP_MESSAGE#BASE64#{}#{}",base64,outmsg);	
		//LOGGER.info("RP_MESSAGE#BASE64#{}#{}",Base64.getEncoder().encodeToString(base64),outmsg);	
		//Thread.sleep(2000);
		System.out.println("outside screenshot");
		Thread.sleep(2000);
		
	}
	 

}
