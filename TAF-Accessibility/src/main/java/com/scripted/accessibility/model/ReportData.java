/**
 * This Class is Public Class for capturing the Report Data.
 */
package com.scripted.accessibility.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ReportData contains totalPages, passed, failed, totalFlaws, totalRuleViolation, violationAnalysis List and overallPassedRules.
 */
public class ReportData {

	/** The total pages. */
	public static int totalPages = 0;

	/** The passed. */
	public static int passed = 0;

	/** The failed. */
	public static int failed = 0;

	/** The total flaws. */
	public static int totalFlaws = 0;

	/** The total rule violations. */
	public static int totalRuleViolations = 0;

	/** The violation analysis. */
	public static List<PageViolations> violationAnalysis = new ArrayList<PageViolations>();
	
	/** The overall passed rules. */
	public static int overallPassedRules = 0;
}
