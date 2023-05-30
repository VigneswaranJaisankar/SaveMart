package com.scripted.jsonParser;

import java.io.Serializable;

import com.scripted.selfhealing.HealingConfig;

public class Project {

	private String projectName;
	private String pageUrl;
	private ObjectRepository objectRepository;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public ObjectRepository getObjectRepository() {
		return objectRepository;
	}
	public void setObjectRepository(ObjectRepository objectRepository) {
		this.objectRepository = objectRepository;
	}
//	@Override
//	public String toString() {
//		String objects = "";
//		if (HealingConfig.Jsoncategory == "web") {
//			objects = "Project [projectName=" + projectName + ", pageUrl=" + pageUrl + ", objectRepository=" + objectRepository + "]";
//		}else if (HealingConfig.Jsoncategory == "mobile"){
//			objects = "Project [projectName=" + projectName + ", objectRepository=" + objectRepository + "]";			
//		}
//		return objects;
//	}
//	
	@Override
	public String toString() {
		String objects = "";		
			return "Project [projectName=" + projectName + ", pageUrl=" + pageUrl + ", objectRepository=" + objectRepository + "]";	
		
	}

	
}
