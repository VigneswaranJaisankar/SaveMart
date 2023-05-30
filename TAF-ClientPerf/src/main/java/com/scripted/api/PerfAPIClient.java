package com.scripted.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.scripted.dataload.PropertyDriver;
import com.scripted.reporting.ReportGeneration;

public class PerfAPIClient {
	public static JSONObject resultJson = new JSONObject();
	private static String cdir = System.getProperty("user.dir");
	public static Logger LOGGER = LogManager.getLogger(PerfAPIClient.class);
	public static String reportName;
	public static List<String> pageNamesList = new ArrayList<>();
	public static int mobStatus;
	public static int deskStatus;

	public static void doPerfTest(String pageName, String pageURL) {
		try {
			LOGGER.info("Executing Performance Test for the Page :" + pageName + "  URL :" + pageURL);
			PropertyDriver propDriver = new PropertyDriver();
			propDriver.setPropFilePath("/src/main/resources/ClientSidePerformance/Properties/ClientSidePerformance.properties");
			if (PropertyDriver.readProp().containsKey("https.proxyHost")
					&& PropertyDriver.readProp().containsKey("https.proxyPort")) {
				String host = PropertyDriver.readProp("https.proxyHost");
				String port = PropertyDriver.readProp("https.proxyPort");
				if (!host.isEmpty() && !port.isEmpty()) {
					System.setProperty("https.proxyHost", host);
					System.setProperty("https.proxyPort", port);
					System.setProperty("http.proxyHost", host);
					System.setProperty("http.proxyPort", port);
				}
			}
			String strategyDesktop = PropertyDriver.readProp("perf.strategy.desktop");
			String strategyMobile = PropertyDriver.readProp("perf.strategy.mobile");
			if (strategyMobile.equalsIgnoreCase("true")) {
				Object result = getMobilePerfJson(pageURL);
				if (mobStatus == 200) {
					resultJson.put(pageName + " : Mobile", result);
					pageNamesList.add(pageName + " : Mobile");
				}
			}
			if (strategyDesktop.equalsIgnoreCase("true")) {
				Object result = getDesktopPerfJson(pageURL);
				if (deskStatus == 200) {
					resultJson.put(pageName + " : Desktop", result);
					pageNamesList.add(pageName + " : Desktop");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error Occurred While doing Perfomance Testing for the URL " + pageURL + "Exception" + e);
		}
	}

	public static List<String> getPageNamesList() {
		return pageNamesList;
	}

	public static Object getMobilePerfJson(String WebUrl) {
		Object result = null;
		try {

			URL url = new URL(
					"https://www.googleapis.com/pagespeedonline/v5/runPagespeed?url=" + WebUrl + "&strategy=mobile");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			InputStream istream;
			mobStatus = conn.getResponseCode();
			istream = conn.getInputStream();
			String text = "";
			if (istream != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(istream, "UTF-8"));

				String line;
				while ((line = reader.readLine()) != null) {
					text += line;
					text += System.getProperty("line.separator");
				}
				reader.close();
			}
			if (text != "") {
				result = JSONValue.parse(text);
			} else {
				result = new JSONObject();
			}
			LOGGER.info("GET : " + result.toString());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error Occurred While doing Perfomance Testing for the URL " + WebUrl + "Exception" + e);
		}

		return result;
	}

	public static Object getDesktopPerfJson(String WebUrl) {
		Object result = null;
		try {
			URL url = new URL("https://www.googleapis.com/pagespeedonline/v5/runPagespeed?url=" + WebUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			InputStream istream;
			deskStatus = conn.getResponseCode();
			istream = conn.getInputStream();
			String text = "";
			if (istream != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(istream, "UTF-8"));

				String line;
				while ((line = reader.readLine()) != null) {
					text += line;
					text += System.getProperty("line.separator");
				}
				reader.close();
			}
			if (text != "") {
				result = JSONValue.parse(text);
			} else {
				result = new JSONObject();
			}
			LOGGER.info("GET : " + result.toString());

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("Error Occurred While doing Perfomance Testing for the URL " + WebUrl + "Exception" + e);
		}

		return result;
	}

	public static void getOverallClientPerfReport() {
		try {
			String jsonpath = cdir + "/clientperf-results/";
			DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss_ms_aa");
			String dateString = dateFormat.format(new Date()).toString();
			String jsonReportName = "jsonResults_" + dateString;
			if (!new File(jsonpath).exists())
				com.scripted.generic.FileUtils.makeDirs(jsonpath);
			FileWriter file = new FileWriter(jsonpath + jsonReportName + ".json");
			file.write(resultJson.toJSONString());
			file.close();
			reportName = jsonpath + jsonReportName + ".json";
			LOGGER.info("Performance testing JSON result generated in the file location :" + jsonpath + jsonReportName
					+ ".json");
			ReportGeneration.reportWriter();
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.info("Error Occurred while getting overall pages performace testing result");
		}
	}

	public static String getReportName() {
		return reportName;
	}

	public static String getRedirectUrl(String url) {

		URL urlTmp = null;
		String redUrl = null;
		HttpURLConnection connection = null;

		try {
			urlTmp = new URL(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		try {
			connection = (HttpURLConnection) urlTmp.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			connection.getResponseCode();
		} catch (IOException e) {
			e.printStackTrace();
		}

		redUrl = connection.getURL().toString();
		connection.disconnect();

		return redUrl;
	}

}
