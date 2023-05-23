package com.scripted.zephyr;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.testng.Assert;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientResponse;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;


public class ZephyrTestCaseManagementCloud extends zephyrConfig {
	Logger log = LogManager.getLogger(this.getClass().getSimpleName());
	static String failEmbData = null;
	public String doGet(String url) throws Exception {
		Client client = getClient().build();
		Response response = null;
		if (url.contains(ZAPI_BASEURL)) {
			ZFJCloudRestClient client1 = ZFJCloudRestClient.restBuilder(ZAPI_BASEURL, ACCESS_KEY, SECRET_KEY, USER_NAME)
					.build();
			JwtGenerator jwtGenerator = client1.getJwtGenerator();
			getInstance(url);
			URI uri = new URI(url);
			int expirationInSec = 1000;
			String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);
			System.out.println("jwt" + jwt);
			System.out.println("zapiAccessKey" + ACCESS_KEY);
			response = client.target(url).request().header("Authorization", jwt).header("zapiAccessKey", ACCESS_KEY)
					.get();

		} else {
			response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE)
					.header("Authorization", JIRA_AUTHORIZATION).get();

		}
		log.info("GET : " + response.toString());
		if (response.getStatus() != 200) {
			throw new Exception("Unable to connect to JIRA");
		}
		return response.readEntity(String.class);
	}
	
	
	public String doGetcycle(String url) throws Exception {
		Client client = getClient().build();
		Response response = null;
		if (url.contains(ZAPI_BASEURL)) {
			ZFJCloudRestClient client1 = ZFJCloudRestClient.restBuilder(ZAPI_BASEURL, ACCESS_KEY, SECRET_KEY, USER_NAME)
					.build();
			JwtGenerator jwtGenerator = client1.getJwtGenerator();
			getInstance(url);
			URI uri = new URI(url);
			int expirationInSec = 1000;
			String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);
			System.out.println("jwt" + jwt);
			System.out.println("zapiAccessKey" + ACCESS_KEY);
			response = client.target(uri).request().header("Authorization", jwt).header("zapiAccessKey", ACCESS_KEY)
					.get();

		} else {
			response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE)
					.header("Authorization", JIRA_AUTHORIZATION).get();

		}
		log.info("GET : " + response.toString());
		if (response.getStatus() != 200) {
			throw new Exception("Unable to connect to JIRA");
		}
		return response.readEntity(String.class);
	}
	
	
	@SuppressWarnings("static-access")
	public static HttpsURLConnection trustCert(HttpsURLConnection conn) {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
            public void checkServerTrusted(X509Certificate[] certs, String authType) { }
        } };

        SSLContext sc = null;
		try {
			if(sc == null) {
			sc = SSLContext.getInstance("TLS");
			 sc.init(null, trustAllCerts, new java.security.SecureRandom());
			 conn.setDefaultSSLSocketFactory(sc.getSocketFactory());
			}
		} catch (NoSuchAlgorithmException |KeyManagementException  | NullPointerException e) {
			e.printStackTrace();
		} 
      
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) { return true; }
        };
        conn.setDefaultHostnameVerifier(allHostsValid);
        return conn;
	}

	
	public static void getInstance(String hostUrl) {
		HttpsURLConnection conn = null;
		URL url = null ;
		try {
			url = new URL("https://prod-api.zephyr4jiracloud.com/connect");
			conn = (HttpsURLConnection) url.openConnection();
			trustCert(conn);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	
	public String doDelete(String url) throws Exception {
		Client client = getClient().build();
		Response response = null;
		if (url.contains(ZAPI_BASEURL)) {
			ZFJCloudRestClient client1 = ZFJCloudRestClient.restBuilder(ZAPI_BASEURL, ACCESS_KEY, SECRET_KEY, USER_NAME)
					.build();
			JwtGenerator jwtGenerator = client1.getJwtGenerator();
			URI uri = new URI(url);
			int expirationInSec = 500;
			String jwt = jwtGenerator.generateJWT("DELETE", uri, expirationInSec);
			response = client.target(uri).request().header("Authorization", jwt).header("zapiAccessKey", ACCESS_KEY)
					.get();

		} else {
			response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE)
					.header("Authorization", JIRA_AUTHORIZATION).delete();

		}
		if (response.getStatus() != 200) {
			throw new Exception("Unable to connect to JIRA");
		}
		return response.readEntity(String.class);
	}

	@SuppressWarnings("rawtypes")
	public String doPost(String url, String payload) throws Exception {
		Client client = getClient().build();
		Entity payloadEntity = Entity.json(payload);
		Response response = null;
		if (url.contains(ZAPI_BASEURL)) {
			ZFJCloudRestClient client1 = ZFJCloudRestClient.restBuilder(ZAPI_BASEURL, ACCESS_KEY, SECRET_KEY, USER_NAME)
					.build();
			JwtGenerator jwtGenerator = client1.getJwtGenerator();
			URI uri = new URI(url);
			int expirationInSec = 500;
			String jwt = jwtGenerator.generateJWT("POST", uri, expirationInSec);
			response = client.target(uri).request().header("Authorization", jwt).header("zapiAccessKey", ACCESS_KEY)
					.post(payloadEntity);

		} else {
			response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE)
					.header("Authorization", JIRA_AUTHORIZATION).post(payloadEntity);

		}
		if (response.getStatus() != 200) {
			if (response.getStatus() != 201) {
				throw new Exception("Unable to connect to JIRA");
			}
		}
		return response.readEntity(String.class);
	}

	@SuppressWarnings("rawtypes")
	public String doAttachPost(String url, File file) throws Exception {
		Response response = null;

		ZFJCloudRestClient client1 = ZFJCloudRestClient.restBuilder(ZAPI_BASEURL, ACCESS_KEY, SECRET_KEY, USER_NAME)
				.build();
		JwtGenerator jwtGenerator = client1.getJwtGenerator();
		URI uri = new URI(url);
		int expirationInSec = 1000;
		String jwt = jwtGenerator.generateJWT("POST", uri, expirationInSec);

		final Client client = getClient().register(MultiPartFeature.class).build();

		final FileDataBodyPart filePart = new FileDataBodyPart("image", file);
		FormDataMultiPart multipart = new FormDataMultiPart();

		multipart.bodyPart(filePart);

		if (url.contains(ZAPI_BASEURL)) {
			response = client.target(url).request().header("Authorization", jwt).header("zapiAccessKey", ACCESS_KEY)
					.post(Entity.entity(multipart, multipart.getMediaType()));
			multipart.close();
		} else {
			response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE)
					.header("Authorization", JIRA_AUTHORIZATION)
					.post(Entity.entity(multipart, multipart.getMediaType()));
		}
		if (response.getStatus() != 200) {
			if (response.getStatus() != 201) {
				throw new Exception("Unable to connect to JIRA");
			}
		}
		return response.readEntity(String.class);
	}

	@SuppressWarnings("rawtypes")
	public String doPut(String url, String payload) throws Exception {
		Client client = getClient().build();
		Entity payloadEntity = Entity.json(payload);
		Response response = null;
		if (url.contains(ZAPI_BASEURL)) {
			ZFJCloudRestClient client1 = ZFJCloudRestClient.restBuilder(ZAPI_BASEURL, ACCESS_KEY, SECRET_KEY, USER_NAME)
					.build();
			JwtGenerator jwtGenerator = client1.getJwtGenerator();
			URI uri = new URI(url);
			int expirationInSec = 500;
			String jwt = jwtGenerator.generateJWT("PUT", uri, expirationInSec);
			response = client.target(uri).request().header("Authorization", jwt).header("zapiAccessKey", ACCESS_KEY)
					.put(payloadEntity);
			
		} else {
			response = client.target(url).request(MediaType.APPLICATION_JSON_TYPE)
					.header("Authorization", JIRA_AUTHORIZATION).put(payloadEntity);
			
		}
		if (response.getStatus() != 200) {
			throw new Exception("Unable to connect to JIRA");
		}
		return response.readEntity(String.class);
	}

	public void updateTestCaseAndStepStatus(String cucumberJsonPath) {
		try {
			ZephyrAPI zapi = new ZephyrAPI();
			JiraAPI jira = new JiraAPI();
			CucumberJsonExtractor cucm = new CucumberJsonExtractor();
			JSONObject obj = cucm.getScenarioAndStepsStatus(cucumberJsonPath);
			for (String feature : obj.keySet()) {
				JSONObject featureObject = obj.getJSONObject(feature);
				for (String testCase : featureObject.keySet()) {
					if(!testCase.equals(""))
					{
					String jiraKey = testCase.replace("@", "").trim();
					int issueId = jira.getIssueId(jiraKey);
					String executionId = zapi.createExecution(issueId);
					if (featureObject.getJSONObject(testCase).getString("scenarioStatus").equalsIgnoreCase("Passed")) {
						zapi.updateExecutionStatus(issueId,
								featureObject.getJSONObject(testCase).getString("scenarioStatus"), "", executionId);
					} else {
						zapi.updateExecutionStatus(issueId,
								featureObject.getJSONObject(testCase).getString("scenarioStatus"),
								featureObject.getJSONObject(testCase).getString("scenarioError"), executionId);
						String tsName = featureObject.getJSONObject(testCase).getString("scenarioName");
						String errMsg = featureObject.getJSONObject(testCase).getString("scenarioError");
						
						if (featureObject.getJSONObject(testCase).has("embededdata")) {
							failEmbData = featureObject.getJSONObject(testCase).getString("embededdata");
						}
						if (DEFECT_FLAG.equalsIgnoreCase("true"))
							CreateBug.createDefect(tsName, errMsg, failEmbData);
					}
					if (STEP_STATUS_FLAG.equalsIgnoreCase("true")) {
						HashMap<String, List<String>> stepEmbedData = CucumberJsonExtractor.getEmbedDataMap();
						JSONObject stepObj = zapi.getTestStepIds(jiraKey, executionId, issueId);
						
						for (int i = 0; i < stepObj.getJSONArray("testResultId").length(); i++) {
							if (STEP_SCREENSHOT_FLAG.equalsIgnoreCase("true")) {
								String stepName = featureObject.getJSONObject(testCase).getJSONArray("stepNames")
										.getString(i);
								List<String> embedData = stepEmbedData.get(stepName);
								if(embedData!=null)
								{
								for (int j = 0; j < embedData.size(); j++) {
									ZephyrAPI.addStepsScreenshots(issueId,
											stepObj.getJSONArray("testResultId").getString(i), embedData.get(j));
								}
								}
							}
							if (featureObject.getJSONObject(testCase).getString("scenarioStatus")
									.equalsIgnoreCase("Passed")) {
								zapi.updateTestStepStatus(
										featureObject.getJSONObject(testCase).getJSONArray("stepStatus").getString(i),
										"", issueId, stepObj.getJSONArray("testStepId").getString(i), executionId,
										stepObj.getJSONArray("testResultId").getString(i));
							} else {
								if (featureObject.getJSONObject(testCase).getJSONArray("stepStatus").getString(i)
										.equalsIgnoreCase("Passed")) {
									zapi.updateTestStepStatus(
											featureObject.getJSONObject(testCase).getJSONArray("stepStatus").getString(
													i),
											"", issueId, stepObj.getJSONArray("testStepId").getString(i), executionId,
											stepObj.getJSONArray("testResultId").getString(i));

								} else {
									zapi.updateTestStepStatus(
											featureObject.getJSONObject(testCase).getJSONArray("stepStatus")
													.getString(i),
											featureObject.getJSONObject(testCase).getString("scenarioError"), issueId,
											stepObj.getJSONArray("testStepId").getString(i), executionId,
											stepObj.getJSONArray("testResultId").getString(i));
									if(failEmbData!=null)
									{
										ZephyrAPI.addStepsScreenshots(issueId,
												stepObj.getJSONArray("testResultId").getString(i),failEmbData);
									}
								}
							}
						}
					}
					}
				
			}
			}
		} catch (Exception e) {
			log.info(e.getStackTrace());
			log.error("Unable to Update the Test case and step status" + e);
			Assert.fail("Unable to Update the Test case and step status" + e);
		}
	}

	private ClientBuilder getClient() {

		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
			public void checkClientTrusted(X509Certificate[] certs, String authType) { }
			public void checkServerTrusted(X509Certificate[] certs, String authType) { }
		} };

		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
		} catch (NoSuchAlgorithmException |KeyManagementException e) {
			e.printStackTrace();
		} 
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) { return true; }
		};

		ClientBuilder builder = ClientBuilder.newBuilder().sslContext(sc).hostnameVerifier(allHostsValid);

		return builder;


}

	
}
