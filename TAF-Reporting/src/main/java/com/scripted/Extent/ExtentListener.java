package com.scripted.Extent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventListener;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentLoggerReporter;

public class ExtentListener implements ITestListener {

	public static WebDriver driver = null;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static String screenshotPath = "";
	public static String destination = "";
	private static String cdir = System.getProperty("user.dir");
	public static Logger LOGGER = LogManager.getLogger(ITestListener.class);
	static Object testObject = null;

	public static Object getTestObject() {
		return testObject;
	}

	public static Object setTestObject(Object test) {
		return ExtentListener.testObject = test;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		ExtentListener.driver = driver;
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult result) {

	}

	@Override
	public void onTestFailure(ITestResult result) {
		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				ExtentUtils.eStepFail(" This step failed : " + result.getInstanceName());
				driver = ExtentListener.getDriver();
				test = (ExtentTest) ExtentListener.getTestObject();
				screenshotPath = saveScreenshot(driver, result.getName());
				MediaEntityModelProvider mediaModel = MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath)
						.build();
				ExtentUtils.addStepScreenshot("Screenshot", mediaModel);
			} else if (result.getStatus() == ITestResult.SKIP) {
				((ExtentTest) testObject).log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
			} else if (result.getStatus() == ITestResult.SUCCESS) {
				((ExtentTest) testObject).log(Status.PASS, "Test Case PASSED IS " + result.getName());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error while generating extent report" + e1);
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public static String saveScreenshot(WebDriver driver, String screenshotName) throws IOException {
		try {
			String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot scrnShot = (TakesScreenshot) driver;
			File source = scrnShot.getScreenshotAs(OutputType.FILE);
			destination = cdir + "/Screenshots/" + screenshotName + timeStamp + ".png";
			File finalDestination = new File(destination);
			FileUtils.copyFile(source, finalDestination);
		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error while save screenshots" + e1);
		}
		return destination;

	}

}
