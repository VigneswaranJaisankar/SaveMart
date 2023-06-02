@tag
Feature: Validate the LuckySupermarket Offer Images are Displayed
  I want to use this template for my feature file
  
  
@Luckysupermarket
Scenario: Validate the Offer Images are Displayed in Luckymart
  Given the user launch the browser and enter url luckymart
	When the user clicks the Log In button
  Then the user enters the username
  Then the user enters the password
  And user clicks login button to enter homepage
  Then user click the Lucky Coupons Icon
  Then user check the available offers
  And user see the displayed offers in luckysupermarket
  
@PurchaseOrder  
Scenario: Verify purchase Order Limits
	Then User check the purchase order limit for all the offers
	And User should see the correct purchase order limits for each offer

