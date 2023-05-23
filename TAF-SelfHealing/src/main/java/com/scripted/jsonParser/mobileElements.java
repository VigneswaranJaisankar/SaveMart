package com.scripted.jsonParser;

public class mobileElements {
	
	private String elementName;
	private LocatorsMob locators;
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	public LocatorsMob getLocators() {
		return locators;
	}
	public void setLocators(LocatorsMob locators) {
		this.locators = locators;
	}
	
	@Override
	public String toString() {
		return "mobileElements [elementName=" + elementName + ", locators=" + locators + "]";
	}

}
