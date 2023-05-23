
package com.scripted.brokenlink;

import org.openqa.selenium.WebDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.scripted.web.BrowserDriver;

import java.io.FileReader;
import java.io.FileWriter;
import java.net.HttpURLConnection;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;;

public class BrokenLink {
	static JSONObject resulltObject = new JSONObject();
	static JSONObject brokenObject = new JSONObject();
	static JSONArray pagearray = new JSONArray();
	static String jsonReportName;
	static String jsonpath;
	private static String cdir = System.getProperty("user.dir");
	double Passcount = 0;
	double Failcount = 0;
	static BrokenLinkReport linkinfo = new BrokenLinkReport();
	static String senariostatus;
	Map<String, String> resultmap = new HashMap<String, String>();
	static int scannedUrlCount = 0;
	static int totalpasscount = 0;
	static int totalfailcount = 0;
	static int pagepasscount = 0;
	static int pagefailcount = 0;
	private static WebDriver driver;

	public BrokenLink(final WebDriver pDriver) {
		driver = pDriver;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void brokenLinkValidate(String pagename) {
		int linkcount;
		int responseCode = 0;
		int id = 0;
		String scanLinkUrl = null;
		// BrowserDriver.launchWebURL(driver.getCurrentUrl());
		linkinfo.setApplicationurl(driver.getCurrentUrl());
		JSONArray linkarray = new JSONArray();
		try {
			List<WebElement> links = BrowserDriver.getDriver().findElements(By.tagName("a"));
			linkcount = links.size();
			System.out.println("total link count:"+linkcount);
			for (int i = 0; i < links.size(); i++) {

				WebElement ele = links.get(i);
				String linkUrl = ele.getAttribute("href");
				if (linkUrl != null) {
					if (linkUrl.startsWith("http")) {
						id++;
						scanLinkUrl = linkUrl;
						if(linkUrl.contains("#")) {
							linkUrl=linkUrl.replace("#", "%23");
							}
						linkUrl = linkUrl.trim();
						trustAllHosts();
						URL url = new URL(new String(linkUrl));
						url.toURI();
						HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
						httpURLConnect.setConnectTimeout(5000);
						httpURLConnect.connect();
						responseCode = httpURLConnect.getResponseCode();
						if (responseCode >= 400) {
							senariostatus = "Fail";
						} else {
							senariostatus = "Pass";
						}

					}
				}
				if ((linkUrl != null) && !linkUrl.startsWith("javascript")) {
					linkarray.add(generateJsonarray(scanLinkUrl, senariostatus, Integer.toString(responseCode)));

				}
			}
			System.out.println("Brokenlinks is validated in current url");
			resulltObject = writeToJson(pagename, linkarray);
			pagearray.add(resulltObject);
			brokenObject = GenerateRepotJson(pagearray);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JSONObject GenerateRepotJson(JSONArray dataarray) {
		JSONObject MainObject = new JSONObject();
		MainObject.put("BrokenLink Data", dataarray);
		return MainObject;

	}

	public static JSONObject writeToJson(String pagename, JSONArray urls) {
		JSONObject Object = new JSONObject();
		Object.put("Pagename", pagename);
		Object.put("PageUrl", driver.getCurrentUrl());
		Object.put("Urls", urls);
		return Object;

	}

	public static JSONObject generateJsonarray(String linkurl, String Status, String responceCode) {
		JSONObject urlobject = new JSONObject();
		urlobject.put("linkurl", linkurl);
		urlobject.put("Status", Status);
		urlobject.put("responseCode", responceCode);
		return urlobject;

	}

	public static void saveJson(JSONObject Object) {
		try(FileWriter file = new FileWriter(jsonpath + jsonReportName + ".json")) {
			jsonpathcreate();
			file.write(Object.toJSONString());
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void jsonpathcreate() {
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss_ms_aa");
		String dateString = dateFormat.format(new Date()).toString();
		jsonReportName = "brokenLinkjsonResults_" + dateString;
		jsonpath = cdir + "/brokenLink-results/";
		com.scripted.generic.FileUtils.makeDirs(jsonpath);
	}

	public void brokenLinkReportGen() {

		JSONParser jsonParser = new JSONParser();
		int pagecount = 0;
		int urlcount = 0;
		String pageurl;
		String pagename;
		String linkurl;
		String status;
		String responseCode;
		int pagepasscount = 0;
		int pagefailcount = 0;
		try {
			saveJson(brokenObject);
			linkinfo.reportPathCreate();
			FileReader reader = new FileReader(jsonpath + jsonReportName + ".json");
			Object obj = jsonParser.parse(reader);
			JSONObject JsonObj = (JSONObject) obj;
			JSONArray jsonarray = (JSONArray) JsonObj.get("BrokenLink Data");
			pagecount = jsonarray.size();
			for (int i = 0; i < pagecount; i++) {
				linkinfo.linkBodyEmpty();
				pagepasscount = 0;
				pagefailcount = 0;
				pageurl = (String) ((JSONObject) jsonarray.get(i)).get("PageUrl").toString();
				linkinfo.setApplicationurl(pageurl);
				pagename = (String) ((JSONObject) jsonarray.get(i)).get("Pagename").toString();
				JSONArray scanUrls = (JSONArray) ((JSONObject) jsonarray.get(i)).get("Urls");
				urlcount = scanUrls.size();
				for (int j = 0; j < urlcount; j++) {
					scannedUrlCount++;
					linkurl = (String) ((JSONObject) scanUrls.get(j)).get("linkurl").toString();
					status = (String) ((JSONObject) scanUrls.get(j)).get("Status").toString();
					responseCode = (String) ((JSONObject) scanUrls.get(j)).get("responseCode").toString();
					if (status.equalsIgnoreCase("Pass")) {
						pagepasscount++;
						totalpasscount++;
					} else {
						pagefailcount++;
						totalfailcount++;
					}
					linkinfo.linkbody(status, linkurl, responseCode);

				}

				linkinfo.pageBody(pagename, Integer.toString(pagepasscount), Integer.toString(pagefailcount),
						Integer.toString(i));
				linkinfo.CreatePageBody();

			}

			linkinfo.setTotalPages(Integer.toString(pagecount));
			linkinfo.setScannedlinkCount(Integer.toString(scannedUrlCount));
			linkinfo.setTotalPasscount(Integer.toString(totalpasscount));
			linkinfo.setTotalFailcount(Integer.toString(totalfailcount));
			
			linkinfo.reportGen();
			System.out.println("report generated");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void trustAllHosts() {
		// TODO Auto-generated method stub
		try {
			TrustManager[] trustAllCerts = new TrustManager[] { new X509ExtendedTrustManager() {
				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string, Socket socket)
						throws CertificateException {

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string, Socket socket)
						throws CertificateException {

				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] xcs, String string, SSLEngine ssle)
						throws CertificateException {

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] xcs, String string, SSLEngine ssle)
						throws CertificateException {

				}

			} };

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
