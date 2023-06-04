package com.scripted.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import cucumber.api.Result;
import io.cucumber.core.api.Scenario;

public class UtilityClass {
	
	public String takeSnapShot(WebDriver webdriver) throws Exception {

		String imgpath = "";

		try {

			// Convert web driver object to TakeScreenshot

			TakesScreenshot scrShot = ((TakesScreenshot) webdriver);

			// Call getScreenshotAs method to create image file

			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

			// Move image file to new destination

			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

			Date date = new Date();

			String startat = dateFormat.format(date);

			imgpath = "C:\\Users\\V987920\\eclipse-workspace\\Savemart-main\\TAF-Scripting\\src\\main\\resources\\Screenshots\\Test"+ startat + ".png";

			File DestFile = new File(imgpath);

			// Copy file at destination

			FileUtils.copyFile(SrcFile, DestFile);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return imgpath;

	}
	
	public void browserZoomOut() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_SUBTRACT);
		robot.keyRelease(KeyEvent.VK_SUBTRACT);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	public String currentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

		Date date = new Date();

		return dateFormat.format(date);
	}
	
	public String currentDateformatDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		Date date = new Date();

		return dateFormat.format(date);
	}
	
	public String currentTime() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		return dtf.format(LocalDateTime.now()).substring(10).trim();

	}
	
	public String duration(String startTime, String endTime) throws ParseException {
		

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

		Date date1 = format.parse(startTime);

		Date date2 = format.parse(endTime);

		long diff = date2.getTime() - date1.getTime();

		long diffSeconds = diff / 1000 % 60;

		long diffMinutes = diff / (60 * 1000) % 60;

		long diffHours = diff / (60 * 60 * 1000) % 24;

		String duration = diffHours + ":" + diffMinutes + ":" + diffSeconds;
		return duration;
	}
	
	public String logError(Scenario scenario) {

		String error = "";

		try {

			Class clasz = ClassUtils.getClass("cucumber.runtime.java.JavaHookDefinition$ScenarioAdaptor");

			Field fieldScenario = FieldUtils.getField(clasz, "scenario", true);

			fieldScenario.setAccessible(true);

			Object objectScenario = fieldScenario.get(scenario);

			Field fieldStepResults = objectScenario.getClass().getDeclaredField("stepResults");

			fieldStepResults.setAccessible(true);

			ArrayList<Result> results = (ArrayList<Result>) fieldStepResults.get(objectScenario);

			for (Result result : results) {

				if (result.getError() != null) {

					error = result.getError().toString();

				}

			}

		} catch (Exception e) {

		}

		return error;

	}
	
	public String dateExpiryFormat(String value) {
		String expiry1 = value;
		System.out.println("valueeeee" + value);
		SimpleDateFormat date1 =new SimpleDateFormat("MM-dd-yyyy");
	    Calendar cal1 = Calendar.getInstance();
	    int expiryyear = cal1.get(Calendar.YEAR);  
	    String expirydate = expiry1 +"/"+ expiryyear;
	    return expirydate.replaceAll("/", "-");
	}
	
	public Boolean dateVerification(String date) throws ParseException {
		Boolean value =false;
		Date objDate = new Date();
		String str=date;
	//	String str="12-31-2023";
		    Date date1 =new SimpleDateFormat("MM-dd-yyyy").parse(str);
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(objDate);
		    Calendar cal1 = Calendar.getInstance();
		    cal1.setTime(date1);	 
		    int expirydate = cal1.get(Calendar.DATE);
		    int currentdate = cal.get(Calendar.DATE);	    
		    if(expirydate >= currentdate){
		    	value =true;
		    	}
		    	else{
		    	System.out.println("Expiry date is not greater than current Date");
		    	value =false;
		    	}
			
		    return value;
	     }
	    
	}
