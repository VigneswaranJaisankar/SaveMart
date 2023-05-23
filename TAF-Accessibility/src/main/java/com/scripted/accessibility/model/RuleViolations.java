/**
 * This Class is Model Class for the Violation Lists.
 */
package com.scripted.accessibility.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class RuleViolations Gets and Sets the rule, description, samples, impact, helpUrl and id.

 */
public class RuleViolations {

	/** The rule. */
	String rule;
	
	/** The description. */
	String description;
	
	/** The samples. */
	String samples;
	
	/** The impact. */
	String impact;
	
	/** The help URL. */
	String helpURL;
	
	/** The id. */
	String id;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the help URL.
	 *
	 * @return the help URL
	 */
	public String getHelpURL() {
		return helpURL;
	}

	/**
	 * Sets the help URL.
	 *
	 * @param helpURL the new help URL
	 */
	public void setHelpURL(String helpURL) {
		this.helpURL = helpURL;
	}

	/**
	 * Gets the impact.
	 *
	 * @return the impact
	 */
	public String getImpact() {
		return impact;
	}

	/**
	 * Sets the impact.
	 *
	 * @param impact the new impact
	 */
	public void setImpact(String impact) {
		this.impact = impact;
	}

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Sets the target.
	 *
	 * @param target the new target
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/** The target. */
	String target;

	/**
	 * Gets the samples.
	 *
	 * @return the samples
	 */
	public String getSamples() {
		return samples;
	}

	/**
	 * Sets the samples.
	 *
	 * @param samples            the samples to set
	 */
	public void setSamples(String samples) {
		this.samples = samples;
	}

	/** The violation list. */
	List<String> violationList = new ArrayList<String>();
	
	/** The violation message. */
	List<String> violationMessage = new ArrayList<String>();
	
	/** The violation target. */
	List<String> violationTarget = new ArrayList<String>();

	/**
	 * Gets the rule.
	 *
	 * @return the rule
	 */
	public String getRule() {
		return rule;
	}

	/**
	 * Sets the rule.
	 *
	 * @param rule            the rule to set
	 */
	public void setRule(String rule) {
		this.rule = rule;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the violation list.
	 *
	 * @return the violationList
	 */
	public List<String> getViolationList() {
		return violationList;
	}

	/**
	 * Gets the violation message.
	 *
	 * @return the violationMessage
	 */
	public List<String> getViolationMessage() {
		return violationMessage;
	}

	/**
	 * Gets the violation target.
	 *
	 * @return the violationTarget
	 */
	public List<String> getViolationTarget() {
		return violationTarget;
	}

	/**
	 * Sets the violation.
	 *
	 * @param violation the new violation
	 */
	public void setViolation(String violation) {
		this.violationList.add(violation);
	}

	/**
	 * Sets the violation message.
	 *
	 * @param violationMessage the new violation message
	 */
	public void setViolationMessage(String violationMessage) {
		this.violationMessage.add(violationMessage);
	}

	/**
	 * Sets the violation target.
	 *
	 * @param violationTarget the new violation target
	 */
	public void setViolationTarget(String violationTarget) {
		this.violationTarget.add(violationTarget);
	}
}
