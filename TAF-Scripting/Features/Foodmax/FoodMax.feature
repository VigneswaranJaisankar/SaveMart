@tag
Feature: foodmax
  I want to use this template for my foodmax feature verification

  @Foodmaxlogin
Scenario: TC_001_FMLogin
	Given User launch the browser and enter the foodmaxx url
	When User click on the Log In button
  When User enter the username
  Then User enter the password
  And User click on login
  #And the User click on profile button
  Then User verify MyCoupons option in foodmaxx 
  Then User clicks on the signout button for foodmax
