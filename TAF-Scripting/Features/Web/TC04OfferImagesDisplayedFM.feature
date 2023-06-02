@tag
Feature: Title of your feature
  I want to use this template for my feature file

Background:
		Given User launch the browser and enter the foodmaxx url
		When User click on the Log In button
    When User enter the username
    Then User enter the password
    And User clicks the login button to enter homepage

@Foodmaxx
Scenario: Validate the Offer Images are Displayed
  	Then User clicks the Foodmaxx Coupons Icon
    And User see the displayed offers with images
    
@Foodmaxx
Scenario: Validate the Offer Images are Displayed
  	Given User launch the browser and enter the foodmaxx url
    Then User clicks the Foodmaxx Coupons Icon
    Then User see the offers and clicks the image
    And User see the displayed offers
    
@Foodmaxxgetofferid    
Scenario: Get User offerId from login endpoint
	Given User add header
	And User add basic authentication for login
	When User send "GET" request for login endpoint
	Then User verify the status code is 200
	And User verify the response body present as "offer id" and get the id saved    