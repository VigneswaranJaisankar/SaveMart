@tag
Feature: Validate the Savemart Offer Images are Displayed
  I want to use this template for my feature file
    
Background:
		Given User launch the browser and enter the savemart url
    When user click on Log In button
    Then enter the valid username
    Then enter the valid password
    And click on login button to enter homepage
	 
@Savemart
Scenario: Validate the Offer Images are Displayed
  	When user click on Savemart Coupons Icon
    Then user view the offers and click on image
    And User view the displayed offers
   
@Savemartofferslimit    
Scenario: Validate the Offer Order Limits
	Then User click on product to check the limit     
	
@Savemartgetofferid
Scenario: Get User offerId from login endpoint
	Given User add header
	And User add basic authentication for login
	When User send "GET" request for login endpoint
	Then User verify the status code is 200
	And User verify the response body present as "offer id" and get the id saved	