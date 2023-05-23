package com.scripted.brokenlink;

public class BrokenLinkInfo {
	String responceCode ;
	String Scannedlink;
	String totalPages;
	String scenarioStatus;
	String applicationurl;
	String Pagename;
	public String getPagename() {
		return Pagename;
	}
	public void setPagename(String pagename) {
		Pagename = pagename;
	}
	
	public String getScenarioStatus() {
		return scenarioStatus;
	}
	public void setScenarioStatus(String scenariostatus) {
		scenarioStatus = scenariostatus;
	}
	public String getResponceCode() {
		return responceCode;
	}
	public void setResponceCode(String ResponceCode) {
		responceCode = ResponceCode;
	}
	public String getScannedlink() {
		return Scannedlink;
	}
	public void setScannedlink(String scannedlink) {
		Scannedlink = scannedlink;
	}
	public String getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(String TPages) {
		totalPages = TPages;
	}
	
}
