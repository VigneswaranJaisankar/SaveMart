package com.scripted.api;

import java.io.File;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class RequestParams {

	public String uri;
	public String contenttype;
	public String proxy;
	public int port;
	public JSONObject jsonBody;
	public JSONArray jsonArrBody;
	public String RestMethodType;
	public String apiDataPath = "src/main/resources/WebServices/DataFiles";
	public String genJsonResponsePath = "src/main/resources/GenRocket/Output/";
	public String apiJsonRequestPath = "src/main/resources/WebServices/APIJsonRequest/";
    public static Logger LOGGER = LogManager.getLogger(RequestParams.class);
    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_0);
   
	public String getRestMethodType() {
		return RestMethodType;
	}

	public void setRestMethodType(String restMethodType) {
		RestMethodType = restMethodType;
	}

	public JSONObject getJsonbody() {
		return jsonBody;
	}

	public void setJsonbody(String fileName) {
		try {
			String requestFilePath = GetProp.getFilePath(apiJsonRequestPath + fileName + ".txt");
			String content = new String(Files.readAllBytes(Paths.get(requestFilePath)));
			jsonBody = new JSONObject(content);
		} catch (Exception e) {
			LOGGER.error("Exception: " + e);
			Assert.fail("Error while set the Json body : "+e);
			e.printStackTrace();
		}
	}

	
	public void setGenJsonbody(String fileName) {
		try {
			String requestFilePath = GetProp.getFilePath(genJsonResponsePath + fileName + ".json");
			String content = new String(Files.readAllBytes(Paths.get(requestFilePath)));
			jsonArrBody = new JSONArray(content);
			jsonBody = jsonArrBody.getJSONObject(0);
		} catch (Exception e) {
			LOGGER.error("Exception: " + e);
			e.printStackTrace();
		}
	}
	
	public void setJsonbyTemplate(String Templatefile) {

		try {

			File[] dataFiles = new File(apiDataPath).listFiles();

			Map<String, Object> fullDataMap = new HashMap<>();

			for (File file : dataFiles) {
				if (!file.getName().endsWith(".json"))
					continue;

				String content = new String(Files.readAllBytes(file.toPath()));
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> map = mapper.readValue(content, Map.class);
				fullDataMap.putAll(map);

			}

			String json = generateJsonByTemplate(
					"src/main/resources/WebServices/APIJsonRequest/" + Templatefile + ".json", fullDataMap);

			System.out.println(json);
			jsonBody = new JSONObject(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String generateJsonByTemplate(String templateName, Map<String, Object> input) throws Exception {
		String sourceCode = null;

		try {
			Template template = CONFIGURATION.getTemplate(templateName);
			StringWriter writer = new StringWriter();
			template.process(input, writer);
			sourceCode = writer.toString();
		} catch (Exception exception) {
			throw new Exception(
					"Processing failed for template '" + templateName + "' with error: " + exception.getMessage(),
					exception);
		}

		return sourceCode;
	}
	

	public RequestParams(String uri, String contentType, String proxy, int port, String body, String restMethodType) {
		super();
		this.uri = uri;
		this.contenttype = contentType;
		this.proxy = proxy;
		this.port = port;
		//this.body = body;
		this.RestMethodType = restMethodType;
	}

	public String getproxy() {
		return proxy;
	}

	public void setproxyAndPort(String proxy, int port) {
		this.proxy = proxy;
		this.port = port;
	}

	public int getport() {
		return port;
	}

	public void setport(int port) {
		this.port = port;
	}

	public String geturi() {
		return uri;
	}

	public void seturi(String uri) {
		this.uri = uri;
	}

	public String getcontenttype() {
		return contenttype;
	}

	public void setcontenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	public RequestParams() {
	}

}
