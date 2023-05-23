package com.scripted.xray;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.scripted.generic.FileUtils;


public class XrayUtils {

	public static Logger LOGGER = LogManager.getLogger(XrayUtils.class);

	public static void uploadResultsFromCucumberJson(String jsonReportLocation) {
		try {
			File cucumberJsonfilePath = new File(FileUtils.getFilePath(jsonReportLocation));
			XrayTestManagement Xmanage = new XrayTestManagement();
			Xmanage.updateTestCaseStatus(cucumberJsonfilePath.toString());
			LOGGER.info("Updated test case status successfully");
		} catch (Exception e) {
			System.out.println(e);
			LOGGER.error("Error while trying to upload results from CucumberJson" + " Exception :" + e);
			Assert.fail("Error while trying to upload results from CucumberJson" + " Exception :" + e);
		}
	}
}
