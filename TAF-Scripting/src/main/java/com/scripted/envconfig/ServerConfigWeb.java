package com.scripted.envconfig;
import org.aeonbits.owner.Config;


//@Config.Sources({"classpath:${env}.properties"})
@Config.Sources({"file:src/main/resources/EnvDetails/WebConfig.properties"})
public interface ServerConfigWeb extends Config {
	@Key("QA.url")
    String QA_url();
	
	@Key("STAGING.url")
    String STAGING_url();

}
