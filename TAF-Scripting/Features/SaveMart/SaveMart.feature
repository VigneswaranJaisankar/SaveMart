@tag
Feature: Savemart feature
  I want to use this template for savemart functionality

@Savemartlogin
Scenario: TC_001_SMLogin
	Given User launch the browser and enter the savemart url
	When User click on Log In button
	When User enter the valid username
	Then User enter the valid password
	And User clicks on login button to enter homepage
	Then User verify the MyRewards option in savemart
  And User click on MyRewards button and verify available rewards in saveMart
  Then User click on signout button for savemart
  