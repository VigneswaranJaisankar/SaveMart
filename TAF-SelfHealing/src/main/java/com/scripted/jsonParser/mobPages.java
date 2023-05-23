package com.scripted.jsonParser;

import java.util.List;

public class mobPages {
	
	private String pageName;
	private List<WebElements> webElements;
	private List<mobileElements> mobileElements;
	
	@Override
	public String toString() {
			return "Pages [pageName=" + pageName + ", mobileElements=" + mobileElements + "]";		
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public List<WebElements> getWebElements() {
		return webElements;
	}
	public void setWebElements(List<WebElements> webElements) {
		this.webElements = webElements;
	}
	
	public void setMobileElements(List<mobileElements> mobileElements) {
		this.mobileElements = mobileElements;
	}
	
	public List<mobileElements> mobileElements() {
		return mobileElements;
	}

}
