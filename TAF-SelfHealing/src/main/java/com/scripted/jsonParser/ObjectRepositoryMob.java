package com.scripted.jsonParser;

import com.scripted.selfhealing.HealingConfig;

public class ObjectRepositoryMob {
	
	private WebObjectRepo webObjectRepo;
	private mobileOR mobileOR;

	@Override
	public String toString() {		
		
			return "ObjectRepository [mobileOR=" + mobileOR + "]";
		
	}
	
	public mobileOR getmobileOR() {
		
		return mobileOR;
	}
	
	public void setmobileOR(mobileOR mobileOR) {
		this.mobileOR = mobileOR;
	}


}
