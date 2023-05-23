package com.scripted.testrail;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.scripted.generic.FileUtils;

import junit.framework.Assert;

public class TestRailUtils {
	public static Logger LOGGER = LogManager.getLogger(TestRailUtils.class);

	public static void uploadResultsFromCucumberJson(String jsonReportLocation) {
		try {
			File cucumberJsonfilePath = new File(FileUtils.getFilePath(jsonReportLocation));
			TestRailTestManagement testrailManage = new TestRailTestManagement();
			testrailManage.updateTestCaseStatus(cucumberJsonfilePath.toString());
			LOGGER.info("Updated testcase and teststep status successfully");
		} catch (Exception e) {
			LOGGER.error("Error while trying to upload results from CucumberJson" + " Exception :" + e);
			Assert.fail("Error while trying to upload results from CucumberJson" + " Exception :" + e);
		}
	}
}
