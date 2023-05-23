package com.scripted.accessibility.ui;

import java.io.File;

import org.apache.logging.log4j.core.config.Configurator;

/**
 * The Class AccessibilityExecutor class. This is entry point for accessibility
 * execution.
 */
public class AccessibilityExecutor {
	static String log4j_Path = "src/main/resources/LogConf/log4j3.xml";
	private static String cdir = System.getProperty("user.dir");

	/**
	 * The main method for Executing Accessibility Executor Window and
	 * PropertiesConfigurator is used to configure logger from properties file
	 * 
	 * @param args the arguments
	 */
	public static void main(final String[] args) {

		ClassLoader classLoader = AccessibilityExecutor.class.getClassLoader();
		// File file = new File(classLoader.getResource("log4j.properties").getFile());

		// PropertiesConfigurator is used to configure logger from properties file
//		PropertyConfigurator.configure(file.getAbsolutePath());

		String log4jPropFilePath = cdir + File.separator + log4j_Path;

		Configurator.initialize(null, log4jPropFilePath);
		new AccessibilityExecutorWindow();
	}
}
