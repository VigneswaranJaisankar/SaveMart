package com.scripted.jsonParser;

import java.util.concurrent.ConcurrentHashMap;

public class LocatorsMob {
	
	private String id;
	private String name;
	private String classname;
	private String iframeElement;
	private String css;
	private ConcurrentHashMap<String,String> xpath;
	private ConcurrentHashMap<String,String> attributes;
	private ConcurrentHashMap<String,String> selectedLocator;
	
	private String selectorMethod;
	private String selectorValue;
	private ConcurrentHashMap<String,String> primaryLocators;
	private ConcurrentHashMap<String,String> xpathLocators;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getIframeElement() {
		return iframeElement;
	}
	public void setIframeElement(String iframeElement) {
		this.iframeElement = iframeElement;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	public ConcurrentHashMap<String, String> getXpath() {
		return xpath;
	}
	public void setXpath(ConcurrentHashMap<String, String> xpath) {
		this.xpath = xpath;
	}
	public ConcurrentHashMap<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(ConcurrentHashMap<String, String> attributes) {
		this.attributes = attributes;
	}
	public ConcurrentHashMap<String, String> getSelectedLocator() {
		return selectedLocator;
	}
	public void setSelectedLocator(ConcurrentHashMap<String, String> selectedLocator) {
		this.selectedLocator = selectedLocator;
	}
	
	public ConcurrentHashMap<String, String> getprimaryLocators() {
		return primaryLocators;
	}
	public void setprimaryLocators(ConcurrentHashMap<String, String> primaryLocators) {
		this.primaryLocators = primaryLocators;
	}
	
	
	public ConcurrentHashMap<String, String> getxpathLocators() {
		return xpathLocators;
	}
	public void setxpathLocators(ConcurrentHashMap<String, String> xpathLocators) {
		this.xpathLocators = xpathLocators;
	}
	
	public String getselectorMethod() {
		return selectorMethod;
	}
	public void setselectorMethod(String selectorMethod) {
		this.selectorMethod = selectorMethod;
	}
	
	public String getselectorValue() {
		return selectorValue;
	}
	public void setselectorValue(String selectorValue) {
		this.selectorValue = selectorValue;
	}
	@Override
	public String toString() {
		return "Locators [primaryLocators=" + primaryLocators + ",  attributes=" + attributes + ", selectorMethod=" + selectorMethod
				+ ",selectorValue=" + selectorValue +",xpathLocators=" + xpathLocators +"]";
	}

}
