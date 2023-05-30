/**
 * This is the reporting engine class.
 */
package com.scripted.accessibility.reporting;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.scripted.accessibility.model.PageViolations;
import com.scripted.accessibility.model.ReportData;
import com.scripted.accessibility.model.RuleViolations;

/**
 * The Class AccessibilityReportEngine is reporting engine used for generating
 * reports.
 */
public class AccessibilityReportEngine {
	
	private String criticalCount;
	private String majorCount;
	private String minorCount;

	/** The Constant LOGGER. */
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static String currentTimeStamp = null;

	/**
	 * Report accessibility violations method gets the pageViolations lists and
	 * reports it accordingly using VelocityEngine.
	 *
	 * @param pageViolations - gets the page violations list.
	 * @return
	 */

	// Changed return type from void to StringWriter - for Accessibility testing -
	// on 27/05/2019

	public StringWriter reportAccessibilityViolations(final PageViolations pageViolations) {
		try {
			
			criticalCount = pageViolations.getCriticalCount();
			majorCount = pageViolations.getMajorCount();
			minorCount = pageViolations.getMinorCount();			
			CurrentTime();
			String pageTitle = pageViolations.getPageTitle();
			String URL = pageViolations.getPageUrl();
			File directory = new File("Skriptmate Accessibility Report");
			if (!directory.exists())
				directory.mkdir();
			String source = System.getProperty("user.dir") + "/src/main/resources/Accessibility/Artefacts/ImgStyle";
			File srcDir = new File(source);
			String destination = System.getProperty("user.dir") + "/SkriptmateReport/Accessibility/"
					+ getCurrentTimeStamp();
			File destDir = new File(destination);

			try {
				FileUtils.copyDirectory(srcDir, destDir);
			} catch (IOException e) {
				e.printStackTrace();
			}

			generateRuleViolationsInDetail(pageViolations.getViolationsList(), URL, pageViolations.getPageFolderName());
			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.init();

			List<Map<String, String>> rulesList = new ArrayList<Map<String, String>>();
			Template template = velocityEngine.getTemplate(
					"./src/main/resources/Accessibility/Artefacts/HtmlTemplates/accessibility_report_page_summary_template.vm");

			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("Title", pageTitle);
			velocityContext.put("Url", URL);
			velocityContext.put("Date", new Date().toString());

			for (RuleViolations ruleViolation : pageViolations.getViolationsList()) {
				Map<String, String> tempMap = new HashMap<String, String>();
				tempMap.put("Rule", ruleViolation.getRule());
				tempMap.put("Count", Integer.toString(ruleViolation.getViolationList().size()));
				tempMap.put("Description", ruleViolation.getDescription());
				tempMap.put("InternalReport", pageViolations.getPageFolderName() + File.separator
						+ ruleViolation.getId() + "_Violation_Report.html");
				rulesList.add(tempMap);
			}
			velocityContext.put("RulesList", rulesList);

			StringWriter writer = new StringWriter();
			template.merge(velocityContext, writer);

			FileUtils.writeStringToFile(
					new File(System.getProperty("user.dir") + "/SkriptmateReport/Accessibility/" + getCurrentTimeStamp()
							+ File.separator + pageViolations.getPageFolderName() + "_Summary.html"),
					writer.toString());

			return writer;

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return null;

	}

	/**
	 * Generates rule violations in detail.
	 *
	 * @param rulesViolation the rules violation
	 * @param URL            gets the url
	 * @param title          gets the title of page
	 */
	private void generateRuleViolationsInDetail(List<RuleViolations> rulesViolation, String URL, String title) {
		try {
			CurrentTime();
			for (RuleViolations ruleViolations : rulesViolation) {
				List<Map<String, String>> rulesList = new ArrayList<Map<String, String>>();
				VelocityEngine velocityEngine = new VelocityEngine();
				velocityEngine.init();

				Template template = velocityEngine.getTemplate(
						"./src/main/resources/Accessibility/Artefacts/HtmlTemplates/accessibility_report_rule_violation_template.vm");

				VelocityContext velocityContext = new VelocityContext();
				velocityContext.put("Title", title);
				velocityContext.put("Url", URL);
				velocityContext.put("Date", new Date().toString());
				velocityContext.put("Rule", ruleViolations.getRule());
				velocityContext.put("Description", ruleViolations.getDescription());

				for (int i = 0; i < ruleViolations.getViolationList().size(); i++) {
					Map<String, String> temp = new HashMap<String, String>();
					temp.put("violation", ruleViolations.getViolationList().get(i));
					if (ruleViolations.getViolationMessage().size() > 0) {
						temp.put("message", ruleViolations.getViolationMessage().get(i));
					} else {
						temp.put("message", "");
					}
					if (ruleViolations.getViolationTarget().size() > 0) {
						temp.put("target", ruleViolations.getViolationTarget().get(i));
					} else {
						temp.put("target", "");
					}

					rulesList.add(temp);
				}
				velocityContext.put("RulesList", rulesList);

				File directory = new File(System.getProperty("user.dir") + "/SkriptmateReport/Accessibility/"
						+ currentTimeStamp + File.separator + title);
				boolean status = false;
				if (directory.exists()) {
					status = directory.delete();
				}

				if (status) {
					LOGGER.info("Directory deleted");
				}

				StringWriter writer = new StringWriter();
				template.merge(velocityContext, writer);

				FileUtils.writeStringToFile(new File(System.getProperty("user.dir") + "/SkriptmateReport/Accessibility/"
						+ getCurrentTimeStamp() + File.separator + title + File.separator + ruleViolations.getId()
						+ "_Violation_Report.html"), writer.toString());
			}

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * Generates overall accessibility reports using velocity engine.
	 */
	public void generateOverallAccessibilityReports() {
		try {
			CurrentTime();
			VelocityEngine velocityEngine1 = new VelocityEngine();
			velocityEngine1.init();
			// String cdir = System.getProperty("user.dir");
			Template template1 = velocityEngine1.getTemplate(
					"./src/main/resources/Accessibility/Artefacts/HtmlTemplates/accessibility_report_page_overall_summary_template.vm");
			VelocityContext velocityContext1 = new VelocityContext();		
			velocityContext1.put("Passed", ReportData.passed);
			velocityContext1.put("Failed", ReportData.failed);
			velocityContext1.put("TotalCount", (ReportData.overallPassedRules + ReportData.totalRuleViolations));
			velocityContext1.put("RuleViolations", ReportData.totalRuleViolations);
			velocityContext1.put("OverallPassed", ReportData.overallPassedRules);
			velocityContext1.put("Date", new Date().toString());
			velocityContext1.put("TotalPages", ReportData.totalPages);
			velocityContext1.put("PassPercentage", ((ReportData.overallPassedRules * 100)
					/ (ReportData.overallPassedRules + ReportData.totalRuleViolations)));
			velocityContext1.put("FailurePercentage", ((ReportData.totalRuleViolations * 100)
					/ (ReportData.overallPassedRules + ReportData.totalRuleViolations)));
			
			
			
			List<Map<String, String>> violations = new ArrayList<Map<String, String>>();
			List<PageViolations> pagesViolation = ReportData.violationAnalysis;
			
			

			for (PageViolations pageViolations : pagesViolation) {
				Map<String, String> tempMap = new HashMap<String, String>();
				tempMap.put("PageTitle", pageViolations.getPageTitle());
				tempMap.put("PageURL", pageViolations.getPageUrl());
				tempMap.put("RuleViolations", Integer.toString(pageViolations.getViolationsList().size()));
				tempMap.put("TotalViolations", Integer.toString(pageViolations.getViolationCount()));
				tempMap.put("PageSummary", pageViolations.getPageFolderName() + "_Summary.html");
				tempMap.put("PassedRules", pageViolations.getPassedRules());
				tempMap.put("criticalCount", pageViolations.getCriticalCount().toString());
				tempMap.put("majorCount", pageViolations.getMajorCount().toString());
				tempMap.put("minorCount", pageViolations.getMinorCount().toString());
				violations.add(tempMap);				
				velocityContext1.put("criticalCount", pageViolations.getCriticalCount().toString());
				velocityContext1.put("majorCount", pageViolations.getMajorCount().toString());
				velocityContext1.put("minorCount", pageViolations.getMinorCount().toString());
			}

			velocityContext1.put("violations", violations);
			StringWriter writer1 = new StringWriter();
			template1.merge(velocityContext1, writer1);

			FileUtils
					.writeStringToFile(
							new File(System.getProperty("user.dir") + "/SkriptmateReport/Accessibility/"
									+ getCurrentTimeStamp() + "/AccessibilityTesting_Overall_Summary.html"),
							writer1.toString());

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	public static void CurrentTime() {
		if (currentTimeStamp == null || currentTimeStamp.equals("")) {
			currentTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			setCurrentTimeStamp(currentTimeStamp);
			System.out.println(currentTimeStamp);
		}
	}

	public static String getCurrentTimeStamp() {
		return currentTimeStamp;
	}

	public static void setCurrentTimeStamp(String currentTimeStamp) {
		AccessibilityReportEngine.currentTimeStamp = currentTimeStamp;
	}
}
