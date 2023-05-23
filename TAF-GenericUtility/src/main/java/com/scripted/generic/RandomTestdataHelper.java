package com.scripted.generic;

import com.github.javafaker.Faker;

public class RandomTestdataHelper {
	
	 public enum Category {
		    USER_FIRSTNAME,USER_LASTNAME,USER_FULLNAME,USER_PREFIX,USER_SUFFIX,USER_JOBTIILE,USER_USERNAME,USER_BLOODGROUP,USER_MIDDLENAME,
            ADDRESS_STREETNAME,ADDRESS_STREETADDRESSNUMBER,ADDRESS_STREETADDRESS,ADDRESS_FULLADDRESS,ADDRESS_BUILDINGNIMBER,ADDRESS_COUNTRYCODE,ADDRESS_COUNTRY,ADDRESS_TIMEZONE,ADDRESS_CITYNAME,ADREESS_CITY,ADDRESS_SECONDARYADDRESS,ADDRESS_ZIPCODE,ADDRESS_STATE,PAYMENT_CCNUMBER,PAYMENT_EXPIRYDATE,PAYMENT_CVV,RANDOM_NUMERIC_5,RANDOM_NUMERIC_10,RANDOM_NUMERIC_15, RANDOM_ALPHASTRING_15, RANDOM_ALPHASTRING_10, RANDOM_ALPHASTRING_5, RANDOM_ALPHANUMERIC_5, RANDOM_ALPHANUMERIC_10, RANDOM_ALPHANUMERIC_15,RANDOM_NUMERIC,RANDOM_ALPHASTRING, RANDOM_ALPHANUMERIC;	    
		   
		 }
	
	
	public static String get(Category value) {
		String output = "";
	
		 Faker faker = new Faker();
		try {		
			switch (value) {
			
			case USER_FIRSTNAME :
			output =faker.name().firstName();
		break;
			case USER_LASTNAME :
				output =faker.name().lastName();
			break;	
			
			case USER_FULLNAME :
				output =faker.name().fullName();
			break;	
			case USER_PREFIX :
				output =faker.name().prefix();
			break;	
			case USER_SUFFIX :
				output =faker.name().suffix();
			break;	
			case USER_JOBTIILE :
				output =faker.name().title();
			break;	
			case USER_USERNAME :
				output =faker.name().username();
			break;	
			case USER_BLOODGROUP :
				output =faker.name().bloodGroup();
			break;	
			case USER_MIDDLENAME :
				output =faker.name().nameWithMiddle();
			break;	
			case  ADDRESS_STREETNAME:
				output =faker.address().streetName();
			break;
			case ADDRESS_STREETADDRESSNUMBER :
				output =faker.address().streetAddressNumber();
			break;
			case ADDRESS_STREETADDRESS :
				output =faker.address().streetAddress();
			break;
			case ADDRESS_FULLADDRESS :
				output =faker.address().fullAddress();
			break;
			case ADDRESS_BUILDINGNIMBER:
				output =faker.address().buildingNumber();
			break;
			case ADDRESS_COUNTRYCODE :
				output =faker.address().countryCode();
			break;
			case ADDRESS_COUNTRY :
				output =faker.address().country();
			break;
			case ADDRESS_TIMEZONE :
				output =faker.address().timeZone();
			break;
			case ADDRESS_CITYNAME :
				output =faker.address().cityName();
			break;
			case ADREESS_CITY :
				output =faker.address().city();
			break;
			case ADDRESS_SECONDARYADDRESS :
				output =faker.address().secondaryAddress();
			break;
			case ADDRESS_ZIPCODE :
				output =faker.address().zipCode();
			break;
			case ADDRESS_STATE :
				output =faker.address().state();
			break;
			case PAYMENT_CCNUMBER :
				output =faker.business().creditCardNumber();
			break;
			case PAYMENT_EXPIRYDATE :
				output =faker.business().creditCardExpiry();
			break;
			case PAYMENT_CVV :
				output =faker.number().digits(3);
			break;
			case RANDOM_NUMERIC_5 :
				output =faker.number().digits(5);
			break;
			case RANDOM_NUMERIC_10 :
				output =faker.number().digits(10);
			break;
			case RANDOM_NUMERIC_15 :
				output =faker.number().digits(15);
			break;
			case RANDOM_ALPHASTRING_15 :
				output =faker.regexify("[a-zA-Z]{15}");
			break;
			case RANDOM_ALPHASTRING_10 :
				output =faker.regexify("[a-zA-Z]{10}");
			break;
			case RANDOM_ALPHASTRING_5 :
				output =faker.regexify("[a-zA-Z]{5}");
			break;
			case RANDOM_ALPHANUMERIC_5 :
				output =faker.regexify("[a-zA-Z0-9]{5}");
			break;
			case RANDOM_ALPHANUMERIC_10 :
				output =faker.regexify("[a-zA-Z0-9]{10}");
			break;
			case RANDOM_ALPHANUMERIC_15 :
				output =faker.regexify("[a-zA-Z0-9]{15}");
			break;
			case RANDOM_ALPHASTRING:
				output =faker.regexify("[a-zA-Z]{10}");
			break;
			case RANDOM_NUMERIC:
				output =faker.number().digits(10);
			break;
			case RANDOM_ALPHANUMERIC :
				output =faker.regexify("[a-zA-Z0-9]{10}");
			break;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
		return output;
	}
	
	
	public static String get(Category value, Integer number) {
		String output = "";
		Faker faker = new Faker();
		try {
			switch (value) {

			case RANDOM_NUMERIC:
				output = faker.number().digits(number);
				break;
			case RANDOM_ALPHASTRING:
				String num = number.toString();
				output = faker.regexify("[a-zA-Z]{" + num + "}");
				break;
			case RANDOM_ALPHANUMERIC:
				String len = number.toString();
				output = faker.regexify("[a-zA-Z]{" + len + "}");
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return output;

	}

}
