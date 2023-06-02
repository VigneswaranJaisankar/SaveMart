@tag
Feature: Luckymart
  I want to use this template for lucymartfeature

@Luckysupermarketlogin
Scenario: TC_001_LMLOGIN
	Given User launch the browser and enter lucky url
	When User clicks the Log In button
  When User enter username
  Then User enter password
  And User clicks login button to navigates homepage
  And User click on MyRewards button and verify available rewards
  Then User click on signout button