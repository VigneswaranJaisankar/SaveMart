package com.scripted.envconfig;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.AfterMethod;


public class EnvConfigDefine {
	String baseUrl = "";
	String chainedUrl = "";
	String parameter1 = "";
	String parameter2 = "";
	String proxyAndPort = "";
 
	  @Parameters({"env","technology"})
	  @BeforeMethod(alwaysRun = true)
	  public void beforeMethod(final String env , final String technology) {
		  if (technology.toUpperCase().equals("WEB")) {
		  ServerConfigWeb cfg = ConfigFactory.create(ServerConfigWeb.class);
		  switch (env) {
	      case "QA":
	          baseUrl = cfg.QA_url();
	          break;
	      case "STAGING":
	          baseUrl = cfg.STAGING_url();
	          break;
	      default:
	          //Do nothing
	  }
      }else if (technology.toUpperCase().equals("WEBSERVICES")) {
    	  
    	  ServerConfigServices cfg = ConfigFactory.create(ServerConfigServices.class);
		  
		switch (env) {
		  case "DEV":
	          baseUrl = cfg.DEV_url();
	          chainedUrl = cfg.DEV_Chained();
	          parameter1 = cfg.DEV_parameter1();
	          parameter2 = cfg.DEV_parameter2();
	          proxyAndPort= cfg.DEV_proxyAndPort();
	          
	          break;
	      case "QA":
	          baseUrl = cfg.QA_url();
	          chainedUrl = cfg.QA_Chained();
	          parameter1 = cfg.QA_parameter1();
	          parameter2 = cfg.QA_parameter2();
	          proxyAndPort= cfg.QA_proxyAndPort();
	          break;
	      case "STAGING":
	          baseUrl = cfg.STAGING_url();
	          chainedUrl = cfg.STAGING_Chained();
	          parameter1 = cfg.STAGING_parameter1();
	          parameter2 = cfg.STAGING_parameter2();
	          proxyAndPort= cfg.STAGING_proxyAndPort();
	          break;
	      default:
	          //Do nothing  
		 }
      }
	  }
	  
public String baseurl() {
	
	return baseUrl;
	
}

public String chainedUrl() {	
	return chainedUrl;
	
}

public String parameter1() {	
	return parameter1;
	
}

public String parameter2() {	
	return parameter2;
	
}


public String proxyandport() {	
	return proxyAndPort;
	
}
}
