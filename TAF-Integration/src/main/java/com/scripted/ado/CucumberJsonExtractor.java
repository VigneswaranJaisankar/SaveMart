package com.scripted.ado;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import com.scripted.dataload.PropertyDriver;


public class CucumberJsonExtractor {
	String PASS = "passed";
	String FAIL = "failed";

	public static Logger LOGGER = LogManager.getLogger(CucumberJsonExtractor.class);

	private JSONArray readData(String path) throws IOException {
		JSONArray cucumberJson = null;
		File file = new File(path);
		try {
			String content = FileUtils.readFileToString(file, "utf-8");
			cucumberJson = new JSONArray(content);

		} catch (Exception e) {
			LOGGER.error("Error while trying to read file " + "Exception :" + e);
			Assert.fail("Error while trying to read file " + "Exception :" + e);
		}
		return cucumberJson;
	}

	public JSONObject getScenarioAndStepsStatus(String cucumberJsonPath) throws IOException {
		JSONArray jsonArray = readData(cucumberJsonPath);
		JSONObject result = new JSONObject();
		String d = null;
		PropertyDriver propDriver = new PropertyDriver();
	
		propDriver.setPropFilePath("src/main/resources/Integrations/AzureDevOps/Properties/ADO.properties");
		String projectKey = PropertyDriver.readProp("ADO.projectkey");
		try {

			for (Object object : jsonArray) {
				JSONObject tagObject = new JSONObject();

				JSONObject obj = (JSONObject) object;
				JSONArray scenario = obj.getJSONArray("elements");
				for (Object arr : scenario) {

					JSONObject details = new JSONObject();
					JSONObject scenarioObj = (JSONObject) arr;
					String scenarioName = scenarioObj.getString("name");

					if (scenarioObj.has("after")) {
						JSONArray afterExecution = scenarioObj.getJSONArray("after");
						for (Object afterEle : afterExecution) {
							JSONObject e = (JSONObject) afterEle;
							if (e.has("embeddings")) {
								JSONArray embedList = e.getJSONArray("embeddings");
								for (Object embedElement : embedList) {
									JSONObject ele = (JSONObject) embedElement;
									d = ele.get("data").toString();
									String memeType = ele.get("mime_type").toString();
								}
							}
						}
					}

					JSONArray stepsArray = scenarioObj.getJSONArray("steps");
					JSONArray tags = scenarioObj.getJSONArray("tags");
					String tagName = "";
					for (Object tag : tags) {
						JSONObject tagList = (JSONObject) tag;

						tagName = tagList.getString("name");
						if(tagName.startsWith("@"+projectKey))
							break;

					}
					JSONArray stepStatusList = new JSONArray();
					details.put("scenarioStatus", PASS);
					for (Object stepObj : stepsArray) {
						JSONObject steps = (JSONObject) stepObj;
						String stepStatus = steps.getJSONObject("result").getString("status");
						if (stepStatus.equals("failed")) {
							String stepErrorDetails = steps.getJSONObject("result").getString("error_message");
							stepErrorDetails = stepErrorDetails.replaceAll("[\\n\\t\\r\"]", "");
							if (stepErrorDetails.length() > 700)
								stepErrorDetails = stepErrorDetails.substring(0, 700);
							details.put("scenarioStatus", FAIL);
							details.put("scenarioError", stepErrorDetails);
						}
						stepStatusList.put(stepStatus);
					}
					details.put("stepStatus", stepStatusList);
					details.put("scenarioName", scenarioName);
					details.put("embededdata", d);
					tagObject.put(tagName, details);

					result.put(obj.getString("name"), tagObject);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error while trying to get status of steps and scenarios " + "Exception :" + e);
			Assert.fail("Error while trying to get status of steps and scenarios " + "Exception :" + e);
		}
		return result;
	}
}
