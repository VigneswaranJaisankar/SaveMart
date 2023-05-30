package com.scripted.jsonParser;

import java.util.List;

public class WebObjectRepo {

	private List<webPages> pages;

	@Override
	public String toString() {
		return "WebObjectRepo [pages=" + pages + "]";
	}

	public List<webPages> getPages() {
		return pages;
	}

	public void setPages(List<webPages> pages) {
		this.pages = pages;
	}
	
	
}
