package com.scripted.jsonParser;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
//import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.parser.JSONParser;
import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scripted.selfhealing.HealingConfig;

public class JsonObjectParser {
	private static String cdir = System.getProperty("user.dir");
	String jsonPath;
	static boolean jsonUpdateFlag = false;

	String pageName;
	Locators LocatorValue;
	LocatorsMob LocatorValuemob;
	static ConcurrentHashMap<String, Project> jsonUpdateFilesData = new ConcurrentHashMap<String, Project>();
	static ConcurrentHashMap<String, ProjectMobile> jsonUpdateFilesDatamob = new ConcurrentHashMap<String, ProjectMobile>();

	public JsonObjectParser() {

	}
	public List<String> networkJsonParser() {
		List<String> networkErrorList = new ArrayList<>();
		try {

			String cdir = System.getProperty("user.dir");

			byte[] jsonData = Files.readAllBytes(Paths.get(HealingConfig.ntwrkJson));
			ObjectMapper objectMapper = new ObjectMapper();

			NetworkError networkError = objectMapper.readValue(jsonData, NetworkError.class);
			for (String errorList : networkError.NetworkError) {
				networkErrorList.add(errorList);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return networkErrorList;
	}

	public String readFile(String jsonPath) {
		String fileContent = null;
		try {
			File file = new File(jsonPath);
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			fileContent = new String(chars);
		} catch (Exception e) {
			System.out.println("Exception while reading file content :" + e);
		}

		return fileContent;
	}

	public void encryptFile(String path) {
		try {
			String enc = JsonEncryptor.encryptPass(readFile(path));
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(enc);
			writer.close();	
			System.out.println("File encrypted successfully");
		} catch (Exception e) {
			System.out.println("Exception while encrypting file :" + e);
		}
	}

	public void decryptFile(String path) {
		try {
			String dec = JsonEncryptor.decryptPass(readFile(path));
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(dec);
			writer.close();
			System.out.println("File decrypted successfully");
		} catch (Exception e) {
			System.out.println("Exception while decrypting file : " + e);
		}
	}

	// #Method to parse the output json from spy#
	public ConcurrentHashMap<String, String> jsonMapper(String pageNameValue, String elementNameValue, String locType,
			ConcurrentHashMap<String, String>  HealedEle) throws IOException {
		Boolean status = false;
		// ObjectMapper objectMapper = new ObjectMapper();
		ConcurrentHashMap<String, String> attributeValuesMap = new ConcurrentHashMap<String, String>();
		try {
			Iterator<Entry<String, Project>> iterator = HealingConfig.jsonFilesMapweb.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Project> entry = iterator.next();
				jsonPath = entry.getKey();
				System.out.println("json path " + jsonPath);
				Project project = entry.getValue();
				//StringBuffer value = new StringBuffer();
				List<webPages> pages = project.getObjectRepository().getWebObjectRepo().getPages();

				for (webPages pageObj : pages) {
					pageName = pageObj.getPageName();
					System.out.println("Page name :" + pageName);
					if (pageNameValue.equals(pageName)) {
						List<WebElements> webElements = pageObj.getWebElements();
						for (WebElements webElement : webElements) {
							System.out.println("mobileElementgetelementname" + webElement.getElementName());
							System.out.println("elementNameValue" + elementNameValue);
							if (webElement.getElementName().equals(elementNameValue)) {
								status = true;

								LocatorValue = webElement.getLocators();
								System.out.println("Locator value :"+LocatorValue.toString());
								if (HealedEle != null) {
									System.out.println("Healed Ele value :" + HealedEle);
								/*	if (!locType.trim().equalsIgnoreCase(
											LocatorValue.getSelectedLocator().get("attribute").trim())) {*/
										ConcurrentHashMap<String, String> updData = new ConcurrentHashMap<>();
										updData.put("attribute", HealedEle.get("key"));
										updData.put("value", HealedEle.get("value"));
										//System.out.println("Attribute to be updated in json :" + updData.get("value"));
										LocatorValue.setSelectedLocator(updData);
										//System.out.println("data in project :" + project);
										jsonUpdateFilesData.put(jsonPath, project);
									//	System.out.println("Json array updated : " + jsonFilesData);
										jsonUpdateFlag = true;

									//}

								} else {
									/*if (locType.trim().equalsIgnoreCase(
										LocatorValue.getSelectedLocator().get("attribute").trim())) {*/
										attributeValuesMap.put("id", LocatorValue.getId());
										attributeValuesMap.put("xpath_Follows",LocatorValue.getXpath().get("follows"));
										attributeValuesMap.put("xpath_default",LocatorValue.getXpath().get("default"));
										attributeValuesMap.put("xpath_Parent",LocatorValue.getXpath().get("parent"));
										attributeValuesMap.put("xpath_Attribute",LocatorValue.getXpath().get("attribute"));
										attributeValuesMap.put("xpath_Descendant",LocatorValue.getXpath().get("descendant"));
										attributeValuesMap.put("xpath_Position",LocatorValue.getXpath().get("position"));
										attributeValuesMap.put("xpath_fullXPath",LocatorValue.getXpath().get("fullxpath"));
										attributeValuesMap.put("xpath_Contains",LocatorValue.getXpath().get("contains"));
										attributeValuesMap.put("xpath_Precedance",LocatorValue.getXpath().get("precedance"));
										attributeValuesMap.put("xpath_Ancestor",LocatorValue.getXpath().get("ancestor"));
										attributeValuesMap.put("xpath_child",LocatorValue.getXpath().get("child"));
										attributeValuesMap.put("name", LocatorValue.getName());
										attributeValuesMap.put("css", LocatorValue.getCss());
										attributeValuesMap.put("classname", LocatorValue.getClassname());
										attributeValuesMap.put("iframe", LocatorValue.getIframeElement());
									//} 
									if (!locType.trim().equalsIgnoreCase(
										LocatorValue.getSelectedLocator().get("attribute").trim()))
									{
										attributeValuesMap.put("sel_attr"+":"+LocatorValue.getSelectedLocator().get("attribute"),
												LocatorValue.getSelectedLocator().get("value"));
										attributeValuesMap.put("iframe", LocatorValue.getIframeElement());
									}
								}

							}
						}
						if (status == true) {
							break;

						}
					}
					if (status == true) {

						break;

					}
				}
				if (status == true) {

					break;

				}

			}
		} catch (Exception e) {
			System.out.println("Exception in parser :" + e);
			attributeValuesMap = null;
		}
		if (status == false) {
			attributeValuesMap = null;
		}
     // System.out.println(attributeValuesMap);
		return attributeValuesMap;
	}
	
	// #Method to parse the output json from spy#
		public ConcurrentHashMap<String, String> jsonMapperMobile(String pageNameValue1, String elementNameValue1, String locType1,
				ConcurrentHashMap<String, String>  HealedEle1) throws IOException {
//			System.out.println("Page name in json parser: "+pageNameValue1);
//			System.out.println("Element name in json parser: "+elementNameValue1);		
			Boolean status = false;
			// ObjectMapper objectMapper = new ObjectMapper();
			ConcurrentHashMap<String, String> attributeValuesMap1 = new ConcurrentHashMap<String, String>();
			try {
				Iterator<Entry<String, ProjectMobile>> iterator = HealingConfig.jsonFilesMapmob.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, ProjectMobile> entry = iterator.next();
					jsonPath = entry.getKey();
					System.out.println("json path " + jsonPath);
					ProjectMobile project = entry.getValue();
					//StringBuffer value = new StringBuffer();
					if (project.getObjectRepository().getmobileOR()!=null) {
					List<mobPages> pages = project.getObjectRepository().getmobileOR().getPages();

					for (mobPages pageObj : pages) {
						pageName = pageObj.getPageName();
						System.out.println("Page name :" + pageName);
						if (pageNameValue1.equals(pageName)) {
							List<mobileElements> mobileElements = pageObj.mobileElements();
							for (mobileElements mobileElement : mobileElements) {
//								System.out.println("mobileElementgetelementname" + mobileElement.getElementName());
//								System.out.println("elementNameValue" + elementNameValue1);
								if (mobileElement.getElementName().equals(elementNameValue1)) {
									status = true;
									LocatorValuemob = mobileElement.getLocators();
//									System.out.println("Locator value :"+LocatorValuemob.toString());
									if (HealedEle1 != null) {
										System.out.println("Healed Ele value :" + HealedEle1);
									/*	if (!locType.trim().equalsIgnoreCase(
												LocatorValue.getSelectedLocator().get("attribute").trim())) {*/
											ConcurrentHashMap<String, String> updData = new ConcurrentHashMap<>();
											updData.put("attribute", HealedEle1.get("key"));
											updData.put("value", HealedEle1.get("value"));
											LocatorValuemob.setSelectedLocator(updData);
											jsonUpdateFilesDatamob.put(jsonPath, project);
											jsonUpdateFlag = true;

										//}

									} else {
										/*if (locType.trim().equalsIgnoreCase(
											LocatorValue.getSelectedLocator().get("attribute").trim())) {*/
										attributeValuesMap1.put("resource-id", LocatorValuemob.getprimaryLocators().get("resource-id"));
										attributeValuesMap1.put("text", LocatorValuemob.getprimaryLocators().get("text"));
										attributeValuesMap1.put("class", LocatorValuemob.getprimaryLocators().get("class"));
										attributeValuesMap1.put("content-desc", LocatorValuemob.getprimaryLocators().get("content-desc"));
										attributeValuesMap1.put("xpath_index",LocatorValuemob.getxpathLocators().get("index"));
										attributeValuesMap1.put("xpath_content-desc",LocatorValuemob.getxpathLocators().get("content-desc"));
										attributeValuesMap1.put("xpath_text",LocatorValuemob.getxpathLocators().get("text"));
										attributeValuesMap1.put("xpath_position",LocatorValuemob.getxpathLocators().get("position"));
										//} 
										if (!locType1.trim().equalsIgnoreCase(
												LocatorValuemob.getselectorMethod().trim()))
										{
											attributeValuesMap1.put("sel_attr"+":"+LocatorValuemob.getselectorMethod(),LocatorValuemob.getselectorValue());
											attributeValuesMap1.put("iframe", LocatorValuemob.getIframeElement());
										}
									}

								}
							}
							if (status == true) {
								break;

							}
						}
						if (status == true) {

							break;

						}
					}
				}
					if (status == true) {

						break;

					}

				}
			} catch (Exception e) {
				System.out.println("Exception in parser :" + e);
				attributeValuesMap1 = null;
			}
			if (status == false) {
				attributeValuesMap1 = null;
			}
	     // System.out.println(attributeValuesMap);
			return attributeValuesMap1;
		}

	public void JsonUpdate() {
	//	System.out.println("json update status flag : " + jsonUpdateFlag);
		if (jsonUpdateFlag == true) {
			Iterator<Entry<String, Project>> iterator = jsonUpdateFilesData.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Project> entry = iterator.next();
				jsonPath = entry.getKey();
				System.out.println("Json files to be updated : "+jsonPath);
				Project jsonData = entry.getValue();
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonPath), jsonData);
				//	System.out.println("Updated json written back");
					encryptFile(jsonPath);

				} catch (Exception e) {
				//	System.out.println("Error while writing back updated json file : " + e);
				}
			}
		}
	}
}
