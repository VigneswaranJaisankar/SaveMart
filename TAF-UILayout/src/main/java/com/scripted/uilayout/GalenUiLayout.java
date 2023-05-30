package com.scripted.uilayout;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;

import com.galenframework.api.Galen;
import com.galenframework.config.GalenConfig;
import com.galenframework.config.GalenProperty;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.galenframework.utils.GalenUtils;

public class GalenUiLayout {

	public static void galenTest(WebDriver driver, String specFileName) throws InterruptedException {
		try {
			GalenConfig.getConfig().setProperty(GalenProperty.SCREENSHOT_FULLPAGE, "true");

			String specFile =  "src/main/resources/UILayout/GalenSpecFiles/"+specFileName+".gspec";
			String URL =  driver.getCurrentUrl();
			
			//Galen screenshot generation
			String screenpath = "layout.png";
			String encodedString = null;
			File file;
			file = GalenUtils.makeFullScreenshot(driver);
			BufferedImage image = null;
			image = ImageIO.read(file);
			ImageIO.write(image, "png", new File(screenpath));
			byte[] fileContent = FileUtils.readFileToByteArray(new File(screenpath));
			encodedString = Base64.getEncoder().encodeToString(fileContent);

			//Galen report generation
			LayoutReport layoutReport = null;
			layoutReport = Galen.checkLayout(driver, specFile, Arrays.asList("mobile", "desktop", "tablet", "all"));

			List<GalenTestInfo> tests;
			tests = new LinkedList<GalenTestInfo>();
			GalenTestInfo test = GalenTestInfo.fromString(URL);
			test.getReport().layout(layoutReport, screenpath);
			tests.add(test);
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm").format(new Date());
			new HtmlReportBuilder().build(tests, "./Skriptmatereport/UILayout/"+timeStamp+"/galenreport");
		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
}
