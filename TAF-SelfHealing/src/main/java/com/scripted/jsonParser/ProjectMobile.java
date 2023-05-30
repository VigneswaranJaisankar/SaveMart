package com.scripted.jsonParser;

import java.io.Serializable;

import com.scripted.selfhealing.HealingConfig;

public class ProjectMobile {
	
		private String projectName;
		private String pageUrl;
		private ObjectRepositoryMob objectRepository;
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
		public ObjectRepositoryMob getObjectRepository() {
			return objectRepository;
		}
		public void setObjectRepository(ObjectRepositoryMob objectRepository) {
			this.objectRepository = objectRepository;
		}
		
		@Override
		public String toString() {
				return "Project [projectName=" + projectName + ", objectRepository=" + objectRepository + "]";			
			
		}

}
