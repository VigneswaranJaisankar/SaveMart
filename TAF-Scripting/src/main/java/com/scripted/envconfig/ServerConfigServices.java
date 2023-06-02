package com.scripted.envconfig;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Key;

@Config.Sources({"file:src/main/resources/EnvDetails/ServiceConfig.properties"})
public interface ServerConfigServices extends Config {
	@Key("QA.url")
    String QA_url();
	
	@Key("QA.chained")
    String QA_Chained();
	
	@Key("QA.parameter1")
    String QA_parameter1();
	
	@Key("QA.parameter2")
    String QA_parameter2();
	
	@Key("DEV.url")
    String DEV_url();
	
	@Key("DEV.chained")
    String DEV_Chained();
	
	@Key("DEV.parameter1")
    String DEV_parameter1();
	
	@Key("DEV.parameter2")
    String DEV_parameter2();
	
	@Key("STAGING.url")
    String STAGING_url();
	
	@Key("STAGING.chained")
    String STAGING_Chained();
	
	@Key("STAGING.parameter1")
    String STAGING_parameter1();
	
	@Key("STAGING.parameter2")
    String STAGING_parameter2();
			
	@Key("STAGING.proxyAndPort")
    String STAGING_proxyAndPort();
	@Key("QA.proxyAndPort")
    String QA_proxyAndPort();
	@Key("DEV.proxyAndPort")
    String DEV_proxyAndPort();
			
			
	

	
	

}