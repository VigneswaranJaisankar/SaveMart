package com.scripted.configurations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import org.apache.commons.configuration.PropertiesConfiguration;

import org.apache.logging.log4j.core.config.Configurator;

import com.scripted.Allure.AllureReport;
import com.scripted.Email.Sendmail;
import com.scripted.Enablevoice.VoiceEnabledReport;
import com.scripted.Extent.ExtentUtils;
import com.scripted.PDF.CucumberPDF;
import com.scripted.dataload.PropertyDriver;
import com.scripted.generic.FileUtils;
import com.scripted.integration.JiraUtils;
import com.scripted.selfhealing.HealingConfig;

public class SkriptmateConfigurations {

	//static String log4j_Path = "src/main/resources/LogConf/log4j2.properties";
	static String log4j_Path = "src/main/resources/LogConf/log4j3.xml";
	static String reportLog4j="src/main/resources/LogConf/log4j3.xml";
	static String extentProFile_Path = "src/main/resources/extent.properties";
	static String skrimateConfig_Path = "SkriptmateConfigurations/SkriptmateConfig.Properties";
	static String logPath = "SkriptmateLogs/Skriptmate.log";
	private static String cdir = System.getProperty("user.dir");
	static Boolean shFlag;
	static Boolean impactFlg;

	public static void beforeSuite() {
		try {
			setlog4jConfig(log4j_Path);
			Map<String, String> configMap = readConf();
			configMap.forEach((k, v) -> {
				if (k.equalsIgnoreCase("Skriptmate.Self.Healing")) {
					if (v.equalsIgnoreCase("true"))
						shFlag = true;
					else
						shFlag = false;

				}
				if (k.equalsIgnoreCase("Skriptmate.Impact.Report")) {
					if (v.equalsIgnoreCase("true"))
						impactFlg = true;
					else
						impactFlg = false;

				}
				
				
			if (configMap.get("Skriptmate.allure.clear").equalsIgnoreCase("true")) {
				File dirPath = new File(FileUtils.getCurrentDir() + File.separator + "allure-results");
				FileUtils.deleteDirectory(dirPath);
			}
			if (configMap.get("Skriptmate.extent.report").equalsIgnoreCase("true")) {
				try {
					//ExtentUtils.setExtentPropValue(extentProFile_Path);
					ExtentUtils.setSparkExtentPropValue(extentProFile_Path);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			/*
			 * if(configMap.get("Skriptmate.Jira.Xray.ExtractScenatio").equalsIgnoreCase(
			 * "true")) { try { XrayAPIClient client = new XrayAPIClient(); PropertyDriver
			 * propDriver = new PropertyDriver(); propDriver.setPropFilePath(
			 * "src/main/resources/Integrations/Jira/Xray/Xray.properties"); String id =
			 * PropertyDriver.readProp("TC_id"); String[] arrOfId = id.split(",");
			 * 
			 * for (String a : arrOfId) XrayAPIClient.getScenario(a);
			 * 
			 * } catch (Exception e) {
			 * 
			 * e.printStackTrace(); } }
			 */
			});
			HealingConfig.setSlfHelngProp(shFlag,impactFlg );
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public static void afterSuite() {
		Map<String, String> configMap = readConf();
		
		try {			
			if (configMap.get("Skriptmate.Self.Healing").equalsIgnoreCase("true"))
				try {
					HealingConfig.afterSuite();

				} catch (Exception e) {
					e.printStackTrace();
				}
			if (configMap.get("Skriptmate.allure").equalsIgnoreCase("true"))
				try {
					AllureReport.customizeReport();

				} catch (Exception e) {
					e.printStackTrace();
				}
			if (configMap.get("Skriptmate.extent.report").equalsIgnoreCase("true")) {
				try {

					//ExtentUtils.copyExtentLogo();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(configMap.get("Skriptmate.enable.reportportal").equalsIgnoreCase("true")) {
				try {
					configureReportPortalLog(reportLog4j);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (configMap.get("Skriptmate.PDF.report").equalsIgnoreCase("true")) {
				try {

					CucumberPDF.ExportJsondataPdf("target/cucumber.json");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (configMap.get("Skriptmate.allure.voice").equalsIgnoreCase("true")) {
				try {
					VoiceEnabledReport.createAllureAudio("target/cucumber.json");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (configMap.get("Skriptmate.extent.voice").equalsIgnoreCase("true")) {
				try {
					VoiceEnabledReport.createExtentAudio("target/cucumber.json");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			 if (configMap.get("Skritmate.Jira.Zephyr.ExecutionStatusUpdate").equalsIgnoreCase("true")) {
			       JiraUtils.uploadResultsFromCucumberJson("target/cucumber.json"); } 
			 
			 if (configMap.get("Skriptmate.Report.enableMail").equalsIgnoreCase("true")) {
				  Sendmail.send("src/main/resources/Reporting/Email/Email.properties","target/cucumber.json");
			 }
			 /*if
			 * (configMap.get("Skriptmate.TestRail.ExecutionStatusUpdate").equalsIgnoreCase(
			 * "true")) {
			 * TestRailUtils.uploadResultsFromCucumberJson("target/cucumber.json"); } if
			 * (configMap.get("Skriptmate.Jira.Xray.ExecutionStatusUpdate").equalsIgnoreCase
			 * ("true")) { XrayUtils.uploadResultsFromCucumberJson("target/cucumber.json");
			 * } 
			 * if (configMap.get("Skriptmate.qTest.ExecutionStatusUpdate").equalsIgnoreCase(
			 * "true")) { qTestManagement client=new qTestManagement(); try { try {
			 * client.updateTestCaseStatus("target/cucumber.json"); } catch (Exception e) {
			 * 
			 * e.printStackTrace(); } } catch (Exception e) {
			 * 
			 * e.printStackTrace(); } } if
			 * (configMap.get("Skriptmate.ADO.ExecutionStatusUpdate").equalsIgnoreCase(
			 * "true")) { ADOTestManagement client=new ADOTestManagement(); try { try {
			 * client.updateTestCaseStatus("target/cucumber.json"); } catch (Exception e) {
			 * 
			 * e.printStackTrace(); } } catch (Exception e) {
			 * 
			 * e.printStackTrace(); } }
			 * 
			 */
			// });

			// Will kill all the open driver after the suite execution if any
			killDrivers();
			
		} catch (Exception e) {

		}
	}

	public static Map<String, String> readConf() {
		PropertyDriver propertyDriver = new PropertyDriver();
		propertyDriver.setPropFilePath(skrimateConfig_Path);
		return propertyDriver.readProp();
	}

	public static void setLog4jPropValue(String log4jPropFilePath) throws Exception {
		PropertiesConfiguration conf = new PropertiesConfiguration(log4jPropFilePath);
		conf.setProperty("appender.file.File", cdir + File.separator + logPath);
		conf.save();
	}

	public static void setlog4jConfig(String log4jPropFileLoc) throws Exception {
		String log4jPropFilePath = cdir + File.separator + log4jPropFileLoc;
		//setLog4jPropValue(log4jPropFilePath);
		//InputStream log4j = new FileInputStream(log4jPropFilePath);
		//PropertyConfigurator.configure(log4j);
		Configurator.initialize(null,log4jPropFilePath);
	}

	public static void configureReportPortalLog(String path) {

		String log4jPropFilePath = cdir + "\\" + path;
		Configurator.initialize(null, log4jPropFilePath);
	}
	public static void killDrivers() {
		Process process;
		try {
			String line;
			String pidInfo = "";
			process = Runtime.getRuntime().exec(System.getenv("windir") + "/system32/" + "tasklist.exe");
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = input.readLine()) != null) {
				pidInfo += line;
			}
			input.close();
			if (pidInfo.contains("ChromeDriver.exe")) {
				Runtime.getRuntime().exec("taskkill /f /im ChromeDriver.exe");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
