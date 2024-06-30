package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.User;
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.StringUtils;

public class CreateUserTest extends BaseTest {

	Integer userId;
	
	@BeforeMethod
	public void getUserSetup() {
		restClient = new RestClient(prop, baseURI);
	}
	
	@DataProvider
	public Object[][] getUserTestData() {
	    return new Object[][] {
	        {"Subodh", "male", "active"},
	        {"Seema", "female", "inactive"},
	        {"Madhuri", "female", "active"}
	    };
	}

	@DataProvider
	public Object[][] getUserTestSheetData() throws InvalidFormatException {
		return ExcelUtil.getTestData(APIConstants.GOREST_USER_SHEET_NAME);

	}

	@Test(dataProvider = "getUserTestSheetData")
	public void postUser(String name, String gender, String status) {
		
//		User user = new User("Buffy", StringUtils.getRandomEmailId(), "male", "active");
		User user = User.builder().name(name).email(StringUtils.getRandomEmailId()).gender(gender).status(status).build();

		userId = restClient.post("/public/v2/users", "json", user, true, true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode()).extract().path("id");
		System.out.println(userId);
		
	}
	
	@Test(dependsOnMethods = "postUser")
	public void getUser() {
		restClient.get("/public/v2/users/"+userId, true, true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}
	
	
	
	
}
