package com.scripted.jsonParser;

import com.scripted.selfhealing.HealingConfig;

public class ObjectRepository {
	
	private WebObjectRepo webObjectRepo;
	private mobileOR mobileOR;

	@Override
	public String toString() {				
			return "ObjectRepository [webObjectRepo=" + webObjectRepo + "]";
	}

	public WebObjectRepo getWebObjectRepo() {
				
	   return webObjectRepo;

	}
	
	public mobileOR getmobileOR() {
		
		return mobileOR;
	}
	
	
	public void setWebObjectRepo(WebObjectRepo webObjectRepo) {
		this.webObjectRepo = webObjectRepo;
	}


	public void setmobileOR(mobileOR mobileOR) {
		this.mobileOR = mobileOR;
	}

}
