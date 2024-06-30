package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

public class GetUserTest extends BaseTest {
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}

	//code smell: sonarQube
	@Test(priority = 3)
	public void getAllUser() {
		restClient.get(GOREST_ENDPOINT, true, true).then().log().all().assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}

	@Test(priority = 2)
	public void getUser() {
		restClient.get(GOREST_ENDPOINT+"/6989626", true, true).then().log().all().assertThat().statusCode(APIHttpStatus.OK_200.getCode()).and()
				.body("id", equalTo(6989626));
	}

	@Test(priority = 1)
	public void getUserWithQueryParamsTest() {
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("name", "buffy");
		queryParams.put("status", "active");
		restClient.get(GOREST_ENDPOINT, null, queryParams, true, true).then().log().all().assertThat()
				.statusCode(APIHttpStatus.OK_200.getCode());
	}
}
