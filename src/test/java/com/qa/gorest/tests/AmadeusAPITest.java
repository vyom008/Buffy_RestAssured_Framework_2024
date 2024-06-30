package com.qa.gorest.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AmadeusAPITest extends BaseTest{
	
	private String accessToken;
	
	@Parameters({"baseURI", "grantType", "clientId", "clientSecret"})
	@BeforeMethod
	public void flightAPISetup(String baseURI, String grantType, String clientId, String clientSecret) {
		restClient = new RestClient(prop, baseURI);
		accessToken = restClient.getAccessToken(AMADEUS_TOKEN_ENDPOINT, grantType, clientId, clientSecret);
	}
	
	@Test
	public void getFlightInfoTest() {
		
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("origin", "PAR");
		queryParams.put("maxPrice", "200");
		
		Map<String, String> headersMap = new HashMap<>();
		headersMap.put("Authorization", "Bearer "+accessToken);
		
		Response response = restClient.getAmadeus(AMADEUS_FLIGHTBOOKING_ENDPOINT, headersMap, queryParams, false, true).then().log().all().assertThat()
		.statusCode(APIHttpStatus.OK_200.getCode()).and().extract().response();
		
		JsonPath js = response.jsonPath();
		
		String type = js.get("data[0].type");
		System.out.println(type);
		

	}
	
}
