/**
 * This Class is Model Class for the Page Violation Lists.
 */
package com.scripted.accessibility.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class PageViolations gets and sets the PageVoilationList, Passed Rules, PageUrl, pageFolderName, violationCount.
 *
 */
public class PageViolations {

	/** The page title. */
	String pageTitle;

	/** The page url. */
	String pageUrl;
	
	
	String critical;
	
	String major;
	
	String minor;

	/** The violation count. */
	int violationCount;

	/** The violations list. */
	List<RuleViolations> violationsList = new ArrayList<RuleViolations>();

	/** The page folder name. */
	String pageFolderName;

	/** The passed rules. */
	String passedRules;
	
	
	public void setcriticalcount(String critical) {
		this.critical = critical;
	}
	
	public void setmajorcount(String major) {
		this.major = major;
	}
	
	public void setminorcount(String minor) {
		this.minor = minor;
	}


	public String getCriticalCount() {
		return critical;
	}
	
	public String getMajorCount() {
		return major;
	}
	
	public String getMinorCount() {
		return minor;
	}
	/**
	 * Gets the passed rules.
	 *
	 * @return the passed rules
	 */
	public String getPassedRules() {
		return passedRules;
	}

	/**
	 * Sets the passed rules.
	 *
	 * @param passedRules
	 *            the new passed rules
	 */
	public void setPassedRules(String passedRules) {
		this.passedRules = passedRules;
	}

	/**
	 * Gets the page folder name.
	 *
	 * @return the pageFolderName
	 */
	public String getPageFolderName() {
		return pageFolderName;
	}

	/**
	 * Sets the page folder name.
	 *
	 * @param pageFolderName
	 *            the pageFolderName to set
	 */
	public void setPageFolderName(String pageFolderName) {
		this.pageFolderName = pageFolderName.replaceAll(" ", "_");
	}

	/**
	 * Gets the violations list.
	 *
	 * @return the violationsList
	 */
	public List<RuleViolations> getViolationsList() {
		return violationsList;
	}

	/**
	 * Sets the violations list.
	 *
	 * @param violationList
	 *            the new violations list
	 */
	public void setViolationsList(List<RuleViolations> violationList) {
		this.violationsList = violationList;
	}

	/**
	 * Gets the page title.
	 *
	 * @return the pageTitle
	 */
	public String getPageTitle() {
		return pageTitle;
	}

	/**
	 * Sets the page title.
	 *
	 * @param pageTitle
	 *            the pageTitle to set
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle.replaceAll(" ", "_");
	}

	/**
	 * Gets the page url.
	 *
	 * @return the pageUrl
	 */
	public String getPageUrl() {
		return pageUrl;
	}

	/**
	 * Sets the page url.
	 *
	 * @param pageUrl
	 *            the pageUrl to set
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	/**
	 * Gets the violation count.
	 *
	 * @return the violationCount
	 */
	public int getViolationCount() {
		return violationCount;
	}

	/**
	 * Sets the violation count.
	 *
	 * @param violationCount
	 *            the violationCount to set
	 */
	public void setViolationCount(int violationCount) {
		this.violationCount = violationCount;
	}
	
	
}
