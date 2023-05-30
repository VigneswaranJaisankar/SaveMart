package com.scripted.qtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class qTestManagement {
	org.json.simple.JSONObject body = new org.json.simple.JSONObject();
	org.json.simple.JSONObject response = null;
	public static Logger LOGGER = LogManager.getLogger(qTestManagement.class);
	public void updateTestCaseStatus(String cucumberJsonPath) throws Exception {

		APIClient obj=new APIClient(cucumberJsonPath);
		LOGGER.info("qTest test cases status updation completed");
		
	}

}
