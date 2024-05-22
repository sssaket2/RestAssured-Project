package restAssuredTests;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ExcelUtils;

import  static io.restassured.RestAssured.*;
import  static io.restassured.matcher.RestAssuredMatchers.*;
import  static org.hamcrest.Matchers.*;
import 	static org.testng.Assert.assertEquals;
import 	static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;



public class ApiTestCases {
	
	Map<String, String> map = new HashMap<String, String>();
	ExcelUtils data = new ExcelUtils();
	
	
	
	@Test
	public void verifyStatusCode() throws JSONException {	
		
		map = data.readData();
		String baseUrl = map.get("Base_Url");
		Response res = 	given()
						.when()
						.get(baseUrl);
		
		//System.out.println(res.statusCode());
		
		
		
		String StatusCode = map.get("Success_Status_Code");
		int expectedStatusCode = Integer.parseInt(StatusCode);
		int ActualStatusCode = res.statusCode();
		assertEquals(ActualStatusCode,expectedStatusCode,"Status code is not matching with given data in Test");
		
	}
	@Test
	public void verifyStatus() throws JSONException {
		
		map = data.readData();
		String baseUrl = map.get("Base_Url");
		
		Response res = 	given()
						.when()
						.get(baseUrl);
		
		//System.out.println(res.statusCode());
		String expectedStatus = map.get("Status_line");
		String ActualStatus = res.statusLine();
		//System.out.println(ActualStatus);
		assertEquals(expectedStatus,ActualStatus,"Status line is not matching with given data in Test");
		
	}
	@Test
	public void verifyPrice() throws JSONException {
		
		map = data.readData();
		String baseUrl = map.get("Base_Url");
		
		Response res = 	given()
						.when()
						.get(baseUrl);
		
		JSONObject jo = new JSONObject(res.asString());
		
		String expectedAEDPrice = map.get("AED_Price");
		String actualAEDPrice = jo.getJSONObject("rates").get("AED").toString();
		//System.out.println(actualAEDPrice);
		assertEquals(expectedAEDPrice,actualAEDPrice, "AED price is not matching with given price in Test data");
		
	}
	@Test
	public void verifyUSDToAEDPrice() throws JSONException {
		
		map = data.readData();
		String baseUrl = map.get("Base_Url");
		
		Response res = 	given()
						.when()
						.get(baseUrl);
		
		JSONObject jo = new JSONObject(res.asString());
		
		String USDtoAEDPricemMin = map.get("AED_Price_Min");
		double expectedUSDtoAEDPricemMin = Double.parseDouble(USDtoAEDPricemMin);
		String USDtoAEDPricemMax = map.get("AED_Price_Max");
		double expectedUSDtoAEDPricemMax = Double.parseDouble(USDtoAEDPricemMax);
		String USDToAEDPrice = jo.getJSONObject("rates").get("AED").toString();
		double actualUSDToAEDPrice = Double.parseDouble(USDToAEDPrice);
		
		//System.out.println(actualAEDPrice);
		if(actualUSDToAEDPrice>=expectedUSDtoAEDPricemMin && actualUSDToAEDPrice<=expectedUSDtoAEDPricemMax)
		{
			Assert.assertTrue(true);
		}else {
			
			Assert.assertTrue(false,"AED price is not between 3.6 & 3.7");
		}
		
	}
	
	@Test
	public void verifyResponseTime() throws JSONException {
		
		map = data.readData();
		String baseUrl = map.get("Base_Url");
		
		Response res = 	given()
						.when()
						.get(baseUrl);
		
		double actulaResponseTime = res.getTimeIn(TimeUnit.MILLISECONDS);
		String minResponseTime = map.get("Min_Response_Time");
		double minExpectedResponseTime = Double.parseDouble(minResponseTime);
		
		if(actulaResponseTime>minExpectedResponseTime) {
			
			Assert.assertTrue(true);
		}else {
			
			Assert.assertTrue(false, "Response time is less than 3 seconds");
		}
		
			}
	
	@Test
	public void verifyTotalCurrencies() throws JSONException {
		
		map = data.readData();
		String baseUrl = map.get("Base_Url");
		
		Response res = 	given()
						.when()
						.get(baseUrl);
		
		
		JSONObject jo = new JSONObject(res.asString());
		
		String numberOfCurrencies = map.get("Number_Of_Currencies");
		double expectedNumberOfCurrencies = Double.parseDouble(numberOfCurrencies);
		double actualNumberOfCurrencies = jo.getJSONObject("rates").length();
		
		
		assertEquals(expectedNumberOfCurrencies,actualNumberOfCurrencies , "Number of currencies is not matching with the given Test Data");
		
		//Saving all the currencies in map
		String str = jo.getJSONObject("rates").toString();
		HashMap<String, String> map = new HashMap<String, String>();
        JSONObject jObject = new JSONObject(str);
        Iterator<String> keys = jObject.keys();
        
        
        while( keys.hasNext() ){
            String key = keys.next(); 
            String value = jObject.getString(key); 
            map.put(key, value);

        }
        
        //System.out.println(map.get("AED"));
        
			}

	@Test
	public void verifyJsonSchema() throws JSONException {
		
			map = data.readData();
			String baseUrl = map.get("Base_Url");
			String fileName = map.get("Json_Schema");
			
			//System.out.println(fileName);
			 
					given()
					.when()
					.get(baseUrl)
			        .then()
			        .assertThat().body(matchesJsonSchemaInClasspath(fileName));
			
			

		
			}
	


}
