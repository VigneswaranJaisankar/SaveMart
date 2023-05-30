/**
 * This is main class which would be referenced across the automated test.
 */
package com.scripted.accessibility.main;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import com.deque.axe.AXE;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.scripted.accessibility.model.PageViolations;
import com.scripted.accessibility.model.ReportData;
import com.scripted.accessibility.model.RuleViolations;
import com.scripted.accessibility.reporting.AccessibilityReportEngine;

/**
 * The Class AccessibilityCop Generate Accessibility Report for CurrentPage and
 * also Generate Overall Accessibility Report.
 */
public class Accessibility {

	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/** The driver. */
	private static WebDriver driver;
	
	private static int critical = 0 ;
	
	private static int major = 0 ;
	
	private static int minor = 0 ;

	/**
	 * Constructor of Accessibility.
	 * 
	 * @param pDriver gets the driver object of current test
	 */
	public  Accessibility(final WebDriver pDriver) {
		driver = pDriver;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Generates Accessibility Report Method generated the Report on the Current
	 * Page with all the Page Violation Rules. It gets the scriptUrl for analyzing
	 * pages according to violation Rules. PageViolation class object is invoked for
	 * setting ViolationCount, PageTitle, PageUrl and PassedRules. RuleViolation
	 * class object is invoked for setting violation Count, pageTitle, helpUrl,
	 * impact, Id and Report data is used to Report totalPages, passed, failed,
	 * totalFlaws, totalRuleViolation, violationAnalysis List and
	 * overallPassedRules.
	 * 
	 * @param title gets the title of the page.
	 * 
	 */
	public JSONArray generateAccessibilityReportForCurrentPage(final String url) {
		URL scriptUrl = null;
		StringWriter accessibilityWriter = new StringWriter();
		JSONArray violations = new JSONArray();
		try {
			scriptUrl = new File(System.getProperty("user.dir").concat("/src/main/resources/Accessibility/Artefacts/HtmlTemplates/axe.min.js")).toURI().toURL();

		} catch (MalformedURLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}

		try {
			final PageViolations pageViolations = new PageViolations();
			final List<RuleViolations> ruleViolationsList = new ArrayList<RuleViolations>();
			final JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).analyze();

			JSONArray violationReport = responseJSON.getJSONArray("violations");

			for (int i = 0; i < violationReport.length(); i++) {
				JSONObject siteDetails = violationReport.getJSONObject(i);
				siteDetails.put("url", url);
				siteDetails.put("title", driver.getTitle());
				siteDetails.put("visitedtime", new Date().toString());
				violations.put(siteDetails);

			}

			violations = responseJSON.getJSONArray("violations");
			final JSONArray passes = responseJSON.getJSONArray("passes");
			ReportData.totalPages++;

			pageViolations.setViolationCount(violations.length());
			pageViolations.setPageTitle(driver.getTitle());
			pageViolations.setPageUrl(url);
			pageViolations.setPassedRules(Integer.toString(passes.length()));

			if (violations.length() == 0) {
				ReportData.passed = ReportData.passed + 1;
			} else {
				ReportData.failed++;
			}

			ReportData.overallPassedRules = ReportData.overallPassedRules + passes.length();
			ReportData.totalRuleViolations = ReportData.totalRuleViolations + violations.length();

			for (int i = 0; i < violations.length(); i++) {
				final RuleViolations ruleViolations = new RuleViolations();
				final JSONObject ruleViolation = violations.getJSONObject(i);
				String desc = ruleViolation.get("description").toString();
				if (desc.contains("<") && desc.contains(">")) {
					desc = desc.replaceAll("<", "&lt;");
					desc = desc.replaceAll(">", "&gt;");
					ruleViolations.setDescription(desc);
				} else {
					ruleViolations.setDescription(desc);
				}
				ruleViolations.setHelpURL(ruleViolation.get("helpUrl").toString());
				ruleViolations.setImpact(ruleViolation.get("impact").toString());
				ruleViolations.setId(ruleViolation.get("id").toString());
						
				final JSONArray tagsArray = ruleViolation.getJSONArray("tags");
				String tags = "";
				for (int j = 0; j < tagsArray.length(); j++)
					tags += tagsArray.getString(j) + "\n";

				ruleViolations.setRule(tags);

				final JSONArray violations_list = ruleViolation.getJSONArray("nodes");
				for (int k = 0; k < violations_list.length(); k++) {
					ruleViolations.setViolation(violations_list.getJSONObject(k).get("html").toString());
					if (violations_list.getJSONObject(k).getJSONArray("any").length() > 0) {
						ruleViolations.setViolationMessage(violations_list.getJSONObject(k).getJSONArray("any")
								.getJSONObject(0).getString("message"));
					}
					if (violations_list.getJSONObject(k).getJSONArray("target").length() > 0) {
						ruleViolations.setViolationTarget(
								violations_list.getJSONObject(k).getJSONArray("target").get(0).toString());
					}
					if (violations_list.getJSONObject(k).getJSONArray("any").length() > 0) {
						String impactValue =violations_list.getJSONObject(k).getJSONArray("any").getJSONObject(0).getString("impact");
						if(impactValue.equalsIgnoreCase("critical") || impactValue.equalsIgnoreCase("serious") ) {
							critical++;
						}
							
						if(impactValue.equalsIgnoreCase("major")) {
							major++;
						}
						
						if(impactValue.equalsIgnoreCase("minor")) {
							minor++;
						}
					}
				}
				ruleViolationsList.add(ruleViolations);
			}
			pageViolations.setViolationsList(ruleViolationsList);
			String pageFolderName = pageViolations.getPageTitle();

			// Handle special characters for Page title

			pageFolderName = pageFolderName.replaceAll("\\|", "");
			pageFolderName = pageFolderName.replaceAll("&", "");
			pageFolderName = pageFolderName.replaceAll("$", "");
			pageFolderName = pageFolderName.replaceAll("%", "");
			pageFolderName = pageFolderName.replaceAll("@", "");
			pageFolderName = pageFolderName.replaceAll("'", "");
			pageFolderName = pageFolderName.replaceAll("�", "");

			pageViolations.setPageFolderName(pageFolderName);
			ReportData.violationAnalysis.add(pageViolations);
			
			pageViolations.setcriticalcount(String.valueOf(critical));
			pageViolations.setmajorcount(String.valueOf(major));
			pageViolations.setminorcount(String.valueOf(minor));
			
			AccessibilityReportEngine reportEngine = new AccessibilityReportEngine();
			accessibilityWriter = reportEngine.reportAccessibilityViolations(pageViolations);

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		// return accessibilityWriter;
		LOGGER.info("--violations--" + violations);
		System.out.println("--violations--" + violations);
		System.out.println(String.valueOf(critical));
		System.out.println(String.valueOf(major));
		System.out.println(String.valueOf(minor));
		return violations;
	}

	/**
	 * This generates overall accessibility report using velocityEngine.
	 *
	 */
	public void generateOverallAccessibilityReport() {
		try {
			AccessibilityReportEngine reportEngine = new AccessibilityReportEngine();
			reportEngine.generateOverallAccessibilityReports();

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	public void checkAccessibilityViolations() throws IOException {
		List<String> tags = Arrays.asList("wcag2a", "wcag2aa", "wcag21aa");
		// String reportFile = reportPath + "accessibilityReport";
		AxeBuilder builder = new AxeBuilder();
		builder.withTags(tags);
		Results results = builder.analyze(driver);
		System.out.println(results);
	}
}
