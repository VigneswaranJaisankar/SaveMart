package com.scripted.PDF;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class DataFromJSON {

	org.json.simple.JSONObject body = new org.json.simple.JSONObject();
	org.json.simple.JSONObject response = null;
	public static Logger LOGGER = LogManager.getLogger(DataFromJSON.class);
	public static Map scenarioCountdetails(String cucumberJsonPath) throws IOException {
		Map<String, String> resultmap = new HashedMap();
		try {
		CucumberJsonExtractor cucm = new CucumberJsonExtractor();
		JSONObject obj = cucm.getScenarioAndStepsStatus(cucumberJsonPath);

		int passcount = 0;
		int failcount = 0;

		for (String feature : obj.keySet()) {
			JSONObject featureObject = obj.getJSONObject(feature);
			for (String testCase : featureObject.keySet()) {
				if (featureObject.getJSONObject(testCase).getString("scenarioStatus").equalsIgnoreCase("Passed")) {
					passcount++;
				}
				if (featureObject.getJSONObject(testCase).getString("scenarioStatus").equalsIgnoreCase("failed")) {
					failcount++;
				}
			}

		}
		resultmap.put("passcount", Integer.toString(passcount));
		resultmap.put("failcount", Integer.toString(failcount));
		int totalcount = passcount + failcount;
		resultmap.put("totalcount", Integer.toString(totalcount));
		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error while getting scenario count details" +e1);
		}
		return resultmap;
		
	}

	public static Map scenariodetails(String cucumberJsonPath) throws IOException {
		
		Map<String, String> resultmap = new HashedMap();
		try {
		CucumberJsonExtractor cucm = new CucumberJsonExtractor();
		JSONObject obj = cucm.getScenarioAndStepsStatus(cucumberJsonPath);

		int passcount = 0;
		int failcount = 0;
		for (String feature : obj.keySet()) {
			JSONObject featureObject = obj.getJSONObject(feature);
				if (featureObject.getString("scenarioStatus").equalsIgnoreCase("Passed")) {
					passcount++;
				}
				if (featureObject.getString("scenarioStatus").equalsIgnoreCase("failed")) {
					failcount++;
				}
			
			resultmap.put(feature, passcount + "##" + failcount + "##" + (passcount + failcount));
			 passcount = 0;
			 failcount = 0;
		}
		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error while getting scenario details" +e1);
		}
		return resultmap;

	}
	public static Map scenarioCount(String cucumberJsonPath) throws IOException {
		
		Map<String, String> resultmap = new HashedMap();
		try {
		CucumberJsonExtractor cucm = new CucumberJsonExtractor();
		JSONObject obj = cucm.getScenarioAndStepsStatus(cucumberJsonPath);

		int passcount = 0;
		int failcount = 0;
		for (String feature : obj.keySet()) {
			JSONObject featureObject = obj.getJSONObject(feature);
				if (featureObject.getString("scenarioStatus").equalsIgnoreCase("Passed")) {
					passcount++;
				}
				if (featureObject.getString("scenarioStatus").equalsIgnoreCase("failed")) {
					failcount++;
				} 
		}
		resultmap.put("Count", passcount + "##" + failcount + "##" + (passcount + failcount));
		} catch (Exception e1) {
			e1.printStackTrace();
			LOGGER.error("Error while getting scenario details" +e1);
		}
		return resultmap;

	}

}
